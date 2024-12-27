package com.android.server.backup;

import android.app.backup.IMemorySaverBackupRestoreObserver;
import android.os.RemoteException;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class BackupManagerYuva {
    public static BackupManagerYuva backupManagerYuva;
    public boolean isMemorySaverBackupFail;
    public boolean isMemorySaverRestoreFail;
    public String mBackupPackageName;
    public IMemorySaverBackupRestoreObserver mMemorySaverObserver;
    public String mRestorePackageName;

    public static BackupManagerYuva getInstanceBackupYuva() {
        if (backupManagerYuva == null) {
            BackupManagerYuva backupManagerYuva2 = new BackupManagerYuva();
            backupManagerYuva2.isMemorySaverBackupFail = false;
            backupManagerYuva2.isMemorySaverRestoreFail = false;
            backupManagerYuva2.mMemorySaverObserver = null;
            backupManagerYuva2.mBackupPackageName = null;
            backupManagerYuva2.mRestorePackageName = null;
            backupManagerYuva = backupManagerYuva2;
        }
        return backupManagerYuva;
    }

    public final void sendEndBackupCallback() {
        if (this.mMemorySaverObserver != null) {
            try {
                Slog.d("BackupManagerYuva", "full backup Completed " + this.mBackupPackageName);
                this.mMemorySaverObserver.onBackupCompleted(
                        this.mBackupPackageName, this.isMemorySaverBackupFail ^ true);
                this.mBackupPackageName = null;
            } catch (RemoteException unused) {
                Slog.w("BackupManagerYuva", "full backup observer went away: EndBackup");
                this.mMemorySaverObserver = null;
            }
        }
    }

    public final void sendEndRestoreCallback() {
        if (this.mMemorySaverObserver != null) {
            try {
                Slog.d("BackupManagerYuva", "restore completed " + this.mRestorePackageName);
                this.mMemorySaverObserver.onRestoreCompleted(
                        this.mRestorePackageName, this.isMemorySaverRestoreFail ^ true);
                this.mRestorePackageName = null;
            } catch (RemoteException unused) {
                Slog.w("BackupManagerYuva", "full restore observer went away: endRestore");
                this.mMemorySaverObserver = null;
            }
        }
    }

    public final void sendStartBackupCallback(String str) {
        this.mBackupPackageName = str;
        if (this.mMemorySaverObserver != null) {
            try {
                Slog.d("BackupManagerYuva", "backup started " + this.mBackupPackageName);
                this.mMemorySaverObserver.onStartBackup(this.mBackupPackageName);
            } catch (RemoteException unused) {
                Slog.w("BackupManagerYuva", "full backup observer went away: startBackup");
                this.mMemorySaverObserver = null;
            }
        }
    }
}
