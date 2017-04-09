package com.cepw.model.node.section;

import com.cepw.model.node.SectionNode;

/**
 * The class representing the [plugins] section.
 * Section specific logic goes here.
 */
public class PluginsSection extends SectionNode {

  /**
   * The {@code serialVersionUID}
   */
  private static final long serialVersionUID = -226192210700314882L;

  /**
   * The section name.
   */
  public static final String SECTION_NAME = "plugins";

  /**
   * Constructor.
   */
  public PluginsSection() {
    super(SECTION_NAME);
  }

  @Override
  public String getSectionName() {
    return SECTION_NAME;
  }
}
