package com.cepw.model.node;

import com.cepw.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;

public class SimpleKeyValuesNode extends Node {

  private List<String> values;

  public SimpleKeyValuesNode(String key, String... args) {
    super(key);

    if (args == null || args.length == 0) {
      throw new IllegalArgumentException("values cannot be null or empty");
    }
    List<String> values = new ArrayList<>();
    for(String value : args) {
      values.add(value.trim());
    }

    this.values = values;
  }

  public SimpleKeyValuesNode(String key, List<String> values) {
    this(key, values.toArray(new String[values.size()]));
  }

  public void add(String string) {
    this.values.add(string);
  }

  public void remove(String string) {
    this.values.remove(string);
  }

  public void clear() {
    this.values.clear();
  }

  public List<String> getAllValues() {
    return values;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    SimpleKeyValuesNode that = (SimpleKeyValuesNode) o;

    return values.equals(that.values);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + values.hashCode();
    return result;
  }

  public String print(int indent) {
    StringBuilder sb = new StringBuilder();
    sb.append(StringUtils.repeat(INDENT_CHARACTER, indent));
    sb.append(key);
    sb.append(" =");
    for (String value : values) {
      sb.append(" ");
      sb.append(value);
    }
    sb.append("\n");
    return sb.toString();
  }
}
