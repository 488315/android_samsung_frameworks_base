package com.android.server.knox.dar.ddar.ta;

/* loaded from: classes2.dex */
public class TACommandResponse {
  public String mErrorMsg;
  public byte[] mResponse;
  public int mResponseCode;

  public TACommandResponse() {
    this.mResponseCode = -1;
    this.mErrorMsg = null;
    this.mResponse = null;
  }

  public TACommandResponse(int i, String str, byte[] bArr) {
    this.mResponseCode = i;
    this.mErrorMsg = str;
    this.mResponse = bArr;
  }
}
