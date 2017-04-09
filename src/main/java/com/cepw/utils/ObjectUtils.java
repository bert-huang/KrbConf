package com.cepw.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Utility class for performing {@link Object} related operations
 */
public class ObjectUtils {

  /**
   * Serialise the given {@link Object}.
   *
   * @param obj the {@link Object} to serialise.
   * @return the serialised {@link Object} in the form of {@link Byte} array.
   * @throws IOException if {@link Object} cannot be serialised.
   */
  public static byte[] serialize(Object obj) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(baos);
    oos.writeObject(obj);
    return baos.toByteArray();
  }
}
