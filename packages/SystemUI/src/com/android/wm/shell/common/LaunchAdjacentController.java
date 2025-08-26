package com.android.wm.shell.common;

import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import com.android.internal.protolog.ProtoLog;
import com.android.wm.shell.protolog.ShellProtoLogGroup;

/* loaded from: classes3.dex */
public final class LaunchAdjacentController {
    public WindowContainerToken container;
    public boolean launchAdjacentEnabled = true;
    public final SyncTransactionQueue syncQueue;

    public LaunchAdjacentController(SyncTransactionQueue syncTransactionQueue) {
        this.syncQueue = syncTransactionQueue;
    }

    public final void setLaunchAdjacentEnabled(boolean z) {
        if (this.launchAdjacentEnabled != z) {
            ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_TASK_ORG;
            ProtoLog.d(shellProtoLogGroup, "set launch adjacent flag root enabled=%b", new Object[]{Boolean.valueOf(z)});
            this.launchAdjacentEnabled = z;
            WindowContainerToken windowContainerToken = this.container;
            if (windowContainerToken != null) {
                SyncTransactionQueue syncTransactionQueue = this.syncQueue;
                if (z) {
                    ProtoLog.v(shellProtoLogGroup, "enable launch adjacent flag root container", new Object[0]);
                    WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                    windowContainerTransaction.setLaunchAdjacentFlagRoot(windowContainerToken);
                    syncTransactionQueue.queue(windowContainerTransaction);
                    return;
                }
                ProtoLog.v(shellProtoLogGroup, "disable launch adjacent flag root container", new Object[0]);
                WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
                windowContainerTransaction2.clearLaunchAdjacentFlagRoot(windowContainerToken);
                syncTransactionQueue.queue(windowContainerTransaction2);
            }
        }
    }
}
