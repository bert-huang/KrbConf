package com.cepw.model.node.section;

import com.cepw.model.node.SectionNode;

/**
 * The class representing the [logging] section.
 * Section specific logic goes here.
 */
public class LoggingSection extends SectionNode {

  /**
   * The {@code serialVersionUID}
   */
  private static final long serialVersionUID = 2011330182756433131L;

  /**
   * The section name.
   */
  public static final String SECTION_NAME = "logging";

  /**
   * Constructor.
   */
  public LoggingSection() {
    super(SECTION_NAME);
  }

  @Override
  public String getSectionName() {
    return SECTION_NAME;
  }
}
