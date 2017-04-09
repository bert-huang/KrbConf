package com.cepw.model.node.section;

import com.cepw.model.node.SectionNode;

/**
 * The class representing the [plugins] section.
 * Section specific logic goes here.
 */
public class PluginsSection extends SectionNode {

  /**
   * The section name.
   */
  public static final String KEY = "plugins";
  /**
   * The {@code serialVersionUID}
   */
  private static final long serialVersionUID = -226192210700314882L;

  /**
   * Constructor.
   */
  public PluginsSection() {
    super(KEY);
  }
}
