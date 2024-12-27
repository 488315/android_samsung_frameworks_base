package com.android.server.backup.restore;

import android.app.IBackupAgent;
import android.os.RemoteException;

import com.android.server.backup.UserBackupManagerService;

public final class AdbRestoreFinishedRunnable implements Runnable {
    public final IBackupAgent mAgent;
    public final UserBackupManagerService mBackupManagerService;
    public final int mToken;

    public AdbRestoreFinishedRunnable(
            IBackupAgent iBackupAgent, int i, UserBackupManagerService userBackupManagerService) {
        this.mAgent = iBackupAgent;
        this.mToken = i;
        this.mBackupManagerService = userBackupManagerService;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            this.mAgent.doRestoreFinished(
                    this.mToken, this.mBackupManagerService.mBackupManagerBinder);
        } catch (RemoteException unused) {
        }
    }
}
