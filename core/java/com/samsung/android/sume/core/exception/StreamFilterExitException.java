package com.samsung.android.sume.core.exception;

/* loaded from: classes4.dex */
public class StreamFilterExitException extends IllegalStateException
    implements IntendedQuitException {
  public StreamFilterExitException() {}

  public StreamFilterExitException(String s) {
    super(s);
  }

  public StreamFilterExitException(String message, Throwable cause) {
    super(message, cause);
  }

  public StreamFilterExitException(Throwable cause) {
    super(cause);
  }
}
