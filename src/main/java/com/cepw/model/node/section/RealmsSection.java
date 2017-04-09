package com.cepw.model.node.section;

import com.cepw.model.node.SectionNode;

/**
 * The class representing the [realms] section.
 * Section specific logic goes here.
 */
public class RealmsSection extends SectionNode {

  /**
   * The section name.
   */
  public static final String KEY = "realms";
  /**
   * The {@code serialVersionUID}
   */
  private static final long serialVersionUID = 6446171039240243377L;

  /**
   * Constructor.
   */
  public RealmsSection() {
    super(KEY);
  }
}
