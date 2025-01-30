package com.android.server.wm;

import android.R;
import android.app.ActivityManager;
import android.os.SystemProperties;
import android.util.ArraySet;
import android.util.SparseArray;
import android.window.TaskSnapshot;
import com.android.server.LocalServices;
import com.android.server.display.DisplayPowerController2;
import com.android.server.pm.UserManagerInternal;
import com.android.server.wm.BaseAppSnapshotPersister;
import com.android.server.wm.SnapshotController;
import com.android.server.wm.SnapshotPersistQueue;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class ActivitySnapshotController extends AbsAppSnapshotController {
    final ArraySet mPendingCaptureActivity;
    final ArraySet mPendingDeleteActivity;
    final ArraySet mPendingLoadActivity;
    final ArraySet mPendingRemoveActivity;
    public final BaseAppSnapshotPersister.PersistInfoProvider mPersistInfoProvider;
    public final TaskSnapshotPersister mPersister;
    public final ArrayList mSavedFilesInOrder;
    public final AppSnapshotLoader mSnapshotLoader;
    public final SnapshotPersistQueue mSnapshotPersistQueue;
    public final SparseArray mUserSavedFiles;

    @Override // com.android.server.wm.AbsAppSnapshotController
    public ActivityRecord getTopActivity(ActivityRecord activityRecord) {
        return activityRecord;
    }

    public ActivitySnapshotController(WindowManagerService windowManagerService, SnapshotPersistQueue snapshotPersistQueue) {
        super(windowManagerService);
        this.mPendingCaptureActivity = new ArraySet();
        this.mPendingRemoveActivity = new ArraySet();
        this.mPendingDeleteActivity = new ArraySet();
        this.mPendingLoadActivity = new ArraySet();
        this.mUserSavedFiles = new SparseArray();
        this.mSavedFilesInOrder = new ArrayList();
        this.mSnapshotPersistQueue = snapshotPersistQueue;
        BaseAppSnapshotPersister.PersistInfoProvider createPersistInfoProvider = createPersistInfoProvider(windowManagerService, new ActivitySnapshotController$$ExternalSyntheticLambda0());
        this.mPersistInfoProvider = createPersistInfoProvider;
        this.mPersister = new TaskSnapshotPersister(snapshotPersistQueue, createPersistInfoProvider);
        this.mSnapshotLoader = new AppSnapshotLoader(createPersistInfoProvider);
        initialize(new ActivitySnapshotCache(windowManagerService));
        setSnapshotEnabled((windowManagerService.mContext.getResources().getBoolean(R.bool.config_closeDialogWhenTouchOutside) || !isSnapshotEnabled() || ActivityManager.isLowRamDeviceStatic()) ? false : true);
    }

    public void systemReady() {
        if (shouldDisableSnapshots()) {
            return;
        }
        this.mService.mSnapshotController.registerTransitionStateConsumer(1, new Consumer() { // from class: com.android.server.wm.ActivitySnapshotController$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ActivitySnapshotController.this.handleOpenActivityTransition((SnapshotController.TransitionState) obj);
            }
        });
        this.mService.mSnapshotController.registerTransitionStateConsumer(2, new Consumer() { // from class: com.android.server.wm.ActivitySnapshotController$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ActivitySnapshotController.this.handleCloseActivityTransition((SnapshotController.TransitionState) obj);
            }
        });
        this.mService.mSnapshotController.registerTransitionStateConsumer(4, new Consumer() { // from class: com.android.server.wm.ActivitySnapshotController$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ActivitySnapshotController.this.handleOpenTaskTransition((SnapshotController.TransitionState) obj);
            }
        });
        this.mService.mSnapshotController.registerTransitionStateConsumer(8, new Consumer() { // from class: com.android.server.wm.ActivitySnapshotController$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ActivitySnapshotController.this.handleCloseTaskTransition((SnapshotController.TransitionState) obj);
            }
        });
    }

    @Override // com.android.server.wm.AbsAppSnapshotController
    public float initSnapshotScale() {
        return Math.max(Math.min(this.mService.mContext.getResources().getFloat(R.dimen.config_minPercentageMultiWindowSupportWidth), 1.0f), 0.1f);
    }

    public static boolean isSnapshotEnabled() {
        return SystemProperties.getInt("persist.wm.debug.activity_screenshot", 0) != 0;
    }

    public static BaseAppSnapshotPersister.PersistInfoProvider createPersistInfoProvider(WindowManagerService windowManagerService, BaseAppSnapshotPersister.DirectoryResolver directoryResolver) {
        return new BaseAppSnapshotPersister.PersistInfoProvider(directoryResolver, "activity_snapshots", false, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, windowManagerService.mContext.getResources().getBoolean(R.bool.config_supportAudioSourceUnprocessed));
    }

    public final void cleanUpUserFiles(final int i) {
        synchronized (this.mSnapshotPersistQueue.getLock()) {
            this.mSnapshotPersistQueue.sendToQueueLocked(new SnapshotPersistQueue.WriteQueueItem(this.mPersistInfoProvider) { // from class: com.android.server.wm.ActivitySnapshotController.1
                @Override // com.android.server.wm.SnapshotPersistQueue.WriteQueueItem
                public boolean isReady() {
                    return ((UserManagerInternal) LocalServices.getService(UserManagerInternal.class)).isUserUnlocked(i);
                }

                @Override // com.android.server.wm.SnapshotPersistQueue.WriteQueueItem
                public void write() {
                    File[] listFiles;
                    File directory = this.mPersistInfoProvider.getDirectory(i);
                    if (!directory.exists() || (listFiles = directory.listFiles()) == null) {
                        return;
                    }
                    for (int length = listFiles.length - 1; length >= 0; length--) {
                        listFiles[length].delete();
                    }
                }
            });
        }
    }

    public void preTransitionStart() {
        if (shouldDisableSnapshots()) {
            return;
        }
        resetTmpFields();
    }

    public void postTransitionStart() {
        if (shouldDisableSnapshots()) {
            return;
        }
        onCommitTransition();
    }

    public void resetTmpFields() {
        this.mPendingCaptureActivity.clear();
        this.mPendingRemoveActivity.clear();
        this.mPendingDeleteActivity.clear();
        this.mPendingLoadActivity.clear();
    }

    public final void onCommitTransition() {
        for (int size = this.mPendingCaptureActivity.size() - 1; size >= 0; size--) {
            recordSnapshot((ActivityRecord) this.mPendingCaptureActivity.valueAt(size));
        }
        for (int size2 = this.mPendingRemoveActivity.size() - 1; size2 >= 0; size2--) {
            ((ActivitySnapshotCache) this.mCache).onIdRemoved(Integer.valueOf(getSystemHashCode((ActivityRecord) this.mPendingRemoveActivity.valueAt(size2))));
        }
        for (int size3 = this.mPendingDeleteActivity.size() - 1; size3 >= 0; size3--) {
            ActivityRecord activityRecord = (ActivityRecord) this.mPendingDeleteActivity.valueAt(size3);
            int systemHashCode = getSystemHashCode(activityRecord);
            ((ActivitySnapshotCache) this.mCache).onIdRemoved(Integer.valueOf(systemHashCode));
            removeIfUserSavedFileExist(systemHashCode, activityRecord.mUserId);
        }
        for (int size4 = this.mPendingLoadActivity.size() - 1; size4 >= 0; size4--) {
            final ActivityRecord activityRecord2 = (ActivityRecord) this.mPendingLoadActivity.valueAt(size4);
            final int systemHashCode2 = getSystemHashCode(activityRecord2);
            final int i = activityRecord2.mUserId;
            if (((ActivitySnapshotCache) this.mCache).getSnapshot(Integer.valueOf(systemHashCode2)) == null && containsFile(systemHashCode2, i)) {
                synchronized (this.mSnapshotPersistQueue.getLock()) {
                    this.mSnapshotPersistQueue.sendToQueueLocked(new SnapshotPersistQueue.WriteQueueItem(this.mPersistInfoProvider) { // from class: com.android.server.wm.ActivitySnapshotController.2
                        @Override // com.android.server.wm.SnapshotPersistQueue.WriteQueueItem
                        public void write() {
                            TaskSnapshot loadTask = ActivitySnapshotController.this.mSnapshotLoader.loadTask(systemHashCode2, i, false);
                            synchronized (ActivitySnapshotController.this.mService.getWindowManagerLock()) {
                                if (loadTask != null) {
                                    ActivityRecord activityRecord3 = activityRecord2;
                                    if (!activityRecord3.finishing) {
                                        ((ActivitySnapshotCache) ActivitySnapshotController.this.mCache).putSnapshot(activityRecord3, loadTask);
                                    }
                                }
                            }
                        }
                    });
                }
            }
        }
        resetTmpFields();
    }

    public final void recordSnapshot(ActivityRecord activityRecord) {
        TaskSnapshot recordSnapshotInner = recordSnapshotInner(activityRecord, false);
        if (recordSnapshotInner != null) {
            addUserSavedFile(getSystemHashCode(activityRecord), activityRecord.mUserId, recordSnapshotInner);
        }
    }

    public void notifyAppVisibilityChanged(ActivityRecord activityRecord, boolean z) {
        if (shouldDisableSnapshots() || z) {
            return;
        }
        resetTmpFields();
        addBelowTopActivityIfExist(activityRecord.getTask(), this.mPendingRemoveActivity, "remove-snapshot");
        onCommitTransition();
    }

    public static int getSystemHashCode(ActivityRecord activityRecord) {
        return System.identityHashCode(activityRecord);
    }

    public void handleOpenActivityTransition(SnapshotController.TransitionState transitionState) {
        Iterator it = transitionState.getParticipant(false).iterator();
        while (it.hasNext()) {
            ActivityRecord activityRecord = (ActivityRecord) it.next();
            this.mPendingCaptureActivity.add(activityRecord);
            ActivityRecord activityBelow = activityRecord.getTask().getActivityBelow(activityRecord);
            if (activityBelow != null) {
                this.mPendingRemoveActivity.add(activityBelow);
            }
        }
    }

    public void handleCloseActivityTransition(SnapshotController.TransitionState transitionState) {
        Iterator it = transitionState.getParticipant(true).iterator();
        while (it.hasNext()) {
            ActivityRecord activityRecord = (ActivityRecord) it.next();
            this.mPendingDeleteActivity.add(activityRecord);
            ActivityRecord activityBelow = activityRecord.getTask().getActivityBelow(activityRecord);
            if (activityBelow != null) {
                this.mPendingLoadActivity.add(activityBelow);
            }
        }
    }

    public void handleCloseTaskTransition(SnapshotController.TransitionState transitionState) {
        Iterator it = transitionState.getParticipant(false).iterator();
        while (it.hasNext()) {
            addBelowTopActivityIfExist((Task) it.next(), this.mPendingRemoveActivity, "remove-snapshot");
        }
    }

    public void handleOpenTaskTransition(SnapshotController.TransitionState transitionState) {
        Iterator it = transitionState.getParticipant(true).iterator();
        while (it.hasNext()) {
            Task task = (Task) it.next();
            addBelowTopActivityIfExist(task, this.mPendingLoadActivity, "load-snapshot");
            adjustSavedFileOrder(task);
        }
    }

    public final void addBelowTopActivityIfExist(Task task, ArraySet arraySet, String str) {
        ActivityRecord activityBelow;
        ActivityRecord topMostActivity = task.getTopMostActivity();
        if (topMostActivity == null || (activityBelow = task.getActivityBelow(topMostActivity)) == null) {
            return;
        }
        arraySet.add(activityBelow);
    }

    public final void adjustSavedFileOrder(Task task) {
        final int i = task.mUserId;
        task.forAllActivities(new Consumer() { // from class: com.android.server.wm.ActivitySnapshotController$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ActivitySnapshotController.this.lambda$adjustSavedFileOrder$0(i, (ActivityRecord) obj);
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$adjustSavedFileOrder$0(int i, ActivityRecord activityRecord) {
        UserSavedFile userSavedFile = (UserSavedFile) getUserFiles(i).get(getSystemHashCode(activityRecord));
        if (userSavedFile != null) {
            this.mSavedFilesInOrder.remove(userSavedFile);
            this.mSavedFilesInOrder.add(userSavedFile);
        }
    }

    @Override // com.android.server.wm.AbsAppSnapshotController
    public void onAppRemoved(ActivityRecord activityRecord) {
        super.onAppRemoved(activityRecord);
        removeIfUserSavedFileExist(getSystemHashCode(activityRecord), activityRecord.mUserId);
    }

    @Override // com.android.server.wm.AbsAppSnapshotController
    public void onAppDied(ActivityRecord activityRecord) {
        super.onAppDied(activityRecord);
        removeIfUserSavedFileExist(getSystemHashCode(activityRecord), activityRecord.mUserId);
    }

    @Override // com.android.server.wm.AbsAppSnapshotController
    public ActivityRecord getTopFullscreenActivity(ActivityRecord activityRecord) {
        WindowState findMainWindow = activityRecord.findMainWindow();
        if (findMainWindow == null || !findMainWindow.mAttrs.isFullscreen()) {
            return null;
        }
        return activityRecord;
    }

    @Override // com.android.server.wm.AbsAppSnapshotController
    public ActivityManager.TaskDescription getTaskDescription(ActivityRecord activityRecord) {
        return activityRecord.taskDescription;
    }

    @Override // com.android.server.wm.AbsAppSnapshotController
    public ActivityRecord findAppTokenForSnapshot(ActivityRecord activityRecord) {
        if (activityRecord != null && activityRecord.canCaptureSnapshot()) {
            return activityRecord;
        }
        return null;
    }

    @Override // com.android.server.wm.AbsAppSnapshotController
    public boolean use16BitFormat() {
        return this.mPersistInfoProvider.use16BitFormat();
    }

    public final SparseArray getUserFiles(int i) {
        if (this.mUserSavedFiles.get(i) == null) {
            this.mUserSavedFiles.put(i, new SparseArray());
            cleanUpUserFiles(i);
        }
        return (SparseArray) this.mUserSavedFiles.get(i);
    }

    public final void removeIfUserSavedFileExist(int i, int i2) {
        UserSavedFile userSavedFile = (UserSavedFile) getUserFiles(i2).get(i);
        if (userSavedFile != null) {
            this.mUserSavedFiles.remove(i);
            this.mSavedFilesInOrder.remove(userSavedFile);
            this.mPersister.removeSnap(i, i2);
        }
    }

    public final boolean containsFile(int i, int i2) {
        return getUserFiles(i2).get(i) != null;
    }

    public final void addUserSavedFile(int i, int i2, TaskSnapshot taskSnapshot) {
        SparseArray userFiles = getUserFiles(i2);
        if (((UserSavedFile) userFiles.get(i)) == null) {
            UserSavedFile userSavedFile = new UserSavedFile(i, i2);
            userFiles.put(i, userSavedFile);
            this.mSavedFilesInOrder.add(userSavedFile);
            this.mPersister.persistSnapshot(i, i2, taskSnapshot);
            if (this.mSavedFilesInOrder.size() > 40) {
                purgeSavedFile();
            }
        }
    }

    public final void purgeSavedFile() {
        int size = this.mSavedFilesInOrder.size();
        int i = size - 20;
        ArrayList arrayList = new ArrayList();
        if (i > 0) {
            int i2 = size - i;
            for (int i3 = size - 1; i3 > i2; i3--) {
                UserSavedFile userSavedFile = (UserSavedFile) this.mSavedFilesInOrder.remove(i3);
                if (userSavedFile != null) {
                    this.mUserSavedFiles.remove(userSavedFile.mFileId);
                    arrayList.add(userSavedFile);
                }
            }
        }
        if (arrayList.size() > 0) {
            removeSnapshotFiles(arrayList);
        }
    }

    public final void removeSnapshotFiles(final ArrayList arrayList) {
        synchronized (this.mSnapshotPersistQueue.getLock()) {
            this.mSnapshotPersistQueue.sendToQueueLocked(new SnapshotPersistQueue.WriteQueueItem(this.mPersistInfoProvider) { // from class: com.android.server.wm.ActivitySnapshotController.3
                @Override // com.android.server.wm.SnapshotPersistQueue.WriteQueueItem
                public void write() {
                    for (int size = arrayList.size() - 1; size >= 0; size--) {
                        UserSavedFile userSavedFile = (UserSavedFile) arrayList.get(size);
                        ActivitySnapshotController.this.mSnapshotPersistQueue.deleteSnapshot(userSavedFile.mFileId, userSavedFile.mUserId, this.mPersistInfoProvider);
                    }
                }
            });
        }
    }

    public class UserSavedFile {
        public int mFileId;
        public int mUserId;

        public UserSavedFile(int i, int i2) {
            this.mFileId = i;
            this.mUserId = i2;
        }
    }
}
