package com.cepw.model.node.section;

import com.cepw.model.node.SectionNode;

/**
 * The class representing the [dbmodules] section.
 * Section specific logic goes here.
 */
public class DbModulesSection extends SectionNode {

  /**
   * The section name.
   */
  public static final String KEY = "dbmodules";
  /**
   * The {@code serialVersionUID}
   */
  private static final long serialVersionUID = -5448790636775131416L;

  /**
   * Constructor.
   */
  public DbModulesSection() {
    super(KEY);
  }
}
