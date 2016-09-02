package com.github.bartekdobija.omniture.loader.utils;


import com.github.bartekdobija.omniture.loader.DataLoader;
import com.github.bartekdobija.omniture.loader.S3DataLoader;
import com.github.bartekdobija.omniture.manifest.ManifestException;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.*;

public class DataLoaderUtilsTest {

  private URI uriA, uriB, uriC;

  {
    try {
      uriA = new URI("s3://a/b/c");
      uriB = new URI("s3a://a/b/c");
      uriC = new URI("s3n://a/b/c");
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
  }

}
