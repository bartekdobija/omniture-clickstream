package com.github.bartekdobija.omniture.metadata.columntyperesolver;

import com.github.bartekdobija.omniture.metadata.Column;
import com.github.bartekdobija.omniture.metadata.ColumnType;

import java.util.regex.Pattern;

/**
 * Name based {@link Column} type resolution.
 */
public class SimpleColumnTypeResolver implements ColumnTypeResolver {

  protected Pattern _string = Pattern.compile("evar|prop");
  protected Pattern _lookedUpString =
      Pattern.compile("browser|connection_type|(^color$)|country|language|javascript");
  protected Pattern _lookedUpNonStandardString =
      Pattern.compile("(^os$)|search_engine");

  protected Pattern _array = Pattern.compile("_list");
  protected Pattern _lookedUpArray =
      Pattern.compile("event_list|plugins");
  protected Pattern _int =
      Pattern.compile("_visit|_height|_width|_visid_type|_num|(_hit$)" +
          "|page_event$|duplicate_|c_col|hit_s");
  protected Pattern _long =
      Pattern.compile("_id|_hash|userid|_visitor|_high|_low|^va_|(^visid_type$)");
  protected Pattern _double = Pattern.compile("_rate");
  protected String _timestamp = "_gmt";

  /**
   * Interpret column name into a fully configured {@link Column} object.
   *
   * @param name column name
   * @return column object with type and lookup table configuration
   */
  @Override
  public Column parseName(String name) {

    if (name == null || name.isEmpty()) {
      return null;
    }

    if (_lookedUpString.matcher(name).find()) {
      return new Column(name, ColumnType.STRING, name);
    } else if (_lookedUpNonStandardString.matcher(name).find()) {
      return new Column(name, ColumnType.STRING, name);
    } else if (name.contains(_timestamp)) {
      return new Column(name, ColumnType.TIMESTAMP);
    } else if (_string.matcher(name).find()) {
      return new Column(name, ColumnType.STRING);
    } else if (_lookedUpArray.matcher(name).find()) {
      return new Column(name, ColumnType.STRING_ARRAY, name);
    } else if (_array.matcher(name).find()) {
      return new Column(name, ColumnType.STRING_ARRAY);
    } else if (_int.matcher(name).find()) {
      return new Column(name, ColumnType.INT);
    } else if (_long.matcher(name).find()) {
      return new Column(name, ColumnType.LONG);
    } else if (_double.matcher(name).find()) {
      return new Column(name, ColumnType.DOUBLE);
    }

    return new Column(name, ColumnType.STRING, null);
  }
}
