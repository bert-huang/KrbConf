package com.cepw;

import com.cepw.exception.KrbConfParseException;
import com.cepw.model.KrbConf;
import com.cepw.model.node.section.Section;
import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KrbConfParser {

  private static final Pattern SECTION_NAME_PATTERN = Pattern.compile("^\\s*\\[([A-Za-z_]+)]\\s*$");
  private static final Pattern SINGLE_VALUED_VAR_PATTERN = Pattern.compile("^\\s*([^\\s=]+)\\s*=\\s*([[\\S]+\\s*]+[\\S]+)\\s*(?<!\\{)$");
  private static final Pattern MULTI_VALUED_VAR_START_PATTERN = Pattern.compile("^\\s*([^\\s=]+)\\s*=\\s*\\{\\s*$");
  private static final Pattern MULTI_VALUED_VAR_END_PATTERN = Pattern.compile("^\\s*}\\s*$");

  private enum Scope {
    SECTION, MULTI
  }

  public static KrbConf parse(String file) throws KrbConfParseException {
    return parse(new File(file));
  }

  public static KrbConf parse(File file) throws KrbConfParseException {

    try {
      KrbConf krbConf = new KrbConf();
      LineNumberReader lr = new LineNumberReader(new FileReader(file));

      String line;
      Section section = null;
      Map<String, Object> multi = null;
      Scope scope = null;
      while ((line = lr.readLine()) != null) {

        line = line.trim();

        /* Ignore blank or commented lines */
        if (line.isEmpty() || line.startsWith("#")) {
          continue;
        }

        Matcher matcher;

        /* 1. Section Block */
        matcher = SECTION_NAME_PATTERN.matcher(line);
        if (matcher.matches()) {

          if (Scope.MULTI.equals(scope)) {
            String errorMsg = "Unexpected termination of multi value variable at line: " + lr.getLineNumber();
            throw new KrbConfParseException(errorMsg);
          }
          scope = Scope.SECTION;
          String sectionName = matcher.group(1).trim();
          section = krbConf.getSection(sectionName);
          continue;
        }
        if (section == null) {
          String errorMsg = "Definition before section. Error at line: " + lr.getLineNumber();
          throw new KrbConfParseException(errorMsg);
        }

        /* 2. Single valued variable */
        matcher = SINGLE_VALUED_VAR_PATTERN.matcher(line);
        if (matcher.matches()) {
          String key = matcher.group(1).trim();
          String val = matcher.group(2).trim();
          String[] vals = val.replaceAll("\\s+", " ").split(" ");
          List<String> obj = new ArrayList<>(Arrays.asList(vals));

          Map<String, Object> collection;
          switch (scope) {
            case SECTION:
              collection = section.getEntries();
              break;
            case MULTI:
              collection = multi;
              break;
            default:
              throw new IllegalStateException("Unknown scope: " + scope);
          }

          Object extObj = collection.get(key);
          if (extObj instanceof List) {
            List<List<String>> lst = (List<List<String>>) extObj;
            lst.add(obj);
            collection.put(key, lst);
          }
          else if (extObj instanceof Map) {
            String errorMsg = "Cannot convert multi value variable single value variable. Error at line: " + lr.getLineNumber();
            throw new KrbConfParseException(errorMsg);
          }
          else {
            List<List<String>> lst = new ArrayList<>();
            lst.add(obj);
            collection.put(key, lst);
          }
          continue;
        }

        /* 3. Multi valued variable (start) */
        matcher = MULTI_VALUED_VAR_START_PATTERN.matcher(line);
        if (matcher.matches()) {

          if (Scope.MULTI.equals(scope)) {
            String errorMsg = "Unsupported nesting of multi value variables. Error at line: " + lr.getLineNumber();
            throw new KrbConfParseException(errorMsg);
          }

          String key = matcher.group(1).trim();
          Object extObj = section.getEntries().get(key);
          /* If a single value var exists, throw error */
          if (extObj instanceof List) {
            String errorMsg = "Cannot convert single value variable to multi value variable. Error at line: " + lr.getLineNumber();
            throw new KrbConfParseException(errorMsg);
          }
          /* If an multi value var with the same key exists, use the existing var */
          else if (extObj instanceof Map) {
            multi = (Map<String, Object>)extObj;
          }
          /* Create a new multi value var */
          else {
            multi = new HashMap<>();
          }

          section.getEntries().put(key, multi);
          scope = Scope.MULTI;
          continue;
        }

        /* 4. Multi valued variable (end) */
        matcher = MULTI_VALUED_VAR_END_PATTERN.matcher(line);
        if (matcher.matches()) {
          multi = null;
          scope = Scope.SECTION;
          continue;
        }

        throw new KrbConfParseException("Unrecognisable pattern. Error at line: " + lr.getLineNumber());
      }
      return krbConf;
    }
    catch (KrbConfParseException e) {
      throw e;
    }
    catch (Exception e) {
      throw new KrbConfParseException(e);
    }
  }
}
