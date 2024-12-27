package com.android.server.wm;

import android.app.ActivityOptions;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class ForceLaunchWindowingModeUtils {
    public static int resolveForceLaunchWindowingMode(
            ActivityOptions activityOptions, ActivityRecord activityRecord, Task task) {
        if (activityOptions == null) {
            return 0;
        }
        if (task != null && task.inPinnedWindowingMode()) {
            return 0;
        }
        if (activityRecord != null) {
            return (activityRecord.supportsMultiWindowInDisplayArea(
                                    activityRecord.mRootWindowContainer.mDefaultDisplay
                                            .getDefaultTaskDisplayArea())
                            && activityRecord.isActivityTypeStandardOrUndefined())
                    ? activityOptions.getForceLaunchWindowingMode()
                    : (activityOptions.getForceLaunchWindowingMode() != 1
                                    || activityRecord.isResizeable(true))
                            ? 0
                            : 1;
        }
        if (task != null
                && task.supportsMultiWindowInDisplayArea(task.getTaskDisplayArea(), false)
                && task.isActivityTypeStandardOrUndefined()) {
            return activityOptions.getForceLaunchWindowingMode();
        }
        return 0;
    }
}
