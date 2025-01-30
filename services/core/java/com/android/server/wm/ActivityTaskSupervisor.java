package com.android.server.wm;

import android.R;
import android.app.ActivityManagerInternal;
import android.app.ActivityOptions;
import android.app.AppOpsManager;
import android.app.AppOpsManagerInternal;
import android.app.BackgroundStartPrivileges;
import android.app.IActivityClientController;
import android.app.ProfilerInfo;
import android.app.TaskInfo;
import android.app.WaitResult;
import android.app.servertransaction.ClientTransaction;
import android.app.servertransaction.LaunchActivityItem;
import android.app.servertransaction.PauseActivityItem;
import android.app.servertransaction.ResumeActivityItem;
import android.companion.virtual.VirtualDeviceManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.hardware.SensorPrivacyManagerInternal;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.WorkSource;
import android.p005os.IInstalld;
import android.util.ArrayMap;
import android.util.MergedConfiguration;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.Display;
import android.widget.Toast;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.function.QuadConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.LocalServices;
import com.android.server.UiThread;
import com.android.server.am.AppStateBroadcaster;
import com.android.server.am.UserState;
import com.android.server.pm.PackageManagerServiceUtils;
import com.android.server.utils.Slogf;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.game.PkgDataHelper;
import com.samsung.android.rune.CoreRune;
import com.sec.tmodiagnostics.DeviceReportingSecurityChecker;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class ActivityTaskSupervisor implements RecentTasks.Callbacks {
  public static final ArrayMap ACTION_TO_RUNTIME_PERMISSION;
  public static final int IDLE_TIMEOUT;
  public static final int LAUNCH_TIMEOUT;
  public static final int SLEEP_TIMEOUT;
  public ActivityMetricsLogger mActivityMetricsLogger;
  public AppOpsManager mAppOpsManager;
  public boolean mAppVisibilitiesChangedSinceLastPause;
  public int mDeferResumeCount;
  public boolean mDeferRootVisibilityUpdate;
  public PowerManager.WakeLock mGoingToSleepWakeLock;
  public final ActivityTaskSupervisorHandler mHandler;
  public boolean mInitialized;
  public KeyguardController mKeyguardController;
  public LaunchParamsController mLaunchParamsController;
  public LaunchParamsPersister mLaunchParamsPersister;
  public PowerManager.WakeLock mLaunchingActivityWakeLock;
  public final Looper mLooper;
  public PersisterQueue mPersisterQueue;
  public Rect mPipModeChangedTargetRootTaskBounds;
  public PowerManager mPowerManager;
  public int mRealStartActivityTransactionDepth;
  public RecentTasks mRecentTasks;
  public RootWindowContainer mRootWindowContainer;
  public RunningTasks mRunningTasks;
  public final ActivityTaskManagerService mService;
  public ComponentName mSystemChooserActivity;
  public ActivityRecord mTopResumedActivity;
  public boolean mTopResumedActivityWaitingForPrev;
  public VirtualDeviceManager mVirtualDeviceManager;
  public int mVisibilityTransactionDepth;
  public WindowManagerService mWindowManager;
  public final TaskInfoHelper mTaskInfoHelper = new TaskInfoHelper();
  public final OpaqueActivityHelper mOpaqueActivityHelper = new OpaqueActivityHelper();
  public final ArrayList mActivityStateChangedProcs = new ArrayList();
  public final SparseIntArray mCurTaskIdForUser = new SparseIntArray(20);
  public final ArrayList mWaitingActivityLaunched = new ArrayList();
  public final ArrayList mStoppingActivities = new ArrayList();
  public final ArrayList mFinishingActivities = new ArrayList();
  public final ArrayList mNoHistoryActivities = new ArrayList();
  public final ArrayList mMultiWindowModeChangedActivities = new ArrayList();
  public final ArrayList mPipModeChangedActivities = new ArrayList();
  public final ArrayList mNoAnimActivities = new ArrayList();
  public final ArrayList mStartingUsers = new ArrayList();
  public boolean mUserLeaving = false;
  public ActivityManagerPerformance mTaskBooster = null;

  public static int nextTaskIdForUser(int i, int i2) {
    int i3 = i + 1;
    return i3 == (i2 + 1) * 100000 ? i3 - 100000 : i3;
  }

  static {
    int i = Build.HW_TIMEOUT_MULTIPLIER;
    IDLE_TIMEOUT = i * 10000;
    SLEEP_TIMEOUT = i * 5000;
    LAUNCH_TIMEOUT = i * 10000;
    ArrayMap arrayMap = new ArrayMap();
    ACTION_TO_RUNTIME_PERMISSION = arrayMap;
    arrayMap.put("android.media.action.IMAGE_CAPTURE", "android.permission.CAMERA");
    arrayMap.put("android.media.action.VIDEO_CAPTURE", "android.permission.CAMERA");
    arrayMap.put("android.intent.action.CALL", "android.permission.CALL_PHONE");
  }

  public boolean canPlaceEntityOnDisplay(int i, int i2, int i3, ActivityInfo activityInfo) {
    return canPlaceEntityOnDisplay(i, i2, i3, null, activityInfo);
  }

  public boolean canPlaceEntityOnDisplay(int i, int i2, int i3, Task task) {
    return canPlaceEntityOnDisplay(i, i2, i3, task, null);
  }

  public final boolean canPlaceEntityOnDisplay(
      int i, int i2, int i3, Task task, ActivityInfo activityInfo) {
    if (i == 0) {
      return true;
    }
    if (!this.mService.mSupportsMultiDisplay
        || !isCallerAllowedToLaunchOnDisplay(i2, i3, i, activityInfo)) {
      return false;
    }
    DisplayContent displayContentOrCreate = this.mRootWindowContainer.getDisplayContentOrCreate(i);
    if (displayContentOrCreate == null) {
      return true;
    }
    final ArrayList arrayList = new ArrayList();
    if (activityInfo != null) {
      arrayList.add(activityInfo);
    }
    if (task != null) {
      task.forAllActivities(
          new Consumer() { // from class:
                           // com.android.server.wm.ActivityTaskSupervisor$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              ActivityTaskSupervisor.lambda$canPlaceEntityOnDisplay$0(
                  arrayList, (ActivityRecord) obj);
            }
          });
    }
    return displayContentOrCreate.mDwpcHelper.canContainActivities(
        arrayList, displayContentOrCreate.getWindowingMode());
  }

  public static /* synthetic */ void lambda$canPlaceEntityOnDisplay$0(
      ArrayList arrayList, ActivityRecord activityRecord) {
    arrayList.add(activityRecord.info);
  }

  public ActivityTaskSupervisor(
      ActivityTaskManagerService activityTaskManagerService, Looper looper) {
    this.mService = activityTaskManagerService;
    this.mLooper = looper;
    this.mHandler = new ActivityTaskSupervisorHandler(looper);
  }

  public void initialize() {
    if (this.mInitialized) {
      return;
    }
    this.mInitialized = true;
    setRunningTasks(new RunningTasks());
    this.mActivityMetricsLogger = new ActivityMetricsLogger(this, this.mHandler.getLooper());
    this.mKeyguardController = new KeyguardController(this.mService, this);
    PersisterQueue persisterQueue = new PersisterQueue();
    this.mPersisterQueue = persisterQueue;
    LaunchParamsPersister launchParamsPersister = new LaunchParamsPersister(persisterQueue, this);
    this.mLaunchParamsPersister = launchParamsPersister;
    LaunchParamsController launchParamsController =
        new LaunchParamsController(this.mService, launchParamsPersister);
    this.mLaunchParamsController = launchParamsController;
    launchParamsController.registerDefaultModifiers(this);
  }

  public void onSystemReady() {
    this.mLaunchParamsPersister.onSystemReady();
  }

  public void onUserUnlocked(int i) {
    this.mPersisterQueue.startPersisting();
    this.mLaunchParamsPersister.onUnlockUser(i);
    scheduleStartHome("userUnlocked");
  }

  public ActivityMetricsLogger getActivityMetricsLogger() {
    return this.mActivityMetricsLogger;
  }

  public KeyguardController getKeyguardController() {
    return this.mKeyguardController;
  }

  public ComponentName getSystemChooserActivity() {
    if (this.mSystemChooserActivity == null) {
      this.mSystemChooserActivity =
          ComponentName.unflattenFromString(
              this.mService.mContext.getResources().getString(R.string.contentServiceSync));
    }
    return this.mSystemChooserActivity;
  }

  public void setRecentTasks(RecentTasks recentTasks) {
    RecentTasks recentTasks2 = this.mRecentTasks;
    if (recentTasks2 != null) {
      recentTasks2.unregisterCallback(this);
    }
    this.mRecentTasks = recentTasks;
    recentTasks.registerCallback(this);
  }

  public void setRunningTasks(RunningTasks runningTasks) {
    this.mRunningTasks = runningTasks;
  }

  public RunningTasks getRunningTasks() {
    return this.mRunningTasks;
  }

  public void initPowerManagement() {
    PowerManager powerManager =
        (PowerManager) this.mService.mContext.getSystemService(PowerManager.class);
    this.mPowerManager = powerManager;
    this.mGoingToSleepWakeLock = powerManager.newWakeLock(1, "ActivityManager-Sleep");
    PowerManager.WakeLock newWakeLock = this.mPowerManager.newWakeLock(1, "*launch*");
    this.mLaunchingActivityWakeLock = newWakeLock;
    newWakeLock.setReferenceCounted(false);
  }

  public void setWindowManager(WindowManagerService windowManagerService) {
    this.mWindowManager = windowManagerService;
    getKeyguardController().setWindowManager(windowManagerService);
  }

  public void setNextTaskIdForUser(int i, int i2) {
    if (i > this.mCurTaskIdForUser.get(i2, -1)) {
      this.mCurTaskIdForUser.put(i2, i);
    }
  }

  public void finishNoHistoryActivitiesIfNeeded(ActivityRecord activityRecord) {
    for (int size = this.mNoHistoryActivities.size() - 1; size >= 0; size--) {
      ActivityRecord activityRecord2 = (ActivityRecord) this.mNoHistoryActivities.get(size);
      if (!activityRecord2.finishing
          && activityRecord2 != activityRecord
          && activityRecord.occludesParent()
          && activityRecord2.getDisplayId() == activityRecord.getDisplayId()) {
        if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
          ProtoLogImpl.d(
              ProtoLogGroup.WM_DEBUG_STATES,
              -484194149,
              0,
              (String) null,
              new Object[] {String.valueOf(activityRecord2)});
        }
        activityRecord2.finishIfPossible("resume-no-history", false);
        this.mNoHistoryActivities.remove(activityRecord2);
      }
    }
  }

  public int getNextTaskIdForUser() {
    return getNextTaskIdForUser(this.mRootWindowContainer.mCurrentUser);
  }

  public int getNextTaskIdForUser(int i) {
    int i2 = this.mCurTaskIdForUser.get(i, 100000 * i);
    int nextTaskIdForUser = nextTaskIdForUser(i2, i);
    do {
      if (this.mRecentTasks.containsTaskId(nextTaskIdForUser, i)
          || this.mRootWindowContainer.anyTaskForId(nextTaskIdForUser, 1) != null) {
        nextTaskIdForUser = nextTaskIdForUser(nextTaskIdForUser, i);
      } else {
        this.mCurTaskIdForUser.put(i, nextTaskIdForUser);
        return nextTaskIdForUser;
      }
    } while (nextTaskIdForUser != i2);
    throw new IllegalStateException(
        "Cannot get an available task id. Reached limit of 100000 running tasks per user.");
  }

  public void waitActivityVisibleOrLaunched(
      WaitResult waitResult,
      ActivityRecord activityRecord,
      ActivityMetricsLogger.LaunchingState launchingState) {
    int i = waitResult.result;
    if (i == 2 || i == 0) {
      WaitInfo waitInfo =
          new WaitInfo(waitResult, activityRecord.mActivityComponent, launchingState);
      this.mWaitingActivityLaunched.add(waitInfo);
      do {
        try {
          this.mService.mGlobalLock.wait();
        } catch (InterruptedException unused) {
        }
      } while (this.mWaitingActivityLaunched.contains(waitInfo));
    }
  }

  public void cleanupActivity(ActivityRecord activityRecord) {
    this.mFinishingActivities.remove(activityRecord);
    stopWaitingForActivityVisible(activityRecord);
  }

  public void stopWaitingForActivityVisible(ActivityRecord activityRecord) {
    reportActivityLaunched(false, activityRecord, -1L, 0);
  }

  public void reportActivityLaunched(boolean z, ActivityRecord activityRecord, long j, int i) {
    boolean z2 = false;
    for (int size = this.mWaitingActivityLaunched.size() - 1; size >= 0; size--) {
      WaitInfo waitInfo = (WaitInfo) this.mWaitingActivityLaunched.get(size);
      if (waitInfo.matches(activityRecord)) {
        WaitResult waitResult = waitInfo.mResult;
        waitResult.timeout = z;
        waitResult.who = activityRecord.mActivityComponent;
        waitResult.totalTime = j;
        waitResult.launchState = i;
        this.mWaitingActivityLaunched.remove(size);
        z2 = true;
      }
    }
    if (z2) {
      this.mService.mGlobalLock.notifyAll();
    }
  }

  public void reportWaitingActivityLaunchedIfNeeded(ActivityRecord activityRecord, int i) {
    if (this.mWaitingActivityLaunched.isEmpty()) {
      return;
    }
    if (i == 3 || i == 2) {
      boolean z = false;
      for (int size = this.mWaitingActivityLaunched.size() - 1; size >= 0; size--) {
        WaitInfo waitInfo = (WaitInfo) this.mWaitingActivityLaunched.get(size);
        if (waitInfo.matches(activityRecord)) {
          WaitResult waitResult = waitInfo.mResult;
          waitResult.result = i;
          if (i == 3) {
            waitResult.who = activityRecord.mActivityComponent;
            this.mWaitingActivityLaunched.remove(size);
            z = true;
          }
        }
      }
      if (z) {
        this.mService.mGlobalLock.notifyAll();
      }
    }
  }

  public ActivityInfo resolveActivity(
      Intent intent, ResolveInfo resolveInfo, final int i, final ProfilerInfo profilerInfo) {
    final ActivityInfo activityInfo = resolveInfo != null ? resolveInfo.activityInfo : null;
    if (activityInfo != null) {
      intent.setComponent(
          new ComponentName(activityInfo.applicationInfo.packageName, activityInfo.name));
      boolean z = (i & 14) != 0;
      boolean z2 = profilerInfo != null;
      if (z || z2) {
        boolean z3 =
            (Build.IS_DEBUGGABLE || (activityInfo.applicationInfo.flags & 2) != 0)
                && !activityInfo.processName.equals("system");
        if ((z && !z3) || (z2 && !z3 && !activityInfo.applicationInfo.isProfileableByShell())) {
          Slog.w(
              "ActivityTaskManager",
              "Ignore debugging for non-debuggable app: " + activityInfo.packageName);
        } else {
          WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
          WindowManagerService.boostPriorityForLockedSection();
          synchronized (windowManagerGlobalLock) {
            try {
              this.mService.f1734mH.post(
                  new Runnable() { // from class:
                                   // com.android.server.wm.ActivityTaskSupervisor$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                      ActivityTaskSupervisor.this.lambda$resolveActivity$1(
                          activityInfo, i, profilerInfo);
                    }
                  });
              try {
                this.mService.mGlobalLock.wait();
              } catch (InterruptedException unused) {
              }
            } catch (Throwable th) {
              WindowManagerService.resetPriorityAfterLockedSection();
              throw th;
            }
          }
          WindowManagerService.resetPriorityAfterLockedSection();
        }
      }
      String launchToken = intent.getLaunchToken();
      if (activityInfo.launchToken == null && launchToken != null) {
        activityInfo.launchToken = launchToken;
      }
    }
    return activityInfo;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$resolveActivity$1(
      ActivityInfo activityInfo, int i, ProfilerInfo profilerInfo) {
    try {
      ActivityTaskManagerService activityTaskManagerService = this.mService;
      activityTaskManagerService.mAmInternal.setDebugFlagsForStartingActivity(
          activityInfo, i, profilerInfo, activityTaskManagerService.mGlobalLock);
    } finally {
    }
  }

  public ResolveInfo resolveIntent(Intent intent, String str, int i, int i2, int i3, int i4) {
    try {
      Trace.traceBegin(32L, "resolveIntent");
      int i5 = i2 | 65536 | 1024;
      if (intent.isWebIntent()
          || (intent.getFlags() & IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES) != 0) {
        i5 |= 8388608;
      }
      int i6 = (!intent.isWebIntent() || (intent.getFlags() & 1024) == 0) ? 0 : 1;
      if ((intent.getFlags() & 512) != 0) {
        i6 |= 2;
      }
      int i7 = i5 | 128;
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return this.mService
            .getPackageManagerInternalLocked()
            .resolveIntentExported(intent, str, i7, i6, i, true, i3, i4);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    } finally {
      Trace.traceEnd(32L);
    }
  }

  public ActivityInfo resolveActivity(
      Intent intent, String str, int i, ProfilerInfo profilerInfo, int i2, int i3, int i4) {
    return resolveActivity(intent, resolveIntent(intent, str, i2, 0, i3, i4), i, profilerInfo);
  }

  public boolean realStartActivityLocked(
      ActivityRecord activityRecord,
      WindowProcessController windowProcessController,
      boolean z,
      boolean z2) {
    Configuration configuration;
    ArrayList arrayList;
    ArrayList arrayList2;
    ResumeActivityItem obtain;
    if (activityRecord.getState() == ActivityRecord.State.INITIALIZING
        && this.mRealStartActivityTransactionDepth > 0) {
      Slog.d(
          "ActivityTaskManager",
          "realStartActivityLocked: Skipping start of "
              + activityRecord
              + ", mRealStartActivityTransactionDepth="
              + this.mRealStartActivityTransactionDepth);
      return false;
    }
    if (!this.mRootWindowContainer.allPausedActivitiesComplete()) {
      if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_STATES,
            -641258376,
            0,
            (String) null,
            new Object[] {String.valueOf(activityRecord)});
      }
      return false;
    }
    if (((windowProcessController != null)
            && windowProcessController.hasStartedActivity(activityRecord))
        ? false
        : true) {
      if (this.mTaskBooster == null) {
        this.mTaskBooster = ActivityManagerPerformance.getBooster();
      }
      ActivityManagerPerformance activityManagerPerformance = this.mTaskBooster;
      if (activityManagerPerformance != null) {
        if (CoreRune.SYSFW_APP_SPEG) {
          if (!"com.samsung.speg".equals(activityRecord.launchedFromPackage)) {
            this.mTaskBooster.notifyTaskBoost(windowProcessController.getPid());
          }
        } else {
          activityManagerPerformance.notifyTaskBoost(windowProcessController.getPid());
        }
      }
    }
    Task task = activityRecord.getTask();
    Task rootTask = task.getRootTask();
    beginDeferResume();
    windowProcessController.pauseConfigurationDispatch();
    try {
      this.mRealStartActivityTransactionDepth++;
      activityRecord.startFreezingScreenLocked(windowProcessController, 0);
      activityRecord.startLaunchTickingLocked();
      activityRecord.lastLaunchTime = SystemClock.uptimeMillis();
      activityRecord.setProcess(windowProcessController);
      ApplicationInfo applicationInfo = windowProcessController.mInfo;
      activityRecord.mProcessAppInfo = applicationInfo;
      if (this.mService.mDexCompatController.shouldBeApplyDexCompatConfigurationLocked(
          activityRecord, applicationInfo, windowProcessController.getDisplayId())) {
        configuration = new Configuration(windowProcessController.getConfiguration());
        this.mService.mDexCompatController.applyDexCompatConfigurationLocked(
            activityRecord, windowProcessController.mInfo, configuration, "setProcess[ r]");
      } else {
        configuration = null;
      }
      if (windowProcessController.getDisplayId() == 2) {
        configuration = new Configuration(windowProcessController.getConfiguration());
      }
      if (configuration != null) {
        activityRecord.setLastReportedConfiguration(
            new MergedConfiguration(
                configuration, activityRecord.getMergedOverrideConfiguration()));
      }
      boolean z3 = (!z || activityRecord.canResumeByCompat()) ? z : false;
      activityRecord.notifyUnknownVisibilityLaunchedForKeyguardTransition();
      if (z2) {
        this.mRootWindowContainer.ensureVisibilityAndConfig(
            activityRecord, activityRecord.getDisplayId(), false, true);
      }
      if (this.mKeyguardController.checkKeyguardVisibility(activityRecord)
          && activityRecord.allowMoveToFront()) {
        activityRecord.setVisibility(true);
      }
      ApplicationInfo applicationInfo2 = activityRecord.info.applicationInfo;
      int i = applicationInfo2 != null ? applicationInfo2.uid : -1;
      if (activityRecord.mUserId != windowProcessController.mUserId || applicationInfo2.uid != i) {
        Slog.wtf(
            "ActivityTaskManager",
            "User ID for activity changing for "
                + activityRecord
                + " appInfo.uid="
                + activityRecord.info.applicationInfo.uid
                + " info.ai.uid="
                + i
                + " old="
                + activityRecord.app
                + " new="
                + windowProcessController);
      }
      IActivityClientController iActivityClientController =
          windowProcessController.hasEverLaunchedActivity()
              ? null
              : this.mService.mActivityClientController;
      activityRecord.launchCount++;
      LockTaskController lockTaskController = this.mService.getLockTaskController();
      int i2 = task.mLockTaskAuth;
      if (i2 == 2 || i2 == 4 || (i2 == 3 && lockTaskController.getLockTaskModeState() == 1)) {
        lockTaskController.startLockTaskMode(task, false, 0);
      }
      try {
        try {
          if (!windowProcessController.hasThread()) {
            throw new RemoteException();
          }
          if (z3) {
            arrayList = activityRecord.results;
            arrayList2 = activityRecord.newIntents;
          } else {
            arrayList = null;
            arrayList2 = null;
          }
          EventLogTags.writeWmRestartActivity(
              activityRecord.mUserId,
              System.identityHashCode(activityRecord),
              task.mTaskId,
              activityRecord.shortComponentName);
          if (activityRecord.isActivityTypeHome()) {
            updateHomeProcess(task.getBottomMostActivity().app);
          }
          this.mService.mExt.boostPriority(activityRecord);
          this.mService
              .getPackageManagerInternalLocked()
              .notifyPackageUse(activityRecord.intent.getComponent().getPackageName(), 0);
          activityRecord.forceNewConfig = false;
          this.mService.getAppWarningsLocked().onStartActivity(activityRecord);
          new Configuration(windowProcessController.prepareConfigurationForLaunchingActivity());
          new Configuration(activityRecord.getMergedOverrideConfiguration());
          PkgDataHelper.getInstance()
              .notifyAppCreate(
                  windowProcessController.mInfo.packageName, windowProcessController.mUserId);
          Configuration prepareConfigurationForLaunchingActivity =
              windowProcessController.prepareConfigurationForLaunchingActivity();
          MergedConfiguration mergedConfiguration =
              new MergedConfiguration(
                  prepareConfigurationForLaunchingActivity,
                  activityRecord.getMergedOverrideConfiguration());
          activityRecord.setLastReportedConfiguration(mergedConfiguration);
          logIfTransactionTooLarge(activityRecord.intent, activityRecord.getSavedState());
          TaskFragment organizedTaskFragment = activityRecord.getOrganizedTaskFragment();
          if (organizedTaskFragment != null) {
            this.mService.mTaskFragmentOrganizerController.dispatchPendingInfoChangedEvent(
                organizedTaskFragment);
          }
          ClientTransaction obtain2 =
              ClientTransaction.obtain(windowProcessController.getThread(), activityRecord.token);
          boolean isTransitionForward = activityRecord.isTransitionForward();
          obtain2.addCallback(
              LaunchActivityItem.obtain(
                  new Intent(activityRecord.intent),
                  System.identityHashCode(activityRecord),
                  activityRecord.info,
                  mergedConfiguration.getGlobalConfiguration(),
                  mergedConfiguration.getOverrideConfiguration(),
                  getDeviceIdForDisplayId(activityRecord.getDisplayId()),
                  activityRecord.getFilteredReferrer(activityRecord.launchedFromPackage),
                  task.voiceInteractor,
                  windowProcessController.getReportedProcState(),
                  activityRecord.getSavedState(),
                  activityRecord.getPersistentSavedState(),
                  arrayList,
                  arrayList2,
                  activityRecord.takeOptions(),
                  isTransitionForward,
                  windowProcessController.createProfilerInfoIfNeeded(),
                  activityRecord.assistToken,
                  iActivityClientController,
                  activityRecord.shareableActivityToken,
                  activityRecord.getLaunchedFromBubble(),
                  activityRecord.getTaskFragment().getFragmentToken()));
          if (z3) {
            obtain =
                ResumeActivityItem.obtain(
                    isTransitionForward, activityRecord.shouldSendCompatFakeFocus());
          } else {
            obtain = PauseActivityItem.obtain();
          }
          obtain2.setLifecycleStateRequest(obtain);
          this.mService.getLifecycleManager().scheduleTransaction(obtain2);
          if (prepareConfigurationForLaunchingActivity.seq
              > this.mRootWindowContainer.getConfiguration().seq) {
            windowProcessController.setLastReportedConfiguration(
                prepareConfigurationForLaunchingActivity);
          }
          ApplicationInfo applicationInfo3 = windowProcessController.mInfo;
          if ((applicationInfo3.privateFlags & 2) != 0
              && this.mService.mHasHeavyWeightFeature
              && windowProcessController.mName.equals(applicationInfo3.packageName)) {
            if (this.mService.mHeavyWeightProcess != null
                && this.mService.mHeavyWeightProcess != windowProcessController) {
              Slog.w(
                  "ActivityTaskManager",
                  "Starting new heavy weight process "
                      + windowProcessController
                      + " when already running "
                      + this.mService.mHeavyWeightProcess);
            }
            this.mService.setHeavyWeightProcess(activityRecord);
          }
          endDeferResume();
          windowProcessController.resumeConfigurationDispatch();
          this.mRealStartActivityTransactionDepth--;
          activityRecord.launchFailed = false;
          ActivityManagerPerformance.notifyCurTopAct(activityRecord);
          if (z3 && readyToResume()) {
            this.mService.mAmInternal.onPackageResumedFG(
                activityRecord.app.getPackageList(),
                activityRecord.info.packageName,
                activityRecord.launchedFromPackage,
                activityRecord.occludesParent(),
                activityRecord.intent,
                activityRecord.mUserId);
            rootTask.minimalResumeActivityLocked(activityRecord);
          } else {
            if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
              ProtoLogImpl.v(
                  ProtoLogGroup.WM_DEBUG_STATES,
                  -1468740466,
                  0,
                  (String) null,
                  new Object[] {String.valueOf(activityRecord)});
            }
            activityRecord.setState(ActivityRecord.State.PAUSED, "realStartActivityLocked");
            this.mRootWindowContainer.executeAppTransitionForAllDisplay();
          }
          windowProcessController.onStartActivity(
              this.mService.mTopProcessState, activityRecord.info);
          if (CoreRune.FW_DEDICATED_MEMORY) {
            setLongLiveProcessIfNeeded(windowProcessController);
          }
          if (this.mRootWindowContainer.isTopDisplayFocusedRootTask(rootTask)) {
            this.mService.getActivityStartController().startSetupActivity();
          }
          WindowProcessController windowProcessController2 = activityRecord.app;
          if (windowProcessController2 != null) {
            windowProcessController2.updateServiceConnectionActivities();
          }
          if (!CoreRune.SYSFW_APP_PREL || !activityRecord.mIsPrelMode) {
            return true;
          }
          if (Build.IS_DEBUGGABLE) {
            Slog.d("PREL", "Stop if possible " + activityRecord);
          }
          windowProcessController.mIsPrelScheduleGroupOverride = true;
          this.mService.mAps.setPidForPrelaunchedAppAsync(
              windowProcessController.mUid, windowProcessController.getPid());
          reportWaitingActivityLaunchedIfNeeded(activityRecord, 3);
          activityRecord.mShouldPrelNotify = true;
          activityRecord.stopIfPossible();
          activityRecord.mIsPrelMode = false;
          return true;
        } catch (RemoteException e) {
          e = e;
          if (activityRecord.launchFailed) {
            Slog.e(
                "ActivityTaskManager",
                "Second failure launching "
                    + activityRecord.intent.getComponent().flattenToShortString()
                    + ", giving up",
                e);
            windowProcessController.appDied("2nd-crash");
            activityRecord.finishIfPossible("2nd-crash", false);
            return false;
          }
          activityRecord.launchFailed = true;
          activityRecord.detachFromProcess();
          throw e;
        }
      } catch (RemoteException e2) {
        e = e2;
      }
    } finally {
      endDeferResume();
      windowProcessController.resumeConfigurationDispatch();
      this.mRealStartActivityTransactionDepth--;
    }
  }

  public void updateHomeProcess(WindowProcessController windowProcessController) {
    if (windowProcessController == null || this.mService.mHomeProcess == windowProcessController) {
      return;
    }
    scheduleStartHome("homeChanged");
    this.mService.mHomeProcess = windowProcessController;
  }

  public final void scheduleStartHome(String str) {
    if (this.mHandler.hasMessages(
        FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__SET_MTE_POLICY)) {
      return;
    }
    this.mHandler
        .obtainMessage(FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__SET_MTE_POLICY, str)
        .sendToTarget();
  }

  public final void logIfTransactionTooLarge(Intent intent, Bundle bundle) {
    Bundle extras;
    int size = (intent == null || (extras = intent.getExtras()) == null) ? 0 : extras.getSize();
    int size2 = bundle != null ? bundle.getSize() : 0;
    if (size + size2 > 200000) {
      Slog.e(
          "ActivityTaskManager",
          "Transaction too large, intent: "
              + intent
              + ", extras size: "
              + size
              + ", icicle size: "
              + size2);
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:47:0x00a9  */
  /* JADX WARN: Removed duplicated region for block: B:49:0x00b9  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public void startSpecificActivity(ActivityRecord activityRecord, boolean z, boolean z2) {
    int i;
    boolean z3;
    Handler handler;
    final WindowProcessController processController =
        this.mService.getProcessController(
            activityRecord.processName, activityRecord.info.applicationInfo.uid);
    if (processController != null
        && this.mService.mDexController.shouldKillProcess(
            processController, activityRecord.getDisplayId())) {
      if (activityRecord.isTaskOverlay() && processController.hasVisibleActivities()) {
        Slog.i(
            "ActivityTaskManager",
            "Activity "
                + activityRecord
                + " is overlay, wpc = "
                + processController
                + "has visible activity. So do not kill");
      } else {
        i = processController.getPid();
        processController = null;
        if (processController == null && processController.hasThread()) {
          try {
            realStartActivityLocked(activityRecord, processController, z, z2);
            return;
          } catch (RemoteException e) {
            Slog.w(
                "ActivityTaskManager",
                "Exception when starting activity "
                    + activityRecord.intent.getComponent().flattenToShortString(),
                e);
            this.mService.mProcessNames.remove(processController.mName, processController.mUid);
            this.mService.mProcessMap.remove(processController.getPid());
            z3 = true;
          }
        } else {
          if (!activityRecord.intent.isSandboxActivity(this.mService.mContext)) {
            Slog.e(
                "ActivityTaskManager",
                "Abort sandbox activity launching as no sandbox process to host it.");
            activityRecord.finishIfPossible("No sandbox process for the activity", false);
            activityRecord.launchFailed = true;
            activityRecord.detachFromProcess();
            return;
          }
          z3 = false;
        }
        activityRecord.notifyUnknownVisibilityLaunchedForKeyguardTransition();
        boolean z4 = z && activityRecord.isTopRunningActivity();
        this.mService.startProcessAsync(
            activityRecord,
            z3,
            z4,
            z4 ? "top-activity" : "activity",
            i,
            activityRecord.getDisplayId());
        if (!CoreRune.MNO_TMO_DEVICE_REPORTING
            || !DeviceReportingSecurityChecker.getStatus()
            || (handler = AppStateBroadcaster.mObjHandler) == null
            || processController == null) {
          return;
        }
        final String str = activityRecord.packageName;
        handler.post(
            new Runnable() { // from class:
                             // com.android.server.wm.ActivityTaskSupervisor$$ExternalSyntheticLambda9
              @Override // java.lang.Runnable
              public final void run() {
                ActivityTaskSupervisor.lambda$startSpecificActivity$2(str, processController);
              }
            });
        return;
      }
    }
    i = -1;
    if (processController == null) {}
    if (!activityRecord.intent.isSandboxActivity(this.mService.mContext)) {}
  }

  public static /* synthetic */ void lambda$startSpecificActivity$2(
      String str, WindowProcessController windowProcessController) {
    AppStateBroadcaster.sendApplicationStart(str, windowProcessController.getPid());
  }

  public boolean checkStartAnyActivityPermission(
      Intent intent,
      ActivityInfo activityInfo,
      String str,
      int i,
      int i2,
      int i3,
      String str2,
      String str3,
      boolean z,
      boolean z2,
      WindowProcessController windowProcessController,
      ActivityRecord activityRecord,
      Task task) {
    String str4;
    boolean z3 =
        this.mService.getRecentTasks() != null
            && this.mService.getRecentTasks().isCallerRecents(i3);
    if (ActivityTaskManagerService.checkPermission("android.permission.START_ANY_ACTIVITY", i2, i3)
            == 0
        || (z3 && z2)) {
      return true;
    }
    if (z2
        && this.mService.mDexController.isPendingStartRecent()
        && this.mService.f1734mH.getLooper().equals(Looper.myLooper())) {
      return true;
    }
    int componentRestrictionForCallingPackage =
        getComponentRestrictionForCallingPackage(activityInfo, str2, str3, i2, i3, z);
    int actionRestrictionForCallingPackage =
        getActionRestrictionForCallingPackage(intent.getAction(), str2, str3, i2, i3);
    if (componentRestrictionForCallingPackage != 1 && actionRestrictionForCallingPackage != 1) {
      if (actionRestrictionForCallingPackage == 2) {
        Slog.w(
            "ActivityTaskManager",
            "Appop Denial: starting "
                + intent.toString()
                + " from "
                + windowProcessController
                + " (pid="
                + i2
                + ", uid="
                + i3
                + ") requires "
                + AppOpsManager.permissionToOp(
                    (String) ACTION_TO_RUNTIME_PERMISSION.get(intent.getAction())));
        return false;
      }
      if (componentRestrictionForCallingPackage != 2) {
        return true;
      }
      Slog.w(
          "ActivityTaskManager",
          "Appop Denial: starting "
              + intent.toString()
              + " from "
              + windowProcessController
              + " (pid="
              + i2
              + ", uid="
              + i3
              + ") requires appop "
              + AppOpsManager.permissionToOp(activityInfo.permission));
      return false;
    }
    if (activityRecord != null) {
      activityRecord.sendResult(-1, str, i, 0, null, null);
    }
    if (actionRestrictionForCallingPackage == 1) {
      str4 =
          "Permission Denial: starting "
              + intent.toString()
              + " from "
              + windowProcessController
              + " (pid="
              + i2
              + ", uid="
              + i3
              + ") with revoked permission "
              + ((String) ACTION_TO_RUNTIME_PERMISSION.get(intent.getAction()));
    } else if (!activityInfo.exported) {
      str4 =
          "Permission Denial: starting "
              + intent.toString()
              + " from "
              + windowProcessController
              + " (pid="
              + i2
              + ", uid="
              + i3
              + ") not exported from uid "
              + activityInfo.applicationInfo.uid;
    } else {
      str4 =
          "Permission Denial: starting "
              + intent.toString()
              + " from "
              + windowProcessController
              + " (pid="
              + i2
              + ", uid="
              + i3
              + ") requires "
              + activityInfo.permission;
    }
    Slog.w("ActivityTaskManager", str4);
    throw new SecurityException(str4);
  }

  public boolean isCallerAllowedToLaunchOnTaskDisplayArea(
      int i, int i2, TaskDisplayArea taskDisplayArea, ActivityInfo activityInfo) {
    return isCallerAllowedToLaunchOnDisplay(
        i, i2, taskDisplayArea != null ? taskDisplayArea.getDisplayId() : 0, activityInfo);
  }

  public boolean isCallerAllowedToLaunchOnDisplay(
      int i, int i2, int i3, ActivityInfo activityInfo) {
    if (ProtoLogCache.WM_DEBUG_TASKS_enabled) {
      ProtoLogImpl.d(
          ProtoLogGroup.WM_DEBUG_TASKS,
          -1228653755,
          21,
          (String) null,
          new Object[] {Long.valueOf(i3), Long.valueOf(i), Long.valueOf(i2)});
    }
    if (i == -1 && i2 == -1) {
      if (ProtoLogCache.WM_DEBUG_TASKS_enabled) {
        ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_TASKS, 1524174282, 0, (String) null, (Object[]) null);
      }
      return true;
    }
    DisplayContent displayContentOrCreate = this.mRootWindowContainer.getDisplayContentOrCreate(i3);
    boolean z =
        (displayContentOrCreate == null
                || (displayContentOrCreate.getDisplayInfo().flags & 32768) == 0)
            ? false
            : true;
    if (displayContentOrCreate == null || displayContentOrCreate.isRemoved()) {
      Slog.w("ActivityTaskManager", "Launch on display check: display not found");
      return false;
    }
    if ((displayContentOrCreate.mDisplay.getFlags() & IInstalld.FLAG_FORCE) != 0) {
      Slog.w(
          "ActivityTaskManager",
          "Launch on display check: activity launch is not allowed on rear display");
      return false;
    }
    if (ActivityTaskManagerService.checkPermission(
            "android.permission.INTERNAL_SYSTEM_WINDOW", i, i2)
        == 0) {
      if (ProtoLogCache.WM_DEBUG_TASKS_enabled) {
        ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_TASKS, 431715812, 0, (String) null, (Object[]) null);
      }
      return true;
    }
    boolean isUidPresent = displayContentOrCreate.isUidPresent(i2);
    Display display = displayContentOrCreate.mDisplay;
    if (!display.isTrusted()) {
      PackageManager packageManager = this.mService.mContext.getPackageManager();
      if (!CoreRune.SYSFW_APP_SPEG || !z || packageManager == null || !packageManager.isSpeg(i2)) {
        if ((activityInfo.flags & Integer.MIN_VALUE) == 0) {
          if (ProtoLogCache.WM_DEBUG_TASKS_enabled) {
            ProtoLogImpl.d(
                ProtoLogGroup.WM_DEBUG_TASKS, -1474602871, 0, (String) null, (Object[]) null);
          }
          return false;
        }
        if (ActivityTaskManagerService.checkPermission(
                    "android.permission.ACTIVITY_EMBEDDING", i, i2)
                == -1
            && !isUidPresent) {
          if (ProtoLogCache.WM_DEBUG_TASKS_enabled) {
            ProtoLogImpl.d(
                ProtoLogGroup.WM_DEBUG_TASKS, 979347997, 0, (String) null, (Object[]) null);
          }
          return false;
        }
      } else {
        Slog.i("SPEG", "Ignore activity launch permission checking");
      }
    }
    if (!displayContentOrCreate.isPrivate()) {
      int userId = UserHandle.getUserId(i2);
      int displayId = display.getDisplayId();
      boolean isUserVisible = this.mWindowManager.mUmInternal.isUserVisible(userId, displayId);
      if (ProtoLogCache.WM_DEBUG_TASKS_enabled) {
        ProtoLogImpl.d(
            ProtoLogGroup.WM_DEBUG_TASKS,
            -1253056469,
            20,
            (String) null,
            new Object[] {
              isUserVisible ? "allow" : "disallow", Long.valueOf(userId), Long.valueOf(displayId)
            });
      }
      return isUserVisible;
    }
    if (display.getOwnerUid() == i2) {
      if (ProtoLogCache.WM_DEBUG_TASKS_enabled) {
        ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_TASKS, -856750101, 0, (String) null, (Object[]) null);
      }
      return true;
    }
    if (isUidPresent) {
      if (ProtoLogCache.WM_DEBUG_TASKS_enabled) {
        ProtoLogImpl.d(
            ProtoLogGroup.WM_DEBUG_TASKS, -1979455254, 0, (String) null, (Object[]) null);
      }
      return true;
    }
    Slog.w("ActivityTaskManager", "Launch on display check: denied");
    StringBuilder sb = new StringBuilder();
    sb.append("Launch on display that does not exist, d=");
    sb.append(displayContentOrCreate);
    sb.append(", isRemoved=" + displayContentOrCreate.isRemoved());
    Slog.d(StartingSurfaceController.TAG, sb.toString());
    return false;
  }

  public UserInfo getUserInfo(int i) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      return UserManager.get(this.mService.mContext).getUserInfo(i);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public int getDeviceIdForDisplayId(int i) {
    if (i == 0 || i == -1) {
      return 0;
    }
    if (this.mVirtualDeviceManager == null) {
      ActivityTaskManagerService activityTaskManagerService = this.mService;
      if (activityTaskManagerService.mHasCompanionDeviceSetupFeature) {
        this.mVirtualDeviceManager =
            (VirtualDeviceManager)
                activityTaskManagerService.mContext.getSystemService(VirtualDeviceManager.class);
      }
      if (this.mVirtualDeviceManager == null) {
        return 0;
      }
    }
    return this.mVirtualDeviceManager.getDeviceIdForDisplayId(i);
  }

  public final AppOpsManager getAppOpsManager() {
    if (this.mAppOpsManager == null) {
      this.mAppOpsManager =
          (AppOpsManager) this.mService.mContext.getSystemService(AppOpsManager.class);
    }
    return this.mAppOpsManager;
  }

  public final int getComponentRestrictionForCallingPackage(
      ActivityInfo activityInfo, String str, String str2, int i, int i2, boolean z) {
    int permissionToOpCode;
    if (!z
        && ActivityTaskManagerService.checkComponentPermission(
                activityInfo.permission,
                i,
                i2,
                activityInfo.applicationInfo.uid,
                activityInfo.exported)
            == -1) {
      return 1;
    }
    String str3 = activityInfo.permission;
    return (str3 == null
            || (permissionToOpCode = AppOpsManager.permissionToOpCode(str3)) == -1
            || getAppOpsManager().noteOpNoThrow(permissionToOpCode, i2, str, str2, "") == 0
            || z)
        ? 0
        : 2;
  }

  public final int getActionRestrictionForCallingPackage(
      String str, String str2, String str3, int i, int i2) {
    String str4;
    if (str == null || (str4 = (String) ACTION_TO_RUNTIME_PERMISSION.get(str)) == null) {
      return 0;
    }
    try {
      if (!ArrayUtils.contains(
          this.mService
              .mContext
              .getPackageManager()
              .getPackageInfoAsUser(str2, IInstalld.FLAG_USE_QUOTA, UserHandle.getUserId(i2))
              .requestedPermissions,
          str4)) {
        return 0;
      }
      if (ActivityTaskManagerService.checkPermission(str4, i, i2) == -1) {
        return 1;
      }
      int permissionToOpCode = AppOpsManager.permissionToOpCode(str4);
      if (permissionToOpCode == -1
          || getAppOpsManager().noteOpNoThrow(permissionToOpCode, i2, str2, str3, "") == 0) {
        return 0;
      }
      if ("android.permission.CAMERA".equals(str4)) {
        SensorPrivacyManagerInternal sensorPrivacyManagerInternal =
            (SensorPrivacyManagerInternal)
                LocalServices.getService(SensorPrivacyManagerInternal.class);
        UserHandle userHandleForUid = UserHandle.getUserHandleForUid(i2);
        if (sensorPrivacyManagerInternal.isSensorPrivacyEnabled(userHandleForUid.getIdentifier(), 2)
            && ((AppOpsManagerInternal) LocalServices.getService(AppOpsManagerInternal.class))
                    .getOpRestrictionCount(26, userHandleForUid, str2, (String) null)
                == 1) {
          return 0;
        }
      }
      return 2;
    } catch (PackageManager.NameNotFoundException unused) {
      Slog.i("ActivityTaskManager", "Cannot find package info for " + str2);
      return 0;
    }
  }

  public void setLaunchSource(int i) {
    this.mLaunchingActivityWakeLock.setWorkSource(new WorkSource(i));
  }

  public void acquireLaunchWakelock() {
    if (Binder.getCallingPid() != Process.myPid()) {
      Slog.w(
          "ActivityTaskManager",
          "acquireLaunchWakelock: callingPid="
              + Binder.getCallingPid()
              + ", myPid="
              + Process.myPid()
              + ", callers="
              + Debug.getCallers(20));
    }
    this.mLaunchingActivityWakeLock.acquire();
    if (this.mHandler.hasMessages(204)) {
      return;
    }
    this.mHandler.sendEmptyMessageDelayed(204, LAUNCH_TIMEOUT);
  }

  public final void checkFinishBootingLocked() {
    boolean isBooting = this.mService.isBooting();
    boolean z = false;
    this.mService.setBooting(false);
    if (!this.mService.isBooted()) {
      z = true;
      this.mService.setBooted(true);
    }
    if (isBooting || z) {
      this.mService.postFinishBooting(isBooting, z);
    }
  }

  public void activityIdleInternal(
      ActivityRecord activityRecord, boolean z, boolean z2, Configuration configuration) {
    if (activityRecord != null) {
      this.mHandler.removeMessages(200, activityRecord);
      activityRecord.finishLaunchTickingLocked();
      if (z) {
        reportActivityLaunched(z, activityRecord, -1L, -1);
      }
      if (configuration != null) {
        if (activityRecord.isDexMode()) {
          if ((configuration.dexCompatEnabled == 2)
              && !this.mService.mDexCompatController.shouldBeApplyDexCompatConfigurationLocked(
                  activityRecord, activityRecord.mProcessAppInfo, activityRecord.getDisplayId())) {
            configuration = activityRecord.getProcessGlobalConfiguration();
          }
        }
        activityRecord.setLastReportedGlobalConfiguration(configuration);
      }
      activityRecord.idle = true;
      if ((this.mService.isBooting() && this.mRootWindowContainer.allResumedActivitiesIdle())
          || z) {
        checkFinishBootingLocked();
      }
      activityRecord.mRelaunchReason = 0;
    }
    if (this.mRootWindowContainer.allResumedActivitiesIdle()) {
      if (activityRecord != null) {
        this.mService.scheduleAppGcsLocked();
        this.mRecentTasks.onActivityIdle(activityRecord);
      }
      if (this.mLaunchingActivityWakeLock.isHeld()) {
        this.mHandler.removeMessages(204);
        this.mLaunchingActivityWakeLock.release();
      }
      this.mRootWindowContainer.ensureActivitiesVisible(null, 0, false);
    }
    processStoppingAndFinishingActivities(activityRecord, z2, "idle");
    if (!this.mStartingUsers.isEmpty()) {
      ArrayList arrayList = new ArrayList(this.mStartingUsers);
      this.mStartingUsers.clear();
      for (int i = 0; i < arrayList.size(); i++) {
        UserState userState = (UserState) arrayList.get(i);
        Slogf.m96i(
            "ActivityTaskManager",
            "finishing switch of user %d",
            Integer.valueOf(userState.mHandle.getIdentifier()));
        this.mService.mAmInternal.finishUserSwitch(userState);
      }
    }
    this.mService.f1734mH.post(
        new Runnable() { // from class:
                         // com.android.server.wm.ActivityTaskSupervisor$$ExternalSyntheticLambda7
          @Override // java.lang.Runnable
          public final void run() {
            ActivityTaskSupervisor.this.lambda$activityIdleInternal$3();
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$activityIdleInternal$3() {
    this.mService.mAmInternal.trimApplications();
  }

  public void findTaskToMoveToFront(
      Task task, int i, ActivityOptions activityOptions, String str, boolean z) {
    findTaskToMoveToFront(task, i, activityOptions, str, z, false);
  }

  /* JADX WARN: Removed duplicated region for block: B:16:0x0058 A[Catch: all -> 0x01c6, TryCatch #1 {all -> 0x01c6, blocks: (B:86:0x0030, B:8:0x0032, B:10:0x0040, B:13:0x0049, B:14:0x0052, B:16:0x0058, B:17:0x005f, B:19:0x007d, B:21:0x0083, B:23:0x008b, B:24:0x00ad, B:27:0x00e3, B:30:0x00f0, B:32:0x00f6, B:34:0x00fc, B:36:0x0122, B:38:0x012a, B:40:0x0130, B:41:0x0135, B:47:0x0147, B:49:0x014d, B:50:0x0159, B:76:0x0162), top: B:85:0x0030 }] */
  /* JADX WARN: Removed duplicated region for block: B:36:0x0122 A[Catch: all -> 0x01c6, TryCatch #1 {all -> 0x01c6, blocks: (B:86:0x0030, B:8:0x0032, B:10:0x0040, B:13:0x0049, B:14:0x0052, B:16:0x0058, B:17:0x005f, B:19:0x007d, B:21:0x0083, B:23:0x008b, B:24:0x00ad, B:27:0x00e3, B:30:0x00f0, B:32:0x00f6, B:34:0x00fc, B:36:0x0122, B:38:0x012a, B:40:0x0130, B:41:0x0135, B:47:0x0147, B:49:0x014d, B:50:0x0159, B:76:0x0162), top: B:85:0x0030 }] */
  /* JADX WARN: Removed duplicated region for block: B:43:0x0142  */
  /* JADX WARN: Removed duplicated region for block: B:45:0x0144  */
  /* JADX WARN: Removed duplicated region for block: B:46:0x0127  */
  /* JADX WARN: Removed duplicated region for block: B:49:0x014d A[Catch: all -> 0x01c6, TryCatch #1 {all -> 0x01c6, blocks: (B:86:0x0030, B:8:0x0032, B:10:0x0040, B:13:0x0049, B:14:0x0052, B:16:0x0058, B:17:0x005f, B:19:0x007d, B:21:0x0083, B:23:0x008b, B:24:0x00ad, B:27:0x00e3, B:30:0x00f0, B:32:0x00f6, B:34:0x00fc, B:36:0x0122, B:38:0x012a, B:40:0x0130, B:41:0x0135, B:47:0x0147, B:49:0x014d, B:50:0x0159, B:76:0x0162), top: B:85:0x0030 }] */
  /* JADX WARN: Removed duplicated region for block: B:52:0x0160  */
  /* JADX WARN: Removed duplicated region for block: B:62:0x0190 A[Catch: all -> 0x01c4, TryCatch #0 {all -> 0x01c4, blocks: (B:55:0x016e, B:58:0x0186, B:62:0x0190, B:63:0x01a8, B:65:0x01ae, B:67:0x01b2, B:68:0x01b8), top: B:54:0x016e }] */
  /* JADX WARN: Removed duplicated region for block: B:63:0x01a8 A[Catch: all -> 0x01c4, TryCatch #0 {all -> 0x01c4, blocks: (B:55:0x016e, B:58:0x0186, B:62:0x0190, B:63:0x01a8, B:65:0x01ae, B:67:0x01b2, B:68:0x01b8), top: B:54:0x016e }] */
  /* JADX WARN: Removed duplicated region for block: B:65:0x01ae A[Catch: all -> 0x01c4, TryCatch #0 {all -> 0x01c4, blocks: (B:55:0x016e, B:58:0x0186, B:62:0x0190, B:63:0x01a8, B:65:0x01ae, B:67:0x01b2, B:68:0x01b8), top: B:54:0x016e }] */
  /* JADX WARN: Removed duplicated region for block: B:76:0x0162 A[Catch: all -> 0x01c6, TRY_LEAVE, TryCatch #1 {all -> 0x01c6, blocks: (B:86:0x0030, B:8:0x0032, B:10:0x0040, B:13:0x0049, B:14:0x0052, B:16:0x0058, B:17:0x005f, B:19:0x007d, B:21:0x0083, B:23:0x008b, B:24:0x00ad, B:27:0x00e3, B:30:0x00f0, B:32:0x00f6, B:34:0x00fc, B:36:0x0122, B:38:0x012a, B:40:0x0130, B:41:0x0135, B:47:0x0147, B:49:0x014d, B:50:0x0159, B:76:0x0162), top: B:85:0x0030 }] */
  /* JADX WARN: Removed duplicated region for block: B:81:0x0157  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public void findTaskToMoveToFront(
      Task task, int i, ActivityOptions activityOptions, String str, boolean z, boolean z2) {
    boolean z3;
    Transition transition;
    String str2;
    String str3;
    Task task2;
    boolean z4;
    String str4;
    ActivityRecord topNonFinishingActivity;
    Transition transition2;
    int launchDisplayId;
    TaskDisplayArea defaultTaskDisplayArea;
    String str5;
    Task rootTask = task.getRootTask();
    if (rootTask == null) {
      Slog.e(
          "ActivityTaskManager",
          "findTaskToMoveToFront: can't move task=" + task + " to front. Root task is null");
      return;
    }
    if ((i & 2) == 0) {
      try {
        this.mUserLeaving = true;
      } catch (Throwable th) {
        th = th;
        z3 = false;
        this.mUserLeaving = z3;
        this.mService.continueWindowLayout();
        throw th;
      }
    }
    this.mService.deferWindowLayout();
    try {
      if (task.mTransitionController.isShellTransitionsEnabled()
          && !task.mTransitionController.isCollecting()) {
        transition = task.mTransitionController.createTransition(3);
        if (task.isFreeformForceHidden()) {
          this.mService.mFreeformController.releaseForceHideTaskLocked(task);
        }
        task.mTransitionController.collect(task);
        str2 = str + " findTaskToMoveToFront";
        if (task.isResizeable() || !canUseActivityOptionsLaunchBounds(activityOptions)) {
          str3 = str2;
          task2 = rootTask;
          z4 = false;
        } else {
          Task orCreateRootTask =
              this.mRootWindowContainer.getOrCreateRootTask(null, activityOptions, task, true);
          if (orCreateRootTask != rootTask) {
            moveHomeRootTaskToFrontIfNeeded(i, orCreateRootTask.getDisplayArea(), str2);
            task.reparent(orCreateRootTask, true, 1, false, true, str2);
            str5 = str2;
            rootTask = orCreateRootTask;
            z4 = true;
          } else {
            str5 = str2;
            z4 = false;
          }
          LaunchParamsController.LaunchParams launchParams =
              new LaunchParamsController.LaunchParams();
          str3 = str5;
          getLaunchParamsController()
              .calculate(
                  task,
                  null,
                  task.getTopNonFinishingActivity(),
                  null,
                  activityOptions,
                  null,
                  3,
                  launchParams);
          task.setBounds(launchParams.mBounds);
          task2 = rootTask;
        }
        if (!z4 && activityOptions != null) {
          int displayId = task2.getDisplayId();
          launchDisplayId = activityOptions.getLaunchDisplayId();
          if (launchDisplayId != -1
              && displayId != launchDisplayId
              && task2.isActivityTypeStandard()
              && task2.inFullscreenWindowingMode()) {
            Slog.d(
                "ActivityTaskManager",
                "findTaskToMoveToFront: move rootTask "
                    + task2
                    + " to display #"
                    + launchDisplayId);
            DisplayContent displayContentOrCreate =
                this.mService.mRootWindowContainer.getDisplayContentOrCreate(launchDisplayId);
            defaultTaskDisplayArea =
                displayContentOrCreate == null
                    ? displayContentOrCreate.getDefaultTaskDisplayArea()
                    : null;
            if (defaultTaskDisplayArea != null && !defaultTaskDisplayArea.isVisibleRequested()) {
              task.mTransitionController.collect(defaultTaskDisplayArea);
            }
            this.mService.moveRootTaskToDisplay(task2.mTaskId, launchDisplayId);
            z4 = task2.getDisplayId() != launchDisplayId;
          }
        }
        boolean isFreeformForceHidden = task.isFreeformForceHidden();
        if (z4) {
          str4 = str3;
          moveHomeRootTaskToFrontIfNeeded(i, task2.getDisplayArea(), str4);
        } else {
          str4 = str3;
        }
        topNonFinishingActivity = task.getTopNonFinishingActivity();
        transition2 = transition;
        z3 = false;
        task2.moveTaskToFront(
            task,
            false,
            activityOptions,
            topNonFinishingActivity != null ? null : topNonFinishingActivity.appTimeTracker,
            str4);
        handleNonResizableTaskIfNeeded(
            task, 0, this.mRootWindowContainer.getDefaultTaskDisplayArea(), task2, z);
        if (topNonFinishingActivity != null
            && ((activityOptions == null || !activityOptions.getDisableStartingWindow())
                && !isFreeformForceHidden)) {
          if (!z2) {
            topNonFinishingActivity.showStartingWindow(
                null,
                false,
                true,
                topNonFinishingActivity.isProcessRunning(),
                false,
                null,
                null,
                true);
          } else {
            topNonFinishingActivity.showStartingWindow(true);
          }
        }
        if (transition2 != null) {
          task.mTransitionController.requestStartTransition(
              transition2,
              task,
              activityOptions != null ? activityOptions.getRemoteTransition() : null,
              null);
        }
        this.mUserLeaving = false;
        this.mService.continueWindowLayout();
        return;
      }
      task2.moveTaskToFront(
          task,
          false,
          activityOptions,
          topNonFinishingActivity != null ? null : topNonFinishingActivity.appTimeTracker,
          str4);
      handleNonResizableTaskIfNeeded(
          task, 0, this.mRootWindowContainer.getDefaultTaskDisplayArea(), task2, z);
      if (topNonFinishingActivity != null) {
        if (!z2) {}
      }
      if (transition2 != null) {}
      this.mUserLeaving = false;
      this.mService.continueWindowLayout();
      return;
    } catch (Throwable th2) {
      th = th2;
      this.mUserLeaving = z3;
      this.mService.continueWindowLayout();
      throw th;
    }
    transition = null;
    if (task.isFreeformForceHidden()) {}
    task.mTransitionController.collect(task);
    str2 = str + " findTaskToMoveToFront";
    if (task.isResizeable()) {}
    str3 = str2;
    task2 = rootTask;
    z4 = false;
    if (!z4) {
      int displayId2 = task2.getDisplayId();
      launchDisplayId = activityOptions.getLaunchDisplayId();
      if (launchDisplayId != -1) {
        Slog.d(
            "ActivityTaskManager",
            "findTaskToMoveToFront: move rootTask " + task2 + " to display #" + launchDisplayId);
        DisplayContent displayContentOrCreate2 =
            this.mService.mRootWindowContainer.getDisplayContentOrCreate(launchDisplayId);
        if (displayContentOrCreate2 == null) {}
        if (defaultTaskDisplayArea != null) {
          task.mTransitionController.collect(defaultTaskDisplayArea);
        }
        this.mService.moveRootTaskToDisplay(task2.mTaskId, launchDisplayId);
        z4 = task2.getDisplayId() != launchDisplayId;
      }
    }
    boolean isFreeformForceHidden2 = task.isFreeformForceHidden();
    if (z4) {}
    topNonFinishingActivity = task.getTopNonFinishingActivity();
    transition2 = transition;
    z3 = false;
  }

  public final void moveHomeRootTaskToFrontIfNeeded(
      int i, TaskDisplayArea taskDisplayArea, String str) {
    Task focusedRootTask = taskDisplayArea.getFocusedRootTask();
    if ((taskDisplayArea.getWindowingMode() != 1 || (i & 1) == 0)
        && (focusedRootTask == null || !focusedRootTask.isActivityTypeRecents())) {
      return;
    }
    taskDisplayArea.moveHomeRootTaskToFront(str);
  }

  public boolean canUseActivityOptionsLaunchBounds(ActivityOptions activityOptions) {
    if (activityOptions == null || activityOptions.getLaunchBounds() == null) {
      return false;
    }
    return (this.mService.mSupportsPictureInPicture
            && activityOptions.getLaunchWindowingMode() == 2)
        || this.mService.mSupportsFreeformWindowManagement;
  }

  public LaunchParamsController getLaunchParamsController() {
    return this.mLaunchParamsController;
  }

  public final void removePinnedRootTaskInSurfaceTransaction(Task task) {
    task.cancelAnimation();
    task.setForceHidden(1, true);
    task.ensureActivitiesVisible(null, 0, true);
    activityIdleInternal(null, false, true, null);
    DisplayContent displayContent = this.mRootWindowContainer.getDisplayContent(0);
    this.mService.deferWindowLayout();
    try {
      setDeferRootVisibilityUpdate(true);
      task.setWindowingMode(0);
      if (task.getWindowingMode() != 5) {
        task.setBounds(null);
      }
      displayContent.getDefaultTaskDisplayArea().positionTaskBehindHome(task);
      setDeferRootVisibilityUpdate(false);
      task.setForceHidden(1, false);
      this.mRootWindowContainer.ensureActivitiesVisible(null, 0, true);
      this.mRootWindowContainer.resumeFocusedTasksTopActivities();
    } finally {
      this.mService.continueWindowLayout();
    }
  }

  /* renamed from: removeRootTaskInSurfaceTransaction, reason: merged with bridge method [inline-methods] */
  public final void lambda$removeRootTask$5(Task task) {
    if (task.getWindowingMode() == 2) {
      removePinnedRootTaskInSurfaceTransaction(task);
    } else {
      task.forAllLeafTasks(
          new Consumer() { // from class:
                           // com.android.server.wm.ActivityTaskSupervisor$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              ActivityTaskSupervisor.this.lambda$removeRootTaskInSurfaceTransaction$4((Task) obj);
            }
          },
          true);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$removeRootTaskInSurfaceTransaction$4(Task task) {
    removeTask(task, true, true, "remove-root-task");
  }

  public void removeRootTask(final Task task) {
    this.mWindowManager.inSurfaceTransaction(
        new Runnable() { // from class:
                         // com.android.server.wm.ActivityTaskSupervisor$$ExternalSyntheticLambda10
          @Override // java.lang.Runnable
          public final void run() {
            ActivityTaskSupervisor.this.lambda$removeRootTask$5(task);
          }
        });
  }

  public boolean removeTaskById(int i, boolean z, boolean z2, String str, int i2) {
    return removeTaskById(i, z, z2, false, str, i2);
  }

  public boolean removeTaskById(int i, boolean z, boolean z2, boolean z3, String str, int i2) {
    Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i, 1);
    if (anyTaskForId != null) {
      removeTask(anyTaskForId, z, z2, z3, str, i2, (String) null);
      return true;
    }
    Slog.w("ActivityTaskManager", "Request to remove task ignored for non-existent task " + i);
    return false;
  }

  public void removeTask(Task task, boolean z, boolean z2, String str) {
    removeTask(task, z, z2, str, 1000, null);
  }

  public void removeTask(Task task, boolean z, boolean z2, String str, int i, String str2) {
    removeTask(task, z, z2, str, 1000, (String) null, false);
  }

  public void removeTask(
      Task task, boolean z, boolean z2, String str, int i, String str2, boolean z3) {
    removeTask(task, z, z2, str, false, i, str2, z3);
  }

  public void removeTask(
      Task task, boolean z, boolean z2, boolean z3, String str, int i, String str2) {
    removeTask(task, z, z2, str, z3, i, str2, false);
  }

  public void removeTask(
      Task task, boolean z, boolean z2, String str, boolean z3, int i, String str2, boolean z4) {
    ActivityRecord topNonFinishingActivity;
    if (task.mInRemoveTask) {
      return;
    }
    task.mTransitionController.requestCloseTransitionIfNeeded(task);
    if (z) {
      ArrayList arrayList = null;
      for (int size = this.mStoppingActivities.size() - 1; size >= 0; size--) {
        ActivityRecord activityRecord = (ActivityRecord) this.mStoppingActivities.get(size);
        if (activityRecord.getTask() == task) {
          if (arrayList == null) {
            arrayList = new ArrayList();
          }
          arrayList.add(activityRecord);
          this.mStoppingActivities.remove(size);
        }
      }
      if (arrayList != null) {
        for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
          ((ActivityRecord) arrayList.get(size2)).stopIfPossible();
        }
      }
    }
    task.mInRemoveTask = true;
    if (CoreRune.MW_FREEFORM_DISMISS_VIEW) {
      task.mRemoveByDrag = z3;
    }
    try {
      if (z) {
        if (this.mService.isForceStopDisabled(task, z4)) {
          return;
        }
      } else if (this.mService.isForceStopDisabledWithoutToast(task, z4)) {
        return;
      }
      if (!task.isDexMode()
          && (topNonFinishingActivity = task.getTopNonFinishingActivity()) != null
          && topNonFinishingActivity.mUserId != task.mUserId
          && !z2
          && !task.inFreeformWindowingMode()
          && "com.android.systemui.keyguard.WorkLockActivity"
              .equalsIgnoreCase(topNonFinishingActivity.mActivityComponent.getClassName())) {
        topNonFinishingActivity.setVisibility(false);
        task.moveTaskToBack(task);
        return;
      }
      task.removeActivities(str, false);
      task.reason = str;
      cleanUpRemovedTask(task, z, z2);
      this.mService.getLockTaskController().clearLockedTask(task);
      this.mService.getTaskChangeNotificationController().notifyTaskStackChanged();
      if (task.isPersistable) {
        this.mService.notifyTaskPersisterLocked(null, true);
      }
      checkActivitySecurityForTaskClear(i, task, str2);
    } finally {
      task.mInRemoveTask = false;
    }
  }

  /* JADX WARN: Multi-variable type inference failed */
  /* JADX WARN: Type inference failed for: r2v11, types: [java.lang.CharSequence] */
  public final void checkActivitySecurityForTaskClear(int i, Task task, String str) {
    TaskDisplayArea taskDisplayArea;
    final String applicationLabel;
    String str2;
    if (i == 1000
        || !task.isVisible()
        || task.inMultiWindowMode()
        || (taskDisplayArea = task.getTaskDisplayArea()) == null) {
      return;
    }
    boolean z =
        !((Boolean) doesTopActivityMatchingUidExistForAsm(task, i, null).first).booleanValue();
    if (!((Boolean) r2.second).booleanValue()) {
      ActivityRecord activity =
          task.getActivity(
              new Predicate() { // from class:
                                // com.android.server.wm.ActivityTaskSupervisor$$ExternalSyntheticLambda5
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                  boolean lambda$checkActivitySecurityForTaskClear$6;
                  lambda$checkActivitySecurityForTaskClear$6 =
                      ActivityTaskSupervisor.lambda$checkActivitySecurityForTaskClear$6(
                          (ActivityRecord) obj);
                  return lambda$checkActivitySecurityForTaskClear$6;
                }
              });
      FrameworkStatsLog.write(
          FrameworkStatsLog.ACTIVITY_ACTION_BLOCKED,
          i,
          str,
          activity == null ? -1 : activity.getUid(),
          activity != null ? activity.info.name : null,
          false,
          -1,
          (String) null,
          (String) null,
          0,
          4,
          7,
          false,
          -1);
      final boolean z2 = ActivitySecurityModelFeatureFlags.shouldRestrictActivitySwitch(i) && z;
      PackageManager packageManager = this.mService.mContext.getPackageManager();
      String nameForUid = packageManager.getNameForUid(i);
      if (nameForUid == null) {
        applicationLabel = String.valueOf(i);
        str2 = applicationLabel;
      } else {
        applicationLabel = getApplicationLabel(packageManager, nameForUid);
        str2 = nameForUid;
      }
      if (ActivitySecurityModelFeatureFlags.shouldShowToast(i)) {
        UiThread.getHandler()
            .post(
                new Runnable() { // from class:
                                 // com.android.server.wm.ActivityTaskSupervisor$$ExternalSyntheticLambda6
                  @Override // java.lang.Runnable
                  public final void run() {
                    ActivityTaskSupervisor.this.lambda$checkActivitySecurityForTaskClear$7(
                        z2, applicationLabel);
                  }
                });
      }
      if (z2) {
        Slog.w(
            "ActivityTaskManager",
            "[ASM] Return to home as source: " + str2 + " is not on top of task t: " + task);
        taskDisplayArea.moveHomeActivityToTop("taskRemoved");
        return;
      }
      Slog.i(
          "ActivityTaskManager",
          "[ASM] Would return to home as source: " + str2 + " is not on top of task t: " + task);
    }
  }

  public static /* synthetic */ boolean lambda$checkActivitySecurityForTaskClear$6(
      ActivityRecord activityRecord) {
    return (activityRecord.finishing || activityRecord.isAlwaysOnTop()) ? false : true;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$checkActivitySecurityForTaskClear$7(
      boolean z, CharSequence charSequence) {
    Context context = this.mService.mContext;
    StringBuilder sb = new StringBuilder();
    sb.append("go/android-asm");
    sb.append(z ? " returned home due to " : " would return home due to ");
    sb.append((Object) charSequence);
    Toast.makeText(context, sb.toString(), 1).show();
  }

  public static Pair doesTopActivityMatchingUidExistForAsm(
      Task task, int i, final ActivityRecord activityRecord) {
    if (activityRecord != null && activityRecord.isVisible()) {
      Boolean bool = Boolean.TRUE;
      return new Pair(bool, bool);
    }
    ActivityRecord topMostActivity = task.getTopMostActivity();
    if (topMostActivity != null && topMostActivity.isUid(i)) {
      Boolean bool2 = Boolean.TRUE;
      return new Pair(bool2, bool2);
    }
    Predicate predicate =
        new Predicate() { // from class:
                          // com.android.server.wm.ActivityTaskSupervisor$$ExternalSyntheticLambda14
          @Override // java.util.function.Predicate
          public final boolean test(Object obj) {
            boolean lambda$doesTopActivityMatchingUidExistForAsm$8;
            lambda$doesTopActivityMatchingUidExistForAsm$8 =
                ActivityTaskSupervisor.lambda$doesTopActivityMatchingUidExistForAsm$8(
                    ActivityRecord.this, (ActivityRecord) obj);
            return lambda$doesTopActivityMatchingUidExistForAsm$8;
          }
        };
    ActivityRecord activity = task.getActivity(predicate);
    if (activity == null) {
      Boolean bool3 = Boolean.FALSE;
      return new Pair(bool3, bool3);
    }
    Pair allowCrossUidActivitySwitchFromBelow = activity.allowCrossUidActivitySwitchFromBelow(i);
    if (((Boolean) allowCrossUidActivitySwitchFromBelow.first).booleanValue()) {
      return new Pair(Boolean.TRUE, (Boolean) allowCrossUidActivitySwitchFromBelow.second);
    }
    TaskFragment taskFragment = activity.getTaskFragment();
    if (taskFragment == null) {
      Boolean bool4 = Boolean.FALSE;
      return new Pair(bool4, bool4);
    }
    TaskFragment adjacentTaskFragment = taskFragment.getAdjacentTaskFragment();
    if (adjacentTaskFragment == null) {
      Boolean bool5 = Boolean.FALSE;
      return new Pair(bool5, bool5);
    }
    ActivityRecord activity2 = adjacentTaskFragment.getActivity(predicate);
    if (activity2 == null) {
      Boolean bool6 = Boolean.FALSE;
      return new Pair(bool6, bool6);
    }
    return activity2.allowCrossUidActivitySwitchFromBelow(i);
  }

  public static /* synthetic */ boolean lambda$doesTopActivityMatchingUidExistForAsm$8(
      ActivityRecord activityRecord, ActivityRecord activityRecord2) {
    return activityRecord2.equals(activityRecord)
        || !(activityRecord2.finishing || activityRecord2.isAlwaysOnTop());
  }

  public static CharSequence getApplicationLabel(PackageManager packageManager, String str) {
    try {
      return packageManager.getApplicationLabel(
          packageManager.getApplicationInfo(str, PackageManager.ApplicationInfoFlags.of(0L)));
    } catch (PackageManager.NameNotFoundException unused) {
      return str;
    }
  }

  public final void cleanUpRemovedTask(Task task, boolean z, boolean z2) {
    if (z2) {
      this.mRecentTasks.remove(task);
    }
    Intent baseIntent = task.getBaseIntent();
    ComponentName component = baseIntent != null ? baseIntent.getComponent() : null;
    if (component == null) {
      Slog.w("ActivityTaskManager", "No component for base intent of task: " + task);
      return;
    }
    this.mService.f1734mH.sendMessage(
        PooledLambda.obtainMessage(
            new QuadConsumer() { // from class:
                                 // com.android.server.wm.ActivityTaskSupervisor$$ExternalSyntheticLambda8
              public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
                ((ActivityManagerInternal) obj)
                    .cleanUpServices(
                        ((Integer) obj2).intValue(), (ComponentName) obj3, (Intent) obj4);
              }
            },
            this.mService.mAmInternal,
            Integer.valueOf(task.mUserId),
            component,
            new Intent(baseIntent)));
    if (z) {
      ActivityRecord topMostActivity = task.getTopMostActivity();
      if (topMostActivity != null
          && topMostActivity.finishing
          && !topMostActivity.mAppStopped
          && topMostActivity.lastVisibleTime > 0
          && !task.mKillProcessesOnDestroyed) {
        if (CoreRune.MW_FREEFORM_DISMISS_VIEW && task.mRemoveByDrag) {
          return;
        }
        task.mKillProcessesOnDestroyed = true;
        ActivityTaskSupervisorHandler activityTaskSupervisorHandler = this.mHandler;
        activityTaskSupervisorHandler.sendMessageDelayed(
            activityTaskSupervisorHandler.obtainMessage(206, task), 1000L);
        return;
      }
      killTaskProcessesIfPossible(task);
    }
  }

  public void killTaskProcessesOnDestroyedIfNeeded(Task task) {
    if (task == null || !task.mKillProcessesOnDestroyed) {
      return;
    }
    this.mHandler.removeMessages(206, task);
    killTaskProcessesIfPossible(task);
  }

  public final void killTaskProcessesIfPossible(Task task) {
    task.mKillProcessesOnDestroyed = false;
    String basePackageName = task.getBasePackageName();
    ArrayMap map = this.mService.mProcessNames.getMap();
    ArrayList arrayList = null;
    for (int i = 0; i < map.size(); i++) {
      SparseArray sparseArray = (SparseArray) map.valueAt(i);
      for (int i2 = 0; i2 < sparseArray.size(); i2++) {
        WindowProcessController windowProcessController =
            (WindowProcessController) sparseArray.valueAt(i2);
        if (windowProcessController.mUserId == task.mUserId
            && windowProcessController != this.mService.mHomeProcess
            && windowProcessController.containsPackage(basePackageName)) {
          if (!windowProcessController.shouldKillProcessForRemovedTask(task)) {
            return;
          }
          if (windowProcessController.hasForegroundServices()
              && !this.mService.mAmInternal.isAutoRunBlockedApp(
                  windowProcessController.mInfo.packageName, windowProcessController.mUserId)) {
            return;
          }
          String str = task.reason;
          if (str != null && "setFixedAspectRatioPackages".contains(str)) {
            windowProcessController.mReason = "setFixedAspectRatioPackages";
          } else {
            windowProcessController.mReason = "kill";
          }
          if (arrayList == null) {
            arrayList = new ArrayList();
          }
          arrayList.add(windowProcessController);
        }
      }
    }
    if (arrayList == null) {
      return;
    }
    this.mService.f1734mH.sendMessage(
        PooledLambda.obtainMessage(
            new BiConsumer() { // from class:
                               // com.android.server.wm.ActivityTaskSupervisor$$ExternalSyntheticLambda12
              @Override // java.util.function.BiConsumer
              public final void accept(Object obj, Object obj2) {
                ((ActivityManagerInternal) obj).killProcessesForRemovedTask((ArrayList) obj2);
              }
            },
            this.mService.mAmInternal,
            arrayList));
  }

  public boolean restoreRecentTaskLocked(Task task, ActivityOptions activityOptions, boolean z) {
    Task orCreateRootTask =
        this.mRootWindowContainer.getOrCreateRootTask(null, activityOptions, task, z);
    WindowContainer parent = task.getParent();
    if (parent != orCreateRootTask && task != orCreateRootTask) {
      if (parent != null) {
        task.reparent(orCreateRootTask, Integer.MAX_VALUE, true, "restoreRecentTaskLocked");
        return true;
      }
      orCreateRootTask.addChild((WindowContainer) task, z, true);
    }
    return true;
  }

  @Override // com.android.server.wm.RecentTasks.Callbacks
  public void onRecentTaskAdded(Task task) {
    task.touchActiveTime();
  }

  @Override // com.android.server.wm.RecentTasks.Callbacks
  public void onRecentTaskRemoved(Task task, boolean z, boolean z2) {
    if (z) {
      removeTaskById(task.mTaskId, z2, false, "recent-task-trimmed", 1000);
    }
    task.removedFromRecents();
  }

  public Task getReparentTargetRootTask(Task task, Task task2, boolean z) {
    Task rootTask = task.getRootTask();
    int i = task2.mTaskId;
    boolean inMultiWindowMode = task2.inMultiWindowMode();
    if (rootTask != null && rootTask.mTaskId == i) {
      Slog.w(
          "ActivityTaskManager",
          "Can not reparent to same root task, task=" + task + " already in rootTaskId=" + i);
      return rootTask;
    }
    if (inMultiWindowMode && !this.mService.mSupportsMultiWindow) {
      throw new IllegalArgumentException(
          "Device doesn't support multi-window, can not reparent task="
              + task
              + " to root-task="
              + task2);
    }
    if (task2.getDisplayId() != 0 && !this.mService.mSupportsMultiDisplay) {
      throw new IllegalArgumentException(
          "Device doesn't support multi-display, can not reparent task="
              + task
              + " to rootTaskId="
              + i);
    }
    if (task2.getWindowingMode() == 5 && !this.mService.mSupportsFreeformWindowManagement) {
      throw new IllegalArgumentException(
          "Device doesn't support freeform, can not reparent task=" + task);
    }
    if (task2.inPinnedWindowingMode()) {
      throw new IllegalArgumentException("No support to reparent to PIP, task=" + task);
    }
    if (!inMultiWindowMode
        || task.supportsMultiWindowInDisplayArea(task2.getDisplayArea())
        || task2.isDexMode()) {
      return task2;
    }
    Slog.w(
        "ActivityTaskManager",
        "Can not move unresizeable task="
            + task
            + " to multi-window root task="
            + task2
            + " Moving to a fullscreen root task instead.");
    return rootTask != null
        ? rootTask
        : task2.getDisplayArea().createRootTask(1, task2.getActivityType(), z);
  }

  public void goingToSleepLocked() {
    scheduleSleepTimeout();
    if (!this.mGoingToSleepWakeLock.isHeld()) {
      this.mGoingToSleepWakeLock.acquire();
      if (this.mLaunchingActivityWakeLock.isHeld()) {
        this.mLaunchingActivityWakeLock.release();
        this.mHandler.removeMessages(204);
      }
    }
    this.mRootWindowContainer.applySleepTokens(false);
    checkReadyForSleepLocked(true);
  }

  public boolean shutdownLocked(int i) {
    boolean z;
    goingToSleepLocked();
    long currentTimeMillis = System.currentTimeMillis() + i;
    while (true) {
      z = true;
      if (!this.mRootWindowContainer.putTasksToSleep(true, true)) {
        long currentTimeMillis2 = currentTimeMillis - System.currentTimeMillis();
        if (currentTimeMillis2 > 0) {
          try {
            this.mService.mGlobalLock.wait(currentTimeMillis2);
          } catch (InterruptedException unused) {
          }
        } else {
          Slog.w("ActivityTaskManager", "Activity manager shutdown timed out");
          break;
        }
      } else {
        z = false;
        break;
      }
    }
    checkReadyForSleepLocked(false);
    return z;
  }

  public void comeOutOfSleepIfNeededLocked() {
    removeSleepTimeouts();
    if (this.mGoingToSleepWakeLock.isHeld()) {
      this.mGoingToSleepWakeLock.release();
    }
  }

  public void checkReadyForSleepLocked(boolean z) {
    if (this.mService.isSleepingOrShuttingDownLocked()
        && this.mRootWindowContainer.putTasksToSleep(z, false)) {
      this.mService.endLaunchPowerMode(3);
      this.mRootWindowContainer.rankTaskLayers();
      removeSleepTimeouts();
      if (this.mGoingToSleepWakeLock.isHeld()) {
        this.mGoingToSleepWakeLock.release();
      }
      ActivityTaskManagerService activityTaskManagerService = this.mService;
      if (activityTaskManagerService.mShuttingDown) {
        activityTaskManagerService.mGlobalLock.notifyAll();
      }
    }
  }

  public boolean reportResumedActivityLocked(ActivityRecord activityRecord) {
    this.mStoppingActivities.remove(activityRecord);
    if (!activityRecord.getRootTask().getDisplayArea().allResumedActivitiesComplete()) {
      return false;
    }
    this.mRootWindowContainer.ensureActivitiesVisible(null, 0, false);
    this.mRootWindowContainer.executeAppTransitionForAllDisplay();
    return true;
  }

  public final void handleLaunchTaskBehindCompleteLocked(ActivityRecord activityRecord) {
    Task task = activityRecord.getTask();
    Task rootTask = task.getRootTask();
    this.mRecentTasks.add(task);
    this.mService.getTaskChangeNotificationController().notifyTaskStackChanged();
    rootTask.ensureActivitiesVisible(null, 0, false);
    ActivityRecord topNonFinishingActivity = rootTask.getTopNonFinishingActivity();
    if (topNonFinishingActivity != null) {
      topNonFinishingActivity.getTask().touchActiveTime();
    }
  }

  public void scheduleLaunchTaskBehindComplete(IBinder iBinder) {
    this.mHandler.obtainMessage(212, iBinder).sendToTarget();
  }

  public void processStoppingAndFinishingActivities(
      ActivityRecord activityRecord, boolean z, String str) {
    int i = 0;
    boolean z2 = false;
    ArrayList arrayList = null;
    while (i < this.mStoppingActivities.size()) {
      ActivityRecord activityRecord2 = (ActivityRecord) this.mStoppingActivities.get(i);
      boolean z3 =
          (!activityRecord2.isInTransition()
                  || activityRecord2.getTask() == null
                  || activityRecord2.getTask().isForceHidden())
              ? false
              : true;
      z2 |= activityRecord2.isDisplaySleepingAndSwapping();
      if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_STATES,
            -1740512980,
            60,
            (String) null,
            new Object[] {
              String.valueOf(activityRecord2),
              Boolean.valueOf(activityRecord2.nowVisible),
              Boolean.valueOf(z3),
              String.valueOf(activityRecord2.finishing)
            });
      }
      if ((!z3 && !z2) || this.mService.mShuttingDown) {
        if (!z && activityRecord2.isState(ActivityRecord.State.PAUSING)) {
          removeIdleTimeoutForActivity(activityRecord);
          scheduleIdleTimeout(activityRecord);
        } else {
          if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
            ProtoLogImpl.v(
                ProtoLogGroup.WM_DEBUG_STATES,
                -1707370822,
                0,
                (String) null,
                new Object[] {String.valueOf(activityRecord2)});
          }
          if (arrayList == null) {
            arrayList = new ArrayList();
          }
          arrayList.add(activityRecord2);
          this.mStoppingActivities.remove(i);
          i--;
        }
      }
      i++;
    }
    if (z2) {
      this.mHandler.postDelayed(
          new Runnable() { // from class:
                           // com.android.server.wm.ActivityTaskSupervisor$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
              ActivityTaskSupervisor.this.lambda$processStoppingAndFinishingActivities$9();
            }
          },
          200L);
    }
    int size = arrayList == null ? 0 : arrayList.size();
    for (int i2 = 0; i2 < size; i2++) {
      ActivityRecord activityRecord3 = (ActivityRecord) arrayList.get(i2);
      if (activityRecord3.isInHistory()) {
        if (activityRecord3.finishing) {
          activityRecord3.destroyIfPossible(str);
        } else {
          activityRecord3.stopIfPossible();
        }
      }
    }
    int size2 = this.mFinishingActivities.size();
    if (size2 == 0) {
      return;
    }
    ArrayList arrayList2 = new ArrayList(this.mFinishingActivities);
    this.mFinishingActivities.clear();
    for (int i3 = 0; i3 < size2; i3++) {
      ActivityRecord activityRecord4 = (ActivityRecord) arrayList2.get(i3);
      if (activityRecord4.isInHistory()) {
        activityRecord4.destroyImmediately("finish-" + str);
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$processStoppingAndFinishingActivities$9() {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        scheduleProcessStoppingAndFinishingActivitiesIfNeeded();
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public void removeHistoryRecords(WindowProcessController windowProcessController) {
    removeHistoryRecords(this.mStoppingActivities, windowProcessController, "mStoppingActivities");
    removeHistoryRecords(
        this.mFinishingActivities, windowProcessController, "mFinishingActivities");
    removeHistoryRecords(
        this.mNoHistoryActivities, windowProcessController, "mNoHistoryActivities");
  }

  public final void removeHistoryRecords(
      ArrayList arrayList, WindowProcessController windowProcessController, String str) {
    int size = arrayList.size();
    while (size > 0) {
      size--;
      ActivityRecord activityRecord = (ActivityRecord) arrayList.get(size);
      if (activityRecord.app == windowProcessController) {
        arrayList.remove(size);
        activityRecord.removeTimeouts();
      }
    }
  }

  public void dump(PrintWriter printWriter, String str) {
    printWriter.println();
    printWriter.println("ActivityTaskSupervisor state:");
    this.mRootWindowContainer.dump(printWriter, str, true);
    getKeyguardController().dump(printWriter, str);
    this.mService.getLockTaskController().dump(printWriter, str);
    printWriter.print(str);
    printWriter.println("mCurTaskIdForUser=" + this.mCurTaskIdForUser);
    printWriter.println(
        str + "mUserRootTaskInFront=" + this.mRootWindowContainer.mUserRootTaskInFront);
    printWriter.println(str + "mVisibilityTransactionDepth=" + this.mVisibilityTransactionDepth);
    printWriter.print(str);
    printWriter.print("isHomeRecentsComponent=");
    printWriter.println(
        this.mRecentTasks.isRecentsComponentHomeActivity(this.mRootWindowContainer.mCurrentUser));
    if (!this.mWaitingActivityLaunched.isEmpty()) {
      printWriter.println(str + "mWaitingActivityLaunched=");
      for (int size = this.mWaitingActivityLaunched.size() - 1; size >= 0; size += -1) {
        ((WaitInfo) this.mWaitingActivityLaunched.get(size)).dump(printWriter, str + "  ");
      }
    }
    printWriter.println(str + "mNoHistoryActivities=" + this.mNoHistoryActivities);
    printWriter.println();
  }

  public static boolean printThisActivity(
      PrintWriter printWriter,
      ActivityRecord activityRecord,
      String str,
      boolean z,
      String str2,
      Runnable runnable) {
    return printThisActivity(printWriter, activityRecord, str, -1, z, str2, runnable);
  }

  public static boolean printThisActivity(
      PrintWriter printWriter,
      ActivityRecord activityRecord,
      String str,
      int i,
      boolean z,
      String str2,
      Runnable runnable) {
    if (activityRecord == null) {
      return false;
    }
    if (i != -1 && i != activityRecord.getDisplayId()) {
      return false;
    }
    if (str != null && !str.equals(activityRecord.packageName)) {
      return false;
    }
    if (z) {
      printWriter.println();
    }
    if (runnable != null) {
      runnable.run();
    }
    printWriter.print(str2);
    printWriter.println(activityRecord);
    return true;
  }

  public static boolean dumpHistoryList(
      FileDescriptor fileDescriptor,
      PrintWriter printWriter,
      List list,
      String str,
      String str2,
      boolean z,
      boolean z2,
      boolean z3,
      String str3,
      boolean z4,
      Runnable runnable,
      Task task) {
    int size = list.size() - 1;
    boolean z5 = z4;
    Runnable runnable2 = runnable;
    Task task2 = task;
    while (size >= 0) {
      ActivityRecord activityRecord = (ActivityRecord) list.get(size);
      ActivityRecord.dumpActivity(
          fileDescriptor,
          printWriter,
          size,
          activityRecord,
          str,
          str2,
          z,
          z2,
          z3,
          str3,
          z5,
          runnable2,
          task2);
      task2 = activityRecord.getTask();
      z5 = z3 && activityRecord.attachedToProcess();
      size--;
      runnable2 = null;
    }
    return false;
  }

  public void scheduleIdleTimeout(ActivityRecord activityRecord) {
    this.mHandler.sendMessageDelayed(
        this.mHandler.obtainMessage(200, activityRecord), IDLE_TIMEOUT);
  }

  public final void scheduleIdle() {
    if (this.mHandler.hasMessages(201)) {
      return;
    }
    this.mHandler.sendEmptyMessage(201);
  }

  public void updateTopResumedActivityIfNeeded(String str) {
    ActivityRecord activityRecord = this.mTopResumedActivity;
    Task topDisplayFocusedRootTask = this.mRootWindowContainer.getTopDisplayFocusedRootTask();
    if (topDisplayFocusedRootTask == null
        || topDisplayFocusedRootTask.getTopResumedActivity() == activityRecord) {
      if (this.mService.isSleepingLocked()) {
        this.mService.updateTopApp(null);
        return;
      }
      return;
    }
    if (((activityRecord == null || this.mTopResumedActivityWaitingForPrev) ? false : true)
        && activityRecord.scheduleTopResumedActivityChanged(false)) {
      scheduleTopResumedStateLossTimeout(activityRecord);
      this.mTopResumedActivityWaitingForPrev = true;
    }
    ActivityRecord topResumedActivity = topDisplayFocusedRootTask.getTopResumedActivity();
    this.mTopResumedActivity = topResumedActivity;
    if (topResumedActivity != null && activityRecord != null) {
      WindowProcessController windowProcessController = topResumedActivity.app;
      if (windowProcessController != null) {
        windowProcessController.addToPendingTop();
      }
      this.mService.updateOomAdj();
    }
    ActivityRecord activityRecord2 = this.mTopResumedActivity;
    if (activityRecord2 != null) {
      this.mService.setLastResumedActivityUncheckLocked(activityRecord2, str);
    }
    scheduleTopResumedActivityStateIfNeeded();
    this.mService.updateTopApp(this.mTopResumedActivity);
  }

  public final void scheduleTopResumedActivityStateIfNeeded() {
    ActivityRecord activityRecord = this.mTopResumedActivity;
    if (activityRecord == null || this.mTopResumedActivityWaitingForPrev) {
      return;
    }
    activityRecord.scheduleTopResumedActivityChanged(true);
  }

  public final void scheduleTopResumedStateLossTimeout(ActivityRecord activityRecord) {
    Message obtainMessage =
        this.mHandler.obtainMessage(
            FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__SET_APPLICATION_EXEMPTIONS);
    obtainMessage.obj = activityRecord;
    activityRecord.topResumedStateLossTime = SystemClock.uptimeMillis();
    this.mHandler.sendMessageDelayed(obtainMessage, 500L);
    if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
      ProtoLogImpl.v(
          ProtoLogGroup.WM_DEBUG_STATES,
          74885950,
          0,
          (String) null,
          new Object[] {String.valueOf(activityRecord)});
    }
  }

  public void handleTopResumedStateReleased(boolean z) {
    if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
      ProtoLogImpl.v(
          ProtoLogGroup.WM_DEBUG_STATES,
          708142634,
          0,
          (String) null,
          new Object[] {z ? "(due to timeout)" : "(transition complete)"});
    }
    this.mHandler.removeMessages(
        FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__SET_APPLICATION_EXEMPTIONS);
    if (this.mTopResumedActivityWaitingForPrev) {
      this.mTopResumedActivityWaitingForPrev = false;
      scheduleTopResumedActivityStateIfNeeded();
    }
  }

  public void removeIdleTimeoutForActivity(ActivityRecord activityRecord) {
    this.mHandler.removeMessages(200, activityRecord);
  }

  public final void scheduleResumeTopActivities() {
    if (this.mHandler.hasMessages(202)) {
      return;
    }
    this.mHandler.sendEmptyMessage(202);
  }

  public void scheduleProcessStoppingAndFinishingActivitiesIfNeeded() {
    if (this.mStoppingActivities.isEmpty() && this.mFinishingActivities.isEmpty()) {
      return;
    }
    if (this.mRootWindowContainer.allResumedActivitiesIdle()) {
      scheduleIdle();
    } else {
      if (this.mHandler.hasMessages(205)
          || !this.mRootWindowContainer.allResumedActivitiesVisible()) {
        return;
      }
      this.mHandler.sendEmptyMessage(205);
    }
  }

  public void removeSleepTimeouts() {
    this.mHandler.removeMessages(203);
  }

  public final void scheduleSleepTimeout() {
    removeSleepTimeouts();
    this.mHandler.sendEmptyMessageDelayed(203, SLEEP_TIMEOUT);
  }

  public boolean hasScheduledRestartTimeouts(ActivityRecord activityRecord) {
    return this.mHandler.hasMessages(213, activityRecord);
  }

  public void removeRestartTimeouts(ActivityRecord activityRecord) {
    this.mHandler.removeMessages(213, activityRecord);
  }

  public final void scheduleRestartTimeout(ActivityRecord activityRecord) {
    removeRestartTimeouts(activityRecord);
    ActivityTaskSupervisorHandler activityTaskSupervisorHandler = this.mHandler;
    activityTaskSupervisorHandler.sendMessageDelayed(
        activityTaskSupervisorHandler.obtainMessage(213, activityRecord), 2000L);
  }

  public void handleNonResizableTaskIfNeeded(
      Task task, int i, TaskDisplayArea taskDisplayArea, Task task2) {
    handleNonResizableTaskIfNeeded(task, i, taskDisplayArea, task2, false);
  }

  public void handleNonResizableTaskIfNeeded(
      Task task, int i, TaskDisplayArea taskDisplayArea, Task task2, boolean z) {
    boolean z2 = (taskDisplayArea == null || taskDisplayArea.getDisplayId() == 0) ? false : true;
    boolean z3 = task2 != null && task2.getWindowingMode() == 5;
    if (task.isActivityTypeStandardOrUndefined() || z3) {
      if (this.mService.mDexController.getDexModeLocked() != 0
          && ((taskDisplayArea != null && taskDisplayArea.getDisplayId() == 2)
              || (task2 != null && task2.getDisplayId() == 2))) {
        if (this.mService.isKeyguardLocked(0)) {
          Slog.d(
              "ActivityTaskManager",
              "skip handleNonResizableTaskIfNeeded by KeyguardLocked, task=" + task);
          return;
        }
        if (z3 || i == 5) {
          handleForcedResizableTaskIfNeeded(task, 4);
          return;
        }
        return;
      }
      if (z2) {
        if (!task.canBeLaunchedOnDisplay(task.getDisplayId())) {
          throw new IllegalStateException("Task resolved to incompatible display");
        }
        DisplayContent displayContent = taskDisplayArea.mDisplayContent;
        if (displayContent == task.getDisplayContent()) {
          if (z) {
            return;
          }
          handleForcedResizableTaskIfNeeded(task, 2);
          return;
        }
        Slog.w(
            "ActivityTaskManager",
            "Failed to put " + task + " on display " + displayContent.mDisplayId);
        this.mService
            .getTaskChangeNotificationController()
            .notifyActivityLaunchOnSecondaryDisplayFailed(
                task.getTaskInfo(), displayContent.mDisplayId);
        return;
      }
      if (task.supportsMultiWindow()
          || taskDisplayArea == null
          || !taskDisplayArea.isSplitScreenModeActivated()) {
        if (z) {
          return;
        }
        if (z3) {
          handleForcedResizableTaskIfNeeded(task, 3);
          return;
        } else {
          handleForcedResizableTaskIfNeeded(task, 1);
          return;
        }
      }
      if (taskDisplayArea.getDisplayContent() == null
          || taskDisplayArea.getDisplayContent().isSleeping()) {
        return;
      }
      ActivityRecord activityRecord = task.topRunningActivity();
      String str = null;
      ActivityOptions options = activityRecord != null ? activityRecord.getOptions() : null;
      if ((options == null || !options.isDismissSplitBeforeLaunch())
          && taskDisplayArea.getRootMainStageTask().getRootTask().hasChild(task)) {
        Intent baseIntent = task.getBaseIntent();
        if (baseIntent != null && baseIntent.getComponent() != null) {
          str = baseIntent.getComponent().getPackageName();
        }
        this.mService
            .getTaskChangeNotificationController()
            .notifyActivityDismissingDockedRootTask(str);
      }
    }
  }

  public final void handleForcedResizableTaskIfNeeded(Task task, int i) {
    ActivityRecord topNonFinishingActivity = task.getTopNonFinishingActivity();
    if (topNonFinishingActivity == null
        || topNonFinishingActivity.noDisplay
        || !topNonFinishingActivity.canForceResizeNonResizable(task.getWindowingMode())) {
      return;
    }
    if (CoreRune.SAFE_DEBUG) {
      Slog.d(
          "ActivityTaskManager",
          "handleNonResizableTaskIfNeeded: task="
              + task
              + " reason="
              + i
              + " packageName="
              + topNonFinishingActivity.info.applicationInfo.packageName);
    }
    if (this.mWindowManager.mExt.mExtraDisplayPolicy.shouldNotHandleForcedResizableTaskIfNeeded(
        task.getDisplayId(), i)) {
      return;
    }
    if (CoreRune.BAIDU_CARLIFE && topNonFinishingActivity.getDisplayContent().isCarLifeDisplay()) {
      return;
    }
    this.mService
        .getTaskChangeNotificationController()
        .notifyActivityForcedResizable(
            task.mTaskId, i, topNonFinishingActivity.info.applicationInfo.packageName);
  }

  public void scheduleUpdateMultiWindowMode(Task task) {
    task.forAllActivities(
        new Consumer() { // from class:
                         // com.android.server.wm.ActivityTaskSupervisor$$ExternalSyntheticLambda2
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            ActivityTaskSupervisor.this.lambda$scheduleUpdateMultiWindowMode$10(
                (ActivityRecord) obj);
          }
        });
    if (this.mHandler.hasMessages(FrameworkStatsLog.f654xe56e23e8)) {
      return;
    }
    this.mHandler.sendEmptyMessage(FrameworkStatsLog.f654xe56e23e8);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$scheduleUpdateMultiWindowMode$10(
      ActivityRecord activityRecord) {
    if (activityRecord.attachedToProcess()) {
      this.mMultiWindowModeChangedActivities.add(activityRecord);
    }
  }

  public void scheduleUpdatePictureInPictureModeIfNeeded(Task task, Task task2) {
    Task rootTask = task.getRootTask();
    if (task2 != null) {
      if (task2 == rootTask || task2.inPinnedWindowingMode() || rootTask.inPinnedWindowingMode()) {
        scheduleUpdatePictureInPictureModeIfNeeded(task, rootTask.getRequestedOverrideBounds());
      }
    }
  }

  public void scheduleUpdatePictureInPictureModeIfNeeded(Task task, Rect rect) {
    task.forAllActivities(
        new Consumer() { // from class:
                         // com.android.server.wm.ActivityTaskSupervisor$$ExternalSyntheticLambda1
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            ActivityTaskSupervisor.this.lambda$scheduleUpdatePictureInPictureModeIfNeeded$11(
                (ActivityRecord) obj);
          }
        });
    this.mPipModeChangedTargetRootTaskBounds = rect;
    if (this.mHandler.hasMessages(FrameworkStatsLog.f653x532b6133)) {
      return;
    }
    this.mHandler.sendEmptyMessage(FrameworkStatsLog.f653x532b6133);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$scheduleUpdatePictureInPictureModeIfNeeded$11(
      ActivityRecord activityRecord) {
    if (activityRecord.attachedToProcess()) {
      this.mPipModeChangedActivities.add(activityRecord);
      this.mMultiWindowModeChangedActivities.remove(activityRecord);
    }
  }

  public void wakeUp(String str) {
    this.mPowerManager.wakeUp(SystemClock.uptimeMillis(), 2, "android.server.am:TURN_ON:" + str);
  }

  public void beginActivityVisibilityUpdate() {
    beginActivityVisibilityUpdate(null);
  }

  public void beginActivityVisibilityUpdate(DisplayContent displayContent) {
    if ((displayContent == null || displayContent.isOnTop())
        && this.mVisibilityTransactionDepth == 0) {
      getKeyguardController().updateVisibility();
    }
    this.mVisibilityTransactionDepth++;
  }

  public void endActivityVisibilityUpdate() {
    int i = this.mVisibilityTransactionDepth - 1;
    this.mVisibilityTransactionDepth = i;
    if (i == 0) {
      computeProcessActivityStateBatch();
    }
  }

  public boolean inActivityVisibilityUpdate() {
    return this.mVisibilityTransactionDepth > 0;
  }

  public void setDeferRootVisibilityUpdate(boolean z) {
    this.mDeferRootVisibilityUpdate = z;
  }

  public boolean isRootVisibilityUpdateDeferred() {
    return this.mDeferRootVisibilityUpdate;
  }

  public void onProcessActivityStateChanged(
      WindowProcessController windowProcessController, boolean z) {
    if (z || inActivityVisibilityUpdate()) {
      if (this.mActivityStateChangedProcs.contains(windowProcessController)) {
        return;
      }
      this.mActivityStateChangedProcs.add(windowProcessController);
      return;
    }
    windowProcessController.computeProcessActivityState();
  }

  public void computeProcessActivityStateBatch() {
    if (this.mActivityStateChangedProcs.isEmpty()) {
      return;
    }
    for (int size = this.mActivityStateChangedProcs.size() - 1; size >= 0; size--) {
      ((WindowProcessController) this.mActivityStateChangedProcs.get(size))
          .computeProcessActivityState();
    }
    this.mActivityStateChangedProcs.clear();
  }

  public void beginDeferResume() {
    this.mDeferResumeCount++;
  }

  public void endDeferResume() {
    this.mDeferResumeCount--;
  }

  public boolean readyToResume() {
    return this.mDeferResumeCount == 0;
  }

  public final class ActivityTaskSupervisorHandler extends Handler {
    public ActivityTaskSupervisorHandler(Looper looper) {
      super(looper);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      String str;
      int i;
      WindowManagerGlobalLock windowManagerGlobalLock =
          ActivityTaskSupervisor.this.mService.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          if (handleMessageInner(message)) {
            return;
          }
          WindowManagerService.resetPriorityAfterLockedSection();
          if (message.what != 213) {
            return;
          }
          ActivityRecord activityRecord = (ActivityRecord) message.obj;
          WindowManagerGlobalLock windowManagerGlobalLock2 =
              ActivityTaskSupervisor.this.mService.mGlobalLock;
          WindowManagerService.boostPriorityForLockedSection();
          synchronized (windowManagerGlobalLock2) {
            try {
              if (activityRecord.attachedToProcess()
                  && activityRecord.isState(ActivityRecord.State.RESTARTING_PROCESS)) {
                WindowProcessController windowProcessController = activityRecord.app;
                str = windowProcessController.mName;
                i = windowProcessController.mUid;
              } else {
                str = null;
                i = 0;
              }
            } finally {
            }
          }
          WindowManagerService.resetPriorityAfterLockedSection();
          if (str != null) {
            ActivityTaskSupervisor.this.mService.mAmInternal.killProcess(
                str, i, "restartActivityProcessTimeout");
          }
        } finally {
          WindowManagerService.resetPriorityAfterLockedSection();
        }
      }
    }

    public final void activityIdleFromMessage(ActivityRecord activityRecord, boolean z) {
      ActivityTaskSupervisor.this.activityIdleInternal(activityRecord, z, z, null);
    }

    public final boolean handleMessageInner(Message message) {
      int i = message.what;
      if (i != 212) {
        switch (i) {
          case 200:
            activityIdleFromMessage((ActivityRecord) message.obj, true);
            break;
          case 201:
            activityIdleFromMessage((ActivityRecord) message.obj, false);
            break;
          case 202:
            ActivityTaskSupervisor.this.mRootWindowContainer.resumeFocusedTasksTopActivities();
            break;
          case 203:
            if (ActivityTaskSupervisor.this.mService.isSleepingOrShuttingDownLocked()) {
              Slog.w("ActivityTaskManager", "Sleep timeout!  Sleeping now.");
              ActivityTaskSupervisor.this.checkReadyForSleepLocked(false);
              break;
            }
            break;
          case 204:
            if (ActivityTaskSupervisor.this.mLaunchingActivityWakeLock.isHeld()) {
              Slog.w("ActivityTaskManager", "Launch timeout has expired, giving up wake lock!");
              ActivityTaskSupervisor.this.mLaunchingActivityWakeLock.release();
              break;
            }
            break;
          case 205:
            ActivityTaskSupervisor.this.processStoppingAndFinishingActivities(
                null, false, "transit");
            break;
          case 206:
            Task task = (Task) message.obj;
            if (task.mKillProcessesOnDestroyed) {
              Slog.i(
                  "ActivityTaskManager", "Destroy timeout of remove-task, attempt to kill " + task);
              ActivityTaskSupervisor.this.killTaskProcessesIfPossible(task);
              break;
            }
            break;
          default:
            switch (i) {
              case FrameworkStatsLog.f654xe56e23e8 /* 214 */:
                for (int size =
                        ActivityTaskSupervisor.this.mMultiWindowModeChangedActivities.size() - 1;
                    size >= 0;
                    size--) {
                  ((ActivityRecord)
                          ActivityTaskSupervisor.this.mMultiWindowModeChangedActivities.remove(
                              size))
                      .updateMultiWindowMode();
                }
                break;
              case FrameworkStatsLog.f653x532b6133 /* 215 */:
                for (int size2 = ActivityTaskSupervisor.this.mPipModeChangedActivities.size() - 1;
                    size2 >= 0;
                    size2--) {
                  ((ActivityRecord)
                          ActivityTaskSupervisor.this.mPipModeChangedActivities.remove(size2))
                      .updatePictureInPictureMode(
                          ActivityTaskSupervisor.this.mPipModeChangedTargetRootTaskBounds, false);
                }
                break;
              case FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__SET_MTE_POLICY /* 216 */:
                ActivityTaskSupervisor.this.mHandler.removeMessages(
                    FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__SET_MTE_POLICY);
                ActivityTaskSupervisor.this.mRootWindowContainer.startHomeOnEmptyDisplays(
                    (String) message.obj);
                break;
              case FrameworkStatsLog
                  .DEVICE_POLICY_EVENT__EVENT_ID__SET_APPLICATION_EXEMPTIONS /* 217 */:
                ActivityRecord activityRecord = (ActivityRecord) message.obj;
                Slog.w(
                    "ActivityTaskManager",
                    "Activity top resumed state loss timeout for " + activityRecord);
                if (activityRecord.hasProcess()) {
                  ActivityTaskSupervisor.this.mService.logAppTooSlow(
                      activityRecord.app,
                      activityRecord.topResumedStateLossTime,
                      "top state loss for " + activityRecord);
                }
                ActivityTaskSupervisor.this.handleTopResumedStateReleased(true);
                break;
              default:
                return false;
            }
        }
      } else {
        ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked((IBinder) message.obj);
        if (forTokenLocked != null) {
          ActivityTaskSupervisor.this.handleLaunchTaskBehindCompleteLocked(forTokenLocked);
        }
      }
      return true;
    }
  }

  public int startActivityFromRecents(
      int i, int i2, int i3, SafeActivityOptions safeActivityOptions) {
    return startActivityFromRecents(i, i2, i3, safeActivityOptions, false);
  }

  /* JADX WARN: Code restructure failed: missing block: B:141:0x021c, code lost:

     if (r2.isMultiTaskingDisplay() != false) goto L147;
  */
  /* JADX WARN: Code restructure failed: missing block: B:142:0x020a, code lost:

     r9 = false;
     r18 = false;
  */
  /* JADX WARN: Code restructure failed: missing block: B:338:0x0208, code lost:

     if (r2.isDexMode() != false) goto L147;
  */
  /* JADX WARN: Finally extract failed */
  /* JADX WARN: Removed duplicated region for block: B:247:0x03ef A[Catch: all -> 0x056c, TryCatch #10 {all -> 0x056c, blocks: (B:8:0x0022, B:10:0x0034, B:13:0x0042, B:16:0x004e, B:18:0x0056, B:20:0x0061, B:23:0x006c, B:25:0x0072, B:26:0x0075, B:33:0x008c, B:36:0x0096, B:38:0x009c, B:41:0x00af, B:42:0x00b5, B:47:0x00c5, B:49:0x00cf, B:50:0x00d8, B:56:0x00e1, B:58:0x00eb, B:59:0x00ee, B:64:0x00f9, B:66:0x0100, B:72:0x010e, B:74:0x0114, B:75:0x011c, B:77:0x0123, B:79:0x0129, B:81:0x013b, B:83:0x0141, B:84:0x0144, B:87:0x014a, B:89:0x0150, B:91:0x0156, B:93:0x015f, B:97:0x0169, B:99:0x016f, B:101:0x0175, B:103:0x017b, B:106:0x018a, B:108:0x0190, B:110:0x0196, B:112:0x019c, B:114:0x01a2, B:118:0x01ad, B:120:0x01bd, B:122:0x01c3, B:126:0x01d2, B:128:0x01d8, B:130:0x01de, B:132:0x01e9, B:223:0x03b8, B:224:0x03bd, B:231:0x03b2, B:232:0x03b7, B:245:0x03d7, B:247:0x03ef, B:248:0x03f8, B:354:0x0549, B:355:0x054e, B:360:0x0543, B:361:0x0548, B:365:0x054f, B:366:0x056b, B:357:0x0537, B:228:0x03a6, B:326:0x0527, B:327:0x052f, B:345:0x0533), top: B:7:0x0022, inners: #2, #12, #13 }] */
  /* JADX WARN: Removed duplicated region for block: B:257:0x0458 A[EXC_TOP_SPLITTER, SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:25:0x0072 A[Catch: all -> 0x056c, TryCatch #10 {all -> 0x056c, blocks: (B:8:0x0022, B:10:0x0034, B:13:0x0042, B:16:0x004e, B:18:0x0056, B:20:0x0061, B:23:0x006c, B:25:0x0072, B:26:0x0075, B:33:0x008c, B:36:0x0096, B:38:0x009c, B:41:0x00af, B:42:0x00b5, B:47:0x00c5, B:49:0x00cf, B:50:0x00d8, B:56:0x00e1, B:58:0x00eb, B:59:0x00ee, B:64:0x00f9, B:66:0x0100, B:72:0x010e, B:74:0x0114, B:75:0x011c, B:77:0x0123, B:79:0x0129, B:81:0x013b, B:83:0x0141, B:84:0x0144, B:87:0x014a, B:89:0x0150, B:91:0x0156, B:93:0x015f, B:97:0x0169, B:99:0x016f, B:101:0x0175, B:103:0x017b, B:106:0x018a, B:108:0x0190, B:110:0x0196, B:112:0x019c, B:114:0x01a2, B:118:0x01ad, B:120:0x01bd, B:122:0x01c3, B:126:0x01d2, B:128:0x01d8, B:130:0x01de, B:132:0x01e9, B:223:0x03b8, B:224:0x03bd, B:231:0x03b2, B:232:0x03b7, B:245:0x03d7, B:247:0x03ef, B:248:0x03f8, B:354:0x0549, B:355:0x054e, B:360:0x0543, B:361:0x0548, B:365:0x054f, B:366:0x056b, B:357:0x0537, B:228:0x03a6, B:326:0x0527, B:327:0x052f, B:345:0x0533), top: B:7:0x0022, inners: #2, #12, #13 }] */
  /* JADX WARN: Removed duplicated region for block: B:28:0x007b  */
  /* JADX WARN: Removed duplicated region for block: B:312:0x03f6  */
  /* JADX WARN: Removed duplicated region for block: B:367:0x007e  */
  /* JADX WARN: Removed duplicated region for block: B:74:0x0114 A[Catch: all -> 0x056c, TryCatch #10 {all -> 0x056c, blocks: (B:8:0x0022, B:10:0x0034, B:13:0x0042, B:16:0x004e, B:18:0x0056, B:20:0x0061, B:23:0x006c, B:25:0x0072, B:26:0x0075, B:33:0x008c, B:36:0x0096, B:38:0x009c, B:41:0x00af, B:42:0x00b5, B:47:0x00c5, B:49:0x00cf, B:50:0x00d8, B:56:0x00e1, B:58:0x00eb, B:59:0x00ee, B:64:0x00f9, B:66:0x0100, B:72:0x010e, B:74:0x0114, B:75:0x011c, B:77:0x0123, B:79:0x0129, B:81:0x013b, B:83:0x0141, B:84:0x0144, B:87:0x014a, B:89:0x0150, B:91:0x0156, B:93:0x015f, B:97:0x0169, B:99:0x016f, B:101:0x0175, B:103:0x017b, B:106:0x018a, B:108:0x0190, B:110:0x0196, B:112:0x019c, B:114:0x01a2, B:118:0x01ad, B:120:0x01bd, B:122:0x01c3, B:126:0x01d2, B:128:0x01d8, B:130:0x01de, B:132:0x01e9, B:223:0x03b8, B:224:0x03bd, B:231:0x03b2, B:232:0x03b7, B:245:0x03d7, B:247:0x03ef, B:248:0x03f8, B:354:0x0549, B:355:0x054e, B:360:0x0543, B:361:0x0548, B:365:0x054f, B:366:0x056b, B:357:0x0537, B:228:0x03a6, B:326:0x0527, B:327:0x052f, B:345:0x0533), top: B:7:0x0022, inners: #2, #12, #13 }] */
  /* JADX WARN: Removed duplicated region for block: B:75:0x011c A[Catch: all -> 0x056c, TryCatch #10 {all -> 0x056c, blocks: (B:8:0x0022, B:10:0x0034, B:13:0x0042, B:16:0x004e, B:18:0x0056, B:20:0x0061, B:23:0x006c, B:25:0x0072, B:26:0x0075, B:33:0x008c, B:36:0x0096, B:38:0x009c, B:41:0x00af, B:42:0x00b5, B:47:0x00c5, B:49:0x00cf, B:50:0x00d8, B:56:0x00e1, B:58:0x00eb, B:59:0x00ee, B:64:0x00f9, B:66:0x0100, B:72:0x010e, B:74:0x0114, B:75:0x011c, B:77:0x0123, B:79:0x0129, B:81:0x013b, B:83:0x0141, B:84:0x0144, B:87:0x014a, B:89:0x0150, B:91:0x0156, B:93:0x015f, B:97:0x0169, B:99:0x016f, B:101:0x0175, B:103:0x017b, B:106:0x018a, B:108:0x0190, B:110:0x0196, B:112:0x019c, B:114:0x01a2, B:118:0x01ad, B:120:0x01bd, B:122:0x01c3, B:126:0x01d2, B:128:0x01d8, B:130:0x01de, B:132:0x01e9, B:223:0x03b8, B:224:0x03bd, B:231:0x03b2, B:232:0x03b7, B:245:0x03d7, B:247:0x03ef, B:248:0x03f8, B:354:0x0549, B:355:0x054e, B:360:0x0543, B:361:0x0548, B:365:0x054f, B:366:0x056b, B:357:0x0537, B:228:0x03a6, B:326:0x0527, B:327:0x052f, B:345:0x0533), top: B:7:0x0022, inners: #2, #12, #13 }] */
  /* JADX WARN: Removed duplicated region for block: B:7:0x0022 A[EXC_TOP_SPLITTER, SYNTHETIC] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public int startActivityFromRecents(
      int i, int i2, int i3, SafeActivityOptions safeActivityOptions, boolean z) {
    ActivityOptions activityOptions;
    ActivityOptions makeBasic;
    WindowManagerGlobalLock windowManagerGlobalLock;
    boolean z2;
    int i4;
    boolean z3;
    int i5;
    int i6;
    boolean z4;
    boolean z5;
    int i7;
    Task task;
    int i8;
    ActivityOptions activityOptions2;
    int i9;
    int i10;
    ActivityOptions activityOptions3;
    WindowManagerGlobalLock windowManagerGlobalLock2;
    Task task2;
    ActivityRecord activityRecord;
    int dexModeLocked;
    Task topRootStageTask;
    boolean z6;
    ActivityRecord nonStartableActivityInDexMode;
    if (safeActivityOptions != null) {
      makeBasic = safeActivityOptions.getOptions(this);
    } else if (z) {
      makeBasic = ActivityOptions.makeBasic();
    } else {
      activityOptions = null;
      windowManagerGlobalLock = this.mService.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          boolean isCallerRecents = this.mRecentTasks.isCallerRecents(i2);
          TaskDisplayArea defaultTaskDisplayArea =
              this.mRootWindowContainer.getDefaultTaskDisplayArea();
          boolean isSplitScreenModeActivated = defaultTaskDisplayArea.isSplitScreenModeActivated();
          Task fromWindowContainerToken =
              activityOptions != null
                  ? Task.fromWindowContainerToken(activityOptions.getLaunchRootTask())
                  : null;
          if (activityOptions != null) {
            i5 = activityOptions.getLaunchActivityType();
            if (activityOptions.freezeRecentTasksReordering()
                && (isCallerRecents
                    || ActivityTaskManagerService.checkPermission(
                            "android.permission.MANAGE_ACTIVITY_TASKS", i, i2)
                        == 0)) {
              this.mRecentTasks.setFreezeTaskListReordering();
              if (activityOptions.getRemoteTransition() == null
                  && this.mWindowManager.getRecentsAnimationController() != null) {
                z3 = true;
                i4 = activityOptions.getLaunchWindowingMode();
                if (i4 == 5) {
                  activityOptions.setForceLaunchWindowingMode(i4);
                }
                z2 = activityOptions.getLaunchRootTask() != null;
              }
            }
            z3 = false;
            i4 = activityOptions.getLaunchWindowingMode();
            if (i4 == 5) {}
            if (activityOptions.getLaunchRootTask() != null) {}
          } else {
            z2 = true;
            i4 = 0;
            z3 = false;
            i5 = 0;
          }
          if (i5 == 2 || i5 == 3) {
            throw new IllegalArgumentException(
                "startActivityFromRecents: Task "
                    + i3
                    + " can't be launch in the home/recents root task.");
          }
          Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i3, 1);
          if (z3 && anyTaskForId != null && anyTaskForId.inFreeformWindowingMode()) {
            this.mWindowManager
                .getRecentsAnimationController()
                .cancelAnimation(2, "startActivityFromRecents-freeformByGesture");
            z2 = false;
          }
          if (anyTaskForId != null) {
            int launchDisplayId =
                activityOptions != null ? activityOptions.getLaunchDisplayId() : -1;
            int dexModeLocked2 = this.mService.mDexController.getDexModeLocked();
            if ((dexModeLocked2 == 1 || (dexModeLocked2 == 2 && launchDisplayId == 2))
                && (nonStartableActivityInDexMode =
                        this.mService.mDexController.getNonStartableActivityInDexMode(anyTaskForId))
                    != null) {
              this.mService.mDexController.showWarningToastIfNeeded(
                  nonStartableActivityInDexMode.info);
              WindowManagerService.resetPriorityAfterLockedSection();
              return 102;
            }
          }
          if (anyTaskForId != null
              && this.mService.mMultiTaskingController.interceptStartActivityFromRecentsLocked(
                  anyTaskForId, activityOptions, i, i2)) {
            ActivityOptions.abort(activityOptions);
            WindowManagerService.resetPriorityAfterLockedSection();
            return 10;
          }
          if (anyTaskForId == null || activityOptions == null) {
            i6 = -1;
          } else {
            int launchDisplayId2 = activityOptions.getLaunchDisplayId();
            i6 = -1;
            if (launchDisplayId2 != -1 && anyTaskForId.getDisplayId() != launchDisplayId2) {
              z6 = false;
              if (i4 == 0 && z6) {
                if (!anyTaskForId.isMinimized()) {
                  activityOptions.setLaunchWindowingMode(anyTaskForId.getWindowingMode());
                } else if (anyTaskForId.getWindowingMode() == 5
                    && anyTaskForId.topRunningActivity() != null) {
                  this.mService.mMultiTaskingController.removeMoveToBackTaskWithIme(i3);
                  activityOptions.setLaunchWindowingMode(anyTaskForId.getWindowingMode());
                }
              }
            }
            z6 = true;
            if (i4 == 0) {
              if (!anyTaskForId.isMinimized()) {}
            }
          }
          if (anyTaskForId != null && !anyTaskForId.hasChild()) {
            anyTaskForId.mSkipSavingLaunchingState = true;
          }
          int windowingMode =
              (!CoreRune.MW_SA_LOGGING
                      || anyTaskForId == null
                      || (!anyTaskForId.isMinimized()
                          && anyTaskForId.getTopVisibleActivity() == null))
                  ? 0
                  : anyTaskForId.getWindowingMode();
          if (activityOptions != null && activityOptions.getLaunchedFromDnD()) {
            z2 = false;
          }
          if (activityOptions != null
              && activityOptions.getLaunchInFocusedStageRoot()
              && defaultTaskDisplayArea.isSplitScreenModeActivated()
              && (topRootStageTask = defaultTaskDisplayArea.getTopRootStageTask()) != null) {
            activityOptions.setLaunchRootTask(topRootStageTask.getTaskInfo().token);
            z2 = false;
          }
          if (anyTaskForId != null) {
            if (activityOptions != null
                && activityOptions.allowEnterPipWhileLaunching()
                && activityOptions.getLaunchDisplayId() <= 0
                && activityOptions.getLaunchRootTask() == null
                && activityOptions.getLaunchWindowingMode() == 0
                && activityOptions.getForceLaunchWindowingMode() == 0) {
              ActivityRecord findEnterPipOnTaskSwitchCandidate =
                  Task.findEnterPipOnTaskSwitchCandidate(
                      this.mRootWindowContainer.getDefaultTaskDisplayArea().getTopRootTask());
              if (findEnterPipOnTaskSwitchCandidate != null
                  && findEnterPipOnTaskSwitchCandidate.getTask() != anyTaskForId) {
                Task.enableEnterPipOnTaskSwitch(
                    findEnterPipOnTaskSwitchCandidate, anyTaskForId, null, activityOptions);
                anyTaskForId.intent.removeFlags(262144);
              }
              z2 = false;
            }
          }
          if (z && !anyTaskForId.isVisible() && anyTaskForId.inSplitScreenWindowingMode()) {
            activityOptions.setForceLaunchWindowingMode(1);
            activityOptions.setLaunchDisplayId(anyTaskForId.getDisplayId());
          }
          try {
            this.mService.deferWindowLayout();
            try {
              z4 = true;
              Task anyTaskForId2 =
                  this.mRootWindowContainer.anyTaskForId(i3, 2, activityOptions, true);
              try {
                if (anyTaskForId2 == null) {
                  this.mWindowManager.executeAppTransition();
                  throw new IllegalArgumentException(
                      "startActivityFromRecents: Task " + i3 + " not found.");
                }
                DisplayContent displayContent =
                    this.mRootWindowContainer.getDisplayContent(anyTaskForId2.getDisplayId());
                if (displayContent != null) {
                  try {
                  } catch (RuntimeException e) {
                    throw e;
                  } catch (Throwable th) {
                    th = th;
                    Slog.w(
                        "ActivityTaskManager",
                        "startActivity: reason=startActivityFromRecents",
                        th);
                    throw th;
                  }
                }
                if (displayContent != null) {}
                boolean z7 = false;
                anyTaskForId2.mSkipSavingLaunchingState = z7;
                if (isSplitScreenModeActivated
                    || fromWindowContainerToken == null
                    || !fromWindowContainerToken.inSplitScreenWindowingMode()
                    || this.mService.mWindowOrganizerController.whileEnterSplitWithSingleStage()) {
                  z5 = z7;
                } else {
                  this.mService.mWindowOrganizerController.onEnterSplitWithSingleStageStarted(
                      "recents");
                  z5 = true;
                }
                if (z) {
                  try {
                    if (anyTaskForId2.inFreeformWindowingMode() && anyTaskForId2.isVisible()) {
                      z2 = z7;
                    }
                  } catch (RuntimeException e2) {
                    throw e2;
                  } catch (Throwable th2) {
                    th = th2;
                    Slog.w(
                        "ActivityTaskManager",
                        "startActivity: reason=startActivityFromRecents",
                        th);
                    throw th;
                  }
                }
                try {
                  try {
                    if (z2) {
                      this.mRootWindowContainer
                          .getDefaultTaskDisplayArea()
                          .moveHomeRootTaskToFront("startActivityFromRecents");
                      if (anyTaskForId2.inFreeformWindowingMode()
                          && (dexModeLocked = this.mService.mDexController.getDexModeLocked())
                              != 1) {
                        i7 = 2;
                        if (dexModeLocked != 2 || anyTaskForId2.getDisplayId() != 2) {
                          this.mService
                              .getTaskChangeNotificationController()
                              .notifyTaskMovedToFront(
                                  defaultTaskDisplayArea
                                      .getRootHomeTask()
                                      .getTopLeafTask()
                                      .getTaskInfo());
                        }
                        if (!this.mService.mAmInternal.shouldConfirmCredentials(
                                anyTaskForId2.mUserId)
                            || anyTaskForId2.getRootActivity() == null) {
                          int i11 = anyTaskForId2.mCallingUid;
                          String str = anyTaskForId2.mCallingPackage;
                          String str2 = anyTaskForId2.mCallingFeatureId;
                          Intent intent = anyTaskForId2.intent;
                          intent.addFlags(1048576);
                          int i12 = anyTaskForId2.mUserId;
                          intent.removeFlags(IInstalld.FLAG_USE_QUOTA);
                          Intent intent2 = anyTaskForId2.intent;
                          String type = intent2 == null ? intent2.getType() : null;
                          WindowManagerService.resetPriorityAfterLockedSection();
                          this.mService.mDexDockingController.setOtherTaskIfNeeded(
                              anyTaskForId2, activityOptions);
                          ThreadLocal threadLocal =
                              PackageManagerServiceUtils
                                  .DISABLE_ENFORCE_INTENTS_TO_MATCH_INTENT_FILTERS;
                          threadLocal.set(Boolean.TRUE);
                          i10 = windowingMode;
                          activityOptions3 = activityOptions;
                          int startActivityInPackage =
                              this.mService
                                  .getActivityStartController()
                                  .startActivityInPackage(
                                      i11,
                                      i,
                                      i2,
                                      str,
                                      str2,
                                      intent,
                                      type,
                                      null,
                                      null,
                                      0,
                                      0,
                                      safeActivityOptions,
                                      i12,
                                      anyTaskForId2,
                                      "startActivityFromRecents",
                                      false,
                                      null,
                                      BackgroundStartPrivileges.NONE);
                          threadLocal.set(Boolean.FALSE);
                          windowManagerGlobalLock2 = this.mService.mGlobalLock;
                          WindowManagerService.boostPriorityForLockedSection();
                          synchronized (windowManagerGlobalLock2) {
                            try {
                              this.mService.continueWindowLayout();
                              if (!anyTaskForId2.isAttached() || anyTaskForId2.hasActivity()) {
                                task2 = anyTaskForId2;
                              } else {
                                task2 = anyTaskForId2;
                                if (!task2.mCreatedByOrganizer
                                    && task2.inSplitScreenWindowingMode()) {
                                  task2.removeIfPossible("handleStartActivityFromRecents");
                                }
                              }
                              if (z5) {
                                this.mService.mWindowOrganizerController
                                    .onEnterSplitWithSingleStageFinished("recents");
                              }
                              if (CoreRune.MW_SA_LOGGING) {
                                sendMultiWindowSALogging(task2, i10, activityOptions3, i2);
                              }
                            } catch (Throwable th3) {
                              throw th3;
                            }
                          }
                          WindowManagerService.resetPriorityAfterLockedSection();
                          return startActivityInPackage;
                        }
                        ActivityRecord topNonFinishingActivity =
                            anyTaskForId2.getTopNonFinishingActivity();
                        this.mRootWindowContainer.startPowerModeLaunchIfNeeded(
                            true, topNonFinishingActivity);
                        ActivityMetricsLogger activityMetricsLogger = this.mActivityMetricsLogger;
                        Intent intent3 = anyTaskForId2.intent;
                        if (!isCallerRecents) {
                          i6 = i2;
                        }
                        ActivityMetricsLogger.LaunchingState notifyActivityLaunching =
                            activityMetricsLogger.notifyActivityLaunching(intent3, null, i6);
                        try {
                          if (anyTaskForId2.inFreeformWindowingMode()
                              && anyTaskForId2.isFreeformStashed()) {
                            if (anyTaskForId2.isVisible()
                                && !anyTaskForId2.isFreeformForceHidden()) {
                              z7 = true;
                            }
                            this.mService.mTaskOrganizerController.resetStashedFreeform(
                                anyTaskForId2, z7);
                          }
                          if (anyTaskForId2.isDexMode()
                              && anyTaskForId2.inFullscreenWindowingMode()
                              && activityOptions != null
                              && activityOptions.getLaunchBounds() != null
                              && !activityOptions.getLaunchBounds().isEmpty()) {
                            anyTaskForId2.setLastNonFullscreenBounds(
                                activityOptions.getLaunchBounds());
                            activityOptions.setLaunchWindowingMode(5);
                          }
                          if (anyTaskForId2.isDexMode()
                              && anyTaskForId2.isDexTaskDocked()
                              && activityOptions != null
                              && activityOptions.getLaunchBounds() != null
                              && !activityOptions.getLaunchBounds().isEmpty()) {
                            this.mService.mDexDockingController.clearAllTasks(
                                "startActivityFromRecents");
                          }
                          int i13 = i7;
                          try {
                            this.mService.moveTaskToFrontLocked(
                                null, null, anyTaskForId2.mTaskId, 0, safeActivityOptions, true);
                            if (activityOptions == null
                                || activityOptions.getAnimationType() != 13) {
                              activityRecord = topNonFinishingActivity;
                            } else {
                              activityRecord = topNonFinishingActivity;
                              try {
                                activityRecord.mPendingRemoteAnimation =
                                    activityOptions.getRemoteAnimationAdapter();
                              } catch (Throwable th4) {
                                th = th4;
                                this.mActivityMetricsLogger.notifyActivityLaunched(
                                    notifyActivityLaunching,
                                    2,
                                    false,
                                    activityRecord,
                                    activityOptions);
                                throw th;
                              }
                            }
                            activityRecord.applyOptionsAnimation();
                            if (activityOptions != null
                                && activityOptions.getLaunchCookie() != null) {
                              activityRecord.mLaunchCookie = activityOptions.getLaunchCookie();
                            }
                            this.mActivityMetricsLogger.notifyActivityLaunched(
                                notifyActivityLaunching, 2, false, activityRecord, activityOptions);
                            this.mService.mDexDockingController.setOtherTaskIfNeeded(
                                anyTaskForId2, activityOptions);
                            this.mService
                                .getActivityStartController()
                                .postStartActivityProcessingForLastStarter(
                                    anyTaskForId2.getTopNonFinishingActivity(),
                                    i13,
                                    anyTaskForId2.getRootTask());
                            this.mService.resumeAppSwitches();
                            if (CoreRune.MW_SA_LOGGING) {
                              sendMultiWindowSALogging(
                                  anyTaskForId2, windowingMode, activityOptions, i2);
                            }
                            if (z5) {
                              try {
                                this.mService.mWindowOrganizerController
                                    .onEnterSplitWithSingleStageFinished("recents");
                              } finally {
                              }
                            }
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return i13;
                          } catch (Throwable th5) {
                            th = th5;
                            activityRecord = topNonFinishingActivity;
                          }
                        } catch (Throwable th6) {
                          th = th6;
                          activityRecord = topNonFinishingActivity;
                        }
                      }
                    }
                    int startActivityInPackage2 =
                        this.mService
                            .getActivityStartController()
                            .startActivityInPackage(
                                i11,
                                i,
                                i2,
                                str,
                                str2,
                                intent,
                                type,
                                null,
                                null,
                                0,
                                0,
                                safeActivityOptions,
                                i12,
                                anyTaskForId2,
                                "startActivityFromRecents",
                                false,
                                null,
                                BackgroundStartPrivileges.NONE);
                    threadLocal.set(Boolean.FALSE);
                    windowManagerGlobalLock2 = this.mService.mGlobalLock;
                    WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock2) {
                    }
                  } catch (Throwable th7) {
                    th = th7;
                    task = anyTaskForId2;
                    i9 = i2;
                    i8 = i10;
                    activityOptions2 = activityOptions3;
                    PackageManagerServiceUtils.DISABLE_ENFORCE_INTENTS_TO_MATCH_INTENT_FILTERS.set(
                        Boolean.FALSE);
                    WindowManagerGlobalLock windowManagerGlobalLock3 = this.mService.mGlobalLock;
                    WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock3) {
                      try {
                        this.mService.continueWindowLayout();
                        if (task.isAttached()
                            && !task.hasActivity()
                            && !task.mCreatedByOrganizer
                            && task.inSplitScreenWindowingMode()) {
                          task.removeIfPossible("handleStartActivityFromRecents");
                        }
                        if (z5) {
                          this.mService.mWindowOrganizerController
                              .onEnterSplitWithSingleStageFinished("recents");
                        }
                        if (CoreRune.MW_SA_LOGGING) {
                          sendMultiWindowSALogging(task, i8, activityOptions2, i9);
                        }
                      } catch (Throwable th8) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th8;
                      }
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                  }
                  this.mService.mDexDockingController.setOtherTaskIfNeeded(
                      anyTaskForId2, activityOptions);
                  ThreadLocal threadLocal2 =
                      PackageManagerServiceUtils.DISABLE_ENFORCE_INTENTS_TO_MATCH_INTENT_FILTERS;
                  threadLocal2.set(Boolean.TRUE);
                  i10 = windowingMode;
                  activityOptions3 = activityOptions;
                } catch (Throwable th9) {
                  th = th9;
                  task = anyTaskForId2;
                  i8 = windowingMode;
                  activityOptions2 = activityOptions;
                  i9 = i2;
                }
                i7 = 2;
                if (this.mService.mAmInternal.shouldConfirmCredentials(anyTaskForId2.mUserId)) {}
                int i112 = anyTaskForId2.mCallingUid;
                String str3 = anyTaskForId2.mCallingPackage;
                String str22 = anyTaskForId2.mCallingFeatureId;
                Intent intent4 = anyTaskForId2.intent;
                intent4.addFlags(1048576);
                int i122 = anyTaskForId2.mUserId;
                intent4.removeFlags(IInstalld.FLAG_USE_QUOTA);
                Intent intent22 = anyTaskForId2.intent;
                if (intent22 == null) {}
                WindowManagerService.resetPriorityAfterLockedSection();
              } catch (RuntimeException e3) {
                e = e3;
                throw e;
              } catch (Throwable th10) {
                th = th10;
                Slog.w("ActivityTaskManager", "startActivity: reason=startActivityFromRecents", th);
                throw th;
              }
            } catch (RuntimeException e4) {
              e = e4;
              z4 = false;
            } catch (Throwable th11) {
              th = th11;
              z4 = false;
            }
          } catch (Throwable th12) {
            if (i6 != 0) {
              try {
                this.mService.mWindowOrganizerController.onEnterSplitWithSingleStageFinished(
                    "recents");
              } finally {
              }
            }
            throw th12;
          }
        } finally {
          WindowManagerService.resetPriorityAfterLockedSection();
        }
      }
    }
    activityOptions = makeBasic;
    windowManagerGlobalLock = this.mService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
    }
  }

  public class OpaqueActivityHelper implements Predicate {
    public boolean mIncludeInvisibleAndFinishing;
    public ActivityRecord mStarting;

    public ActivityRecord getOpaqueActivity(WindowContainer windowContainer) {
      this.mIncludeInvisibleAndFinishing = true;
      return windowContainer.getActivity(this, true, null);
    }

    public ActivityRecord getVisibleOpaqueActivity(
        WindowContainer windowContainer, ActivityRecord activityRecord) {
      this.mStarting = activityRecord;
      this.mIncludeInvisibleAndFinishing = false;
      ActivityRecord activity = windowContainer.getActivity(this, true, null);
      this.mStarting = null;
      return activity;
    }

    @Override // java.util.function.Predicate
    public boolean test(ActivityRecord activityRecord) {
      boolean z = this.mIncludeInvisibleAndFinishing;
      if (!z && !activityRecord.visibleIgnoringKeyguard && activityRecord != this.mStarting) {
        return false;
      }
      if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX
          && z
          && activityRecord.finishing
          && !activityRecord.hasChild()) {
        return false;
      }
      if (CoreRune.FW_CUSTOM_LETTERBOX) {
        if (this.mIncludeInvisibleAndFinishing) {
          if (CoreRune.FW_ORIENTATION_CONTROL_WITH_ASPECT_RATIO
              && activityRecord.mCompatRecord.mIsIgnoreOrientationRequest
              && activityRecord.getTask() != null
              && activityRecord.getTask().mOrientationControlEnabledAsAspectRatio
              && activityRecord.occludesParent(true)
              && activityRecord.getTask().getActivityBelow(activityRecord) == null) {
            return true;
          }
        } else if (CustomLetterboxConfiguration.hasWallpaperBackgroundForLetterbox(
            activityRecord)) {
          return true;
        }
      }
      return activityRecord.occludesParent(this.mIncludeInvisibleAndFinishing);
    }
  }

  public class TaskInfoHelper implements Consumer {
    public TaskInfo mInfo;
    public ActivityRecord mTopRunning;

    public ActivityRecord fillAndReturnTop(Task task, TaskInfo taskInfo) {
      taskInfo.numActivities = 0;
      taskInfo.baseActivity = null;
      this.mInfo = taskInfo;
      task.forAllActivities(this);
      ActivityRecord activityRecord = this.mTopRunning;
      this.mTopRunning = null;
      this.mInfo = null;
      return activityRecord;
    }

    @Override // java.util.function.Consumer
    public void accept(ActivityRecord activityRecord) {
      IBinder iBinder = activityRecord.mLaunchCookie;
      if (iBinder != null) {
        this.mInfo.addLaunchCookie(iBinder);
      }
      if (activityRecord.finishing) {
        return;
      }
      TaskInfo taskInfo = this.mInfo;
      taskInfo.numActivities++;
      taskInfo.baseActivity = activityRecord.mActivityComponent;
      if (this.mTopRunning == null) {
        this.mTopRunning = activityRecord;
      }
    }
  }

  public final void sendMultiWindowSALogging(
      Task task, int i, ActivityOptions activityOptions, int i2) {
    if (this.mService.getRecentTasks().isRecentsUid(i2)) {
      if (i == 5 && activityOptions != null && activityOptions.getForceLaunchWindowingMode() == 1) {
        CoreSaLogger.logForAdvanced("2090", "From recent_option");
        return;
      }
      boolean z =
          (activityOptions == null
                  || activityOptions.getLaunchBounds() == null
                  || activityOptions.getLaunchBounds().isEmpty())
              ? false
              : true;
      if ((task == null || task.isDexMode() || !task.inFreeformWindowingMode()) && !z) {
        return;
      }
      if (i == 5) {
        CoreSaLogger.logForAdvanced("2004", "From recent_task");
      } else {
        CoreSaLogger.logForAdvanced("2004", "From recent_option");
      }
    }
  }

  public class WaitInfo {
    public final ActivityMetricsLogger.LaunchingState mLaunchingState;
    public final WaitResult mResult;
    public final ComponentName mTargetComponent;

    public WaitInfo(
        WaitResult waitResult,
        ComponentName componentName,
        ActivityMetricsLogger.LaunchingState launchingState) {
      this.mResult = waitResult;
      this.mTargetComponent = componentName;
      this.mLaunchingState = launchingState;
    }

    public boolean matches(ActivityRecord activityRecord) {
      if (!this.mLaunchingState.hasActiveTransitionInfo()) {
        return this.mTargetComponent.equals(activityRecord.mActivityComponent);
      }
      return this.mLaunchingState.contains(activityRecord);
    }

    public void dump(PrintWriter printWriter, String str) {
      printWriter.println(str + "WaitInfo:");
      printWriter.println(str + "  mTargetComponent=" + this.mTargetComponent);
      StringBuilder sb = new StringBuilder();
      sb.append(str);
      sb.append("  mResult=");
      printWriter.println(sb.toString());
      this.mResult.dump(printWriter, str + "    ");
    }
  }

  public boolean isTopResumedActivity(ActivityRecord activityRecord) {
    return this.mTopResumedActivity == activityRecord;
  }

  public void setLongLiveProcessIfNeeded(WindowProcessController windowProcessController) {
    if (CoreRune.FW_DEDICATED_MEMORY
        && this.mRecentTasks.isDedicatedProcess(
            windowProcessController.mUserId, windowProcessController.mName)) {
      this.mService.f1734mH.sendMessage(
          PooledLambda.obtainMessage(
              new BiConsumer() { // from class:
                                 // com.android.server.wm.ActivityTaskSupervisor$$ExternalSyntheticLambda13
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                  ((ActivityManagerInternal) obj).setLongLiveProcess(((Integer) obj2).intValue());
                }
              },
              this.mService.mAmInternal,
              Integer.valueOf(windowProcessController.getPid())));
    }
  }
}
