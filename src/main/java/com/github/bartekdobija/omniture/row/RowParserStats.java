package com.github.bartekdobija.omniture.row;

import java.io.Serializable;

/**
 * Row parser statistics.
 */
public class RowParserStats implements Serializable {

  private long parsedCount;
  private long emptyRowCount;
  private long emptyColumnCount;
  private long exceptionCount;

  public RowParserStats() {

  }

  public RowParserStats(
      long parsedCount,
      long emptyRowCount,
      long emptyColumnCount,
      long exceptionCount) {

    this.parsedCount = parsedCount;
    this.emptyRowCount = emptyRowCount;
    this.emptyColumnCount = emptyColumnCount;
    this.exceptionCount = exceptionCount;
  }

  public long getParsedCount() {
    return parsedCount;
  }

  public void setParsedCount(long parsedCount) {
    this.parsedCount = parsedCount;
  }

  public long getEmptyRowCount() {
    return emptyRowCount;
  }

  public void setEmptyRowCount(long emptyRowCount) {
    this.emptyRowCount = emptyRowCount;
  }

  public long getExceptionCount() {
    return exceptionCount;
  }

  public void setExceptionCount(long exceptionCount) {
    this.exceptionCount = exceptionCount;
  }

  public long getEmptyColumnCount() {
    return emptyColumnCount;
  }

  public void setEmptyColumnCount(long emptyColumnCount) {
    this.emptyColumnCount = emptyColumnCount;
  }
}
