package com.android.internal.org.bouncycastle.util.encoders;

/* loaded from: classes5.dex */
public class DecoderException extends IllegalStateException {
  private Throwable cause;

  DecoderException(String msg, Throwable cause) {
    super(msg);
    this.cause = cause;
  }

  @Override // java.lang.Throwable
  public Throwable getCause() {
    return this.cause;
  }
}
