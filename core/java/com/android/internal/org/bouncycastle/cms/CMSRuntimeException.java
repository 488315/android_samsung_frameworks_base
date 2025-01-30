package com.android.internal.org.bouncycastle.cms;

/* loaded from: classes5.dex */
public class CMSRuntimeException extends RuntimeException {

  /* renamed from: e */
  Exception f758e;

  public CMSRuntimeException(String name) {
    super(name);
  }

  public CMSRuntimeException(String name, Exception e) {
    super(name);
    this.f758e = e;
  }

  public Exception getUnderlyingException() {
    return this.f758e;
  }

  @Override // java.lang.Throwable
  public Throwable getCause() {
    return this.f758e;
  }
}
