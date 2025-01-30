package com.android.p038wm.shell.startingsurface;

import android.os.RemoteException;
import com.android.p038wm.shell.common.ShellExecutor;
import com.android.p038wm.shell.protolog.ShellProtoLogCache;
import com.android.p038wm.shell.protolog.ShellProtoLogGroup;
import com.android.p038wm.shell.protolog.ShellProtoLogImpl;
import com.android.p038wm.shell.startingsurface.StartingSurfaceDrawer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SnapshotWindowCreator {
    public final ShellExecutor mMainExecutor;
    public final StartingSurfaceDrawer.StartingWindowRecordManager mStartingWindowRecordManager;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SnapshotWindowRecord extends StartingSurfaceDrawer.SnapshotRecord {
        public final TaskSnapshotWindow mTaskSnapshotWindow;

        public SnapshotWindowRecord(TaskSnapshotWindow taskSnapshotWindow, int i, ShellExecutor shellExecutor, int i2, StartingSurfaceDrawer.StartingWindowRecordManager startingWindowRecordManager) {
            super(i, shellExecutor, i2, startingWindowRecordManager);
            this.mTaskSnapshotWindow = taskSnapshotWindow;
            this.mBGColor = taskSnapshotWindow.mBackgroundPaint.getColor();
        }

        @Override // com.android.wm.shell.startingsurface.StartingSurfaceDrawer.SnapshotRecord
        public final boolean hasImeSurface() {
            return this.mTaskSnapshotWindow.mHasImeSurface;
        }

        @Override // com.android.wm.shell.startingsurface.StartingSurfaceDrawer.SnapshotRecord
        public final void removeImmediately() {
            super.removeImmediately();
            TaskSnapshotWindow taskSnapshotWindow = this.mTaskSnapshotWindow;
            taskSnapshotWindow.getClass();
            try {
                if (ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled) {
                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, 1218213214, 3, null, Boolean.valueOf(taskSnapshotWindow.mHasDrawn));
                }
                taskSnapshotWindow.mSession.remove(taskSnapshotWindow.mWindow);
            } catch (RemoteException unused) {
            }
        }
    }

    public SnapshotWindowCreator(ShellExecutor shellExecutor, StartingSurfaceDrawer.StartingWindowRecordManager startingWindowRecordManager) {
        this.mMainExecutor = shellExecutor;
        this.mStartingWindowRecordManager = startingWindowRecordManager;
    }
}
