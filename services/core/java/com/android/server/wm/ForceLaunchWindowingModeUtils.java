package com.android.server.wm;

import android.app.ActivityOptions;

/* loaded from: classes3.dex */
public abstract class ForceLaunchWindowingModeUtils {
    public static boolean shouldApplyForceLaunchWindowingMode(ActivityOptions activityOptions, ActivityRecord activityRecord, Task task) {
        int resolveForceLaunchWindowingMode = resolveForceLaunchWindowingMode(activityOptions, activityRecord, task);
        return resolveForceLaunchWindowingMode != 0 && (task == null || task.getWindowingMode() != resolveForceLaunchWindowingMode);
    }

    public static boolean shouldDismissSplitBeforeLaunch(ActivityOptions activityOptions, ActivityRecord activityRecord) {
        return resolveForceLaunchWindowingMode(activityOptions, activityRecord, null) == 1;
    }

    public static int resolveForceLaunchWindowingMode(ActivityOptions activityOptions, ActivityRecord activityRecord, Task task) {
        if (activityOptions == null) {
            return 0;
        }
        if (task != null && task.inPinnedWindowingMode()) {
            return 0;
        }
        if (activityRecord != null) {
            if (activityRecord.supportsMultiWindowInDefaultDisplayArea() && activityRecord.isActivityTypeStandardOrUndefined()) {
                return activityOptions.getForceLaunchWindowingMode();
            }
            return (activityOptions.getForceLaunchWindowingMode() != 1 || activityRecord.isResizeable()) ? 0 : 1;
        }
        if (task != null && task.supportsMultiWindowInDefaultDisplayArea() && task.isActivityTypeStandardOrUndefined()) {
            return activityOptions.getForceLaunchWindowingMode();
        }
        return 0;
    }
}
