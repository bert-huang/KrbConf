package com.cepw.model.node.section;

import com.cepw.model.node.SectionNode;

/**
 * The class representing the [libdefaults] section.
 * Section specific logic goes here.
 */
public class LibDefaultsSection extends SectionNode {

  /**
   * The section name.
   */
  public static final String KEY = "libdefaults";
  /**
   * The {@code serialVersionUID}
   */
  private static final long serialVersionUID = -2294810939675626670L;

  /**
   * Constructor.
   */
  public LibDefaultsSection() {
    super(KEY);
  }
}
