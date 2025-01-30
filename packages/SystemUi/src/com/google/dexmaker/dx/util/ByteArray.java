package com.google.dexmaker.dx.util;

import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
        StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("bad range: ", i, "..", i2, "; actual size ");
        m45m.append(i3);
        throw new IllegalArgumentException(m45m.toString());
    }

    public ByteArray(byte[] bArr) {
        this(bArr, 0, bArr.length);
    }
}
