package com.cepw;

import com.cepw.model.KrbConf;
import com.cepw.model.node.ComplexKeyValuesNode;
import com.cepw.model.node.SimpleKeyValuesNode;
import com.cepw.utils.ObjectUtils;
import java.io.File;

public class Example {

  public static void main(String[] args) {
    try {
      System.out.println("=== Empty KrbConf ===");
      KrbConf emptyConf = new KrbConf();
      System.out.println("Object Size: " + ObjectUtils.serialize(emptyConf).length);
      System.out.println(emptyConf);
      System.out.println();

      System.out.println("=== New KrbConf ===");
      KrbConf newConf = new KrbConf();
      newConf.getLibDefaults().add(new SimpleKeyValuesNode("default_realm", "CEPW.CO.NZ"));
      newConf.getDomainRealm().add(new SimpleKeyValuesNode("cepw.co.nz", "CEPW.CO.NZ"));
      newConf.getDomainRealm().add(new SimpleKeyValuesNode("cepw.co.nz", "CEPW.CO.NZ"));
      newConf.getRealms().add(new ComplexKeyValuesNode("CEPW.CO.NZ",
              new SimpleKeyValuesNode("master_kdc", "cepw-alpha.domain.co.nz"),
              new SimpleKeyValuesNode("admin_server", "cepw-gamma.domain.co.nz"),
              new SimpleKeyValuesNode("kdc", "cepw-alpha.domain.co.nz"),
              new SimpleKeyValuesNode("kdc", "cpew-beta.domain.co.nz")));

      newConf.getRealms().add(new ComplexKeyValuesNode("CEPW.CO.NZ",
              new SimpleKeyValuesNode("kdc", "cpew-omega.domain.co.nz"),
              new SimpleKeyValuesNode("default_domain", "mit.edu")));
      newConf.getDomainRealm().remove("cepw.co.nz");
      System.out.println("Object Size: " + ObjectUtils.serialize(newConf).length);
      System.out.println(newConf);
      System.out.println();

      System.out.println("=== Parsed KrbConf ===");
      File krb5Conf = new File("krb5.conf");
      KrbConf conf = KrbConfParser.parse(krb5Conf);
      System.out.println("Object Size: " + ObjectUtils.serialize(conf).length);
      System.out.println(conf);
      System.out.println("---");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}
