package com.cepw;

import com.cepw.exception.KrbConfParseException;
import com.cepw.model.KrbConf;
import java.io.File;
import java.net.URL;

public class Example {

  public static void main(String[] args) {
    ClassLoader classLoader = Example.class.getClassLoader();
    URL url = classLoader.getResource("krb5.conf");
    if (url != null) {
      File krb5Conf = new File(url.getFile());
      try {
        KrbConf conf = KrbConfParser.parse(krb5Conf);
        System.out.println(conf);
      }
      catch (KrbConfParseException e) {
        System.err.println(e.getMessage());
      }
    }
  }
}
