package com.github.bartekdobija.omniture.metadata;

import com.github.bartekdobija.omniture.manifest.OmnitureManifest;
import com.github.bartekdobija.omniture.manifest.utils.ManifestUtils;
import com.github.bartekdobija.omniture.metadata.utils.MetadataUtils;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

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

    List<DataFile> files = meta.getDataFiles();

    assertTrue(files.get(0).getName().contains("resources/data/01-suite"));
    assertTrue(files.get(1).getName().contains("resources/data/02-suite"));

  }

  @Test
  public void asHiveSchema() throws MetadataException {

    OmnitureMetadata meta =
        OmnitureMetadataFactory.newInstance().create(manifestFile);

    String schema = MetadataUtils.hiveTableDefinition(meta);

    assertNotNull(schema);
    assertTrue(schema.contains("accept_language string"));
    assertTrue(schema.contains("zip string"));
    assertTrue(schema.contains("product_list array<string>"));
    assertTrue(schema.contains("mobile_id bigint"));
  }

}
