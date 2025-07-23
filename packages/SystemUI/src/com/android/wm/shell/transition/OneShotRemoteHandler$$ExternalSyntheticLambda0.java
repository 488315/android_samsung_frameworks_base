package com.android.wm.shell.transition;

import android.os.IBinder;
import android.util.Log;
import com.android.wm.shell.transition.Transitions;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class OneShotRemoteHandler$$ExternalSyntheticLambda0 implements IBinder.DeathRecipient {
    public final /* synthetic */ OneShotRemoteHandler f$0;
    public final /* synthetic */ Transitions.TransitionFinishCallback f$1;

    public /* synthetic */ OneShotRemoteHandler$$ExternalSyntheticLambda0(OneShotRemoteHandler oneShotRemoteHandler, Transitions.TransitionFinishCallback transitionFinishCallback) {
        this.f$0 = oneShotRemoteHandler;
        this.f$1 = transitionFinishCallback;
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        OneShotRemoteHandler oneShotRemoteHandler = this.f$0;
        final Transitions.TransitionFinishCallback transitionFinishCallback = this.f$1;
        oneShotRemoteHandler.getClass();
        Log.e("ShellTransitions", "Remote transition died, finishing");
        oneShotRemoteHandler.mMainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.transition.OneShotRemoteHandler$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                Transitions.TransitionFinishCallback.this.onTransitionFinished(null);
            }
        });
    }
}
