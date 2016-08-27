package com.github.bartekdobija.omniture.manifest;

import sun.tools.jar.Manifest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public class OmnitureManifest extends Manifest {

  private URI dataDirectory;

  public OmnitureManifest(URI path, InputStream is) throws IOException {
    super(is);
    dataDirectory = path;
  }

  public URI getDataDirectory() {
    return dataDirectory;
  }

}
