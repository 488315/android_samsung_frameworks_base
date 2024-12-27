package com.android.internal.util;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes5.dex */
public class SizedInputStream extends InputStream {
    private long mLength;
    private final InputStream mWrapped;

    public SizedInputStream(InputStream wrapped, long length) {
        this.mWrapped = wrapped;
        this.mLength = length;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        super.close();
        this.mWrapped.close();
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        byte[] buffer = new byte[1];
        int result = read(buffer, 0, 1);
        if (result != -1) {
            return buffer[0] & 255;
        }
        return -1;
    }

    @Override // java.io.InputStream
    public int read(byte[] buffer, int byteOffset, int byteCount) throws IOException {
        if (this.mLength <= 0) {
            return -1;
        }
        if (byteCount > this.mLength) {
            byteCount = (int) this.mLength;
        }
        int n = this.mWrapped.read(buffer, byteOffset, byteCount);
        if (n != -1) {
            this.mLength -= n;
        } else if (this.mLength > 0) {
            throw new IOException("Unexpected EOF; expected " + this.mLength + " more bytes");
        }
        return n;
    }
}
