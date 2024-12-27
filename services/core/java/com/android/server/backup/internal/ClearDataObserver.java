package com.android.server.backup.internal;

import android.content.pm.IPackageDataObserver;

import com.android.server.backup.UserBackupManagerService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ClearDataObserver extends IPackageDataObserver.Stub {
    public UserBackupManagerService backupManagerService;

    public final void onRemoveCompleted(String str, boolean z) {
        synchronized (this.backupManagerService.mClearDataLock) {
            this.backupManagerService.mClearingData = false;
            this.backupManagerService.mClearDataLock.notifyAll();
        }
    }
}
