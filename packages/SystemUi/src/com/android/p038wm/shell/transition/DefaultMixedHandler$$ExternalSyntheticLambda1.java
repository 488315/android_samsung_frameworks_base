package com.android.p038wm.shell.transition;

import android.window.WindowContainerTransaction;
import android.window.WindowContainerTransactionCallback;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.p038wm.shell.transition.DefaultMixedHandler;
import com.android.p038wm.shell.transition.Transitions;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class DefaultMixedHandler$$ExternalSyntheticLambda1 implements Transitions.TransitionFinishCallback {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DefaultMixedHandler f$0;
    public final /* synthetic */ DefaultMixedHandler.MixedTransition f$1;
    public final /* synthetic */ Transitions.TransitionFinishCallback f$2;

    public /* synthetic */ DefaultMixedHandler$$ExternalSyntheticLambda1(DefaultMixedHandler defaultMixedHandler, DefaultMixedHandler.MixedTransition mixedTransition, Transitions.TransitionFinishCallback transitionFinishCallback, int i) {
        this.$r8$classId = i;
        this.f$0 = defaultMixedHandler;
        this.f$1 = mixedTransition;
        this.f$2 = transitionFinishCallback;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionFinishCallback
    public final void onTransitionFinished(WindowContainerTransaction windowContainerTransaction, WindowContainerTransactionCallback windowContainerTransactionCallback) {
        int i = this.$r8$classId;
        Transitions.TransitionFinishCallback transitionFinishCallback = this.f$2;
        DefaultMixedHandler.MixedTransition mixedTransition = this.f$1;
        DefaultMixedHandler defaultMixedHandler = this.f$0;
        switch (i) {
            case 0:
                defaultMixedHandler.getClass();
                int i2 = mixedTransition.mInFlightSubAnimations - 1;
                mixedTransition.mInFlightSubAnimations = i2;
                if (i2 <= 0) {
                    defaultMixedHandler.mActiveTransitions.remove(mixedTransition);
                    transitionFinishCallback.onTransitionFinished(windowContainerTransaction, windowContainerTransactionCallback);
                    break;
                }
                break;
            case 1:
                defaultMixedHandler.getClass();
                int i3 = mixedTransition.mInFlightSubAnimations - 1;
                mixedTransition.mInFlightSubAnimations = i3;
                if (i3 == 0) {
                    defaultMixedHandler.mActiveTransitions.remove(mixedTransition);
                    transitionFinishCallback.onTransitionFinished(windowContainerTransaction, windowContainerTransactionCallback);
                    break;
                }
                break;
            case 2:
                defaultMixedHandler.getClass();
                mixedTransition.mInFlightSubAnimations--;
                mixedTransition.joinFinishArgs(windowContainerTransaction, windowContainerTransactionCallback);
                if (mixedTransition.mInFlightSubAnimations <= 0) {
                    defaultMixedHandler.mActiveTransitions.remove(mixedTransition);
                    transitionFinishCallback.onTransitionFinished(mixedTransition.mFinishWCT, null);
                    break;
                }
                break;
            case 3:
                defaultMixedHandler.getClass();
                mixedTransition.mInFlightSubAnimations--;
                mixedTransition.joinFinishArgs(windowContainerTransaction, windowContainerTransactionCallback);
                if (mixedTransition.mInFlightSubAnimations <= 0) {
                    defaultMixedHandler.mActiveTransitions.remove(mixedTransition);
                    transitionFinishCallback.onTransitionFinished(mixedTransition.mFinishWCT, windowContainerTransactionCallback);
                    break;
                }
                break;
            case 4:
                defaultMixedHandler.getClass();
                mixedTransition.mInFlightSubAnimations--;
                mixedTransition.joinFinishArgs(windowContainerTransaction, windowContainerTransactionCallback);
                RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("animateEnterPipWithDefaultTransition: remain flight="), mixedTransition.mInFlightSubAnimations, "DefaultMixedHandler");
                if (mixedTransition.mInFlightSubAnimations <= 0) {
                    defaultMixedHandler.mActiveTransitions.remove(mixedTransition);
                    transitionFinishCallback.onTransitionFinished(mixedTransition.mFinishWCT, null);
                    break;
                }
                break;
            case 5:
                defaultMixedHandler.getClass();
                mixedTransition.mInFlightSubAnimations--;
                mixedTransition.joinFinishArgs(windowContainerTransaction, windowContainerTransactionCallback);
                if (mixedTransition.mInFlightSubAnimations <= 0) {
                    defaultMixedHandler.mActiveTransitions.remove(mixedTransition);
                    transitionFinishCallback.onTransitionFinished(mixedTransition.mFinishWCT, null);
                    break;
                }
                break;
            default:
                defaultMixedHandler.getClass();
                mixedTransition.mInFlightSubAnimations--;
                mixedTransition.joinFinishArgs(windowContainerTransaction, windowContainerTransactionCallback);
                if (mixedTransition.mInFlightSubAnimations <= 0) {
                    defaultMixedHandler.mActiveTransitions.remove(mixedTransition);
                    transitionFinishCallback.onTransitionFinished(mixedTransition.mFinishWCT, null);
                    break;
                }
                break;
        }
    }
}
