package android.util.apk;

import android.system.ErrnoException;
import android.system.Os;
import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.DigestException;

/* loaded from: classes4.dex */
class ReadFileDataSource implements DataSource {
    private static final int CHUNK_SIZE = 1048576;
    private final FileDescriptor mFd;
    private final long mFilePosition;
    private final long mSize;

    ReadFileDataSource(FileDescriptor fileDescriptor, long j, long j2) {
        this.mFd = fileDescriptor;
        this.mFilePosition = j;
        this.mSize = j2;
    }

    @Override // android.util.apk.DataSource
    public long size() {
        return this.mSize;
    }

    @Override // android.util.apk.DataSource
    public void feedIntoDataDigester(DataDigester dataDigester, long j, int i) throws IOException, DigestException {
        try {
            byte[] bArr = new byte[Math.min(i, 1048576)];
            long j2 = this.mFilePosition + j;
            long j3 = i + j2;
            long min = Math.min(i, 1048576);
            long j4 = j2;
            while (j4 < j3) {
                int pread = Os.pread(this.mFd, bArr, 0, (int) min, j4);
                dataDigester.consume(ByteBuffer.wrap(bArr, 0, pread));
                j4 += pread;
                min = Math.min(j3 - j4, 1048576L);
            }
        } catch (ErrnoException e) {
            throw new IOException(e);
        }
    }
}
