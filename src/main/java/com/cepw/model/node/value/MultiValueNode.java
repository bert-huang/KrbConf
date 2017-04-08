package com.cepw.model.node.value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiValueNode extends ValueNode {

  private Map<String, List<List<String>>> values;

  public MultiValueNode(String key, SingleValueNode... nodes) {
    super(key);

    if (nodes == null || nodes.length == 0) {
      throw new IllegalArgumentException("values cannot be null or empty");
    }
    this.values = new HashMap<>();
    for (SingleValueNode node : nodes) {
      List<List<String>> existingValue = this.values.get(node.getKey());
      if (existingValue == null) {
        existingValue = new ArrayList<>();
        existingValue.add(node.getValue());
        this.values.put(node.getKey(), existingValue);
      }
      else {
        existingValue.add(node.getValue());
      }
    }
  }

  public Map<String, Object> getValues() {
    return (Map) values;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    MultiValueNode that = (MultiValueNode) o;

    return values.equals(that.values);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + values.hashCode();
    return result;
  }
}
