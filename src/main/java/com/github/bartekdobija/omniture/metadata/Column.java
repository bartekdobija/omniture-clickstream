package com.github.bartekdobija.omniture.metadata;

import java.io.Serializable;

public class Column implements Serializable {

  private String name;
  private ColumnType type;
  private String lookupGroup;

  public Column() {
    this(null, null, null);
  }

  public Column(String name, ColumnType type) {
    this(name, type, null);
  }

  public Column(String name, ColumnType type, String lookup) {
    this.name = name;
    this.type = type;
    this.lookupGroup = lookup;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ColumnType getType() {
    return type;
  }

  public void setType(ColumnType type) {
    this.type = type;
  }

  public String getLookupGroup() {
    return lookupGroup;
  }

  public void setLookupGroup(String lookupGroup) {
    this.lookupGroup = lookupGroup;
  }

  @Override
  public String toString() {
    return "Column{" +
        "name='" + name + '\'' +
        ", type=" + type +
        ", lookupGroup='" + lookupGroup + '\'' +
        '}';
  }
}
