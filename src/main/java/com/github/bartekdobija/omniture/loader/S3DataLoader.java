package com.github.bartekdobija.omniture.loader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.s3.S3FileSystem;
import org.apache.hadoop.fs.s3a.S3AFileSystem;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;


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
      Configuration conf = new Configuration();
      conf.set("fs.defaultFS", getBucket(path));
      s3 = S3AFileSystem.get(conf);
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

  public static String getBucket(String uri) {
    if (uri == null || uri.isEmpty()) {
      return null;
    }
    try {
      URI u = new URI(uri);
      return u.getScheme() + "://" + u.getHost();
    } catch (URISyntaxException e) {
      e.printStackTrace();
      return null;
    }
  }

}
