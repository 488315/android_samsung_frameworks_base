package com.android.modules.utils;

import java.io.Closeable;
import java.io.DataInput;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes6.dex */
public class FastDataInput implements DataInput, Closeable {
    protected static final int DEFAULT_BUFFER_SIZE = 32768;
    protected static final int MAX_UNSIGNED_SHORT = 65535;
    protected final byte[] mBuffer;
    protected final int mBufferCap;
    protected int mBufferLim;
    protected int mBufferPos;
    private InputStream mIn;
    private int mStringRefCount = 0;
    private String[] mStringRefs = new String[32];

    public FastDataInput(InputStream inputStream, int i) {
        this.mIn = (InputStream) Objects.requireNonNull(inputStream);
        if (i < 8) {
            throw new IllegalArgumentException();
        }
        byte[] newByteArray = newByteArray(i);
        this.mBuffer = newByteArray;
        this.mBufferCap = newByteArray.length;
    }

    public static FastDataInput obtain(InputStream inputStream) {
        return new FastDataInput(inputStream, 32768);
    }

    public void release() {
        this.mIn = null;
        this.mBufferPos = 0;
        this.mBufferLim = 0;
        this.mStringRefCount = 0;
    }

    public byte[] newByteArray(int i) {
        return new byte[i];
    }

    protected void setInput(InputStream inputStream) {
        if (this.mIn != null) {
            throw new IllegalStateException("setInput() called before calling release()");
        }
        this.mIn = (InputStream) Objects.requireNonNull(inputStream);
        this.mBufferPos = 0;
        this.mBufferLim = 0;
        this.mStringRefCount = 0;
    }

    protected void fill(int i) throws IOException {
        int i2 = this.mBufferLim;
        int i3 = this.mBufferPos;
        int i4 = i2 - i3;
        byte[] bArr = this.mBuffer;
        System.arraycopy(bArr, i3, bArr, 0, i4);
        this.mBufferPos = 0;
        this.mBufferLim = i4;
        while (true) {
            i -= i4;
            if (i <= 0) {
                return;
            }
            InputStream inputStream = this.mIn;
            byte[] bArr2 = this.mBuffer;
            int i5 = this.mBufferLim;
            i4 = inputStream.read(bArr2, i5, this.mBufferCap - i5);
            if (i4 == -1) {
                throw new EOFException();
            }
            this.mBufferLim += i4;
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.mIn.close();
        release();
    }

    @Override // java.io.DataInput
    public void readFully(byte[] bArr) throws IOException {
        readFully(bArr, 0, bArr.length);
    }

    @Override // java.io.DataInput
    public void readFully(byte[] bArr, int i, int i2) throws IOException {
        if (this.mBufferCap >= i2) {
            if (this.mBufferLim - this.mBufferPos < i2) {
                fill(i2);
            }
            System.arraycopy(this.mBuffer, this.mBufferPos, bArr, i, i2);
            this.mBufferPos += i2;
            return;
        }
        int i3 = this.mBufferLim;
        int i4 = this.mBufferPos;
        int i5 = i3 - i4;
        System.arraycopy(this.mBuffer, i4, bArr, i, i5);
        this.mBufferPos += i5;
        do {
            i += i5;
            i2 -= i5;
            if (i2 <= 0) {
                return;
            } else {
                i5 = this.mIn.read(bArr, i, i2);
            }
        } while (i5 != -1);
        throw new EOFException();
    }

    @Override // java.io.DataInput
    public String readUTF() throws IOException {
        int readUnsignedShort = readUnsignedShort();
        if (this.mBufferCap > readUnsignedShort) {
            if (this.mBufferLim - this.mBufferPos < readUnsignedShort) {
                fill(readUnsignedShort);
            }
            String decode = ModifiedUtf8.decode(this.mBuffer, new char[readUnsignedShort], this.mBufferPos, readUnsignedShort);
            this.mBufferPos += readUnsignedShort;
            return decode;
        }
        byte[] newByteArray = newByteArray(readUnsignedShort + 1);
        readFully(newByteArray, 0, readUnsignedShort);
        return ModifiedUtf8.decode(newByteArray, new char[readUnsignedShort], 0, readUnsignedShort);
    }

    public String readInternedUTF() throws IOException {
        int readUnsignedShort = readUnsignedShort();
        if (readUnsignedShort == 65535) {
            String readUTF = readUTF();
            int i = this.mStringRefCount;
            if (i < 65535) {
                String[] strArr = this.mStringRefs;
                if (i == strArr.length) {
                    this.mStringRefs = (String[]) Arrays.copyOf(strArr, i + (i >> 1));
                }
                String[] strArr2 = this.mStringRefs;
                int i2 = this.mStringRefCount;
                this.mStringRefCount = i2 + 1;
                strArr2[i2] = readUTF;
            }
            return readUTF;
        }
        String[] strArr3 = this.mStringRefs;
        if (readUnsignedShort >= strArr3.length) {
            throw new IOException("Invalid interned string reference " + readUnsignedShort + " for " + this.mStringRefs.length + " interned strings");
        }
        return strArr3[readUnsignedShort];
    }

    @Override // java.io.DataInput
    public boolean readBoolean() throws IOException {
        return readByte() != 0;
    }

    public byte peekByte() throws IOException {
        if (this.mBufferLim - this.mBufferPos < 1) {
            fill(1);
        }
        return this.mBuffer[this.mBufferPos];
    }

    @Override // java.io.DataInput
    public byte readByte() throws IOException {
        if (this.mBufferLim - this.mBufferPos < 1) {
            fill(1);
        }
        byte[] bArr = this.mBuffer;
        int i = this.mBufferPos;
        this.mBufferPos = i + 1;
        return bArr[i];
    }

    @Override // java.io.DataInput
    public int readUnsignedByte() throws IOException {
        return Byte.toUnsignedInt(readByte());
    }

    @Override // java.io.DataInput
    public short readShort() throws IOException {
        if (this.mBufferLim - this.mBufferPos < 2) {
            fill(2);
        }
        byte[] bArr = this.mBuffer;
        int i = this.mBufferPos;
        int i2 = i + 1;
        this.mBufferPos = i2;
        int i3 = (bArr[i] & 255) << 8;
        this.mBufferPos = i + 2;
        return (short) ((bArr[i2] & 255) | i3);
    }

    @Override // java.io.DataInput
    public int readUnsignedShort() throws IOException {
        return Short.toUnsignedInt(readShort());
    }

    @Override // java.io.DataInput
    public char readChar() throws IOException {
        return (char) readShort();
    }

    @Override // java.io.DataInput
    public int readInt() throws IOException {
        if (this.mBufferLim - this.mBufferPos < 4) {
            fill(4);
        }
        byte[] bArr = this.mBuffer;
        int i = this.mBufferPos;
        int i2 = i + 1;
        this.mBufferPos = i2;
        int i3 = (bArr[i] & 255) << 24;
        int i4 = i + 2;
        this.mBufferPos = i4;
        int i5 = ((bArr[i2] & 255) << 16) | i3;
        int i6 = i + 3;
        this.mBufferPos = i6;
        int i7 = i5 | ((bArr[i4] & 255) << 8);
        this.mBufferPos = i + 4;
        return (bArr[i6] & 255) | i7;
    }

    @Override // java.io.DataInput
    public long readLong() throws IOException {
        if (this.mBufferLim - this.mBufferPos < 8) {
            fill(8);
        }
        byte[] bArr = this.mBuffer;
        int i = this.mBufferPos;
        int i2 = i + 1;
        this.mBufferPos = i2;
        int i3 = (bArr[i] & 255) << 24;
        int i4 = i + 2;
        this.mBufferPos = i4;
        int i5 = ((bArr[i2] & 255) << 16) | i3;
        int i6 = i + 3;
        this.mBufferPos = i6;
        int i7 = i5 | ((bArr[i4] & 255) << 8);
        int i8 = i + 4;
        this.mBufferPos = i8;
        int i9 = i7 | (bArr[i6] & 255);
        int i10 = i + 5;
        this.mBufferPos = i10;
        int i11 = (bArr[i8] & 255) << 24;
        int i12 = i + 6;
        this.mBufferPos = i12;
        int i13 = ((bArr[i10] & 255) << 16) | i11;
        this.mBufferPos = i + 7;
        int i14 = i13 | ((bArr[i12] & 255) << 8);
        this.mBufferPos = i + 8;
        return (i9 << 32) | (((bArr[r5] & 255) | i14) & 4294967295L);
    }

    @Override // java.io.DataInput
    public float readFloat() throws IOException {
        return Float.intBitsToFloat(readInt());
    }

    @Override // java.io.DataInput
    public double readDouble() throws IOException {
        return Double.longBitsToDouble(readLong());
    }

    @Override // java.io.DataInput
    public int skipBytes(int i) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override // java.io.DataInput
    public String readLine() throws IOException {
        throw new UnsupportedOperationException();
    }
}
