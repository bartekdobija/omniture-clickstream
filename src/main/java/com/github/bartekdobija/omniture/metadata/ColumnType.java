package com.github.bartekdobija.omniture.metadata;

public enum ColumnType {

  INT("int"),
  STRING("string"),
  LONG("long"),
  BIGINT("bigint"),
  FLOAT("float"),
  DOUBLE("double"),
  STRING_ARRAY("array<string>"),
  INT_ARRAY("array<int>"),
  LONG_ARRAY("array<long>"),
  FLOAT_ARRAY("array<float>"),
  TIMESTAMP("timestamp");

  public String name;

  ColumnType(String n) {
    name = n;
  }
}
