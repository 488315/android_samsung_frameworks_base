package com.android.server.backup.remote;

import android.app.backup.IBackupCallback;
import android.app.backup.IBackupManager;

public final class ServiceBackupCallback extends IBackupCallback.Stub {
    public final IBackupManager mBackupManager;
    public final int mToken;

    public ServiceBackupCallback(IBackupManager iBackupManager, int i) {
        this.mBackupManager = iBackupManager;
        this.mToken = i;
    }

    public final void operationComplete(long j) {
        this.mBackupManager.opComplete(this.mToken, j);
    }
}
