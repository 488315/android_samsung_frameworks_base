package com.android.wm.shell.splitscreen;

import android.content.Context;
import android.view.SurfaceSession;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import com.android.launcher3.icons.IconProvider;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.splitscreen.StageTaskListener;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SideStage extends StageTaskListener {
    public SideStage(Context context, ShellTaskOrganizer shellTaskOrganizer, int i, StageTaskListener.StageListenerCallbacks stageListenerCallbacks, SyncTransactionQueue syncTransactionQueue, SurfaceSession surfaceSession, IconProvider iconProvider) {
        super(context, shellTaskOrganizer, i, stageListenerCallbacks, syncTransactionQueue, surfaceSession, iconProvider, 2);
    }

    public final boolean removeAllTasks(WindowContainerTransaction windowContainerTransaction, boolean z, boolean z2) {
        if (this.mChildrenTaskInfo.size() == 0 && (!CoreRune.MW_SPLIT_SHELL_TRANSITION || z2)) {
            return false;
        }
        if (CoreRune.MT_NEW_DEX_LAUNCH_POLICY && z) {
            adjustChildTaskWindowingModeIfNeeded(windowContainerTransaction);
        }
        windowContainerTransaction.reparentTasks(this.mRootTaskInfo.token, (WindowContainerToken) null, (int[]) null, (int[]) null, z);
        return true;
    }
}
