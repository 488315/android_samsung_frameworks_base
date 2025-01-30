package com.samsung.android.app;

import android.app.ActivityManager;
import android.content.Context;
import java.util.List;

/* loaded from: classes5.dex */
public class SemAppLockManager {
  private static final String TAG = "SemAppLockManager";
  private ActivityManager mActivityManager;
  private final Context mContext;

  public SemAppLockManager(Context context) {
    this.mContext = context;
    this.mActivityManager = (ActivityManager) context.getSystemService("activity");
  }

  private ActivityManager getActivityManager() {
    if (this.mActivityManager == null) {
      this.mActivityManager = (ActivityManager) this.mContext.getSystemService("activity");
    }
    return this.mActivityManager;
  }

  public boolean isAppLockEnabled() {
    if (getActivityManager() != null) {
      return this.mActivityManager.isApplockEnabled();
    }
    return false;
  }

  public String getCheckAction() {
    if (getActivityManager() != null) {
      return this.mActivityManager.getAppLockedCheckAction();
    }
    return null;
  }

  public boolean isPackageLocked(String packageName) {
    if (getActivityManager() != null) {
      return this.mActivityManager.isAppLockedPackage(packageName);
    }
    return false;
  }

  public List<String> getPackageList() {
    if (getActivityManager() != null) {
      return this.mActivityManager.getAppLockedPackageList();
    }
    return null;
  }
}
