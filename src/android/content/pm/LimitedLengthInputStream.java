package android.content.pm;

import com.android.internal.util.ArrayUtils;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public class LimitedLengthInputStream extends FilterInputStream {
    private final long mEnd;
    private long mOffset;

    public LimitedLengthInputStream(InputStream inputStream, long j, long j2) throws IOException {
        super(inputStream);
        if (inputStream == null) {
            throw new IOException("in == null");
        }
        if (j < 0) {
            throw new IOException("offset < 0");
        }
        if (j2 < 0) {
            throw new IOException("length < 0");
        }
        if (j2 > Long.MAX_VALUE - j) {
            throw new IOException("offset + length > Long.MAX_VALUE");
        }
        this.mEnd = j2 + j;
        skip(j);
        this.mOffset = j;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public synchronized int read() throws IOException {
        long j = this.mOffset;
        if (j >= this.mEnd) {
            return -1;
        }
        this.mOffset = j + 1;
        return super.read();
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (this.mOffset >= this.mEnd) {
            return -1;
        }
        ArrayUtils.throwsIfOutOfBounds(bArr.length, i, i2);
        long j = this.mOffset;
        long j2 = i2;
        if (j > Long.MAX_VALUE - j2) {
            throw new IOException("offset out of bounds: " + this.mOffset + " + " + i2);
        }
        long j3 = j2 + j;
        long j4 = this.mEnd;
        if (j3 > j4) {
            i2 = (int) (j4 - j);
        }
        int i3 = super.read(bArr, i, i2);
        this.mOffset += i3;
        return i3;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }
}
