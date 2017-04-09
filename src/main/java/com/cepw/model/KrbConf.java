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
import java.util.Collections;
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

  /**
   * A {@link Map} holding all possible {@link SectionNode} and the corresponding {@link Class}.
   */
  public static final Map<String, Class<? extends SectionNode>> SECTION_CLASSES;

  static {
    Map<String, Class<? extends SectionNode>> sections = new HashMap<>();
    sections.put(AppDefaultsSection.KEY, AppDefaultsSection.class);
    sections.put(CAPathsSection.KEY, CAPathsSection.class);
    sections.put(DbDefaultsSection.KEY, DbDefaultsSection.class);
    sections.put(DbModulesSection.KEY, DbModulesSection.class);
    sections.put(DomainRealmSection.KEY, DomainRealmSection.class);
    sections.put(KdcDefaultsSection.KEY, KdcDefaultsSection.class);
    sections.put(LibDefaultsSection.KEY, LibDefaultsSection.class);
    sections.put(LoggingSection.KEY, LoggingSection.class);
    sections.put(LoginSection.KEY, LoginSection.class);
    sections.put(PluginsSection.KEY, PluginsSection.class);
    sections.put(RealmsSection.KEY, RealmsSection.class);

    SECTION_CLASSES = Collections.unmodifiableMap(sections);
  }

  /**
   * The {@link SectionNode}s
   */
  private Map<Class<? extends SectionNode>, SectionNode> sections;

  /**
   * Constructor.
   * <p>
   * Initialises all possible sections.
   */
  public KrbConf() {
    sections = new LinkedHashMap<>();
  }

  /**
   * @return the {@link AppDefaultsSection}
   */
  public AppDefaultsSection getAppDefaults() {
    return getSection(AppDefaultsSection.class);
  }

  /**
   * @return the {@link CAPathsSection}
   */
  public CAPathsSection getCAPaths() {
    return getSection(CAPathsSection.class);
  }

  /**
   * @return the {@link DbDefaultsSection}
   */
  public DbDefaultsSection getDbDefaults() {
    return getSection(DbDefaultsSection.class);
  }

  /**
   * @return the {@link DbModulesSection}
   */
  public DbModulesSection getDbModules() {
    return getSection(DbModulesSection.class);
  }

  /**
   * @return the {@link DomainRealmSection}
   */
  public DomainRealmSection getDomainRealm() {
    return getSection(DomainRealmSection.class);
  }

  /**
   * @return the {@link KdcDefaultsSection}
   */
  public KdcDefaultsSection getKdcDefaults() {
    return getSection(KdcDefaultsSection.class);
  }

  /**
   * @return the {@link LibDefaultsSection}
   */
  public LibDefaultsSection getLibDefaults() {
    return getSection(LibDefaultsSection.class);
  }

  /**
   * @return the {@link LoggingSection}
   */
  public LoggingSection getLogging() {
    return getSection(LoggingSection.class);
  }

  /**
   * @return the {@link LoginSection}
   */
  public LoginSection getLogin() {
    return getSection(LoginSection.class);
  }

  /**
   * @return the {@link PluginsSection}
   */
  public PluginsSection getPlugins() {
    return getSection(PluginsSection.class);
  }

  /**
   * @return the {@link RealmsSection}
   */
  public RealmsSection getRealms() {
    return getSection(RealmsSection.class);
  }

  /**
   * Create a {@link SectionNode} with the given sub {@link Class}.
   *
   * @param clazz the type of {@link SectionNode} to get.
   * @return the {@link SectionNode} with the given key.
   * Null if an invalid key for the {@link SectionNode} is specified.
   */
  public <T extends SectionNode> T getSection(Class<T> clazz) {

    try {
      if (clazz != null) {
        SectionNode section = sections.get(clazz);
        if (section == null) {
          section = clazz.newInstance();
          sections.put(clazz, section);
        }
        return clazz.cast(section);
      }
    } catch (IllegalAccessException | InstantiationException e) {
      /* Nothing we can do. */
    }
    return null;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (SectionNode section : sections.values()) {
      sb.append(section.toString(0));
    }
    return sb.toString();
  }
}
