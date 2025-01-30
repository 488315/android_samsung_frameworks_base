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
    public void write(byte[] buffer, int offset, int count) throws IOException {
        this.mOutputStream.write(buffer, offset, count);
        this.mCount += count;
    }

    @Override // java.io.OutputStream
    public void write(int oneByte) throws IOException {
        this.mOutputStream.write(oneByte);
        this.mCount++;
    }
}
