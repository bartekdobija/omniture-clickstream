package com.github.bartekdobija.omniture.loader;

public enum DataSchemes {

  S3("s3"),
  S3N("s3n"),
  S3A("s3a"),
  LOCAL("file"),
  HDFS("hdfs");

  public final String value;

  DataSchemes(String scheme) {
    value = scheme;
  }

}
