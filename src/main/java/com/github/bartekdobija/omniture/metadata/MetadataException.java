package com.github.bartekdobija.omniture.metadata;

public class MetadataException extends Exception {
  public MetadataException () {}

  public MetadataException(Throwable e) {
    super(e);
  }

  public MetadataException(String msg) {
    super(msg);
  }

}
