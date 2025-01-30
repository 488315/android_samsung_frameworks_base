package com.android.server.wm;

import android.app.ActivityOptions;
import android.app.WindowConfiguration;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.text.TextUtils;
import android.window.WindowContainerTransaction;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class MultiInstanceController implements IController {
  public final ActivityTaskManagerService mAtm;
  public final WindowManagerGlobalLock mGlobalLock;
  public final int LAUNCH_SINGLE_INSTANCE_PER_TASK_MAX_COUNT = 5;
  public final FindTasksResult mTmpFindTaskResult = new FindTasksResult();

  @Override // com.android.server.wm.IController
  public void initialize() {}

  public MultiInstanceController(ActivityTaskManagerService activityTaskManagerService) {
    this.mAtm = activityTaskManagerService;
    this.mGlobalLock = activityTaskManagerService.mGlobalLock;
  }

  public boolean adjustStartIntents(
      WindowContainerTransaction windowContainerTransaction,
      WindowOrganizerController.CallerInfo callerInfo) {
    Task task;
    ActivityInfo activityInfo;
    Bundle bundle;
    Intent[] intentArr = {null, null, null};
    WindowContainerTransaction.HierarchyOp[] hierarchyOpArr = {null, null, null};
    WindowContainerTransaction.HierarchyOp[] hierarchyOpArr2 = {null, null, null};
    int[] iArr = new int[3];
    int i = 0;
    int i2 = 0;
    for (WindowContainerTransaction.HierarchyOp hierarchyOp :
        windowContainerTransaction.getHierarchyOps()) {
      if (hierarchyOp.getType() == 7) {
        Task fromWindowContainerToken =
            Task.fromWindowContainerToken(
                new ActivityOptions(hierarchyOp.getLaunchOptions()).getLaunchRootTask());
        if (fromWindowContainerToken != null) {
          int stageType = fromWindowContainerToken.getWindowConfiguration().getStageType();
          Intent activityIntent = hierarchyOp.getActivityIntent();
          int userId = hierarchyOp.getPendingIntent().getTarget().getUserId();
          if (stageType == 1) {
            intentArr[0] = activityIntent;
            iArr[0] = userId;
            hierarchyOpArr[0] = hierarchyOp;
          } else if (stageType == 2) {
            intentArr[1] = activityIntent;
            iArr[1] = userId;
            hierarchyOpArr[1] = hierarchyOp;
          } else if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && stageType == 4) {
            intentArr[2] = activityIntent;
            iArr[2] = userId;
            hierarchyOpArr[2] = hierarchyOp;
          }
          i++;
        }
      } else if (hierarchyOp.isReparent()) {
        hierarchyOpArr2[i2] = hierarchyOp;
        i2++;
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
                  .getDefaultTaskDisplayArea()
                  .getTopRootTaskInStageType(iArr2[i3]);
          taskArr[i3] = topRootTaskInStageType;
          Task topMostTask =
              (topRootTaskInStageType == null || !topRootTaskInStageType.shouldBeVisible(null))
                  ? null
                  : taskArr[i3].getTopMostTask();
          if (topMostTask != null && topMostTask.getRootActivity() != null) {
            activityRecordArr[i3] = topMostTask.getRootActivity();
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
    int i4 = 0;
    for (int i5 = 0; i5 < i; i5++) {
      ResolveInfo resolveIntent =
          this.mAtm.mTaskSupervisor.resolveIntent(
              intentArr[i5], null, iArr[i5], 0, callerInfo.mUid, callerInfo.mPid);
      if (resolveIntent != null
          && (activityInfo = resolveIntent.activityInfo) != null
          && (bundle = activityInfo.metaData) != null
          && !TextUtils.isEmpty(
              bundle.getString("com.samsung.android.multiwindow.activity.alias.targetactivity"))) {
        String str = resolveIntent.activityInfo.packageName;
        strArr[i5] = str;
        ActivityRecord activityRecord = activityRecordArr[i5];
        if (activityRecord != null && activityRecord.packageName.equals(str)) {
          arrayList.add(Integer.valueOf(activityRecordArr[i5].getTask().mTaskId));
          intentArr[i5] = null;
          windowContainerTransaction.getHierarchyOps().remove(hierarchyOpArr[i5]);
          if (hierarchyOpArr2[i5] != null) {
            windowContainerTransaction.getHierarchyOps().remove(hierarchyOpArr2[i5]);
          }
          i4++;
        }
      }
    }
    for (int i6 = 0; i6 < i; i6++) {
      if (intentArr[i6] != null && strArr[i6] != null) {
        ArrayList arrayList2 = new ArrayList();
        findAliasManagedTaskInPackage(strArr[i6], iArr[i6], arrayList2);
        arrayList2.sort(
            new Comparator() { // from class:
                               // com.android.server.wm.MultiInstanceController$$ExternalSyntheticLambda0
              @Override // java.util.Comparator
              public final int compare(Object obj, Object obj2) {
                int lambda$adjustStartIntents$0;
                lambda$adjustStartIntents$0 =
                    MultiInstanceController.lambda$adjustStartIntents$0((Task) obj, (Task) obj2);
                return lambda$adjustStartIntents$0;
              }
            });
        for (int size = arrayList2.size() - 1; size >= 0; size--) {
          task = (Task) arrayList2.get(size);
          if (arrayList.contains(Integer.valueOf(task.mTaskId))) {
            arrayList2.remove(task);
          } else if (task.isVisible()
              && (task.inSplitScreenWindowingMode() || task.getWindowingMode() == 1)) {
            arrayList2.remove(task);
            break;
          }
        }
        task = null;
        if (task == null) {
          int i7 = 0;
          while (true) {
            if (i7 >= arrayList2.size()) {
              break;
            }
            Task task2 = (Task) arrayList2.get(i7);
            if (task2.getWindowingMode() == 5) {
              task = task2;
              break;
            }
            i7++;
          }
        }
        if (task != null) {
          intentArr[i6].setLaunchTaskIdForAliasManagedTarget(task.mTaskId);
          intentArr[i6].setComponent(task.getBaseIntent().getComponent());
          arrayList.add(Integer.valueOf(task.mTaskId));
        }
      }
    }
    return i4 != i;
  }

  public static /* synthetic */ int lambda$adjustStartIntents$0(Task task, Task task2) {
    return Long.compare(task2.lastGainFocusTime, task.lastGainFocusTime);
  }

  public void findAliasManagedTaskInPackage(
      final String str, final int i, final ArrayList arrayList) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        this.mAtm.mRootWindowContainer.forAllLeafTasks(
            new Consumer() { // from class:
                             // com.android.server.wm.MultiInstanceController$$ExternalSyntheticLambda2
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                MultiInstanceController.lambda$findAliasManagedTaskInPackage$1(
                    i, str, arrayList, (Task) obj);
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

  public static /* synthetic */ void lambda$findAliasManagedTaskInPackage$1(
      int i, String str, ArrayList arrayList, Task task) {
    ActivityRecord rootActivity;
    if (task.isAliasManaged()
        && task.mUserId == i
        && (rootActivity = task.getRootActivity()) != null
        && str.equals(rootActivity.packageName)) {
      arrayList.add(task);
    }
  }

  public boolean allowMultipleTask(
      ActivityRecord activityRecord, int i, int i2, ActivityRecord activityRecord2) {
    if (activityRecord.intent.getLaunchTaskIdForSingleInstancePerTask() != -1) {
      return false;
    }
    ArrayList arrayList = new ArrayList();
    findTasks(activityRecord, arrayList);
    if ((activityRecord2 == null && i == 0 && i2 == 0)
        || (activityRecord2 != null
            && !activityRecord2.isActivityTypeHome()
            && i == 0
            && i2 == 0)) {
      int i3 = 0;
      while (true) {
        if (i3 >= arrayList.size()) {
          break;
        }
        Task task = (Task) arrayList.get(i3);
        if (task.isVisible()) {
          activityRecord.intent.setLaunchTaskIdForSingleInstancePerTask(task.mTaskId);
          break;
        }
        i3++;
      }
      return false;
    }
    if (activityRecord2 != null
        && !activityRecord2.isActivityTypeHome()
        && !activityRecord2.inMultiWindowMode()) {
      return false;
    }
    HashMap hashMap = new HashMap();
    Task task2 = null;
    Task task3 = null;
    for (int i4 = 0; i4 < arrayList.size(); i4++) {
      Task task4 = (Task) arrayList.get(i4);
      if (task4.inSplitScreenWindowingMode() && task4.getStageType() == i2) {
        activityRecord.intent.setLaunchTaskIdForSingleInstancePerTask(task4.mTaskId);
        return false;
      }
      if (task4.inFreeformWindowingMode()) {
        if (task4.isMinimized() || (task4.isDexMode() && !task4.isVisible())) {
          activityRecord.intent.setLaunchTaskIdForSingleInstancePerTask(task4.mTaskId);
          return false;
        }
        if (task3 == null) {
          task3 = task4;
        }
      } else if (task4.isVisible()) {
        if (task4.inSplitScreenWindowingMode()) {
          hashMap.put(Integer.valueOf(task4.getStageType()), task4);
        } else {
          hashMap.put(Integer.valueOf(task4.getWindowingMode()), task4);
        }
      } else if (task2 == null) {
        task2 = task4;
      }
    }
    Task task5 = (Task) hashMap.get(Integer.valueOf(i));
    if (task5 != null || task2 == null) {
      task2 = task5;
    }
    if (task2 != null) {
      activityRecord.intent.setLaunchTaskIdForSingleInstancePerTask(task2.mTaskId);
      return false;
    }
    if (arrayList.size() < 5 && arrayList.size() != 0) {
      return true;
    }
    if (task3 != null) {
      activityRecord.intent.setLaunchTaskIdForSingleInstancePerTask(task3.mTaskId);
    }
    return false;
  }

  public boolean adjustStartIntentsForSingleInstancePerTask(
      WindowContainerTransaction windowContainerTransaction) {
    Task task;
    Task fromWindowContainerToken;
    Task task2 = null;
    Intent[] intentArr = {null, null, null};
    WindowContainerTransaction.HierarchyOp[] hierarchyOpArr = {null, null, null};
    for (WindowContainerTransaction.HierarchyOp hierarchyOp :
        windowContainerTransaction.getHierarchyOps()) {
      if (hierarchyOp.getType() == 7
          && (fromWindowContainerToken =
                  Task.fromWindowContainerToken(
                      new ActivityOptions(hierarchyOp.getLaunchOptions()).getLaunchRootTask()))
              != null) {
        int stageType = fromWindowContainerToken.getWindowConfiguration().getStageType();
        Intent activityIntent = hierarchyOp.getActivityIntent();
        hierarchyOp.getPendingIntent().getTarget().getUserId();
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
    for (int i = 0; i < 3; i++) {
      Task topRootTaskInStageType =
          this.mAtm
              .mRootWindowContainer
              .getDefaultTaskDisplayArea()
              .getTopRootTaskInStageType(iArr[i]);
      taskArr[i] = topRootTaskInStageType;
      Task topMostTask =
          (topRootTaskInStageType == null || !topRootTaskInStageType.shouldBeVisible(null))
              ? null
              : taskArr[i].getTopMostTask();
      if (topMostTask != null && topMostTask.getRootActivity() != null) {
        taskArr2[i] = topMostTask;
      }
    }
    ActivityRecord[] activityRecordArr = new ActivityRecord[3];
    ArrayList arrayList = new ArrayList();
    int i2 = 0;
    for (int i3 = 0; i3 < 3; i3++) {
      Intent intent = intentArr[i3];
      if (intent != null) {
        ActivityInfo launchModeSingleInstancePerTask =
            getLaunchModeSingleInstancePerTask(this.mAtm.mContext, intent);
        if (launchModeSingleInstancePerTask != null) {
          long clearCallingIdentity = Binder.clearCallingIdentity();
          try {
            ActivityRecord build =
                new ActivityRecord.Builder(this.mAtm)
                    .setCaller(null)
                    .setLaunchedFromPid(0)
                    .setLaunchedFromUid(-1)
                    .setLaunchedFromPackage(null)
                    .setLaunchedFromFeature(null)
                    .setIntent(intentArr[i3])
                    .setResolvedType(null)
                    .setActivityInfo(launchModeSingleInstancePerTask)
                    .setConfiguration(this.mAtm.getGlobalConfiguration())
                    .setResultTo(null)
                    .setResultWho(null)
                    .setRequestCode(0)
                    .setComponentSpecified(intentArr[i3].getComponent() != null)
                    .setRootVoiceInteraction(false)
                    .setActivityOptions(null)
                    .setSourceRecord(null)
                    .build();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            activityRecordArr[i3] = build;
            Task task3 = taskArr2[i3];
            if (task3 != null && hasMatchedActivity(build, task3)) {
              arrayList.add(Integer.valueOf(taskArr2[i3].mTaskId));
              intentArr[i3] = null;
              windowContainerTransaction.getHierarchyOps().remove(hierarchyOpArr[i3]);
            }
          } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
          }
        } else {
          continue;
        }
      }
      i2++;
    }
    ArrayList arrayList2 = new ArrayList();
    int i4 = 0;
    while (i4 < 3) {
      if (intentArr[i4] != null && activityRecordArr[i4] != null) {
        ArrayList arrayList3 = new ArrayList();
        String flattenToShortString = intentArr[i4].getComponent().flattenToShortString();
        findTasks(activityRecordArr[i4], arrayList3);
        if (arrayList3.size() == 0 && !arrayList2.contains(flattenToShortString)) {
          arrayList2.add(flattenToShortString);
        } else {
          int size = arrayList3.size() - 1;
          while (true) {
            if (size < 0) {
              task = task2;
              break;
            }
            task = (Task) arrayList3.get(size);
            if (arrayList.contains(Integer.valueOf(task.mTaskId))) {
              arrayList3.remove(task);
            } else if (task.isVisible()) {
              if (WindowConfiguration.isSplitScreenWindowingMode(task.getWindowConfiguration())
                  || task.getWindowingMode() == 1) {
                break;
              }
              size--;
            }
            size--;
          }
          if (task == null) {
            int i5 = 0;
            while (true) {
              if (i5 >= arrayList3.size()) {
                break;
              }
              Task task4 = (Task) arrayList3.get(i5);
              if (task4.getWindowingMode() == 5) {
                task = task4;
                break;
              }
              i5++;
            }
          }
          if (task == null) {
            int i6 = 0;
            while (true) {
              if (i6 >= arrayList3.size()) {
                break;
              }
              Task task5 = (Task) arrayList3.get(i6);
              if (!task5.isVisible()) {
                task = task5;
                break;
              }
              i6++;
            }
          }
          if (task == null) {
            intentArr[i4].addFlags(134217728);
          } else {
            intentArr[i4].setLaunchTaskIdForSingleInstancePerTask(task.mTaskId);
            arrayList.add(Integer.valueOf(task.mTaskId));
          }
          i4++;
          task2 = null;
        }
      }
      i4++;
      task2 = null;
    }
    return i2 != 3;
  }

  public boolean hasMatchedActivity(ActivityRecord activityRecord, Task task) {
    ArrayList arrayList = new ArrayList();
    this.mTmpFindTaskResult.process(activityRecord, task, arrayList);
    return arrayList.size() > 0;
  }

  public ActivityInfo getLaunchModeSingleInstancePerTask(Context context, Intent intent) {
    PackageManager packageManager = context.getPackageManager();
    if (packageManager == null) {
      return null;
    }
    Iterator<ResolveInfo> it =
        packageManager
            .queryIntentActivities(intent, PackageManager.ResolveInfoFlags.of(65536L))
            .iterator();
    while (it.hasNext()) {
      ActivityInfo activityInfo = it.next().activityInfo;
      if (activityInfo.launchMode == 4) {
        return activityInfo;
      }
    }
    return null;
  }

  public final void findTasks(ActivityRecord activityRecord, ArrayList arrayList) {
    this.mTmpFindTaskResult.process(activityRecord, this.mAtm.mRootWindowContainer, arrayList);
    arrayList.sort(
        new Comparator() { // from class:
                           // com.android.server.wm.MultiInstanceController$$ExternalSyntheticLambda1
          @Override // java.util.Comparator
          public final int compare(Object obj, Object obj2) {
            int lambda$findTasks$2;
            lambda$findTasks$2 =
                MultiInstanceController.lambda$findTasks$2((Task) obj, (Task) obj2);
            return lambda$findTasks$2;
          }
        });
  }

  public static /* synthetic */ int lambda$findTasks$2(Task task, Task task2) {
    return Long.compare(task2.lastGainFocusTime, task.lastGainFocusTime);
  }

  public class FindTasksResult {
    public ComponentName cls;
    public Uri documentData;
    public Intent intent;
    public boolean isDocument;
    public ActivityRecord mTarget;
    public int userId;

    public void process(
        ActivityRecord activityRecord, WindowContainer windowContainer, final ArrayList arrayList) {
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
              MultiInstanceController.FindTasksResult.this.lambda$process$0(arrayList, obj);
            }
          },
          true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$process$0(ArrayList arrayList, Object obj) {
      ActivityRecord topNonFinishingActivity;
      Uri uri;
      String str;
      Task task = (Task) obj;
      if (task.voiceSession == null
          && task.mUserId == this.userId
          && (topNonFinishingActivity = task.getTopNonFinishingActivity(false)) != null
          && !topNonFinishingActivity.finishing
          && topNonFinishingActivity.mUserId == this.userId
          && ConfigurationContainer.isCompatibleActivityType(
              topNonFinishingActivity.getActivityType(), this.mTarget.getActivityType())) {
        Intent intent = task.intent;
        Intent intent2 = task.affinityIntent;
        boolean z = true;
        if (intent != null && intent.isDocument()) {
          uri = intent.getData();
        } else if (intent2 == null || !intent2.isDocument()) {
          z = false;
          uri = null;
        } else {
          uri = intent2.getData();
        }
        ComponentName componentName = task.realActivity;
        if (componentName != null
            && componentName.compareTo(this.cls) == 0
            && Objects.equals(this.documentData, uri)) {
          arrayList.add(task);
          return;
        }
        if (intent2 != null
            && intent2.getComponent() != null
            && intent2.getComponent().compareTo(this.cls) == 0
            && Objects.equals(this.documentData, uri)) {
          arrayList.add(task);
        } else {
          if (this.isDocument
              || z
              || (str = task.rootAffinity) == null
              || !str.equals(this.mTarget.taskAffinity)) {
            return;
          }
          arrayList.add(task);
        }
      }
    }
  }

  @Override // com.android.server.wm.IController
  public void dumpLocked(PrintWriter printWriter, String str) {
    printWriter.println("[MultiInstanceController]");
  }
}
