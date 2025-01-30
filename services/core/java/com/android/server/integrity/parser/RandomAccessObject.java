package com.android.server.integrity.parser;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/* loaded from: classes2.dex */
public abstract class RandomAccessObject {
  public abstract void close();

  public abstract int length();

  public abstract int read();

  public abstract int read(byte[] bArr, int i, int i2);

  public abstract void seek(int i);

  public static RandomAccessObject ofFile(File file) {
    return new RandomAccessFileObject(file);
  }

  public class RandomAccessFileObject extends RandomAccessObject {
    public final int mLength;
    public final RandomAccessFile mRandomAccessFile;

    public RandomAccessFileObject(File file) {
      long length = file.length();
      if (length > 2147483647L) {
        throw new IOException("Unsupported file size (too big) " + length);
      }
      this.mRandomAccessFile = new RandomAccessFile(file, "r");
      this.mLength = (int) length;
    }

    @Override // com.android.server.integrity.parser.RandomAccessObject
    public void seek(int i) {
      this.mRandomAccessFile.seek(i);
    }

    @Override // com.android.server.integrity.parser.RandomAccessObject
    public int read() {
      return this.mRandomAccessFile.read();
    }

    @Override // com.android.server.integrity.parser.RandomAccessObject
    public int read(byte[] bArr, int i, int i2) {
      return this.mRandomAccessFile.read(bArr, i, i2);
    }

    @Override // com.android.server.integrity.parser.RandomAccessObject
    public void close() {
      this.mRandomAccessFile.close();
    }

    @Override // com.android.server.integrity.parser.RandomAccessObject
    public int length() {
      return this.mLength;
    }
  }
}
