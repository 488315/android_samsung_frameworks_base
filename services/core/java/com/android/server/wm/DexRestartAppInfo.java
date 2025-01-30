package com.android.server.wm;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public abstract class DexRestartAppInfo {
  public static final boolean SAFE_DEBUG = CoreRune.SAFE_DEBUG;
  public final String TAG;
  public boolean mKeepProcessAlive;
  public int mLaunchTaskId;
  public ActivityOptions mOptions;
  public PendingActivityLaunch mPal;
  public int mPreferredDisplayId;
  public String mProcessName;
  public Task mReusedTask;
  public boolean mSkipToShow;
  public Task mTargetTask;
  public int mUid;

  public abstract CharSequence getAppName(
      Context context, ActivityTaskSupervisor activityTaskSupervisor);

  public abstract void startResult(ActivityTaskManagerService activityTaskManagerService, int i);

  public DexRestartAppInfo(
      String str,
      int i,
      ApplicationInfo applicationInfo,
      Task task,
      ActivityOptions activityOptions,
      int i2) {
    this(str, i, applicationInfo, task, null, activityOptions, i2);
  }

  public DexRestartAppInfo(
      String str,
      int i,
      ApplicationInfo applicationInfo,
      Task task,
      Task task2,
      ActivityOptions activityOptions,
      int i2) {
    Bundle bundle;
    this.TAG = "DexController";
    this.mProcessName = str;
    this.mUid = i;
    this.mReusedTask = task;
    this.mTargetTask = task2;
    this.mOptions = activityOptions == null ? ActivityOptions.makeBasic() : activityOptions;
    this.mPreferredDisplayId = i2;
    if (applicationInfo == null || (bundle = applicationInfo.metaData) == null) {
      return;
    }
    this.mSkipToShow =
        bundle.getBoolean("com.samsung.android.multidisplay.do_not_show_displaychooser");
  }

  public static DexRestartAppInfo createPendingActivityLaunchType(
      PendingActivityLaunch pendingActivityLaunch,
      String str,
      int i,
      ApplicationInfo applicationInfo,
      Task task,
      Task task2,
      ActivityOptions activityOptions,
      int i2) {
    return new PendingActivityLaunchInfo(
        pendingActivityLaunch, str, i, applicationInfo, task, task2, activityOptions, i2);
  }

  public static DexRestartAppInfo createStartActivityFromRecentsType(
      String str,
      int i,
      ApplicationInfo applicationInfo,
      Task task,
      ActivityOptions activityOptions,
      int i2) {
    return new StartActivityFromRecentsInfo(str, i, applicationInfo, task, activityOptions, i2);
  }

  public ArrayList getMovingTaskLocked(DexController dexController, int i) {
    return dexController.getTaskLocked(this.mProcessName, this.mUid, false, i);
  }

  public String getProcessName() {
    return this.mProcessName;
  }

  public int getUid() {
    return this.mUid;
  }

  public void setKeepProcessAlive(boolean z) {
    this.mKeepProcessAlive = z;
  }

  public boolean skipToShow() {
    return this.mSkipToShow;
  }
}
