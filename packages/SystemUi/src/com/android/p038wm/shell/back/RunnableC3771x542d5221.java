package com.android.p038wm.shell.back;

import com.android.p038wm.shell.back.BackAnimationController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.wm.shell.back.BackAnimationController$BackAnimationImpl$$ExternalSyntheticLambda0 */
/* loaded from: classes2.dex */
public final /* synthetic */ class RunnableC3771x542d5221 implements Runnable {
    public final /* synthetic */ BackAnimationController.BackAnimationImpl f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ RunnableC3771x542d5221(BackAnimationController.BackAnimationImpl backAnimationImpl, boolean z) {
        this.f$0 = backAnimationImpl;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        BackAnimationController.BackAnimationImpl backAnimationImpl = this.f$0;
        boolean z = this.f$1;
        BackAnimationController backAnimationController = BackAnimationController.this;
        if (backAnimationController.mPostCommitAnimationInProgress) {
            return;
        }
        backAnimationController.mTriggerBack = z;
        TouchTracker touchTracker = backAnimationController.mTouchTracker;
        if (touchTracker.mTriggerBack != z && !z) {
            touchTracker.mCancelled = true;
        }
        touchTracker.mTriggerBack = z;
    }
}
