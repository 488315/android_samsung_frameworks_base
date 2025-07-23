package android.util.sysfwutil;

import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes4.dex */
public class CountingOutputStream extends OutputStream {
    private long mCount;
    private final OutputStream mOutputStream;

    public CountingOutputStream(OutputStream outputStream) {
        this.mOutputStream = outputStream;
    }

    public long getCount() {
        return this.mCount;
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        this.mOutputStream.write(bArr, i, i2);
        this.mCount += i2;
    }

    @Override // java.io.OutputStream
    public void write(int i) throws IOException {
        this.mOutputStream.write(i);
        this.mCount++;
    }
}
