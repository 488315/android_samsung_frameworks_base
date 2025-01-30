package com.android.p038wm.shell.desktopmode;

import com.android.p038wm.shell.desktopmode.EnterDesktopTaskTransitionHandler;
import com.android.p038wm.shell.transition.Transitions;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class EnterDesktopTaskTransitionHandler$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Transitions.TransitionFinishCallback f$0;

    public /* synthetic */ EnterDesktopTaskTransitionHandler$$ExternalSyntheticLambda1(Transitions.TransitionFinishCallback transitionFinishCallback, int i) {
        this.$r8$classId = i;
        this.f$0 = transitionFinishCallback;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.onTransitionFinished(null, null);
                break;
            case 1:
                Transitions.TransitionFinishCallback transitionFinishCallback = this.f$0;
                int i = EnterDesktopTaskTransitionHandler.C39581.$r8$clinit;
                transitionFinishCallback.onTransitionFinished(null, null);
                break;
            default:
                Transitions.TransitionFinishCallback transitionFinishCallback2 = this.f$0;
                int i2 = EnterDesktopTaskTransitionHandler.C39592.$r8$clinit;
                transitionFinishCallback2.onTransitionFinished(null, null);
                break;
        }
    }
}
