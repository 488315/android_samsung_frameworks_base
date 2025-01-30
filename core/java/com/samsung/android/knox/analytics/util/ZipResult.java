package com.samsung.android.knox.analytics.util;

/* loaded from: classes5.dex */
public class ZipResult {
  private byte[] content;
  private int length;
  private int originalLength;

  public ZipResult(byte[] content, int length, int originalLength) {
    this.content = content;
    this.length = length;
    this.originalLength = originalLength;
  }

  public byte[] getContent() {
    return this.content;
  }

  public int getLength() {
    return this.length;
  }

  public int getOriginalLength() {
    return this.originalLength;
  }
}
