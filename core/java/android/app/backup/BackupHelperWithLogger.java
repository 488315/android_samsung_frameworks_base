package android.app.backup;

import android.os.ParcelFileDescriptor;

public abstract class BackupHelperWithLogger implements BackupHelper {
    private boolean mIsLoggerSet = false;
    private BackupRestoreEventLogger mLogger;

    @Override // android.app.backup.BackupHelper
    public abstract void performBackup(
            ParcelFileDescriptor parcelFileDescriptor,
            BackupDataOutput backupDataOutput,
            ParcelFileDescriptor parcelFileDescriptor2);

    @Override // android.app.backup.BackupHelper
    public abstract void restoreEntity(BackupDataInputStream backupDataInputStream);

    @Override // android.app.backup.BackupHelper
    public abstract void writeNewStateDescription(ParcelFileDescriptor parcelFileDescriptor);

    public BackupRestoreEventLogger getLogger() {
        return this.mLogger;
    }

    public void setLogger(BackupRestoreEventLogger logger) {
        this.mLogger = logger;
        this.mIsLoggerSet = true;
    }

    public boolean isLoggerSet() {
        return this.mIsLoggerSet;
    }
}
