package com.cepw.model.node.section;

import com.cepw.model.node.SectionNode;

/**
 * The class representing the [appdefaults] section.
 * Section specific logic goes here.
 */
public class AppDefaultsSection extends SectionNode {

  /**
   * The section name.
   */
  public static final String SECTION_NAME = "appdefaults";
  /**
   * The {@code serialVersionUID}
   */
  private static final long serialVersionUID = -4586814915221769576L;

  /**
   * Constructor.
   */
  public AppDefaultsSection() {
    super(SECTION_NAME);
  }
}
