package com.android.server.integrity.model;

import java.io.OutputStream;

/* loaded from: classes2.dex */
public class ByteTrackedOutputStream extends OutputStream {
    public final OutputStream mOutputStream;
    public int mWrittenBytesCount = 0;

    public ByteTrackedOutputStream(OutputStream outputStream) {
        this.mOutputStream = outputStream;
    }

    @Override // java.io.OutputStream
    public void write(int i) {
        this.mWrittenBytesCount++;
        this.mOutputStream.write(i);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr) {
        write(bArr, 0, bArr.length);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) {
        this.mWrittenBytesCount += i2;
        this.mOutputStream.write(bArr, i, i2);
    }

    public int getWrittenBytesCount() {
        return this.mWrittenBytesCount;
    }
}
