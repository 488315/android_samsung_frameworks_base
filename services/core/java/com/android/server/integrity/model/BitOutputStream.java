package com.android.server.integrity.model;

import java.io.OutputStream;
import java.util.Arrays;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class BitOutputStream {
    public final byte[] mBuffer = new byte[4096];
    public int mNextBitIndex = 0;
    public final OutputStream mOutputStream;

    public BitOutputStream(OutputStream outputStream) {
        this.mOutputStream = outputStream;
    }

    public final void flush() {
        int i = this.mNextBitIndex;
        int i2 = i / 8;
        if (i % 8 != 0) {
            i2++;
        }
        OutputStream outputStream = this.mOutputStream;
        byte[] bArr = this.mBuffer;
        outputStream.write(bArr, 0, i2);
        this.mNextBitIndex = 0;
        Arrays.fill(bArr, (byte) 0);
    }

    public final void setNext(int i, int i2) {
        if (i <= 0) {
            return;
        }
        int i3 = 1 << (i - 1);
        while (true) {
            int i4 = i - 1;
            if (i <= 0) {
                return;
            }
            setNext((i2 & i3) != 0);
            i3 >>>= 1;
            i = i4;
        }
    }

    public final void setNext(boolean z) {
        int i = this.mNextBitIndex / 8;
        byte[] bArr = this.mBuffer;
        if (i == 4096) {
            this.mOutputStream.write(bArr);
            i = 0;
            this.mNextBitIndex = 0;
            Arrays.fill(bArr, (byte) 0);
        }
        if (z) {
            bArr[i] = (byte) (bArr[i] | (1 << (7 - (this.mNextBitIndex % 8))));
        }
        this.mNextBitIndex++;
    }
}
