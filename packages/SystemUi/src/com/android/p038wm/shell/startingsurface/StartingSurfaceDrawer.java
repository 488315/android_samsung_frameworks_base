package com.android.p038wm.shell.startingsurface;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.display.DisplayManager;
import android.os.IBinder;
import android.util.SparseArray;
import android.view.IWindow;
import android.view.SurfaceControl;
import android.view.SurfaceSession;
import android.view.WindowManager;
import android.view.WindowlessWindowManager;
import android.window.StartingWindowRemovalInfo;
import com.android.launcher3.icons.IconProvider;
import com.android.p038wm.shell.common.HandlerExecutor;
import com.android.p038wm.shell.common.ShellExecutor;
import com.android.p038wm.shell.common.TransactionPool;
import com.android.p038wm.shell.protolog.ShellProtoLogCache;
import com.android.p038wm.shell.protolog.ShellProtoLogGroup;
import com.android.p038wm.shell.protolog.ShellProtoLogImpl;
import com.android.p038wm.shell.startingsurface.StartingSurfaceDrawer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class StartingSurfaceDrawer {
    public final SnapshotWindowCreator mSnapshotWindowCreator;
    public final ShellExecutor mSplashScreenExecutor;
    final SplashscreenContentDrawer mSplashscreenContentDrawer;
    final SplashscreenWindowCreator mSplashscreenWindowCreator;
    final StartingWindowRecordManager mWindowRecords;
    final StartingWindowRecordManager mWindowlessRecords;
    public final WindowlessSnapshotWindowCreator mWindowlessSnapshotWindowCreator;
    public final WindowlessSplashWindowCreator mWindowlessSplashWindowCreator;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class SnapshotRecord extends StartingWindowRecord {
        public final int mActivityType;
        public final StartingWindowRecordManager mRecordManager;
        public final ShellExecutor mRemoveExecutor;
        public final StartingSurfaceDrawer$SnapshotRecord$$ExternalSyntheticLambda0 mScheduledRunnable = new Runnable() { // from class: com.android.wm.shell.startingsurface.StartingSurfaceDrawer$SnapshotRecord$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                StartingSurfaceDrawer.SnapshotRecord.this.removeImmediately();
            }
        };
        public final int mTaskId;

        /* JADX WARN: Type inference failed for: r0v0, types: [com.android.wm.shell.startingsurface.StartingSurfaceDrawer$SnapshotRecord$$ExternalSyntheticLambda0] */
        public SnapshotRecord(int i, ShellExecutor shellExecutor, int i2, StartingWindowRecordManager startingWindowRecordManager) {
            this.mActivityType = i;
            this.mRemoveExecutor = shellExecutor;
            this.mTaskId = i2;
            this.mRecordManager = startingWindowRecordManager;
        }

        public abstract boolean hasImeSurface();

        @Override // com.android.wm.shell.startingsurface.StartingSurfaceDrawer.StartingWindowRecord
        public final boolean removeIfPossible(StartingWindowRemovalInfo startingWindowRemovalInfo, boolean z) {
            if (z) {
                removeImmediately();
                return true;
            }
            int i = startingWindowRemovalInfo.deferRemoveForImeMode;
            if (this.mActivityType == 2) {
                removeImmediately();
                return false;
            }
            HandlerExecutor handlerExecutor = (HandlerExecutor) this.mRemoveExecutor;
            StartingSurfaceDrawer$SnapshotRecord$$ExternalSyntheticLambda0 startingSurfaceDrawer$SnapshotRecord$$ExternalSyntheticLambda0 = this.mScheduledRunnable;
            handlerExecutor.removeCallbacks(startingSurfaceDrawer$SnapshotRecord$$ExternalSyntheticLambda0);
            long j = i != 1 ? i != 2 ? 100L : 3000L : 600L;
            handlerExecutor.executeDelayed(j, startingSurfaceDrawer$SnapshotRecord$$ExternalSyntheticLambda0);
            if (!ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled) {
                return false;
            }
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, 508166491, 1, null, Long.valueOf(j));
            return false;
        }

        public void removeImmediately() {
            ((HandlerExecutor) this.mRemoveExecutor).removeCallbacks(this.mScheduledRunnable);
            this.mRecordManager.mStartingWindowRecords.remove(this.mTaskId);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class StartingWindowRecord {
        public int mBGColor;

        public abstract boolean removeIfPossible(StartingWindowRemovalInfo startingWindowRemovalInfo, boolean z);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class StartingWindowRecordManager {
        public final StartingWindowRemovalInfo mTmpRemovalInfo = new StartingWindowRemovalInfo();
        public final SparseArray mStartingWindowRecords = new SparseArray();

        public final void clearAllWindows() {
            SparseArray sparseArray = this.mStartingWindowRecords;
            int size = sparseArray.size();
            int[] iArr = new int[size];
            int i = size - 1;
            for (int i2 = i; i2 >= 0; i2--) {
                iArr[i2] = sparseArray.keyAt(i2);
            }
            while (i >= 0) {
                int i3 = iArr[i];
                StartingWindowRemovalInfo startingWindowRemovalInfo = this.mTmpRemovalInfo;
                startingWindowRemovalInfo.taskId = i3;
                removeWindow(startingWindowRemovalInfo, true);
                i--;
            }
        }

        public int recordSize() {
            return this.mStartingWindowRecords.size();
        }

        public final void removeWindow(StartingWindowRemovalInfo startingWindowRemovalInfo, boolean z) {
            int i = startingWindowRemovalInfo.taskId;
            SparseArray sparseArray = this.mStartingWindowRecords;
            StartingWindowRecord startingWindowRecord = (StartingWindowRecord) sparseArray.get(i);
            if (startingWindowRecord == null || !startingWindowRecord.removeIfPossible(startingWindowRemovalInfo, z)) {
                return;
            }
            sparseArray.remove(i);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class WindowlessStartingWindow extends WindowlessWindowManager {
        public SurfaceControl mChildSurface;

        public WindowlessStartingWindow(Configuration configuration, SurfaceControl surfaceControl) {
            super(configuration, surfaceControl, (IBinder) null);
        }

        public final SurfaceControl getParentSurface(IWindow iWindow, WindowManager.LayoutParams layoutParams) {
            this.mChildSurface = new SurfaceControl.Builder(new SurfaceSession()).setContainerLayer().setName("Windowless window").setHidden(false).setParent(((WindowlessWindowManager) this).mRootSurface).setCallsite("WindowlessStartingWindow#attachToParentSurface").build();
            SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
            try {
                transaction.setLayer(this.mChildSurface, Integer.MAX_VALUE);
                transaction.apply();
                transaction.close();
                return this.mChildSurface;
            } catch (Throwable th) {
                try {
                    transaction.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
    }

    public StartingSurfaceDrawer(Context context, ShellExecutor shellExecutor, IconProvider iconProvider, TransactionPool transactionPool) {
        StartingWindowRecordManager startingWindowRecordManager = new StartingWindowRecordManager();
        this.mWindowRecords = startingWindowRecordManager;
        StartingWindowRecordManager startingWindowRecordManager2 = new StartingWindowRecordManager();
        this.mWindowlessRecords = startingWindowRecordManager2;
        this.mSplashScreenExecutor = shellExecutor;
        DisplayManager displayManager = (DisplayManager) context.getSystemService(DisplayManager.class);
        SplashscreenContentDrawer splashscreenContentDrawer = new SplashscreenContentDrawer(context, iconProvider, transactionPool, shellExecutor);
        this.mSplashscreenContentDrawer = splashscreenContentDrawer;
        displayManager.getDisplay(0);
        this.mSplashscreenWindowCreator = new SplashscreenWindowCreator(splashscreenContentDrawer, context, shellExecutor, displayManager, startingWindowRecordManager);
        this.mSnapshotWindowCreator = new SnapshotWindowCreator(shellExecutor, startingWindowRecordManager);
        this.mWindowlessSplashWindowCreator = new WindowlessSplashWindowCreator(splashscreenContentDrawer, context, shellExecutor, displayManager, startingWindowRecordManager2, transactionPool);
        this.mWindowlessSnapshotWindowCreator = new WindowlessSnapshotWindowCreator(startingWindowRecordManager2, context, displayManager, splashscreenContentDrawer, transactionPool);
    }
}
