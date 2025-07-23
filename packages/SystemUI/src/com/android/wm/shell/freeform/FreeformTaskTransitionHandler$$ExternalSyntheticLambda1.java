package com.android.wm.shell.freeform;

import android.os.IBinder;
import com.android.wm.shell.transition.Transitions;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class FreeformTaskTransitionHandler$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FreeformTaskTransitionHandler f$0;
    public final /* synthetic */ ArrayList f$1;
    public final /* synthetic */ IBinder f$2;
    public final /* synthetic */ Transitions.TransitionFinishCallback f$3;

    public /* synthetic */ FreeformTaskTransitionHandler$$ExternalSyntheticLambda1(FreeformTaskTransitionHandler freeformTaskTransitionHandler, ArrayList arrayList, IBinder iBinder, Transitions.TransitionFinishCallback transitionFinishCallback, int i) {
        this.$r8$classId = i;
        this.f$0 = freeformTaskTransitionHandler;
        this.f$1 = arrayList;
        this.f$2 = iBinder;
        this.f$3 = transitionFinishCallback;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                FreeformTaskTransitionHandler freeformTaskTransitionHandler = this.f$0;
                ArrayList arrayList = this.f$1;
                IBinder iBinder = this.f$2;
                Transitions.TransitionFinishCallback transitionFinishCallback = this.f$3;
                freeformTaskTransitionHandler.getClass();
                freeformTaskTransitionHandler.mMainExecutor.execute(new FreeformTaskTransitionHandler$$ExternalSyntheticLambda1(freeformTaskTransitionHandler, arrayList, iBinder, transitionFinishCallback, 1));
                break;
            default:
                FreeformTaskTransitionHandler freeformTaskTransitionHandler2 = this.f$0;
                ArrayList arrayList2 = this.f$1;
                IBinder iBinder2 = this.f$2;
                Transitions.TransitionFinishCallback transitionFinishCallback2 = this.f$3;
                freeformTaskTransitionHandler2.getClass();
                if (arrayList2.isEmpty()) {
                    freeformTaskTransitionHandler2.mAnimations.remove(iBinder2);
                    transitionFinishCallback2.onTransitionFinished(null);
                    break;
                }
                break;
        }
    }
}
