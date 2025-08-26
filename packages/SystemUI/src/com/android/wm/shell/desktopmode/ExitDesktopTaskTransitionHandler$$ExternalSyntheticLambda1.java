package com.android.wm.shell.desktopmode;

import android.animation.Animator;
import com.android.wm.shell.desktopmode.ExitDesktopTaskTransitionHandler;
import com.android.wm.shell.transition.Transitions;

/* loaded from: classes3.dex */
public final /* synthetic */ class ExitDesktopTaskTransitionHandler$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ExitDesktopTaskTransitionHandler$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                Animator animator = (Animator) obj;
                if (!animator.isStarted()) {
                    animator.start();
                    break;
                }
                break;
            case 1:
                ((ExitDesktopTaskTransitionHandler) obj).onFinish();
                break;
            default:
                int i2 = ExitDesktopTaskTransitionHandler.AnonymousClass1.$r8$clinit;
                ((Transitions.TransitionFinishCallback) obj).onTransitionFinished(null);
                break;
        }
    }
}
