package com.cepw.model.node;

import java.io.Serializable;

public abstract class Node implements Serializable {

  private static final long serialVersionUID = -4871360033178840759L;

  public static final String INDENT_CHARACTER = "\t";

  protected String key;

  public Node(String key) {
    if (key == null) {
      throw new IllegalArgumentException("key cannot be null");
    }
    this.key = key.trim();
  }

  public String getKey() {
    return key;
  }

  public abstract String print(int indent);

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Node node = (Node) o;

    return key.equals(node.key);
  }

  @Override
  public int hashCode() {
    return key.hashCode();
  }

  @Override
  public String toString() {
    return print(0);
  }
}
