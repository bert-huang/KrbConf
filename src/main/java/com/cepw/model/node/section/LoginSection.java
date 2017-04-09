package com.cepw.model.node.section;

import com.cepw.model.node.SectionNode;

/**
 * The class representing the [login] section.
 * Section specific logic goes here.
 */
public class LoginSection extends SectionNode {

  /**
   * The section name.
   */
  public static final String KEY = "login";
  /**
   * The {@code serialVersionUID}
   */
  private static final long serialVersionUID = -7795233329451152953L;

  /**
   * Constructor.
   */
  public LoginSection() {
    super(KEY);
  }
}
