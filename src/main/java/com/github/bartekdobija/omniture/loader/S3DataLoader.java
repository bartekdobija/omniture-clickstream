package com.github.bartekdobija.omniture.loader;

import com.github.bartekdobija.omniture.loader.utils.DataLoaderUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.s3.S3FileSystem;
import org.apache.hadoop.fs.s3a.S3AFileSystem;
import org.apache.hadoop.fs.s3native.NativeS3FileSystem;
import org.apache.hadoop.fs.s3a.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import static org.apache.hadoop.fs.CommonConfigurationKeysPublic.*;


public class S3DataLoader implements DataLoader {

  private String source;
  private String access;
  private String secret;
  private FileSystem s3;
  private InputStream is;

  private String proxyHost;
  private String proxyPort;
  private String proxyUser;
  private String proxyPass;

  public static String S3_ACCESS_KEY = "omniture.s3.access.key";
  public static String S3_SECRET_KEY = "omniture.s3.secret.key";

  public S3DataLoader(String url) {
    source = url;
    access = System.getProperty(S3_ACCESS_KEY, null);
    secret = System.getProperty(S3_SECRET_KEY, null);

    if (System.getProperty("https.proxyHost") != null) {
      proxyHost = System.getProperty("https.proxyHost");
      proxyPort = System.getProperty("https.proxyPort");
      proxyUser = System.getProperty("https.proxyUser");
      proxyPass = System.getProperty("https.proxyPassword");
    } else if (System.getProperty("http.proxyHost") != null) {
      proxyHost = System.getProperty("http.proxyHost");
      proxyPort = System.getProperty("http.proxyPort");
      proxyUser = System.getProperty("http.proxyUser");
      proxyPass = System.getProperty("http.proxyPassword");
    }
  }

  @Override
  public InputStream stream() throws DataLoaderException {
    try {
      s3 = getFileSystem(source);
      is = s3.open(new Path(source));
      return is;
    } catch (IOException | URISyntaxException e) {
      throw new DataLoaderException(e);
    }
  }

  @Override
  public String getSource() {
    return source;
  }

  @Override
  public void close() {
    if (is != null) try { is.close(); } catch (IOException e) {}
    if (s3 != null) try { s3.close(); } catch (IOException e) {}
  }

  private FileSystem getFileSystem(String path)
      throws IOException, URISyntaxException {

    Configuration conf = new Configuration();
    conf.set(FS_DEFAULT_NAME_KEY, DataLoaderUtils.getS3FS(path));

    String scheme = new URI(path).getScheme().toLowerCase();

    if (scheme.equals(DataSchemes.S3N.value)) {
      conf.set("fs.s3n.awsAccessKeyId", access);
      conf.set("fs.s3n.awsSecretAccessKey", secret);
      return NativeS3FileSystem.get(conf);
    } else if (scheme.equals(DataSchemes.S3.value)) {
      conf.set("fs.s3.awsAccessKeyId", access);
      conf.set("fs.s3.awsSecretAccessKey", secret);
      return S3FileSystem.get(conf);
    } else if (scheme.equals(DataSchemes.LOCAL.value)) {
      return FileSystem.get(conf);
    }

    conf.set(Constants.ACCESS_KEY, access);
    conf.set(Constants.SECRET_KEY, secret);

    if (proxyHost != null) {
      conf.set(Constants.PROXY_HOST, proxyHost);
      conf.set(Constants.PROXY_PORT, proxyPort);
      if (proxyUser != null) {
        conf.set(Constants.PROXY_USERNAME, proxyUser);
      }
      if (proxyPass != null) {
        conf.set(Constants.PROXY_PASSWORD, proxyPass);
      }

    }

    return S3AFileSystem.get(conf);
  }

  private void setIfMissing(Configuration conf, String key, String value) {
    String v = conf.get(key);
    if (v == null || v.isEmpty()) {
      conf.set(key, value);
    }
  }
}
