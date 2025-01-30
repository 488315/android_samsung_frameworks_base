package com.android.server.knox.dar.sdp;

/* loaded from: classes2.dex */
public abstract class SdpManagerInternal {
  public abstract int getMasterKeyVersion(int i);

  public abstract void setMasterKeyVersion(int i, int i2);

  public abstract boolean setSdpPolicyToPath(int i, String str);

  public abstract boolean updateMasterKey(byte[] bArr, byte[] bArr2, int i);
}
