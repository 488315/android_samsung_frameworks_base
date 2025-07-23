package com.android.wm.shell.transition;

import android.window.TransitionInfo;
import android.window.WindowContainerTransaction;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.transition.DefaultMixedHandler;
import com.android.wm.shell.transition.Transitions;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class DefaultMixedHandler$$ExternalSyntheticLambda5 implements Transitions.TransitionFinishCallback {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ DefaultMixedHandler$$ExternalSyntheticLambda5(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionFinishCallback
    public final void onTransitionFinished(WindowContainerTransaction windowContainerTransaction) {
        switch (this.$r8$classId) {
            case 0:
                ((DefaultMixedHandler) this.f$0).mPipHandler.mShellTaskOrganizer.applyTransaction(windowContainerTransaction);
                ((DefaultMixedHandler$$ExternalSyntheticLambda4) this.f$1).onTransitionFinished(null);
                break;
            case 1:
                ((DefaultMixedHandler) this.f$0).mPipHandler.mShellTaskOrganizer.applyTransaction(windowContainerTransaction);
                ((DefaultMixedHandler$$ExternalSyntheticLambda4) this.f$1).onTransitionFinished(null);
                break;
            default:
                TransitionInfo transitionInfo = (TransitionInfo) this.f$1;
                DefaultMixedHandler.MixedTransition mixedTransition = (DefaultMixedHandler.MixedTransition) this.f$0;
                mixedTransition.mInFlightSubAnimations--;
                if (mixedTransition.mInfo != null && ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[0]) {
                    ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -1915658714613334671L, 21, Long.valueOf(r1.getDebugId()), Long.valueOf(transitionInfo.getDebugId()), Long.valueOf(mixedTransition.mInFlightSubAnimations));
                }
                mixedTransition.joinFinishArgs(windowContainerTransaction);
                if (mixedTransition.mInFlightSubAnimations == 0) {
                    mixedTransition.mFinishCB.onTransitionFinished(mixedTransition.mFinishWCT);
                    break;
                }
                break;
        }
    }
}
