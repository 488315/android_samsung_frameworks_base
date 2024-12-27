package com.android.server.wm;

import android.util.Slog;

import java.util.function.Consumer;

public final /* synthetic */ class DexController$$ExternalSyntheticLambda7 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        Task task = (Task) obj;
        if (task.isActivityTypeHomeOrRecents()) {
            return;
        }
        boolean inFreeformWindowingMode = task.inFreeformWindowingMode();
        boolean z =
                task.hasOverrideBounds()
                        && task.inFullscreenWindowingMode()
                        && task.getRequestedOverrideWindowingMode() == 0;
        if (inFreeformWindowingMode || z) {
            task.moveToBack("deactivate standalone", null);
            task.ensureActivitiesVisible(false, null);
            task.setWindowingMode(1);
            if (task.hasOverrideBounds()) {
                Slog.d(
                        "DexController",
                        "changeAllRootTasksToFullscreenLocked: resize to full, isResizeable:"
                                + task.isResizeable(true));
                task.resize(0, null);
            }
        }
    }
}
