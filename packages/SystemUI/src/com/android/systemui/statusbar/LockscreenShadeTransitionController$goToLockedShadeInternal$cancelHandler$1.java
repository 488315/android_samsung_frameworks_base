package com.android.systemui.statusbar;

/* loaded from: classes3.dex */
public final class LockscreenShadeTransitionController$goToLockedShadeInternal$cancelHandler$1 implements Runnable {
    public final /* synthetic */ Runnable $cancelAction;
    public final /* synthetic */ LockscreenShadeTransitionController this$0;

    public LockscreenShadeTransitionController$goToLockedShadeInternal$cancelHandler$1(LockscreenShadeTransitionController lockscreenShadeTransitionController, Runnable runnable) {
        this.this$0 = lockscreenShadeTransitionController;
        this.$cancelAction = runnable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ((StatusBarStateControllerImpl) this.this$0.statusBarStateController).setLeaveOpenOnKeyguardHide(false);
        this.this$0.getClass();
        Runnable runnable = this.$cancelAction;
        if (runnable != null) {
            runnable.run();
        }
    }
}
