package com.android.server.wm;

import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.WindowConfiguration;
import android.app.servertransaction.PauseActivityItem;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.IBinder;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.EventLog;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import android.view.DisplayInfo;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.WindowInsets;
import android.window.ITaskFragmentOrganizer;
import android.window.TaskFragmentAnimationParams;
import android.window.TaskFragmentInfo;
import android.window.WindowContainerToken;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService;
import com.android.server.pm.pkg.AndroidPackage;

import com.samsung.android.rune.CoreRune;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class TaskFragment extends WindowContainer {
    public TaskFragment mAdjacentTaskFragment;
    public boolean mAllowTransitionWhenEmpty;
    public TaskFragmentAnimationParams mAnimationParams;
    public final ActivityTaskManagerService mAtmService;
    public boolean mClearedForReorderActivityToFront;
    public boolean mClearedTaskForReuse;
    public boolean mClearedTaskFragmentForPip;
    public TaskFragment mCompanionTaskFragment;
    boolean mCreatedByOrganizer;
    public boolean mDelayLastActivityRemoval;
    public boolean mDelayOrganizedTaskFragmentSurfaceUpdate;
    public final Dimmer mDimmer;
    public int mEmbeddedDimArea;
    public final EnsureActivitiesVisibleHelper mEnsureActivitiesVisibleHelper;
    public int mForceHiddenFlags;
    public boolean mForceTranslucent;
    public final IBinder mFragmentToken;
    public final boolean mIsEmbedded;
    public boolean mIsPlaceholderTaskFragment;
    public boolean mIsRemovalRequested;
    public boolean mIsolatedNav;
    public ActivityRecord mLastPausedActivity;
    public final Point mLastSurfaceSize;
    public int mMinHeight;
    public int mMinWidth;
    public boolean mMoveToBottomIfClearWhenLaunch;
    public ActivityRecord mPausingActivity;
    public boolean mPinned;
    public final Rect mRelativeEmbeddedBounds;
    public ActivityRecord mResumedActivity;
    public final RootWindowContainer mRootWindowContainer;
    public boolean mTaskFragmentAppearedSent;
    public ITaskFragmentOrganizer mTaskFragmentOrganizer;
    public final TaskFragmentOrganizerController mTaskFragmentOrganizerController;
    public String mTaskFragmentOrganizerProcessName;
    public int mTaskFragmentOrganizerUid;
    public boolean mTaskFragmentVanishedSent;
    public final ActivityTaskSupervisor mTaskSupervisor;
    public final Rect mTmpAbsBounds;
    public final Rect mTmpBounds;
    public final Rect mTmpFullBounds;
    public final Rect mTmpNonDecorBounds;
    public final Rect mTmpStableBounds;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ConfigOverrideHint {
        public Rect mParentAppBoundsOverride;
        public AppCompatDisplayInsets mTmpCompatInsets;
        public int mTmpOverrideConfigOrientation;
        public DisplayInfo mTmpOverrideDisplayInfo;
        public boolean mUseOverrideInsetsForConfig;
    }

    public TaskFragment(
            ActivityTaskManagerService activityTaskManagerService,
            IBinder iBinder,
            boolean z,
            boolean z2) {
        super(activityTaskManagerService.mWindowManager);
        this.mDimmer = new Dimmer(this);
        this.mEmbeddedDimArea = 0;
        this.mPausingActivity = null;
        this.mLastPausedActivity = null;
        this.mResumedActivity = null;
        this.mIsPlaceholderTaskFragment = false;
        this.mTaskFragmentOrganizerUid = -1;
        this.mAnimationParams = TaskFragmentAnimationParams.DEFAULT;
        this.mForceHiddenFlags = 0;
        this.mForceTranslucent = false;
        this.mLastSurfaceSize = new Point();
        this.mTmpBounds = new Rect();
        this.mTmpAbsBounds = new Rect();
        this.mTmpFullBounds = new Rect();
        this.mTmpStableBounds = new Rect();
        this.mTmpNonDecorBounds = new Rect();
        this.mEnsureActivitiesVisibleHelper = new EnsureActivitiesVisibleHelper(this);
        this.mAtmService = activityTaskManagerService;
        this.mTaskSupervisor = activityTaskManagerService.mTaskSupervisor;
        this.mRootWindowContainer = activityTaskManagerService.mRootWindowContainer;
        this.mCreatedByOrganizer = z;
        this.mIsEmbedded = z2;
        this.mRelativeEmbeddedBounds = z2 ? new Rect() : null;
        this.mTaskFragmentOrganizerController =
                activityTaskManagerService
                        .mWindowOrganizerController
                        .mTaskFragmentOrganizerController;
        this.mFragmentToken = iBinder;
        this.mRemoteToken = new WindowContainer.RemoteToken(this);
    }

    public static int calculateEmbedActivityMode(Task task, Rect rect) {
        Rect bounds = task.getBounds();
        if (bounds.equals(rect) || rect.isEmpty()) {
            return 1;
        }
        int i = bounds.left;
        int i2 = rect.left;
        if (i == i2 && bounds.right != rect.right) {
            return 2;
        }
        if (bounds.right == rect.right && i != i2) {
            return 3;
        }
        int i3 = bounds.top;
        int i4 = rect.top;
        if (i3 != i4 || bounds.bottom == rect.bottom) {
            return (bounds.bottom != rect.bottom || i3 == i4) ? 0 : 5;
        }
        return 4;
    }

    public static void intersectWithInsetsIfFits(Rect rect, Rect rect2, Rect rect3) {
        int i = rect.right;
        int i2 = rect2.right;
        if (i <= i2) {
            rect.right = Math.min(i2 - rect3.right, i);
        }
        int i3 = rect.bottom;
        int i4 = rect2.bottom;
        if (i3 <= i4) {
            rect.bottom = Math.min(i4 - rect3.bottom, i3);
        }
        int i5 = rect.left;
        int i6 = rect2.left;
        if (i5 >= i6) {
            rect.left = Math.max(i6 + rect3.left, i5);
        }
        int i7 = rect.top;
        int i8 = rect2.top;
        if (i7 >= i8) {
            rect.top = Math.max(i8 + rect3.top, i7);
        }
    }

    public static boolean isFullyTrustedEmbedding(int i, ActivityRecord activityRecord) {
        if (UserHandle.getAppId(i) != 1000 && !activityRecord.isUid(i)) {
            Boolean bool = ActivityTaskManagerService.sIsPip2ExperimentEnabled;
            if (ActivityManagerService.checkComponentPermission(
                            -1, i, "android.permission.MANAGE_ACTIVITY_TASKS", 0, -1, true)
                    != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isTranslucent(
            WindowContainer windowContainer, ActivityRecord activityRecord) {
        if (windowContainer.asTaskFragment() != null) {
            return windowContainer.asTaskFragment().isTranslucent(activityRecord);
        }
        if (windowContainer.asActivityRecord() != null) {
            return !windowContainer.asActivityRecord().occludesParent(false);
        }
        return false;
    }

    @Override // com.android.server.wm.WindowContainer
    public void addChild(WindowContainer windowContainer, int i) {
        topRunningActivity(false);
        this.mClearedTaskForReuse = false;
        this.mClearedTaskFragmentForPip = false;
        this.mClearedForReorderActivityToFront = false;
        ActivityRecord asActivityRecord = windowContainer.asActivityRecord();
        boolean z = asActivityRecord != null;
        Task task = z ? getTask() : null;
        boolean z2 = (task == null || task.getTopMostActivity() == null) ? false : true;
        int activityType = task != null ? task.getActivityType() : 0;
        super.addChild(windowContainer, i);
        if (z && task != null) {
            asActivityRecord.inHistory = true;
            if (!task.isLeafTask()) {
                Slog.w("ActivityTaskManager", "onDescendantActivityAdded on non-leaf task " + task);
            }
            if (z2) {
                asActivityRecord.setActivityType(activityType);
            } else {
                int activityType2 =
                        asActivityRecord
                                .getRequestedOverrideConfiguration()
                                .windowConfiguration
                                .getActivityType();
                if (activityType2 == 0) {
                    if (activityType == 0) {
                        activityType = 1;
                    }
                    asActivityRecord
                            .getRequestedOverrideConfiguration()
                            .windowConfiguration
                            .setActivityType(activityType);
                    activityType2 = activityType;
                }
                task.setActivityType(activityType2);
                task.isPersistable = asActivityRecord.isPersistable();
                task.mCallingUid = asActivityRecord.launchedFromUid;
                task.mCallingPackage = asActivityRecord.launchedFromPackage;
                task.mCallingFeatureId = asActivityRecord.launchedFromFeatureId;
                task.maxRecents =
                        Math.min(
                                Math.max(asActivityRecord.info.maxRecents, 1),
                                ActivityTaskManager.getMaxAppRecentsLimitStatic());
            }
            task.updateEffectiveIntent();
        }
        WindowProcessController organizerProcessIfDifferent =
                getOrganizerProcessIfDifferent(asActivityRecord);
        if (organizerProcessIfDifferent != null) {
            int[] remoteActivityFlags =
                    organizerProcessIfDifferent.getRemoteActivityFlags(asActivityRecord);
            remoteActivityFlags[0] = remoteActivityFlags[0] | 2;
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean allowEdgeExtension() {
        return getActivity(new TaskFragment$$ExternalSyntheticLambda0(0)) != null;
    }

    @Override // com.android.server.wm.WindowContainer
    public final TaskFragment asTaskFragment() {
        return this;
    }

    public final void calculateInsetFrames(
            Rect rect, Rect rect2, Rect rect3, DisplayInfo displayInfo, boolean z) {
        rect.set(rect3);
        rect2.set(rect3);
        if (this.mDisplayContent == null) {
            return;
        }
        this.mTmpBounds.set(0, 0, displayInfo.logicalWidth, displayInfo.logicalHeight);
        DisplayPolicy.DecorInsets.Info decorInsetsInfo =
                this.mDisplayContent.mDisplayPolicy.getDecorInsetsInfo(
                        displayInfo.rotation, displayInfo.logicalWidth, displayInfo.logicalHeight);
        if (z) {
            intersectWithInsetsIfFits(
                    rect2, this.mTmpBounds, decorInsetsInfo.mOverrideConfigInsets);
            intersectWithInsetsIfFits(
                    rect, this.mTmpBounds, decorInsetsInfo.mOverrideNonDecorInsets);
        } else {
            intersectWithInsetsIfFits(rect2, this.mTmpBounds, decorInsetsInfo.mConfigInsets);
            intersectWithInsetsIfFits(rect, this.mTmpBounds, decorInsetsInfo.mNonDecorInsets);
        }
    }

    public final boolean canBeResumed(ActivityRecord activityRecord) {
        return (getTask() == null || !getTask().isFreeformStashed())
                ? isTopActivityFocusable() && getVisibility(activityRecord) == 0
                : topRunningActivity(false) != null && getVisibility(activityRecord) == 0;
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean canCreateRemoteAnimationTarget() {
        return true;
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean canCustomizeAppTransition() {
        return this.mIsEmbedded && matchParentBounds();
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean canStartChangeTransition() {
        Task task = getTask();
        return (task == null || task.mDragResizing || !super.canStartChangeTransition())
                ? false
                : true;
    }

    public final void cleanUpActivityReferences(ActivityRecord activityRecord) {
        ActivityRecord activityRecord2 = this.mPausingActivity;
        if (activityRecord2 != null && activityRecord2 == activityRecord) {
            this.mPausingActivity = null;
        }
        ActivityRecord activityRecord3 = this.mResumedActivity;
        if (activityRecord3 != null && activityRecord3 == activityRecord) {
            setResumedActivity(null, "cleanUpActivityReferences");
        }
        activityRecord.removeTimeouts();
    }

    public final void clearLastPausedActivity() {
        forAllTaskFragments(new TaskFragment$$ExternalSyntheticLambda10());
    }

    public void completePause(boolean z, ActivityRecord activityRecord) {
        ActivityRecord activityRecord2 = this.mPausingActivity;
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_STATES_enabled;
        if (zArr[1]) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_STATES,
                    -8936154984341817384L,
                    0,
                    null,
                    String.valueOf(activityRecord2));
        }
        if (activityRecord2 != null) {
            activityRecord2.mWillCloseOrEnterPip = false;
            ActivityRecord.State state = ActivityRecord.State.STOPPING;
            boolean isState = activityRecord2.isState(state);
            activityRecord2.setState(ActivityRecord.State.PAUSED, "completePausedLocked");
            this.mPausingActivity = null;
            if (activityRecord2.finishing) {
                if (zArr[1]) {
                    ProtoLogImpl_54989576.v(
                            ProtoLogGroup.WM_DEBUG_STATES,
                            4971958459026584561L,
                            0,
                            null,
                            String.valueOf(activityRecord2));
                }
                activityRecord2 = activityRecord2.completeFinishing("completePausedLocked", false);
            } else if (activityRecord2.attachedToProcess()) {
                if (zArr[1]) {
                    ProtoLogImpl_54989576.v(
                            ProtoLogGroup.WM_DEBUG_STATES,
                            -7113165071559345173L,
                            60,
                            null,
                            String.valueOf(activityRecord2),
                            Boolean.valueOf(isState),
                            Boolean.valueOf(activityRecord2.isVisibleRequested()));
                }
                if (isState) {
                    activityRecord2.setState(state, "completePausedLocked");
                } else if (!activityRecord2.isVisibleRequested()
                        || shouldSleepOrShutDownActivities()) {
                    activityRecord2.setDeferHidingClient(false);
                    activityRecord2.addToStopping("completePauseLocked", true, false);
                }
            } else {
                if (zArr[1]) {
                    ProtoLogImpl_54989576.v(
                            ProtoLogGroup.WM_DEBUG_STATES,
                            -3777748052684097788L,
                            0,
                            null,
                            String.valueOf(activityRecord2));
                }
                activityRecord2 = null;
            }
            if (activityRecord2 != null) {
                activityRecord2.stopFreezingScreen(true);
            }
        }
        if (z) {
            Task topDisplayFocusedRootTask =
                    this.mRootWindowContainer.getTopDisplayFocusedRootTask();
            if (topDisplayFocusedRootTask == null
                    || topDisplayFocusedRootTask.shouldSleepOrShutDownActivities()) {
                ActivityRecord activityRecord3 =
                        topDisplayFocusedRootTask != null
                                ? topDisplayFocusedRootTask.topRunningActivity(false)
                                : null;
                if (activityRecord3 == null
                        || !(activityRecord2 == null || activityRecord3 == activityRecord2)) {
                    this.mRootWindowContainer.resumeFocusedTasksTopActivities();
                } else if (CoreRune.FW_SHELL_TRANSITION_BUG_FIX
                        && activityRecord3 == activityRecord2
                        && this.mTransitionController.isCollecting()) {
                    this.mTransitionController.mCollectingTransition.setReady(
                            this.mDisplayContent, true);
                }
            } else {
                this.mRootWindowContainer.resumeFocusedTasksTopActivities(
                        topDisplayFocusedRootTask, activityRecord2, null, false);
            }
        }
        if (activityRecord2 != null) {
            activityRecord2.resumeKeyDispatchingLocked();
        }
        this.mRootWindowContainer.ensureActivitiesVisible(true, activityRecord);
        if (this.mTaskSupervisor.mAppVisibilitiesChangedSinceLastPause
                || (((TaskDisplayArea) super.getDisplayArea()) != null
                        && ((TaskDisplayArea) super.getDisplayArea()).hasPinnedTask())) {
            this.mAtmService.mTaskChangeNotificationController.notifyTaskStackChanged();
            this.mTaskSupervisor.mAppVisibilitiesChangedSinceLastPause = false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:47:0x02db  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x02ea  */
    /* JADX WARN: Removed duplicated region for block: B:63:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void computeConfigResourceOverrides(
            android.content.res.Configuration r21,
            android.content.res.Configuration r22,
            com.android.server.wm.TaskFragment.ConfigOverrideHint r23,
            com.android.server.wm.DexSizeCompatController.DexSizeCompatPolicy r24) {
        /*
            Method dump skipped, instructions count: 787
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.TaskFragment.computeConfigResourceOverrides(android.content.res.Configuration,"
                    + " android.content.res.Configuration,"
                    + " com.android.server.wm.TaskFragment$ConfigOverrideHint,"
                    + " com.android.server.wm.DexSizeCompatController$DexSizeCompatPolicy):void");
    }

    @Override // com.android.server.wm.WindowContainer
    public final RemoteAnimationTarget createRemoteAnimationTarget(
            RemoteAnimationController.RemoteAnimationRecord remoteAnimationRecord) {
        ActivityRecord activity =
                remoteAnimationRecord.mMode == 0
                        ? getActivity(new TaskFragment$$ExternalSyntheticLambda0(5))
                        : getTopMostActivity();
        if (activity != null) {
            return activity.createRemoteAnimationTarget(remoteAnimationRecord);
        }
        return null;
    }

    @Override // com.android.server.wm.WindowContainer
    public void dump(PrintWriter printWriter, String str, boolean z) {
        super.dump(printWriter, str, z);
        StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, "bounds=");
        m.append(getBounds().toShortString());
        m.append(this.mIsolatedNav ? ", isolatedNav" : "");
        printWriter.println(m.toString());
        String str2 = str + "  ";
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            WindowContainer windowContainer = (WindowContainer) this.mChildren.get(size);
            TaskFragment asTaskFragment = windowContainer.asTaskFragment();
            StringBuilder m2 = Preconditions$$ExternalSyntheticOutline0.m(str, "* ");
            m2.append(
                    (Object)
                            (asTaskFragment != null
                                    ? asTaskFragment.toFullString()
                                    : windowContainer));
            printWriter.println(m2.toString());
            if (asTaskFragment != null) {
                windowContainer.dump(printWriter, str2, z);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean dump(
            final String str,
            FileDescriptor fileDescriptor,
            final PrintWriter printWriter,
            final boolean z,
            boolean z2,
            final String str2,
            final TaskFragment$$ExternalSyntheticLambda15 taskFragment$$ExternalSyntheticLambda15) {
        boolean z3;
        Runnable runnable =
                new Runnable() { // from class:
                                 // com.android.server.wm.TaskFragment$$ExternalSyntheticLambda15
                    public final /* synthetic */ boolean f$1 = false;

                    @Override // java.lang.Runnable
                    public final void run() {
                        TaskFragment taskFragment = TaskFragment.this;
                        boolean z4 = this.f$1;
                        PrintWriter printWriter2 = printWriter;
                        Runnable runnable2 = taskFragment$$ExternalSyntheticLambda15;
                        String str3 = str;
                        boolean z5 = z;
                        String str4 = str2;
                        taskFragment.getClass();
                        if (z4) {
                            printWriter2.println();
                        }
                        if (runnable2 != null) {
                            runnable2.run();
                        }
                        taskFragment.dumpInner(printWriter2, str3, str4, z5);
                    }
                };
        if (str2 == null) {
            runnable.run();
            runnable = null;
            z3 = true;
        } else {
            z3 = false;
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            WindowContainer windowContainer = (WindowContainer) this.mChildren.get(size);
            if (windowContainer.asTaskFragment() != null) {
                z3 =
                        windowContainer
                                        .asTaskFragment()
                                        .dump(
                                                ConnectivityModuleConnector$$ExternalSyntheticOutline0
                                                        .m$1(str, "  "),
                                                fileDescriptor,
                                                printWriter,
                                                z,
                                                z2,
                                                str2,
                                                runnable)
                                | z3;
            } else if (windowContainer.asActivityRecord() != null) {
                ActivityRecord.dumpActivity(
                        fileDescriptor,
                        printWriter,
                        size,
                        windowContainer.asActivityRecord(),
                        ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, "  "),
                        "Hist ",
                        true,
                        !z,
                        z2,
                        str2,
                        false,
                        runnable,
                        getTask());
            }
        }
        return z3;
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    public void dumpDebug(ProtoOutputStream protoOutputStream, long j, int i) {
        if (i != 2 || isVisible()) {
            long start = protoOutputStream.start(j);
            super.dumpDebug(protoOutputStream, 1146756268033L, i);
            protoOutputStream.write(1120986464258L, getDisplayId());
            protoOutputStream.write(1120986464259L, getActivityType());
            protoOutputStream.write(1120986464260L, this.mMinWidth);
            protoOutputStream.write(1120986464261L, this.mMinHeight);
            protoOutputStream.end(start);
        }
    }

    public void dumpInner(PrintWriter printWriter, String str, String str2, boolean z) {
        printWriter.print(str);
        printWriter.print("* ");
        printWriter.println(toFullString());
        Rect requestedOverrideBounds = getRequestedOverrideBounds();
        if (!requestedOverrideBounds.isEmpty()) {
            printWriter.println(str + "  mBounds=" + requestedOverrideBounds);
        }
        if (this.mIsRemovalRequested) {
            printWriter.println(str + "  mIsRemovalRequested=true");
        }
        if (z) {
            ActivityTaskSupervisor.printThisActivity(
                    printWriter,
                    this.mLastPausedActivity,
                    str2,
                    -1,
                    false,
                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                            str, "  mLastPausedActivity: "),
                    null);
        }
    }

    public void executeAppTransition(ActivityOptions activityOptions) {}

    @Override // com.android.server.wm.WindowContainer
    public final boolean fillsParent() {
        return getWindowingMode() == 1 || matchParentBounds();
    }

    @Override // com.android.server.wm.WindowContainer
    public final void forAllLeafTaskFragments(Consumer consumer, boolean z) {
        int size = this.mChildren.size();
        boolean z2 = true;
        if (z) {
            for (int i = size - 1; i >= 0; i--) {
                TaskFragment asTaskFragment =
                        ((WindowContainer) this.mChildren.get(i)).asTaskFragment();
                if (asTaskFragment != null) {
                    asTaskFragment.forAllLeafTaskFragments(consumer, z);
                    z2 = false;
                }
            }
        } else {
            for (int i2 = 0; i2 < size; i2++) {
                TaskFragment asTaskFragment2 =
                        ((WindowContainer) this.mChildren.get(i2)).asTaskFragment();
                if (asTaskFragment2 != null) {
                    asTaskFragment2.forAllLeafTaskFragments(consumer, z);
                    z2 = false;
                }
            }
        }
        if (z2) {
            consumer.accept(this);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean forAllLeafTaskFragments(Predicate predicate) {
        boolean z = true;
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            TaskFragment asTaskFragment =
                    ((WindowContainer) this.mChildren.get(size)).asTaskFragment();
            if (asTaskFragment != null) {
                if (asTaskFragment.forAllLeafTaskFragments(predicate)) {
                    return true;
                }
                z = false;
            }
        }
        if (z) {
            return predicate.test(this);
        }
        return false;
    }

    @Override // com.android.server.wm.WindowContainer
    public final void forAllTaskFragments(Consumer consumer, boolean z) {
        super.forAllTaskFragments(consumer, z);
        consumer.accept(this);
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public final int getActivityType() {
        int activityType = super.getActivityType();
        if (activityType != 0 || !hasChild()) {
            return activityType;
        }
        ActivityRecord topMostActivity = getTopMostActivity();
        return topMostActivity != null
                ? topMostActivity.getActivityType()
                : getTopChild().getActivityType();
    }

    public void getDimBounds(Rect rect) {
        if (this.mIsEmbedded && isDimmingOnParentTask()) {
            Dimmer.DimState dimState = getDimmer().mDimState;
            if ((dimState != null ? dimState.mDimBounds : null) != null) {
                rect.set(getTask().getBounds());
                return;
            }
        }
        rect.set(getBounds());
    }

    @Override // com.android.server.wm.WindowContainer
    public Dimmer getDimmer() {
        return (!this.mIsEmbedded || isDimmingOnParentTask()) ? super.getDimmer() : this.mDimmer;
    }

    @Override // com.android.server.wm.WindowContainer
    public final DisplayArea getDisplayArea() {
        return (TaskDisplayArea) super.getDisplayArea();
    }

    @Override // com.android.server.wm.WindowContainer
    public final TaskDisplayArea getDisplayArea() {
        return (TaskDisplayArea) super.getDisplayArea();
    }

    public final int getDisplayId() {
        DisplayContent displayContent = getDisplayContent();
        if (displayContent != null) {
            return displayContent.mDisplayId;
        }
        return -1;
    }

    public final int getNonFinishingActivityCount() {
        int[] iArr = new int[1];
        forAllActivities(new TaskFragment$$ExternalSyntheticLambda8(0, iArr));
        return iArr[0];
    }

    public final TaskFragment getOrganizedTaskFragment() {
        if (this.mTaskFragmentOrganizer != null) {
            return this;
        }
        TaskFragment asTaskFragment = getParent() != null ? getParent().asTaskFragment() : null;
        if (asTaskFragment != null) {
            return asTaskFragment.getOrganizedTaskFragment();
        }
        return null;
    }

    public final WindowProcessController getOrganizerProcessIfDifferent(
            ActivityRecord activityRecord) {
        String str;
        Task task = getTask();
        if (activityRecord == null
                || task == null
                || (str = task.mTaskFragmentHostProcessName) == null) {
            return null;
        }
        if (str.equals(activityRecord.processName)
                && task.mTaskFragmentHostUid == activityRecord.getUid()) {
            return null;
        }
        return this.mAtmService.getProcessController(
                task.mTaskFragmentHostUid, task.mTaskFragmentHostProcessName);
    }

    @Override // com.android.server.wm.WindowContainer
    public final int getOrientation(int i) {
        if (this.mAdjacentTaskFragment != null && isVisibleRequested()) {
            return -1;
        }
        int windowingMode = getWindowingMode();
        int activityType = getActivityType();
        if (windowingMode == 1 || activityType == 2 || activityType == 3 || activityType == 4) {
            return super.getOrientation(i);
        }
        return -2;
    }

    @Override // com.android.server.wm.WindowContainer
    public final int getOverrideOrientation() {
        if (!this.mIsEmbedded || isVisibleRequested()) {
            return super.getOverrideOrientation();
        }
        return -1;
    }

    @Override // com.android.server.wm.WindowContainer
    public long getProtoFieldId() {
        return 1146756268041L;
    }

    public final Task getRootTask() {
        return getRootTaskFragment().asTask();
    }

    public final TaskFragment getRootTaskFragment() {
        TaskFragment asTaskFragment;
        WindowContainer parent = getParent();
        return (parent == null || (asTaskFragment = parent.asTaskFragment()) == null)
                ? this
                : asTaskFragment.getRootTaskFragment();
    }

    public final Task getTask() {
        if (asTask() != null) {
            return asTask();
        }
        TaskFragment asTaskFragment = getParent() != null ? getParent().asTaskFragment() : null;
        if (asTaskFragment != null) {
            return asTaskFragment.getTask();
        }
        return null;
    }

    @Override // com.android.server.wm.WindowContainer
    public final TaskFragment getTaskFragment(Predicate predicate) {
        TaskFragment taskFragment = super.getTaskFragment(predicate);
        if (taskFragment != null) {
            return taskFragment;
        }
        if (predicate.test(this)) {
            return this;
        }
        return null;
    }

    public final TaskFragmentInfo getTaskFragmentInfo() {
        ApplicationInfo applicationInfo;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < getChildCount(); i++) {
            ActivityRecord asActivityRecord = getChildAt(i).asActivityRecord();
            if (this.mTaskFragmentOrganizerUid != -1
                    && asActivityRecord != null
                    && ((asActivityRecord.info.processName.equals(
                                            this.mTaskFragmentOrganizerProcessName)
                                    || (CoreRune.MW_EMBED_ACTIVITY
                                            && (applicationInfo =
                                                            asActivityRecord.info.applicationInfo)
                                                    != null
                                            && applicationInfo.processName.equals(
                                                    this.mTaskFragmentOrganizerProcessName)))
                            && asActivityRecord.getUid() == this.mTaskFragmentOrganizerUid
                            && !asActivityRecord.finishing)) {
                arrayList.add(asActivityRecord.token);
                if (asActivityRecord.mRequestedLaunchingTaskFragmentToken == this.mFragmentToken) {
                    arrayList2.add(asActivityRecord.token);
                }
            }
        }
        Point point = new Point();
        getRelativePosition(point);
        IBinder iBinder = this.mFragmentToken;
        WindowContainerToken windowContainerToken = this.mRemoteToken.toWindowContainerToken();
        Configuration configuration = getConfiguration();
        int nonFinishingActivityCount = getNonFinishingActivityCount();
        boolean shouldBeVisible = shouldBeVisible(null);
        boolean z = this.mClearedTaskForReuse;
        boolean z2 = this.mClearedTaskFragmentForPip;
        boolean z3 = this.mClearedForReorderActivityToFront;
        int[] iArr = new int[1];
        int[] iArr2 = new int[1];
        forAllActivities(new TaskFragment$$ExternalSyntheticLambda13(2, iArr, iArr2));
        return new TaskFragmentInfo(
                iBinder,
                windowContainerToken,
                configuration,
                nonFinishingActivityCount,
                shouldBeVisible,
                arrayList,
                arrayList2,
                point,
                z,
                z2,
                z3,
                new Point(iArr[0], iArr2[0]));
    }

    public final ActivityRecord getTopNonFinishingActivity(boolean z, boolean z2) {
        return z
                ? z2
                        ? getActivity(new TaskFragment$$ExternalSyntheticLambda0(1))
                        : getActivity(new TaskFragment$$ExternalSyntheticLambda0(2))
                : z2
                        ? getActivity(new TaskFragment$$ExternalSyntheticLambda0(3))
                        : getActivity(new TaskFragment$$ExternalSyntheticLambda0(4));
    }

    public ActivityRecord getTopPausingActivity() {
        ActivityRecord activityRecord;
        ActivityRecord activityRecord2 = this.mPausingActivity;
        int childCount = getChildCount();
        do {
            childCount--;
            activityRecord = null;
            if (childCount < 0) {
                return null;
            }
            WindowContainer childAt = getChildAt(childCount);
            if (activityRecord2 != null && childAt == activityRecord2) {
                activityRecord = childAt.asActivityRecord();
            } else if (childAt.asTaskFragment() != null) {
                activityRecord = childAt.asTaskFragment().getTopPausingActivity();
            }
        } while (activityRecord == null);
        return activityRecord;
    }

    public ActivityRecord getTopResumedActivity() {
        ActivityRecord activityRecord;
        ActivityRecord activityRecord2 = this.mResumedActivity;
        int childCount = getChildCount();
        do {
            childCount--;
            activityRecord = null;
            if (childCount < 0) {
                return null;
            }
            WindowContainer childAt = getChildAt(childCount);
            if (activityRecord2 != null && childAt == activityRecord2) {
                activityRecord = childAt.asActivityRecord();
            } else if (childAt.asTaskFragment() != null) {
                activityRecord = childAt.asTaskFragment().getTopResumedActivity();
            }
        } while (activityRecord == null);
        return activityRecord;
    }

    /* JADX WARN: Code restructure failed: missing block: B:252:0x02e4, code lost:

       if (com.samsung.android.rune.CoreRune.MW_MULTI_SPLIT_TASK_VISIBILITY == false) goto L250;
    */
    /* JADX WARN: Code restructure failed: missing block: B:254:0x02ea, code lost:

       if (asTask() == null) goto L250;
    */
    /* JADX WARN: Code restructure failed: missing block: B:256:0x02f1, code lost:

       if (getStageType() != 4) goto L250;
    */
    /* JADX WARN: Code restructure failed: missing block: B:257:0x02f3, code lost:

       if (r11 == false) goto L247;
    */
    /* JADX WARN: Code restructure failed: missing block: B:258:0x02f5, code lost:

       return r1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:259:0x02f6, code lost:

       if (r12 == false) goto L250;
    */
    /* JADX WARN: Code restructure failed: missing block: B:260:0x02f8, code lost:

       return 1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:261:0x02fa, code lost:

       if (r7 != 0) goto L258;
    */
    /* JADX WARN: Code restructure failed: missing block: B:263:0x0300, code lost:

       if (asTask() == null) goto L258;
    */
    /* JADX WARN: Code restructure failed: missing block: B:265:0x0308, code lost:

       if (asTask().mHiddenWhileActivatingDrag == false) goto L258;
    */
    /* JADX WARN: Code restructure failed: missing block: B:267:0x0310, code lost:

       if (asTask().mIsAnimatingByRecentsAndDragSourceTask != false) goto L258;
    */
    /* JADX WARN: Code restructure failed: missing block: B:268:0x0312, code lost:

       android.util.Slog.d("ActivityTaskManager", "[TWODND] Clear mHiddenWhileActivatingDrag");
       asTask().mHiddenWhileActivatingDrag = false;
       asTask().updateSurfaceVisibilityForDragAndDrop();
    */
    /* JADX WARN: Code restructure failed: missing block: B:269:0x0325, code lost:

       return r7;
    */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00db, code lost:

       r3 = r6;
    */
    /* JADX WARN: Removed duplicated region for block: B:276:0x02e1 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00c3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getVisibility(com.android.server.wm.ActivityRecord r21) {
        /*
            Method dump skipped, instructions count: 807
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.TaskFragment.getVisibility(com.android.server.wm.ActivityRecord):int");
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean handleCompleteDeferredRemoval() {
        if (!hasChild() ? false : isExitAnimationRunningSelfOrChild()) {
            return true;
        }
        return super.handleCompleteDeferredRemoval();
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x0033, code lost:

       if ((r9.info.flags & 268435456) == 268435456) goto L19;
    */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0029, code lost:

       if (com.android.server.am.ActivityManagerService.checkComponentPermission(-1, r2, "android.permission.EMBED_ANY_APP_IN_UNTRUSTED_MODE", 0, -1, true) == 0) goto L19;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int isAllowedToEmbedActivity(int r8, com.android.server.wm.ActivityRecord r9) {
        /*
            r7 = this;
            com.android.server.wm.WindowContainer r0 = r7.getParent()
            if (r0 == 0) goto L36
            android.graphics.Rect r0 = r0.getBounds()
            android.graphics.Rect r1 = r7.getBounds()
            boolean r0 = r0.contains(r1)
            if (r0 != 0) goto L15
            goto L36
        L15:
            int r2 = r7.mTaskFragmentOrganizerUid
            boolean r0 = com.android.window.flags.Flags.untrustedEmbeddingAnyAppPermission()
            if (r0 == 0) goto L2c
            java.lang.Boolean r0 = com.android.server.wm.ActivityTaskManagerService.sIsPip2ExperimentEnabled
            r5 = -1
            r6 = 1
            java.lang.String r3 = "android.permission.EMBED_ANY_APP_IN_UNTRUSTED_MODE"
            r1 = -1
            r4 = 0
            int r0 = com.android.server.am.ActivityManagerService.checkComponentPermission(r1, r2, r3, r4, r5, r6)
            if (r0 != 0) goto L2c
            goto L3e
        L2c:
            android.content.pm.ActivityInfo r0 = r9.info
            int r0 = r0.flags
            r1 = 268435456(0x10000000, float:2.5243549E-29)
            r0 = r0 & r1
            if (r0 != r1) goto L36
            goto L3e
        L36:
            boolean r8 = r7.isAllowedToEmbedActivityInTrustedMode(r8, r9)
            if (r8 != 0) goto L3e
            r7 = 1
            return r7
        L3e:
            android.graphics.Rect r8 = r7.getBounds()
            com.android.server.wm.Task r7 = r7.getTask()
            if (r7 == 0) goto L6c
            android.graphics.Rect r7 = r7.getBounds()
            boolean r7 = r8.equals(r7)
            if (r7 == 0) goto L53
            goto L6c
        L53:
            android.graphics.Point r7 = r9.getMinDimensions()
            if (r7 != 0) goto L5a
            goto L6c
        L5a:
            int r0 = r7.x
            int r7 = r7.y
            int r1 = r8.width()
            if (r1 < r0) goto L6a
            int r8 = r8.height()
            if (r8 >= r7) goto L6c
        L6a:
            r7 = 2
            return r7
        L6c:
            boolean r7 = com.samsung.android.rune.CoreRune.MW_EMBED_ACTIVITY
            r8 = 0
            if (r7 == 0) goto L82
            android.content.pm.ActivityInfo r7 = r9.info
            android.os.Bundle r7 = r7.metaData
            if (r7 == 0) goto L82
            java.lang.String r9 = "com.samsung.android.multiwindow.embed_activity_not_supported"
            boolean r7 = r7.getBoolean(r9, r8)
            if (r7 == 0) goto L82
            r7 = 10
            return r7
        L82:
            return r8
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.TaskFragment.isAllowedToEmbedActivity(int,"
                    + " com.android.server.wm.ActivityRecord):int");
    }

    public final boolean isAllowedToEmbedActivityInTrustedMode(
            int i, ActivityRecord activityRecord) {
        if (isFullyTrustedEmbedding(i, activityRecord)) {
            return true;
        }
        AndroidPackage androidPackage =
                this.mAtmService.getPackageManagerInternalLocked().getPackage(i);
        return androidPackage != null
                && isAllowedToEmbedActivityInTrustedModeByHostPackage(
                        activityRecord, androidPackage);
    }

    public boolean isAllowedToEmbedActivityInTrustedModeByHostPackage(
            ActivityRecord activityRecord, AndroidPackage androidPackage) {
        Set<String> knownActivityEmbeddingCerts =
                activityRecord.info.getKnownActivityEmbeddingCerts();
        if (knownActivityEmbeddingCerts.isEmpty()) {
            knownActivityEmbeddingCerts =
                    activityRecord.info.applicationInfo.getKnownActivityEmbeddingCerts();
        }
        return androidPackage
                .getSigningDetails()
                .hasAncestorOrSelfWithDigest(knownActivityEmbeddingCerts);
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean isAttached() {
        TaskDisplayArea taskDisplayArea = (TaskDisplayArea) super.getDisplayArea();
        return (taskDisplayArea == null || taskDisplayArea.mRemoved) ? false : true;
    }

    public boolean isDimmingOnParentTask() {
        return this.mEmbeddedDimArea == 1;
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean isEmbedded() {
        return this.mIsEmbedded;
    }

    public final boolean isEmbeddedTaskFragmentInPip() {
        return isOrganizedTaskFragment() && getTask() != null && getTask().inPinnedWindowingMode();
    }

    public final boolean isEmbeddedWithBoundsOverride() {
        Task task;
        if (!this.mIsEmbedded || (task = getTask()) == null) {
            return false;
        }
        Rect bounds = task.getBounds();
        Rect bounds2 = getBounds();
        return !bounds.equals(bounds2) && bounds.contains(bounds2);
    }

    public final boolean isFocusableAndVisible() {
        return isTopActivityFocusable() && shouldBeVisible(null);
    }

    public final boolean isForceHidden() {
        return this.mForceHiddenFlags != 0;
    }

    public final boolean isLeafTaskFragment() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if (((WindowContainer) this.mChildren.get(size)).asTaskFragment() != null) {
                return false;
            }
        }
        return true;
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean isOrganized() {
        return this.mTaskFragmentOrganizer != null;
    }

    public final boolean isOrganizedTaskFragment() {
        return this.mTaskFragmentOrganizer != null;
    }

    public final boolean isReadyToTransit() {
        if (!isOrganizedTaskFragment()
                || getTopNonFinishingActivity(true, true) != null
                || this.mIsRemovalRequested
                || this.mAllowTransitionWhenEmpty
                || isEmbeddedTaskFragmentInPip()) {
            return true;
        }
        if (!this.mClearedTaskFragmentForPip) {
            return false;
        }
        Task task = getTask();
        return task == null || !task.isVisibleRequested();
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean isSplitEmbedded() {
        if (asTask() == null) {
            return this.mIsEmbedded && !matchParentBounds();
        }
        ActivityRecord topVisibleActivity = asTask().getTopVisibleActivity(true, false);
        return topVisibleActivity != null && topVisibleActivity.isSplitEmbedded();
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean isSyncFinished(BLASTSyncEngine.SyncGroup syncGroup) {
        return super.isSyncFinished(syncGroup) && isReadyToTransit();
    }

    public final boolean isTopActivityFocusable() {
        ActivityRecord activityRecord = topRunningActivity(false);
        return activityRecord != null
                ? activityRecord.isFocusable()
                : isFocusable() && getWindowConfiguration().canReceiveKeys();
    }

    public final boolean isTranslucent(ActivityRecord activityRecord) {
        if (!isAttached() || isForceHidden() || this.mForceTranslucent) {
            return true;
        }
        ActivityTaskSupervisor.OpaqueActivityHelper opaqueActivityHelper =
                this.mTaskSupervisor.mOpaqueActivityHelper;
        opaqueActivityHelper.mStarting = activityRecord;
        opaqueActivityHelper.mIncludeInvisibleAndFinishing = false;
        opaqueActivityHelper.mIgnoringKeyguard = true;
        ActivityRecord activity = getActivity(opaqueActivityHelper, true, null);
        opaqueActivityHelper.mStarting = null;
        return activity == null;
    }

    public final boolean isTranslucentAndVisible() {
        if (!isAttached() || isForceHidden() || this.mForceTranslucent) {
            return true;
        }
        ActivityTaskSupervisor.OpaqueActivityHelper opaqueActivityHelper =
                this.mTaskSupervisor.mOpaqueActivityHelper;
        opaqueActivityHelper.mStarting = null;
        opaqueActivityHelper.mIncludeInvisibleAndFinishing = false;
        opaqueActivityHelper.mIgnoringKeyguard = false;
        ActivityRecord activity = getActivity(opaqueActivityHelper, true, null);
        opaqueActivityHelper.mStarting = null;
        return activity == null;
    }

    public final boolean isTranslucentForTransition() {
        if (!isAttached() || isForceHidden() || this.mForceTranslucent) {
            return true;
        }
        ActivityTaskSupervisor.OpaqueActivityHelper opaqueActivityHelper =
                this.mTaskSupervisor.mOpaqueActivityHelper;
        opaqueActivityHelper.mIncludeInvisibleAndFinishing = true;
        opaqueActivityHelper.mIgnoringKeyguard = true;
        opaqueActivityHelper.mIgnoreFloatingWindow = false;
        return getActivity(opaqueActivityHelper, true, null) == null;
    }

    @Override // com.android.server.wm.WindowContainer,
              // com.android.server.wm.SurfaceAnimator.Animatable
    public final void onAnimationLeashCreated(
            SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        super.onAnimationLeashCreated(transaction, surfaceControl);
        if (this.mTaskFragmentOrganizer != null) {
            Point point = this.mLastSurfaceSize;
            if (point.x == 0 && point.y == 0) {
                return;
            }
            transaction.setWindowCrop(this.mSurfaceControl, 0, 0);
            SurfaceControl.Transaction syncTransaction = getSyncTransaction();
            if (transaction != syncTransaction) {
                syncTransaction.setWindowCrop(this.mSurfaceControl, 0, 0);
            }
            this.mLastSurfaceSize.set(0, 0);
        }
    }

    @Override // com.android.server.wm.WindowContainer,
              // com.android.server.wm.SurfaceAnimator.Animatable
    public final void onAnimationLeashLost(SurfaceControl.Transaction transaction) {
        super.onAnimationLeashLost(transaction);
        if (this.mTaskFragmentOrganizer != null) {
            updateOrganizedTaskFragmentSurfaceSize(transaction, true);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public void onChildPositionChanged(WindowContainer windowContainer) {
        sendTaskFragmentInfoChanged();
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean onChildVisibleRequestedChanged(WindowContainer windowContainer) {
        if (!super.onChildVisibleRequestedChanged(windowContainer)) {
            return false;
        }
        sendTaskFragmentInfoChanged();
        return true;
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    public void onConfigurationChanged(Configuration configuration) {
        if (CoreRune.MW_SHELL_TRANSITION
                && this.mIsEmbedded
                && this.mTransitionController.isShellTransitionsEnabled()
                && isVisible()
                && isVisibleRequested()
                && getRequestedOverrideWindowingMode() == 0
                && configuration.windowConfiguration.getWindowingMode() != getWindowingMode()) {
            this.mTransitionController.collect(this);
        }
        super.onConfigurationChanged(configuration);
        updateOrganizedTaskFragmentSurface();
        sendTaskFragmentInfoChanged();
    }

    @Override // com.android.server.wm.WindowContainer
    public void prepareSurfaces() {
        if (asTask() != null) {
            super.prepareSurfaces();
            return;
        }
        Dimmer.DimState dimState = this.mDimmer.mDimState;
        if (dimState != null) {
            dimState.mLastDimmingWindow = null;
        }
        super.prepareSurfaces();
        Dimmer.DimState dimState2 = this.mDimmer.mDimState;
        Rect rect = dimState2 != null ? dimState2.mDimBounds : null;
        if (rect != null) {
            if (CoreRune.MW_EMBED_ACTIVITY && !matchParentBounds()) {
                rect.inset(
                        this.mDisplayContent.mInsetsStateController.mState.calculateInsets(
                                this.mTmpBounds, WindowInsets.Type.navigationBars(), false));
            }
            rect.offsetTo(0, 0);
            if (this.mDimmer.updateDims(getSyncTransaction())) {
                scheduleAnimation();
            }
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean providesOrientation() {
        return super.providesOrientation()
                || (this.mAdjacentTaskFragment != null && isVisibleRequested());
    }

    public final void remove(String str, boolean z) {
        if (!hasChild()) {
            removeImmediately(str);
            return;
        }
        this.mIsRemovalRequested = true;
        ArrayList arrayList = new ArrayList();
        forAllActivities(new Task$$ExternalSyntheticLambda8(2, arrayList));
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            ActivityRecord activityRecord = (ActivityRecord) arrayList.get(size);
            if (z && activityRecord.mVisible) {
                activityRecord.finishIfPossible(str, false);
            } else if (CoreRune.MW_EMBED_ACTIVITY
                    && z
                    && !activityRecord.mVisible
                    && activityRecord.isVisibleRequested()
                    && this.mIsPlaceholderTaskFragment) {
                activityRecord.finishIfPossible(str, false);
            } else {
                activityRecord.destroyIfPossible(str);
            }
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public void removeChild(WindowContainer windowContainer) {
        removeChild(windowContainer, true);
    }

    public final void removeChild(WindowContainer windowContainer, boolean z) {
        super.removeChild(windowContainer);
        ActivityRecord asActivityRecord = windowContainer.asActivityRecord();
        WindowProcessController organizerProcessIfDifferent =
                getOrganizerProcessIfDifferent(asActivityRecord);
        if (organizerProcessIfDifferent != null) {
            organizerProcessIfDifferent.removeRemoteActivityFlags(2, asActivityRecord);
        }
        if (z && shouldRemoveSelfOnLastChildRemoval() && !hasChild()) {
            removeImmediately("removeLastChild " + windowContainer);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public void removeImmediately() {
        DisplayContent displayContent;
        Task task;
        if (asTask() == null) {
            EventLog.writeEvent(
                    31005,
                    Integer.valueOf(System.identityHashCode(this)),
                    Integer.valueOf(getTask() != null ? getTask().mTaskId : -1));
        }
        boolean z = false;
        this.mIsRemovalRequested = false;
        resetAdjacentTaskFragment();
        if (this.mIsEmbedded) {
            this.mAtmService.mWindowOrganizerController.mLaunchTaskFragments.remove(
                    this.mFragmentToken);
            Task task2 = getTask();
            if (task2 != null) {
                task2.forAllLeafTaskFragments(
                        new TaskFragment$$ExternalSyntheticLambda8(1, this), false);
            }
        }
        if (this.mClearedTaskFragmentForPip
                && (task = getTask()) != null
                && task.isVisibleRequested()) {
            z = true;
        }
        super.removeImmediately();
        ITaskFragmentOrganizer iTaskFragmentOrganizer = this.mTaskFragmentOrganizer;
        if (iTaskFragmentOrganizer != null) {
            TaskFragmentOrganizerController taskFragmentOrganizerController =
                    this.mTaskFragmentOrganizerController;
            taskFragmentOrganizerController.getClass();
            if (!this.mTaskFragmentVanishedSent) {
                this.mTaskFragmentVanishedSent = true;
                TaskFragmentOrganizerController.TaskFragmentOrganizerState validateAndGetState =
                        taskFragmentOrganizerController.validateAndGetState(iTaskFragmentOrganizer);
                List list =
                        (List)
                                taskFragmentOrganizerController.mPendingTaskFragmentEvents.get(
                                        iTaskFragmentOrganizer.asBinder());
                for (int size = list.size() - 1; size >= 0; size--) {
                    if (this
                            == ((TaskFragmentOrganizerController.PendingTaskFragmentEvent)
                                            list.get(size))
                                    .mTaskFragment) {
                        list.remove(size);
                    }
                }
                taskFragmentOrganizerController.addPendingEvent(
                        new TaskFragmentOrganizerController.PendingTaskFragmentEvent(
                                1,
                                iTaskFragmentOrganizer,
                                this,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                0));
                validateAndGetState.mOrganizedTaskFragments.remove(this);
                taskFragmentOrganizerController.mAtmService.mWindowManager.mWindowPlacerLocked
                        .requestTraversal();
            }
        }
        if (!z || (displayContent = this.mDisplayContent) == null) {
            return;
        }
        this.mAtmService.mLayoutReasons |= 2;
        displayContent.executeAppTransition();
    }

    public void removeImmediately(String str) {
        Slog.d("ActivityTaskManager", "Remove task fragment: " + str);
        removeImmediately();
    }

    public final void resetAdjacentTaskFragment() {
        TaskFragment taskFragment = this.mAdjacentTaskFragment;
        if (taskFragment != null && taskFragment.mAdjacentTaskFragment == this) {
            taskFragment.mAdjacentTaskFragment = null;
            this.mAdjacentTaskFragment.mDelayLastActivityRemoval = false;
        }
        this.mAdjacentTaskFragment = null;
        this.mDelayLastActivityRemoval = false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0071, code lost:

       if (com.android.server.wm.DexSizeCompatController.getCompatPolicy(r6) != null) goto L51;
    */
    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void resolveOverrideConfiguration(android.content.res.Configuration r12) {
        /*
            Method dump skipped, instructions count: 692
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.TaskFragment.resolveOverrideConfiguration(android.content.res.Configuration):void");
    }

    /* JADX WARN: Can't wrap try/catch for region: R(20:177|(1:179)(1:472)|180|(4:182|(2:184|(1:186))|187|(4:193|(1:195)|196|(1:(1:199))(2:200|(2:202|(2:(1:205)|206)(2:207|(3:459|460|(1:(1:467))(1:465))(2:209|(1:211)(2:212|(4:441|442|(1:(2:(2:446|(1:448))|(1:452)))|(1:454))(17:214|(2:413|(2:(2:(2:433|434)|432)|(1:439))(3:417|418|(1:(1:426))(2:423|424)))(4:218|219|(1:409)(2:(4:224|225|226|(1:228))(1:408)|(1:235))|(1:403))|236|(3:238|(3:240|(4:247|(1:249)|250|251)|252)|257)|(1:264)|265|266|(1:268)(3:396|(1:398)|399)|(1:270)(1:395)|271|272|(2:274|(3:276|(1:278)(1:379)|279)(2:380|(1:382)(5:383|(1:385)(1:389)|386|387|388)))(2:390|(1:392)(2:393|388))|(1:281)(1:378)|282|(24:284|(1:369)(1:290)|291|(1:368)|296|(1:300)|(1:302)(1:367)|303|(1:305)|306|(2:308|(2:310|311))|312|313|(1:315)(1:365)|316|(2:318|(2:321|(1:323)(1:324)))|325|(5:327|328|329|330|(1:332)(1:333))|353|(1:355)(1:364)|356|357|(1:359)|361)(5:370|(1:372)(1:377)|373|(1:375)|376)|362|363))))))))|471|236|(0)|(3:260|262|264)|265|266|(0)(0)|(0)(0)|271|272|(0)(0)|(0)(0)|282|(0)(0)|362|363) */
    /* JADX WARN: Code restructure failed: missing block: B:400:0x0609, code lost:

       r0 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:401:0x060a, code lost:

       android.util.Slog.w("ActivityTaskManager", "Failed trying to unstop package " + r3.packageName + ": " + r0);
    */
    /* JADX WARN: Code restructure failed: missing block: B:435:0x0526, code lost:

       if (r3.processName.equals(r4.processName) != false) goto L306;
    */
    /* JADX WARN: Removed duplicated region for block: B:136:0x0276  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x02b5  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x02f8  */
    /* JADX WARN: Removed duplicated region for block: B:238:0x0562  */
    /* JADX WARN: Removed duplicated region for block: B:259:0x05b8 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:268:0x05d0  */
    /* JADX WARN: Removed duplicated region for block: B:270:0x05e4 A[Catch: IllegalArgumentException -> 0x0609, TryCatch #8 {IllegalArgumentException -> 0x0609, blocks: (B:266:0x05c6, B:270:0x05e4, B:271:0x05f7, B:395:0x05f4, B:396:0x05d2, B:398:0x05d6, B:399:0x05e0), top: B:265:0x05c6 }] */
    /* JADX WARN: Removed duplicated region for block: B:274:0x0629  */
    /* JADX WARN: Removed duplicated region for block: B:281:0x0680  */
    /* JADX WARN: Removed duplicated region for block: B:284:0x0694  */
    /* JADX WARN: Removed duplicated region for block: B:370:0x0869  */
    /* JADX WARN: Removed duplicated region for block: B:378:0x0684  */
    /* JADX WARN: Removed duplicated region for block: B:390:0x0669  */
    /* JADX WARN: Removed duplicated region for block: B:395:0x05f4 A[Catch: IllegalArgumentException -> 0x0609, TryCatch #8 {IllegalArgumentException -> 0x0609, blocks: (B:266:0x05c6, B:270:0x05e4, B:271:0x05f7, B:395:0x05f4, B:396:0x05d2, B:398:0x05d6, B:399:0x05e0), top: B:265:0x05c6 }] */
    /* JADX WARN: Removed duplicated region for block: B:396:0x05d2 A[Catch: IllegalArgumentException -> 0x0609, TryCatch #8 {IllegalArgumentException -> 0x0609, blocks: (B:266:0x05c6, B:270:0x05e4, B:271:0x05f7, B:395:0x05f4, B:396:0x05d2, B:398:0x05d6, B:399:0x05e0), top: B:265:0x05c6 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean resumeTopActivity(
            com.android.server.wm.ActivityRecord r26,
            android.app.ActivityOptions r27,
            boolean r28) {
        /*
            Method dump skipped, instructions count: 2197
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.TaskFragment.resumeTopActivity(com.android.server.wm.ActivityRecord,"
                    + " android.app.ActivityOptions, boolean):boolean");
    }

    public final void schedulePauseActivity(
            ActivityRecord activityRecord, boolean z, boolean z2, boolean z3, String str) {
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_STATES_enabled[1]) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_STATES,
                    1917394294249960915L,
                    0,
                    null,
                    String.valueOf(activityRecord));
        }
        try {
            activityRecord.mPauseSchedulePendingForPip = false;
            int i = activityRecord.mUserId;
            int identityHashCode = System.identityHashCode(activityRecord);
            EventLog.writeEvent(
                    30013,
                    Integer.valueOf(i),
                    Integer.valueOf(identityHashCode),
                    activityRecord.shortComponentName,
                    "userLeaving=" + z,
                    str);
            this.mAtmService.mLifecycleManager.scheduleTransactionItem(
                    activityRecord.app.mThread,
                    PauseActivityItem.obtain(
                            activityRecord.token, activityRecord.finishing, z, z2, z3));
        } catch (Exception e) {
            Slog.w("ActivityTaskManager", "Exception thrown during pause", e);
            this.mPausingActivity = null;
            this.mLastPausedActivity = null;
            this.mTaskSupervisor.mNoHistoryActivities.remove(activityRecord);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0048, code lost:

       r4 = new com.android.server.wm.TaskFragmentOrganizerController.PendingTaskFragmentEvent(2, r2, r13, null, null, null, null, null, null, 0);
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void sendTaskFragmentInfoChanged() {
        /*
            r13 = this;
            android.window.ITaskFragmentOrganizer r2 = r13.mTaskFragmentOrganizer
            if (r2 == 0) goto L71
            com.android.server.wm.TaskFragmentOrganizerController r11 = r13.mTaskFragmentOrganizerController
            r11.getClass()
            boolean r0 = r13.mTaskFragmentVanishedSent
            if (r0 == 0) goto Le
            goto L71
        Le:
            r11.validateAndGetState(r2)
            boolean r0 = r13.mTaskFragmentAppearedSent
            if (r0 != 0) goto L16
            goto L71
        L16:
            android.window.ITaskFragmentOrganizer r0 = r13.mTaskFragmentOrganizer
            android.util.ArrayMap r1 = r11.mPendingTaskFragmentEvents
            android.os.IBinder r0 = r0.asBinder()
            java.lang.Object r0 = r1.get(r0)
            java.util.List r0 = (java.util.List) r0
            int r1 = r0.size()
            r3 = 1
            int r1 = r1 - r3
        L2a:
            if (r1 < 0) goto L45
            java.lang.Object r4 = r0.get(r1)
            com.android.server.wm.TaskFragmentOrganizerController$PendingTaskFragmentEvent r4 = (com.android.server.wm.TaskFragmentOrganizerController.PendingTaskFragmentEvent) r4
            com.android.server.wm.TaskFragment r5 = r4.mTaskFragment
            if (r13 != r5) goto L42
            int r5 = r4.mEventType
            if (r5 == 0) goto L46
            if (r5 == r3) goto L46
            r6 = 2
            if (r5 == r6) goto L46
            r6 = 3
            if (r5 == r6) goto L46
        L42:
            int r1 = r1 + (-1)
            goto L2a
        L45:
            r4 = 0
        L46:
            if (r4 != 0) goto L59
            com.android.server.wm.TaskFragmentOrganizerController$PendingTaskFragmentEvent r12 = new com.android.server.wm.TaskFragmentOrganizerController$PendingTaskFragmentEvent
            r7 = 0
            r8 = 0
            r1 = 2
            r4 = 0
            r5 = 0
            r6 = 0
            r9 = 0
            r10 = 0
            r0 = r12
            r3 = r13
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)
            r4 = r12
            goto L6e
        L59:
            android.util.ArrayMap r13 = r11.mPendingTaskFragmentEvents
            android.window.ITaskFragmentOrganizer r0 = r4.mTaskFragmentOrg
            android.os.IBinder r0 = r0.asBinder()
            java.lang.Object r13 = r13.get(r0)
            java.util.List r13 = (java.util.List) r13
            r13.remove(r4)
            r0 = 0
            r4.mDeferTime = r0
        L6e:
            r11.addPendingEvent(r4)
        L71:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.TaskFragment.sendTaskFragmentInfoChanged():void");
    }

    public final void setAdjacentTaskFragment(TaskFragment taskFragment) {
        if (this.mAdjacentTaskFragment == taskFragment) {
            return;
        }
        resetAdjacentTaskFragment();
        if (taskFragment != null) {
            this.mAdjacentTaskFragment = taskFragment;
            taskFragment.setAdjacentTaskFragment(this);
        }
    }

    public boolean setForceHidden(int i, boolean z) {
        int i2 = this.mForceHiddenFlags;
        int i3 = z ? i | i2 : (~i) & i2;
        if (i2 == i3) {
            return false;
        }
        this.mForceHiddenFlags = i3;
        return true;
    }

    public void setPausingActivity(ActivityRecord activityRecord) {
        this.mPausingActivity = activityRecord;
    }

    public final void setRelativeEmbeddedBounds(Rect rect) {
        Rect rect2 = this.mRelativeEmbeddedBounds;
        if (rect2 == null) {
            throw new IllegalStateException("The TaskFragment is not embedded");
        }
        if (rect2.equals(rect)) {
            return;
        }
        this.mRelativeEmbeddedBounds.set(rect);
    }

    public final void setResumedActivity(ActivityRecord activityRecord, String str) {
        DisplayContent displayContent;
        ActivityRecord activityRecord2 = this.mResumedActivity;
        if (activityRecord2 == activityRecord) {
            return;
        }
        if (activityRecord != null && activityRecord2 == null) {
            Task task = getTask();
            task.getClass();
            task.lastActiveTime = SystemClock.elapsedRealtime();
        }
        ActivityRecord activityRecord3 = this.mResumedActivity;
        this.mResumedActivity = activityRecord;
        ActivityRecord updateTopResumedActivityIfNeeded =
                this.mTaskSupervisor.updateTopResumedActivityIfNeeded(str);
        if (this.mResumedActivity != null
                && updateTopResumedActivityIfNeeded != null
                && updateTopResumedActivityIfNeeded.isEmbedded()
                && updateTopResumedActivityIfNeeded.getTaskFragment().mAdjacentTaskFragment
                        == this) {
            this.mAtmService.setLastResumedActivityUncheckLocked(this.mResumedActivity, str);
        }
        if (activityRecord != null && !activityRecord.mDisplayContent.isOnTop()) {
            activityRecord.mDisplayContent.setFocusedApp(activityRecord);
        }
        if (activityRecord == null
                && (displayContent = activityRecord3.mDisplayContent) != null
                && displayContent.getFocusedRootTask() == null) {
            activityRecord3.mDisplayContent.onRunningActivityChanged();
        } else if (activityRecord != null) {
            activityRecord.mDisplayContent.onRunningActivityChanged();
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public void setSurfaceControl(SurfaceControl surfaceControl) {
        super.setSurfaceControl(surfaceControl);
        if (this.mTaskFragmentOrganizer != null) {
            SurfaceControl.Transaction syncTransaction = getSyncTransaction();
            updateSurfacePosition(syncTransaction);
            updateOrganizedTaskFragmentSurfaceSize(syncTransaction, false);
            ITaskFragmentOrganizer iTaskFragmentOrganizer = this.mTaskFragmentOrganizer;
            if (iTaskFragmentOrganizer != null) {
                TaskFragmentOrganizerController taskFragmentOrganizerController =
                        this.mTaskFragmentOrganizerController;
                taskFragmentOrganizerController.getClass();
                if (this.mTaskFragmentVanishedSent) {
                    return;
                }
                if (getTask() == null) {
                    Slog.w(
                            "TaskFragmentOrganizerController",
                            "onTaskFragmentAppeared failed because it is not attached tf=" + this);
                    return;
                }
                TaskFragmentOrganizerController.TaskFragmentOrganizerState validateAndGetState =
                        taskFragmentOrganizerController.validateAndGetState(iTaskFragmentOrganizer);
                if (this.mTaskFragmentAppearedSent
                        || validateAndGetState.mOrganizedTaskFragments.contains(this)) {
                    return;
                }
                validateAndGetState.mOrganizedTaskFragments.add(this);
                if (taskFragmentOrganizerController.getPendingTaskFragmentEvent(this, 0) == null) {
                    taskFragmentOrganizerController.addPendingEvent(
                            new TaskFragmentOrganizerController.PendingTaskFragmentEvent(
                                    0,
                                    iTaskFragmentOrganizer,
                                    this,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    0));
                }
            }
        }
    }

    public final boolean shouldBeVisible(ActivityRecord activityRecord) {
        return getVisibility(activityRecord) != 2;
    }

    public final boolean shouldRemoveSelfOnLastChildRemoval() {
        return !this.mCreatedByOrganizer || this.mIsRemovalRequested;
    }

    public boolean shouldSleepActivities() {
        Task rootTask = getRootTask();
        return rootTask != null && rootTask.shouldSleepActivities();
    }

    public final boolean shouldSleepOrShutDownActivities() {
        return shouldSleepActivities() || this.mAtmService.mShuttingDown;
    }

    /* JADX WARN: Removed duplicated region for block: B:72:0x01ad  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean startPausing$1(
            com.android.server.wm.ActivityRecord r23,
            java.lang.String r24,
            boolean r25,
            boolean r26) {
        /*
            Method dump skipped, instructions count: 762
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.TaskFragment.startPausing$1(com.android.server.wm.ActivityRecord,"
                    + " java.lang.String, boolean, boolean):boolean");
    }

    public final boolean supportsMultiWindow() {
        return supportsMultiWindowInDisplayArea((TaskDisplayArea) super.getDisplayArea(), false);
    }

    public final boolean supportsMultiWindowInDefaultDisplayArea() {
        return supportsMultiWindowInDisplayArea(
                this.mRootWindowContainer.mDefaultDisplay.getDefaultTaskDisplayArea(), false);
    }

    public final boolean supportsMultiWindowInDisplayArea(
            TaskDisplayArea taskDisplayArea, boolean z) {
        Task task;
        ActivityTaskManagerService activityTaskManagerService = this.mAtmService;
        if ((!activityTaskManagerService.mSupportsMultiWindow
                        && (!z
                                || !activityTaskManagerService
                                        .mMultiWindowEnableController
                                        .mDeviceSupportsMultiWindow))
                || taskDisplayArea == null
                || (task = getTask()) == null) {
            return false;
        }
        return this.mAtmService.mMwSupportPolicyController.supportsMultiWindowInDisplayArea(
                taskDisplayArea,
                task.mResizeMode,
                task.isResizeable(true),
                task.mIgnoreDevSettingForNonResizable);
    }

    public String toFullString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append(this);
        sb.setLength(sb.length() - 1);
        if (this.mTaskFragmentOrganizerUid != -1) {
            sb.append(" organizerUid=");
            sb.append(this.mTaskFragmentOrganizerUid);
        }
        if (this.mTaskFragmentOrganizerProcessName != null) {
            sb.append(" organizerProc=");
            sb.append(this.mTaskFragmentOrganizerProcessName);
        }
        if (this.mAdjacentTaskFragment != null) {
            sb.append(" adjacent=");
            sb.append(this.mAdjacentTaskFragment);
        }
        sb.append('}');
        return sb.toString();
    }

    public String toString() {
        return "TaskFragment{"
                + Integer.toHexString(System.identityHashCode(this))
                + " mode="
                + WindowConfiguration.windowingModeToString(getWindowingMode())
                + "}";
    }

    public final ActivityRecord topRunningActivity(boolean z) {
        if (getTask() == null || !getTask().isMinimized()) {
            return z
                    ? getActivity(new TaskFragment$$ExternalSyntheticLambda0(6))
                    : getActivity(new ActivityStarter$$ExternalSyntheticLambda0(1));
        }
        return null;
    }

    public final Rect translateRelativeBoundsToAbsoluteBounds(Rect rect, Rect rect2) {
        if (rect.isEmpty()) {
            this.mTmpAbsBounds.setEmpty();
            return this.mTmpAbsBounds;
        }
        this.mTmpAbsBounds.set(rect);
        this.mTmpAbsBounds.offset(rect2.left, rect2.top);
        if (!(!forAllActivities(new TaskFragment$$ExternalSyntheticLambda6(this)))
                && !rect2.contains(this.mTmpAbsBounds)
                && !this.mTmpAbsBounds.intersect(rect2)) {
            this.mTmpAbsBounds.setEmpty();
        }
        return this.mTmpAbsBounds;
    }

    public final void updateActivityVisibilities(boolean z, ActivityRecord activityRecord) {
        this.mTaskSupervisor.beginActivityVisibilityUpdate(getDisplayContent());
        try {
            this.mEnsureActivitiesVisibleHelper.process(z, activityRecord);
            this.mTaskSupervisor.endActivityVisibilityUpdate();
            if (CoreRune.MT_SIZE_COMPAT_POLICY) {
                SizeCompatPolicyManager sizeCompatPolicyManager =
                        SizeCompatPolicyManager.LazyHolder.sManager;
                Task asTask = asTask();
                sizeCompatPolicyManager.getClass();
                SizeCompatPolicyManager.ensureConfiguration(asTask);
            }
        } catch (Throwable th) {
            this.mTaskSupervisor.endActivityVisibilityUpdate();
            throw th;
        }
    }

    public final void updateOrganizedTaskFragmentSurface() {
        if (this.mDelayOrganizedTaskFragmentSurfaceUpdate || this.mTaskFragmentOrganizer == null) {
            return;
        }
        boolean z =
                CoreRune.MW_EMBED_ACTIVITY
                        && this.mTransitionController.isShellTransitionsEnabled()
                        && this.mTransitionController.isCollecting(this)
                        && !isVisibleRequested()
                        && !isVisible();
        if (this.mTransitionController.isShellTransitionsEnabled()
                && (!this.mTransitionController.isCollecting(this) || z)) {
            SurfaceControl.Transaction syncTransaction = getSyncTransaction();
            updateSurfacePosition(syncTransaction);
            updateOrganizedTaskFragmentSurfaceSize(syncTransaction, false);
        } else {
            if (this.mTransitionController.isShellTransitionsEnabled() || isAnimating()) {
                return;
            }
            SurfaceControl.Transaction syncTransaction2 = getSyncTransaction();
            updateSurfacePosition(syncTransaction2);
            updateOrganizedTaskFragmentSurfaceSize(syncTransaction2, false);
        }
    }

    public final void updateOrganizedTaskFragmentSurfaceSize(
            SurfaceControl.Transaction transaction, boolean z) {
        if (this.mTaskFragmentOrganizer == null
                || this.mSurfaceControl == null
                || this.mSurfaceAnimator.hasLeash()
                || this.mSurfaceFreezer.hasLeash()) {
            return;
        }
        Rect bounds =
                isClosingWhenResizing()
                        ? (Rect) this.mDisplayContent.mClosingChangingContainers.get(this)
                        : getBounds();
        int width = bounds.width();
        int height = bounds.height();
        if (!z) {
            Point point = this.mLastSurfaceSize;
            if (width == point.x && height == point.y) {
                return;
            }
        }
        transaction.setWindowCrop(this.mSurfaceControl, width, height);
        this.mLastSurfaceSize.set(width, height);
    }

    @Override // com.android.server.wm.WindowContainer
    public void writeIdentifierToProto(ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, System.identityHashCode(this));
        ActivityRecord activityRecord = topRunningActivity(false);
        protoOutputStream.write(
                1120986464258L, activityRecord != null ? activityRecord.mUserId : -10000);
        protoOutputStream.write(
                1138166333443L,
                activityRecord != null
                        ? activityRecord.intent.getComponent().flattenToShortString()
                        : "TaskFragment");
        protoOutputStream.end(start);
    }
}
