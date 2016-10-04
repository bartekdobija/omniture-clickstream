package com.github.bartekdobija.omniture.metadata;

import com.github.bartekdobija.omniture.manifest.ManifestException;
import com.github.bartekdobija.omniture.manifest.OmnitureManifest;
import com.github.bartekdobija.omniture.manifest.utils.ManifestUtils;
import com.github.bartekdobija.omniture.metadata.utils.MetadataUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Initialises the {@link OmnitureMetadata} instance based on manifest files' location.
 */
public class OmnitureMetadataFactory {

  public OmnitureMetadataFactory() {}

  /**
   * Creates an instance of {@link OmnitureMetadataFactory}
   *
   * @return new instance of {@link OmnitureMetadataFactory}
   */
  public static OmnitureMetadataFactory newInstance() {
    return new OmnitureMetadataFactory();
  }

  /**
   * Creates an initialised instance of {@link OmnitureMetadata}.
   *
   * The method supports protocols listed in {@link com.github.bartekdobija.omniture.loader.DataSchemes}
   *
   * @param url path to the Omniture manifest file.
   * @return initialized omniture metadata instance
   * @throws MetadataException exception returned on invalid url
   * or manifest file unavailability
   */
  public OmnitureMetadata create(String url)
      throws MetadataException {
    if (url == null || url.isEmpty()) {
      throw new MetadataException("empty manifest url parameter");
    }

    try {
      OmnitureManifest manifest = ManifestUtils.fromUrl(url);
      return MetadataUtils.fromOmnitureManifest(manifest);
    } catch (ManifestException e) {
      throw new MetadataException(e);
    }
  }

  /**
   * Creates a list of initialised {@link OmnitureMetadata} instances.
   *
   * Protocols listed in {@link com.github.bartekdobija.omniture.loader.DataSchemes} are supported.
   *
   * @param urls a list of Omniture manifest files separated with a separator character.
   * @param separator urls separator character.
   * @return initialized omniture metadata instance
   * @throws MetadataException exception returned on invalid url
   * or manifest file unavailability
   */
  public List<OmnitureMetadata> create(String urls, String separator)
      throws MetadataException {

    if (urls == null || urls.isEmpty()) {
      throw new MetadataException("empty manifest url parameter");
    }

    if (separator == null || separator.isEmpty()) {
      throw new MetadataException("empty manifest separator parameter");
    }

    List<OmnitureMetadata> meta = new ArrayList<>();

    try {
      for(OmnitureManifest m: ManifestUtils.fromUrl(urls, separator)) {
        meta.add(MetadataUtils.fromOmnitureManifest(m));
      }
    } catch (ManifestException e) {
      throw new MetadataException(e);
    }

    return meta;
  }

}
