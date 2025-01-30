package com.android.p038wm.shell.freeform;

import android.app.ActivityManager;
import android.app.WindowConfiguration;
import android.os.Debug;
import android.os.IBinder;
import android.util.Log;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import com.android.p038wm.shell.common.HandlerExecutor;
import com.android.p038wm.shell.shortcut.ShortcutController;
import com.android.p038wm.shell.sysui.ShellInit;
import com.android.p038wm.shell.transition.Transitions;
import com.android.p038wm.shell.transition.change.ChangeTransitionProvider;
import com.android.p038wm.shell.windowdecor.WindowDecorViewModel;
import com.samsung.android.knox.EnterpriseContainerCallback;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class FreeformTaskTransitionHandler implements Transitions.TransitionHandler, FreeformTaskTransitionStarter {
    public final List mPendingTransitionTokens = new ArrayList();
    public final ShortcutController mShortcutController;
    public final Transitions mTransitions;
    public final WindowDecorViewModel mWindowDecorViewModel;

    public FreeformTaskTransitionHandler(ShellInit shellInit, Transitions transitions, WindowDecorViewModel windowDecorViewModel, ShortcutController shortcutController) {
        this.mTransitions = transitions;
        this.mWindowDecorViewModel = windowDecorViewModel;
        if (Transitions.ENABLE_SHELL_TRANSITIONS) {
            shellInit.addInitCallback(new FreeformTaskTransitionHandler$$ExternalSyntheticLambda0(this, 0), this);
        }
        this.mShortcutController = shortcutController;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        return null;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        int i;
        List list;
        ActivityManager.RunningTaskInfo taskInfo;
        Iterator it = transitionInfo.getChanges().iterator();
        int i2 = 0;
        while (true) {
            boolean hasNext = it.hasNext();
            i = 1;
            list = this.mPendingTransitionTokens;
            if (!hasNext) {
                break;
            }
            TransitionInfo.Change change = (TransitionInfo.Change) it.next();
            if ((change.getFlags() & 2) == 0 && (taskInfo = change.getTaskInfo()) != null && taskInfo.taskId != -1) {
                int mode = change.getMode();
                if (mode == 4) {
                    i2 |= ((ArrayList) list).contains(iBinder) ? 1 : 0;
                } else if (mode == 6) {
                    int type = transitionInfo.getType();
                    if (!CoreRune.MW_SHELL_CHANGE_TRANSITION && ((ArrayList) list).contains(iBinder)) {
                        ActivityManager.RunningTaskInfo taskInfo2 = change.getTaskInfo();
                        int i3 = (type == 1008 && taskInfo2.getWindowingMode() == 1) ? 1 : 0;
                        if (type != 1009 || taskInfo2.getWindowingMode() != 5) {
                            i = i3;
                        }
                    } else {
                        i = 0;
                    }
                    i2 |= i;
                }
            }
        }
        ((ArrayList) list).remove(iBinder);
        if (i2 == 0) {
            return false;
        }
        transaction.apply();
        ((HandlerExecutor) this.mTransitions.mMainExecutor).execute(new FreeformTaskTransitionHandler$$ExternalSyntheticLambda0(transitionFinishCallback, i));
        return true;
    }

    public final void startWindowingModeTransition(WindowContainerTransaction windowContainerTransaction, int i) {
        int i2;
        boolean z = CoreRune.MW_SHELL_CHANGE_TRANSITION;
        Transitions transitions = this.mTransitions;
        if (z) {
            ChangeTransitionProvider changeTransitionProvider = transitions.mChangeTransitProvider;
            changeTransitionProvider.getClass();
            Log.d("ChangeTransitionProvider", "startChangeTransition: handler=null, wct=" + windowContainerTransaction + ", Caller=" + Debug.getCaller());
            changeTransitionProvider.mTransitions.startTransition(6, windowContainerTransaction, null);
            return;
        }
        if (i == 1) {
            i2 = EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_SUCCESS;
        } else {
            if (i != 5) {
                throw new IllegalArgumentException("Unexpected target windowing mode " + WindowConfiguration.windowingModeToString(i));
            }
            i2 = EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_FAILURE;
        }
        ((ArrayList) this.mPendingTransitionTokens).add(transitions.startTransition(i2, windowContainerTransaction, this));
    }
}
