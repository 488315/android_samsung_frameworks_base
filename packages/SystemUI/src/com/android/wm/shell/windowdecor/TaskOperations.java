package com.android.wm.shell.windowdecor;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Rect;
import android.hardware.input.InputManager;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.freeform.FreeformTaskTransitionStarter;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.Iterator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class TaskOperations {
    public final Context mContext;
    public SplitScreenController mSplitScreenController;
    public final SyncTransactionQueue mSyncQueue;
    public final FreeformTaskTransitionStarter mTransitionStarter;

    public TaskOperations(FreeformTaskTransitionStarter freeformTaskTransitionStarter, Context context, SyncTransactionQueue syncTransactionQueue) {
        this.mTransitionStarter = freeformTaskTransitionStarter;
        this.mContext = context;
        this.mSyncQueue = syncTransactionQueue;
    }

    public final IBinder closeTask(WindowContainerToken windowContainerToken, WindowContainerTransaction windowContainerTransaction, boolean z) {
        if (z) {
            windowContainerTransaction.removeTask(windowContainerToken);
        } else {
            windowContainerTransaction.closeTask(windowContainerToken);
        }
        if (Transitions.ENABLE_SHELL_TRANSITIONS) {
            return this.mTransitionStarter.startRemoveTransition(windowContainerTransaction);
        }
        this.mSyncQueue.queue(windowContainerTransaction);
        return null;
    }

    public final void maximizeTask(ActivityManager.RunningTaskInfo runningTaskInfo, int i) {
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        int i2 = runningTaskInfo.getWindowingMode() != 1 ? 1 : 5;
        windowContainerTransaction.setWindowingMode(runningTaskInfo.token, i2 == i ? 0 : i2);
        if (i2 == 1) {
            windowContainerTransaction.setBounds(runningTaskInfo.token, (Rect) null);
        }
        if (CoreRune.MW_FREEFORM_SHELL_TRANSITION) {
            windowContainerTransaction.setChangeTransitMode(runningTaskInfo.token, 1, ReorderTile$$ExternalSyntheticOutline0.m(runningTaskInfo.taskId, ")", new StringBuilder("maximize_operation(")));
        }
        if (Transitions.ENABLE_SHELL_TRANSITIONS) {
            this.mTransitionStarter.startWindowingModeTransition(windowContainerTransaction, i2);
        } else {
            this.mSyncQueue.queue(windowContainerTransaction);
        }
    }

    public final void moveFreeformToSplit(ActivityManager.RunningTaskInfo runningTaskInfo, int i) {
        boolean z;
        ActivityManager.RunningTaskInfo runningTaskInfo2;
        if (runningTaskInfo == null || this.mSplitScreenController == null) {
            return;
        }
        int i2 = runningTaskInfo.displayId;
        Iterator it = MultiWindowManager.getInstance().getVisibleTasks().iterator();
        while (true) {
            z = true;
            if (!it.hasNext()) {
                runningTaskInfo2 = null;
                break;
            }
            runningTaskInfo2 = (ActivityManager.RunningTaskInfo) it.next();
            if (runningTaskInfo2.displayId == i2 && runningTaskInfo2.getWindowingMode() == 1) {
                break;
            }
        }
        if (runningTaskInfo2 != null && !runningTaskInfo2.supportsMultiWindow) {
            Log.w("TaskOperations", "moveFreeformToSplit: failed, not support mw, top fullscreen t#" + runningTaskInfo2.taskId);
        } else {
            if (runningTaskInfo2 == null || (runningTaskInfo2.getActivityType() != 2 && runningTaskInfo2.getActivityType() != 3)) {
                z = false;
            }
            this.mSplitScreenController.onFreeformToSplitRequested(runningTaskInfo, z, i, false, null, false);
        }
    }

    public final void sendBackEvent(int i, int i2) {
        long uptimeMillis = SystemClock.uptimeMillis();
        KeyEvent keyEvent = new KeyEvent(uptimeMillis, uptimeMillis, i, 4, 0, 0, -1, 0, 72, 257);
        keyEvent.setDisplayId(i2);
        if (((InputManager) this.mContext.getSystemService(InputManager.class)).injectInputEvent(keyEvent, 0)) {
            return;
        }
        Log.e("TaskOperations", "Inject input event fail");
    }
}
