package com.github.bartekdobija.omniture.metadata;

import java.util.HashMap;
import java.util.Map;

public class LookupTableIndex extends HashMap<String, String> {

  public transient static final char GROUP_SEPARATOR = ':';

  public LookupTableIndex() {

  }

  public LookupTableIndex(Map<String, String> map) {
    super(map);
  }

  //TODO : optimize this
  public char[] getGroupValue(String group, char[] data) {
    String ret = get(
        group + GROUP_SEPARATOR + String.valueOf(data, 0, data.length));
    return ret != null ? ret.toCharArray() : data;
  }

  public String getGroupValue(String group, String key) {
    return get(group + GROUP_SEPARATOR + key);
  }

  public void setGroupValue(String group, String key, String value) {
    put(group + GROUP_SEPARATOR + key, value);
  }

  public String getGroupValue(String group, String key, String def) {
    String res = getGroupValue(group, key);
    return res != null ? res : def;
  }
}
