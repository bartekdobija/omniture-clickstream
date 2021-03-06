package com.github.bartekdobija.omniture.loader.utils;


import com.github.bartekdobija.omniture.loader.DataLoader;
import com.github.bartekdobija.omniture.loader.LocalFSDataLoader;
import com.github.bartekdobija.omniture.loader.S3DataLoader;
import com.github.bartekdobija.omniture.manifest.ManifestException;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.*;

public class DataLoaderUtilsTest {

  private URI uriA, uriB, uriC, uriD, uriE;

  {
    try {
      uriA = new URI("s3://a/b/c");
      uriB = new URI("s3a://a/b/c");
      uriC = new URI("s3n://a/b/c");
      uriD = new URI("file:/a/b/c");
      uriE = new URI("/a/b/c");
    } catch (URISyntaxException e) {}
  }

  @Test
  public void loader() throws ManifestException {
    DataLoader dl = DataLoaderUtils.getLoader(uriA);
    assertTrue(dl instanceof S3DataLoader);
    dl = DataLoaderUtils.getLoader(uriB);
    assertTrue(dl instanceof S3DataLoader);
    dl = DataLoaderUtils.getLoader(uriC);
    assertTrue(dl instanceof S3DataLoader);
    dl = DataLoaderUtils.getLoader(uriD);
    assertTrue(dl instanceof LocalFSDataLoader);
    dl = DataLoaderUtils.getLoader(uriE);
    assertTrue(dl instanceof LocalFSDataLoader);
  }

  @Test
  public void getS3FS() {
    assertEquals("s3://bucket",
        DataLoaderUtils.getS3FS("s3://bucket/dir/manifest.txt"));

    assertEquals("s3a://bucket",
        DataLoaderUtils.getS3FS("s3a://bucket/dir/manifest.txt"));

    assertEquals("s3n://bucket",
        DataLoaderUtils.getS3FS("s3n://bucket/dir/manifest.txt"));

    assertEquals("s3n://a:b@bucket",
        DataLoaderUtils.getS3FS("s3n://a:b@bucket/dir/manifest.txt"));

    assertEquals(null, DataLoaderUtils.getS3FS(""));

    assertEquals(null, DataLoaderUtils.getS3FS(null));
  }

}
