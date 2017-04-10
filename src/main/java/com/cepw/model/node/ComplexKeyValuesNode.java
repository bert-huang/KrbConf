package com.cepw.model.node;

import com.cepw.utils.StringUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A node representing a complex key-value entry.
 * A {@link ComplexKeyValuesNode} can hold multiple {@link SimpleKeyValuesNode}s and
 * multiple {@link ComplexKeyValuesNode}s.
 */
public class ComplexKeyValuesNode extends KeyValuesNode {

  /**
   * The {@code serialVersionUID}
   */
  private static final long serialVersionUID = -4241028347277548226L;

  /**
   * The {@link Map} holding a {@link List}s of {@link SimpleKeyValuesNode}s.
   * A {@link List} is required as krb.conf can have multiple {@link SimpleKeyValuesNode}s
   * with the same key but different values.
   * e.g.
   * kdc = kerberos.mit.edu
   * kdc = kerberos-1.mit.edu
   * kdc = kerberos-2.mit.edu:750
   */
  protected Map<String, List<SimpleKeyValuesNode>> simpleKeyValuesNodes;

  /**
   * The {@link Map} holding all {@link ComplexKeyValuesNode}s
   */
  protected Map<String, ComplexKeyValuesNode> complexKeyValuesNodes;

  /**
   * Constructor.
   *
   * @param key   the key of the node.
   */
  public ComplexKeyValuesNode(String key) {
    super(key);
    this.simpleKeyValuesNodes = new LinkedHashMap<String, List<SimpleKeyValuesNode>>();
    this.complexKeyValuesNodes = new LinkedHashMap<String, ComplexKeyValuesNode>();
  }

  /**
   * Constructor.
   *
   * @param key   the key of the node.
   * @param nodes the array of {@link SimpleKeyValuesNode}s.
   */
  public ComplexKeyValuesNode(String key, KeyValuesNode... nodes) {
    super(key);
    this.simpleKeyValuesNodes = new LinkedHashMap<String, List<SimpleKeyValuesNode>>();
    this.complexKeyValuesNodes = new LinkedHashMap<String, ComplexKeyValuesNode>();
    if (nodes != null) {
      for (KeyValuesNode node : nodes) {
        if (node instanceof SimpleKeyValuesNode) {
          SimpleKeyValuesNode simpleNode = (SimpleKeyValuesNode) node;
          add(simpleNode);
        }
        else if (node instanceof ComplexKeyValuesNode) {
          ComplexKeyValuesNode complexNode = (ComplexKeyValuesNode) node;
          add(complexNode);
        }
      }
    }
  }

  /**
   * Constructor.
   *
   * @param key   the key of the node.
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

    if (node != null) {
      this.complexKeyValuesNodes.remove(node.getKey());
      List<SimpleKeyValuesNode> existingNodes = this.simpleKeyValuesNodes.get(node.getKey());
      if (existingNodes == null) {
        List<SimpleKeyValuesNode> nodes = new ArrayList<SimpleKeyValuesNode>();
        nodes.add(node);
        this.simpleKeyValuesNodes.put(node.getKey(), nodes);
      } else {
        existingNodes.add(node);
      }
    }
    return node;
  }

  public ComplexKeyValuesNode add(ComplexKeyValuesNode node) {
    if (node != null) {
      this.simpleKeyValuesNodes.remove(node.getKey());
      ComplexKeyValuesNode existingNode = this.complexKeyValuesNodes.get(node.getKey());
      if (existingNode == null) {
        this.complexKeyValuesNodes.put(node.getKey(), node);
      } else {
        existingNode.merge(node);
      }
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
      } else {
        existingNodes.addAll(singleValueNodes.getValue());
      }
    }
  }

  /**
   * Returns the {@link KeyValuesNode} with the given key.
   * This will return either {@link SimpleKeyValuesNode} or
   * {@link ComplexKeyValuesNode} depend on the specified {@link Class}.
   * <p>
   * Return null if no {@link KeyValuesNode} with the given key is found.
   *
   * @param key   the key.
   * @param clazz the actual type of the {@link KeyValuesNode}.
   * @param <T>   sub type for {@link KeyValuesNode}
   * @return the {@link KeyValuesNode} with the given key. Null if it does not exist.
   */
  public <T extends KeyValuesNode> T get(String key, Class<T> clazz) {
    if (SimpleKeyValuesNode.class.equals(clazz)) {
      List<SimpleKeyValuesNode> nodes = this.simpleKeyValuesNodes.get(key);
      if (nodes != null) {
        List<String> values = new ArrayList<String>();
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
   * @return the {@link Set} of {@link SimpleKeyValuesNode} keys
   */
  public Set<String> getSimpleKeySet() {
    return Collections.unmodifiableSet(this.simpleKeyValuesNodes.keySet());
  }

  /**
   * @return the {@link Set} of {@link ComplexKeyValuesNode} keys
   */
  public Set<String> getComplexKeySet() {
    return Collections.unmodifiableSet(this.complexKeyValuesNodes.keySet());
  }

  /**
   * Checks if the section contains a key.
   *
   * @param key the key
   * @return whether the keys exists.
   */
  public boolean containsKey(String key) {
    return this.simpleKeyValuesNodes.containsKey(key) || this.complexKeyValuesNodes.containsKey(key);
  }

  /**
   * Returns the {@link KeyValuesNode} type of a given key.
   *
   * @param key the key
   * @return the {@link Class} of the {@link KeyValuesNode}. Null if key does not exist.
   */
  public Class<? extends KeyValuesNode> getNodeType(String key) {
    if (this.simpleKeyValuesNodes.containsKey(key)) {
      return SimpleKeyValuesNode.class;
    }
    else if (this.complexKeyValuesNodes.containsKey(key)) {
      return ComplexKeyValuesNode.class;
    }
    return null;
  }

  /**
   * @return true if the {@link SectionNode} is empty; false otherwise.
   */
  public boolean isEmpty() {
    return this.simpleKeyValuesNodes.isEmpty() && this.complexKeyValuesNodes.isEmpty();
  }

  /**
   * Remove all values in with the given key.
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
   * Clear all {@link SimpleKeyValuesNode}s and {@link ComplexKeyValuesNode}s.
   */
  public void clear() {
    this.simpleKeyValuesNodes.clear();
    this.complexKeyValuesNodes.clear();
  }

  @Override
  public String asString(int indent) {
    indent = indent < 0 ? 0 : indent;

    return StringUtils.repeat(INDENT_CHARACTERS, indent) +
            this.getKey() + " = {" + "\n" +
            nodesAsString(indent + 1) +
            StringUtils.repeat(INDENT_CHARACTERS, indent) +
            "}" + "\n";
  }

  /**
   * Returns the {@link String} representation of the all the sub {@link KeyValuesNode}s
   *
   * @param indent the initial indentation for this node
   * @return the {@link String} representation of the all the sub nodes
   */
  protected String nodesAsString(int indent) {
    StringBuilder sb = new StringBuilder();
    for (List<SimpleKeyValuesNode> simpleNodes : this.simpleKeyValuesNodes.values()) {
      for (SimpleKeyValuesNode simpleNode : simpleNodes) {
        sb.append(simpleNode.asString(indent));
      }
    }
    for (ComplexKeyValuesNode complexNode : this.complexKeyValuesNodes.values()) {
      sb.append(complexNode.asString(indent));
    }
    return sb.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    ComplexKeyValuesNode that = (ComplexKeyValuesNode) o;

    if (!simpleKeyValuesNodes.equals(that.simpleKeyValuesNodes)) return false;
    return complexKeyValuesNodes.equals(that.complexKeyValuesNodes);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + simpleKeyValuesNodes.hashCode();
    result = 31 * result + complexKeyValuesNodes.hashCode();
    return result;
  }
}
