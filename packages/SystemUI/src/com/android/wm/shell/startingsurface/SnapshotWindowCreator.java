package com.android.wm.shell.startingsurface;

import android.os.RemoteException;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.startingsurface.StartingSurfaceDrawer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SnapshotWindowCreator {
    public final ShellExecutor mMainExecutor;
    public final StartingSurfaceDrawer.StartingWindowRecordManager mStartingWindowRecordManager;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SnapshotWindowRecord extends StartingSurfaceDrawer.SnapshotRecord {
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
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_STARTING_WINDOW_enabled[1]) {
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, -2148041522326587115L, 3, Boolean.valueOf(taskSnapshotWindow.mHasDrawn));
                }
                taskSnapshotWindow.mSession.remove(taskSnapshotWindow.mWindow.asBinder());
            } catch (RemoteException unused) {
            }
        }
    }

    public SnapshotWindowCreator(ShellExecutor shellExecutor, StartingSurfaceDrawer.StartingWindowRecordManager startingWindowRecordManager) {
        this.mMainExecutor = shellExecutor;
        this.mStartingWindowRecordManager = startingWindowRecordManager;
    }
}
