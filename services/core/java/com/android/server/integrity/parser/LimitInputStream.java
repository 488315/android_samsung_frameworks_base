package com.android.server.integrity.parser;

import java.io.FilterInputStream;
import java.io.InputStream;

/* loaded from: classes2.dex */
public class LimitInputStream extends FilterInputStream {
  public final int mLimit;
  public int mReadBytes;

  public LimitInputStream(InputStream inputStream, int i) {
    super(inputStream);
    if (i < 0) {
      throw new IllegalArgumentException("limit " + i + " cannot be negative");
    }
    this.mReadBytes = 0;
    this.mLimit = i;
  }

  @Override // java.io.FilterInputStream, java.io.InputStream
  public int available() {
    return Math.min(super.available(), this.mLimit - this.mReadBytes);
  }

  @Override // java.io.FilterInputStream, java.io.InputStream
  public int read() {
    int i = this.mReadBytes;
    if (i == this.mLimit) {
      return -1;
    }
    this.mReadBytes = i + 1;
    return super.read();
  }

  @Override // java.io.FilterInputStream, java.io.InputStream
  public int read(byte[] bArr) {
    return read(bArr, 0, bArr.length);
  }

  @Override // java.io.FilterInputStream, java.io.InputStream
  public int read(byte[] bArr, int i, int i2) {
    if (i2 <= 0) {
      return 0;
    }
    int available = available();
    if (available <= 0) {
      return -1;
    }
    int read = super.read(bArr, i, Math.min(i2, available));
    this.mReadBytes += read;
    return read;
  }

  @Override // java.io.FilterInputStream, java.io.InputStream
  public long skip(long j) {
    int available;
    if (j <= 0 || (available = available()) <= 0) {
      return 0L;
    }
    long skip = super.skip((int) Math.min(available, j));
    this.mReadBytes += (int) skip;
    return skip;
  }
}
