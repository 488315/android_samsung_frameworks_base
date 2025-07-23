package com.android.wm.shell.desktopmode;

import android.os.IBinder;
import android.view.SurfaceControl;
import android.window.DesktopExperienceFlags;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.Transitions;
import java.util.LinkedHashSet;
import java.util.Set;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DisplayDisconnectTransitionHandler implements Transitions.TransitionHandler {
    public final Set pendingTransitions = new LinkedHashSet();
    public final Transitions transitions;

    public DisplayDisconnectTransitionHandler(Transitions transitions, ShellInit shellInit) {
        this.transitions = transitions;
        shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.desktopmode.DisplayDisconnectTransitionHandler.1
            @Override // java.lang.Runnable
            public final void run() {
                DisplayDisconnectTransitionHandler displayDisconnectTransitionHandler = DisplayDisconnectTransitionHandler.this;
                displayDisconnectTransitionHandler.transitions.addHandler(displayDisconnectTransitionHandler);
            }
        }, this);
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        TransitionRequestInfo.DisplayChange displayChange = transitionRequestInfo.getDisplayChange();
        if (displayChange != null && DesktopExperienceFlags.ENABLE_DISPLAY_DISCONNECT_INTERACTION.isTrue() && displayChange.getDisconnectReparentDisplay() != -1) {
            this.pendingTransitions.add(iBinder);
        }
        return null;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        if (!this.pendingTransitions.contains(iBinder)) {
            return false;
        }
        transaction.apply();
        transitionFinishCallback.onTransitionFinished(null);
        this.pendingTransitions.remove(iBinder);
        return true;
    }
}
