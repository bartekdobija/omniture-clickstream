package com.github.bartekdobija.omniture.metadata;

import com.github.bartekdobija.omniture.loader.DataLoader;
import com.github.bartekdobija.omniture.metadata.utils.MetadataUtils;

import java.util.Map;

public class LookupTable {

  public static String[] REGISTERED_LOOKUP_TABLES = {
    "browser.tsv", "browser_type.tsv", "color_depth.tsv", "connection_type.tsv",
    "country.tsv", "event.tsv", "event_lookup.tsv", "javascript_version.tsv",
    "languages.tsv", "operating_systems.tsv", "plugins.tsv",
    "referrer_type.tsv", "resolution.tsv", "search_engines.tsv"
  };

  public static final String LOOKUP_TABLE_EOL = "\n";
  public static final String LOOKUP_TABLE_SEPARATOR = "\t";
  public static final String LOOKUP_FILE_EXT = ".tsv";
  private static final String EMPTY_STR = "";

  private DataLoader loader;
  private LookupTableIndex index = null;

  public LookupTable(DataLoader dataLoader) {
    loader = dataLoader;
  }

  public String getGroupValue(String group, String key)
      throws MetadataException {
    return getIndex().getGroupValue(group, key);
  }

  public LookupTableIndex getIndex() throws MetadataException {
    if (index == null) {
      ensureIndexAvailable();
    }
    return index;
  }

  public DataLoader getLoader() {
    return loader;
  }

  private void ensureIndexAvailable() throws MetadataException {
    Map<String, String> tableContent =
        MetadataUtils.getArchivedContent(loader, REGISTERED_LOOKUP_TABLES);

    LookupTableIndex tmp = new LookupTableIndex();
    for (Map.Entry<String, String> e : tableContent.entrySet()) {
      String group = e.getKey().replace(LOOKUP_FILE_EXT, EMPTY_STR);
      String[] cols;
      for (String line: e.getValue().split(LOOKUP_TABLE_EOL)) {
        cols = line.split(LOOKUP_TABLE_SEPARATOR);
        if (cols.length == 2) {
          tmp.setGroupValue(group, cols[0], cols[1]);
        }
      }
    }

    //TODO: check if the condition is required, there's an exception thrown earlier
    if (!tmp.isEmpty()) index = tmp;
  }

  @Override
  public String toString() {
    return "LookupTable{" +
        "loader=" + loader +
        ", index=" + index +
        '}';
  }
}
