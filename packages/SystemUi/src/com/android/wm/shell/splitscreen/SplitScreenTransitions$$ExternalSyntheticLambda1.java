package com.android.wm.shell.splitscreen;

import android.animation.Animator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class SplitScreenTransitions$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Animator f$0;

    public /* synthetic */ SplitScreenTransitions$$ExternalSyntheticLambda1(Animator animator, int i) {
        this.$r8$classId = i;
        this.f$0 = animator;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
            case 1:
                this.f$0.end();
                break;
            default:
                Animator animator = this.f$0;
                if (!animator.isStarted()) {
                    animator.start();
                    break;
                }
                break;
        }
    }
}
