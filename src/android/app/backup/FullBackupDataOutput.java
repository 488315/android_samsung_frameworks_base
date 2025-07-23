package android.app.backup;

import android.os.ParcelFileDescriptor;

/* loaded from: classes.dex */
public class FullBackupDataOutput {
    private final BackupDataOutput mData;
    private final long mQuota;
    private long mSize;
    private final int mTransportFlags;

    public long getQuota() {
        return this.mQuota;
    }

    public int getTransportFlags() {
        return this.mTransportFlags;
    }

    public FullBackupDataOutput(long j) {
        this.mData = null;
        this.mQuota = j;
        this.mSize = 0L;
        this.mTransportFlags = 0;
    }

    public FullBackupDataOutput(long j, int i) {
        this.mData = null;
        this.mQuota = j;
        this.mSize = 0L;
        this.mTransportFlags = i;
    }

    public FullBackupDataOutput(ParcelFileDescriptor parcelFileDescriptor, long j) {
        this.mData = new BackupDataOutput(parcelFileDescriptor.getFileDescriptor(), j, 0);
        this.mQuota = j;
        this.mTransportFlags = 0;
    }

    public FullBackupDataOutput(ParcelFileDescriptor parcelFileDescriptor, long j, int i) {
        this.mData = new BackupDataOutput(parcelFileDescriptor.getFileDescriptor(), j, i);
        this.mQuota = j;
        this.mTransportFlags = i;
    }

    public FullBackupDataOutput(ParcelFileDescriptor parcelFileDescriptor) {
        this(parcelFileDescriptor, -1L, 0);
    }

    public BackupDataOutput getData() {
        return this.mData;
    }

    public void addSize(long j) {
        if (j > 0) {
            this.mSize += j;
        }
    }

    public long getSize() {
        return this.mSize;
    }
}
