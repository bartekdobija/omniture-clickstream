package com.github.bartekdobija.omniture.loader.utils;

import com.github.bartekdobija.omniture.loader.*;
import com.github.bartekdobija.omniture.manifest.ManifestException;

import java.net.URI;
import java.net.URISyntaxException;


public class DataLoaderUtils {

  public static DataLoader getLoader(URI uri) throws ManifestException {
    return getLoader(uri.toString());
  }

  public static DataLoader getLoader(String uri) throws ManifestException {

    try {
      String protocol = new URI(uri).getScheme();
      if (protocol.equals(DataSchemes.LOCAL.value)) {
        return new LocalFSDataLoader(uri);
      } else if (protocol.equals(DataSchemes.S3.value)
          || protocol.equals(DataSchemes.S3N.value)
          || protocol.equals(DataSchemes.S3A.value)) {
        return new S3DataLoader(uri);
      } else if (protocol.equals(DataSchemes.HDFS.value)) {
        return new HDFSDataLoader(uri);
      }
    } catch (DataLoaderException | URISyntaxException e) {
      throw new ManifestException(e);
    }
    throw new ManifestException("unsupported scheme");
  }

}
