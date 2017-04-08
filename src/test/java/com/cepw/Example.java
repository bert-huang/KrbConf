package com.cepw;

import com.cepw.model.KrbConf;
import com.cepw.model.node.value.MultiValueNode;
import com.cepw.model.node.value.SingleValueNode;
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
      newConf.getLibDefaults().add(new SingleValueNode("default_realm", "CEPW.CO.NZ"));
      newConf.getDomainRealm().add(new SingleValueNode("cepw.co.nz", "CEPW.CO.NZ"));
      newConf.getDomainRealm().add(new SingleValueNode("cepw.co.nz", "CEPW.CO.NZ"));
      newConf.getRealms().add(new MultiValueNode("CEPW.CO.NZ",
              new SingleValueNode("master_kdc", "cepw-alpha.domain.co.nz"),
              new SingleValueNode("admin_server", "cepw-gamma.domain.co.nz"),
              new SingleValueNode("kdc", "cepw-alpha.domain.co.nz"),
              new SingleValueNode("kdc", "cpew-beta.domain.co.nz")));

      newConf.getRealms().add(new MultiValueNode("CEPW.CO.NZ",
              new SingleValueNode("kdc", "cpew-omega.domain.co.nz"),
              new SingleValueNode("default_domain", "mit.edu")));
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
