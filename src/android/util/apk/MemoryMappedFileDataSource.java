package android.util.apk;

import android.system.Os;
import android.system.OsConstants;
import java.io.FileDescriptor;

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

    /* JADX WARN: Removed duplicated region for block: B:25:0x0067 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // android.util.apk.DataSource
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void feedIntoDataDigester(android.util.apk.DataDigester r20, long r21, int r23) throws java.io.IOException, java.security.DigestException {
        /*
            r19 = this;
            r0 = r19
            java.lang.String r1 = "Failed to mmap "
            long r2 = r0.mFilePosition
            long r2 = r2 + r21
            long r4 = android.util.apk.MemoryMappedFileDataSource.MEMORY_PAGE_SIZE_BYTES
            long r6 = r2 / r4
            long r15 = r6 * r4
            long r2 = r2 - r15
            int r2 = (int) r2
            int r3 = r23 + r2
            long r10 = (long) r3
            r3 = 0
            int r12 = android.system.OsConstants.PROT_READ     // Catch: java.lang.Throwable -> L46 android.system.ErrnoException -> L49
            int r5 = android.system.OsConstants.MAP_SHARED     // Catch: java.lang.Throwable -> L46 android.system.ErrnoException -> L49
            int r6 = android.system.OsConstants.MAP_POPULATE     // Catch: java.lang.Throwable -> L46 android.system.ErrnoException -> L49
            r13 = r5 | r6
            java.io.FileDescriptor r14 = r0.mFd     // Catch: java.lang.Throwable -> L46 android.system.ErrnoException -> L49
            r8 = 0
            long r5 = android.system.Os.mmap(r8, r10, r12, r13, r14, r15)     // Catch: java.lang.Throwable -> L46 android.system.ErrnoException -> L49
            java.nio.DirectByteBuffer r12 = new java.nio.DirectByteBuffer     // Catch: android.system.ErrnoException -> L44 java.lang.Throwable -> L62
            long r7 = (long) r2     // Catch: android.system.ErrnoException -> L44 java.lang.Throwable -> L62
            long r14 = r5 + r7
            java.io.FileDescriptor r0 = r0.mFd     // Catch: android.system.ErrnoException -> L44 java.lang.Throwable -> L62
            r17 = 0
            r18 = 1
            r13 = r23
            r16 = r0
            r12.<init>(r13, r14, r16, r17, r18)     // Catch: android.system.ErrnoException -> L44 java.lang.Throwable -> L62
            r0 = r20
            r0.consume(r12)     // Catch: android.system.ErrnoException -> L44 java.lang.Throwable -> L62
            int r0 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r0 == 0) goto L43
            android.system.Os.munmap(r5, r10)     // Catch: android.system.ErrnoException -> L43
        L43:
            return
        L44:
            r0 = move-exception
            goto L4b
        L46:
            r0 = move-exception
            r5 = r3
            goto L63
        L49:
            r0 = move-exception
            r5 = r3
        L4b:
            java.io.IOException r2 = new java.io.IOException     // Catch: java.lang.Throwable -> L62
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L62
            r7.<init>(r1)     // Catch: java.lang.Throwable -> L62
            r7.append(r10)     // Catch: java.lang.Throwable -> L62
            java.lang.String r1 = " bytes"
            r7.append(r1)     // Catch: java.lang.Throwable -> L62
            java.lang.String r1 = r7.toString()     // Catch: java.lang.Throwable -> L62
            r2.<init>(r1, r0)     // Catch: java.lang.Throwable -> L62
            throw r2     // Catch: java.lang.Throwable -> L62
        L62:
            r0 = move-exception
        L63:
            int r1 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r1 == 0) goto L6a
            android.system.Os.munmap(r5, r10)     // Catch: android.system.ErrnoException -> L6a
        L6a:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.util.apk.MemoryMappedFileDataSource.feedIntoDataDigester(android.util.apk.DataDigester, long, int):void");
    }
}
