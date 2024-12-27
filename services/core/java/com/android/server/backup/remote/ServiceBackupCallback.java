package com.android.server.backup.remote;

import android.app.backup.IBackupCallback;
import android.app.backup.IBackupManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
