package com.cepw.model.node;

import com.cepw.utils.StringUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SectionNode extends Node implements Serializable {

  private static final long serialVersionUID = 2656840740346128646L;

  protected Map<String, List<SimpleKeyValuesNode>> simpleKeyValuesNodes;

  protected Map<String, ComplexKeyValuesNode> complexKeyValuesNodes;

  public SectionNode(String key) {
    super(key);
    this.simpleKeyValuesNodes = new HashMap<>();
    this.complexKeyValuesNodes = new HashMap<>();
  }

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

  public ComplexKeyValuesNode add(ComplexKeyValuesNode node) {
    ComplexKeyValuesNode existingNode = this.complexKeyValuesNodes.get(node.getKey());
    if (existingNode == null) {
      this.complexKeyValuesNodes.put(node.getKey(), node);
    }

    /* Merge the two nodes */
    else {
      existingNode.merge(node);
    }
    return node;
  }

  public void remove(String key) {
    this.simpleKeyValuesNodes.remove(key);
    this.complexKeyValuesNodes.remove(key);
  }

  public void clear() {
    this.simpleKeyValuesNodes.clear();
    this.complexKeyValuesNodes.clear();
  }

  public SimpleKeyValuesNode getSimpleKeyValuesNode(String key) {
    List<SimpleKeyValuesNode> nodes = this.simpleKeyValuesNodes.get(key);
    List<String> values = new ArrayList<>();
    for (SimpleKeyValuesNode node : nodes) {
      values.addAll(node.getAllValues());
    }
    return new SimpleKeyValuesNode(key, values);
  }

  public ComplexKeyValuesNode getComplexKeyValuesNode(String key) {
    return this.complexKeyValuesNodes.get(key);
  }

  public boolean isEmpty() {
    return this.simpleKeyValuesNodes.isEmpty() && this.complexKeyValuesNodes.isEmpty();
  }

  @Override
  public String print(int indent) {
    StringBuilder sb = new StringBuilder();
    if (!isEmpty()) {
      sb.append(StringUtils.repeat(INDENT_CHARACTER, indent));
      sb.append("[").append(key).append("]").append("\n");

      for (Map.Entry<String, List<SimpleKeyValuesNode>> svns : this.simpleKeyValuesNodes.entrySet()) {
        for (SimpleKeyValuesNode svn : svns.getValue()) {
          sb.append(svn.print(indent + 1));
        }
      }

      for (Map.Entry<String, ComplexKeyValuesNode> mvns : this.complexKeyValuesNodes.entrySet()) {
        sb.append(mvns.getValue().print(indent + 1));
      }
      sb.append("\n");
    }
    return sb.toString();
  }
}
