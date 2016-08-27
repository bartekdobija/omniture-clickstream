package com.github.bartekdobija.omniture.row;

import com.github.bartekdobija.omniture.metadata.*;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

public class RowParser {

  public static final char DEFAULT_ARRAY_SEPARATOR = ',';
  public static final char DEFAULT_COLUMN_SEPARATOR = '\t';

  private Column[] columns;
  private LookupTableIndex lookupTableIndex;
  private char arraySeparator;
  private char separator;
  private Row rowTemplate;
  private Timestamp timestampTemplate;

  protected RowParser(
      Column[] cols,
      LookupTableIndex index,
      char columnSeparator,
      char arrSeparator) throws RowParserException {
    columns = cols;
    lookupTableIndex = index;
    separator = columnSeparator;
    arraySeparator = arrSeparator;
    rowTemplate = new Row(cols.length);
    timestampTemplate = new Timestamp(0);
  }

  public static RowParser newInstance() throws RowParserException {
    return new RowParser(
        null, null, DEFAULT_COLUMN_SEPARATOR, DEFAULT_ARRAY_SEPARATOR);
  }

  public static RowParser newInstance(Column[] cols, LookupTableIndex index)
      throws RowParserException {
    return newInstance(
        cols, index, DEFAULT_COLUMN_SEPARATOR, DEFAULT_ARRAY_SEPARATOR);
  }

  public static RowParser newInstance(List<Column> cols, LookupTableIndex index)
      throws RowParserException {
    return newInstance(
        cols.toArray(new Column[cols.size()]),
        index,
        DEFAULT_COLUMN_SEPARATOR,
        DEFAULT_ARRAY_SEPARATOR
    );
  }

  public static RowParser newInstance(
      Header header,
      LookupTableIndex index) throws RowParserException {
    try {
      List<Column> c = header.getColumns();
      return newInstance(
          c.toArray(new Column[c.size()]),
          index,
          DEFAULT_COLUMN_SEPARATOR,
          DEFAULT_ARRAY_SEPARATOR
      );
    } catch (MetadataException e) {
      throw new RowParserException(e);
    }
  }

  public static RowParser newInstance(
      Column[] cols,
      LookupTableIndex index,
      char colSeparator,
      char arrSeparator) throws RowParserException {
    return new RowParser(cols, index, colSeparator, arrSeparator);
  }

  public Row parse(String line) throws RowParserException {
    if (line == null || line.isEmpty()) return null;
    return parse(line.toCharArray());
  }

  public Row parse(char[] line) throws RowParserException {
    if (line == null || line.length == 0) return null;

    Row row;
    try {
      row = (Row) rowTemplate.clone();
    } catch (CloneNotSupportedException e) {
      throw new RowParserException(e);
    }

    for (int i = 0, col = 0, offset = 0; i < line.length; i++) {
      if (line[i] == separator) {
        row.add(parseSubset(line, offset, i, col++));
        offset = i + 1;
      } else if (i == line.length - 1) {
        row.add(parseSubset(line, offset, i + 1, col));
      }
    }

    return row;
  }

  private Object parseSubset(char[] line, int start, int end, int colIndex) {
    String group = columns[colIndex].getLookupGroup();
    ColumnType type = columns[colIndex].getType();

    if (type == ColumnType.STRING) {
      if (start == end) {
        return null;
      }
      String buff = String.valueOf(Arrays.copyOfRange(line, start, end));
      return group != null
          ? lookupTableIndex.getGroupValue(group, buff, buff)
          : buff;
    } else if (type == ColumnType.INT) {
      return asInteger(line, start, end);
    } else if (type == ColumnType.DOUBLE) {
      return asDouble(line, start, end);
    } else if (type == ColumnType.FLOAT) {
      return asFloat(line, start, end);
    } else if (type == ColumnType.STRING_ARRAY) {
      return asStrings(line, start, end, group);
    } else if (type == ColumnType.TIMESTAMP) {
      return asTimestamp(line, start, end);
    } else if (type == ColumnType.LONG) {
      return asLong(line, start, end);
    }

    return null;
  }

  private Integer asInteger(char[] line, int start, int end) {
    if (start == end) {
      return null;
    }

    int result = 0;
    for (int i = start; i < end; i++) {
      int digit = (int) line[i] - (int) '0';
      if ((digit < 0) || (digit > 9)) {
        return null;
      }
      result = (result * 10) + digit;
    }
    return result;
  }

  private Timestamp asTimestamp(char[] line, int start, int end) {
    Long l;
    if (start == end || (l = asLong(line, start, end)) == null) {
      return null;
    }

    Timestamp ts = (Timestamp) timestampTemplate.clone();
    ts.setTime(l * 1000);
    return ts;
  }

  private Long asLong(char[] line, int start, int end) {
    if (start == end) {
      return null;
    }

    long result = 0;
    for (int i = start; i < end; i++) {
      long digit = (long) line[i] - (long) '0';
      if ((digit < 0) || (digit > 9)) {
        return null;
      }
      result = (result * 10) + digit;
    }
    return result;
  }

  private Float asFloat(char[] data, int start, int end) {
    Double d = asDouble(data, start, end);
    return d != null ? d.floatValue() : null;
  }

  private Double asDouble(char[] data, int start, int end) {
    if (start == end) {
      return null;
    }

    double result = 0;
    double div = 0;
    for (int i = start; i< end; i++) {
      double digit = (double) data[i] - (double) '0';
      if (data[i] == '.') {
        div = 1;
        continue;
      } else if ((digit < 0) || (digit > 9)) {
        return null;
      }
      result = (result * 10) + digit;
      if (div > 0) div *= 10;
    }

    return result / ((div<1) ? 1 : div);
  }

  private String[] asStrings(char[] data, int start, int end, String group) {

    if (start == end) {
      return null;
    }

    int[] sepPos = new int[end - start];
    int i, arrSize;
    for (i = start, arrSize = 0; i < end; i++) {
      if (data[i] == arraySeparator) {
        sepPos[arrSize++] = i;
      }
    }
    sepPos[arrSize++] = end;

    String[] result = new String[arrSize];
    int es = start;
    for (i = 0; i < arrSize; i++) {
      String s = String.valueOf(Arrays.copyOfRange(data, es, sepPos[i]));
      result[i] =
          group != null
          ? lookupTableIndex.getGroupValue(group, s, s)
          : s;
      es = sepPos[i] + 1;
    }

    return result;
  }

  public Column[] getColumns() {
    return columns;
  }

  public void setColumns(Column[] columns) {
    this.columns = columns;
  }

  public LookupTableIndex getLookupTableIndex() {
    return lookupTableIndex;
  }

  public void setLookupTableIndex(LookupTableIndex lookupTableIndex) {
    this.lookupTableIndex = lookupTableIndex;
  }

  public char getArraySeparator() {
    return arraySeparator;
  }

  public void setArraySeparator(char arraySeparator) {
    this.arraySeparator = arraySeparator;
  }

  public char getSeparator() {
    return separator;
  }

  public void setSeparator(char separator) {
    this.separator = separator;
  }

  @Override
  public String toString() {
    return "RowParser{" +
        "columns=" + Arrays.toString(columns) +
        ", lookupTableIndex=" + lookupTableIndex +
        ", arraySeparator=" + arraySeparator +
        ", separator=" + separator +
        '}';
  }
}
