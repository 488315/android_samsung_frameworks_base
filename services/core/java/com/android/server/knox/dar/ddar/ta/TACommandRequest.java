package com.android.server.knox.dar.ddar.ta;

/* loaded from: classes2.dex */
public class TACommandRequest {
  public int mVersion = -1;
  public byte[] mMagicNum = null;
  public int mLength = 0;
  public int mOffset = 0;
  public int mCommandId = -1;
  public byte[] mRequest = null;

  public void init(int i, byte[] bArr, int i2, byte[] bArr2) {
    this.mVersion = i;
    this.mMagicNum = bArr;
    this.mCommandId = i2;
    this.mRequest = bArr2;
    if (bArr2 != null) {
      this.mLength = bArr2.length;
    } else {
      this.mLength = 0;
    }
    this.mOffset = 0;
  }
}
