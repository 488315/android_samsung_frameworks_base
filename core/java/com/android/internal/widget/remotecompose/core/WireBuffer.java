package com.android.internal.widget.remotecompose.core;

import java.util.Arrays;

public class WireBuffer {
    private static final int BUFFER_SIZE = 1048576;
    byte[] mBuffer;
    int mIndex;
    int mMaxSize;
    int mSize;
    int mStartingIndex;

    public WireBuffer(int size) {
        this.mIndex = 0;
        this.mStartingIndex = 0;
        this.mSize = 0;
        this.mMaxSize = size;
        this.mBuffer = new byte[this.mMaxSize];
    }

    public WireBuffer() {
        this(1048576);
    }

    private void resize(int need) {
        if (this.mSize + need >= this.mMaxSize) {
            this.mMaxSize = Math.max(this.mMaxSize * 2, this.mSize + need);
            this.mBuffer = Arrays.copyOf(this.mBuffer, this.mMaxSize);
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

    public void setIndex(int index) {
        this.mIndex = index;
    }

    public void start(int type) {
        this.mStartingIndex = this.mIndex;
        writeByte(type);
    }

    public void startWithSize(int type) {
        this.mStartingIndex = this.mIndex;
        writeByte(type);
        this.mIndex += 4;
    }

    public void endWithSize() {
        int size = this.mIndex - this.mStartingIndex;
        int currentIndex = this.mIndex;
        this.mIndex = this.mStartingIndex + 1;
        writeInt(size);
        this.mIndex = currentIndex;
    }

    public void reset(int expectedSize) {
        this.mIndex = 0;
        this.mStartingIndex = 0;
        this.mSize = 0;
        if (expectedSize >= this.mMaxSize) {
            resize(expectedSize);
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
        byte value = this.mBuffer[this.mIndex];
        this.mIndex++;
        return value == 1;
    }

    public int readByte() {
        int value = this.mBuffer[this.mIndex] & 255;
        this.mIndex++;
        return value;
    }

    public int readShort() {
        byte[] bArr = this.mBuffer;
        int i = this.mIndex;
        this.mIndex = i + 1;
        int v1 = (bArr[i] & 255) << 8;
        byte[] bArr2 = this.mBuffer;
        int i2 = this.mIndex;
        this.mIndex = i2 + 1;
        int v2 = (bArr2[i2] & 255) << 0;
        return v1 + v2;
    }

    public int peekInt() {
        int tmp = this.mIndex;
        int tmp2 = tmp + 1;
        int v1 = (this.mBuffer[tmp] & 255) << 24;
        int tmp3 = tmp2 + 1;
        int v2 = (this.mBuffer[tmp2] & 255) << 16;
        int tmp4 = tmp3 + 1;
        int v3 = (this.mBuffer[tmp3] & 255) << 8;
        int i = tmp4 + 1;
        int v4 = (this.mBuffer[tmp4] & 255) << 0;
        return v1 + v2 + v3 + v4;
    }

    public int readInt() {
        byte[] bArr = this.mBuffer;
        int i = this.mIndex;
        this.mIndex = i + 1;
        int v1 = (bArr[i] & 255) << 24;
        byte[] bArr2 = this.mBuffer;
        int i2 = this.mIndex;
        this.mIndex = i2 + 1;
        int v2 = (bArr2[i2] & 255) << 16;
        byte[] bArr3 = this.mBuffer;
        int i3 = this.mIndex;
        this.mIndex = i3 + 1;
        int v3 = (bArr3[i3] & 255) << 8;
        byte[] bArr4 = this.mBuffer;
        int i4 = this.mIndex;
        this.mIndex = i4 + 1;
        int v4 = (bArr4[i4] & 255) << 0;
        return v1 + v2 + v3 + v4;
    }

    public long readLong() {
        byte[] bArr = this.mBuffer;
        this.mIndex = this.mIndex + 1;
        long v1 = (bArr[r2] & 255) << 56;
        byte[] bArr2 = this.mBuffer;
        this.mIndex = this.mIndex + 1;
        long v2 = (bArr2[r6] & 255) << 48;
        byte[] bArr3 = this.mBuffer;
        this.mIndex = this.mIndex + 1;
        long v3 = (bArr3[r8] & 255) << 40;
        byte[] bArr4 = this.mBuffer;
        this.mIndex = this.mIndex + 1;
        long v4 = (bArr4[r10] & 255) << 32;
        byte[] bArr5 = this.mBuffer;
        this.mIndex = this.mIndex + 1;
        long v5 = (bArr5[r12] & 255) << 24;
        byte[] bArr6 = this.mBuffer;
        this.mIndex = this.mIndex + 1;
        long v6 = (bArr6[r14] & 255) << 16;
        byte[] bArr7 = this.mBuffer;
        this.mIndex = this.mIndex + 1;
        long v7 = (bArr7[r3] & 255) << 8;
        byte[] bArr8 = this.mBuffer;
        this.mIndex = this.mIndex + 1;
        long v8 = (bArr8[r3] & 255) << 0;
        return v1 + v2 + v3 + v4 + v5 + v6 + v7 + v8;
    }

    public float readFloat() {
        return Float.intBitsToFloat(readInt());
    }

    public double readDouble() {
        return Double.longBitsToDouble(readLong());
    }

    public byte[] readBuffer() {
        int count = readInt();
        byte[] b = Arrays.copyOfRange(this.mBuffer, this.mIndex, this.mIndex + count);
        this.mIndex += count;
        return b;
    }

    public byte[] readBuffer(int maxSize) {
        int count = readInt();
        if (count < 0 || count > maxSize) {
            throw new RuntimeException(
                    "attempt read a buff of invalid size 0 <= " + count + " > " + maxSize);
        }
        byte[] b = Arrays.copyOfRange(this.mBuffer, this.mIndex, this.mIndex + count);
        this.mIndex += count;
        return b;
    }

    public String readUTF8() {
        byte[] stringBuffer = readBuffer();
        return new String(stringBuffer);
    }

    public String readUTF8(int maxSize) {
        byte[] stringBuffer = readBuffer(maxSize);
        return new String(stringBuffer);
    }

    public void writeBoolean(boolean z) {
        resize(1);
        byte[] bArr = this.mBuffer;
        int i = this.mIndex;
        this.mIndex = i + 1;
        bArr[i] = z ? (byte) 1 : (byte) 0;
        this.mSize++;
    }

    public void writeByte(int value) {
        resize(1);
        byte[] bArr = this.mBuffer;
        int i = this.mIndex;
        this.mIndex = i + 1;
        bArr[i] = (byte) value;
        this.mSize++;
    }

    public void writeShort(int value) {
        resize(2);
        byte[] bArr = this.mBuffer;
        int i = this.mIndex;
        this.mIndex = i + 1;
        bArr[i] = (byte) ((value >>> 8) & 255);
        byte[] bArr2 = this.mBuffer;
        int i2 = this.mIndex;
        this.mIndex = i2 + 1;
        bArr2[i2] = (byte) (value & 255);
        this.mSize += 2;
    }

    public void writeInt(int value) {
        resize(4);
        byte[] bArr = this.mBuffer;
        int i = this.mIndex;
        this.mIndex = i + 1;
        bArr[i] = (byte) ((value >>> 24) & 255);
        byte[] bArr2 = this.mBuffer;
        int i2 = this.mIndex;
        this.mIndex = i2 + 1;
        bArr2[i2] = (byte) ((value >>> 16) & 255);
        byte[] bArr3 = this.mBuffer;
        int i3 = this.mIndex;
        this.mIndex = i3 + 1;
        bArr3[i3] = (byte) ((value >>> 8) & 255);
        byte[] bArr4 = this.mBuffer;
        int i4 = this.mIndex;
        this.mIndex = i4 + 1;
        bArr4[i4] = (byte) (value & 255);
        this.mSize += 4;
    }

    public void writeLong(long value) {
        resize(8);
        byte[] bArr = this.mBuffer;
        int i = this.mIndex;
        this.mIndex = i + 1;
        bArr[i] = (byte) ((value >>> 56) & 255);
        byte[] bArr2 = this.mBuffer;
        int i2 = this.mIndex;
        this.mIndex = i2 + 1;
        bArr2[i2] = (byte) ((value >>> 48) & 255);
        byte[] bArr3 = this.mBuffer;
        int i3 = this.mIndex;
        this.mIndex = i3 + 1;
        bArr3[i3] = (byte) ((value >>> 40) & 255);
        byte[] bArr4 = this.mBuffer;
        int i4 = this.mIndex;
        this.mIndex = i4 + 1;
        bArr4[i4] = (byte) ((value >>> 32) & 255);
        byte[] bArr5 = this.mBuffer;
        int i5 = this.mIndex;
        this.mIndex = i5 + 1;
        bArr5[i5] = (byte) ((value >>> 24) & 255);
        byte[] bArr6 = this.mBuffer;
        int i6 = this.mIndex;
        this.mIndex = i6 + 1;
        bArr6[i6] = (byte) ((value >>> 16) & 255);
        byte[] bArr7 = this.mBuffer;
        int i7 = this.mIndex;
        this.mIndex = i7 + 1;
        bArr7[i7] = (byte) ((value >>> 8) & 255);
        byte[] bArr8 = this.mBuffer;
        int i8 = this.mIndex;
        this.mIndex = i8 + 1;
        bArr8[i8] = (byte) (value & 255);
        this.mSize += 8;
    }

    public void writeFloat(float value) {
        writeInt(Float.floatToRawIntBits(value));
    }

    public void writeDouble(double value) {
        writeLong(Double.doubleToRawLongBits(value));
    }

    public void writeBuffer(byte[] b) {
        resize(b.length + 4);
        writeInt(b.length);
        for (byte b2 : b) {
            byte[] bArr = this.mBuffer;
            int i = this.mIndex;
            this.mIndex = i + 1;
            bArr[i] = b2;
        }
        int i2 = this.mSize;
        this.mSize = i2 + b.length;
    }

    public void writeUTF8(String content) {
        byte[] buffer = content.getBytes();
        writeBuffer(buffer);
    }
}
