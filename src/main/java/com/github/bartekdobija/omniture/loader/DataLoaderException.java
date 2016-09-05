package com.github.bartekdobija.omniture.loader;

public class DataLoaderException extends Exception {

  public DataLoaderException() { }
  public DataLoaderException(final Throwable ex) {
    super(ex);
  }
  public DataLoaderException(final String msg) {
    super(msg);
  }
}
