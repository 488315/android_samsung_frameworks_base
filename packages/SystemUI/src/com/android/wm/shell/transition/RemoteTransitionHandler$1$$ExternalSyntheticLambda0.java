package com.android.wm.shell.transition;

import android.os.IBinder;
import android.util.Log;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.transition.RemoteTransitionHandler;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes3.dex */
public final /* synthetic */ class RemoteTransitionHandler$1$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ IBinder f$1;
    public final /* synthetic */ Transitions.TransitionFinishCallback f$2;
    public final /* synthetic */ WindowContainerTransaction f$3;

    public /* synthetic */ RemoteTransitionHandler$1$$ExternalSyntheticLambda0(RemoteTransitionHandler.AnonymousClass1 anonymousClass1, IBinder iBinder, Transitions.TransitionFinishCallback transitionFinishCallback, WindowContainerTransaction windowContainerTransaction) {
        this.f$0 = anonymousClass1;
        this.f$1 = iBinder;
        this.f$2 = transitionFinishCallback;
        this.f$3 = windowContainerTransaction;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                RemoteTransitionHandler.AnonymousClass1 anonymousClass1 = (RemoteTransitionHandler.AnonymousClass1) this.f$0;
                IBinder iBinder = this.f$1;
                Transitions.TransitionFinishCallback transitionFinishCallback = this.f$2;
                WindowContainerTransaction windowContainerTransaction = this.f$3;
                RemoteTransitionHandler.this.mRequestedRemotes.remove(iBinder);
                if (CoreRune.MW_FREEFORM_FORCE_HIDING_TRANSITION) {
                    MultiTaskingTransitionProvider.cancelForceHideAnimationsIfNeeded("RemoteTransitionHandler", RemoteTransitionHandler.this.mAnimExecutor);
                }
                if (CoreRune.FW_SHELL_TRANSITION_MERGE) {
                    RemoteTransitionHandler.this.mRequestedInfoList.remove(iBinder);
                }
                transitionFinishCallback.onTransitionFinished(windowContainerTransaction);
                break;
            default:
                RemoteTransitionHandler.AnonymousClass2 anonymousClass2 = (RemoteTransitionHandler.AnonymousClass2) this.f$0;
                IBinder iBinder2 = this.f$1;
                Transitions.TransitionFinishCallback transitionFinishCallback2 = this.f$2;
                WindowContainerTransaction windowContainerTransaction2 = this.f$3;
                if (!RemoteTransitionHandler.this.mRequestedRemotes.containsKey(iBinder2)) {
                    Log.e("RemoteTransitionHandler", "Merged transition finished after it's mergeTarget (the transition it was supposed to merge into). This usually means that the mergeTarget's RemoteTransition impl erroneously accepted/ran the merge request after finishing the mergeTarget");
                }
                transitionFinishCallback2.onTransitionFinished(windowContainerTransaction2);
                break;
        }
    }

    public /* synthetic */ RemoteTransitionHandler$1$$ExternalSyntheticLambda0(RemoteTransitionHandler.AnonymousClass2 anonymousClass2, IBinder iBinder, Transitions.TransitionFinishCallback transitionFinishCallback, WindowContainerTransaction windowContainerTransaction) {
        this.f$0 = anonymousClass2;
        this.f$1 = iBinder;
        this.f$2 = transitionFinishCallback;
        this.f$3 = windowContainerTransaction;
    }
}
