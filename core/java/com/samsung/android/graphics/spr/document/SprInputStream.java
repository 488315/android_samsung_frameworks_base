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

    public SprInputStream(InputStream in) {
        this.in = new DataInputStream(in);
    }

    public synchronized void mark(int readlimit) {
        this.in.mark(readlimit);
        this.mMark = this.mPosition;
    }

    public int read() throws IOException {
        int v = this.in.read();
        if (v >= 0) {
            this.mPosition++;
        }
        return v;
    }

    public int read(byte[] buffer, int byteOffset, int byteCount) throws IOException {
        int n = this.in.read(buffer, byteOffset, byteCount);
        if (n > 0) {
            this.mPosition += n;
        }
        return n;
    }

    public synchronized void reset() throws IOException {
        if (!this.in.markSupported()) {
            throw new IOException("Mark not supported");
        }
        this.in.reset();
        this.mPosition = this.mMark;
    }

    public long skip(long byteCount) throws IOException {
        long n = this.in.skip(byteCount);
        if (n > 0) {
            this.mPosition += n;
        }
        return n;
    }

    public long getPosition() {
        return this.mPosition;
    }

    public int readInt() throws IOException {
        int value = this.in.readInt();
        this.mPosition += 4;
        return value;
    }

    public short readShort() throws IOException {
        short value = this.in.readShort();
        this.mPosition += 2;
        return value;
    }

    public float readFloat() throws IOException {
        float value = this.in.readFloat();
        this.mPosition += 4;
        return value;
    }

    public byte readByte() throws IOException {
        byte value = this.in.readByte();
        this.mPosition++;
        return value;
    }
}
