package com.cepw.model.node.section;

import com.cepw.model.node.SectionNode;

/**
 * The class representing the [domain_realm] section.
 * Section specific logic goes here.
 */
public class DomainRealmSection extends SectionNode {

  /**
   * The section name.
   */
  public static final String KEY = "domain_realm";
  /**
   * The {@code serialVersionUID}
   */
  private static final long serialVersionUID = -5972912363955939910L;

  /**
   * Constructor.
   */
  public DomainRealmSection() {
    super(KEY);
  }
}
