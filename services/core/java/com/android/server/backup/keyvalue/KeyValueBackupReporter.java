package com.android.server.backup.keyvalue;

import android.app.backup.IBackupObserver;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.util.EventLog;
import android.util.Slog;

import com.android.server.backup.UserBackupManagerService;
import com.android.server.backup.utils.BackupManagerMonitorEventSender;

public class KeyValueBackupReporter {
    static final boolean MORE_DEBUG = false;
    static final String TAG = "KeyValueBackupTask";
    public final BackupManagerMonitorEventSender mBackupManagerMonitorEventSender;
    public final UserBackupManagerService mBackupManagerService;
    public final IBackupObserver mObserver;

    public KeyValueBackupReporter(
            UserBackupManagerService userBackupManagerService,
            IBackupObserver iBackupObserver,
            BackupManagerMonitorEventSender backupManagerMonitorEventSender) {
        this.mBackupManagerService = userBackupManagerService;
        this.mObserver = iBackupObserver;
        this.mBackupManagerMonitorEventSender = backupManagerMonitorEventSender;
    }

    public final void onAgentCancelled(PackageInfo packageInfo) {
        String str = packageInfo.packageName;
        Slog.i(TAG, "Cancel backing up " + str);
        EventLog.writeEvent(2823, str);
        Bundle bundle = new Bundle();
        bundle.putBoolean("android.app.backup.extra.LOG_CANCEL_ALL", true);
        this.mBackupManagerMonitorEventSender.monitorEvent(21, packageInfo, 2, bundle);
    }
}
