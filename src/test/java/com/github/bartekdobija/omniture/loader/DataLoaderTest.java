package com.github.bartekdobija.omniture.loader;


import com.github.bartekdobija.omniture.loader.utils.DataLoaderUtils;
import org.junit.Ignore;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.*;

public class DataLoaderTest {

  private static final String LOCAL_FS_FILE =
      "file://"
      + new File(".").getAbsolutePath()
      + "/src/test/resources/data/suiteid_2015-12-29.txt";

  @Test
  public void localFSDataLoader() throws DataLoaderException, IOException {
    try (DataLoader dl = new LocalFSDataLoader(LOCAL_FS_FILE)) {
      assertEquals(dl.stream().read(), 68);
    }
  }

  @Test
  public void HDFSDataLoader() throws DataLoaderException, IOException {
    try (DataLoader ds = new HDFSDataLoader(LOCAL_FS_FILE)) {
      assertEquals(ds.stream().read(), 68);
    }
  }

  @Test
  public void localS3DataLoader() throws DataLoaderException, IOException {
    try (DataLoader dl = new S3DataLoader(LOCAL_FS_FILE)) {
      assertEquals(dl.stream().read(), 68);
    }
  }

  @Test
  @Ignore
  public void s3nDataLoader() throws DataLoaderException, IOException {
    try(DataLoader dl = new S3DataLoader("s3n://a/b.txt")) {
      InputStream is = dl.stream();
      assertNotNull(is);
      assertEquals(is.read(), 68);
    }
  }

}
