package com.android.wm.shell.desktopmode;

import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.desktopmode.DesktopModeVisualIndicator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DesktopTasksController$dragToDesktopStateListener$1 {
    public final /* synthetic */ DesktopTasksController this$0;

    public DesktopTasksController$dragToDesktopStateListener$1(DesktopTasksController desktopTasksController) {
        this.this$0 = desktopTasksController;
    }

    public final void removeVisualIndicator() {
        final DesktopTasksController desktopTasksController = this.this$0;
        DesktopModeVisualIndicator desktopModeVisualIndicator = desktopTasksController.visualIndicator;
        if (desktopModeVisualIndicator != null) {
            Runnable runnable = new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$dragToDesktopStateListener$1$removeVisualIndicator$1
                @Override // java.lang.Runnable
                public final void run() {
                    DesktopTasksController.this.releaseVisualIndicator();
                }
            };
            DisplayLayout displayLayout = desktopModeVisualIndicator.mDisplayController.getDisplayLayout(desktopModeVisualIndicator.mTaskInfo.displayId);
            DesktopModeVisualIndicator.IndicatorType indicatorType = desktopModeVisualIndicator.mCurrentType;
            int i = desktopModeVisualIndicator.mTaskInfo.displayId;
            VisualIndicatorViewContainer visualIndicatorViewContainer = desktopModeVisualIndicator.mVisualIndicatorViewContainer;
            visualIndicatorViewContainer.getClass();
            if (indicatorType == DesktopModeVisualIndicator.IndicatorType.NO_INDICATOR || indicatorType == DesktopModeVisualIndicator.IndicatorType.TO_DESKTOP_MAXIMIZED_WINDOW) {
                runnable.run();
            } else {
                visualIndicatorViewContainer.desktopExecutor.execute(new VisualIndicatorViewContainer$fadeOutIndicator$1(visualIndicatorViewContainer, indicatorType, displayLayout, i, desktopModeVisualIndicator.mSnapEventHandler, runnable));
            }
        }
    }
}
