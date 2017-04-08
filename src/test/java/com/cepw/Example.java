package com.cepw;

import com.cepw.model.KrbConf;
import com.cepw.utils.ObjectUtils;
import java.io.File;

public class Example {

  public static void main(String[] args) {
    File krb5Conf = new File("krb5.conf");
    try {
      KrbConf conf = KrbConfParser.parse(krb5Conf);
      System.out.println("Object Size: " + ObjectUtils.serialize(conf).length);
      System.out.println(conf);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}
