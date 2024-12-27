package com.android.server.wm;

import android.R;
import android.content.res.ResourceId;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.util.EventLog;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.WindowInsets;
import android.window.BackAnimationAdapter;
import android.window.ITaskOrganizer;
import android.window.StartingWindowRemovalInfo;

import com.android.internal.policy.TransitionAnimation;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.window.flags.Flags;

import com.samsung.android.rune.CoreRune;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class BackNavigationController {
    public static int sDefaultAnimationResId;
    public static final boolean sPredictBackEnable =
            SystemProperties.getBoolean("persist.wm.debug.predictive_back", true);
    public AnimationHandler mAnimationHandler;
    public boolean mBackAnimationInProgress;
    public boolean mBackGestureFinished;
    public int mLastBackType;
    public
    BackNavigationController$AnimationHandler$ScheduleAnimationBuilder$$ExternalSyntheticLambda0
            mPendingAnimation;
    public AnimationHandler.ScheduleAnimationBuilder mPendingAnimationBuilder;
    public boolean mShowWallpaper;
    public Transition mWaitTransitionFinish;
    public WindowManagerService mWindowManagerService;
    public final NavigationMonitor mNavigationMonitor = new NavigationMonitor();
    public final ArrayList mTmpOpenApps = new ArrayList();
    public final ArrayList mTmpCloseApps = new ArrayList();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AnimationHandler {
        public BackWindowAnimationAdaptor mCloseAdaptor;
        public boolean mComposed;
        public ActivityRecord[] mOpenActivities;
        public BackWindowAnimationAdaptorWrapper mOpenAnimAdaptor;
        public final boolean mShowWindowlessSurface;
        public boolean mStartingSurfaceTargetMatch;
        public int mSwitchType = 0;
        public boolean mWaitTransition;
        public final WindowManagerService mWindowManagerService;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class BackWindowAnimationAdaptor implements AnimationAdapter {
            public RemoteAnimationTarget mAnimationTarget;
            public boolean mAppWindowDrawn;
            public final Rect mBounds;
            public SurfaceControl mCapturedLeash;
            public final boolean mIsOpen;
            public final int mSwitchType;
            public final WindowContainer mTarget;

            public BackWindowAnimationAdaptor(int i, WindowContainer windowContainer, boolean z) {
                Rect rect = new Rect();
                this.mBounds = rect;
                rect.set(windowContainer.getBounds());
                this.mTarget = windowContainer;
                this.mIsOpen = z;
                this.mSwitchType = i;
            }

            @Override // com.android.server.wm.AnimationAdapter
            public final void dump(PrintWriter printWriter, String str) {
                printWriter.print(str + "BackWindowAnimationAdaptor mCapturedLeash=");
                printWriter.print(this.mCapturedLeash);
                printWriter.println();
            }

            @Override // com.android.server.wm.AnimationAdapter
            public final void dumpDebug$1(ProtoOutputStream protoOutputStream) {}

            @Override // com.android.server.wm.AnimationAdapter
            public final long getDurationHint() {
                return 0L;
            }

            @Override // com.android.server.wm.AnimationAdapter
            public final boolean getShowWallpaper() {
                return false;
            }

            @Override // com.android.server.wm.AnimationAdapter
            public final long getStatusBarTransitionsStartTime() {
                return 0L;
            }

            @Override // com.android.server.wm.AnimationAdapter
            public final void onAnimationCancelled(SurfaceControl surfaceControl) {
                if (this.mCapturedLeash == surfaceControl) {
                    this.mCapturedLeash = null;
                }
            }

            @Override // com.android.server.wm.AnimationAdapter
            public final void startAnimation(
                    SurfaceControl surfaceControl,
                    SurfaceControl.Transaction transaction,
                    int i,
                    SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback) {
                this.mCapturedLeash = surfaceControl;
                RemoteAnimationTarget remoteAnimationTarget = this.mAnimationTarget;
                WindowContainer windowContainer = this.mTarget;
                if (remoteAnimationTarget == null) {
                    WindowState asWindowState = windowContainer.asWindowState();
                    ActivityRecord activityRecord =
                            asWindowState != null ? asWindowState.mActivityRecord : null;
                    Task asTask =
                            activityRecord != null ? activityRecord.task : windowContainer.asTask();
                    if (asTask == null && windowContainer.asTaskFragment() != null) {
                        asTask = windowContainer.asTaskFragment().getTask();
                        activityRecord =
                                windowContainer
                                        .asTaskFragment()
                                        .getTopNonFinishingActivity(true, true);
                    }
                    if (activityRecord == null) {
                        activityRecord =
                                asTask != null
                                        ? asTask.getTopNonFinishingActivity(true, true)
                                        : windowContainer.asActivityRecord();
                    }
                    if (asTask == null && activityRecord != null) {
                        asTask = activityRecord.task;
                    }
                    if (asTask == null || activityRecord == null) {
                        Slog.e(
                                "CoreBackPreview",
                                "createRemoteAnimationTarget fail " + windowContainer);
                    } else {
                        WindowState findMainWindow = activityRecord.findMainWindow(true);
                        Rect rect =
                                findMainWindow != null
                                        ? findMainWindow
                                                .getInsetsStateWithVisibilityOverride()
                                                .calculateInsets(
                                                        this.mBounds,
                                                        WindowInsets.Type.tappableElement(),
                                                        false)
                                                .toRect()
                                        : new Rect();
                        int i2 = !this.mIsOpen ? 1 : 0;
                        int i3 = asTask.mTaskId;
                        SurfaceControl surfaceControl2 = this.mCapturedLeash;
                        boolean z = !activityRecord.occludesParent(true);
                        Rect rect2 = new Rect();
                        int prefixOrderIndex = activityRecord.getPrefixOrderIndex();
                        Rect rect3 = this.mBounds;
                        Point point = new Point(rect3.left, rect3.top);
                        Rect rect4 = this.mBounds;
                        RemoteAnimationTarget remoteAnimationTarget2 =
                                new RemoteAnimationTarget(
                                        i3,
                                        i2,
                                        surfaceControl2,
                                        z,
                                        rect2,
                                        rect,
                                        prefixOrderIndex,
                                        point,
                                        rect4,
                                        rect4,
                                        asTask.getWindowConfiguration(),
                                        true,
                                        (SurfaceControl) null,
                                        (Rect) null,
                                        asTask.getTaskInfo(),
                                        activityRecord.checkEnterPictureInPictureAppOpsState());
                        this.mAnimationTarget = remoteAnimationTarget2;
                        if (CoreRune.FW_PREDICTIVE_BACK_ANIM) {
                            remoteAnimationTarget2.setDisplayId(activityRecord.getDisplayId());
                        }
                    }
                }
                WindowState asWindowState2 = windowContainer.asWindowState();
                if (asWindowState2 == null || this.mSwitchType != 3) {
                    return;
                }
                Rect rect5 = asWindowState2.mWindowFrames.mFrame;
                asWindowState2.transformFrameToSurfacePosition(rect5.left, rect5.top, new Point());
                transaction.setPosition(this.mCapturedLeash, r3.x, r3.y);
            }
        }

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class BackWindowAnimationAdaptorWrapper {
            public final BackWindowAnimationAdaptor[] mAdaptors;
            public SurfaceControl.Transaction mCloseTransaction;
            public final RemoteAnimationTarget mRemoteAnimationTarget;
            public int mRequestedStartingSurfaceId = -1;
            public SurfaceControl mStartingSurface;

            public BackWindowAnimationAdaptorWrapper(int i, WindowContainer... windowContainerArr) {
                RemoteAnimationTarget remoteAnimationTarget;
                this.mAdaptors = new BackWindowAnimationAdaptor[windowContainerArr.length];
                for (int length = windowContainerArr.length - 1; length >= 0; length--) {
                    this.mAdaptors[length] =
                            AnimationHandler.createAdaptor(i, windowContainerArr[length], true);
                }
                if (windowContainerArr.length > 1) {
                    Rect rect = new Rect();
                    BackWindowAnimationAdaptor[] backWindowAnimationAdaptorArr = this.mAdaptors;
                    for (int length2 = backWindowAnimationAdaptorArr.length - 1;
                            length2 >= 0;
                            length2--) {
                        rect.union(
                                backWindowAnimationAdaptorArr[length2]
                                        .mAnimationTarget
                                        .localBounds);
                    }
                    WindowContainer windowContainer = backWindowAnimationAdaptorArr[0].mTarget;
                    Task asTask =
                            windowContainer.asActivityRecord() != null
                                    ? windowContainer.asActivityRecord().task
                                    : windowContainer.asTask();
                    RemoteAnimationTarget remoteAnimationTarget2 =
                            backWindowAnimationAdaptorArr[0].mAnimationTarget;
                    SurfaceControl build =
                            new SurfaceControl.Builder()
                                    .setName("cross-animation-leash")
                                    .setContainerLayer()
                                    .setHidden(false)
                                    .setParent(asTask.mSurfaceControl)
                                    .setCallsite(
                                            "BackWindowAnimationAdaptorWrapper.getOrCreateAnimationTarget")
                                    .build();
                    SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                    this.mCloseTransaction = transaction;
                    transaction.reparent(build, null);
                    SurfaceControl.Transaction pendingTransaction =
                            windowContainer.getPendingTransaction();
                    pendingTransaction.setLayer(build, windowContainer.getParent().getLastLayer());
                    for (int length3 = backWindowAnimationAdaptorArr.length - 1;
                            length3 >= 0;
                            length3--) {
                        BackWindowAnimationAdaptor backWindowAnimationAdaptor =
                                backWindowAnimationAdaptorArr[length3];
                        pendingTransaction.reparent(
                                backWindowAnimationAdaptor.mAnimationTarget.leash, build);
                        RemoteAnimationTarget remoteAnimationTarget3 =
                                backWindowAnimationAdaptor.mAnimationTarget;
                        SurfaceControl surfaceControl = remoteAnimationTarget3.leash;
                        Rect rect2 = remoteAnimationTarget3.localBounds;
                        pendingTransaction.setPosition(surfaceControl, rect2.left, rect2.top);
                        WindowContainer windowContainer2 = backWindowAnimationAdaptor.mTarget;
                        WindowContainer parent = windowContainer2.getParent();
                        if (parent != null) {
                            this.mCloseTransaction.reparent(
                                    windowContainer2.getSurfaceControl(),
                                    parent.getSurfaceControl());
                        }
                    }
                    remoteAnimationTarget =
                            new RemoteAnimationTarget(
                                    remoteAnimationTarget2.taskId,
                                    remoteAnimationTarget2.mode,
                                    build,
                                    remoteAnimationTarget2.isTranslucent,
                                    remoteAnimationTarget2.clipRect,
                                    remoteAnimationTarget2.contentInsets,
                                    remoteAnimationTarget2.prefixOrderIndex,
                                    new Point(rect.left, rect.top),
                                    rect,
                                    rect,
                                    remoteAnimationTarget2.windowConfiguration,
                                    true,
                                    (SurfaceControl) null,
                                    (Rect) null,
                                    remoteAnimationTarget2.taskInfo,
                                    remoteAnimationTarget2.allowEnterPip);
                } else {
                    remoteAnimationTarget = this.mAdaptors[0].mAnimationTarget;
                }
                this.mRemoteAnimationTarget = remoteAnimationTarget;
            }

            public final void cleanUpWindowlessSurface(boolean z) {
                int i = this.mRequestedStartingSurfaceId;
                if (i == -1) {
                    return;
                }
                boolean z2 = !z;
                ITaskOrganizer iTaskOrganizer =
                        (ITaskOrganizer)
                                this.mAdaptors[0].mTarget.mWmService.mAtmService
                                        .mTaskOrganizerController.mTaskOrganizers.peekLast();
                if (iTaskOrganizer != null && i != 0) {
                    StartingWindowRemovalInfo startingWindowRemovalInfo =
                            new StartingWindowRemovalInfo();
                    startingWindowRemovalInfo.taskId = i;
                    startingWindowRemovalInfo.windowlessSurface = true;
                    startingWindowRemovalInfo.removeImmediately = z2;
                    startingWindowRemovalInfo.deferRemoveMode = 3;
                    try {
                        iTaskOrganizer.removeStartingWindow(startingWindowRemovalInfo);
                    } catch (RemoteException e) {
                        Slog.e(
                                "TaskOrganizerController",
                                "Exception sending removeWindowlessStartingSurface ",
                                e);
                    }
                }
                this.mRequestedStartingSurfaceId = -1;
                SurfaceControl surfaceControl = this.mStartingSurface;
                if (surfaceControl == null || !surfaceControl.isValid()) {
                    return;
                }
                this.mStartingSurface.release();
                this.mStartingSurface = null;
            }
        }

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class ScheduleAnimationBuilder {
            public final BackAnimationAdapter mBackAnimationAdapter;
            public WindowContainer mCloseTarget;
            public boolean mIsLaunchBehind;
            public final NavigationMonitor mNavigationMonitor;
            public WindowContainer[] mOpenTargets;

            public ScheduleAnimationBuilder(
                    BackAnimationAdapter backAnimationAdapter,
                    NavigationMonitor navigationMonitor) {
                this.mBackAnimationAdapter = backAnimationAdapter;
                this.mNavigationMonitor = navigationMonitor;
            }
        }

        public AnimationHandler(WindowManagerService windowManagerService) {
            boolean z = false;
            this.mWindowManagerService = windowManagerService;
            if (windowManagerService
                            .mContext
                            .getResources()
                            .getBoolean(R.bool.config_reverseDefaultRotation)
                    && Flags.activitySnapshotByDefault()) {
                z = true;
            }
            this.mShowWindowlessSurface = z;
        }

        public static BackWindowAnimationAdaptor createAdaptor(
                int i, WindowContainer windowContainer, boolean z) {
            TaskFragment taskFragment;
            BackWindowAnimationAdaptor backWindowAnimationAdaptor =
                    new BackWindowAnimationAdaptor(i, windowContainer, z);
            SurfaceControl.Transaction pendingTransaction = windowContainer.getPendingTransaction();
            if (z
                    && windowContainer.asActivityRecord() != null
                    && (taskFragment = windowContainer.asActivityRecord().getTaskFragment())
                            != null) {
                taskFragment.updateOrganizedTaskFragmentSurface();
                pendingTransaction.show(taskFragment.mSurfaceControl);
            }
            windowContainer.startAnimation(
                    pendingTransaction, backWindowAnimationAdaptor, false, 256);
            return backWindowAnimationAdaptor;
        }

        public static boolean isAnimateTarget(
                WindowContainer windowContainer, WindowContainer windowContainer2, int i) {
            if (i != 1) {
                if (i == 2) {
                    return windowContainer == windowContainer2
                            || (windowContainer.asTaskFragment() != null
                                    && windowContainer.hasChild(windowContainer2));
                }
                return false;
            }
            if (windowContainer.isActivityTypeHome() && windowContainer2.isActivityTypeHome()) {
                return true;
            }
            return windowContainer == windowContainer2
                    || (windowContainer2.asTask() != null
                            && windowContainer2.hasChild(windowContainer))
                    || (windowContainer2.asActivityRecord() != null
                            && windowContainer.hasChild(windowContainer2));
        }

        public final void clearBackAnimateTarget(boolean z) {
            if (this.mComposed) {
                this.mComposed = false;
                ActivityRecord[] activityRecordArr = this.mOpenActivities;
                if (activityRecordArr != null) {
                    for (int length = activityRecordArr.length - 1; length >= 0; length--) {
                        ActivityRecord activityRecord = this.mOpenActivities[length];
                        if (activityRecord.mDisplayContent.isFixedRotationLaunchingApp(
                                activityRecord)) {
                            activityRecord.mDisplayContent
                                    .continueUpdateOrientationForDiffOrienLaunchingApp();
                        }
                        if (activityRecord.mLaunchTaskBehind && activityRecord.isAttached()) {
                            activityRecord.mLaunchTaskBehind = false;
                            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_BACK_PREVIEW_enabled[0]) {
                                ProtoLogImpl_54989576.d(
                                        ProtoLogGroup.WM_DEBUG_BACK_PREVIEW,
                                        -4442170697458371588L,
                                        0,
                                        "Setting Activity.mLauncherTaskBehind to false."
                                            + " Activity=%s",
                                        String.valueOf(activityRecord));
                            }
                            if (z) {
                                activityRecord
                                        .mTaskSupervisor
                                        .mHandler
                                        .obtainMessage(212, activityRecord.token)
                                        .sendToTarget();
                                ActivitySnapshotController activitySnapshotController =
                                        activityRecord
                                                .mTransitionController
                                                .mSnapshotController
                                                .mActivitySnapshotController;
                                if (!activitySnapshotController.shouldDisableSnapshots()) {
                                    activitySnapshotController.mOnBackPressedActivities.clear();
                                }
                            }
                        }
                    }
                }
                BackWindowAnimationAdaptor backWindowAnimationAdaptor = this.mCloseAdaptor;
                if (backWindowAnimationAdaptor != null) {
                    backWindowAnimationAdaptor.mTarget.cancelAnimation();
                    this.mCloseAdaptor = null;
                }
                BackWindowAnimationAdaptorWrapper backWindowAnimationAdaptorWrapper =
                        this.mOpenAnimAdaptor;
                if (backWindowAnimationAdaptorWrapper != null) {
                    backWindowAnimationAdaptorWrapper.cleanUpWindowlessSurface(
                            this.mStartingSurfaceTargetMatch);
                    BackWindowAnimationAdaptor[] backWindowAnimationAdaptorArr =
                            backWindowAnimationAdaptorWrapper.mAdaptors;
                    for (int length2 = backWindowAnimationAdaptorArr.length - 1;
                            length2 >= 0;
                            length2--) {
                        backWindowAnimationAdaptorArr[length2].mTarget.cancelAnimation();
                    }
                    SurfaceControl.Transaction transaction =
                            backWindowAnimationAdaptorWrapper.mCloseTransaction;
                    if (transaction != null) {
                        transaction.apply();
                        backWindowAnimationAdaptorWrapper.mCloseTransaction = null;
                    }
                    this.mOpenAnimAdaptor = null;
                }
            }
            this.mWaitTransition = false;
            this.mStartingSurfaceTargetMatch = false;
            this.mSwitchType = 0;
            this.mOpenActivities = null;
        }

        public final boolean containTarget(ArrayList arrayList, boolean z) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                if (isTarget((WindowContainer) arrayList.get(size), z)) {
                    return true;
                }
            }
            return arrayList.isEmpty();
        }

        public final boolean hasTargetDetached() {
            if (!this.mComposed) {
                return false;
            }
            for (int length = this.mOpenAnimAdaptor.mAdaptors.length - 1; length >= 0; length--) {
                if (!this.mOpenAnimAdaptor.mAdaptors[length].mTarget.isAttached()) {
                    return true;
                }
            }
            return !this.mCloseAdaptor.mTarget.isAttached();
        }

        public final boolean isTarget(WindowContainer windowContainer, boolean z) {
            if (!this.mComposed) {
                return false;
            }
            if (!z) {
                return isAnimateTarget(
                        windowContainer, this.mCloseAdaptor.mTarget, this.mSwitchType);
            }
            for (int length = this.mOpenAnimAdaptor.mAdaptors.length - 1; length >= 0; length--) {
                if (isAnimateTarget(
                        windowContainer,
                        this.mOpenAnimAdaptor.mAdaptors[length].mTarget,
                        this.mSwitchType)) {
                    return true;
                }
            }
            return false;
        }

        public final void markStartingSurfaceMatch(SurfaceControl.Transaction transaction) {
            SurfaceControl surfaceControl;
            if (this.mStartingSurfaceTargetMatch) {
                return;
            }
            this.mStartingSurfaceTargetMatch = true;
            BackWindowAnimationAdaptorWrapper backWindowAnimationAdaptorWrapper =
                    this.mOpenAnimAdaptor;
            if (backWindowAnimationAdaptorWrapper.mRequestedStartingSurfaceId == -1
                    || (surfaceControl = backWindowAnimationAdaptorWrapper.mStartingSurface) == null
                    || !surfaceControl.isValid()) {
                return;
            }
            BackWindowAnimationAdaptor[] backWindowAnimationAdaptorArr =
                    backWindowAnimationAdaptorWrapper.mAdaptors;
            if (transaction == null) {
                transaction = backWindowAnimationAdaptorArr[0].mTarget.getPendingTransaction();
            }
            if (backWindowAnimationAdaptorArr.length != 1) {
                WindowContainer windowContainer = backWindowAnimationAdaptorArr[0].mTarget;
                Task asTask =
                        windowContainer.asActivityRecord() != null
                                ? windowContainer.asActivityRecord().task
                                : windowContainer.asTask();
                transaction.reparent(
                        backWindowAnimationAdaptorWrapper.mStartingSurface,
                        asTask != null
                                ? asTask.mSurfaceControl
                                : backWindowAnimationAdaptorArr[0].mTarget.getSurfaceControl());
            }
        }

        public final ScheduleAnimationBuilder prepareAnimation(
                int i,
                BackAnimationAdapter backAnimationAdapter,
                NavigationMonitor navigationMonitor,
                Task task,
                Task task2,
                ActivityRecord activityRecord,
                ArrayList arrayList,
                WindowContainer windowContainer) {
            ScheduleAnimationBuilder scheduleAnimationBuilder =
                    new ScheduleAnimationBuilder(backAnimationAdapter, navigationMonitor);
            if (i == 0) {
                scheduleAnimationBuilder.mCloseTarget = windowContainer;
                scheduleAnimationBuilder.mOpenTargets = new WindowContainer[] {activityRecord};
                scheduleAnimationBuilder.mIsLaunchBehind = false;
                return scheduleAnimationBuilder;
            }
            if (i == 1) {
                scheduleAnimationBuilder.mIsLaunchBehind = true;
                scheduleAnimationBuilder.mCloseTarget = task;
                scheduleAnimationBuilder.mOpenTargets = new WindowContainer[] {task2};
                return scheduleAnimationBuilder;
            }
            if (i == 2) {
                ActivityRecord[] activityRecordArr =
                        (ActivityRecord[]) arrayList.toArray(new ActivityRecord[arrayList.size()]);
                scheduleAnimationBuilder.mCloseTarget = activityRecord;
                scheduleAnimationBuilder.mOpenTargets = activityRecordArr;
                scheduleAnimationBuilder.mIsLaunchBehind = false;
                return scheduleAnimationBuilder;
            }
            if (i != 3) {
                return null;
            }
            scheduleAnimationBuilder.mCloseTarget = task;
            scheduleAnimationBuilder.mOpenTargets = new WindowContainer[] {task2};
            scheduleAnimationBuilder.mIsLaunchBehind = false;
            return scheduleAnimationBuilder;
        }

        public final String toString() {
            String str;
            StringBuilder sb = new StringBuilder("AnimationTargets{ openTarget= ");
            if (this.mOpenAnimAdaptor != null) {
                StringBuilder sb2 = new StringBuilder("{");
                for (int i = 0; i < this.mOpenAnimAdaptor.mAdaptors.length; i++) {
                    if (i > 0) {
                        sb2.append(',');
                    }
                    sb2.append(this.mOpenAnimAdaptor.mAdaptors[i].mTarget);
                }
                sb2.append("}");
                str = sb2.toString();
            } else {
                str = null;
            }
            sb.append(str);
            sb.append(" closeTarget= ");
            BackWindowAnimationAdaptor backWindowAnimationAdaptor = this.mCloseAdaptor;
            sb.append(
                    backWindowAnimationAdaptor != null ? backWindowAnimationAdaptor.mTarget : null);
            sb.append(" mSwitchType= ");
            sb.append(this.mSwitchType);
            sb.append(" mComposed= ");
            sb.append(this.mComposed);
            sb.append(" mWaitTransition= ");
            sb.append(this.mWaitTransition);
            sb.append('}');
            return sb.toString();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class NavigationMonitor {
        public WindowState mNavigatingWindow;
        public RemoteCallback mObserver;

        /* renamed from: -$$Nest$monTransitionReadyWhileNavigate, reason: not valid java name */
        public static void m1055$$Nest$monTransitionReadyWhileNavigate(
                NavigationMonitor navigationMonitor, ArrayList arrayList, ArrayList arrayList2) {
            if ((navigationMonitor.mNavigatingWindow == null || navigationMonitor.mObserver == null)
                    && !navigationMonitor.isMonitorAnimationOrTransition()) {
                return;
            }
            ArrayList arrayList3 = new ArrayList(arrayList);
            arrayList3.addAll(arrayList2);
            for (int size = arrayList3.size() - 1; size >= 0; size--) {
                if (((WindowContainer) arrayList3.get(size))
                        .hasChild(navigationMonitor.mNavigatingWindow)) {
                    navigationMonitor.cancelBackNavigating("transitionHappens");
                    return;
                }
            }
        }

        public NavigationMonitor() {}

        public final void cancelBackNavigating(String str) {
            RemoteCallback remoteCallback;
            EventLog.writeEvent(31100, str);
            if (this.mNavigatingWindow != null && (remoteCallback = this.mObserver) != null) {
                remoteCallback.sendResult((Bundle) null);
            }
            boolean isMonitorAnimationOrTransition = isMonitorAnimationOrTransition();
            BackNavigationController backNavigationController = BackNavigationController.this;
            if (isMonitorAnimationOrTransition) {
                backNavigationController.clearBackAnimations(true);
            }
            int i = BackNavigationController.sDefaultAnimationResId;
            backNavigationController.cancelPendingAnimation();
        }

        public final boolean isMonitorAnimationOrTransition() {
            if (this.mNavigatingWindow != null) {
                AnimationHandler animationHandler = BackNavigationController.this.mAnimationHandler;
                if (animationHandler.mComposed || animationHandler.mWaitTransition) {
                    return true;
                }
            }
            return false;
        }
    }

    public static void addPreviousAdjacentActivityIfExist(
            ActivityRecord activityRecord, ArrayList arrayList) {
        TaskFragment taskFragment;
        ActivityRecord topNonFinishingActivity;
        TaskFragment taskFragment2 = activityRecord.getTaskFragment();
        if (taskFragment2 == null
                || taskFragment2.asTask() != null
                || (taskFragment = taskFragment2.mAdjacentTaskFragment) == null
                || taskFragment.asTask() != null
                || (topNonFinishingActivity = taskFragment.getTopNonFinishingActivity(true, true))
                        == null) {
            return;
        }
        arrayList.add(topNonFinishingActivity);
    }

    public static boolean getAnimatablePrevActivities(
            Task task, ActivityRecord activityRecord, ArrayList arrayList) {
        TaskOrganizerController taskOrganizerController =
                activityRecord.mAtmService.mTaskOrganizerController;
        Task rootTask = task.getRootTask();
        taskOrganizerController.getClass();
        if (rootTask != null
                && rootTask.isOrganized()
                && taskOrganizerController.mInterceptBackPressedOnRootTasks.contains(
                        Integer.valueOf(rootTask.mTaskId))) {
            return false;
        }
        ActivityRecord rootActivity = task.getRootActivity(false, true);
        if (rootActivity != null
                && ActivityClientController.shouldMoveTaskToBack(activityRecord, rootActivity)) {
            return true;
        }
        ActivityRecord activity =
                task.getActivity(
                        new BackNavigationController$$ExternalSyntheticLambda6(1),
                        activityRecord,
                        false,
                        true);
        TaskFragment taskFragment = activityRecord.getTaskFragment();
        if (taskFragment != null && taskFragment.asTask() == null) {
            if (activity != null && taskFragment.hasChild(activity)) {
                arrayList.add(activity);
                return true;
            }
            TaskFragment taskFragment2 = taskFragment.mAdjacentTaskFragment;
            if (taskFragment2 != null) {
                if (taskFragment2.mCompanionTaskFragment != taskFragment) {
                    return false;
                }
                WindowContainer parent = taskFragment.getParent();
                if (parent.mChildren.indexOf(taskFragment)
                        >= parent.mChildren.indexOf(taskFragment2)) {
                    taskFragment = taskFragment2;
                }
                return task.getActivity(
                                new BackNavigationController$$ExternalSyntheticLambda6(4),
                                taskFragment.getTopNonFinishingActivity(true, true),
                                false,
                                true)
                        == null;
            }
            int indexOf = task.mChildren.indexOf(taskFragment);
            TaskFragment asTaskFragment =
                    indexOf <= 0
                            ? null
                            : ((WindowContainer) task.mChildren.get(indexOf - 1)).asTaskFragment();
            if (asTaskFragment != null && asTaskFragment.mCompanionTaskFragment == taskFragment) {
                ActivityRecord activity2 =
                        task.getActivity(
                                new BackNavigationController$$ExternalSyntheticLambda6(3),
                                asTaskFragment.getActivity(
                                        new BackNavigationController$$ExternalSyntheticLambda6(2),
                                        false),
                                false,
                                true);
                if (activity2 != null) {
                    arrayList.add(activity2);
                    addPreviousAdjacentActivityIfExist(activity2, arrayList);
                }
                return true;
            }
        }
        if (activity == null) {
            return true;
        }
        addPreviousAdjacentActivityIfExist(activity, arrayList);
        arrayList.add(activity);
        return true;
    }

    public static boolean hasTranslucentActivity(
            ActivityRecord activityRecord, ArrayList arrayList) {
        if (!activityRecord.occludesParent(false) || activityRecord.showWallpaper()) {
            return true;
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            ActivityRecord activityRecord2 = (ActivityRecord) arrayList.get(size);
            if (!activityRecord2.occludesParent(false) || activityRecord2.hasWallpaper()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAllActivitiesCanShowWhenLocked(ArrayList arrayList) {
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            if (!((ActivityRecord) arrayList.get(size)).canShowWhenLocked()) {
                return false;
            }
        }
        return !arrayList.isEmpty();
    }

    public static boolean isCustomizeExitAnimation(WindowState windowState) {
        if (Objects.equals(windowState.mAttrs.packageName, "android")
                || windowState.mAttrs.windowAnimations == 0) {
            return false;
        }
        TransitionAnimation transitionAnimation =
                windowState.getDisplayContent().mAppTransition.mTransitionAnimation;
        int animationResId = transitionAnimation.getAnimationResId(windowState.mAttrs, 7, 0);
        if (!ResourceId.isValid(animationResId)) {
            return false;
        }
        if (sDefaultAnimationResId == 0) {
            sDefaultAnimationResId = transitionAnimation.getDefaultAnimationResId(7, 0);
        }
        return sDefaultAnimationResId != animationResId;
    }

    public static void setLaunchBehind(ActivityRecord activityRecord) {
        if (!activityRecord.isVisibleRequested()) {
            activityRecord.commitVisibility(true, false, false);
            ActivitySnapshotController activitySnapshotController =
                    activityRecord
                            .mTransitionController
                            .mSnapshotController
                            .mActivitySnapshotController;
            if (!activitySnapshotController.shouldDisableSnapshots()) {
                activitySnapshotController.mOnBackPressedActivities.add(activityRecord);
            }
        }
        activityRecord.mLaunchTaskBehind = true;
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_BACK_PREVIEW_enabled[0]) {
            ProtoLogImpl_54989576.d(
                    ProtoLogGroup.WM_DEBUG_BACK_PREVIEW,
                    2077221835543623088L,
                    0,
                    "Setting Activity.mLauncherTaskBehind to true. Activity=%s",
                    String.valueOf(activityRecord));
        }
        activityRecord.mTaskSupervisor.mStoppingActivities.remove(activityRecord);
        activityRecord.getDisplayContent().ensureActivitiesVisible(true, null);
    }

    public final void cancelPendingAnimation() {
        AnimationHandler.ScheduleAnimationBuilder scheduleAnimationBuilder =
                this.mPendingAnimationBuilder;
        if (scheduleAnimationBuilder == null) {
            return;
        }
        try {
            scheduleAnimationBuilder.mBackAnimationAdapter.getRunner().onAnimationCancelled();
        } catch (RemoteException e) {
            Slog.e("CoreBackPreview", "Remote animation gone", e);
        }
        this.mPendingAnimationBuilder = null;
    }

    public final void clearBackAnimations(boolean z) {
        this.mAnimationHandler.clearBackAnimateTarget(z);
        this.mNavigationMonitor.mNavigatingWindow = null;
        this.mWaitTransitionFinish = null;
    }

    public final boolean isMonitoringTransition() {
        if (!this.mAnimationHandler.mComposed) {
            NavigationMonitor navigationMonitor = this.mNavigationMonitor;
            if (navigationMonitor.mNavigatingWindow == null
                    || navigationMonitor.mObserver == null) {
                return false;
            }
        }
        return true;
    }

    public final void onBackNavigationDone(int i, Bundle bundle) {
        ActivityRecord[] activityRecordArr;
        int i2;
        if (bundle == null) {
            return;
        }
        if (bundle.containsKey("NavigationFinished")) {
            boolean z = bundle.getBoolean("NavigationFinished");
            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_BACK_PREVIEW_enabled[0]) {
                ProtoLogImpl_54989576.d(
                        ProtoLogGroup.WM_DEBUG_BACK_PREVIEW,
                        267946503010201613L,
                        12,
                        "onBackNavigationDone backType=%s, triggerBack=%b",
                        String.valueOf(i),
                        Boolean.valueOf(z));
            }
            WindowManagerGlobalLock windowManagerGlobalLock =
                    this.mWindowManagerService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    this.mNavigationMonitor.mObserver = null;
                    this.mBackAnimationInProgress = false;
                    this.mShowWallpaper = false;
                    this.mPendingAnimation = null;
                    this.mPendingAnimationBuilder = null;
                    if (CoreRune.FW_PREDICTIVE_BACK_ANIM_BUG_FIX) {
                        this.mBackGestureFinished = false;
                    }
                } finally {
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }
        if (bundle.getBoolean("GestureFinished")) {
            WindowManagerGlobalLock windowManagerGlobalLock2 =
                    this.mWindowManagerService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock2) {
                try {
                    if (CoreRune.FW_PREDICTIVE_BACK_ANIM_BUG_FIX) {
                        this.mBackGestureFinished = true;
                    }
                    AnimationHandler animationHandler = this.mAnimationHandler;
                    if (animationHandler.mComposed
                            && !animationHandler.mWaitTransition
                            && (activityRecordArr = animationHandler.mOpenActivities) != null
                            && ((i2 = animationHandler.mSwitchType) == 1 || i2 == 2)) {
                        for (int length = activityRecordArr.length - 1; length >= 0; length--) {
                            ActivityRecord activityRecord =
                                    this.mAnimationHandler.mOpenActivities[length];
                            if (!activityRecord.mLaunchTaskBehind) {
                                setLaunchBehind(activityRecord);
                            }
                        }
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                } finally {
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:117:0x02a2  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x02b9  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x02cf  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0322  */
    /* JADX WARN: Removed duplicated region for block: B:139:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:140:0x02d1  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x0208  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x0128  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x011f  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0175  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0206  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0213  */
    /* JADX WARN: Type inference failed for: r0v33, types: [com.android.server.wm.BackNavigationController$AnimationHandler$ScheduleAnimationBuilder$1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void scheduleAnimation(
            final com.android.server.wm.BackNavigationController.AnimationHandler
                            .ScheduleAnimationBuilder
                    r21) {
        /*
            Method dump skipped, instructions count: 823
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.BackNavigationController.scheduleAnimation(com.android.server.wm.BackNavigationController$AnimationHandler$ScheduleAnimationBuilder):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:192:0x026f, code lost:

       if (r9.isEmpty() != false) goto L165;
    */
    /* JADX WARN: Code restructure failed: missing block: B:193:0x0271, code lost:

       if (r12 == false) goto L151;
    */
    /* JADX WARN: Code restructure failed: missing block: B:195:0x0277, code lost:

       if (isAllActivitiesCanShowWhenLocked(r9) != false) goto L151;
    */
    /* JADX WARN: Code restructure failed: missing block: B:198:0x027e, code lost:

       if (r1.isActivityTypeHome() == false) goto L160;
    */
    /* JADX WARN: Code restructure failed: missing block: B:199:0x0280, code lost:

       r10 = r1.getTopNonFinishingActivity(true, true);
    */
    /* JADX WARN: Code restructure failed: missing block: B:200:0x0284, code lost:

       if (r10 == null) goto L158;
    */
    /* JADX WARN: Code restructure failed: missing block: B:202:0x028a, code lost:

       if (r10.hasWallpaper() == false) goto L158;
    */
    /* JADX WARN: Code restructure failed: missing block: B:203:0x028c, code lost:

       r10 = true;
    */
    /* JADX WARN: Code restructure failed: missing block: B:204:0x028f, code lost:

       r28.mShowWallpaper = r10;
       r17 = r1;
       r1 = r8;
       r11 = 1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:205:0x028e, code lost:

       r10 = false;
    */
    /* JADX WARN: Code restructure failed: missing block: B:206:0x0296, code lost:

       r10 = r1.getParent().asTask();
       r12 = r8.getParent().asTask();
    */
    /* JADX WARN: Code restructure failed: missing block: B:207:0x02aa, code lost:

       if (r1.inMultiWindowMode() == false) goto L163;
    */
    /* JADX WARN: Code restructure failed: missing block: B:208:0x02ac, code lost:

       if (r10 != r12) goto L165;
    */
    /* JADX WARN: Code restructure failed: missing block: B:210:0x02b2, code lost:

       if (hasTranslucentActivity(r2, r9) == false) goto L166;
    */
    /* JADX WARN: Code restructure failed: missing block: B:211:0x02b8, code lost:

       r17 = r1;
       r11 = 3;
       r1 = r1;
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:102:0x0320 A[Catch: all -> 0x0035, TryCatch #0 {all -> 0x0035, blocks: (B:8:0x0017, B:10:0x001d, B:11:0x0030, B:14:0x0038, B:16:0x003f, B:18:0x0045, B:19:0x0053, B:21:0x005d, B:24:0x006b, B:25:0x0072, B:28:0x0077, B:30:0x007f, B:32:0x0085, B:33:0x008c, B:36:0x0091, B:38:0x0098, B:40:0x009e, B:41:0x00ac, B:44:0x00b1, B:46:0x00b9, B:48:0x00c7, B:50:0x00cd, B:51:0x00db, B:55:0x00c1, B:57:0x00e0, B:59:0x00e4, B:60:0x00eb, B:63:0x00f0, B:66:0x00fa, B:69:0x011b, B:71:0x012c, B:75:0x0155, B:77:0x015b, B:79:0x0161, B:82:0x0167, B:85:0x02bb, B:87:0x02c4, B:89:0x02ca, B:90:0x02e3, B:92:0x02e9, B:93:0x02ef, B:102:0x0320, B:105:0x033a, B:107:0x033e, B:109:0x0344, B:111:0x034c, B:113:0x035b, B:115:0x035f, B:117:0x0363, B:118:0x0372, B:120:0x037b, B:123:0x0387, B:125:0x038c, B:126:0x039f, B:127:0x03a5, B:130:0x039a, B:133:0x0186, B:135:0x0190, B:138:0x019f, B:141:0x01a6, B:144:0x01ae, B:146:0x01b4, B:148:0x01bb, B:154:0x01d5, B:156:0x01db, B:158:0x01e1, B:160:0x01e7, B:164:0x01f2, B:166:0x01f8, B:167:0x0201, B:169:0x0205, B:170:0x0210, B:150:0x01cb, B:173:0x01ce, B:174:0x022f, B:177:0x023b, B:179:0x0249, B:181:0x0251, B:183:0x025a, B:186:0x025f, B:189:0x0266, B:191:0x026b, B:194:0x0273, B:197:0x027a, B:199:0x0280, B:201:0x0286, B:204:0x028f, B:206:0x0296, B:209:0x02ae, B:214:0x03aa, B:215:0x03c0), top: B:7:0x0017 }] */
    /* JADX WARN: Removed duplicated region for block: B:125:0x038c A[Catch: all -> 0x0035, TryCatch #0 {all -> 0x0035, blocks: (B:8:0x0017, B:10:0x001d, B:11:0x0030, B:14:0x0038, B:16:0x003f, B:18:0x0045, B:19:0x0053, B:21:0x005d, B:24:0x006b, B:25:0x0072, B:28:0x0077, B:30:0x007f, B:32:0x0085, B:33:0x008c, B:36:0x0091, B:38:0x0098, B:40:0x009e, B:41:0x00ac, B:44:0x00b1, B:46:0x00b9, B:48:0x00c7, B:50:0x00cd, B:51:0x00db, B:55:0x00c1, B:57:0x00e0, B:59:0x00e4, B:60:0x00eb, B:63:0x00f0, B:66:0x00fa, B:69:0x011b, B:71:0x012c, B:75:0x0155, B:77:0x015b, B:79:0x0161, B:82:0x0167, B:85:0x02bb, B:87:0x02c4, B:89:0x02ca, B:90:0x02e3, B:92:0x02e9, B:93:0x02ef, B:102:0x0320, B:105:0x033a, B:107:0x033e, B:109:0x0344, B:111:0x034c, B:113:0x035b, B:115:0x035f, B:117:0x0363, B:118:0x0372, B:120:0x037b, B:123:0x0387, B:125:0x038c, B:126:0x039f, B:127:0x03a5, B:130:0x039a, B:133:0x0186, B:135:0x0190, B:138:0x019f, B:141:0x01a6, B:144:0x01ae, B:146:0x01b4, B:148:0x01bb, B:154:0x01d5, B:156:0x01db, B:158:0x01e1, B:160:0x01e7, B:164:0x01f2, B:166:0x01f8, B:167:0x0201, B:169:0x0205, B:170:0x0210, B:150:0x01cb, B:173:0x01ce, B:174:0x022f, B:177:0x023b, B:179:0x0249, B:181:0x0251, B:183:0x025a, B:186:0x025f, B:189:0x0266, B:191:0x026b, B:194:0x0273, B:197:0x027a, B:199:0x0280, B:201:0x0286, B:204:0x028f, B:206:0x0296, B:209:0x02ae, B:214:0x03aa, B:215:0x03c0), top: B:7:0x0017 }] */
    /* JADX WARN: Removed duplicated region for block: B:130:0x039a A[Catch: all -> 0x0035, TryCatch #0 {all -> 0x0035, blocks: (B:8:0x0017, B:10:0x001d, B:11:0x0030, B:14:0x0038, B:16:0x003f, B:18:0x0045, B:19:0x0053, B:21:0x005d, B:24:0x006b, B:25:0x0072, B:28:0x0077, B:30:0x007f, B:32:0x0085, B:33:0x008c, B:36:0x0091, B:38:0x0098, B:40:0x009e, B:41:0x00ac, B:44:0x00b1, B:46:0x00b9, B:48:0x00c7, B:50:0x00cd, B:51:0x00db, B:55:0x00c1, B:57:0x00e0, B:59:0x00e4, B:60:0x00eb, B:63:0x00f0, B:66:0x00fa, B:69:0x011b, B:71:0x012c, B:75:0x0155, B:77:0x015b, B:79:0x0161, B:82:0x0167, B:85:0x02bb, B:87:0x02c4, B:89:0x02ca, B:90:0x02e3, B:92:0x02e9, B:93:0x02ef, B:102:0x0320, B:105:0x033a, B:107:0x033e, B:109:0x0344, B:111:0x034c, B:113:0x035b, B:115:0x035f, B:117:0x0363, B:118:0x0372, B:120:0x037b, B:123:0x0387, B:125:0x038c, B:126:0x039f, B:127:0x03a5, B:130:0x039a, B:133:0x0186, B:135:0x0190, B:138:0x019f, B:141:0x01a6, B:144:0x01ae, B:146:0x01b4, B:148:0x01bb, B:154:0x01d5, B:156:0x01db, B:158:0x01e1, B:160:0x01e7, B:164:0x01f2, B:166:0x01f8, B:167:0x0201, B:169:0x0205, B:170:0x0210, B:150:0x01cb, B:173:0x01ce, B:174:0x022f, B:177:0x023b, B:179:0x0249, B:181:0x0251, B:183:0x025a, B:186:0x025f, B:189:0x0266, B:191:0x026b, B:194:0x0273, B:197:0x027a, B:199:0x0280, B:201:0x0286, B:204:0x028f, B:206:0x0296, B:209:0x02ae, B:214:0x03aa, B:215:0x03c0), top: B:7:0x0017 }] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x02c4 A[Catch: all -> 0x0035, TryCatch #0 {all -> 0x0035, blocks: (B:8:0x0017, B:10:0x001d, B:11:0x0030, B:14:0x0038, B:16:0x003f, B:18:0x0045, B:19:0x0053, B:21:0x005d, B:24:0x006b, B:25:0x0072, B:28:0x0077, B:30:0x007f, B:32:0x0085, B:33:0x008c, B:36:0x0091, B:38:0x0098, B:40:0x009e, B:41:0x00ac, B:44:0x00b1, B:46:0x00b9, B:48:0x00c7, B:50:0x00cd, B:51:0x00db, B:55:0x00c1, B:57:0x00e0, B:59:0x00e4, B:60:0x00eb, B:63:0x00f0, B:66:0x00fa, B:69:0x011b, B:71:0x012c, B:75:0x0155, B:77:0x015b, B:79:0x0161, B:82:0x0167, B:85:0x02bb, B:87:0x02c4, B:89:0x02ca, B:90:0x02e3, B:92:0x02e9, B:93:0x02ef, B:102:0x0320, B:105:0x033a, B:107:0x033e, B:109:0x0344, B:111:0x034c, B:113:0x035b, B:115:0x035f, B:117:0x0363, B:118:0x0372, B:120:0x037b, B:123:0x0387, B:125:0x038c, B:126:0x039f, B:127:0x03a5, B:130:0x039a, B:133:0x0186, B:135:0x0190, B:138:0x019f, B:141:0x01a6, B:144:0x01ae, B:146:0x01b4, B:148:0x01bb, B:154:0x01d5, B:156:0x01db, B:158:0x01e1, B:160:0x01e7, B:164:0x01f2, B:166:0x01f8, B:167:0x0201, B:169:0x0205, B:170:0x0210, B:150:0x01cb, B:173:0x01ce, B:174:0x022f, B:177:0x023b, B:179:0x0249, B:181:0x0251, B:183:0x025a, B:186:0x025f, B:189:0x0266, B:191:0x026b, B:194:0x0273, B:197:0x027a, B:199:0x0280, B:201:0x0286, B:204:0x028f, B:206:0x0296, B:209:0x02ae, B:214:0x03aa, B:215:0x03c0), top: B:7:0x0017 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.window.BackNavigationInfo startBackNavigation(
            android.os.RemoteCallback r29, android.window.BackAnimationAdapter r30) {
        /*
            Method dump skipped, instructions count: 970
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.BackNavigationController.startBackNavigation(android.os.RemoteCallback,"
                    + " android.window.BackAnimationAdapter):android.window.BackNavigationInfo");
    }
}
