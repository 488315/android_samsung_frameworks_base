package com.android.wm.shell.common.transition;

import com.android.wm.shell.recents.RecentsTransitionHandler;
import com.android.wm.shell.recents.RecentsTransitionStateListener;
import com.android.wm.shell.sysui.ShellInit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class TransitionStateHolder {
    public final RecentsTransitionHandler recentsTransitionHandler;
    public volatile int recentsTransitionState = 1;

    public TransitionStateHolder(ShellInit shellInit, RecentsTransitionHandler recentsTransitionHandler) {
        this.recentsTransitionHandler = recentsTransitionHandler;
        shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.common.transition.TransitionStateHolder.1
            @Override // java.lang.Runnable
            public final void run() {
                final TransitionStateHolder transitionStateHolder = TransitionStateHolder.this;
                transitionStateHolder.getClass();
                transitionStateHolder.recentsTransitionHandler.mStateListeners.add(new RecentsTransitionStateListener() { // from class: com.android.wm.shell.common.transition.TransitionStateHolder$onInit$1
                    @Override // com.android.wm.shell.recents.RecentsTransitionStateListener
                    public final void onTransitionStateChanged(int i) {
                        TransitionStateHolder.this.recentsTransitionState = i;
                    }
                });
            }
        }, this);
    }
}
