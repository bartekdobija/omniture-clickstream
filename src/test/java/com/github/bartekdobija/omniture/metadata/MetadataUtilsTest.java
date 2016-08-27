package com.github.bartekdobija.omniture.metadata;

import com.github.bartekdobija.omniture.manifest.OmnitureManifest;
import com.github.bartekdobija.omniture.manifest.utils.ManifestUtils;
import com.github.bartekdobija.omniture.metadata.utils.MetadataUtils;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;

public class MetadataUtilsTest {

  String manifestFile =
      "file://" +
          new File(".").getAbsolutePath() +
          "/src/test/resources/data/suiteid_2015-12-29.txt";

  @Test
  public void fromExtendedManifest() throws Exception {

    OmnitureManifest em = ManifestUtils.fromUrl(manifestFile);
    OmnitureMetadata meta = MetadataUtils.fromOmnitureManifest(em);
    Header header = meta.getHeader();

    LookupTable table = meta.getLookupTable();
    LookupTableIndex index = table.getIndex();

    assertEquals("language", header.getColumn(159).getName());
    assertTrue(header.getColumn(159).getType() == ColumnType.STRING);

    assertEquals("browser", header.getColumn(1).getName());
    assertTrue(header.getColumn(1).getType() == ColumnType.STRING);

    assertEquals("Lynx 2.7.1", table.getGroupValue("browser","1"));
    assertEquals("Afrikaans", table.getGroupValue("language","2"));

    assertEquals("Lynx 2.7.1", index.getGroupValue("browser","1"));
    assertEquals("Afrikaans", index.getGroupValue("language","2"));

  }

}
