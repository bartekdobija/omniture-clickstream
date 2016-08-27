package com.github.bartekdobija.omniture.manifest;

import com.github.bartekdobija.omniture.manifest.utils.ManifestUtils;
import org.junit.Test;

import static org.junit.Assert.*;


import java.net.URI;

public class ManifestUtilsTest {

  @Test
  public void manifestParent() throws ManifestException {

    URI s3 = ManifestUtils.manifestParent("s3://bucket/manifest.txt");
    URI hdfs =
        ManifestUtils.manifestParent("hdfs://master:8020/bucket/manifest.txt");
    URI local = ManifestUtils.manifestParent("file:///bucket/manifest.txt");

    assertEquals("s3", s3.getScheme());
    assertEquals("/bucket", s3.getPath());

    assertEquals("hdfs", hdfs.getScheme());
    assertEquals("/master:8020/bucket", hdfs.getPath());

    assertEquals("file", local.getScheme());
    assertEquals("/bucket", local.getPath());

  }

}
