package android.util.apk;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.DigestException;

/* loaded from: classes4.dex */
class ByteBufferDataSource implements DataSource {
    private final ByteBuffer mBuf;

    ByteBufferDataSource(ByteBuffer byteBuffer) {
        this.mBuf = byteBuffer.slice();
    }

    @Override // android.util.apk.DataSource
    public long size() {
        return this.mBuf.capacity();
    }

    @Override // android.util.apk.DataSource
    public void feedIntoDataDigester(DataDigester dataDigester, long j, int i) throws DigestException, IOException {
        ByteBuffer byteBufferSlice;
        synchronized (this.mBuf) {
            this.mBuf.position(0);
            int i2 = (int) j;
            this.mBuf.limit(i + i2);
            this.mBuf.position(i2);
            byteBufferSlice = this.mBuf.slice();
        }
        dataDigester.consume(byteBufferSlice);
    }
}
