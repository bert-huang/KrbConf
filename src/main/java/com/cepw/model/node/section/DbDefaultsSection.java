package com.cepw.model.node.section;

import com.cepw.model.node.SectionNode;

/**
 * The class representing the [dbdefaults] section.
 * Section specific logic goes here.
 */
public class DbDefaultsSection extends SectionNode {

  /**
   * The {@code serialVersionUID}
   */
  private static final long serialVersionUID = -8829368574796828279L;

  /**
   * The section name.
   */
  public static final String SECTION_NAME = "dbdefaults";

  /**
   * Constructor.
   */
  public DbDefaultsSection() {
    super(SECTION_NAME);
  }

  @Override
  public String getSectionName() {
    return SECTION_NAME;
  }
}
