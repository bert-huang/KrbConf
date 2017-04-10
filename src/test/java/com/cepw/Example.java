package com.cepw;

import com.cepw.model.KrbConf;
import com.cepw.model.node.ComplexKeyValuesNode;
import com.cepw.model.node.SimpleKeyValuesNode;
import com.cepw.utils.ObjectUtils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Example {

  public static void main(String[] args) {
    try {
      KrbConf conf;

      System.out.println("=== Empty KrbConf ===");
      conf = new KrbConf();
      System.out.println("Object Size: " + ObjectUtils.serialize(conf).length);
      System.out.println(conf);
      System.out.println();

      System.out.println("=== New KrbConf ===");
      conf = new KrbConf();
      conf.getLibDefaults().add(new SimpleKeyValuesNode("default_realm", "CEPW.CO.NZ"));
      conf.getDomainRealm().add(new SimpleKeyValuesNode("cepw.co.nz", "CEPW.CO.NZ"));
      conf.getDomainRealm().add(new SimpleKeyValuesNode(".cepw.co.nz", "CEPW.CO.NZ"));
      conf.getRealms().add(new ComplexKeyValuesNode("CEPW.CO.NZ",
              new SimpleKeyValuesNode("master_kdc", "cepw-alpha.domain.co.nz"),
              new SimpleKeyValuesNode("admin_server", "cepw-gamma.domain.co.nz"),
              new SimpleKeyValuesNode("kdc", "cepw-alpha.domain.co.nz"),
              new SimpleKeyValuesNode("kdc", "cpew-beta.domain.co.nz")));
      conf.getRealms().add(new ComplexKeyValuesNode("CEPW.CO.NZ",
              new SimpleKeyValuesNode("kdc", "cpew-omega.domain.co.nz"),
              new SimpleKeyValuesNode("default_domain", "mit.edu")));
      System.out.println("Object Size: " + ObjectUtils.serialize(conf).length);
      System.out.println(conf);
      System.out.println();

      System.out.println("=== Parse Simple KrbConf ===");
      File simpleKrb5Conf = new File("krb5.simple.conf");
      conf = KrbConfParser.parse(simpleKrb5Conf);
      System.out.println("Object Size: " + ObjectUtils.serialize(conf).length);
      System.out.println(conf);
      System.out.println("---");

      System.out.println("=== Parse Simple KrbConf ===");
      File complexKrb5Conf = new File("krb5.complex.conf");
      conf = KrbConfParser.parse(complexKrb5Conf);
      System.out.println("Object Size: " + ObjectUtils.serialize(conf).length);
      System.out.println(conf);
      System.out.println("---");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}
