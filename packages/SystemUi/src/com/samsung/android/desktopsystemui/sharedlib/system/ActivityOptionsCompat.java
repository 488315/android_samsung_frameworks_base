package com.samsung.android.desktopsystemui.sharedlib.system;

import android.app.ActivityOptions;
import android.content.Context;
import android.os.Handler;
import com.samsung.android.desktopsystemui.sharedlib.recents.model.Task;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class ActivityOptionsCompat {
    public static ActivityOptions makeCustomAnimation(Context context, int i, int i2, final Runnable runnable, final Handler handler) {
        return ActivityOptions.makeCustomTaskAnimation(context, i, i2, handler, new ActivityOptions.OnAnimationStartedListener() { // from class: com.samsung.android.desktopsystemui.sharedlib.system.ActivityOptionsCompat.1
            public void onAnimationStarted(long j) {
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    handler.post(runnable2);
                }
            }
        }, null);
    }

    public static ActivityOptions makeFreeformOptions() {
        ActivityOptions makeBasic = ActivityOptions.makeBasic();
        makeBasic.setLaunchWindowingMode(5);
        return makeBasic;
    }

    public static ActivityOptions makeRemoteAnimation(RemoteAnimationAdapterCompat remoteAnimationAdapterCompat) {
        return ActivityOptions.makeBasic();
    }

    public static ActivityOptions makeRemoteTransition(RemoteTransitionCompat remoteTransitionCompat) {
        return ActivityOptions.makeRemoteTransition(remoteTransitionCompat.getTransition());
    }

    public static ActivityOptions makeSplitScreenOptions(boolean z) {
        return makeSplitScreenOptions(z, true);
    }

    public static ActivityOptions setFreezeRecentTasksList(ActivityOptions activityOptions) {
        activityOptions.setFreezeRecentTasksReordering();
        return activityOptions;
    }

    public static ActivityOptions setLauncherSourceInfo(ActivityOptions activityOptions, long j) {
        activityOptions.setSourceInfo(1, j);
        return activityOptions;
    }

    public static ActivityOptions setStartedFromWindowTypeLauncher(ActivityOptions activityOptions, boolean z) {
        activityOptions.setStartedFromWindowTypeLauncher(z);
        return activityOptions;
    }

    public static ActivityOptions makeSplitScreenOptions(boolean z, boolean z2) {
        return ActivityOptions.makeBasic();
    }

    public static void addTaskInfo(ActivityOptions activityOptions, Task.TaskKey taskKey) {
    }
}
