package android.app.backup;

import android.annotation.SystemApi;
import java.io.FileDescriptor;
import java.io.IOException;

/* loaded from: classes.dex */
public class BackupDataOutput {
    long mBackupWriter;
    private final long mQuota;
    private final int mTransportFlags;

    private static native long ctor(FileDescriptor fileDescriptor);

    private static native void dtor(long j);

    private static native void setKeyPrefix_native(long j, String str);

    private static native int writeEntityData_native(long j, byte[] bArr, int i);

    private static native int writeEntityHeader_native(long j, String str, int i);

    @SystemApi
    public BackupDataOutput(FileDescriptor fileDescriptor) {
        this(fileDescriptor, -1L, 0);
    }

    @SystemApi
    public BackupDataOutput(FileDescriptor fileDescriptor, long j) {
        this(fileDescriptor, j, 0);
    }

    public BackupDataOutput(FileDescriptor fileDescriptor, long j, int i) {
        fileDescriptor.getClass();
        this.mQuota = j;
        this.mTransportFlags = i;
        long jCtor = ctor(fileDescriptor);
        this.mBackupWriter = jCtor;
        if (jCtor != 0) {
            return;
        }
        throw new RuntimeException("Native initialization failed with fd=" + fileDescriptor);
    }

    public long getQuota() {
        return this.mQuota;
    }

    public int getTransportFlags() {
        return this.mTransportFlags;
    }

    public int writeEntityHeader(String str, int i) throws IOException {
        int iWriteEntityHeader_native = writeEntityHeader_native(this.mBackupWriter, str, i);
        if (iWriteEntityHeader_native >= 0) {
            return iWriteEntityHeader_native;
        }
        throw new IOException("result=0x" + Integer.toHexString(iWriteEntityHeader_native));
    }

    public int writeEntityData(byte[] bArr, int i) throws IOException {
        int iWriteEntityData_native = writeEntityData_native(this.mBackupWriter, bArr, i);
        if (iWriteEntityData_native >= 0) {
            return iWriteEntityData_native;
        }
        throw new IOException("result=0x" + Integer.toHexString(iWriteEntityData_native));
    }

    public void setKeyPrefix(String str) {
        setKeyPrefix_native(this.mBackupWriter, str);
    }

    protected void finalize() throws Throwable {
        try {
            dtor(this.mBackupWriter);
        } finally {
            super.finalize();
        }
    }
}
