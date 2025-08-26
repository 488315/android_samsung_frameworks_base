package com.android.wm.shell.pip2.phone;

import android.window.TransitionInfo;
import com.android.wm.shell.pip2.animation.PipEnterAnimator;

/* loaded from: classes3.dex */
public final /* synthetic */ class PipTransition$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ PipEnterAnimator f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ PipTransition$$ExternalSyntheticLambda4(PipEnterAnimator pipEnterAnimator, TransitionInfo.Change change) {
        this.f$0 = pipEnterAnimator;
        this.f$1 = change;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.setEnterStartState((TransitionInfo.Change) this.f$1);
                break;
            default:
                PipTransition pipTransition = (PipTransition) this.f$1;
                PipEnterAnimator pipEnterAnimator = this.f$0;
                pipTransition.getClass();
                PipAppIconOverlay pipAppIconOverlay = pipEnterAnimator.mContentOverlay;
                if ((pipAppIconOverlay == null ? null : pipAppIconOverlay.mLeash) != null) {
                    pipTransition.mPipScheduler.startOverlayFadeoutAnimation(pipAppIconOverlay != null ? pipAppIconOverlay.mLeash : null, true, new PipTransition$$ExternalSyntheticLambda1(pipEnterAnimator, 2));
                }
                pipTransition.finishTransition();
                break;
        }
    }

    public /* synthetic */ PipTransition$$ExternalSyntheticLambda4(PipTransition pipTransition, PipEnterAnimator pipEnterAnimator) {
        this.f$1 = pipTransition;
        this.f$0 = pipEnterAnimator;
    }
}
