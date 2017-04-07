package com.cepw.exception;

public class KrbConfParseException extends Exception {

  public KrbConfParseException(String msg) {
    super(msg);
  }

  public KrbConfParseException(String msg, Throwable t) {
    super(msg, t);
  }

  public KrbConfParseException(Throwable t) {
    super(t);
  }
}
