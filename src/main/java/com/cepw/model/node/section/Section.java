package com.cepw.model.node.section;

import com.cepw.model.node.value.MultiValueNode;
import com.cepw.model.node.value.SingleValueNode;
import com.cepw.utils.StringUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Section implements Serializable {

  private static final long serialVersionUID = 2656840740346128646L;

  protected Map<String, Object> entries;

  public Section() {
    entries = new HashMap<>();
  }

  public boolean add(SingleValueNode node) {
    Object existingEntry = this.entries.get(node.getKey());
    if (existingEntry == null) {
      List<List<String>> list = new ArrayList<>();
      list.add(node.getValue());
      this.entries.put(node.getKey(), list);
      return true;
    }
    else if (existingEntry instanceof List) {
      ((List<List<String>>)existingEntry).add(node.getValue());
      return true;
    }
    else if (existingEntry instanceof Map) {
      return false;
    }
    return false;
  }

  public boolean add(MultiValueNode node) {
    Object existingEntry = this.entries.get(node.getKey());
    if (existingEntry == null) {
      this.entries.put(node.getKey(), node.getValues());
      return true;
    }
    else if (existingEntry instanceof Map) {
      Map<String, Object> existingMap = (Map<String, Object>)existingEntry;
      for(Map.Entry<String, Object> entry : node.getValues().entrySet()) {
        if (existingMap.get(entry.getKey()) == null) {
          existingMap.put(entry.getKey(), entry.getValue());
        }
        else {
          List<List<String>> existingObj = (List<List<String>>) existingMap.get(entry.getKey());
          existingObj.addAll(((List<List<String>>)entry.getValue()));
        }
      }
      return true;
    }
    else if (existingEntry instanceof List) {
      return false;
    }
    return false;
  }

  public void remove(String key) {
    this.entries.remove(key);
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

        for (List<String> values : lists) {
          sb.append(StringUtils.repeat(" ", 2 * depth));
          sb.append(entry.getKey());
          sb.append(" =");
          for (String value : values) {
            sb.append(" ");
            sb.append(value);
          }
          sb.append("\n");
        }
      } else if (entry.getValue() instanceof Map) {
        sb.append(StringUtils.repeat(" ", 2 * depth));
        sb.append(entry.getKey());
        sb.append(" = {").append("\n");
        sb.append(traverse((Map<String, Object>)entry.getValue(), depth+1));
        sb.append(StringUtils.repeat(" ", 2 * depth));
        sb.append("}");
        sb.append("\n");
      }
      else {
        throw new IllegalStateException("Encountered unknown entry: " + entry);
      }
    }
    return sb.toString();
  }
}
