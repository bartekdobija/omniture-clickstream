package com.github.bartekdobija.omniture.loader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.io.InputStream;

public class HDFSDataLoader implements DataLoader {

  private String source;
  private FileSystem hdfs;
  private InputStream is;

  public HDFSDataLoader(String url) {
    source = url;
  }

  @Override
  public InputStream stream() throws DataLoaderException {

    try {
      hdfs = FileSystem.get(new Configuration());
      is = hdfs.open(new Path(source));
    } catch (IOException e) {
      throw new DataLoaderException(e);
    }

    return is;
  }

  @Override
  public String getSource() {
    return source;
  }

  @Override
  public void close() {
    if (is != null) try{ is.close(); } catch (IOException e) {}
    if (hdfs != null) try{ hdfs.close(); } catch (IOException e) {}
  }

}
