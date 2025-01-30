package com.android.internal.org.bouncycastle.crypto.params;

import com.android.internal.org.bouncycastle.crypto.CipherParameters;

/* loaded from: classes5.dex */
public class ParametersWithID implements CipherParameters {

  /* renamed from: id */
  private byte[] f884id;
  private CipherParameters parameters;

  public ParametersWithID(CipherParameters parameters, byte[] id) {
    this.parameters = parameters;
    this.f884id = id;
  }

  public byte[] getID() {
    return this.f884id;
  }

  public CipherParameters getParameters() {
    return this.parameters;
  }
}
