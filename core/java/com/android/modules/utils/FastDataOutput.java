package com.android.modules.utils;

import java.io.Closeable;
import java.io.DataOutput;
import java.io.Flushable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Objects;

/* loaded from: classes5.dex */
public class FastDataOutput implements DataOutput, Flushable, Closeable {
    protected static final int DEFAULT_BUFFER_SIZE = 32768;
    protected static final int MAX_UNSIGNED_SHORT = 65535;
    protected final byte[] mBuffer;
    protected final int mBufferCap;
    protected int mBufferPos;
    private OutputStream mOut;
    private final HashMap<String, Integer> mStringRefs = new HashMap<>();

    public FastDataOutput(OutputStream out, int bufferSize) {
        if (bufferSize < 8) {
            throw new IllegalArgumentException();
        }
        this.mBuffer = newByteArray(bufferSize);
        this.mBufferCap = this.mBuffer.length;
        setOutput(out);
    }

    public static FastDataOutput obtain(OutputStream out) {
        return new FastDataOutput(out, 32768);
    }

    public void release() {
        if (this.mBufferPos > 0) {
            throw new IllegalStateException("Lingering data, call flush() before releasing.");
        }
        this.mOut = null;
        this.mBufferPos = 0;
        this.mStringRefs.clear();
    }

    public byte[] newByteArray(int bufferSize) {
        return new byte[bufferSize];
    }

    protected void setOutput(OutputStream out) {
        if (this.mOut != null) {
            throw new IllegalStateException("setOutput() called before calling release()");
        }
        this.mOut = (OutputStream) Objects.requireNonNull(out);
        this.mBufferPos = 0;
        this.mStringRefs.clear();
    }

    protected void drain() throws IOException {
        if (this.mBufferPos > 0) {
            this.mOut.write(this.mBuffer, 0, this.mBufferPos);
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
    public void write(int b) throws IOException {
        writeByte(b);
    }

    @Override // java.io.DataOutput
    public void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    @Override // java.io.DataOutput
    public void write(byte[] b, int off, int len) throws IOException {
        if (this.mBufferCap < len) {
            drain();
            this.mOut.write(b, off, len);
        } else {
            if (this.mBufferCap - this.mBufferPos < len) {
                drain();
            }
            System.arraycopy(b, off, this.mBuffer, this.mBufferPos, len);
            this.mBufferPos += len;
        }
    }

    @Override // java.io.DataOutput
    public void writeUTF(String s) throws IOException {
        int len = (int) ModifiedUtf8.countBytes(s, false);
        if (len > 65535) {
            throw new IOException("Modified UTF-8 length too large: " + len);
        }
        if (this.mBufferCap >= len + 2) {
            if (this.mBufferCap - this.mBufferPos < len + 2) {
                drain();
            }
            writeShort(len);
            ModifiedUtf8.encode(this.mBuffer, this.mBufferPos, s);
            this.mBufferPos += len;
            return;
        }
        byte[] tmp = newByteArray(len + 1);
        ModifiedUtf8.encode(tmp, 0, s);
        writeShort(len);
        write(tmp, 0, len);
    }

    public void writeInternedUTF(String s) throws IOException {
        Integer ref = this.mStringRefs.get(s);
        if (ref != null) {
            writeShort(ref.intValue());
            return;
        }
        writeShort(65535);
        writeUTF(s);
        Integer ref2 = Integer.valueOf(this.mStringRefs.size());
        if (ref2.intValue() < 65535) {
            this.mStringRefs.put(s, ref2);
        }
    }

    @Override // java.io.DataOutput
    public void writeBoolean(boolean z) throws IOException {
        writeByte(z ? 1 : 0);
    }

    @Override // java.io.DataOutput
    public void writeByte(int v) throws IOException {
        if (this.mBufferCap - this.mBufferPos < 1) {
            drain();
        }
        byte[] bArr = this.mBuffer;
        int i = this.mBufferPos;
        this.mBufferPos = i + 1;
        bArr[i] = (byte) ((v >> 0) & 255);
    }

    @Override // java.io.DataOutput
    public void writeShort(int v) throws IOException {
        if (this.mBufferCap - this.mBufferPos < 2) {
            drain();
        }
        byte[] bArr = this.mBuffer;
        int i = this.mBufferPos;
        this.mBufferPos = i + 1;
        bArr[i] = (byte) ((v >> 8) & 255);
        byte[] bArr2 = this.mBuffer;
        int i2 = this.mBufferPos;
        this.mBufferPos = i2 + 1;
        bArr2[i2] = (byte) ((v >> 0) & 255);
    }

    @Override // java.io.DataOutput
    public void writeChar(int v) throws IOException {
        writeShort((short) v);
    }

    @Override // java.io.DataOutput
    public void writeInt(int v) throws IOException {
        if (this.mBufferCap - this.mBufferPos < 4) {
            drain();
        }
        byte[] bArr = this.mBuffer;
        int i = this.mBufferPos;
        this.mBufferPos = i + 1;
        bArr[i] = (byte) ((v >> 24) & 255);
        byte[] bArr2 = this.mBuffer;
        int i2 = this.mBufferPos;
        this.mBufferPos = i2 + 1;
        bArr2[i2] = (byte) ((v >> 16) & 255);
        byte[] bArr3 = this.mBuffer;
        int i3 = this.mBufferPos;
        this.mBufferPos = i3 + 1;
        bArr3[i3] = (byte) ((v >> 8) & 255);
        byte[] bArr4 = this.mBuffer;
        int i4 = this.mBufferPos;
        this.mBufferPos = i4 + 1;
        bArr4[i4] = (byte) ((v >> 0) & 255);
    }

    @Override // java.io.DataOutput
    public void writeLong(long v) throws IOException {
        if (this.mBufferCap - this.mBufferPos < 8) {
            drain();
        }
        int i = (int) (v >> 32);
        byte[] bArr = this.mBuffer;
        int i2 = this.mBufferPos;
        this.mBufferPos = i2 + 1;
        bArr[i2] = (byte) ((i >> 24) & 255);
        byte[] bArr2 = this.mBuffer;
        int i3 = this.mBufferPos;
        this.mBufferPos = i3 + 1;
        bArr2[i3] = (byte) ((i >> 16) & 255);
        byte[] bArr3 = this.mBuffer;
        int i4 = this.mBufferPos;
        this.mBufferPos = i4 + 1;
        bArr3[i4] = (byte) ((i >> 8) & 255);
        byte[] bArr4 = this.mBuffer;
        int i5 = this.mBufferPos;
        this.mBufferPos = i5 + 1;
        bArr4[i5] = (byte) ((i >> 0) & 255);
        int i6 = (int) v;
        byte[] bArr5 = this.mBuffer;
        int i7 = this.mBufferPos;
        this.mBufferPos = i7 + 1;
        bArr5[i7] = (byte) ((i6 >> 24) & 255);
        byte[] bArr6 = this.mBuffer;
        int i8 = this.mBufferPos;
        this.mBufferPos = i8 + 1;
        bArr6[i8] = (byte) ((i6 >> 16) & 255);
        byte[] bArr7 = this.mBuffer;
        int i9 = this.mBufferPos;
        this.mBufferPos = i9 + 1;
        bArr7[i9] = (byte) ((i6 >> 8) & 255);
        byte[] bArr8 = this.mBuffer;
        int i10 = this.mBufferPos;
        this.mBufferPos = i10 + 1;
        bArr8[i10] = (byte) ((i6 >> 0) & 255);
    }

    @Override // java.io.DataOutput
    public void writeFloat(float v) throws IOException {
        writeInt(Float.floatToIntBits(v));
    }

    @Override // java.io.DataOutput
    public void writeDouble(double v) throws IOException {
        writeLong(Double.doubleToLongBits(v));
    }

    @Override // java.io.DataOutput
    public void writeBytes(String s) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override // java.io.DataOutput
    public void writeChars(String s) throws IOException {
        throw new UnsupportedOperationException();
    }
}
