package com.android.server.backup.params;

import android.app.backup.IBackupManagerMonitor;
import android.app.backup.IRestoreObserver;
import android.content.pm.PackageInfo;

import com.android.server.backup.internal.OnTaskFinishedListener;
import com.android.server.backup.transport.TransportConnection;
import com.android.server.backup.utils.BackupEligibilityRules;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class RestoreParams {
    public final BackupEligibilityRules backupEligibilityRules;
    public final String[] filterSet;
    public final boolean isSystemRestore;
    public final OnTaskFinishedListener listener;
    public final TransportConnection mTransportConnection;
    public final IBackupManagerMonitor monitor;
    public final IRestoreObserver observer;
    public final PackageInfo packageInfo;
    public final int pmToken;
    public final long token;

    public RestoreParams(
            TransportConnection transportConnection,
            IRestoreObserver iRestoreObserver,
            IBackupManagerMonitor iBackupManagerMonitor,
            long j,
            PackageInfo packageInfo,
            int i,
            boolean z,
            String[] strArr,
            OnTaskFinishedListener onTaskFinishedListener,
            BackupEligibilityRules backupEligibilityRules) {
        this.mTransportConnection = transportConnection;
        this.observer = iRestoreObserver;
        this.monitor = iBackupManagerMonitor;
        this.token = j;
        this.packageInfo = packageInfo;
        this.pmToken = i;
        this.isSystemRestore = z;
        this.filterSet = strArr;
        this.listener = onTaskFinishedListener;
        this.backupEligibilityRules = backupEligibilityRules;
    }
}
