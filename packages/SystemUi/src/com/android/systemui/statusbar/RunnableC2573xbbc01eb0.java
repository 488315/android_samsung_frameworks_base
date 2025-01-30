package com.android.systemui.statusbar;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.statusbar.LockscreenShadeTransitionController$onDraggedDown$cancelRunnable$1 */
/* loaded from: classes2.dex */
public final class RunnableC2573xbbc01eb0 implements Runnable {
    public final /* synthetic */ LockscreenShadeTransitionController this$0;

    public RunnableC2573xbbc01eb0(LockscreenShadeTransitionController lockscreenShadeTransitionController) {
        this.this$0 = lockscreenShadeTransitionController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.this$0.logger.logGoingToLockedShadeAborted();
        this.this$0.setDragDownAmountAnimated(0.0f, 0L, null);
    }
}
