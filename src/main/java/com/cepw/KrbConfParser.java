package com.cepw;

import com.cepw.exception.KrbConfParseException;
import com.cepw.model.KrbConf;
import com.cepw.model.node.Node;
import com.cepw.model.node.SectionNode;
import com.cepw.model.node.ComplexKeyValuesNode;
import com.cepw.model.node.SimpleKeyValuesNode;
import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KrbConfParser {

  private static final Pattern SECTION_HEADER_PATTERN = Pattern.compile("^\\s*\\[([A-Za-z_]+)]\\s*$");
  private static final Pattern SIMPLE_KEY_VALUES_PATTERN = Pattern.compile("^\\s*([^\\s=]+)\\s*=\\s*([[\\S]+\\s*]+[\\S]+)\\s*(?<!\\{)$");
  private static final Pattern COMPLEX_KEY_VALUES_START_PATTERN = Pattern.compile("^\\s*([^\\s=]+)\\s*=\\s*\\{\\s*$");
  private static final Pattern COMPLEX_KEY_VALUES_END_PATTERN = Pattern.compile("^\\s*}\\s*$");

  public static KrbConf parse(String file) throws KrbConfParseException {
    return parse(new File(file));
  }

  public static KrbConf parse(File file) throws KrbConfParseException {
    try {
      KrbConf krbConf = new KrbConf();
      LineNumberReader lr = new LineNumberReader(new FileReader(file));

      String line;
      Stack<Node> nodeStack = new Stack<>();
      while ((line = lr.readLine()) != null) {

        /* Ignore blank or commented lines */
        line = line.trim();
        if (line.isEmpty() || line.startsWith("#") || line.startsWith(";")) {
          continue;
        }

        Matcher matcher;
        /* 1. Section header */
        matcher = SECTION_HEADER_PATTERN.matcher(line);
        if (matcher.matches()) {

          Node node = nodeStack.isEmpty() ? null : nodeStack.peek();
          if (node instanceof ComplexKeyValuesNode) {
            String errorMsg = "Unexpected termination of ComplexKeyValuesNode at line: " + lr.getLineNumber();
            throw new KrbConfParseException(errorMsg);
          }
          /* Pop the previous sectionNode */
          if (!nodeStack.isEmpty()) {
            nodeStack.pop();
          }

          String sectionName = matcher.group(1).trim();
          nodeStack.push(krbConf.getSection(sectionName));
          continue;
        }
        if (nodeStack.isEmpty()) {
          String errorMsg = "KeyValueNode definition before SectionNode. Error at line: " + lr.getLineNumber();
          throw new KrbConfParseException(errorMsg);
        }

        /* 2. Simple key-value node */
        matcher = SIMPLE_KEY_VALUES_PATTERN.matcher(line);
        if (matcher.matches()) {
          String key = matcher.group(1).trim();
          String val = matcher.group(2).trim();
          String[] vals = val.replaceAll("\\s+", " ").split("\\s");
          SimpleKeyValuesNode svn = new SimpleKeyValuesNode(key, vals);

          Node node = nodeStack.isEmpty() ? null : nodeStack.peek();
          if (node instanceof SectionNode) {
            ((SectionNode) node).add(svn);
          }
          else if (node instanceof ComplexKeyValuesNode) {
            ((ComplexKeyValuesNode) node).add(svn);
          }
          else {
            String errorMsg = "Invalid node " + node + ". Error at line: " + lr.getLineNumber();
            throw new KrbConfParseException(errorMsg);
          }
          continue;
        }

        /* 3. Complex key-value node START */
        matcher = COMPLEX_KEY_VALUES_START_PATTERN.matcher(line);
        if (matcher.matches()) {
          String key = matcher.group(1).trim();
          Node node = nodeStack.isEmpty() ? null : nodeStack.peek();
          if (node instanceof SectionNode) {
            ComplexKeyValuesNode mvn = new ComplexKeyValuesNode(key);
            ((SectionNode) node).add(mvn);
            nodeStack.push(mvn);
          }
          else {
            String errorMsg = "ComplexKeyValuesNode nesting is not supported. Error at line: " + lr.getLineNumber();
            throw new KrbConfParseException(errorMsg);
          }
          continue;
        }

        /* 4. Complex key-value node END */
        matcher = COMPLEX_KEY_VALUES_END_PATTERN.matcher(line);
        if (matcher.matches()) {
          nodeStack.pop();
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
