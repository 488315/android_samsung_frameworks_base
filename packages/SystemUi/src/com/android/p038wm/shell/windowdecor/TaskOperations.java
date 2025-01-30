package com.android.p038wm.shell.windowdecor;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Rect;
import android.hardware.input.InputManager;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import com.android.p038wm.shell.common.SyncTransactionQueue;
import com.android.p038wm.shell.freeform.FreeformTaskTransitionHandler;
import com.android.p038wm.shell.freeform.FreeformTaskTransitionStarter;
import com.android.p038wm.shell.splitscreen.SplitScreenController;
import com.android.p038wm.shell.transition.Transitions;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Optional;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TaskOperations {
    public final Context mContext;
    public final Optional mSplitScreenController;
    public final SyncTransactionQueue mSyncQueue;
    public final FreeformTaskTransitionStarter mTransitionStarter;

    public TaskOperations(FreeformTaskTransitionStarter freeformTaskTransitionStarter, Context context, SyncTransactionQueue syncTransactionQueue, Optional<SplitScreenController> optional) {
        this.mTransitionStarter = freeformTaskTransitionStarter;
        this.mContext = context;
        this.mSyncQueue = syncTransactionQueue;
        this.mSplitScreenController = optional;
    }

    public final void closeTask(WindowContainerToken windowContainerToken) {
        if (CoreRune.MW_CAPTION_SHELL) {
            Optional optional = this.mSplitScreenController;
            if (optional.isPresent() && ((SplitScreenController) optional.get()).isTaskInSplitScreen(windowContainerToken)) {
                ((SplitScreenController) optional.get()).dismissSplitTask(windowContainerToken);
                return;
            }
        }
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        windowContainerTransaction.removeTask(windowContainerToken);
        if (!Transitions.ENABLE_SHELL_TRANSITIONS) {
            this.mSyncQueue.queue(windowContainerTransaction);
            return;
        }
        FreeformTaskTransitionHandler freeformTaskTransitionHandler = (FreeformTaskTransitionHandler) this.mTransitionStarter;
        ((ArrayList) freeformTaskTransitionHandler.mPendingTransitionTokens).add(freeformTaskTransitionHandler.mTransitions.startTransition(2, windowContainerTransaction, freeformTaskTransitionHandler));
    }

    public final void maximizeTask(ActivityManager.RunningTaskInfo runningTaskInfo) {
        if (CoreRune.MW_CAPTION_SHELL) {
            Optional optional = this.mSplitScreenController;
            if (optional.isPresent() && ((SplitScreenController) optional.get()).isTaskInSplitScreen(runningTaskInfo.token)) {
                ((SplitScreenController) optional.get()).maximizeSplitTask(runningTaskInfo.token);
                return;
            }
        }
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        int i = runningTaskInfo.getWindowingMode() != 1 ? 1 : 5;
        windowContainerTransaction.setWindowingMode(runningTaskInfo.token, i == runningTaskInfo.configuration.windowConfiguration.getDisplayWindowingMode() ? 0 : i);
        if (i == 1) {
            windowContainerTransaction.setBounds(runningTaskInfo.token, (Rect) null);
        }
        if (Transitions.ENABLE_SHELL_TRANSITIONS) {
            ((FreeformTaskTransitionHandler) this.mTransitionStarter).startWindowingModeTransition(windowContainerTransaction, i);
        } else {
            this.mSyncQueue.queue(windowContainerTransaction);
        }
    }

    public final void minimizeTask(WindowContainerToken windowContainerToken) {
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        windowContainerTransaction.reorder(windowContainerToken, false);
        if (!Transitions.ENABLE_SHELL_TRANSITIONS) {
            this.mSyncQueue.queue(windowContainerTransaction);
            return;
        }
        FreeformTaskTransitionHandler freeformTaskTransitionHandler = (FreeformTaskTransitionHandler) this.mTransitionStarter;
        ((ArrayList) freeformTaskTransitionHandler.mPendingTransitionTokens).add(freeformTaskTransitionHandler.mTransitions.startTransition(4, windowContainerTransaction, freeformTaskTransitionHandler));
    }

    public final void moveToFreeform(WindowContainerToken windowContainerToken) {
        Optional optional = this.mSplitScreenController;
        if (optional.isPresent() && ((SplitScreenController) optional.get()).isTaskInSplitScreen(windowContainerToken)) {
            ((SplitScreenController) optional.get()).moveSplitToFreeform(windowContainerToken, null, false);
            return;
        }
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        windowContainerTransaction.setWindowingMode(windowContainerToken, 5);
        ((FreeformTaskTransitionHandler) this.mTransitionStarter).startWindowingModeTransition(windowContainerTransaction, 5);
    }

    public final void sendBackEvent(int i, int i2) {
        long uptimeMillis = SystemClock.uptimeMillis();
        KeyEvent keyEvent = new KeyEvent(uptimeMillis, uptimeMillis, i, 4, 0, 0, -1, 0, 72, 257);
        if (CoreRune.MW_CAPTION_SHELL_DEX) {
            keyEvent.setDisplayId(i2);
        }
        if (((InputManager) this.mContext.getSystemService(InputManager.class)).injectInputEvent(keyEvent, 0)) {
            return;
        }
        Log.e("TaskOperations", "Inject input event fail");
    }
}
