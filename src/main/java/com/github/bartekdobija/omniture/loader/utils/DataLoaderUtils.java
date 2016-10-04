package com.github.bartekdobija.omniture.loader.utils;

import com.github.bartekdobija.omniture.loader.*;
import com.github.bartekdobija.omniture.manifest.ManifestException;

import java.net.URI;
import java.net.URISyntaxException;


public class DataLoaderUtils {

  private final static String EMTPY_STRING = "";
  private final static String AMP_STRING = "@";
  private final static String UH_STRING = "://";

  public static DataLoader getLoader(URI uri) throws ManifestException {
    return getLoader(uri.toString());
  }

  public static DataLoader getLoader(String uri) throws ManifestException {

    try {
      String protocol = new URI(uri).getScheme();
      if (protocol == null || DataSchemes.LOCAL.value.equals(protocol)) {
        return new LocalFSDataLoader(uri);
      } else if (DataSchemes.S3A.value.equals(protocol)
          || DataSchemes.S3N.value.equals(protocol)
          || DataSchemes.S3.value.equals(protocol) ) {
        return new S3DataLoader(uri);
      } else if (DataSchemes.HDFS.value.equals(protocol)) {
        return new HDFSDataLoader(uri);
      }
    } catch (DataLoaderException | URISyntaxException e) {
      throw new ManifestException(e);
    }
    throw new ManifestException("unsupported scheme");
  }

  public static String getS3FS(String uri) {
    if (uri == null || uri.isEmpty()) {
      return null;
    }
    try {
      URI u = new URI(uri);
      String auth = EMTPY_STRING;
      String userInfo = u.getUserInfo();

      if (userInfo != null) {
        auth = userInfo + AMP_STRING;
      }

      return u.getScheme() + UH_STRING + auth + u.getHost();
    } catch (URISyntaxException e) {
      e.printStackTrace();
      return null;
    }
  }

}
