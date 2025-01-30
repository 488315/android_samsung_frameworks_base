package com.android.server.wm;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.GraphicBuffer;
import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;
import android.view.SurfaceControl;
import android.window.ScreenCapture;
import com.android.internal.policy.TransitionAnimation;
import com.samsung.android.multiwindow.SurfaceFreezerSnapshot;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class NaturalSwitchingController implements IController {
    public final ActivityTaskManagerService mAtm;
    public IBinder mClient;
    public DisplayArea mDragTargetArea;
    public Task mNaturalSwitchingPipTask;
    public WindowManagerService mWm;
    public final DeathRecipient mDeathRecipient = new DeathRecipient();
    public boolean mNaturalSwitchingRunning = false;
    public final Rect mTmpRect = new Rect();

    @Override // com.android.server.wm.IController
    public void initialize() {
    }

    public NaturalSwitchingController(ActivityTaskManagerService activityTaskManagerService) {
        this.mAtm = activityTaskManagerService;
    }

    @Override // com.android.server.wm.IController
    public void setWindowManager(WindowManagerService windowManagerService) {
        this.mWm = windowManagerService;
    }

    public boolean startNaturalSwitching(IBinder iBinder, IBinder iBinder2) {
        if (isRunning()) {
            Slog.e("NaturalSwitchingController", "startNaturalSwitching: failed, already running!");
            return false;
        }
        try {
            iBinder.linkToDeath(this.mDeathRecipient, 0);
            this.mClient = iBinder;
            Slog.d("NaturalSwitchingController", "startNaturalSwitching: " + this.mClient);
            this.mNaturalSwitchingRunning = true;
            if (CoreRune.MW_NATURAL_SWITCHING_PIP && iBinder2 != null) {
                assignRelativeLayerForPipTask(iBinder2);
            }
            updateWallpaper();
            return true;
        } catch (RemoteException e) {
            Slog.e("NaturalSwitchingController", "startNaturalSwitching: failed, cannot link to death, " + e);
            return false;
        }
    }

    public void finishNaturalSwitching() {
        if (!isRunning()) {
            Slog.e("NaturalSwitchingController", "finishNaturalSwitching: failed, it's not running!");
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
            reassignPipTaskLayerIfNeeded();
        }
        updateWallpaper();
    }

    public final void assignRelativeLayerForPipTask(IBinder iBinder) {
        WindowState windowState = (WindowState) this.mWm.mWindowMap.get(iBinder);
        if (windowState == null || windowState.getDisplayArea() == null) {
            Slog.w("NaturalSwitchingController", "assignRelativeLayerForPipTask: failed, cannot find win, " + windowState);
            return;
        }
        SurfaceControl parentSurfaceControl = windowState.getParentSurfaceControl();
        if (parentSurfaceControl == null || !parentSurfaceControl.isValid()) {
            Slog.w("NaturalSwitchingController", "assignRelativeLayerForPipTask: failed, invalid parent, " + parentSurfaceControl);
            return;
        }
        Task rootPinnedTask = this.mWm.mRoot.getDefaultTaskDisplayArea().getRootPinnedTask();
        if (rootPinnedTask == null || !rootPinnedTask.isVisible()) {
            Slog.w("NaturalSwitchingController", "assignRelativeLayerForPipTask: failed, wrong pip, " + rootPinnedTask);
            return;
        }
        Slog.d("NaturalSwitchingController", "assignRelativeLayerForPipTask: tid #" + rootPinnedTask.mTaskId);
        this.mNaturalSwitchingPipTask = rootPinnedTask;
        this.mDragTargetArea = windowState.getDisplayArea();
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        rootPinnedTask.assignRelativeLayer(transaction, parentSurfaceControl, Integer.MAX_VALUE);
        transaction.apply();
    }

    public final void reassignPipTaskLayerIfNeeded() {
        if (this.mNaturalSwitchingPipTask != null) {
            Slog.d("NaturalSwitchingController", "reassignPipTaskLayerIfNeeded: tid #" + this.mNaturalSwitchingPipTask.mTaskId);
            this.mNaturalSwitchingPipTask = null;
            this.mWm.mRoot.getDefaultDisplay().assignWindowLayers(false);
        }
    }

    public boolean isNaturalSwitchingPipTask(Task task) {
        return this.mNaturalSwitchingPipTask == task;
    }

    public DisplayArea getDragTargetArea() {
        return this.mDragTargetArea;
    }

    public final void updateWallpaper() {
        this.mWm.mRoot.getDefaultDisplay().mWallpaperMayChange = true;
        this.mWm.requestTraversal();
    }

    public boolean isRunning() {
        return this.mNaturalSwitchingRunning;
    }

    public SurfaceFreezerSnapshot getSurfaceFreezerSnapshot(int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                Task findTaskForFreezerSnapshotLocked = findTaskForFreezerSnapshotLocked(i);
                if (findTaskForFreezerSnapshotLocked == null) {
                    Slog.w("NaturalSwitchingController", "getSurfaceFreezerSnapshot: failed, cannot find tid=" + i);
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return null;
                }
                DisplayContent displayContent = findTaskForFreezerSnapshotLocked.getDisplayContent();
                if (displayContent != null && findTaskForFreezerSnapshotLocked.getDisplayArea() != null && findTaskForFreezerSnapshotLocked.getParentSurfaceControl() != null) {
                    findTaskForFreezerSnapshotLocked.getBounds(this.mTmpRect);
                    ScreenCapture.ScreenshotHardwareBuffer createSnapshotBuffer = SurfaceFreezer.createSnapshotBuffer(findTaskForFreezerSnapshotLocked.getSurfaceControl(), this.mTmpRect);
                    WindowState topVisibleAppMainWindow = findTaskForFreezerSnapshotLocked.getTopVisibleAppMainWindow();
                    Bitmap screenshotWallpaperLocked = (topVisibleAppMainWindow == null || !topVisibleAppMainWindow.hasWallpaper()) ? null : displayContent.mWallpaperController.screenshotWallpaperLocked(this.mTmpRect);
                    WindowManagerService.resetPriorityAfterLockedSection();
                    HardwareBuffer hardwareBuffer = createSnapshotBuffer != null ? createSnapshotBuffer.getHardwareBuffer() : null;
                    if (hardwareBuffer == null || hardwareBuffer.getWidth() <= 1 || hardwareBuffer.getHeight() <= 1) {
                        Slog.w("NaturalSwitchingController", "getSurfaceFreezerSnapshot: failed to get buffer, tid=" + i);
                        return null;
                    }
                    GraphicBuffer createFromHardwareBuffer = GraphicBuffer.createFromHardwareBuffer(hardwareBuffer);
                    boolean containsSecureLayers = createSnapshotBuffer.containsSecureLayers();
                    boolean hasProtectedContent = TransitionAnimation.hasProtectedContent(hardwareBuffer);
                    Bitmap wrapHardwareBuffer = Bitmap.wrapHardwareBuffer(HardwareBuffer.createFromGraphicBuffer(createFromHardwareBuffer), null);
                    if (wrapHardwareBuffer == null) {
                        Slog.w("NaturalSwitchingController", "getSurfaceFreezerSnapshot: failed to create bitmap, tid=" + i);
                        return null;
                    }
                    return new SurfaceFreezerSnapshot(wrapHardwareBuffer, i, containsSecureLayers, hasProtectedContent, screenshotWallpaperLocked);
                }
                Slog.w("NaturalSwitchingController", "getSurfaceFreezerSnapshot: failed, not attached tid=" + i);
                WindowManagerService.resetPriorityAfterLockedSection();
                return null;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final Task findTaskForFreezerSnapshotLocked(int i) {
        Task anyTaskForId = this.mAtm.mRootWindowContainer.anyTaskForId(i, 0);
        if (anyTaskForId == null) {
            Slog.w("NaturalSwitchingController", "findTaskForFreezerSnapshotLocked: failed, cannot find tid=" + i);
            return null;
        }
        Task topLeafTask = anyTaskForId.getTopLeafTask();
        return topLeafTask == null || !topLeafTask.isAnimating() ? anyTaskForId : topLeafTask;
    }

    public boolean preventNaturalSwitching(int i) {
        Task topMostTask;
        ActivityRecord activityRecord;
        ActivityInfo activityInfo;
        Bundle bundle;
        ActivityRecord activityRecord2;
        ActivityInfo activityInfo2;
        Bundle bundle2;
        TaskDisplayArea defaultTaskDisplayArea = this.mAtm.mRootWindowContainer.getDefaultTaskDisplayArea();
        Task anyTaskForId = this.mAtm.mRootWindowContainer.anyTaskForId(i, 0);
        if (this.mWm.isKeyguardLocked()) {
            return true;
        }
        if (defaultTaskDisplayArea.isSplitScreenModeActivated()) {
            Task topMostTask2 = defaultTaskDisplayArea.getRootMainStageTask().getTopMostTask();
            if (topMostTask2 != null && (activityRecord2 = topMostTask2.topRunningActivityLocked()) != null && (activityInfo2 = activityRecord2.info) != null && (bundle2 = activityInfo2.metaData) != null && bundle2.getBoolean("com.samsung.android.multiwindow.ignore.trim.task")) {
                return true;
            }
            if (defaultTaskDisplayArea.getRootSideStageTask() != null && (topMostTask = defaultTaskDisplayArea.getRootSideStageTask().getTopMostTask()) != null && (activityRecord = topMostTask.topRunningActivityLocked()) != null && (activityInfo = activityRecord.info) != null && (bundle = activityInfo.metaData) != null && bundle.getBoolean("com.samsung.android.multiwindow.ignore.trim.task")) {
                return true;
            }
        } else if (anyTaskForId != null && anyTaskForId.getWindowingMode() == 5) {
            ArrayList visibleTasks = defaultTaskDisplayArea.getVisibleTasks();
            for (int size = visibleTasks.size() - 1; size >= 0; size--) {
                if (((Task) visibleTasks.get(size)).getWindowingMode() == 1) {
                    return !r0.supportsMultiWindow();
                }
            }
        }
        return false;
    }

    @Override // com.android.server.wm.IController
    public void dumpLocked(PrintWriter printWriter, String str) {
        printWriter.println("[NaturalSwitchingController]");
        if (isRunning()) {
            printWriter.println(str + "isRunning=true");
        }
        if (CoreRune.MW_NATURAL_SWITCHING_PIP) {
            if (this.mNaturalSwitchingPipTask != null) {
                printWriter.println(str + "mNaturalSwitchingPipTask=" + this.mNaturalSwitchingPipTask);
            }
            if (this.mDragTargetArea != null) {
                printWriter.println(str + "mDragTargetArea=" + this.mDragTargetArea);
            }
        }
        printWriter.println();
    }

    public final class DeathRecipient implements IBinder.DeathRecipient {
        public DeathRecipient() {
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            WindowManagerGlobalLock windowManagerGlobalLock = NaturalSwitchingController.this.mAtm.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    Slog.w("NaturalSwitchingController", "binderDied: " + NaturalSwitchingController.this.mClient);
                    NaturalSwitchingController.this.finishNaturalSwitching();
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }
    }
}
