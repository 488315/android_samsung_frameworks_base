package com.android.wm.shell.recents;

import android.app.ActivityTaskManager;
import android.os.RemoteException;
import android.util.Slog;
import com.android.wm.shell.protolog.ShellProtoLogCache;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.protolog.ShellProtoLogImpl;
import com.android.wm.shell.recents.RecentsTransitionHandler;
import com.android.wm.shell.transition.Transitions;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.wm.shell.recents.RecentsTransitionHandler$RecentsController$$ExternalSyntheticLambda1 */
/* loaded from: classes2.dex */
public final /* synthetic */ class RunnableC4088x1263b67d implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ RecentsTransitionHandler.RecentsController f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ RunnableC4088x1263b67d(RecentsTransitionHandler.RecentsController recentsController, boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = recentsController;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                RecentsTransitionHandler.RecentsController recentsController = this.f$0;
                boolean z = this.f$1;
                Transitions.TransitionFinishCallback transitionFinishCallback = recentsController.mFinishCB;
                if (transitionFinishCallback != null && z) {
                    int displayId = recentsController.mInfo.getRootCount() > 0 ? recentsController.mInfo.getRoot(0).getDisplayId() : 0;
                    try {
                        if (ShellProtoLogCache.WM_SHELL_RECENTS_TRANSITION_enabled) {
                            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 867205103, 1, "[%d] RecentsController.setInputConsumerEnabled: set focus to recents", Long.valueOf(recentsController.mInstanceId));
                        }
                        ActivityTaskManager.getService().focusTopTask(displayId);
                        break;
                    } catch (RemoteException e) {
                        Slog.e("RecentsTransitionHandler", "Failed to set focused task", e);
                        return;
                    }
                } else if (ShellProtoLogCache.WM_SHELL_RECENTS_TRANSITION_enabled) {
                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -1058990760, 15, "RecentsController.setInputConsumerEnabled: skip, cb?=%b enabled?=%b", Boolean.valueOf(transitionFinishCallback != null), Boolean.valueOf(z));
                    break;
                }
                break;
            default:
                this.f$0.mWillFinishToHome = this.f$1;
                break;
        }
    }
}
