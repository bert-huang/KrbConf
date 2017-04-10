package com.cepw.model.node;

/**
 * A class representing key-value nodes.
 * Can be either {@link SimpleKeyValuesNode} or {@link ComplexKeyValuesNode}
 */
public abstract class KeyValuesNode extends KrbConfNode {

  /**
   * The {@code serialVersionUID}
   */
  private static final long serialVersionUID = 8154855487572802648L;

  /**
   * Constructor.
   *
   * @param key the key of the node.
   */
  public KeyValuesNode(String key) {
    super(key);
  }
}
