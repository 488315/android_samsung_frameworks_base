package com.android.wm.shell.startingsurface;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.display.DisplayManager;
import android.util.SparseArray;
import android.view.IWindow;
import android.view.SurfaceControl;
import android.view.WindowManager;
import android.view.WindowlessWindowManager;
import android.window.InputTransferToken;
import android.window.StartingWindowRemovalInfo;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.launcher3.icons.IconProvider;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.TransactionPool;

/* loaded from: classes3.dex */
public class StartingSurfaceDrawer {
    public final SnapshotWindowCreator mSnapshotWindowCreator;
    public final ShellExecutor mSplashScreenExecutor;
    final SplashscreenContentDrawer mSplashscreenContentDrawer;
    final SplashscreenWindowCreator mSplashscreenWindowCreator;
    final StartingWindowRecordManager mWindowRecords;
    final StartingWindowRecordManager mWindowlessRecords;
    public final WindowlessSnapshotWindowCreator mWindowlessSnapshotWindowCreator;
    public final WindowlessSplashWindowCreator mWindowlessSplashWindowCreator;

    public abstract class SnapshotRecord extends StartingWindowRecord {
        public final int mActivityType;
        public final StartingWindowRecordManager mRecordManager;
        public final ShellExecutor mRemoveExecutor;
        public final StartingSurfaceDrawer$SnapshotRecord$$ExternalSyntheticLambda0 mScheduledRunnable = new Runnable() { // from class: com.android.wm.shell.startingsurface.StartingSurfaceDrawer$SnapshotRecord$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.removeImmediately();
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
            int i;
            if (z || this.mActivityType == 2 || (i = startingWindowRemovalInfo.deferRemoveMode) == 3) {
                removeImmediately();
                return true;
            }
            HandlerExecutor handlerExecutor = (HandlerExecutor) this.mRemoveExecutor;
            StartingSurfaceDrawer$SnapshotRecord$$ExternalSyntheticLambda0 startingSurfaceDrawer$SnapshotRecord$$ExternalSyntheticLambda0 = this.mScheduledRunnable;
            handlerExecutor.removeCallbacks(startingSurfaceDrawer$SnapshotRecord$$ExternalSyntheticLambda0);
            long j = i != 1 ? i != 2 ? 100L : 3000L : 600L;
            handlerExecutor.executeDelayed(startingSurfaceDrawer$SnapshotRecord$$ExternalSyntheticLambda0, j);
            if (!ProtoLogImpl_1771455215.Cache.WM_SHELL_STARTING_WINDOW_enabled[1]) {
                return false;
            }
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, 7226291696181264257L, 1, Long.valueOf(j));
            return false;
        }

        public void removeImmediately() {
            ((HandlerExecutor) this.mRemoveExecutor).removeCallbacks(this.mScheduledRunnable);
            StartingWindowRecordManager startingWindowRecordManager = this.mRecordManager;
            SparseArray sparseArray = startingWindowRecordManager.mStartingWindowRecords;
            int i = this.mTaskId;
            if (((StartingWindowRecord) sparseArray.get(i)) == this) {
                startingWindowRecordManager.mStartingWindowRecords.remove(i);
            }
        }
    }

    public abstract class StartingWindowRecord {
        public int mBGColor;

        public abstract boolean removeIfPossible(StartingWindowRemovalInfo startingWindowRemovalInfo, boolean z);
    }

    public class StartingWindowRecordManager {
        public final StartingWindowRemovalInfo mTmpRemovalInfo = new StartingWindowRemovalInfo();
        public final SparseArray mStartingWindowRecords = new SparseArray();

        public final void addRecord(int i, StartingWindowRecord startingWindowRecord) {
            StartingWindowRecord startingWindowRecord2 = (StartingWindowRecord) this.mStartingWindowRecords.get(i);
            if (startingWindowRecord2 != null) {
                StartingWindowRemovalInfo startingWindowRemovalInfo = this.mTmpRemovalInfo;
                startingWindowRemovalInfo.taskId = i;
                startingWindowRecord2.removeIfPossible(startingWindowRemovalInfo, true);
            }
            this.mStartingWindowRecords.put(i, startingWindowRecord);
        }

        public final void clearAllWindows() {
            int size = this.mStartingWindowRecords.size();
            int[] iArr = new int[size];
            int i = size - 1;
            for (int i2 = i; i2 >= 0; i2--) {
                iArr[i2] = this.mStartingWindowRecords.keyAt(i2);
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
            StartingWindowRecord startingWindowRecord = (StartingWindowRecord) this.mStartingWindowRecords.get(i);
            if (startingWindowRecord == null || !startingWindowRecord.removeIfPossible(startingWindowRemovalInfo, z)) {
                return;
            }
            this.mStartingWindowRecords.remove(i);
        }
    }

    public class WindowlessStartingWindow extends WindowlessWindowManager {
        public SurfaceControl mChildSurface;

        public WindowlessStartingWindow(Configuration configuration, SurfaceControl surfaceControl) {
            super(configuration, surfaceControl, (InputTransferToken) null);
        }

        public final SurfaceControl getParentSurface(IWindow iWindow, WindowManager.LayoutParams layoutParams) {
            this.mChildSurface = new SurfaceControl.Builder().setContainerLayer().setName("Windowless window").setHidden(false).setParent(((WindowlessWindowManager) this).mRootSurface).setCallsite("WindowlessStartingWindow#attachToParentSurface").build();
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
