package com.android.modules.utils;

import java.io.Closeable;
import java.io.DataInput;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes5.dex */
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

    public FastDataInput(InputStream in, int bufferSize) {
        this.mIn = (InputStream) Objects.requireNonNull(in);
        if (bufferSize < 8) {
            throw new IllegalArgumentException();
        }
        this.mBuffer = newByteArray(bufferSize);
        this.mBufferCap = this.mBuffer.length;
    }

    public static FastDataInput obtain(InputStream in) {
        return new FastDataInput(in, 32768);
    }

    public void release() {
        this.mIn = null;
        this.mBufferPos = 0;
        this.mBufferLim = 0;
        this.mStringRefCount = 0;
    }

    public byte[] newByteArray(int bufferSize) {
        return new byte[bufferSize];
    }

    protected void setInput(InputStream in) {
        if (this.mIn != null) {
            throw new IllegalStateException("setInput() called before calling release()");
        }
        this.mIn = (InputStream) Objects.requireNonNull(in);
        this.mBufferPos = 0;
        this.mBufferLim = 0;
        this.mStringRefCount = 0;
    }

    protected void fill(int need) throws IOException {
        int remain = this.mBufferLim - this.mBufferPos;
        System.arraycopy(this.mBuffer, this.mBufferPos, this.mBuffer, 0, remain);
        this.mBufferPos = 0;
        this.mBufferLim = remain;
        int need2 = need - remain;
        while (need2 > 0) {
            int c = this.mIn.read(this.mBuffer, this.mBufferLim, this.mBufferCap - this.mBufferLim);
            if (c == -1) {
                throw new EOFException();
            }
            this.mBufferLim += c;
            need2 -= c;
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.mIn.close();
        release();
    }

    @Override // java.io.DataInput
    public void readFully(byte[] b) throws IOException {
        readFully(b, 0, b.length);
    }

    @Override // java.io.DataInput
    public void readFully(byte[] b, int off, int len) throws IOException {
        if (this.mBufferCap >= len) {
            if (this.mBufferLim - this.mBufferPos < len) {
                fill(len);
            }
            System.arraycopy(this.mBuffer, this.mBufferPos, b, off, len);
            this.mBufferPos += len;
            return;
        }
        int remain = this.mBufferLim - this.mBufferPos;
        System.arraycopy(this.mBuffer, this.mBufferPos, b, off, remain);
        this.mBufferPos += remain;
        int off2 = off + remain;
        int len2 = len - remain;
        while (len2 > 0) {
            int c = this.mIn.read(b, off2, len2);
            if (c == -1) {
                throw new EOFException();
            }
            off2 += c;
            len2 -= c;
        }
    }

    @Override // java.io.DataInput
    public String readUTF() throws IOException {
        int len = readUnsignedShort();
        if (this.mBufferCap > len) {
            if (this.mBufferLim - this.mBufferPos < len) {
                fill(len);
            }
            String res = ModifiedUtf8.decode(this.mBuffer, new char[len], this.mBufferPos, len);
            this.mBufferPos += len;
            return res;
        }
        byte[] tmp = newByteArray(len + 1);
        readFully(tmp, 0, len);
        return ModifiedUtf8.decode(tmp, new char[len], 0, len);
    }

    public String readInternedUTF() throws IOException {
        int ref = readUnsignedShort();
        if (ref == 65535) {
            String s = readUTF();
            if (this.mStringRefCount < 65535) {
                if (this.mStringRefCount == this.mStringRefs.length) {
                    this.mStringRefs =
                            (String[])
                                    Arrays.copyOf(
                                            this.mStringRefs,
                                            this.mStringRefCount + (this.mStringRefCount >> 1));
                }
                String[] strArr = this.mStringRefs;
                int i = this.mStringRefCount;
                this.mStringRefCount = i + 1;
                strArr[i] = s;
            }
            return s;
        }
        if (ref >= this.mStringRefs.length) {
            throw new IOException(
                    "Invalid interned string reference "
                            + ref
                            + " for "
                            + this.mStringRefs.length
                            + " interned strings");
        }
        return this.mStringRefs[ref];
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
        this.mBufferPos = i + 1;
        int i2 = (bArr[i] & 255) << 8;
        byte[] bArr2 = this.mBuffer;
        int i3 = this.mBufferPos;
        this.mBufferPos = i3 + 1;
        return (short) (i2 | ((bArr2[i3] & 255) << 0));
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
        this.mBufferPos = i + 1;
        int i2 = (bArr[i] & 255) << 24;
        byte[] bArr2 = this.mBuffer;
        int i3 = this.mBufferPos;
        this.mBufferPos = i3 + 1;
        int i4 = i2 | ((bArr2[i3] & 255) << 16);
        byte[] bArr3 = this.mBuffer;
        int i5 = this.mBufferPos;
        this.mBufferPos = i5 + 1;
        int i6 = i4 | ((bArr3[i5] & 255) << 8);
        byte[] bArr4 = this.mBuffer;
        int i7 = this.mBufferPos;
        this.mBufferPos = i7 + 1;
        return i6 | ((bArr4[i7] & 255) << 0);
    }

    @Override // java.io.DataInput
    public long readLong() throws IOException {
        if (this.mBufferLim - this.mBufferPos < 8) {
            fill(8);
        }
        byte[] bArr = this.mBuffer;
        int i = this.mBufferPos;
        this.mBufferPos = i + 1;
        int i2 = (bArr[i] & 255) << 24;
        byte[] bArr2 = this.mBuffer;
        int i3 = this.mBufferPos;
        this.mBufferPos = i3 + 1;
        int i4 = i2 | ((bArr2[i3] & 255) << 16);
        byte[] bArr3 = this.mBuffer;
        int i5 = this.mBufferPos;
        this.mBufferPos = i5 + 1;
        int i6 = i4 | ((bArr3[i5] & 255) << 8);
        byte[] bArr4 = this.mBuffer;
        int i7 = this.mBufferPos;
        this.mBufferPos = i7 + 1;
        int h = i6 | ((bArr4[i7] & 255) << 0);
        byte[] bArr5 = this.mBuffer;
        int i8 = this.mBufferPos;
        this.mBufferPos = i8 + 1;
        int i9 = (bArr5[i8] & 255) << 24;
        byte[] bArr6 = this.mBuffer;
        int i10 = this.mBufferPos;
        this.mBufferPos = i10 + 1;
        int i11 = i9 | ((bArr6[i10] & 255) << 16);
        byte[] bArr7 = this.mBuffer;
        int i12 = this.mBufferPos;
        this.mBufferPos = i12 + 1;
        int i13 = ((bArr7[i12] & 255) << 8) | i11;
        byte[] bArr8 = this.mBuffer;
        int i14 = this.mBufferPos;
        this.mBufferPos = i14 + 1;
        int l = i13 | ((bArr8[i14] & 255) << 0);
        return (h << 32) | (l & 4294967295L);
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
    public int skipBytes(int n) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override // java.io.DataInput
    public String readLine() throws IOException {
        throw new UnsupportedOperationException();
    }
}
