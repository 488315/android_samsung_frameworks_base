package com.android.internal.util;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes4.dex */
public class SizedInputStream extends InputStream {
    private long mLength;
    private final InputStream mWrapped;

    public SizedInputStream(InputStream inputStream, long j) {
        this.mWrapped = inputStream;
        this.mLength = j;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        super.close();
        this.mWrapped.close();
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        byte[] bArr = new byte[1];
        if (read(bArr, 0, 1) != -1) {
            return bArr[0] & 255;
        }
        return -1;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        long j = this.mLength;
        if (j <= 0) {
            return -1;
        }
        if (i2 > j) {
            i2 = (int) j;
        }
        int i3 = this.mWrapped.read(bArr, i, i2);
        if (i3 != -1) {
            this.mLength -= i3;
            return i3;
        }
        if (this.mLength <= 0) {
            return i3;
        }
        throw new IOException("Unexpected EOF; expected " + this.mLength + " more bytes");
    }
}
