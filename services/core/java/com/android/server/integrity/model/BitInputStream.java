package com.android.server.integrity.model;

import java.io.InputStream;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class BitInputStream {
    public long mBitsRead;
    public byte mCurrentByte;
    public final InputStream mInputStream;

    public BitInputStream(InputStream inputStream) {
        this.mInputStream = inputStream;
    }

    public final int getNext(int i) {
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int i4 = i2 + 1;
            if (i2 >= i) {
                return i3;
            }
            if (this.mBitsRead % 8 == 0) {
                this.mCurrentByte = (byte) this.mInputStream.read();
            }
            long j = this.mBitsRead;
            i3 = (i3 << 1) | ((this.mCurrentByte >>> (7 - ((int) (j % 8)))) & 1);
            this.mBitsRead = j + 1;
            i2 = i4;
        }
    }
}
