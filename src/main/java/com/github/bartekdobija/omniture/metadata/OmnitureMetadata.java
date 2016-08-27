package com.github.bartekdobija.omniture.metadata;

import java.util.List;

public class OmnitureMetadata {

  private Header header;
  private LookupTable lookupTable;
  private List<DataFile> dataFiles;

  public OmnitureMetadata() {
  }

  public OmnitureMetadata(Header header, LookupTable lookup, List<DataFile> data) {
    this.header = header;
    this.lookupTable = lookup;
    this.dataFiles = data;
  }

  public Header getHeader() {
    return header;
  }

  public void setHeader(Header header) {
    this.header = header;
  }

  public LookupTable getLookupTable() {
    return lookupTable;
  }

  public void setLookupTable(LookupTable lookupTable) {
    this.lookupTable = lookupTable;
  }

  public List<DataFile> getDataFiles() {
    return dataFiles;
  }

  public void setDataFiles(List<DataFile> dataFiles) {
    this.dataFiles = dataFiles;
  }

  @Override
  public String toString() {
    return "OmnitureMetadata{" +
        "header=" + header +
        ", lookupTable=" + lookupTable +
        ", data=" + dataFiles +
        '}';
  }
}
