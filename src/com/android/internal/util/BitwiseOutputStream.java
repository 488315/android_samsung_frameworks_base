package com.android.internal.util;

/* loaded from: classes4.dex */
public class BitwiseOutputStream {
    private byte[] mBuf;
    private int mEnd;
    private int mPos = 0;

    public static class AccessException extends Exception {
        public AccessException(String str) {
            super("BitwiseOutputStream access failed: " + str);
        }
    }

    public BitwiseOutputStream(int i) {
        this.mBuf = new byte[i];
        this.mEnd = i << 3;
    }

    public byte[] toByteArray() {
        int i = this.mPos;
        int i2 = (i >>> 3) + ((i & 7) > 0 ? 1 : 0);
        byte[] bArr = new byte[i2];
        System.arraycopy(this.mBuf, 0, bArr, 0, i2);
        return bArr;
    }

    private void possExpand(int i) {
        int i2 = this.mPos;
        int i3 = i2 + i;
        int i4 = this.mEnd;
        if (i3 < i4) {
            return;
        }
        int i5 = (i2 + i) >>> 2;
        byte[] bArr = new byte[i5];
        System.arraycopy(this.mBuf, 0, bArr, 0, i4 >>> 3);
        this.mBuf = bArr;
        this.mEnd = i5 << 3;
    }

    public void write(int i, int i2) throws AccessException {
        if (i < 0 || i > 8) {
            throw new AccessException("illegal write (" + i + " bits)");
        }
        possExpand(i);
        int i3 = this.mPos;
        int i4 = i3 >>> 3;
        int i5 = (16 - (i3 & 7)) - i;
        int i6 = (i2 & ((-1) >>> (32 - i))) << i5;
        this.mPos = i3 + i;
        byte[] bArr = this.mBuf;
        bArr[i4] = (byte) (bArr[i4] | (i6 >>> 8));
        if (i5 < 8) {
            int i7 = i4 + 1;
            bArr[i7] = (byte) (bArr[i7] | (i6 & 255));
        }
    }

    public void writeByteArray(int i, byte[] bArr) throws AccessException {
        for (int i2 = 0; i2 < bArr.length; i2++) {
            int min = Math.min(8, i - (i2 << 3));
            if (min > 0) {
                write(min, (byte) (bArr[i2] >>> (8 - min)));
            }
        }
    }

    public void skip(int i) {
        possExpand(i);
        this.mPos += i;
    }
}
