package com.cepw.model.node;

import com.cepw.utils.StringUtils;

/**
 * A node representing a section and all of it's properties.
 */
public abstract class SectionNode extends ComplexKeyValuesNode {

  /**
   * The {@code serialVersionUID}
   */
  private static final long serialVersionUID = 2656840740346128646L;

  /**
   * Constructor.
   *
   * @param key the section key.
   */
  public SectionNode(String key) {
    super(key);
  }

  @Override
  public String asString(int indent) {
    if (!isEmpty()) {
      return StringUtils.repeat(INDENT_CHARACTERS, indent) +
              "[" + this.getKey() + "]" + "\n" +
              nodesAsString(indent + 1) +
              "\n";
    }
    return "";
  }
}
