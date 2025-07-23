package com.android.wm.shell.desktopmode;

import com.android.wm.shell.common.DragHintToFullscreenManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DesktopTasksController$onDragPositioningEnd$1 implements Runnable {
    public final /* synthetic */ DesktopTasksController this$0;

    public DesktopTasksController$onDragPositioningEnd$1(DesktopTasksController desktopTasksController) {
        this.this$0 = desktopTasksController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        DragHintToFullscreenManager dragHintToFullscreenManager = this.this$0.dragHintToFullscreenManager;
        if (dragHintToFullscreenManager != null) {
            dragHintToFullscreenManager.removeWindow(false);
        }
        this.this$0.dragHintToFullscreenManager = null;
    }
}
