package com.android.server.backup.utils;

import android.app.IBackupAgent;
import android.app.backup.IBackupManagerMonitor;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Slog;
import com.android.internal.infra.AndroidFuture;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes.dex */
public abstract class BackupManagerMonitorUtils {
  public static IBackupManagerMonitor monitorEvent(
      IBackupManagerMonitor iBackupManagerMonitor,
      int i,
      PackageInfo packageInfo,
      int i2,
      Bundle bundle) {
    if (iBackupManagerMonitor == null) {
      return null;
    }
    try {
      Bundle bundle2 = new Bundle();
      bundle2.putInt("android.app.backup.extra.LOG_EVENT_ID", i);
      bundle2.putInt("android.app.backup.extra.LOG_EVENT_CATEGORY", i2);
      if (packageInfo != null) {
        bundle2.putString(
            "android.app.backup.extra.LOG_EVENT_PACKAGE_NAME", packageInfo.packageName);
        bundle2.putInt(
            "android.app.backup.extra.LOG_EVENT_PACKAGE_VERSION", packageInfo.versionCode);
        bundle2.putLong(
            "android.app.backup.extra.LOG_EVENT_PACKAGE_FULL_VERSION",
            packageInfo.getLongVersionCode());
      }
      if (bundle != null) {
        bundle2.putAll(bundle);
      }
      iBackupManagerMonitor.onEvent(bundle2);
      return iBackupManagerMonitor;
    } catch (RemoteException unused) {
      Slog.w("BackupManagerService", "backup manager monitor went away");
      return null;
    }
  }

  public static IBackupManagerMonitor monitorAgentLoggingResults(
      IBackupManagerMonitor iBackupManagerMonitor,
      PackageInfo packageInfo,
      IBackupAgent iBackupAgent) {
    if (iBackupManagerMonitor == null) {
      return null;
    }
    try {
      AndroidFuture androidFuture = new AndroidFuture();
      AndroidFuture androidFuture2 = new AndroidFuture();
      iBackupAgent.getLoggerResults(androidFuture);
      iBackupAgent.getOperationType(androidFuture2);
      TimeUnit timeUnit = TimeUnit.MILLISECONDS;
      return sendAgentLoggingResults(
          iBackupManagerMonitor,
          packageInfo,
          (List) androidFuture.get(500L, timeUnit),
          ((Integer) androidFuture2.get(500L, timeUnit)).intValue());
    } catch (TimeoutException e) {
      Slog.w(
          "BackupManagerService",
          "Timeout while waiting to retrieve logging results from agent",
          e);
      return iBackupManagerMonitor;
    } catch (Exception e2) {
      Slog.w("BackupManagerService", "Failed to retrieve logging results from agent", e2);
      return iBackupManagerMonitor;
    }
  }

  public static IBackupManagerMonitor sendAgentLoggingResults(
      IBackupManagerMonitor iBackupManagerMonitor, PackageInfo packageInfo, List list, int i) {
    Bundle bundle = new Bundle();
    bundle.putParcelableList("android.app.backup.extra.LOG_AGENT_LOGGING_RESULTS", list);
    bundle.putInt("android.app.backup.extra.OPERATION_TYPE", i);
    return monitorEvent(iBackupManagerMonitor, 52, packageInfo, 2, bundle);
  }

  public static Bundle putMonitoringExtra(Bundle bundle, String str, String str2) {
    if (bundle == null) {
      bundle = new Bundle();
    }
    bundle.putString(str, str2);
    return bundle;
  }

  public static Bundle putMonitoringExtra(Bundle bundle, String str, long j) {
    if (bundle == null) {
      bundle = new Bundle();
    }
    bundle.putLong(str, j);
    return bundle;
  }

  public static Bundle putMonitoringExtra(Bundle bundle, String str, boolean z) {
    if (bundle == null) {
      bundle = new Bundle();
    }
    bundle.putBoolean(str, z);
    return bundle;
  }
}
