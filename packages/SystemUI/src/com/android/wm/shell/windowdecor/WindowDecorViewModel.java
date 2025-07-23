package com.android.wm.shell.windowdecor;

import android.app.ActivityManager;
import android.os.IBinder;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import com.android.wm.shell.freeform.FreeformTaskTransitionStarter;
import com.android.wm.shell.splitscreen.SplitScreenController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface WindowDecorViewModel {
    void destroyWindowDecoration(ActivityManager.RunningTaskInfo runningTaskInfo);

    void onTaskChanging(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2);

    void onTaskClosing(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2);

    void onTaskInfoChanged(ActivityManager.RunningTaskInfo runningTaskInfo);

    boolean onTaskOpening(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2);

    void onTaskVanished(ActivityManager.RunningTaskInfo runningTaskInfo);

    void setFreeformTaskTransitionStarter(FreeformTaskTransitionStarter freeformTaskTransitionStarter);

    void setSplitScreenController(SplitScreenController splitScreenController);

    default void onTaskToBack(ActivityManager.RunningTaskInfo runningTaskInfo) {
    }

    default void onTaskToFront(ActivityManager.RunningTaskInfo runningTaskInfo) {
    }

    default void onTransitionFinished(IBinder iBinder) {
    }

    default void onDecorationTaskTransitionReady(IBinder iBinder, TransitionInfo.Change change) {
    }

    default void onTransitionMerged(IBinder iBinder, IBinder iBinder2) {
    }
}
