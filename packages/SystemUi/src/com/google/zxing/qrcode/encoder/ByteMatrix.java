package com.google.zxing.qrcode.encoder;

import java.lang.reflect.Array;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ByteMatrix {
    public final byte[][] bytes;
    public final int height;
    public final int width;

    public ByteMatrix(int i, int i2) {
        this.bytes = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, i2, i);
        this.width = i;
        this.height = i2;
    }

    public final byte get(int i, int i2) {
        return this.bytes[i2][i];
    }

    public final void set(int i, int i2, int i3) {
        this.bytes[i2][i] = (byte) i3;
    }

    public final String toString() {
        int i = this.width;
        int i2 = this.height;
        StringBuilder sb = new StringBuilder((i * 2 * i2) + 2);
        for (int i3 = 0; i3 < i2; i3++) {
            for (int i4 = 0; i4 < i; i4++) {
                byte b = this.bytes[i3][i4];
                if (b == 0) {
                    sb.append(" 0");
                } else if (b != 1) {
                    sb.append("  ");
                } else {
                    sb.append(" 1");
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public final void set(int i, int i2, boolean z) {
        this.bytes[i2][i] = z ? (byte) 1 : (byte) 0;
    }
}
