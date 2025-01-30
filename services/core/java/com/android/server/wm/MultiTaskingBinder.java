package com.android.server.wm;

import android.app.PendingIntent;
import android.content.pm.ActivityInfo;
import android.content.pm.ParceledListSlice;
import android.content.pm.StringParceledListSlice;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Binder;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Slog;
import android.view.MotionEvent;
import android.window.WindowContainerToken;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.multiwindow.IDexSnappingCallback;
import com.samsung.android.multiwindow.IDexTransientCaptionDelayListener;
import com.samsung.android.multiwindow.IFreeformCallback;
import com.samsung.android.multiwindow.IMultiTaskingBinder;
import com.samsung.android.multiwindow.IRemoteAppTransitionListener;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.multiwindow.SurfaceFreezerSnapshot;
import com.samsung.android.rune.CoreRune;
import java.io.FileDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public final class MultiTaskingBinder extends IMultiTaskingBinder.Stub {
  public static final String TAG = MultiTaskingBinder.class.getSimpleName();
  public final ActivityTaskManagerService mAtm;

  public int getSplitActivityPackageEnabled(String str, int i) {
    return 0;
  }

  public boolean isDismissedFlexPanelMode() {
    return false;
  }

  public boolean isFlexPanelRunning() {
    return false;
  }

  public void setSplitActivityPackageEnabled(String str, int i, int i2) {}

  public MultiTaskingBinder(ActivityTaskManagerService activityTaskManagerService) {
    this.mAtm = activityTaskManagerService;
  }

  public final void enforceNotIsolatedCaller(String str) {
    if (UserHandle.isIsolated(Binder.getCallingUid())) {
      throw new SecurityException("Isolated process not allowed to call " + str);
    }
  }

  public boolean minimizeTaskById(int i) {
    int callingPid = Binder.getCallingPid();
    int callingUid = Binder.getCallingUid();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          if (ActivityTaskManagerService.checkPermission(
                  "android.permission.MANAGE_ACTIVITY_TASKS", callingPid, callingUid)
              != 0) {
            Slog.w(TAG, "Permission Denial: minimizeTaskById, caller:" + callingUid);
            WindowManagerService.resetPriorityAfterLockedSection();
            return false;
          }
          Task anyTaskForId = this.mAtm.mRootWindowContainer.anyTaskForId(i, 0);
          if (anyTaskForId == null) {
            WindowManagerService.resetPriorityAfterLockedSection();
            return false;
          }
          if (CoreRune.MW_FREEFORM_MINIMIZE_SA_LOGGING && !anyTaskForId.isDexMode()) {
            final int[] iArr = {1};
            this.mAtm
                .mRootWindowContainer
                .getDefaultTaskDisplayArea()
                .forAllLeafTasks(
                    new Consumer() { // from class:
                                     // com.android.server.wm.MultiTaskingBinder$$ExternalSyntheticLambda2
                      @Override // java.util.function.Consumer
                      public final void accept(Object obj) {
                        MultiTaskingBinder.lambda$minimizeTaskById$0(iArr, (Task) obj);
                      }
                    },
                    true);
            CoreSaLogger.logForAdvanced("2001", String.valueOf(iArr[0]));
          }
          boolean minimizeTaskLocked =
              this.mAtm.mMultiTaskingController.minimizeTaskLocked(anyTaskForId, true);
          WindowManagerService.resetPriorityAfterLockedSection();
          return minimizeTaskLocked;
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public static /* synthetic */ void lambda$minimizeTaskById$0(int[] iArr, Task task) {
    if (task.inFreeformWindowingMode() && task.isMinimized()) {
      iArr[0] = iArr[0] + 1;
    }
  }

  public boolean minimizeAllTasks(int i) {
    ActivityTaskManagerService.enforceTaskPermission("minimizeAllTasks");
    WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
          this.mAtm.mMultiTaskingController.minimizeAllTasksLocked(i, true);
        } finally {
          Binder.restoreCallingIdentity(clearCallingIdentity);
        }
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
    return true;
  }

  public boolean minimizeAllTasksByRecents(int i) {
    ActivityTaskManagerService.enforceTaskPermission("minimizeAllTasksByRecents");
    WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
          this.mAtm.mMultiTaskingController.minimizeAllTasksLocked(i, false);
        } finally {
          Binder.restoreCallingIdentity(clearCallingIdentity);
        }
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
    return true;
  }

  public boolean minimizeTaskToSpecificPosition(int i, boolean z, int i2, int i3) {
    boolean minimizeTaskLocked;
    ActivityTaskManagerService.enforceTaskPermission("minimizeTaskToSpecificPosition");
    WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
          minimizeTaskLocked =
              this.mAtm.mMultiTaskingController.minimizeTaskLocked(
                  this.mAtm.mRootWindowContainer.anyTaskForId(i), z, i2, i3);
        } finally {
          Binder.restoreCallingIdentity(clearCallingIdentity);
        }
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
    return minimizeTaskLocked;
  }

  public void registerFreeformCallback(IFreeformCallback iFreeformCallback) {
    ActivityTaskManagerService.enforceTaskPermission("registerFreeformCallback");
    this.mAtm.mFreeformController.registerFreeformCallback(iFreeformCallback);
  }

  public void unregisterFreeformCallback(IFreeformCallback iFreeformCallback) {
    ActivityTaskManagerService.enforceTaskPermission("unregisterFreeformCallback");
    this.mAtm.mFreeformController.unregisterFreeformCallback(iFreeformCallback);
  }

  public void notifyFreeformMinimizeAnimationEnd(int i, PointF pointF) {
    if (CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER) {
      ActivityTaskManagerService.enforceTaskPermission("notifyFreeformMinimizeAnimationEnd");
      this.mAtm.mFreeformController.notifyFreeformMinimizeAnimationEnd(i, pointF);
    }
  }

  public void reportFreeformContainerPoint(PointF pointF) {
    if (CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER) {
      this.mAtm.mFreeformController.reportFreeformContainerPoint(pointF);
    }
  }

  public PointF getFreeformContainerPoint() {
    if (CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER) {
      return this.mAtm.mFreeformController.getFreeformContainerPoint();
    }
    return null;
  }

  public ParceledListSlice getMinimizedFreeformTasksForCurrentUser() {
    if (!CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER) {
      return null;
    }
    ActivityTaskManagerService.enforceTaskPermission("getMinimizedFreeformTasksForCurrentUser");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      return this.mAtm.mFreeformController.getMinimizedFreeformTasksForCurrentUser();
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void setBoostFreeformTaskLayer(int i, boolean z) {
    ActivityTaskManagerService.enforceTaskPermission("setBoostFreeformTaskLayer");
    WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
          Task anyTaskForId = this.mAtm.mRootWindowContainer.anyTaskForId(i, 0);
          if (anyTaskForId != null && anyTaskForId.inFreeformWindowingMode()) {
            Slog.d(TAG, "setBoostFreeformTaskLayer: t #" + i + ", boost=" + z);
            anyTaskForId.setBoostTaskLayerForFreeform(z, true);
            WindowManagerService.resetPriorityAfterLockedSection();
            return;
          }
          Slog.w(TAG, "setBoostFreeformTaskLayer: failed, cannot find t#" + i);
          WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
          Binder.restoreCallingIdentity(clearCallingIdentity);
        }
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }

  public void setBlockedMinimizeFreeformEnable(boolean z) {
    this.mAtm.mMultiStarController.checkMultiStarPackageAndPermission(
        "setBlockedMinimizeFreeformEnable");
    WindowManagerGlobalLock globalLock = this.mAtm.getGlobalLock();
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (globalLock) {
      try {
        this.mAtm.mMultiWindowEnableController.setBlockedMinimizeFreeformEnabled(z);
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public void setCustomDensityEnabled(int i) {
    this.mAtm.mMultiStarController.checkMultiStarPackageAndPermission("setCustomDensityEnabled");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          if (MultiWindowCoreState.MW_MULTISTAR_CUSTOM_DENSITY_DYNAMIC_ENABLED != i) {
            this.mAtm.mInternal.dismissSplitScreenMode(true);
            this.mAtm.mMultiStarController.setCustomDensityEnabled(i);
          }
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void setEnsureLaunchSplitEnabled(boolean z) {
    this.mAtm.mMultiStarController.checkMultiStarPackageAndPermission(
        "setEnsureLaunchSplitEnabled");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          if (MultiWindowCoreState.MW_MULTISTAR_ENSURE_LAUNCH_SPLIT_ENABLED != z) {
            this.mAtm.mMultiWindowEnableController.setEnsureLaunchSplitEnabled(z);
          }
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public ParceledListSlice getVisibleTasks() {
    return this.mAtm.getVisibleTasks();
  }

  public StringParceledListSlice getMWDisableRequesters() {
    StringParceledListSlice stringParceledListSlice;
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      synchronized (this.mAtm.mGlobalLockWithoutBoost) {
        stringParceledListSlice =
            new StringParceledListSlice(
                this.mAtm.mMultiWindowEnableController.getMWDisableRequestersLocked());
      }
      return stringParceledListSlice;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void setMultiWindowEnabledForUser(String str, String str2, boolean z, int i) {
    ActivityTaskManagerService.enforceTaskPermission("setMultiWindowEnabledForUser");
    if (i != UserHandle.getCallingUserId()) {
      this.mAtm.mAmInternal.enforceCallingPermission(
          "android.permission.INTERACT_ACROSS_USERS_FULL", "setMultiWindowEnabledForUser");
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      this.mAtm.mMultiWindowEnableController.setMultiWindowEnabledForUser(str, str2, z, i);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void setMaxVisibleFreeformCountForDex(int i, int i2) {
    ActivityTaskManagerService.enforceTaskPermission("setMaxVisibleFreeformCount");
    WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
          this.mAtm.mFreeformController.setMaxVisibleFreeformCountForDex(i, i2);
        } finally {
          Binder.restoreCallingIdentity(clearCallingIdentity);
        }
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public void notifyDragSplitAppIconHasDrawable(boolean z) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          this.mAtm.mWindowManager.mDragDropController.notifyDragSplitAppIconHasDrawable(z);
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public int getDexTaskInfoFlags(IBinder iBinder) {
    enforceNotIsolatedCaller("getDexTaskInfoFlags");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(iBinder);
          if (forTokenLocked != null && forTokenLocked.getTask() != null) {
            int dexTaskInfoFlagsLocked =
                this.mAtm.mDexController.getDexTaskInfoFlagsLocked(forTokenLocked.getTask());
            WindowManagerService.resetPriorityAfterLockedSection();
            return dexTaskInfoFlagsLocked;
          }
          Slog.w(TAG, "getDexTaskInfoFlags: cannot find ActivityRecord, token=" + iBinder);
          WindowManagerService.resetPriorityAfterLockedSection();
          Binder.restoreCallingIdentity(clearCallingIdentity);
          return 0;
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  /* JADX WARN: Multi-variable type inference failed */
  public void onShellCommand(
      FileDescriptor fileDescriptor,
      FileDescriptor fileDescriptor2,
      FileDescriptor fileDescriptor3,
      String[] strArr,
      ShellCallback shellCallback,
      ResultReceiver resultReceiver) {
    enforceShell();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      new MultiWindowShellCommand(this.mAtm)
          .exec(
              this,
              fileDescriptor,
              fileDescriptor2,
              fileDescriptor3,
              strArr,
              shellCallback,
              resultReceiver);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final boolean isCallerShell() {
    int callingUid = Binder.getCallingUid();
    return callingUid == 2000 || callingUid == 0;
  }

  public final void enforceShell() {
    if (!isCallerShell()) {
      throw new SecurityException("Caller must be shell");
    }
  }

  /* JADX WARN: Finally extract failed */
  public boolean isAllTasksResizable(int i, int i2, int i3) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          Task task = this.mAtm.mTaskSupervisor.mRecentTasks.getTask(i);
          Task task2 = this.mAtm.mTaskSupervisor.mRecentTasks.getTask(i2);
          if (!CoreRune.MW_MULTI_SPLIT_RECENT_TASKS) {
            if (task != null
                && task2 != null
                && task.supportsMultiWindowInDefaultDisplayArea()
                && task2.supportsMultiWindowInDefaultDisplayArea()) {
              WindowManagerService.resetPriorityAfterLockedSection();
              return true;
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
          }
          if (i3 == -1) {
            if (task != null
                && task2 != null
                && task.supportsMultiWindowInDefaultDisplayArea()
                && task2.supportsMultiWindowInDefaultDisplayArea()) {
              WindowManagerService.resetPriorityAfterLockedSection();
              return true;
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
          }
          Task task3 = this.mAtm.mTaskSupervisor.mRecentTasks.getTask(i3);
          if (task != null
              && task2 != null
              && task3 != null
              && task.supportsMultiWindowInDefaultDisplayArea()
              && task2.supportsMultiWindowInDefaultDisplayArea()
              && task3.supportsMultiWindowInDefaultDisplayArea()) {
            WindowManagerService.resetPriorityAfterLockedSection();
            return true;
          }
          WindowManagerService.resetPriorityAfterLockedSection();
          Binder.restoreCallingIdentity(clearCallingIdentity);
          return false;
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void registerRemoteAppTransitionListener(
      IRemoteAppTransitionListener iRemoteAppTransitionListener) {
    if (iRemoteAppTransitionListener != null) {
      ActivityTaskManagerService.enforceTaskPermission("registerRemoteAppTransitionListener()");
      this.mAtm.mMultiTaskingController.registerRemoteAppTransitionListener(
          iRemoteAppTransitionListener);
    }
  }

  public void unregisterRemoteAppTransitionListener(
      IRemoteAppTransitionListener iRemoteAppTransitionListener) {
    if (iRemoteAppTransitionListener != null) {
      ActivityTaskManagerService.enforceTaskPermission("unregisterRemoteAppTransitionListener()");
      this.mAtm.mMultiTaskingController.unregisterRemoteAppTransitionListener(
          iRemoteAppTransitionListener);
    }
  }

  /* JADX WARN: Finally extract failed */
  public void dismissSplitTask(IBinder iBinder, boolean z) {
    this.mAtm.mMultiStarController.checkMultiStarPackageAndPermission(
        "dismissSplitTask", new ArrayList(Arrays.asList("com.samsung.controlpanel")));
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
          Task task = null;
          Task task2 = isInRootTaskLocked != null ? isInRootTaskLocked.getTask() : null;
          if (isInRootTaskLocked != null && task2 != null) {
            TaskDisplayArea displayArea = task2.getDisplayArea();
            if (displayArea != null && displayArea.getDisplayId() == 0) {
              Task orCreateRootHomeTask = displayArea.getOrCreateRootHomeTask();
              boolean z2 = true;
              if (CoreRune.MW_MULTI_SPLIT && displayArea.isMultiSplitActive()) {
                if (task2.inSplitScreenWindowingMode()
                    && orCreateRootHomeTask != null
                    && orCreateRootHomeTask.getRootTask().equals(task2.getRootTask())) {
                  this.mAtm.deferWindowLayout();
                  try {
                    orCreateRootHomeTask
                        .getRootTask()
                        .positionChildAt(
                            orCreateRootHomeTask.getRootTask().getChildCount() - 2,
                            orCreateRootHomeTask,
                            true);
                    orCreateRootHomeTask.getRootTask().moveTaskToBack(task2);
                  } finally {
                    this.mAtm.continueWindowLayout();
                  }
                } else {
                  Slog.w(TAG, "dismissSplitTask: failed, now multi-split");
                }
              } else {
                if (task2.getCreatedByOrganizerTask() != null
                    && task2.getCreatedByOrganizerTask().getTopChild() == task2) {
                  if (task2.getStageType() == 2) {
                    task = displayArea.getRootMainStageTask().getTopLeafTask();
                  } else if (task2.getStageType() == 1) {
                    task = displayArea.getRootSideStageTask().getTopLeafTask();
                  }
                  if (task != null && !task.mCreatedByOrganizer) {
                    final Task[] taskArr = new Task[1];
                    displayArea.forAllLeafTasks(
                        new Predicate() { // from class:
                                          // com.android.server.wm.MultiTaskingBinder$$ExternalSyntheticLambda0
                          @Override // java.util.function.Predicate
                          public final boolean test(Object obj) {
                            boolean lambda$dismissSplitTask$1;
                            lambda$dismissSplitTask$1 =
                                MultiTaskingBinder.lambda$dismissSplitTask$1(taskArr, (Task) obj);
                            return lambda$dismissSplitTask$1;
                          }
                        });
                    Task task3 = taskArr[0];
                    if (task3 != null && task3.compareTo((WindowContainer) task) > 0) {
                      z2 = false;
                    }
                    displayArea.onStageSplitScreenDismissed(task.getTopMostTask(), z2);
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                  }
                  Slog.w(TAG, "dismissSplitTask: failed, couldn't found opposite task.");
                }
                Slog.w(TAG, "dismissSplitTask: failed, controlpanel is not top child");
              }
              WindowManagerService.resetPriorityAfterLockedSection();
            }
            WindowManagerService.resetPriorityAfterLockedSection();
          }
          Slog.w(TAG, "dismissSplitTask: failed, " + iBinder);
          WindowManagerService.resetPriorityAfterLockedSection();
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public static /* synthetic */ boolean lambda$dismissSplitTask$1(Task[] taskArr, Task task) {
    if (task.inMultiWindowMode() || task.topRunningActivity() == null) {
      return false;
    }
    taskArr[0] = task;
    return true;
  }

  public boolean supportsMultiWindow(IBinder iBinder) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      synchronized (this.mAtm.mGlobalLockWithoutBoost) {
        ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
        Task task = isInRootTaskLocked != null ? isInRootTaskLocked.getTask() : null;
        if (task != null && task.getDisplayArea() != null) {
          return task.supportsMultiWindowInDisplayArea(task.getTaskDisplayArea());
        }
        Slog.w(TAG, "supportsMultiWindow: cannot find task, token=" + iBinder);
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return false;
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public int getResizeMode(ActivityInfo activityInfo) {
    return this.mAtm.mMwSupportPolicyController.getResizeMode(activityInfo);
  }

  public boolean isAllowedMultiWindowPackage(String str) {
    return this.mAtm.mMwSupportPolicyController.isAllowListApp(str);
  }

  public List getAllowedMultiWindowPackageList() {
    return this.mAtm.mMwSupportPolicyController.getAllowAppList();
  }

  public boolean isMultiWindowBlockListApp(String str) {
    return this.mAtm.mMwSupportPolicyController.isBlocklistApp(str);
  }

  public StringParceledListSlice getMultiWindowBlockListApp() {
    return new StringParceledListSlice(this.mAtm.mMwSupportPolicyController.getBlocklistAppList());
  }

  public SurfaceFreezerSnapshot getSurfaceFreezerSnapshot(int i) {
    this.mAtm.mAmInternal.enforceCallingPermission(
        "android.permission.READ_FRAME_BUFFER", "getSurfaceFreezerSnapshot");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      return this.mAtm.mNaturalSwitchingController.getSurfaceFreezerSnapshot(i);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public boolean startNaturalSwitching(IBinder iBinder, IBinder iBinder2) {
    boolean startNaturalSwitching;
    ActivityTaskManagerService.enforceTaskPermission("startNaturalSwitching()");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          startNaturalSwitching =
              this.mAtm.mNaturalSwitchingController.startNaturalSwitching(iBinder, iBinder2);
        } finally {
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
      return startNaturalSwitching;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void finishNaturalSwitching() {
    ActivityTaskManagerService.enforceTaskPermission("finishNaturalSwitching()");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          this.mAtm.mNaturalSwitchingController.finishNaturalSwitching();
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public boolean preventNaturalSwitching(int i) {
    boolean preventNaturalSwitching;
    ActivityTaskManagerService.enforceTaskPermission("preventNaturalSwitching()");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          preventNaturalSwitching =
              this.mAtm.mNaturalSwitchingController.preventNaturalSwitching(i);
        } finally {
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
      return preventNaturalSwitching;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  /* JADX WARN: Multi-variable type inference failed */
  public void rotateDexCompatTask(IBinder iBinder) {
    enforceNotIsolatedCaller("rotateDexCompatTask");
    WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
          ActivityRecord activityRecord =
              ((WindowContainer.RemoteToken) iBinder).getContainer().asTask().topRunningActivity();
          if (activityRecord == null) {
            Slog.w(TAG, "rotateDexCompatTask: cannot find ActivityRecord, token=" + iBinder);
            WindowManagerService.resetPriorityAfterLockedSection();
            return;
          }
          this.mAtm.mDexCompatController.rotateDexCompatTaskLocked(activityRecord);
          WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
          Binder.restoreCallingIdentity(clearCallingIdentity);
        }
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }

  public List getSplitActivityAllowPackages() {
    return List.of();
  }

  public boolean getEmbedActivityPackageEnabled(String str, int i) {
    if (!CoreRune.MW_EMBED_ACTIVITY_PACKAGE_ENABLED) {
      return true;
    }
    if (i != UserHandle.getCallingUserId()) {
      this.mAtm.mAmInternal.enforceCallingPermission(
          "android.permission.INTERACT_ACROSS_USERS", "getEmbedActivityPackageEnabled");
    }
    if (TextUtils.isEmpty(str)) {
      throw new IllegalArgumentException("getEmbedActivityPackageEnabled, packageName=" + str);
    }
    return this.mAtm.mMultiTaskingController.getEmbedActivityPackageEnabled(str, i);
  }

  public void setEmbedActivityPackageEnabled(String str, boolean z, int i) {
    if (CoreRune.MW_EMBED_ACTIVITY_PACKAGE_ENABLED) {
      if (i != UserHandle.getCallingUserId()) {
        this.mAtm.mAmInternal.enforceCallingPermission(
            "android.permission.INTERACT_ACROSS_USERS", "setEmbedActivityPackageEnabled");
      }
      if (TextUtils.isEmpty(str)) {
        throw new IllegalArgumentException("setEmbedActivityPackageEnabled, packageName=" + str);
      }
      this.mAtm.mMultiTaskingController.setEmbedActivityPackageEnabled(str, z, i);
    }
  }

  public List getSupportEmbedActivityPackages() {
    return this.mAtm.mMultiTaskingController.getSupportEmbedActivityPackages();
  }

  /* JADX WARN: Finally extract failed */
  public void toggleFreeformWindowingModeForDex(WindowContainerToken windowContainerToken) {
    ActivityTaskManagerService.enforceTaskPermission("toggleFreeformWindowingModeForDex");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          Task asTask =
              ((WindowContainer.RemoteToken) windowContainerToken.asBinder())
                  .getContainer()
                  .asTask();
          if (asTask == null) {
            Slog.w(TAG, "toggleFreeformWindowingModeForDex: task is null");
          } else {
            ActivityRecord activityRecord = asTask.topRunningActivity();
            if (activityRecord == null) {
              Slog.w(
                  TAG,
                  "toggleFreeformWindowingModeForDex: cannot find ActivityRecord, token= "
                      + windowContainerToken);
            } else {
              this.mAtm.mActivityClientController.toggleFreeformWindowingMode(activityRecord.token);
              WindowManagerService.resetPriorityAfterLockedSection();
              return;
            }
          }
          WindowManagerService.resetPriorityAfterLockedSection();
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public boolean exitMultiWindow(IBinder iBinder, boolean z) {
    if (z) {
      ActivityTaskManagerService.enforceTaskPermission("exitMultiWindow");
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      return this.mAtm.mMultiTaskingController.exitMultiWindow(iBinder, true, z);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public int getMultiWindowModeStates(int i) {
    return this.mAtm.mMultiTaskingController.getMultiWindowModeStates(i);
  }

  public void setCornerGestureEnabledWithSettings(final boolean z) {
    ActivityTaskManagerService.enforceTaskPermission("setCornerGestureEnabledWithSettings");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      this.mAtm.f1734mH.post(
          new Runnable() { // from class:
                           // com.android.server.wm.MultiTaskingBinder$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
              MultiTaskingBinder.this.lambda$setCornerGestureEnabledWithSettings$2(z);
            }
          });
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$setCornerGestureEnabledWithSettings$2(boolean z) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        if (MultiWindowCoreState.MW_FREEFORM_CORNER_GESTURE_ENABLED == z) {
          WindowManagerService.resetPriorityAfterLockedSection();
        } else {
          this.mAtm.mMultiWindowEnableController.setCornerGestureEnabled(z);
          WindowManagerService.resetPriorityAfterLockedSection();
        }
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }

  public boolean isValidCornerGesture(MotionEvent motionEvent) {
    boolean isValidCornerGesture;
    ActivityTaskManagerService.enforceTaskPermission("isValidCornerGesture");
    synchronized (this.mAtm.mGlobalLockWithoutBoost) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        isValidCornerGesture =
            this.mAtm
                .mWindowManager
                .getDefaultDisplayContentLocked()
                .isValidCornerGesture(motionEvent);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
    return isValidCornerGesture;
  }

  public boolean removeFocusedTask(int i) {
    ActivityTaskManagerService.enforceTaskPermission("removeFocusedTask");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      return this.mAtm.mMultiTaskingController.removeFocusedTask(i);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public ParceledListSlice getTaskInfoFromPackageName(String str) {
    ActivityTaskManagerService.enforceTaskPermission("getTaskInfoFromPackageName");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      return this.mAtm.mMultiTaskingController.getTaskInfoFromPackageName(str);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void setStayFocusActivityEnabled(boolean z) {
    this.mAtm.mMultiStarController.checkMultiStarPackageAndPermission(
        "setStayFocusActivityEnabled");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock globalLock = this.mAtm.getGlobalLock();
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (globalLock) {
        try {
          if (MultiWindowCoreState.MW_MULTISTAR_STAY_FOCUS_ACTIVITY_DYNAMIC_ENABLED != z) {
            this.mAtm.mMultiWindowEnableController.setStayFocusAndTopResumedActivityEnabled(z, z);
          }
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void setStayFocusAndTopResumedActivityEnabled(boolean z, boolean z2) {
    this.mAtm.mMultiStarController.checkMultiStarPackageAndPermission(
        "setStayFocusAndTopResumedActivityEnabled");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock globalLock = this.mAtm.getGlobalLock();
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (globalLock) {
        try {
          boolean z3 = true;
          boolean z4 = z != MultiWindowCoreState.MW_MULTISTAR_STAY_FOCUS_ACTIVITY_DYNAMIC_ENABLED;
          if (z2 == MultiWindowCoreState.MW_MULTISTAR_STAY_TOP_RESUMED_ACTIVITY_DYNAMIC_ENABLED) {
            z3 = false;
          }
          if (z4 || z3) {
            this.mAtm.mMultiWindowEnableController.setStayFocusAndTopResumedActivityEnabled(z, z2);
          }
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void setSplitImmersiveMode(boolean z) {
    ActivityTaskManagerService.enforceTaskPermission("setSplitImmersiveMode");
    synchronized (this.mAtm.mGlobalLockWithoutBoost) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        this.mAtm.mMultiWindowEnableController.setSplitImmersiveModeLocked(z);
        this.mAtm.mWindowManager.getDefaultDisplayContentLocked().setLayoutNeeded();
        this.mAtm.mWindowManager.requestTraversal();
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
  }

  public boolean isSplitImmersiveModeEnabled() {
    boolean isSplitImmersiveModeEnabledLocked;
    synchronized (this.mAtm.mGlobalLockWithoutBoost) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        isSplitImmersiveModeEnabledLocked =
            this.mAtm.mMultiWindowEnableController.isSplitImmersiveModeEnabledLocked();
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
    return isSplitImmersiveModeEnabledLocked;
  }

  public void setNaviStarSplitImmersiveMode(boolean z) {
    this.mAtm.mMultiStarController.checkMultiStarPackageAndPermission(
        "setNaviBarImmersiveModeLocked",
        new ArrayList(Arrays.asList("com.samsung.systemui.navillera")));
    synchronized (this.mAtm.mGlobalLockWithoutBoost) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        if (MultiWindowCoreState.MW_NAVISTAR_SPLIT_IMMERSIVE_MODE_ENABLED != z) {
          this.mAtm.mMultiWindowEnableController.setNaviStarImmersiveSplitModeLocked(z);
          this.mAtm.mWindowManager.getDefaultDisplayContentLocked().setLayoutNeeded();
          this.mAtm.mWindowManager.requestTraversal();
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
  }

  public boolean hasMinimizedToggleTasks() {
    boolean hasMinimizedToggleTasksLocked;
    WindowManagerGlobalLock globalLock = this.mAtm.getGlobalLock();
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (globalLock) {
      try {
        hasMinimizedToggleTasksLocked = this.mAtm.mDexController.hasMinimizedToggleTasksLocked();
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
    return hasMinimizedToggleTasksLocked;
  }

  public int getMultiSplitFlags() {
    int multiSplitFlags;
    ActivityTaskManagerService.enforceTaskPermission("getMultiSplitFlags");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          multiSplitFlags = this.mAtm.mMultiTaskingController.getMultiSplitFlags();
        } finally {
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
      return multiSplitFlags;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void registerDexSnappingCallback(IDexSnappingCallback iDexSnappingCallback) {
    this.mAtm.mAmInternal.enforceCallingPermission(
        "android.permission.REAL_GET_TASKS", "registerDexSnappingCallback()");
    this.mAtm.mDexController.registerDexSnappingCallback(iDexSnappingCallback);
  }

  public void unregisterDexSnappingCallback(IDexSnappingCallback iDexSnappingCallback) {
    this.mAtm.mDexController.unregisterDexSnappingCallback(iDexSnappingCallback);
  }

  public void scheduleNotifyDexSnappingCallback(int i, Rect rect) {
    this.mAtm.mDexController.scheduleNotifyDexSnappingCallback(i, rect);
  }

  public void initDockingBounds(Rect rect, Rect rect2, int i) {
    ActivityTaskManagerService.enforceTaskPermission("initDockingBounds");
    synchronized (this.mAtm.mGlobalLockWithoutBoost) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        this.mAtm.mDexDockingController.initDockingBounds(rect, rect2, i);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
  }

  public void setCandidateTask(int i) {
    ActivityTaskManagerService.enforceTaskPermission("setCandidateTask");
    synchronized (this.mAtm.mGlobalLockWithoutBoost) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        this.mAtm.mDexDockingController.setCandidateTask(
            this.mAtm.mRootWindowContainer.anyTaskForId(i));
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
  }

  public int calculateMaxWidth(int i, int i2, int i3) {
    int calculateMaxWidth;
    ActivityTaskManagerService.enforceTaskPermission("calculateMaxWidth");
    synchronized (this.mAtm.mGlobalLockWithoutBoost) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        calculateMaxWidth = this.mAtm.mDexDockingController.calculateMaxWidth(i, i2, i3);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
    return calculateMaxWidth;
  }

  public void resizeOtherTaskIfNeeded(int i, Rect rect) {
    ActivityTaskManagerService.enforceTaskPermission("resizeOtherTaskIfNeeded");
    synchronized (this.mAtm.mGlobalLockWithoutBoost) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        this.mAtm.mDexDockingController.resizeOtherTaskIfNeeded(
            this.mAtm.mRootWindowContainer.anyTaskForId(i), rect);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
  }

  public void clearAllDockingTasks(String str) {
    ActivityTaskManagerService.enforceTaskPermission("clearAllDockingTasks");
    synchronized (this.mAtm.mGlobalLockWithoutBoost) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        this.mAtm.mDexDockingController.clearAllDockingTasks(str);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
  }

  public void toggleFreeformForDexCompatApp(int i) {
    ActivityTaskManagerService.enforceTaskPermission("toggleFreeformForDexCompatApp");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          Task anyTaskForId = this.mAtm.mRootWindowContainer.anyTaskForId(i, 0);
          if (anyTaskForId == null) {
            Slog.w(TAG, "toggleFreeformForDexCompatApp : invalid task");
            WindowManagerService.resetPriorityAfterLockedSection();
          } else {
            this.mAtm.mDexCompatController.toggleFreeformForDexCompatApp(anyTaskForId);
            WindowManagerService.resetPriorityAfterLockedSection();
          }
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public boolean supportMultiSplitAppMinimumSize() {
    boolean supportMultiSplitAppMinimumSizeLocked;
    if (!CoreRune.MW_MULTI_SPLIT_ENSURE_APP_SIZE) {
      return false;
    }
    synchronized (this.mAtm.mGlobalLockWithoutBoost) {
      supportMultiSplitAppMinimumSizeLocked =
          this.mAtm.mMultiTaskingController.supportMultiSplitAppMinimumSizeLocked();
    }
    return supportMultiSplitAppMinimumSizeLocked;
  }

  public void updateMultiSplitAppMinimumSize() {
    if (CoreRune.MW_MULTI_SPLIT_ENSURE_APP_SIZE) {
      ActivityTaskManagerService.enforceTaskPermission("updateMultiSplitAppMinimumSize");
      synchronized (this.mAtm.mGlobalLockWithoutBoost) {
        this.mAtm.mMultiTaskingController.updateMultiSplitAppMinimumSizeLocked();
      }
    }
  }

  public boolean toggleFreeformWindowingMode() {
    boolean z;
    this.mAtm.mMultiStarController.checkMultiStarPackageAndPermission(
        "toggleFreeformWindowingMode",
        new ArrayList(Arrays.asList("com.samsung.android.sidegesturepad")));
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      synchronized (this.mAtm.mGlobalLockWithoutBoost) {
        z = this.mAtm.mMultiStarController.toggleFreeformWindowingMode();
      }
      return z;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void saveFreeformBounds(int i) {
    ActivityTaskManagerService.enforceTaskPermission("saveFreeformBounds");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      synchronized (this.mAtm.mGlobalLockWithoutBoost) {
        Task anyTaskForId = this.mAtm.mRootWindowContainer.anyTaskForId(i, 0);
        if (anyTaskForId == null) {
          Slog.w(TAG, "saveFreeformBounds : invalid task");
        } else {
          anyTaskForId.saveFreeformBoundsIfNeeded();
        }
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public boolean isVisibleTaskInDexDisplay(PendingIntent pendingIntent) {
    boolean isVisibleTaskInDexDisplay;
    ActivityTaskManagerService.enforceTaskPermission("isTaskInDexDisplay");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          isVisibleTaskInDexDisplay =
              this.mAtm.mMultiTaskingController.isVisibleTaskInDexDisplay(pendingIntent);
        } finally {
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
      return isVisibleTaskInDexDisplay;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public boolean isVisibleTaskByTaskIdInDexDisplay(int i) {
    boolean isVisibleTaskByTaskIdInDexDisplay;
    ActivityTaskManagerService.enforceTaskPermission("isVisibleTaskByTaskIdInDexDisplay");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          isVisibleTaskByTaskIdInDexDisplay =
              this.mAtm.mMultiTaskingController.isVisibleTaskByTaskIdInDexDisplay(i);
        } finally {
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
      return isVisibleTaskByTaskIdInDexDisplay;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public boolean shouldDeferEnterSplit(List list, List list2) {
    boolean shouldDeferEnterSplit;
    ActivityTaskManagerService.enforceTaskPermission("shouldDeferEnterSplit");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          shouldDeferEnterSplit =
              this.mAtm.mMultiTaskingController.shouldDeferEnterSplit(list, list2);
        } finally {
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
      return shouldDeferEnterSplit;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void registerDexTransientDelayListener(
      IDexTransientCaptionDelayListener iDexTransientCaptionDelayListener) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      this.mAtm.mDexController.registerDexTransientDelayListener(iDexTransientCaptionDelayListener);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }
}
