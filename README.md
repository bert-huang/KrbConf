# Kerberos Configuration Tool
A simple java library to create, parse, and modify krb5.conf.

---
# How to use
## Create
```java
KrbConf krbConf = new KrbConf();
```

## Add/Update
```java
LibDefaultsSection libDefaults = krbConf.getSection(LibDefaultsSection.class);
libDefaults.add(new SimpleKeyValuesNode("default_realm", "example.com"));
libDefaults.add(new SimpleKeyValuesNode("default_tkt_enctypes", "des3-hmac-sha1", "des3-hmac-sha1"));

RealmsSection realms = krbConf.getSection(RealmsSection.class);
realms.add(new ComplexKeyValuesNode("EXAMPLE.COM", 
                new SimpleKeyValuesNode("master_kdc", "example-domain.controller1.com"),
                new SimpleKeyValuesNode("kdc", "example-domain.controller1.com"),
                new SimpleKeyValuesNode("kdc", "example-domain.controller2.com:750")
          )
);
```

## Get/Access
```java
LibDefaultsSection libDefaults = krbConf.getSection(LibDefaultsSection.class);
List<String> valuesAsList = libDefaults.get("default_realm", SimpleKeyValuesNode.class).getValues();
String valuesAsString = libDefaults.get("default_realm", SimpleKeyValuesNode.class).getValuesAsString();

RealmsSection realms = conf.getSection(RealmsSection.class);
ComplexKeyValuesNode complexNode = realms.get("EXAMPLE.COM", ComplexKeyValuesNode.class);
SimpleKeyValuesNode simpleNode = complexNode.get("kdc");
List<String> kdcs = simpleNode.getValues();
```

## Remove
```java
LibDefaultsSection libDefaults = krbConf.getSection(LibDefaultsSection.class);
libDefaults.remove("default_tkt_enctypes");

RealmsSection realms = krbConf.getSection(RealmsSection.class);
ComplexKeyValuesNode complexNode = realms.get("EXAMPLE.COM", ComplexKeyValuesNode.class);
complexNode.remove("master_kdc");
```

## Parse from file
```java
File inputFile = new File("path/to/krb5.conf");
KrbConf krbConf = KrbConfParser.parse(inputFile);
```

## Output to file
```java
KrbConf krbConf = ...
File outputFile = new File("path/of/output/krb5.conf");
try {
    BufferedWriter out = new BufferedWriter(new FileWriter(outputFile));
    out.write(krbConf.toString());
    out.close();
}
catch (IOException e) {
    // Do error handling.
}
```
