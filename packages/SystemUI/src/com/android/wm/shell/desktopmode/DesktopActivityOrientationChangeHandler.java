package com.android.wm.shell.desktopmode;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.window.WindowContainerTransaction;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.TaskStackListenerCallback;
import com.android.wm.shell.common.TaskStackListenerImpl;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.sysui.ShellInit;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DesktopActivityOrientationChangeHandler {
    public final DesktopUserRepositories desktopUserRepositories;
    public final DisplayController displayController;
    public final ToggleResizeDesktopTaskTransitionHandler resizeHandler;
    public final ShellTaskOrganizer shellTaskOrganizer;
    public final TaskStackListenerImpl taskStackListener;

    public DesktopActivityOrientationChangeHandler(Context context, ShellInit shellInit, ShellTaskOrganizer shellTaskOrganizer, TaskStackListenerImpl taskStackListenerImpl, ToggleResizeDesktopTaskTransitionHandler toggleResizeDesktopTaskTransitionHandler, DesktopUserRepositories desktopUserRepositories, DisplayController displayController, DesktopState desktopState) {
        this.shellTaskOrganizer = shellTaskOrganizer;
        this.taskStackListener = taskStackListenerImpl;
        this.resizeHandler = toggleResizeDesktopTaskTransitionHandler;
        this.desktopUserRepositories = desktopUserRepositories;
        this.displayController = displayController;
        if (((DesktopStateImpl) desktopState).canEnterDesktopMode) {
            shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopActivityOrientationChangeHandler.1
                @Override // java.lang.Runnable
                public final void run() {
                    final DesktopActivityOrientationChangeHandler desktopActivityOrientationChangeHandler = DesktopActivityOrientationChangeHandler.this;
                    desktopActivityOrientationChangeHandler.getClass();
                    desktopActivityOrientationChangeHandler.taskStackListener.addListener(new TaskStackListenerCallback() { // from class: com.android.wm.shell.desktopmode.DesktopActivityOrientationChangeHandler$onInit$1
                        @Override // com.android.wm.shell.common.TaskStackListenerCallback
                        public final void onActivityRequestedOrientationChanged(int i, int i2) {
                            DesktopActivityOrientationChangeHandler.this.handleActivityOrientationChange(i, i2);
                        }
                    });
                }
            }, this);
        }
    }

    public final void handleActivityOrientationChange(int i, int i2) {
        Rect bounds;
        int height;
        int width;
        DisplayLayout displayLayout;
        ActivityManager.RunningTaskInfo runningTaskInfo = this.shellTaskOrganizer.getRunningTaskInfo(i);
        if (runningTaskInfo == null || !this.desktopUserRepositories.getCurrent().isAnyDeskActive(runningTaskInfo.displayId) || !runningTaskInfo.isFreeform() || runningTaskInfo.isResizeable || (width = bounds.width()) == (height = (bounds = runningTaskInfo.configuration.windowConfiguration.getBounds()).height())) {
            return;
        }
        int i3 = width > height ? 2 : 1;
        if (!((i3 == 1 && ActivityInfo.isFixedOrientationLandscape(i2)) || (i3 == 2 && ActivityInfo.isFixedOrientationPortrait(i2))) || (displayLayout = this.displayController.getDisplayLayout(runningTaskInfo.displayId)) == null) {
            return;
        }
        Rect appBounds = runningTaskInfo.configuration.windowConfiguration.getAppBounds();
        Rect calculateInitialBounds$default = DesktopModeUtils.calculateInitialBounds$default(displayLayout, runningTaskInfo, appBounds != null ? appBounds.top - runningTaskInfo.configuration.windowConfiguration.getBounds().top : 0, Integer.valueOf(i2), 4);
        int centerX = bounds.centerX() - (calculateInitialBounds$default.width() / 2);
        int width2 = calculateInitialBounds$default.width() + centerX;
        int i4 = bounds.top;
        WindowContainerTransaction bounds2 = new WindowContainerTransaction().setBounds(runningTaskInfo.token, new Rect(centerX, i4, width2, calculateInitialBounds$default.height() + i4));
        if (CoreRune.DW_SHELL_CHANGE_TRANSITION) {
            bounds2.setChangeTransitMode(runningTaskInfo.token, 1, ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i3, "non_resizeable_or_changed(", ")"));
        }
        ToggleResizeDesktopTaskTransitionHandler.startTransition$default(this.resizeHandler, bounds2, null, 6);
    }
}
