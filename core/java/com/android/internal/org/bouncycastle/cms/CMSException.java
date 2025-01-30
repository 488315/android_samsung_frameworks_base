package com.android.internal.org.bouncycastle.cms;

/* loaded from: classes5.dex */
public class CMSException extends Exception {

  /* renamed from: e */
  Exception f757e;

  public CMSException(String msg) {
    super(msg);
  }

  public CMSException(String msg, Exception e) {
    super(msg);
    this.f757e = e;
  }

  public Exception getUnderlyingException() {
    return this.f757e;
  }

  @Override // java.lang.Throwable
  public Throwable getCause() {
    return this.f757e;
  }
}
