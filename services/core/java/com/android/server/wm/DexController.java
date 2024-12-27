package com.android.server.wm;

import android.R;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.biometrics.face.V1_0.OptionalBool$$ExternalSyntheticOutline0;
import android.hardware.display.DisplayManagerGlobal;
import android.hardware.display.VirtualDisplay;
import android.hardware.display.VirtualDisplayConfig;
import android.media.projection.MediaProjection;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.Base64;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.WindowInsets;
import android.widget.Toast;

import com.android.internal.os.SomeArgs;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.internal.util.function.pooled.PooledPredicate;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.ServiceKeeper$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import com.android.server.wm.ActivityTaskManagerService.SleepTokenAcquirerImpl;

import com.samsung.android.desktopmode.DesktopModeManagerInternal;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.game.SemGameManager;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import com.samsung.android.multiwindow.IDexTransientCaptionDelayListener;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.server.packagefeature.PackageFeature;
import com.samsung.android.server.packagefeature.util.PackageSpecialManagementList;

import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class DexController implements IController {
    public static final Set DEFAULT_ALLOW_HOME_SET =
            Set.of(
                    "TP+fe8M5uStQvlunzY6n5uiGTr6ReHrxNWA2QXUmsbo=",
                    "9jgH8FMKl5YrmkLKzhPt0BPyunVOn5QZd4RXlHG+m3U=",
                    "ntxM9ozBwRd3xqwAhxYRewH46bxRXjgtRewzTdBekgc=",
                    "5LmLdKeONhZxMkwo4Z8PX72qMPwRt7aEqQGAXXrBEYk=",
                    "SPlqtyOkQMcV+iLM67vecvg2Or3jcHS+/2TBTCIcX6Q=",
                    "5oo37SkHJlg9Fi08Q6gJjx2yE6xywWNxwerw09xkRcI=");
    public static final int UPDATE_DEX_IME_STATE_DELAY_MS =
            FrameworkStatsLog
                    .CAMERA_SHOT_LATENCY_REPORTED__MODE__CONTROL_DS_MODE_MACRO_RAW_SR_MERGE;
    public final ActivityTaskManagerService mAtm;
    public final ActivityTaskManagerService.SleepTokenAcquirerImpl mDeactivateDexSleepTokenAcquirer;
    public boolean mDexDisplayActivated;
    public float mDexFontScale;
    public final DexActivityStartInterceptor mDexInterceptor;
    public final DexMetaKeyPolicy mDexMetaKeyPolicy;
    public boolean mDexStandaloneRotationEnabled;
    public boolean mDexTouchPadEnabled;
    public float mGlobalFontScaleForRestore;
    public final WindowManagerGlobalLock mGlobalLock;
    public H mH;
    public boolean mIsDexForceImmersiveModeEnabled;
    public boolean mIsInDexForceImmersiveMode;
    public boolean mStartFromRecentInfo;
    public boolean mUpdatedFontScaleForDexDual;
    public WindowManagerService mWm;
    public final PackageSpecialManagementList mSCPMLaunchBlockList =
            new PackageSpecialManagementList(PackageFeature.DEX_LAUNCH_B);
    public final HashMap mGameAppsMap = new HashMap();
    public final PendingActivityInfo mPendingActivityInfo = new PendingActivityInfo();
    public int mLastDexMode = 0;
    public int mSourceDisplayId = -1;
    public int mTargetDisplayId = -1;
    public WindowState mLastInputMethodInputTarget = null;
    public boolean mDexImeWindowVisibleInDefaultDisplay = false;
    public final AnonymousClass1 mUpdateDexImeStateRunnable =
            new Runnable() { // from class: com.android.server.wm.DexController.1
                @Override // java.lang.Runnable
                public final void run() {
                    WindowManagerGlobalLock windowManagerGlobalLock =
                            DexController.this.mAtm.mGlobalLock;
                    WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            if (DexController.this.updateDexImeWindowStateIfNeededLocked()) {
                                DexController.this.mWm.requestTraversal();
                            }
                        } catch (Throwable th) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                }
            };
    public boolean mRequestedDexDisplayEnabled = false;
    public boolean mLastReportedDexDisplayState = false;
    public VirtualDisplay mDexDisplay = null;
    public final SparseArray mDisplayContexts = new SparseArray();
    public final Point mDexDisplaySize = new Point();
    public final List mMinimizedToggleTasks = new ArrayList();
    public int mDisplayFreeformMaxCount = 0;
    public final RemoteCallbackList mDexSnappingCallbacks = new RemoteCallbackList();
    public int mDexStarShowingDelayTime = -1;
    public final RemoteCallbackList mDexTransientCaptionDelayCallbacks = new RemoteCallbackList();
    public final ArrayList mWaitingTransitionFinishedTokens = new ArrayList();

    public final class DexMetaDataInfo {
        public DexController$Utils$TypedMetaDataValue mHeightValue;
        public DexController$Utils$TypedMetaDataValue mWidthValue;
    }

    public final class FindTaskResult {
        public final boolean mIsRootTask;
        public final String mProcessName;
        public final int mTargetDisplayId;
        public final Task mTask;
        public final int mUid;

        public FindTaskResult(ActivityRecord activityRecord, int i) {
            Task task = activityRecord.task;
            this.mTask = task;
            this.mIsRootTask = activityRecord.equals(task.getRootActivity(true, false));
            this.mUid = activityRecord.getUid();
            this.mProcessName = activityRecord.processName;
            this.mTargetDisplayId = i;
        }

        public final void execute() {
            Task task;
            boolean z = this.mIsRootTask;
            Task task2 = this.mTask;
            if (z) {
                DexController dexController = DexController.this;
                DexRestartAppInfo dexRestartAppInfo = dexController.mPendingActivityInfo.mInfo;
                ActivityOptions activityOptions =
                        dexRestartAppInfo != null ? dexRestartAppInfo.mOptions : null;
                int i = this.mTargetDisplayId;
                if (activityOptions == null
                        || ((task = dexRestartAppInfo.mReusedTask) != null && task == task2)) {
                    dexController.moveTaskToDisplayBackLocked(
                            task2, i, "reparentToDisplayAndStartPendingActivity", activityOptions);
                    return;
                } else {
                    dexController.moveTaskToDisplayBackLocked(
                            task2, i, "reparentToDisplayAndStartPendingActivity", null);
                    return;
                }
            }
            AtomicInteger atomicInteger = new AtomicInteger();
            task2.forAllActivities(
                    new DexController$$ExternalSyntheticLambda4(2, this, atomicInteger));
            if (atomicInteger.get() > -1) {
                Slog.d(
                        "DexController",
                        "FindTaskResult_execute: performClear(Ndx="
                                + atomicInteger
                                + "), "
                                + task2
                                + ", reason=reparentToDisplayAndStartPendingActivity");
                task2.removeActivities("reparentToDisplayAndStartPendingActivity", false);
            }
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("{");
            sb.append(this.mTask);
            sb.append(" targetDisplayId=");
            sb.append(this.mTargetDisplayId);
            sb.append(" isRoot=");
            return OptionalBool$$ExternalSyntheticOutline0.m("}", sb, this.mIsRootTask);
        }
    }

    public final class H extends Handler {
        public H(Looper looper) {
            super(looper);
        }

        /* JADX WARN: Finally extract failed */
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            Task rootTask;
            RecentsAnimationController recentsAnimationController;
            boolean z;
            int i = message.what;
            if (i == 1) {
                DexRestartAppInfo dexRestartAppInfo = (DexRestartAppInfo) message.obj;
                int i2 = message.arg1;
                DexController dexController = DexController.this;
                if (dexRestartAppInfo == null) {
                    dexController.getClass();
                    Slog.w(
                            "DexController",
                            "DisplayChooserInfo is null. Abort to start pending activity");
                    return;
                }
                WindowManagerGlobalLock windowManagerGlobalLock = dexController.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        ArrayList taskLocked =
                                dexController.getTaskLocked(
                                        dexRestartAppInfo.mUid,
                                        i2,
                                        dexRestartAppInfo.mProcessName,
                                        false);
                        dexController.mPendingActivityInfo.reset();
                        if (!taskLocked.isEmpty()) {
                            dexController.mPendingActivityInfo.set(
                                    dexRestartAppInfo, taskLocked, i2);
                            HashSet hashSet = new HashSet();
                            Iterator it = taskLocked.iterator();
                            while (it.hasNext()) {
                                Task task = ((FindTaskResult) it.next()).mTask;
                                Task rootTask2 =
                                        task.inSplitScreenWindowingMode()
                                                ? task
                                                : task.getRootTask();
                                if (rootTask2 != null) {
                                    if (dexController.mDexDisplayActivated
                                            || rootTask2.getDisplayId() != 2) {
                                        if (rootTask2.isAnimatingByRecents()
                                                && (recentsAnimationController =
                                                                dexController
                                                                        .mAtm
                                                                        .mWindowManager
                                                                        .mRecentsAnimationController)
                                                        != null) {
                                            recentsAnimationController.cancelAnimation(
                                                    recentsAnimationController.mWillFinishToHome
                                                            ? 1
                                                            : 2,
                                                    "cancelAnimationForDisplayChange",
                                                    true);
                                        }
                                        hashSet.add(Integer.valueOf(rootTask2.getDisplayId()));
                                        task.mIsAvoidTrimDexPendingActivityTask = true;
                                        ActivityTaskSupervisor activityTaskSupervisor =
                                                dexController.mAtm.mTaskSupervisor;
                                        if (activityTaskSupervisor.mDeferRootVisibilityUpdate) {
                                            activityTaskSupervisor.mDeferRootVisibilityUpdate =
                                                    false;
                                        }
                                        rootTask2.moveTaskToBack(task, null);
                                    } else {
                                        ActivityRecord topActivity =
                                                task.getTopActivity(false, true);
                                        if (topActivity != null && topActivity.mVisible) {
                                            topActivity.setVisibility(false);
                                        }
                                    }
                                }
                            }
                            Iterator it2 = hashSet.iterator();
                            while (it2.hasNext()) {
                                DisplayContent displayContent =
                                        dexController.mAtm.mRootWindowContainer.getDisplayContent(
                                                ((Integer) it2.next()).intValue());
                                if (displayContent != null
                                        && (rootTask =
                                                        displayContent.getRootTask(
                                                                WindowContainer
                                                                        .alwaysTruePredicate()))
                                                != null) {
                                    dexController.mAtm.mRootWindowContainer
                                            .ensureVisibilityAndConfig(
                                                    rootTask.getTopActivity(false, true),
                                                    displayContent,
                                                    true);
                                }
                            }
                        }
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                dexController.mH.sendEmptyMessageDelayed(3, 4000L);
                dexController.scheduleReparentToDisplayAndStartPendingActivity(false);
                return;
            }
            if (i == 2) {
                DexController.m1058$$Nest$mreparentToDisplayAndStartPendingActivity(
                        DexController.this);
                return;
            }
            if (i == 6) {
                try {
                    DexController.this.notifyAppTransitionFinished();
                    return;
                } catch (Exception unused) {
                    Slog.w("DexController", "Failed to notify AppTransitionFinished");
                    return;
                }
            }
            if (i != 7) {
                if (i == 10) {
                    Toast.makeText(
                                    new ContextThemeWrapper(
                                            DexController.this.mAtm.mContext,
                                            R.style.Theme.DeviceDefault.Light),
                                    DexController.this
                                            .mAtm
                                            .mContext
                                            .getResources()
                                            .getString(R.string.guest_name),
                                    0)
                            .show();
                    return;
                }
                if (i != 11) {
                    return;
                }
                SomeArgs someArgs = (SomeArgs) message.obj;
                int i3 = someArgs.argi1;
                Rect rect = (Rect) someArgs.arg1;
                synchronized (DexController.this.mDexSnappingCallbacks) {
                    int beginBroadcast = DexController.this.mDexSnappingCallbacks.beginBroadcast();
                    for (int i4 = 0; i4 < beginBroadcast; i4++) {
                        try {
                            DexController.this
                                    .mDexSnappingCallbacks
                                    .getBroadcastItem(i4)
                                    .onWindowSnappingChanged(i3, rect);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    DexController.this.mDexSnappingCallbacks.finishBroadcast();
                }
                return;
            }
            Slog.d("DexController", "handleMessage: START_DEX_HOME");
            int i5 = ((SomeArgs) message.obj).argi1;
            DesktopModeManagerInternal desktopModeManagerInternal =
                    (DesktopModeManagerInternal)
                            LocalServices.getService(DesktopModeManagerInternal.class);
            if (desktopModeManagerInternal == null) {
                Slog.w("DexController", "startHomeOnDexDisplay: Cannot found DesktopModeService");
                return;
            }
            long currentTimeMillis = System.currentTimeMillis();
            try {
                WindowManagerGlobalLock windowManagerGlobalLock2 = DexController.this.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock2) {
                    try {
                        z = !((ArrayList) DexController.this.mMinimizedToggleTasks).isEmpty();
                        if (!z) {
                            DexController.this.mAtm.mMultiTaskingController.minimizeAllTasksLocked(
                                    i5, true);
                        }
                    } catch (Throwable th2) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th2;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                Slog.d(
                        "DexController",
                        "START_DEX_HOME: minimizeAll for "
                                + (System.currentTimeMillis() - currentTimeMillis)
                                + "ms");
                long currentTimeMillis2 = System.currentTimeMillis();
                try {
                    desktopModeManagerInternal.startHome();
                    Slog.d(
                            "DexController",
                            "START_DEX_HOME: startHome for "
                                    + (System.currentTimeMillis() - currentTimeMillis2)
                                    + "ms");
                    long currentTimeMillis3 = System.currentTimeMillis();
                    try {
                        SemDesktopModeState desktopModeState =
                                desktopModeManagerInternal.getDesktopModeState();
                        WindowManagerGlobalLock windowManagerGlobalLock3 =
                                DexController.this.mGlobalLock;
                        WindowManagerService.boostPriorityForLockedSection();
                        synchronized (windowManagerGlobalLock3) {
                            if (z) {
                                if (i5 != 2) {
                                    try {
                                        if (desktopModeState.getEnabled() == 4) {}
                                    } catch (Throwable th3) {
                                        WindowManagerService.resetPriorityAfterLockedSection();
                                        throw th3;
                                    }
                                }
                                DexController.this.restoreToggleTasksToFrontLocked(i5);
                            }
                        }
                        WindowManagerService.resetPriorityAfterLockedSection();
                    } finally {
                        Slog.d(
                                "DexController",
                                "START_DEX_HOME: restoreToggle for "
                                        + (System.currentTimeMillis() - currentTimeMillis3)
                                        + "ms");
                    }
                } catch (Throwable th4) {
                    Slog.d(
                            "DexController",
                            "START_DEX_HOME: startHome for "
                                    + (System.currentTimeMillis() - currentTimeMillis2)
                                    + "ms");
                    throw th4;
                }
            } catch (Throwable th5) {
                Slog.d(
                        "DexController",
                        "START_DEX_HOME: minimizeAll for "
                                + (System.currentTimeMillis() - currentTimeMillis)
                                + "ms");
                throw th5;
            }
        }
    }

    public final class PendingActivityInfo {
        public int mDisplayId;
        public DexRestartAppInfo mInfo;
        public final ArrayList mFindTaskResultList = new ArrayList();
        public final ArrayList mWaitingStoppedTasks = new ArrayList();
        public final ArrayList mInvisibleTasks = new ArrayList();
        public final ArrayList mWaitingTransitionFinishedTokens = new ArrayList();
        public final ArrayList mOrganizedTaskFragments = new ArrayList();

        public PendingActivityInfo() {}

        public final boolean removeWaitingStoppedTask(String str, Task task) {
            if (!this.mWaitingStoppedTasks.remove(task)) {
                return false;
            }
            Slog.d(
                    "DexController",
                    "removeWaitingStoppedTask: removed from "
                            + task
                            + ", reason="
                            + str
                            + ", numWaitingTasks="
                            + this.mWaitingStoppedTasks.size());
            return true;
        }

        public final void reset() {
            this.mDisplayId = 0;
            this.mFindTaskResultList.clear();
            this.mInfo = null;
            this.mWaitingStoppedTasks.clear();
            this.mWaitingTransitionFinishedTokens.clear();
            this.mInvisibleTasks.clear();
            DexController.this.setWaitingTransitionFinished(this.mWaitingTransitionFinishedTokens);
            this.mOrganizedTaskFragments.clear();
        }

        public final void set(DexRestartAppInfo dexRestartAppInfo, ArrayList arrayList, int i) {
            this.mInfo = dexRestartAppInfo;
            this.mDisplayId = i;
            this.mFindTaskResultList.addAll(arrayList);
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Task task = ((FindTaskResult) it.next()).mTask;
                task.getClass();
                ActivityRecord topActivity = task.getTopActivity(false, true);
                if (topActivity != null) {
                    if (topActivity.nowVisible) {
                        this.mWaitingStoppedTasks.add(task);
                        if (!task.isAnimatingByRecents()) {
                            this.mWaitingTransitionFinishedTokens.add(topActivity.token);
                        }
                    } else {
                        this.mInvisibleTasks.add(task);
                    }
                }
            }
            DexController.this.setWaitingTransitionFinished(this.mWaitingTransitionFinishedTokens);
        }

        public final String toString() {
            StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(128, "DexRestartAppInfo ");
            m.append(this.mInfo);
            m.append(" mDisplayId = ");
            m.append(this.mDisplayId);
            m.append(" mFindTaskResultList size = ");
            m.append(this.mFindTaskResultList.size());
            return m.toString();
        }
    }

    /* renamed from: -$$Nest$mreparentToDisplayAndStartPendingActivity, reason: not valid java name */
    public static void m1058$$Nest$mreparentToDisplayAndStartPendingActivity(
            DexController dexController) {
        ActivityInfo activityInfo;
        PendingActivityLaunch pendingActivityLaunch;
        WindowManagerGlobalLock windowManagerGlobalLock = dexController.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                PendingActivityInfo pendingActivityInfo = dexController.mPendingActivityInfo;
                DexRestartAppInfo dexRestartAppInfo = pendingActivityInfo.mInfo;
                if (dexRestartAppInfo == null) {
                    Slog.w(
                            "DexController",
                            "PendingActivityLaunch is null. Abort to start pending activity");
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                Iterator it = pendingActivityInfo.mFindTaskResultList.iterator();
                while (it.hasNext()) {
                    FindTaskResult findTaskResult = (FindTaskResult) it.next();
                    Task task = findTaskResult.mTask;
                    if (task == null) {
                        Slog.w(
                                "DexController",
                                "reparentToDisplayAndStartPendingActivity : skip handle task null");
                    } else {
                        task.mSkipSavingLaunchingState = true;
                        if (task.mLastNonFullscreenBounds == null) {
                            task.mLastNonFullscreenBounds = new Rect();
                        }
                        findTaskResult.execute();
                        if (findTaskResult.mTask.getDisplayId()
                                == dexRestartAppInfo.mPreferredDisplayId) {
                            Task task2 = findTaskResult.mTask;
                            ActivityInfo.WindowLayout windowLayout = null;
                            task2.mLastNonFullscreenBounds = null;
                            ActivityRecord rootActivity = task2.getRootActivity(true, false);
                            if (rootActivity == null
                                    && (pendingActivityLaunch = dexRestartAppInfo.mPal) != null) {
                                rootActivity = pendingActivityLaunch.r;
                            }
                            ActivityRecord activityRecord = rootActivity;
                            PendingActivityLaunch pendingActivityLaunch2 = dexRestartAppInfo.mPal;
                            ActivityRecord activityRecord2 =
                                    pendingActivityLaunch2 != null
                                            ? pendingActivityLaunch2.sourceRecord
                                            : null;
                            if (activityRecord != null
                                    && (activityInfo = activityRecord.info) != null) {
                                windowLayout = activityInfo.windowLayout;
                            }
                            dexRestartAppInfo.mOptions.setLaunchDisplayId(
                                    dexRestartAppInfo.mPreferredDisplayId);
                            dexController.mAtm.mTaskSupervisor.mLaunchParamsController.layoutTask(
                                    findTaskResult.mTask,
                                    windowLayout,
                                    activityRecord,
                                    activityRecord2,
                                    dexRestartAppInfo.mOptions,
                                    dexRestartAppInfo.mPreferredDisplayId);
                        }
                        findTaskResult.mTask.mIsAvoidTrimDexPendingActivityTask = false;
                    }
                }
                int i = dexController.mPendingActivityInfo.mDisplayId;
                WindowManagerService.resetPriorityAfterLockedSection();
                WindowManagerGlobalLock windowManagerGlobalLock2 = dexController.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock2) {
                    try {
                        Iterator it2 =
                                dexController.mPendingActivityInfo.mFindTaskResultList.iterator();
                        while (it2.hasNext()) {
                            ((FindTaskResult) it2.next()).mTask.mSkipSavingLaunchingState = false;
                        }
                        dexRestartAppInfo.startResult(i, dexController.mAtm);
                        dexController.mPendingActivityInfo.reset();
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            } finally {
                WindowManagerService.resetPriorityAfterLockedSection();
            }
        }
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [com.android.server.wm.DexController$1] */
    public DexController(ActivityTaskManagerService activityTaskManagerService) {
        this.mDexMetaKeyPolicy = null;
        this.mAtm = activityTaskManagerService;
        this.mGlobalLock = activityTaskManagerService.mGlobalLock;
        this.mDexInterceptor = new DexActivityStartInterceptor(activityTaskManagerService, this);
        this.mDeactivateDexSleepTokenAcquirer =
                activityTaskManagerService.new SleepTokenAcquirerImpl("DexController");
        this.mDexMetaKeyPolicy = new DexMetaKeyPolicy(activityTaskManagerService, this);
    }

    public static DexMetaDataInfo parseDexMetadata(ActivityInfo activityInfo) {
        Object obj;
        Object obj2;
        String str;
        String str2;
        Bundle bundle = activityInfo.metaData;
        if (bundle == null) {
            bundle = null;
        }
        ApplicationInfo applicationInfo = activityInfo.applicationInfo;
        Bundle bundle2 = applicationInfo != null ? applicationInfo.metaData : null;
        if (bundle == null && bundle2 == null) {
            return null;
        }
        if (bundle != null
                && bundle.get("com.samsung.android.dex.launchwidth") != null
                && bundle.get("com.samsung.android.dex.launchheight") != null) {
            obj = bundle.get("com.samsung.android.dex.launchwidth");
            obj2 = bundle.get("com.samsung.android.dex.launchheight");
        } else if (bundle2 == null
                || bundle2.get("com.samsung.android.dex.launchwidth") == null
                || bundle2.get("com.samsung.android.dex.launchheight") == null) {
            obj = null;
            obj2 = null;
        } else {
            Object obj3 = bundle2.get("com.samsung.android.dex.launchwidth");
            obj2 = bundle2.get("com.samsung.android.dex.launchheight");
            obj = obj3;
        }
        if (obj == null || obj2 == null) {
            str = null;
            str2 = null;
        } else {
            str =
                    obj instanceof Integer
                            ? Integer.toString(((Integer) obj).intValue())
                            : obj instanceof String ? (String) obj : null;
            str2 =
                    obj2 instanceof Integer
                            ? Integer.toString(((Integer) obj2).intValue())
                            : obj2 instanceof String ? (String) obj2 : null;
        }
        if (str == null || str2 == null) {
            return null;
        }
        DexMetaDataInfo dexMetaDataInfo = new DexMetaDataInfo();
        dexMetaDataInfo.mWidthValue = DexController$Utils$TypedMetaDataValue.parseSizeMetaData(str);
        dexMetaDataInfo.mHeightValue =
                DexController$Utils$TypedMetaDataValue.parseSizeMetaData(str2);
        return dexMetaDataInfo;
    }

    public static String toHashText(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes(Charset.defaultCharset()));
            return Base64.encodeToString(messageDigest.digest(), 2);
        } catch (NoSuchAlgorithmException unused) {
            return null;
        }
    }

    public final void activateDexDisplayLocked(DisplayContent displayContent) {
        if (displayContent.mDisplayId != 2) {
            return;
        }
        StringBuilder sb = new StringBuilder("activateDexDisplayLocked: currentDisplayState=");
        VirtualDisplay virtualDisplay = this.mDexDisplay;
        sb.append(
                Display.stateToString(
                        virtualDisplay != null ? virtualDisplay.getDisplay().getState() : 0));
        sb.append(", mRequestedDexDisplayEnabled=");
        sb.append(this.mRequestedDexDisplayEnabled);
        sb.append("");
        Slog.i("DexController", sb.toString());
        if (this.mRequestedDexDisplayEnabled && updateDexDisplayStateLocked(true)) {
            displayContent.updateDisplayOverrideConfigurationLocked();
            notifyDexDisplayStateLocked(true);
            displayContent.mDisplayPolicy.notifyDisplayReady();
        }
    }

    public final boolean createDexDisplayLocked(int i, int i2, int i3) {
        VirtualDisplay createVirtualDisplay =
                DisplayManagerGlobal.getInstance()
                        .createVirtualDisplay(
                                this.mAtm.mContext,
                                (MediaProjection) null,
                                new VirtualDisplayConfig.Builder("Desktop", i, i2, i3)
                                        .setFlags(265225)
                                        .build(),
                                (VirtualDisplay.Callback) null,
                                (Executor) null);
        this.mDexDisplay = createVirtualDisplay;
        if (createVirtualDisplay == null) {
            Slog.w("DexController", "enableDexDisplay: Failed to create a display for DeX");
            return false;
        }
        Display display = createVirtualDisplay.getDisplay();
        if (display.getDisplayId() != 2) {
            return true;
        }
        int displayId = display.getDisplayId();
        Context createDisplayContext = this.mAtm.mContext.createDisplayContext(display);
        if (createDisplayContext == null) {
            return true;
        }
        this.mDisplayContexts.put(displayId, createDisplayContext);
        return true;
    }

    public final void deactivateDexDisplayLocked(DisplayContent displayContent) {
        if (displayContent.mDisplayId != 2) {
            return;
        }
        StringBuilder sb = new StringBuilder("deactivateDexDisplayLocked: currentDisplayState=");
        VirtualDisplay virtualDisplay = this.mDexDisplay;
        sb.append(
                Display.stateToString(
                        virtualDisplay != null ? virtualDisplay.getDisplay().getState() : 0));
        sb.append(", mRequestedDexDisplayEnabled=");
        sb.append(this.mRequestedDexDisplayEnabled);
        sb.append("");
        Slog.i("DexController", sb.toString());
        if (this.mRequestedDexDisplayEnabled || !updateDexDisplayStateLocked(false)) {
            return;
        }
        ActivityTaskManagerService activityTaskManagerService = this.mAtm;
        activityTaskManagerService.mMultiTaskingController.minimizeAllTasksLocked(2, false);
        activityTaskManagerService
                .mTaskSupervisor
                .mKeyguardController
                .getDisplayState(2)
                .mSleepTokenAcquirer
                .release(2);
        this.mWm.moveDisplayToTop(0, "deactivateDexDisplay");
        notifyDexDisplayStateLocked(false);
    }

    public final int disableDexDisplay() {
        int updateDexDisplayState;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                VirtualDisplay virtualDisplay = this.mDexDisplay;
                int state = virtualDisplay != null ? virtualDisplay.getDisplay().getState() : 0;
                updateDexDisplayState =
                        this.mWm.mDisplayManagerInternal.updateDexDisplayState(false);
                if (this.mRequestedDexDisplayEnabled) {
                    this.mRequestedDexDisplayEnabled = false;
                    Slog.i("DexController", "setRequestedDexDisplayEnabledLocked: false");
                }
                DisplayContent displayContent = this.mAtm.mRootWindowContainer.getDisplayContent(2);
                if (displayContent != null) {
                    if (this.mIsDexForceImmersiveModeEnabled) {
                        InsetsPolicy insetsPolicy = displayContent.mInsetsPolicy;
                        if (insetsPolicy.mShowingTransientTypes != 0) {
                            insetsPolicy.dispatchTransientSystemBarsVisibilityChanged(
                                    insetsPolicy.mFocusedWin, false, false);
                            insetsPolicy.mShowingTransientTypes = 0;
                            insetsPolicy.updateBarControlTarget(insetsPolicy.mFocusedWin);
                        }
                    }
                    if (state == 1) {
                        deactivateDexDisplayLocked(displayContent);
                        updateDexDisplayState = displayContent.mDisplayId;
                    }
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return updateDexDisplayState;
    }

    @Override // com.android.server.wm.IController
    public final void dumpLocked(PrintWriter printWriter) {
        StringBuilder m =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                        BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                                                printWriter,
                                                "[DexController]",
                                                "  mDexDisplayActivated="),
                                        this.mDexDisplayActivated,
                                        printWriter,
                                        "  mIsDexForceImmersiveModeEnabled="),
                                this.mIsDexForceImmersiveModeEnabled,
                                printWriter,
                                "  mIsInDexForceImmersiveMode="),
                        this.mIsInDexForceImmersiveMode,
                        printWriter,
                        "  mDexStandaloneRotationEnabled=");
        m.append(this.mDexStandaloneRotationEnabled);
        printWriter.println(m.toString());
        if (getDexModeLocked() == 2) {
            StringBuilder m2 =
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            new StringBuilder("  mDexImeWindowVisibleInDefaultDisplay="),
                            this.mDexImeWindowVisibleInDefaultDisplay,
                            printWriter,
                            "  mLastInputMethodInputTarget=");
            m2.append(this.mLastInputMethodInputTarget);
            printWriter.println(m2.toString());
        }
        printWriter.println();
    }

    public final int enableDexDisplay(int i, int i2, int i3) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (!this.mRequestedDexDisplayEnabled) {
                    this.mRequestedDexDisplayEnabled = true;
                    Slog.i("DexController", "setRequestedDexDisplayEnabledLocked: true");
                }
                if (this.mDexDisplay != null) {
                    DisplayInfo displayInfo = new DisplayInfo();
                    Display display = this.mDexDisplay.getDisplay();
                    DisplayContent displayContentOrCreate =
                            this.mAtm.mRootWindowContainer.getDisplayContentOrCreate(
                                    display.getDisplayId());
                    display.getDisplayInfo(displayInfo);
                    if (i != displayInfo.logicalWidth
                            || i2 != displayInfo.logicalHeight
                            || i3 != displayInfo.logicalDensityDpi) {
                        setDisplaySizeAndDensityLocked(i, i2, i3, displayContentOrCreate);
                    }
                } else if (!createDexDisplayLocked(i, i2, i3)) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return -1;
                }
                this.mWm.mDisplayManagerInternal.updateDexDisplayState(true);
                int displayId = this.mDexDisplay.getDisplay().getDisplayId();
                WindowManagerService.resetPriorityAfterLockedSection();
                return displayId;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final Point getDexMetadataLaunchSizeLocked(DexMetaDataInfo dexMetaDataInfo, int i) {
        DexController$Utils$TypedMetaDataValue dexController$Utils$TypedMetaDataValue;
        DexController$Utils$TypedMetaDataValue dexController$Utils$TypedMetaDataValue2;
        if (dexMetaDataInfo == null
                || (dexController$Utils$TypedMetaDataValue = dexMetaDataInfo.mWidthValue) == null
                || (dexController$Utils$TypedMetaDataValue2 = dexMetaDataInfo.mHeightValue)
                        == null) {
            return null;
        }
        if (dexController$Utils$TypedMetaDataValue.data == 0
                && dexController$Utils$TypedMetaDataValue2.data == 0) {
            return new Point(0, 0);
        }
        DisplayContent displayContent = this.mAtm.mRootWindowContainer.getDisplayContent(i);
        if (displayContent == null) {
            return null;
        }
        Configuration configuration = displayContent.getConfiguration();
        int dimensionPixelSize =
                DexController$Utils$TypedMetaDataValue.getDimensionPixelSize(
                        dexController$Utils$TypedMetaDataValue,
                        configuration.densityDpi,
                        this.mDexDisplaySize.x,
                        960);
        int dimensionPixelSize2 =
                DexController$Utils$TypedMetaDataValue.getDimensionPixelSize(
                        dexController$Utils$TypedMetaDataValue2,
                        configuration.densityDpi,
                        this.mDexDisplaySize.y,
                        720);
        Rect appBounds = configuration.windowConfiguration.getAppBounds();
        Point point = new Point();
        point.x = Math.min(appBounds.width(), dimensionPixelSize);
        point.y = Math.min(appBounds.height(), dimensionPixelSize2);
        return point;
    }

    public final int getDexModeLocked() {
        if (this.mDexDisplayActivated) {
            return 2;
        }
        ActivityTaskManagerService activityTaskManagerService = this.mAtm;
        if (activityTaskManagerService.getGlobalConfiguration().semDesktopModeEnabled == 1) {
            return activityTaskManagerService.getGlobalConfiguration().dexMode;
        }
        return 0;
    }

    public final int getDexPolicyFlags(ActivityInfo activityInfo, ApplicationInfo applicationInfo) {
        Bundle bundle;
        Bundle bundle2 = applicationInfo.metaData;
        if (bundle2 == null) {
            try {
                this.mAtm
                        .mContext
                        .getPackageManager()
                        .getApplicationInfo(
                                applicationInfo.packageName,
                                PackageManager.ApplicationInfoFlags.of(128L));
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        if (bundle2 != null
                && bundle2.getBoolean("com.samsung.android.dex.launchpolicy.notsupported")) {
            return 2;
        }
        synchronized (this.mSCPMLaunchBlockList) {
            try {
                if (this.mSCPMLaunchBlockList.contains(applicationInfo.packageName)) {
                    return 2;
                }
                String str = applicationInfo.packageName;
                int i = 0;
                if (str != null
                        && !DEFAULT_ALLOW_HOME_SET.contains(toHashText(str))
                        && (activityInfo == null
                                || (bundle = activityInfo.metaData) == null
                                || !bundle.getBoolean(
                                        "com.samsung.android.dex.launchpolicy.allow_home_activity",
                                        false))) {
                    if (this.mAtm
                                    .mContext
                                    .getPackageManager()
                                    .resolveActivityAsUser(
                                            new Intent("android.intent.action.MAIN")
                                                    .addCategory("android.intent.category.HOME")
                                                    .setPackage(applicationInfo.packageName),
                                            PackageManager.ResolveInfoFlags.of(65536L),
                                            UserHandle.getUserId(applicationInfo.uid))
                            != null) {
                        i = 4;
                    }
                }
                return isGameApp(applicationInfo) ? i | 8 : i;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final ActivityRecord getNonStartableActivityInDexMode(Task task) {
        for (int childCount = task.getChildCount() - 1; childCount >= 0; childCount--) {
            ActivityRecord asActivityRecord = task.getChildAt(childCount).asActivityRecord();
            if (asActivityRecord != null) {
                ActivityInfo activityInfo = asActivityRecord.info;
                if ((getDexPolicyFlags(activityInfo, activityInfo.applicationInfo) & 6) != 0) {
                    return asActivityRecord;
                }
            }
        }
        return null;
    }

    public final ArrayList getTaskLocked(final int i, int i2, final String str, boolean z) {
        ActivityRecord activity;
        ArrayList arrayList = new ArrayList();
        if (str != null) {
            ActivityTaskManagerService activityTaskManagerService = this.mAtm;
            for (int childCount =
                            activityTaskManagerService.mRootWindowContainer.getChildCount() - 1;
                    childCount >= 0;
                    childCount--) {
                DisplayContent displayContent =
                        (DisplayContent)
                                activityTaskManagerService.mRootWindowContainer.getChildAt(
                                        childCount);
                if (displayContent.mDisplayId != i2) {
                    final ArrayList arrayList2 = new ArrayList();
                    displayContent.forAllTasks(
                            new Consumer() { // from class:
                                             // com.android.server.wm.DexController$$ExternalSyntheticLambda0
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    ArrayList arrayList3 = arrayList2;
                                    Task task = (Task) obj;
                                    if (task.isActivityTypeHomeOrRecents()
                                            || !task.isLeafTask()
                                            || task.getTopActivity(false, true) == null) {
                                        return;
                                    }
                                    arrayList3.add(task);
                                }
                            });
                    for (int size = arrayList2.size() - 1; size >= 0; size--) {
                        Task task = (Task) arrayList2.get(size);
                        if ((!z || task.getTopActivity(false, true).mVisible)
                                && (activity =
                                                task.getActivity(
                                                        new Predicate() { // from class:
                                                                          // com.android.server.wm.DexController$$ExternalSyntheticLambda1
                                                            @Override // java.util.function.Predicate
                                                            public final boolean test(Object obj) {
                                                                ActivityRecord activityRecord =
                                                                        (ActivityRecord) obj;
                                                                return str.equals(
                                                                                activityRecord
                                                                                        .processName)
                                                                        && activityRecord.getUid()
                                                                                == i;
                                                            }
                                                        },
                                                        false))
                                        != null) {
                            arrayList.add(new FindTaskResult(activity, i2));
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    public final boolean hideDexImeOnDefaultDisplayLocked() {
        boolean z;
        DisplayContent defaultDisplayContentLocked =
                this.mAtm.mWindowManager.getDefaultDisplayContentLocked();
        defaultDisplayContentLocked
                .mInsetsStateController
                .getImeSourceProvider()
                .abortShowImePostLayout();
        InsetsControlTarget imeTarget = defaultDisplayContentLocked.getImeTarget(2);
        if (imeTarget != null) {
            z = true;
            imeTarget.hideInsets(WindowInsets.Type.ime(), true, null);
        } else {
            z = false;
        }
        defaultDisplayContentLocked.mInsetsStateController.getImeSourceProvider().mImeShowing =
                false;
        return z;
    }

    @Override // com.android.server.wm.IController
    public final void initialize() {
        this.mH = new H(this.mAtm.mH.getLooper());
    }

    public final boolean isGameApp(ApplicationInfo applicationInfo) {
        final long elapsedRealtime = SystemClock.elapsedRealtime();
        this.mGameAppsMap
                .values()
                .removeIf(
                        new Predicate() { // from class:
                                          // com.android.server.wm.DexController$$ExternalSyntheticLambda2
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                return elapsedRealtime - ((Long) ((Pair) obj).second).longValue()
                                        > 10000;
                            }
                        });
        Pair pair = (Pair) this.mGameAppsMap.get(applicationInfo.packageName);
        if (pair != null) {
            if (CoreRune.IS_DEBUG_LEVEL_MID) {
                BootReceiver$$ExternalSyntheticOutline0.m(
                        new StringBuilder("isGameApp : hit= "),
                        applicationInfo.packageName,
                        "DexController");
            }
            return ((Boolean) pair.first).booleanValue();
        }
        try {
            boolean isGamePackage = SemGameManager.isGamePackage(applicationInfo.packageName);
            this.mGameAppsMap.put(
                    applicationInfo.packageName,
                    new Pair(Boolean.valueOf(isGamePackage), Long.valueOf(elapsedRealtime)));
            if (CoreRune.IS_DEBUG_LEVEL_MID) {
                Slog.d("DexController", "isGameApp : put=" + applicationInfo.packageName);
            }
            return isGamePackage;
        } catch (Exception unused) {
            return false;
        }
    }

    public final void moveTaskToDefaultDisplayAndLayoutTask(
            ActivityOptions activityOptions,
            ActivityRecord activityRecord,
            ActivityRecord activityRecord2,
            Task task) {
        task.mSkipSavingLaunchingState = true;
        if (task.mLastNonFullscreenBounds == null) {
            task.mLastNonFullscreenBounds = new Rect();
        }
        moveTaskToDisplayBackLocked(task, 0, "dex_disabled", activityOptions);
        task.mLastNonFullscreenBounds = null;
        ActivityInfo activityInfo = activityRecord.info;
        ActivityInfo.WindowLayout windowLayout =
                activityInfo != null ? activityInfo.windowLayout : null;
        if (activityOptions == null) {
            activityOptions = ActivityOptions.makeBasic();
        }
        ActivityOptions activityOptions2 = activityOptions;
        activityOptions2.setLaunchDisplayId(0);
        this.mAtm.mTaskSupervisor.mLaunchParamsController.layoutTask(
                task, windowLayout, activityRecord, activityRecord2, activityOptions2, 0);
        task.mSkipSavingLaunchingState = false;
    }

    public final void moveTaskToDisplayBackLocked(
            Task task, int i, String str, ActivityOptions activityOptions) {
        if (task.getDisplayId() == i) {
            return;
        }
        ActivityTaskManagerService activityTaskManagerService = this.mAtm;
        DisplayContent displayContent =
                activityTaskManagerService.mRootWindowContainer.getDisplayContent(i);
        if (displayContent == null
                || activityTaskManagerService.mRootWindowContainer.getRootTask(
                                task.getRootTask().mTaskId)
                        == null) {
            return;
        }
        Task orCreateRootTask =
                displayContent
                        .getDefaultTaskDisplayArea()
                        .getOrCreateRootTask(
                                task.getTopActivity(false, true),
                                activityOptions != null
                                        ? activityOptions
                                        : ActivityOptions.makeBasic(),
                                task,
                                null,
                                null,
                                0,
                                task.getActivityType(),
                                false);
        activityTaskManagerService.mMultiTaskingAppCompatController.mSizeCompatModePolicy
                .getClass();
        task.forAllActivities(
                new MultiTaskingAppCompatSizeCompatModePolicy$$ExternalSyntheticLambda0(
                        false, true));
        if (orCreateRootTask.isOrganized() && orCreateRootTask != task) {
            task.getRequestedOverrideConfiguration().windowConfiguration.setBounds((Rect) null);
        }
        task.reparent(orCreateRootTask, false, 2, false, true, str);
        if (task.getDisplayId() == i) {
            return;
        }
        orCreateRootTask.removeImmediately();
    }

    public final void notifyAppTransitionFinished() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                Slog.d(
                        "DexController",
                        "notifyAppTransitionFinished. isTransitionFinished="
                                + this.mPendingActivityInfo.mWaitingTransitionFinishedTokens
                                        .isEmpty());
                if (!this.mPendingActivityInfo.mWaitingTransitionFinishedTokens.isEmpty()) {
                    this.mPendingActivityInfo.mWaitingTransitionFinishedTokens.clear();
                    this.mH.removeMessages(3);
                    if (this.mPendingActivityInfo.mWaitingStoppedTasks.isEmpty()
                            && this.mPendingActivityInfo.mOrganizedTaskFragments.size() <= 0) {
                        scheduleReparentToDisplayAndStartPendingActivity(true);
                    }
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void notifyDexDisplayStateLocked(boolean z) {
        DesktopModeManagerInternal desktopModeManagerInternal =
                (DesktopModeManagerInternal)
                        LocalServices.getService(DesktopModeManagerInternal.class);
        if (desktopModeManagerInternal == null) {
            Slog.w("DexController", "notifyDexDisplayStateLocked: failed, dexService is null");
        } else if (this.mLastReportedDexDisplayState != z) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    "notifyDexDisplayStateLocked: dexDisplayEnabled=", "DexController", z);
            this.mLastReportedDexDisplayState = z;
            desktopModeManagerInternal.onDesktopDisplayConfigured(z);
        }
    }

    public final void restoreToggleTasksToFrontLocked(int i) {
        ArrayList arrayList = new ArrayList(this.mMinimizedToggleTasks);
        TransitionController transitionController =
                this.mAtm.mWindowOrganizerController.mTransitionController;
        boolean z = transitionController.mCollectingTransition == null;
        ((ArrayList) this.mMinimizedToggleTasks).clear();
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            Task task = (Task) arrayList.get(size);
            Task rootTask = task.getRootTask();
            if (rootTask != null && task.getDisplayId() == i) {
                if (z) {
                    transitionController.requestStartTransition(
                            transitionController.createTransition(1, 0), null, null, null);
                    z = false;
                }
                ActivityRecord topNonFinishingActivity =
                        task.getTopNonFinishingActivity(true, true);
                rootTask.moveTaskToFront(
                        task,
                        false,
                        null,
                        topNonFinishingActivity != null
                                ? topNonFinishingActivity.appTimeTracker
                                : null,
                        false,
                        "restoreToggleTasksToFrontLocked");
            }
        }
    }

    public final void scheduleReparentToDisplayAndStartPendingActivity(
            DexRestartAppInfo dexRestartAppInfo, int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ArrayList taskLocked =
                        getTaskLocked(
                                dexRestartAppInfo.mUid, i, dexRestartAppInfo.mProcessName, false);
                this.mPendingActivityInfo.reset();
                if (!taskLocked.isEmpty()) {
                    this.mPendingActivityInfo.set(dexRestartAppInfo, taskLocked, i);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        scheduleReparentToDisplayAndStartPendingActivity(true);
    }

    public final void scheduleReparentToDisplayAndStartPendingActivity(boolean z) {
        this.mH.removeMessages(2);
        if (z) {
            H h = this.mH;
            h.sendMessage(h.obtainMessage(2));
        } else {
            H h2 = this.mH;
            h2.sendMessageDelayed(h2.obtainMessage(2), 5000L);
        }
    }

    public final boolean setDexImeWindowStateLocked(boolean z) {
        if (this.mDexImeWindowVisibleInDefaultDisplay == z) {
            return false;
        }
        this.mDexImeWindowVisibleInDefaultDisplay = z;
        if (CoreRune.IS_DEBUG_LEVEL_MID) {
            StringBuilder m =
                    BatteryService$$ExternalSyntheticOutline0.m(
                            "setDexImeWindowStateLocked: ", " Callers=", z);
            m.append(Debug.getCallers(3));
            Slog.i("DexController", m.toString());
        }
        this.mAtm.mWindowManager.mInputManager.mDexImeWindowVisibleInDefaultDisplay = z;
        return true;
    }

    public final void setDisplaySizeAndDensityLocked(
            int i, int i2, int i3, DisplayContent displayContent) {
        Transition.ChangeInfo changeInfo;
        int i4 = displayContent.mDisplayId;
        if (i4 != 0 && i4 != 2) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    i4, "setDisplaySizeAndDensityLocked: failed, invalid id #", "DexController");
            return;
        }
        StringBuilder m =
                ArrayUtils$$ExternalSyntheticOutline0.m(
                        i4, i, "setDisplaySizeAndDensityLocked: #", ", ", "x");
        ServiceKeeper$$ExternalSyntheticOutline0.m(i2, i3, ", ", "dpi, Callers=", m);
        ActivityManagerService$$ExternalSyntheticOutline0.m(4, m, "DexController");
        int i5 = displayContent.mBaseDisplayDensity;
        boolean z = i5 != i3;
        int i6 = displayContent.mBaseDisplayWidth;
        ActivityTaskManagerService activityTaskManagerService = this.mAtm;
        if (i6 == i && displayContent.mBaseDisplayHeight == i2 && i5 == i3) {
            displayContent.sendNewConfiguration();
        } else if (i4 == 2) {
            displayContent.setForcedSizeDensity(i, i2, i3, false, -1, false);
            Transition transition =
                    activityTaskManagerService
                            .mWindowOrganizerController
                            .mTransitionController
                            .mCollectingTransition;
            if (transition != null
                    && (changeInfo =
                                    (Transition.ChangeInfo) transition.mChanges.get(displayContent))
                            != null) {
                changeInfo.mFlags |= EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT;
            }
        } else {
            displayContent.setForcedSizeDensity(i, i2, i3, false, -1, false);
        }
        displayContent.reconfigureDisplayLocked();
        if (z) {
            activityTaskManagerService.mWindowManager.mInputManager.reloadPointerIcons();
        }
    }

    public final void setTasksToDisplayLocked(int i, int i2) {
        ActivityTaskManagerService activityTaskManagerService = this.mAtm;
        if (activityTaskManagerService.mRootWindowContainer.getDisplayContent(i) == null) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    i, "moveTasksToDisplayLocked: no source display #", "DexController");
            return;
        }
        if (activityTaskManagerService.mRootWindowContainer.getDisplayContent(i2) == null) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    i2, "moveTasksToDisplayLocked: no target display #", "DexController");
        } else if (i2 == 2 && !this.mDexDisplayActivated) {
            Slog.w("DexController", "moveTasksToDisplayLocked: no dex dual mode");
        } else {
            this.mSourceDisplayId = i;
            this.mTargetDisplayId = i2;
        }
    }

    public final void setWaitingTransitionFinished(ArrayList arrayList) {
        this.mWaitingTransitionFinishedTokens.clear();
        if (arrayList == null) {
            return;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked((IBinder) it.next());
            if (forTokenLocked != null) {
                this.mWaitingTransitionFinishedTokens.add(forTokenLocked);
            }
        }
    }

    @Override // com.android.server.wm.IController
    public final void setWindowManager(WindowManagerService windowManagerService) {
        this.mWm = windowManagerService;
    }

    public final boolean shouldShowDexImeInDefaultDisplayLocked() {
        WindowState windowState;
        DisplayContent defaultDisplayContentLocked;
        boolean z =
                (getDexModeLocked() != 2
                                || (windowState = this.mLastInputMethodInputTarget) == null
                                || windowState.getDisplayId() != 2
                                || (defaultDisplayContentLocked =
                                                this.mAtm.mWindowManager
                                                        .getDefaultDisplayContentLocked())
                                        == null
                                || defaultDisplayContentLocked.mInputMethodWindow == null)
                        ? false
                        : true;
        updateDexImeWindowStateIfNeededLocked();
        return z;
    }

    public final boolean showDexImeOnDefaultDisplayLocked() {
        ActivityTaskManagerService activityTaskManagerService = this.mAtm;
        DisplayContent defaultDisplayContentLocked =
                activityTaskManagerService.mWindowManager.getDefaultDisplayContentLocked();
        InputTarget inputTarget = defaultDisplayContentLocked.mImeInputTarget;
        WindowState windowState =
                inputTarget != null
                        ? inputTarget.getWindowState()
                        : defaultDisplayContentLocked.getImeFallback() != null
                                ? defaultDisplayContentLocked.getImeFallback().getWindow()
                                : null;
        if (windowState == null) {
            return false;
        }
        DisplayContent defaultDisplayContentLocked2 =
                activityTaskManagerService.mWindowManager.getDefaultDisplayContentLocked();
        WindowState window = windowState.getImeControlTarget().getWindow();
        WindowState windowState2 = defaultDisplayContentLocked2.mInputMethodWindow;
        defaultDisplayContentLocked2
                .mInsetsStateController
                .getImeSourceProvider()
                .scheduleShowImePostLayout(window, null);
        if (windowState2 == null || windowState2.isVisible() || !windowState2.isDrawn()) {
            return true;
        }
        windowState2.mWinAnimator.resetDrawState();
        windowState2.forceReportingResized();
        return true;
    }

    public final void showWarningToastIfNeeded(ActivityInfo activityInfo, Task task) {
        String string;
        ActivityRecord activityRecord;
        if (task == null
                || (activityRecord = task.topRunningActivity(false)) == null
                || !activityRecord.isState(ActivityRecord.State.RESUMED)
                || !isGameApp(activityInfo.applicationInfo)) {
            int dexPolicyFlags = getDexPolicyFlags(activityInfo, activityInfo.applicationInfo);
            ActivityTaskManagerService activityTaskManagerService = this.mAtm;
            Resources resources = activityTaskManagerService.mContext.getResources();
            if ((dexPolicyFlags & 2) != 0) {
                CharSequence loadLabel =
                        activityInfo.loadLabel(this.mAtm.mContext.getPackageManager());
                string =
                        resources.getString(
                                R.string.roamingText10,
                                loadLabel != null ? loadLabel.toString() : "");
            } else if ((dexPolicyFlags & 4) != 0) {
                CharSequence loadLabel2 =
                        activityInfo.loadLabel(this.mAtm.mContext.getPackageManager());
                string =
                        resources.getString(
                                R.string.roamingText11,
                                loadLabel2 != null ? loadLabel2.toString() : "");
            } else {
                string =
                        (dexPolicyFlags & 8) != 0
                                ? resources.getString(R.string.roamingText12)
                                : null;
            }
            if (string == null) {
                return;
            }
            this.mH.post(
                    new DexController$$ExternalSyntheticLambda5(
                            new ContextThemeWrapper(
                                    activityTaskManagerService.mContext,
                                    R.style.Theme.DeviceDefault.Light),
                            string));
        }
    }

    public final boolean updateDexDisplayStateLocked(boolean z) {
        VirtualDisplay virtualDisplay;
        if (this.mDexDisplayActivated == z) {
            return false;
        }
        Slog.i("DexController", "updateDexDisplayStateLocked: " + z);
        this.mDexDisplayActivated = z;
        if (z && (virtualDisplay = this.mDexDisplay) != null) {
            virtualDisplay.getDisplay().getRealSize(this.mDexDisplaySize);
        }
        updateDexModeIfNeededLocked();
        ActivityTaskManagerService.SleepTokenAcquirerImpl sleepTokenAcquirerImpl =
                this.mDeactivateDexSleepTokenAcquirer;
        if (z) {
            sleepTokenAcquirerImpl.release(2);
            Slog.i("DexController", "updateSleepTokenLocked: sleepToken is released");
            return true;
        }
        sleepTokenAcquirerImpl.acquire(2, false);
        Slog.i("DexController", "updateSleepTokenLocked: sleepToken is acquired");
        return true;
    }

    public final void updateDexFontScaleIfNeeded(float f) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                int dexModeLocked = getDexModeLocked();
                boolean z = dexModeLocked == 2;
                if (this.mDexFontScale != f || (z && !this.mUpdatedFontScaleForDexDual)) {
                    this.mDexFontScale = f;
                    int i = z ? 2 : 0;
                    if (dexModeLocked != 0 && dexModeLocked != 3) {
                        this.mUpdatedFontScaleForDexDual = z;
                        if (i == 2) {
                            this.mAtm.mExt.mCoreStateController.setVolatileState(
                                    "dex_font_scale", Float.valueOf(f), 0, true, true, null);
                            this.mAtm
                                    .mRootWindowContainer
                                    .getDisplayContent(2)
                                    .reconfigureDisplayLocked();
                        } else {
                            Configuration computeNewConfiguration =
                                    this.mAtm.mWindowManager.computeNewConfiguration(i);
                            if (computeNewConfiguration == null) {
                                WindowManagerService.resetPriorityAfterLockedSection();
                                return;
                            }
                            computeNewConfiguration.fontScale = f;
                            if (dexModeLocked == 1) {
                                WindowManagerService windowManagerService =
                                        this.mAtm.mWindowManager;
                                windowManagerService.startFreezingDisplay(
                                        0,
                                        0,
                                        -1,
                                        windowManagerService.getDefaultDisplayContentLocked());
                            }
                            this.mAtm.updateConfigurationLocked(
                                    computeNewConfiguration, false, false, -10000);
                        }
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                Slog.d(
                        "DexController",
                        "updateDexFontScaleIfNeeded: DexFontScale is same as scaleFactor " + f);
                WindowManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final boolean updateDexImeWindowStateIfNeededLocked() {
        WindowState windowState;
        DisplayContent defaultDisplayContentLocked;
        WindowState windowState2;
        return setDexImeWindowStateLocked(
                getDexModeLocked() == 2
                        && (windowState = this.mLastInputMethodInputTarget) != null
                        && windowState.getDisplayId() == 2
                        && (defaultDisplayContentLocked =
                                        this.mAtm.mWindowManager.getDefaultDisplayContentLocked())
                                != null
                        && (windowState2 = defaultDisplayContentLocked.mInputMethodWindow) != null
                        && windowState2.isVisible());
    }

    public final void updateDexModeIfNeededLocked() {
        int dexModeLocked = getDexModeLocked();
        if (this.mLastDexMode != dexModeLocked) {
            if (dexModeLocked == 0 || dexModeLocked == 2) {
                FreeformController freeformController = this.mAtm.mFreeformController;
                Message obtainMessage = freeformController.mH.obtainMessage(101);
                obtainMessage.obj = "dex_off";
                freeformController.mH.sendMessage(obtainMessage);
                if (CoreRune.MW_FREEFORM_SMART_POPUP_VIEW) {
                    Message obtainMessage2 = freeformController.mH.obtainMessage(103);
                    obtainMessage2.obj = "dex_off";
                    freeformController.mH.sendMessage(obtainMessage2);
                }
            } else {
                FreeformController freeformController2 = this.mAtm.mFreeformController;
                freeformController2.scheduleUnbindMinimizeContainerService("dex_on");
                if (CoreRune.MW_FREEFORM_SMART_POPUP_VIEW) {
                    Message obtainMessage3 = freeformController2.mH.obtainMessage(104);
                    obtainMessage3.obj = "dex_on";
                    freeformController2.mH.sendMessage(obtainMessage3);
                }
            }
            if (dexModeLocked != 0) {
                this.mH.post(new DexController$$ExternalSyntheticLambda3(this, 0));
            }
            boolean z = dexModeLocked != 0;
            MultiWindowEnableController multiWindowEnableController =
                    this.mAtm.mMultiWindowEnableController;
            String str = z ? "Desktop On" : "Desktop Off";
            WindowManagerGlobalLock windowManagerGlobalLock =
                    multiWindowEnableController.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    multiWindowEnableController.setForceEnableForUser(str, z);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            ((ArrayList) this.mMinimizedToggleTasks).clear();
            try {
                this.mAtm.deferWindowLayout();
                DesktopModeManagerInternal desktopModeManagerInternal =
                        (DesktopModeManagerInternal)
                                LocalServices.getService(DesktopModeManagerInternal.class);
                int i = this.mLastDexMode;
                if (i == 2 && dexModeLocked == 1) {
                    setTasksToDisplayLocked(2, 0);
                } else if ((i == 1 && dexModeLocked == 2)
                        || desktopModeManagerInternal.getModeToModeChangeType() == 2) {
                    setTasksToDisplayLocked(0, 2);
                }
                DisplayContent displayContent = this.mAtm.mRootWindowContainer.getDisplayContent(0);
                int i2 = this.mLastDexMode;
                ActivityRecord activityRecord = null;
                if (i2 == 1 && dexModeLocked == 0) {
                    displayContent.forAllRootTasks(new DexController$$ExternalSyntheticLambda7());
                } else if (i2 == 0 && dexModeLocked == 1) {
                    if (displayContent.getDefaultTaskDisplayArea().hasPinnedTask()) {
                        this.mAtm.mTaskSupervisor.removeRootTask(
                                displayContent.getDefaultTaskDisplayArea().mRootPinnedTask);
                    }
                    if (displayContent.getDefaultTaskDisplayArea().isSplitScreenModeActivated()) {
                        displayContent
                                .getDefaultTaskDisplayArea()
                                .onStageSplitScreenDismissed(null, true);
                    }
                    displayContent.forAllTasks(
                            new DexController$$ExternalSyntheticLambda4(1, this, displayContent));
                }
                if (dexModeLocked == 1) {
                    TaskDisplayArea defaultTaskDisplayArea =
                            this.mAtm
                                    .mRootWindowContainer
                                    .getDisplayContent(0)
                                    .getDefaultTaskDisplayArea();
                    int i3 = defaultTaskDisplayArea.mRootWindowContainer.mCurrentUser;
                    Task task = defaultTaskDisplayArea.mRootHomeTask;
                    if (task != null) {
                        PooledPredicate obtainPredicate =
                                PooledLambda.obtainPredicate(
                                        new TaskDisplayArea$$ExternalSyntheticLambda2(),
                                        PooledLambda.__(ActivityRecord.class),
                                        Integer.valueOf(i3));
                        activityRecord = task.getActivity(obtainPredicate);
                        obtainPredicate.recycle();
                    }
                    if (activityRecord == null) {
                        defaultTaskDisplayArea.moveHomeRootTaskToFront("dex standalone activated");
                    } else {
                        activityRecord.moveFocusableActivityToTop("dex standalone activated");
                    }
                } else if (dexModeLocked == 2) {
                    DisplayContent displayContent2 =
                            this.mAtm.mRootWindowContainer.getDisplayContent(2);
                    if (displayContent2 != null) {
                        TaskDisplayArea defaultTaskDisplayArea2 =
                                displayContent2.getDefaultTaskDisplayArea();
                        int i4 = defaultTaskDisplayArea2.mRootWindowContainer.mCurrentUser;
                        Task task2 = defaultTaskDisplayArea2.mRootHomeTask;
                        if (task2 != null) {
                            PooledPredicate obtainPredicate2 =
                                    PooledLambda.obtainPredicate(
                                            new TaskDisplayArea$$ExternalSyntheticLambda2(),
                                            PooledLambda.__(ActivityRecord.class),
                                            Integer.valueOf(i4));
                            activityRecord = task2.getActivity(obtainPredicate2);
                            obtainPredicate2.recycle();
                        }
                        if (activityRecord == null) {
                            defaultTaskDisplayArea2.moveHomeRootTaskToFront("dex dual activated");
                        } else {
                            activityRecord.moveFocusableActivityToTop("dex dual activated");
                        }
                    } else {
                        Slog.w("DexController", "updateDexModeIfNeededLocked() dexDc is null");
                    }
                }
                this.mAtm.continueWindowLayout();
                this.mAtm.mWindowManager.mExt.mPolicyExt.onDexModeChangedLw(dexModeLocked);
                if (this.mLastDexMode == 2) {
                    setDexImeWindowStateLocked(false);
                }
                this.mAtm.mAmInternal.setCurrentDexMode(dexModeLocked);
                this.mLastDexMode = dexModeLocked;
            } catch (Throwable th2) {
                this.mAtm.continueWindowLayout();
                throw th2;
            }
        }
    }

    public final void updateDexStarShowingDelayTime(final int i) {
        if (i != this.mDexStarShowingDelayTime) {
            this.mDexStarShowingDelayTime = i;
            synchronized (this.mDexTransientCaptionDelayCallbacks) {
                this.mDexTransientCaptionDelayCallbacks.broadcast(
                        new Consumer() { // from class:
                                         // com.android.server.wm.DexController$$ExternalSyntheticLambda9
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                try {
                                    ((IDexTransientCaptionDelayListener) obj).onDelayChanged(i);
                                } catch (RemoteException e) {
                                    Slog.e("DexController", "updateDexStarShowingDelayTime. " + e);
                                }
                            }
                        });
            }
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    new StringBuilder("update: mDexStarShowingDelayTime="),
                    this.mDexStarShowingDelayTime,
                    "DexController");
        }
    }

    public final void updateForceImmersiveModeSetting(boolean z) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (z != this.mIsDexForceImmersiveModeEnabled) {
                    this.mIsDexForceImmersiveModeEnabled = z;
                    Slog.d(
                            "DexController",
                            "updateForceImmersiveModeSetting: mIsDexForceImmersiveModeEnabled="
                                    + this.mIsDexForceImmersiveModeEnabled);
                    this.mH.post(new DexController$$ExternalSyntheticLambda3(this, 1));
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void updateForceImmersiveModeState(boolean z) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (z != this.mIsInDexForceImmersiveMode) {
                    this.mIsInDexForceImmersiveMode = z;
                    Slog.d(
                            "DexController",
                            "updateForceImmersiveModeSkip: mIsInDexForceImmersiveMode="
                                    + this.mIsInDexForceImmersiveMode);
                    this.mH.post(new DexController$$ExternalSyntheticLambda3(this, 2));
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }
}
