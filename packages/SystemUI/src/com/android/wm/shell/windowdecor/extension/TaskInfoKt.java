package com.android.wm.shell.windowdecor.extension;

import android.app.ActivityManager;
import android.app.TaskInfo;

/* loaded from: classes3.dex */
public abstract class TaskInfoKt {
    public static final boolean isFullscreen(TaskInfo taskInfo) {
        return taskInfo.getWindowingMode() == 1;
    }

    public static final boolean isLightCaptionBarAppearance(TaskInfo taskInfo) {
        ActivityManager.TaskDescription taskDescription = taskInfo.taskDescription;
        return ((taskDescription != null ? taskDescription.getTopOpaqueSystemBarsAppearance() : 0) & 256) != 0;
    }

    public static final boolean isMultiWindow(TaskInfo taskInfo) {
        return taskInfo.getWindowingMode() == 6;
    }

    public static final boolean isTransparentCaptionBarAppearance(TaskInfo taskInfo) {
        ActivityManager.TaskDescription taskDescription = taskInfo.taskDescription;
        return ((taskDescription != null ? taskDescription.getTopOpaqueSystemBarsAppearance() : 0) & 128) != 0;
    }
}
