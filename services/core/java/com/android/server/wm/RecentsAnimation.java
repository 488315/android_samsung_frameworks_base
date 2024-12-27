package com.android.server.wm;

import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Intent;
import android.os.RemoteException;
import android.os.Trace;
import android.util.Slog;
import android.view.IRecentsAnimationRunner;
import android.window.TaskSnapshot;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.internal.util.function.pooled.PooledPredicate;

public final class RecentsAnimation {
    public final ActivityStartController mActivityStartController;
    public final WindowProcessController mCaller;
    public final TaskDisplayArea mDefaultTaskDisplayArea;
    public ActivityRecord mLaunchedTargetActivity;
    public final ComponentName mRecentsComponent;
    public final String mRecentsFeatureId = null;
    public final int mRecentsUid;
    public Task mRestoreTargetBehindRootTask;
    public final ActivityTaskManagerService mService;
    public final int mTargetActivityType;
    public final Intent mTargetIntent;
    public final ActivityTaskSupervisor mTaskSupervisor;
    public final int mUserId;
    public final WindowManagerService mWindowManager;

    public RecentsAnimation(
            ActivityTaskManagerService activityTaskManagerService,
            ActivityTaskSupervisor activityTaskSupervisor,
            ActivityStartController activityStartController,
            WindowManagerService windowManagerService,
            Intent intent,
            ComponentName componentName,
            int i,
            WindowProcessController windowProcessController) {
        this.mService = activityTaskManagerService;
        this.mTaskSupervisor = activityTaskSupervisor;
        this.mDefaultTaskDisplayArea =
                activityTaskManagerService.mRootWindowContainer.mDefaultDisplay
                        .getDefaultTaskDisplayArea();
        this.mActivityStartController = activityStartController;
        this.mWindowManager = windowManagerService;
        this.mTargetIntent = intent;
        this.mRecentsComponent = componentName;
        this.mRecentsUid = i;
        this.mCaller = windowProcessController;
        this.mUserId = activityTaskManagerService.mAmInternal.getCurrentUserId();
        this.mTargetActivityType =
                (intent.getComponent() == null || !componentName.equals(intent.getComponent()))
                        ? 2
                        : 3;
    }

    public final ActivityRecord getTargetActivity(Task task) {
        if (task == null) {
            return null;
        }
        PooledPredicate obtainPredicate =
                PooledLambda.obtainPredicate(
                        new RecentsAnimation$$ExternalSyntheticLambda0(),
                        this,
                        PooledLambda.__(Task.class));
        Task task2 = task.getTask(obtainPredicate);
        obtainPredicate.recycle();
        if (task2 != null) {
            return task2.getTopNonFinishingActivity(true, true);
        }
        return null;
    }

    public final void onAnimationFinished(final int i, final boolean z) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (ProtoLogImpl_54989576.Cache.WM_DEBUG_RECENTS_ANIMATIONS_enabled[0]) {
                    ProtoLogImpl_54989576.d(
                            ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS,
                            5474198007669537235L,
                            4,
                            null,
                            String.valueOf(this.mWindowManager.mRecentsAnimationController),
                            Long.valueOf(i));
                }
                this.mDefaultTaskDisplayArea.mRootTaskOrderChangedCallbacks.remove(this);
                final RecentsAnimationController recentsAnimationController =
                        this.mWindowManager.mRecentsAnimationController;
                if (recentsAnimationController == null) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                if (i != 0) {
                    this.mService.endPowerMode(1);
                }
                if (i == 1) {
                    this.mService.stopAppSwitches();
                }
                new Runnable() { // from class:
                    // com.android.server.wm.RecentsAnimation$$ExternalSyntheticLambda1
                    /* JADX WARN: Code restructure failed: missing block: B:16:0x0070, code lost:

                       if (r7.mRoot.isLayoutNeeded() != false) goto L22;
                    */
                    /* JADX WARN: Code restructure failed: missing block: B:17:0x0072, code lost:

                       r7.mRoot.performSurfacePlacement();
                    */
                    /* JADX WARN: Code restructure failed: missing block: B:18:0x0077, code lost:

                       r1.setProcessAnimating(false);
                       android.os.Trace.traceEnd(32);
                    */
                    /* JADX WARN: Code restructure failed: missing block: B:19:0x0172, code lost:

                       return;
                    */
                    /* JADX WARN: Code restructure failed: missing block: B:38:0x014c, code lost:

                       if (r7.mRoot.isLayoutNeeded() != false) goto L22;
                    */
                    /* JADX WARN: Code restructure failed: missing block: B:56:0x016e, code lost:

                       if (r7.mRoot.isLayoutNeeded() != false) goto L22;
                    */
                    @Override // java.lang.Runnable
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final void run() {
                        /*
                            Method dump skipped, instructions count: 404
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.android.server.wm.RecentsAnimation$$ExternalSyntheticLambda1.run():void");
                    }
                }.run();
                WindowManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void preloadRecentsActivity() {
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_RECENTS_ANIMATIONS_enabled;
        if (zArr[0]) {
            ProtoLogImpl_54989576.d(
                    ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS,
                    -3758280623533049031L,
                    0,
                    null,
                    String.valueOf(this.mTargetIntent));
        }
        ActivityRecord targetActivity =
                getTargetActivity(
                        this.mDefaultTaskDisplayArea.getRootTask(0, this.mTargetActivityType));
        if (targetActivity != null) {
            if (targetActivity.attachedToProcess()) {
                if (targetActivity.isVisibleRequested() || targetActivity.isTopRunningActivity()) {
                    return;
                }
                if (targetActivity.app.mCurProcState >= 16) {
                    Slog.v(
                            "RecentsAnimation",
                            "Skip preload recents for cached proc " + targetActivity.app);
                    return;
                } else {
                    targetActivity.ensureActivityConfiguration(true);
                    if (zArr[0]) {
                        ProtoLogImpl_54989576.d(
                                ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS,
                                -3365656764099317101L,
                                0,
                                null,
                                String.valueOf(targetActivity.getConfiguration()));
                    }
                }
            }
        } else {
            if (this.mDefaultTaskDisplayArea.getActivity(
                            new RecentsAnimation$$ExternalSyntheticLambda2(0), false)
                    == null) {
                return;
            }
            startRecentsActivityInBackground("preloadRecents");
            targetActivity =
                    getTargetActivity(
                            this.mDefaultTaskDisplayArea.getRootTask(0, this.mTargetActivityType));
            if (targetActivity == null) {
                Slog.w("RecentsAnimation", "Cannot start " + this.mTargetIntent);
                return;
            }
        }
        if (!targetActivity.attachedToProcess()) {
            if (zArr[0]) {
                ProtoLogImpl_54989576.d(
                        ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS,
                        -7165162073742035900L,
                        0,
                        null,
                        null);
            }
            this.mTaskSupervisor.startSpecificActivity(targetActivity, false, false);
            if (targetActivity.getDisplayContent() != null) {
                targetActivity
                        .getDisplayContent()
                        .mUnknownAppVisibilityController
                        .appRemovedOrHidden(targetActivity);
            }
        }
        if (targetActivity.finishing
                || !targetActivity.isAttached()
                || targetActivity.isState(
                        ActivityRecord.State.STOPPING, ActivityRecord.State.STOPPED)) {
            return;
        }
        targetActivity.addToStopping("preloadRecents", true, true);
    }

    public final void setProcessAnimating(boolean z) {
        WindowProcessController windowProcessController = this.mCaller;
        if (windowProcessController == null) {
            return;
        }
        if (z) {
            int i = windowProcessController.mAnimatingReasons;
            windowProcessController.mAnimatingReasons = i | 4;
            if (i == 0) {
                windowProcessController.mAtm.mH.post(
                        new WindowProcessController$$ExternalSyntheticLambda1(
                                windowProcessController, true));
            }
        } else {
            windowProcessController.removeAnimatingReason(4);
        }
        int i2 = this.mService.mDemoteTopAppReasons;
        this.mService.mDemoteTopAppReasons = z ? i2 | 2 : i2 & (-3);
        if (!z || this.mService.mTopApp == null) {
            return;
        }
        WindowProcessController windowProcessController2 = this.mService.mTopApp;
        ActivityTaskManagerService.H h = windowProcessController2.mAtm.mH;
        WindowProcessController$$ExternalSyntheticLambda2
                windowProcessController$$ExternalSyntheticLambda2 =
                        new WindowProcessController$$ExternalSyntheticLambda2();
        Boolean bool = Boolean.FALSE;
        h.sendMessage(
                PooledLambda.obtainMessage(
                        windowProcessController$$ExternalSyntheticLambda2,
                        windowProcessController2.mListener,
                        bool,
                        bool,
                        Boolean.TRUE));
    }

    public final void startRecentsActivity(
            IRecentsAnimationRunner iRecentsAnimationRunner, long j) {
        ActivityOptions activityOptions;
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_RECENTS_ANIMATIONS_enabled;
        if (zArr[0]) {
            ProtoLogImpl_54989576.d(
                    ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS,
                    -3403665718306852375L,
                    0,
                    null,
                    String.valueOf(this.mTargetIntent));
        }
        Trace.traceBegin(32L, "RecentsAnimation#startRecentsActivity");
        WindowManagerService windowManagerService = this.mWindowManager;
        RecentsAnimationController recentsAnimationController =
                windowManagerService.mRecentsAnimationController;
        if (recentsAnimationController != null) {
            if (recentsAnimationController.mCanceled) {
                recentsAnimationController.continueDeferredCancelAnimation();
            } else {
                recentsAnimationController.cancelAnimation(2, "startRecentsActivity", false);
            }
        }
        TaskDisplayArea taskDisplayArea = this.mDefaultTaskDisplayArea;
        int i = this.mTargetActivityType;
        Task rootTask = taskDisplayArea.getRootTask(0, i);
        ActivityRecord targetActivity = getTargetActivity(rootTask);
        boolean z = targetActivity != null;
        if (z) {
            Task rootTaskAbove = TaskDisplayArea.getRootTaskAbove(rootTask);
            this.mRestoreTargetBehindRootTask = rootTaskAbove;
            if (rootTaskAbove == null && rootTask.getTopMostTask() == targetActivity.task) {
                try {
                    iRecentsAnimationRunner.onAnimationCanceled(
                            (int[]) null, (TaskSnapshot[]) null);
                } catch (RemoteException e) {
                    Slog.e(
                            "RecentsAnimation",
                            "Failed to cancel recents animation before start",
                            e);
                }
                if (zArr[0]) {
                    ProtoLogImpl_54989576.d(
                            ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS,
                            -8325607672707336373L,
                            0,
                            null,
                            String.valueOf(rootTask));
                    return;
                }
                return;
            }
        }
        ActivityTaskManagerService activityTaskManagerService = this.mService;
        if (targetActivity == null || !targetActivity.isVisibleRequested()) {
            activityTaskManagerService.mRootWindowContainer.startPowerModeLaunchIfNeeded(
                    true, targetActivity);
        }
        ActivityTaskSupervisor activityTaskSupervisor = this.mTaskSupervisor;
        ActivityMetricsLogger.LaunchingState notifyActivityLaunching =
                activityTaskSupervisor.mActivityMetricsLogger.notifyActivityLaunching(
                        this.mTargetIntent, null, -1);
        setProcessAnimating(true);
        activityTaskManagerService.deferWindowLayout();
        try {
            try {
                if (z) {
                    taskDisplayArea.moveRootTaskBehindBottomMostVisibleRootTask(rootTask);
                    if (zArr[0]) {
                        ProtoLogImpl_54989576.d(
                                ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS,
                                -7278356485797757819L,
                                0,
                                null,
                                String.valueOf(rootTask),
                                String.valueOf(TaskDisplayArea.getRootTaskAbove(rootTask)));
                    }
                    Task task = targetActivity.task;
                    if (rootTask.getTopMostTask() != task) {
                        rootTask.positionChildAtTop(task);
                    }
                } else {
                    startRecentsActivityInBackground("startRecentsActivity_noTargetActivity");
                    Task rootTask2 = taskDisplayArea.getRootTask(0, i);
                    targetActivity = getTargetActivity(rootTask2);
                    taskDisplayArea.moveRootTaskBehindBottomMostVisibleRootTask(rootTask2);
                    if (zArr[0]) {
                        ProtoLogImpl_54989576.d(
                                ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS,
                                -7278356485797757819L,
                                0,
                                null,
                                String.valueOf(rootTask2),
                                String.valueOf(TaskDisplayArea.getRootTaskAbove(rootTask2)));
                    }
                    windowManagerService.prepareAppTransitionNone();
                    windowManagerService.executeAppTransition();
                    if (zArr[0]) {
                        ProtoLogImpl_54989576.d(
                                ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS,
                                1012359606301505741L,
                                0,
                                null,
                                String.valueOf(this.mTargetIntent));
                    }
                }
                ActivityRecord activityRecord = targetActivity;
                activityRecord.mLaunchTaskBehind = true;
                this.mLaunchedTargetActivity = activityRecord;
                activityRecord.intent.replaceExtras(this.mTargetIntent);
                this.mWindowManager.initializeRecentsAnimation(
                        this.mTargetActivityType,
                        iRecentsAnimationRunner,
                        this,
                        taskDisplayArea.mDisplayContent.mDisplayId,
                        activityTaskSupervisor.mRecentTasks.getRecentTaskIds(),
                        activityRecord);
                activityTaskManagerService.mRootWindowContainer.ensureActivitiesVisible();
                if (j > 0) {
                    ActivityOptions makeBasic = ActivityOptions.makeBasic();
                    makeBasic.setSourceInfo(4, j);
                    activityOptions = makeBasic;
                } else {
                    activityOptions = null;
                }
                activityTaskSupervisor.mActivityMetricsLogger.notifyActivityLaunched(
                        notifyActivityLaunching, 2, !z, activityRecord, activityOptions);
                if (!taskDisplayArea.mRootTaskOrderChangedCallbacks.contains(this)) {
                    taskDisplayArea.mRootTaskOrderChangedCallbacks.add(this);
                }
                activityTaskManagerService.continueWindowLayout();
                Trace.traceEnd(32L);
            } catch (Exception e2) {
                Slog.e("RecentsAnimation", "Failed to start recents activity", e2);
                throw e2;
            }
        } catch (Throwable th) {
            activityTaskManagerService.continueWindowLayout();
            Trace.traceEnd(32L);
            throw th;
        }
    }

    public final void startRecentsActivityInBackground(String str) {
        ActivityOptions makeBasic = ActivityOptions.makeBasic();
        makeBasic.setLaunchActivityType(this.mTargetActivityType);
        makeBasic.setAvoidMoveToFront();
        this.mTargetIntent.addFlags(268500992);
        ActivityStarter obtainStarter =
                this.mActivityStartController.obtainStarter(this.mTargetIntent, str);
        obtainStarter.mRequest.callingUid = this.mRecentsUid;
        String packageName = this.mRecentsComponent.getPackageName();
        ActivityStarter.Request request = obtainStarter.mRequest;
        request.callingPackage = packageName;
        request.callingFeatureId = this.mRecentsFeatureId;
        SafeActivityOptions safeActivityOptions = new SafeActivityOptions(makeBasic);
        ActivityStarter.Request request2 = obtainStarter.mRequest;
        request2.activityOptions = safeActivityOptions;
        request2.userId = this.mUserId;
        obtainStarter.execute();
    }
}
