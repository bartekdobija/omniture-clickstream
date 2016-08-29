package com.github.bartekdobija.omniture.row;

import com.github.bartekdobija.omniture.metadata.Column;
import com.github.bartekdobija.omniture.metadata.ColumnType;
import com.github.bartekdobija.omniture.metadata.LookupTableIndex;
import org.junit.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

public class DenormalizedDataRowParserTest {

  private static final String EMPTY_ROW = "";
  private static final String VALID_ROW =
      "\ttest\t666\t2\t12.50\t2.50\t12.540\t120.500\tga,ta,ca,,da" +
          "\t1423556690\t35884732\t2511725138\t6851172488903270000";
  private static final String ALL_MISSING_ROW = "\t\t\t\t\t\t\t\t\t";
  private static final String INVALID_TYPE_ROW =
      "\t6\tstring\tstring\tstring\tstring\tstring\tstring\t6\tstring\tx\ty";

  private static final String TEST_GROUP_NAME = "test";
  private static final String MOCKED_VALUE1 = "mockedvalue#1";
  private static final String MOCKED_VALUE2 = "mockedvalue#2";

  private static final Column[] VALID_ROW_SCHEMA = {
      new Column("a", ColumnType.STRING),
      new Column("b", ColumnType.STRING),
      new Column("c", ColumnType.INT),
      new Column("d", ColumnType.FLOAT),
      new Column("e", ColumnType.DOUBLE),
      new Column("f", ColumnType.FLOAT),
      new Column("g", ColumnType.FLOAT),
      new Column("h", ColumnType.FLOAT),
      new Column("i", ColumnType.STRING_ARRAY, TEST_GROUP_NAME),
      new Column("j", ColumnType.TIMESTAMP),
      new Column("k", ColumnType.LONG),
      new Column("l", ColumnType.LONG),
      new Column("l", ColumnType.LONG)

  };

  private static final List<Column> TEST_ROW_SCHEMA_LIST = Arrays.asList(
      new Column("a", ColumnType.STRING),
      new Column("b", ColumnType.STRING),
      new Column("c", ColumnType.INT),
      new Column("d", ColumnType.FLOAT),
      new Column("e", ColumnType.DOUBLE),
      new Column("f", ColumnType.FLOAT),
      new Column("g", ColumnType.FLOAT),
      new Column("h", ColumnType.FLOAT),
      new Column("i", ColumnType.STRING_ARRAY, TEST_GROUP_NAME),
      new Column("j", ColumnType.TIMESTAMP),
      new Column("k", ColumnType.LONG),
      new Column("l", ColumnType.LONG),
      new Column("l", ColumnType.LONG)

  );

  @Test
  public void validRow() throws RowParserException {

    LookupTableIndex lookupTable = new LookupTableIndex();

    lookupTable.setGroupValue(TEST_GROUP_NAME, "ga", MOCKED_VALUE1);
    lookupTable.setGroupValue(TEST_GROUP_NAME, "ta", MOCKED_VALUE2);

    Calendar expectedTimestamp = Calendar.getInstance();
    expectedTimestamp.setTimeInMillis(1423556690 * 1000L);

    RowParser parser =
        DenormalizedDataRowParser.newInstance(VALID_ROW_SCHEMA, lookupTable);

    Row row = parser.parse(VALID_ROW);

    assertEquals(null, row.get(0));
    assertEquals("test", row.get(1));
    assertEquals(666, row.get(2));
    assertEquals(2.0F, row.get(3));
    assertEquals(12.5, row.get(4));
    assertEquals(2.5F, row.get(5));
    assertEquals(12.54F, row.get(6));
    assertEquals(120.5F, row.get(7));
    assertEquals(MOCKED_VALUE1, ((String[]) row.get(8))[0]);
    assertEquals(MOCKED_VALUE2, ((String[]) row.get(8))[1]);
    assertEquals("ca", ((String[]) row.get(8))[2]);
    assertEquals("", ((String[]) row.get(8))[3]);
    assertEquals("da", ((String[]) row.get(8))[4]);
    assertEquals(expectedTimestamp.getTime(), row.get(9));
    assertEquals(35884732L, row.get(10));
    assertEquals(2511725138L, row.get(11));
    assertEquals(6851172488903270000L, row.get(12));

    row = parser.parse(INVALID_TYPE_ROW);
    RowParserStats stats = parser.getRowParserStats();

    assertEquals(2, stats.getParsedCount());
    assertEquals(0, stats.getEmptyRowCount());
    assertEquals(0, stats.getExceptionCount());

  }

  @Test
  public void emptyRow() throws RowParserException {

    RowParser parser =
        DenormalizedDataRowParser.newInstance(VALID_ROW_SCHEMA, new LookupTableIndex());
    Row row = parser.parse(EMPTY_ROW);

    assertEquals(null, row);

    row = parser.parse(EMPTY_ROW);
    RowParserStats stats = parser.getRowParserStats();

    assertEquals(0, stats.getParsedCount());
    assertEquals(2, stats.getEmptyRowCount());
    assertEquals(0, stats.getExceptionCount());

  }

  @Test
  public void missingAllData() throws RowParserException {

    LookupTableIndex lookupTable = new LookupTableIndex();

    lookupTable.setGroupValue(TEST_GROUP_NAME, "ga", MOCKED_VALUE1);
    lookupTable.setGroupValue(TEST_GROUP_NAME, "ta", MOCKED_VALUE2);

    RowParser parser =
        DenormalizedDataRowParser.newInstance(VALID_ROW_SCHEMA, lookupTable);

    Row row = parser.parse(ALL_MISSING_ROW);

    assertTrue("row is null", row != null);

    assertEquals(null, row.get(0));
    assertEquals(null, row.get(2));
    assertEquals(null, row.get(3));
    assertEquals(null, row.get(4));
    assertEquals(null, row.get(5));
    assertEquals(null, row.get(6));
    assertEquals(null, row.get(7));
    assertEquals(null, row.get(9));
    assertEquals(null, row.get(10));
    assertEquals(null, row.get(11));

    row = parser.parse(INVALID_TYPE_ROW);
    RowParserStats stats = parser.getRowParserStats();

    assertEquals(2, stats.getParsedCount());
    assertEquals(0, stats.getEmptyRowCount());
    assertEquals(0, stats.getExceptionCount());

  }

  @Test
  public void invalidTypes() throws RowParserException {

    LookupTableIndex lookupTable = new LookupTableIndex();

    lookupTable.setGroupValue(TEST_GROUP_NAME, "ga", MOCKED_VALUE1);
    lookupTable.setGroupValue(TEST_GROUP_NAME, "ta", MOCKED_VALUE2);

    RowParser parser =
        DenormalizedDataRowParser.newInstance(VALID_ROW_SCHEMA, lookupTable);

    Row row = parser.parse(INVALID_TYPE_ROW);

    assertEquals(null, row.get(0));
    assertEquals("6",  row.get(1));
    assertEquals(null, row.get(2));
    assertEquals(null, row.get(3));
    assertEquals(null, row.get(4));
    assertEquals(null, row.get(5));
    assertEquals(null, row.get(6));
    assertEquals(null, row.get(7));
    assertEquals(null, row.get(9));
    assertEquals(null, row.get(10));
    assertEquals(null, row.get(11));

    row = parser.parse(INVALID_TYPE_ROW);
    RowParserStats stats = parser.getRowParserStats();

    assertEquals(2, stats.getParsedCount());
    assertEquals(0, stats.getEmptyRowCount());
    assertEquals(0, stats.getExceptionCount());

  }

}
