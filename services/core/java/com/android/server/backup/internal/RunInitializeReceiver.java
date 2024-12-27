package com.android.server.backup.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.ArraySet;
import android.util.Slog;

import com.android.server.backup.UserBackupManagerService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class RunInitializeReceiver extends BroadcastReceiver {
    public final UserBackupManagerService mUserBackupManagerService;

    public RunInitializeReceiver(UserBackupManagerService userBackupManagerService) {
        this.mUserBackupManagerService = userBackupManagerService;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if ("android.app.backup.intent.INIT".equals(intent.getAction())) {
            synchronized (this.mUserBackupManagerService.mQueueLock) {
                try {
                    ArraySet arraySet = this.mUserBackupManagerService.mPendingInits;
                    Slog.v(
                            "BackupManagerService",
                            "Running a device init; " + arraySet.size() + " pending");
                    if (arraySet.size() > 0) {
                        String[] strArr = (String[]) arraySet.toArray(new String[arraySet.size()]);
                        this.mUserBackupManagerService.mPendingInits.clear();
                        this.mUserBackupManagerService.initializeTransports(strArr, null);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }
}
