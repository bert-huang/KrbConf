package com.cepw.utils;

import java.util.List;

/**
 * Utility class for performing {@link String} related operations
 */
public class StringUtils {

  /**
   * Repeats a {@link String} by the given number of times.
   *
   * @param s the {@link String} to repeat
   * @param count number of times to repeat
   * @return the repeated {@link String}
   */
  public static String repeat(String s, int count) {
    return new String(new char[count]).replace("\0", s);
  }

  /**
   * Join a given {@link List} of {@link String}s, separated by a given delimiter.
   *
   * @param values the {@link List} of {@link String}s to join.
   * @param delimiter the delimiter
   * @return the joined {@link String}
   */
  public static String join(List<String> values, String delimiter) {
    StringBuilder sb = new StringBuilder();
    if (values != null && !values.isEmpty()) {
      sb.append(values.get(0));
      for(int i = 1; i < values.size(); i++) {
        sb.append(delimiter);
        sb.append(values.get(i));
      }
    }
    return sb.toString();
  }
}
