package com.google.dexmaker.dx.util;

import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class ByteArray {
    public final byte[] bytes;
    public final int size;
    public final int start;

    public ByteArray(byte[] bArr, int i, int i2) {
        if (bArr == null) {
            throw new NullPointerException("bytes == null");
        }
        if (i < 0) {
            throw new IllegalArgumentException("start < 0");
        }
        if (i2 < i) {
            throw new IllegalArgumentException("end < start");
        }
        if (i2 > bArr.length) {
            throw new IllegalArgumentException("end > bytes.length");
        }
        this.bytes = bArr;
        this.start = i;
        this.size = i2 - i;
    }

    public final int getUnsignedByte(int i) {
        int i2 = i + 1;
        int i3 = this.size;
        if (i >= 0 && i2 >= i && i2 <= i3) {
            return this.bytes[this.start + i] & 255;
        }
        StringBuilder m = MutableObjectList$$ExternalSyntheticOutline0.m(i, i2, "bad range: ", "..", "; actual size ");
        m.append(i3);
        throw new IllegalArgumentException(m.toString());
    }

    public ByteArray(byte[] bArr) {
        this(bArr, 0, bArr.length);
    }
}
