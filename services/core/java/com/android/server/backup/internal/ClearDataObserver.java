package com.android.server.backup.internal;

import android.content.pm.IPackageDataObserver;

import com.android.server.backup.UserBackupManagerService;

public final class ClearDataObserver extends IPackageDataObserver.Stub {
    public UserBackupManagerService backupManagerService;

    public final void onRemoveCompleted(String str, boolean z) {
        synchronized (this.backupManagerService.mClearDataLock) {
            this.backupManagerService.mClearingData = false;
            this.backupManagerService.mClearDataLock.notifyAll();
        }
    }
}
