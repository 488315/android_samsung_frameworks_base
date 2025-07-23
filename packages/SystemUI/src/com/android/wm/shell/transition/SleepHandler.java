package com.android.wm.shell.transition;

import android.os.IBinder;
import android.util.Slog;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.transition.Transitions;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SleepHandler implements Transitions.TransitionHandler {
    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        return new WindowContainerTransaction();
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        if (transitionInfo.hasChangesOrSideEffects()) {
            Slog.e("ShellTransitions", "Real changes included in a SLEEP transition");
            return false;
        }
        transaction.apply();
        transitionFinishCallback.onTransitionFinished(null);
        return true;
    }
}
