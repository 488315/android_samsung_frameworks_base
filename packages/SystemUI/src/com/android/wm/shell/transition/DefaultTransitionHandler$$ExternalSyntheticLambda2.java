package com.android.wm.shell.transition;

import android.os.IBinder;
import com.android.wm.shell.transition.Transitions;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class DefaultTransitionHandler$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ DefaultTransitionHandler f$0;
    public final /* synthetic */ ArrayList f$1;
    public final /* synthetic */ boolean f$2;
    public final /* synthetic */ IBinder f$3;
    public final /* synthetic */ Transitions.TransitionFinishCallback f$4;

    public /* synthetic */ DefaultTransitionHandler$$ExternalSyntheticLambda2(DefaultTransitionHandler defaultTransitionHandler, ArrayList arrayList, boolean z, IBinder iBinder, Transitions.TransitionFinishCallback transitionFinishCallback) {
        this.f$0 = defaultTransitionHandler;
        this.f$1 = arrayList;
        this.f$2 = z;
        this.f$3 = iBinder;
        this.f$4 = transitionFinishCallback;
    }

    @Override // java.lang.Runnable
    public final void run() {
        DefaultTransitionHandler defaultTransitionHandler = this.f$0;
        ArrayList arrayList = this.f$1;
        boolean z = this.f$2;
        IBinder iBinder = this.f$3;
        Transitions.TransitionFinishCallback transitionFinishCallback = this.f$4;
        defaultTransitionHandler.getClass();
        if (arrayList.isEmpty()) {
            if (z) {
                defaultTransitionHandler.mInteractionJankMonitor.end(128);
            }
            defaultTransitionHandler.mAnimations.remove(iBinder);
            transitionFinishCallback.onTransitionFinished(null);
        }
    }
}
