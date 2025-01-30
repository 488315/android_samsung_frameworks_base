package com.android.server.enterprise.email;

import com.samsung.android.knox.ContextInfo;

/* compiled from: AccountsReceiver.java */
/* loaded from: classes2.dex */
public class AccountSMIMECertificate {
  public ContextInfo mCxtInfo;
  public String mPassword;
  public String mPath;
  public int sMode;

  public AccountSMIMECertificate(ContextInfo contextInfo, String str, String str2, int i) {
    this.mPath = str;
    this.mPassword = str2;
    this.sMode = i;
    this.mCxtInfo = contextInfo;
  }
}
