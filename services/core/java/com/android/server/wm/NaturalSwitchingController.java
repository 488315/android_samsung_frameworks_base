package com.android.server.wm;

import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;
import android.view.SurfaceControl;

import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;

import com.samsung.android.rune.CoreRune;

import java.io.PrintWriter;
import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class NaturalSwitchingController implements IController {
    public final ActivityTaskManagerService mAtm;
    public IBinder mClient;
    public DisplayArea mDragTargetArea;
    public Task mNaturalSwitchingPipTask;
    public WindowManagerService mWm;
    public final DeathRecipient mDeathRecipient = new DeathRecipient();
    public boolean mNaturalSwitchingRunning = false;
    public final Rect mTmpRect = new Rect();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DeathRecipient implements IBinder.DeathRecipient {
        public DeathRecipient() {}

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            WindowManagerGlobalLock windowManagerGlobalLock =
                    NaturalSwitchingController.this.mAtm.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    Slog.w(
                            "NaturalSwitchingController",
                            "binderDied: " + NaturalSwitchingController.this.mClient);
                    NaturalSwitchingController.this.finishNaturalSwitching();
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }
    }

    public NaturalSwitchingController(ActivityTaskManagerService activityTaskManagerService) {
        this.mAtm = activityTaskManagerService;
    }

    @Override // com.android.server.wm.IController
    public final void dumpLocked(PrintWriter printWriter) {
        printWriter.println("[NaturalSwitchingController]");
        if (this.mNaturalSwitchingRunning) {
            printWriter.println("  isRunning=true");
        }
        if (CoreRune.MW_NATURAL_SWITCHING_PIP) {
            if (this.mNaturalSwitchingPipTask != null) {
                printWriter.println("  mNaturalSwitchingPipTask=" + this.mNaturalSwitchingPipTask);
            }
            if (this.mDragTargetArea != null) {
                printWriter.println("  mDragTargetArea=" + this.mDragTargetArea);
            }
        }
        printWriter.println();
    }

    public final void finishNaturalSwitching() {
        if (!this.mNaturalSwitchingRunning) {
            Slog.e(
                    "NaturalSwitchingController",
                    "finishNaturalSwitching: failed, it's not running!");
            return;
        }
        Slog.d("NaturalSwitchingController", "finishNaturalSwitching: " + this.mClient);
        this.mNaturalSwitchingRunning = false;
        IBinder iBinder = this.mClient;
        if (iBinder != null) {
            iBinder.unlinkToDeath(this.mDeathRecipient, 0);
            this.mClient = null;
        }
        if (CoreRune.MW_NATURAL_SWITCHING_PIP) {
            this.mDragTargetArea = null;
            if (this.mNaturalSwitchingPipTask != null) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        new StringBuilder("reassignPipTaskLayerIfNeeded: tid #"),
                        this.mNaturalSwitchingPipTask.mTaskId,
                        "NaturalSwitchingController");
                this.mNaturalSwitchingPipTask = null;
                this.mWm.mRoot.mDefaultDisplay.assignWindowLayers(false);
            }
        }
        WindowManagerService windowManagerService = this.mWm;
        windowManagerService.mRoot.mDefaultDisplay.mWallpaperMayChange = true;
        windowManagerService.requestTraversal();
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x004f, code lost:

       com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0052, code lost:

       return null;
    */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x003d, code lost:

       android.util.Slog.w("NaturalSwitchingController", "getSurfaceFreezerSnapshot: failed, cannot find tid=" + r10);
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.samsung.android.multiwindow.SurfaceFreezerSnapshot getSurfaceFreezerSnapshot(
            int r10) {
        /*
            Method dump skipped, instructions count: 238
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.NaturalSwitchingController.getSurfaceFreezerSnapshot(int):com.samsung.android.multiwindow.SurfaceFreezerSnapshot");
    }

    @Override // com.android.server.wm.IController
    public final void initialize() {}

    public final boolean preventNaturalSwitching(int i) {
        Task topMostTask;
        ActivityRecord activityRecord;
        ActivityInfo activityInfo;
        Bundle bundle;
        ActivityRecord activityRecord2;
        ActivityInfo activityInfo2;
        Bundle bundle2;
        ActivityTaskManagerService activityTaskManagerService = this.mAtm;
        TaskDisplayArea defaultTaskDisplayArea =
                activityTaskManagerService.mRootWindowContainer.mDefaultDisplay
                        .getDefaultTaskDisplayArea();
        Task anyTaskForId =
                activityTaskManagerService.mRootWindowContainer.anyTaskForId(i, 0, null, false);
        if (this.mWm.isKeyguardLocked()) {
            return true;
        }
        if (defaultTaskDisplayArea.isSplitScreenModeActivated()) {
            Task topMostTask2 = defaultTaskDisplayArea.mRootMainStageTask.getTopMostTask();
            if (topMostTask2 != null
                    && (activityRecord2 = topMostTask2.topRunningActivityLocked()) != null
                    && (activityInfo2 = activityRecord2.info) != null
                    && (bundle2 = activityInfo2.metaData) != null
                    && bundle2.getBoolean("com.samsung.android.multiwindow.ignore.trim.task")) {
                return true;
            }
            Task task = defaultTaskDisplayArea.mRootSideStageTask;
            if (task != null
                    && (topMostTask = task.getTopMostTask()) != null
                    && (activityRecord = topMostTask.topRunningActivityLocked()) != null
                    && (activityInfo = activityRecord.info) != null
                    && (bundle = activityInfo.metaData) != null
                    && bundle.getBoolean("com.samsung.android.multiwindow.ignore.trim.task")) {
                return true;
            }
        } else if (anyTaskForId != null && anyTaskForId.getWindowingMode() == 5) {
            ArrayList arrayList = new ArrayList();
            defaultTaskDisplayArea.forAllTasks(
                    new TaskDisplayArea$$ExternalSyntheticLambda0(0, arrayList));
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                if (((Task) arrayList.get(size)).getWindowingMode() == 1) {
                    return !r1.supportsMultiWindow();
                }
            }
        }
        return false;
    }

    @Override // com.android.server.wm.IController
    public final void setWindowManager(WindowManagerService windowManagerService) {
        this.mWm = windowManagerService;
    }

    public final boolean startNaturalSwitching(IBinder iBinder, IBinder iBinder2) {
        if (this.mNaturalSwitchingRunning) {
            Slog.e("NaturalSwitchingController", "startNaturalSwitching: failed, already running!");
            return false;
        }
        try {
            iBinder.linkToDeath(this.mDeathRecipient, 0);
            this.mClient = iBinder;
            Slog.d("NaturalSwitchingController", "startNaturalSwitching: " + this.mClient);
            this.mNaturalSwitchingRunning = true;
            if (CoreRune.MW_NATURAL_SWITCHING_PIP && iBinder2 != null) {
                WindowState windowState = (WindowState) this.mWm.mWindowMap.get(iBinder2);
                if (windowState == null || windowState.getDisplayArea() == null) {
                    Slog.w(
                            "NaturalSwitchingController",
                            "assignRelativeLayerForPipTask: failed, cannot find win, "
                                    + windowState);
                } else {
                    SurfaceControl parentSurfaceControl = windowState.getParentSurfaceControl();
                    if (parentSurfaceControl == null || !parentSurfaceControl.isValid()) {
                        Slog.w(
                                "NaturalSwitchingController",
                                "assignRelativeLayerForPipTask: failed, invalid parent, "
                                        + parentSurfaceControl);
                    } else {
                        Task task =
                                this.mWm.mRoot.mDefaultDisplay.getDefaultTaskDisplayArea()
                                        .mRootPinnedTask;
                        if (task == null || !task.isVisible()) {
                            Slog.w(
                                    "NaturalSwitchingController",
                                    "assignRelativeLayerForPipTask: failed, wrong pip, " + task);
                        } else {
                            DeviceIdleController$$ExternalSyntheticOutline0.m(
                                    new StringBuilder("assignRelativeLayerForPipTask: tid #"),
                                    task.mTaskId,
                                    "NaturalSwitchingController");
                            this.mNaturalSwitchingPipTask = task;
                            this.mDragTargetArea = windowState.getDisplayArea();
                            SurfaceControl.Transaction transaction =
                                    new SurfaceControl.Transaction();
                            task.assignRelativeLayer(
                                    transaction, parentSurfaceControl, Integer.MAX_VALUE);
                            transaction.apply();
                        }
                    }
                }
            }
            WindowManagerService windowManagerService = this.mWm;
            windowManagerService.mRoot.mDefaultDisplay.mWallpaperMayChange = true;
            windowManagerService.requestTraversal();
            return true;
        } catch (RemoteException e) {
            Slog.e(
                    "NaturalSwitchingController",
                    "startNaturalSwitching: failed, cannot link to death, " + e);
            return false;
        }
    }
}
