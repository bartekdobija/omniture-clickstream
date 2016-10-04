package com.github.bartekdobija.omniture.loader;

/**
 * Exception returned by {@link DataLoader} implementations.
 *
 * @since 0.1
 */
public class DataLoaderException extends Exception {

  public DataLoaderException() { }
  public DataLoaderException(final Throwable ex) {
    super(ex);
  }
  public DataLoaderException(final String msg) {
    super(msg);
  }
}
