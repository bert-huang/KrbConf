package com.cepw.model.section;

import com.cepw.utils.StringUtils;
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

  public abstract String getSectionName();

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    if (!isEmpty()) {
      sb.append("[").append(getSectionName()).append("]").append("\n");
      sb.append(traverse(entries, 1));
      sb.append("\n");
    }
    return sb.toString();
  }

  private String traverse(Map<String, Object> entries, int depth) {
    StringBuilder sb = new StringBuilder();
    for (Map.Entry<String, Object> entry : entries.entrySet()) {
      if (entry.getValue() instanceof List) {
        List<List<String>> lists = (List<List<String>>) entry.getValue();
        sb.append(StringUtils.repeat(" ", 2 * depth));

        sb.append(entry.getKey());
        sb.append(" =");
        for (List<String> values : lists) {
          for (String value : values) {
            sb.append(" ");
            sb.append(value);
          }
        }
      } else if (entry.getValue() instanceof Map) {
        sb.append(StringUtils.repeat(" ", 2 * depth));
        sb.append(entry.getKey());
        sb.append(" = {").append("\n");
        sb.append(traverse((Map<String, Object>)entry.getValue(), depth+1));
        sb.append(StringUtils.repeat(" ", 2 * depth));
        sb.append("}");
      }
      else {
        throw new IllegalStateException("Encountered unknown entry");
      }
      sb.append("\n");
    }
    return sb.toString();
  }
}
