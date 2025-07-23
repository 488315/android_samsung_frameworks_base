package com.android.wm.shell.transition;

import android.os.IBinder;
import android.util.Log;
import android.window.RemoteTransition;
import com.android.wm.shell.shared.FocusTransitionListener;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class Transitions$ShellTransitionImpl$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ Transitions$ShellTransitionImpl$$ExternalSyntheticLambda1(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = -1;
        switch (this.$r8$classId) {
            case 0:
                Transitions.this.mRemoteTransitionHandler.removeFiltered((RemoteTransition) this.f$1);
                break;
            case 1:
                ((HashMap) Transitions.this.mFocusTransitionObserver.mLocalListeners).remove((FocusTransitionListener) this.f$1);
                break;
            default:
                Transitions.TransitionPlayerImpl transitionPlayerImpl = (Transitions.TransitionPlayerImpl) this.f$0;
                IBinder iBinder = (IBinder) this.f$1;
                Transitions transitions = Transitions.this;
                boolean z = Transitions.DEBUG_START_TRANSITION;
                transitions.getClass();
                if (CoreRune.MW_SHELL_TRANSITION) {
                    ArrayList arrayList = transitions.mPendingTransitions;
                    int size = arrayList.size() - 1;
                    while (true) {
                        if (size >= 0) {
                            if (((Transitions.ActiveTransition) arrayList.get(size)).mToken == iBinder) {
                                i = size;
                            } else {
                                size--;
                            }
                        }
                    }
                    if (i >= 0) {
                        transitions.mPendingTransitions.remove(i);
                        break;
                    } else {
                        Log.e("ShellTransitions", "Got transitionAborted for non-pending transition " + iBinder + ". expecting one of " + Arrays.toString(transitions.mPendingTransitions.stream().map(new Transitions$$ExternalSyntheticLambda0(1)).toArray()));
                        break;
                    }
                }
                break;
        }
    }
}
