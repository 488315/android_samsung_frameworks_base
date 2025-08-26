package com.android.wm.shell.desktopmode;

import android.animation.ValueAnimator;
import com.android.wm.shell.desktopmode.ExitDesktopTaskTransitionHandler;
import com.android.wm.shell.transition.Transitions;

/* loaded from: classes3.dex */
public final /* synthetic */ class ExitDesktopTaskTransitionHandler$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ ExitDesktopTaskTransitionHandler$$ExternalSyntheticLambda2(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ExitDesktopTaskTransitionHandler exitDesktopTaskTransitionHandler = (ExitDesktopTaskTransitionHandler) this.f$0;
                Transitions.TransitionFinishCallback transitionFinishCallback = (Transitions.TransitionFinishCallback) this.f$1;
                DesktopTasksController$$ExternalSyntheticLambda1 desktopTasksController$$ExternalSyntheticLambda1 = exitDesktopTaskTransitionHandler.mOnAnimationFinishedCallback;
                if (desktopTasksController$$ExternalSyntheticLambda1 != null) {
                    desktopTasksController$$ExternalSyntheticLambda1.invoke();
                }
                transitionFinishCallback.onTransitionFinished(null);
                break;
            default:
                ExitDesktopTaskTransitionHandler.AnonymousClass1 anonymousClass1 = (ExitDesktopTaskTransitionHandler.AnonymousClass1) this.f$0;
                anonymousClass1.this$0.mAnimators.remove((ValueAnimator) this.f$1);
                anonymousClass1.this$0.onFinish();
                break;
        }
    }
}
