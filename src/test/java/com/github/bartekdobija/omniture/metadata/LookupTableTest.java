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


    assertEquals("accept_language", header.getColumn(0).getName());
    assertEquals(ColumnType.STRING, header.getColumn(0).getType());

    assertEquals("browser", header.getColumn(1).getName());
    assertEquals(ColumnType.STRING, header.getColumn(1).getType());

    assertEquals("language", header.getColumn(159).getName());
    assertEquals(ColumnType.STRING, header.getColumn(159).getType());

    assertEquals("mobile_id", header.getColumn(165).getName());
    assertEquals(ColumnType.LONG, header.getColumn(165).getType());

    assertEquals("os", header.getColumn(193).getName());
    assertEquals(ColumnType.STRING, header.getColumn(193).getType());

    assertEquals("page_event", header.getColumn(195).getName());
    assertEquals(ColumnType.INT, header.getColumn(195).getType());

    assertEquals("event_list", header.getColumn(132).getName());
    assertEquals(ColumnType.STRING_ARRAY, header.getColumn(132).getType());

    assertEquals("post_event_list", header.getColumn(317).getName());
    assertEquals(ColumnType.STRING_ARRAY, header.getColumn(317).getType());

    assertEquals("post_search_engine", header.getColumn(441).getName());
    assertEquals(ColumnType.STRING, header.getColumn(441).getType());

    assertEquals("ref_type", header.getColumn(578).getName());
    assertEquals(ColumnType.STRING, header.getColumn(578).getType());

    assertEquals("va_finder_id", header.getColumn(636).getName());
    assertEquals(ColumnType.LONG, header.getColumn(636).getType());

    assertEquals("va_instance_event", header.getColumn(637).getName());
    assertEquals(ColumnType.LONG, header.getColumn(637).getType());

    assertEquals("va_new_engagement", header.getColumn(638).getName());
    assertEquals(ColumnType.LONG, header.getColumn(638).getType());

    assertEquals("visid_type", header.getColumn(660).getName());
    assertEquals(ColumnType.LONG, header.getColumn(660).getType());

    assertEquals("visit_num", header.getColumn(662).getName());
    assertEquals(ColumnType.INT, header.getColumn(662).getType());

    assertEquals("visit_page_num", header.getColumn(663).getName());
    assertEquals(ColumnType.INT, header.getColumn(663).getType());

    assertEquals("visit_search_engine", header.getColumn(665).getName());
    assertEquals(ColumnType.STRING, header.getColumn(665).getType());

    assertEquals("weekly_visitor", header.getColumn(669).getName());
    assertEquals(ColumnType.INT, header.getColumn(669).getType());

    assertEquals("yearly_visitor", header.getColumn(670).getName());
    assertEquals(ColumnType.INT, header.getColumn(670).getType());

    assertEquals("Lynx 2.7.1", table.getGroupValue("browser", "1"));
    assertEquals("Lynx 2.7.1", index.getGroupValue("browser", "1"));

    assertNull(index.getGroupValue("languages", "1"));
    assertNull(table.getGroupValue("languages", "1"));

    assertEquals("Not Specified", table.getGroupValue("language","1"));
    assertEquals("Afrikaans", index.getGroupValue("language","2"));

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

    assertEquals(null, table.getGroupValue("page_event","0"));
    assertEquals(null, table.getGroupValue("page_event","10"));

    assertEquals(null, index.getGroupValue("page_event", "1"));
    assertEquals(null, index.getGroupValue("page_event", "2"));

    assertEquals("internal", table.getGroupValue("ref_type", "1"));
    assertEquals("external", table.getGroupValue("ref_type", "2"));

    assertEquals("internal", index.getGroupValue("ref_type", "1"));
    assertEquals("external", index.getGroupValue("ref_type", "2"));

    assertEquals("Yahoo!", table.getGroupValue("search_engine", "1"));
    assertEquals("AltaVista", table.getGroupValue("search_engine", "2"));

    assertEquals("Yahoo!", index.getGroupValue("search_engine", "1"));
    assertEquals("AltaVista", index.getGroupValue("search_engine", "2"));

    assertEquals("Yahoo!", table.getGroupValue("post_search_engine", "1"));
    assertEquals("AltaVista", table.getGroupValue("post_search_engine", "2"));

    assertEquals("Yahoo!", index.getGroupValue("post_search_engine", "1"));
    assertEquals("AltaVista", index.getGroupValue("post_search_engine", "2"));

    assertEquals("Yahoo!", table.getGroupValue("visit_search_engine", "1"));
    assertEquals("AltaVista", table.getGroupValue("visit_search_engine", "2"));

    assertEquals("Yahoo!", index.getGroupValue("visit_search_engine", "1"));
    assertEquals("AltaVista", index.getGroupValue("visit_search_engine", "2"));

  }

}
