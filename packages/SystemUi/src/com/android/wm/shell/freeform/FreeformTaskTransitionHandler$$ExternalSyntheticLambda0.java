package com.android.wm.shell.freeform;

import android.app.ActivityTaskManager;
import android.os.RemoteException;
import android.util.Log;
import android.util.SparseArray;
import com.android.wm.shell.shortcut.ShortcutController;
import com.android.wm.shell.shortcut.ShortcutDownKeyLaunchPolicy;
import com.android.wm.shell.shortcut.ShortcutLeftKeyLaunchPolicy;
import com.android.wm.shell.shortcut.ShortcutRightKeyLaunchPolicy;
import com.android.wm.shell.shortcut.ShortcutRotationKeyLaunchPolicy;
import com.android.wm.shell.shortcut.ShortcutUpKeyLaunchPolicy;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.windowdecor.TaskOperations;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class FreeformTaskTransitionHandler$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ FreeformTaskTransitionHandler$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                FreeformTaskTransitionHandler freeformTaskTransitionHandler = (FreeformTaskTransitionHandler) this.f$0;
                freeformTaskTransitionHandler.mWindowDecorViewModel.setFreeformTaskTransitionStarter(freeformTaskTransitionHandler);
                ShortcutController shortcutController = freeformTaskTransitionHandler.mShortcutController;
                shortcutController.mTaskOperations = new TaskOperations(freeformTaskTransitionHandler, shortcutController.mContext, shortcutController.mSyncQueue, shortcutController.mSplitScreenController);
                SparseArray sparseArray = shortcutController.mShortCutPolicyMap;
                sparseArray.put(21, new ShortcutLeftKeyLaunchPolicy(shortcutController));
                sparseArray.put(22, new ShortcutRightKeyLaunchPolicy(shortcutController));
                sparseArray.put(19, new ShortcutUpKeyLaunchPolicy(shortcutController));
                sparseArray.put(20, new ShortcutDownKeyLaunchPolicy(shortcutController));
                sparseArray.put(46, new ShortcutRotationKeyLaunchPolicy(shortcutController));
                try {
                    ActivityTaskManager.getService().registKeyEventListener(shortcutController.mKeyEventListener);
                    break;
                } catch (RemoteException e) {
                    Log.e("ShortcutController", "setFreeformTaskTransitionStarter: e=" + e);
                }
            default:
                ((Transitions.TransitionFinishCallback) this.f$0).onTransitionFinished(null, null);
                break;
        }
    }
}
