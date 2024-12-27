package com.android.server.wm;

import android.util.ArrayMap;
import android.util.Pair;
import android.window.TaskSnapshot;

import com.samsung.android.rune.CoreRune;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SnapshotController {
    public final ActivitySnapshotController mActivitySnapshotController;
    public final SnapshotPersistQueue mSnapshotPersistQueue;
    public final TaskSnapshotController mTaskSnapshotController;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ActivitiesByTask {
        public final ArrayMap mActivitiesMap = new ArrayMap();

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class OpenCloseActivities {
            public final ArrayList mOpenActivities = new ArrayList();
            public final ArrayList mCloseActivities = new ArrayList();
        }
    }

    public SnapshotController(WindowManagerService windowManagerService) {
        SnapshotPersistQueue snapshotPersistQueue = new SnapshotPersistQueue();
        this.mSnapshotPersistQueue = snapshotPersistQueue;
        this.mTaskSnapshotController =
                new TaskSnapshotController(windowManagerService, snapshotPersistQueue);
        this.mActivitySnapshotController =
                new ActivitySnapshotController(windowManagerService, snapshotPersistQueue);
    }

    public final void onTransactionReady(ArrayList arrayList, int i) {
        TaskSnapshot captureSnapshot;
        boolean z = false;
        boolean z2 = i == 1 || i == 3;
        boolean z3 = i == 2 || i == 4;
        TaskSnapshotController taskSnapshotController = this.mTaskSnapshotController;
        if (!z2 && !z3 && i < 1000) {
            if (CoreRune.MW_FREEFORM_SHELL_TRANSITION) {
                Iterator it = arrayList.iterator();
                Pair pair = null;
                while (it.hasNext()) {
                    Transition.ChangeInfo changeInfo = (Transition.ChangeInfo) it.next();
                    Task asTask = changeInfo.mContainer.asTask();
                    if (changeInfo.mWindowingMode == 5
                            && asTask != null
                            && asTask.inFullscreenWindowingMode()) {
                        z = true;
                    }
                    if (asTask != null
                            && !asTask.mCreatedByOrganizer
                            && !asTask.isVisibleRequested()
                            && asTask.getWindowingMode() == 1) {
                        pair = new Pair(asTask, changeInfo);
                    }
                }
                if (!z || pair == null) {
                    return;
                }
                Task task = (Task) pair.first;
                taskSnapshotController.mCurrentChangeInfo = (Transition.ChangeInfo) pair.second;
                try {
                    taskSnapshotController.recordSnapshot(task);
                    return;
                } finally {
                }
            }
            return;
        }
        ActivitiesByTask activitiesByTask = null;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            Transition.ChangeInfo changeInfo2 = (Transition.ChangeInfo) arrayList.get(size);
            if (changeInfo2.mWindowingMode != 2) {
                WindowContainer windowContainer = changeInfo2.mContainer;
                if (windowContainer.isActivityTypeHome()) {
                    continue;
                } else {
                    Task asTask2 = windowContainer.asTask();
                    if (asTask2 != null
                            && !asTask2.mCreatedByOrganizer
                            && !asTask2.isVisibleRequested()) {
                        if (changeInfo2.mChangeTransitMode != 2
                                || changeInfo2.mWindowingMode != 6
                                || asTask2.getWindowingMode() != 1) {
                            if (taskSnapshotController.isInSkipClosingAppSnapshotTasks(asTask2)) {
                                taskSnapshotController.removeSkipClosingAppSnapshotTasks(asTask2);
                            } else {
                                taskSnapshotController.mCurrentChangeInfo = changeInfo2;
                                try {
                                    taskSnapshotController.recordSnapshot(asTask2);
                                } finally {
                                }
                            }
                        }
                    }
                    if (!z3
                            && (windowContainer.asActivityRecord() != null
                                    || windowContainer.asTaskFragment() != null)) {
                        TaskFragment asTaskFragment = windowContainer.asTaskFragment();
                        ActivityRecord topMostActivity =
                                asTaskFragment != null
                                        ? asTaskFragment.getTopMostActivity()
                                        : windowContainer.asActivityRecord();
                        if (topMostActivity != null && topMostActivity.task.isVisibleRequested()) {
                            if (activitiesByTask == null) {
                                activitiesByTask = new ActivitiesByTask();
                            }
                            ActivitiesByTask.OpenCloseActivities openCloseActivities =
                                    (ActivitiesByTask.OpenCloseActivities)
                                            activitiesByTask.mActivitiesMap.get(
                                                    topMostActivity.task);
                            if (openCloseActivities == null) {
                                openCloseActivities = new ActivitiesByTask.OpenCloseActivities();
                                activitiesByTask.mActivitiesMap.put(
                                        topMostActivity.task, openCloseActivities);
                            }
                            if (topMostActivity.isVisibleRequested()) {
                                openCloseActivities.mOpenActivities.add(topMostActivity);
                            } else {
                                openCloseActivities.mCloseActivities.add(topMostActivity);
                            }
                        }
                    }
                }
            }
        }
        if (activitiesByTask != null) {
            for (int size2 = activitiesByTask.mActivitiesMap.size() - 1; size2 >= 0; size2--) {
                ActivitiesByTask.OpenCloseActivities openCloseActivities2 =
                        (ActivitiesByTask.OpenCloseActivities)
                                activitiesByTask.mActivitiesMap.valueAt(size2);
                if (!openCloseActivities2.mOpenActivities.isEmpty()) {
                    int size3 = openCloseActivities2.mOpenActivities.size() - 1;
                    while (true) {
                        if (size3 >= 0) {
                            if (!((ActivityRecord) openCloseActivities2.mOpenActivities.get(size3))
                                    .mOptInOnBackInvoked) {
                                break;
                            } else {
                                size3--;
                            }
                        } else if (!openCloseActivities2.mCloseActivities.isEmpty()) {
                            ArrayList arrayList2 = openCloseActivities2.mCloseActivities;
                            ActivitySnapshotController activitySnapshotController =
                                    this.mActivitySnapshotController;
                            if (!activitySnapshotController.shouldDisableSnapshots()
                                    && !arrayList2.isEmpty()) {
                                int size4 = arrayList2.size();
                                int[] iArr = new int[size4];
                                if (size4 == 1) {
                                    ActivityRecord activityRecord =
                                            (ActivityRecord) arrayList2.get(0);
                                    if (activitySnapshotController.shouldDisableSnapshots()
                                            || (captureSnapshot =
                                                            activitySnapshotController
                                                                    .captureSnapshot(
                                                                            activityRecord))
                                                    == null) {
                                        captureSnapshot = null;
                                    } else {
                                        activitySnapshotController.mCache.putSnapshot(
                                                activityRecord, captureSnapshot);
                                    }
                                    if (captureSnapshot != null) {
                                        iArr[0] =
                                                ActivitySnapshotController.getSystemHashCode(
                                                        activityRecord);
                                        activitySnapshotController.addUserSavedFile(
                                                activityRecord.mUserId, captureSnapshot, iArr);
                                    }
                                } else {
                                    Task task2 = ((ActivityRecord) arrayList2.get(0)).task;
                                    TaskSnapshot snapshot =
                                            activitySnapshotController.mService
                                                    .mTaskSnapshotController.snapshot(
                                                    task2,
                                                    activitySnapshotController
                                                            .mHighResSnapshotScale);
                                    if (snapshot != null) {
                                        for (int i2 = 0; i2 < arrayList2.size(); i2++) {
                                            ActivityRecord activityRecord2 =
                                                    (ActivityRecord) arrayList2.get(i2);
                                            ((ActivitySnapshotCache)
                                                            activitySnapshotController.mCache)
                                                    .putSnapshot(snapshot, activityRecord2);
                                            iArr[i2] =
                                                    ActivitySnapshotController.getSystemHashCode(
                                                            activityRecord2);
                                        }
                                        activitySnapshotController.addUserSavedFile(
                                                task2.mUserId, snapshot, iArr);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public final void setPause(boolean z) {
        SnapshotPersistQueue snapshotPersistQueue = this.mSnapshotPersistQueue;
        synchronized (snapshotPersistQueue.mLock) {
            try {
                snapshotPersistQueue.mPaused = z;
                if (!z) {
                    snapshotPersistQueue.mLock.notifyAll();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
