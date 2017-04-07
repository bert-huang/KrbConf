package com.cepw.model.section;

import java.util.HashMap;
import java.util.Map;

public abstract class Section {

  private Map<String, Object> entries;

  public Section() {
    entries = new HashMap<String, Object>();
  }

  public Object add(String key, Object val) {

    Object obj = entries.get(key);
    if (obj instanceof String) {
      String str = (String) obj;
      if (str.trim().endsWith("*")) {
        return obj;
      }
    }

    return entries.put(key, val);
  }

  public Object remove(String key) {
    return entries.remove(key);
  }

  public void clear() {
    entries.clear();
  }

  public Object get(String key) {
    return entries.get(key);
  }

}
