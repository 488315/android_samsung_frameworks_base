package android.media;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes2.dex */
public final class ResampleInputStream extends InputStream {
    private static final String TAG = "ResampleInputStream";
    private static final int mFirLength = 29;
    private byte[] mBuf;
    private int mBufCount;
    private InputStream mInputStream;
    private final byte[] mOneByte = new byte[1];
    private final int mRateIn;
    private final int mRateOut;

    private static native void fir21(byte[] bArr, int i, byte[] bArr2, int i2, int i3);

    static {
        System.loadLibrary("media_jni");
    }

    public ResampleInputStream(InputStream inputStream, int i, int i2) {
        if (i != i2 * 2) {
            throw new IllegalArgumentException("only support 2:1 at the moment");
        }
        this.mInputStream = inputStream;
        this.mRateIn = 2;
        this.mRateOut = 1;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (read(this.mOneByte, 0, 1) == 1) {
            return this.mOneByte[0] & 255;
        }
        return -1;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (this.mInputStream == null) {
            throw new IllegalStateException("not open");
        }
        int i3 = i2 / 2;
        int i4 = (((this.mRateIn * i3) / this.mRateOut) + 29) * 2;
        byte[] bArr2 = this.mBuf;
        if (bArr2 == null) {
            this.mBuf = new byte[i4];
        } else if (i4 > bArr2.length) {
            byte[] bArr3 = new byte[i4];
            System.arraycopy(bArr2, 0, bArr3, 0, this.mBufCount);
            this.mBuf = bArr3;
        }
        while (true) {
            int i5 = this.mBufCount;
            int i6 = ((((i5 / 2) - 29) * this.mRateOut) / this.mRateIn) * 2;
            if (i6 <= 0) {
                InputStream inputStream = this.mInputStream;
                byte[] bArr4 = this.mBuf;
                int read = inputStream.read(bArr4, i5, bArr4.length - i5);
                if (read == -1) {
                    return -1;
                }
                this.mBufCount += read;
            } else {
                if (i6 >= i2) {
                    i6 = i3 * 2;
                }
                fir21(this.mBuf, 0, bArr, i, i6 / 2);
                int i7 = (this.mRateIn * i6) / this.mRateOut;
                int i8 = this.mBufCount - i7;
                this.mBufCount = i8;
                if (i8 > 0) {
                    byte[] bArr5 = this.mBuf;
                    System.arraycopy(bArr5, i7, bArr5, 0, i8);
                }
                return i6;
            }
        }
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        try {
            InputStream inputStream = this.mInputStream;
            if (inputStream != null) {
                inputStream.close();
            }
        } finally {
            this.mInputStream = null;
        }
    }

    protected void finalize() throws Throwable {
        if (this.mInputStream == null) {
            return;
        }
        close();
        throw new IllegalStateException("someone forgot to close ResampleInputStream");
    }
}
