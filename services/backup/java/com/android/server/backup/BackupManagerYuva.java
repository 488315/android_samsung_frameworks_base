package com.android.server.backup;

import android.app.backup.IMemorySaverBackupRestoreObserver;
import android.os.RemoteException;
import android.util.Slog;
import com.samsung.android.feature.SemCscFeature;

/* loaded from: classes.dex */
public class BackupManagerYuva {
    public static BackupManagerYuva backupManagerYuva;
    public static final String valueCscYuva = SemCscFeature.getInstance().getString("CscFeature_Common_ConfigYuva");
    public boolean isMemorySaverBackupFail = false;
    public boolean isMemorySaverRestoreFail = false;
    public boolean isSupported = false;
    public IMemorySaverBackupRestoreObserver mMemorySaverObserver = null;
    public String mBackupPackageName = null;
    public String mRestorePackageName = null;

    public static BackupManagerYuva getInstanceBackupYuva() {
        if (backupManagerYuva == null) {
            backupManagerYuva = new BackupManagerYuva();
        }
        return backupManagerYuva;
    }

    public void setMemorySaverObserver(IMemorySaverBackupRestoreObserver iMemorySaverBackupRestoreObserver) {
        this.mMemorySaverObserver = iMemorySaverBackupRestoreObserver;
        resetBackupRestoreState();
    }

    public void resetBackupRestoreState() {
        this.isMemorySaverRestoreFail = false;
        this.isMemorySaverBackupFail = false;
        this.mBackupPackageName = null;
        this.mRestorePackageName = null;
    }

    public void sendStartBackupCallback(String str) {
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

    public void sendEndBackupCallback() {
        if (this.mMemorySaverObserver != null) {
            try {
                Slog.d("BackupManagerYuva", "full backup Completed " + this.mBackupPackageName);
                this.mMemorySaverObserver.onBackupCompleted(this.mBackupPackageName, !this.isMemorySaverBackupFail);
                this.mBackupPackageName = null;
            } catch (RemoteException unused) {
                Slog.w("BackupManagerYuva", "full backup observer went away: EndBackup");
                this.mMemorySaverObserver = null;
            }
        }
    }

    public void sendStartRestoreCallback(String str) {
        this.mRestorePackageName = str;
        if (this.mMemorySaverObserver != null) {
            try {
                Slog.d("BackupManagerYuva", "restore started " + this.mRestorePackageName);
                this.mMemorySaverObserver.onRestoreStart(this.mRestorePackageName);
            } catch (RemoteException unused) {
                Slog.w("BackupManagerYuva", "full restore observer went away: startRestore");
                this.mMemorySaverObserver = null;
            }
        }
    }

    public void sendEndRestoreCallback() {
        if (this.mMemorySaverObserver != null) {
            try {
                Slog.d("BackupManagerYuva", "restore completed " + this.mRestorePackageName);
                this.mMemorySaverObserver.onRestoreCompleted(this.mRestorePackageName, !this.isMemorySaverRestoreFail);
                this.mRestorePackageName = null;
            } catch (RemoteException unused) {
                Slog.w("BackupManagerYuva", "full restore observer went away: endRestore");
                this.mMemorySaverObserver = null;
            }
        }
    }

    public void setMemorySaverBackupFail() {
        this.isMemorySaverBackupFail = true;
    }

    public void setMemorySaverRestoreFail() {
        this.isMemorySaverRestoreFail = true;
    }
}
