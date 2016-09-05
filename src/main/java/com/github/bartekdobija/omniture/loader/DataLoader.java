package com.github.bartekdobija.omniture.loader;

import java.io.Closeable;
import java.io.InputStream;

public interface DataLoader extends Closeable {
  InputStream stream() throws DataLoaderException;
  String getSource();
  @Override void close();
}
