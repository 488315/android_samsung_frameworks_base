package android.util.apk;

import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.DirectByteBuffer;

/* loaded from: classes4.dex */
class MemoryMappedFileDataSource implements DataSource {
    private static final long MEMORY_PAGE_SIZE_BYTES = Os.sysconf(OsConstants._SC_PAGESIZE);
    private final FileDescriptor mFd;
    private final long mFilePosition;
    private final long mSize;

    MemoryMappedFileDataSource(FileDescriptor fileDescriptor, long j, long j2) {
        this.mFd = fileDescriptor;
        this.mFilePosition = j;
        this.mSize = j2;
    }

    @Override // android.util.apk.DataSource
    public long size() {
        return this.mSize;
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0067 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // android.util.apk.DataSource
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void feedIntoDataDigester(DataDigester dataDigester, long j, int i) throws Throwable {
        long j2 = this.mFilePosition + j;
        long j3 = MEMORY_PAGE_SIZE_BYTES;
        long j4 = (j2 / j3) * j3;
        int i2 = (int) (j2 - j4);
        long j5 = i + i2;
        try {
            try {
                long jMmap = Os.mmap(0L, j5, OsConstants.PROT_READ, OsConstants.MAP_SHARED | OsConstants.MAP_POPULATE, this.mFd, j4);
                try {
                    dataDigester.consume(new DirectByteBuffer(i, jMmap + i2, this.mFd, (Runnable) null, true));
                    if (jMmap != 0) {
                        try {
                            Os.munmap(jMmap, j5);
                        } catch (ErrnoException unused) {
                        }
                    }
                } catch (ErrnoException e) {
                    e = e;
                    throw new IOException("Failed to mmap " + j5 + " bytes", e);
                }
            } catch (Throwable th) {
                th = th;
                if (0 != 0) {
                    try {
                        Os.munmap(0L, j5);
                    } catch (ErrnoException unused2) {
                    }
                }
                throw th;
            }
        } catch (ErrnoException e2) {
            e = e2;
        } catch (Throwable th2) {
            th = th2;
            if (0 != 0) {
            }
            throw th;
        }
    }
}
