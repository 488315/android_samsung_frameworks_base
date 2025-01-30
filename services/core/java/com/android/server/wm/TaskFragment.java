package com.android.server.wm;

import android.app.ActivityManagerInternal;
import android.app.ActivityOptions;
import android.app.WindowConfiguration;
import android.app.servertransaction.ActivityLifecycleItem;
import android.app.servertransaction.ActivityResultItem;
import android.app.servertransaction.ClientTransaction;
import android.app.servertransaction.NewIntentItem;
import android.app.servertransaction.PauseActivityItem;
import android.app.servertransaction.ResumeActivityItem;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import android.view.DisplayInfo;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.WindowInsets;
import android.window.ITaskFragmentOrganizer;
import android.window.ScreenCapture;
import android.window.TaskFragmentAnimationParams;
import android.window.TaskFragmentInfo;
import android.window.TaskFragmentOrganizerToken;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl;
import com.android.server.chimera.PerProcessNandswap;
import com.android.server.display.DisplayPowerController2;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.wm.ActivityRecord;
import com.android.server.wm.BLASTSyncEngine;
import com.android.server.wm.DisplayPolicy;
import com.android.server.wm.RemoteAnimationController;
import com.android.server.wm.WindowContainer;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class TaskFragment extends WindowContainer {
    public TaskFragment mAdjacentTaskFragment;
    public TaskFragmentAnimationParams mAnimationParams;
    public final ActivityTaskManagerService mAtmService;
    public HashMap mBackScreenshots;
    public boolean mClearedForReorderActivityToFront;
    public boolean mClearedTaskForReuse;
    public boolean mClearedTaskFragmentForPip;
    public TaskFragment mCompanionTaskFragment;
    boolean mCreatedByOrganizer;
    public boolean mDelayLastActivityRemoval;
    public boolean mDelayOrganizedTaskFragmentSurfaceUpdate;
    public Dimmer mDimmer;
    public final EnsureActivitiesVisibleHelper mEnsureActivitiesVisibleHelper;
    public final EnsureVisibleActivitiesConfigHelper mEnsureVisibleActivitiesConfigHelper;
    public final IBinder mFragmentToken;
    public final boolean mIsEmbedded;
    public boolean mIsPlaceholderTaskFragment;
    public boolean mIsRemovalRequested;
    public ActivityRecord mLastPausedActivity;
    public final Point mLastSurfaceSize;
    public int mMinHeight;
    public int mMinWidth;
    public ActivityRecord mPausingActivity;
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

    public static boolean inStageMainOrSide(int i) {
        return i == 1 || i == 2;
    }

    @Override // com.android.server.wm.WindowContainer
    public TaskFragment asTaskFragment() {
        return this;
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean canCreateRemoteAnimationTarget() {
        return true;
    }

    @Override // com.android.server.wm.WindowContainer
    public long getProtoFieldId() {
        return 1146756268041L;
    }

    public boolean isForceHidden() {
        return false;
    }

    public boolean isForceTranslucent() {
        return false;
    }

    public class EnsureVisibleActivitiesConfigHelper implements Predicate {
        public boolean mBehindFullscreen;
        public boolean mPreserveWindow;
        public boolean mUpdateConfig;

        public EnsureVisibleActivitiesConfigHelper() {
        }

        @Override // java.util.function.Predicate
        public boolean test(ActivityRecord activityRecord) {
            this.mUpdateConfig |= activityRecord.ensureActivityConfiguration(0, this.mPreserveWindow);
            boolean occludesParent = activityRecord.occludesParent() | this.mBehindFullscreen;
            this.mBehindFullscreen = occludesParent;
            return occludesParent;
        }
    }

    public TaskFragment(ActivityTaskManagerService activityTaskManagerService, IBinder iBinder, boolean z) {
        this(activityTaskManagerService, iBinder, z, true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public TaskFragment(ActivityTaskManagerService activityTaskManagerService, IBinder iBinder, boolean z, boolean z2) {
        super(activityTaskManagerService.mWindowManager);
        this.mDimmer = new Dimmer(this);
        this.mPausingActivity = null;
        this.mLastPausedActivity = null;
        this.mResumedActivity = null;
        this.mIsPlaceholderTaskFragment = false;
        this.mTaskFragmentOrganizerUid = -1;
        this.mAnimationParams = TaskFragmentAnimationParams.DEFAULT;
        this.mLastSurfaceSize = new Point();
        this.mTmpBounds = new Rect();
        this.mTmpAbsBounds = new Rect();
        this.mTmpFullBounds = new Rect();
        this.mTmpStableBounds = new Rect();
        this.mTmpNonDecorBounds = new Rect();
        this.mBackScreenshots = new HashMap();
        this.mEnsureActivitiesVisibleHelper = new EnsureActivitiesVisibleHelper(this);
        this.mEnsureVisibleActivitiesConfigHelper = new EnsureVisibleActivitiesConfigHelper();
        this.mAtmService = activityTaskManagerService;
        this.mTaskSupervisor = activityTaskManagerService.mTaskSupervisor;
        this.mRootWindowContainer = activityTaskManagerService.mRootWindowContainer;
        this.mCreatedByOrganizer = z;
        this.mIsEmbedded = z2;
        this.mRelativeEmbeddedBounds = z2 ? new Rect() : null;
        this.mTaskFragmentOrganizerController = activityTaskManagerService.mWindowOrganizerController.mTaskFragmentOrganizerController;
        this.mFragmentToken = iBinder;
        this.mRemoteToken = new WindowContainer.RemoteToken(this);
    }

    public static TaskFragment fromTaskFragmentToken(IBinder iBinder, ActivityTaskManagerService activityTaskManagerService) {
        if (iBinder == null) {
            return null;
        }
        return activityTaskManagerService.mWindowOrganizerController.getTaskFragment(iBinder);
    }

    public void setAdjacentTaskFragment(TaskFragment taskFragment) {
        if (this.mAdjacentTaskFragment == taskFragment) {
            return;
        }
        resetAdjacentTaskFragment();
        if (taskFragment != null) {
            this.mAdjacentTaskFragment = taskFragment;
            taskFragment.setAdjacentTaskFragment(this);
        }
    }

    public void setCompanionTaskFragment(TaskFragment taskFragment) {
        this.mCompanionTaskFragment = taskFragment;
    }

    public TaskFragment getCompanionTaskFragment() {
        return this.mCompanionTaskFragment;
    }

    public void resetAdjacentTaskFragment() {
        TaskFragment taskFragment = this.mAdjacentTaskFragment;
        if (taskFragment != null && taskFragment.mAdjacentTaskFragment == this) {
            taskFragment.mAdjacentTaskFragment = null;
            this.mAdjacentTaskFragment.mDelayLastActivityRemoval = false;
        }
        this.mAdjacentTaskFragment = null;
        this.mDelayLastActivityRemoval = false;
    }

    public void setTaskFragmentOrganizer(TaskFragmentOrganizerToken taskFragmentOrganizerToken, int i, String str) {
        this.mTaskFragmentOrganizer = ITaskFragmentOrganizer.Stub.asInterface(taskFragmentOrganizerToken.asBinder());
        this.mTaskFragmentOrganizerUid = i;
        this.mTaskFragmentOrganizerProcessName = str;
    }

    public void onTaskFragmentOrganizerRemoved() {
        this.mTaskFragmentOrganizer = null;
    }

    public boolean hasTaskFragmentOrganizer(ITaskFragmentOrganizer iTaskFragmentOrganizer) {
        return (iTaskFragmentOrganizer == null || this.mTaskFragmentOrganizer == null || !iTaskFragmentOrganizer.asBinder().equals(this.mTaskFragmentOrganizer.asBinder())) ? false : true;
    }

    public final WindowProcessController getOrganizerProcessIfDifferent(ActivityRecord activityRecord) {
        String str;
        if (activityRecord == null || (str = this.mTaskFragmentOrganizerProcessName) == null) {
            return null;
        }
        if (str.equals(activityRecord.processName) && this.mTaskFragmentOrganizerUid == activityRecord.getUid()) {
            return null;
        }
        return this.mAtmService.getProcessController(this.mTaskFragmentOrganizerProcessName, this.mTaskFragmentOrganizerUid);
    }

    public void setAnimationParams(TaskFragmentAnimationParams taskFragmentAnimationParams) {
        this.mAnimationParams = taskFragmentAnimationParams;
    }

    public TaskFragmentAnimationParams getAnimationParams() {
        return this.mAnimationParams;
    }

    public TaskFragment getAdjacentTaskFragment() {
        return this.mAdjacentTaskFragment;
    }

    public ActivityRecord getTopResumedActivity() {
        ActivityRecord activityRecord;
        ActivityRecord resumedActivity = getResumedActivity();
        int childCount = getChildCount();
        do {
            childCount--;
            activityRecord = null;
            if (childCount < 0) {
                return null;
            }
            WindowContainer childAt = getChildAt(childCount);
            if (resumedActivity != null && childAt == resumedActivity) {
                activityRecord = childAt.asActivityRecord();
            } else if (childAt.asTaskFragment() != null) {
                activityRecord = childAt.asTaskFragment().getTopResumedActivity();
            }
        } while (activityRecord == null);
        return activityRecord;
    }

    public ActivityRecord getResumedActivity() {
        return this.mResumedActivity;
    }

    public void setResumedActivity(ActivityRecord activityRecord, String str) {
        DisplayContent displayContent;
        warnForNonLeafTaskFragment("setResumedActivity");
        ActivityRecord activityRecord2 = this.mResumedActivity;
        if (activityRecord2 == activityRecord) {
            return;
        }
        if (activityRecord != null && activityRecord2 == null) {
            getTask().touchActiveTime();
        }
        ActivityRecord activityRecord3 = this.mResumedActivity;
        this.mResumedActivity = activityRecord;
        this.mTaskSupervisor.updateTopResumedActivityIfNeeded(str);
        if (activityRecord != null && !activityRecord.mDisplayContent.isOnTop()) {
            activityRecord.mDisplayContent.setFocusedApp(activityRecord);
        }
        if (activityRecord == null && (displayContent = activityRecord3.mDisplayContent) != null && displayContent.getFocusedRootTask() == null) {
            activityRecord3.mDisplayContent.onRunningActivityChanged();
        } else if (activityRecord != null) {
            activityRecord.mDisplayContent.onRunningActivityChanged();
        }
    }

    public void setPausingActivity(ActivityRecord activityRecord) {
        this.mPausingActivity = activityRecord;
    }

    public ActivityRecord getTopPausingActivity() {
        ActivityRecord activityRecord;
        ActivityRecord pausingActivity = getPausingActivity();
        int childCount = getChildCount();
        do {
            childCount--;
            activityRecord = null;
            if (childCount < 0) {
                return null;
            }
            WindowContainer childAt = getChildAt(childCount);
            if (pausingActivity != null && childAt == pausingActivity) {
                activityRecord = childAt.asActivityRecord();
            } else if (childAt.asTaskFragment() != null) {
                activityRecord = childAt.asTaskFragment().getTopPausingActivity();
            }
        } while (activityRecord == null);
        return activityRecord;
    }

    public ActivityRecord getPausingActivity() {
        return this.mPausingActivity;
    }

    public int getDisplayId() {
        DisplayContent displayContent = getDisplayContent();
        if (displayContent != null) {
            return displayContent.mDisplayId;
        }
        return -1;
    }

    public Task getTask() {
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
    public TaskDisplayArea getDisplayArea() {
        return (TaskDisplayArea) super.getDisplayArea();
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean isAttached() {
        TaskDisplayArea displayArea = getDisplayArea();
        return (displayArea == null || displayArea.isRemoved()) ? false : true;
    }

    public TaskFragment getRootTaskFragment() {
        TaskFragment asTaskFragment;
        WindowContainer parent = getParent();
        return (parent == null || (asTaskFragment = parent.asTaskFragment()) == null) ? this : asTaskFragment.getRootTaskFragment();
    }

    public Task getRootTask() {
        return getRootTaskFragment().asTask();
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean isEmbedded() {
        return this.mIsEmbedded;
    }

    public int isAllowedToEmbedActivity(ActivityRecord activityRecord) {
        return isAllowedToEmbedActivity(activityRecord, this.mTaskFragmentOrganizerUid);
    }

    public int isAllowedToEmbedActivity(ActivityRecord activityRecord, int i) {
        Bundle bundle;
        if (!isAllowedToEmbedActivityInUntrustedMode(activityRecord) && !isAllowedToEmbedActivityInTrustedMode(activityRecord, i)) {
            return 1;
        }
        if (smallerThanMinDimension(activityRecord)) {
            return 2;
        }
        return (CoreRune.MW_EMBED_ACTIVITY && (bundle = activityRecord.info.metaData) != null && bundle.getBoolean("com.samsung.android.multiwindow.embed_activity_not_supported", false)) ? 10 : 0;
    }

    public boolean smallerThanMinDimension(ActivityRecord activityRecord) {
        Point minDimensions;
        Rect bounds = getBounds();
        Task task = getTask();
        if (task == null || bounds.equals(task.getBounds()) || (minDimensions = activityRecord.getMinDimensions()) == null) {
            return false;
        }
        return bounds.width() < minDimensions.x || bounds.height() < minDimensions.y;
    }

    public boolean isAllowedToEmbedActivityInUntrustedMode(ActivityRecord activityRecord) {
        WindowContainer parent = getParent();
        return parent != null && parent.getBounds().contains(getBounds()) && (activityRecord.info.flags & 268435456) == 268435456;
    }

    public boolean isAllowedToEmbedActivityInTrustedMode(ActivityRecord activityRecord) {
        return isAllowedToEmbedActivityInTrustedMode(activityRecord, this.mTaskFragmentOrganizerUid);
    }

    public boolean isAllowedToEmbedActivityInTrustedMode(ActivityRecord activityRecord, int i) {
        if (isFullyTrustedEmbedding(activityRecord, i)) {
            return true;
        }
        Set<String> knownActivityEmbeddingCerts = activityRecord.info.getKnownActivityEmbeddingCerts();
        if (knownActivityEmbeddingCerts.isEmpty()) {
            return false;
        }
        AndroidPackage androidPackage = this.mAtmService.getPackageManagerInternalLocked().getPackage(i);
        return androidPackage != null && androidPackage.getSigningDetails().hasAncestorOrSelfWithDigest(knownActivityEmbeddingCerts);
    }

    public static boolean isFullyTrustedEmbedding(ActivityRecord activityRecord, int i) {
        return UserHandle.getAppId(i) == 1000 || activityRecord.isUid(i);
    }

    public static /* synthetic */ boolean lambda$isFullyTrustedEmbedding$0(int i, ActivityRecord activityRecord) {
        return !isFullyTrustedEmbedding(activityRecord, i);
    }

    public boolean isFullyTrustedEmbedding(final int i) {
        return !forAllActivities(new Predicate() { // from class: com.android.server.wm.TaskFragment$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$isFullyTrustedEmbedding$0;
                lambda$isFullyTrustedEmbedding$0 = TaskFragment.lambda$isFullyTrustedEmbedding$0(i, (ActivityRecord) obj);
                return lambda$isFullyTrustedEmbedding$0;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$isAllowedToBeEmbeddedInTrustedMode$1(ActivityRecord activityRecord) {
        return !isAllowedToEmbedActivityInTrustedMode(activityRecord);
    }

    public boolean isAllowedToBeEmbeddedInTrustedMode() {
        return !forAllActivities(new Predicate() { // from class: com.android.server.wm.TaskFragment$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$isAllowedToBeEmbeddedInTrustedMode$1;
                lambda$isAllowedToBeEmbeddedInTrustedMode$1 = TaskFragment.this.lambda$isAllowedToBeEmbeddedInTrustedMode$1((ActivityRecord) obj);
                return lambda$isAllowedToBeEmbeddedInTrustedMode$1;
            }
        });
    }

    public TaskFragment getOrganizedTaskFragment() {
        if (this.mTaskFragmentOrganizer != null) {
            return this;
        }
        TaskFragment asTaskFragment = getParent() != null ? getParent().asTaskFragment() : null;
        if (asTaskFragment != null) {
            return asTaskFragment.getOrganizedTaskFragment();
        }
        return null;
    }

    public final void warnForNonLeafTaskFragment(String str) {
        if (isLeafTaskFragment()) {
            return;
        }
        Slog.w("ActivityTaskManager", str + " on non-leaf task fragment " + this);
    }

    public boolean hasDirectChildActivities() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if (((WindowContainer) this.mChildren.get(size)).asActivityRecord() != null) {
                return true;
            }
        }
        return false;
    }

    public void cleanUpActivityReferences(ActivityRecord activityRecord) {
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

    public boolean isLeafTaskFragment() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if (((WindowContainer) this.mChildren.get(size)).asTaskFragment() != null) {
                return false;
            }
        }
        return true;
    }

    public void onActivityStateChanged(ActivityRecord activityRecord, ActivityRecord.State state, String str) {
        warnForNonLeafTaskFragment("onActivityStateChanged");
        if (activityRecord == this.mResumedActivity && state != ActivityRecord.State.RESUMED) {
            setResumedActivity(null, str + " - onActivityStateChanged");
        }
        if (state == ActivityRecord.State.RESUMED) {
            setResumedActivity(activityRecord, str + " - onActivityStateChanged");
            this.mTaskSupervisor.mRecentTasks.add(activityRecord.getTask());
        }
        WindowProcessController organizerProcessIfDifferent = getOrganizerProcessIfDifferent(activityRecord);
        if (organizerProcessIfDifferent != null) {
            this.mTaskSupervisor.onProcessActivityStateChanged(organizerProcessIfDifferent, false);
            organizerProcessIfDifferent.updateProcessInfo(false, true, true, false);
        }
    }

    public boolean handleAppDied(WindowProcessController windowProcessController) {
        warnForNonLeafTaskFragment("handleAppDied");
        ActivityRecord activityRecord = this.mPausingActivity;
        boolean z = false;
        if (activityRecord != null && activityRecord.app == windowProcessController) {
            if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_STATES, -1564228464, 0, (String) null, new Object[]{String.valueOf(activityRecord)});
            }
            this.mPausingActivity = null;
            z = true;
        }
        ActivityRecord activityRecord2 = this.mLastPausedActivity;
        if (activityRecord2 != null && activityRecord2.app == windowProcessController) {
            this.mLastPausedActivity = null;
        }
        return z;
    }

    public void awakeFromSleeping() {
        if (this.mPausingActivity != null) {
            Slog.d("ActivityTaskManager", "awakeFromSleeping: previously pausing activity didn't pause");
            this.mPausingActivity.activityPaused(true);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x006a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean sleepIfPossible(boolean z) {
        boolean z2;
        ActivityRecord activityRecord = this.mResumedActivity;
        if (activityRecord != null) {
            if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_STATES, 987903142, 0, (String) null, new Object[]{String.valueOf(activityRecord)});
            }
            startPausing(false, true, null, "sleep");
        } else {
            ActivityRecord activityRecord2 = this.mPausingActivity;
            if (activityRecord2 != null) {
                if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
                    ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_STATES, 1912291550, 0, (String) null, new Object[]{String.valueOf(activityRecord2)});
                }
            } else {
                z2 = true;
                if (!z && containsStoppingActivity()) {
                    if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
                        ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_STATES, 669361121, 1, (String) null, new Object[]{Long.valueOf(this.mTaskSupervisor.mStoppingActivities.size())});
                    }
                    this.mTaskSupervisor.scheduleIdle();
                    z2 = false;
                }
                if (z2) {
                    updateActivityVisibilities(null, 0, false, true);
                }
                return z2;
            }
        }
        z2 = false;
        if (!z) {
            if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
            }
            this.mTaskSupervisor.scheduleIdle();
            z2 = false;
        }
        if (z2) {
        }
        return z2;
    }

    public final boolean containsStoppingActivity() {
        for (int size = this.mTaskSupervisor.mStoppingActivities.size() - 1; size >= 0; size--) {
            if (((ActivityRecord) this.mTaskSupervisor.mStoppingActivities.get(size)).getTaskFragment() == this) {
                return true;
            }
        }
        return false;
    }

    public boolean isTranslucent(ActivityRecord activityRecord) {
        return !isAttached() || isForceHidden() || isForceTranslucent() || this.mTaskSupervisor.mOpaqueActivityHelper.getVisibleOpaqueActivity(this, activityRecord) == null;
    }

    public boolean isTranslucentForTransition() {
        return !isAttached() || isForceHidden() || isForceTranslucent() || this.mTaskSupervisor.mOpaqueActivityHelper.getOpaqueActivity(this) == null;
    }

    public ActivityRecord getTopNonFinishingActivity() {
        return getTopNonFinishingActivity(true);
    }

    public static /* synthetic */ boolean lambda$getTopNonFinishingActivity$2(ActivityRecord activityRecord) {
        return !activityRecord.finishing;
    }

    public ActivityRecord getTopNonFinishingActivity(boolean z) {
        if (z) {
            return getActivity(new Predicate() { // from class: com.android.server.wm.TaskFragment$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$getTopNonFinishingActivity$2;
                    lambda$getTopNonFinishingActivity$2 = TaskFragment.lambda$getTopNonFinishingActivity$2((ActivityRecord) obj);
                    return lambda$getTopNonFinishingActivity$2;
                }
            });
        }
        return getActivity(new Predicate() { // from class: com.android.server.wm.TaskFragment$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$getTopNonFinishingActivity$3;
                lambda$getTopNonFinishingActivity$3 = TaskFragment.lambda$getTopNonFinishingActivity$3((ActivityRecord) obj);
                return lambda$getTopNonFinishingActivity$3;
            }
        });
    }

    public static /* synthetic */ boolean lambda$getTopNonFinishingActivity$3(ActivityRecord activityRecord) {
        return (activityRecord.finishing || activityRecord.isTaskOverlay()) ? false : true;
    }

    public ActivityRecord topRunningActivity() {
        return topRunningActivity(false);
    }

    public ActivityRecord topRunningActivity(boolean z) {
        if (getTask() != null && getTask().isMinimized()) {
            return null;
        }
        if (z) {
            return getActivity(new Predicate() { // from class: com.android.server.wm.TaskFragment$$ExternalSyntheticLambda6
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$topRunningActivity$4;
                    lambda$topRunningActivity$4 = TaskFragment.lambda$topRunningActivity$4((ActivityRecord) obj);
                    return lambda$topRunningActivity$4;
                }
            });
        }
        return getActivity(new Task$$ExternalSyntheticLambda17());
    }

    public static /* synthetic */ boolean lambda$topRunningActivity$4(ActivityRecord activityRecord) {
        return activityRecord.canBeTopRunning() && activityRecord.isFocusable();
    }

    public int getNonFinishingActivityCount() {
        final int[] iArr = new int[1];
        forAllActivities(new Consumer() { // from class: com.android.server.wm.TaskFragment$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                TaskFragment.lambda$getNonFinishingActivityCount$5(iArr, (ActivityRecord) obj);
            }
        });
        return iArr[0];
    }

    public static /* synthetic */ void lambda$getNonFinishingActivityCount$5(int[] iArr, ActivityRecord activityRecord) {
        if (activityRecord.finishing) {
            return;
        }
        iArr[0] = iArr[0] + 1;
    }

    public boolean isTopActivityFocusable() {
        ActivityRecord activityRecord = topRunningActivity();
        if (activityRecord != null) {
            return activityRecord.isFocusable();
        }
        return isFocusable() && getWindowConfiguration().canReceiveKeys();
    }

    /* JADX WARN: Code restructure failed: missing block: B:161:0x0261, code lost:
    
        return 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:206:0x026e, code lost:
    
        if (r1 != false) goto L190;
     */
    /* JADX WARN: Code restructure failed: missing block: B:207:0x0270, code lost:
    
        return 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:209:0x0273, code lost:
    
        if (com.samsung.android.rune.CoreRune.MW_MULTI_SPLIT_TASK_VISIBILITY == false) goto L200;
     */
    /* JADX WARN: Code restructure failed: missing block: B:211:0x0279, code lost:
    
        if (asTask() == null) goto L200;
     */
    /* JADX WARN: Code restructure failed: missing block: B:213:0x0280, code lost:
    
        if (getStageType() != 4) goto L200;
     */
    /* JADX WARN: Code restructure failed: missing block: B:214:0x0282, code lost:
    
        if (r10 == false) goto L198;
     */
    /* JADX WARN: Code restructure failed: missing block: B:215:0x0284, code lost:
    
        return 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:216:0x0285, code lost:
    
        if (r11 == false) goto L200;
     */
    /* JADX WARN: Code restructure failed: missing block: B:217:0x0287, code lost:
    
        return 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:218:0x0288, code lost:
    
        if (r5 != 0) goto L208;
     */
    /* JADX WARN: Code restructure failed: missing block: B:220:0x028e, code lost:
    
        if (asTask() == null) goto L208;
     */
    /* JADX WARN: Code restructure failed: missing block: B:222:0x0296, code lost:
    
        if (asTask().mHiddenWhileActivatingDrag == false) goto L208;
     */
    /* JADX WARN: Code restructure failed: missing block: B:224:0x029e, code lost:
    
        if (asTask().mIsAnimatingByRecentsAndDragSourceTask != false) goto L208;
     */
    /* JADX WARN: Code restructure failed: missing block: B:225:0x02a0, code lost:
    
        android.util.Slog.d("ActivityTaskManager", "[TWODND] Clear mHiddenWhileActivatingDrag");
        asTask().mHiddenWhileActivatingDrag = false;
        asTask().updateSurfaceVisibilityForDragAndDrop();
     */
    /* JADX WARN: Code restructure failed: missing block: B:226:0x02b3, code lost:
    
        return r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:227:0x026d, code lost:
    
        r1 = true;
     */
    /* JADX WARN: Removed duplicated region for block: B:229:0x026d A[EDGE_INSN: B:229:0x026d->B:227:0x026d BREAK  A[LOOP:0: B:35:0x007f->B:55:0x0268], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0084  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int getVisibility(ActivityRecord activityRecord) {
        int i;
        int childCount;
        TaskFragment taskFragment;
        if (!isAttached() || isForceHidden()) {
            return 2;
        }
        boolean z = false;
        if (isTopActivityLaunchedBehind()) {
            return 0;
        }
        WindowContainer parent = getParent();
        Task asTask = asTask();
        if (asTask != null && parent.asTask() == null && this.mTransitionController.isTransientHide(asTask)) {
            return 0;
        }
        if (inFreeformWindowingMode() && getTask() != null && !getTask().isMinimized() && !isAlwaysOnTop() && getTask().isUnderHomeRootTask()) {
            return 2;
        }
        if (parent.asTaskFragment() != null) {
            int visibility = parent.asTaskFragment().getVisibility(activityRecord);
            if (visibility == 2) {
                return 2;
            }
            if (visibility == 1) {
                i = 1;
                ArrayList arrayList = new ArrayList();
                childCount = parent.getChildCount() - 1;
                boolean z2 = false;
                boolean z3 = false;
                boolean z4 = false;
                while (true) {
                    if (childCount < 0) {
                        break;
                    }
                    WindowContainer childAt = parent.getChildAt(childCount);
                    if (childAt != null) {
                        boolean hasRunningActivity = hasRunningActivity(childAt);
                        if (childAt == this) {
                            if (!arrayList.isEmpty() && !z2) {
                                ((WindowContainer) this).mTmpRect.set(getBounds());
                                for (int size = arrayList.size() - 1; size >= 0; size--) {
                                    TaskFragment taskFragment2 = (TaskFragment) arrayList.get(size);
                                    TaskFragment taskFragment3 = taskFragment2.mAdjacentTaskFragment;
                                    if (taskFragment3 != this && (!(CoreRune.MW_MULTI_SPLIT_TASK_VISIBILITY && getStageType() == 4 && inStageMainOrSide(taskFragment2.getStageType()) && inStageMainOrSide(taskFragment3.getStageType())) && (((WindowContainer) this).mTmpRect.intersect(taskFragment2.getBounds()) || ((WindowContainer) this).mTmpRect.intersect(taskFragment3.getBounds())))) {
                                        return 2;
                                    }
                                }
                            }
                            if (!hasRunningActivity && ((activityRecord == null || !activityRecord.isDescendantOf(this)) && !isActivityTypeHome())) {
                                boolean z5 = z;
                            }
                        } else if (hasRunningActivity) {
                            int windowingMode = childAt.getWindowingMode();
                            if (windowingMode == 1) {
                                Task asTask2 = childAt.asTask();
                                TaskDisplayArea taskDisplayArea = getTaskDisplayArea();
                                if (asTask2 == null || !asTask2.isFullscreenRootForStageTask() || !this.mAtmService.mWindowOrganizerController.whileEnterSplitWithSingleStage() || (taskDisplayArea.isSplitScreenModeActivated() && taskDisplayArea.hasChildTaskInSideStage())) {
                                    if (!isTranslucent(childAt, activityRecord)) {
                                        if (asTask() != null && asTask().mIsDragSourceTask && !isActivityTypeHomeOrRecents() && this.mWmService.mDragDropController.dragDropActiveLocked()) {
                                            Slog.d("ActivityTaskManager", "[TWODND] Set mHiddenWhileActivatingDrag");
                                            this.mWmService.mDragDropController.setUpdateTaskVisibilityAfterDragClosed(true);
                                            asTask().mHiddenWhileActivatingDrag = true;
                                        } else {
                                            if (!CoreRune.MW_MULTI_SPLIT_TASK_VISIBILITY || !inSplitScreenWindowingMode() || childAt.getStageType() != 4 || asTask() == null) {
                                                break;
                                            }
                                            z3 = true;
                                        }
                                    }
                                    i = 1;
                                }
                            } else {
                                if (CoreRune.MW_EMBED_ACTIVITY && isEmbedded() && windowingMode == 5 && childAt.matchParentBounds()) {
                                    if (!isTranslucent(childAt, activityRecord)) {
                                        return 2;
                                    }
                                } else {
                                    if (CoreRune.MW_EMBED_ACTIVITY && isSplitEmbedded() && childAt.isSplitEmbedded() && childAt.getBounds().equals(getBounds()) && !isTranslucent(childAt, activityRecord)) {
                                        return 2;
                                    }
                                    if (windowingMode == 6 && childAt.matchParentBounds()) {
                                        if (!isTranslucent(childAt, activityRecord)) {
                                            if (!inSplitScreenWindowingMode() || asTask() == null || !asTask().mIsDragSourceTask || !this.mWmService.mDragDropController.dragDropActiveLocked()) {
                                                break;
                                            }
                                            Slog.d("ActivityTaskManager", "[TWODND] Set mHiddenWhileActivatingDrag(split)");
                                            this.mWmService.mDragDropController.setUpdateTaskVisibilityAfterDragClosed(true);
                                            asTask().mHiddenWhileActivatingDrag = true;
                                        }
                                    } else if (CoreRune.MW_MULTI_SPLIT_TASK_VISIBILITY && childAt.getStageType() == 4 && !z3 && asTask() != null) {
                                        if (isTranslucent(activityRecord)) {
                                            z4 = true;
                                        }
                                        z3 = !z4;
                                        if (getStageType() == 4 && z3) {
                                            return 2;
                                        }
                                    } else {
                                        TaskFragment asTaskFragment = childAt.asTaskFragment();
                                        if (asTaskFragment != null && (taskFragment = asTaskFragment.mAdjacentTaskFragment) != null) {
                                            if (arrayList.contains(taskFragment)) {
                                                if (!asTaskFragment.isTranslucent(activityRecord) && !asTaskFragment.mAdjacentTaskFragment.isTranslucent(activityRecord)) {
                                                    if (!CoreRune.MW_MULTI_SPLIT_TASK_VISIBILITY || getStageType() != 4) {
                                                        break;
                                                    }
                                                } else {
                                                    i = 1;
                                                    z2 = true;
                                                }
                                            } else {
                                                arrayList.add(asTaskFragment);
                                            }
                                        }
                                    }
                                }
                                i = 1;
                            }
                        } else {
                            continue;
                        }
                    }
                    childCount--;
                    z = false;
                }
                return 2;
            }
        }
        i = 0;
        ArrayList arrayList2 = new ArrayList();
        childCount = parent.getChildCount() - 1;
        boolean z22 = false;
        boolean z32 = false;
        boolean z42 = false;
        while (true) {
            if (childCount < 0) {
            }
            childCount--;
            z = false;
        }
        return 2;
    }

    public static boolean hasRunningActivity(WindowContainer windowContainer) {
        return windowContainer.asTaskFragment() != null ? windowContainer.asTaskFragment().topRunningActivity() != null : (windowContainer.asActivityRecord() == null || windowContainer.asActivityRecord().finishing) ? false : true;
    }

    public static boolean isTranslucent(WindowContainer windowContainer, ActivityRecord activityRecord) {
        if (windowContainer.asTaskFragment() != null) {
            return windowContainer.asTaskFragment().isTranslucent(activityRecord);
        }
        if (windowContainer.asActivityRecord() != null) {
            return !windowContainer.asActivityRecord().occludesParent();
        }
        return false;
    }

    public final boolean isTopActivityLaunchedBehind() {
        ActivityRecord activityRecord = topRunningActivity();
        return activityRecord != null && activityRecord.mLaunchTaskBehind;
    }

    public final void updateActivityVisibilities(ActivityRecord activityRecord, int i, boolean z, boolean z2) {
        this.mTaskSupervisor.beginActivityVisibilityUpdate(getDisplayContent());
        try {
            this.mEnsureActivitiesVisibleHelper.process(activityRecord, i, z, z2);
            this.mTaskSupervisor.endActivityVisibilityUpdate();
            if (CoreRune.MT_SUPPORT_SIZE_COMPAT) {
                SizeCompatPolicyManager.get().ensureConfiguration(asTask());
            }
        } catch (Throwable th) {
            this.mTaskSupervisor.endActivityVisibilityUpdate();
            throw th;
        }
    }

    public final boolean resumeTopActivity(ActivityRecord activityRecord, ActivityOptions activityOptions, boolean z) {
        return resumeTopActivity(activityRecord, activityOptions, z, null);
    }

    public final boolean resumeTopActivity(ActivityRecord activityRecord, ActivityOptions activityOptions, boolean z, ActivityRecord activityRecord2) {
        boolean z2;
        ActivityRecord activityRecord3;
        boolean startPausing;
        Task task;
        DisplayContent displayContent;
        ActivityRecord activityRecord4;
        ActivityRecord activityRecord5 = topRunningActivity(getTask() == null || !getTask().isFreeformStashed());
        if (activityRecord5 == null || !activityRecord5.canResumeByCompat()) {
            return false;
        }
        activityRecord5.delayedResume = false;
        boolean allPausedActivitiesComplete = this.mRootWindowContainer.allPausedActivitiesComplete();
        if (!(CoreRune.MW_EMBED_ACTIVITY && !allPausedActivitiesComplete && activityRecord5.isSplitEmbedded() && getAdjacentTaskFragment() != null && getAdjacentTaskFragment().getTopMostActivity() != null && getAdjacentTaskFragment().getTopMostActivity().isState(ActivityRecord.State.RESUMED)) && !allPausedActivitiesComplete) {
            if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_STATES, 935418348, 0, (String) null, (Object[]) null);
            }
            return false;
        }
        TaskDisplayArea displayArea = getDisplayArea();
        if (this.mResumedActivity == activityRecord5 && activityRecord5.isState(ActivityRecord.State.RESUMED) && displayArea.allResumedActivitiesComplete()) {
            displayArea.ensureActivitiesVisible(null, 0, false, true);
            ActivityRecord activityRecord6 = this.mDisplayContent.mFocusedApp;
            if (activityRecord6 != null && activityRecord6.getLaunchedFromBubble() && this.mDisplayContent.mFocusedApp != activityRecord5) {
                this.mAtmService.setLastResumedActivityUncheckLocked(activityRecord5, "bubble-collapse");
            }
            if (!(getTask() != null ? getTask().inMultiWindowMode() : inMultiWindowMode()) && activityOptions != null && activityOptions.isResumedAffordanceAnimationRequested()) {
                this.mTransitionController.collect(activityRecord5.getTask());
                this.mTransitionController.setResumedAffordance(activityRecord5.getTask());
                Slog.d("ActivityTaskManager", "setResumedAffordance for " + activityRecord5);
            }
            if (getTask() != null && this.mAtmService.mMultiTaskingController.isAffordanceTargetTask(getTask())) {
                final Task task2 = activityRecord5.getTask();
                this.mTransitionController.collect(task2);
                this.mTransitionController.setResumedAffordance(task2);
                if (inFreeformWindowingMode()) {
                    this.mTransitionController.setAffordanceTargetFreeformTask(task2);
                    TaskDisplayArea taskDisplayArea = task2.getTaskDisplayArea();
                    if (taskDisplayArea != null) {
                        taskDisplayArea.forAllRootTasks(new Consumer() { // from class: com.android.server.wm.TaskFragment$$ExternalSyntheticLambda10
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                TaskFragment.this.lambda$resumeTopActivity$6(task2, (Task) obj);
                            }
                        });
                    }
                }
                Slog.d("ActivityTaskManager", "setResumedAffordance(multi-window) for " + activityRecord5);
            }
            executeAppTransition(activityOptions);
            if (displayArea.inMultiWindowMode() && (displayContent = displayArea.mDisplayContent) != null && displayContent.mFocusedApp != activityRecord5 && !activityRecord5.inSplitScreenWindowingMode() && (!CoreRune.MW_EMBED_ACTIVITY || (activityRecord4 = displayArea.mDisplayContent.mFocusedApp) == null || activityRecord4.getTask() != activityRecord5.getTask() || !displayArea.mDisplayContent.mFocusedApp.isEmbedded())) {
                displayArea.mDisplayContent.setFocusedApp(activityRecord5);
            }
            if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
                ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_STATES, 675705156, 0, (String) null, new Object[]{String.valueOf(activityRecord5)});
            }
            return false;
        }
        if (CoreRune.MT_SUPPORT_SIZE_COMPAT) {
            SizeCompatPolicyManager.get().ensureConfiguration(activityRecord5.getTask());
        }
        if (isDexMode() && (task = activityRecord5.getTask()) != null && this.mAtmService.mDexCompatController.isOrientationChangedLocked(task, activityRecord5)) {
            this.mAtmService.mDexCompatController.rotateDexCompatTaskLocked(activityRecord5);
        }
        if (this.mLastPausedActivity == activityRecord5 && shouldSleepOrShutDownActivities()) {
            executeAppTransition(activityOptions);
            if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
                ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_STATES, -1886145147, 0, (String) null, (Object[]) null);
            }
            return false;
        }
        if (!this.mAtmService.mAmInternal.hasStartedUserState(activityRecord5.mUserId)) {
            Slog.w("ActivityTaskManager", "Skipping resume of top activity " + activityRecord5 + ": user " + activityRecord5.mUserId + " is stopped");
            return false;
        }
        this.mTaskSupervisor.mStoppingActivities.remove(activityRecord5);
        this.mTaskSupervisor.setLaunchSource(activityRecord5.info.applicationInfo.uid);
        Task lastFocusedRootTask = displayArea.getLastFocusedRootTask();
        ActivityRecord topResumedActivity = (lastFocusedRootTask == null || lastFocusedRootTask == getRootTaskFragment().asTask()) ? null : lastFocusedRootTask.getTopResumedActivity();
        boolean z3 = !z && displayArea.pauseBackTasks(activityRecord5);
        if (this.mResumedActivity != null) {
            if (this.mTaskSupervisor.mUserLeaving && inSplitScreenWindowingMode()) {
                Slog.d("ActivityTaskManager", "resumeTopActivity: clear userLeaving");
                startPausing = startPausing(false, false, activityRecord5, "resumeTopActivity");
            } else {
                if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
                    ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_STATES, 102618780, 0, (String) null, new Object[]{String.valueOf(this.mResumedActivity)});
                }
                startPausing = startPausing(this.mTaskSupervisor.mUserLeaving, false, activityRecord5, "resumeTopActivity");
            }
            z3 |= startPausing;
        }
        if (z3) {
            if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_STATES, 341055768, 0, (String) null, (Object[]) null);
            }
            if (activityRecord5.attachedToProcess()) {
                activityRecord5.app.updateProcessInfo(false, true, false, false);
            } else if (!activityRecord5.isProcessRunning()) {
                boolean z4 = this == displayArea.getFocusedRootTask();
                this.mAtmService.startProcessAsync(activityRecord5, false, z4, z4 ? "next-top-activity" : "next-activity");
            }
            if (topResumedActivity != null) {
                topResumedActivity.setWillCloseOrEnterPip(true);
            }
            return true;
        }
        if (this.mResumedActivity == activityRecord5 && activityRecord5.isState(ActivityRecord.State.RESUMED) && displayArea.allResumedActivitiesComplete()) {
            executeAppTransition(activityOptions);
            if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
                ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_STATES, -2010331310, 0, (String) null, new Object[]{String.valueOf(activityRecord5)});
            }
            return true;
        }
        ActivityManagerInternal activityManagerInternal = this.mAtmService.mAmInternal;
        WindowProcessController windowProcessController = activityRecord5.app;
        activityManagerInternal.onPackageResumedFG(windowProcessController != null ? windowProcessController.getPackageList() : null, activityRecord5.packageName, activityRecord5.launchedFromPackage, activityRecord5.occludesParent(), activityRecord5.intent, activityRecord5.mUserId);
        ActivityManagerPerformance activityManagerPerformance = this.mAtmService.mAMBooster;
        if (activityManagerPerformance != null) {
            activityManagerPerformance.onActivityResumeLocked(activityRecord5);
        }
        PerProcessNandswap.getInstance().notifyReentryAppToPPR(activityRecord5.getPid());
        if (shouldSleepActivities()) {
            this.mTaskSupervisor.finishNoHistoryActivitiesIfNeeded(activityRecord5);
        }
        if (activityRecord != null && activityRecord != activityRecord5 && activityRecord5.nowVisible && activityRecord.finishing) {
            activityRecord.setVisibility(false);
        }
        try {
            this.mTaskSupervisor.getActivityMetricsLogger().notifyBeforePackageUnstopped(activityRecord5.packageName);
            this.mAtmService.getPackageManager().setPackageStoppedState(activityRecord5.packageName, false, activityRecord5.mUserId);
        } catch (RemoteException unused) {
        } catch (IllegalArgumentException e) {
            Slog.w("ActivityTaskManager", "Failed trying to unstop package " + activityRecord5.packageName + ": " + e);
        }
        DisplayContent displayContent2 = displayArea.mDisplayContent;
        if (activityRecord != null) {
            if (activityRecord.finishing) {
                if (this.mTaskSupervisor.mNoAnimActivities.contains(activityRecord)) {
                    displayContent2.prepareAppTransition(0);
                    z2 = false;
                } else {
                    displayContent2.prepareAppTransition(2);
                    z2 = true;
                }
                activityRecord.setVisibility(false);
            } else if (this.mTaskSupervisor.mNoAnimActivities.contains(activityRecord5)) {
                displayContent2.prepareAppTransition(0);
                z2 = false;
            } else {
                displayContent2.prepareAppTransition(1, activityRecord5.mLaunchTaskBehind ? 32 : 0);
                z2 = true;
            }
        } else if (this.mTaskSupervisor.mNoAnimActivities.contains(activityRecord5)) {
            displayContent2.prepareAppTransition(0);
            z2 = false;
        } else {
            displayContent2.prepareAppTransition(1);
            z2 = true;
        }
        if (z2) {
            activityRecord5.applyOptionsAnimation();
        } else {
            activityRecord5.abortAndClearOptionsAnimation();
        }
        this.mTaskSupervisor.mNoAnimActivities.clear();
        if (activityRecord5.attachedToProcess() && activityRecord5.getState() != ActivityRecord.State.INITIALIZING) {
            boolean z5 = inMultiWindowMode() || !((activityRecord3 = this.mLastPausedActivity) == null || activityRecord3.occludesParent());
            if (!activityRecord5.isVisibleRequested() || activityRecord5.mAppStopped || z5) {
                activityRecord5.app.addToPendingTop();
                activityRecord5.setVisibility(true);
            }
            activityRecord5.startLaunchTickingLocked();
            ActivityRecord topResumedActivity2 = lastFocusedRootTask == null ? null : lastFocusedRootTask.getTopResumedActivity();
            ActivityRecord.State state = activityRecord5.getState();
            this.mAtmService.updateCpuStats();
            if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_STATES, -1421296808, 0, (String) null, new Object[]{String.valueOf(activityRecord5)});
            }
            activityRecord5.setState(ActivityRecord.State.RESUMED, "resumeTopActivity");
            ActivityManagerPerformance.notifyCurTopAct(activityRecord5);
            if (shouldBeVisible(activityRecord5) ? !this.mRootWindowContainer.ensureVisibilityAndConfig(activityRecord5, getDisplayId(), true, false) : true) {
                ActivityRecord activityRecord7 = topRunningActivity();
                if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
                    ProtoLogImpl.i(ProtoLogGroup.WM_DEBUG_STATES, -310337305, 0, (String) null, new Object[]{String.valueOf(activityRecord5), String.valueOf(activityRecord7)});
                }
                if (activityRecord7 != activityRecord5) {
                    this.mTaskSupervisor.scheduleResumeTopActivities();
                }
                if (!activityRecord5.isVisibleRequested() || activityRecord5.mAppStopped) {
                    activityRecord5.setVisibility(true);
                }
                activityRecord5.completeResumeLocked();
                return true;
            }
            try {
                ClientTransaction obtain = ClientTransaction.obtain(activityRecord5.app.getThread(), activityRecord5.token);
                ArrayList arrayList = activityRecord5.results;
                if (arrayList != null) {
                    int size = arrayList.size();
                    if (!activityRecord5.finishing && size > 0) {
                        obtain.addCallback(ActivityResultItem.obtain(arrayList));
                    }
                }
                ArrayList arrayList2 = activityRecord5.newIntents;
                if (arrayList2 != null) {
                    obtain.addCallback(NewIntentItem.obtain(arrayList2, true));
                }
                activityRecord5.notifyAppResumed();
                EventLogTags.writeWmResumeActivity(activityRecord5.mUserId, System.identityHashCode(activityRecord5), activityRecord5.getTask().mTaskId, activityRecord5.shortComponentName);
                this.mAtmService.mExt.boostPriority(activityRecord5);
                this.mAtmService.getAppWarningsLocked().onResumeActivity(activityRecord5);
                activityRecord5.app.setPendingUiCleanAndForceProcessStateUpTo(this.mAtmService.mTopProcessState);
                activityRecord5.abortAndClearOptionsAnimation();
                obtain.setLifecycleStateRequest(ResumeActivityItem.obtain(activityRecord5.app.getReportedProcState(), displayContent2.isNextTransitionForward(), activityRecord5.shouldSendCompatFakeFocus()));
                this.mAtmService.getLifecycleManager().scheduleTransaction(obtain);
                if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
                    ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_STATES, -1419461256, 0, (String) null, new Object[]{String.valueOf(activityRecord5)});
                }
                try {
                    activityRecord5.completeResumeLocked();
                } catch (Exception e2) {
                    Slog.w("ActivityTaskManager", "Exception thrown during resume of " + activityRecord5, e2);
                    activityRecord5.finishIfPossible("resume-exception", true);
                    return true;
                }
            } catch (Exception unused2) {
                if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
                    ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_STATES, -80004683, 0, (String) null, new Object[]{String.valueOf(state), String.valueOf(activityRecord5)});
                }
                activityRecord5.setState(state, "resumeTopActivityInnerLocked");
                if (topResumedActivity2 != null) {
                    topResumedActivity2.setState(ActivityRecord.State.RESUMED, "resumeTopActivityInnerLocked");
                }
                Slog.i("ActivityTaskManager", "Restarting because process died: " + activityRecord5);
                if (!activityRecord5.hasBeenLaunched) {
                    activityRecord5.hasBeenLaunched = true;
                } else if (lastFocusedRootTask != null && lastFocusedRootTask.isTopRootTaskInDisplayArea()) {
                    activityRecord5.showStartingWindow(false);
                }
                this.mTaskSupervisor.startSpecificActivity(activityRecord5, true, false);
                return true;
            }
        } else {
            if (!activityRecord5.hasBeenLaunched) {
                activityRecord5.hasBeenLaunched = true;
            } else {
                activityRecord5.showStartingWindow(false);
            }
            if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
                ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_STATES, 1856783490, 0, (String) null, new Object[]{String.valueOf(activityRecord5)});
            }
            this.mTaskSupervisor.startSpecificActivity(activityRecord5, true, true);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$resumeTopActivity$6(Task task, Task task2) {
        if (task != task2 && task2.inFreeformWindowingMode() && task2.isVisible()) {
            this.mTransitionController.collect(task2);
            this.mTransitionController.setResumedAffordance(task2);
        }
    }

    public boolean shouldSleepOrShutDownActivities() {
        return shouldSleepActivities() || this.mAtmService.mShuttingDown;
    }

    public boolean shouldBeVisible(ActivityRecord activityRecord) {
        return getVisibility(activityRecord) != 2;
    }

    public boolean canBeResumed(ActivityRecord activityRecord) {
        return (getTask() == null || !getTask().isFreeformStashed()) ? isTopActivityFocusable() && getVisibility(activityRecord) == 0 : topRunningActivity() != null && getVisibility(activityRecord) == 0;
    }

    public boolean isFocusableAndVisible() {
        return isTopActivityFocusable() && shouldBeVisible(null);
    }

    public final boolean startPausing(boolean z, ActivityRecord activityRecord, String str) {
        return startPausing(this.mTaskSupervisor.mUserLeaving, z, activityRecord, str);
    }

    public boolean startPausing(boolean z, boolean z2, ActivityRecord activityRecord, String str) {
        return startPausing(z, z2, activityRecord, null, str);
    }

    public boolean startPausing(boolean z, boolean z2, ActivityRecord activityRecord, ActivityRecord activityRecord2, String str) {
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        if (!hasDirectChildActivities()) {
            return false;
        }
        if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
            ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_STATES, -248761393, 0, (String) null, new Object[]{String.valueOf(this), String.valueOf(this.mResumedActivity)});
        }
        if (this.mPausingActivity != null) {
            Slog.wtf("ActivityTaskManager", "Going to pause when pause is already pending for " + this.mPausingActivity + " state=" + this.mPausingActivity.getState());
            if (!shouldSleepActivities()) {
                completePause(false, activityRecord);
            }
        }
        ActivityRecord activityRecord3 = this.mResumedActivity;
        if (activityRecord3 == null) {
            if (activityRecord == null) {
                Slog.wtf("ActivityTaskManager", "Trying to pause when nothing is resumed");
                this.mRootWindowContainer.resumeFocusedTasksTopActivities();
            }
            return false;
        }
        if (activityRecord3 == activityRecord) {
            Slog.wtf("ActivityTaskManager", "Trying to pause activity that is in process of being resumed");
            return false;
        }
        if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_STATES, -957060823, 0, (String) null, new Object[]{String.valueOf(activityRecord3)});
        }
        this.mPausingActivity = activityRecord3;
        this.mLastPausedActivity = activityRecord3;
        if (!activityRecord3.finishing && activityRecord3.isNoHistory() && !this.mTaskSupervisor.mNoHistoryActivities.contains(activityRecord3)) {
            this.mTaskSupervisor.mNoHistoryActivities.add(activityRecord3);
        }
        ActivityRecord.State state = ActivityRecord.State.PAUSING;
        activityRecord3.setState(state, "startPausingLocked");
        activityRecord3.getTask().touchActiveTime();
        this.mAtmService.updateCpuStats();
        if (activityRecord != null) {
            boolean z7 = activityRecord.occludesParent() || this.mAtmService.mAppPairController.shouldAutoPipByAppPair();
            boolean checkEnterPictureInPictureState = activityRecord3.checkEnterPictureInPictureState("shouldAutoPipWhilePausing", z);
            if (z && z7 && checkEnterPictureInPictureState && activityRecord3.pictureInPictureArgs.isAutoEnterEnabled()) {
                z5 = false;
                z6 = true;
            } else if (checkEnterPictureInPictureState) {
                z5 = false;
                z6 = false;
            } else {
                z5 = (activityRecord.info.flags & 16384) != 0;
                z6 = false;
            }
            if (checkEnterPictureInPictureState && !z6) {
                Slog.d("PipTaskOrganizer", "entering autoPip failed, userLeaving=" + z + " resumingOccludesParent=" + z7 + " isAutoEnterEnabled=" + activityRecord3.pictureInPictureArgs.isAutoEnterEnabled());
            }
            z3 = z5;
            z4 = z6;
        } else {
            z3 = false;
            z4 = false;
        }
        if (!activityRecord3.attachedToProcess()) {
            this.mPausingActivity = null;
            this.mLastPausedActivity = null;
            this.mTaskSupervisor.mNoHistoryActivities.remove(activityRecord3);
        } else if (z4) {
            activityRecord3.mPauseSchedulePendingForPip = true;
            boolean enterPictureInPictureMode = this.mAtmService.enterPictureInPictureMode(activityRecord3, activityRecord3.pictureInPictureArgs, false);
            if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
                ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_STATES, -1101551167, 12, (String) null, new Object[]{String.valueOf(activityRecord3), Boolean.valueOf(enterPictureInPictureMode)});
            }
        } else {
            schedulePauseActivity(activityRecord3, z, z3, false, str);
            this.mAtmService.mAmInternal.onPackagePausedBG(activityRecord3.info.packageName, activityRecord3.launchedFromPackage, activityRecord3.occludesParent(), activityRecord3.mUserId);
        }
        if (!z2 && !this.mAtmService.isSleepingOrShuttingDownLocked()) {
            this.mTaskSupervisor.acquireLaunchWakelock();
        }
        if (this.mPausingActivity == null) {
            if (z4 && activityRecord3.isState(state) && activityRecord3.getLastParentBeforePip() == this) {
                activityRecord3.activityPaused(false);
            }
            if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_STATES, -648891906, 0, (String) null, (Object[]) null);
            }
            if (activityRecord == null) {
                this.mRootWindowContainer.resumeFocusedTasksTopActivities();
            }
            return false;
        }
        if (!z2) {
            activityRecord3.pauseKeyDispatchingLocked();
        } else if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_STATES, -1633115609, 0, (String) null, (Object[]) null);
        }
        if (z3) {
            completePause(false, activityRecord);
            return false;
        }
        activityRecord3.schedulePauseTimeout();
        if (!z2) {
            this.mTransitionController.setReady(this, false);
        }
        return true;
    }

    public void schedulePauseActivity(ActivityRecord activityRecord, boolean z, boolean z2, boolean z3, String str) {
        if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_STATES, 378825104, 0, (String) null, new Object[]{String.valueOf(activityRecord)});
        }
        try {
            activityRecord.mPauseSchedulePendingForPip = false;
            EventLogTags.writeWmPauseActivity(activityRecord.mUserId, System.identityHashCode(activityRecord), activityRecord.shortComponentName, "userLeaving=" + z, str);
            this.mAtmService.getLifecycleManager().scheduleTransaction(activityRecord.app.getThread(), activityRecord.token, (ActivityLifecycleItem) PauseActivityItem.obtain(activityRecord.finishing, z, activityRecord.configChangeFlags, z2, z3));
        } catch (Exception e) {
            Slog.w("ActivityTaskManager", "Exception thrown during pause", e);
            this.mPausingActivity = null;
            this.mLastPausedActivity = null;
            this.mTaskSupervisor.mNoHistoryActivities.remove(activityRecord);
        }
    }

    public void completePause(boolean z, ActivityRecord activityRecord) {
        ActivityRecord activityRecord2 = this.mPausingActivity;
        if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_STATES, 327461496, 0, (String) null, new Object[]{String.valueOf(activityRecord2)});
        }
        if (activityRecord2 != null) {
            activityRecord2.setWillCloseOrEnterPip(false);
            ActivityRecord.State state = ActivityRecord.State.STOPPING;
            boolean isState = activityRecord2.isState(state);
            activityRecord2.setState(ActivityRecord.State.PAUSED, "completePausedLocked");
            if (activityRecord2.finishing) {
                if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
                    ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_STATES, -312353598, 0, (String) null, new Object[]{String.valueOf(activityRecord2)});
                }
                activityRecord2 = activityRecord2.completeFinishing(false, "completePausedLocked");
            } else if (activityRecord2.attachedToProcess()) {
                if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
                    ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_STATES, -1187377055, 60, (String) null, new Object[]{String.valueOf(activityRecord2), Boolean.valueOf(isState), Boolean.valueOf(activityRecord2.isVisibleRequested())});
                }
                if (activityRecord2.deferRelaunchUntilPaused) {
                    if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
                        ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_STATES, 1011462000, 0, (String) null, new Object[]{String.valueOf(activityRecord2)});
                    }
                    activityRecord2.relaunchActivityLocked(activityRecord2.preserveWindowOnDeferredRelaunch);
                } else if (isState) {
                    activityRecord2.setState(state, "completePausedLocked");
                } else if (!activityRecord2.isVisibleRequested() || shouldSleepOrShutDownActivities()) {
                    activityRecord2.setDeferHidingClient(false);
                    activityRecord2.addToStopping(true, false, "completePauseLocked");
                }
            } else {
                if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
                    ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_STATES, -521613870, 0, (String) null, new Object[]{String.valueOf(activityRecord2)});
                }
                activityRecord2 = null;
            }
            if (activityRecord2 != null) {
                activityRecord2.stopFreezingScreenLocked(true);
            }
            this.mPausingActivity = null;
        }
        if (z) {
            Task topDisplayFocusedRootTask = this.mRootWindowContainer.getTopDisplayFocusedRootTask();
            if (topDisplayFocusedRootTask != null && !topDisplayFocusedRootTask.shouldSleepOrShutDownActivities()) {
                this.mRootWindowContainer.resumeFocusedTasksTopActivities(topDisplayFocusedRootTask, activityRecord2, null);
            } else {
                ActivityRecord activityRecord3 = topDisplayFocusedRootTask != null ? topDisplayFocusedRootTask.topRunningActivity() : null;
                if (activityRecord3 == null || (activityRecord2 != null && activityRecord3 != activityRecord2)) {
                    this.mRootWindowContainer.resumeFocusedTasksTopActivities();
                }
            }
        }
        if (activityRecord2 != null) {
            activityRecord2.resumeKeyDispatchingLocked();
        }
        this.mRootWindowContainer.ensureActivitiesVisible(activityRecord, 0, false);
        if (this.mTaskSupervisor.mAppVisibilitiesChangedSinceLastPause || (getDisplayArea() != null && getDisplayArea().hasPinnedTask())) {
            this.mAtmService.getTaskChangeNotificationController().notifyTaskStackChanged();
            this.mTaskSupervisor.mAppVisibilitiesChangedSinceLastPause = false;
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public int getOrientation(int i) {
        if (shouldReportOrientationUnspecified()) {
            return -1;
        }
        if (canSpecifyOrientation()) {
            return super.getOrientation(i);
        }
        return -2;
    }

    public boolean canSpecifyOrientation() {
        int windowingMode = getWindowingMode();
        int activityType = getActivityType();
        return windowingMode == 1 || activityType == 2 || activityType == 3 || activityType == 4;
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean providesOrientation() {
        return super.providesOrientation() || shouldReportOrientationUnspecified();
    }

    public final boolean shouldReportOrientationUnspecified() {
        return getAdjacentTaskFragment() != null && isVisibleRequested();
    }

    @Override // com.android.server.wm.WindowContainer
    public void forAllTaskFragments(Consumer consumer, boolean z) {
        super.forAllTaskFragments(consumer, z);
        consumer.accept(this);
    }

    @Override // com.android.server.wm.WindowContainer
    public void forAllLeafTaskFragments(Consumer consumer, boolean z) {
        int size = this.mChildren.size();
        boolean z2 = true;
        if (z) {
            for (int i = size - 1; i >= 0; i--) {
                TaskFragment asTaskFragment = ((WindowContainer) this.mChildren.get(i)).asTaskFragment();
                if (asTaskFragment != null) {
                    asTaskFragment.forAllLeafTaskFragments(consumer, z);
                    z2 = false;
                }
            }
        } else {
            for (int i2 = 0; i2 < size; i2++) {
                TaskFragment asTaskFragment2 = ((WindowContainer) this.mChildren.get(i2)).asTaskFragment();
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
    public boolean forAllLeafTaskFragments(Predicate predicate) {
        boolean z = true;
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            TaskFragment asTaskFragment = ((WindowContainer) this.mChildren.get(size)).asTaskFragment();
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

    public void addChild(ActivityRecord activityRecord) {
        addChild(activityRecord, Integer.MAX_VALUE);
    }

    @Override // com.android.server.wm.WindowContainer
    public void addChild(WindowContainer windowContainer, int i) {
        ActivityRecord activityRecord = topRunningActivity();
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
            if (activityRecord != null && BackNavigationController.isScreenshotEnabled()) {
                if (ProtoLogCache.WM_DEBUG_BACK_PREVIEW_enabled) {
                    ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_BACK_PREVIEW, -134091882, 0, "Screenshotting Activity %s", new Object[]{String.valueOf(activityRecord.mActivityComponent.flattenToString())});
                }
                Rect bounds = activityRecord.getBounds();
                this.mBackScreenshots.put(activityRecord.mActivityComponent.flattenToString(), ScreenCapture.captureLayers(activityRecord.mSurfaceControl, new Rect(0, 0, bounds.width(), bounds.height()), 1.0f));
            }
            asActivityRecord.inHistory = true;
            task.onDescendantActivityAdded(z2, activityType, asActivityRecord);
        }
        WindowProcessController organizerProcessIfDifferent = getOrganizerProcessIfDifferent(asActivityRecord);
        if (organizerProcessIfDifferent != null) {
            organizerProcessIfDifferent.addEmbeddedActivity(asActivityRecord);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public void onChildPositionChanged(WindowContainer windowContainer) {
        super.onChildPositionChanged(windowContainer);
        sendTaskFragmentInfoChanged();
    }

    public void executeAppTransition(ActivityOptions activityOptions) {
        this.mDisplayContent.executeAppTransition();
        ActivityOptions.abort(activityOptions);
    }

    @Override // com.android.server.wm.WindowContainer
    public RemoteAnimationTarget createRemoteAnimationTarget(RemoteAnimationController.RemoteAnimationRecord remoteAnimationRecord) {
        ActivityRecord topMostActivity;
        if (remoteAnimationRecord.getMode() == 0) {
            topMostActivity = getActivity(new Predicate() { // from class: com.android.server.wm.TaskFragment$$ExternalSyntheticLambda7
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$createRemoteAnimationTarget$7;
                    lambda$createRemoteAnimationTarget$7 = TaskFragment.lambda$createRemoteAnimationTarget$7((ActivityRecord) obj);
                    return lambda$createRemoteAnimationTarget$7;
                }
            });
        } else {
            topMostActivity = getTopMostActivity();
        }
        if (topMostActivity != null) {
            return topMostActivity.createRemoteAnimationTarget(remoteAnimationRecord);
        }
        return null;
    }

    public static /* synthetic */ boolean lambda$createRemoteAnimationTarget$7(ActivityRecord activityRecord) {
        return !activityRecord.finishing && activityRecord.hasChild();
    }

    public boolean shouldSleepActivities() {
        Task rootTask = getRootTask();
        return rootTask != null && rootTask.shouldSleepActivities();
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    public void resolveOverrideConfiguration(Configuration configuration) {
        this.mTmpBounds.set(getResolvedOverrideConfiguration().windowConfiguration.getBounds());
        super.resolveOverrideConfiguration(configuration);
        Configuration resolvedOverrideConfiguration = getResolvedOverrideConfiguration();
        Rect rect = this.mRelativeEmbeddedBounds;
        if (rect != null && !rect.isEmpty()) {
            resolvedOverrideConfiguration.windowConfiguration.setBounds(translateRelativeBoundsToAbsoluteBounds(this.mRelativeEmbeddedBounds, configuration.windowConfiguration.getBounds()));
        }
        int windowingMode = resolvedOverrideConfiguration.windowConfiguration.getWindowingMode();
        int windowingMode2 = configuration.windowConfiguration.getWindowingMode();
        if (getActivityType() == 2 && windowingMode == 0) {
            resolvedOverrideConfiguration.windowConfiguration.setWindowingMode(1);
            windowingMode = 1;
        }
        if (CoreRune.MT_NEW_DEX && getActivityType() == 3 && windowingMode == 0) {
            resolvedOverrideConfiguration.windowConfiguration.setWindowingMode(1);
            windowingMode = 1;
        }
        if ((!CoreRune.MT_DEX_SIZE_COMPAT_MODE || DexSizeCompatController.getInstance().getCompatPolicy(asTask()) == null) && !supportsMultiWindow() && (!CoreRune.MW_EMBED_ACTIVITY || asTask() != null || !isEmbedded())) {
            int stage = getResolvedOverrideConfiguration().windowConfiguration.getStage();
            if (this.mCreatedByOrganizer && asTask() != null && windowingMode == 6 && WindowConfiguration.isSplitScreenWindowingMode(stage) && this.mAtmService.mMultiWindowEnableController.deviceSupportsMultiWindow()) {
                Slog.d("ActivityTaskManager", "resolveOverrideConfiguration: force allow multi-window, tid #" + asTask().mTaskId);
            } else {
                if (windowingMode != 0) {
                    windowingMode2 = windowingMode;
                }
                if (WindowConfiguration.inMultiWindowMode(windowingMode2) && windowingMode2 != 2 && (getTask() == null || !getTask().isDexCompatEnabled())) {
                    resolvedOverrideConfiguration.windowConfiguration.setWindowingMode(1);
                }
            }
        }
        Task asTask = asTask();
        if (asTask != null) {
            asTask.resolveLeafTaskOnlyOverrideConfigs(configuration, this.mTmpBounds);
        }
        computeConfigResourceOverrides(resolvedOverrideConfiguration, configuration);
    }

    public boolean supportsMultiWindow() {
        return supportsMultiWindowInDisplayArea(getDisplayArea());
    }

    public boolean supportsMultiWindowInDefaultDisplayArea() {
        return supportsMultiWindowInDisplayArea(this.mRootWindowContainer.getDefaultTaskDisplayArea());
    }

    public boolean supportsMultiWindowInDisplayArea(TaskDisplayArea taskDisplayArea) {
        return supportsMultiWindowInDisplayArea(taskDisplayArea, false);
    }

    public boolean supportsMultiWindowInDisplayArea(TaskDisplayArea taskDisplayArea, boolean z) {
        Task task;
        ActivityTaskManagerService activityTaskManagerService = this.mAtmService;
        if ((!activityTaskManagerService.mSupportsMultiWindow && (!z || !activityTaskManagerService.mMultiWindowEnableController.deviceSupportsMultiWindow())) || taskDisplayArea == null || (task = getTask()) == null) {
            return false;
        }
        return this.mAtmService.mMwSupportPolicyController.supportsMultiWindowInDisplayArea(taskDisplayArea, task.mResizeMode, task.isResizeable(), task.mIgnoreDevSettingForNonResizable);
    }

    public void computeConfigResourceOverrides(Configuration configuration, Configuration configuration2) {
        computeConfigResourceOverrides(configuration, configuration2, null, null);
    }

    public void computeConfigResourceOverrides(Configuration configuration, Configuration configuration2, DisplayInfo displayInfo) {
        if (displayInfo != null) {
            configuration.screenLayout = 0;
            invalidateAppBoundsConfig(configuration);
        }
        computeConfigResourceOverrides(configuration, configuration2, displayInfo, null);
    }

    public void computeConfigResourceOverrides(Configuration configuration, Configuration configuration2, ActivityRecord.CompatDisplayInsets compatDisplayInsets) {
        if (compatDisplayInsets != null) {
            invalidateAppBoundsConfig(configuration);
        }
        computeConfigResourceOverrides(configuration, configuration2, null, compatDisplayInsets);
    }

    public static void invalidateAppBoundsConfig(Configuration configuration) {
        Rect appBounds = configuration.windowConfiguration.getAppBounds();
        if (appBounds != null) {
            appBounds.setEmpty();
        }
        configuration.screenWidthDp = 0;
        configuration.screenHeightDp = 0;
    }

    public void computeConfigResourceOverrides(Configuration configuration, Configuration configuration2, DisplayInfo displayInfo, ActivityRecord.CompatDisplayInsets compatDisplayInsets) {
        computeConfigResourceOverrides(configuration, configuration2, displayInfo, compatDisplayInsets, null);
    }

    public void computeConfigResourceOverrides(Configuration configuration, Configuration configuration2, DisplayInfo displayInfo, ActivityRecord.CompatDisplayInsets compatDisplayInsets, SizeCompatPolicy sizeCompatPolicy) {
        boolean contains;
        Rect appBounds;
        int scaleDownDensity;
        if (sizeCompatPolicy != null) {
            invalidateAppBoundsConfig(configuration);
        }
        int windowingMode = configuration.windowConfiguration.getWindowingMode();
        if (windowingMode == 0) {
            windowingMode = configuration2.windowConfiguration.getWindowingMode();
        }
        float f = configuration.densityDpi;
        if (f == DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
            f = configuration2.densityDpi;
        }
        float f2 = f * 0.00625f;
        if (!isDexMode() && getTask() != null && !getTask().mCreatedByOrganizer && MultiWindowUtils.needToUpdateDensity(windowingMode, getActivityType(), WindowConfiguration.isSplitScreenWindowingMode(configuration2.windowConfiguration)) && (scaleDownDensity = MultiWindowUtils.getScaleDownDensity(configuration2.smallestScreenWidthDp, configuration2.densityDpi)) > 0) {
            f2 = scaleDownDensity * 0.00625f;
            configuration.densityDpi = scaleDownDensity;
        }
        Rect bounds = configuration2.windowConfiguration.getBounds();
        Rect bounds2 = configuration.windowConfiguration.getBounds();
        if (bounds2.isEmpty()) {
            this.mTmpFullBounds.set(bounds);
            contains = true;
        } else {
            this.mTmpFullBounds.set(bounds2);
            contains = bounds.contains(bounds2);
        }
        boolean z = (compatDisplayInsets == null && sizeCompatPolicy == null) ? false : true;
        Rect appBounds2 = configuration.windowConfiguration.getAppBounds();
        if (appBounds2 == null || appBounds2.isEmpty()) {
            configuration.windowConfiguration.setAppBounds(this.mTmpFullBounds);
            appBounds2 = configuration.windowConfiguration.getAppBounds();
            if (!z && windowingMode != 5) {
                if (contains) {
                    appBounds = configuration2.windowConfiguration.getAppBounds();
                } else {
                    TaskDisplayArea displayArea = getDisplayArea();
                    appBounds = displayArea != null ? displayArea.getWindowConfiguration().getAppBounds() : null;
                }
                if (appBounds != null && !appBounds.isEmpty()) {
                    appBounds2.intersect(appBounds);
                }
            }
        }
        if (configuration.screenWidthDp == 0 || configuration.screenHeightDp == 0) {
            if (!z && WindowConfiguration.isFloating(windowingMode)) {
                this.mTmpNonDecorBounds.set(this.mTmpFullBounds);
                this.mTmpStableBounds.set(this.mTmpFullBounds);
            } else if (!z && (displayInfo != null || getDisplayContent() != null)) {
                DisplayInfo displayInfo2 = displayInfo != null ? displayInfo : getDisplayContent().getDisplayInfo();
                if (MultiWindowCoreState.MW_SPLIT_IMMERSIVE_MODE_ENABLED && inSplitScreenWindowingMode()) {
                    this.mTmpNonDecorBounds.set(this.mTmpFullBounds);
                    this.mTmpStableBounds.set(this.mTmpFullBounds);
                } else if (MultiWindowCoreState.MW_NAVISTAR_SPLIT_IMMERSIVE_MODE_ENABLED && this.mDisplayContent != null && inSplitScreenWindowingMode()) {
                    this.mTmpNonDecorBounds.set(this.mTmpFullBounds);
                    this.mTmpStableBounds.set(this.mTmpFullBounds);
                    this.mTmpBounds.set(0, 0, displayInfo2.logicalWidth, displayInfo2.logicalHeight);
                    DisplayPolicy.DecorInsets.Info decorInsetsInfo = this.mDisplayContent.getDisplayPolicy().getDecorInsetsInfo(displayInfo2.rotation, displayInfo2.logicalWidth, displayInfo2.logicalHeight);
                    intersectWithInsetsIfFits(this.mTmpNonDecorBounds, this.mTmpBounds, decorInsetsInfo.mCutoutInsets);
                    intersectWithInsetsIfFits(this.mTmpStableBounds, this.mTmpBounds, decorInsetsInfo.mExceptNavConfigInsets);
                } else {
                    calculateInsetFrames(this.mTmpNonDecorBounds, this.mTmpStableBounds, this.mTmpFullBounds, displayInfo2);
                }
            } else {
                int rotation = configuration.windowConfiguration.getRotation();
                if (rotation == -1) {
                    rotation = configuration2.windowConfiguration.getRotation();
                }
                if (CoreRune.MT_SUPPORT_SIZE_COMPAT && sizeCompatPolicy != null && rotation != -1 && z) {
                    this.mTmpNonDecorBounds.set(this.mTmpFullBounds);
                    this.mTmpStableBounds.set(this.mTmpFullBounds);
                    sizeCompatPolicy.getBoundsByRotation(this.mTmpBounds, rotation);
                    intersectWithInsetsIfFits(this.mTmpNonDecorBounds, this.mTmpBounds, sizeCompatPolicy.mNonDecorInsets[rotation]);
                    intersectWithInsetsIfFits(this.mTmpStableBounds, this.mTmpBounds, sizeCompatPolicy.mStableInsets[rotation]);
                    appBounds2.set(this.mTmpNonDecorBounds);
                } else if (rotation != -1 && z) {
                    this.mTmpNonDecorBounds.set(this.mTmpFullBounds);
                    this.mTmpStableBounds.set(this.mTmpFullBounds);
                    compatDisplayInsets.getBoundsByRotation(this.mTmpBounds, rotation);
                    intersectWithInsetsIfFits(this.mTmpNonDecorBounds, this.mTmpBounds, compatDisplayInsets.mNonDecorInsets[rotation]);
                    intersectWithInsetsIfFits(this.mTmpStableBounds, this.mTmpBounds, compatDisplayInsets.mStableInsets[rotation]);
                    appBounds2.set(this.mTmpNonDecorBounds);
                } else {
                    this.mTmpNonDecorBounds.set(appBounds2);
                    this.mTmpStableBounds.set(appBounds2);
                }
            }
            if (configuration.screenWidthDp == 0) {
                int width = (int) ((this.mTmpStableBounds.width() / f2) + 0.5f);
                if (contains && !z) {
                    width = Math.min(width, configuration2.screenWidthDp);
                }
                configuration.screenWidthDp = width;
            }
            if (configuration.screenHeightDp == 0) {
                int height = (int) ((this.mTmpStableBounds.height() / f2) + 0.5f);
                if (contains && !z) {
                    height = Math.min(height, configuration2.screenHeightDp);
                }
                configuration.screenHeightDp = height;
            }
            if (configuration.smallestScreenWidthDp == 0) {
                boolean z2 = windowingMode == 2 && !this.mTmpFullBounds.isEmpty() && this.mTmpFullBounds.equals(bounds);
                if ((WindowConfiguration.isFloating(windowingMode) || configuration.windowConfiguration.isPopOver()) && !z2) {
                    configuration.smallestScreenWidthDp = (int) ((Math.min(this.mTmpFullBounds.width(), this.mTmpFullBounds.height()) / f2) + 0.5f);
                } else if (windowingMode == 6 && this.mIsEmbedded && contains && !bounds2.equals(bounds)) {
                    configuration.smallestScreenWidthDp = Math.min(configuration.screenWidthDp, configuration.screenHeightDp);
                }
            }
        }
        if (configuration.orientation == 0) {
            configuration.orientation = configuration.screenWidthDp <= configuration.screenHeightDp ? 1 : 2;
        }
        if (configuration.screenLayout == 0) {
            int width2 = (int) ((this.mTmpNonDecorBounds.width() / f2) + 0.5f);
            int height2 = (int) ((this.mTmpNonDecorBounds.height() / f2) + 0.5f);
            int i = configuration.screenWidthDp;
            if (i != 0) {
                width2 = i;
            }
            int i2 = configuration.screenHeightDp;
            if (i2 != 0) {
                height2 = i2;
            }
            configuration.screenLayout = WindowContainer.computeScreenLayout(configuration2.screenLayout, width2, height2);
        }
    }

    public void calculateInsetFrames(Rect rect, Rect rect2, Rect rect3, DisplayInfo displayInfo) {
        rect.set(rect3);
        rect2.set(rect3);
        if (this.mDisplayContent == null) {
            return;
        }
        this.mTmpBounds.set(0, 0, displayInfo.logicalWidth, displayInfo.logicalHeight);
        DisplayPolicy.DecorInsets.Info decorInsetsInfo = this.mDisplayContent.getDisplayPolicy().getDecorInsetsInfo(displayInfo.rotation, displayInfo.logicalWidth, displayInfo.logicalHeight);
        intersectWithInsetsIfFits(rect, this.mTmpBounds, decorInsetsInfo.mNonDecorInsets);
        intersectWithInsetsIfFits(rect2, this.mTmpBounds, decorInsetsInfo.mConfigInsets);
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

    @Override // com.android.server.wm.ConfigurationContainer
    public int getActivityType() {
        int activityType = super.getActivityType();
        if (activityType != 0 || !hasChild()) {
            return activityType;
        }
        ActivityRecord topMostActivity = getTopMostActivity();
        return topMostActivity != null ? topMostActivity.getActivityType() : getTopChild().getActivityType();
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    public void onConfigurationChanged(Configuration configuration) {
        if (CoreRune.MW_SHELL_TRANSITION && this.mIsEmbedded && this.mTransitionController.isShellTransitionsEnabled() && isVisible() && isVisibleRequested() && getRequestedOverrideWindowingMode() == 0 && configuration.windowConfiguration.getWindowingMode() != getWindowingMode()) {
            this.mTransitionController.collect(this);
        }
        super.onConfigurationChanged(configuration);
        updateOrganizedTaskFragmentSurface();
        sendTaskFragmentInfoChanged();
    }

    public void deferOrganizedTaskFragmentSurfaceUpdate() {
        this.mDelayOrganizedTaskFragmentSurfaceUpdate = true;
    }

    public void continueOrganizedTaskFragmentSurfaceUpdate() {
        this.mDelayOrganizedTaskFragmentSurfaceUpdate = false;
        updateOrganizedTaskFragmentSurface();
    }

    public void updateOrganizedTaskFragmentSurface() {
        if (this.mDelayOrganizedTaskFragmentSurfaceUpdate || this.mTaskFragmentOrganizer == null) {
            return;
        }
        boolean z = false;
        if (CoreRune.MW_EMBED_ACTIVITY && this.mTransitionController.isShellTransitionsEnabled() && this.mTransitionController.isCollecting(this) && !isVisibleRequested() && !isVisible()) {
            z = true;
        }
        if (this.mTransitionController.isShellTransitionsEnabled() && (!this.mTransitionController.isCollecting(this) || z)) {
            updateOrganizedTaskFragmentSurfaceUnchecked();
        } else {
            if (this.mTransitionController.isShellTransitionsEnabled() || isAnimating()) {
                return;
            }
            updateOrganizedTaskFragmentSurfaceUnchecked();
        }
    }

    public final void updateOrganizedTaskFragmentSurfaceUnchecked() {
        SurfaceControl.Transaction syncTransaction = getSyncTransaction();
        updateSurfacePosition(syncTransaction);
        updateOrganizedTaskFragmentSurfaceSize(syncTransaction, false);
    }

    public final void updateOrganizedTaskFragmentSurfaceSize(SurfaceControl.Transaction transaction, boolean z) {
        Rect bounds;
        if (this.mTaskFragmentOrganizer == null || this.mSurfaceControl == null || this.mSurfaceAnimator.hasLeash() || this.mSurfaceFreezer.hasLeash()) {
            return;
        }
        if (isClosingWhenResizing()) {
            bounds = (Rect) this.mDisplayContent.mClosingChangingContainers.get(this);
        } else {
            bounds = getBounds();
        }
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

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
    public void onAnimationLeashCreated(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
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

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
    public void onAnimationLeashLost(SurfaceControl.Transaction transaction) {
        super.onAnimationLeashLost(transaction);
        if (this.mTaskFragmentOrganizer != null) {
            updateOrganizedTaskFragmentSurfaceSize(transaction, true);
        }
    }

    public Rect getRelativeEmbeddedBounds() {
        Rect rect = this.mRelativeEmbeddedBounds;
        if (rect != null) {
            return rect;
        }
        throw new IllegalStateException("The TaskFragment is not embedded");
    }

    public Rect translateRelativeBoundsToAbsoluteBounds(Rect rect, Rect rect2) {
        if (rect.isEmpty()) {
            this.mTmpAbsBounds.setEmpty();
            return this.mTmpAbsBounds;
        }
        this.mTmpAbsBounds.set(rect);
        this.mTmpAbsBounds.offset(rect2.left, rect2.top);
        if (!isAllowedToBeEmbeddedInTrustedMode() && !rect2.contains(this.mTmpAbsBounds) && !this.mTmpAbsBounds.intersect(rect2)) {
            this.mTmpAbsBounds.setEmpty();
        }
        return this.mTmpAbsBounds;
    }

    public void recomputeConfiguration() {
        onRequestedOverrideConfigurationChanged(getRequestedOverrideConfiguration());
    }

    public void setRelativeEmbeddedBounds(Rect rect) {
        Rect rect2 = this.mRelativeEmbeddedBounds;
        if (rect2 == null) {
            throw new IllegalStateException("The TaskFragment is not embedded");
        }
        if (rect2.equals(rect)) {
            return;
        }
        this.mRelativeEmbeddedBounds.set(rect);
    }

    public boolean shouldStartChangeTransition(Rect rect, Rect rect2) {
        if (this.mTaskFragmentOrganizer == null || !canStartChangeTransition()) {
            return false;
        }
        if (this.mTransitionController.isShellTransitionsEnabled()) {
            Rect bounds = getConfiguration().windowConfiguration.getBounds();
            return (bounds.width() == rect.width() && bounds.height() == rect.height()) ? false : true;
        }
        return !rect2.equals(this.mRelativeEmbeddedBounds);
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean canStartChangeTransition() {
        Task task = getTask();
        return (task == null || task.isDragResizing() || !super.canStartChangeTransition()) ? false : true;
    }

    public boolean setClosingChangingStartBoundsIfNeeded() {
        DisplayContent displayContent;
        if (!isOrganizedTaskFragment() || (displayContent = this.mDisplayContent) == null || !displayContent.mChangingContainers.remove(this)) {
            return false;
        }
        this.mDisplayContent.mClosingChangingContainers.put(this, new Rect(this.mSurfaceFreezer.mFreezeBounds));
        return true;
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean isSyncFinished(BLASTSyncEngine.SyncGroup syncGroup) {
        return super.isSyncFinished(syncGroup) && isReadyToTransit();
    }

    @Override // com.android.server.wm.WindowContainer
    public void setSurfaceControl(SurfaceControl surfaceControl) {
        super.setSurfaceControl(surfaceControl);
        if (this.mTaskFragmentOrganizer != null) {
            updateOrganizedTaskFragmentSurfaceUnchecked();
            sendTaskFragmentAppeared();
        }
    }

    public void sendTaskFragmentInfoChanged() {
        ITaskFragmentOrganizer iTaskFragmentOrganizer = this.mTaskFragmentOrganizer;
        if (iTaskFragmentOrganizer != null) {
            this.mTaskFragmentOrganizerController.onTaskFragmentInfoChanged(iTaskFragmentOrganizer, this);
        }
    }

    public void sendTaskFragmentParentInfoChanged() {
        Task asTask = getParent().asTask();
        ITaskFragmentOrganizer iTaskFragmentOrganizer = this.mTaskFragmentOrganizer;
        if (iTaskFragmentOrganizer == null || asTask == null) {
            return;
        }
        this.mTaskFragmentOrganizerController.onTaskFragmentParentInfoChanged(iTaskFragmentOrganizer, asTask);
    }

    public final void sendTaskFragmentAppeared() {
        ITaskFragmentOrganizer iTaskFragmentOrganizer = this.mTaskFragmentOrganizer;
        if (iTaskFragmentOrganizer != null) {
            this.mTaskFragmentOrganizerController.onTaskFragmentAppeared(iTaskFragmentOrganizer, this);
        }
    }

    public final void sendTaskFragmentVanished() {
        ITaskFragmentOrganizer iTaskFragmentOrganizer = this.mTaskFragmentOrganizer;
        if (iTaskFragmentOrganizer != null) {
            this.mTaskFragmentOrganizerController.onTaskFragmentVanished(iTaskFragmentOrganizer, this);
        }
    }

    public TaskFragmentInfo getTaskFragmentInfo() {
        ApplicationInfo applicationInfo;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < getChildCount(); i++) {
            ActivityRecord asActivityRecord = getChildAt(i).asActivityRecord();
            if (this.mTaskFragmentOrganizerUid != -1 && asActivityRecord != null && ((asActivityRecord.info.processName.equals(this.mTaskFragmentOrganizerProcessName) || (CoreRune.MW_EMBED_ACTIVITY && (applicationInfo = asActivityRecord.info.applicationInfo) != null && applicationInfo.processName.equals(this.mTaskFragmentOrganizerProcessName))) && asActivityRecord.getUid() == this.mTaskFragmentOrganizerUid && !asActivityRecord.finishing)) {
                arrayList.add(asActivityRecord.token);
                if (asActivityRecord.mRequestedLaunchingTaskFragmentToken == this.mFragmentToken) {
                    arrayList2.add(asActivityRecord.token);
                }
            }
        }
        Point point = new Point();
        getRelativePosition(point);
        return new TaskFragmentInfo(this.mFragmentToken, this.mRemoteToken.toWindowContainerToken(), getConfiguration(), getNonFinishingActivityCount(), shouldBeVisible(null), arrayList, arrayList2, point, this.mClearedTaskForReuse, this.mClearedTaskFragmentForPip, this.mClearedForReorderActivityToFront, calculateMinDimension());
    }

    public Point calculateMinDimension() {
        final int[] iArr = new int[1];
        final int[] iArr2 = new int[1];
        forAllActivities(new Consumer() { // from class: com.android.server.wm.TaskFragment$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                TaskFragment.lambda$calculateMinDimension$8(iArr, iArr2, (ActivityRecord) obj);
            }
        });
        return new Point(iArr[0], iArr2[0]);
    }

    public static /* synthetic */ void lambda$calculateMinDimension$8(int[] iArr, int[] iArr2, ActivityRecord activityRecord) {
        Point minDimensions;
        if (activityRecord.finishing || (minDimensions = activityRecord.getMinDimensions()) == null) {
            return;
        }
        iArr[0] = Math.max(iArr[0], minDimensions.x);
        iArr2[0] = Math.max(iArr2[0], minDimensions.y);
    }

    public IBinder getFragmentToken() {
        return this.mFragmentToken;
    }

    public ITaskFragmentOrganizer getTaskFragmentOrganizer() {
        return this.mTaskFragmentOrganizer;
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean isOrganized() {
        return this.mTaskFragmentOrganizer != null;
    }

    public final boolean isOrganizedTaskFragment() {
        return this.mTaskFragmentOrganizer != null;
    }

    public boolean isEmbeddedWithBoundsOverride() {
        Task task;
        if (!this.mIsEmbedded || (task = getTask()) == null) {
            return false;
        }
        Rect bounds = task.getBounds();
        Rect bounds2 = getBounds();
        return !bounds.equals(bounds2) && bounds.contains(bounds2);
    }

    public boolean isTaskVisibleRequested() {
        Task task = getTask();
        return task != null && task.isVisibleRequested();
    }

    public boolean isReadyToTransit() {
        if (isOrganizedTaskFragment() && getTopNonFinishingActivity() == null && !this.mIsRemovalRequested && !isEmbeddedTaskFragmentInPip()) {
            return this.mClearedTaskFragmentForPip && !isTaskVisibleRequested();
        }
        return true;
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean canCustomizeAppTransition() {
        return isEmbedded() && matchParentBounds();
    }

    public void clearLastPausedActivity() {
        forAllTaskFragments(new Consumer() { // from class: com.android.server.wm.TaskFragment$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((TaskFragment) obj).mLastPausedActivity = null;
            }
        });
    }

    public void setMinDimensions(int i, int i2) {
        if (asTask() != null) {
            throw new UnsupportedOperationException("This method must not be used to Task. The  minimum dimension of Task should be passed from Task constructor.");
        }
        this.mMinWidth = i;
        this.mMinHeight = i2;
    }

    public boolean isEmbeddedTaskFragmentInPip() {
        return isOrganizedTaskFragment() && getTask() != null && getTask().inPinnedWindowingMode();
    }

    public boolean shouldRemoveSelfOnLastChildRemoval() {
        return !this.mCreatedByOrganizer || this.mIsRemovalRequested;
    }

    public boolean isRemovalRequested() {
        return this.mIsRemovalRequested;
    }

    @Override // com.android.server.wm.WindowContainer
    public void removeChild(WindowContainer windowContainer) {
        removeChild(windowContainer, true);
    }

    public void removeChild(WindowContainer windowContainer, boolean z) {
        super.removeChild(windowContainer);
        ActivityRecord asActivityRecord = windowContainer.asActivityRecord();
        if (BackNavigationController.isScreenshotEnabled() && asActivityRecord != null) {
            this.mBackScreenshots.remove(asActivityRecord.mActivityComponent.flattenToString());
        }
        WindowProcessController organizerProcessIfDifferent = getOrganizerProcessIfDifferent(asActivityRecord);
        if (organizerProcessIfDifferent != null) {
            organizerProcessIfDifferent.removeEmbeddedActivity(asActivityRecord);
        }
        if (z && shouldRemoveSelfOnLastChildRemoval() && !hasChild()) {
            removeImmediately("removeLastChild " + windowContainer);
        }
    }

    public void remove(boolean z, String str) {
        if (!hasChild()) {
            removeImmediately(str);
            return;
        }
        this.mIsRemovalRequested = true;
        ArrayList arrayList = new ArrayList();
        forAllActivities(new Task$$ExternalSyntheticLambda58(arrayList));
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            ActivityRecord activityRecord = (ActivityRecord) arrayList.get(size);
            if (z && activityRecord.isVisible()) {
                activityRecord.finishIfPossible(str, false);
            } else if (CoreRune.MW_EMBED_ACTIVITY && z && !activityRecord.isVisible() && activityRecord.isVisibleRequested() && isPlaceholderTaskFragment()) {
                activityRecord.finishIfPossible(str, false);
            } else {
                activityRecord.destroyIfPossible(str);
            }
        }
    }

    public void setDelayLastActivityRemoval(boolean z) {
        if (!this.mIsEmbedded) {
            Slog.w("ActivityTaskManager", "Set delaying last activity removal on a non-embedded TF.");
        }
        this.mDelayLastActivityRemoval = z;
    }

    public boolean isDelayLastActivityRemoval() {
        return this.mDelayLastActivityRemoval;
    }

    public boolean shouldDeferRemoval() {
        if (hasChild()) {
            return isExitAnimationRunningSelfOrChild();
        }
        return false;
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean handleCompleteDeferredRemoval() {
        if (shouldDeferRemoval()) {
            return true;
        }
        return super.handleCompleteDeferredRemoval();
    }

    public void removeImmediately(String str) {
        Slog.d("ActivityTaskManager", "Remove task fragment: " + str);
        removeImmediately();
    }

    @Override // com.android.server.wm.WindowContainer
    public void removeImmediately() {
        boolean z = false;
        this.mIsRemovalRequested = false;
        resetAdjacentTaskFragment();
        cleanUpEmbeddedTaskFragment();
        if (this.mClearedTaskFragmentForPip && isTaskVisibleRequested()) {
            z = true;
        }
        super.removeImmediately();
        sendTaskFragmentVanished();
        if (!z || this.mDisplayContent == null) {
            return;
        }
        this.mAtmService.addWindowLayoutReasons(2);
        this.mDisplayContent.executeAppTransition();
    }

    public final void cleanUpEmbeddedTaskFragment() {
        if (this.mIsEmbedded) {
            this.mAtmService.mWindowOrganizerController.cleanUpEmbeddedTaskFragment(this);
            Task task = getTask();
            if (task == null) {
                return;
            }
            task.forAllLeafTaskFragments(new Consumer() { // from class: com.android.server.wm.TaskFragment$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    TaskFragment.this.lambda$cleanUpEmbeddedTaskFragment$10((TaskFragment) obj);
                }
            }, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cleanUpEmbeddedTaskFragment$10(TaskFragment taskFragment) {
        if (taskFragment.getCompanionTaskFragment() == this) {
            taskFragment.setCompanionTaskFragment(null);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public Dimmer getDimmer() {
        if (asTask() == null) {
            return this.mDimmer;
        }
        return super.getDimmer();
    }

    @Override // com.android.server.wm.WindowContainer
    public void prepareSurfaces() {
        if (asTask() != null) {
            super.prepareSurfaces();
            return;
        }
        this.mDimmer.resetDimStates();
        super.prepareSurfaces();
        Rect dimBounds = this.mDimmer.getDimBounds();
        if (dimBounds != null) {
            if (CoreRune.MW_EMBED_ACTIVITY && !matchParentBounds()) {
                dimBounds.inset(this.mDisplayContent.getInsetsStateController().getRawInsetsState().calculateInsets(this.mTmpBounds, WindowInsets.Type.navigationBars(), false));
            }
            dimBounds.offsetTo(0, 0);
            if (this.mDimmer.updateDims(getSyncTransaction())) {
                scheduleAnimation();
            }
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean fillsParent() {
        return getWindowingMode() == 1 || matchParentBounds();
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean onChildVisibleRequestedChanged(WindowContainer windowContainer) {
        if (!super.onChildVisibleRequestedChanged(windowContainer)) {
            return false;
        }
        sendTaskFragmentInfoChanged();
        return true;
    }

    @Override // com.android.server.wm.WindowContainer
    public TaskFragment getTaskFragment(Predicate predicate) {
        TaskFragment taskFragment = super.getTaskFragment(predicate);
        if (taskFragment != null) {
            return taskFragment;
        }
        if (predicate.test(this)) {
            return this;
        }
        return null;
    }

    public boolean moveChildToFront(WindowContainer windowContainer) {
        int distanceFromTop = getDistanceFromTop(windowContainer);
        positionChildAt(Integer.MAX_VALUE, windowContainer, false);
        return getDistanceFromTop(windowContainer) != distanceFromTop;
    }

    public int calculateEmbedActivityMode(Task task, Rect rect) {
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

    @Override // com.android.server.wm.WindowContainer
    public boolean isSplitEmbedded() {
        if (asTask() != null) {
            ActivityRecord topVisibleActivity = asTask().getTopVisibleActivity();
            return topVisibleActivity != null && topVisibleActivity.isSplitEmbedded();
        }
        if (asTaskFragment() != null) {
            return isEmbedded() && !matchParentBounds();
        }
        return false;
    }

    public void setPlaceholderTaskFragment(boolean z) {
        this.mIsPlaceholderTaskFragment = z;
    }

    public boolean isPlaceholderTaskFragment() {
        return this.mIsPlaceholderTaskFragment;
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
        return "TaskFragment{" + Integer.toHexString(System.identityHashCode(this)) + " mode=" + WindowConfiguration.windowingModeToString(getWindowingMode()) + "}";
    }

    public boolean dump(final String str, FileDescriptor fileDescriptor, final PrintWriter printWriter, final boolean z, boolean z2, final String str2, final boolean z3, final Runnable runnable) {
        boolean z4;
        Runnable runnable2 = new Runnable() { // from class: com.android.server.wm.TaskFragment$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                TaskFragment.this.lambda$dump$11(z3, printWriter, runnable, str, z, str2);
            }
        };
        if (str2 == null) {
            runnable2.run();
            runnable2 = null;
            z4 = true;
        } else {
            z4 = false;
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            WindowContainer windowContainer = (WindowContainer) this.mChildren.get(size);
            if (windowContainer.asTaskFragment() != null) {
                z4 = windowContainer.asTaskFragment().dump(str + "  ", fileDescriptor, printWriter, z, z2, str2, z3, runnable2) | z4;
            } else if (windowContainer.asActivityRecord() != null) {
                ActivityRecord.dumpActivity(fileDescriptor, printWriter, size, windowContainer.asActivityRecord(), str + "  ", "Hist ", true, !z, z2, str2, false, runnable2, getTask());
            }
        }
        return z4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dump$11(boolean z, PrintWriter printWriter, Runnable runnable, String str, boolean z2, String str2) {
        if (z) {
            printWriter.println();
        }
        if (runnable != null) {
            runnable.run();
        }
        dumpInner(str, printWriter, z2, str2);
    }

    public void dumpInner(String str, PrintWriter printWriter, boolean z, String str2) {
        int size;
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
            ActivityTaskSupervisor.printThisActivity(printWriter, this.mLastPausedActivity, str2, false, str + "  mLastPausedActivity: ", null);
        }
        Intent baseIntent = getTask() != null ? getTask().getBaseIntent() : null;
        if (baseIntent == null || baseIntent.getExtras() == null || (size = baseIntent.getExtras().getSize()) <= 20000) {
            return;
        }
        printWriter.println(str + "  baseIntent extra sz : " + size);
    }

    @Override // com.android.server.wm.WindowContainer
    public void dump(PrintWriter printWriter, String str, boolean z) {
        super.dump(printWriter, str, z);
        printWriter.println(str + "bounds=" + getBounds().toShortString());
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("  ");
        String sb2 = sb.toString();
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            WindowContainer windowContainer = (WindowContainer) this.mChildren.get(size);
            TaskFragment asTaskFragment = windowContainer.asTaskFragment();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str);
            sb3.append("* ");
            sb3.append((Object) (asTaskFragment != null ? asTaskFragment.toFullString() : windowContainer));
            printWriter.println(sb3.toString());
            if (asTaskFragment != null) {
                windowContainer.dump(printWriter, sb2, z);
            }
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public void writeIdentifierToProto(ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, System.identityHashCode(this));
        ActivityRecord activityRecord = topRunningActivity();
        protoOutputStream.write(1120986464258L, activityRecord != null ? activityRecord.mUserId : -10000);
        protoOutputStream.write(1138166333443L, activityRecord != null ? activityRecord.intent.getComponent().flattenToShortString() : "TaskFragment");
        protoOutputStream.end(start);
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

    public static /* synthetic */ boolean lambda$allowEdgeExtension$12(ActivityRecord activityRecord) {
        return activityRecord.isInTransition() && activityRecord.allowEdgeExtension();
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean allowEdgeExtension() {
        return getActivity(new Predicate() { // from class: com.android.server.wm.TaskFragment$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$allowEdgeExtension$12;
                lambda$allowEdgeExtension$12 = TaskFragment.lambda$allowEdgeExtension$12((ActivityRecord) obj);
                return lambda$allowEdgeExtension$12;
            }
        }) != null;
    }
}
