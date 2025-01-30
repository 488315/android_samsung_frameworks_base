package com.android.server.wm;

import android.app.ActivityManager;
import android.app.TaskInfo;
import android.app.WindowConfiguration;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ParceledListSlice;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import android.view.SurfaceControl;
import android.window.ITaskOrganizer;
import android.window.ITaskOrganizerController;
import android.window.StartingWindowInfo;
import android.window.StartingWindowRemovalInfo;
import android.window.TaskAppearedInfo;
import android.window.TaskSnapshot;
import android.window.WindowContainerToken;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.jobs.XmlUtils;
import com.samsung.android.multiwindow.TaskOrganizerInfo;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class TaskOrganizerController extends ITaskOrganizerController.Stub {
  public Consumer mDeferTaskOrgCallbacksConsumer;
  public final WindowManagerGlobalLock mGlobalLock;
  public final ActivityTaskManagerService mService;
  public final ArrayDeque mTaskOrganizers = new ArrayDeque();
  public final ArrayMap mTaskOrganizerStates = new ArrayMap();
  public final HashSet mInterceptBackPressedOnRootTasks = new HashSet();

  class DeathRecipient implements IBinder.DeathRecipient {
    public ITaskOrganizer mTaskOrganizer;

    public DeathRecipient(ITaskOrganizer iTaskOrganizer) {
      this.mTaskOrganizer = iTaskOrganizer;
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
      WindowManagerGlobalLock windowManagerGlobalLock = TaskOrganizerController.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          TaskOrganizerState taskOrganizerState =
              (TaskOrganizerState)
                  TaskOrganizerController.this.mTaskOrganizerStates.get(
                      this.mTaskOrganizer.asBinder());
          if (taskOrganizerState != null) {
            taskOrganizerState.dispose();
          }
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }
  }

  public class TaskOrganizerCallbacks {
    public final Consumer mDeferTaskOrgCallbacksConsumer;
    public final ITaskOrganizer mTaskOrganizer;

    public TaskOrganizerCallbacks(ITaskOrganizer iTaskOrganizer, Consumer consumer) {
      this.mDeferTaskOrgCallbacksConsumer = consumer;
      this.mTaskOrganizer = iTaskOrganizer;
    }

    public IBinder getBinder() {
      return this.mTaskOrganizer.asBinder();
    }

    public SurfaceControl prepareLeash(Task task, String str) {
      return new SurfaceControl(task.getSurfaceControl(), str);
    }

    public void onTaskAppeared(Task task) {
      if (ProtoLogCache.WM_DEBUG_WINDOW_ORGANIZER_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER,
            1918448345,
            1,
            (String) null,
            new Object[] {Long.valueOf(task.mTaskId)});
      }
      try {
        this.mTaskOrganizer.onTaskAppeared(
            task.getTaskInfo(), prepareLeash(task, "TaskOrganizerController.onTaskAppeared"));
      } catch (RemoteException e) {
        Slog.e("TaskOrganizerController", "Exception sending onTaskAppeared callback", e);
      }
    }

    public void onTaskVanished(Task task) {
      if (ProtoLogCache.WM_DEBUG_WINDOW_ORGANIZER_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER,
            -1364754753,
            1,
            (String) null,
            new Object[] {Long.valueOf(task.mTaskId)});
      }
      try {
        this.mTaskOrganizer.onTaskVanished(task.getTaskInfo());
      } catch (RemoteException e) {
        Slog.e("TaskOrganizerController", "Exception sending onTaskVanished callback", e);
      }
    }

    public void onTaskInfoChanged(Task task, ActivityManager.RunningTaskInfo runningTaskInfo) {
      if (task.mTaskAppearedSent) {
        if (ProtoLogCache.WM_DEBUG_WINDOW_ORGANIZER_enabled) {
          ProtoLogImpl.v(
              ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER,
              302969511,
              1,
              (String) null,
              new Object[] {Long.valueOf(task.mTaskId)});
        }
        if (task.isOrganized()) {
          try {
            this.mTaskOrganizer.onTaskInfoChanged(runningTaskInfo);
          } catch (RemoteException e) {
            Slog.e("TaskOrganizerController", "Exception sending onTaskInfoChanged callback", e);
          }
        }
      }
    }

    public void onBackPressedOnTaskRoot(Task task) {
      if (ProtoLogCache.WM_DEBUG_WINDOW_ORGANIZER_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER,
            -2049725903,
            1,
            (String) null,
            new Object[] {Long.valueOf(task.mTaskId)});
      }
      if (task.mTaskAppearedSent && task.isOrganized()) {
        try {
          this.mTaskOrganizer.onBackPressedOnTaskRoot(task.getTaskInfo());
        } catch (Exception e) {
          Slog.e(
              "TaskOrganizerController", "Exception sending onBackPressedOnTaskRoot callback", e);
        }
      }
    }

    public void requestAffordanceAnim(Task task, int i) {
      if (task.mTaskAppearedSent && task.isOrganized()) {
        try {
          this.mTaskOrganizer.requestAffordanceAnim(task.getTaskInfo(), i);
        } catch (Exception e) {
          Slog.e("TaskOrganizerController", "Exception sending onAffordanceAnim callback", e);
        }
      }
    }
  }

  public final class TaskOrganizerPendingEventsQueue {
    public String mLastCaller;
    public final TaskOrganizerState mOrganizerState;
    public ActivityManager.RunningTaskInfo mTmpTaskInfo;
    public final WeakHashMap mLastSentTaskInfos = new WeakHashMap();
    public final ArrayList mPendingTaskEvents = new ArrayList();

    public TaskOrganizerPendingEventsQueue(TaskOrganizerState taskOrganizerState) {
      this.mOrganizerState = taskOrganizerState;
    }

    public ArrayList getPendingEventList() {
      return this.mPendingTaskEvents;
    }

    public int numPendingTaskEvents() {
      return this.mPendingTaskEvents.size();
    }

    public void clearPendingTaskEvents() {
      this.mPendingTaskEvents.clear();
    }

    public void addPendingTaskEvent(PendingTaskEvent pendingTaskEvent) {
      this.mPendingTaskEvents.add(pendingTaskEvent);
    }

    public void removePendingTaskEvent(PendingTaskEvent pendingTaskEvent) {
      this.mPendingTaskEvents.remove(pendingTaskEvent);
    }

    public boolean removePendingTaskEvents(Task task) {
      boolean z = false;
      for (int size = this.mPendingTaskEvents.size() - 1; size >= 0; size--) {
        PendingTaskEvent pendingTaskEvent = (PendingTaskEvent) this.mPendingTaskEvents.get(size);
        if (task.mTaskId == pendingTaskEvent.mTask.mTaskId) {
          this.mPendingTaskEvents.remove(size);
          if (pendingTaskEvent.mEventType == 0) {
            z = true;
          }
        }
      }
      return z;
    }

    public final PendingTaskEvent getPendingTaskEvent(Task task, int i) {
      for (int size = this.mPendingTaskEvents.size() - 1; size >= 0; size--) {
        PendingTaskEvent pendingTaskEvent = (PendingTaskEvent) this.mPendingTaskEvents.get(size);
        if (task.mTaskId == pendingTaskEvent.mTask.mTaskId && i == pendingTaskEvent.mEventType) {
          return pendingTaskEvent;
        }
      }
      return null;
    }

    public PendingTaskEvent getPendingLifecycleTaskEvent(Task task) {
      for (int size = this.mPendingTaskEvents.size() - 1; size >= 0; size--) {
        PendingTaskEvent pendingTaskEvent = (PendingTaskEvent) this.mPendingTaskEvents.get(size);
        if (task.mTaskId == pendingTaskEvent.mTask.mTaskId && pendingTaskEvent.isLifecycleEvent()) {
          return pendingTaskEvent;
        }
      }
      return null;
    }

    public void dispatchPendingEvents() {
      if (this.mPendingTaskEvents.isEmpty()) {
        return;
      }
      int size = this.mPendingTaskEvents.size();
      for (int i = 0; i < size; i++) {
        dispatchPendingEvent((PendingTaskEvent) this.mPendingTaskEvents.get(i));
      }
      this.mPendingTaskEvents.clear();
    }

    public final void dispatchPendingEvent(PendingTaskEvent pendingTaskEvent) {
      Task task = pendingTaskEvent.mTask;
      int i = pendingTaskEvent.mEventType;
      if (i == 0) {
        if (task.taskAppearedReady()) {
          this.mOrganizerState.mOrganizer.onTaskAppeared(task);
        }
      } else if (i == 1) {
        this.mOrganizerState.mOrganizer.onTaskVanished(task);
        this.mLastSentTaskInfos.remove(task);
      } else if (i == 2) {
        dispatchTaskInfoChanged(task, pendingTaskEvent.mForce);
      } else if (i == 3) {
        this.mOrganizerState.mOrganizer.onBackPressedOnTaskRoot(task);
      } else {
        if (i != 4) {
          return;
        }
        this.mOrganizerState.mOrganizer.requestAffordanceAnim(task, pendingTaskEvent.mGestureFrom);
      }
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:29:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void dispatchTaskInfoChanged(Task task, boolean z) {
      boolean z2;
      ActivityManager.RunningTaskInfo runningTaskInfo;
      ActivityManager.RunningTaskInfo runningTaskInfo2 =
          (ActivityManager.RunningTaskInfo) this.mLastSentTaskInfos.get(task);
      if (this.mTmpTaskInfo == null) {
        this.mTmpTaskInfo = new ActivityManager.RunningTaskInfo();
      }
      try {
        this.mTmpTaskInfo.configuration.unset();
        task.fillTaskInfo(this.mTmpTaskInfo);
        if (this.mTmpTaskInfo.equalsForTaskOrganizer(runningTaskInfo2)
            && WindowOrganizerController.configurationsAreEqualForOrganizer(
                this.mTmpTaskInfo.configuration, runningTaskInfo2.configuration)) {
          z2 = false;
          if (!z2 || z) {
            runningTaskInfo = this.mTmpTaskInfo;
            if (task.inSplitScreenWindowingMode()
                && task.isLeafTask()
                && task.getParent() != null
                && task.getParent().getTopMostTask() == task) {
              runningTaskInfo.isTopTaskInStage = true;
            }
            this.mLastSentTaskInfos.put(task, this.mTmpTaskInfo);
            this.mTmpTaskInfo = null;
            this.mLastCaller = Debug.getCallers(10, "    ");
            if (task.isOrganized()) {
              return;
            }
            this.mOrganizerState.mOrganizer.onTaskInfoChanged(task, runningTaskInfo);
            return;
          }
          return;
        }
        z2 = true;
        if (z2) {}
        runningTaskInfo = this.mTmpTaskInfo;
        if (task.inSplitScreenWindowingMode()) {
          runningTaskInfo.isTopTaskInStage = true;
        }
        this.mLastSentTaskInfos.put(task, this.mTmpTaskInfo);
        this.mTmpTaskInfo = null;
        this.mLastCaller = Debug.getCallers(10, "    ");
        if (task.isOrganized()) {}
      } catch (NullPointerException e) {
        throw new NullPointerException(
            e.getMessage() + "\n mLastCaller:\n" + this.mLastCaller + "mCurCaller:");
      }
    }
  }

  class TaskOrganizerState {
    public final DeathRecipient mDeathRecipient;
    public final ArrayList mOrganizedTasks = new ArrayList();
    public final TaskOrganizerCallbacks mOrganizer;
    public final TaskOrganizerPendingEventsQueue mPendingEventsQueue;
    public final int mUid;

    public TaskOrganizerState(ITaskOrganizer iTaskOrganizer, int i) {
      Consumer consumer;
      if (TaskOrganizerController.this.mDeferTaskOrgCallbacksConsumer != null) {
        consumer = TaskOrganizerController.this.mDeferTaskOrgCallbacksConsumer;
      } else {
        final WindowAnimator windowAnimator =
            TaskOrganizerController.this.mService.mWindowManager.mAnimator;
        Objects.requireNonNull(windowAnimator);
        consumer =
            new Consumer() { // from class:
                             // com.android.server.wm.TaskOrganizerController$TaskOrganizerState$$ExternalSyntheticLambda0
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                WindowAnimator.this.addAfterPrepareSurfacesRunnable((Runnable) obj);
              }
            };
      }
      this.mOrganizer = new TaskOrganizerCallbacks(iTaskOrganizer, consumer);
      DeathRecipient deathRecipient =
          TaskOrganizerController.this.new DeathRecipient(iTaskOrganizer);
      this.mDeathRecipient = deathRecipient;
      this.mPendingEventsQueue = new TaskOrganizerPendingEventsQueue(this);
      try {
        iTaskOrganizer.asBinder().linkToDeath(deathRecipient, 0);
      } catch (RemoteException unused) {
        Slog.e("TaskOrganizerController", "TaskOrganizer failed to register death recipient");
      }
      this.mUid = i;
    }

    public DeathRecipient getDeathRecipient() {
      return this.mDeathRecipient;
    }

    public TaskOrganizerPendingEventsQueue getPendingEventsQueue() {
      return this.mPendingEventsQueue;
    }

    public SurfaceControl addTaskWithoutCallback(Task task, String str) {
      task.mTaskAppearedSent = true;
      if (!this.mOrganizedTasks.contains(task)) {
        this.mOrganizedTasks.add(task);
      }
      return this.mOrganizer.prepareLeash(task, str);
    }

    public final boolean addTask(Task task) {
      if (task.mTaskAppearedSent) {
        return false;
      }
      if (!this.mOrganizedTasks.contains(task)) {
        this.mOrganizedTasks.add(task);
      }
      if (!task.taskAppearedReady()) {
        return false;
      }
      task.mTaskAppearedSent = true;
      return true;
    }

    public final boolean removeTask(Task task, boolean z) {
      this.mOrganizedTasks.remove(task);
      TaskOrganizerController.this.mInterceptBackPressedOnRootTasks.remove(
          Integer.valueOf(task.mTaskId));
      boolean z2 = task.mTaskAppearedSent;
      if (z2) {
        if (task.getSurfaceControl() != null) {
          if (!task.canMigrateToNewSurfaceControl()) {
            Slog.w("TaskOrganizerController", "removeTask: skip to migrate, t=" + task);
          } else {
            task.migrateToNewSurfaceControl(task.getPendingTransaction());
          }
        }
        task.mTaskAppearedSent = false;
      }
      if (z) {
        TaskOrganizerController.this.mService.removeTask(task.mTaskId);
      }
      return z2;
    }

    public void dispose() {
      TaskOrganizerController.this.mTaskOrganizers.remove(this.mOrganizer.mTaskOrganizer);
      while (!this.mOrganizedTasks.isEmpty()) {
        Task task = (Task) this.mOrganizedTasks.get(0);
        if (task.mCreatedByOrganizer) {
          task.removeImmediately();
        } else {
          task.updateTaskOrganizerState();
        }
        if (this.mOrganizedTasks.contains(task)
            && removeTask(task, task.mRemoveWithTaskOrganizer)) {
          TaskOrganizerController.this.onTaskVanishedInternal(this, task);
        }
        if (TaskOrganizerController.this
                .mService
                .getTransitionController()
                .isShellTransitionsEnabled()
            && task.mTaskOrganizer != null
            && task.getSurfaceControl() != null) {
          task.getSyncTransaction().show(task.getSurfaceControl());
        }
      }
      this.mPendingEventsQueue.clearPendingTaskEvents();
      TaskOrganizerController.this.mTaskOrganizerStates.remove(this.mOrganizer.getBinder());
    }

    public void unlinkDeath() {
      this.mOrganizer.getBinder().unlinkToDeath(this.mDeathRecipient, 0);
    }
  }

  public class PendingTaskEvent {
    public final int mEventType;
    public boolean mForce;
    public int mGestureFrom;
    public final Task mTask;
    public final ITaskOrganizer mTaskOrg;

    public PendingTaskEvent(Task task, int i) {
      this(task, task.mTaskOrganizer, i);
    }

    public PendingTaskEvent(Task task, ITaskOrganizer iTaskOrganizer, int i) {
      this.mTask = task;
      this.mTaskOrg = iTaskOrganizer;
      this.mEventType = i;
    }

    public boolean isLifecycleEvent() {
      int i = this.mEventType;
      return i == 0 || i == 1 || i == 2;
    }
  }

  public TaskOrganizerController(ActivityTaskManagerService activityTaskManagerService) {
    this.mService = activityTaskManagerService;
    this.mGlobalLock = activityTaskManagerService.mGlobalLock;
  }

  public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
    try {
      return super.onTransact(i, parcel, parcel2, i2);
    } catch (RuntimeException e) {
      throw ActivityTaskManagerService.logAndRethrowRuntimeExceptionOnTransact(
          "TaskOrganizerController", e);
    }
  }

  public void setDeferTaskOrgCallbacksConsumer(Consumer consumer) {
    this.mDeferTaskOrgCallbacksConsumer = consumer;
  }

  public ParceledListSlice registerTaskOrganizer(final ITaskOrganizer iTaskOrganizer) {
    ActivityTaskManagerService.enforceTaskPermission("registerTaskOrganizer()");
    final int callingUid = Binder.getCallingUid();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      final ArrayList arrayList = new ArrayList();
      Runnable runnable =
          new Runnable() { // from class:
                           // com.android.server.wm.TaskOrganizerController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              TaskOrganizerController.this.lambda$registerTaskOrganizer$1(
                  iTaskOrganizer, callingUid, arrayList);
            }
          };
      if (this.mService.getTransitionController().isShellTransitionsEnabled()) {
        this.mService.getTransitionController().mRunningLock.runWhenIdle(1000L, runnable);
      } else {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
          try {
            runnable.run();
          } catch (Throwable th) {
            WindowManagerService.resetPriorityAfterLockedSection();
            throw th;
          }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
      }
      return new ParceledListSlice(arrayList);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$registerTaskOrganizer$1(
      ITaskOrganizer iTaskOrganizer, int i, final ArrayList arrayList) {
    if (ProtoLogCache.WM_DEBUG_WINDOW_ORGANIZER_enabled) {
      ProtoLogImpl.v(
          ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER,
          -1792633344,
          4,
          (String) null,
          new Object[] {String.valueOf(iTaskOrganizer.asBinder()), Long.valueOf(i)});
    }
    if (!this.mTaskOrganizerStates.containsKey(iTaskOrganizer.asBinder())) {
      this.mTaskOrganizers.add(iTaskOrganizer);
      this.mTaskOrganizerStates.put(
          iTaskOrganizer.asBinder(), new TaskOrganizerState(iTaskOrganizer, i));
    }
    final TaskOrganizerState taskOrganizerState =
        (TaskOrganizerState) this.mTaskOrganizerStates.get(iTaskOrganizer.asBinder());
    this.mService.mRootWindowContainer.forAllTasks(
        new Consumer() { // from class:
                         // com.android.server.wm.TaskOrganizerController$$ExternalSyntheticLambda3
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            TaskOrganizerController.lambda$registerTaskOrganizer$0(
                TaskOrganizerController.TaskOrganizerState.this, arrayList, (Task) obj);
          }
        });
  }

  public static /* synthetic */ void lambda$registerTaskOrganizer$0(
      TaskOrganizerState taskOrganizerState, ArrayList arrayList, Task task) {
    boolean z = !task.mCreatedByOrganizer;
    task.updateTaskOrganizerState(z);
    if (task.isOrganized() && z) {
      arrayList.add(
          new TaskAppearedInfo(
              task.getTaskInfo(),
              taskOrganizerState.addTaskWithoutCallback(
                  task, "TaskOrganizerController.registerTaskOrganizer")));
    }
  }

  public void unregisterTaskOrganizer(final ITaskOrganizer iTaskOrganizer) {
    ActivityTaskManagerService.enforceTaskPermission("unregisterTaskOrganizer()");
    final int callingUid = Binder.getCallingUid();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      Runnable runnable =
          new Runnable() { // from class:
                           // com.android.server.wm.TaskOrganizerController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
              TaskOrganizerController.this.lambda$unregisterTaskOrganizer$2(
                  iTaskOrganizer, callingUid);
            }
          };
      if (this.mService.getTransitionController().isShellTransitionsEnabled()) {
        this.mService.getTransitionController().mRunningLock.runWhenIdle(1000L, runnable);
      } else {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
          try {
            runnable.run();
          } catch (Throwable th) {
            WindowManagerService.resetPriorityAfterLockedSection();
            throw th;
          }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$unregisterTaskOrganizer$2(
      ITaskOrganizer iTaskOrganizer, int i) {
    TaskOrganizerState taskOrganizerState =
        (TaskOrganizerState) this.mTaskOrganizerStates.get(iTaskOrganizer.asBinder());
    if (taskOrganizerState == null) {
      return;
    }
    if (ProtoLogCache.WM_DEBUG_WINDOW_ORGANIZER_enabled) {
      ProtoLogImpl.v(
          ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER,
          -951939129,
          4,
          (String) null,
          new Object[] {String.valueOf(iTaskOrganizer.asBinder()), Long.valueOf(i)});
    }
    taskOrganizerState.unlinkDeath();
    taskOrganizerState.dispose();
  }

  public ITaskOrganizer getTaskOrganizer() {
    return (ITaskOrganizer) this.mTaskOrganizers.peekLast();
  }

  public class StartingWindowAnimationAdaptor implements AnimationAdapter {
    public SurfaceControl mAnimationLeash;

    @Override // com.android.server.wm.AnimationAdapter
    public void dumpDebug(ProtoOutputStream protoOutputStream) {}

    @Override // com.android.server.wm.AnimationAdapter
    public long getDurationHint() {
      return 0L;
    }

    @Override // com.android.server.wm.AnimationAdapter
    public boolean getShowWallpaper() {
      return false;
    }

    @Override // com.android.server.wm.AnimationAdapter
    public long getStatusBarTransitionsStartTime() {
      return 0L;
    }

    @Override // com.android.server.wm.AnimationAdapter
    public void startAnimation(
        SurfaceControl surfaceControl,
        SurfaceControl.Transaction transaction,
        int i,
        SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback) {
      this.mAnimationLeash = surfaceControl;
    }

    @Override // com.android.server.wm.AnimationAdapter
    public void onAnimationCancelled(SurfaceControl surfaceControl) {
      if (this.mAnimationLeash == surfaceControl) {
        this.mAnimationLeash = null;
      }
    }

    @Override // com.android.server.wm.AnimationAdapter
    public void dump(PrintWriter printWriter, String str) {
      printWriter.print(str + "StartingWindowAnimationAdaptor mCapturedLeash=");
      printWriter.print(this.mAnimationLeash);
      printWriter.println();
    }
  }

  public static SurfaceControl applyStartingWindowAnimation(WindowState windowState) {
    Slog.d(
        "TaskOrganizerController",
        "applyStartingWindowAnimation, window=" + windowState + ", caller=" + Debug.getCallers(3));
    SurfaceControl.Transaction pendingTransaction = windowState.getPendingTransaction();
    Rect relativeFrame = windowState.getRelativeFrame();
    StartingWindowAnimationAdaptor startingWindowAnimationAdaptor =
        new StartingWindowAnimationAdaptor();
    windowState.startAnimation(pendingTransaction, startingWindowAnimationAdaptor, false, 128);
    SurfaceControl surfaceControl = startingWindowAnimationAdaptor.mAnimationLeash;
    if (surfaceControl == null) {
      Slog.e(
          "TaskOrganizerController",
          "Cannot start starting window animation, the window " + windowState + " was removed");
      return null;
    }
    pendingTransaction.setPosition(surfaceControl, relativeFrame.left, relativeFrame.top);
    return startingWindowAnimationAdaptor.mAnimationLeash;
  }

  public boolean addStartingWindow(
      Task task, ActivityRecord activityRecord, int i, TaskSnapshot taskSnapshot) {
    ITaskOrganizer iTaskOrganizer;
    if (task.getRootTask() == null
        || activityRecord.mStartingData == null
        || (iTaskOrganizer = (ITaskOrganizer) this.mTaskOrganizers.peekLast()) == null) {
      return false;
    }
    StartingWindowInfo startingWindowInfo = task.getStartingWindowInfo(activityRecord);
    if (i != 0) {
      startingWindowInfo.splashScreenThemeResId = i;
    }
    startingWindowInfo.taskSnapshot = taskSnapshot;
    startingWindowInfo.appToken = activityRecord.token;
    try {
      iTaskOrganizer.addStartingWindow(startingWindowInfo);
      return true;
    } catch (RemoteException e) {
      Slog.e("TaskOrganizerController", "Exception sending onTaskStart callback", e);
      return false;
    }
  }

  public void removeStartingWindow(Task task, boolean z) {
    ITaskOrganizer iTaskOrganizer;
    if (task.getRootTask() == null
        || (iTaskOrganizer = (ITaskOrganizer) this.mTaskOrganizers.peekLast()) == null) {
      return;
    }
    StartingWindowRemovalInfo startingWindowRemovalInfo = new StartingWindowRemovalInfo();
    startingWindowRemovalInfo.taskId = task.mTaskId;
    startingWindowRemovalInfo.playRevealAnimation =
        z && task.getDisplayContent() != null && task.getDisplayInfo().state == 2;
    boolean z2 = !task.inMultiWindowMode();
    ActivityRecord activityRecord = task.topActivityContainsStartingWindow();
    if (activityRecord != null) {
      DisplayContent displayContent = activityRecord.getDisplayContent();
      WindowState windowState = displayContent.mInputMethodWindow;
      if (activityRecord.isVisibleRequested()
          && windowState != null
          && displayContent.mayImeShowOnLaunchingActivity(activityRecord)
          && displayContent.isFixedRotationLaunchingApp(activityRecord)) {
        startingWindowRemovalInfo.deferRemoveForImeMode = 2;
      } else if (displayContent.mayImeShowOnLaunchingActivity(activityRecord)) {
        startingWindowRemovalInfo.deferRemoveForImeMode = 1;
      } else {
        startingWindowRemovalInfo.deferRemoveForImeMode = 0;
      }
      WindowState findMainWindow = activityRecord.findMainWindow(false);
      if (findMainWindow == null || findMainWindow.mRemoved) {
        startingWindowRemovalInfo.playRevealAnimation = false;
      } else if (startingWindowRemovalInfo.playRevealAnimation && z2) {
        startingWindowRemovalInfo.roundedCornerRadius =
            activityRecord.mLetterboxUiController.getRoundedCornersRadius(findMainWindow);
        startingWindowRemovalInfo.windowAnimationLeash =
            applyStartingWindowAnimation(findMainWindow);
        startingWindowRemovalInfo.mainFrame = findMainWindow.getRelativeFrame();
        WindowContainer animatingContainer = activityRecord.getAnimatingContainer();
        if (animatingContainer != null) {
          startingWindowRemovalInfo.duration = animatingContainer.getAnimation().getDurationHint();
        }
      }
    }
    try {
      iTaskOrganizer.removeStartingWindow(startingWindowRemovalInfo);
    } catch (RemoteException e) {
      Slog.e("TaskOrganizerController", "Exception sending onStartTaskFinished callback", e);
    }
  }

  public boolean copySplashScreenView(Task task) {
    ITaskOrganizer iTaskOrganizer;
    if (task.getRootTask() == null
        || (iTaskOrganizer = (ITaskOrganizer) this.mTaskOrganizers.peekLast()) == null) {
      return false;
    }
    try {
      iTaskOrganizer.copySplashScreenView(task.mTaskId);
      return true;
    } catch (RemoteException e) {
      Slog.e("TaskOrganizerController", "Exception sending copyStartingWindowView callback", e);
      return false;
    }
  }

  public boolean isSupportWindowlessStartingSurface() {
    return false;
  }

  public void onAppSplashScreenViewRemoved(Task task) {
    ITaskOrganizer iTaskOrganizer;
    if (task.getRootTask() == null
        || (iTaskOrganizer = (ITaskOrganizer) this.mTaskOrganizers.peekLast()) == null) {
      return;
    }
    try {
      iTaskOrganizer.onAppSplashScreenViewRemoved(task.mTaskId);
    } catch (RemoteException e) {
      Slog.e(
          "TaskOrganizerController", "Exception sending onAppSplashScreenViewRemoved callback", e);
    }
  }

  public void preloadSplashScreenAppIcon(
      ActivityInfo activityInfo, int i, Configuration configuration) {
    ITaskOrganizer iTaskOrganizer = (ITaskOrganizer) this.mTaskOrganizers.peekLast();
    if (iTaskOrganizer == null) {
      return;
    }
    try {
      iTaskOrganizer.preloadSplashScreenAppIcon(activityInfo, i, configuration);
    } catch (RemoteException e) {
      Slog.e("TaskOrganizerController", "Exception sending preloadSplashScreenAppInfo callback", e);
    }
  }

  public void onTaskAppeared(ITaskOrganizer iTaskOrganizer, Task task) {
    TaskOrganizerState taskOrganizerState =
        (TaskOrganizerState) this.mTaskOrganizerStates.get(iTaskOrganizer.asBinder());
    if (taskOrganizerState == null || !taskOrganizerState.addTask(task)) {
      return;
    }
    TaskOrganizerPendingEventsQueue taskOrganizerPendingEventsQueue =
        taskOrganizerState.mPendingEventsQueue;
    if (taskOrganizerPendingEventsQueue.getPendingTaskEvent(task, 0) == null) {
      taskOrganizerPendingEventsQueue.addPendingTaskEvent(new PendingTaskEvent(task, 0));
    }
  }

  public void onTaskVanished(ITaskOrganizer iTaskOrganizer, Task task) {
    TaskOrganizerState taskOrganizerState =
        (TaskOrganizerState) this.mTaskOrganizerStates.get(iTaskOrganizer.asBinder());
    if (taskOrganizerState == null
        || !taskOrganizerState.removeTask(task, task.mRemoveWithTaskOrganizer)) {
      return;
    }
    onTaskVanishedInternal(taskOrganizerState, task);
  }

  public final void onTaskVanishedInternal(TaskOrganizerState taskOrganizerState, Task task) {
    if (taskOrganizerState == null) {
      Slog.i(
          "TaskOrganizerController",
          "cannot send onTaskVanished because organizer state is not present for this organizer");
      return;
    }
    TaskOrganizerPendingEventsQueue taskOrganizerPendingEventsQueue =
        taskOrganizerState.mPendingEventsQueue;
    if (taskOrganizerPendingEventsQueue.removePendingTaskEvents(task)) {
      return;
    }
    taskOrganizerPendingEventsQueue.addPendingTaskEvent(
        new PendingTaskEvent(task, taskOrganizerState.mOrganizer.mTaskOrganizer, 1));
  }

  public void createRootTask(int i, int i2, IBinder iBinder, boolean z) {
    ActivityTaskManagerService.enforceTaskPermission("createRootTask()");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          DisplayContent displayContent = this.mService.mRootWindowContainer.getDisplayContent(i);
          if (displayContent == null) {
            if (ProtoLogCache.WM_DEBUG_WINDOW_ORGANIZER_enabled) {
              ProtoLogImpl.e(
                  ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER,
                  1396893178,
                  1,
                  (String) null,
                  new Object[] {Long.valueOf(i)});
            }
            WindowManagerService.resetPriorityAfterLockedSection();
          } else {
            createRootTask(displayContent, i2, iBinder, z);
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

  public void createStageRootTask(int i, int i2, int i3, IBinder iBinder) {
    ActivityTaskManagerService.enforceTaskPermission("createStageRootTask()");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          DisplayContent displayContent = this.mService.mRootWindowContainer.getDisplayContent(i);
          if (displayContent == null) {
            if (ProtoLogCache.WM_DEBUG_WINDOW_ORGANIZER_enabled) {
              ProtoLogImpl.e(
                  ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER,
                  1575635070,
                  1,
                  (String) null,
                  new Object[] {Long.valueOf(i)});
            }
            WindowManagerService.resetPriorityAfterLockedSection();
          } else {
            createRootTask(displayContent, i2, iBinder, false, i3);
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

  public Task createRootTask(DisplayContent displayContent, int i, IBinder iBinder) {
    return createRootTask(displayContent, i, iBinder, false, 0);
  }

  public Task createRootTask(DisplayContent displayContent, int i, IBinder iBinder, boolean z) {
    return createRootTask(displayContent, i, iBinder, z, 0);
  }

  public Task createRootTask(
      DisplayContent displayContent, int i, IBinder iBinder, boolean z, int i2) {
    if (ProtoLogCache.WM_DEBUG_WINDOW_ORGANIZER_enabled) {
      ProtoLogImpl.v(
          ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER,
          -1939861963,
          5,
          (String) null,
          new Object[] {Long.valueOf(displayContent.mDisplayId), Long.valueOf(i)});
    }
    Task build =
        new Task.Builder(this.mService)
            .setWindowingMode(i)
            .setIntent(new Intent())
            .setCreatedByOrganizer(true)
            .setDeferTaskAppear(true)
            .setLaunchCookie(iBinder)
            .setParent(displayContent.getDefaultTaskDisplayArea())
            .setRemoveWithTaskOrganizer(z)
            .setStageType(i2)
            .build();
    build.setDeferTaskAppear(false);
    return build;
  }

  /* JADX WARN: Finally extract failed */
  public boolean deleteRootTask(WindowContainerToken windowContainerToken) {
    ActivityTaskManagerService.enforceTaskPermission("deleteRootTask()");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          WindowContainer fromBinder = WindowContainer.fromBinder(windowContainerToken.asBinder());
          if (fromBinder != null) {
            Task asTask = fromBinder.asTask();
            if (asTask != null) {
              if (!asTask.mCreatedByOrganizer) {
                throw new IllegalArgumentException(
                    "Attempt to delete task not created by organizer task=" + asTask);
              }
              if (ProtoLogCache.WM_DEBUG_WINDOW_ORGANIZER_enabled) {
                ProtoLogImpl.v(
                    ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER,
                    -1895337367,
                    5,
                    (String) null,
                    new Object[] {
                      Long.valueOf(asTask.getDisplayId()), Long.valueOf(asTask.getWindowingMode())
                    });
              }
              asTask.remove(true, "deleteRootTask");
              WindowManagerService.resetPriorityAfterLockedSection();
              return true;
            }
          }
          WindowManagerService.resetPriorityAfterLockedSection();
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

  public void dispatchPendingEvents() {
    if (this.mService.mWindowManager.mWindowPlacerLocked.isLayoutDeferred()) {
      return;
    }
    for (int i = 0; i < this.mTaskOrganizerStates.size(); i++) {
      ((TaskOrganizerState) this.mTaskOrganizerStates.valueAt(i))
          .mPendingEventsQueue.dispatchPendingEvents();
    }
  }

  public void reportImeDrawnOnTask(Task task) {
    TaskOrganizerState taskOrganizerState =
        (TaskOrganizerState) this.mTaskOrganizerStates.get(task.mTaskOrganizer.asBinder());
    if (taskOrganizerState != null) {
      try {
        taskOrganizerState.mOrganizer.mTaskOrganizer.onImeDrawnOnTask(task.mTaskId);
      } catch (RemoteException e) {
        Slog.e("TaskOrganizerController", "Exception sending onImeDrawnOnTask callback", e);
      }
    }
  }

  public void onTaskInfoChanged(Task task, boolean z) {
    if (task.mTaskAppearedSent) {
      TaskOrganizerPendingEventsQueue taskOrganizerPendingEventsQueue =
          ((TaskOrganizerState) this.mTaskOrganizerStates.get(task.mTaskOrganizer.asBinder()))
              .mPendingEventsQueue;
      if (taskOrganizerPendingEventsQueue == null) {
        Slog.i(
            "TaskOrganizerController",
            "cannot send onTaskInfoChanged because pending events queue is not present for this"
                + " organizer");
        return;
      }
      if (z && taskOrganizerPendingEventsQueue.numPendingTaskEvents() == 0) {
        taskOrganizerPendingEventsQueue.dispatchTaskInfoChanged(task, true);
        return;
      }
      PendingTaskEvent pendingLifecycleTaskEvent =
          taskOrganizerPendingEventsQueue.getPendingLifecycleTaskEvent(task);
      if (pendingLifecycleTaskEvent == null) {
        pendingLifecycleTaskEvent = new PendingTaskEvent(task, 2);
      } else if (pendingLifecycleTaskEvent.mEventType != 2) {
        return;
      } else {
        taskOrganizerPendingEventsQueue.removePendingTaskEvent(pendingLifecycleTaskEvent);
      }
      pendingLifecycleTaskEvent.mForce |= z;
      taskOrganizerPendingEventsQueue.addPendingTaskEvent(pendingLifecycleTaskEvent);
    }
  }

  /* JADX WARN: Finally extract failed */
  public WindowContainerToken getImeTarget(int i) {
    WindowContainerToken windowContainerToken;
    ActivityTaskManagerService.enforceTaskPermission("getImeTarget()");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          DisplayContent displayContent = this.mService.mWindowManager.mRoot.getDisplayContent(i);
          if (displayContent != null) {
            InsetsControlTarget imeTarget = displayContent.getImeTarget(0);
            if (imeTarget != null && imeTarget.getWindow() != null) {
              Task task = imeTarget.getWindow().getTask();
              if (task != null) {
                if (task.inFreeformWindowingMode()) {
                  WindowState windowState =
                      displayContent.getImeInputTarget() != null
                          ? displayContent.getImeInputTarget().getWindowState()
                          : null;
                  Task task2 = windowState != null ? windowState.getTask() : null;
                  if (task2 != null && task2 != task) {
                    windowContainerToken = task2.mRemoteToken.toWindowContainerToken();
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return windowContainerToken;
                  }
                }
                windowContainerToken = task.mRemoteToken.toWindowContainerToken();
                WindowManagerService.resetPriorityAfterLockedSection();
                return windowContainerToken;
              }
            }
          }
          WindowManagerService.resetPriorityAfterLockedSection();
          return null;
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  /* JADX WARN: Finally extract failed */
  public List getChildTasks(WindowContainerToken windowContainerToken, int[] iArr) {
    ActivityTaskManagerService.enforceTaskPermission("getChildTasks()");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          if (windowContainerToken == null) {
            throw new IllegalArgumentException("Can't get children of null parent");
          }
          WindowContainer fromBinder = WindowContainer.fromBinder(windowContainerToken.asBinder());
          if (fromBinder == null) {
            Slog.e(
                "TaskOrganizerController",
                "Can't get children of " + windowContainerToken + " because it is not valid.");
          } else {
            Task asTask = fromBinder.asTask();
            if (asTask == null) {
              Slog.e("TaskOrganizerController", fromBinder + " is not a task...");
            } else if (!asTask.mCreatedByOrganizer) {
              Slog.w(
                  "TaskOrganizerController",
                  "Can only get children of root tasks created via createRootTask");
            } else {
              ArrayList arrayList = new ArrayList();
              for (int childCount = asTask.getChildCount() - 1; childCount >= 0; childCount--) {
                Task asTask2 = asTask.getChildAt(childCount).asTask();
                if (asTask2 != null
                    && (iArr == null || ArrayUtils.contains(iArr, asTask2.getActivityType()))) {
                  arrayList.add(asTask2.getTaskInfo());
                }
              }
              WindowManagerService.resetPriorityAfterLockedSection();
              return arrayList;
            }
          }
          WindowManagerService.resetPriorityAfterLockedSection();
          return null;
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public List getRootTasks(int i, final int[] iArr) {
    final ArrayList arrayList;
    ActivityTaskManagerService.enforceTaskPermission("getRootTasks()");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          DisplayContent displayContent = this.mService.mRootWindowContainer.getDisplayContent(i);
          if (displayContent == null) {
            throw new IllegalArgumentException("Display " + i + " doesn't exist");
          }
          arrayList = new ArrayList();
          displayContent.forAllRootTasks(
              new Consumer() { // from class:
                               // com.android.server.wm.TaskOrganizerController$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                  TaskOrganizerController.lambda$getRootTasks$3(iArr, arrayList, (Task) obj);
                }
              });
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
      return arrayList;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public static /* synthetic */ void lambda$getRootTasks$3(
      int[] iArr, ArrayList arrayList, Task task) {
    if (iArr == null || ArrayUtils.contains(iArr, task.getActivityType())) {
      arrayList.add(task.getTaskInfo());
    }
  }

  /* JADX WARN: Finally extract failed */
  public void setInterceptBackPressedOnTaskRoot(
      WindowContainerToken windowContainerToken, boolean z) {
    ActivityTaskManagerService.enforceTaskPermission("setInterceptBackPressedOnTaskRoot()");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          if (ProtoLogCache.WM_DEBUG_WINDOW_ORGANIZER_enabled) {
            ProtoLogImpl.v(
                ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER,
                232317536,
                3,
                (String) null,
                new Object[] {Boolean.valueOf(z)});
          }
          WindowContainer fromBinder = WindowContainer.fromBinder(windowContainerToken.asBinder());
          if (fromBinder == null) {
            Slog.w("TaskOrganizerController", "Could not resolve window from token");
          } else {
            Task asTask = fromBinder.asTask();
            if (asTask != null) {
              if (z) {
                this.mInterceptBackPressedOnRootTasks.add(Integer.valueOf(asTask.mTaskId));
              } else {
                this.mInterceptBackPressedOnRootTasks.remove(Integer.valueOf(asTask.mTaskId));
              }
              WindowManagerService.resetPriorityAfterLockedSection();
              return;
            }
            Slog.w("TaskOrganizerController", "Could not resolve task from token");
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

  /* JADX WARN: Finally extract failed */
  public void restartTaskTopActivityProcessIfVisible(WindowContainerToken windowContainerToken) {
    ActivityTaskManagerService.enforceTaskPermission("restartTopActivityProcessIfVisible()");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          WindowContainer fromBinder = WindowContainer.fromBinder(windowContainerToken.asBinder());
          if (fromBinder == null) {
            Slog.w("TaskOrganizerController", "Could not resolve window from token");
          } else {
            Task asTask = fromBinder.asTask();
            if (asTask == null) {
              Slog.w("TaskOrganizerController", "Could not resolve task from token");
            } else {
              if (ProtoLogCache.WM_DEBUG_WINDOW_ORGANIZER_enabled) {
                ProtoLogImpl.v(
                    ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER,
                    -1963363332,
                    1,
                    (String) null,
                    new Object[] {Long.valueOf(asTask.mTaskId)});
              }
              ActivityRecord topNonFinishingActivity = asTask.getTopNonFinishingActivity();
              if (topNonFinishingActivity != null) {
                topNonFinishingActivity.restartProcessIfVisible();
              }
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

  /* JADX WARN: Finally extract failed */
  public void updateCameraCompatControlState(WindowContainerToken windowContainerToken, int i) {
    ActivityTaskManagerService.enforceTaskPermission("updateCameraCompatControlState()");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          WindowContainer fromBinder = WindowContainer.fromBinder(windowContainerToken.asBinder());
          if (fromBinder == null) {
            Slog.w("TaskOrganizerController", "Could not resolve window from token");
          } else {
            Task asTask = fromBinder.asTask();
            if (asTask == null) {
              Slog.w("TaskOrganizerController", "Could not resolve task from token");
            } else {
              if (ProtoLogCache.WM_DEBUG_WINDOW_ORGANIZER_enabled) {
                ProtoLogImpl.v(
                    ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER,
                    -846931068,
                    4,
                    (String) null,
                    new Object[] {
                      String.valueOf(TaskInfo.cameraCompatControlStateToString(i)),
                      Long.valueOf(asTask.mTaskId)
                    });
              }
              ActivityRecord topNonFinishingActivity = asTask.getTopNonFinishingActivity();
              if (topNonFinishingActivity != null) {
                topNonFinishingActivity.updateCameraCompatStateFromUser(i);
              }
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

  public void setOrientationRequestPolicy(boolean z, int[] iArr, int[] iArr2) {
    ActivityTaskManagerService.enforceTaskPermission("setOrientationRequestPolicy()");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          this.mService.mWindowManager.setOrientationRequestPolicy(z, iArr, iArr2);
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

  public boolean handleInterceptBackPressedOnTaskRoot(Task task) {
    if (task == null
        || !task.isOrganized()
        || !this.mInterceptBackPressedOnRootTasks.contains(Integer.valueOf(task.mTaskId))) {
      return false;
    }
    TaskOrganizerPendingEventsQueue taskOrganizerPendingEventsQueue =
        ((TaskOrganizerState) this.mTaskOrganizerStates.get(task.mTaskOrganizer.asBinder()))
            .mPendingEventsQueue;
    if (taskOrganizerPendingEventsQueue == null) {
      Slog.w(
          "TaskOrganizerController",
          "cannot get handle BackPressedOnTaskRoot because organizerState is not present");
      return false;
    }
    if (taskOrganizerPendingEventsQueue.getPendingTaskEvent(task, 1) != null) {
      return false;
    }
    PendingTaskEvent pendingTaskEvent =
        taskOrganizerPendingEventsQueue.getPendingTaskEvent(task, 3);
    if (pendingTaskEvent == null) {
      pendingTaskEvent = new PendingTaskEvent(task, 3);
    } else {
      taskOrganizerPendingEventsQueue.removePendingTaskEvent(pendingTaskEvent);
    }
    taskOrganizerPendingEventsQueue.addPendingTaskEvent(pendingTaskEvent);
    this.mService.mWindowManager.mWindowPlacerLocked.requestTraversal();
    return true;
  }

  public void setFreeformTaskSurfaceOverlappedWithNavi(
      WindowContainerToken windowContainerToken, boolean z) {
    ActivityTaskManagerService.enforceTaskPermission("setFreeformTaskSurfaceOverlappedWithNavi()");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          WindowContainer fromBinder = WindowContainer.fromBinder(windowContainerToken.asBinder());
          if (fromBinder == null) {
            Slog.w("TaskOrganizerController", "Could not resolve window from token");
            WindowManagerService.resetPriorityAfterLockedSection();
          } else {
            DisplayContent displayContent = fromBinder.mDisplayContent;
            if (displayContent != null) {
              displayContent.getDisplayPolicy().setFreeforTaskSurfaceOverlappingWithNavBar(z);
            }
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

  public void dump(PrintWriter printWriter, String str) {
    String str2 = str + "  ";
    printWriter.print(str);
    printWriter.println("TaskOrganizerController:");
    for (TaskOrganizerState taskOrganizerState : this.mTaskOrganizerStates.values()) {
      ArrayList arrayList = taskOrganizerState.mOrganizedTasks;
      printWriter.print(str2 + "  ");
      printWriter.println(
          taskOrganizerState.mOrganizer.mTaskOrganizer
              + " uid="
              + taskOrganizerState.mUid
              + XmlUtils.STRING_ARRAY_SEPARATOR);
      for (int i = 0; i < arrayList.size(); i++) {
        Task task = (Task) arrayList.get(i);
        printWriter.println(
            str2
                + "    ("
                + WindowConfiguration.windowingModeToString(task.getWindowingMode())
                + ") "
                + task);
      }
    }
    printWriter.println();
  }

  public TaskOrganizerState getTaskOrganizerState(IBinder iBinder) {
    return (TaskOrganizerState) this.mTaskOrganizerStates.get(iBinder);
  }

  public TaskOrganizerPendingEventsQueue getTaskOrganizerPendingEvents(IBinder iBinder) {
    return ((TaskOrganizerState) this.mTaskOrganizerStates.get(iBinder)).mPendingEventsQueue;
  }

  public void onSplitLayoutChangeRequested(Bundle bundle) {
    Task rootMainStageTask =
        this.mService.mRootWindowContainer.getDefaultTaskDisplayArea().getRootMainStageTask();
    if (rootMainStageTask == null) {
      return;
    }
    ActivityManager.RunningTaskInfo runningTaskInfo = new ActivityManager.RunningTaskInfo();
    rootMainStageTask.fillTaskInfo(runningTaskInfo);
    try {
      rootMainStageTask.mTaskOrganizer.onSplitLayoutChangeRequested(runningTaskInfo, bundle);
    } catch (RemoteException e) {
      Slog.e(
          "TaskOrganizerController", "Exception sending onSplitLayoutChangeRequested callback", e);
    }
  }

  public void requestAffordanceAnim(Task task, int i) {
    if (task == null || !task.isOrganized()) {
      return;
    }
    TaskOrganizerPendingEventsQueue pendingEventsQueue =
        getTaskOrganizerState(task.mTaskOrganizer.asBinder()).getPendingEventsQueue();
    if (pendingEventsQueue.getPendingTaskEvent(task, 1) != null) {
      return;
    }
    PendingTaskEvent pendingTaskEvent = pendingEventsQueue.getPendingTaskEvent(task, 4);
    if (pendingTaskEvent == null) {
      pendingTaskEvent = new PendingTaskEvent(task, 4);
    } else {
      pendingEventsQueue.removePendingTaskEvent(pendingTaskEvent);
    }
    pendingTaskEvent.mGestureFrom = i;
    pendingEventsQueue.addPendingTaskEvent(pendingTaskEvent);
    this.mService.mWindowManager.mWindowPlacerLocked.requestTraversal();
  }

  public void setFreeformTaskOpacity(int i, float f) {
    ActivityTaskManagerService.enforceTaskPermission("setFreeformTaskOpacity()");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          Task anyTaskForId = this.mService.mRootWindowContainer.anyTaskForId(i, 0);
          if (anyTaskForId != null) {
            anyTaskForId.mFreeformAlpha = f;
            this.mService.mWindowManager.mWindowPlacerLocked.performSurfacePlacement();
            WindowManagerService.resetPriorityAfterLockedSection();
            return;
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

  /* JADX WARN: Finally extract failed */
  public float getFreeformTaskOpacity(int i) {
    ActivityTaskManagerService.enforceTaskPermission("getFreeformTaskOpacity()");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          Task anyTaskForId = this.mService.mRootWindowContainer.anyTaskForId(i, 0);
          if (anyTaskForId == null) {
            Slog.w("TaskOrganizerController", "Cannot find task #" + i);
            WindowManagerService.resetPriorityAfterLockedSection();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return 1.0f;
          }
          float f = anyTaskForId.mFreeformAlpha;
          WindowManagerService.resetPriorityAfterLockedSection();
          Binder.restoreCallingIdentity(clearCallingIdentity);
          return f;
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
    } catch (Throwable th2) {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      throw th2;
    }
  }

  /* JADX WARN: Finally extract failed */
  public boolean togglePinTaskState(int i) {
    boolean z;
    ActivityTaskManagerService.enforceTaskPermission("togglePinTaskState()");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          Task anyTaskForId = this.mService.mRootWindowContainer.anyTaskForId(i, 0);
          if (anyTaskForId != null) {
            boolean isFreeformPinned = anyTaskForId.isFreeformPinned();
            if (isFreeformPinned) {
              anyTaskForId.stopFreeformPinning(false, "unPinButtonClicked");
            } else {
              anyTaskForId.startFreeformPinning();
            }
            z = isFreeformPinned ? false : true;
          }
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
      return z;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  /* JADX WARN: Finally extract failed */
  public boolean isPinStateChangeable(int i) {
    boolean z;
    ActivityTaskManagerService.enforceTaskPermission("isPinStateChangeable()");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          Task anyTaskForId = this.mService.mRootWindowContainer.anyTaskForId(i, 0);
          if (anyTaskForId != null) {
            z = anyTaskForId.hasPinnedFreeformTask() ? false : true;
          }
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
      return z;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void changeSplitScreenCreateMode(int i) {
    TaskOrganizerInfo taskOrganizerInfo = new TaskOrganizerInfo();
    taskOrganizerInfo.setSplitScreenCreateModeForLaunchAdjacent(i);
    onSplitLayoutChangeRequested(taskOrganizerInfo.toBundle());
  }

  public void resetStashedFreeform(Task task, boolean z) {
    try {
      if (task.isOrganized()) {
        task.resetFreeformStashed();
        task.mTaskOrganizer.resetStashedFreeform(task.mTaskId, z);
      }
    } catch (RemoteException e) {
      Slog.e("TaskOrganizerController", "Exception sending resetStashedFreeform callback", e);
    }
  }

  public void onKeepScreenOnChanged(Task task, boolean z) {
    try {
      if (task.isOrganized()) {
        task.mTaskOrganizer.onKeepScreenOnChanged(task.mTaskId, z);
      }
    } catch (RemoteException e) {
      Slog.e("TaskOrganizerController", "Exception sending onKeepScreenOnChanged callback. ", e);
    }
  }

  /* JADX WARN: Finally extract failed */
  public boolean isKeepScreenOn(int i) {
    ActivityTaskManagerService.enforceTaskPermission("isKeepScreenOn()");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          Task anyTaskForId = this.mService.mRootWindowContainer.anyTaskForId(i, 0);
          if (anyTaskForId == null) {
            Slog.w("TaskOrganizerController", "Cannot find task #" + i);
          } else {
            WindowState topVisibleAppMainWindow = anyTaskForId.getTopVisibleAppMainWindow();
            if (topVisibleAppMainWindow == null) {
              Slog.w("TaskOrganizerController", "Cannot find task top Window #" + i);
            } else {
              boolean isKeepScreenOn = anyTaskForId.isKeepScreenOn(topVisibleAppMainWindow);
              WindowManagerService.resetPriorityAfterLockedSection();
              return isKeepScreenOn;
            }
          }
          WindowManagerService.resetPriorityAfterLockedSection();
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

  public void onImmersiveModeChanged(Task task, boolean z) {
    try {
      ITaskOrganizer taskOrganizer = task.isOrganized() ? task.mTaskOrganizer : getTaskOrganizer();
      if (taskOrganizer == null) {
        Slog.w("TaskOrganizerController", "onImmersiveModeChanged: cannot find organizer");
      } else {
        taskOrganizer.onImmersiveModeChanged(task.mTaskId, z);
      }
    } catch (RemoteException e) {
      Slog.e("TaskOrganizerController", "Exception sending onImmersiveModeChanged callback. ", e);
    }
  }

  public void onNewDexImmersiveModeChanged(Task task, boolean z) {
    try {
      ITaskOrganizer taskOrganizer = task.isOrganized() ? task.mTaskOrganizer : getTaskOrganizer();
      if (taskOrganizer == null) {
        Slog.w("TaskOrganizerController", "onNewDexImmersiveModeChanged: cannot find organizer");
      } else {
        taskOrganizer.onNewDexImmersiveModeChanged(task.mTaskId, z);
      }
    } catch (RemoteException e) {
      Slog.e(
          "TaskOrganizerController",
          "Exception sending onNewDexImmersiveModeChanged callback. ",
          e);
    }
  }
}
