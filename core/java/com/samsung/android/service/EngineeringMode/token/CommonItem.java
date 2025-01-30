package com.samsung.android.service.EngineeringMode.token;

/* loaded from: classes5.dex */
public class CommonItem {
  private byte[] mContents;
  private int mLen;
  private int mType;

  public CommonItem(int mType, int mLen, byte[] mContents) {
    this.mType = mType;
    this.mLen = mLen;
    this.mContents = mContents;
  }

  public int getType() {
    return this.mType;
  }

  public int getLen() {
    return this.mLen;
  }

  public byte[] getData() {
    return this.mContents;
  }
}
