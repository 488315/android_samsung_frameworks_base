package com.android.systemui.shared.system;

import android.app.ActivityManager;
import android.content.ComponentName;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface TaskStackChangeListener {
    default void onTaskMovedToFront() {
    }

    default void onTaskMovedToFront(ActivityManager.RunningTaskInfo runningTaskInfo) {
        int i = runningTaskInfo.taskId;
        onTaskMovedToFront();
    }

    default void onActivityRequestedOrientationChanged(int i) {
    }

    default void onLockTaskModeChanged(int i) {
    }

    default void onTaskCreated(ComponentName componentName) {
    }

    default void onTaskRemoved() {
    }

    default void onTaskStackChanged() {
    }

    default void onTaskStackChangedBackground() {
    }

    default void onTaskProfileLocked(ActivityManager.RunningTaskInfo runningTaskInfo, int i) {
    }
}
