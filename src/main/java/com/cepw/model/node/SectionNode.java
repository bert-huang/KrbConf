package com.cepw.model.node;

import com.cepw.utils.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A node representing a section and all of it's properties.
 * A {@link SectionNode} can hold multiple {@link SimpleKeyValuesNode}s and
 * multiple {@link ComplexKeyValuesNode}s.
 */
public abstract class SectionNode<T> extends KrbConfNode {

  /**
   * The {@code serialVersionUID}
   */
  private static final long serialVersionUID = 2656840740346128646L;

  /**
   * The {@link Map} holding all {@link SimpleKeyValuesNode}s in the {@link SectionNode}
   */
  private Map<String, List<SimpleKeyValuesNode>> simpleKeyValuesNodes;

  /**
   * The {@link Map} holding all {@link ComplexKeyValuesNode}s in the {@link SectionNode}
   */
  private Map<String, ComplexKeyValuesNode> complexKeyValuesNodes;

  /**
   * Constructor.
   *
   * @param key the section key.
   */
  public SectionNode(String key) {
    super(key);
    this.simpleKeyValuesNodes = new HashMap<>();
    this.complexKeyValuesNodes = new HashMap<>();
  }

  /**
   * Adds a {@link SimpleKeyValuesNode} to the {@link SectionNode}.
   *
   * @param node the {@link SimpleKeyValuesNode}.
   * @return the added {@link SimpleKeyValuesNode}.
   */
  public SimpleKeyValuesNode add(SimpleKeyValuesNode node) {
    List<SimpleKeyValuesNode> existingNodes = this.simpleKeyValuesNodes.get(node.getKey());
    if (existingNodes == null) {
      List<SimpleKeyValuesNode> nodes = new ArrayList<>();
      nodes.add(node);
      this.simpleKeyValuesNodes.put(node.getKey(), nodes);
    } else {
      existingNodes.add(node);
    }
    return node;
  }

  /**
   * Adds a {@link ComplexKeyValuesNode} to the {@link SectionNode}
   *
   * @param node the {@link ComplexKeyValuesNode}
   * @return the added {@link ComplexKeyValuesNode}
   */
  public ComplexKeyValuesNode add(ComplexKeyValuesNode node) {
    ComplexKeyValuesNode existingNode = this.complexKeyValuesNodes.get(node.getKey());
    if (existingNode == null) {
      this.complexKeyValuesNodes.put(node.getKey(), node);
    } else {
      existingNode.merge(node);
    }
    return node;
  }

  /**
   * Remove all values in the {@link SectionNode} with the given key.
   * This will remove all {@link SimpleKeyValuesNode}s or {@link ComplexKeyValuesNode}
   * with that key.
   *
   * @param key the key to remove
   */
  public void remove(String key) {
    this.simpleKeyValuesNodes.remove(key);
    this.complexKeyValuesNodes.remove(key);
  }

  /**
   * Clear all {@link SimpleKeyValuesNode}s and {@link ComplexKeyValuesNode}s from the
   * {@link SectionNode}
   */
  public void clear() {
    this.simpleKeyValuesNodes.clear();
    this.complexKeyValuesNodes.clear();
  }

  /**
   * Returns the {@link KeyValueNode} with the given key.
   * This will return either {@link SimpleKeyValuesNode} or
   * {@link ComplexKeyValuesNode} depend on the specified {@link Class}.
   * <p>
   * Return null if no {@link KeyValueNode} with the given key is found.
   *
   * @param key   the key.
   * @param clazz the actual type of the {@link KeyValueNode}.
   * @param <T>   sub type for {@link KeyValueNode}
   * @return the {@link KeyValueNode} with the given key. Null if it does not exist.
   */
  public <T extends KeyValueNode> T get(String key, Class<T> clazz) {
    if (SimpleKeyValuesNode.class.equals(clazz)) {
      List<SimpleKeyValuesNode> nodes = this.simpleKeyValuesNodes.get(key);
      if (nodes != null) {
        List<String> values = new ArrayList<>();
        for (SimpleKeyValuesNode node : nodes) {
          values.addAll(node.getRawData());
        }
        return clazz.cast(new SimpleKeyValuesNode(key, values));
      }
    } else if (ComplexKeyValuesNode.class.equals(clazz)) {
      return clazz.cast(this.complexKeyValuesNodes.get(key));
    }
    return null;
  }

  /**
   * @return true if the {@link SectionNode} is empty; false otherwise.
   */
  public boolean isEmpty() {
    return this.simpleKeyValuesNodes.isEmpty() && this.complexKeyValuesNodes.isEmpty();
  }

  @Override
  public String toString(int indent) {
    StringBuilder sb = new StringBuilder();

    if (!isEmpty()) {
      sb.append(StringUtils.repeat(INDENT_CHARACTERS, indent));
      sb.append("[").append(this.getKey()).append("]").append("\n");

      for (Map.Entry<String, List<SimpleKeyValuesNode>> svns : this.simpleKeyValuesNodes.entrySet()) {
        for (SimpleKeyValuesNode svn : svns.getValue()) {
          sb.append(svn.toString(indent + 1));
        }
      }

      for (Map.Entry<String, ComplexKeyValuesNode> mvns : this.complexKeyValuesNodes.entrySet()) {
        sb.append(mvns.getValue().toString(indent + 1));
      }
      sb.append("\n");
    }
    return sb.toString();
  }
}
