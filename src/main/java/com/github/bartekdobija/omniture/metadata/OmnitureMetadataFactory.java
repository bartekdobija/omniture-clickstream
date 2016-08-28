package com.github.bartekdobija.omniture.metadata;

import com.github.bartekdobija.omniture.manifest.ManifestException;
import com.github.bartekdobija.omniture.manifest.OmnitureManifest;
import com.github.bartekdobija.omniture.manifest.utils.ManifestUtils;
import com.github.bartekdobija.omniture.metadata.utils.MetadataUtils;

import java.util.ArrayList;
import java.util.List;

public class OmnitureMetadataFactory {

  public OmnitureMetadataFactory() {

  }

  public static OmnitureMetadataFactory newInstance() {
    return new OmnitureMetadataFactory();
  }

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

  public List<OmnitureMetadata> create(String url, String separator)
      throws MetadataException {

    if (url == null || url.isEmpty()) {
      throw new MetadataException("empty manifest url parameter");
    }

    if (separator == null || separator.isEmpty()) {
      throw new MetadataException("empty manifest separator parameter");
    }

    List<OmnitureMetadata> meta = new ArrayList<>();

    try {
      for(OmnitureManifest m: ManifestUtils.fromUrl(url, separator)) {
        meta.add(MetadataUtils.fromOmnitureManifest(m));
      }
    } catch (ManifestException e) {
      throw new MetadataException(e);
    }

    return meta;
  }

}
