package com.android.wm.shell.transition;

import android.os.IBinder;
import android.util.Pair;
import android.window.RemoteTransition;
import com.android.wm.shell.transition.RemoteTransitionHandler;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class RemoteTransitionHandler$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ RemoteTransitionHandler$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                boolean z = RemoteTransitionHandler.SUPPORT_MINIMIZE_REMOTE_TRANSITION;
                ((Transitions.TransitionFinishCallback) obj).onTransitionFinished(null);
                break;
            default:
                RemoteTransitionHandler.RemoteDeathHandler remoteDeathHandler = (RemoteTransitionHandler.RemoteDeathHandler) obj;
                for (int size = RemoteTransitionHandler.this.mFilters.size() - 1; size >= 0; size--) {
                    if (remoteDeathHandler.mRemote.equals(((RemoteTransition) ((Pair) RemoteTransitionHandler.this.mFilters.get(size)).second).asBinder())) {
                        RemoteTransitionHandler.this.mFilters.remove(size);
                    }
                }
                for (int size2 = RemoteTransitionHandler.this.mRequestedRemotes.size() - 1; size2 >= 0; size2--) {
                    if (remoteDeathHandler.mRemote.equals(((RemoteTransition) RemoteTransitionHandler.this.mRequestedRemotes.valueAt(size2)).asBinder())) {
                        if (CoreRune.FW_SHELL_TRANSITION_MERGE) {
                            RemoteTransitionHandler.this.mRequestedInfoList.remove((IBinder) RemoteTransitionHandler.this.mRequestedRemotes.keyAt(size2));
                        }
                        RemoteTransitionHandler.this.mRequestedRemotes.removeAt(size2);
                    }
                }
                for (int size3 = remoteDeathHandler.mPendingFinishCallbacks.size() - 1; size3 >= 0; size3--) {
                    ((Transitions.TransitionFinishCallback) remoteDeathHandler.mPendingFinishCallbacks.get(size3)).onTransitionFinished(null);
                }
                remoteDeathHandler.mPendingFinishCallbacks.clear();
                break;
        }
    }
}
