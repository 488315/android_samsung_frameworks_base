package android.app.backup;

import android.annotation.SystemApi;
import java.io.FileDescriptor;
import java.io.IOException;

/* loaded from: classes.dex */
public class BackupDataInput {
    long mBackupReader;
    private EntityHeader mHeader = new EntityHeader();
    private boolean mHeaderReady;

    private static native long ctor(FileDescriptor fileDescriptor);

    private static native void dtor(long j);

    private native int readEntityData_native(long j, byte[] bArr, int i, int i2);

    private native int readNextHeader_native(long j, EntityHeader entityHeader);

    private native int skipEntityData_native(long j);

    private static class EntityHeader {
        int dataSize;
        String key;

        private EntityHeader() {
        }
    }

    @SystemApi
    public BackupDataInput(FileDescriptor fileDescriptor) {
        fileDescriptor.getClass();
        long ctor = ctor(fileDescriptor);
        this.mBackupReader = ctor;
        if (ctor != 0) {
            return;
        }
        throw new RuntimeException("Native initialization failed with fd=" + fileDescriptor);
    }

    protected void finalize() throws Throwable {
        try {
            dtor(this.mBackupReader);
        } finally {
            super.finalize();
        }
    }

    public boolean readNextHeader() throws IOException {
        int readNextHeader_native = readNextHeader_native(this.mBackupReader, this.mHeader);
        if (readNextHeader_native == 0) {
            this.mHeaderReady = true;
            return true;
        }
        if (readNextHeader_native > 0) {
            this.mHeaderReady = false;
            return false;
        }
        this.mHeaderReady = false;
        throw new IOException("failed: 0x" + Integer.toHexString(readNextHeader_native));
    }

    public String getKey() {
        if (this.mHeaderReady) {
            return this.mHeader.key;
        }
        throw new IllegalStateException("Entity header not read");
    }

    public int getDataSize() {
        if (this.mHeaderReady) {
            return this.mHeader.dataSize;
        }
        throw new IllegalStateException("Entity header not read");
    }

    public int readEntityData(byte[] bArr, int i, int i2) throws IOException {
        if (this.mHeaderReady) {
            int readEntityData_native = readEntityData_native(this.mBackupReader, bArr, i, i2);
            if (readEntityData_native >= 0) {
                return readEntityData_native;
            }
            throw new IOException("result=0x" + Integer.toHexString(readEntityData_native));
        }
        throw new IllegalStateException("Entity header not read");
    }

    public void skipEntityData() throws IOException {
        if (this.mHeaderReady) {
            skipEntityData_native(this.mBackupReader);
            return;
        }
        throw new IllegalStateException("Entity header not read");
    }
}
