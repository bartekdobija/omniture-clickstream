package com.github.bartekdobija.omniture.metadata;


import static org.junit.Assert.*;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class OmnitureMetadataFactoryTest {

  private static final String PWD = "file:" + new File(".").getAbsolutePath();

  private static final String MANIFEST_FILE =
      PWD + "/src/test/resources/data/suiteid_2015-12-29.txt";
  private static final String MANIFEST_FILE_LIST =
      PWD + "/src/test/resources/data/suiteid_2015-02-10.txt," +
      PWD + "/src/test/resources/data/suiteid_2015-12-29.txt";

  private static String DATA_FILE_LOCATION =
      PWD + "/src/test/resources/data/01-suiteid_2015-12-29.tsv.gz";

  private static String SEPARATOR = ",";

  @Test
  public void objectFactory() throws MetadataException {

    OmnitureMetadata meta = new OmnitureMetadataFactory().create(MANIFEST_FILE);

    assertEquals("accept_language", meta.getHeader().getColumn(0).getName());
    assertEquals(DATA_FILE_LOCATION, meta.getDataFiles().get(0).getName());
    assertEquals(
        "Thailand",
        meta.getLookupTable().getIndex().getGroupValue("country", "220")
    );
  }

  @Test
  public void listFactory() throws MetadataException {

    List<OmnitureMetadata> metas =
        new OmnitureMetadataFactory().create(MANIFEST_FILE_LIST, SEPARATOR);

    OmnitureMetadata meta = metas.get(1);

    assertEquals("accept_language", meta.getHeader().getColumn(0).getName());
    assertEquals(DATA_FILE_LOCATION, meta.getDataFiles().get(0).getName());
    assertEquals(
        "Thailand",
        meta.getLookupTable().getIndex().getGroupValue("country", "220")
    );

  }

}
