package com.android.internal.widget.remotecompose.core;

import java.util.Arrays;

/* loaded from: classes6.dex */
public class WireBuffer {
    private static final int BUFFER_SIZE = 1048576;
    byte[] mBuffer;
    int mIndex;
    int mMaxSize;
    int mSize;
    int mStartingIndex;

    public WireBuffer(int i) {
        this.mIndex = 0;
        this.mStartingIndex = 0;
        this.mSize = 0;
        this.mMaxSize = i;
        this.mBuffer = new byte[i];
    }

    public WireBuffer() {
        this(1048576);
    }

    private void resize(int i) {
        int i2 = this.mSize;
        int i3 = i2 + i;
        int i4 = this.mMaxSize;
        if (i3 >= i4) {
            int max = Math.max(i4 * 2, i2 + i);
            this.mMaxSize = max;
            this.mBuffer = Arrays.copyOf(this.mBuffer, max);
        }
    }

    public byte[] getBuffer() {
        return this.mBuffer;
    }

    public int getMax_size() {
        return this.mMaxSize;
    }

    public int getIndex() {
        return this.mIndex;
    }

    public int getSize() {
        return this.mSize;
    }

    public void setIndex(int i) {
        this.mIndex = i;
    }

    public void start(int i) {
        this.mStartingIndex = this.mIndex;
        writeByte(i);
    }

    public void startWithSize(int i) {
        this.mStartingIndex = this.mIndex;
        writeByte(i);
        this.mIndex += 4;
    }

    public void endWithSize() {
        int i = this.mIndex;
        int i2 = this.mStartingIndex;
        this.mIndex = i2 + 1;
        writeInt(i - i2);
        this.mIndex = i;
    }

    public void reset(int i) {
        this.mIndex = 0;
        this.mStartingIndex = 0;
        this.mSize = 0;
        if (i >= this.mMaxSize) {
            resize(i);
        }
    }

    public int size() {
        return this.mSize;
    }

    public boolean available() {
        return this.mSize - this.mIndex > 0;
    }

    public int readOperationType() {
        return readByte();
    }

    public boolean readBoolean() {
        byte[] bArr = this.mBuffer;
        int i = this.mIndex;
        byte b = bArr[i];
        this.mIndex = i + 1;
        return b == 1;
    }

    public int readByte() {
        byte[] bArr = this.mBuffer;
        int i = this.mIndex;
        int i2 = bArr[i] & 255;
        this.mIndex = i + 1;
        return i2;
    }

    public int readShort() {
        byte[] bArr = this.mBuffer;
        int i = this.mIndex;
        int i2 = i + 1;
        this.mIndex = i2;
        int i3 = (bArr[i] & 255) << 8;
        this.mIndex = i + 2;
        return i3 + (bArr[i2] & 255);
    }

    public int peekInt() {
        int i = this.mIndex;
        byte[] bArr = this.mBuffer;
        int i2 = (bArr[i] & 255) << 24;
        int i3 = (bArr[i + 1] & 255) << 16;
        return i2 + i3 + ((bArr[i + 2] & 255) << 8) + (bArr[i + 3] & 255);
    }

    public int readInt() {
        byte[] bArr = this.mBuffer;
        int i = this.mIndex;
        int i2 = i + 1;
        this.mIndex = i2;
        int i3 = (bArr[i] & 255) << 24;
        int i4 = i + 2;
        this.mIndex = i4;
        int i5 = (bArr[i2] & 255) << 16;
        int i6 = i + 3;
        this.mIndex = i6;
        int i7 = (bArr[i4] & 255) << 8;
        this.mIndex = i + 4;
        return i3 + i5 + i7 + (bArr[i6] & 255);
    }

    public long readLong() {
        byte[] bArr = this.mBuffer;
        int i = this.mIndex;
        this.mIndex = i + 1;
        long j = (bArr[i] & 255) << 56;
        this.mIndex = i + 2;
        long j2 = (bArr[r3] & 255) << 48;
        this.mIndex = i + 3;
        long j3 = (bArr[r8] & 255) << 40;
        this.mIndex = i + 4;
        long j4 = (bArr[r3] & 255) << 32;
        this.mIndex = i + 5;
        long j5 = (bArr[r8] & 255) << 24;
        this.mIndex = i + 6;
        long j6 = (bArr[r3] & 255) << 16;
        this.mIndex = i + 7;
        long j7 = (bArr[r8] & 255) << 8;
        this.mIndex = i + 8;
        return j + j2 + j3 + j4 + j5 + j6 + j7 + (bArr[r3] & 255);
    }

    public float readFloat() {
        return Float.intBitsToFloat(readInt());
    }

    public double readDouble() {
        return Double.longBitsToDouble(readLong());
    }

    public byte[] readBuffer() {
        int readInt = readInt();
        byte[] bArr = this.mBuffer;
        int i = this.mIndex;
        byte[] copyOfRange = Arrays.copyOfRange(bArr, i, i + readInt);
        this.mIndex += readInt;
        return copyOfRange;
    }

    public byte[] readBuffer(int i) {
        int readInt = readInt();
        if (readInt < 0 || readInt > i) {
            throw new RuntimeException("attempt read a buff of invalid size 0 <= " + readInt + " > " + i);
        }
        byte[] bArr = this.mBuffer;
        int i2 = this.mIndex;
        byte[] copyOfRange = Arrays.copyOfRange(bArr, i2, i2 + readInt);
        this.mIndex += readInt;
        return copyOfRange;
    }

    public String readUTF8() {
        return new String(readBuffer());
    }

    public String readUTF8(int i) {
        return new String(readBuffer(i));
    }

    public void writeBoolean(boolean z) {
        resize(1);
        byte[] bArr = this.mBuffer;
        int i = this.mIndex;
        this.mIndex = i + 1;
        bArr[i] = z ? (byte) 1 : (byte) 0;
        this.mSize++;
    }

    public void writeByte(int i) {
        resize(1);
        byte[] bArr = this.mBuffer;
        int i2 = this.mIndex;
        this.mIndex = i2 + 1;
        bArr[i2] = (byte) i;
        this.mSize++;
    }

    public void writeShort(int i) {
        resize(2);
        byte[] bArr = this.mBuffer;
        int i2 = this.mIndex;
        int i3 = i2 + 1;
        this.mIndex = i3;
        bArr[i2] = (byte) ((i >>> 8) & 255);
        this.mIndex = i2 + 2;
        bArr[i3] = (byte) (i & 255);
        this.mSize += 2;
    }

    public void writeInt(int i) {
        resize(4);
        byte[] bArr = this.mBuffer;
        int i2 = this.mIndex;
        int i3 = i2 + 1;
        this.mIndex = i3;
        bArr[i2] = (byte) ((i >>> 24) & 255);
        int i4 = i2 + 2;
        this.mIndex = i4;
        bArr[i3] = (byte) ((i >>> 16) & 255);
        int i5 = i2 + 3;
        this.mIndex = i5;
        bArr[i4] = (byte) ((i >>> 8) & 255);
        this.mIndex = i2 + 4;
        bArr[i5] = (byte) (i & 255);
        this.mSize += 4;
    }

    public void writeLong(long j) {
        resize(8);
        byte[] bArr = this.mBuffer;
        int i = this.mIndex;
        int i2 = i + 1;
        this.mIndex = i2;
        bArr[i] = (byte) ((j >>> 56) & 255);
        int i3 = i + 2;
        this.mIndex = i3;
        bArr[i2] = (byte) ((j >>> 48) & 255);
        int i4 = i + 3;
        this.mIndex = i4;
        bArr[i3] = (byte) ((j >>> 40) & 255);
        int i5 = i + 4;
        this.mIndex = i5;
        bArr[i4] = (byte) ((j >>> 32) & 255);
        int i6 = i + 5;
        this.mIndex = i6;
        bArr[i5] = (byte) ((j >>> 24) & 255);
        int i7 = i + 6;
        this.mIndex = i7;
        bArr[i6] = (byte) ((j >>> 16) & 255);
        int i8 = i + 7;
        this.mIndex = i8;
        bArr[i7] = (byte) ((j >>> 8) & 255);
        this.mIndex = i + 8;
        bArr[i8] = (byte) (j & 255);
        this.mSize += 8;
    }

    public void writeFloat(float f) {
        writeInt(Float.floatToRawIntBits(f));
    }

    public void writeDouble(double d) {
        writeLong(Double.doubleToRawLongBits(d));
    }

    public void writeBuffer(byte[] bArr) {
        resize(bArr.length + 4);
        writeInt(bArr.length);
        for (byte b : bArr) {
            byte[] bArr2 = this.mBuffer;
            int i = this.mIndex;
            this.mIndex = i + 1;
            bArr2[i] = b;
        }
        this.mSize += bArr.length;
    }

    public void writeUTF8(String str) {
        writeBuffer(str.getBytes());
    }
}
