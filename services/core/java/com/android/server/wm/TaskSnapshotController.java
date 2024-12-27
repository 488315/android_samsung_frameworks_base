package com.android.server.wm;

import android.R;
import android.app.ActivityManager;
import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.os.Handler;
import android.os.Message;
import android.util.ArraySet;
import android.util.IntArray;
import android.window.TaskSnapshot;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.policy.PhoneWindowManager;
import com.android.server.wm.TaskSnapshotPersister.RemoveObsoleteFilesQueueItem;

import com.samsung.android.rune.CoreRune;

import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class TaskSnapshotController extends AbsAppSnapshotController {
    public final Handler mHandler;
    public final BaseAppSnapshotPersister$PersistInfoProvider mPersistInfoProvider;
    public final TaskSnapshotPersister mPersister;
    public final IntArray mSkipClosingAppSnapshotTasks;
    public final ArraySet mTmpTasks;

    public TaskSnapshotController(
            WindowManagerService windowManagerService, SnapshotPersistQueue snapshotPersistQueue) {
        super(windowManagerService);
        boolean z;
        float f;
        this.mSkipClosingAppSnapshotTasks = new IntArray();
        this.mTmpTasks = new ArraySet();
        this.mHandler = new Handler();
        ActivitySnapshotController$$ExternalSyntheticLambda1
                activitySnapshotController$$ExternalSyntheticLambda1 =
                        new ActivitySnapshotController$$ExternalSyntheticLambda1();
        float f2 =
                windowManagerService
                        .mContext
                        .getResources()
                        .getFloat(R.dimen.config_preferredHyphenationFrequency);
        float f3 =
                CoreRune.FW_LOW_TASK_SNAPSHOT_SCALE_FOR_TABLET
                        ? 0.7f
                        : windowManagerService
                                .mContext
                                .getResources()
                                .getFloat(R.dimen.config_scrollFactor);
        if (f3 < FullScreenMagnificationGestureHandler.MAX_SCALE || 1.0f <= f3) {
            throw new RuntimeException("Low-res scale must be between 0 and 1");
        }
        if (f2 <= FullScreenMagnificationGestureHandler.MAX_SCALE || 1.0f < f2) {
            throw new RuntimeException("High-res scale must be between 0 and 1");
        }
        if (f2 <= f3) {
            throw new RuntimeException("High-res scale must be greater than low-res scale");
        }
        if (f3 > FullScreenMagnificationGestureHandler.MAX_SCALE) {
            f = f3 / f2;
            z = true;
        } else {
            z = false;
            f = 0.0f;
        }
        BaseAppSnapshotPersister$PersistInfoProvider baseAppSnapshotPersister$PersistInfoProvider =
                new BaseAppSnapshotPersister$PersistInfoProvider(
                        activitySnapshotController$$ExternalSyntheticLambda1,
                        "snapshots",
                        z,
                        f,
                        windowManagerService
                                .mContext
                                .getResources()
                                .getBoolean(
                                        R.bool
                                                .config_use_strict_phone_number_comparation_for_kazakhstan));
        this.mPersistInfoProvider = baseAppSnapshotPersister$PersistInfoProvider;
        this.mPersister =
                new TaskSnapshotPersister(
                        snapshotPersistQueue, baseAppSnapshotPersister$PersistInfoProvider);
        this.mCache =
                new TaskSnapshotCache(
                        new AppSnapshotLoader(baseAppSnapshotPersister$PersistInfoProvider));
        this.mSnapshotEnabled =
                !windowManagerService
                        .mContext
                        .getResources()
                        .getBoolean(R.bool.config_displayBlanksAfterDoze);
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

    @Override // com.android.server.wm.AbsAppSnapshotController
    public final ActivityRecord findAppTokenForSnapshot(WindowContainer windowContainer) {
        return ((Task) windowContainer)
                .getActivity(new TaskSnapshotController$$ExternalSyntheticLambda1());
    }

    @Override // com.android.server.wm.AbsAppSnapshotController
    public final Rect getLetterboxInsets(ActivityRecord activityRecord) {
        return activityRecord.getLetterboxInsets();
    }

    public final TaskSnapshot getSnapshot(int i, int i2, boolean z, boolean z2, boolean z3) {
        BaseAppSnapshotPersister$PersistInfoProvider baseAppSnapshotPersister$PersistInfoProvider =
                this.mPersistInfoProvider;
        if (z2 && z3) {
            return ((TaskSnapshotCache) this.mCache)
                    .getSnapshot(
                            i,
                            i2,
                            false,
                            z2
                                    && baseAppSnapshotPersister$PersistInfoProvider
                                            .mEnableLowResSnapshots,
                            z3);
        }
        return ((TaskSnapshotCache) this.mCache)
                .getSnapshot(
                        i,
                        i2,
                        z,
                        z2 && baseAppSnapshotPersister$PersistInfoProvider.mEnableLowResSnapshots,
                        false);
    }

    @Override // com.android.server.wm.AbsAppSnapshotController
    public final ActivityManager.TaskDescription getTaskDescription(
            WindowContainer windowContainer) {
        return ((Task) windowContainer).mTaskDescription;
    }

    @Override // com.android.server.wm.AbsAppSnapshotController
    public final ActivityRecord getTopActivity(WindowContainer windowContainer) {
        return ((Task) windowContainer).getTopMostActivity();
    }

    public final boolean isInSkipClosingAppSnapshotTasks(Task task) {
        return this.mSkipClosingAppSnapshotTasks.indexOf(task.mTaskId) != -1;
    }

    public final TaskSnapshot recordSnapshot(Task task) {
        TaskSnapshot captureSnapshot;
        TaskSnapshot taskSnapshot = null;
        if (!shouldDisableSnapshots() && (captureSnapshot = captureSnapshot(task)) != null) {
            this.mCache.putSnapshot(task, captureSnapshot);
            taskSnapshot = captureSnapshot;
        }
        if (taskSnapshot != null && !task.isActivityTypeHome()) {
            this.mPersister.persistSnapshot(task.mTaskId, task.mUserId, taskSnapshot);
            task.mLastTaskSnapshotData.set(taskSnapshot);
            TaskChangeNotificationController taskChangeNotificationController =
                    task.mAtmService.mTaskChangeNotificationController;
            int i = task.mTaskId;
            taskChangeNotificationController.getClass();
            taskSnapshot.addReference(1);
            Message obtainMessage =
                    taskChangeNotificationController.mHandler.obtainMessage(15, i, 0, taskSnapshot);
            taskChangeNotificationController.forAllLocalListeners(
                    taskChangeNotificationController.mNotifyTaskSnapshotChanged, obtainMessage);
            obtainMessage.sendToTarget();
        }
        return taskSnapshot;
    }

    public final void removeAndDeleteSnapshot(int i, int i2) {
        TaskSnapshot snapshot =
                ((TaskSnapshotCache) this.mCache).getSnapshot(i, i2, false, false, false);
        HardwareBuffer hardwareBuffer = snapshot != null ? snapshot.getHardwareBuffer() : null;
        ((TaskSnapshotCache) this.mCache).removeRunningEntry(Integer.valueOf(i));
        this.mPersister.removeSnapshot(i, i2);
        if (hardwareBuffer != null) {
            TaskSnapshotPersister taskSnapshotPersister = this.mPersister;
            synchronized (taskSnapshotPersister.mLock) {
                synchronized (taskSnapshotPersister.mLock) {
                    SnapshotPersistQueue snapshotPersistQueue =
                            taskSnapshotPersister.mSnapshotPersistQueue;
                    BaseAppSnapshotPersister$PersistInfoProvider
                            baseAppSnapshotPersister$PersistInfoProvider =
                                    taskSnapshotPersister.mPersistInfoProvider;
                    snapshotPersistQueue.getClass();
                    SnapshotPersistQueue.CloseBufferWriteQueueItem closeBufferWriteQueueItem =
                            new SnapshotPersistQueue.CloseBufferWriteQueueItem(
                                    baseAppSnapshotPersister$PersistInfoProvider, i2);
                    closeBufferWriteQueueItem.mHardwareBuffer = hardwareBuffer;
                    snapshotPersistQueue.addToQueueInternal(closeBufferWriteQueueItem, false);
                }
            }
        }
    }

    public final void removeObsoleteTaskFiles(ArraySet arraySet, int[] iArr) {
        TaskSnapshotPersister taskSnapshotPersister = this.mPersister;
        taskSnapshotPersister.getClass();
        if (iArr.length == 0) {
            return;
        }
        synchronized (taskSnapshotPersister.mLock) {
            taskSnapshotPersister.mPersistedTaskIdsSinceLastRemoveObsolete.clear();
            taskSnapshotPersister.mSnapshotPersistQueue.addToQueueInternal(
                    taskSnapshotPersister
                    .new RemoveObsoleteFilesQueueItem(
                            arraySet, iArr, taskSnapshotPersister.mPersistInfoProvider),
                    false);
        }
    }

    public final void removeSkipClosingAppSnapshotTasks(Task task) {
        int indexOf = this.mSkipClosingAppSnapshotTasks.indexOf(task.mTaskId);
        if (indexOf != -1) {
            this.mSkipClosingAppSnapshotTasks.remove(indexOf);
        }
    }

    public final void snapshotForSleeping(int i) {
        DisplayContent displayContent;
        final boolean z;
        if (shouldDisableSnapshots()) {
            return;
        }
        WindowManagerService windowManagerService = this.mService;
        if (windowManagerService.mDisplayEnabled
                && (displayContent = windowManagerService.mRoot.getDisplayContent(i)) != null) {
            if (i == 0) {
                if (((PhoneWindowManager) windowManagerService.mPolicy)
                        .isKeyguardSecure(windowManagerService.mCurrentUserId)) {
                    z = true;
                    this.mTmpTasks.clear();
                    displayContent.forAllLeafTasks(
                            new Consumer() { // from class:
                                             // com.android.server.wm.TaskSnapshotController$$ExternalSyntheticLambda2
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    TaskSnapshotController taskSnapshotController =
                                            TaskSnapshotController.this;
                                    boolean z2 = z;
                                    Task task = (Task) obj;
                                    taskSnapshotController.getClass();
                                    if ((z2 || !task.isActivityTypeHome())
                                            && task.isVisible()
                                            && !task.isAnimatingByRecents()) {
                                        taskSnapshotController.mTmpTasks.add(task);
                                    }
                                }
                            },
                            true);
                    snapshotTasks(this.mTmpTasks);
                }
            }
            z = false;
            this.mTmpTasks.clear();
            displayContent.forAllLeafTasks(
                    new Consumer() { // from class:
                                     // com.android.server.wm.TaskSnapshotController$$ExternalSyntheticLambda2
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            TaskSnapshotController taskSnapshotController =
                                    TaskSnapshotController.this;
                            boolean z2 = z;
                            Task task = (Task) obj;
                            taskSnapshotController.getClass();
                            if ((z2 || !task.isActivityTypeHome())
                                    && task.isVisible()
                                    && !task.isAnimatingByRecents()) {
                                taskSnapshotController.mTmpTasks.add(task);
                            }
                        }
                    },
                    true);
            snapshotTasks(this.mTmpTasks);
        }
    }

    public final void snapshotTasks(ArraySet arraySet) {
        for (int size = arraySet.size() - 1; size >= 0; size--) {
            recordSnapshot((Task) arraySet.valueAt(size));
        }
    }

    public final void takeSnapshotByForce(Task task) {
        if (shouldDisableSnapshots() || !task.isVisible()) {
            return;
        }
        this.mTmpTasks.clear();
        this.mTmpTasks.add(task);
        snapshotTasks(this.mTmpTasks);
    }

    @Override // com.android.server.wm.AbsAppSnapshotController
    public final boolean use16BitFormat() {
        return this.mPersistInfoProvider.mUse16BitFormat;
    }
}
