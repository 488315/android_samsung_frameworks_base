package com.samsung.android.graphics.spr.document;

import com.samsung.android.graphics.spr.document.shape.SprObjectBase;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public class SprInputStream {
    private DataInputStream in;
    public ArrayList<SprObjectBase> mAnimationObject;
    public short mMajorVersion;
    public short mMinorVersion;
    private long mPosition = 0;
    private long mMark = 0;

    public SprInputStream(InputStream inputStream) {
        this.in = new DataInputStream(inputStream);
    }

    public synchronized void mark(int i) {
        this.in.mark(i);
        this.mMark = this.mPosition;
    }

    public int read() throws IOException {
        int i = this.in.read();
        if (i >= 0) {
            this.mPosition++;
        }
        return i;
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        int i3 = this.in.read(bArr, i, i2);
        if (i3 > 0) {
            this.mPosition += i3;
        }
        return i3;
    }

    public synchronized void reset() throws IOException {
        if (!this.in.markSupported()) {
            throw new IOException("Mark not supported");
        }
        this.in.reset();
        this.mPosition = this.mMark;
    }

    public long skip(long j) throws IOException {
        long jSkip = this.in.skip(j);
        if (jSkip > 0) {
            this.mPosition += jSkip;
        }
        return jSkip;
    }

    public long getPosition() {
        return this.mPosition;
    }

    public int readInt() throws IOException {
        int i = this.in.readInt();
        this.mPosition += 4;
        return i;
    }

    public short readShort() throws IOException {
        short s = this.in.readShort();
        this.mPosition += 2;
        return s;
    }

    public float readFloat() throws IOException {
        float f = this.in.readFloat();
        this.mPosition += 4;
        return f;
    }

    public byte readByte() throws IOException {
        byte b = this.in.readByte();
        this.mPosition++;
        return b;
    }
}
