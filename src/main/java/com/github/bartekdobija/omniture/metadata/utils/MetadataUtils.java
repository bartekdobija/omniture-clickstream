package com.github.bartekdobija.omniture.metadata.utils;

import com.github.bartekdobija.omniture.loader.DataLoader;
import com.github.bartekdobija.omniture.loader.DataLoaderException;
import com.github.bartekdobija.omniture.manifest.OmnitureManifest;
import com.github.bartekdobija.omniture.manifest.ManifestException;
import com.github.bartekdobija.omniture.loader.utils.DataLoaderUtils;
import com.github.bartekdobija.omniture.metadata.*;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import sun.net.www.MessageHeader;
import sun.tools.jar.Manifest;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.GZIPInputStream;

public class MetadataUtils {

  public static final String DATA_LOOKUP_FILE_KEY = "Lookup-File";

  private static final String SEPARATOR = "/";
  private static final int LOOKUP_FILE_ENTRY_GROUP = 1;
  private static final int ZERO_LENGTH = 0;
  private static final char NEW_LINE_CHAR = '\n';
  private static final char SPACE_CHAR = ' ';
  private static final char COMMA_CHAR = ',';
  private static final String HIVE_TPL_LOCATION = "/hive/log.q.tpl";

  public static OmnitureMetadata fromOmnitureManifest(OmnitureManifest manifest)
      throws ManifestException {

    URI dataDir = manifest.getDataDirectory();
    String lookup =
        dataDir
        + SEPARATOR
        + manifest
            .entryAt(LOOKUP_FILE_ENTRY_GROUP)
            .findValue(DATA_LOOKUP_FILE_KEY);

    DataLoader loader = DataLoaderUtils.getLoader(lookup);

    return new OmnitureMetadata(
        new Header(loader),
        new LookupTable(loader),
        dataFileList(dataDir,manifest)
    );
  }

  public static String getArchivedContent(DataLoader loader, String fileName)
      throws MetadataException {
    try {
      return getArchivedContent(loader.stream(), fileName);
    } catch (DataLoaderException e) {
      throw new MetadataException(e);
    }
  }

  public static String getArchivedContent(InputStream stream, String fileName)
      throws MetadataException {
    Map<String, String> result =
        getArchivedContent(stream, Collections.singletonList(fileName));
    return result != null ? result.get(fileName) : null;
  }

  public static Map<String, String> getArchivedContent(
      DataLoader loader, String[] fileNames) throws MetadataException {
    try {
      return getArchivedContent(loader.stream(), Arrays.asList(fileNames));
    } catch (DataLoaderException e) {
      throw new MetadataException(e);
    }
  }

  public static String hiveTableDefinition(OmnitureMetadata meta) {
    InputStream s = MetadataUtils.class.getResourceAsStream(HIVE_TPL_LOCATION);
    BufferedReader bf = new BufferedReader(
        new InputStreamReader(s, StandardCharsets.UTF_8));
    StringBuilder sb = new StringBuilder();
    try {
      String line;
      while ((line = bf.readLine()) != null) sb.append(line).append("\n");
      return sb.toString()
          .replace("${COLUMNS}", renderCols(meta))
          .replaceAll(ColumnType.LONG.name, ColumnType.BIGINT.name);
    } catch (IOException | MetadataException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static Map<String, String> getArchivedContent(
      InputStream stream, List<String> fileNames) throws MetadataException {

    Map<String, String> result = new HashMap<>();

    try (TarArchiveInputStream tis =
             new TarArchiveInputStream(new GZIPInputStream(stream))) {

      TarArchiveEntry entry;

      String name;
      int entrySize, len;
      byte[] buff;
      ByteArrayOutputStream bos = new ByteArrayOutputStream();

      while((entry = tis.getNextTarEntry()) != null) {
        name = entry.getName();
        if (!fileNames.contains(name) || entry.isDirectory()) {
          continue;
        }

        entrySize = (int) entry.getSize();
        buff = new byte[entrySize];
        bos.reset();

        while((len = tis.read(buff)) != -1) {
          bos.write(buff,0,len);
        }

        result.put(name, bos.toString());
      }
    } catch (IOException e) {
      throw new MetadataException(e);
    }

    return !result.isEmpty() ? result : null;
  }

  public static LookupTableIndex asLookupTableIndex(Map<String, String> map) {
    if (map == null) {
      return null;
    }
    return new LookupTableIndex(map);
  }

  private static String renderCols(OmnitureMetadata meta)
      throws MetadataException {
    List<Column> cols = meta.getHeader().getColumns();
    StringBuilder sb = new StringBuilder();
    sb.append(NEW_LINE_CHAR);
    int size = cols.size();
    for(int i = 0; i < size; i++) {
      Column c = cols.get(i);
      sb.append(c.getName()).append(SPACE_CHAR).append(c.getType().name);
      if (i < size - 1) sb.append(COMMA_CHAR);
      sb.append(NEW_LINE_CHAR);
    }
    return sb.toString();
  }

  private static List<DataFile> dataFileList(URI dataDir, Manifest manifest) {

    List<DataFile> result = new ArrayList<>();
    StringBuilder path = new StringBuilder();

    for(Enumeration e = manifest.entries(); e.hasMoreElements();) {
      MessageHeader mh = (MessageHeader) e.nextElement();
      String file = mh.findValue(DataFile.NAME_KEY);
      String digest = mh.findValue(DataFile.DIGEST_KEY);
      String fileSize = mh.findValue(DataFile.SIZE_KEY);
      String recordCount = mh.findValue(DataFile.RECORD_COUNT_KEY);
      if (!isValidDataFileMeta(file, digest, fileSize, recordCount)) {
        //TODO: log error
        continue;
      }

      path.setLength(ZERO_LENGTH);
      path.append(dataDir.toString()).append(SEPARATOR).append(file);

      result.add(
          new DataFile(path.toString(),
              digest,
              Long.parseLong(fileSize),
              Long.parseLong(recordCount)
          )
      );
    }

    return result;
  }

  private static boolean isValidDataFileMeta(Object ...object) {
    for(Object s : object) {
      if(s == null || (s instanceof String && ((String) s).isEmpty())) {
        return false;
      }
    }
    return true;
  }

}
