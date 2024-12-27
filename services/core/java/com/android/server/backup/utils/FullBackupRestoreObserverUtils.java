package com.android.server.backup.utils;

import android.app.backup.IFullBackupRestoreObserver;
import android.os.RemoteException;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class FullBackupRestoreObserverUtils {
    public static IFullBackupRestoreObserver sendEndRestore(IFullBackupRestoreObserver iFullBackupRestoreObserver) {
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

    public static IFullBackupRestoreObserver sendOnRestorePackage(IFullBackupRestoreObserver iFullBackupRestoreObserver, String str) {
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
