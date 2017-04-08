package com.cepw.model;

import com.cepw.model.node.section.AppDefaults;
import com.cepw.model.node.section.CAPaths;
import com.cepw.model.node.section.DomainRealm;
import com.cepw.model.node.section.LibDefaults;
import com.cepw.model.node.section.Logging;
import com.cepw.model.node.section.Login;
import com.cepw.model.node.section.Realms;
import com.cepw.model.node.section.Section;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class KrbConf implements Serializable {

  private static final long serialVersionUID = 6282488388607321323L;

  private Map<String, Section> sections;

  private LibDefaults libDefaults;

  private DomainRealm domainRealm;

  private Realms realms;

  private Login login;

  private Logging logging;

  private AppDefaults appDefaults;

  private CAPaths caPaths;

  public KrbConf() {

    sections = new HashMap<>();
    sections.put(LibDefaults.SECTION, new LibDefaults());
    sections.put(DomainRealm.SECTION, new DomainRealm());
    sections.put(Realms.SECTION, new Realms());
    sections.put(Login.SECTION, new Login());
    sections.put(Logging.SECTION, new Logging());
    sections.put(AppDefaults.SECTION, new AppDefaults());
    sections.put(CAPaths.SECTION, new CAPaths());
  }

  public LibDefaults getLibDefaults() {
    return (LibDefaults) sections.get(LibDefaults.SECTION);
  }

  public DomainRealm getDomainRealm() {
    return (DomainRealm) sections.get(DomainRealm.SECTION);
  }

  public Realms getRealms() {
    return (Realms) sections.get(Realms.SECTION);
  }

  public Login getLogin() {
    return (Login) sections.get(Login.SECTION);
  }

  public Logging getLogging() {
    return (Logging) sections.get(Logging.SECTION);
  }

  public AppDefaults getAppDefaults() {
    return (AppDefaults) sections.get(AppDefaults.SECTION);
  }

  public CAPaths getCAPaths() {
    return (CAPaths) sections.get(CAPaths.SECTION);
  }

  public Section getSection(String key) {
    return sections.get(key);
  }

  public String print() {
    return null;
  }
}
