package com.android.server.wm;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import com.android.server.uri.NeededUriGrants;

/* loaded from: classes3.dex */
public interface RemoteAppControllerCallbacks {
  boolean onStartActivityInterceptedLocked(
      Intent intent,
      ActivityOptions activityOptions,
      ActivityInfo activityInfo,
      int i,
      boolean z,
      ActivityManager.RunningTaskInfo runningTaskInfo,
      RemoteAppController.CallerInfo callerInfo,
      NeededUriGrants neededUriGrants,
      int i2,
      int i3);

  static String interceptReasonToString(int i) {
    return i != 0
        ? i != 1
            ? i != 2
                ? i != 3
                    ? i != 4 ? String.valueOf(i) : "INTERCEPT_REASON_APP_REQUESTED"
                    : "TASK_IS_MOVING_TO_REMOTE_APP_DISPLAY"
                : "TASK_IS_MOVING_TO_DEFAULT_DISPLAY"
            : "OTHER_TASK_EXISTS_IN_REMOTE_APP_DISPLAY"
        : "UNDEFINED";
  }
}
