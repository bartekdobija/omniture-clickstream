package com.github.bartekdobija.omniture.loader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.io.InputStream;

public class S3DataLoader implements DataLoader {

  private String path;
  private FileSystem s3;
  private InputStream is;

  public S3DataLoader(String url) {
    path = url;
  }

  @Override
  public InputStream stream() throws DataLoaderException {
    try {
      s3 = FileSystem.get(new Configuration());
      is = s3.open(new Path(path));
      return is;
    } catch (IOException e) {
      throw new DataLoaderException(e);
    }
  }

  @Override
  public void close() {
    if (is != null) try { is.close(); } catch (IOException e) {}
    if (s3 != null) try { s3.close(); } catch (IOException e) {}
  }

}
