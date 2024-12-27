package com.android.server.wm;

import android.R;
import android.app.ActivityManager;
import android.graphics.Rect;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.os.SystemProperties;
import android.os.Trace;
import android.util.ArraySet;
import android.util.IntArray;
import android.util.Slog;
import android.util.SparseArray;
import android.window.TaskSnapshot;

import com.android.internal.util.ToBooleanFunction;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.window.flags.Flags;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ActivitySnapshotController extends AbsAppSnapshotController {
    public final ArraySet mOnBackPressedActivities;
    final ArraySet mPendingDeleteActivity;
    final ArraySet mPendingLoadActivity;
    final ArraySet mPendingRemoveActivity;
    public final BaseAppSnapshotPersister$PersistInfoProvider mPersistInfoProvider;
    public final TaskSnapshotPersister mPersister;
    public final ArrayList mSavedFilesInOrder;
    public final AppSnapshotLoader mSnapshotLoader;
    public final SnapshotPersistQueue mSnapshotPersistQueue;
    public final ArrayList mTmpBelowActivities;
    public final ArrayList mTmpTransitionParticipants;
    public final SparseArray mUserSavedFiles;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.wm.ActivitySnapshotController$1, reason: invalid class name */
    public final class AnonymousClass1 extends SnapshotPersistQueue.WriteQueueItem {
        @Override // com.android.server.wm.SnapshotPersistQueue.WriteQueueItem
        public final void write() {
            File[] listFiles;
            Trace.traceBegin(32L, "cleanUpUserFiles");
            File directory = this.mPersistInfoProvider.getDirectory(this.mUserId);
            if (directory.exists() && (listFiles = directory.listFiles()) != null) {
                for (int length = listFiles.length - 1; length >= 0; length--) {
                    listFiles[length].delete();
                }
            }
            Trace.traceEnd(32L);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LoadActivitySnapshotItem extends SnapshotPersistQueue.WriteQueueItem {
        public final ActivityRecord[] mActivities;
        public final int mCode;

        public LoadActivitySnapshotItem(
                ActivityRecord[] activityRecordArr,
                int i,
                int i2,
                BaseAppSnapshotPersister$PersistInfoProvider
                        baseAppSnapshotPersister$PersistInfoProvider) {
            super(baseAppSnapshotPersister$PersistInfoProvider, i2);
            this.mActivities = activityRecordArr;
            this.mCode = i;
        }

        public final boolean equals(Object obj) {
            if (obj == null || LoadActivitySnapshotItem.class != obj.getClass()) {
                return false;
            }
            LoadActivitySnapshotItem loadActivitySnapshotItem = (LoadActivitySnapshotItem) obj;
            return this.mCode == loadActivitySnapshotItem.mCode
                    && this.mUserId == loadActivitySnapshotItem.mUserId
                    && this.mPersistInfoProvider == loadActivitySnapshotItem.mPersistInfoProvider;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("LoadActivitySnapshotItem{code=");
            sb.append(this.mCode);
            sb.append(", UserId=");
            return AmFmBandRange$$ExternalSyntheticOutline0.m(this.mUserId, sb, "}");
        }

        @Override // com.android.server.wm.SnapshotPersistQueue.WriteQueueItem
        public final void write() {
            try {
                Trace.traceBegin(32L, "load_activity_snapshot");
                TaskSnapshot loadTask =
                        ActivitySnapshotController.this.mSnapshotLoader.loadTask(
                                this.mCode, this.mUserId, false);
                if (loadTask == null) {
                    return;
                }
                synchronized (ActivitySnapshotController.this.mService.mGlobalLock) {
                    if (ActivitySnapshotController.this.hasRecord(this.mActivities[0])) {
                        for (ActivityRecord activityRecord : this.mActivities) {
                            ((ActivitySnapshotCache) ActivitySnapshotController.this.mCache)
                                    .putSnapshot(loadTask, activityRecord);
                        }
                    }
                }
            } finally {
                Trace.traceEnd(32L);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UserSavedFile {
        public final IntArray mActivityIds = new IntArray();
        public final int mFileId;
        public final int mUserId;

        public UserSavedFile(int i, int i2) {
            this.mFileId = i;
            this.mUserId = i2;
        }

        public final String toString() {
            StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(128, "UserSavedFile{");
            m.append(Integer.toHexString(System.identityHashCode(this)));
            m.append(" fileId=");
            BatteryService$$ExternalSyntheticOutline0.m(this.mFileId, m, ", activityIds=[");
            for (int size = this.mActivityIds.size() - 1; size >= 0; size--) {
                m.append(Integer.toHexString(this.mActivityIds.get(size)));
                if (size > 0) {
                    m.append(',');
                }
            }
            m.append("]}");
            return m.toString();
        }
    }

    public ActivitySnapshotController(
            WindowManagerService windowManagerService, SnapshotPersistQueue snapshotPersistQueue) {
        super(windowManagerService);
        this.mPendingRemoveActivity = new ArraySet();
        this.mPendingDeleteActivity = new ArraySet();
        this.mPendingLoadActivity = new ArraySet();
        this.mOnBackPressedActivities = new ArraySet();
        this.mTmpBelowActivities = new ArrayList();
        this.mTmpTransitionParticipants = new ArrayList();
        this.mUserSavedFiles = new SparseArray();
        this.mSavedFilesInOrder = new ArrayList();
        this.mSnapshotPersistQueue = snapshotPersistQueue;
        BaseAppSnapshotPersister$PersistInfoProvider baseAppSnapshotPersister$PersistInfoProvider =
                new BaseAppSnapshotPersister$PersistInfoProvider(
                        new ActivitySnapshotController$$ExternalSyntheticLambda1(),
                        "activity_snapshots",
                        false,
                        FullScreenMagnificationGestureHandler.MAX_SCALE,
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
        this.mSnapshotLoader = new AppSnapshotLoader(baseAppSnapshotPersister$PersistInfoProvider);
        this.mCache = new ActivitySnapshotCache("Activity");
        boolean z = false;
        if (!windowManagerService
                        .mContext
                        .getResources()
                        .getBoolean(R.bool.config_displayBlanksAfterDoze)
                && ((SystemProperties.getInt("persist.wm.debug.activity_screenshot", 0) != 0
                                || Flags.activitySnapshotByDefault())
                        && !ActivityManager.isLowRamDeviceStatic())) {
            z = true;
        }
        this.mSnapshotEnabled = z;
    }

    public static int getSystemHashCode(ActivityRecord activityRecord) {
        return System.identityHashCode(activityRecord);
    }

    public static boolean isInParticipant(ActivityRecord activityRecord, ArrayList arrayList) {
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            WindowContainer windowContainer = (WindowContainer) arrayList.get(size);
            if (activityRecord == windowContainer
                    || activityRecord.isDescendantOf(windowContainer)) {
                return true;
            }
        }
        return false;
    }

    public final void addBelowActivityIfExist(
            ActivityRecord activityRecord, ArraySet arraySet, boolean z) {
        getActivityBelow(activityRecord, z, this.mTmpBelowActivities);
        for (int size = this.mTmpBelowActivities.size() - 1; size >= 0; size--) {
            arraySet.add((ActivityRecord) this.mTmpBelowActivities.get(size));
        }
        this.mTmpBelowActivities.clear();
    }

    public void addUserSavedFile(int i, TaskSnapshot taskSnapshot, int[] iArr) {
        int i2 = 0;
        UserSavedFile userSavedFile = (UserSavedFile) getUserFiles(i).get(iArr[0]);
        if (userSavedFile != null) {
            Slog.w(
                    "WindowManager",
                    "Duplicate request for recording activity snapshot " + userSavedFile);
            return;
        }
        for (int length = iArr.length - 1; length >= 0; length--) {
            i2 ^= iArr[length];
        }
        UserSavedFile userSavedFile2 = new UserSavedFile(i2, i);
        SparseArray userFiles = getUserFiles(i);
        for (int length2 = iArr.length - 1; length2 >= 0; length2--) {
            userFiles.put(iArr[length2], userSavedFile2);
        }
        userSavedFile2.mActivityIds.addAll(iArr);
        this.mSavedFilesInOrder.add(userSavedFile2);
        TaskSnapshotPersister taskSnapshotPersister = this.mPersister;
        taskSnapshotPersister.persistSnapshot(i2, i, taskSnapshot);
        if (this.mSavedFilesInOrder.size() > 40) {
            int size = this.mSavedFilesInOrder.size();
            if (size - 20 < 1) {
                return;
            }
            ArrayList arrayList = new ArrayList();
            for (int i3 = size - 21; i3 >= 0; i3--) {
                UserSavedFile userSavedFile3 = (UserSavedFile) this.mSavedFilesInOrder.remove(i3);
                SparseArray sparseArray =
                        (SparseArray) this.mUserSavedFiles.get(userSavedFile3.mUserId);
                for (int size2 = userSavedFile3.mActivityIds.size() - 1; size2 >= 0; size2--) {
                    ((ActivitySnapshotCache) this.mCache)
                            .removeRunningEntry(
                                    Integer.valueOf(userSavedFile3.mActivityIds.get(size2)));
                    sparseArray.remove(userSavedFile3.mActivityIds.get(size2));
                }
                arrayList.add(userSavedFile3);
            }
            for (int size3 = arrayList.size() - 1; size3 >= 0; size3--) {
                UserSavedFile userSavedFile4 = (UserSavedFile) arrayList.get(size3);
                taskSnapshotPersister.removeSnapshot(
                        userSavedFile4.mFileId, userSavedFile4.mUserId);
            }
        }
    }

    @Override // com.android.server.wm.AbsAppSnapshotController
    public final void dump(PrintWriter printWriter) {
        super.dump(printWriter);
        for (int size = this.mUserSavedFiles.size() - 1; size >= 0; size--) {
            SparseArray sparseArray = (SparseArray) this.mUserSavedFiles.valueAt(size);
            printWriter.println("   UserSavedFile userId=" + this.mUserSavedFiles.keyAt(size));
            ArraySet arraySet = new ArraySet();
            for (int size2 = sparseArray.size() + (-1); size2 >= 0; size2--) {
                arraySet.add((UserSavedFile) sparseArray.valueAt(size2));
            }
            for (int size3 = arraySet.size() - 1; size3 >= 0; size3 += -1) {
                printWriter.println("     SavedFile=" + arraySet.valueAt(size3));
            }
        }
    }

    @Override // com.android.server.wm.AbsAppSnapshotController
    public final ActivityRecord findAppTokenForSnapshot(WindowContainer windowContainer) {
        ActivityRecord activityRecord = (ActivityRecord) windowContainer;
        if (activityRecord == null) {
            return null;
        }
        if (!((!activityRecord.mLastSurfaceShowing
                        || activityRecord.findMainWindow(true) == null
                        || activityRecord.mPopOverState.mIsActivated)
                ? false
                : activityRecord.forAllWindows(
                        (ToBooleanFunction) new ActivityRecord$$ExternalSyntheticLambda18(0),
                        true))) {
            activityRecord = null;
        }
        return activityRecord;
    }

    public final UserSavedFile findSavedFile(ActivityRecord activityRecord) {
        return (UserSavedFile)
                getUserFiles(activityRecord.mUserId).get(getSystemHashCode(activityRecord));
    }

    public final void getActivityBelow(
            ActivityRecord activityRecord, boolean z, ArrayList arrayList) {
        ActivityRecord activityBelow;
        Task task = activityRecord.task;
        if (task == null || (activityBelow = task.getActivityBelow(activityRecord)) == null) {
            return;
        }
        TaskFragment taskFragment = activityRecord.getTaskFragment();
        TaskFragment taskFragment2 = activityBelow.getTaskFragment();
        TaskFragment taskFragment3 =
                taskFragment2 != null ? taskFragment2.mAdjacentTaskFragment : null;
        if ((taskFragment == taskFragment2 && taskFragment != null) || taskFragment3 == null) {
            if (!z || isInParticipant(activityBelow, this.mTmpTransitionParticipants)) {
                arrayList.add(activityBelow);
                return;
            }
            return;
        }
        if (taskFragment3 == taskFragment) {
            getActivityBelow(activityBelow, z, arrayList);
            return;
        }
        Task task2 = taskFragment3.getTask();
        if (task2 == task) {
            if (task2.mChildren.indexOf(taskFragment3)
                    > (taskFragment != null
                            ? task.mChildren.indexOf(taskFragment)
                            : task.mChildren.indexOf(activityRecord))) {
                return;
            }
        }
        if (!z || isInParticipant(activityBelow, this.mTmpTransitionParticipants)) {
            arrayList.add(activityBelow);
        }
        ActivityRecord topMostActivity = taskFragment3.getTopMostActivity();
        if (topMostActivity != null) {
            if (!z || isInParticipant(topMostActivity, this.mTmpTransitionParticipants)) {
                arrayList.add(topMostActivity);
            }
        }
    }

    @Override // com.android.server.wm.AbsAppSnapshotController
    public final Rect getLetterboxInsets(ActivityRecord activityRecord) {
        return Letterbox.EMPTY_RECT;
    }

    public final TaskSnapshot getSnapshot(ActivityRecord[] activityRecordArr) {
        UserSavedFile findSavedFile;
        if (activityRecordArr.length == 0
                || (findSavedFile = findSavedFile(activityRecordArr[0])) == null
                || findSavedFile.mActivityIds.size() != activityRecordArr.length) {
            return null;
        }
        int i = 0;
        for (int length = activityRecordArr.length - 1; length >= 0; length--) {
            i ^= getSystemHashCode(activityRecordArr[length]);
        }
        if (findSavedFile.mFileId == i) {
            return ((ActivitySnapshotCache) this.mCache)
                    .getSnapshot(Integer.valueOf(findSavedFile.mActivityIds.get(0)));
        }
        return null;
    }

    @Override // com.android.server.wm.AbsAppSnapshotController
    public final ActivityManager.TaskDescription getTaskDescription(
            WindowContainer windowContainer) {
        return ((ActivityRecord) windowContainer).taskDescription;
    }

    @Override // com.android.server.wm.AbsAppSnapshotController
    public final ActivityRecord getTopActivity(WindowContainer windowContainer) {
        return (ActivityRecord) windowContainer;
    }

    public final SparseArray getUserFiles(int i) {
        if (this.mUserSavedFiles.get(i) == null) {
            this.mUserSavedFiles.put(i, new SparseArray());
            synchronized (this.mSnapshotPersistQueue.mLock) {
                this.mSnapshotPersistQueue.addToQueueInternal(
                        new AnonymousClass1(this.mPersistInfoProvider, i), false);
            }
        }
        return (SparseArray) this.mUserSavedFiles.get(i);
    }

    public final void handleActivityTransition(ActivityRecord activityRecord) {
        if (shouldDisableSnapshots()) {
            return;
        }
        if (!activityRecord.isVisibleRequested()) {
            addBelowActivityIfExist(activityRecord, this.mPendingRemoveActivity, true);
        } else {
            this.mPendingDeleteActivity.add(activityRecord);
            addBelowActivityIfExist(activityRecord, this.mPendingLoadActivity, false);
        }
    }

    public void handleTransitionFinish(ArrayList arrayList) {
        ActivityRecord topMostActivity;
        this.mTmpTransitionParticipants.clear();
        this.mTmpTransitionParticipants.addAll(arrayList);
        for (int size = this.mTmpTransitionParticipants.size() - 1; size >= 0; size--) {
            WindowContainer windowContainer =
                    (WindowContainer) this.mTmpTransitionParticipants.get(size);
            if (windowContainer.asTask() != null) {
                Task asTask = windowContainer.asTask();
                if (!shouldDisableSnapshots()
                        && (topMostActivity = asTask.getTopMostActivity()) != null) {
                    if (asTask.isVisibleRequested()) {
                        addBelowActivityIfExist(topMostActivity, this.mPendingLoadActivity, true);
                        asTask.forAllActivities(
                                new Consumer() { // from class:
                                                 // com.android.server.wm.ActivitySnapshotController$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        ActivitySnapshotController activitySnapshotController =
                                                ActivitySnapshotController.this;
                                        ActivitySnapshotController.UserSavedFile findSavedFile =
                                                activitySnapshotController.findSavedFile(
                                                        (ActivityRecord) obj);
                                        if (findSavedFile != null) {
                                            activitySnapshotController.mSavedFilesInOrder.remove(
                                                    findSavedFile);
                                            activitySnapshotController.mSavedFilesInOrder.add(
                                                    findSavedFile);
                                        }
                                    }
                                },
                                false);
                    } else {
                        addBelowActivityIfExist(topMostActivity, this.mPendingRemoveActivity, true);
                    }
                }
            } else if (windowContainer.asTaskFragment() != null) {
                ActivityRecord topMostActivity2 =
                        windowContainer.asTaskFragment().getTopMostActivity();
                if (topMostActivity2 != null) {
                    handleActivityTransition(topMostActivity2);
                }
            } else if (windowContainer.asActivityRecord() != null) {
                handleActivityTransition(windowContainer.asActivityRecord());
            }
        }
    }

    public boolean hasRecord(ActivityRecord activityRecord) {
        return findSavedFile(activityRecord) != null;
    }

    @Override // com.android.server.wm.AbsAppSnapshotController
    public final float initSnapshotScale() {
        return Math.max(
                Math.min(
                        this.mService
                                .mContext
                                .getResources()
                                .getFloat(R.dimen.control_padding_material),
                        1.0f),
                0.1f);
    }

    public void loadSnapshotInner(ActivityRecord[] activityRecordArr, UserSavedFile userSavedFile) {
        synchronized (this.mSnapshotPersistQueue.mLock) {
            this.mSnapshotPersistQueue.addToQueueInternal(
                    new LoadActivitySnapshotItem(
                            activityRecordArr,
                            userSavedFile.mFileId,
                            userSavedFile.mUserId,
                            this.mPersistInfoProvider),
                    true);
        }
    }

    public final void postProcess() {
        if (!this.mPendingLoadActivity.isEmpty()) {
            ArraySet arraySet = new ArraySet();
            for (int size = this.mPendingLoadActivity.size() - 1; size >= 0; size--) {
                UserSavedFile findSavedFile =
                        findSavedFile((ActivityRecord) this.mPendingLoadActivity.valueAt(size));
                if (findSavedFile != null) {
                    arraySet.add(findSavedFile);
                }
            }
            for (int size2 = arraySet.size() - 1; size2 >= 0; size2--) {
                UserSavedFile userSavedFile = (UserSavedFile) arraySet.valueAt(size2);
                ArraySet arraySet2 = this.mPendingLoadActivity;
                userSavedFile.getClass();
                ActivityRecord[] activityRecordArr = null;
                ArrayList arrayList = null;
                for (int size3 = arraySet2.size() - 1; size3 >= 0; size3--) {
                    ActivityRecord activityRecord = (ActivityRecord) arraySet2.valueAt(size3);
                    if (userSavedFile.mActivityIds.contains(getSystemHashCode(activityRecord))) {
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                        }
                        arrayList.add(activityRecord);
                    }
                }
                if (arrayList != null && arrayList.size() == userSavedFile.mActivityIds.size()) {
                    activityRecordArr = (ActivityRecord[]) arrayList.toArray(new ActivityRecord[0]);
                }
                if (activityRecordArr != null && getSnapshot(activityRecordArr) == null) {
                    loadSnapshotInner(activityRecordArr, userSavedFile);
                }
            }
        }
        for (int size4 = this.mPendingRemoveActivity.size() - 1; size4 >= 0; size4--) {
            UserSavedFile findSavedFile2 =
                    findSavedFile((ActivityRecord) this.mPendingRemoveActivity.valueAt(size4));
            if (findSavedFile2 != null) {
                for (int size5 = findSavedFile2.mActivityIds.size() - 1; size5 >= 0; size5--) {
                    ((ActivitySnapshotCache) this.mCache)
                            .removeRunningEntry(
                                    Integer.valueOf(findSavedFile2.mActivityIds.get(size5)));
                }
            }
        }
        for (int size6 = this.mPendingDeleteActivity.size() - 1; size6 >= 0; size6--) {
            removeIfUserSavedFileExist((ActivityRecord) this.mPendingDeleteActivity.valueAt(size6));
        }
        resetTmpFields();
    }

    public final void removeIfUserSavedFileExist(ActivityRecord activityRecord) {
        UserSavedFile findSavedFile = findSavedFile(activityRecord);
        if (findSavedFile != null) {
            SparseArray userFiles = getUserFiles(activityRecord.mUserId);
            for (int size = findSavedFile.mActivityIds.size() - 1; size >= 0; size--) {
                int i = findSavedFile.mActivityIds.get(size);
                int indexOf = findSavedFile.mActivityIds.indexOf(i);
                if (indexOf >= 0) {
                    findSavedFile.mActivityIds.remove(indexOf);
                }
                ((ActivitySnapshotCache) this.mCache).removeRunningEntry(Integer.valueOf(i));
                userFiles.remove(i);
            }
            this.mSavedFilesInOrder.remove(findSavedFile);
            this.mPersister.removeSnapshot(findSavedFile.mFileId, activityRecord.mUserId);
        }
    }

    public void resetTmpFields() {
        this.mPendingRemoveActivity.clear();
        this.mPendingDeleteActivity.clear();
        this.mPendingLoadActivity.clear();
    }

    @Override // com.android.server.wm.AbsAppSnapshotController
    public final boolean use16BitFormat() {
        return this.mPersistInfoProvider.mUse16BitFormat;
    }
}
