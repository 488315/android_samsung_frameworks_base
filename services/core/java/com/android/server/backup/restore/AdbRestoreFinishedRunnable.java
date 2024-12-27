package com.android.server.backup.restore;

import android.app.IBackupAgent;
import android.os.RemoteException;
import com.android.server.backup.UserBackupManagerService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AdbRestoreFinishedRunnable implements Runnable {
    public final IBackupAgent mAgent;
    public final UserBackupManagerService mBackupManagerService;
    public final int mToken;

    public AdbRestoreFinishedRunnable(IBackupAgent iBackupAgent, int i, UserBackupManagerService userBackupManagerService) {
        this.mAgent = iBackupAgent;
        this.mToken = i;
        this.mBackupManagerService = userBackupManagerService;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            this.mAgent.doRestoreFinished(this.mToken, this.mBackupManagerService.mBackupManagerBinder);
        } catch (RemoteException unused) {
        }
    }
}
