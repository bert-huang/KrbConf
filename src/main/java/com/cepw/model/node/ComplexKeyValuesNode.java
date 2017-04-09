package com.cepw.model.node;

import com.cepw.utils.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A node representing a complex key-value entry.
 * A {@link ComplexKeyValuesNode} can hold multiple {@link SimpleKeyValuesNode}s.
 */
public class ComplexKeyValuesNode extends KeyValueNode {

  /**
   * The {@code serialVersionUID}
   */
  private static final long serialVersionUID = -4241028347277548226L;

  /**
   * The {@link Map} holding a {@link List}s of {@link SimpleKeyValuesNode}s.
   * A {@link List} is required as krb.conf can have multiple {@link SimpleKeyValuesNode}s
   * with the same key but different values.
   * e.g.
   *      kdc = kerberos.mit.edu
   *      kdc = kerberos-1.mit.edu
   *      kdc = kerberos-2.mit.edu:750
   */
  private Map<String, List<SimpleKeyValuesNode>> simpleKeyValuesNodes;

  /**
   * Constructor.
   *
   * @param key the key of the node.
   * @param nodes the array of {@link SimpleKeyValuesNode}s.
   */
  public ComplexKeyValuesNode(String key, SimpleKeyValuesNode... nodes) {
    super(key);
    this.simpleKeyValuesNodes = new HashMap<>();
    if (nodes != null) {
      for (SimpleKeyValuesNode node : nodes) {
        List<SimpleKeyValuesNode> existingNodes = this.simpleKeyValuesNodes.get(node.getKey());
        if (existingNodes == null) {
          existingNodes = new ArrayList<>();
          existingNodes.add(node);
          this.simpleKeyValuesNodes.put(node.getKey(), existingNodes);
        } else {
          existingNodes.add(node);
        }
      }
    }
  }

  /**
   * Constructor.
   *
   * @param key the key of the node.
   * @param nodes the {@link List} of {@link SimpleKeyValuesNode}s.
   */
  public ComplexKeyValuesNode(String key, List<SimpleKeyValuesNode> nodes) {
    this(key, nodes.toArray(new SimpleKeyValuesNode[nodes.size()]));
  }

  /**
   * Adds a {@link SimpleKeyValuesNode} to the node.
   *
   * @param node the {@link SimpleKeyValuesNode}
   * @return the added {@link SimpleKeyValuesNode}
   */
  public SimpleKeyValuesNode add(SimpleKeyValuesNode node) {
    List<SimpleKeyValuesNode> existingNodes = this.simpleKeyValuesNodes.get(node.getKey());
    if (existingNodes == null) {
      List<SimpleKeyValuesNode> nodes = new ArrayList<>();
      nodes.add(node);
      this.simpleKeyValuesNodes.put(node.getKey(), nodes);
    }
    else {
      existingNodes.add(node);
    }
    return node;
  }

  /**
   * Merge two {@link ComplexKeyValuesNode} together.
   * If a {@link List} of {@link SimpleKeyValuesNode} with the same key already exists
   * in the {@link Map} of node A, add all the content from the {@link List} in node B into node A.
   * If the key does not already exist, add the {@link List} in node B into node A.
   *
   * @param node the node to merge into the node.
   */
  public void merge(ComplexKeyValuesNode node) {
    for (Map.Entry<String, List<SimpleKeyValuesNode>> singleValueNodes : node.simpleKeyValuesNodes.entrySet()) {
      List<SimpleKeyValuesNode> existingNodes = this.simpleKeyValuesNodes.get(singleValueNodes.getKey());
      if (existingNodes == null) {
        this.simpleKeyValuesNodes.put(singleValueNodes.getKey(), singleValueNodes.getValue());
      }
      else {
        existingNodes.addAll(singleValueNodes.getValue());
      }
    }
  }

  /**
   * Get the {@link SimpleKeyValuesNode} with the given key.
   * This method create a new {@link SimpleKeyValuesNode} with the
   * combined value of all other {@link SimpleKeyValuesNode}s with the
   * same key.
   *
   * @param key the key
   * @return the {@link SimpleKeyValuesNode} containing combined values of other
   * {@link SimpleKeyValuesNode}s.
   */
  public SimpleKeyValuesNode get(String key) {
    List<SimpleKeyValuesNode> nodes = this.simpleKeyValuesNodes.get(key);
    List<String> values = new ArrayList<>();
    for (SimpleKeyValuesNode node : nodes) {
      values.addAll(node.getRawData());
    }
    return new SimpleKeyValuesNode(key, values);
  }

  /**
   * Returns the underlying ADT holding the information about
   * this node.
   *
   * @return the {@link Map} holding the mapping between key and the {@link List}
   * of {@link SimpleKeyValuesNode}s
   */
  public Map<String, List<SimpleKeyValuesNode>> getRawData() {
    return this.simpleKeyValuesNodes;
  }

  /**
   * Removes all {@link SimpleKeyValuesNode}s with the given key from the node.
   *
   * @param key the key to remove.
   */
  public SimpleKeyValuesNode remove(String key) {
    SimpleKeyValuesNode node = get(key);
    this.simpleKeyValuesNodes.remove(key);
    return node;
  }

  /**
   * Clear all the {@link SimpleKeyValuesNode}s from the node
   */
  public void clear() {
    this.simpleKeyValuesNodes.clear();
  }

  @Override
  public String toString(int indent) {
    indent = indent < 0 ? 0 : indent;

    StringBuilder sb = new StringBuilder();
    sb.append(StringUtils.repeat(INDENT_CHARACTERS, indent));
    sb.append(this.getKey());
    sb.append(" = {").append("\n");
    for (Map.Entry<String, List<SimpleKeyValuesNode>> node : this.simpleKeyValuesNodes.entrySet()) {
      for (SimpleKeyValuesNode svn : node.getValue()) {
        sb.append(svn.toString(indent+1));
      }
    }
    sb.append(StringUtils.repeat(INDENT_CHARACTERS, indent));
    sb.append("}").append("\n");

    return sb.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    ComplexKeyValuesNode that = (ComplexKeyValuesNode) o;

    return simpleKeyValuesNodes.equals(that.simpleKeyValuesNodes);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + simpleKeyValuesNodes.hashCode();
    return result;
  }
}
