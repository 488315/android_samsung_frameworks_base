package com.android.wm.shell.unfold.animation;

import android.app.TaskInfo;
import android.view.SurfaceControl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface UnfoldTaskAnimator {
    void applyAnimationProgress(float f, SurfaceControl.Transaction transaction);

    void clearTasks();

    boolean hasActiveTasks();

    void init();

    boolean isApplicableTask(TaskInfo taskInfo);

    void onTaskAppeared(TaskInfo taskInfo, SurfaceControl surfaceControl);

    void onTaskVanished(TaskInfo taskInfo);

    void prepareFinishTransaction(SurfaceControl.Transaction transaction);

    void prepareStartTransaction(SurfaceControl.Transaction transaction);

    void resetAllSurfaces(SurfaceControl.Transaction transaction);

    void resetSurface(TaskInfo taskInfo, SurfaceControl.Transaction transaction);

    default void onSplitScreenTransitionMerged(SurfaceControl.Transaction transaction) {
    }

    default void onTaskChanged(TaskInfo taskInfo) {
    }

    default void start() {
    }

    default void stop() {
    }
}
