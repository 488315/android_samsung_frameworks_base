package com.android.server.wm;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.IActivityClientController;
import android.app.IRequestFinishCallback;
import android.app.PictureInPictureParams;
import android.app.PictureInPictureUiState;
import android.app.compat.CompatChanges;
import android.app.servertransaction.EnterPipRequestedItem;
import android.app.servertransaction.PipStateTransactionItem;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManagerInternal;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.Parcel;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.service.voice.VoiceInteractionManagerInternal;
import android.util.EventLog;
import android.util.Log;
import android.util.Slog;
import android.view.RemoteAnimationDefinition;
import android.window.SizeConfigurationBuckets;
import android.window.TransitionInfo;

import com.android.internal.app.AssistUtils;
import com.android.internal.app.IVoiceInteractionSessionShowCallback;
import com.android.internal.policy.IKeyguardDismissCallback;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService;
import com.android.server.LocalServices;
import com.android.server.SystemUpdateManagerService$$ExternalSyntheticOutline0;
import com.android.server.Watchdog;
import com.android.server.am.ActivityManagerService;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.uri.GrantUri;
import com.android.server.uri.NeededUriGrants;
import com.android.server.uri.UriGrantsManagerService;
import com.android.server.utils.quota.Categorizer;
import com.android.server.utils.quota.Category;
import com.android.server.utils.quota.CountQuotaTracker;
import com.android.server.vr.VrManagerService;
import com.android.window.flags.Flags;

import com.samsung.android.feature.SemGateConfig;
import com.samsung.android.knox.ISemPersonaManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.rune.CoreRune;

import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ActivityClientController extends IActivityClientController.Stub {
    public AssistUtils mAssistUtils;
    public final Context mContext;
    public final WindowManagerGlobalLock mGlobalLock;
    public final ActivityTaskManagerService mService;
    CountQuotaTracker mSetPipAspectRatioQuotaTracker;
    public final ActivityTaskSupervisor mTaskSupervisor;

    public ActivityClientController(ActivityTaskManagerService activityTaskManagerService) {
        this.mService = activityTaskManagerService;
        this.mGlobalLock = activityTaskManagerService.mGlobalLock;
        this.mTaskSupervisor = activityTaskManagerService.mTaskSupervisor;
        this.mContext = activityTaskManagerService.mContext;
    }

    public static boolean canGetLaunchedFromLocked(
            int i, ActivityRecord activityRecord, IBinder iBinder, boolean z) {
        boolean z2;
        int i2;
        if (!CompatChanges.isChangeEnabled(259743961L, i)) {
            return false;
        }
        if (z) {
            ActivityCallerState.CallerInfo callerInfo =
                    (ActivityCallerState.CallerInfo)
                            activityRecord.mCallerState.mCallerTokenInfoMap.getOrDefault(
                                    iBinder, null);
            z2 = callerInfo != null ? callerInfo.mIsShareIdentityEnabled : false;
        } else {
            z2 = activityRecord.mShareIdentity;
        }
        if (z) {
            ActivityCallerState.CallerInfo callerInfo2 =
                    (ActivityCallerState.CallerInfo)
                            activityRecord.mCallerState.mCallerTokenInfoMap.getOrDefault(
                                    iBinder, null);
            i2 = callerInfo2 != null ? callerInfo2.mUid : -1;
        } else {
            i2 = activityRecord.launchedFromUid;
        }
        return z2 || i2 == i;
    }

    public static void executeMultiWindowFullscreenRequest(int i, Task task) {
        int i2;
        if (i == 1) {
            int requestedOverrideWindowingMode = task.getRequestedOverrideWindowingMode();
            task.setWindowingMode(1);
            task.mMultiWindowRestoreWindowingMode = requestedOverrideWindowingMode;
            task.mMultiWindowRestoreParent = task.getParent().mRemoteToken.toWindowContainerToken();
            i2 = 1;
        } else {
            i2 = task.mMultiWindowRestoreWindowingMode;
            if (i2 != -1) {
                if (!task.getParent()
                        .mRemoteToken
                        .toWindowContainerToken()
                        .equals(task.mMultiWindowRestoreParent)) {
                    task.reparent(
                            Task.fromWindowContainerToken(task.mMultiWindowRestoreParent),
                            Integer.MAX_VALUE);
                }
                task.setWindowingMode(task.mMultiWindowRestoreWindowingMode);
            }
        }
        if (i2 == 1) {
            task.setBounds(null);
        }
    }

    public static boolean shouldMoveTaskToBack(
            ActivityRecord activityRecord, ActivityRecord activityRecord2) {
        if (activityRecord != activityRecord2) {
            TaskFragment taskFragment = activityRecord.getTaskFragment();
            if (activityRecord
                            != taskFragment.getActivity(
                                    new ActivityClientController$$ExternalSyntheticLambda0(
                                            0, activityRecord),
                                    false)
                    || activityRecord2.getTaskFragment().mCompanionTaskFragment != taskFragment) {
                return false;
            }
        }
        Intent intent =
                activityRecord2.mActivityComponent.equals(activityRecord.task.realActivity)
                        ? activityRecord2.intent
                        : null;
        return intent != null
                && activityRecord.getTaskFragment().topRunningActivity(false) == activityRecord
                && activityRecord2.mLaunchSourceType == 2
                && ActivityRecord.isMainIntent(intent);
    }

    public static int validateMultiwindowFullscreenRequestLocked(
            int i, ActivityRecord activityRecord, Task task) {
        if (activityRecord.getWindowingMode() == 2) {
            return 0;
        }
        if (activityRecord != task.getTopMostActivity()) {
            return 2;
        }
        return (i != 0
                        || (task.getWindowingMode() == 1
                                && task.mMultiWindowRestoreWindowingMode != -1))
                ? 0
                : 1;
    }

    public final void activityDestroyed(IBinder iBinder) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                Trace.traceBegin(32L, "activityDestroyed");
                try {
                    ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(iBinder);
                    if (forTokenLocked != null) {
                        forTokenLocked.destroyed("activityDestroyed");
                    }
                } finally {
                    Trace.traceEnd(32L);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void activityIdle(IBinder iBinder, Configuration configuration, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    Trace.traceBegin(32L, "activityIdle");
                    ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(iBinder);
                    if (forTokenLocked == null) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    int i = forTokenLocked.mUserId;
                    int identityHashCode = System.identityHashCode(forTokenLocked);
                    EventLog.writeEvent(
                            1000203,
                            Integer.valueOf(i),
                            Integer.valueOf(identityHashCode),
                            forTokenLocked.shortComponentName);
                    this.mTaskSupervisor.activityIdleInternal(
                            forTokenLocked, false, false, configuration);
                    if (z && forTokenLocked.hasProcess()) {
                        WindowProcessController windowProcessController = forTokenLocked.app;
                        windowProcessController.mAtm.mH.sendMessage(
                                PooledLambda.obtainMessage(
                                        new WindowProcessController$$ExternalSyntheticLambda6(1),
                                        windowProcessController.mListener));
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Trace.traceEnd(32L);
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void activityLocalRelaunch(IBinder iBinder) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(iBinder);
                if (forTokenLocked != null) {
                    forTokenLocked.startRelaunching();
                    if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
                        this.mService.mChangeTransitController.onActivityLocalRelaunched(
                                forTokenLocked);
                    }
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final void activityPaused(IBinder iBinder) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                Trace.traceBegin(32L, "activityPaused");
                ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(iBinder);
                if (forTokenLocked != null) {
                    forTokenLocked.activityPaused(false);
                }
                Trace.traceEnd(32L);
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final void activityRefreshed(IBinder iBinder) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(iBinder);
                if (ProtoLogImpl_54989576.Cache.WM_DEBUG_STATES_enabled[2]) {
                    ProtoLogImpl_54989576.i(
                            ProtoLogGroup.WM_DEBUG_STATES,
                            -69666241054231397L,
                            0,
                            null,
                            String.valueOf(forTokenLocked));
                }
                if (forTokenLocked != null
                        && forTokenLocked.mDisplayContent.mAppCompatCameraPolicy.mActivityRefresher
                                != null) {
                    forTokenLocked
                                    .mAppCompatController
                                    .mAppCompatOverrides
                                    .mAppCompatCameraOverrides
                                    .mAppCompatCameraOverridesState
                                    .mIsRefreshRequested =
                            false;
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final void activityRelaunched(IBinder iBinder) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(iBinder);
                if (forTokenLocked != null) {
                    forTokenLocked.finishRelaunching();
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final void activityResumed(IBinder iBinder, boolean z) {
        ActivityTaskManagerService activityTaskManagerService;
        PersonaActivityHelper personaActivityHelper;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(iBinder);
                if (forTokenLocked != null
                        && (activityTaskManagerService = this.mService) != null
                        && (personaActivityHelper =
                                        activityTaskManagerService.mPersonaActivityHelper)
                                != null) {
                    if (personaActivityHelper.mPersonaManager == null) {
                        personaActivityHelper.mPersonaManager =
                                ISemPersonaManager.Stub.asInterface(
                                        ServiceManager.getService("persona"));
                    }
                    if (personaActivityHelper.mPersonaManager != null) {
                        this.mService.mPersonaActivityHelper.notifyActivityResumedLocked(
                                isTopOfTask(iBinder), forTokenLocked);
                    }
                }
                if (forTokenLocked != null
                        && forTokenLocked.isState(ActivityRecord.State.RESUMED)
                        && !forTokenLocked.noDisplay) {
                    DualAppManagerService.notifyActivityResumedLocked(
                            forTokenLocked.shortComponentName);
                }
                ActivityRecord.activityResumedLocked(iBinder, z);
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final void activityStopped(
            IBinder iBinder,
            Bundle bundle,
            PersistableBundle persistableBundle,
            CharSequence charSequence) {
        ActivityRecord isInRootTaskLocked;
        String str;
        int i;
        if (bundle != null && bundle.hasFileDescriptors()) {
            throw new IllegalArgumentException("File descriptors passed in Bundle");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                Trace.traceBegin(32L, "activityStopped");
                isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
                str = null;
                i = 0;
                if (isInRootTaskLocked != null) {
                    ActivityRecord.State state = ActivityRecord.State.STOPPING;
                    ActivityRecord.State state2 = ActivityRecord.State.RESTARTING_PROCESS;
                    if (!isInRootTaskLocked.isState(state, state2)
                            && this.mTaskSupervisor.mHandler.hasMessages(213, isInRootTaskLocked)) {
                        isInRootTaskLocked.setState(state2, "continue-restart");
                    }
                    if (isInRootTaskLocked.attachedToProcess()
                            && isInRootTaskLocked.isState(state2)) {
                        WindowProcessController windowProcessController = isInRootTaskLocked.app;
                        String str2 = windowProcessController.mName;
                        i = windowProcessController.mUid;
                        str = str2;
                    }
                    isInRootTaskLocked.activityStopped(bundle, persistableBundle, charSequence);
                }
                Trace.traceEnd(32L);
            } finally {
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        if (isInRootTaskLocked != null) {
            DexController dexController = this.mService.mDexController;
            WindowManagerGlobalLock windowManagerGlobalLock2 = dexController.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock2) {
                try {
                    if (!dexController.mPendingActivityInfo.mWaitingStoppedTasks.isEmpty()) {
                        Task task = isInRootTaskLocked.task;
                        if (task != null
                                && task.getActivity(new DexController$$ExternalSyntheticLambda8())
                                        == null) {
                            dexController.mPendingActivityInfo.removeWaitingStoppedTask(
                                    "activityStopped", task);
                        }
                        if (dexController.mPendingActivityInfo.mWaitingStoppedTasks.isEmpty()
                                && dexController.mPendingActivityInfo
                                        .mWaitingTransitionFinishedTokens.isEmpty()) {
                            Slog.d(
                                    "DexController",
                                    "reparentToDisplayAndStartPendingActivity from"
                                        + " activityStopped");
                            dexController.scheduleReparentToDisplayAndStartPendingActivity(true);
                        }
                    }
                } finally {
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }
        if (CoreRune.MW_EMBED_ACTIVITY_PACKAGE_ENABLED && isInRootTaskLocked != null) {
            MultiTaskingController multiTaskingController = this.mService.mMultiTaskingController;
            WindowManagerGlobalLock windowManagerGlobalLock3 =
                    multiTaskingController.mAtm.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock3) {
                try {
                    Task rootTask = isInRootTaskLocked.getRootTask();
                    if (rootTask != null && rootTask.mIsWaitingRemoveEmbedActivityTask) {
                        WindowProcessController windowProcessController2 = rootTask.mRootProcess;
                        if (windowProcessController2 != null
                                && windowProcessController2.mPid != ActivityManagerService.MY_PID) {
                            multiTaskingController.mH.post(
                                    new MultiTaskingController$$ExternalSyntheticLambda14(
                                            multiTaskingController,
                                            rootTask,
                                            windowProcessController2));
                        }
                        WindowManagerService.resetPriorityAfterLockedSection();
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                } finally {
                    WindowManagerService.resetPriorityAfterLockedSection();
                }
            }
        }
        if (str != null) {
            this.mTaskSupervisor.mHandler.removeMessages(213, isInRootTaskLocked);
            this.mService.mAmInternal.killProcess(str, i, "restartActivityProcess");
        }
        this.mService.mAmInternal.trimApplications();
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final void activityTopResumedStateLost() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mTaskSupervisor.handleTopResumedStateReleased(false);
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final void adjustPopOverOptions(
            IBinder iBinder, int[] iArr, int[] iArr2, Point[] pointArr, int[] iArr3) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
                    if (isInRootTaskLocked != null) {
                        isInRootTaskLocked.mPopOverState.adjustOptions(
                                iArr, iArr2, pointArr, iArr3);
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

    public final int checkActivityCallerContentUriPermission(
            IBinder iBinder, IBinder iBinder2, Uri uri, int i, int i2) {
        GrantUri grantUri = new GrantUri(i2, i, uri);
        if (!((UriGrantsManagerService.LocalService) this.mService.mUgmInternal)
                .checkUriPermission(grantUri, Binder.getCallingUid(), i, true)) {
            throw new SecurityException(
                    "You don't have access to the content URI, hence can't check if the caller has"
                        + " access to it: "
                            + uri);
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(iBinder);
                if (forTokenLocked == null) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return -1;
                }
                int i3 = forTokenLocked.checkContentUriPermission(iBinder2, grantUri, i) ? 0 : -1;
                WindowManagerService.resetPriorityAfterLockedSection();
                return i3;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void clearOverrideActivityTransition(IBinder iBinder, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
                if (isInRootTaskLocked != null) {
                    if (z) {
                        isInRootTaskLocked.mCustomOpenTransition = null;
                    } else {
                        isInRootTaskLocked.mCustomCloseTransition = null;
                    }
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final boolean convertFromTranslucent(IBinder iBinder) {
        return convertFromTranslucentOp(iBinder, false);
    }

    public final boolean convertFromTranslucentOp(IBinder iBinder, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
                    if (isInRootTaskLocked == null) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return false;
                    }
                    Transition createTransition =
                            (!isInRootTaskLocked.mTransitionController.isShellTransitionsEnabled()
                                            || isInRootTaskLocked.mTransitionController
                                                    .isCollecting())
                                    ? null
                                    : isInRootTaskLocked.mTransitionController.createTransition(
                                            4, 0);
                    boolean occludesParent = isInRootTaskLocked.setOccludesParent(true, z);
                    if (createTransition != null) {
                        if (occludesParent) {
                            createTransition.setOverrideAnimation(
                                    TransitionInfo.AnimationOptions
                                            .makeSceneTransitionAnimOptions(),
                                    null,
                                    null);
                            isInRootTaskLocked.mTransitionController.requestStartTransition(
                                    createTransition, null, null, null);
                            isInRootTaskLocked.mTransitionController.setReady(
                                    isInRootTaskLocked.getDisplayContent(), true);
                        } else {
                            createTransition.abort();
                        }
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return occludesParent;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean convertToTranslucent(IBinder iBinder, Bundle bundle) {
        ActivityOptions activityOptions;
        SafeActivityOptions fromBundle = SafeActivityOptions.fromBundle(bundle);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
                    if (isInRootTaskLocked == null) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return false;
                    }
                    ActivityRecord activityBelow =
                            isInRootTaskLocked.task.getActivityBelow(isInRootTaskLocked);
                    if (activityBelow != null) {
                        activityBelow.returningOptions =
                                fromBundle != null
                                        ? fromBundle.getOptions(
                                                isInRootTaskLocked.intent,
                                                isInRootTaskLocked.info,
                                                isInRootTaskLocked.app,
                                                isInRootTaskLocked.mTaskSupervisor)
                                        : null;
                        Slog.d(
                                "ActivityTaskManager",
                                "convertToTranslucent, r="
                                        + isInRootTaskLocked
                                        + ", under="
                                        + activityBelow
                                        + ", under.returningOptions="
                                        + activityBelow.returningOptions
                                        + ", caller="
                                        + Debug.getCallers(3));
                    }
                    Transition createTransition =
                            (!isInRootTaskLocked.mTransitionController.isShellTransitionsEnabled()
                                            || isInRootTaskLocked.mTransitionController
                                                    .isCollecting())
                                    ? null
                                    : isInRootTaskLocked.mTransitionController.createTransition(
                                            3, 0);
                    boolean occludesParent = isInRootTaskLocked.setOccludesParent(false, false);
                    if (createTransition != null) {
                        if (occludesParent) {
                            isInRootTaskLocked.mTransitionController.requestStartTransition(
                                    createTransition, null, null, null);
                            isInRootTaskLocked.mTransitionController.setReady(
                                    isInRootTaskLocked.getDisplayContent(), true);
                            if ((activityBelow != null
                                            && (activityOptions = activityBelow.returningOptions)
                                                    != null
                                            && activityOptions.getAnimationType() == 5)
                                    || CoreRune.FW_SHELL_TRANSITION) {
                                createTransition.setOverrideAnimation(
                                        TransitionInfo.AnimationOptions
                                                .makeSceneTransitionAnimOptions(),
                                        null,
                                        null);
                            }
                        } else {
                            createTransition.abort();
                        }
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return occludesParent;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void dismissKeyguard(
            IBinder iBinder,
            IKeyguardDismissCallback iKeyguardDismissCallback,
            CharSequence charSequence) {
        if (charSequence != null) {
            this.mService.mAmInternal.enforceCallingPermission(
                    "android.permission.SHOW_KEYGUARD_MESSAGE", "dismissKeyguard");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    this.mService.mKeyguardController.dismissKeyguard(
                            iBinder, iKeyguardDismissCallback, charSequence);
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

    public final void enableTaskLocaleOverride(IBinder iBinder) {
        if (UserHandle.getAppId(Binder.getCallingUid()) != 1000) {
            return;
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(iBinder);
                if (forTokenLocked != null) {
                    forTokenLocked.task.mAlignActivityLocaleWithTask = true;
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final ActivityRecord ensureValidPictureInPictureActivityParams(
            String str, IBinder iBinder, PictureInPictureParams pictureInPictureParams) {
        if (!this.mService.mSupportsPictureInPicture) {
            throw new IllegalStateException(
                    str.concat(": Device doesn't support picture-in-picture mode."));
        }
        ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(iBinder);
        if (forTokenLocked == null) {
            throw new IllegalStateException(str + ": Can't find activity for token=" + iBinder);
        }
        if (!forTokenLocked.supportsPictureInPicture()) {
            throw new IllegalStateException(
                    str.concat(": Current activity does not support picture-in-picture."));
        }
        int callingUserId = UserHandle.getCallingUserId();
        if (forTokenLocked.pictureInPictureArgs.hasSetAspectRatio()
                && pictureInPictureParams.hasSetAspectRatio()
                && !forTokenLocked
                        .pictureInPictureArgs
                        .getAspectRatio()
                        .equals(pictureInPictureParams.getAspectRatio())
                && !this.mSetPipAspectRatioQuotaTracker.noteEvent(
                        callingUserId, forTokenLocked.packageName, "setPipAspectRatio")) {
            StringBuilder m =
                    Preconditions$$ExternalSyntheticOutline0.m(
                            str, ": Too many PiP aspect ratio change requests from ");
            m.append(forTokenLocked.packageName);
            throw new IllegalStateException(m.toString());
        }
        float f = this.mContext.getResources().getFloat(R.dimen.config_viewMinFlingVelocity);
        float f2 =
                this.mContext
                        .getResources()
                        .getFloat(R.dimen.config_viewMaxRotaryEncoderFlingVelocity);
        if (pictureInPictureParams.hasSetAspectRatio()) {
            WindowManagerService windowManagerService = this.mService.mWindowManager;
            DisplayContent displayContent = forTokenLocked.mDisplayContent;
            float aspectRatioFloat = pictureInPictureParams.getAspectRatioFloat();
            windowManagerService.getClass();
            PinnedTaskController pinnedTaskController = displayContent.mPinnedTaskController;
            if (Float.compare(pinnedTaskController.mMinAspectRatio, aspectRatioFloat) > 0
                    || Float.compare(aspectRatioFloat, pinnedTaskController.mMaxAspectRatio) > 0) {
                throw new IllegalArgumentException(
                        String.format(
                                str.concat(
                                        ": Aspect ratio is too extreme (must be between %f and"
                                            + " %f)."),
                                Float.valueOf(f),
                                Float.valueOf(f2)));
            }
        }
        if (this.mService.mSupportsExpandedPictureInPicture
                && pictureInPictureParams.hasSetExpandedAspectRatio()) {
            WindowManagerService windowManagerService2 = this.mService.mWindowManager;
            DisplayContent displayContent2 = forTokenLocked.mDisplayContent;
            float expandedAspectRatioFloat = pictureInPictureParams.getExpandedAspectRatioFloat();
            windowManagerService2.getClass();
            PinnedTaskController pinnedTaskController2 = displayContent2.mPinnedTaskController;
            if (Float.compare(pinnedTaskController2.mMinAspectRatio, expandedAspectRatioFloat) <= 0
                    && Float.compare(
                                    expandedAspectRatioFloat, pinnedTaskController2.mMaxAspectRatio)
                            <= 0) {
                throw new IllegalArgumentException(
                        String.format(
                                str.concat(
                                        ": Expanded aspect ratio is not extreme enough (must not be"
                                            + " between %f and %f)."),
                                Float.valueOf(f),
                                Float.valueOf(f2)));
            }
        }
        pictureInPictureParams.truncateActions(
                ActivityTaskManager.getMaxNumPictureInPictureActions(this.mContext));
        return forTokenLocked;
    }

    public final boolean enterPictureInPictureMode(
            IBinder iBinder, PictureInPictureParams pictureInPictureParams) {
        boolean enterPictureInPictureMode;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mSetPipAspectRatioQuotaTracker == null) {
                CountQuotaTracker countQuotaTracker =
                        new CountQuotaTracker(this.mContext, Categorizer.SINGLE_CATEGORIZER);
                this.mSetPipAspectRatioQuotaTracker = countQuotaTracker;
                countQuotaTracker.setCountLimit(Category.SINGLE_CATEGORY, 60, 60000L);
            }
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    enterPictureInPictureMode =
                            this.mService.enterPictureInPictureMode(
                                    ensureValidPictureInPictureActivityParams(
                                            "enterPictureInPictureMode",
                                            iBinder,
                                            pictureInPictureParams),
                                    pictureInPictureParams,
                                    true,
                                    false);
                } finally {
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return enterPictureInPictureMode;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean finishActivity(IBinder iBinder, int i, Intent intent, int i2) {
        long j;
        ActivityTaskSupervisor activityTaskSupervisor;
        int pid;
        ActivityRecord activityRecord;
        boolean z;
        if (intent != null && intent.hasFileDescriptors()) {
            throw new IllegalArgumentException("File descriptors passed in Intent");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
                boolean z2 = true;
                if (isInRootTaskLocked == null) {
                    return true;
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                NeededUriGrants collectGrants =
                        this.mService.collectGrants(intent, isInRootTaskLocked.resultTo);
                WindowManagerGlobalLock windowManagerGlobalLock2 = this.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock2) {
                    try {
                        if (!isInRootTaskLocked.inHistory) {
                            return true;
                        }
                        Task task = isInRootTaskLocked.task;
                        ActivityRecord rootActivity = task.getRootActivity(true, false);
                        if (rootActivity == null) {
                            Slog.w(
                                    "ActivityTaskManager",
                                    "Finishing task with all activities already finished");
                        }
                        if (this.mService.mLockTaskController.activityBlockedFromFinish(
                                isInRootTaskLocked)) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return false;
                        }
                        if (this.mService.mController != null
                                && (activityRecord =
                                                isInRootTaskLocked
                                                        .getRootTask()
                                                        .topRunningActivity(-1, iBinder))
                                        != null) {
                            try {
                                z =
                                        this.mService.mController.activityResuming(
                                                activityRecord.packageName);
                            } catch (RemoteException unused) {
                                this.mService.mController = null;
                                Watchdog.getInstance().setActivityController(null);
                                z = true;
                            }
                            if (!z) {
                                Slog.i(
                                        "ActivityTaskManager",
                                        "Not finishing activity because controller resumed");
                                WindowManagerService.resetPriorityAfterLockedSection();
                                return false;
                            }
                        }
                        WindowProcessController windowProcessController = isInRootTaskLocked.app;
                        if (windowProcessController != null) {
                            long uptimeMillis = SystemClock.uptimeMillis();
                            if (uptimeMillis > windowProcessController.mLastActivityFinishTime
                                    && windowProcessController.hasActivityInVisibleTask()) {
                                windowProcessController.mLastActivityFinishTime = uptimeMillis;
                            }
                        }
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        Trace.traceBegin(32L, "finishActivity");
                        boolean z3 = i2 == 1;
                        try {
                            this.mTaskSupervisor.mBalController.onActivityRequestedFinishing(
                                    isInRootTaskLocked);
                        } catch (Throwable th) {
                            th = th;
                            j = 32;
                        }
                        try {
                            if (i2 != 2 && (!z3 || isInRootTaskLocked != rootActivity)) {
                                isInRootTaskLocked.finishIfPossible(
                                        i, intent, collectGrants, "app-request", true);
                                z2 = isInRootTaskLocked.finishing;
                                if (!z2) {
                                    Slog.i(
                                            "ActivityTaskManager",
                                            "Failed to finish by app-request");
                                }
                                j = 32;
                                Trace.traceEnd(j);
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                WindowManagerService.resetPriorityAfterLockedSection();
                                return z2;
                            }
                            activityTaskSupervisor.removeTask(
                                    task, false, z3, "finish-activity", false, 1000, pid);
                            isInRootTaskLocked.mRelaunchReason = 0;
                            Trace.traceEnd(j);
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return z2;
                        } catch (Throwable th2) {
                            th = th2;
                            Trace.traceEnd(j);
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            throw th;
                        }
                        activityTaskSupervisor = this.mTaskSupervisor;
                        isInRootTaskLocked.getUid();
                        pid = isInRootTaskLocked.getPid();
                        String str = isInRootTaskLocked.info.name;
                        j = 32;
                    } finally {
                        WindowManagerService.resetPriorityAfterLockedSection();
                    }
                }
            } finally {
                WindowManagerService.resetPriorityAfterLockedSection();
            }
        }
    }

    /* JADX WARN: Finally extract failed */
    public final boolean finishActivityAffinity(IBinder iBinder) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
                    if (isInRootTaskLocked != null) {
                        if (!this.mService.mLockTaskController.activityBlockedFromFinish(
                                isInRootTaskLocked)) {
                            isInRootTaskLocked.task.forAllActivities(
                                    new ActivityClientController$$ExternalSyntheticLambda0(
                                            1, isInRootTaskLocked),
                                    isInRootTaskLocked,
                                    true,
                                    true);
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

    public final void finishSubActivity(IBinder iBinder, final String str, final int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    final ActivityRecord isInRootTaskLocked =
                            ActivityRecord.isInRootTaskLocked(iBinder);
                    if (isInRootTaskLocked == null) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    isInRootTaskLocked
                            .getRootTask()
                            .forAllActivities(
                                    new Consumer() { // from class:
                                                     // com.android.server.wm.ActivityClientController$$ExternalSyntheticLambda3
                                        @Override // java.util.function.Consumer
                                        public final void accept(Object obj) {
                                            ActivityRecord activityRecord = ActivityRecord.this;
                                            String str2 = str;
                                            int i2 = i;
                                            ActivityRecord activityRecord2 = (ActivityRecord) obj;
                                            if (activityRecord2.resultTo == activityRecord
                                                    && activityRecord2.requestCode == i2
                                                    && Objects.equals(
                                                            activityRecord2.resultWho, str2)) {
                                                activityRecord2.finishIfPossible(
                                                        "request-sub", false);
                                            }
                                        }
                                    },
                                    true);
                    this.mService.updateOomAdj();
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

    public final String getActivityCallerPackage(IBinder iBinder, IBinder iBinder2) {
        return getPackage(iBinder, iBinder2, true);
    }

    public final int getActivityCallerUid(IBinder iBinder, IBinder iBinder2) {
        return getUid(iBinder, iBinder2, true);
    }

    public final IBinder getActivityTokenBelow(IBinder iBinder) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ActivityRecord isInAnyTask = ActivityRecord.isInAnyTask(iBinder);
                    if (isInAnyTask == null) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return null;
                    }
                    ActivityRecord activity =
                            isInAnyTask.task.getActivity(
                                    new ActivityClientController$$ExternalSyntheticLambda2(),
                                    isInAnyTask,
                                    false,
                                    true);
                    if (activity == null || activity.getUid() != isInAnyTask.getUid()) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return null;
                    }
                    IBinder iBinder2 = activity.token;
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return iBinder2;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final ComponentName getCallingActivity(IBinder iBinder) {
        ComponentName component;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
                ActivityRecord activityRecord =
                        isInRootTaskLocked != null ? isInRootTaskLocked.resultTo : null;
                component = activityRecord != null ? activityRecord.intent.getComponent() : null;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return component;
    }

    public final String getCallingPackage(IBinder iBinder) {
        String str;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
                ActivityRecord activityRecord =
                        isInRootTaskLocked != null ? isInRootTaskLocked.resultTo : null;
                str = activityRecord != null ? activityRecord.info.packageName : null;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return str;
    }

    public final int getDisplayId(IBinder iBinder) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
                Task rootTask =
                        isInRootTaskLocked != null ? isInRootTaskLocked.getRootTask() : null;
                if (rootTask == null) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return 0;
                }
                int displayId = rootTask.getDisplayId();
                int i = displayId != -1 ? displayId : 0;
                WindowManagerService.resetPriorityAfterLockedSection();
                return i;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final String getLaunchedFromPackage(IBinder iBinder) {
        return getPackage(iBinder, null, false);
    }

    public final int getLaunchedFromUid(IBinder iBinder) {
        return getUid(iBinder, null, false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x002f, code lost:

       if (r5 == null) goto L17;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String getPackage(
            android.os.IBinder r4, android.os.IBinder r5, boolean r6) {
        /*
            r3 = this;
            int r0 = android.os.Binder.getCallingUid()
            boolean r1 = r3.isInternalCallerGetLaunchedFrom(r0)
            com.android.server.wm.WindowManagerGlobalLock r3 = r3.mGlobalLock
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection()
            monitor-enter(r3)
            com.android.server.wm.ActivityRecord r4 = com.android.server.wm.ActivityRecord.forTokenLocked(r4)     // Catch: java.lang.Throwable -> L1e
            r2 = 0
            if (r4 == 0) goto L49
            if (r1 != 0) goto L20
            boolean r0 = canGetLaunchedFromLocked(r0, r4, r5, r6)     // Catch: java.lang.Throwable -> L1e
            if (r0 == 0) goto L49
            goto L20
        L1e:
            r4 = move-exception
            goto L4e
        L20:
            if (r6 == 0) goto L2f
            com.android.server.wm.ActivityCallerState r0 = r4.mCallerState     // Catch: java.lang.Throwable -> L1e
            java.util.WeakHashMap r0 = r0.mCallerTokenInfoMap     // Catch: java.lang.Throwable -> L1e
            java.lang.Object r0 = r0.getOrDefault(r5, r2)     // Catch: java.lang.Throwable -> L1e
            com.android.server.wm.ActivityCallerState$CallerInfo r0 = (com.android.server.wm.ActivityCallerState.CallerInfo) r0     // Catch: java.lang.Throwable -> L1e
            if (r0 == 0) goto L49
            goto L31
        L2f:
            if (r5 != 0) goto L49
        L31:
            if (r6 == 0) goto L42
            com.android.server.wm.ActivityCallerState r4 = r4.mCallerState     // Catch: java.lang.Throwable -> L1e
            java.util.WeakHashMap r4 = r4.mCallerTokenInfoMap     // Catch: java.lang.Throwable -> L1e
            java.lang.Object r4 = r4.getOrDefault(r5, r2)     // Catch: java.lang.Throwable -> L1e
            com.android.server.wm.ActivityCallerState$CallerInfo r4 = (com.android.server.wm.ActivityCallerState.CallerInfo) r4     // Catch: java.lang.Throwable -> L1e
            if (r4 == 0) goto L44
            java.lang.String r2 = r4.mPackageName     // Catch: java.lang.Throwable -> L1e
            goto L44
        L42:
            java.lang.String r2 = r4.launchedFromPackage     // Catch: java.lang.Throwable -> L1e
        L44:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L1e
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection()
            return r2
        L49:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L1e
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection()
            return r2
        L4e:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L1e
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection()
            throw r4
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.ActivityClientController.getPackage(android.os.IBinder,"
                    + " android.os.IBinder, boolean):java.lang.String");
    }

    public final int getRequestedOrientation(IBinder iBinder) {
        int overrideOrientation;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
                overrideOrientation =
                        isInRootTaskLocked != null
                                ? isInRootTaskLocked.getOverrideOrientation()
                                : -1;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return overrideOrientation;
    }

    public final Configuration getTaskConfiguration(IBinder iBinder) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ActivityRecord isInAnyTask = ActivityRecord.isInAnyTask(iBinder);
                if (isInAnyTask == null) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return null;
                }
                Configuration configuration = isInAnyTask.task.getConfiguration();
                WindowManagerService.resetPriorityAfterLockedSection();
                return configuration;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final int getTaskForActivity(IBinder iBinder, boolean z) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(iBinder);
                if (forTokenLocked == null) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return -1;
                }
                Task task = forTokenLocked.task;
                if (z) {
                    int i = task.getRootActivity(true, false) == forTokenLocked ? task.mTaskId : -1;
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return i;
                }
                int i2 = task.mTaskId;
                WindowManagerService.resetPriorityAfterLockedSection();
                return i2;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0030, code lost:

       if (r5 == null) goto L18;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getUid(android.os.IBinder r4, android.os.IBinder r5, boolean r6) {
        /*
            r3 = this;
            int r0 = android.os.Binder.getCallingUid()
            boolean r1 = r3.isInternalCallerGetLaunchedFrom(r0)
            com.android.server.wm.WindowManagerGlobalLock r3 = r3.mGlobalLock
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection()
            monitor-enter(r3)
            com.android.server.wm.ActivityRecord r4 = com.android.server.wm.ActivityRecord.forTokenLocked(r4)     // Catch: java.lang.Throwable -> L1e
            r2 = -1
            if (r4 == 0) goto L4a
            if (r1 != 0) goto L20
            boolean r0 = canGetLaunchedFromLocked(r0, r4, r5, r6)     // Catch: java.lang.Throwable -> L1e
            if (r0 == 0) goto L4a
            goto L20
        L1e:
            r4 = move-exception
            goto L4f
        L20:
            r0 = 0
            if (r6 == 0) goto L30
            com.android.server.wm.ActivityCallerState r1 = r4.mCallerState     // Catch: java.lang.Throwable -> L1e
            java.util.WeakHashMap r1 = r1.mCallerTokenInfoMap     // Catch: java.lang.Throwable -> L1e
            java.lang.Object r1 = r1.getOrDefault(r5, r0)     // Catch: java.lang.Throwable -> L1e
            com.android.server.wm.ActivityCallerState$CallerInfo r1 = (com.android.server.wm.ActivityCallerState.CallerInfo) r1     // Catch: java.lang.Throwable -> L1e
            if (r1 == 0) goto L4a
            goto L32
        L30:
            if (r5 != 0) goto L4a
        L32:
            if (r6 == 0) goto L43
            com.android.server.wm.ActivityCallerState r4 = r4.mCallerState     // Catch: java.lang.Throwable -> L1e
            java.util.WeakHashMap r4 = r4.mCallerTokenInfoMap     // Catch: java.lang.Throwable -> L1e
            java.lang.Object r4 = r4.getOrDefault(r5, r0)     // Catch: java.lang.Throwable -> L1e
            com.android.server.wm.ActivityCallerState$CallerInfo r4 = (com.android.server.wm.ActivityCallerState.CallerInfo) r4     // Catch: java.lang.Throwable -> L1e
            if (r4 == 0) goto L45
            int r2 = r4.mUid     // Catch: java.lang.Throwable -> L1e
            goto L45
        L43:
            int r2 = r4.launchedFromUid     // Catch: java.lang.Throwable -> L1e
        L45:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L1e
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection()
            return r2
        L4a:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L1e
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection()
            return r2
        L4f:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L1e
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection()
            throw r4
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.ActivityClientController.getUid(android.os.IBinder,"
                    + " android.os.IBinder, boolean):int");
    }

    public final void invalidateHomeTaskSnapshot(IBinder iBinder) {
        ActivityRecord isInRootTaskLocked;
        if (iBinder == null) {
            ActivityTaskManagerService.enforceTaskPermission("invalidateHomeTaskSnapshot");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (iBinder == null) {
                    Task task =
                            this.mService.mRootWindowContainer.mDefaultDisplay
                                    .getDefaultTaskDisplayArea()
                                    .mRootHomeTask;
                    isInRootTaskLocked = task != null ? task.topRunningActivity(false) : null;
                } else {
                    isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
                }
                if (isInRootTaskLocked != null && isInRootTaskLocked.isActivityTypeHome()) {
                    ((TaskSnapshotCache)
                                    this.mService.mWindowManager.mTaskSnapshotController.mCache)
                            .removeRunningEntry(Integer.valueOf(isInRootTaskLocked.task.mTaskId));
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final boolean isImmersive(IBinder iBinder) {
        boolean z;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
                if (isInRootTaskLocked == null) {
                    throw new IllegalArgumentException();
                }
                z = isInRootTaskLocked.immersive;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return z;
    }

    public final boolean isInternalCallerGetLaunchedFrom(int i) {
        if (UserHandle.getAppId(i) == 1000) {
            return true;
        }
        PackageManagerInternal packageManagerInternal = this.mService.mWindowManager.mPmInternal;
        AndroidPackage androidPackage = packageManagerInternal.getPackage(i);
        if (androidPackage == null) {
            return false;
        }
        if (androidPackage.isSignedWithPlatformKey()) {
            return true;
        }
        String[] knownPackageNames =
                packageManagerInternal.getKnownPackageNames(2, UserHandle.getUserId(i));
        return knownPackageNames.length > 0
                && androidPackage.getPackageName().equals(knownPackageNames[0]);
    }

    public final boolean isRequestedToLaunchInTaskFragment(IBinder iBinder, IBinder iBinder2) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
                if (isInRootTaskLocked == null) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                boolean z = isInRootTaskLocked.mRequestedLaunchingTaskFragmentToken == iBinder2;
                WindowManagerService.resetPriorityAfterLockedSection();
                return z;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final boolean isRootVoiceInteraction(IBinder iBinder) {
        boolean z;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
                z = isInRootTaskLocked != null && isInRootTaskLocked.rootVoiceInteraction;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return z;
    }

    public final boolean isTopOfTask(IBinder iBinder) {
        boolean z;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
                if (isInRootTaskLocked != null) {
                    z = true;
                    if (isInRootTaskLocked.task.getTopNonFinishingActivity(true, true)
                            == isInRootTaskLocked) {}
                }
                z = false;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return z;
    }

    public final boolean moveActivityTaskToBack(IBinder iBinder, boolean z) {
        ActivityTaskManagerService.enforceNotIsolatedCaller("moveActivityTaskToBack");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                boolean z2 = !z;
                try {
                    ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(iBinder);
                    int i = -1;
                    if (forTokenLocked != null && forTokenLocked.getParent() != null) {
                        Task task = forTokenLocked.task;
                        if (!z2
                                || forTokenLocked.compareTo(
                                                (WindowContainer) task.getRootActivity(false, true))
                                        <= 0) {
                            i = task.mTaskId;
                        }
                    }
                    Task anyTaskForId = this.mService.mRootWindowContainer.anyTaskForId(i);
                    if (anyTaskForId == null) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return false;
                    }
                    ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
                    boolean moveTaskToBack =
                            (isInRootTaskLocked != null ? isInRootTaskLocked.getRootTask() : null)
                                    .moveTaskToBack(anyTaskForId, null);
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return moveTaskToBack;
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
    public final boolean navigateUpTo(
            IBinder iBinder, Intent intent, String str, int i, Intent intent2) {
        boolean navigateUpTo;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
                if (isInRootTaskLocked == null) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                NeededUriGrants collectGrants =
                        this.mService.collectGrants(intent, isInRootTaskLocked);
                NeededUriGrants collectGrants2 =
                        this.mService.collectGrants(intent2, isInRootTaskLocked.resultTo);
                WindowManagerGlobalLock windowManagerGlobalLock2 = this.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock2) {
                    try {
                        navigateUpTo =
                                isInRootTaskLocked
                                        .getRootTask()
                                        .navigateUpTo(
                                                isInRootTaskLocked,
                                                intent,
                                                str,
                                                collectGrants,
                                                i,
                                                intent2,
                                                collectGrants2);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                return navigateUpTo;
            } finally {
                WindowManagerService.resetPriorityAfterLockedSection();
            }
        }
    }

    /* JADX WARN: Finally extract failed */
    public final void onBackPressed(
            IBinder iBinder, IRequestFinishCallback iRequestFinishCallback) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
                    if (isInRootTaskLocked != null) {
                        ActivityRecord rootActivity =
                                isInRootTaskLocked.task.getRootActivity(false, true);
                        if (isInRootTaskLocked != rootActivity
                                || !this.mService.mWindowOrganizerController
                                        .mTaskOrganizerController
                                        .handleInterceptBackPressedOnTaskRoot(
                                                isInRootTaskLocked.getRootTask())) {
                            if (!shouldMoveTaskToBack(isInRootTaskLocked, rootActivity)) {
                                WindowManagerService.resetPriorityAfterLockedSection();
                                try {
                                    iRequestFinishCallback.requestFinish();
                                } catch (RemoteException e) {
                                    Slog.e(
                                            "ActivityTaskManager",
                                            "Failed to invoke request finish callback",
                                            e);
                                }
                                return;
                            }
                            moveActivityTaskToBack(iBinder, true);
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

    public final void onPictureInPictureUiStateChanged(
            ActivityRecord activityRecord, PictureInPictureUiState pictureInPictureUiState) {
        try {
            this.mService.mLifecycleManager.scheduleTransactionItem(
                    activityRecord.app.mThread,
                    PipStateTransactionItem.obtain(activityRecord.token, pictureInPictureUiState));
        } catch (Exception e) {
            Slog.w(
                    "ActivityTaskManager",
                    "Failed to send pip state transaction item: "
                            + activityRecord.intent.getComponent(),
                    e);
        }
    }

    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        try {
            return super.onTransact(i, parcel, parcel2, i2);
        } catch (RuntimeException e) {
            ActivityTaskManagerService.logAndRethrowRuntimeExceptionOnTransact(
                    e, "ActivityClientController");
            throw null;
        }
    }

    public final void overrideActivityTransition(
            IBinder iBinder, boolean z, int i, int i2, int i3) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
                if (isInRootTaskLocked != null) {
                    ActivityRecord.CustomAppTransition customAppTransition =
                            z
                                    ? isInRootTaskLocked.mCustomOpenTransition
                                    : isInRootTaskLocked.mCustomCloseTransition;
                    if (customAppTransition == null) {
                        customAppTransition = new ActivityRecord.CustomAppTransition();
                        if (z) {
                            isInRootTaskLocked.mCustomOpenTransition = customAppTransition;
                        } else {
                            isInRootTaskLocked.mCustomCloseTransition = customAppTransition;
                        }
                    }
                    customAppTransition.mEnterAnim = i;
                    customAppTransition.mExitAnim = i2;
                    customAppTransition.mBackgroundColor = i3;
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final void overridePendingTaskTransition(IBinder iBinder, String str, int i, int i2) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
                if (isInRootTaskLocked != null
                        && isInRootTaskLocked.isState(
                                ActivityRecord.State.RESUMED, ActivityRecord.State.PAUSING)) {
                    isInRootTaskLocked.mDisplayContent.mAppTransition.overridePendingAppTransition(
                            str, i, i2, 0, null, null, true);
                    TransitionController transitionController =
                            isInRootTaskLocked.mTransitionController;
                    TransitionInfo.AnimationOptions makeCustomAnimOptions =
                            TransitionInfo.AnimationOptions.makeCustomAnimOptions(
                                    str, i, i2, 0, true);
                    Transition transition = transitionController.mCollectingTransition;
                    if (transition != null) {
                        transition.setOverrideAnimation(makeCustomAnimOptions, null, null);
                    }
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final void overridePendingTransition(
            IBinder iBinder, String str, int i, int i2, int i3) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
                if (CoreRune.MT_APP_COMPAT_CONFIGURATION
                        && MultiTaskingAppCompatConfiguration.isPresetLetterboxed(
                                isInRootTaskLocked)) {
                    Slog.d(
                            "MultiTaskingAppCompat",
                            "OverrideAnimation is not allowed by Letterbox. r="
                                    + isInRootTaskLocked);
                } else if (isInRootTaskLocked != null
                        && isInRootTaskLocked.isState(
                                ActivityRecord.State.RESUMED, ActivityRecord.State.PAUSING)) {
                    isInRootTaskLocked.mDisplayContent.mAppTransition.overridePendingAppTransition(
                            str, i, i2, i3, null, null, isInRootTaskLocked.mOverrideTaskTransition);
                    TransitionController transitionController =
                            isInRootTaskLocked.mTransitionController;
                    TransitionInfo.AnimationOptions makeCustomAnimOptions =
                            TransitionInfo.AnimationOptions.makeCustomAnimOptions(
                                    str, i, i2, i3, isInRootTaskLocked.mOverrideTaskTransition);
                    Transition transition = transitionController.mCollectingTransition;
                    if (transition != null) {
                        transition.setOverrideAnimation(makeCustomAnimOptions, null, null);
                    }
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final void registerRemoteAnimations(
            IBinder iBinder, RemoteAnimationDefinition remoteAnimationDefinition) {
        this.mService.mAmInternal.enforceCallingPermission(
                "android.permission.CONTROL_REMOTE_APP_TRANSITION_ANIMATIONS",
                "registerRemoteAnimations");
        remoteAnimationDefinition.setCallingPidUid(Binder.getCallingPid(), Binder.getCallingUid());
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    final ActivityRecord isInRootTaskLocked =
                            ActivityRecord.isInRootTaskLocked(iBinder);
                    if (isInRootTaskLocked != null) {
                        isInRootTaskLocked.mRemoteAnimationDefinition = remoteAnimationDefinition;
                        remoteAnimationDefinition.linkToDeath(
                                new IBinder
                                        .DeathRecipient() { // from class:
                                                            // com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda9
                                    @Override // android.os.IBinder.DeathRecipient
                                    public final void binderDied() {
                                        ActivityRecord.this.mRemoteAnimationDefinition = null;
                                    }
                                });
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

    public final boolean releaseActivityInstance(IBinder iBinder) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
                    if (isInRootTaskLocked != null && isInRootTaskLocked.isDestroyable()) {
                        isInRootTaskLocked.destroyImmediately("app-req");
                        boolean isState =
                                isInRootTaskLocked.isState(
                                        ActivityRecord.State.DESTROYING,
                                        ActivityRecord.State.DESTROYED);
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return isState;
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

    public final void reportActivityFullyDrawn(IBinder iBinder, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
                    if (isInRootTaskLocked != null) {
                        this.mTaskSupervisor.mActivityMetricsLogger.notifyFullyDrawn(
                                z, isInRootTaskLocked);
                        if (SemGateConfig.isGateEnabled()) {
                            Log.i(
                                    "GATE",
                                    "<GATE-M>APP_FULLY_LOADED_"
                                            + isInRootTaskLocked.packageName
                                            + "</GATE-M>");
                        }
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

    public final void reportSizeConfigurations(
            IBinder iBinder, SizeConfigurationBuckets sizeConfigurationBuckets) {
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_CONFIGURATION_enabled[1]) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_CONFIGURATION,
                    -4921282642721622589L,
                    0,
                    null,
                    String.valueOf(iBinder),
                    String.valueOf(sizeConfigurationBuckets));
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
                if (isInRootTaskLocked != null) {
                    isInRootTaskLocked.mSizeConfigurations = sizeConfigurationBuckets;
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void requestMultiwindowFullscreen(
            IBinder iBinder, int i, IRemoteCallback iRemoteCallback) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    requestMultiwindowFullscreenLocked(iBinder, i, iRemoteCallback);
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

    public final void requestMultiwindowFullscreenLocked(
            IBinder iBinder, final int i, final IRemoteCallback iRemoteCallback) {
        final ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(iBinder);
        if (forTokenLocked == null) {
            return;
        }
        TransitionController transitionController = forTokenLocked.mTransitionController;
        if (transitionController.isShellTransitionsEnabled()) {
            final Transition transition =
                    new Transition(
                            6, 0, transitionController, this.mService.mWindowManager.mSyncEngine);
            forTokenLocked.mTransitionController.startCollectOrQueue(
                    transition,
                    new TransitionController.OnStartCollect() { // from class:
                        // com.android.server.wm.ActivityClientController$$ExternalSyntheticLambda4
                        @Override // com.android.server.wm.TransitionController.OnStartCollect
                        public final void onCollectStarted(boolean z) {
                            IRemoteCallback iRemoteCallback2 = iRemoteCallback;
                            Task topDisplayFocusedRootTask =
                                    ActivityClientController.this.mService.mRootWindowContainer
                                            .getTopDisplayFocusedRootTask();
                            int i2 = i;
                            ActivityRecord activityRecord = forTokenLocked;
                            int validateMultiwindowFullscreenRequestLocked =
                                    ActivityClientController
                                            .validateMultiwindowFullscreenRequestLocked(
                                                    i2, activityRecord, topDisplayFocusedRootTask);
                            if (iRemoteCallback2 != null) {
                                try {
                                    iRemoteCallback2.sendResult(
                                            SystemUpdateManagerService$$ExternalSyntheticOutline0.m(
                                                    validateMultiwindowFullscreenRequestLocked,
                                                    KnoxCustomManagerService.SPCM_KEY_RESULT));
                                } catch (RemoteException unused) {
                                    Slog.w(
                                            "ActivityTaskManager",
                                            "client throws an exception back to the server, ignore"
                                                + " it");
                                }
                            }
                            Transition transition2 = transition;
                            if (validateMultiwindowFullscreenRequestLocked != 0) {
                                transition2.abort();
                                return;
                            }
                            Task task = activityRecord.task;
                            transition2.collect(task, false);
                            ActivityClientController.executeMultiWindowFullscreenRequest(i2, task);
                            activityRecord.mTransitionController.requestStartTransition(
                                    transition2, task, null, null);
                            transition2.setReady(task, true);
                        }
                    });
            return;
        }
        Task topDisplayFocusedRootTask =
                this.mService.mRootWindowContainer.getTopDisplayFocusedRootTask();
        int validateMultiwindowFullscreenRequestLocked =
                validateMultiwindowFullscreenRequestLocked(
                        i, forTokenLocked, topDisplayFocusedRootTask);
        if (iRemoteCallback != null) {
            try {
                iRemoteCallback.sendResult(
                        SystemUpdateManagerService$$ExternalSyntheticOutline0.m(
                                validateMultiwindowFullscreenRequestLocked,
                                KnoxCustomManagerService.SPCM_KEY_RESULT));
            } catch (RemoteException unused) {
                Slog.w(
                        "ActivityTaskManager",
                        "client throws an exception back to the server, ignore it");
            }
        }
        if (validateMultiwindowFullscreenRequestLocked == 0) {
            executeMultiWindowFullscreenRequest(i, topDisplayFocusedRootTask);
        }
    }

    public final boolean requestPictureInPictureMode(ActivityRecord activityRecord) {
        if (activityRecord.inPinnedWindowingMode()
                || !activityRecord.checkEnterPictureInPictureState(
                        "requestPictureInPictureMode", false, false)) {
            return false;
        }
        if (activityRecord.pictureInPictureArgs.isAutoEnterEnabled()) {
            return this.mService.enterPictureInPictureMode(
                    activityRecord, activityRecord.pictureInPictureArgs, false, false);
        }
        try {
            this.mService.mLifecycleManager.scheduleTransactionItem(
                    activityRecord.app.mThread, EnterPipRequestedItem.obtain(activityRecord.token));
            return true;
        } catch (Exception e) {
            Slog.w(
                    "ActivityTaskManager",
                    "Failed to send enter pip requested item: "
                            + activityRecord.intent.getComponent(),
                    e);
            return false;
        }
    }

    public final void setActivityRecordInputSinkEnabled(IBinder iBinder, boolean z) {
        if (Flags.allowDisableActivityRecordInputSink()) {
            this.mService.mAmInternal.enforceCallingPermission(
                    "android.permission.INTERNAL_SYSTEM_WINDOW",
                    "setActivityRecordInputSinkEnabled");
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(iBinder);
                    if (forTokenLocked != null) {
                        forTokenLocked.mActivityRecordInputSinkEnabled = z;
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }
    }

    public final void setAllowCrossUidActivitySwitchFromBelow(IBinder iBinder, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
                    if (isInRootTaskLocked != null) {
                        isInRootTaskLocked.mAllowCrossUidActivitySwitchFromBelow = z;
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

    public final void setForceSendResultForMediaProjection(IBinder iBinder) {
        this.mService.mAmInternal.enforceCallingPermission(
                "android.permission.MANAGE_MEDIA_PROJECTION",
                "setForceSendResultForMediaProjection");
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
                if (isInRootTaskLocked != null && isInRootTaskLocked.inHistory) {
                    isInRootTaskLocked.mForceSendResultForMediaProjection = true;
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void setImmersive(IBinder iBinder, boolean z) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
                if (isInRootTaskLocked == null) {
                    throw new IllegalArgumentException();
                }
                isInRootTaskLocked.immersive = z;
                if (isInRootTaskLocked.mDisplayContent.forAllTaskDisplayAreas(
                        new ActivityRecord$$ExternalSyntheticLambda1(5, isInRootTaskLocked))) {
                    if (ProtoLogImpl_54989576.Cache.WM_DEBUG_IMMERSIVE_enabled[0]) {
                        ProtoLogImpl_54989576.d(
                                ProtoLogGroup.WM_DEBUG_IMMERSIVE,
                                -1597980207704427048L,
                                0,
                                null,
                                String.valueOf(isInRootTaskLocked));
                    }
                    ActivityTaskManagerService activityTaskManagerService = this.mService;
                    activityTaskManagerService.getClass();
                    activityTaskManagerService.mH.post(
                            new ActivityTaskManagerService$$ExternalSyntheticLambda16(
                                    activityTaskManagerService,
                                    isInRootTaskLocked.immersive,
                                    isInRootTaskLocked));
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void setInheritShowWhenLocked(IBinder iBinder, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
                    if (isInRootTaskLocked != null) {
                        isInRootTaskLocked.mInheritShownWhenLocked = z;
                        isInRootTaskLocked.mAtmService.mRootWindowContainer
                                .ensureActivitiesVisible();
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

    public final void setPictureInPictureParams(
            IBinder iBinder, PictureInPictureParams pictureInPictureParams) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mSetPipAspectRatioQuotaTracker == null) {
                CountQuotaTracker countQuotaTracker =
                        new CountQuotaTracker(this.mContext, Categorizer.SINGLE_CATEGORIZER);
                this.mSetPipAspectRatioQuotaTracker = countQuotaTracker;
                countQuotaTracker.setCountLimit(Category.SINGLE_CATEGORY, 60, 60000L);
            }
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ActivityRecord ensureValidPictureInPictureActivityParams =
                            ensureValidPictureInPictureActivityParams(
                                    "setPictureInPictureParams", iBinder, pictureInPictureParams);
                    ensureValidPictureInPictureActivityParams.pictureInPictureArgs.copyOnlySet(
                            pictureInPictureParams);
                    Rect bounds = ensureValidPictureInPictureActivityParams.getBounds();
                    PictureInPictureParams pictureInPictureParams2 =
                            ensureValidPictureInPictureActivityParams.pictureInPictureArgs;
                    if (pictureInPictureParams2 != null
                            && pictureInPictureParams2.hasSourceBoundsHint()) {
                        ensureValidPictureInPictureActivityParams
                                .pictureInPictureArgs
                                .getSourceRectHint()
                                .offset(bounds.left, bounds.top);
                    }
                    Task rootTask = ensureValidPictureInPictureActivityParams.task.getRootTask();
                    if (rootTask.inPinnedWindowingMode()) {
                        rootTask.dispatchTaskInfoChangedIfNeeded(true);
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

    public final void setRecentsScreenshotEnabled(IBinder iBinder, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
                    if (isInRootTaskLocked != null) {
                        isInRootTaskLocked.mEnableRecentsScreenshot = z;
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

    public final void setRequestedOrientation(IBinder iBinder, int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
                    if (isInRootTaskLocked != null) {
                        EventLog.writeEvent(
                                31006, Integer.valueOf(i), isInRootTaskLocked.shortComponentName);
                        isInRootTaskLocked.setRequestedOrientation(i);
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

    public final void setShouldDockBigOverlays(IBinder iBinder, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(iBinder);
                    forTokenLocked.shouldDockBigOverlays = z;
                    forTokenLocked.task.getRootTask().dispatchTaskInfoChangedIfNeeded(true);
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

    public final void setShowWhenLocked(IBinder iBinder, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
                    if (isInRootTaskLocked != null) {
                        int i = isInRootTaskLocked.mUserId;
                        int identityHashCode = System.identityHashCode(isInRootTaskLocked);
                        EventLog.writeEvent(
                                1000205,
                                Integer.valueOf(i),
                                Integer.valueOf(identityHashCode),
                                isInRootTaskLocked.shortComponentName,
                                Integer.valueOf(z ? 1 : 0));
                        isInRootTaskLocked.mShowWhenLocked = z;
                        isInRootTaskLocked.mAtmService.mRootWindowContainer
                                .ensureActivitiesVisible();
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

    public final void setTaskDescription(
            IBinder iBinder, ActivityManager.TaskDescription taskDescription) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
                if (isInRootTaskLocked != null) {
                    isInRootTaskLocked.setTaskDescription(taskDescription);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void setTurnScreenOn(IBinder iBinder, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
                    if (isInRootTaskLocked != null) {
                        isInRootTaskLocked.mTurnScreenOn = z;
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

    /* JADX WARN: Finally extract failed */
    public final int setVrMode(IBinder iBinder, boolean z, ComponentName componentName) {
        ActivityRecord isInRootTaskLocked;
        int isValid;
        this.mService.enforceSystemHasVrFeature();
        VrManagerService.LocalService localService =
                (VrManagerService.LocalService)
                        LocalServices.getService(VrManagerService.LocalService.class);
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
            } finally {
                WindowManagerService.resetPriorityAfterLockedSection();
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        if (isInRootTaskLocked == null) {
            throw new IllegalArgumentException();
        }
        int i = isInRootTaskLocked.mUserId;
        VrManagerService vrManagerService = VrManagerService.this;
        synchronized (vrManagerService.mLock) {
            isValid = vrManagerService.mComponentObserver.isValid(i, componentName);
        }
        if (isValid != 0) {
            return isValid;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock2 = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock2) {
                if (!z) {
                    componentName = null;
                }
                try {
                    isInRootTaskLocked.requestedVrComponent = componentName;
                    if (isInRootTaskLocked.mDisplayContent.forAllTaskDisplayAreas(
                            new ActivityRecord$$ExternalSyntheticLambda1(5, isInRootTaskLocked))) {
                        this.mService.applyUpdateVrModeLocked(isInRootTaskLocked);
                    }
                } finally {
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return 0;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean shouldUpRecreateTask(IBinder iBinder, String str) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(iBinder);
                if (forTokenLocked == null) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                boolean shouldUpRecreateTaskLocked =
                        forTokenLocked
                                .getRootTask()
                                .shouldUpRecreateTaskLocked(forTokenLocked, str);
                WindowManagerService.resetPriorityAfterLockedSection();
                return shouldUpRecreateTaskLocked;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    /* JADX WARN: Finally extract failed */
    public final boolean showAssistFromActivity(IBinder iBinder, Bundle bundle) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(iBinder);
                    Task topDisplayFocusedRootTask =
                            this.mService.mRootWindowContainer.getTopDisplayFocusedRootTask();
                    ActivityRecord topNonFinishingActivity =
                            topDisplayFocusedRootTask != null
                                    ? topDisplayFocusedRootTask.getTopNonFinishingActivity(
                                            true, true)
                                    : null;
                    if (topNonFinishingActivity != forTokenLocked) {
                        Slog.w(
                                "ActivityTaskManager",
                                "showAssistFromActivity failed: caller "
                                        + forTokenLocked
                                        + " is not current top "
                                        + topNonFinishingActivity);
                    } else {
                        if (topNonFinishingActivity.nowVisible) {
                            String str = topNonFinishingActivity.launchedFromFeatureId;
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return this.mAssistUtils.showSessionForActiveService(
                                    bundle,
                                    8,
                                    str,
                                    (IVoiceInteractionSessionShowCallback) null,
                                    iBinder);
                        }
                        Slog.w(
                                "ActivityTaskManager",
                                "showAssistFromActivity failed: caller "
                                        + forTokenLocked
                                        + " is not visible");
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

    public final void showLockTaskEscapeMessage(IBinder iBinder) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (ActivityRecord.forTokenLocked(iBinder) != null) {
                    this.mService.mLockTaskController.showLockTaskToast();
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void splashScreenAttached(IBinder iBinder) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(iBinder);
                if (forTokenLocked == null) {
                    Slog.w(
                            "ActivityTaskManager",
                            "splashScreenTransferredLocked cannot find activity");
                } else {
                    forTokenLocked.mAtmService.mH.removeCallbacks(
                            forTokenLocked.mTransferSplashScreenTimeoutRunnable);
                    WindowState windowState = forTokenLocked.mStartingWindow;
                    if (windowState != null) {
                        windowState.cancelAnimation();
                        forTokenLocked.mStartingWindow.hide(false, false);
                    }
                    forTokenLocked.mTransferringSplashScreenState = 3;
                    forTokenLocked.removeStartingWindowAnimation(false);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final void startLocalVoiceInteraction(IBinder iBinder, Bundle bundle) {
        Slog.i("ActivityTaskManager", "Activity tried to startLocalVoiceInteraction");
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                Task topDisplayFocusedRootTask =
                        this.mService.mRootWindowContainer.getTopDisplayFocusedRootTask();
                ActivityRecord topNonFinishingActivity =
                        topDisplayFocusedRootTask != null
                                ? topDisplayFocusedRootTask.getTopNonFinishingActivity(true, true)
                                : null;
                if (ActivityRecord.forTokenLocked(iBinder) != topNonFinishingActivity) {
                    throw new SecurityException(
                            "Only focused activity can call startVoiceInteraction");
                }
                if (this.mService.mRunningVoice == null
                        && topNonFinishingActivity.task.voiceSession == null
                        && topNonFinishingActivity.voiceSession == null) {
                    if (topNonFinishingActivity.pendingVoiceInteractionStart) {
                        Slog.w(
                                "ActivityTaskManager",
                                "Pending start of voice interaction already.");
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    } else {
                        topNonFinishingActivity.pendingVoiceInteractionStart = true;
                        String str = topNonFinishingActivity.launchedFromFeatureId;
                        WindowManagerService.resetPriorityAfterLockedSection();
                        ((VoiceInteractionManagerInternal)
                                        LocalServices.getService(
                                                VoiceInteractionManagerInternal.class))
                                .startLocalVoiceInteraction(iBinder, str, bundle);
                        return;
                    }
                }
                Slog.w(
                        "ActivityTaskManager",
                        "Already in a voice interaction, cannot start new voice interaction");
                WindowManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void startLockTaskModeByToken(IBinder iBinder) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(iBinder);
                if (forTokenLocked != null) {
                    this.mService.startLockTaskMode(forTokenLocked.task, false);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void stopLocalVoiceInteraction(IBinder iBinder) {
        ((VoiceInteractionManagerInternal)
                        LocalServices.getService(VoiceInteractionManagerInternal.class))
                .stopLocalVoiceInteraction(iBinder);
    }

    public final void stopLockTaskModeByToken(IBinder iBinder) {
        this.mService.stopLockTaskModeInternal(iBinder, false);
    }

    public final void toggleFreeformWindowingMode(IBinder iBinder) {
        Rect rect;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(iBinder);
                    if (forTokenLocked == null) {
                        throw new IllegalArgumentException(
                                "toggleFreeformWindowingMode: No activity record matching token="
                                        + iBinder);
                    }
                    Task rootTask = forTokenLocked.getRootTask();
                    if (rootTask == null) {
                        throw new IllegalStateException(
                                "toggleFreeformWindowingMode: the activity doesn't have a root"
                                    + " task");
                    }
                    if (!rootTask.inFreeformWindowingMode() && rootTask.getWindowingMode() != 1) {
                        throw new IllegalStateException(
                                "toggleFreeformWindowingMode: You can only toggle between"
                                    + " fullscreen and freeform.");
                    }
                    if (rootTask.isDexCompatEnabled()) {
                        DexCompatController dexCompatController =
                                this.mService.mDexCompatController;
                        dexCompatController.getClass();
                        dexCompatController.scheduleStartActivityAsToggleFreeform(
                                rootTask,
                                new DexCompatController$$ExternalSyntheticLambda0(rootTask),
                                new DexCompatController$$ExternalSyntheticLambda1(0, rootTask),
                                new DexCompatController$$ExternalSyntheticLambda2(0));
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    if (rootTask.inFreeformWindowingMode()) {
                        rootTask.setWindowingMode(1);
                        rootTask.setBounds(null);
                        if (!forTokenLocked.isDexMode()) {
                            forTokenLocked.task.mLastNonFullscreenBounds = null;
                        }
                    } else {
                        if (!forTokenLocked.supportsFreeform()
                                && ((!CoreRune.MT_NEW_DEX_BOUNDS_POLICY
                                                || !rootTask.isNewDexMode()
                                                || !rootTask.inFullscreenWindowingMode())
                                        && !rootTask.isResizeable(true))) {
                            if (CoreRune.MT_DEX_SIZE_COMPAT_MODE && rootTask.isNewDexMode()) {
                                DexSizeCompatController.LazyHolder.sInstance.getClass();
                                if (DexSizeCompatController.getCompatPolicy(rootTask) != null) {}
                            }
                            throw new IllegalStateException(
                                    "This activity is currently not freeform-enabled");
                        }
                        if (rootTask.getParent().inFreeformWindowingMode()) {
                            rootTask.setWindowingMode(0);
                            Task task = forTokenLocked.task;
                            if (task.isDesktopModeEnabled()
                                    && ((rect = task.mLastNonFullscreenBounds) == null
                                            || rect.isEmpty())) {
                                task.mTakeInitBounds = true;
                                this.mTaskSupervisor.mLaunchParamsController.layoutTask(
                                        task,
                                        forTokenLocked.info.windowLayout,
                                        null,
                                        null,
                                        null,
                                        -1);
                                this.mService.mRootWindowContainer.ensureActivitiesVisible();
                            }
                        } else {
                            rootTask.setWindowingMode(5);
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

    public final void unregisterRemoteAnimations(IBinder iBinder) {
        this.mService.mAmInternal.enforceCallingPermission(
                "android.permission.CONTROL_REMOTE_APP_TRANSITION_ANIMATIONS",
                "unregisterRemoteAnimations");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
                    if (isInRootTaskLocked != null) {
                        isInRootTaskLocked.mRemoteAnimationDefinition = null;
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

    /* JADX WARN: Removed duplicated region for block: B:13:0x0042  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean willActivityBeVisible(android.os.IBinder r5) {
        /*
            r4 = this;
            com.android.server.wm.WindowManagerGlobalLock r4 = r4.mGlobalLock
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection()
            monitor-enter(r4)
            com.android.server.wm.ActivityRecord r0 = com.android.server.wm.ActivityRecord.isInRootTaskLocked(r5)     // Catch: java.lang.Throwable -> L48
            if (r0 == 0) goto L11
            com.android.server.wm.Task r0 = r0.getRootTask()     // Catch: java.lang.Throwable -> L48
            goto L12
        L11:
            r0 = 0
        L12:
            r1 = 0
            if (r0 == 0) goto L43
            com.android.server.wm.ActivityRecord r5 = com.android.server.wm.ActivityRecord.forTokenLocked(r5)     // Catch: java.lang.Throwable -> L48
            r0 = 1
            if (r5 != 0) goto L1e
        L1c:
            r5 = r1
            goto L40
        L1e:
            boolean r2 = r5.shouldBeVisible(r1)     // Catch: java.lang.Throwable -> L48
            if (r2 != 0) goto L25
            goto L1c
        L25:
            boolean r2 = r5.finishing     // Catch: java.lang.Throwable -> L48
            if (r2 == 0) goto L3d
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L48
            java.lang.String r3 = "willActivityBeVisible: Returning false, would have returned true for r="
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L48
            r2.append(r5)     // Catch: java.lang.Throwable -> L48
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> L48
            java.lang.String r3 = "ActivityTaskManager"
            android.util.Slog.e(r3, r2)     // Catch: java.lang.Throwable -> L48
        L3d:
            boolean r5 = r5.finishing     // Catch: java.lang.Throwable -> L48
            r5 = r5 ^ r0
        L40:
            if (r5 == 0) goto L43
            r1 = r0
        L43:
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L48
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection()
            return r1
        L48:
            r5 = move-exception
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L48
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection()
            throw r5
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.ActivityClientController.willActivityBeVisible(android.os.IBinder):boolean");
    }
}
