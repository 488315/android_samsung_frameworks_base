package com.google.dexmaker.dx.util;

import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;

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
        StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(i, i2, "bad range: ", "..", "; actual size ");
        sbM.append(i3);
        throw new IllegalArgumentException(sbM.toString());
    }

    public ByteArray(byte[] bArr) {
        this(bArr, 0, bArr.length);
    }
}
