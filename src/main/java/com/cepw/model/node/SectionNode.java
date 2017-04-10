package com.cepw.model.node;

import java.util.List;
import java.util.Map;

import com.cepw.utils.StringUtils;

/**
 * A node representing a section and all of it's properties.
 * A {@link SectionNode} can hold multiple {@link SimpleKeyValuesNode}s and
 * multiple {@link ComplexKeyValuesNode}s.
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
    StringBuilder sb = new StringBuilder();

    if (!isEmpty()) {
      sb.append(StringUtils.repeat(INDENT_CHARACTERS, indent));
      sb.append("[").append(this.getKey()).append("]").append("\n");
      sb.append(nodesAsString(indent + 1));
      sb.append("\n");
    }
    return sb.toString();
  }
}
