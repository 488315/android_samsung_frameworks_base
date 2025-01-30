package com.android.internal.org.bouncycastle.asn1;

import java.io.IOException;

/* loaded from: classes5.dex */
public class ASN1Exception extends IOException {
  private Throwable cause;

  ASN1Exception(String message) {
    super(message);
  }

  ASN1Exception(String message, Throwable cause) {
    super(message);
    this.cause = cause;
  }

  @Override // java.lang.Throwable
  public Throwable getCause() {
    return this.cause;
  }
}
