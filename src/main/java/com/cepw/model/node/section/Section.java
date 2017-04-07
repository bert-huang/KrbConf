package com.cepw.model.node.section;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Section {

  protected Map<String, Object> entries;

  public Section() {
    entries = new HashMap<>();
  }

  public Object put(String key, Object val) {
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

  public Map<String, Object> getEntries() {
    return this.entries;
  }
}
