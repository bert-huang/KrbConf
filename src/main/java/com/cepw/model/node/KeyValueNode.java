package com.cepw.model.node;

/**
 * A class representing key-value nodes.
 * Can be either {@link SimpleKeyValuesNode} or {@link ComplexKeyValuesNode}
 */
public abstract class KeyValueNode extends KrbConfNode {

  /**
   * The {@code serialVersionUID}
   */
  private static final long serialVersionUID = 8154855487572802648L;

  /**
   * Constrcutor.
   *
   * @param key the key of the node.
   */
  public KeyValueNode(String key) {
    super(key);
  }
}
