package com.github.bartekdobija.omniture.metadata;

import com.github.bartekdobija.omniture.loader.DataLoader;
import com.github.bartekdobija.omniture.metadata.columntyperesolver.ColumnTypeResolver;
import com.github.bartekdobija.omniture.metadata.columntyperesolver.SimpleColumnTypeResolver;
import com.github.bartekdobija.omniture.metadata.utils.MetadataUtils;

import java.util.ArrayList;
import java.util.List;

public class Header {

  public static final String DEFAULT_HEADER_FILE = "column_headers.tsv";
  public static final String COLUMN_SEPARATOR = "\t";

  private DataLoader loader;
  private List<Column> columns;
  private String headers;
  private ColumnTypeResolver colResolver = new SimpleColumnTypeResolver();

  public Header(DataLoader dataLoader) {
    this(dataLoader, DEFAULT_HEADER_FILE);
  }

  public Header(DataLoader dataLoader, String headerFile) {
    super();
    setLoader(dataLoader);
    headers = headerFile;
  }

  public void setColumnParser(ColumnTypeResolver resolver) {
    colResolver = resolver;
  }

  public void setLoader(DataLoader loader) {
    this.loader = loader;
  }

  public List<Column> getColumns() throws MetadataException {
    if (columns == null) {
      ensureInitColumns();
    }
    return columns;
  }

  public Column getColumn(int index) throws MetadataException {
    return getColumns().get(index);
  }

  private void ensureInitColumns() throws MetadataException {
    String content = MetadataUtils.getArchivedContent(loader, headers);
    List<Column> result = new ArrayList<>();
    for(String name: content.trim().split(COLUMN_SEPARATOR)) {
      result.add(colResolver.parseName(name));
    }
    if (!result.isEmpty()) columns = result;
  }

}
