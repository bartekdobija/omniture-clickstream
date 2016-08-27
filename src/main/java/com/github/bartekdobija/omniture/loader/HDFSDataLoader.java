package com.github.bartekdobija.omniture.loader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.io.InputStream;

public class HDFSDataLoader implements DataLoader {

  private String path;
  private FileSystem hdfs;
  private InputStream is;

  public HDFSDataLoader(String url) {
    path = url;
  }

  @Override
  public InputStream stream() throws DataLoaderException {

    try {
      hdfs = FileSystem.get(new Configuration());
      is = hdfs.open(new Path(path));
    } catch (IOException e) {
      throw new DataLoaderException(e);
    }

    return is;
  }

  @Override
  public void close() {
    if (is != null) try{ is.close(); } catch (IOException e) {}
    if (hdfs != null) try{ hdfs.close(); } catch (IOException e) {}
  }

}
