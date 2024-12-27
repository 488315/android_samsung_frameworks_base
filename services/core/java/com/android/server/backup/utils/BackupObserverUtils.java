package com.android.server.backup.utils;

import android.app.backup.BackupProgress;
import android.app.backup.IBackupObserver;
import android.os.RemoteException;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class BackupObserverUtils {
    public static void sendBackupFinished(IBackupObserver iBackupObserver, int i) {
        if (iBackupObserver != null) {
            try {
                iBackupObserver.backupFinished(i);
            } catch (RemoteException unused) {
                Slog.w("BackupManagerService", "Backup observer went away: backupFinished");
            }
        }
    }

    public static void sendBackupOnPackageResult(IBackupObserver iBackupObserver, String str, int i) {
        if (iBackupObserver != null) {
            try {
                iBackupObserver.onResult(str, i);
            } catch (RemoteException unused) {
                Slog.w("BackupManagerService", "Backup observer went away: onResult");
            }
        }
    }

    public static void sendBackupOnUpdate(IBackupObserver iBackupObserver, String str, BackupProgress backupProgress) {
        if (iBackupObserver != null) {
            try {
                iBackupObserver.onUpdate(str, backupProgress);
            } catch (RemoteException unused) {
                Slog.w("BackupManagerService", "Backup observer went away: onUpdate");
            }
        }
    }
}
