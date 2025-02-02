package com.android.server.locales;

import android.os.UserHandle;
import com.android.internal.content.PackageMonitor;

/* loaded from: classes2.dex */
public final class LocaleManagerServicePackageMonitor extends PackageMonitor {
  public LocaleManagerBackupHelper mBackupHelper;
  public LocaleManagerService mLocaleManagerService;
  public SystemAppUpdateTracker mSystemAppUpdateTracker;

  public LocaleManagerServicePackageMonitor(
      LocaleManagerBackupHelper localeManagerBackupHelper,
      SystemAppUpdateTracker systemAppUpdateTracker,
      LocaleManagerService localeManagerService) {
    this.mBackupHelper = localeManagerBackupHelper;
    this.mSystemAppUpdateTracker = systemAppUpdateTracker;
    this.mLocaleManagerService = localeManagerService;
  }

  public void onPackageAdded(String str, int i) {
    this.mBackupHelper.onPackageAdded(str, i);
  }

  public void onPackageDataCleared(String str, int i) {
    this.mBackupHelper.onPackageDataCleared(str, i);
  }

  public void onPackageRemoved(String str, int i) {
    this.mBackupHelper.onPackageRemoved(str, i);
    this.mLocaleManagerService.deleteOverrideLocaleConfig(str, UserHandle.getUserId(i));
  }

  public void onPackageUpdateFinished(String str, int i) {
    this.mBackupHelper.onPackageUpdateFinished(str, i);
    this.mSystemAppUpdateTracker.onPackageUpdateFinished(str, i);
  }
}
