package com.github.bartekdobija.omniture;


import com.github.bartekdobija.omniture.metadata.MetadataException;
import com.github.bartekdobija.omniture.metadata.OmnitureMetadata;
import com.github.bartekdobija.omniture.metadata.OmnitureMetadataFactory;
import static org.junit.Assert.*;

import com.github.bartekdobija.omniture.row.*;
import org.junit.Test;

import java.util.List;

/**
 * This test demonstrates a sample API use case.
 */
public class SampleUseCaseTest {

  private static final String LIST_SEPARATOR = ",";

  private static final String MANIFEST_FILE =
      SampleUseCaseTest.class.
          getResource("/data/suiteid_2015-02-10.txt").toString();

  private static final String MANIFEST_LIST =
      SampleUseCaseTest.class.
          getResource("/data/suiteid_2015-02-10.txt").toString()
      + LIST_SEPARATOR +
      SampleUseCaseTest.class.
          getResource("/data/suiteid_2015-12-29.txt").toString();

  private static final String ROW_STRING = "a\tb\tc";


  @Test
  public void singleManifest() throws MetadataException, RowParserException {

    OmnitureMetadata metadata =
        new OmnitureMetadataFactory().create(MANIFEST_FILE);
    RowParser parser = DenormalizedDataRowParser.newInstance(metadata);
    Row row = parser.parse(ROW_STRING);

    assertNotNull(row);
    assertNotNull(row.get(0));
    assertNotNull(row.get(1));
    assertNotNull(row.get(2));

    RowParserStats stats = parser.getRowParserStats();

    assertEquals(1, stats.getParsedCount());
    assertEquals(0, stats.getEmptyRowCount());
    assertEquals(0, stats.getExceptionCount());

  }

  @Test
  public void multipleManifests() throws MetadataException, RowParserException {
    List<OmnitureMetadata> metadatas =
        new OmnitureMetadataFactory().create(MANIFEST_LIST, LIST_SEPARATOR);

    RowParser parserA = DenormalizedDataRowParser.newInstance(metadatas.get(0));
    RowParser parserB = DenormalizedDataRowParser.newInstance(metadatas.get(1));

    assertNotNull(parserA);
    assertNotNull(parserB);

  }

}
