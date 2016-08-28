package com.github.bartekdobija.omniture.row;

import java.io.Serializable;

public class RowParserStats implements Serializable {

  private long parsedCount;
  private long emptyRowCount;
  private long exceptionCount;

  public RowParserStats() {

  }

  public RowParserStats(
      long parsedCount, long emptyRowCount, long exceptionCount) {
    this.parsedCount = parsedCount;
    this.emptyRowCount = emptyRowCount;
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
}
