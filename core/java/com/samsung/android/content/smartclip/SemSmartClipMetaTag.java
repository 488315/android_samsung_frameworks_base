package com.samsung.android.content.smartclip;

import android.graphics.Bitmap;

/* loaded from: classes5.dex */
public class SemSmartClipMetaTag {
  private Bitmap mBitmap;
  private String mType;
  private String mValue;

  public SemSmartClipMetaTag(String tagType, String value) {
    this.mType = null;
    this.mValue = null;
    this.mBitmap = null;
    this.mType = tagType;
    this.mValue = value;
  }

  public SemSmartClipMetaTag(String tagType, String value, Bitmap bitmap) {
    this.mType = null;
    this.mValue = null;
    this.mBitmap = null;
    this.mType = tagType;
    this.mValue = value;
    this.mBitmap = bitmap;
  }

  public String getType() {
    return this.mType;
  }

  public String getValue() {
    return this.mValue;
  }

  public Bitmap getBitmap() {
    return this.mBitmap;
  }

  public void setType(String tagType) {
    this.mType = tagType;
  }

  public void setValue(String value) {
    this.mValue = value;
  }
}
