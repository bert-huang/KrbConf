package com.cepw.model.node.value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SingleValueNode extends ValueNode {

  private List<String> value;

  public SingleValueNode(String key, String... values) {
    super(key);

    if (values == null || values.length == 0) {
      throw new IllegalArgumentException("values cannot be null or empty");
    }
    List<String> list = new ArrayList<>();
    for(String value : values) {
      list.add(value.trim());
    }

    this.value = list;
  }


  public List<String> getValue() {
    return Collections.unmodifiableList(value);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    SingleValueNode that = (SingleValueNode) o;

    return value.equals(that.value);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + value.hashCode();
    return result;
  }
}
