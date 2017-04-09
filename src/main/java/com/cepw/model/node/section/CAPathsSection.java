package com.cepw.model.node.section;

import com.cepw.model.node.SectionNode;

/**
 * The class representing the [capaths] section.
 * Section specific logic goes here.
 */
public class CAPathsSection extends SectionNode {

  /**
   * The section name.
   */
  public static final String KEY = "capaths";
  /**
   * The {@code serialVersionUID}
   */
  private static final long serialVersionUID = 4422439644551314591L;

  /**
   * Constructor.
   */
  public CAPathsSection() {
    super(KEY);
  }
}
