package com.android.server.wm;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.AppOpsManager;
import android.app.ProfilerInfo;
import android.app.TaskInfo;
import android.app.WaitResult;
import android.app.WindowConfiguration;
import android.app.sdksandbox.sandboxactivity.SdkSandboxActivityAuthority;
import android.companion.virtual.VirtualDeviceManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.Xml;
import android.window.DisplayWindowPolicyController;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.sdksandbox.flags.Flags;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.HeapdumpWatcher$$ExternalSyntheticOutline0;
import com.android.server.HermesService$3$$ExternalSyntheticOutline0;
import com.android.server.VaultKeeperService$$ExternalSyntheticOutline0;
import com.android.server.am.AppStateBroadcaster;
import com.android.server.am.UserState;
import com.android.server.utils.Slogf;
import com.android.server.wm.ActivityMetricsLogger;
import com.android.server.wm.ActivityRecord;
import com.android.server.wm.LaunchParamsPersister;
import com.android.server.wm.RecentTasks;
import com.android.server.wm.RootWindowContainer;
import com.android.server.wm.TaskChangeNotificationController;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.rune.CoreRune;
import com.sec.tmodiagnostics.DeviceReportingSecurityChecker;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class ActivityTaskSupervisor implements RecentTasks.Callbacks {
    public static final ArrayMap ACTION_TO_RUNTIME_PERMISSION;
    public static final int IDLE_TIMEOUT;
    public static final int LAUNCH_TIMEOUT;
    public static final int SLEEP_TIMEOUT;
    public ActivityMetricsLogger mActivityMetricsLogger;
    public AppOpsManager mAppOpsManager;
    public boolean mAppVisibilitiesChangedSinceLastPause;
    public BackgroundActivityStartController mBalController;
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

    public final class ActivityTaskSupervisorHandler extends Handler {
        public ActivityTaskSupervisorHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            String str;
            int i;
            WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskSupervisor.this.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (handleMessageInner(message)) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                    if (message.what != 213) {
                        return;
                    }
                    ActivityRecord activityRecord = (ActivityRecord) message.obj;
                    WindowManagerGlobalLock windowManagerGlobalLock2 = ActivityTaskSupervisor.this.mService.mGlobalLock;
                    WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock2) {
                        try {
                            if (activityRecord.attachedToProcess() && activityRecord.isState(ActivityRecord.State.RESTARTING_PROCESS)) {
                                WindowProcessController windowProcessController = activityRecord.app;
                                str = windowProcessController.mName;
                                i = windowProcessController.mUid;
                            } else {
                                str = null;
                                i = 0;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                    if (str != null) {
                        ActivityTaskSupervisor.this.mService.mAmInternal.killProcess(str, i, "restartActivityProcessTimeout");
                    }
                } finally {
                    WindowManagerService.resetPriorityAfterLockedSection();
                }
            }
        }

        public final boolean handleMessageInner(Message message) {
            boolean inMultiWindowMode;
            int i = message.what;
            ActivityTaskSupervisor activityTaskSupervisor = ActivityTaskSupervisor.this;
            if (i != 212) {
                switch (i) {
                    case 200:
                        activityTaskSupervisor.activityIdleInternal((ActivityRecord) message.obj, true, true, null);
                        break;
                    case 201:
                        activityTaskSupervisor.activityIdleInternal((ActivityRecord) message.obj, false, false, null);
                        break;
                    case 202:
                        activityTaskSupervisor.mRootWindowContainer.resumeFocusedTasksTopActivities();
                        break;
                    case 203:
                        ActivityTaskManagerService activityTaskManagerService = activityTaskSupervisor.mService;
                        if (activityTaskManagerService.mSleeping || activityTaskManagerService.mShuttingDown) {
                            Slog.w("ActivityTaskManager", "Sleep timeout!  Sleeping now.");
                            activityTaskSupervisor.checkReadyForSleepLocked(false);
                            break;
                        }
                        break;
                    case 204:
                        if (activityTaskSupervisor.mLaunchingActivityWakeLock.isHeld()) {
                            Slog.w("ActivityTaskManager", "Launch timeout has expired, giving up wake lock!");
                            activityTaskSupervisor.mLaunchingActivityWakeLock.release();
                            break;
                        }
                        break;
                    case 205:
                        activityTaskSupervisor.processStoppingAndFinishingActivities(null, false, "transit");
                        break;
                    case 206:
                        Task task = (Task) message.obj;
                        if (task.mKillProcessesOnDestroyed && task.hasActivity()) {
                            Slog.i("ActivityTaskManager", "Destroy timeout of remove-task, attempt to kill " + task);
                            activityTaskSupervisor.killTaskProcessesIfPossible(task);
                            break;
                        }
                        break;
                    default:
                        switch (i) {
                            case FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__PLATFORM_ROLE_HOLDER_UPDATE_FINISHED /* 214 */:
                                for (int size = activityTaskSupervisor.mMultiWindowModeChangedActivities.size() - 1; size >= 0; size--) {
                                    ActivityRecord activityRecord = (ActivityRecord) activityTaskSupervisor.mMultiWindowModeChangedActivities.remove(size);
                                    Task task2 = activityRecord.task;
                                    if (task2 != null && task2.getRootTask() != null && activityRecord.attachedToProcess() && (inMultiWindowMode = activityRecord.inMultiWindowMode()) != activityRecord.mLastReportedMultiWindowMode) {
                                        if (inMultiWindowMode || !activityRecord.mLastReportedPictureInPictureMode) {
                                            activityRecord.mLastReportedMultiWindowMode = inMultiWindowMode;
                                            activityRecord.ensureActivityConfiguration(false);
                                        } else {
                                            activityRecord.updatePictureInPictureMode(null);
                                        }
                                    }
                                }
                                break;
                            case FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__PLATFORM_ROLE_HOLDER_UPDATE_FAILED /* 215 */:
                                for (int size2 = activityTaskSupervisor.mPipModeChangedActivities.size() - 1; size2 >= 0; size2--) {
                                    ((ActivityRecord) activityTaskSupervisor.mPipModeChangedActivities.remove(size2)).updatePictureInPictureMode(activityTaskSupervisor.mPipModeChangedTargetRootTaskBounds);
                                }
                                break;
                            case FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__SET_MTE_POLICY /* 216 */:
                                activityTaskSupervisor.mHandler.removeMessages(FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__SET_MTE_POLICY);
                                RootWindowContainer rootWindowContainer = activityTaskSupervisor.mRootWindowContainer;
                                String str = (String) message.obj;
                                rootWindowContainer.getClass();
                                rootWindowContainer.forAllTaskDisplayAreas(new RootWindowContainer$$ExternalSyntheticLambda16(rootWindowContainer, str, 2));
                                break;
                            case FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__SET_APPLICATION_EXEMPTIONS /* 217 */:
                                ActivityRecord activityRecord2 = (ActivityRecord) message.obj;
                                Slog.w("ActivityTaskManager", "Activity top resumed state loss timeout for " + activityRecord2);
                                if (activityRecord2.hasProcess()) {
                                    ActivityTaskManagerService activityTaskManagerService2 = activityTaskSupervisor.mService;
                                    activityRecord2.toString();
                                    activityTaskManagerService2.getClass();
                                }
                                activityTaskSupervisor.handleTopResumedStateReleased(true);
                                break;
                            default:
                                return false;
                        }
                }
            } else {
                ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked((IBinder) message.obj);
                if (forTokenLocked != null) {
                    activityTaskSupervisor.getClass();
                    Task task3 = forTokenLocked.task;
                    Task rootTask = task3.getRootTask();
                    activityTaskSupervisor.mRecentTasks.add(task3);
                    activityTaskSupervisor.mService.mTaskChangeNotificationController.notifyTaskStackChanged();
                    rootTask.ensureActivitiesVisible(true, null);
                    ActivityRecord topNonFinishingActivity = rootTask.getTopNonFinishingActivity(true, true);
                    if (topNonFinishingActivity != null) {
                        Task task4 = topNonFinishingActivity.task;
                        task4.getClass();
                        task4.lastActiveTime = SystemClock.elapsedRealtime();
                    }
                }
            }
            return true;
        }
    }

    public final class OpaqueActivityHelper implements Predicate {
        public boolean mIgnoreFloatingWindow;
        public boolean mIgnoringKeyguard;
        public boolean mIncludeInvisibleAndFinishing;
        public ActivityRecord mStarting;

        @Override // java.util.function.Predicate
        public final boolean test(Object obj) {
            ActivityRecord activityRecord = (ActivityRecord) obj;
            if (!this.mIncludeInvisibleAndFinishing && activityRecord != this.mStarting) {
                boolean z = this.mIgnoringKeyguard;
                if (z && !activityRecord.visibleIgnoringKeyguard) {
                    return false;
                }
                if (!z && !activityRecord.mVisible) {
                    return false;
                }
            }
            if (CoreRune.MW_SHELL_TRANSITION && this.mIgnoreFloatingWindow && WindowConfiguration.isFloating(activityRecord.getWindowingMode())) {
                return false;
            }
            return activityRecord.occludesParent(this.mIncludeInvisibleAndFinishing);
        }
    }

    public final class TaskInfoHelper implements Consumer {
        public TaskInfo mInfo;
        public ActivityRecord mTopRunning;

        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            ActivityRecord activityRecord = (ActivityRecord) obj;
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

    public final class WaitInfo {
        public final ActivityMetricsLogger.LaunchingState mLaunchingState;
        public final WaitResult mResult;
        public final ComponentName mTargetComponent;

        public WaitInfo(WaitResult waitResult, ComponentName componentName, ActivityMetricsLogger.LaunchingState launchingState) {
            this.mResult = waitResult;
            this.mTargetComponent = componentName;
            this.mLaunchingState = launchingState;
        }
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

    public ActivityTaskSupervisor(ActivityTaskManagerService activityTaskManagerService, Looper looper) {
        this.mService = activityTaskManagerService;
        this.mLooper = looper;
        this.mHandler = new ActivityTaskSupervisorHandler(looper);
    }

    public static void dumpHistoryList(FileDescriptor fileDescriptor, PrintWriter printWriter, List list, String str, boolean z, String str2, Runnable runnable) {
        int size = list.size() - 1;
        Runnable runnable2 = runnable;
        boolean z2 = true;
        Task task = null;
        while (size >= 0) {
            ActivityRecord activityRecord = (ActivityRecord) list.get(size);
            ActivityRecord.dumpActivity(fileDescriptor, printWriter, size, activityRecord, "  ", str, false, z, false, str2, z2, runnable2, task);
            task = activityRecord.task;
            size--;
            z2 = false;
            runnable2 = null;
        }
    }

    public static void logIfTransactionTooLarge(Intent intent, Bundle bundle) {
        Bundle extras;
        int size = (intent == null || (extras = intent.getExtras()) == null) ? 0 : extras.getSize();
        int size2 = bundle != null ? bundle.getSize() : 0;
        if (size + size2 > 200000) {
            StringBuilder sb = new StringBuilder("Transaction too large, intent: ");
            sb.append(intent);
            sb.append(", extras size: ");
            sb.append(size);
            sb.append(", icicle size: ");
            VaultKeeperService$$ExternalSyntheticOutline0.m(sb, size2, "ActivityTaskManager");
        }
    }

    public static boolean printThisActivity(PrintWriter printWriter, ActivityRecord activityRecord, String str, int i, boolean z, String str2, RootWindowContainer$$ExternalSyntheticLambda32 rootWindowContainer$$ExternalSyntheticLambda32) {
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
        if (rootWindowContainer$$ExternalSyntheticLambda32 != null) {
            rootWindowContainer$$ExternalSyntheticLambda32.run();
        }
        printWriter.print(str2);
        printWriter.println(activityRecord);
        return true;
    }

    public static void removeHistoryRecords(ArrayList arrayList, WindowProcessController windowProcessController) {
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v6, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r15v1 */
    /* JADX WARN: Type inference failed for: r15v2, types: [int] */
    /* JADX WARN: Type inference failed for: r15v7 */
    public final void activityIdleInternal(ActivityRecord activityRecord, boolean z, boolean z2, Configuration configuration) {
        ActivityTaskManagerService activityTaskManagerService;
        boolean z3;
        ActivityTaskManagerService activityTaskManagerService2;
        boolean z4;
        boolean z5;
        final boolean z6;
        Configuration configuration2;
        ActivityTaskSupervisorHandler activityTaskSupervisorHandler = this.mHandler;
        boolean z7 = false;
        boolean z8 = true;
        final ActivityTaskManagerService activityTaskManagerService3 = this.mService;
        if (activityRecord != null) {
            activityTaskSupervisorHandler.removeMessages(200, activityRecord);
            activityRecord.launchTickTime = 0L;
            Task rootTask = activityRecord.getRootTask();
            if (rootTask != null) {
                rootTask.forAllActivities(new Task$$ExternalSyntheticLambda6(5));
            }
            if (z) {
                reportActivityLaunched(z, activityRecord, -1L, -1);
            }
            if (configuration != null) {
                if (configuration.dexCompatEnabled != 2 || activityTaskManagerService3.mDexCompatController.shouldBeApplyDexCompatConfigurationLocked(activityRecord, activityRecord.mProcessAppInfo, activityRecord.getDisplayId())) {
                    configuration2 = configuration;
                } else {
                    WindowProcessController windowProcessController = activityRecord.app;
                    configuration2 = windowProcessController != null ? windowProcessController.getConfiguration() : activityRecord.mAtmService.getGlobalConfiguration();
                }
                activityRecord.mLastReportedConfiguration.setGlobalConfiguration(configuration2);
            }
            activityRecord.idle = true;
            if ((activityTaskManagerService3.mAmInternal.isBooting() && this.mRootWindowContainer.allResumedActivitiesIdle()) || z) {
                final boolean isBooting = activityTaskManagerService3.mAmInternal.isBooting();
                activityTaskManagerService3.mAmInternal.setBooting(false);
                if (activityTaskManagerService3.mAmInternal.isBooted()) {
                    z6 = false;
                } else {
                    activityTaskManagerService3.mAmInternal.setBooted(true);
                    z6 = true;
                }
                if (isBooting || z6) {
                    activityTaskManagerService3.mH.post(new Runnable() { // from class: com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda32
                        @Override // java.lang.Runnable
                        public final void run() {
                            ActivityTaskManagerService activityTaskManagerService4 = ActivityTaskManagerService.this;
                            boolean z9 = isBooting;
                            boolean z10 = z6;
                            if (z9) {
                                activityTaskManagerService4.mAmInternal.finishBooting();
                                activityTaskManagerService4.mAMBooster = ActivityManagerPerformance.getBooster(activityTaskManagerService4.mContext, activityTaskManagerService4);
                                MultiTaskingController multiTaskingController = activityTaskManagerService4.mMultiTaskingController;
                                multiTaskingController.getClass();
                                if (CoreRune.MW_EMBED_ACTIVITY_PACKAGE_ENABLED) {
                                    ActivityEmbeddedPackageRepository activityEmbeddedPackageRepository = multiTaskingController.mActivityEmbeddedPackageRepository;
                                    activityEmbeddedPackageRepository.getClass();
                                    IntentFilter intentFilter = new IntentFilter();
                                    intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
                                    intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
                                    intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
                                    intentFilter.addDataScheme("package");
                                    ActivityTaskManagerService activityTaskManagerService5 = activityEmbeddedPackageRepository.mAtm;
                                    activityTaskManagerService5.mContext.registerReceiver(activityEmbeddedPackageRepository.mPackageReceiver, intentFilter, null, activityTaskManagerService5.mH);
                                    if (activityTaskManagerService5.mContext.getPackageManager() == null) {
                                        Slog.v("ActivityEmbeddedPackageRepository", "PackageManager is not ready yet.");
                                    } else {
                                        for (ApplicationInfo applicationInfo : activityTaskManagerService5.mContext.getPackageManager().getInstalledApplications(0)) {
                                            try {
                                                if (activityTaskManagerService5.mContext.getPackageManager().getProperty("android.window.PROPERTY_ACTIVITY_EMBEDDING_ALLOW_SYSTEM_OVERRIDE", applicationInfo.packageName) != null) {
                                                    activityTaskManagerService5.mMultiTaskingController.updateEmbedActivityPackageEnabled(1, activityTaskManagerService5.mAmInternal.getCurrentUserId(), applicationInfo.packageName, false);
                                                    activityEmbeddedPackageRepository.add(applicationInfo.packageName);
                                                }
                                            } catch (PackageManager.NameNotFoundException unused) {
                                            }
                                        }
                                    }
                                }
                                IntentFilter m = DirEncryptServiceHelper$$ExternalSyntheticOutline0.m("com.samsung.android.multiwindow.MINIMIZE_ALL", "com.samsung.android.multiwindow.MINIMIZE_ALL_BY_SYSTEM");
                                ActivityTaskManagerService activityTaskManagerService6 = multiTaskingController.mAtm;
                                activityTaskManagerService6.mContext.registerReceiver(multiTaskingController.mMinimizeAllReceiver, m, "android.permission.MANAGE_ACTIVITY_TASKS", multiTaskingController.mH, 2);
                                multiTaskingController.mSwipeGestureThreshold = multiTaskingController.mWm.getDefaultDisplayContentLocked().mDisplayPolicy.getCurrentUserResources().getDimensionPixelSize(17106300);
                                boolean z11 = CoreRune.MW_FREEFORM_SMART_POPUP_VIEW;
                                IntentFilter m2 = DirEncryptServiceHelper$$ExternalSyntheticOutline0.m("com.samsung.sea.rm.DEMO_RESET_STARTED", "com.samsung.intent.action.SETTINGS_SOFT_RESET");
                                activityTaskManagerService6.mContext.registerReceiver(multiTaskingController.mDemoResetStartedReceiver, m2, "android.permission.MANAGE_ACTIVITY_TASKS", multiTaskingController.mH);
                            }
                            if (z10) {
                                activityTaskManagerService4.mInternal.enableScreenAfterBoot(activityTaskManagerService4.mAmInternal.isBooted());
                            } else {
                                activityTaskManagerService4.getClass();
                            }
                        }
                    });
                }
            }
            activityRecord.mRelaunchReason = 0;
        }
        if (this.mRootWindowContainer.allResumedActivitiesIdle()) {
            if (activityRecord != null) {
                activityTaskManagerService3.mH.post(new ActivityTaskManagerService$$ExternalSyntheticLambda14(1, activityTaskManagerService3));
                RecentTasks recentTasks = this.mRecentTasks;
                if (!recentTasks.mHiddenTasks.isEmpty() && activityRecord.isActivityTypeHome() && activityRecord.isState(ActivityRecord.State.RESUMED)) {
                    int windowingMode = activityRecord.getWindowingMode();
                    int size = recentTasks.mHiddenTasks.size();
                    if (size > 10) {
                        int i = size - 1;
                        while (i >= 10) {
                            Task task = (Task) recentTasks.mHiddenTasks.get(i);
                            if (!task.hasChild() || task.inRecents) {
                                activityTaskManagerService2 = activityTaskManagerService3;
                                z4 = z8;
                                z5 = z7;
                                recentTasks.mHiddenTasks.remove(i);
                            } else if (task.getWindowingMode() == windowingMode && task.getTopVisibleActivity(z8, z7) == null) {
                                recentTasks.mHiddenTasks.remove(i);
                                activityTaskManagerService2 = activityTaskManagerService3;
                                z4 = z8;
                                z5 = z7;
                                recentTasks.mSupervisor.removeTask(task, z7, z7, "remove-hidden-task", false, 1000, -1);
                            } else {
                                activityTaskManagerService2 = activityTaskManagerService3;
                                z4 = z8;
                                z5 = z7;
                            }
                            i--;
                            z7 = z5;
                            activityTaskManagerService3 = activityTaskManagerService2;
                            z8 = z4;
                        }
                    }
                }
                activityTaskManagerService = activityTaskManagerService3;
                z3 = z7;
                if (recentTasks.mCheckTrimmableTasksOnIdle) {
                    recentTasks.mCheckTrimmableTasksOnIdle = z3;
                    recentTasks.trimInactiveRecentTasks();
                }
            } else {
                activityTaskManagerService = activityTaskManagerService3;
                z3 = false;
            }
            if (this.mLaunchingActivityWakeLock.isHeld()) {
                activityTaskSupervisorHandler.removeMessages(204);
                this.mLaunchingActivityWakeLock.release();
            }
        } else {
            activityTaskManagerService = activityTaskManagerService3;
            z3 = false;
        }
        processStoppingAndFinishingActivities(activityRecord, z2, "idle");
        if (!this.mStartingUsers.isEmpty()) {
            ?? arrayList = new ArrayList(this.mStartingUsers);
            this.mStartingUsers.clear();
            for (?? r15 = z3; r15 < arrayList.size(); r15++) {
                UserState userState = (UserState) arrayList.get(r15);
                Slogf.i("ActivityTaskManager", "finishing switch of user %d", Integer.valueOf(userState.mHandle.getIdentifier()));
                activityTaskManagerService.mAmInternal.finishUserSwitch(userState);
            }
        }
        activityTaskManagerService.mH.post(new ActivityTaskSupervisor$$ExternalSyntheticLambda0(this, 0));
    }

    /* JADX WARN: Removed duplicated region for block: B:113:0x0292  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x02cd A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void beginActivityVisibilityUpdate(com.android.server.wm.DisplayContent r22) {
        /*
            Method dump skipped, instructions count: 733
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityTaskSupervisor.beginActivityVisibilityUpdate(com.android.server.wm.DisplayContent):void");
    }

    public final void beginDeferResume() {
        this.mDeferResumeCount++;
    }

    public final boolean canPlaceEntityOnDisplay(int i, int i2, int i3, Task task, ActivityInfo activityInfo) {
        if (i == 0) {
            return true;
        }
        if (!this.mService.mSupportsMultiDisplay || !isCallerAllowedToLaunchOnDisplay(i2, i3, i, activityInfo)) {
            return false;
        }
        DisplayContent displayContentOrCreate = this.mRootWindowContainer.getDisplayContentOrCreate(i);
        if (displayContentOrCreate == null) {
            return true;
        }
        ArrayList arrayList = new ArrayList();
        if (activityInfo != null) {
            arrayList.add(activityInfo);
        }
        if (task != null) {
            task.forAllActivities(new ActivityTaskSupervisor$$ExternalSyntheticLambda6(0, arrayList));
        }
        DisplayWindowPolicyControllerHelper displayWindowPolicyControllerHelper = displayContentOrCreate.mDwpcHelper;
        int windowingMode = displayContentOrCreate.getWindowingMode();
        DisplayWindowPolicyController displayWindowPolicyController = displayWindowPolicyControllerHelper.mDisplayWindowPolicyController;
        if (displayWindowPolicyController != null) {
            return displayWindowPolicyController.canContainActivities(arrayList, windowingMode);
        }
        for (int i4 = 0; i4 < arrayList.size(); i4++) {
            String str = ((ActivityInfo) arrayList.get(i4)).requiredDisplayCategory;
            if (str != null) {
                Slog.d("DisplayWindowPolicyControllerHelper", String.format("Checking activity launch with requiredDisplayCategory='%s' on display %d, which doesn't have a matching category.", str, Integer.valueOf(displayWindowPolicyControllerHelper.mDisplayContent.mDisplayId)));
                return false;
            }
        }
        return true;
    }

    public final boolean canUseActivityOptionsLaunchBounds(ActivityOptions activityOptions) {
        if (activityOptions == null || activityOptions.getLaunchBounds() == null) {
            return false;
        }
        ActivityTaskManagerService activityTaskManagerService = this.mService;
        return (activityTaskManagerService.mSupportsPictureInPicture && activityOptions.getLaunchWindowingMode() == 2) || activityTaskManagerService.mSupportsFreeformWindowManagement;
    }

    public final void checkReadyForSleepLocked(boolean z) {
        boolean z2 = false;
        ActivityTaskManagerService activityTaskManagerService = this.mService;
        if (activityTaskManagerService.mSleeping || activityTaskManagerService.mShuttingDown) {
            RootWindowContainer rootWindowContainer = this.mRootWindowContainer;
            rootWindowContainer.getClass();
            boolean[] zArr = {true};
            rootWindowContainer.forAllRootTasks(new RootWindowContainer$$ExternalSyntheticLambda44(z, zArr, z2));
            if (zArr[0]) {
                this.mService.endPowerMode(3);
                this.mRootWindowContainer.rankTaskLayers();
                this.mHandler.removeMessages(203);
                if (this.mGoingToSleepWakeLock.isHeld()) {
                    this.mGoingToSleepWakeLock.release();
                }
                if (this.mService.mShuttingDown) {
                    this.mService.mGlobalLock.notifyAll();
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:76:0x0149, code lost:
    
        if (((android.app.AppOpsManagerInternal) com.android.server.LocalServices.getService(android.app.AppOpsManagerInternal.class)).getOpRestrictionCount(26, r3, r31, (java.lang.String) null) == 1) goto L55;
     */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0167 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x01ec  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0205  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0251  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00cf  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean checkStartAnyActivityPermission(android.content.Intent r25, android.content.pm.ActivityInfo r26, java.lang.String r27, int r28, int r29, int r30, java.lang.String r31, java.lang.String r32, boolean r33, boolean r34, com.android.server.wm.WindowProcessController r35, com.android.server.wm.ActivityRecord r36) {
        /*
            Method dump skipped, instructions count: 648
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityTaskSupervisor.checkStartAnyActivityPermission(android.content.Intent, android.content.pm.ActivityInfo, java.lang.String, int, int, int, java.lang.String, java.lang.String, boolean, boolean, com.android.server.wm.WindowProcessController, com.android.server.wm.ActivityRecord):boolean");
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
        ActivityTaskSupervisor$$ExternalSyntheticLambda3 activityTaskSupervisor$$ExternalSyntheticLambda3 = new ActivityTaskSupervisor$$ExternalSyntheticLambda3();
        ActivityTaskManagerService activityTaskManagerService = this.mService;
        activityTaskManagerService.mH.sendMessage(PooledLambda.obtainMessage(activityTaskSupervisor$$ExternalSyntheticLambda3, activityTaskManagerService.mAmInternal, Integer.valueOf(task.mUserId), component, new Intent(baseIntent)));
        if (z) {
            ActivityRecord topMostActivity = task.getTopMostActivity();
            if (topMostActivity == null || !topMostActivity.finishing || topMostActivity.mAppStopped || topMostActivity.lastVisibleTime <= 0 || task.mKillProcessesOnDestroyed || !topMostActivity.hasProcess()) {
                killTaskProcessesIfPossible(task);
            } else {
                if (CoreRune.MW_DND_FREEFORM_DISMISS_VIEW && task.mRemoveByDrag) {
                    return;
                }
                task.mKillProcessesOnDestroyed = true;
                ActivityTaskSupervisorHandler activityTaskSupervisorHandler = this.mHandler;
                activityTaskSupervisorHandler.sendMessageDelayed(activityTaskSupervisorHandler.obtainMessage(206, task), 1000L);
            }
        }
    }

    public final void computeProcessActivityStateBatch() {
        if (this.mActivityStateChangedProcs.isEmpty()) {
            return;
        }
        for (int size = this.mActivityStateChangedProcs.size() - 1; size >= 0; size--) {
            ((WindowProcessController) this.mActivityStateChangedProcs.get(size)).computeProcessActivityState();
        }
        this.mActivityStateChangedProcs.clear();
    }

    public final void endActivityVisibilityUpdate() {
        int i = this.mVisibilityTransactionDepth - 1;
        this.mVisibilityTransactionDepth = i;
        if (i == 0) {
            computeProcessActivityStateBatch();
        }
    }

    public final void endDeferResume() {
        this.mDeferResumeCount--;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x006a A[Catch: all -> 0x0038, TryCatch #3 {all -> 0x0038, blocks: (B:129:0x002f, B:9:0x003e, B:12:0x0044, B:13:0x0046, B:15:0x0052, B:17:0x005c, B:18:0x0064, B:20:0x006a, B:21:0x006f), top: B:128:0x002f }] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0111 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x014b A[Catch: all -> 0x0103, TryCatch #0 {all -> 0x0103, blocks: (B:106:0x00f4, B:26:0x0113, B:29:0x0120, B:31:0x0126, B:33:0x012c, B:35:0x014b, B:37:0x0153, B:39:0x0159, B:40:0x015e, B:45:0x016c, B:47:0x0172, B:49:0x0180, B:51:0x018c, B:53:0x0186, B:55:0x018f, B:58:0x019a, B:96:0x0197), top: B:105:0x00f4 }] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0169  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x016b  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0150  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0172 A[Catch: all -> 0x0103, TryCatch #0 {all -> 0x0103, blocks: (B:106:0x00f4, B:26:0x0113, B:29:0x0120, B:31:0x0126, B:33:0x012c, B:35:0x014b, B:37:0x0153, B:39:0x0159, B:40:0x015e, B:45:0x016c, B:47:0x0172, B:49:0x0180, B:51:0x018c, B:53:0x0186, B:55:0x018f, B:58:0x019a, B:96:0x0197), top: B:105:0x00f4 }] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0195  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x01bf  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x01f2 A[Catch: all -> 0x01cb, TryCatch #4 {all -> 0x01cb, blocks: (B:61:0x01ba, B:64:0x01c1, B:68:0x01d0, B:70:0x01d4, B:72:0x01dc, B:74:0x01e0, B:76:0x01ec, B:79:0x01f2, B:81:0x01f6, B:83:0x01fe), top: B:60:0x01ba }] */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0197 A[Catch: all -> 0x0103, TryCatch #0 {all -> 0x0103, blocks: (B:106:0x00f4, B:26:0x0113, B:29:0x0120, B:31:0x0126, B:33:0x012c, B:35:0x014b, B:37:0x0153, B:39:0x0159, B:40:0x015e, B:45:0x016c, B:47:0x0172, B:49:0x0180, B:51:0x018c, B:53:0x0186, B:55:0x018f, B:58:0x019a, B:96:0x0197), top: B:105:0x00f4 }] */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0080 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void findTaskToMoveToFront(com.android.server.wm.Task r23, int r24, android.app.ActivityOptions r25, java.lang.String r26, boolean r27) {
        /*
            Method dump skipped, instructions count: 526
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityTaskSupervisor.findTaskToMoveToFront(com.android.server.wm.Task, int, android.app.ActivityOptions, java.lang.String, boolean):void");
    }

    public final int getDeviceIdForDisplayId(int i) {
        if (i == 0 || i == -1) {
            return 0;
        }
        if (this.mVirtualDeviceManager == null) {
            ActivityTaskManagerService activityTaskManagerService = this.mService;
            if (activityTaskManagerService.mHasCompanionDeviceSetupFeature) {
                this.mVirtualDeviceManager = (VirtualDeviceManager) activityTaskManagerService.mContext.getSystemService(VirtualDeviceManager.class);
            }
            if (this.mVirtualDeviceManager == null) {
                return 0;
            }
        }
        return this.mVirtualDeviceManager.getDeviceIdForDisplayId(i);
    }

    public final int getNextTaskIdForUser(int i) {
        int i2 = this.mCurTaskIdForUser.get(i, i * 100000);
        int i3 = i2 + 1;
        int i4 = (i + 1) * 100000;
        if (i3 == i4) {
            i3 = i2 - 99999;
        }
        do {
            SparseBooleanArray sparseBooleanArray = (SparseBooleanArray) this.mRecentTasks.mPersistedTaskIds.get(i);
            if ((sparseBooleanArray == null || !sparseBooleanArray.get(i3)) && this.mRootWindowContainer.anyTaskForId(i3, 1, null, false) == null) {
                this.mCurTaskIdForUser.put(i, i3);
                return i3;
            }
            int i5 = i3 + 1;
            i3 = i5 == i4 ? i3 - 99999 : i5;
        } while (i3 != i2);
        throw new IllegalStateException("Cannot get an available task id. Reached limit of 100000 running tasks per user.");
    }

    public final UserInfo getUserInfo(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return UserManager.get(this.mService.mContext).getUserInfo(i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void goingToSleepLocked() {
        ActivityTaskSupervisorHandler activityTaskSupervisorHandler = this.mHandler;
        activityTaskSupervisorHandler.removeMessages(203);
        activityTaskSupervisorHandler.sendEmptyMessageDelayed(203, SLEEP_TIMEOUT);
        if (!this.mGoingToSleepWakeLock.isHeld()) {
            this.mGoingToSleepWakeLock.acquire();
            if (this.mLaunchingActivityWakeLock.isHeld()) {
                this.mLaunchingActivityWakeLock.release();
                activityTaskSupervisorHandler.removeMessages(204);
            }
        }
        this.mRootWindowContainer.applySleepTokens(false);
        checkReadyForSleepLocked(true);
    }

    public final void handleForcedResizableTaskIfNeeded(int i, Task task) {
        ActivityRecord topNonFinishingActivity = task.getTopNonFinishingActivity(true, true);
        if (topNonFinishingActivity == null || topNonFinishingActivity.noDisplay || !topNonFinishingActivity.canForceResizeNonResizable(task.getWindowingMode()) || this.mWindowManager.mExt.mExtraDisplayPolicy.shouldNotHandleForcedResizableTaskIfNeeded(task.getDisplayId(), i)) {
            return;
        }
        if (CoreRune.BAIDU_CARLIFE && topNonFinishingActivity.getDisplayContent().isCarLifeDisplay()) {
            return;
        }
        this.mService.mTaskChangeNotificationController.notifyActivityForcedResizable(task.mTaskId, i, topNonFinishingActivity.info.applicationInfo.packageName);
    }

    public final void handleNonResizableTaskIfNeeded(Task task, int i, TaskDisplayArea taskDisplayArea, Task task2, boolean z) {
        boolean z2 = task2 != null && task2.getWindowingMode() == 5;
        if (task.isActivityTypeStandardOrUndefined() || z2) {
            ActivityTaskManagerService activityTaskManagerService = this.mService;
            if (activityTaskManagerService.mDexController.getDexModeLocked() != 0 && ((taskDisplayArea != null && taskDisplayArea.mDisplayContent.mDisplayId == 2) || (task2 != null && task2.getDisplayId() == 2))) {
                if (activityTaskManagerService.mKeyguardController.isKeyguardLocked(0)) {
                    Slog.d("ActivityTaskManager", "skip handleNonResizableTaskIfNeeded by KeyguardLocked, task=" + task);
                    return;
                } else {
                    if (z2 || i == 5) {
                        handleForcedResizableTaskIfNeeded(4, task);
                        return;
                    }
                    return;
                }
            }
            boolean z3 = (taskDisplayArea == null || taskDisplayArea.mDisplayContent.mDisplayId == 0) ? false : true;
            if (task.isActivityTypeStandardOrUndefined()) {
                if (z3) {
                    if (!task.mTaskSupervisor.canPlaceEntityOnDisplay(task.getDisplayId(), -1, -1, task, null)) {
                        throw new IllegalStateException("Task resolved to incompatible display");
                    }
                    DisplayContent displayContent = taskDisplayArea.mDisplayContent;
                    if (displayContent == task.getDisplayContent()) {
                        if (z) {
                            return;
                        }
                        handleForcedResizableTaskIfNeeded(2, task);
                        return;
                    }
                    StringBuilder sb = new StringBuilder("Failed to put ");
                    sb.append(task);
                    sb.append(" on display ");
                    HeapdumpWatcher$$ExternalSyntheticOutline0.m(sb, displayContent.mDisplayId, "ActivityTaskManager");
                    TaskChangeNotificationController taskChangeNotificationController = activityTaskManagerService.mTaskChangeNotificationController;
                    ActivityManager.RunningTaskInfo taskInfo = task.getTaskInfo();
                    int i2 = displayContent.mDisplayId;
                    TaskChangeNotificationController.MainHandler mainHandler = taskChangeNotificationController.mHandler;
                    mainHandler.removeMessages(18);
                    Message obtainMessage = mainHandler.obtainMessage(18, i2, 0, taskInfo);
                    taskChangeNotificationController.forAllLocalListeners(taskChangeNotificationController.mNotifyActivityLaunchOnSecondaryDisplayFailed, obtainMessage);
                    obtainMessage.sendToTarget();
                    return;
                }
                if (task.supportsMultiWindow() || taskDisplayArea == null || !taskDisplayArea.isSplitScreenModeActivated()) {
                    if (z) {
                        return;
                    }
                    if (z2) {
                        handleForcedResizableTaskIfNeeded(3, task);
                        return;
                    } else {
                        handleForcedResizableTaskIfNeeded(1, task);
                        return;
                    }
                }
                if (taskDisplayArea.getDisplayContent() == null || taskDisplayArea.getDisplayContent().mSleeping) {
                    return;
                }
                task.topRunningActivity(false);
                if (taskDisplayArea.mRootMainStageTask.getRootTask().hasChild(task)) {
                    Intent baseIntent = task.getBaseIntent();
                    String packageName = (baseIntent == null || baseIntent.getComponent() == null) ? null : baseIntent.getComponent().getPackageName();
                    TaskChangeNotificationController taskChangeNotificationController2 = activityTaskManagerService.mTaskChangeNotificationController;
                    TaskChangeNotificationController.MainHandler mainHandler2 = taskChangeNotificationController2.mHandler;
                    mainHandler2.removeMessages(7);
                    Message obtainMessage2 = mainHandler2.obtainMessage(7, packageName);
                    taskChangeNotificationController2.forAllLocalListeners(taskChangeNotificationController2.mNotifyActivityDismissingDockedTask, obtainMessage2);
                    obtainMessage2.sendToTarget();
                }
            }
        }
    }

    public final void handleTopResumedStateReleased(boolean z) {
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_STATES_enabled[1]) {
            ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_STATES, 3997062844427155487L, 0, null, z ? "(due to timeout)" : "(transition complete)");
        }
        this.mHandler.removeMessages(FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__SET_APPLICATION_EXEMPTIONS);
        if (this.mTopResumedActivityWaitingForPrev) {
            this.mTopResumedActivityWaitingForPrev = false;
            ActivityRecord activityRecord = this.mTopResumedActivity;
            if (activityRecord != null) {
                activityRecord.scheduleTopResumedActivityChanged(true);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:57:0x0127  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x015e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isCallerAllowedToLaunchOnDisplay(int r20, int r21, int r22, android.content.pm.ActivityInfo r23) {
        /*
            Method dump skipped, instructions count: 444
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityTaskSupervisor.isCallerAllowedToLaunchOnDisplay(int, int, int, android.content.pm.ActivityInfo):boolean");
    }

    public final void killTaskProcessesIfPossible(Task task) {
        task.mKillProcessesOnDestroyed = false;
        String basePackageName = task.getBasePackageName();
        ArrayMap map = this.mService.mProcessNames.getMap();
        ArrayList arrayList = null;
        for (int i = 0; i < map.size(); i++) {
            SparseArray sparseArray = (SparseArray) map.valueAt(i);
            for (int i2 = 0; i2 < sparseArray.size(); i2++) {
                WindowProcessController windowProcessController = (WindowProcessController) sparseArray.valueAt(i2);
                if (windowProcessController.mUserId == task.mUserId && windowProcessController != this.mService.mHomeProcess && windowProcessController.containsPackage(basePackageName)) {
                    for (int i3 = 0; i3 < windowProcessController.mActivities.size(); i3++) {
                        ActivityRecord activityRecord = (ActivityRecord) windowProcessController.mActivities.get(i3);
                        if (!activityRecord.mAppStopped) {
                            return;
                        }
                        Task task2 = activityRecord.task;
                        if (task.mTaskId != task2.mTaskId && task2.inRecents) {
                            return;
                        }
                    }
                    if (windowProcessController.mHasForegroundServices && !this.mService.mAmInternal.isAutoRunBlockedApp(windowProcessController.mInfo.packageName, windowProcessController.mUserId)) {
                        return;
                    }
                    String str = task.mReason;
                    if (str == null || !str.contains("MultiTaskingAppCompat")) {
                        windowProcessController.mReason = "kill";
                    } else {
                        windowProcessController.mReason = "MultiTaskingAppCompat";
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
        this.mService.mH.sendMessage(PooledLambda.obtainMessage(new ActivityTaskSupervisor$$ExternalSyntheticLambda5(1), this.mService.mAmInternal, arrayList));
    }

    public final void onProcessActivityStateChanged(WindowProcessController windowProcessController, boolean z) {
        if (!z && this.mVisibilityTransactionDepth <= 0) {
            windowProcessController.computeProcessActivityState();
        } else {
            if (this.mActivityStateChangedProcs.contains(windowProcessController)) {
                return;
            }
            this.mActivityStateChangedProcs.add(windowProcessController);
        }
    }

    public final void onUserUnlocked(int i) {
        ArraySet arraySet;
        File file;
        FileInputStream fileInputStream;
        PersisterQueue persisterQueue = this.mPersisterQueue;
        synchronized (persisterQueue) {
            if (!persisterQueue.mLazyTaskWriterThread.isAlive()) {
                persisterQueue.mLazyTaskWriterThread.start();
            }
        }
        LaunchParamsPersister launchParamsPersister = this.mLaunchParamsPersister;
        launchParamsPersister.getClass();
        ArrayList arrayList = new ArrayList();
        File file2 = new File((File) launchParamsPersister.mUserFolderGetter.apply(i), "launch_params");
        if (file2.isDirectory()) {
            ArraySet arraySet2 = new ArraySet(launchParamsPersister.mPackageList.mPackageNames);
            File[] listFiles = file2.listFiles();
            ArrayMap arrayMap = new ArrayMap(listFiles.length);
            launchParamsPersister.mLaunchParamsMap.put(i, arrayMap);
            int length = listFiles.length;
            int i2 = 0;
            while (i2 < length) {
                File file3 = listFiles[i2];
                if (!file3.isFile()) {
                    Slog.w("LaunchParamsPersister", file3.getAbsolutePath() + " is not a file.");
                } else if (file3.getName().endsWith(".xml")) {
                    String name = file3.getName();
                    int indexOf = name.indexOf(95);
                    if (indexOf != -1) {
                        if (name.indexOf(95, indexOf + 1) != -1) {
                            arrayList.add(file3);
                        } else {
                            name = name.replace('_', '-');
                            File file4 = new File(file2, name);
                            if (file3.renameTo(file4)) {
                                file3 = file4;
                            } else {
                                arrayList.add(file3);
                            }
                        }
                    }
                    ComponentName unflattenFromString = ComponentName.unflattenFromString(name.substring(0, name.length() - 4).replace('-', '/'));
                    if (unflattenFromString == null) {
                        Slog.w("LaunchParamsPersister", "Unexpected file name: ".concat(name));
                        arrayList.add(file3);
                    } else if (arraySet2.contains(unflattenFromString.getPackageName())) {
                        try {
                            fileInputStream = new FileInputStream(file3);
                            try {
                                LaunchParamsPersister.PersistableLaunchParams persistableLaunchParams = new LaunchParamsPersister.PersistableLaunchParams();
                                TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(fileInputStream);
                                while (true) {
                                    int next = resolvePullParser.next();
                                    arraySet = arraySet2;
                                    if (next == 1 || next == 3) {
                                        break;
                                    }
                                    if (next != 2) {
                                        arraySet2 = arraySet;
                                    } else {
                                        try {
                                            String name2 = resolvePullParser.getName();
                                            if ("launch_params".equals(name2)) {
                                                file = file2;
                                                persistableLaunchParams.restore(file3, resolvePullParser);
                                            } else {
                                                StringBuilder sb = new StringBuilder();
                                                file = file2;
                                                try {
                                                    sb.append("Unexpected tag name: ");
                                                    sb.append(name2);
                                                    Slog.w("LaunchParamsPersister", sb.toString());
                                                } catch (Throwable th) {
                                                    th = th;
                                                    Throwable th2 = th;
                                                    try {
                                                        fileInputStream.close();
                                                    } catch (Throwable th3) {
                                                        th2.addSuppressed(th3);
                                                    }
                                                    throw th2;
                                                }
                                            }
                                            arraySet2 = arraySet;
                                            file2 = file;
                                        } catch (Throwable th4) {
                                            th = th4;
                                            file = file2;
                                            Throwable th22 = th;
                                            fileInputStream.close();
                                            throw th22;
                                        }
                                    }
                                }
                                file = file2;
                                arrayMap.put(unflattenFromString, persistableLaunchParams);
                                launchParamsPersister.addComponentNameToLaunchParamAffinityMapIfNotNull(unflattenFromString, persistableLaunchParams.mWindowLayoutAffinity);
                            } catch (Throwable th5) {
                                th = th5;
                                arraySet = arraySet2;
                            }
                        } catch (Exception e) {
                            e = e;
                            arraySet = arraySet2;
                            file = file2;
                        }
                        try {
                            fileInputStream.close();
                        } catch (Exception e2) {
                            e = e2;
                            Slog.w("LaunchParamsPersister", "Failed to restore launch params for " + unflattenFromString, e);
                            arrayList.add(file3);
                            i2++;
                            arraySet2 = arraySet;
                            file2 = file;
                        }
                        i2++;
                        arraySet2 = arraySet;
                        file2 = file;
                    } else {
                        arrayList.add(file3);
                    }
                } else {
                    Slog.w("LaunchParamsPersister", "Unexpected params file name: " + file3.getName());
                    arrayList.add(file3);
                }
                arraySet = arraySet2;
                file = file2;
                i2++;
                arraySet2 = arraySet;
                file2 = file;
            }
            if (!arrayList.isEmpty()) {
                launchParamsPersister.mPersisterQueue.addItem(new LaunchParamsPersister.CleanUpComponentQueueItem(arrayList), true);
            }
        } else {
            HermesService$3$$ExternalSyntheticOutline0.m(i, "Didn't find launch param folder for user ", "LaunchParamsPersister");
        }
        ActivityTaskSupervisorHandler activityTaskSupervisorHandler = this.mHandler;
        if (activityTaskSupervisorHandler.hasMessages(FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__SET_MTE_POLICY)) {
            return;
        }
        activityTaskSupervisorHandler.obtainMessage(FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__SET_MTE_POLICY, "userUnlocked").sendToTarget();
    }

    public final void processStoppingAndFinishingActivities(ActivityRecord activityRecord, boolean z, String str) {
        int i;
        boolean z2;
        Task task;
        ArrayList arrayList = null;
        boolean z3 = false;
        while (i < this.mStoppingActivities.size()) {
            ActivityRecord activityRecord2 = (ActivityRecord) this.mStoppingActivities.get(i);
            boolean z4 = (!activityRecord2.inTransitionSelfOrParent() || (task = activityRecord2.task) == null || task.isForceHidden()) ? false : true;
            int size = activityRecord2.mDisplayContent.mAllSleepTokens.size() - 1;
            while (true) {
                if (size < 0) {
                    z2 = false;
                    break;
                }
                RootWindowContainer.SleepToken sleepToken = (RootWindowContainer.SleepToken) activityRecord2.mDisplayContent.mAllSleepTokens.get(size);
                sleepToken.getClass();
                if (SystemClock.uptimeMillis() - sleepToken.mAcquireTime > 1000 ? false : sleepToken.mIsSwappingDisplay) {
                    z2 = true;
                    break;
                }
                size--;
            }
            z3 |= z2;
            boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_STATES_enabled;
            if (zArr[1]) {
                ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_STATES, 1496536241884839051L, 60, null, String.valueOf(activityRecord2), Boolean.valueOf(activityRecord2.nowVisible), Boolean.valueOf(z4), String.valueOf(activityRecord2.finishing));
            }
            if ((z4 || z3) && !this.mService.mShuttingDown) {
                i = (activityRecord2.getRootTask().mForceHiddenFlags & 1) != 0 ? 0 : i + 1;
            }
            if (z || !activityRecord2.isState(ActivityRecord.State.PAUSING)) {
                if (zArr[1]) {
                    ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_STATES, 5677125188685281770L, 0, null, String.valueOf(activityRecord2));
                }
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(activityRecord2);
                this.mStoppingActivities.remove(i);
                i--;
            } else {
                this.mHandler.removeMessages(200, activityRecord);
                ActivityTaskSupervisorHandler activityTaskSupervisorHandler = this.mHandler;
                activityTaskSupervisorHandler.sendMessageDelayed(activityTaskSupervisorHandler.obtainMessage(200, activityRecord), IDLE_TIMEOUT);
            }
        }
        if (z3) {
            this.mHandler.postDelayed(new ActivityTaskSupervisor$$ExternalSyntheticLambda0(this, 1), 200L);
        }
        int size2 = arrayList == null ? 0 : arrayList.size();
        for (int i2 = 0; i2 < size2; i2++) {
            ActivityRecord activityRecord3 = (ActivityRecord) arrayList.get(i2);
            if (activityRecord3.inHistory) {
                if (activityRecord3.finishing) {
                    activityRecord3.destroyIfPossible(str);
                } else {
                    activityRecord3.stopIfPossible();
                }
            }
        }
        int size3 = this.mFinishingActivities.size();
        if (size3 == 0) {
            return;
        }
        ArrayList arrayList2 = new ArrayList(this.mFinishingActivities);
        this.mFinishingActivities.clear();
        for (int i3 = 0; i3 < size3; i3++) {
            ActivityRecord activityRecord4 = (ActivityRecord) arrayList2.get(i3);
            if (activityRecord4.inHistory) {
                activityRecord4.destroyImmediately("finish-".concat(str));
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:212:0x0593 A[Catch: all -> 0x030a, TRY_LEAVE, TryCatch #5 {all -> 0x030a, blocks: (B:118:0x0304, B:119:0x031a, B:121:0x0330, B:122:0x0333, B:124:0x033b, B:126:0x0341, B:128:0x034b, B:130:0x0351, B:132:0x0357, B:134:0x0373, B:135:0x037c, B:210:0x058f, B:212:0x0593, B:216:0x05d4, B:217:0x05d9, B:262:0x0589, B:263:0x058e), top: B:64:0x016d }] */
    /* JADX WARN: Removed duplicated region for block: B:215:0x05d3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean realStartActivityLocked(com.android.server.wm.ActivityRecord r47, com.android.server.wm.WindowProcessController r48, boolean r49, boolean r50) {
        /*
            Method dump skipped, instructions count: 1505
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityTaskSupervisor.realStartActivityLocked(com.android.server.wm.ActivityRecord, com.android.server.wm.WindowProcessController, boolean, boolean):boolean");
    }

    public final void removeRootTask(Task task) {
        if (task.getWindowingMode() != 2) {
            task.forAllLeafTasks(new ActivityTaskSupervisor$$ExternalSyntheticLambda1(this, 2), true);
            return;
        }
        Transition requestTransitionIfNeeded = task.mTransitionController.requestTransitionIfNeeded(4, 0, task, task.mDisplayContent);
        if (requestTransitionIfNeeded == null) {
            task.mTransitionController.collect(task);
        } else {
            requestTransitionIfNeeded.collect(task, false);
        }
        task.cancelAnimation();
        task.setForceHidden(1, true);
        task.ensureActivitiesVisible(true, null);
        activityIdleInternal(null, false, true, null);
        DisplayContent displayContent = this.mRootWindowContainer.getDisplayContent(0);
        ActivityTaskManagerService activityTaskManagerService = this.mService;
        activityTaskManagerService.deferWindowLayout();
        try {
            this.mDeferRootVisibilityUpdate = true;
            task.setWindowingMode(0);
            if (task.getWindowingMode() != 5) {
                task.setBounds(null);
            }
            displayContent.getDefaultTaskDisplayArea().positionTaskBehindHome(task);
            this.mDeferRootVisibilityUpdate = false;
            task.setForceHidden(1, false);
            this.mRootWindowContainer.ensureActivitiesVisible();
            this.mRootWindowContainer.resumeFocusedTasksTopActivities();
            activityTaskManagerService.continueWindowLayout();
        } catch (Throwable th) {
            activityTaskManagerService.continueWindowLayout();
            throw th;
        }
    }

    public final void removeTask(Task task, boolean z, boolean z2, String str) {
        removeTask(task, z, z2, str, false, 1000, -1);
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x00c2 A[DONT_GENERATE] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x00fe A[DONT_GENERATE] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void removeTask(com.android.server.wm.Task r15, boolean r16, boolean r17, java.lang.String r18, boolean r19, int r20, int r21) {
        /*
            Method dump skipped, instructions count: 348
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityTaskSupervisor.removeTask(com.android.server.wm.Task, boolean, boolean, java.lang.String, boolean, int, int):void");
    }

    public final boolean removeTaskById(int i, int i2, int i3, String str, boolean z, boolean z2, boolean z3) {
        Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i, 1, null, false);
        if (anyTaskForId != null) {
            removeTask(anyTaskForId, z, z2, str, z3, i2, i3);
            return true;
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(i, "Request to remove task ignored for non-existent task ", "ActivityTaskManager");
        return false;
    }

    public final void reportActivityLaunched(boolean z, ActivityRecord activityRecord, long j, int i) {
        boolean z2 = false;
        for (int size = this.mWaitingActivityLaunched.size() - 1; size >= 0; size--) {
            WaitInfo waitInfo = (WaitInfo) this.mWaitingActivityLaunched.get(size);
            ActivityMetricsLogger.TransitionInfo transitionInfo = waitInfo.mLaunchingState.mAssociatedTransitionInfo;
            if (transitionInfo != null ? activityRecord == transitionInfo.mLastLaunchedActivity : waitInfo.mTargetComponent.equals(activityRecord.mActivityComponent)) {
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

    public final ActivityInfo resolveActivity(Intent intent, ResolveInfo resolveInfo, final int i, final ProfilerInfo profilerInfo) {
        final ActivityInfo activityInfo = resolveInfo != null ? resolveInfo.activityInfo : null;
        if (activityInfo != null) {
            intent.setComponent(new ComponentName(activityInfo.applicationInfo.packageName, activityInfo.name));
            boolean z = false;
            boolean z2 = (i & 14) != 0;
            boolean z3 = profilerInfo != null;
            if (z2 || z3) {
                if ((Build.IS_DEBUGGABLE || (activityInfo.applicationInfo.flags & 2) != 0) && !activityInfo.processName.equals("system")) {
                    z = true;
                }
                if ((!z2 || z) && (!z3 || z || activityInfo.applicationInfo.isProfileableByShell())) {
                    WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
                    WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            this.mService.mH.post(new Runnable() { // from class: com.android.server.wm.ActivityTaskSupervisor$$ExternalSyntheticLambda10
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ActivityTaskSupervisor activityTaskSupervisor = ActivityTaskSupervisor.this;
                                    ActivityInfo activityInfo2 = activityInfo;
                                    int i2 = i;
                                    ProfilerInfo profilerInfo2 = profilerInfo;
                                    activityTaskSupervisor.getClass();
                                    try {
                                        ActivityTaskManagerService activityTaskManagerService = activityTaskSupervisor.mService;
                                        activityTaskManagerService.mAmInternal.setDebugFlagsForStartingActivity(activityInfo2, i2, profilerInfo2, activityTaskManagerService.mGlobalLock);
                                    } finally {
                                    }
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
                } else {
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("Ignore debugging for non-debuggable app: "), activityInfo.packageName, "ActivityTaskManager");
                }
            }
            String launchToken = intent.getLaunchToken();
            if (activityInfo.launchToken == null && launchToken != null) {
                activityInfo.launchToken = launchToken;
            }
        }
        return activityInfo;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x003c A[Catch: all -> 0x001c, TryCatch #1 {all -> 0x001c, blocks: (B:3:0x0002, B:5:0x0013, B:8:0x0023, B:10:0x0029, B:13:0x0034, B:15:0x003c, B:16:0x003e, B:20:0x006a, B:26:0x0072, B:27:0x0075, B:29:0x001e, B:19:0x0046), top: B:2:0x0002, inners: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.content.pm.ResolveInfo resolveIntent(android.content.Intent r20, java.lang.String r21, int r22, int r23, int r24, int r25) {
        /*
            r19 = this;
            r1 = 32
            java.lang.String r0 = "resolveIntent"
            android.os.Trace.traceBegin(r1, r0)     // Catch: java.lang.Throwable -> L1c
            r0 = 66560(0x10400, float:9.327E-41)
            r0 = r23 | r0
            boolean r3 = r20.isWebIntent()     // Catch: java.lang.Throwable -> L1c
            if (r3 != 0) goto L1e
            int r3 = r20.getFlags()     // Catch: java.lang.Throwable -> L1c
            r3 = r3 & 2048(0x800, float:2.87E-42)
            if (r3 == 0) goto L23
            goto L1e
        L1c:
            r0 = move-exception
            goto L76
        L1e:
            r0 = 8455168(0x810400, float:1.1848214E-38)
            r0 = r23 | r0
        L23:
            boolean r3 = r20.isWebIntent()     // Catch: java.lang.Throwable -> L1c
            if (r3 == 0) goto L33
            int r3 = r20.getFlags()     // Catch: java.lang.Throwable -> L1c
            r3 = r3 & 1024(0x400, float:1.435E-42)
            if (r3 == 0) goto L33
            r3 = 1
            goto L34
        L33:
            r3 = 0
        L34:
            int r4 = r20.getFlags()     // Catch: java.lang.Throwable -> L1c
            r4 = r4 & 512(0x200, float:7.175E-43)
            if (r4 == 0) goto L3e
            r3 = r3 | 2
        L3e:
            r0 = r0 | 128(0x80, float:1.794E-43)
            long r4 = android.os.Binder.clearCallingIdentity()     // Catch: java.lang.Throwable -> L1c
            r6 = r19
            com.android.server.wm.ActivityTaskManagerService r6 = r6.mService     // Catch: java.lang.Throwable -> L71
            android.content.pm.PackageManagerInternal r6 = r6.getPackageManagerInternalLocked()     // Catch: java.lang.Throwable -> L71
            long r11 = (long) r0     // Catch: java.lang.Throwable -> L71
            long r13 = (long) r3     // Catch: java.lang.Throwable -> L71
            com.android.server.pm.PackageManagerService$PackageManagerInternalImpl r6 = (com.android.server.pm.PackageManagerService.PackageManagerInternalImpl) r6     // Catch: java.lang.Throwable -> L71
            com.android.server.pm.PackageManagerService r0 = com.android.server.pm.PackageManagerService.this     // Catch: java.lang.Throwable -> L71
            com.android.server.pm.ResolveIntentHelper r7 = r0.mResolveIntentHelper     // Catch: java.lang.Throwable -> L71
            com.android.server.pm.PackageManagerService r0 = r6.mService     // Catch: java.lang.Throwable -> L71
            com.android.server.pm.Computer r8 = r0.snapshotComputer()     // Catch: java.lang.Throwable -> L71
            r16 = 1
            r9 = r20
            r10 = r21
            r15 = r22
            r17 = r24
            r18 = r25
            android.content.pm.ResolveInfo r0 = r7.resolveIntentInternal(r8, r9, r10, r11, r13, r15, r16, r17, r18)     // Catch: java.lang.Throwable -> L71
            android.os.Binder.restoreCallingIdentity(r4)     // Catch: java.lang.Throwable -> L1c
            android.os.Trace.traceEnd(r1)
            return r0
        L71:
            r0 = move-exception
            android.os.Binder.restoreCallingIdentity(r4)     // Catch: java.lang.Throwable -> L1c
            throw r0     // Catch: java.lang.Throwable -> L1c
        L76:
            android.os.Trace.traceEnd(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityTaskSupervisor.resolveIntent(android.content.Intent, java.lang.String, int, int, int, int):android.content.pm.ResolveInfo");
    }

    public final void restoreRecentTaskLocked(ActivityOptions activityOptions, Task task, boolean z) {
        Task orCreateRootTask = this.mRootWindowContainer.getOrCreateRootTask(activityOptions, task, z);
        WindowContainer parent = task.getParent();
        if (parent == orCreateRootTask || task == orCreateRootTask) {
            return;
        }
        if (parent != null) {
            task.reparent(Integer.MAX_VALUE, orCreateRootTask, "restoreRecentTaskLocked", true);
        } else {
            orCreateRootTask.addChild(task, z, true);
        }
    }

    public final void scheduleIdle() {
        ActivityTaskSupervisorHandler activityTaskSupervisorHandler = this.mHandler;
        if (activityTaskSupervisorHandler.hasMessages(201)) {
            return;
        }
        activityTaskSupervisorHandler.sendEmptyMessage(201);
    }

    public final void scheduleProcessStoppingAndFinishingActivitiesIfNeeded() {
        int i = 1;
        if (this.mStoppingActivities.isEmpty() && this.mFinishingActivities.isEmpty()) {
            return;
        }
        if (this.mRootWindowContainer.allResumedActivitiesIdle()) {
            scheduleIdle();
            return;
        }
        ActivityTaskSupervisorHandler activityTaskSupervisorHandler = this.mHandler;
        if (activityTaskSupervisorHandler.hasMessages(205)) {
            return;
        }
        RootWindowContainer rootWindowContainer = this.mRootWindowContainer;
        rootWindowContainer.getClass();
        boolean[] zArr = {false};
        if (rootWindowContainer.forAllRootTasks(new RootWindowContainer$$ExternalSyntheticLambda12(i, zArr)) ? false : zArr[0]) {
            activityTaskSupervisorHandler.sendEmptyMessage(205);
        }
    }

    public final void scheduleTopResumedActivityStateLossIfNeeded() {
        ActivityRecord activityRecord = this.mTopResumedActivity;
        if (activityRecord == null || this.mTopResumedActivityWaitingForPrev || !activityRecord.scheduleTopResumedActivityChanged(false)) {
            return;
        }
        ActivityRecord activityRecord2 = this.mTopResumedActivity;
        ActivityTaskSupervisorHandler activityTaskSupervisorHandler = this.mHandler;
        Message obtainMessage = activityTaskSupervisorHandler.obtainMessage(FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__SET_APPLICATION_EXEMPTIONS);
        obtainMessage.obj = activityRecord2;
        activityRecord2.topResumedStateLossTime = SystemClock.uptimeMillis();
        activityTaskSupervisorHandler.sendMessageDelayed(obtainMessage, 500L);
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_STATES_enabled[1]) {
            ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_STATES, 3604633008357193496L, 0, null, String.valueOf(activityRecord2));
        }
        this.mTopResumedActivityWaitingForPrev = true;
        this.mTopResumedActivity = null;
    }

    public final void scheduleUpdatePictureInPictureModeIfNeeded(Task task, Task task2) {
        Task rootTask = task.getRootTask();
        if (task2 != null) {
            if (task2 == rootTask || task2.inPinnedWindowingMode() || rootTask.inPinnedWindowingMode()) {
                Rect requestedOverrideBounds = rootTask.getRequestedOverrideBounds();
                task.forAllActivities(new ActivityTaskSupervisor$$ExternalSyntheticLambda1(this, 1));
                this.mPipModeChangedTargetRootTaskBounds = requestedOverrideBounds;
                ActivityTaskSupervisorHandler activityTaskSupervisorHandler = this.mHandler;
                if (activityTaskSupervisorHandler.hasMessages(FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__PLATFORM_ROLE_HOLDER_UPDATE_FAILED)) {
                    return;
                }
                activityTaskSupervisorHandler.sendEmptyMessage(FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__PLATFORM_ROLE_HOLDER_UPDATE_FAILED);
            }
        }
    }

    public final void sendMultiWindowSALogging(Task task, int i, ActivityOptions activityOptions, int i2) {
        if (UserHandle.isSameApp(i2, this.mService.mRecentTasks.mRecentsUid)) {
            if (i == 5 && activityOptions != null && activityOptions.getForceLaunchWindowingMode() == 1) {
                CoreSaLogger.logForAdvanced("2090", "From recent_option");
                return;
            }
            boolean z = (activityOptions == null || activityOptions.getLaunchBounds() == null || activityOptions.getLaunchBounds().isEmpty()) ? false : true;
            if ((task.isDexMode() || !task.inFreeformWindowingMode()) && !z) {
                return;
            }
            if (i == 5) {
                CoreSaLogger.logForAdvanced("2004", "From recent_task");
            } else {
                CoreSaLogger.logForAdvanced("2004", "From recent_option");
            }
        }
    }

    public void setRunningTasks(RunningTasks runningTasks) {
        this.mRunningTasks = runningTasks;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(17:134|(1:138)|139|(1:342)(2:146|147)|(5:(3:329|330|(4:334|(2:151|(1:153))|154|(14:231|232|(1:234)(1:328)|235|236|237|238|239|240|241|242|44c|271|272)(24:158|(1:160)(1:230)|161|162|163|164|(1:173)|174|(3:178|(1:184)(1:182)|183)|185|(1:196)|197|(1:201)|202|(1:206)|208|(1:210)(1:225)|211|(1:213)|(2:219|220)|215|216|217|218)))|240|241|242|44c)|149|(0)|154|(1:156)|231|232|(0)(0)|235|236|237|238|239) */
    /* JADX WARN: Code restructure failed: missing block: B:326:0x04d2, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:327:0x04d3, code lost:
    
        r4 = r5;
        r3 = r30;
        r14 = r11;
     */
    /* JADX WARN: Removed duplicated region for block: B:151:0x026b A[Catch: all -> 0x025d, RuntimeException -> 0x0262, TryCatch #15 {RuntimeException -> 0x0262, all -> 0x025d, blocks: (B:330:0x024e, B:332:0x0254, B:151:0x026b, B:153:0x027f, B:154:0x0292, B:156:0x029e, B:158:0x02a4, B:162:0x02b8, B:208:0x036e, B:211:0x039c, B:213:0x03a3, B:225:0x0398, B:228:0x03c5, B:229:0x03d4), top: B:329:0x024e }] */
    /* JADX WARN: Removed duplicated region for block: B:234:0x03f0 A[Catch: all -> 0x0046, TryCatch #1 {all -> 0x0046, blocks: (B:8:0x0027, B:10:0x003b, B:13:0x0050, B:16:0x005c, B:19:0x007d, B:22:0x008a, B:24:0x0090, B:25:0x0093, B:32:0x00b5, B:35:0x00c4, B:39:0x00d0, B:43:0x00d9, B:45:0x00e3, B:46:0x00e6, B:51:0x00ef, B:53:0x00f5, B:55:0x00fb, B:57:0x0101, B:61:0x0110, B:63:0x0116, B:69:0x0124, B:71:0x012a, B:72:0x0132, B:74:0x0138, B:76:0x013e, B:79:0x0147, B:81:0x014d, B:82:0x014f, B:85:0x0155, B:87:0x015b, B:89:0x0161, B:91:0x016a, B:93:0x0170, B:95:0x0176, B:98:0x0184, B:99:0x018a, B:104:0x0199, B:106:0x01a3, B:107:0x01ad, B:113:0x01b8, B:115:0x01be, B:117:0x01c4, B:119:0x01ca, B:121:0x01d0, B:123:0x01d6, B:125:0x01e8, B:127:0x01ec, B:130:0x01f8, B:215:0x03ba, B:216:0x03bf, B:223:0x03b4, B:224:0x03b9, B:232:0x03d8, B:234:0x03f0, B:235:0x03f9, B:353:0x0592, B:354:0x0597, B:359:0x058c, B:360:0x0591, B:363:0x0598, B:364:0x05ae, B:356:0x0580, B:220:0x03a8, B:340:0x0572, B:341:0x057a, B:347:0x057d), top: B:7:0x0027, inners: #3, #7, #12 }] */
    /* JADX WARN: Removed duplicated region for block: B:244:0x044d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0090 A[Catch: all -> 0x0046, TryCatch #1 {all -> 0x0046, blocks: (B:8:0x0027, B:10:0x003b, B:13:0x0050, B:16:0x005c, B:19:0x007d, B:22:0x008a, B:24:0x0090, B:25:0x0093, B:32:0x00b5, B:35:0x00c4, B:39:0x00d0, B:43:0x00d9, B:45:0x00e3, B:46:0x00e6, B:51:0x00ef, B:53:0x00f5, B:55:0x00fb, B:57:0x0101, B:61:0x0110, B:63:0x0116, B:69:0x0124, B:71:0x012a, B:72:0x0132, B:74:0x0138, B:76:0x013e, B:79:0x0147, B:81:0x014d, B:82:0x014f, B:85:0x0155, B:87:0x015b, B:89:0x0161, B:91:0x016a, B:93:0x0170, B:95:0x0176, B:98:0x0184, B:99:0x018a, B:104:0x0199, B:106:0x01a3, B:107:0x01ad, B:113:0x01b8, B:115:0x01be, B:117:0x01c4, B:119:0x01ca, B:121:0x01d0, B:123:0x01d6, B:125:0x01e8, B:127:0x01ec, B:130:0x01f8, B:215:0x03ba, B:216:0x03bf, B:223:0x03b4, B:224:0x03b9, B:232:0x03d8, B:234:0x03f0, B:235:0x03f9, B:353:0x0592, B:354:0x0597, B:359:0x058c, B:360:0x0591, B:363:0x0598, B:364:0x05ae, B:356:0x0580, B:220:0x03a8, B:340:0x0572, B:341:0x057a, B:347:0x057d), top: B:7:0x0027, inners: #3, #7, #12 }] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:328:0x03f7  */
    /* JADX WARN: Removed duplicated region for block: B:365:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x012a A[Catch: all -> 0x0046, TryCatch #1 {all -> 0x0046, blocks: (B:8:0x0027, B:10:0x003b, B:13:0x0050, B:16:0x005c, B:19:0x007d, B:22:0x008a, B:24:0x0090, B:25:0x0093, B:32:0x00b5, B:35:0x00c4, B:39:0x00d0, B:43:0x00d9, B:45:0x00e3, B:46:0x00e6, B:51:0x00ef, B:53:0x00f5, B:55:0x00fb, B:57:0x0101, B:61:0x0110, B:63:0x0116, B:69:0x0124, B:71:0x012a, B:72:0x0132, B:74:0x0138, B:76:0x013e, B:79:0x0147, B:81:0x014d, B:82:0x014f, B:85:0x0155, B:87:0x015b, B:89:0x0161, B:91:0x016a, B:93:0x0170, B:95:0x0176, B:98:0x0184, B:99:0x018a, B:104:0x0199, B:106:0x01a3, B:107:0x01ad, B:113:0x01b8, B:115:0x01be, B:117:0x01c4, B:119:0x01ca, B:121:0x01d0, B:123:0x01d6, B:125:0x01e8, B:127:0x01ec, B:130:0x01f8, B:215:0x03ba, B:216:0x03bf, B:223:0x03b4, B:224:0x03b9, B:232:0x03d8, B:234:0x03f0, B:235:0x03f9, B:353:0x0592, B:354:0x0597, B:359:0x058c, B:360:0x0591, B:363:0x0598, B:364:0x05ae, B:356:0x0580, B:220:0x03a8, B:340:0x0572, B:341:0x057a, B:347:0x057d), top: B:7:0x0027, inners: #3, #7, #12 }] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0132 A[Catch: all -> 0x0046, TryCatch #1 {all -> 0x0046, blocks: (B:8:0x0027, B:10:0x003b, B:13:0x0050, B:16:0x005c, B:19:0x007d, B:22:0x008a, B:24:0x0090, B:25:0x0093, B:32:0x00b5, B:35:0x00c4, B:39:0x00d0, B:43:0x00d9, B:45:0x00e3, B:46:0x00e6, B:51:0x00ef, B:53:0x00f5, B:55:0x00fb, B:57:0x0101, B:61:0x0110, B:63:0x0116, B:69:0x0124, B:71:0x012a, B:72:0x0132, B:74:0x0138, B:76:0x013e, B:79:0x0147, B:81:0x014d, B:82:0x014f, B:85:0x0155, B:87:0x015b, B:89:0x0161, B:91:0x016a, B:93:0x0170, B:95:0x0176, B:98:0x0184, B:99:0x018a, B:104:0x0199, B:106:0x01a3, B:107:0x01ad, B:113:0x01b8, B:115:0x01be, B:117:0x01c4, B:119:0x01ca, B:121:0x01d0, B:123:0x01d6, B:125:0x01e8, B:127:0x01ec, B:130:0x01f8, B:215:0x03ba, B:216:0x03bf, B:223:0x03b4, B:224:0x03b9, B:232:0x03d8, B:234:0x03f0, B:235:0x03f9, B:353:0x0592, B:354:0x0597, B:359:0x058c, B:360:0x0591, B:363:0x0598, B:364:0x05ae, B:356:0x0580, B:220:0x03a8, B:340:0x0572, B:341:0x057a, B:347:0x057d), top: B:7:0x0027, inners: #3, #7, #12 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0027 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int startActivityFromRecents(int r29, int r30, int r31, com.android.server.wm.SafeActivityOptions r32, boolean r33) {
        /*
            Method dump skipped, instructions count: 1460
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityTaskSupervisor.startActivityFromRecents(int, int, int, com.android.server.wm.SafeActivityOptions, boolean):int");
    }

    public final void startSpecificActivity(ActivityRecord activityRecord, boolean z, boolean z2) {
        boolean z3;
        Handler handler;
        final WindowProcessController processController = this.mService.getProcessController(activityRecord.info.applicationInfo.uid, activityRecord.processName);
        if (processController == null || !processController.hasThread()) {
            Context context = this.mService.mContext;
            Intent intent = activityRecord.intent;
            if (intent != null && (!Flags.sandboxActivitySdkBasedContext() ? !intent.isSandboxActivity(context) : !SdkSandboxActivityAuthority.isSdkSandboxActivityIntent(context, intent))) {
                Slog.e("ActivityTaskManager", "Abort sandbox activity launching as no sandbox process to host it.");
                activityRecord.finishIfPossible("No sandbox process for the activity", false);
                activityRecord.launchFailed = true;
                activityRecord.detachFromProcess();
                return;
            }
            z3 = false;
        } else {
            try {
                realStartActivityLocked(activityRecord, processController, z, z2);
                return;
            } catch (RemoteException e) {
                Slog.w("ActivityTaskManager", "Exception when starting activity " + activityRecord.intent.getComponent().flattenToShortString(), e);
                this.mService.mProcessNames.remove(processController.mName, processController.mUid);
                WindowProcessControllerMap windowProcessControllerMap = this.mService.mProcessMap;
                int i = processController.mPid;
                WindowProcessController windowProcessController = (WindowProcessController) windowProcessControllerMap.mPidMap.get(i);
                if (windowProcessController != null) {
                    windowProcessControllerMap.mPidMap.remove(i);
                    windowProcessControllerMap.removeProcessFromUidMap(windowProcessController);
                    windowProcessController.unregisterConfigurationListeners();
                }
                z3 = true;
            }
        }
        activityRecord.notifyUnknownVisibilityLaunchedForKeyguardTransition();
        boolean z4 = z && activityRecord.isTopRunningActivity();
        if (activityRecord.getDisplayId() == 2) {
            this.mService.startProcessAsync(activityRecord, z4 ? "top-activity-on-dex" : "activity-on-dex", z3, z4);
        } else {
            this.mService.startProcessAsync(activityRecord, z4 ? "top-activity" : "activity", z3, z4);
        }
        if (!CoreRune.MNO_TMO_DEVICE_REPORTING || !DeviceReportingSecurityChecker.getStatus() || (handler = AppStateBroadcaster.mObjHandler) == null || processController == null) {
            return;
        }
        final String str = activityRecord.packageName;
        handler.post(new Runnable() { // from class: com.android.server.wm.ActivityTaskSupervisor$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                AppStateBroadcaster.sendApplicationStart(processController.mPid, str);
            }
        });
    }

    public final void updateHomeProcessIfNeeded(ActivityRecord activityRecord) {
        WindowProcessController windowProcessController;
        if (activityRecord.isActivityTypeHome()) {
            Task task = activityRecord.task;
            ActivityRecord activity = task.realActivity == null ? null : task.getActivity(new Task$$ExternalSyntheticLambda19(3, task), false);
            if (activity == null || (windowProcessController = activity.app) == null || this.mService.mHomeProcess == windowProcessController) {
                return;
            }
            ActivityTaskSupervisorHandler activityTaskSupervisorHandler = this.mHandler;
            if (!activityTaskSupervisorHandler.hasMessages(FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__SET_MTE_POLICY)) {
                activityTaskSupervisorHandler.obtainMessage(FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__SET_MTE_POLICY, "homeChanged").sendToTarget();
            }
            this.mService.mHomeProcess = windowProcessController;
        }
    }

    public final ActivityRecord updateTopResumedActivityIfNeeded(String str) {
        WindowProcessController windowProcessController;
        WindowProcessController windowProcessController2;
        ActivityRecord activityRecord = this.mTopResumedActivity;
        Task topDisplayFocusedRootTask = this.mRootWindowContainer.getTopDisplayFocusedRootTask();
        if (topDisplayFocusedRootTask == null || topDisplayFocusedRootTask.getTopResumedActivity() == activityRecord) {
            if (topDisplayFocusedRootTask == null) {
                scheduleTopResumedActivityStateLossIfNeeded();
            }
            if (this.mService.mSleeping) {
                ActivityTaskManagerService activityTaskManagerService = this.mService;
                ActivityRecord topResumedActivity = activityTaskManagerService.mRootWindowContainer.getTopResumedActivity();
                if (topResumedActivity != null) {
                    activityTaskManagerService.getClass();
                    windowProcessController = topResumedActivity.app;
                } else {
                    windowProcessController = null;
                }
                activityTaskManagerService.mTopApp = windowProcessController;
                if (activityTaskManagerService.mTopApp == activityTaskManagerService.mPreviousProcess) {
                    activityTaskManagerService.mPreviousProcess = null;
                }
            }
            return this.mTopResumedActivity;
        }
        scheduleTopResumedActivityStateLossIfNeeded();
        ActivityRecord topResumedActivity2 = topDisplayFocusedRootTask.getTopResumedActivity();
        this.mTopResumedActivity = topResumedActivity2;
        if (topResumedActivity2 != null && activityRecord != null) {
            WindowProcessController windowProcessController3 = topResumedActivity2.app;
            if (windowProcessController3 != null) {
                windowProcessController3.addToPendingTop();
            }
            this.mService.updateOomAdj();
        }
        ActivityRecord activityRecord2 = this.mTopResumedActivity;
        if (activityRecord2 != null) {
            this.mService.setLastResumedActivityUncheckLocked(activityRecord2, str);
        }
        ActivityRecord activityRecord3 = this.mTopResumedActivity;
        if (activityRecord3 != null && !this.mTopResumedActivityWaitingForPrev) {
            activityRecord3.scheduleTopResumedActivityChanged(true);
        }
        if (this.mTopResumedActivity != null || this.mService.mSleeping) {
            ActivityTaskManagerService activityTaskManagerService2 = this.mService;
            ActivityRecord activityRecord4 = this.mTopResumedActivity;
            if (activityRecord4 == null) {
                activityRecord4 = activityTaskManagerService2.mRootWindowContainer.getTopResumedActivity();
            }
            if (activityRecord4 != null) {
                activityTaskManagerService2.getClass();
                windowProcessController2 = activityRecord4.app;
            } else {
                windowProcessController2 = null;
            }
            activityTaskManagerService2.mTopApp = windowProcessController2;
            if (activityTaskManagerService2.mTopApp == activityTaskManagerService2.mPreviousProcess) {
                activityTaskManagerService2.mPreviousProcess = null;
            }
        }
        return this.mTopResumedActivity;
    }

    public final void wakeUp(String str) {
        this.mPowerManager.wakeUp(SystemClock.uptimeMillis(), 2, ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("android.server.am:TURN_ON:", str));
    }
}
