package com.cepw.model.node.section;

import com.cepw.model.node.SectionNode;

/**
 * The class representing the [logging] section.
 * Section specific logic goes here.
 */
public class LoggingSection extends SectionNode {

  /**
   * The section name.
   */
  public static final String KEY = "logging";
  /**
   * The {@code serialVersionUID}
   */
  private static final long serialVersionUID = 2011330182756433131L;

  /**
   * Constructor.
   */
  public LoggingSection() {
    super(KEY);
  }
}
