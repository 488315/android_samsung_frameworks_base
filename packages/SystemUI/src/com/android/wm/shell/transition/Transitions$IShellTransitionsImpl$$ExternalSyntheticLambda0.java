package com.android.wm.shell.transition;

import android.util.Pair;
import android.window.RemoteTransition;
import android.window.TransitionFilter;
import com.android.wm.shell.transition.Transitions;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class Transitions$IShellTransitionsImpl$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TransitionFilter f$0;
    public final /* synthetic */ RemoteTransition f$1;

    public /* synthetic */ Transitions$IShellTransitionsImpl$$ExternalSyntheticLambda0(TransitionFilter transitionFilter, RemoteTransition remoteTransition, int i) {
        this.$r8$classId = i;
        this.f$0 = transitionFilter;
        this.f$1 = remoteTransition;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                TransitionFilter transitionFilter = this.f$0;
                RemoteTransition remoteTransition = this.f$1;
                int i = Transitions.IShellTransitionsImpl.$r8$clinit;
                RemoteTransitionHandler remoteTransitionHandler = ((Transitions) obj).mRemoteTransitionHandler;
                remoteTransitionHandler.getClass();
                remoteTransitionHandler.handleDeath(remoteTransition.asBinder(), null);
                remoteTransitionHandler.mFilters.add(new Pair(transitionFilter, remoteTransition));
                break;
            default:
                TransitionFilter transitionFilter2 = this.f$0;
                RemoteTransition remoteTransition2 = this.f$1;
                int i2 = Transitions.IShellTransitionsImpl.$r8$clinit;
                RemoteTransitionHandler remoteTransitionHandler2 = ((Transitions) obj).mRemoteTransitionHandler;
                remoteTransitionHandler2.getClass();
                remoteTransitionHandler2.handleDeath(remoteTransition2.asBinder(), null);
                remoteTransitionHandler2.mTakeoverFilters.add(new Pair(transitionFilter2, remoteTransition2));
                break;
        }
    }
}
