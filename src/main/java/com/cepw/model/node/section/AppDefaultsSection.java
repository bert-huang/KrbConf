package com.cepw.model.node.section;

import com.cepw.model.node.SectionNode;

/**
 * The class representing the [appdefaults] section.
 * Section specific logic goes here.
 */
public class AppDefaultsSection extends SectionNode {

  /**
   * The {@code serialVersionUID}
   */
  private static final long serialVersionUID = -4586814915221769576L;

  /**
   * The section name.
   */
  public static final String SECTION_NAME = "appdefaults";

  /**
   * Constructor.
   */
  public AppDefaultsSection() {
    super(SECTION_NAME);
  }

  @Override
  public String getSectionName() {
    return SECTION_NAME;
  }
}
