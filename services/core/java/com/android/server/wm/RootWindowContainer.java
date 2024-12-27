package com.android.server.wm;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.AppGlobals;
import android.app.servertransaction.WindowStateResizeItem;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.UserProperties;
import android.content.res.Configuration;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManagerInternal;
import android.net.Uri;
import android.os.Binder;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.os.WorkSource;
import android.os.storage.StorageManager;
import android.provider.Settings;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.EventLog;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.SurfaceControl;
import android.window.ITaskFragmentOrganizer;
import android.window.TaskFragmentInfo;
import android.window.TaskFragmentParentInfo;
import android.window.TaskFragmentTransaction;
import com.android.internal.app.ResolverActivity;
import com.android.internal.os.SomeArgs;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.internal.util.function.pooled.PooledPredicate;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.accessibility.ProxyManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.UserState;
import com.android.server.pm.UserManagerInternal;
import com.android.server.policy.PhoneWindowManager;
import com.android.server.wm.ActivityRecord;
import com.android.server.wm.ActivityStarter;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.ActivityTaskManagerService.SleepTokenAcquirerImpl;
import com.android.server.wm.ActivityTaskSupervisor;
import com.android.server.wm.DeviceStateController;
import com.android.server.wm.DexController;
import com.android.server.wm.DisplayContent;
import com.android.server.wm.KeyguardController;
import com.android.server.wm.RootWindowContainer;
import com.android.server.wm.TaskChangeNotificationController;
import com.android.server.wm.TaskFragmentOrganizerController;
import com.android.server.wm.Transition;
import com.android.server.wm.TransitionController;
import com.android.window.flags.Flags;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.ipm.SecIpmManager;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.Executor;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class RootWindowContainer extends WindowContainer implements DisplayManager.DisplayListener {
    public final RootWindowContainer$$ExternalSyntheticLambda7 mCloseSystemDialogsConsumer;
    public String mCloseSystemDialogsReason;
    public int mCurrentUser;
    public long mDeXUserActivityTimeout;
    public DisplayContent mDefaultDisplay;
    public String mDestroyAllActivitiesReason;
    public final AnonymousClass1 mDestroyAllActivitiesRunnable;
    public final AnonymousClass2 mDestroyTargetAllActivitiesRunnable;
    public final DeviceStateController mDeviceStateController;
    public final SparseArray mDisplayAccessUIDs;
    public DisplayManager mDisplayManager;
    public DisplayManagerInternal mDisplayManagerInternal;
    public final ActivityTaskManagerService.SleepTokenAcquirerImpl mDisplayOffTokenAcquirer;
    public final DisplayRotationCoordinator mDisplayRotationCoordinator;
    public final SparseArray mDisplayTransactions;
    public final FinishDisabledPackageActivitiesHelper mFinishDisabledPackageActivitiesHelper;
    public final MyHandler mHandler;
    public WindowState mLastWindowFreezeSource;
    public Runnable mMaybeAbortPipEnterRunnable;
    public boolean mObscureApplicationContentOnSecondaryDisplays;
    public boolean mOrientationChangeComplete;
    public final AnonymousClass1 mRankTaskLayersRunnable;
    public float mScreenBrightnessOverride;
    public String mScreenBrightnessOverridePackage;
    public long mScreenDimDuration;
    public SecIpmManager mSecIpmManager;
    public final ActivityTaskManagerService mService;
    public final SparseArray mSleepTokens;
    public boolean mSustainedPerformanceModeCurrent;
    public boolean mSustainedPerformanceModeEnabled;
    public boolean mTaskLayersChanged;
    public final ActivityTaskSupervisor mTaskSupervisor;
    public final FindTaskResult mTmpFindTaskResult;
    public int mTmpTaskLayerRank;
    public final ArrayMap mTopFocusedAppByProcess;
    public int mTopFocusedDisplayId;
    public boolean mUpdateRotation;
    public long mUserActivityTimeout;
    public final SparseIntArray mUserRootTaskInFront;
    public boolean mWallpaperActionPending;
    public WindowManagerService mWindowManager;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.wm.RootWindowContainer$1, reason: invalid class name */
    public final class AnonymousClass1 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ RootWindowContainer this$0;

        public /* synthetic */ AnonymousClass1(RootWindowContainer rootWindowContainer, int i) {
            this.$r8$classId = i;
            this.this$0 = rootWindowContainer;
        }

        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    WindowManagerGlobalLock windowManagerGlobalLock = this.this$0.mService.mGlobalLock;
                    WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            try {
                                this.this$0.mTaskSupervisor.beginDeferResume();
                                this.this$0.forAllActivities(new RootWindowContainer$$ExternalSyntheticLambda1(3, this));
                            } finally {
                                this.this$0.mTaskSupervisor.endDeferResume();
                                this.this$0.resumeFocusedTasksTopActivities();
                            }
                        } finally {
                            WindowManagerService.resetPriorityAfterLockedSection();
                        }
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                default:
                    WindowManagerGlobalLock windowManagerGlobalLock2 = this.this$0.mService.mGlobalLock;
                    WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock2) {
                        try {
                            RootWindowContainer rootWindowContainer = this.this$0;
                            if (rootWindowContainer.mTaskLayersChanged) {
                                rootWindowContainer.mTaskLayersChanged = false;
                                rootWindowContainer.rankTaskLayers();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.wm.RootWindowContainer$2, reason: invalid class name */
    public final class AnonymousClass2 implements Runnable {
        public final /* synthetic */ int $r8$classId = 1;
        public Object val$enterPipThrowable;
        public ConfigurationContainer val$rootTask;

        public AnonymousClass2() {
        }

        public AnonymousClass2(Task task, Throwable th) {
            this.val$rootTask = task;
            this.val$enterPipThrowable = th;
        }

        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    WindowManagerGlobalLock windowManagerGlobalLock = RootWindowContainer.this.mService.mGlobalLock;
                    WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            if (RootWindowContainer.this.mTransitionController.inTransition()) {
                                RootWindowContainer rootWindowContainer = RootWindowContainer.this;
                                final Runnable runnable = rootWindowContainer.mMaybeAbortPipEnterRunnable;
                                rootWindowContainer.mTransitionController.mStateValidators.add(new Runnable() { // from class: com.android.server.wm.RootWindowContainer$2$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        RootWindowContainer.AnonymousClass2 anonymousClass2 = RootWindowContainer.AnonymousClass2.this;
                                        Runnable runnable2 = runnable;
                                        RootWindowContainer rootWindowContainer2 = RootWindowContainer.this;
                                        if (runnable2 != rootWindowContainer2.mMaybeAbortPipEnterRunnable) {
                                            return;
                                        }
                                        rootWindowContainer2.mMaybeAbortPipEnterRunnable = null;
                                        anonymousClass2.run();
                                    }
                                });
                                WindowManagerService.resetPriorityAfterLockedSection();
                                return;
                            }
                            RootWindowContainer rootWindowContainer2 = RootWindowContainer.this;
                            rootWindowContainer2.mMaybeAbortPipEnterRunnable = null;
                            rootWindowContainer2.mService.deferWindowLayout();
                            ActivityRecord topMostActivity = ((Task) this.val$rootTask).getTopMostActivity();
                            ActivityManager.RunningTaskInfo taskInfo = ((Task) this.val$rootTask).getTaskInfo();
                            if (topMostActivity != null && !topMostActivity.inPinnedWindowingMode() && ((Task) this.val$rootTask).abortPipEnter(topMostActivity)) {
                                Slog.wtf("WindowManager", "Enter PiP was aborted via a scheduled timeouttask_state_before=" + taskInfo + "task_state_after=" + ((Task) this.val$rootTask).getTaskInfo(), (Throwable) this.val$enterPipThrowable);
                            }
                            RootWindowContainer.this.mService.continueWindowLayout();
                            return;
                        } finally {
                            WindowManagerService.resetPriorityAfterLockedSection();
                        }
                    }
                default:
                    WindowManagerGlobalLock windowManagerGlobalLock2 = RootWindowContainer.this.mService.mGlobalLock;
                    WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock2) {
                        try {
                            try {
                                RootWindowContainer.this.mTaskSupervisor.beginDeferResume();
                                RootWindowContainer.this.forAllActivities(new RootWindowContainer$$ExternalSyntheticLambda1(4, this));
                            } finally {
                                RootWindowContainer.this.mTaskSupervisor.endDeferResume();
                                RootWindowContainer.this.resumeFocusedTasksTopActivities();
                            }
                        } finally {
                            WindowManagerService.resetPriorityAfterLockedSection();
                        }
                    }
                    return;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FindTaskResult implements Predicate {
        public ComponentName cls;
        public Uri documentData;
        public boolean isDocument;
        public int mActivityType;
        public ActivityRecord mCandidateRecord;
        public ActivityRecord mIdealRecord;
        public boolean mIncludeLaunchedFromBubble;
        public ActivityInfo mInfo;
        public Intent mIntent;
        public String mTaskAffinity;
        public int userId;

        public final void process(WindowContainer windowContainer) {
            this.cls = this.mIntent.getComponent();
            if (this.mInfo.targetActivity != null) {
                ActivityInfo activityInfo = this.mInfo;
                this.cls = new ComponentName(activityInfo.packageName, activityInfo.targetActivity);
            }
            this.userId = UserHandle.getUserId(this.mInfo.applicationInfo.uid);
            Intent intent = this.mIntent;
            boolean isDocument = intent.isDocument() & (intent != null);
            this.isDocument = isDocument;
            this.documentData = isDocument ? this.mIntent.getData() : null;
            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_TASKS_enabled[0]) {
                ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_TASKS, -8961882615747561040L, 0, null, String.valueOf(this.mInfo), String.valueOf(windowContainer));
            }
            windowContainer.forAllLeafTasks(this);
        }

        /* JADX WARN: Removed duplicated region for block: B:63:0x010a  */
        /* JADX WARN: Removed duplicated region for block: B:67:0x0111  */
        @Override // java.util.function.Predicate
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean test(java.lang.Object r20) {
            /*
                Method dump skipped, instructions count: 617
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.RootWindowContainer.FindTaskResult.test(java.lang.Object):boolean");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FinishDisabledPackageActivitiesHelper implements Predicate {
        public final ArrayList mCollectedActivities = new ArrayList();
        public boolean mDoit;
        public boolean mEvenPersistent;
        public Set mFilterByClasses;
        public Task mLastTask;
        public boolean mOnlyRemoveNoProcess;
        public String mPackageName;
        public int mUserId;

        public FinishDisabledPackageActivitiesHelper() {
        }

        @Override // java.util.function.Predicate
        public final boolean test(Object obj) {
            Set set;
            ActivityRecord activityRecord = (ActivityRecord) obj;
            boolean z = (activityRecord.packageName.equals(this.mPackageName) && ((set = this.mFilterByClasses) == null || set.contains(activityRecord.mActivityComponent.getClassName()))) || (this.mPackageName == null && activityRecord.mUserId == this.mUserId);
            boolean z2 = !activityRecord.hasProcess();
            int i = this.mUserId;
            if (i != -1 && activityRecord.mUserId != i) {
                return false;
            }
            if (!z && activityRecord.task != this.mLastTask) {
                return false;
            }
            if (!z2 && !this.mEvenPersistent && activityRecord.app.mPersistent) {
                return false;
            }
            if (!this.mDoit) {
                return !activityRecord.finishing;
            }
            this.mCollectedActivities.add(activityRecord);
            this.mLastTask = activityRecord.task;
            return false;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MyHandler extends Handler {
        public MyHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                RootWindowContainer.this.mWmService.mPowerManagerInternal.setScreenBrightnessOverrideFromWindowManager(Float.intBitsToFloat(message.arg1), String.valueOf(message.obj));
                return;
            }
            if (i == 2) {
                RootWindowContainer.this.mWmService.mPowerManagerInternal.setUserActivityTimeoutOverrideFromWindowManager(((Long) message.obj).longValue());
                return;
            }
            if (i != 3) {
                if (i == 10) {
                    RootWindowContainer.this.mWmService.mPowerManagerInternal.setUserActivityTimeoutForDexOverrideFromWindowManager(((Long) message.obj).longValue());
                    return;
                } else {
                    if (i != 11) {
                        return;
                    }
                    RootWindowContainer.this.mWmService.mPowerManagerInternal.setScreenDimDurationOverrideFromWindowManager(((Long) message.obj).longValue());
                    return;
                }
            }
            WindowManagerGlobalLock windowManagerGlobalLock = RootWindowContainer.this.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    RootWindowContainer.this.sendSleepTransition((DisplayContent) message.obj);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SleepToken {
        public final long mAcquireTime = SystemClock.uptimeMillis();
        public final int mDisplayId;
        public final int mHashKey;
        public final boolean mIsSwappingDisplay;
        public final String mTag;

        public SleepToken(int i, String str, boolean z) {
            this.mTag = str;
            this.mDisplayId = i;
            this.mIsSwappingDisplay = z;
            this.mHashKey = (str + i).hashCode();
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("{\"");
            sb.append(this.mTag);
            sb.append("\", display ");
            sb.append(this.mDisplayId);
            sb.append(this.mIsSwappingDisplay ? " is swapping " : "");
            sb.append(", acquire at ");
            sb.append(TimeUtils.formatUptime(this.mAcquireTime));
            sb.append("}");
            return sb.toString();
        }
    }

    public RootWindowContainer(WindowManagerService windowManagerService) {
        super(windowManagerService);
        this.mLastWindowFreezeSource = null;
        this.mScreenBrightnessOverride = Float.NaN;
        this.mScreenBrightnessOverridePackage = "";
        this.mUserActivityTimeout = -1L;
        this.mScreenDimDuration = -1L;
        this.mUpdateRotation = false;
        this.mObscureApplicationContentOnSecondaryDisplays = false;
        this.mSustainedPerformanceModeEnabled = false;
        this.mSustainedPerformanceModeCurrent = false;
        this.mOrientationChangeComplete = true;
        this.mWallpaperActionPending = false;
        this.mTopFocusedDisplayId = -1;
        this.mTopFocusedAppByProcess = new ArrayMap();
        this.mDeXUserActivityTimeout = -1L;
        this.mDisplayAccessUIDs = new SparseArray();
        this.mDisplayTransactions = new SparseArray();
        this.mUserRootTaskInFront = new SparseIntArray(2);
        this.mSleepTokens = new SparseArray();
        this.mTaskLayersChanged = true;
        this.mRankTaskLayersRunnable = new AnonymousClass1(this, 1);
        this.mDestroyAllActivitiesRunnable = new AnonymousClass1(this, 0);
        this.mMaybeAbortPipEnterRunnable = null;
        this.mDestroyTargetAllActivitiesRunnable = new AnonymousClass2();
        FindTaskResult findTaskResult = new FindTaskResult();
        findTaskResult.mIncludeLaunchedFromBubble = true;
        this.mTmpFindTaskResult = findTaskResult;
        this.mCloseSystemDialogsConsumer = new RootWindowContainer$$ExternalSyntheticLambda7(this, 1);
        this.mFinishDisabledPackageActivitiesHelper = new FinishDisabledPackageActivitiesHelper();
        this.mHandler = new MyHandler(windowManagerService.mH.getLooper());
        ActivityTaskManagerService activityTaskManagerService = windowManagerService.mAtmService;
        this.mService = activityTaskManagerService;
        ActivityTaskSupervisor activityTaskSupervisor = activityTaskManagerService.mTaskSupervisor;
        this.mTaskSupervisor = activityTaskSupervisor;
        activityTaskSupervisor.mRootWindowContainer = this;
        this.mDisplayOffTokenAcquirer = activityTaskManagerService.new SleepTokenAcquirerImpl("Display-off");
        this.mDeviceStateController = new DeviceStateController(windowManagerService.mContext, windowManagerService.mGlobalLock);
        this.mDisplayRotationCoordinator = new DisplayRotationCoordinator();
    }

    public static boolean canLaunchOnDisplay(int i, ActivityRecord activityRecord) {
        if (activityRecord == null || activityRecord.mAtmService.mTaskSupervisor.canPlaceEntityOnDisplay(i, activityRecord.launchedFromPid, activityRecord.launchedFromUid, null, activityRecord.info)) {
            return true;
        }
        Slog.w("WindowManager", "Not allow to launch " + activityRecord + " on display " + i);
        return false;
    }

    public static boolean canLaunchOnDisplay(Task task, ActivityRecord activityRecord) {
        if (task == null) {
            Slog.w("WindowManager", "canLaunchOnDisplay(), invalid task: " + task);
            return false;
        }
        if (task.isAttached()) {
            return canLaunchOnDisplay(task.getTaskDisplayArea().mDisplayContent.mDisplayId, activityRecord);
        }
        Slog.w("WindowManager", "canLaunchOnDisplay(), Task is not attached: " + task);
        return false;
    }

    public static ActivityTaskManager.RootTaskInfo getRootTaskInfo(final Task task) {
        ActivityTaskManager.RootTaskInfo rootTaskInfo = new ActivityTaskManager.RootTaskInfo();
        task.fillTaskInfo(rootTaskInfo, true);
        DisplayContent displayContent = task.getDisplayContent();
        if (displayContent == null) {
            rootTaskInfo.position = -1;
        } else {
            final int[] iArr = new int[1];
            final boolean[] zArr = new boolean[1];
            displayContent.forAllRootTasks(new Predicate() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda10
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    Task task2 = Task.this;
                    boolean[] zArr2 = zArr;
                    int[] iArr2 = iArr;
                    if (task2 == ((Task) obj)) {
                        zArr2[0] = true;
                        return true;
                    }
                    iArr2[0] = iArr2[0] + 1;
                    return false;
                }
            }, false);
            rootTaskInfo.position = zArr[0] ? iArr[0] : -1;
        }
        rootTaskInfo.visible = task.shouldBeVisible(null);
        task.getBounds(rootTaskInfo.bounds);
        int[] iArr2 = {0};
        task.forAllLeafTasks(new Task$$ExternalSyntheticLambda8(1, iArr2), false);
        int i = iArr2[0];
        rootTaskInfo.childTaskIds = new int[i];
        rootTaskInfo.childTaskNames = new String[i];
        rootTaskInfo.childTaskBounds = new Rect[i];
        rootTaskInfo.childTaskUserIds = new int[i];
        task.forAllLeafTasks(new RootWindowContainer$$ExternalSyntheticLambda20(1, new int[]{0}, rootTaskInfo), false);
        ActivityRecord activityRecord = task.topRunningActivity(false);
        rootTaskInfo.topActivity = activityRecord != null ? activityRecord.intent.getComponent() : null;
        return rootTaskInfo;
    }

    public static int resolveActivityType(ActivityOptions activityOptions, ActivityRecord activityRecord, Task task) {
        int activityType = activityRecord != null ? activityRecord.getActivityType() : 0;
        if (activityType == 0 && task != null) {
            activityType = task.getActivityType();
        }
        if (activityType != 0) {
            return activityType;
        }
        if (activityOptions != null) {
            activityType = activityOptions.getLaunchActivityType();
        }
        if (activityType != 0) {
            return activityType;
        }
        return 1;
    }

    public final boolean allPausedActivitiesComplete() {
        boolean[] zArr = {true};
        if (forAllLeafTasks(new RootWindowContainer$$ExternalSyntheticLambda12(0, zArr))) {
            return false;
        }
        return zArr[0];
    }

    public final boolean allResumedActivitiesIdle() {
        Task focusedRootTask;
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            DisplayContent displayContent = (DisplayContent) getChildAt(childCount);
            if (!displayContent.mSleeping && (focusedRootTask = displayContent.getFocusedRootTask()) != null && focusedRootTask.hasActivity()) {
                ActivityRecord topResumedActivity = focusedRootTask.getTopResumedActivity();
                if (topResumedActivity == null || !topResumedActivity.idle) {
                    if (ProtoLogImpl_54989576.Cache.WM_DEBUG_STATES_enabled[0]) {
                        ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_STATES, 1653728842643223887L, 1, null, Long.valueOf(focusedRootTask.getRootTask().mTaskId), String.valueOf(topResumedActivity));
                    }
                    return false;
                }
                if (this.mTransitionController.isTransientLaunch(topResumedActivity)) {
                    return false;
                }
            }
        }
        this.mService.endPowerMode(1);
        return true;
    }

    public final Task anyTaskForId(int i) {
        return anyTaskForId(i, 2, null, false);
    }

    public final Task anyTaskForId(int i, int i2, ActivityOptions activityOptions, boolean z) {
        Task task;
        Task orCreateRootTask;
        if (i2 != 2 && activityOptions != null) {
            throw new IllegalArgumentException("Should not specify activity options for non-restore lookup");
        }
        PooledPredicate obtainPredicate = PooledLambda.obtainPredicate(new AppTransition$$ExternalSyntheticLambda1(), PooledLambda.__(Task.class), Integer.valueOf(i));
        Task task2 = getTask(obtainPredicate);
        obtainPredicate.recycle();
        if (task2 != null) {
            if (activityOptions != null && (orCreateRootTask = getOrCreateRootTask(activityOptions, task2, z)) != null && task2.getRootTask() != orCreateRootTask && task2.getParent() != orCreateRootTask) {
                task2.reparent(orCreateRootTask, z, z ? 0 : 2, true, true, "anyTaskForId");
            }
            return task2;
        }
        if (i2 == 0 || (task = this.mTaskSupervisor.mRecentTasks.getTask(i)) == null) {
            return null;
        }
        if (i2 == 1) {
            return task;
        }
        this.mTaskSupervisor.restoreRecentTaskLocked(activityOptions, task, z);
        return task;
    }

    public final void applySleepTokens(boolean z) {
        int i;
        int i2;
        Task task;
        boolean z2 = false;
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            final DisplayContent displayContent = (DisplayContent) getChildAt(childCount);
            final boolean shouldSleep = displayContent.shouldSleep();
            boolean z3 = displayContent.mSleeping;
            if (shouldSleep != z3) {
                displayContent.mSleeping = shouldSleep;
                if (displayContent.mTransitionController.isShellTransitionsEnabled() && !z2 && shouldSleep && !displayContent.mAllSleepTokens.isEmpty()) {
                    if (!this.mHandler.hasMessages(3)) {
                        MyHandler myHandler = this.mHandler;
                        myHandler.sendMessageDelayed(myHandler.obtainMessage(3, displayContent), 1000L);
                    }
                    if (CoreRune.FW_SHELL_TRANSITION_BUG_FIX && displayContent.mTransitionController.isCollecting()) {
                        Transition transition = displayContent.mTransitionController.mCollectingTransition;
                        if ((transition.mFlags & 276736) != 0) {
                            transition.setReady(displayContent, true);
                        }
                    }
                    z2 = true;
                }
                if (z) {
                    if (!shouldSleep && displayContent.mTransitionController.isShellTransitionsEnabled() && !displayContent.mTransitionController.isCollecting()) {
                        if (displayContent.mRootWindowContainer.mTaskSupervisor.mKeyguardController.isKeyguardOccluded(displayContent.mDisplayId)) {
                            KeyguardController keyguardController = displayContent.mRootWindowContainer.mTaskSupervisor.mKeyguardController;
                            task = keyguardController.getDisplayState(displayContent.mDisplayId).mTopOccludesActivity != null ? keyguardController.getDisplayState(displayContent.mDisplayId).mTopOccludesActivity.getRootTask() : keyguardController.getDisplayState(displayContent.mDisplayId).mDismissingKeyguardActivity != null ? keyguardController.getDisplayState(displayContent.mDisplayId).mDismissingKeyguardActivity.getRootTask() : null;
                            i = 4096;
                            i2 = 8;
                        } else {
                            i = 0;
                            i2 = 0;
                            task = null;
                        }
                        if (z3) {
                            i2 = 11;
                        }
                        TransitionController transitionController = displayContent.mTransitionController;
                        transitionController.requestStartTransition(transitionController.createTransition(i2, i), task, null, null);
                    }
                    displayContent.forAllRootTasks(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda4
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            RootWindowContainer rootWindowContainer = RootWindowContainer.this;
                            boolean z4 = shouldSleep;
                            DisplayContent displayContent2 = displayContent;
                            Task task2 = (Task) obj;
                            rootWindowContainer.getClass();
                            if (z4) {
                                task2.goToSleepIfPossible(false);
                                return;
                            }
                            RootWindowContainer$$ExternalSyntheticLambda35 rootWindowContainer$$ExternalSyntheticLambda35 = new RootWindowContainer$$ExternalSyntheticLambda35(4);
                            task2.getClass();
                            task2.forAllLeafTasks(new Task$$ExternalSyntheticLambda3(rootWindowContainer$$ExternalSyntheticLambda35), true);
                            if (CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY && displayContent2.isDefaultDisplay) {
                                rootWindowContainer.mWmService.getClass();
                            }
                            if (task2.isFocusedRootTaskOnDisplay() && !rootWindowContainer.mTaskSupervisor.mKeyguardController.isKeyguardOrAodShowing(displayContent2.mDisplayId)) {
                                task2.resumeTopActivityUncheckedLocked(null, null, false);
                            }
                            task2.ensureActivitiesVisible(true, null);
                        }
                    });
                }
            }
        }
        if (z2) {
            return;
        }
        this.mHandler.removeMessages(3);
    }

    public final void applySurfaceChangesTransaction$1() {
        int i;
        DisplayContent displayContent = this.mDefaultDisplay;
        DisplayInfo displayInfo = displayContent.mDisplayInfo;
        int i2 = displayInfo.logicalWidth;
        int i3 = displayInfo.logicalHeight;
        SurfaceControl.Transaction syncTransaction = displayContent.getSyncTransaction();
        Watermark watermark = this.mWmService.mWatermark;
        if (watermark != null && (watermark.mLastDW != i2 || watermark.mLastDH != i3)) {
            watermark.mLastDW = i2;
            watermark.mLastDH = i3;
            syncTransaction.setBufferSize(watermark.mSurfaceControl, i2, i3);
            watermark.mDrawNeeded = true;
        }
        StrictModeFlash strictModeFlash = this.mWmService.mStrictModeFlash;
        if (strictModeFlash != null && (strictModeFlash.mLastDW != i2 || strictModeFlash.mLastDH != i3)) {
            strictModeFlash.mLastDW = i2;
            strictModeFlash.mLastDH = i3;
            syncTransaction.setBufferSize(strictModeFlash.mSurfaceControl, i2, i3);
            strictModeFlash.mDrawNeeded = true;
        }
        EmulatorDisplayOverlay emulatorDisplayOverlay = this.mWmService.mEmulatorDisplayOverlay;
        if (emulatorDisplayOverlay != null) {
            int i4 = displayContent.mDisplayRotation.mRotation;
            if (emulatorDisplayOverlay.mLastDW != i2 || emulatorDisplayOverlay.mLastDH != i3 || emulatorDisplayOverlay.mRotation != i4) {
                emulatorDisplayOverlay.mLastDW = i2;
                emulatorDisplayOverlay.mLastDH = i3;
                emulatorDisplayOverlay.mDrawNeeded = true;
                emulatorDisplayOverlay.mRotation = i4;
                emulatorDisplayOverlay.drawIfNeeded(syncTransaction);
            }
        }
        int size = this.mChildren.size();
        for (int i5 = 0; i5 < size; i5++) {
            DisplayContent displayContent2 = (DisplayContent) this.mChildren.get(i5);
            WindowSurfacePlacer windowSurfacePlacer = displayContent2.mWmService.mWindowPlacerLocked;
            displayContent2.mTmpHoldScreenWindow = null;
            displayContent2.mObscuringWindow = null;
            displayContent2.mTmpUpdateAllDrawn.clear();
            if ((displayContent2.pendingLayoutChanges & 4) != 0) {
                displayContent2.mWallpaperController.adjustWallpaperWindows();
            }
            if ((displayContent2.pendingLayoutChanges & 2) != 0 && displayContent2.updateOrientation(false)) {
                displayContent2.setLayoutNeeded();
                displayContent2.sendNewConfiguration();
            }
            if ((displayContent2.pendingLayoutChanges & 1) != 0) {
                displayContent2.setLayoutNeeded();
            }
            displayContent2.performLayout(false);
            displayContent2.pendingLayoutChanges = 0;
            Trace.traceBegin(32L, "applyPostLayoutPolicy");
            try {
                displayContent2.mDisplayPolicy.beginPostLayoutPolicyLw();
                displayContent2.forAllWindows((Consumer) displayContent2.mApplyPostLayoutPolicy, true);
                displayContent2.mDisplayPolicy.finishPostLayoutPolicyLw();
                Trace.traceEnd(32L);
                InsetsStateController insetsStateController = displayContent2.mInsetsStateController;
                insetsStateController.getClass();
                Trace.traceBegin(32L, "ISC.onPostLayout");
                for (int size2 = insetsStateController.mProviders.size() - 1; size2 >= 0; size2--) {
                    ((InsetsSourceProvider) insetsStateController.mProviders.valueAt(size2)).onPostLayout();
                }
                if (!insetsStateController.mLastState.equals(insetsStateController.mState)) {
                    insetsStateController.mLastState.set(insetsStateController.mState, true);
                    insetsStateController.notifyInsetsChanged();
                }
                Trace.traceEnd(32L);
                DisplayContent.ApplySurfaceChangesTransactionState applySurfaceChangesTransactionState = displayContent2.mTmpApplySurfaceChangesTransactionState;
                applySurfaceChangesTransactionState.displayHasContent = false;
                applySurfaceChangesTransactionState.obscured = false;
                applySurfaceChangesTransactionState.syswin = false;
                applySurfaceChangesTransactionState.preferMinimalPostProcessing = false;
                applySurfaceChangesTransactionState.preferredRefreshRate = FullScreenMagnificationGestureHandler.MAX_SCALE;
                applySurfaceChangesTransactionState.preferredModeId = 0;
                applySurfaceChangesTransactionState.preferredMinRefreshRate = FullScreenMagnificationGestureHandler.MAX_SCALE;
                applySurfaceChangesTransactionState.preferredMaxRefreshRate = FullScreenMagnificationGestureHandler.MAX_SCALE;
                applySurfaceChangesTransactionState.disableHdrConversion = false;
                boolean z = CoreRune.FW_VRR_HIGH_REFRESH_RATE_BLOCK_LIST;
                if (z) {
                    displayContent2.mDisplayPolicy.mRefreshRatePolicy.mRestrictHighRefreshRate = false;
                }
                Trace.traceBegin(32L, "applyWindowSurfaceChanges");
                try {
                    displayContent2.forAllWindows((Consumer) displayContent2.mApplySurfaceChangesTransaction, true);
                    Trace.traceEnd(32L);
                    if (!Flags.removePrepareSurfaceInPlacement()) {
                        displayContent2.prepareSurfaces();
                    }
                    displayContent2.mInsetsStateController.getImeSourceProvider().checkAndStartShowImePostLayout();
                    if (z) {
                        RefreshRatePolicy refreshRatePolicy = displayContent2.mDisplayPolicy.mRefreshRatePolicy;
                        if (refreshRatePolicy.mDisplayInfo.state != 1 && refreshRatePolicy.mReportedRestrictHighRefreshRate.getAndSet(refreshRatePolicy.mRestrictHighRefreshRate) != refreshRatePolicy.mRestrictHighRefreshRate) {
                            SurfaceControl.restrictHighRefreshRate(refreshRatePolicy.mReportedRestrictHighRefreshRate.get());
                        }
                    }
                    displayContent2.mLastHasContent = displayContent2.mTmpApplySurfaceChangesTransactionState.displayHasContent;
                    if (!displayContent2.inTransition() && !displayContent2.mDisplayRotation.mRotatingSeamlessly) {
                        DisplayManagerInternal displayManagerInternal = displayContent2.mWmService.mDisplayManagerInternal;
                        int i6 = displayContent2.mDisplayId;
                        boolean z2 = displayContent2.mLastHasContent;
                        DisplayContent.ApplySurfaceChangesTransactionState applySurfaceChangesTransactionState2 = displayContent2.mTmpApplySurfaceChangesTransactionState;
                        displayManagerInternal.setDisplayProperties(i6, z2, applySurfaceChangesTransactionState2.preferredRefreshRate, applySurfaceChangesTransactionState2.preferredModeId, applySurfaceChangesTransactionState2.preferredMinRefreshRate, applySurfaceChangesTransactionState2.preferredMaxRefreshRate, applySurfaceChangesTransactionState2.preferMinimalPostProcessing, applySurfaceChangesTransactionState2.disableHdrConversion, true);
                    }
                    displayContent2.updateRecording();
                    boolean isWallpaperVisible = displayContent2.mWallpaperController.isWallpaperVisible();
                    if (isWallpaperVisible != displayContent2.mLastWallpaperVisible) {
                        displayContent2.mLastWallpaperVisible = isWallpaperVisible;
                        WallpaperVisibilityListeners wallpaperVisibilityListeners = displayContent2.mWmService.mWallpaperVisibilityListeners;
                        wallpaperVisibilityListeners.getClass();
                        int i7 = displayContent2.mDisplayId;
                        boolean isWallpaperVisible2 = displayContent2.mWallpaperController.isWallpaperVisible();
                        RemoteCallbackList remoteCallbackList = (RemoteCallbackList) wallpaperVisibilityListeners.mDisplayListeners.get(i7);
                        if (remoteCallbackList != null) {
                            int beginBroadcast = remoteCallbackList.beginBroadcast();
                            while (beginBroadcast > 0) {
                                beginBroadcast--;
                                try {
                                    remoteCallbackList.getBroadcastItem(beginBroadcast).onWallpaperVisibilityChanged(isWallpaperVisible2, i7);
                                } catch (RemoteException unused) {
                                }
                            }
                            remoteCallbackList.finishBroadcast();
                        }
                        if (displayContent2.isDefaultDisplay) {
                            MultiTaskingController multiTaskingController = displayContent2.mWmService.mAtmService.mMultiTaskingController;
                            multiTaskingController.getClass();
                            if (displayContent2.getDefaultTaskDisplayArea() != null) {
                                multiTaskingController.mH.removeMessages(6);
                                boolean isWallpaperVisible3 = displayContent2.mWallpaperController.isWallpaperVisible();
                                final boolean[] zArr = {false};
                                displayContent2.getDefaultTaskDisplayArea().forAllRootTasks(new Predicate() { // from class: com.android.server.wm.MultiTaskingController$$ExternalSyntheticLambda18
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        boolean[] zArr2 = zArr;
                                        Task task = (Task) obj;
                                        if (!task.isActivityTypeHomeOrRecents()) {
                                            return false;
                                        }
                                        zArr2[0] = task.isVisibleRequested();
                                        return true;
                                    }
                                });
                                multiTaskingController.mH.obtainMessage(6, isWallpaperVisible3 ? 1 : 0, zArr[0] ? 1 : 0).sendToTarget();
                            }
                        }
                    }
                    while (!displayContent2.mTmpUpdateAllDrawn.isEmpty()) {
                        ActivityRecord activityRecord = (ActivityRecord) displayContent2.mTmpUpdateAllDrawn.removeLast();
                        if (!activityRecord.allDrawn && (i = activityRecord.mNumInterestingWindows) > 0) {
                            int size3 = activityRecord.mChildren.size() - 1;
                            while (true) {
                                if (size3 >= 0) {
                                    WindowState windowState = (WindowState) activityRecord.mChildren.get(size3);
                                    if (!windowState.mightAffectAllDrawn() || windowState.mDrawnStateEvaluated) {
                                        size3--;
                                    }
                                } else if (activityRecord.mNumDrawnWindows >= i && !activityRecord.isRelaunching()) {
                                    activityRecord.allDrawn = true;
                                    if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
                                        activityRecord.mAtmService.mChangeTransitController.removeFromSyncDeferredForAllDrawn(activityRecord, "all_drawn");
                                    }
                                    DisplayContent displayContent3 = activityRecord.mDisplayContent;
                                    if (displayContent3 != null) {
                                        displayContent3.setLayoutNeeded();
                                    }
                                    activityRecord.mWmService.mH.obtainMessage(32, activityRecord).sendToTarget();
                                }
                            }
                        }
                    }
                    WindowState windowState2 = displayContent2.mTmpHoldScreenWindow;
                    boolean z3 = windowState2 != null;
                    if (z3 && windowState2 != displayContent2.mHoldScreenWindow) {
                        PowerManager.WakeLock wakeLock = displayContent2.mHoldScreenWakeLock;
                        Session session = displayContent2.mTmpHoldScreenWindow.mSession;
                        wakeLock.setWorkSource(new WorkSource(session.mUid, session.mPackageName));
                    }
                    displayContent2.mHoldScreenWindow = displayContent2.mTmpHoldScreenWindow;
                    displayContent2.mTmpHoldScreenWindow = null;
                    if (z3 != displayContent2.mHoldScreenWakeLock.isHeld()) {
                        boolean[] zArr2 = ProtoLogImpl_54989576.Cache.WM_DEBUG_KEEP_SCREEN_ON_enabled;
                        if (z3) {
                            if (zArr2[0]) {
                                ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_KEEP_SCREEN_ON, 1959209522588955826L, 0, null, String.valueOf(displayContent2.mHoldScreenWindow));
                            }
                            displayContent2.mLastWakeLockHoldingWindow = displayContent2.mHoldScreenWindow;
                            displayContent2.mLastWakeLockObscuringWindow = null;
                            displayContent2.mHoldScreenWakeLock.acquire();
                        } else {
                            if (zArr2[0]) {
                                ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_KEEP_SCREEN_ON, 352937214222086717L, 0, null, String.valueOf(displayContent2.mObscuringWindow));
                            }
                            displayContent2.mLastWakeLockHoldingWindow = null;
                            displayContent2.mLastWakeLockObscuringWindow = displayContent2.mObscuringWindow;
                            displayContent2.mHoldScreenWakeLock.release();
                        }
                    }
                    this.mDisplayTransactions.append(displayContent2.mDisplayId, displayContent2.getSyncTransaction());
                } finally {
                }
            } finally {
            }
        }
        this.mWmService.mDisplayManagerInternal.performTraversal(syncTransaction, this.mDisplayTransactions);
        this.mDisplayTransactions.clear();
    }

    public final boolean attachApplication(WindowProcessController windowProcessController) {
        ArrayList arrayList = this.mService.mStartingProcessActivities;
        RemoteException e = null;
        boolean z = false;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            ActivityRecord activityRecord = (ActivityRecord) arrayList.get(size);
            if (windowProcessController.mUid == activityRecord.info.applicationInfo.uid && windowProcessController.mName.equals(activityRecord.processName)) {
                arrayList.remove(size);
                TaskFragment taskFragment = activityRecord.getTaskFragment();
                if (taskFragment != null && !activityRecord.finishing && activityRecord.app == null && activityRecord.shouldBeVisible(true) && activityRecord.showToCurrentUser()) {
                    try {
                        if (this.mTaskSupervisor.realStartActivityLocked(activityRecord, windowProcessController, activityRecord.isFocusable() && activityRecord == taskFragment.topRunningActivity(false), true)) {
                            z = true;
                        }
                    } catch (RemoteException e2) {
                        e = e2;
                        Slog.w("WindowManager", "Exception in new process when starting " + activityRecord, e);
                    }
                }
            }
        }
        if (e == null) {
            return z;
        }
        throw e;
    }

    public final boolean canStartHomeOnDisplayArea(ActivityInfo activityInfo, TaskDisplayArea taskDisplayArea, boolean z) {
        ActivityTaskManagerService activityTaskManagerService = this.mService;
        if (activityTaskManagerService.mFactoryTest == 1 && activityTaskManagerService.mTopAction == null) {
            return false;
        }
        WindowProcessController processController = activityTaskManagerService.getProcessController(activityInfo.applicationInfo.uid, activityInfo.processName);
        if (!z && processController != null && processController.mInstrumenting) {
            return false;
        }
        if (taskDisplayArea != null && !taskDisplayArea.canHostHomeTask()) {
            return false;
        }
        int i = taskDisplayArea != null ? taskDisplayArea.mDisplayContent.mDisplayId : -1;
        if (shouldPlacePrimaryHomeOnDisplay(i)) {
            return true;
        }
        if (!shouldPlaceSecondaryHomeOnDisplayArea(taskDisplayArea)) {
            return false;
        }
        if (i == 2 && this.mService.mDexController.getDexModeLocked() == 2) {
            return true;
        }
        int i2 = activityInfo.launchMode;
        return (i2 == 2 || i2 == 3) ? false : true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:533:0x0462, code lost:
    
        if (r6 != 4) goto L246;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:101:0x0275  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x0301  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x0324  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x0346  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x059f  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x05e1 A[LOOP:13: B:189:0x05df->B:190:0x05e1, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:194:0x05fd A[LOOP:14: B:193:0x05fb->B:194:0x05fd, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:198:0x0619 A[LOOP:15: B:197:0x0617->B:198:0x0619, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:205:0x0668  */
    /* JADX WARN: Removed duplicated region for block: B:207:0x0671  */
    /* JADX WARN: Removed duplicated region for block: B:209:0x067c  */
    /* JADX WARN: Removed duplicated region for block: B:213:0x0686  */
    /* JADX WARN: Removed duplicated region for block: B:241:0x0796  */
    /* JADX WARN: Removed duplicated region for block: B:253:0x07cd  */
    /* JADX WARN: Removed duplicated region for block: B:255:0x07d7  */
    /* JADX WARN: Removed duplicated region for block: B:266:0x084c  */
    /* JADX WARN: Removed duplicated region for block: B:283:0x08a8  */
    /* JADX WARN: Removed duplicated region for block: B:298:0x08ba  */
    /* JADX WARN: Removed duplicated region for block: B:304:0x08cc  */
    /* JADX WARN: Removed duplicated region for block: B:315:0x08f5  */
    /* JADX WARN: Removed duplicated region for block: B:409:0x0acc  */
    /* JADX WARN: Removed duplicated region for block: B:411:0x0ad1  */
    /* JADX WARN: Removed duplicated region for block: B:417:0x0ada A[ADDED_TO_REGION, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:418:0x0ace  */
    /* JADX WARN: Removed duplicated region for block: B:434:0x08c9 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:436:0x07d2  */
    /* JADX WARN: Removed duplicated region for block: B:481:0x0677  */
    /* JADX WARN: Removed duplicated region for block: B:482:0x066e  */
    /* JADX WARN: Removed duplicated region for block: B:483:0x063e  */
    /* JADX WARN: Removed duplicated region for block: B:651:0x0587  */
    /* JADX WARN: Removed duplicated region for block: B:656:0x0335 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:659:0x0312 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x01e5  */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r2v4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void checkAppTransitionReady() {
        /*
            Method dump skipped, instructions count: 2786
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.RootWindowContainer.checkAppTransitionReady():void");
    }

    public final SleepToken createSleepToken(int i, String str, boolean z) {
        DisplayContent displayContentOrCreate = this.mWmService.mExt.mExtraDisplayPolicy.isDisplayControlledByPolicy(i) ? getDisplayContentOrCreate(i) : getDisplayContent(i);
        if (displayContentOrCreate == null) {
            throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Invalid display: "));
        }
        int hashCode = (str + i).hashCode();
        SleepToken sleepToken = (SleepToken) this.mSleepTokens.get(hashCode);
        if (sleepToken != null) {
            throw new RuntimeException("Create the same sleep token twice: " + sleepToken);
        }
        SleepToken sleepToken2 = new SleepToken(i, str, z);
        this.mSleepTokens.put(hashCode, sleepToken2);
        displayContentOrCreate.mAllSleepTokens.add(sleepToken2);
        EventLog.writeEvent(1000201, Integer.valueOf(i), 1, str);
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_STATES_enabled[0]) {
            ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_STATES, -4405347314716558580L, 4, null, String.valueOf(str), Long.valueOf(i));
        }
        if (z) {
            WallpaperController wallpaperController = displayContentOrCreate.mWallpaperController;
            wallpaperController.mIsWallpaperNotifiedOnDisplaySwitch = wallpaperController.notifyDisplaySwitch(true);
        }
        return sleepToken2;
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public final void dispatchConfigurationToChild(ConfigurationContainer configurationContainer, Configuration configuration) {
        DisplayContent displayContent = (DisplayContent) configurationContainer;
        if (displayContent.isDefaultDisplay) {
            displayContent.performDisplayOverrideConfigUpdate(configuration);
        } else {
            displayContent.onConfigurationChanged(configuration);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public final void dump(PrintWriter printWriter, String str, boolean z) {
        super.dump(printWriter, str, z);
        StringBuilder m = BinaryTransparencyService$$ExternalSyntheticOutline0.m(printWriter, str, "topDisplayFocusedRootTask=");
        m.append(getTopDisplayFocusedRootTask());
        printWriter.println(m.toString());
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            ((DisplayContent) getChildAt(childCount)).dump(printWriter, str, z);
        }
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    public final void dumpDebug(ProtoOutputStream protoOutputStream, long j, int i) {
        if (i != 2 || isVisible()) {
            long start = protoOutputStream.start(j);
            super.dumpDebug(protoOutputStream, 1146756268033L, i);
            KeyguardController keyguardController = this.mTaskSupervisor.mKeyguardController;
            KeyguardController.KeyguardDisplayState displayState = keyguardController.getDisplayState(0);
            long start2 = protoOutputStream.start(1146756268037L);
            protoOutputStream.write(1133871366147L, displayState.mAodShowing);
            protoOutputStream.write(1133871366145L, displayState.mKeyguardShowing);
            protoOutputStream.write(1133871366149L, displayState.mKeyguardGoingAway);
            for (int i2 = 0; i2 < keyguardController.mDisplayStates.size(); i2++) {
                KeyguardController.KeyguardDisplayState keyguardDisplayState = (KeyguardController.KeyguardDisplayState) keyguardController.mDisplayStates.valueAt(i2);
                keyguardDisplayState.getClass();
                long start3 = protoOutputStream.start(2246267895812L);
                protoOutputStream.write(1120986464257L, keyguardDisplayState.mDisplayId);
                protoOutputStream.write(1133871366146L, keyguardDisplayState.mKeyguardShowing);
                protoOutputStream.write(1133871366147L, keyguardDisplayState.mAodShowing);
                protoOutputStream.write(1133871366148L, keyguardDisplayState.mOccluded);
                protoOutputStream.write(1133871366149L, keyguardDisplayState.mKeyguardGoingAway);
                protoOutputStream.end(start3);
            }
            protoOutputStream.end(start2);
            protoOutputStream.write(1133871366150L, this.mTaskSupervisor.mRecentTasks.isRecentsComponentHomeActivity(this.mCurrentUser));
            protoOutputStream.end(start);
        }
    }

    public final void dumpDisplayConfigs(PrintWriter printWriter) {
        printWriter.print("  ");
        printWriter.println("Display override configurations:");
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            DisplayContent displayContent = (DisplayContent) getChildAt(i);
            printWriter.print("  ");
            printWriter.print("  ");
            printWriter.print(displayContent.mDisplayId);
            printWriter.print(": ");
            printWriter.println(displayContent.getRequestedOverrideConfiguration());
        }
    }

    public final void dumpDisplayContents(PrintWriter printWriter) {
        printWriter.println("WINDOW MANAGER DISPLAY CONTENTS (dumpsys window displays)");
        if (!this.mWmService.mDisplayReady) {
            printWriter.println("  NO DISPLAY");
            return;
        }
        int size = this.mChildren.size();
        for (int i = 0; i < size; i++) {
            ((DisplayContent) this.mChildren.get(i)).dump(printWriter, "  ", true);
        }
    }

    public final void ensureActivitiesVisible() {
        ensureActivitiesVisible(true, null);
    }

    public final void ensureActivitiesVisible(boolean z, ActivityRecord activityRecord) {
        ActivityTaskSupervisor activityTaskSupervisor = this.mTaskSupervisor;
        if (activityTaskSupervisor.mVisibilityTransactionDepth <= 0 && !activityTaskSupervisor.mDeferRootVisibilityUpdate) {
            Trace.traceBegin(32L, "RWC_ensureActivitiesVisible");
            this.mTaskSupervisor.beginActivityVisibilityUpdate(null);
            try {
                for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
                    ((DisplayContent) getChildAt(childCount)).ensureActivitiesVisible(z, activityRecord);
                }
            } finally {
                this.mTaskSupervisor.endActivityVisibilityUpdate();
                Trace.traceEnd(32L);
            }
        }
    }

    public final void ensureVisibilityAndConfig(ActivityRecord activityRecord, DisplayContent displayContent, boolean z) {
        WindowProcessController windowProcessController;
        ensureActivitiesVisible(false, null);
        Configuration updateOrientation = displayContent.updateOrientation(activityRecord, true);
        if (activityRecord != null) {
            if (displayContent.mDisplayId == 1 && (windowProcessController = activityRecord.app) != null && !windowProcessController.mIsActivityConfigOverrideAllowed && activityRecord.getParent() != null) {
                activityRecord.onConfigurationChanged(activityRecord.getParent().getConfiguration());
            }
            if (activityRecord.onDescendantOrientationChanged(activityRecord)) {
                activityRecord.task.dispatchTaskInfoChangedIfNeeded(true);
            }
        }
        displayContent.updateDisplayOverrideConfigurationLocked(updateOrientation, activityRecord, z);
    }

    public final ActivityRecord findActivity(Intent intent, ActivityInfo activityInfo, boolean z) {
        ComponentName component = intent.getComponent();
        if (activityInfo.targetActivity != null) {
            component = new ComponentName(activityInfo.packageName, activityInfo.targetActivity);
        }
        int userId = UserHandle.getUserId(activityInfo.applicationInfo.uid);
        PooledPredicate obtainPredicate = PooledLambda.obtainPredicate(new RootWindowContainer$$ExternalSyntheticLambda36(), PooledLambda.__(ActivityRecord.class), Integer.valueOf(userId), Boolean.valueOf(z), intent, component);
        ActivityRecord activity = getActivity(obtainPredicate);
        obtainPredicate.recycle();
        return activity;
    }

    public final boolean finishDisabledPackageActivities(int i, String str, Set set, boolean z, boolean z2, boolean z3) {
        FinishDisabledPackageActivitiesHelper finishDisabledPackageActivitiesHelper = this.mFinishDisabledPackageActivitiesHelper;
        finishDisabledPackageActivitiesHelper.mPackageName = str;
        finishDisabledPackageActivitiesHelper.mFilterByClasses = set;
        finishDisabledPackageActivitiesHelper.mDoit = z;
        finishDisabledPackageActivitiesHelper.mEvenPersistent = z2;
        finishDisabledPackageActivitiesHelper.mUserId = i;
        finishDisabledPackageActivitiesHelper.mOnlyRemoveNoProcess = z3;
        finishDisabledPackageActivitiesHelper.mLastTask = null;
        RootWindowContainer.this.forAllActivities(finishDisabledPackageActivitiesHelper);
        int size = finishDisabledPackageActivitiesHelper.mCollectedActivities.size();
        boolean z4 = false;
        for (int i2 = 0; i2 < size; i2++) {
            ActivityRecord activityRecord = (ActivityRecord) finishDisabledPackageActivitiesHelper.mCollectedActivities.get(i2);
            if (!finishDisabledPackageActivitiesHelper.mOnlyRemoveNoProcess) {
                Slog.i("WindowManager", "  Force finishing " + activityRecord);
                activityRecord.finishIfPossible("force-stop", true);
            } else if (!activityRecord.hasProcess()) {
                Slog.i("WindowManager", "  Force removing " + activityRecord);
                activityRecord.cleanUp(false, false);
                activityRecord.removeFromHistory("force-stop");
            }
            z4 = true;
        }
        finishDisabledPackageActivitiesHelper.mCollectedActivities.clear();
        return z4;
    }

    public final void forAllDisplayPolicies(Consumer consumer) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            consumer.accept(((DisplayContent) this.mChildren.get(size)).mDisplayPolicy);
        }
    }

    public final void forAllDisplays(Consumer consumer) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            consumer.accept((DisplayContent) this.mChildren.get(size));
        }
    }

    public final ArrayList getAllRootTaskInfos(int i) {
        ArrayList arrayList = new ArrayList();
        if (i == -1) {
            forAllRootTasks(new RootWindowContainer$$ExternalSyntheticLambda2(this, arrayList, 0));
            return arrayList;
        }
        DisplayContent displayContent = getDisplayContent(i);
        if (displayContent == null) {
            return arrayList;
        }
        displayContent.forAllRootTasks(new RootWindowContainer$$ExternalSyntheticLambda2(this, arrayList, 2));
        return arrayList;
    }

    public final WindowState getCurrentInputMethodWindow() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            WindowState windowState = ((DisplayContent) this.mChildren.get(size)).mInputMethodWindow;
            if (windowState != null) {
                return windowState;
            }
        }
        return null;
    }

    public final DisplayContent getDisplayContent(int i) {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            DisplayContent displayContent = (DisplayContent) getChildAt(childCount);
            if (displayContent.mDisplayId == i) {
                return displayContent;
            }
        }
        return null;
    }

    public final DisplayContent getDisplayContent(String str) {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            DisplayContent displayContent = (DisplayContent) getChildAt(childCount);
            if (displayContent.mDisplay.isValid() && displayContent.mDisplay.getUniqueId().equals(str)) {
                return displayContent;
            }
        }
        return null;
    }

    public final DisplayContent getDisplayContentOrCreate(int i) {
        Display display;
        DisplayContent displayContent = getDisplayContent(i);
        if (displayContent != null) {
            return displayContent;
        }
        DisplayManager displayManager = this.mDisplayManager;
        if (displayManager == null || (display = displayManager.getDisplay(i)) == null) {
            return null;
        }
        DisplayContent displayContent2 = new DisplayContent(display, this, this.mDeviceStateController);
        addChild(displayContent2, Integer.MIN_VALUE);
        return displayContent2;
    }

    public final ArrayList getDumpActivities(final String str, final boolean z, boolean z2, final int i) {
        if (z2) {
            Task topDisplayFocusedRootTask = getTopDisplayFocusedRootTask();
            return topDisplayFocusedRootTask != null ? topDisplayFocusedRootTask.getDumpActivitiesLocked(i, str) : new ArrayList();
        }
        RecentTasks recentTasks = this.mWindowManager.mAtmService.mRecentTasks;
        final int i2 = recentTasks != null ? recentTasks.mRecentsUid : -1;
        final ArrayList arrayList = new ArrayList();
        forAllLeafTasks(new Predicate() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda27
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                int i3 = i2;
                boolean z3 = z;
                ArrayList arrayList2 = arrayList;
                String str2 = str;
                int i4 = i;
                Task task = (Task) obj;
                boolean z4 = task.effectiveUid == i3;
                if (!z3 || task.shouldBeVisible(null) || z4) {
                    arrayList2.addAll(task.getDumpActivitiesLocked(i4, str2));
                }
                return false;
            }
        });
        return arrayList;
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public final String getName() {
        return "ROOT";
    }

    public final Task getOrCreateRootTask(ActivityOptions activityOptions, Task task, boolean z) {
        return getOrCreateRootTask(null, activityOptions, task, null, z, null, 0);
    }

    /* JADX WARN: Removed duplicated region for block: B:160:0x021a  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x0221  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.server.wm.Task getOrCreateRootTask(com.android.server.wm.ActivityRecord r15, android.app.ActivityOptions r16, com.android.server.wm.Task r17, com.android.server.wm.Task r18, boolean r19, com.android.server.wm.LaunchParamsController.LaunchParams r20, int r21) {
        /*
            Method dump skipped, instructions count: 565
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.RootWindowContainer.getOrCreateRootTask(com.android.server.wm.ActivityRecord, android.app.ActivityOptions, com.android.server.wm.Task, com.android.server.wm.Task, boolean, com.android.server.wm.LaunchParamsController$LaunchParams, int):com.android.server.wm.Task");
    }

    public final Task getRootTask(int i) {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            DisplayContent displayContent = (DisplayContent) getChildAt(childCount);
            displayContent.getClass();
            Task rootTask = displayContent.getRootTask(new DisplayContent$$ExternalSyntheticLambda14(i, 0));
            if (rootTask != null) {
                return rootTask;
            }
        }
        return null;
    }

    public void getRunningTasks(int i, List list, int i2, int i3, ArraySet arraySet, int i4) {
        RootWindowContainer rootWindowContainer;
        if (i4 != -1) {
            DisplayContent displayContent = getDisplayContent(i4);
            if (displayContent == null) {
                return;
            } else {
                rootWindowContainer = displayContent;
            }
        } else {
            rootWindowContainer = this;
        }
        this.mTaskSupervisor.mRunningTasks.getTasks(i, list, i2, this.mService.mRecentTasks, rootWindowContainer, i3, arraySet, false);
    }

    public final Task getTopDisplayFocusedRootTask() {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            Task focusedRootTask = ((DisplayContent) getChildAt(childCount)).getFocusedRootTask();
            if (focusedRootTask != null) {
                return focusedRootTask;
            }
        }
        return null;
    }

    public final DisplayContent getTopFocusedDisplayContent() {
        DisplayContent displayContent = getDisplayContent(this.mTopFocusedDisplayId);
        return displayContent != null ? displayContent : getDisplayContent(0);
    }

    public final ActivityRecord getTopResumedActivity() {
        Task topDisplayFocusedRootTask = getTopDisplayFocusedRootTask();
        if (topDisplayFocusedRootTask == null) {
            return null;
        }
        ActivityRecord topResumedActivity = topDisplayFocusedRootTask.getTopResumedActivity();
        return (topResumedActivity == null || topResumedActivity.app == null) ? (ActivityRecord) getItemFromTaskDisplayAreas(new RootWindowContainer$$ExternalSyntheticLambda14()) : topResumedActivity;
    }

    public final WindowToken getWindowToken(IBinder iBinder) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            WindowToken windowToken = ((DisplayContent) this.mChildren.get(size)).getWindowToken(iBinder);
            if (windowToken != null) {
                return windowToken;
            }
        }
        return null;
    }

    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1, types: [boolean] */
    /* JADX WARN: Type inference failed for: r2v2 */
    public final void handleResizingWindows() {
        boolean computeDragResizing;
        ?? r2 = 1;
        int size = this.mWmService.mResizingWindows.size() - 1;
        while (size >= 0) {
            WindowState windowState = (WindowState) this.mWmService.mResizingWindows.get(size);
            if (!windowState.mAppFreezing && !windowState.getDisplayContent().mWaitingForConfig) {
                WindowFrames windowFrames = windowState.mWindowFrames;
                Rect rect = windowFrames.mRelFrame;
                int i = rect.top;
                Rect rect2 = windowFrames.mLastRelFrame;
                if (i != rect2.top || rect.left != rect2.left) {
                    windowState.updateSurfacePosition(windowState.getSyncTransaction());
                }
                ActivityRecord activityRecord = windowState.mActivityRecord;
                if ((activityRecord == null || !activityRecord.isRelaunching()) && (!windowState.shouldCheckTokenVisibleRequested() || windowState.mToken.isVisibleRequested())) {
                    if (Trace.isTagEnabled(32L)) {
                        Trace.traceBegin(32L, "wm.reportResized_" + ((Object) windowState.getWindowTag()));
                    }
                    if (ProtoLogImpl_54989576.Cache.WM_DEBUG_RESIZE_enabled[r2]) {
                        ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_RESIZE, -6920306331987525705L, 0, null, String.valueOf(windowState), String.valueOf(windowState.mWindowFrames.mCompatFrame));
                    }
                    boolean z = windowState.mWinAnimator.mDrawState == r2 ? r2 : false;
                    if (z && ProtoLogImpl_54989576.Cache.WM_DEBUG_ORIENTATION_enabled[2]) {
                        ProtoLogImpl_54989576.i(ProtoLogGroup.WM_DEBUG_ORIENTATION, 2714651498627020992L, 0, null, String.valueOf(windowState));
                    }
                    windowState.mDragResizingChangeReported = r2;
                    WindowFrames windowFrames2 = windowState.mWindowFrames;
                    windowFrames2.mLastForceReportingResized = false;
                    windowFrames2.mFrameSizeChanged = false;
                    int rotation = windowState.mLastReportedConfiguration.getMergedConfiguration().windowConfiguration.getRotation();
                    windowState.fillClientWindowFramesAndConfiguration(windowState.mLastReportedFrames, windowState.mLastReportedConfiguration, windowState.mLastReportedActivityWindowInfo, true, false);
                    windowState.fillInsetsState(windowState.mLastReportedInsetsState, false);
                    boolean shouldSendRedrawForSync = windowState.shouldSendRedrawForSync();
                    boolean z2 = (shouldSendRedrawForSync && windowState.shouldSyncWithBuffers()) ? r2 : false;
                    boolean z3 = (shouldSendRedrawForSync || z) ? r2 : false;
                    boolean z4 = windowState.mDragResizing != windowState.computeDragResizing() ? r2 : false;
                    boolean z5 = (z2 || z4) ? r2 : false;
                    DisplayContent displayContent = windowState.getDisplayContent();
                    displayContent.mDisplayPolicy.getClass();
                    int i2 = displayContent.mDisplayId;
                    if (z4 && (computeDragResizing = windowState.computeDragResizing()) != windowState.mDragResizing) {
                        windowState.mDragResizing = computeDragResizing;
                    }
                    boolean z6 = windowState.mDragResizing;
                    windowState.mRedrawForSyncReported = r2;
                    if (Flags.bundleClientTransactionFlag()) {
                        windowState.mSession.mProcess.scheduleClientTransactionItem(WindowStateResizeItem.obtain(windowState.mClient, windowState.mLastReportedFrames, z3, windowState.mLastReportedConfiguration, windowState.mLastReportedInsetsState, z5, false, i2, z2 ? windowState.mSyncSeqId : -1, z6, windowState.mLastReportedActivityWindowInfo));
                        windowState.onResizePostDispatched(rotation, i2, z);
                    } else {
                        try {
                            windowState.mClient.resized(windowState.mLastReportedFrames, z3, windowState.mLastReportedConfiguration, windowState.mLastReportedInsetsState, z5, false, i2, z2 ? windowState.mSyncSeqId : -1, z6, windowState.mLastReportedActivityWindowInfo);
                            windowState.onResizePostDispatched(rotation, i2, z);
                        } catch (RemoteException e) {
                            windowState.setOrientationChanging(false);
                            windowState.mLastFreezeDuration = (int) (SystemClock.elapsedRealtime() - windowState.mWmService.mDisplayFreezeTime);
                            Slog.w("WindowManager", "Failed to report 'resized' to " + windowState + " due to " + e);
                        }
                    }
                    Trace.traceEnd(32L);
                }
                this.mWmService.mResizingWindows.remove(size);
            }
            size--;
            r2 = 1;
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean isAttached() {
        return true;
    }

    public final boolean isLayoutNeeded() {
        int size = this.mChildren.size();
        for (int i = 0; i < size; i++) {
            if (((DisplayContent) this.mChildren.get(i)).mLayoutNeeded) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean isOnTop() {
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:101:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:102:0x024a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:104:0x010f A[Catch: all -> 0x00b0, TryCatch #0 {all -> 0x00b0, blocks: (B:23:0x0078, B:25:0x007c, B:27:0x0084, B:29:0x008a, B:31:0x0091, B:33:0x009b, B:35:0x00a1, B:37:0x00ac, B:38:0x00b3, B:40:0x00c3, B:42:0x00cc, B:44:0x00d2, B:45:0x00d9, B:48:0x00ea, B:50:0x00ee, B:51:0x00f5, B:53:0x00fe, B:54:0x0102, B:56:0x01f3, B:59:0x01fe, B:60:0x0201, B:62:0x0205, B:64:0x020b, B:65:0x021b, B:67:0x0225, B:68:0x0228, B:70:0x022c, B:72:0x0230, B:74:0x0236, B:76:0x023c, B:104:0x010f, B:106:0x013a, B:107:0x013f, B:109:0x0147, B:111:0x0152, B:112:0x0156, B:115:0x015c, B:116:0x0160, B:118:0x0173, B:120:0x018c, B:122:0x0193, B:124:0x0199, B:125:0x019b, B:127:0x01a1, B:130:0x01ad, B:132:0x01b3, B:133:0x01b7, B:135:0x01d0, B:137:0x01d8, B:139:0x01e5, B:140:0x01a6, B:141:0x01aa, B:142:0x0187, B:144:0x014c, B:145:0x013d), top: B:22:0x0078 }] */
    /* JADX WARN: Removed duplicated region for block: B:163:0x02be A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00c3 A[Catch: all -> 0x00b0, TryCatch #0 {all -> 0x00b0, blocks: (B:23:0x0078, B:25:0x007c, B:27:0x0084, B:29:0x008a, B:31:0x0091, B:33:0x009b, B:35:0x00a1, B:37:0x00ac, B:38:0x00b3, B:40:0x00c3, B:42:0x00cc, B:44:0x00d2, B:45:0x00d9, B:48:0x00ea, B:50:0x00ee, B:51:0x00f5, B:53:0x00fe, B:54:0x0102, B:56:0x01f3, B:59:0x01fe, B:60:0x0201, B:62:0x0205, B:64:0x020b, B:65:0x021b, B:67:0x0225, B:68:0x0228, B:70:0x022c, B:72:0x0230, B:74:0x0236, B:76:0x023c, B:104:0x010f, B:106:0x013a, B:107:0x013f, B:109:0x0147, B:111:0x0152, B:112:0x0156, B:115:0x015c, B:116:0x0160, B:118:0x0173, B:120:0x018c, B:122:0x0193, B:124:0x0199, B:125:0x019b, B:127:0x01a1, B:130:0x01ad, B:132:0x01b3, B:133:0x01b7, B:135:0x01d0, B:137:0x01d8, B:139:0x01e5, B:140:0x01a6, B:141:0x01aa, B:142:0x0187, B:144:0x014c, B:145:0x013d), top: B:22:0x0078 }] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00e8  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0225 A[Catch: all -> 0x00b0, TryCatch #0 {all -> 0x00b0, blocks: (B:23:0x0078, B:25:0x007c, B:27:0x0084, B:29:0x008a, B:31:0x0091, B:33:0x009b, B:35:0x00a1, B:37:0x00ac, B:38:0x00b3, B:40:0x00c3, B:42:0x00cc, B:44:0x00d2, B:45:0x00d9, B:48:0x00ea, B:50:0x00ee, B:51:0x00f5, B:53:0x00fe, B:54:0x0102, B:56:0x01f3, B:59:0x01fe, B:60:0x0201, B:62:0x0205, B:64:0x020b, B:65:0x021b, B:67:0x0225, B:68:0x0228, B:70:0x022c, B:72:0x0230, B:74:0x0236, B:76:0x023c, B:104:0x010f, B:106:0x013a, B:107:0x013f, B:109:0x0147, B:111:0x0152, B:112:0x0156, B:115:0x015c, B:116:0x0160, B:118:0x0173, B:120:0x018c, B:122:0x0193, B:124:0x0199, B:125:0x019b, B:127:0x01a1, B:130:0x01ad, B:132:0x01b3, B:133:0x01b7, B:135:0x01d0, B:137:0x01d8, B:139:0x01e5, B:140:0x01a6, B:141:0x01aa, B:142:0x0187, B:144:0x014c, B:145:0x013d), top: B:22:0x0078 }] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0276  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x028c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void moveActivityToPinnedRootTask(com.android.server.wm.ActivityRecord r18, com.android.server.wm.ActivityRecord r19, java.lang.String r20, com.android.server.wm.Transition r21, android.graphics.Rect r22) {
        /*
            Method dump skipped, instructions count: 746
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.RootWindowContainer.moveActivityToPinnedRootTask(com.android.server.wm.ActivityRecord, com.android.server.wm.ActivityRecord, java.lang.String, com.android.server.wm.Transition, android.graphics.Rect):void");
    }

    public final void moveRootTaskToDisplay(int i, int i2) {
        DisplayContent displayContentOrCreate = getDisplayContentOrCreate(i2);
        if (displayContentOrCreate == null) {
            throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i2, "moveRootTaskToDisplay: Unknown displayId="));
        }
        TaskDisplayArea defaultTaskDisplayArea = displayContentOrCreate.getDefaultTaskDisplayArea();
        Task rootTask = getRootTask(i);
        if (rootTask == null) {
            throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "moveRootTaskToTaskDisplayArea: Unknown rootTaskId="));
        }
        TaskDisplayArea displayArea = rootTask.getDisplayArea();
        if (displayArea == null) {
            throw new IllegalStateException("moveRootTaskToTaskDisplayArea: rootTask=" + rootTask + " is not attached to any task display area.");
        }
        if (defaultTaskDisplayArea == null) {
            throw new IllegalArgumentException("moveRootTaskToTaskDisplayArea: Unknown taskDisplayArea=" + defaultTaskDisplayArea);
        }
        if (displayArea != defaultTaskDisplayArea) {
            rootTask.reparent(defaultTaskDisplayArea, true);
            rootTask.resumeNextFocusAfterReparent();
        } else {
            throw new IllegalArgumentException("Trying to move rootTask=" + rootTask + " to its current taskDisplayArea=" + defaultTaskDisplayArea);
        }
    }

    public final void notifyActivityPipModeChanged(Task task, ActivityRecord activityRecord) {
        boolean z = activityRecord != null;
        if (z) {
            TaskChangeNotificationController taskChangeNotificationController = this.mService.mTaskChangeNotificationController;
            TaskChangeNotificationController.MainHandler mainHandler = taskChangeNotificationController.mHandler;
            mainHandler.removeMessages(3);
            Task task2 = activityRecord.task;
            Message obtainMessage = mainHandler.obtainMessage(3, task2.mTaskId, task2 != null ? task2.getRootTask().mTaskId : -1, activityRecord.packageName);
            obtainMessage.sendingUid = activityRecord.mUserId;
            taskChangeNotificationController.forAllLocalListeners(taskChangeNotificationController.mNotifyActivityPinned, obtainMessage);
            obtainMessage.sendToTarget();
        } else {
            TaskChangeNotificationController taskChangeNotificationController2 = this.mService.mTaskChangeNotificationController;
            TaskChangeNotificationController.MainHandler mainHandler2 = taskChangeNotificationController2.mHandler;
            mainHandler2.removeMessages(17);
            Message obtainMessage2 = mainHandler2.obtainMessage(17);
            taskChangeNotificationController2.forAllLocalListeners(taskChangeNotificationController2.mNotifyActivityUnpinned, obtainMessage2);
            obtainMessage2.sendToTarget();
        }
        ((PhoneWindowManager) this.mWindowManager.mPolicy).mPictureInPictureVisible = z;
        if (task.mSurfaceControl != null) {
            ((SurfaceControl.Transaction) this.mWmService.mTransactionFactory.get()).setTrustedOverlay(task.mSurfaceControl, z).apply();
            if (z) {
                return;
            }
            task.forAllActivities(new RootWindowContainer$$ExternalSyntheticLambda35(3));
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public final void onChildPositionChanged(WindowContainer windowContainer) {
        this.mWmService.updateFocusedWindowLocked(0, !r3.mPerDisplayFocusEnabled);
        this.mTaskSupervisor.updateTopResumedActivityIfNeeded("onChildPositionChanged");
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public final void onDisplayAdded(int i) {
        if (i != 0) {
            ProxyManager$$ExternalSyntheticOutline0.m(i, "Display added displayId=", "WindowManager");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContentOrCreate = getDisplayContentOrCreate(i);
                if (displayContentOrCreate == null) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                if (this.mService.mAmInternal.isBooted() || this.mService.mAmInternal.isBooting()) {
                    if (i != 0) {
                        Slog.d("WindowManager", "onDisplayAdded, displayId=" + i + " display=" + displayContentOrCreate.mDisplay);
                    }
                    if (i == 2) {
                        this.mService.mDexController.activateDexDisplayLocked(displayContentOrCreate);
                    }
                    if (!this.mWmService.mExt.mExtraDisplayPolicy.hasCoverHome(displayContentOrCreate.mDisplayId) || this.mCurrentUser == 0) {
                        startHomeOnDisplay("displayAdded", this.mCurrentUser, displayContentOrCreate.mDisplayId, false, false);
                    } else {
                        startHomeOnDisplay("displayAdded", 0, displayContentOrCreate.mDisplayId, false, false);
                    }
                    displayContentOrCreate.mDisplayPolicy.notifyDisplayReady();
                }
                this.mWmService.mPossibleDisplayInfoMapper.mDisplayInfos.remove(i);
                WindowManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public final void onDisplayChanged(final int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = getDisplayContent(i);
                if (displayContent != null) {
                    displayContent.requestDisplayUpdate(new Runnable() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda22
                        @Override // java.lang.Runnable
                        public final void run() {
                            RootWindowContainer rootWindowContainer = RootWindowContainer.this;
                            rootWindowContainer.mWmService.mPossibleDisplayInfoMapper.mDisplayInfos.remove(i);
                            rootWindowContainer.updateDisplayImePolicyCache();
                        }
                    });
                } else {
                    this.mWmService.mPossibleDisplayInfoMapper.mDisplayInfos.remove(i);
                    updateDisplayImePolicyCache();
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void onDisplayManagerReceivedDeviceState(int i) {
        DeviceStateController deviceStateController = this.mDeviceStateController;
        deviceStateController.mCurrentState = i;
        final DeviceStateController.DeviceState deviceState = ArrayUtils.contains(deviceStateController.mHalfFoldedDeviceStates, i) ? DeviceStateController.DeviceState.HALF_FOLDED : ArrayUtils.contains(deviceStateController.mFoldedDeviceStates, i) ? DeviceStateController.DeviceState.FOLDED : ArrayUtils.contains(deviceStateController.mRearDisplayDeviceStates, i) ? DeviceStateController.DeviceState.REAR : ArrayUtils.contains(deviceStateController.mOpenDeviceStates, i) ? DeviceStateController.DeviceState.OPEN : i == deviceStateController.mConcurrentDisplayDeviceState ? DeviceStateController.DeviceState.CONCURRENT : DeviceStateController.DeviceState.UNKNOWN;
        DeviceStateController.DeviceState deviceState2 = deviceStateController.mCurrentDeviceState;
        if (deviceState2 == null || !deviceState2.equals(deviceState)) {
            deviceStateController.mCurrentDeviceState = deviceState;
            List copyDeviceStateCallbacks = deviceStateController.copyDeviceStateCallbacks();
            for (int i2 = 0; i2 < copyDeviceStateCallbacks.size(); i2++) {
                final Pair pair = (Pair) copyDeviceStateCallbacks.get(i2);
                ((Executor) pair.second).execute(new Runnable() { // from class: com.android.server.wm.DeviceStateController$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        Pair pair2 = pair;
                        ((Consumer) pair2.first).accept(deviceState);
                    }
                });
            }
        }
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public final void onDisplayRemoved(int i) {
        if (i != 0) {
            ProxyManager$$ExternalSyntheticOutline0.m(i, "Display removed displayId=", "WindowManager");
        }
        if (i == 0) {
            throw new IllegalArgumentException("Can't remove the primary display.");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = getDisplayContent(i);
                if (displayContent == null) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                Slog.d("WindowManager", "onDisplayRemoved, displayId=" + i);
                displayContent.remove();
                this.mWmService.mPossibleDisplayInfoMapper.mDisplayInfos.remove(i);
                WindowManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void onSettingsRetrieved() {
        int size = this.mChildren.size();
        for (int i = 0; i < size; i++) {
            DisplayContent displayContent = (DisplayContent) this.mChildren.get(i);
            DisplayWindowSettings displayWindowSettings = this.mWmService.mDisplayWindowSettings;
            displayWindowSettings.getClass();
            TaskDisplayArea defaultTaskDisplayArea = displayContent.getDefaultTaskDisplayArea();
            if (defaultTaskDisplayArea != null) {
                int windowingMode = defaultTaskDisplayArea.getWindowingMode();
                DisplayInfo displayInfo = displayContent.mDisplayInfo;
                DisplayWindowSettingsProvider displayWindowSettingsProvider = displayWindowSettings.mSettingsProvider;
                if (windowingMode != displayWindowSettings.getWindowingModeLocked(displayWindowSettingsProvider.getSettings(displayInfo), displayContent)) {
                    defaultTaskDisplayArea.setWindowingMode(displayWindowSettings.getWindowingModeLocked(displayWindowSettingsProvider.getSettings(displayContent.mDisplayInfo), displayContent));
                    displayContent.reconfigureDisplayLocked();
                    if (displayContent.isDefaultDisplay) {
                        this.mWmService.mAtmService.updateConfigurationLocked(this.mWmService.computeNewConfiguration(displayContent.mDisplayId), false, false, -10000);
                    }
                }
            }
        }
    }

    public final void performSurfacePlacement() {
        Trace.traceBegin(32L, "performSurfacePlacement");
        try {
            performSurfacePlacementNoTrace();
        } finally {
            Trace.traceEnd(32L);
        }
    }

    public final void performSurfacePlacementNoTrace() {
        DisplayContent displayContent;
        int i;
        WindowManagerService windowManagerService = this.mWmService;
        int i2 = 0;
        if (windowManagerService.mFocusMayChange) {
            windowManagerService.mFocusMayChange = false;
            windowManagerService.updateFocusedWindowLocked(3, false);
        }
        this.mScreenBrightnessOverride = Float.NaN;
        this.mScreenBrightnessOverridePackage = "";
        this.mUserActivityTimeout = -1L;
        this.mScreenDimDuration = -1L;
        this.mObscureApplicationContentOnSecondaryDisplays = false;
        this.mSustainedPerformanceModeCurrent = false;
        WindowManagerService windowManagerService2 = this.mWmService;
        windowManagerService2.mTransactionSequence++;
        this.mDeXUserActivityTimeout = -1L;
        boolean z = CoreRune.FW_LARGE_FLIP_PREDICTIVE_BACK_ANIM;
        DisplayContent defaultDisplayContentLocked = windowManagerService2.getDefaultDisplayContentLocked();
        WindowSurfacePlacer windowSurfacePlacer = this.mWmService.mWindowPlacerLocked;
        Trace.traceBegin(32L, "applySurfaceChanges");
        try {
            try {
                applySurfaceChangesTransaction$1();
            } catch (RuntimeException e) {
                Slog.wtf("WindowManager", "Unhandled exception in Window Manager", e);
            }
            if (Flags.bundleClientTransactionFlag()) {
                handleResizingWindows();
                this.mWmService.mAtmService.mLifecycleManager.dispatchPendingTransactions();
            }
            this.mWmService.mAtmService.mTaskOrganizerController.dispatchPendingEvents();
            TaskFragmentOrganizerController taskFragmentOrganizerController = this.mWmService.mAtmService.mTaskFragmentOrganizerController;
            int i3 = 2;
            if (!(taskFragmentOrganizerController.mAtmService.mWindowManager.mWindowPlacerLocked.mDeferDepth > 0) && !taskFragmentOrganizerController.mPendingTaskFragmentEvents.isEmpty()) {
                int size = taskFragmentOrganizerController.mPendingTaskFragmentEvents.size();
                int i4 = 0;
                while (i4 < size) {
                    TaskFragmentOrganizerController.TaskFragmentOrganizerState taskFragmentOrganizerState = (TaskFragmentOrganizerController.TaskFragmentOrganizerState) taskFragmentOrganizerController.mTaskFragmentOrganizerState.get(taskFragmentOrganizerController.mPendingTaskFragmentEvents.keyAt(i4));
                    List list = (List) taskFragmentOrganizerController.mPendingTaskFragmentEvents.valueAt(i4);
                    if (!list.isEmpty()) {
                        ArrayList arrayList = new ArrayList();
                        ArrayList arrayList2 = new ArrayList();
                        int size2 = list.size();
                        int i5 = i2;
                        while (i5 < size2) {
                            TaskFragmentOrganizerController.PendingTaskFragmentEvent pendingTaskFragmentEvent = (TaskFragmentOrganizerController.PendingTaskFragmentEvent) list.get(i5);
                            int i6 = pendingTaskFragmentEvent.mEventType;
                            if (i6 == 3 || i6 == i3 || i6 == 0) {
                                TaskFragment taskFragment = pendingTaskFragmentEvent.mTaskFragment;
                                Task task = i6 == 3 ? pendingTaskFragmentEvent.mTask : taskFragment.getTask();
                                displayContent = defaultDisplayContentLocked;
                                i = size;
                                Task task2 = task;
                                if (task.lastActiveTime > pendingTaskFragmentEvent.mDeferTime) {
                                    if (!arrayList.contains(task2)) {
                                        if (!arrayList2.contains(task2)) {
                                            if (task2.shouldBeVisible(null)) {
                                                arrayList.add(task2);
                                            } else {
                                                arrayList2.add(task2);
                                            }
                                        }
                                    }
                                }
                                TaskFragmentParentInfo taskFragmentParentInfo = (TaskFragmentParentInfo) taskFragmentOrganizerState.mLastSentTaskFragmentParentInfos.get(task2.mTaskId);
                                if (taskFragmentParentInfo != null && !taskFragmentParentInfo.isVisible()) {
                                    if (pendingTaskFragmentEvent.mEventType == 2) {
                                        TaskFragmentInfo taskFragmentInfo = (TaskFragmentInfo) ((WeakHashMap) taskFragmentOrganizerState.mLastSentTaskFragmentInfos).get(taskFragment);
                                        boolean z2 = taskFragment.getNonFinishingActivityCount() == 0;
                                        if (taskFragmentInfo != null && taskFragmentInfo.isEmpty() == z2) {
                                        }
                                    }
                                    pendingTaskFragmentEvent.mDeferTime = task2.lastActiveTime;
                                    i5++;
                                    defaultDisplayContentLocked = displayContent;
                                    size = i;
                                    i3 = 2;
                                }
                            } else {
                                displayContent = defaultDisplayContentLocked;
                                i = size;
                            }
                            taskFragmentOrganizerController.mTmpTaskSet.clear();
                            int size3 = list.size();
                            TaskFragmentTransaction taskFragmentTransaction = new TaskFragmentTransaction();
                            for (int i7 = 0; i7 < size3; i7++) {
                                TaskFragmentOrganizerController.PendingTaskFragmentEvent pendingTaskFragmentEvent2 = (TaskFragmentOrganizerController.PendingTaskFragmentEvent) list.get(i7);
                                int i8 = pendingTaskFragmentEvent2.mEventType;
                                if (i8 == 0 || i8 == 2) {
                                    Task task3 = pendingTaskFragmentEvent2.mTaskFragment.getTask();
                                    if (taskFragmentOrganizerController.mTmpTaskSet.add(task3)) {
                                        ITaskFragmentOrganizer iTaskFragmentOrganizer = taskFragmentOrganizerState.mOrganizer;
                                        Objects.requireNonNull(iTaskFragmentOrganizer);
                                        Objects.requireNonNull(task3);
                                        taskFragmentTransaction.addChange(taskFragmentOrganizerController.prepareChange(new TaskFragmentOrganizerController.PendingTaskFragmentEvent(3, iTaskFragmentOrganizer, null, null, null, null, null, null, task3, 0)));
                                    }
                                }
                                taskFragmentTransaction.addChange(taskFragmentOrganizerController.prepareChange(pendingTaskFragmentEvent2));
                            }
                            taskFragmentOrganizerController.mTmpTaskSet.clear();
                            taskFragmentOrganizerState.dispatchTransaction(taskFragmentTransaction);
                            list.clear();
                            i4++;
                            defaultDisplayContentLocked = displayContent;
                            size = i;
                            i2 = 0;
                            i3 = 2;
                        }
                    }
                    displayContent = defaultDisplayContentLocked;
                    i = size;
                    i4++;
                    defaultDisplayContentLocked = displayContent;
                    size = i;
                    i2 = 0;
                    i3 = 2;
                }
            }
            DisplayContent displayContent2 = defaultDisplayContentLocked;
            this.mWmService.mSyncEngine.onSurfacePlacement();
            checkAppTransitionReady();
            RecentsAnimationController recentsAnimationController = this.mWmService.mRecentsAnimationController;
            if (recentsAnimationController != null) {
                WallpaperController wallpaperController = displayContent2.mWallpaperController;
                if (recentsAnimationController.mPendingStart) {
                    ActivityRecord activityRecord = recentsAnimationController.mTargetActivityRecord;
                    if (!(activityRecord == null ? false : activityRecord.windowsCanBeWallpaperTarget()) || (wallpaperController.mWallpaperTarget != null && wallpaperController.wallpaperTransitionReady())) {
                        recentsAnimationController.mService.mRecentsAnimationController.startAnimation();
                    }
                }
            }
            final BackNavigationController backNavigationController = this.mWmService.mAtmService.mBackNavigationController;
            WallpaperController wallpaperController2 = displayContent2.mWallpaperController;
            if (backNavigationController.mBackAnimationInProgress && ((!backNavigationController.mShowWallpaper || (wallpaperController2.mWallpaperTarget != null && wallpaperController2.wallpaperTransitionReady())) && backNavigationController.mPendingAnimation != null)) {
                backNavigationController.mWindowManagerService.mAnimator.addAfterPrepareSurfacesRunnable(new Runnable() { // from class: com.android.server.wm.BackNavigationController$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        BackNavigationController backNavigationController2 = BackNavigationController.this;
                        if (!backNavigationController2.mBackAnimationInProgress) {
                            if (backNavigationController2.mPendingAnimation != null) {
                                backNavigationController2.clearBackAnimations(true);
                                backNavigationController2.mPendingAnimation = null;
                                return;
                            }
                            return;
                        }
                        BackNavigationController$AnimationHandler$ScheduleAnimationBuilder$$ExternalSyntheticLambda0 backNavigationController$AnimationHandler$ScheduleAnimationBuilder$$ExternalSyntheticLambda0 = backNavigationController2.mPendingAnimation;
                        if (backNavigationController$AnimationHandler$ScheduleAnimationBuilder$$ExternalSyntheticLambda0 != null) {
                            backNavigationController$AnimationHandler$ScheduleAnimationBuilder$$ExternalSyntheticLambda0.run();
                            backNavigationController2.mPendingAnimation = null;
                        }
                    }
                });
            }
            for (int i9 = 0; i9 < this.mChildren.size(); i9++) {
                DisplayContent displayContent3 = (DisplayContent) this.mChildren.get(i9);
                if (displayContent3.mWallpaperMayChange) {
                    if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WALLPAPER_enabled[1]) {
                        ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_WALLPAPER, -4150611780753674023L, 0, null, null);
                    }
                    displayContent3.pendingLayoutChanges |= 4;
                }
            }
            WindowManagerService windowManagerService3 = this.mWmService;
            if (windowManagerService3.mFocusMayChange) {
                windowManagerService3.mFocusMayChange = false;
                windowManagerService3.updateFocusedWindowLocked(2, false);
            }
            if (isLayoutNeeded()) {
                displayContent2.pendingLayoutChanges |= 1;
            }
            if (!Flags.bundleClientTransactionFlag()) {
                handleResizingWindows();
            }
            ArrayList arrayList3 = this.mWmService.mFrameChangingWindows;
            for (int size4 = arrayList3.size() - 1; size4 >= 0; size4--) {
                WindowState windowState = (WindowState) arrayList3.get(size4);
                WindowFrames windowFrames = windowState.mWindowFrames;
                windowFrames.mLastFrame.set(windowFrames.mFrame);
                WindowFrames windowFrames2 = windowState.mWindowFrames;
                windowFrames2.mLastRelFrame.set(windowFrames2.mRelFrame);
            }
            arrayList3.clear();
            boolean z3 = this.mWmService.mDisplayFrozen;
            boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_ORIENTATION_enabled;
            if (z3 && zArr[1]) {
                ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_ORIENTATION, 4177291132772627699L, 3, null, Boolean.valueOf(this.mOrientationChangeComplete));
            }
            if (this.mOrientationChangeComplete) {
                WindowManagerService windowManagerService4 = this.mWmService;
                if (windowManagerService4.mWindowsFreezingScreen != 0) {
                    windowManagerService4.mWindowsFreezingScreen = 0;
                    windowManagerService4.mLastFinishedFreezeSource = this.mLastWindowFreezeSource;
                    windowManagerService4.mH.removeMessages(11);
                }
                this.mWmService.stopFreezingDisplayLocked();
            }
            int size5 = this.mWmService.mDestroySurface.size();
            if (size5 > 0) {
                do {
                    size5--;
                    WindowState windowState2 = (WindowState) this.mWmService.mDestroySurface.get(size5);
                    windowState2.mDestroying = false;
                    DisplayContent displayContent4 = windowState2.getDisplayContent();
                    if (displayContent4.mInputMethodWindow == windowState2) {
                        displayContent4.setInputMethodWindowLocked(null);
                    }
                    if (displayContent4.mWallpaperController.isWallpaperTarget(windowState2)) {
                        displayContent4.pendingLayoutChanges |= 4;
                    }
                    windowState2.destroySurfaceUnchecked();
                } while (size5 > 0);
                this.mWmService.mDestroySurface.clear();
            }
            for (int i10 = 0; i10 < this.mChildren.size(); i10++) {
                DisplayContent displayContent5 = (DisplayContent) this.mChildren.get(i10);
                if (displayContent5.pendingLayoutChanges != 0) {
                    displayContent5.setLayoutNeeded();
                }
            }
            if (!this.mWmService.mDisplayFrozen) {
                float f = this.mScreenBrightnessOverride;
                if (f < FullScreenMagnificationGestureHandler.MAX_SCALE || f > 1.0f) {
                    f = Float.NaN;
                }
                this.mHandler.obtainMessage(1, Float.floatToIntBits(f), 0, this.mScreenBrightnessOverridePackage).sendToTarget();
                this.mHandler.obtainMessage(2, Long.valueOf(this.mUserActivityTimeout)).sendToTarget();
                this.mHandler.obtainMessage(10, Long.valueOf(this.mDeXUserActivityTimeout)).sendToTarget();
                this.mHandler.obtainMessage(11, Long.valueOf(this.mScreenDimDuration)).sendToTarget();
            }
            boolean z4 = this.mSustainedPerformanceModeCurrent;
            if (z4 != this.mSustainedPerformanceModeEnabled) {
                this.mSustainedPerformanceModeEnabled = z4;
                this.mWmService.mPowerManagerInternal.setPowerMode(2, z4);
            }
            if (this.mUpdateRotation) {
                if (zArr[0]) {
                    ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_ORIENTATION, -5513616928833586179L, 0, null, null);
                }
                boolean z5 = false;
                for (int size6 = this.mChildren.size() - 1; size6 >= 0; size6--) {
                    if (((DisplayContent) this.mChildren.get(size6)).mDisplayRotation.updateRotationAndSendNewConfigIfChanged()) {
                        z5 = true;
                    }
                }
                this.mUpdateRotation = z5;
            }
            if (!this.mWmService.mWaitingForDrawnCallbacks.isEmpty() || (this.mOrientationChangeComplete && !isLayoutNeeded() && !this.mUpdateRotation)) {
                this.mWmService.checkDrawnWindowsLocked();
            }
            forAllDisplays(new RootWindowContainer$$ExternalSyntheticLambda35(1));
            this.mWmService.enableScreenIfNeededLocked();
            this.mWmService.scheduleAnimationLocked();
        } finally {
            Trace.traceEnd(32L);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public final void positionChildAt(int i, DisplayContent displayContent, boolean z) {
        int otherDisplayId;
        DisplayContent displayContent2;
        int i2 = displayContent.mDisplayId;
        if (i == Integer.MAX_VALUE) {
            if (this.mWmService.mExt.mExtraDisplayPolicy.shouldNotTopDisplay(i2)) {
                Slog.i("WindowManager", "positionChildAt: can't gain focus display=" + displayContent);
                return;
            }
        } else if (i == Integer.MIN_VALUE && (otherDisplayId = this.mWmService.mExt.mExtraDisplayPolicy.getOtherDisplayId(i2)) != -1 && (displayContent2 = this.mWmService.mRoot.getDisplayContent(otherDisplayId)) != null) {
            super.positionChildAt(Integer.MIN_VALUE, (WindowContainer) displayContent2, z);
            i = 1;
        }
        super.positionChildAt(i, (WindowContainer) displayContent, z);
    }

    public final void rankTaskLayers() {
        if (this.mTaskLayersChanged) {
            this.mTaskLayersChanged = false;
            this.mService.mH.removeCallbacks(this.mRankTaskLayersRunnable);
        }
        this.mTmpTaskLayerRank = 0;
        forAllLeafTasks(new RootWindowContainer$$ExternalSyntheticLambda7(this, 0), true);
        ActivityTaskSupervisor activityTaskSupervisor = this.mTaskSupervisor;
        if (activityTaskSupervisor.mVisibilityTransactionDepth > 0) {
            return;
        }
        activityTaskSupervisor.computeProcessActivityStateBatch();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean reclaimSomeSurfaceMemory(WindowStateAnimator windowStateAnimator, String str, boolean z) {
        boolean z2;
        WindowSurfaceController windowSurfaceController = windowStateAnimator.mSurfaceController;
        WindowState windowState = windowStateAnimator.mWin;
        EventLog.writeEvent(31000, windowState.toString(), Integer.valueOf(windowStateAnimator.mSession.mPid), str);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Slog.i("WindowManager", "Out of memory for surface!  Looking for leaks...");
            int size = this.mChildren.size();
            boolean z3 = 0;
            boolean z4 = false;
            for (int i = 0; i < size; i++) {
                DisplayContent displayContent = (DisplayContent) this.mChildren.get(i);
                displayContent.mTmpWindow = null;
                SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) displayContent.mWmService.mTransactionFactory.get();
                displayContent.forAllWindows((Consumer) new DisplayContent$$ExternalSyntheticLambda11(1, displayContent, transaction), false);
                transaction.apply();
                z4 |= displayContent.mTmpWindow != null;
            }
            if (z4) {
                z2 = false;
            } else {
                Slog.w("WindowManager", "No leaked surfaces; killing applications!");
                SparseIntArray sparseIntArray = new SparseIntArray();
                int i2 = 0;
                z2 = false;
                while (i2 < size) {
                    ((DisplayContent) this.mChildren.get(i2)).forAllWindows(new RootWindowContainer$$ExternalSyntheticLambda20(0, this, sparseIntArray), z3);
                    if (sparseIntArray.size() > 0) {
                        int size2 = sparseIntArray.size();
                        int[] iArr = new int[size2];
                        for (int i3 = z3; i3 < size2; i3++) {
                            iArr[i3] = sparseIntArray.keyAt(i3);
                        }
                        try {
                            try {
                                if (this.mWmService.mActivityManager.killPids(iArr, "Free memory", z)) {
                                    z2 = true;
                                }
                            } catch (RemoteException unused) {
                            }
                        } catch (RemoteException unused2) {
                        }
                        i2++;
                        z3 = 0;
                    }
                    i2++;
                    z3 = 0;
                }
            }
            if (z4 || z2) {
                Slog.w("WindowManager", "Looks like we have reclaimed some memory, clearing surface for retry.");
                if (windowSurfaceController != null) {
                    if (ProtoLogImpl_54989576.Cache.WM_SHOW_SURFACE_ALLOC_enabled[2]) {
                        ProtoLogImpl_54989576.i(ProtoLogGroup.WM_SHOW_SURFACE_ALLOC, 865845626039449679L, 0, null, String.valueOf(windowState));
                    }
                    SurfaceControl.Transaction transaction2 = (SurfaceControl.Transaction) this.mWmService.mTransactionFactory.get();
                    windowStateAnimator.destroySurface(transaction2);
                    transaction2.apply();
                    ActivityRecord activityRecord = windowState.mActivityRecord;
                    if (activityRecord != null) {
                        activityRecord.removeStartingWindow();
                    }
                }
                try {
                    windowState.mClient.dispatchGetNewSurface();
                } catch (RemoteException unused3) {
                }
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return z4 || z2;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void refreshSecureSurfaceState() {
        Slog.d("WindowManager", "refreshSecureSurfaceState, callers=" + Debug.getCallers(5));
        forAllWindows((Consumer) new RootWindowContainer$$ExternalSyntheticLambda35(2), true);
    }

    @Override // com.android.server.wm.WindowContainer
    public final void removeChild(WindowContainer windowContainer) {
        DisplayContent displayContent = (DisplayContent) windowContainer;
        super.removeChild(displayContent);
        if (this.mTopFocusedDisplayId == displayContent.mDisplayId) {
            this.mWmService.updateFocusedWindowLocked(0, true);
        }
    }

    public final void removeRootTasksInWindowingModes(int... iArr) {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            DisplayContent displayContent = (DisplayContent) getChildAt(childCount);
            displayContent.getClass();
            if (iArr != null && iArr.length != 0) {
                ArrayList arrayList = new ArrayList();
                displayContent.forAllRootTasks(new DisplayContent$$ExternalSyntheticLambda12(iArr, arrayList, 0));
                for (int size = arrayList.size() - 1; size >= 0; size--) {
                    displayContent.mRootWindowContainer.mTaskSupervisor.removeRootTask((Task) arrayList.get(size));
                }
            }
        }
    }

    public final void removeRootTasksWithActivityTypes(int... iArr) {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            DisplayContent displayContent = (DisplayContent) getChildAt(childCount);
            displayContent.getClass();
            if (iArr != null && iArr.length != 0) {
                ArrayList arrayList = new ArrayList();
                displayContent.forAllRootTasks(new DisplayContent$$ExternalSyntheticLambda12(iArr, arrayList, 1));
                for (int size = arrayList.size() - 1; size >= 0; size--) {
                    displayContent.mRootWindowContainer.mTaskSupervisor.removeRootTask((Task) arrayList.get(size));
                }
            }
        }
    }

    public final void removeSleepToken(SleepToken sleepToken) {
        SparseArray sparseArray = this.mSleepTokens;
        int i = sleepToken.mHashKey;
        if (!sparseArray.contains(i)) {
            StringBuilder sb = new StringBuilder("Remove non-exist sleep token: ");
            sb.append(sleepToken);
            sb.append(" from ");
            ActivityManagerService$$ExternalSyntheticOutline0.m(6, sb, "WindowManager");
        }
        this.mSleepTokens.remove(i);
        int i2 = sleepToken.mDisplayId;
        Integer valueOf = Integer.valueOf(i2);
        String str = sleepToken.mTag;
        EventLog.writeEvent(1000201, valueOf, 0, str);
        DisplayContent displayContent = getDisplayContent(i2);
        if (displayContent == null) {
            StringBuilder sb2 = new StringBuilder("Remove sleep token for non-existing display: ");
            sb2.append(sleepToken);
            sb2.append(" from ");
            ActivityManagerService$$ExternalSyntheticOutline0.m(6, sb2, "WindowManager");
            return;
        }
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_STATES_enabled[0]) {
            ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_STATES, 1329131651776855609L, 4, null, String.valueOf(str), Long.valueOf(i2));
        }
        displayContent.mAllSleepTokens.remove(sleepToken);
        if (displayContent.mAllSleepTokens.isEmpty()) {
            this.mService.updateSleepIfNeededLocked();
            if ((!this.mTaskSupervisor.mKeyguardController.isKeyguardOccluded(displayContent.mDisplayId) && str.equals("keyguard")) || str.equals("Display-off") || str.equals("cover-virtual")) {
                displayContent.mSkipAppTransitionAnimation = true;
            }
        }
    }

    public List resolveActivities(int i, Intent intent) {
        try {
            return AppGlobals.getPackageManager().queryIntentActivities(intent, intent.resolveTypeIfNeeded(this.mService.mContext.getContentResolver()), 1024L, i).getList();
        } catch (RemoteException unused) {
            return new ArrayList();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0036, code lost:
    
        r0 = null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.content.pm.ActivityInfo resolveHomeActivity(int r10, android.content.Intent r11) {
        /*
            r9 = this;
            android.content.ComponentName r0 = r11.getComponent()
            r1 = 0
            if (r0 == 0) goto L12
            android.content.pm.IPackageManager r2 = android.app.AppGlobals.getPackageManager()     // Catch: android.os.RemoteException -> L35
            r3 = 1024(0x400, double:5.06E-321)
            android.content.pm.ActivityInfo r0 = r2.getActivityInfo(r0, r3, r10)     // Catch: android.os.RemoteException -> L35
            goto L36
        L12:
            com.android.server.wm.ActivityTaskManagerService r0 = r9.mService     // Catch: android.os.RemoteException -> L35
            android.content.Context r0 = r0.mContext     // Catch: android.os.RemoteException -> L35
            android.content.ContentResolver r0 = r0.getContentResolver()     // Catch: android.os.RemoteException -> L35
            java.lang.String r4 = r11.resolveTypeIfNeeded(r0)     // Catch: android.os.RemoteException -> L35
            com.android.server.wm.ActivityTaskSupervisor r2 = r9.mTaskSupervisor     // Catch: android.os.RemoteException -> L35
            int r7 = android.os.Binder.getCallingUid()     // Catch: android.os.RemoteException -> L35
            int r8 = android.os.Binder.getCallingPid()     // Catch: android.os.RemoteException -> L35
            r6 = 1024(0x400, float:1.435E-42)
            r3 = r11
            r5 = r10
            android.content.pm.ResolveInfo r0 = r2.resolveIntent(r3, r4, r5, r6, r7, r8)     // Catch: android.os.RemoteException -> L35
            if (r0 == 0) goto L35
            android.content.pm.ActivityInfo r0 = r0.activityInfo     // Catch: android.os.RemoteException -> L35
            goto L36
        L35:
            r0 = r1
        L36:
            if (r0 != 0) goto L4d
            java.lang.Exception r9 = new java.lang.Exception
            r9.<init>()
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)
            java.lang.Object[] r10 = new java.lang.Object[]{r11, r10}
            java.lang.String r11 = "WindowManager"
            java.lang.String r0 = "No home screen found for %s and user %d"
            com.android.server.utils.Slogf.wtf(r11, r9, r0, r10)
            return r1
        L4d:
            android.content.pm.ActivityInfo r11 = new android.content.pm.ActivityInfo
            r11.<init>(r0)
            com.android.server.wm.ActivityTaskManagerService r9 = r9.mService
            android.content.pm.ApplicationInfo r0 = r11.applicationInfo
            r9.getClass()
            if (r0 != 0) goto L5c
            goto L64
        L5c:
            android.content.pm.ApplicationInfo r1 = new android.content.pm.ApplicationInfo
            r1.<init>(r0)
            r1.initForUser(r10)
        L64:
            r11.applicationInfo = r1
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.RootWindowContainer.resolveHomeActivity(int, android.content.Intent):android.content.pm.ActivityInfo");
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0041  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.util.Pair resolveSecondaryHomeActivity(int r10, com.android.server.wm.TaskDisplayArea r11) {
        /*
            r9 = this;
            com.android.server.wm.DisplayContent r0 = r9.mDefaultDisplay
            com.android.server.wm.TaskDisplayArea r0 = r0.getDefaultTaskDisplayArea()
            if (r11 == r0) goto Lcd
            com.android.server.wm.ActivityTaskManagerService r0 = r9.mService
            android.content.Intent r0 = r0.getHomeIntent()
            android.content.pm.ActivityInfo r1 = r9.resolveHomeActivity(r10, r0)
            r2 = 0
            if (r1 == 0) goto L17
            r3 = 1
            goto L18
        L17:
            r3 = r2
        L18:
            boolean r4 = android.companion.virtual.flags.Flags.vdmCustomHome()
            r5 = 0
            if (r4 == 0) goto L4c
            com.android.server.wm.DisplayContent r4 = r11.getDisplayContent()
            if (r4 == 0) goto L3e
            com.android.server.wm.DisplayContent r4 = r11.getDisplayContent()
            boolean r6 = r4.isHomeSupported()
            if (r6 == 0) goto L3e
            com.android.server.wm.DisplayWindowPolicyControllerHelper r4 = r4.mDwpcHelper
            if (r4 != 0) goto L34
            goto L3e
        L34:
            android.window.DisplayWindowPolicyController r4 = r4.mDisplayWindowPolicyController
            if (r4 != 0) goto L39
            goto L3e
        L39:
            android.content.ComponentName r4 = r4.getCustomHomeComponent()
            goto L3f
        L3e:
            r4 = r5
        L3f:
            if (r4 == 0) goto L4c
            r0.setComponent(r4)
            android.content.pm.ActivityInfo r4 = r9.resolveHomeActivity(r10, r0)
            if (r4 == 0) goto L4c
            r3 = r2
            r1 = r4
        L4c:
            if (r3 == 0) goto Lb3
            java.lang.Class<com.android.internal.app.ResolverActivity> r3 = com.android.internal.app.ResolverActivity.class
            java.lang.String r3 = r3.getName()
            java.lang.String r4 = r1.name
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L5e
            r1 = r5
            goto Lb3
        L5e:
            com.android.server.wm.ActivityTaskManagerService r0 = r9.mService
            com.android.server.wm.WindowManagerService r3 = r9.mWmService
            com.android.server.wm.WindowManagerServiceExt r3 = r3.mExt
            com.android.server.wm.ExtraDisplayPolicy r3 = r3.mExtraDisplayPolicy
            com.android.server.wm.DisplayContent r4 = r11.mDisplayContent
            int r4 = r4.mDisplayId
            boolean r3 = r3.hasCoverHome(r4)
            if (r3 == 0) goto L7b
            com.android.server.wm.ActivityTaskManagerService r3 = r9.mService
            android.content.ComponentName r3 = r3.getSysUiServiceComponentLocked()
            java.lang.String r3 = r3.getPackageName()
            goto L7f
        L7b:
            android.content.pm.ApplicationInfo r3 = r1.applicationInfo
            java.lang.String r3 = r3.packageName
        L7f:
            android.content.Intent r0 = r0.getSecondaryHomeIntent(r3)
            java.util.List r3 = r9.resolveActivities(r10, r0)
            int r4 = r3.size()
            java.lang.String r1 = r1.name
            r6 = r2
        L8e:
            if (r6 >= r4) goto La6
            java.lang.Object r7 = r3.get(r6)
            android.content.pm.ResolveInfo r7 = (android.content.pm.ResolveInfo) r7
            android.content.pm.ActivityInfo r8 = r7.activityInfo
            java.lang.String r8 = r8.name
            boolean r8 = r8.equals(r1)
            if (r8 == 0) goto La3
            android.content.pm.ActivityInfo r1 = r7.activityInfo
            goto La7
        La3:
            int r6 = r6 + 1
            goto L8e
        La6:
            r1 = r5
        La7:
            if (r1 != 0) goto Lb3
            if (r4 <= 0) goto Lb3
            java.lang.Object r1 = r3.get(r2)
            android.content.pm.ResolveInfo r1 = (android.content.pm.ResolveInfo) r1
            android.content.pm.ActivityInfo r1 = r1.activityInfo
        Lb3:
            if (r1 == 0) goto Lbc
            boolean r11 = r9.canStartHomeOnDisplayArea(r1, r11, r2)
            if (r11 != 0) goto Lbc
            r1 = r5
        Lbc:
            if (r1 != 0) goto Lc8
            com.android.server.wm.ActivityTaskManagerService r11 = r9.mService
            android.content.Intent r0 = r11.getSecondaryHomeIntent(r5)
            android.content.pm.ActivityInfo r1 = r9.resolveHomeActivity(r10, r0)
        Lc8:
            android.util.Pair r9 = android.util.Pair.create(r1, r0)
            return r9
        Lcd:
            java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException
            java.lang.String r10 = "resolveSecondaryHomeActivity: Should not be default task container"
            r9.<init>(r10)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.RootWindowContainer.resolveSecondaryHomeActivity(int, com.android.server.wm.TaskDisplayArea):android.util.Pair");
    }

    public final void resumeFocusedTasksTopActivities() {
        resumeFocusedTasksTopActivities(null, null, null, false);
    }

    public final boolean resumeFocusedTasksTopActivities(final Task task, final ActivityRecord activityRecord, final ActivityOptions activityOptions, boolean z) {
        int i;
        int i2 = 1;
        if (!(this.mTaskSupervisor.mDeferResumeCount == 0)) {
            return false;
        }
        boolean resumeTopActivityUncheckedLocked = (task == null || !(task.isTopRootTaskInDisplayArea() || getTopDisplayFocusedRootTask() == task)) ? false : task.resumeTopActivityUncheckedLocked(activityRecord, activityOptions, z);
        int childCount = getChildCount() - 1;
        while (childCount >= 0) {
            DisplayContent displayContent = (DisplayContent) getChildAt(childCount);
            final boolean[] zArr = new boolean[i2];
            final ActivityRecord activityRecord2 = displayContent.topRunningActivity(false);
            final boolean z2 = resumeTopActivityUncheckedLocked;
            displayContent.forAllRootTasks(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda19
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    Task task2 = Task.this;
                    boolean[] zArr2 = zArr;
                    boolean z3 = z2;
                    ActivityRecord activityRecord3 = activityRecord2;
                    ActivityOptions activityOptions2 = activityOptions;
                    ActivityRecord activityRecord4 = activityRecord;
                    Task task3 = (Task) obj;
                    ActivityRecord activityRecord5 = task3.topRunningActivity(false);
                    if (!task3.isFocusableAndVisible() || activityRecord5 == null) {
                        return;
                    }
                    if (task3 == task2) {
                        zArr2[0] = zArr2[0] | z3;
                    } else if (activityRecord5.isState(ActivityRecord.State.RESUMED) && activityRecord5 == activityRecord3) {
                        task3.executeAppTransition(activityOptions2);
                    } else {
                        zArr2[0] = activityRecord5.makeActiveIfNeeded(activityRecord4) | zArr2[0];
                    }
                }
            });
            boolean z3 = zArr[0];
            boolean z4 = resumeTopActivityUncheckedLocked | z3;
            if (!z3 && ((displayContent.mDisplayId != 2 || this.mService.mDexController.getDexModeLocked() != 0) && ((!displayContent.isRemoteAppDisplay() || displayContent.topRunningActivity(false) != null) && (!displayContent.isAppCastingDisplay() || displayContent.topRunningActivity(false) != null)))) {
                Task focusedRootTask = displayContent.getFocusedRootTask();
                if (focusedRootTask != null) {
                    z4 |= focusedRootTask.resumeTopActivityUncheckedLocked(activityRecord, activityOptions, false);
                    if (!z4 && activityRecord != null && activityRecord.mAliasChild && activityRecord.isState(ActivityRecord.State.INITIALIZING) && !activityRecord.isVisibleRequested() && focusedRootTask == task && focusedRootTask.inFreeformWindowingMode() && focusedRootTask.shouldBeVisible(null)) {
                        i = 1;
                        focusedRootTask.ensureActivitiesVisible(true, null);
                    }
                } else {
                    i = 1;
                    if (task == null) {
                        resumeTopActivityUncheckedLocked = resumeHomeActivity(null, "no-focusable-task", displayContent.getDefaultTaskDisplayArea()) | z4;
                        childCount--;
                        i2 = i;
                    }
                }
                resumeTopActivityUncheckedLocked = z4;
                childCount--;
                i2 = i;
            }
            i = 1;
            resumeTopActivityUncheckedLocked = z4;
            childCount--;
            i2 = i;
        }
        return resumeTopActivityUncheckedLocked;
    }

    public final boolean resumeHomeActivity(ActivityRecord activityRecord, String str, TaskDisplayArea taskDisplayArea) {
        ActivityRecord activity;
        if (!this.mService.mAmInternal.isBooting() && !this.mService.mAmInternal.isBooted()) {
            return false;
        }
        if (taskDisplayArea == null) {
            taskDisplayArea = this.mDefaultDisplay.getDefaultTaskDisplayArea();
        }
        TaskDisplayArea taskDisplayArea2 = taskDisplayArea;
        int i = taskDisplayArea2.mRootWindowContainer.mCurrentUser;
        Task task = taskDisplayArea2.mRootHomeTask;
        if (task == null) {
            activity = null;
        } else {
            PooledPredicate obtainPredicate = PooledLambda.obtainPredicate(new TaskDisplayArea$$ExternalSyntheticLambda2(), PooledLambda.__(ActivityRecord.class), Integer.valueOf(i));
            activity = task.getActivity(obtainPredicate);
            obtainPredicate.recycle();
        }
        String concat = str.concat(" resumeHomeActivity");
        if (activity != null && !activity.finishing) {
            activity.moveFocusableActivityToTop(concat);
            return resumeFocusedTasksTopActivities(activity.getRootTask(), activityRecord, null, false);
        }
        return startHomeOnTaskDisplayArea(this.mWmService.mUmInternal.getUserAssignedToDisplay(taskDisplayArea2.mDisplayContent.mDisplayId), concat, taskDisplayArea2, false, false);
    }

    public final void sendSleepTransition(final DisplayContent displayContent) {
        final Transition transition = new Transition(12, 0, displayContent.mTransitionController, this.mWmService.mSyncEngine);
        TransitionController.OnStartCollect onStartCollect = new TransitionController.OnStartCollect() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda40
            @Override // com.android.server.wm.TransitionController.OnStartCollect
            public final void onCollectStarted(boolean z) {
                DisplayContent displayContent2 = DisplayContent.this;
                Transition transition2 = transition;
                if (z && !displayContent2.shouldSleep()) {
                    transition2.abort();
                    return;
                }
                displayContent2.mTransitionController.requestStartTransition(transition2, null, null, null);
                int i = transition2.mState;
                if (i == 0 || i == 1) {
                    if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_TRANSITIONS_enabled[1]) {
                        ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -892865733969888022L, 1, null, Long.valueOf(transition2.mSyncId));
                    }
                    transition2.mForcePlaying = true;
                    Transition.ReadyTracker readyTracker = transition2.mReadyTracker;
                    for (int size = readyTracker.mConditions.size() - 1; size >= 0; size--) {
                        Transition.ReadyCondition readyCondition = (Transition.ReadyCondition) readyTracker.mConditions.get(size);
                        if (!readyCondition.mMet) {
                            readyCondition.mAlternate = "play-now";
                            readyCondition.meet();
                        }
                    }
                    Transition.ReadyCondition readyCondition2 = new Transition.ReadyCondition("force-play-now");
                    readyTracker.add(readyCondition2);
                    readyCondition2.meet();
                    transition2.setAllReady();
                    if (transition2.mState == 0) {
                        transition2.start();
                    }
                    transition2.mSyncEngine.onSurfacePlacement();
                }
            }
        };
        if (displayContent.mTransitionController.isCollecting()) {
            displayContent.mTransitionController.startCollectOrQueue(transition, onStartCollect);
            return;
        }
        if (this.mWindowManager.mSyncEngine.hasActiveSync()) {
            Slog.w("WindowManager", "Ongoing sync outside of a transition.");
        }
        displayContent.mTransitionController.moveToCollecting(transition);
        onStartCollect.onCollectStarted(false);
    }

    public final void setWindowManager(WindowManagerService windowManagerService) {
        this.mWindowManager = windowManagerService;
        DisplayManager displayManager = (DisplayManager) this.mService.mContext.getSystemService(DisplayManager.class);
        this.mDisplayManager = displayManager;
        displayManager.registerDisplayListener(this, this.mService.mUiHandler);
        this.mDisplayManagerInternal = (DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class);
        for (Display display : this.mDisplayManager.getDisplays("android.hardware.display.category.ALL_INCLUDING_BUILT_IN")) {
            DisplayContent displayContent = new DisplayContent(display, this, this.mDeviceStateController);
            addChild(displayContent, Integer.MIN_VALUE);
            if (displayContent.mDisplayId == 0) {
                this.mDefaultDisplay = displayContent;
            }
        }
        TaskDisplayArea defaultTaskDisplayArea = this.mDefaultDisplay.getDefaultTaskDisplayArea();
        defaultTaskDisplayArea.getOrCreateRootHomeTask(true);
        positionChildAt(Integer.MAX_VALUE, defaultTaskDisplayArea.mDisplayContent, false);
    }

    public final boolean shouldPlacePrimaryHomeOnDisplay(int i) {
        if (i != 0) {
            if (i != -1) {
                if (i != this.mService.mVr2dDisplayId) {
                    WindowManagerService windowManagerService = this.mWmService;
                    if (windowManagerService.mUmInternal.getMainDisplayAssignedToUser(windowManagerService.mUmInternal.getUserAssignedToDisplay(i)) == i) {
                    }
                }
            }
            return false;
        }
        return true;
    }

    public final boolean shouldPlaceSecondaryHomeOnDisplayArea(TaskDisplayArea taskDisplayArea) {
        if (this.mDefaultDisplay.getDefaultTaskDisplayArea() == taskDisplayArea) {
            throw new IllegalArgumentException("shouldPlaceSecondaryHomeOnDisplay: Should not be on default task container");
        }
        if (taskDisplayArea == null || !taskDisplayArea.canHostHomeTask()) {
            return false;
        }
        int i = taskDisplayArea.mDisplayContent.mDisplayId;
        if (i != 0 && !this.mService.mSupportsMultiDisplay) {
            return false;
        }
        if (!this.mWmService.mExt.mExtraDisplayPolicy.hasCoverHome(i) && (Settings.Global.getInt(this.mService.mContext.getContentResolver(), "device_provisioned", 0) == 0 || !StorageManager.isCeStorageUnlocked(this.mCurrentUser))) {
            return false;
        }
        if (taskDisplayArea.mDisplayContent.mDisplayId == 2 && this.mService.mDexController.getDexModeLocked() == 2) {
            return true;
        }
        DisplayContent displayContent = taskDisplayArea.getDisplayContent();
        return (displayContent == null || displayContent.mRemoved || !displayContent.isHomeSupported()) ? false : true;
    }

    public final boolean startHomeOnDisplay(final String str, final int i, int i2, final boolean z, final boolean z2) {
        if (i2 == -1) {
            Task topDisplayFocusedRootTask = getTopDisplayFocusedRootTask();
            i2 = topDisplayFocusedRootTask != null ? topDisplayFocusedRootTask.getDisplayId() : 0;
        }
        return ((Boolean) getDisplayContent(i2).reduceOnAllTaskDisplayAreas(new BiFunction() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda28
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                RootWindowContainer rootWindowContainer = RootWindowContainer.this;
                int i3 = i;
                String str2 = str;
                boolean z3 = z;
                boolean z4 = z2;
                rootWindowContainer.getClass();
                return Boolean.valueOf(((Boolean) obj2).booleanValue() | rootWindowContainer.startHomeOnTaskDisplayArea(i3, str2, (TaskDisplayArea) obj, z3, z4));
            }
        }, Boolean.FALSE)).booleanValue();
    }

    public final boolean startHomeOnTaskDisplayArea(int i, String str, TaskDisplayArea taskDisplayArea, boolean z, boolean z2) {
        Intent homeIntent;
        ActivityInfo resolveHomeActivity;
        if (taskDisplayArea == null) {
            Task topDisplayFocusedRootTask = getTopDisplayFocusedRootTask();
            taskDisplayArea = topDisplayFocusedRootTask != null ? topDisplayFocusedRootTask.getDisplayArea() : this.mDefaultDisplay.getDefaultTaskDisplayArea();
        }
        int i2 = taskDisplayArea.mDisplayContent.mDisplayId;
        int dexModeLocked = this.mService.mDexController.getDexModeLocked();
        if ((dexModeLocked == 2 && i2 == 2) || (dexModeLocked == 1 && i2 == 0)) {
            DexController dexController = this.mService.mDexController;
            dexController.getClass();
            SomeArgs obtain = SomeArgs.obtain();
            obtain.argi1 = i2;
            DexController.H h = dexController.mH;
            h.sendMessage(h.obtainMessage(7, obtain));
            return true;
        }
        if (i2 == 2) {
            return false;
        }
        if (taskDisplayArea != this.mDefaultDisplay.getDefaultTaskDisplayArea()) {
            if (this.mWmService.mUmInternal.getMainDisplayAssignedToUser(i) != taskDisplayArea.mDisplayContent.mDisplayId) {
                if (shouldPlaceSecondaryHomeOnDisplayArea(taskDisplayArea)) {
                    Pair resolveSecondaryHomeActivity = resolveSecondaryHomeActivity(i, taskDisplayArea);
                    resolveHomeActivity = (ActivityInfo) resolveSecondaryHomeActivity.first;
                    homeIntent = (Intent) resolveSecondaryHomeActivity.second;
                } else {
                    resolveHomeActivity = null;
                    homeIntent = null;
                }
                if (resolveHomeActivity != null || homeIntent == null || !canStartHomeOnDisplayArea(resolveHomeActivity, taskDisplayArea, z)) {
                    return false;
                }
                if (this.mService.mAmInternal.shouldDelayHomeLaunch(i)) {
                    AnyMotionDetector$$ExternalSyntheticOutline0.m(i, "ThemeHomeDelay: Home launch was deferred with user ", "WindowManager");
                    return false;
                }
                homeIntent.setComponent(new ComponentName(resolveHomeActivity.applicationInfo.packageName, resolveHomeActivity.name));
                homeIntent.setFlags(homeIntent.getFlags() | 268435456);
                if (z2) {
                    homeIntent.putExtra("android.intent.extra.FROM_HOME_KEY", true);
                    RecentsAnimationController recentsAnimationController = this.mWindowManager.mRecentsAnimationController;
                    if (recentsAnimationController != null) {
                        recentsAnimationController.cancelAnimation((recentsAnimationController.mTargetActivityType == 2 && recentsAnimationController.mWillFinishToHome) ? 1 : 0, "cancelAnimationForHomeStart", true);
                    }
                    if (taskDisplayArea.mDisplayContent.mDisplayId == 0) {
                        this.mService.mMultiTaskingController.minimizeAllTasksLocked(0, true);
                    }
                    if (CoreRune.MW_SA_LOGGING && taskDisplayArea.mDisplayContent.mDisplayId == 0 && taskDisplayArea.isSplitScreenModeActivated()) {
                        CoreSaLogger.logForAdvanced("1005", "Tap 'Home' button");
                    }
                }
                homeIntent.putExtra("android.intent.extra.EXTRA_START_REASON", str);
                String str2 = str + ":" + i + ":" + UserHandle.getUserId(resolveHomeActivity.applicationInfo.uid) + ":" + taskDisplayArea.mDisplayContent.mDisplayId;
                ActivityStartController activityStartController = this.mService.mActivityStartController;
                activityStartController.getClass();
                ActivityOptions makeBasic = ActivityOptions.makeBasic();
                makeBasic.setLaunchWindowingMode(1);
                if (!ResolverActivity.class.getName().equals(resolveHomeActivity.name)) {
                    makeBasic.setLaunchActivityType(2);
                }
                makeBasic.setLaunchDisplayId(taskDisplayArea.mDisplayContent.mDisplayId);
                makeBasic.setLaunchTaskDisplayArea(taskDisplayArea.mRemoteToken.toWindowContainerToken());
                ActivityTaskSupervisor activityTaskSupervisor = activityStartController.mSupervisor;
                activityTaskSupervisor.beginDeferResume();
                try {
                    Task orCreateRootHomeTask = taskDisplayArea.getOrCreateRootHomeTask(true);
                    activityTaskSupervisor.endDeferResume();
                    ActivityStarter obtainStarter = activityStartController.obtainStarter(homeIntent, "startHomeActivity: " + str2);
                    ActivityStarter.Request request = obtainStarter.mRequest;
                    ActivityRecord[] activityRecordArr = activityStartController.tmpOutRecord;
                    request.outActivity = activityRecordArr;
                    request.callingUid = 0;
                    request.activityInfo = resolveHomeActivity;
                    obtainStarter.setActivityOptions(makeBasic.toBundle());
                    activityStartController.mLastHomeActivityStartResult = obtainStarter.execute();
                    activityStartController.mLastHomeActivityStartRecord = activityRecordArr[0];
                    if (orCreateRootHomeTask.mInResumeTopActivity) {
                        ActivityTaskSupervisor.ActivityTaskSupervisorHandler activityTaskSupervisorHandler = activityTaskSupervisor.mHandler;
                        if (!activityTaskSupervisorHandler.hasMessages(202)) {
                            activityTaskSupervisorHandler.sendEmptyMessage(202);
                        }
                    }
                    return true;
                } catch (Throwable th) {
                    activityTaskSupervisor.endDeferResume();
                    throw th;
                }
            }
        }
        homeIntent = this.mService.getHomeIntent();
        resolveHomeActivity = resolveHomeActivity(i, homeIntent);
        if (resolveHomeActivity != null) {
        }
        return false;
    }

    public final void startPowerModeLaunchIfNeeded(boolean z, ActivityRecord activityRecord) {
        ActivityOptions activityOptions;
        int i = 1;
        if (!z && activityRecord != null && activityRecord.app != null) {
            boolean[] zArr = {true};
            boolean[] zArr2 = {true};
            forAllTaskDisplayAreas(new RootWindowContainer$$ExternalSyntheticLambda0(zArr, zArr2, activityRecord));
            if (!zArr[0] && !zArr2[0]) {
                return;
            }
        }
        if ((activityRecord != null ? activityRecord.isKeyguardLocked() : this.mDefaultDisplay.isKeyguardLocked()) && activityRecord != null) {
            if (!(activityRecord.mLaunchSourceType == 3) && ((activityOptions = activityRecord.mPendingOptions) == null || activityOptions.getSourceInfo() == null || activityOptions.getSourceInfo().type != 3)) {
                i = 5;
            }
        }
        this.mService.startPowerMode(i);
    }

    public final boolean switchUser(final int i, UserState userState) {
        Task topDisplayFocusedRootTask = getTopDisplayFocusedRootTask();
        int i2 = topDisplayFocusedRootTask != null ? topDisplayFocusedRootTask.getRootTask().mTaskId : -1;
        removeRootTasksInWindowingModes(2);
        this.mService.mMultiTaskingController.minimizeAllTasksLocked(0, true);
        this.mUserRootTaskInFront.put(this.mCurrentUser, i2);
        this.mCurrentUser = i;
        this.mTaskSupervisor.mStartingUsers.add(userState);
        forAllRootTasks(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((Task) obj).switchUser(i);
            }
        });
        if (topDisplayFocusedRootTask != null) {
            UserProperties userProperties = ((UserManagerInternal) LocalServices.getService(UserManagerInternal.class)).getUserProperties(topDisplayFocusedRootTask.mUserId);
            if (userProperties != null && userProperties.getAlwaysVisible()) {
                Slog.i("WindowManager", "Persisting top task because it belongs to an always-visible user");
                this.mUserRootTaskInFront.put(this.mCurrentUser, i2);
            }
        }
        Task rootTask = getRootTask(this.mUserRootTaskInFront.get(i));
        if (rootTask == null) {
            rootTask = this.mDefaultDisplay.getDefaultTaskDisplayArea().getOrCreateRootHomeTask(false);
        }
        boolean isActivityTypeHome = rootTask.isActivityTypeHome();
        if (rootTask.getDisplayId() == 0) {
            rootTask.moveToFront("switchUserOnHomeDisplay", null);
        } else {
            resumeHomeActivity(null, "switchUserOnOtherDisplay", this.mDefaultDisplay.getDefaultTaskDisplayArea());
        }
        return isActivityTypeHome;
    }

    public final ActivityRecord topRunningActivity() {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            ActivityRecord activityRecord = ((DisplayContent) getChildAt(childCount)).topRunningActivity(false);
            if (activityRecord != null) {
                return activityRecord;
            }
        }
        return null;
    }

    public final void updateDisplayImePolicyCache() {
        ArrayMap arrayMap = new ArrayMap();
        forAllDisplays(new RootWindowContainer$$ExternalSyntheticLambda1(0, arrayMap));
        this.mWmService.mDisplayImePolicyCache = Collections.unmodifiableMap(arrayMap);
    }

    public final void updateUIDsPresentOnDisplay() {
        this.mDisplayAccessUIDs.clear();
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            DisplayContent displayContent = (DisplayContent) getChildAt(childCount);
            if (displayContent.isPrivate()) {
                SparseArray sparseArray = this.mDisplayAccessUIDs;
                int i = displayContent.mDisplayId;
                displayContent.mDisplayAccessUIDs.clear();
                displayContent.mDisplayContent.forAllActivities(new DisplayContent$$ExternalSyntheticLambda1(7, displayContent));
                sparseArray.append(i, displayContent.mDisplayAccessUIDs);
            }
        }
        this.mDisplayManagerInternal.setDisplayAccessUIDs(this.mDisplayAccessUIDs);
    }
}
