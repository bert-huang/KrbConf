package com.cepw;

import com.cepw.exception.KrbConfParseException;
import com.cepw.model.KrbConf;
import com.cepw.model.node.ComplexKeyValuesNode;
import com.cepw.model.node.KrbConfNode;
import com.cepw.model.node.SectionNode;
import com.cepw.model.node.SimpleKeyValuesNode;
import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A krb.conf parser.
 */
public class KrbConfParser {

  /**
   * The {@link Pattern} for section header.
   */
  private static final Pattern SECTION_HEADER_PATTERN = Pattern.compile("^\\s*\\[([A-Za-z_]+)]\\s*$");

  /**
   * The {@link Pattern} for simple key-value entry.
   */
  private static final Pattern SIMPLE_KEY_VALUES_PATTERN = Pattern.compile("^\\s*([^\\s=]+)\\s*=\\s*([[\\S]+\\s*]+[\\S]+)\\s*(?<!\\{)$");

  /**
   * The {@link Pattern} for the start of complex key-value entry.
   */
  private static final Pattern COMPLEX_KEY_VALUES_START_PATTERN = Pattern.compile("^\\s*([^\\s=]+)\\s*=\\s*\\{\\s*$");

  /**
   * The {@link Pattern} for the end of complex key-value entry.
   */
  private static final Pattern COMPLEX_KEY_VALUES_END_PATTERN = Pattern.compile("^\\s*}\\s*$");

  /**
   * Parse the krb.conf file.
   *
   * @param path the path of the config file
   * @return the {@link KrbConf}
   * @throws KrbConfParseException if parsing failed.
   */
  public static KrbConf parse(String path) throws KrbConfParseException {
    return parse(new File(path));
  }

  /**
   * Parse the krb.conf file.
   *
   * @param file the {@link File} of the config
   * @return the {@link KrbConf}
   * @throws KrbConfParseException if parsing failed.
   */
  public static KrbConf parse(File file) throws KrbConfParseException {
    try {
      KrbConf krbConf = new KrbConf();
      LineNumberReader lr = new LineNumberReader(new FileReader(file));

      String line;
      Stack<KrbConfNode> nodeStack = new Stack<KrbConfNode>();
      while ((line = lr.readLine()) != null) {

        /* Ignore blank or commented lines */
        line = line.trim();
        if (line.length() == 0 || line.startsWith("#") || line.startsWith(";")) {
          continue;
        }

        Matcher matcher;
        /* 1. Section header */
        matcher = SECTION_HEADER_PATTERN.matcher(line);
        if (matcher.matches()) {

          KrbConfNode node = nodeStack.isEmpty() ? null : nodeStack.peek();
          if (node != null && !(node instanceof SectionNode)) {
            String errorMsg = "Complex key-value not closed at line: " + lr.getLineNumber();
            throw new KrbConfParseException(errorMsg);
          }
          /* Pop the previous sectionNode */
          if (!nodeStack.isEmpty()) {
            nodeStack.pop();
          }

          String sectionName = matcher.group(1).trim();
          SectionNode section = krbConf.getSection(KrbConf.SECTION_CLASSES.get(sectionName));
          if (section == null) {
            String errorMsg = "Unknown section " + sectionName + " at line: " + lr.getLineNumber();
            throw new KrbConfParseException(errorMsg);
          }
          nodeStack.push(section);
          continue;
        }
        if (nodeStack.isEmpty()) {
          String errorMsg = "Key-value definition before section at line: " + lr.getLineNumber();
          throw new KrbConfParseException(errorMsg);
        }

        /* 2. Simple key-value node */
        matcher = SIMPLE_KEY_VALUES_PATTERN.matcher(line);
        if (matcher.matches()) {
          String key = matcher.group(1).trim();
          String val = matcher.group(2).trim();
          String[] splits = val.replaceAll("\\s+", " ").split("\\s");
          SimpleKeyValuesNode simpleNode = new SimpleKeyValuesNode(key, splits);

          KrbConfNode node = nodeStack.isEmpty() ? null : nodeStack.peek();
          if (node instanceof SectionNode) {
            ((SectionNode) node).add(simpleNode);
          } else if (node instanceof ComplexKeyValuesNode) {
            ((ComplexKeyValuesNode) node).add(simpleNode);
          }
          continue;
        }

        /* 3. Complex key-value node START */
        matcher = COMPLEX_KEY_VALUES_START_PATTERN.matcher(line);
        if (matcher.matches()) {
          String key = matcher.group(1).trim();
          KrbConfNode node = nodeStack.isEmpty() ? null : nodeStack.peek();
          if (node != null) {
            ComplexKeyValuesNode complexNode = new ComplexKeyValuesNode(key);
            nodeStack.push(complexNode);
          }
          continue;
        }

        /* 4. Complex key-value node END */
        matcher = COMPLEX_KEY_VALUES_END_PATTERN.matcher(line);
        if (matcher.matches()) {
          ComplexKeyValuesNode innerNode = nodeStack.isEmpty() ? null : (ComplexKeyValuesNode) nodeStack.pop();
          ComplexKeyValuesNode outerNode = nodeStack.isEmpty() ? null : (ComplexKeyValuesNode) nodeStack.peek();
          if (outerNode != null) {
            outerNode.add(innerNode);
          }
          continue;
        }
        throw new KrbConfParseException("Invalid value at line: " + lr.getLineNumber());
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
