package com.android.server.integrity.parser;

import java.io.InputStream;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class RandomAccessInputStream extends InputStream {
    public int mPosition = 0;
    public final RandomAccessObject$RandomAccessFileObject mRandomAccessObject;

    public RandomAccessInputStream(
            RandomAccessObject$RandomAccessFileObject randomAccessObject$RandomAccessFileObject) {
        this.mRandomAccessObject = randomAccessObject$RandomAccessFileObject;
    }

    @Override // java.io.InputStream
    public final int available() {
        return this.mRandomAccessObject.mLength - this.mPosition;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        this.mRandomAccessObject.mRandomAccessFile.close();
    }

    @Override // java.io.InputStream
    public final int read() {
        if (available() <= 0) {
            return -1;
        }
        this.mPosition++;
        return this.mRandomAccessObject.mRandomAccessFile.read();
    }

    @Override // java.io.InputStream
    public final int read(byte[] bArr) {
        return read(bArr, 0, bArr.length);
    }

    @Override // java.io.InputStream
    public final int read(byte[] bArr, int i, int i2) {
        if (i2 <= 0) {
            return 0;
        }
        int available = available();
        if (available <= 0) {
            return -1;
        }
        int read =
                this.mRandomAccessObject.mRandomAccessFile.read(bArr, i, Math.min(i2, available));
        this.mPosition += read;
        return read;
    }

    @Override // java.io.InputStream
    public final long skip(long j) {
        int available;
        if (j <= 0 || (available = available()) <= 0) {
            return 0L;
        }
        int min = (int) Math.min(available, j);
        int i = this.mPosition + min;
        this.mPosition = i;
        this.mRandomAccessObject.mRandomAccessFile.seek(i);
        return min;
    }
}
