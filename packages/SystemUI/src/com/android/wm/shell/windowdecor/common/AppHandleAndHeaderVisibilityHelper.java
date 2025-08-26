package com.android.wm.shell.windowdecor.common;

import android.app.ActivityManager;
import android.view.Display;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.desktopmode.DesktopWallpaperActivity;
import com.android.wm.shell.shared.desktopmode.DesktopModeCompatPolicy;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.splitscreen.SplitScreenController;

/* loaded from: classes3.dex */
public final class AppHandleAndHeaderVisibilityHelper {
    public final DesktopModeCompatPolicy desktopModeCompatPolicy;
    public final DesktopState desktopState;
    public final DisplayController displayController;
    public SplitScreenController splitScreenController;

    public AppHandleAndHeaderVisibilityHelper(DisplayController displayController, DesktopModeCompatPolicy desktopModeCompatPolicy, DesktopState desktopState) {
        this.displayController = displayController;
        this.desktopModeCompatPolicy = desktopModeCompatPolicy;
        this.desktopState = desktopState;
    }

    public final boolean allowedForTask(ActivityManager.RunningTaskInfo runningTaskInfo, Display display) {
        SplitScreenController splitScreenController = this.splitScreenController;
        if ((splitScreenController != null && splitScreenController.isTaskRootOrStageRoot(runningTaskInfo.taskId)) || this.desktopModeCompatPolicy.shouldDisableDesktopEntryPoints(runningTaskInfo)) {
            return false;
        }
        display.getMinSizeDimensionDp();
        DesktopState desktopState = this.desktopState;
        if (!((DesktopStateImpl) desktopState).canEnterDesktopMode) {
            desktopState.getClass();
        }
        if (!desktopState.canEnterDesktopModeOrShowAppHandle()) {
            return false;
        }
        DesktopWallpaperActivity.Companion.getClass();
        return (DesktopWallpaperActivity.Companion.isWallpaperTask(runningTaskInfo) || runningTaskInfo.getWindowingMode() == 2 || runningTaskInfo.getActivityType() != 1 || runningTaskInfo.configuration.windowConfiguration.isAlwaysOnTop()) ? false : true;
    }
}
