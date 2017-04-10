package com.cepw.model.node;

import java.util.ArrayList;
import java.util.List;

import com.cepw.utils.StringUtils;

/**
 * A node representing a simple key-value entry.
 * A simple key-value node can hold multiple {@link String} values.
 */
public class SimpleKeyValuesNode extends KeyValuesNode {

  /**
   * The {@code serialVersionUID}
   */
  private static final long serialVersionUID = -1326720630146308942L;

  /**
   * The {@link List} of values.
   * krb.conf simple key value can be in space separated values.
   * This {@link List} is used to store each individual value.
   */
  protected List<String> values;

  /**
   * Constructor.
   *
   * @param key   the key of the node.
   * @param vargs the array of values.
   */
  public SimpleKeyValuesNode(String key, String... vargs) {
    super(key);
    List<String> values = new ArrayList<String>();
    if (vargs != null) {
      for (String value : vargs) {
        values.add(value.trim());
      }
    }
    this.values = values;
  }

  /**
   * Constructor.
   *
   * @param key    the key of the node.
   * @param values the {@link List} of values.
   */
  public SimpleKeyValuesNode(String key, List<String> values) {
    this(key, values.toArray(new String[values.size()]));
  }

  /**
   * Adds a value to the node.
   *
   * @param value the value to add.
   */
  public String add(String value) {
    this.values.add(value);
    return value;
  }

  /**
   * Removes a value from the node.
   *
   * @param value the value to remove.
   */
  public String remove(String value) {
    this.values.remove(value);
    return value;
  }

  /**
   * Clear all the values from the node
   */
  public void clear() {
    this.values.clear();
  }

  /**
   * @return the {@link List} of values of the node
   */
  public List<String> getValues() {
    return values;
  }

  /**
   * @return the {@link List} of space separated values in {@link String}s
   */
  public String getValuesAsString() {
    return StringUtils.join(values, " ");
  }

  /**
   * Returns the underlying ADT holding the information about
   * this node.
   *
   * @return the {@link List} holding values of the {@link SimpleKeyValuesNode}
   */
  public List<String> getRawData() {
    return values;
  }

  @Override
  public String asString(int indent) {
    StringBuilder sb = new StringBuilder();
    sb.append(StringUtils.repeat(INDENT_CHARACTERS, indent));
    sb.append(this.getKey());
    sb.append(" =");
    for (String value : values) {
      sb.append(" ");
      sb.append(value);
    }
    sb.append("\n");
    return sb.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    SimpleKeyValuesNode that = (SimpleKeyValuesNode) o;

    return values.equals(that.values);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + values.hashCode();
    return result;
  }
}
