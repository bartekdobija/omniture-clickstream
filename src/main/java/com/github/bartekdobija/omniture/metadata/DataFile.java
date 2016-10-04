package com.github.bartekdobija.omniture.metadata;

/**
 * Objects of this class represent log data files listed in the {@link com.github.bartekdobija.omniture.manifest.OmnitureManifest}s
 */
public class DataFile {

  private String name;
  private String digest;
  private long size;
  private long records;

  public static final String NAME_KEY = "Data-File";
  public static final String DIGEST_KEY = "MD5-Digest";
  public static final String SIZE_KEY = "File-Size";
  public static final String RECORD_COUNT_KEY = "Record-Count";

  public DataFile(String name, String digest, long size, long records) {
    this.name = name;
    this.digest = digest;
    this.size = size;
    this.records = records;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDigest() {
    return digest;
  }

  public void setDigest(String digest) {
    this.digest = digest;
  }

  public long getSize() {
    return size;
  }

  public void setSize(long size) {
    this.size = size;
  }

  public long getRecords() {
    return records;
  }

  public void setRecords(long records) {
    this.records = records;
  }

  @Override
  public String toString() {
    return "DataFile{" +
        "name='" + name + '\'' +
        ", digest='" + digest + '\'' +
        ", size=" + size +
        ", records=" + records +
        '}';
  }
}
