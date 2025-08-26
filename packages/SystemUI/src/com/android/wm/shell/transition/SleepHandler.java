package com.android.wm.shell.transition;

import android.os.IBinder;
import android.util.Slog;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.transition.Transitions;

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
