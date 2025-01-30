package com.android.p038wm.shell.splitscreen;

import android.app.ActivityManager;
import android.content.Context;
import android.view.SurfaceSession;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import com.android.launcher3.icons.IconProvider;
import com.android.p038wm.shell.ShellTaskOrganizer;
import com.android.p038wm.shell.common.SyncTransactionQueue;
import com.android.p038wm.shell.common.split.SplitScreenConstants;
import com.android.p038wm.shell.splitscreen.StageTaskListener;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MainStage extends StageTaskListener {
    public boolean mIsActive;

    public MainStage(Context context, ShellTaskOrganizer shellTaskOrganizer, int i, StageTaskListener.StageListenerCallbacks stageListenerCallbacks, SyncTransactionQueue syncTransactionQueue, SurfaceSession surfaceSession, IconProvider iconProvider) {
        super(context, shellTaskOrganizer, i, stageListenerCallbacks, syncTransactionQueue, surfaceSession, iconProvider, 1);
        this.mIsActive = false;
    }

    public final void activate(WindowContainerTransaction windowContainerTransaction, boolean z) {
        if (this.mIsActive) {
            return;
        }
        if (z) {
            windowContainerTransaction.reparentTasks((WindowContainerToken) null, this.mRootTaskInfo.token, SplitScreenConstants.CONTROLLED_WINDOWING_MODES, SplitScreenConstants.CONTROLLED_ACTIVITY_TYPES, true, true);
        }
        this.mIsActive = true;
    }

    public final void deactivate(WindowContainerTransaction windowContainerTransaction, boolean z) {
        if (this.mIsActive) {
            this.mIsActive = false;
            ActivityManager.RunningTaskInfo runningTaskInfo = this.mRootTaskInfo;
            if (runningTaskInfo == null) {
                return;
            }
            WindowContainerToken windowContainerToken = runningTaskInfo.token;
            if (CoreRune.MT_NEW_DEX_LAUNCH_POLICY && z) {
                adjustChildTaskWindowingModeIfNeeded(windowContainerTransaction);
            }
            windowContainerTransaction.reparentTasks(windowContainerToken, (WindowContainerToken) null, (int[]) null, (int[]) null, z);
        }
    }
}
