package com.cepw.model.node.section;

import com.cepw.model.node.SectionNode;

/**
 * The class representing the [dbdefaults] section.
 * Section specific logic goes here.
 */
public class DbDefaultsSection extends SectionNode {

  /**
   * The section name.
   */
  public static final String KEY = "dbdefaults";
  /**
   * The {@code serialVersionUID}
   */
  private static final long serialVersionUID = -8829368574796828279L;

  /**
   * Constructor.
   */
  public DbDefaultsSection() {
    super(KEY);
  }
}
