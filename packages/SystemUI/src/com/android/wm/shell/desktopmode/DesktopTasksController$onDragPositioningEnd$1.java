package com.android.wm.shell.desktopmode;

import com.android.wm.shell.common.DragHintToFullscreenManager;

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
