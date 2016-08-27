package com.github.bartekdobija.omniture.metadata;

import com.github.bartekdobija.omniture.manifest.OmnitureManifest;
import com.github.bartekdobija.omniture.manifest.utils.ManifestUtils;
import com.github.bartekdobija.omniture.metadata.utils.MetadataUtils;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;

public class MetadataUtilsTest {

  @Test
  public void fromExtendedManifest() throws Exception {

    String pwd = "file://" + new File(".").getAbsolutePath();
    String manifestFile = pwd + "/src/test/resources/data/suiteid_2015-12-29.txt";
    OmnitureManifest em = ManifestUtils.fromUrl(manifestFile);

    OmnitureMetadata meta = MetadataUtils.fromOmnitureManifest(em);

    assertTrue(meta.getHeader().getColumn(0).getName().equals("accept_language"));
    assertTrue(meta.getHeader().getColumn(0).getType() == ColumnType.STRING);
    assertTrue(meta.getHeader().getColumn(1).getName().equals("browser"));
    assertTrue(meta.getHeader().getColumn(1).getType() == ColumnType.STRING);

    LookupTable table = meta.getLookupTable();
    LookupTableIndex index = table.getIndex();

    assertEquals("Lynx 2.7.1", table.getGroupValue("browser","1"));
    assertEquals("Afrikaans", table.getGroupValue("languages","2"));

    assertEquals("Lynx 2.7.1", index.getGroupValue("browser","1"));
    assertEquals("Afrikaans", index.getGroupValue("languages","2"));

  }

}
