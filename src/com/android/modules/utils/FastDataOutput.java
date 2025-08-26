package com.android.modules.utils;

import java.io.Closeable;
import java.io.DataOutput;
import java.io.Flushable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Objects;

/* loaded from: classes6.dex */
public class FastDataOutput implements DataOutput, Flushable, Closeable {
    protected static final int DEFAULT_BUFFER_SIZE = 32768;
    protected static final int MAX_UNSIGNED_SHORT = 65535;
    protected final byte[] mBuffer;
    protected final int mBufferCap;
    protected int mBufferPos;
    private OutputStream mOut;
    private final HashMap<String, Integer> mStringRefs = new HashMap<>();

    public FastDataOutput(OutputStream outputStream, int i) {
        if (i < 8) {
            throw new IllegalArgumentException();
        }
        byte[] bArrNewByteArray = newByteArray(i);
        this.mBuffer = bArrNewByteArray;
        this.mBufferCap = bArrNewByteArray.length;
        setOutput(outputStream);
    }

    public static FastDataOutput obtain(OutputStream outputStream) {
        return new FastDataOutput(outputStream, 32768);
    }

    public void release() {
        if (this.mBufferPos > 0) {
            throw new IllegalStateException("Lingering data, call flush() before releasing.");
        }
        this.mOut = null;
        this.mBufferPos = 0;
        this.mStringRefs.clear();
    }

    public byte[] newByteArray(int i) {
        return new byte[i];
    }

    protected void setOutput(OutputStream outputStream) {
        if (this.mOut != null) {
            throw new IllegalStateException("setOutput() called before calling release()");
        }
        this.mOut = (OutputStream) Objects.requireNonNull(outputStream);
        this.mBufferPos = 0;
        this.mStringRefs.clear();
    }

    protected void drain() throws IOException {
        int i = this.mBufferPos;
        if (i > 0) {
            this.mOut.write(this.mBuffer, 0, i);
            this.mBufferPos = 0;
        }
    }

    @Override // java.io.Flushable
    public void flush() throws IOException {
        drain();
        this.mOut.flush();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.mOut.close();
        release();
    }

    @Override // java.io.DataOutput
    public void write(int i) throws IOException {
        writeByte(i);
    }

    @Override // java.io.DataOutput
    public void write(byte[] bArr) throws IOException {
        write(bArr, 0, bArr.length);
    }

    @Override // java.io.DataOutput
    public void write(byte[] bArr, int i, int i2) throws IOException {
        int i3 = this.mBufferCap;
        if (i3 < i2) {
            drain();
            this.mOut.write(bArr, i, i2);
        } else {
            if (i3 - this.mBufferPos < i2) {
                drain();
            }
            System.arraycopy(bArr, i, this.mBuffer, this.mBufferPos, i2);
            this.mBufferPos += i2;
        }
    }

    @Override // java.io.DataOutput
    public void writeUTF(String str) throws IOException {
        int iCountBytes = (int) ModifiedUtf8.countBytes(str, false);
        if (iCountBytes > 65535) {
            throw new IOException("Modified UTF-8 length too large: " + iCountBytes);
        }
        int i = this.mBufferCap;
        int i2 = iCountBytes + 2;
        if (i >= i2) {
            if (i - this.mBufferPos < i2) {
                drain();
            }
            writeShort(iCountBytes);
            ModifiedUtf8.encode(this.mBuffer, this.mBufferPos, str);
            this.mBufferPos += iCountBytes;
            return;
        }
        byte[] bArrNewByteArray = newByteArray(iCountBytes + 1);
        ModifiedUtf8.encode(bArrNewByteArray, 0, str);
        writeShort(iCountBytes);
        write(bArrNewByteArray, 0, iCountBytes);
    }

    public void writeInternedUTF(String str) throws IOException {
        Integer num = this.mStringRefs.get(str);
        if (num != null) {
            writeShort(num.intValue());
            return;
        }
        writeShort(65535);
        writeUTF(str);
        int size = this.mStringRefs.size();
        Integer numValueOf = Integer.valueOf(size);
        numValueOf.getClass();
        if (size < 65535) {
            this.mStringRefs.put(str, numValueOf);
        }
    }

    @Override // java.io.DataOutput
    public void writeBoolean(boolean z) throws IOException {
        writeByte(z ? 1 : 0);
    }

    @Override // java.io.DataOutput
    public void writeByte(int i) throws IOException {
        if (this.mBufferCap - this.mBufferPos < 1) {
            drain();
        }
        byte[] bArr = this.mBuffer;
        int i2 = this.mBufferPos;
        this.mBufferPos = i2 + 1;
        bArr[i2] = (byte) (i & 255);
    }

    @Override // java.io.DataOutput
    public void writeShort(int i) throws IOException {
        if (this.mBufferCap - this.mBufferPos < 2) {
            drain();
        }
        byte[] bArr = this.mBuffer;
        int i2 = this.mBufferPos;
        int i3 = i2 + 1;
        this.mBufferPos = i3;
        bArr[i2] = (byte) ((i >> 8) & 255);
        this.mBufferPos = i2 + 2;
        bArr[i3] = (byte) (i & 255);
    }

    @Override // java.io.DataOutput
    public void writeChar(int i) throws IOException {
        writeShort((short) i);
    }

    @Override // java.io.DataOutput
    public void writeInt(int i) throws IOException {
        if (this.mBufferCap - this.mBufferPos < 4) {
            drain();
        }
        byte[] bArr = this.mBuffer;
        int i2 = this.mBufferPos;
        int i3 = i2 + 1;
        this.mBufferPos = i3;
        bArr[i2] = (byte) ((i >> 24) & 255);
        int i4 = i2 + 2;
        this.mBufferPos = i4;
        bArr[i3] = (byte) ((i >> 16) & 255);
        int i5 = i2 + 3;
        this.mBufferPos = i5;
        bArr[i4] = (byte) ((i >> 8) & 255);
        this.mBufferPos = i2 + 4;
        bArr[i5] = (byte) (i & 255);
    }

    @Override // java.io.DataOutput
    public void writeLong(long j) throws IOException {
        if (this.mBufferCap - this.mBufferPos < 8) {
            drain();
        }
        int i = (int) (j >> 32);
        byte[] bArr = this.mBuffer;
        int i2 = this.mBufferPos;
        int i3 = i2 + 1;
        this.mBufferPos = i3;
        bArr[i2] = (byte) ((i >> 24) & 255);
        int i4 = i2 + 2;
        this.mBufferPos = i4;
        bArr[i3] = (byte) ((i >> 16) & 255);
        int i5 = i2 + 3;
        this.mBufferPos = i5;
        bArr[i4] = (byte) ((i >> 8) & 255);
        int i6 = i2 + 4;
        this.mBufferPos = i6;
        bArr[i5] = (byte) (i & 255);
        int i7 = (int) j;
        int i8 = i2 + 5;
        this.mBufferPos = i8;
        bArr[i6] = (byte) ((i7 >> 24) & 255);
        int i9 = i2 + 6;
        this.mBufferPos = i9;
        bArr[i8] = (byte) ((i7 >> 16) & 255);
        int i10 = i2 + 7;
        this.mBufferPos = i10;
        bArr[i9] = (byte) ((i7 >> 8) & 255);
        this.mBufferPos = i2 + 8;
        bArr[i10] = (byte) (i7 & 255);
    }

    @Override // java.io.DataOutput
    public void writeFloat(float f) throws IOException {
        writeInt(Float.floatToIntBits(f));
    }

    @Override // java.io.DataOutput
    public void writeDouble(double d) throws IOException {
        writeLong(Double.doubleToLongBits(d));
    }

    @Override // java.io.DataOutput
    public void writeBytes(String str) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override // java.io.DataOutput
    public void writeChars(String str) throws IOException {
        throw new UnsupportedOperationException();
    }
}
