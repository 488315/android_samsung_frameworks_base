package com.android.systemui.shared.system;

import android.app.ActivityManager;
import android.content.ComponentName;

/* loaded from: classes3.dex */
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

    default void onTaskRemoved(int i) {
    }

    default void onActivityUnpinned() {
    }

    default void onTaskStackChanged() {
    }

    default void onTaskStackChangedBackground() {
    }

    default void onActivityPinned(int i, String str) {
    }

    default void onTaskCreated(int i, ComponentName componentName) {
    }

    default void onTaskFocusChanged(int i, boolean z) {
    }

    default void onTaskProfileLocked(ActivityManager.RunningTaskInfo runningTaskInfo, int i) {
    }
}
