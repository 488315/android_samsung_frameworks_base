package com.android.server.wm;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.util.Slog;

/* compiled from: DexRestartAppInfo.java */
/* loaded from: classes3.dex */
public final class StartActivityFromRecentsInfo extends DexRestartAppInfo {
  public StartActivityFromRecentsInfo(
      String str,
      int i,
      ApplicationInfo applicationInfo,
      Task task,
      ActivityOptions activityOptions,
      int i2) {
    super(str, i, applicationInfo, task, activityOptions, i2);
  }

  @Override // com.android.server.wm.DexRestartAppInfo
  public CharSequence getAppName(Context context, ActivityTaskSupervisor activityTaskSupervisor) {
    ActivityRecord rootActivity;
    ActivityInfo activityInfo;
    Task task = this.mReusedTask;
    return (task == null
            || (rootActivity = task.getRootActivity()) == null
            || (activityInfo = rootActivity.info) == null)
        ? ""
        : activityInfo.loadLabel(context.getPackageManager());
  }

  @Override // com.android.server.wm.DexRestartAppInfo
  public void startResult(final ActivityTaskManagerService activityTaskManagerService, int i) {
    if (this.mReusedTask == null) {
      Slog.w("DexController", "startResult: mReusedTask=null");
      return;
    }
    DisplayContent displayContent =
        activityTaskManagerService.mRootWindowContainer.getDisplayContent(i);
    if (displayContent == null) {
      Slog.w("DexController", "startResult: failed, cannot find display#" + i);
      return;
    }
    this.mOptions.setLaunchDisplayId(i);
    if (DexRestartAppInfo.SAFE_DEBUG) {
      Slog.d(
          "DexController",
          "startResult: windowingMode="
              + this.mOptions.getLaunchWindowingMode()
              + " targetDisplayId="
              + i);
    }
    displayContent.prepareAppTransition(1);
    activityTaskManagerService.f1734mH.post(
        new Runnable() { // from class:
                         // com.android.server.wm.StartActivityFromRecentsInfo$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            StartActivityFromRecentsInfo.this.lambda$startResult$0(activityTaskManagerService);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$startResult$0(
      ActivityTaskManagerService activityTaskManagerService) {
    try {
      try {
        activityTaskManagerService.mDexController.doPendingStartRecent();
        activityTaskManagerService.startActivityFromRecents(
            this.mReusedTask.mTaskId, this.mOptions.toBundle());
      } catch (IllegalArgumentException e) {
        if (DexRestartAppInfo.SAFE_DEBUG) {
          e.printStackTrace();
        }
      }
    } finally {
      activityTaskManagerService.mDexController.finishPendingStartRecent();
    }
  }

  public String toString() {
    return "START_ACTIVITY_FROM_RECENTS_TYPE, launchTaskId : "
        + this.mLaunchTaskId
        + ", processName : "
        + this.mProcessName
        + ", uid : "
        + this.mUid;
  }
}
