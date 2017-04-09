package com.cepw.model;

import com.cepw.model.node.section.AppDefaults;
import com.cepw.model.node.section.CAPaths;
import com.cepw.model.node.section.DomainRealm;
import com.cepw.model.node.section.LibDefaults;
import com.cepw.model.node.section.Logging;
import com.cepw.model.node.section.Login;
import com.cepw.model.node.section.Realms;
import com.cepw.model.node.SectionNode;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class KrbConf implements Serializable {

  private static final long serialVersionUID = 6282488388607321323L;

  private Map<String, SectionNode> sections;

  public KrbConf() {
    sections = new HashMap<>();
    sections.put(LibDefaults.SECTION_NAME, new LibDefaults());
    sections.put(DomainRealm.SECTION_NAME, new DomainRealm());
    sections.put(Realms.SECTION_NAME, new Realms());
    sections.put(Login.SECTION_NAME, new Login());
    sections.put(Logging.SECTION_NAME, new Logging());
    sections.put(AppDefaults.SECTION_NAME, new AppDefaults());
    sections.put(CAPaths.SECTION_NAME, new CAPaths());
  }

  public LibDefaults getLibDefaults() {
    return (LibDefaults) sections.get(LibDefaults.SECTION_NAME);
  }

  public DomainRealm getDomainRealm() {
    return (DomainRealm) sections.get(DomainRealm.SECTION_NAME);
  }

  public Realms getRealms() {
    return (Realms) sections.get(Realms.SECTION_NAME);
  }

  public Login getLogin() {
    return (Login) sections.get(Login.SECTION_NAME);
  }

  public Logging getLogging() {
    return (Logging) sections.get(Logging.SECTION_NAME);
  }

  public AppDefaults getAppDefaults() {
    return (AppDefaults) sections.get(AppDefaults.SECTION_NAME);
  }

  public CAPaths getCAPaths() {
    return (CAPaths) sections.get(CAPaths.SECTION_NAME);
  }

  public SectionNode getSection(String key) {
    return sections.get(key);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Map.Entry<String, SectionNode> section : sections.entrySet()) {
      sb.append(section.getValue().print(0));
    }
    return sb.toString();
  }
}
