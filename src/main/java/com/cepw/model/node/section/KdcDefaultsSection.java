package com.cepw.model.node.section;

import com.cepw.model.node.SectionNode;

/**
 * The class representing the [kdcdefaults] section.
 * Section specific logic goes here.
 */
public class KdcDefaultsSection extends SectionNode {

  /**
   * The section name.
   */
  public static final String KEY = "kdcdefaults";
  /**
   * The {@code serialVersionUID}
   */
  private static final long serialVersionUID = 4132718916086399300L;

  /**
   * Constructor.
   */
  public KdcDefaultsSection() {
    super(KEY);
  }
}