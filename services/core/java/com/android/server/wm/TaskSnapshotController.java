package com.android.server.wm;

import android.R;
import android.app.ActivityManager;
import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.os.Handler;
import android.util.ArraySet;
import android.util.IntArray;
import android.util.Slog;
import android.window.ScreenCapture;
import android.window.TaskSnapshot;
import com.android.server.display.DisplayPowerController2;
import com.android.server.wm.BaseAppSnapshotPersister;
import com.android.server.wm.SnapshotController;
import com.android.server.policy.WindowManagerPolicy;
import com.samsung.android.rune.CoreRune;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class TaskSnapshotController extends AbsAppSnapshotController {
    public final Handler mHandler;
    public final BaseAppSnapshotPersister.PersistInfoProvider mPersistInfoProvider;
    public final TaskSnapshotPersister mPersister;
    public final IntArray mSkipClosingAppSnapshotTasks;
    public final ArraySet mTmpTasks;

    public TaskSnapshotController(WindowManagerService windowManagerService, SnapshotPersistQueue snapshotPersistQueue) {
        super(windowManagerService);
        this.mSkipClosingAppSnapshotTasks = new IntArray();
        this.mTmpTasks = new ArraySet();
        this.mHandler = new Handler();
        BaseAppSnapshotPersister.PersistInfoProvider createPersistInfoProvider = createPersistInfoProvider(windowManagerService, new ActivitySnapshotController$$ExternalSyntheticLambda0());
        this.mPersistInfoProvider = createPersistInfoProvider;
        this.mPersister = new TaskSnapshotPersister(snapshotPersistQueue, createPersistInfoProvider);
        initialize(new TaskSnapshotCache(windowManagerService, new AppSnapshotLoader(createPersistInfoProvider)));
        setSnapshotEnabled(!windowManagerService.mContext.getResources().getBoolean(R.bool.config_closeDialogWhenTouchOutside));
    }

    public void systemReady() {
        if (shouldDisableSnapshots()) {
            return;
        }
        this.mService.mSnapshotController.registerTransitionStateConsumer(8, new Consumer() { // from class: com.android.server.wm.TaskSnapshotController$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                TaskSnapshotController.this.handleTaskClose((SnapshotController.TransitionState) obj);
            }
        });
    }

    public static BaseAppSnapshotPersister.PersistInfoProvider createPersistInfoProvider(WindowManagerService windowManagerService, BaseAppSnapshotPersister.DirectoryResolver directoryResolver) {
        boolean z;
        float f = windowManagerService.mContext.getResources().getFloat(R.dimen.config_closeToSquareDisplayMaxAspectRatio);
        float f2 = CoreRune.FW_LOW_TASK_SNAPSHOT_SCALE_FOR_TABLET ? 0.7f : windowManagerService.mContext.getResources().getFloat(R.dimen.config_fixedOrientationLetterboxAspectRatio);
        float f3 = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
        if (f2 < DisplayPowerController2.RATE_FROM_DOZE_TO_ON || 1.0f <= f2) {
            throw new RuntimeException("Low-res scale must be between 0 and 1");
        }
        if (f <= DisplayPowerController2.RATE_FROM_DOZE_TO_ON || 1.0f < f) {
            throw new RuntimeException("High-res scale must be between 0 and 1");
        }
        if (f <= f2) {
            throw new RuntimeException("High-res scale must be greater than low-res scale");
        }
        if (f2 > DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
            f3 = f2 / f;
            z = true;
        } else {
            z = false;
        }
        return new BaseAppSnapshotPersister.PersistInfoProvider(directoryResolver, "snapshots", z, f3, windowManagerService.mContext.getResources().getBoolean(R.bool.config_supportAudioSourceUnprocessed));
    }

    public void handleTaskClose(SnapshotController.TransitionState transitionState) {
        if (shouldDisableSnapshots()) {
            return;
        }
        this.mTmpTasks.clear();
        ArraySet participant = transitionState.getParticipant(false);
        if (this.mService.mAtmService.getTransitionController().isShellTransitionsEnabled()) {
            this.mTmpTasks.addAll(participant);
        } else {
            Iterator it = participant.iterator();
            while (it.hasNext()) {
                getClosingTasksInner((Task) it.next(), this.mTmpTasks);
            }
        }
        snapshotTasks(this.mTmpTasks);
        this.mSkipClosingAppSnapshotTasks.clear();
    }

    public void addSkipClosingAppSnapshotTasks(Set set) {
        if (shouldDisableSnapshots()) {
            return;
        }
        Iterator it = set.iterator();
        while (it.hasNext()) {
            this.mSkipClosingAppSnapshotTasks.add(((Task) it.next()).mTaskId);
        }
    }

    public void snapshotTasks(ArraySet arraySet) {
        snapshotTasks(arraySet, false);
    }

    public TaskSnapshot recordSnapshot(Task task, boolean z) {
        boolean z2 = z && task.isActivityTypeHome();
        TaskSnapshot recordSnapshotInner = recordSnapshotInner(task, z);
        if (!z2 && recordSnapshotInner != null) {
            this.mPersister.persistSnapshot(task.mTaskId, task.mUserId, recordSnapshotInner);
            task.onSnapshotChanged(recordSnapshotInner);
        }
        return recordSnapshotInner;
    }

    public final void snapshotTasks(ArraySet arraySet, boolean z) {
        for (int size = arraySet.size() - 1; size >= 0; size--) {
            recordSnapshot((Task) arraySet.valueAt(size), z);
        }
    }

    public TaskSnapshot getSnapshot(int i, int i2, boolean z, boolean z2) {
        return ((TaskSnapshotCache) this.mCache).getSnapshot(i, i2, z, z2 && this.mPersistInfoProvider.enableLowResSnapshots());
    }

    public long getSnapshotCaptureTime(int i) {
        TaskSnapshot snapshot = ((TaskSnapshotCache) this.mCache).getSnapshot(Integer.valueOf(i));
        if (snapshot != null) {
            return snapshot.getCaptureTime();
        }
        return -1L;
    }

    public void clearSnapshotCache() {
        ((TaskSnapshotCache) this.mCache).clearRunningCache();
    }

    @Override // com.android.server.wm.AbsAppSnapshotController
    public ActivityRecord findAppTokenForSnapshot(Task task) {
        return task.getActivity(new ChangeTransitionController$$ExternalSyntheticLambda1());
    }

    @Override // com.android.server.wm.AbsAppSnapshotController
    public boolean use16BitFormat() {
        return this.mPersistInfoProvider.use16BitFormat();
    }

    public final ScreenCapture.ScreenshotHardwareBuffer createImeSnapshot(Task task, int i) {
        if (task.getSurfaceControl() == null) {
            Slog.w(StartingSurfaceController.TAG, "Failed to take screenshot. No surface control for " + task);
            return null;
        }
        WindowState windowState = task.getDisplayContent().mInputMethodWindow;
        if (windowState == null || !windowState.isVisible()) {
            return null;
        }
        Rect parentFrame = windowState.getParentFrame();
        parentFrame.offsetTo(0, 0);
        return ScreenCapture.captureLayers(new ScreenCapture.LayerCaptureArgs.Builder(windowState.getSurfaceControl()).setSourceCrop(parentFrame).setFrameScale(1.0f).setPixelFormat(i).setCaptureSecureLayers(true).build());
    }

    public ScreenCapture.ScreenshotHardwareBuffer snapshotImeFromAttachedTask(Task task) {
        if (checkIfReadyToSnapshot(task) == null || hasInputMethodDialog(task)) {
            return null;
        }
        return createImeSnapshot(task, this.mPersistInfoProvider.use16BitFormat() ? 4 : 1);
    }

    public boolean hasInputMethodDialog(Task task) {
        WindowList windowList = task.getDisplayContent().getImeContainer().mChildren;
        for (int i = 0; i < windowList.size(); i++) {
            WindowToken windowToken = (WindowToken) windowList.get(i);
            if (windowToken.windowType == 2012 && windowToken.isVisible()) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.server.wm.AbsAppSnapshotController
    public ActivityRecord getTopActivity(Task task) {
        return task.getTopMostActivity();
    }

    @Override // com.android.server.wm.AbsAppSnapshotController
    public ActivityRecord getTopFullscreenActivity(Task task) {
        return task.getTopFullscreenActivity();
    }

    @Override // com.android.server.wm.AbsAppSnapshotController
    public ActivityManager.TaskDescription getTaskDescription(Task task) {
        return task.getTaskDescription();
    }

    public void getClosingTasksInner(Task task, ArraySet arraySet) {
        if (isAnimatingByRecents(task)) {
            this.mSkipClosingAppSnapshotTasks.add(task.mTaskId);
        }
        if (CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY && this.mService.mAtmService.mMultiWindowFoldController.isHoldingSplitScreen() && task.inSplitScreenWindowingMode()) {
            this.mSkipClosingAppSnapshotTasks.add(task.mTaskId);
        }
        if (task.isVisible() || this.mSkipClosingAppSnapshotTasks.indexOf(task.mTaskId) >= 0) {
            return;
        }
        arraySet.add(task);
    }

    public void notifyTaskRemovedFromRecents(int i, int i2) {
        TaskSnapshot snapshot = ((TaskSnapshotCache) this.mCache).getSnapshot(i, i2, false, false);
        HardwareBuffer hardwareBuffer = snapshot != null ? snapshot.getHardwareBuffer() : null;
        ((TaskSnapshotCache) this.mCache).onIdRemoved(Integer.valueOf(i));
        this.mPersister.onTaskRemovedFromRecents(i, i2);
        if (hardwareBuffer != null) {
            this.mPersister.onTaskRemovedFromRecentsBuffer(hardwareBuffer);
        }
    }

    public void removeSnapshotCache(int i) {
        ((TaskSnapshotCache) this.mCache).removeRunningEntry(Integer.valueOf(i));
    }

    public void removeObsoleteTaskFiles(ArraySet arraySet, int[] iArr) {
        this.mPersister.removeObsoleteFiles(arraySet, iArr);
    }

    public void screenTurningOff(final int i, final WindowManagerPolicy.ScreenOffListener screenOffListener) {
        if (shouldDisableSnapshots()) {
            screenOffListener.onScreenOff();
        } else {
            this.mHandler.post(new Runnable() { // from class: com.android.server.wm.TaskSnapshotController$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    TaskSnapshotController.this.lambda$screenTurningOff$0(i, screenOffListener);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$screenTurningOff$0(int i, WindowManagerPolicy.ScreenOffListener screenOffListener) {
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    snapshotForSleeping(i);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
            screenOffListener.onScreenOff();
        }
    }

    public void snapshotForSleeping(int i) {
        snapshotForSleeping(i, false);
    }

    public void snapshotForSleeping(int i, boolean z) {
        DisplayContent displayContent;
        boolean z2;
        if (shouldDisableSnapshots()) {
            return;
        }
        WindowManagerService windowManagerService = this.mService;
        if (windowManagerService.mDisplayEnabled && (displayContent = windowManagerService.mRoot.getDisplayContent(i)) != null) {
            this.mTmpTasks.clear();
            displayContent.forAllTasks(new Consumer() { // from class: com.android.server.wm.TaskSnapshotController$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    TaskSnapshotController.this.lambda$snapshotForSleeping$1((Task) obj);
                }
            });
            if (i == 0) {
                WindowManagerService windowManagerService2 = this.mService;
                if (windowManagerService2.mPolicy.isKeyguardSecure(windowManagerService2.mCurrentUserId)) {
                    z2 = true;
                    snapshotTasks(this.mTmpTasks, (z2 || z) ? false : true);
                }
            }
            z2 = false;
            snapshotTasks(this.mTmpTasks, (z2 || z) ? false : true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$snapshotForSleeping$1(Task task) {
        if (!task.isVisible() || isAnimatingByRecents(task)) {
            return;
        }
        this.mTmpTasks.add(task);
    }

    public void takeSnapshotByForce(Task task) {
        takeSnapshotByForce(task, false);
    }

    public void takeSnapshotByForce(Task task, boolean z) {
        if (shouldDisableSnapshots() || task == null || !task.isVisible()) {
            return;
        }
        this.mTmpTasks.clear();
        this.mTmpTasks.add(task);
        snapshotTasks(this.mTmpTasks);
        if (z) {
            addSkipClosingAppSnapshotTasks(this.mTmpTasks);
        }
    }

    public boolean isInSkipClosingAppSnapshotTasks(Task task) {
        return this.mSkipClosingAppSnapshotTasks.indexOf(task.mTaskId) != -1;
    }

    public void removeSkipClosingAppSnapshotTasks(Task task) {
        int indexOf = this.mSkipClosingAppSnapshotTasks.indexOf(task.mTaskId);
        if (indexOf != -1) {
            this.mSkipClosingAppSnapshotTasks.remove(indexOf);
        }
    }

    public void snapshotForNightMode() {
        snapshotForSleeping(0, true);
    }
}
