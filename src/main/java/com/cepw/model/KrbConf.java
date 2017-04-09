package com.cepw.model;

import com.cepw.model.node.SectionNode;
import com.cepw.model.node.section.AppDefaultsSection;
import com.cepw.model.node.section.CAPathsSection;
import com.cepw.model.node.section.DbDefaultsSection;
import com.cepw.model.node.section.DbModulesSection;
import com.cepw.model.node.section.DomainRealmSection;
import com.cepw.model.node.section.KdcDefaultsSection;
import com.cepw.model.node.section.LibDefaultsSection;
import com.cepw.model.node.section.LoggingSection;
import com.cepw.model.node.section.LoginSection;
import com.cepw.model.node.section.PluginsSection;
import com.cepw.model.node.section.RealmsSection;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The class representing the Kerberos configuration.
 */
public class KrbConf implements Serializable {

  /**
   * The {@code serialVersionUID}
   */
  private static final long serialVersionUID = 6282488388607321323L;

  private static Map<String, Class> sectionClasses;

  static {
    sectionClasses = new HashMap<>();
    sectionClasses.put(LibDefaultsSection.SECTION_NAME ,AppDefaultsSection.class);
    sectionClasses.put(CAPathsSection.SECTION_NAME     ,CAPathsSection.class);
    sectionClasses.put(DbDefaultsSection.SECTION_NAME  ,DbDefaultsSection.class);
    sectionClasses.put(DbModulesSection.SECTION_NAME   ,DbModulesSection.class);
    sectionClasses.put(DomainRealmSection.SECTION_NAME ,DomainRealmSection.class);
    sectionClasses.put(KdcDefaultsSection.SECTION_NAME ,KdcDefaultsSection.class);
    sectionClasses.put(LibDefaultsSection.SECTION_NAME ,LibDefaultsSection.class);
    sectionClasses.put(LoggingSection.SECTION_NAME     ,LoggingSection.class);
    sectionClasses.put(LoginSection.SECTION_NAME       ,LoginSection.class);
    sectionClasses.put(PluginsSection.SECTION_NAME     ,PluginsSection.class);
    sectionClasses.put(RealmsSection.SECTION_NAME      ,RealmsSection.class);
  }

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
    sections = new LinkedHashMap<>();
  }

  /**
   * @return the {@link AppDefaultsSection}
   */
  public AppDefaultsSection getAppDefaults() {
    return getSection(AppDefaultsSection.SECTION_NAME);
  }

  /**
   * @return the {@link CAPathsSection}
   */
  public CAPathsSection getCAPaths() {
    return getSection(CAPathsSection.SECTION_NAME);
  }

  /**
   * @return the {@link DbDefaultsSection}
   */
  public DbDefaultsSection getDbDefaults() {
    return getSection(DbDefaultsSection.SECTION_NAME);
  }

  /**
   * @return the {@link DbModulesSection}
   */
  public DbModulesSection getDbModules() {
    return getSection(DbModulesSection.SECTION_NAME);
  }

  /**
   * @return the {@link DomainRealmSection}
   */
  public DomainRealmSection getDomainRealm() {
    return getSection(DomainRealmSection.SECTION_NAME);
  }

  /**
   * @return the {@link KdcDefaultsSection}
   */
  public KdcDefaultsSection getKdcDefaults() {
    return getSection(KdcDefaultsSection.SECTION_NAME);
  }

  /**
   * @return the {@link LibDefaultsSection}
   */
  public LibDefaultsSection getLibDefaults() {
    return getSection(LibDefaultsSection.SECTION_NAME);
  }

  /**
   * @return the {@link LoggingSection}
   */
  public LoggingSection getLogging() {
    return (LoggingSection) sections.get(LoggingSection.SECTION_NAME);
  }

  /**
   * @return the {@link LoginSection}
   */
  public LoginSection getLogin() {
    return getSection(LoginSection.SECTION_NAME);
  }

  /**
   * @return the {@link PluginsSection}
   */
  public PluginsSection getPlugins() {
    return getSection(PluginsSection.SECTION_NAME);
  }

  /**
   * @return the {@link RealmsSection}
   */
  public RealmsSection getRealms() {
    return getSection(RealmsSection.SECTION_NAME);
  }

  /**
   * Create a {@link SectionNode} with the given sub {@link Class}.
   *
   * @param key the type of {@link SectionNode} to create.
   * @param <T> the sub class of {@link SectionNode}
   * @return the {@link SectionNode} with the given key.
   * Null if an invalid key for the {@link SectionNode} is specified.
   */
  public <T extends SectionNode> T getSection(String key) {

    try {
      Class<T> clazz = sectionClasses.get(key);
      SectionNode section = sections.get(key);

      if (clazz != null) {
        if (section == null) {
          section = clazz.newInstance();
          sections.put(key, section);
        }
        return clazz.cast(section);
      }
    }
    catch (InstantiationException | IllegalAccessException e) {
      /* Nothing we can do. */
    }
    return null;
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
