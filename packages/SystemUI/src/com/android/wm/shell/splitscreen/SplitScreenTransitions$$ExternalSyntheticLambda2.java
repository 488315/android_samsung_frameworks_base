package com.android.wm.shell.splitscreen;

import android.animation.Animator;
import android.animation.ValueAnimator;

/* loaded from: classes3.dex */
public final /* synthetic */ class SplitScreenTransitions$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SplitScreenTransitions$$ExternalSyntheticLambda2(Animator animator, int i) {
        this.$r8$classId = i;
        this.f$0 = animator;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((Animator) obj).end();
                break;
            case 1:
                Animator animator = (Animator) obj;
                if (!animator.isStarted()) {
                    animator.start();
                    break;
                }
                break;
            default:
                ((ValueAnimator) obj).start();
                break;
        }
    }

    public /* synthetic */ SplitScreenTransitions$$ExternalSyntheticLambda2(ValueAnimator valueAnimator) {
        this.$r8$classId = 2;
        this.f$0 = valueAnimator;
    }
}
