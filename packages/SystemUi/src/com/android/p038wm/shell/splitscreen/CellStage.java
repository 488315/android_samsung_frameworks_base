package com.android.p038wm.shell.splitscreen;

import android.content.Context;
import android.view.SurfaceSession;
import com.android.launcher3.icons.IconProvider;
import com.android.p038wm.shell.ShellTaskOrganizer;
import com.android.p038wm.shell.common.SyncTransactionQueue;
import com.android.p038wm.shell.splitscreen.StageTaskListener;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CellStage extends StageTaskListener {
    public StageTaskListener mHost;
    public boolean mIsActive;
    public boolean mToSplit;

    public CellStage(Context context, ShellTaskOrganizer shellTaskOrganizer, int i, StageTaskListener.StageListenerCallbacks stageListenerCallbacks, SyncTransactionQueue syncTransactionQueue, SurfaceSession surfaceSession, IconProvider iconProvider) {
        super(context, shellTaskOrganizer, i, stageListenerCallbacks, syncTransactionQueue, surfaceSession, iconProvider, 4);
        this.mIsActive = false;
        this.mToSplit = false;
    }
}
