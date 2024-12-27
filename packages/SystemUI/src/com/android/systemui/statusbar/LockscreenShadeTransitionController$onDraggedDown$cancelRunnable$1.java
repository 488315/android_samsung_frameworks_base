package com.android.systemui.statusbar;

public final class LockscreenShadeTransitionController$onDraggedDown$cancelRunnable$1 implements Runnable {
    public final /* synthetic */ LockscreenShadeTransitionController this$0;

    public LockscreenShadeTransitionController$onDraggedDown$cancelRunnable$1(LockscreenShadeTransitionController lockscreenShadeTransitionController) {
        this.this$0 = lockscreenShadeTransitionController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.this$0.logger.logGoingToLockedShadeAborted();
        this.this$0.setDragDownAmountAnimated(0.0f, 0L, null);
    }
}
