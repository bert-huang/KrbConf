package com.cepw.model;

import com.cepw.model.node.section.AppDefaults;
import com.cepw.model.node.section.CAPaths;
import com.cepw.model.node.section.DomainRealm;
import com.cepw.model.node.section.LibDefaults;
import com.cepw.model.node.section.Logging;
import com.cepw.model.node.section.Login;
import com.cepw.model.node.section.Realms;

public class KrbConf {

  private LibDefaults libDefaults;

  private DomainRealm domainRealm;

  private Realms realms;

  private Login login;

  private Logging logging;

  private AppDefaults appDefaults;

  private CAPaths caPaths;

  public KrbConf() {
    this.libDefaults  = new LibDefaults();
    this.domainRealm  = new DomainRealm();
    this.realms       = new Realms();
    this.login        = new Login();
    this.logging      = new Logging();
    this.appDefaults  = new AppDefaults();
    this.caPaths      = new CAPaths();
  }

  public LibDefaults getLibDefaults() {
    return libDefaults;
  }

  public DomainRealm getDomainRealm() {
    return domainRealm;
  }

  public Realms getRealms() {
    return realms;
  }

  public Login getLogin() {
    return login;
  }

  public Logging getLogging() {
    return logging;
  }

  public AppDefaults getAppDefaults() {
    return appDefaults;
  }

  public CAPaths getCAPaths() {
    return caPaths;
  }
}
