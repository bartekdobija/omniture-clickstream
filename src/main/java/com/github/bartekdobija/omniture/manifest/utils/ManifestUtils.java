package com.github.bartekdobija.omniture.manifest.utils;

import com.github.bartekdobija.omniture.loader.utils.DataLoaderUtils;
import com.github.bartekdobija.omniture.manifest.OmnitureManifest;
import com.github.bartekdobija.omniture.manifest.ManifestException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * {@link OmnitureManifest} related convenience methods.
 *
 * @since 0.1
 */
public class ManifestUtils {

  private static final String EMPTY_STRING = "";
  private static final String SEPARATOR = "/";

  public static List<OmnitureManifest> fromUrl(String url, String separator)
      throws ManifestException {
    if (url == null) return Collections.emptyList();
    List<OmnitureManifest> result = new ArrayList<>();
    for(String u : url.split(separator)) result.add(fromUrl(u));
    return result;
  }

  public static OmnitureManifest fromUrl(String url) throws ManifestException {
    try {
      return new OmnitureManifest(
          manifestParent(url), DataLoaderUtils.getLoader(url).stream());
    } catch (Exception ex) {
      throw new ManifestException(ex);
    }
  }

  public static URI manifestParent(String url) throws ManifestException {
    if ((url == null) || url.equals(EMPTY_STRING) || url.equals(SEPARATOR)) {
      throw new ManifestException("exception");
    }

    int lastSlashPos = url.lastIndexOf(SEPARATOR);

    try{
      if (lastSlashPos >= 0) {
        return new URI(url.substring(0, lastSlashPos));
      }
    } catch (URISyntaxException e) {
      throw new ManifestException("exception");
    }
    throw new ManifestException("exception");
  }

}
