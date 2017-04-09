package com.cepw.model.node;

import com.cepw.utils.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComplexKeyValuesNode extends Node {

  private Map<String, List<SimpleKeyValuesNode>> simpleKeyValuesNodes;

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

  public SimpleKeyValuesNode get(String key) {
    List<SimpleKeyValuesNode> nodes = this.simpleKeyValuesNodes.get(key);
    List<String> values = new ArrayList<>();
    for (SimpleKeyValuesNode node : nodes) {
      values.addAll(node.getAllValues());
    }
    return new SimpleKeyValuesNode(key, values);
  }

  public Map<String, List<SimpleKeyValuesNode>> getAllValues() {
    return this.simpleKeyValuesNodes;
  }

  public void clear() {
    this.simpleKeyValuesNodes.clear();
  }

  public SimpleKeyValuesNode remove(String key) {
    SimpleKeyValuesNode node = get(key);
    this.simpleKeyValuesNodes.remove(key);
    return node;
  }

  public String print(int indent) {
    indent = indent < 0 ? 0 : indent;

    StringBuilder sb = new StringBuilder();
    sb.append(StringUtils.repeat(INDENT_CHARACTER, indent));
    sb.append(key);
    sb.append(" = {").append("\n");
    for (Map.Entry<String, List<SimpleKeyValuesNode>> node : this.simpleKeyValuesNodes.entrySet()) {
      for (SimpleKeyValuesNode svn : node.getValue()) {
        sb.append(svn.print(indent+1));
      }
    }
    sb.append(StringUtils.repeat(INDENT_CHARACTER, indent));
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
