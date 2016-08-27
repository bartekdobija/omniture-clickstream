package com.github.bartekdobija.omniture.loader;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

public class LocalFSDataLoader implements DataLoader {

  private URI path;
  private FileInputStream fis;

  public LocalFSDataLoader(String url) throws DataLoaderException {
    try { path = new URI(url); }
    catch (URISyntaxException e) { throw new DataLoaderException(e); }
  }

  @Override
  public InputStream stream() throws DataLoaderException {
    try {
      fis = new FileInputStream(new File(path.getPath()));
      return fis;
    } catch (IOException e) {
      throw new DataLoaderException(e);
    }
  }

  @Override
  public void close() {
    if (fis != null) try { fis.close(); } catch (IOException e) {}
  }

}
