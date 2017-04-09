package com.cepw.model.node;

import java.io.Serializable;

/**
 * Class to represent the up most node entity when constructing
 * a Kerberos configuration.
 */
public abstract class KrbConfNode implements Serializable {

  /**
   * The {@code serialVersionUID}
   */
  private static final long serialVersionUID = -4871360033178840759L;

  /**
   * The character used for indentation when outputting this {@link KrbConfNode}
   * as {@link String}
   */
  public static final String INDENT_CHARACTER = "\t";

  /**
   * The key of the node.
   */
  private String key;

  /**
   * Constructor.
   *
   * @param key the key of the node. Cannot be null.
   */
  public KrbConfNode(String key) {
    if (key == null) {
      throw new IllegalArgumentException("key cannot be null");
    }
    this.key = key.trim();
  }

  /**
   * @return the key of the {@link KrbConfNode}
   */
  public String getKey() {
    return key;
  }

  /**
   * Returns the {@link String} representation of the {@link KrbConfNode}.
   *
   * @param indent the initial indentation for this node
   * @return the {@link String} representation of the node
   */
  public abstract String toString(int indent);

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    KrbConfNode node = (KrbConfNode) o;

    return key.equals(node.key);
  }

  @Override
  public int hashCode() {
    return key.hashCode();
  }

  @Override
  public String toString() {
    return toString(0);
  }
}
