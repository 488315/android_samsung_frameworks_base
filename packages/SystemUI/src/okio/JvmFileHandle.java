package okio;

import java.io.RandomAccessFile;

/* loaded from: classes4.dex */
public final class JvmFileHandle extends FileHandle {
    public final RandomAccessFile randomAccessFile;

    public JvmFileHandle(boolean z, RandomAccessFile randomAccessFile) {
        super(z);
        this.randomAccessFile = randomAccessFile;
    }

    @Override // okio.FileHandle
    public final synchronized void protectedClose() {
        this.randomAccessFile.close();
    }

    @Override // okio.FileHandle
    public final synchronized void protectedFlush() {
        this.randomAccessFile.getFD().sync();
    }

    @Override // okio.FileHandle
    public final synchronized int protectedRead(long j, byte[] bArr, int i, int i2) {
        this.randomAccessFile.seek(j);
        int i3 = 0;
        while (true) {
            if (i3 >= i2) {
                break;
            }
            int i4 = this.randomAccessFile.read(bArr, i, i2 - i3);
            if (i4 != -1) {
                i3 += i4;
            } else if (i3 == 0) {
                return -1;
            }
        }
        return i3;
    }

    @Override // okio.FileHandle
    public final synchronized long protectedSize() {
        return this.randomAccessFile.length();
    }

    @Override // okio.FileHandle
    public final synchronized void protectedWrite(long j, byte[] bArr, int i, int i2) {
        this.randomAccessFile.seek(j);
        this.randomAccessFile.write(bArr, i, i2);
    }
}
