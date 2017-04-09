package com.cepw.exception;

public class KrbConfParseException extends Exception {

  private static final long serialVersionUID = -1533465417435075670L;

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
