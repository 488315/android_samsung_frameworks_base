package com.android.wm.shell.transition;

import android.animation.Animator;
import com.android.wm.shell.transition.Transitions;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class OneShotRemoteHandler$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ OneShotRemoteHandler$$ExternalSyntheticLambda2(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((Transitions.TransitionFinishCallback) this.f$0).onTransitionFinished(null, null);
                break;
            default:
                ((Animator) this.f$0).cancel();
                break;
        }
    }
}
