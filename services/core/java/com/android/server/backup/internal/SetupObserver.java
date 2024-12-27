package com.android.server.backup.internal;

import android.content.Context;
import android.database.ContentObserver;
import android.provider.Settings;
import com.android.server.backup.KeyValueBackupJob;
import com.android.server.backup.UserBackupManagerService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SetupObserver extends ContentObserver {
    public final Context mContext;
    public final UserBackupManagerService mUserBackupManagerService;
    public final int mUserId;

    public SetupObserver(UserBackupManagerService userBackupManagerService, BackupHandler backupHandler) {
        super(backupHandler);
        this.mUserBackupManagerService = userBackupManagerService;
        this.mContext = userBackupManagerService.mContext;
        this.mUserId = userBackupManagerService.mUserId;
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z) {
        boolean z2 = this.mUserBackupManagerService.mSetupComplete;
        boolean z3 = z2 || (Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "user_setup_complete", 0, this.mUserId) != 0);
        UserBackupManagerService userBackupManagerService = this.mUserBackupManagerService;
        userBackupManagerService.mSetupComplete = z3;
        synchronized (userBackupManagerService.mQueueLock) {
            if (z3 && !z2) {
                try {
                    UserBackupManagerService userBackupManagerService2 = this.mUserBackupManagerService;
                    if (userBackupManagerService2.mEnabled) {
                        KeyValueBackupJob.schedule(userBackupManagerService2.mUserId, this.mContext, userBackupManagerService2);
                        this.mUserBackupManagerService.scheduleNextFullBackupJob(0L);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }
}
