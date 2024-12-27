package com.android.server.backup.utils;

import android.app.backup.IFullBackupRestoreObserver;
import android.os.RemoteException;
import android.util.Slog;

public abstract class FullBackupRestoreObserverUtils {
    public static IFullBackupRestoreObserver sendEndRestore(
            IFullBackupRestoreObserver iFullBackupRestoreObserver) {
        if (iFullBackupRestoreObserver == null) {
            return iFullBackupRestoreObserver;
        }
        try {
            iFullBackupRestoreObserver.onEndRestore();
            return iFullBackupRestoreObserver;
        } catch (RemoteException unused) {
            Slog.w("BackupManagerService", "full restore observer went away: endRestore");
            return null;
        }
    }

    public static IFullBackupRestoreObserver sendOnRestorePackage(
            IFullBackupRestoreObserver iFullBackupRestoreObserver, String str) {
        if (iFullBackupRestoreObserver == null) {
            return iFullBackupRestoreObserver;
        }
        try {
            iFullBackupRestoreObserver.onRestorePackage(str);
            return iFullBackupRestoreObserver;
        } catch (RemoteException unused) {
            Slog.w("BackupManagerService", "full restore observer went away: restorePackage");
            return null;
        }
    }
}
