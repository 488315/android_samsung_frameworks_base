package com.android.server.usb.descriptors;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ByteStream {
    public final byte[] mBytes;
    public int mIndex;
    public int mReadCount;

    public ByteStream(byte[] bArr) {
        if (bArr == null) {
            throw new IllegalArgumentException();
        }
        this.mBytes = bArr;
    }

    public final void advance(int i) {
        if (i < 0) {
            throw new IllegalArgumentException();
        }
        int i2 = this.mIndex;
        long j = i2 + i;
        byte[] bArr = this.mBytes;
        if (j > bArr.length) {
            this.mIndex = bArr.length;
            throw new IndexOutOfBoundsException();
        }
        this.mReadCount += i;
        this.mIndex = i2 + i;
    }

    public final int available() {
        return this.mBytes.length - this.mIndex;
    }

    public final byte getByte() {
        if (available() <= 0) {
            throw new IndexOutOfBoundsException();
        }
        this.mReadCount++;
        int i = this.mIndex;
        this.mIndex = i + 1;
        return this.mBytes[i];
    }

    public final int getUnsignedByte() {
        if (available() <= 0) {
            throw new IndexOutOfBoundsException();
        }
        this.mReadCount++;
        int i = this.mIndex;
        this.mIndex = i + 1;
        return this.mBytes[i] & 255;
    }

    public final int unpackUsbInt() {
        if (available() < 4) {
            throw new IndexOutOfBoundsException();
        }
        int unsignedByte = getUnsignedByte();
        int unsignedByte2 = getUnsignedByte();
        return (getUnsignedByte() << 24)
                | (getUnsignedByte() << 16)
                | (unsignedByte2 << 8)
                | unsignedByte;
    }

    public final int unpackUsbShort() {
        if (available() < 2) {
            throw new IndexOutOfBoundsException();
        }
        return (getUnsignedByte() << 8) | getUnsignedByte();
    }

    public final int unpackUsbTriple() {
        if (available() < 3) {
            throw new IndexOutOfBoundsException();
        }
        return (getUnsignedByte() << 16) | (getUnsignedByte() << 8) | getUnsignedByte();
    }
}
