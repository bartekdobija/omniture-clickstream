package com.github.bartekdobija.omniture.metadata;


import com.github.bartekdobija.omniture.manifest.ManifestException;
import com.github.bartekdobija.omniture.manifest.OmnitureManifest;
import com.github.bartekdobija.omniture.manifest.utils.ManifestUtils;
import com.github.bartekdobija.omniture.metadata.utils.MetadataUtils;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class LookupTableTest {

  String manifestFile =
      getClass().getResource("/data/suiteid_2015-12-29.txt").toString();

  @Test
  public void index() throws ManifestException, MetadataException {

    OmnitureManifest em = ManifestUtils.fromUrl(manifestFile);
    OmnitureMetadata meta = MetadataUtils.fromOmnitureManifest(em);

    Header header = meta.getHeader();
    LookupTable table = meta.getLookupTable();
    LookupTableIndex index = table.getIndex();


    assertTrue(header.getColumn(0).getName().equals("accept_language"));
    assertTrue(header.getColumn(0).getType() == ColumnType.STRING);

    assertTrue(header.getColumn(1).getName().equals("browser"));
    assertTrue(header.getColumn(1).getType() == ColumnType.STRING);

    assertTrue(header.getColumn(193).getName().equals("os"));
    assertTrue(header.getColumn(193).getType() == ColumnType.STRING);

    assertTrue(header.getColumn(132).getName().equals("event_list"));
    assertTrue(header.getColumn(132).getType() == ColumnType.STRING_ARRAY);

    assertTrue(header.getColumn(317).getName().equals("post_event_list"));
    assertTrue(header.getColumn(317).getType() == ColumnType.STRING_ARRAY);

    assertEquals("Lynx 2.7.1", table.getGroupValue("browser","1"));
    assertEquals("Afrikaans", table.getGroupValue("languages","2"));

    assertEquals("Lynx 2.7.1", index.getGroupValue("browser","1"));
    assertEquals("Afrikaans", index.getGroupValue("languages","2"));

    assertNull(index.getGroupValue("operating_systems", "1"));
    assertNull(table.getGroupValue("operating_systems", "1"));

    assertEquals("Linux", table.getGroupValue("os","1"));
    assertEquals("Macintosh", table.getGroupValue("os","2"));

    assertEquals("Linux", index.getGroupValue("os", "1"));
    assertEquals("Macintosh", index.getGroupValue("os", "2"));

    assertEquals("Purchase", table.getGroupValue("event_list","1"));
    assertEquals("Product View", table.getGroupValue("event_list","2"));

    assertEquals("Purchase", index.getGroupValue("event_list", "1"));
    assertEquals("Product View", index.getGroupValue("event_list", "2"));

    assertEquals("Purchase", table.getGroupValue("post_event_list","1"));
    assertEquals("Product View", table.getGroupValue("post_event_list","2"));

    assertEquals("Purchase", index.getGroupValue("post_event_list", "1"));
    assertEquals("Product View", index.getGroupValue("post_event_list", "2"));

  }

}
