package com.android.wm.shell.recents;

import android.app.ActivityTaskManager;
import android.content.res.Resources;
import android.os.RemoteException;
import android.util.Slog;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.recents.RecentsTransitionHandler;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes3.dex */
public final /* synthetic */ class RecentsTransitionHandler$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ RecentsTransitionHandler$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() throws Resources.NotFoundException {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                RecentsTransitionHandler recentsTransitionHandler = (RecentsTransitionHandler) obj;
                recentsTransitionHandler.mRecentTasksController.mTransitionHandler = recentsTransitionHandler;
                Transitions transitions = recentsTransitionHandler.mTransitions;
                transitions.addHandler(recentsTransitionHandler);
                transitions.registerObserver(recentsTransitionHandler);
                if (CoreRune.MW_FREEFORM_FORCE_HIDING_TRANSITION) {
                    recentsTransitionHandler.mMultiTaskingTransitions = transitions.mMultiTaskingTransitProvider;
                }
                boolean z = CoreRune.MW_SHELL_TRANSITION_BUG_FIX;
                break;
            case 1:
                RecentsTransitionHandler.RecentsController recentsController = (RecentsTransitionHandler.RecentsController) obj;
                int i2 = RecentsTransitionHandler.RecentsController.$r8$clinit;
                recentsController.getClass();
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -8322929235470962491L, 1, Long.valueOf(recentsController.mInstanceId));
                }
                recentsController.finishInner(recentsController.mWillFinishToHome, false, null, "deathRecipient");
                break;
            case 2:
                int i3 = RecentsTransitionHandler.RecentsController.$r8$clinit;
                ((RecentsTransitionHandler.RecentsController) obj).finishInner(true, false, null, "merge");
                break;
            default:
                RecentsTransitionHandler.RecentsController recentsController2 = (RecentsTransitionHandler.RecentsController) obj;
                if (recentsController2.mTransition != null) {
                    try {
                        ActivityTaskManager.getService().detachNavigationBarFromApp(recentsController2.mTransition);
                        break;
                    } catch (RemoteException e) {
                        Slog.e("RecentsTransitionHandler", "Failed to detach the navigation bar from app", e);
                    }
                }
                break;
        }
    }
}
