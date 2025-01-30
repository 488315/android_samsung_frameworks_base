package com.samsung.android.globalactions.util;

import android.app.ActivityManager;
import android.content.Context;

/* loaded from: classes5.dex */
public class ActivityManagerWrapper {
  private final ActivityManager mActivityManager;

  public ActivityManagerWrapper(Context context) {
    this.mActivityManager = (ActivityManager) context.getSystemService("activity");
  }

  public boolean isInLockTaskMode() {
    ActivityManager activityManager = this.mActivityManager;
    return (activityManager == null || activityManager.getLockTaskModeState() == 0) ? false : true;
  }
}
