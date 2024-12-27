package com.android.server.backup.utils;

import android.app.IBackupAgent;
import android.app.backup.IBackupManagerMonitor;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Slog;

import com.android.internal.infra.AndroidFuture;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class BackupManagerMonitorEventSender {
    public final BackupManagerMonitorDumpsysUtils mBackupManagerMonitorDumpsysUtils;
    public IBackupManagerMonitor mMonitor;

    public BackupManagerMonitorEventSender(IBackupManagerMonitor iBackupManagerMonitor) {
        this.mMonitor = iBackupManagerMonitor;
        this.mBackupManagerMonitorDumpsysUtils = new BackupManagerMonitorDumpsysUtils();
    }

    public BackupManagerMonitorEventSender(
            IBackupManagerMonitor iBackupManagerMonitor,
            BackupManagerMonitorDumpsysUtils backupManagerMonitorDumpsysUtils) {
        this.mMonitor = iBackupManagerMonitor;
        this.mBackupManagerMonitorDumpsysUtils = backupManagerMonitorDumpsysUtils;
    }

    public static Bundle putMonitoringExtra(String str, String str2) {
        return AccountManagerService$$ExternalSyntheticOutline0.m142m(str, str2);
    }

    public final void monitorAgentLoggingResults(
            PackageInfo packageInfo, IBackupAgent iBackupAgent) {
        if (this.mMonitor == null) {
            Slog.i(
                    "BackupManagerService",
                    "backup manager monitor is null unable to send event" + packageInfo);
        }
        try {
            AndroidFuture androidFuture = new AndroidFuture();
            AndroidFuture androidFuture2 = new AndroidFuture();
            iBackupAgent.getLoggerResults(androidFuture);
            iBackupAgent.getOperationType(androidFuture2);
            TimeUnit timeUnit = TimeUnit.MILLISECONDS;
            List list = (List) androidFuture.get(500L, timeUnit);
            int intValue = ((Integer) androidFuture2.get(500L, timeUnit)).intValue();
            Bundle bundle = new Bundle();
            bundle.putParcelableList("android.app.backup.extra.LOG_AGENT_LOGGING_RESULTS", list);
            bundle.putInt("android.app.backup.extra.OPERATION_TYPE", intValue);
            monitorEvent(52, packageInfo, 2, bundle);
        } catch (TimeoutException e) {
            Slog.w(
                    "BackupManagerService",
                    "Timeout while waiting to retrieve logging results from agent",
                    e);
        } catch (Exception e2) {
            Slog.w("BackupManagerService", "Failed to retrieve logging results from agent", e2);
        }
    }

    public final void monitorEvent(int i, PackageInfo packageInfo, int i2, Bundle bundle) {
        try {
            Bundle bundle2 = new Bundle();
            bundle2.putInt("android.app.backup.extra.LOG_EVENT_ID", i);
            bundle2.putInt("android.app.backup.extra.LOG_EVENT_CATEGORY", i2);
            if (packageInfo != null) {
                bundle2.putString(
                        "android.app.backup.extra.LOG_EVENT_PACKAGE_NAME", packageInfo.packageName);
                bundle2.putInt(
                        "android.app.backup.extra.LOG_EVENT_PACKAGE_VERSION",
                        packageInfo.versionCode);
                bundle2.putLong(
                        "android.app.backup.extra.LOG_EVENT_PACKAGE_FULL_VERSION",
                        packageInfo.getLongVersionCode());
            }
            if (bundle != null) {
                bundle2.putAll(bundle);
                if (bundle.containsKey("android.app.backup.extra.OPERATION_TYPE")
                        && bundle.getInt("android.app.backup.extra.OPERATION_TYPE") == 1) {
                    this.mBackupManagerMonitorDumpsysUtils
                            .parseBackupManagerMonitorRestoreEventForDumpsys(bundle2);
                }
            }
            IBackupManagerMonitor iBackupManagerMonitor = this.mMonitor;
            if (iBackupManagerMonitor != null) {
                iBackupManagerMonitor.onEvent(bundle2);
            } else {
                Slog.w(
                        "BackupManagerService",
                        "backup manager monitor is null unable to send event");
            }
        } catch (RemoteException unused) {
            this.mMonitor = null;
            Slog.w("BackupManagerService", "backup manager monitor went away");
        }
    }
}
