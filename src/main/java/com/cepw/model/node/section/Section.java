package com.cepw.model.node.section;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Section implements Serializable {

  private static final long serialVersionUID = 2656840740346128646L;

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

  public boolean isEmpty() {
    return entries.isEmpty();
  }
}
