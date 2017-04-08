package com.cepw.model.node.value;

public abstract class ValueNode {

  private String key;

  public ValueNode(String key) {
    if (key == null) {
      throw new IllegalArgumentException("key cannot be null");
    }
    this.key = key.trim();
  }

  public String getKey() {
    return key;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ValueNode valueNode = (ValueNode) o;

    return key.equals(valueNode.key);
  }

  @Override
  public int hashCode() {
    return key.hashCode();
  }
}
