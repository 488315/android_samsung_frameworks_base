package com.android.server.wm;

import android.app.ActivityOptions;
import android.app.WindowConfiguration;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.text.TextUtils;
import android.window.WindowContainerTransaction;

import com.samsung.android.rune.CoreRune;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

public final class MultiInstanceController implements IController {
    public final ActivityTaskManagerService mAtm;
    public final WindowManagerGlobalLock mGlobalLock;
    public final FindTasksResult mTmpFindTaskResult = new FindTasksResult();

    public final class FindTasksResult {
        public ComponentName cls;
        public Uri documentData;
        public Intent intent;
        public boolean isDocument;
        public ActivityRecord mTarget;
        public int userId;

        public final void process(
                ActivityRecord activityRecord,
                WindowContainer windowContainer,
                final ArrayList arrayList) {
            this.mTarget = activityRecord;
            Intent intent = activityRecord.intent;
            this.intent = intent;
            this.cls = activityRecord.mActivityComponent;
            this.userId = activityRecord.mUserId;
            boolean z = intent != null && intent.isDocument();
            this.isDocument = z;
            this.documentData = z ? this.intent.getData() : null;
            windowContainer.forAllLeafTasks(
                    new Consumer() { // from class:
                                     // com.android.server.wm.MultiInstanceController$FindTasksResult$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            Uri uri;
                            String str;
                            MultiInstanceController.FindTasksResult findTasksResult =
                                    MultiInstanceController.FindTasksResult.this;
                            ArrayList arrayList2 = arrayList;
                            findTasksResult.getClass();
                            Task task = (Task) obj;
                            if (task.voiceSession == null
                                    && task.mUserId == findTasksResult.userId) {
                                boolean z2 = true;
                                ActivityRecord topNonFinishingActivity =
                                        task.getTopNonFinishingActivity(false, true);
                                if (topNonFinishingActivity == null
                                        || topNonFinishingActivity.finishing
                                        || topNonFinishingActivity.mUserId != findTasksResult.userId
                                        || !ConfigurationContainer.isCompatibleActivityType(
                                                topNonFinishingActivity.getActivityType(),
                                                findTasksResult.mTarget.getActivityType())) {
                                    return;
                                }
                                Intent intent2 = task.intent;
                                Intent intent3 = task.affinityIntent;
                                if (intent2 != null && intent2.isDocument()) {
                                    uri = intent2.getData();
                                } else if (intent3 == null || !intent3.isDocument()) {
                                    z2 = false;
                                    uri = null;
                                } else {
                                    uri = intent3.getData();
                                }
                                ComponentName componentName = task.realActivity;
                                if (componentName != null
                                        && componentName.compareTo(findTasksResult.cls) == 0
                                        && Objects.equals(findTasksResult.documentData, uri)) {
                                    arrayList2.add(task);
                                    return;
                                }
                                if (intent3 != null
                                        && intent3.getComponent() != null
                                        && intent3.getComponent().compareTo(findTasksResult.cls)
                                                == 0
                                        && Objects.equals(findTasksResult.documentData, uri)) {
                                    arrayList2.add(task);
                                } else {
                                    if (findTasksResult.isDocument
                                            || z2
                                            || (str = task.rootAffinity) == null
                                            || !str.equals(findTasksResult.mTarget.taskAffinity)) {
                                        return;
                                    }
                                    arrayList2.add(task);
                                }
                            }
                        }
                    },
                    true);
        }
    }

    public MultiInstanceController(ActivityTaskManagerService activityTaskManagerService) {
        this.mAtm = activityTaskManagerService;
        this.mGlobalLock = activityTaskManagerService.mGlobalLock;
    }

    public final void adjustStartIntents(
            WindowContainerTransaction windowContainerTransaction,
            WindowOrganizerController.CallerInfo callerInfo) {
        Task task;
        ActivityInfo activityInfo;
        Bundle bundle;
        Task fromWindowContainerToken;
        Intent[] intentArr = new Intent[3];
        intentArr[0] = null;
        intentArr[1] = null;
        intentArr[2] = null;
        WindowContainerTransaction.HierarchyOp[] hierarchyOpArr = {null, null, null};
        int[] iArr = new int[3];
        int i = 0;
        for (WindowContainerTransaction.HierarchyOp hierarchyOp :
                windowContainerTransaction.getHierarchyOps()) {
            if (hierarchyOp.getType() == 7
                    && (fromWindowContainerToken =
                                    Task.fromWindowContainerToken(
                                            new ActivityOptions(hierarchyOp.getLaunchOptions())
                                                    .getLaunchRootTask()))
                            != null) {
                int stageType = fromWindowContainerToken.getWindowConfiguration().getStageType();
                Intent activityIntent = hierarchyOp.getActivityIntent();
                int i2 = hierarchyOp.getPendingIntent().getTarget().key.userId;
                if (stageType == 1) {
                    intentArr[0] = activityIntent;
                    iArr[0] = i2;
                    hierarchyOpArr[0] = hierarchyOp;
                } else if (stageType == 2) {
                    intentArr[1] = activityIntent;
                    iArr[1] = i2;
                    hierarchyOpArr[1] = hierarchyOp;
                } else if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && stageType == 4) {
                    intentArr[2] = activityIntent;
                    iArr[2] = i2;
                    hierarchyOpArr[2] = hierarchyOp;
                }
                i++;
            }
        }
        Task[] taskArr = new Task[i];
        ActivityRecord[] activityRecordArr = new ActivityRecord[i];
        int[] iArr2 = new int[i];
        iArr2[0] = 1;
        iArr2[1] = 2;
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && i > 2) {
            iArr2[2] = 4;
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            for (int i3 = 0; i3 < i; i3++) {
                try {
                    Task topRootTaskInStageType =
                            this.mAtm
                                    .mRootWindowContainer
                                    .mDefaultDisplay
                                    .getDefaultTaskDisplayArea()
                                    .getTopRootTaskInStageType(iArr2[i3]);
                    taskArr[i3] = topRootTaskInStageType;
                    Task topMostTask =
                            (topRootTaskInStageType == null
                                            || !topRootTaskInStageType.shouldBeVisible(null))
                                    ? null
                                    : taskArr[i3].getTopMostTask();
                    if (topMostTask != null && topMostTask.getRootActivity(true, false) != null) {
                        activityRecordArr[i3] = topMostTask.getRootActivity(true, false);
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        String[] strArr = new String[i];
        ArrayList arrayList = new ArrayList();
        for (int i4 = 0; i4 < i; i4++) {
            ResolveInfo resolveIntent =
                    this.mAtm.mTaskSupervisor.resolveIntent(
                            intentArr[i4], null, iArr[i4], 0, callerInfo.mUid, callerInfo.mPid);
            if (resolveIntent != null
                    && (activityInfo = resolveIntent.activityInfo) != null
                    && (bundle = activityInfo.metaData) != null
                    && !TextUtils.isEmpty(
                            bundle.getString(
                                    "com.samsung.android.multiwindow.activity.alias.targetactivity"))) {
                String str = resolveIntent.activityInfo.packageName;
                strArr[i4] = str;
                ActivityRecord activityRecord = activityRecordArr[i4];
                if (activityRecord != null && activityRecord.packageName.equals(str)) {
                    arrayList.add(Integer.valueOf(activityRecordArr[i4].task.mTaskId));
                    intentArr[i4] = null;
                    windowContainerTransaction.getHierarchyOps().remove(hierarchyOpArr[i4]);
                }
            }
        }
        for (int i5 = 0; i5 < i; i5++) {
            if (intentArr[i5] != null && strArr[i5] != null) {
                ArrayList arrayList2 = new ArrayList();
                findAliasManagedTaskInPackage(iArr[i5], strArr[i5], arrayList2);
                arrayList2.sort(new MultiInstanceController$$ExternalSyntheticLambda0(1));
                for (int size = arrayList2.size() - 1; size >= 0; size--) {
                    task = (Task) arrayList2.get(size);
                    if (arrayList.contains(Integer.valueOf(task.mTaskId))) {
                        arrayList2.remove(task);
                    } else if (task.isVisible()
                            && (task.inSplitScreenWindowingMode()
                                    || task.getWindowingMode() == 1)) {
                        arrayList2.remove(task);
                        break;
                    }
                }
                task = null;
                if (task == null) {
                    int i6 = 0;
                    while (true) {
                        if (i6 >= arrayList2.size()) {
                            break;
                        }
                        Task task2 = (Task) arrayList2.get(i6);
                        if (task2.getWindowingMode() == 5) {
                            task = task2;
                            break;
                        }
                        i6++;
                    }
                }
                if (task != null) {
                    intentArr[i5].setLaunchTaskIdForAliasManagedTarget(task.mTaskId);
                    intentArr[i5].setComponent(task.getBaseIntent().getComponent());
                    arrayList.add(Integer.valueOf(task.mTaskId));
                }
            }
        }
    }

    public final void adjustStartIntentsForSingleInstancePerTask(
            WindowContainerTransaction windowContainerTransaction) {
        int i;
        ActivityTaskManagerService activityTaskManagerService;
        FindTasksResult findTasksResult;
        ActivityTaskManagerService activityTaskManagerService2;
        Task task;
        ActivityInfo activityInfo;
        ActivityTaskManagerService activityTaskManagerService3;
        Task fromWindowContainerToken;
        Intent[] intentArr = new Intent[3];
        intentArr[0] = null;
        intentArr[1] = null;
        intentArr[2] = null;
        WindowContainerTransaction.HierarchyOp[] hierarchyOpArr = {null, null, null};
        Iterator it = windowContainerTransaction.getHierarchyOps().iterator();
        while (true) {
            i = 4;
            if (!it.hasNext()) {
                break;
            }
            WindowContainerTransaction.HierarchyOp hierarchyOp =
                    (WindowContainerTransaction.HierarchyOp) it.next();
            if (hierarchyOp.getType() == 7
                    && (fromWindowContainerToken =
                                    Task.fromWindowContainerToken(
                                            new ActivityOptions(hierarchyOp.getLaunchOptions())
                                                    .getLaunchRootTask()))
                            != null) {
                int stageType = fromWindowContainerToken.getWindowConfiguration().getStageType();
                Intent activityIntent = hierarchyOp.getActivityIntent();
                int i2 = hierarchyOp.getPendingIntent().getTarget().key.userId;
                if (stageType == 1) {
                    intentArr[0] = activityIntent;
                    hierarchyOpArr[0] = hierarchyOp;
                } else if (stageType == 2) {
                    intentArr[1] = activityIntent;
                    hierarchyOpArr[1] = hierarchyOp;
                } else if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && stageType == 4) {
                    intentArr[2] = activityIntent;
                    hierarchyOpArr[2] = hierarchyOp;
                }
            }
        }
        Task[] taskArr = new Task[3];
        Task[] taskArr2 = new Task[3];
        int[] iArr = new int[3];
        iArr[0] = 1;
        iArr[1] = 2;
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
            iArr[2] = 4;
        }
        int i3 = 0;
        while (true) {
            activityTaskManagerService = this.mAtm;
            if (i3 >= 3) {
                break;
            }
            Task topRootTaskInStageType =
                    activityTaskManagerService
                            .mRootWindowContainer
                            .mDefaultDisplay
                            .getDefaultTaskDisplayArea()
                            .getTopRootTaskInStageType(iArr[i3]);
            taskArr[i3] = topRootTaskInStageType;
            Task topMostTask =
                    (topRootTaskInStageType == null
                                    || !topRootTaskInStageType.shouldBeVisible(null))
                            ? null
                            : taskArr[i3].getTopMostTask();
            if (topMostTask != null && topMostTask.getRootActivity(true, false) != null) {
                taskArr2[i3] = topMostTask;
            }
            i3++;
        }
        ActivityRecord[] activityRecordArr = new ActivityRecord[3];
        ArrayList arrayList = new ArrayList();
        int i4 = 0;
        while (true) {
            findTasksResult = this.mTmpFindTaskResult;
            if (i4 >= 3) {
                break;
            }
            Intent intent = intentArr[i4];
            if (intent != null) {
                PackageManager packageManager =
                        activityTaskManagerService.mContext.getPackageManager();
                if (packageManager != null) {
                    Iterator<ResolveInfo> it2 =
                            packageManager
                                    .queryIntentActivities(
                                            intent, PackageManager.ResolveInfoFlags.of(65536L))
                                    .iterator();
                    while (it2.hasNext()) {
                        ActivityInfo activityInfo2 = it2.next().activityInfo;
                        if (activityInfo2.launchMode == i) {
                            activityInfo = activityInfo2;
                            break;
                        }
                    }
                }
                activityInfo = null;
                if (activityInfo != null) {
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        Intent intent2 = intentArr[i4];
                        Configuration globalConfiguration =
                                activityTaskManagerService.getGlobalConfiguration();
                        boolean z = intentArr[i4].getComponent() != null;
                        if (globalConfiguration == null) {
                            globalConfiguration = activityTaskManagerService.getConfiguration();
                        }
                        activityTaskManagerService3 = activityTaskManagerService;
                        ActivityRecord activityRecord =
                                new ActivityRecord(
                                        activityTaskManagerService,
                                        null,
                                        0,
                                        -1,
                                        null,
                                        null,
                                        intent2,
                                        null,
                                        activityInfo,
                                        globalConfiguration,
                                        null,
                                        null,
                                        0,
                                        z,
                                        false,
                                        activityTaskManagerService.mTaskSupervisor,
                                        null,
                                        null,
                                        null,
                                        null,
                                        0L);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        activityRecordArr[i4] = activityRecord;
                        Task task2 = taskArr2[i4];
                        if (task2 != null) {
                            ArrayList arrayList2 = new ArrayList();
                            findTasksResult.process(activityRecord, task2, arrayList2);
                            if (arrayList2.size() > 0) {
                                arrayList.add(Integer.valueOf(taskArr2[i4].mTaskId));
                                intentArr[i4] = null;
                                windowContainerTransaction
                                        .getHierarchyOps()
                                        .remove(hierarchyOpArr[i4]);
                            }
                        }
                        i4++;
                        activityTaskManagerService = activityTaskManagerService3;
                        i = 4;
                    } catch (Throwable th) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
            }
            activityTaskManagerService3 = activityTaskManagerService;
            i4++;
            activityTaskManagerService = activityTaskManagerService3;
            i = 4;
        }
        ActivityTaskManagerService activityTaskManagerService4 = activityTaskManagerService;
        ArrayList arrayList3 = new ArrayList();
        int i5 = 0;
        while (i5 < 3) {
            if (intentArr[i5] == null || activityRecordArr[i5] == null) {
                activityTaskManagerService2 = activityTaskManagerService4;
            } else {
                ArrayList arrayList4 = new ArrayList();
                String flattenToShortString = intentArr[i5].getComponent().flattenToShortString();
                activityTaskManagerService2 = activityTaskManagerService4;
                findTasksResult.process(
                        activityRecordArr[i5],
                        activityTaskManagerService2.mRootWindowContainer,
                        arrayList4);
                arrayList4.sort(new MultiInstanceController$$ExternalSyntheticLambda0(0));
                if (arrayList4.size() != 0 || arrayList3.contains(flattenToShortString)) {
                    int size = arrayList4.size() - 1;
                    while (true) {
                        if (size < 0) {
                            task = null;
                            break;
                        }
                        task = (Task) arrayList4.get(size);
                        if (!arrayList.contains(Integer.valueOf(task.mTaskId))) {
                            if (!task.isVisible()) {
                                continue;
                            } else if (WindowConfiguration.isSplitScreenWindowingMode(
                                            task.getWindowConfiguration())
                                    || task.getWindowingMode() == 1) {
                                break;
                            }
                        } else {
                            arrayList4.remove(task);
                        }
                        size--;
                    }
                    if (task == null) {
                        int i6 = 0;
                        while (true) {
                            if (i6 >= arrayList4.size()) {
                                break;
                            }
                            Task task3 = (Task) arrayList4.get(i6);
                            if (task3.getWindowingMode() == 5) {
                                task = task3;
                                break;
                            }
                            i6++;
                        }
                    }
                    if (task == null) {
                        int i7 = 0;
                        while (true) {
                            if (i7 >= arrayList4.size()) {
                                break;
                            }
                            Task task4 = (Task) arrayList4.get(i7);
                            if (!task4.isVisible()) {
                                task = task4;
                                break;
                            }
                            i7++;
                        }
                    }
                    if (task == null) {
                        intentArr[i5].addFlags(134217728);
                    } else {
                        intentArr[i5].setLaunchTaskIdForSingleInstancePerTask(task.mTaskId);
                        arrayList.add(Integer.valueOf(task.mTaskId));
                    }
                } else {
                    arrayList3.add(flattenToShortString);
                }
            }
            i5++;
            activityTaskManagerService4 = activityTaskManagerService2;
        }
    }

    @Override // com.android.server.wm.IController
    public final void dumpLocked(PrintWriter printWriter) {
        printWriter.println("[MultiInstanceController]");
    }

    public final void findAliasManagedTaskInPackage(
            final int i, final String str, final ArrayList arrayList) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mAtm.mRootWindowContainer.forAllLeafTasks(
                        new Consumer() { // from class:
                                         // com.android.server.wm.MultiInstanceController$$ExternalSyntheticLambda2
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                ActivityRecord rootActivity;
                                int i2 = i;
                                String str2 = str;
                                ArrayList arrayList2 = arrayList;
                                Task task = (Task) obj;
                                if (task.mIsAliasManaged
                                        && task.mUserId == i2
                                        && (rootActivity = task.getRootActivity(true, false))
                                                != null
                                        && str2.equals(rootActivity.packageName)) {
                                    arrayList2.add(task);
                                }
                            }
                        },
                        true);
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.wm.IController
    public final void initialize() {}
}
