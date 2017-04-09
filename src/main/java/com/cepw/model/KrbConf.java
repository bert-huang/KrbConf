package com.cepw.model;

import com.cepw.model.node.section.AppDefaultsSection;
import com.cepw.model.node.section.CAPathsSection;
import com.cepw.model.node.section.DomainRealmSection;
import com.cepw.model.node.section.LibDefaultsSection;
import com.cepw.model.node.section.LoggingSection;
import com.cepw.model.node.section.LoginSection;
import com.cepw.model.node.section.RealmsSection;
import com.cepw.model.node.SectionNode;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * The class representing the Kerberos configuration.
 */
public class KrbConf implements Serializable {

  /**
   * The {@code serialVersionUID}
   */
  private static final long serialVersionUID = 6282488388607321323L;

  /**
   * The {@link SectionNode}s
   */
  private Map<String, SectionNode> sections;

  /**
   * Constructor.
   *
   * Initialises all possible sections.
   */
  public KrbConf() {
    sections = new HashMap<>();
    sections.put(LibDefaultsSection.SECTION_NAME, new LibDefaultsSection());
    sections.put(DomainRealmSection.SECTION_NAME, new DomainRealmSection());
    sections.put(RealmsSection.SECTION_NAME, new RealmsSection());
    sections.put(LoginSection.SECTION_NAME, new LoginSection());
    sections.put(LoggingSection.SECTION_NAME, new LoggingSection());
    sections.put(AppDefaultsSection.SECTION_NAME, new AppDefaultsSection());
    sections.put(CAPathsSection.SECTION_NAME, new CAPathsSection());
  }

  /**
   * @return the {@link LibDefaultsSection}
   */
  public LibDefaultsSection getLibDefaults() {
    return (LibDefaultsSection) sections.get(LibDefaultsSection.SECTION_NAME);
  }

  /**
   * @return the {@link DomainRealmSection}
   */
  public DomainRealmSection getDomainRealm() {
    return (DomainRealmSection) sections.get(DomainRealmSection.SECTION_NAME);
  }

  /**
   * @return the {@link RealmsSection}
   */
  public RealmsSection getRealms() {
    return (RealmsSection) sections.get(RealmsSection.SECTION_NAME);
  }

  /**
   * @return the {@link LoginSection}
   */
  public LoginSection getLogin() {
    return (LoginSection) sections.get(LoginSection.SECTION_NAME);
  }

  /**
   * @return the {@link LoggingSection}
   */
  public LoggingSection getLogging() {
    return (LoggingSection) sections.get(LoggingSection.SECTION_NAME);
  }

  /**
   * @return the {@link AppDefaultsSection}
   */
  public AppDefaultsSection getAppDefaults() {
    return (AppDefaultsSection) sections.get(AppDefaultsSection.SECTION_NAME);
  }

  /**
   * @return the {@link CAPathsSection}
   */
  public CAPathsSection getCAPaths() {
    return (CAPathsSection) sections.get(CAPathsSection.SECTION_NAME);
  }

  /**
   * @return the {@link SectionNode} with the given key.
   * Null if an invalid key for the {@link SectionNode} is specified.
   */
  public SectionNode getSection(String key) {
    return sections.get(key);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Map.Entry<String, SectionNode> section : sections.entrySet()) {
      sb.append(section.getValue().toString(0));
    }
    return sb.toString();
  }
}
