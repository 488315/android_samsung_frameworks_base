package com.android.server.backup.fullbackup;

import android.app.backup.IFullBackupRestoreObserver;
import android.os.RemoteException;
import android.util.Slog;

public abstract class FullBackupTask implements Runnable {
    public IFullBackupRestoreObserver mObserver;

    public FullBackupTask(IFullBackupRestoreObserver iFullBackupRestoreObserver) {
        this.mObserver = iFullBackupRestoreObserver;
    }

    public final void sendEndBackup() {
        IFullBackupRestoreObserver iFullBackupRestoreObserver = this.mObserver;
        if (iFullBackupRestoreObserver != null) {
            try {
                iFullBackupRestoreObserver.onEndBackup();
            } catch (RemoteException unused) {
                Slog.w("BackupManagerService", "full backup observer went away: endBackup");
                this.mObserver = null;
            }
        }
    }

    public final void sendStartBackup() {
        IFullBackupRestoreObserver iFullBackupRestoreObserver = this.mObserver;
        if (iFullBackupRestoreObserver != null) {
            try {
                iFullBackupRestoreObserver.onStartBackup();
            } catch (RemoteException unused) {
                Slog.w("BackupManagerService", "full backup observer went away: startBackup");
                this.mObserver = null;
            }
        }
    }
}
