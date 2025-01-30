package com.android.internal.org.bouncycastle.util;

/* loaded from: classes5.dex */
public class StoreException extends RuntimeException {

  /* renamed from: _e */
  private Throwable f995_e;

  public StoreException(String msg, Throwable cause) {
    super(msg);
    this.f995_e = cause;
  }

  @Override // java.lang.Throwable
  public Throwable getCause() {
    return this.f995_e;
  }
}
