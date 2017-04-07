package com.cepw;

import com.cepw.exception.KrbConfParseException;
import com.cepw.model.KrbConf;
import com.cepw.model.node.section.AppDefaults;
import com.cepw.model.node.section.CAPaths;
import com.cepw.model.node.section.DomainRealm;
import com.cepw.model.node.section.LibDefaults;
import com.cepw.model.node.section.Logging;
import com.cepw.model.node.section.Login;
import com.cepw.model.node.section.Realms;
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
          switch (sectionName) {
            case LibDefaults.SECTION:
              section = krbConf.getLibDefaults(); break;
            case DomainRealm.SECTION:
              section = krbConf.getDomainRealm(); break;
            case Realms.SECTION:
              section = krbConf.getRealms();      break;
            case Login.SECTION:
              section = krbConf.getLogin();       break;
            case Logging.SECTION:
              section = krbConf.getLogging();     break;
            case AppDefaults.SECTION:
              section = krbConf.getAppDefaults(); break;
            case CAPaths.SECTION:
              section = krbConf.getCAPaths();     break;
            default:
              String errorMsg = "Invalid section " + sectionName + " at line: " + lr.getLineNumber();
              throw new KrbConfParseException(errorMsg);
          }
          continue;
        }
        if (section == null) {
          String errorMsg = "Variable not within section scope at line: " + lr.getLineNumber();
          throw new KrbConfParseException(errorMsg);
        }

        /* 2. Single valued variable */
        matcher = SINGLE_VALUED_VAR_PATTERN.matcher(line);
        if (matcher.matches()) {
          String key = matcher.group(1).trim();
          String val = matcher.group(2).trim();
          String[] vals = val.replaceAll("\\s+", " ").split(" ");
          Object obj = vals.length == 1 ? val : Arrays.asList(vals);

          Map<String, Object> collection = null;
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
          /* Combine the string values into a list */
          if (extObj instanceof String) {
            String str = (String) extObj;

            List<String> lst = new ArrayList<>();
            lst.add(str);
            if (obj instanceof String) {
              lst.add((String) obj);
            }
            else if (obj instanceof List) {
              lst.addAll((List<String>) obj);
            }
            collection.put(key, lst);
          }

          /* Combine the new list with existing list */
          else if (extObj instanceof List) {
            List<String> lst = (List<String>) extObj;
            if (obj instanceof String) {
              lst.add((String) obj);
            }
            else if (obj instanceof List) {
              lst.addAll((List<String>) obj);
            }
            collection.put(key, lst);
          }
          else {
            collection.put(key, obj);
          }
          continue;
        }

        /* 3. Multi valued variable (start) */
        matcher = MULTI_VALUED_VAR_START_PATTERN.matcher(line);
        if (matcher.matches()) {

          if (Scope.MULTI.equals(scope)) {
            String errorMsg = "Unsupported nesting of multi value variables at line: " + lr.getLineNumber();
            throw new KrbConfParseException(errorMsg);
          }

          String key = matcher.group(1).trim();
          multi = new HashMap<>();
          section.put(key, multi);
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

        throw new KrbConfParseException("Unrecognisable pattern at line: " + lr.getLineNumber());
      }
      return krbConf;
    } catch (Exception e) {
      throw new KrbConfParseException(e);
    }
  }
}
