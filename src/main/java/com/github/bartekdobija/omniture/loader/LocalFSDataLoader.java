package com.github.bartekdobija.omniture.loader;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Local FS backed {@link DataLoader} implementation.
 * @since 0.1
 */
public class LocalFSDataLoader implements DataLoader {

  private String source;
  private FileInputStream fis;

  public LocalFSDataLoader(String url) throws DataLoaderException {
    source = url;
  }

  @Override
  public InputStream stream() throws DataLoaderException {
    try {
      fis = new FileInputStream(new File(new URI(source).getPath()));
      return fis;
    } catch (IOException | URISyntaxException e) {
      throw new DataLoaderException(e);
    }
  }

  @Override
  public String getSource() {
    return source;
  }

  @Override
  public void close() {
    if (fis != null) try { fis.close(); } catch (IOException e) {}
  }

}
