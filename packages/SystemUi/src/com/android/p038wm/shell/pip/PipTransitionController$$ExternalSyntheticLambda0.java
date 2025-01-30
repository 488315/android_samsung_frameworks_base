package com.android.p038wm.shell.pip;

import com.android.p038wm.shell.pip.PipAnimationController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class PipTransitionController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PipTransitionController$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                PipTransitionController pipTransitionController = (PipTransitionController) this.f$0;
                pipTransitionController.mTransitions.addHandler(pipTransitionController);
                break;
            case 1:
                ((PipAnimationController.PipTransitionAnimator) this.f$0).mContentOverlay = null;
                break;
            case 2:
                ((PipAnimationController.PipTransitionAnimator) this.f$0).mContentOverlay = null;
                break;
            default:
                ((PipAnimationController.PipTransitionAnimator) this.f$0).mContentOverlay = null;
                break;
        }
    }
}
