package com.android.server.wm;

import android.os.Debug;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.Trace;
import android.p005os.IInstalld;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import android.view.Display;
import com.android.internal.policy.IKeyguardDismissCallback;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.inputmethod.InputMethodManagerInternal;
import com.android.server.wm.ActivityRecord;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.KeyguardController;
import com.android.server.policy.WindowManagerPolicy;
import com.android.server.wm.ActivityTaskManagerService.SleepTokenAcquirerImpl;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class KeyguardController {
    public final SparseArray mDisplayStates = new SparseArray();
    public final Runnable mResetWaitTransition = new Runnable() { // from class: com.android.server.wm.KeyguardController$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            KeyguardController.this.lambda$new$0();
        }
    };
    public RootWindowContainer mRootWindowContainer;
    public final ActivityTaskManagerService mService;
    public final ActivityTaskManagerInternal.SleepTokenAcquirer mSleepTokenAcquirer;
    public final ActivityTaskSupervisor mTaskSupervisor;
    public boolean mWaitingForWakeTransition;
    public WindowManagerService mWindowManager;

    public final int convertTransitFlags(int i) {
        int i2 = (i & 1) != 0 ? FrameworkStatsLog.HDMI_CEC_MESSAGE_REPORTED__USER_CONTROL_PRESSED_COMMAND__UP : 256;
        if ((i & 2) != 0) {
            i2 |= 2;
        }
        if ((i & 4) != 0) {
            i2 |= 4;
        }
        if ((i & 8) != 0) {
            i2 |= 8;
        }
        return (i & 16) != 0 ? i2 | 512 : i2;
    }

    public KeyguardController(ActivityTaskManagerService activityTaskManagerService, ActivityTaskSupervisor activityTaskSupervisor) {
        this.mService = activityTaskManagerService;
        this.mTaskSupervisor = activityTaskSupervisor;
        Objects.requireNonNull(activityTaskManagerService);
        this.mSleepTokenAcquirer = activityTaskManagerService.new SleepTokenAcquirerImpl("keyguard");
    }

    public void setWindowManager(WindowManagerService windowManagerService) {
        this.mWindowManager = windowManagerService;
        this.mRootWindowContainer = this.mService.mRootWindowContainer;
    }

    public boolean isAodShowing(int i) {
        return getDisplayState(i).mAodShowing;
    }

    public boolean isKeyguardOrAodShowing(int i) {
        KeyguardDisplayState displayState = getDisplayState(i);
        return ((!displayState.mKeyguardShowing && !displayState.mAodShowing) || displayState.mKeyguardGoingAway || isDisplayOccluded(i)) ? false : true;
    }

    public boolean isKeyguardUnoccludedOrAodShowing(int i) {
        KeyguardDisplayState displayState = getDisplayState(i);
        if (i == 0 && displayState.mAodShowing) {
            return !displayState.mKeyguardGoingAway;
        }
        return isKeyguardOrAodShowing(i);
    }

    public boolean isKeyguardShowing(int i) {
        KeyguardDisplayState displayState = getDisplayState(i);
        return (!displayState.mKeyguardShowing || displayState.mKeyguardGoingAway || isDisplayOccluded(i)) ? false : true;
    }

    public boolean isKeyguardLocked(int i) {
        KeyguardDisplayState displayState = getDisplayState(i);
        return displayState.mKeyguardShowing && !displayState.mKeyguardGoingAway;
    }

    public boolean topActivityOccludesKeyguard(ActivityRecord activityRecord) {
        return getDisplayState(activityRecord.getDisplayId()).mTopOccludesActivity == activityRecord;
    }

    public boolean isKeyguardGoingAway(int i) {
        KeyguardDisplayState displayState = getDisplayState(i);
        return displayState.mKeyguardGoingAway && displayState.mKeyguardShowing;
    }

    public boolean isKeyguardWallpaperShowing(int i) {
        KeyguardDisplayState displayState = getDisplayState(i);
        return displayState.mAodShowing || (displayState.mKeyguardShowing && !displayState.mOccluded);
    }

    public boolean isDismissalRequestedInOccluded(int i) {
        return getDisplayState(i).mDismissalRequestedInOccluded;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r13v0 */
    /* JADX WARN: Type inference failed for: r13v1 */
    /* JADX WARN: Type inference failed for: r13v2 */
    /* JADX WARN: Type inference failed for: r14v0 */
    /* JADX WARN: Type inference failed for: r14v1 */
    /* JADX WARN: Type inference failed for: r14v2 */
    /* JADX WARN: Type inference failed for: r15v0 */
    /* JADX WARN: Type inference failed for: r15v1 */
    /* JADX WARN: Type inference failed for: r15v2 */
    /* JADX WARN: Type inference failed for: r18v1 */
    /* JADX WARN: Type inference failed for: r19v1 */
    /* JADX WARN: Type inference failed for: r1v28 */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v9 */
    public void setKeyguardShown(int i, boolean z, boolean z2) {
        if (this.mRootWindowContainer.getDisplayContent(i).isKeyguardAlwaysUnlocked()) {
            Slog.i("ActivityTaskManager", "setKeyguardShown ignoring always unlocked display " + i);
            return;
        }
        KeyguardDisplayState displayState = getDisplayState(i);
        if (displayState.mWakeAndUnlock && z == z2) {
            displayState.mWakeAndUnlock = false;
        }
        ?? r13 = z2 != displayState.mAodShowing;
        ?? r1 = displayState.mAodShowing && !z2;
        ?? r14 = displayState.mKeyguardGoingAway && z;
        ?? r15 = !(z == displayState.mKeyguardShowing || displayState.mWakeAndUnlock) || (r14 == true && !r1 == true);
        if (r1 != false) {
            updateDeferTransitionForAod(false);
        }
        if (r15 == false && r13 == false) {
            setWakeTransitionReady();
            return;
        }
        EventLogTags.writeWmSetKeyguardShown(i, z ? 1 : 0, z2 ? 1 : 0, displayState.mKeyguardGoingAway ? 1 : 0, displayState.mOccluded ? 1 : 0, "setKeyguardShown");
        if ((((z2 ? 1 : 0) ^ (z ? 1 : 0)) != 0 || (z2 && r13 != false && r15 != false)) && !displayState.mKeyguardGoingAway && Display.isOnState(this.mRootWindowContainer.getDefaultDisplay().getDisplayInfo().state)) {
            this.mWindowManager.mTaskSnapshotController.snapshotForSleeping(0);
        }
        displayState.mKeyguardShowing = z;
        displayState.mAodShowing = z2;
        if (displayState.mKeyguardShowing && this.mTaskSupervisor.mService.mDexController.getDexModeLocked() == 2) {
            this.mWindowManager.getDefaultDisplayContentLocked().getDisplayPolicy().setControlTargetToNotificationShade();
        }
        if (i == 0 && r15 != false && z && displayState.mTopOccludesActivity != null && displayState.mTopOccludesActivity.inFreeformWindowingMode()) {
            DisplayContent defaultDisplay = this.mRootWindowContainer.getDefaultDisplay();
            Task rootTask = defaultDisplay.getRootTask(1, 0);
            WindowState topVisibleAppMainWindow = rootTask != null ? rootTask.getTopVisibleAppMainWindow() : null;
            if (topVisibleAppMainWindow != null && !topVisibleAppMainWindow.hasWallpaper()) {
                defaultDisplay.mWallpaperController.adjustWallpaperWindows();
            }
        }
        if (r15 != false) {
            displayState.mKeyguardGoingAway = false;
            if (z) {
                displayState.mDismissalRequested = false;
                if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX) {
                    displayState.mDismissalRequestedInOccluded = false;
                }
            } else if (displayState.mOccluded) {
                this.mRootWindowContainer.getDefaultDisplay().pendingLayoutChanges |= 4;
            }
            if (r14 != false) {
                DisplayContent defaultDisplay2 = this.mRootWindowContainer.getDefaultDisplay();
                defaultDisplay2.requestTransitionAndLegacyPrepare(3, IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES);
                defaultDisplay2.mWallpaperController.showWallpaperInTransition(false);
                this.mWindowManager.executeAppTransition();
            }
        }
        updateKeyguardSleepToken();
        this.mRootWindowContainer.ensureActivitiesVisible(null, 0, false);
        InputMethodManagerInternal.get().updateImeWindowStatus(false);
        setWakeTransitionReady();
        if (r13 == true) {
            this.mWindowManager.mWindowPlacerLocked.performSurfacePlacement();
        }
    }

    public final void setWakeTransitionReady() {
        if (this.mWindowManager.mAtmService.getTransitionController().getCollectingTransitionType() == 11) {
            this.mWindowManager.mAtmService.getTransitionController().setReady(this.mRootWindowContainer.getDefaultDisplay());
        }
    }

    public void keyguardGoingAway(int i, int i2) {
        Task rootTask;
        ActivityRecord topActivity;
        KeyguardDisplayState displayState = getDisplayState(i);
        if (!displayState.mKeyguardShowing || displayState.mKeyguardGoingAway) {
            Slog.i("ActivityTaskManager", "Skip keyguardGoingAway. displayId=" + i + ", flags=0x" + Integer.toHexString(i2) + ", mKeyguardShowing=" + displayState.mKeyguardShowing + ", mKeyguardGoingAway=" + displayState.mKeyguardGoingAway);
            return;
        }
        Trace.traceBegin(32L, "keyguardGoingAway");
        Slog.d("ActivityTaskManager", "keyguardGoingAway d" + i + ", fl=0x" + Integer.toHexString(i2));
        boolean z = (i2 & 256) != 0;
        boolean z2 = CoreRune.FW_FINGERPRINT_SIDE_TOUCH;
        if (z2 && z) {
            this.mWindowManager.mExt.keyguardGoingAwayWithFingerprintUnlock(z2 && z);
        }
        if (CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY) {
            this.mService.mMultiWindowFoldController.onKeyguardGoingAway();
        }
        if (i == 0) {
            displayState.mWakeAndUnlock = z;
        }
        this.mService.deferWindowLayout();
        displayState.mKeyguardGoingAway = true;
        try {
            EventLogTags.writeWmSetKeyguardShown(i, displayState.mKeyguardShowing ? 1 : 0, displayState.mAodShowing ? 1 : 0, 1, displayState.mOccluded ? 1 : 0, "keyguardGoingAway");
            int convertTransitFlags = convertTransitFlags(i2);
            DisplayContent defaultDisplay = this.mRootWindowContainer.getDefaultDisplay();
            defaultDisplay.prepareAppTransition(7, convertTransitFlags);
            defaultDisplay.mAtmService.getTransitionController().requestTransitionIfNeeded(4, convertTransitFlags, null, defaultDisplay);
            updateKeyguardSleepToken();
            defaultDisplay.mWallpaperController.showWallpaperInTransition(true);
            this.mRootWindowContainer.resumeFocusedTasksTopActivities();
            this.mRootWindowContainer.ensureActivitiesVisible(null, 0, false);
            this.mRootWindowContainer.addStartingWindowsForVisibleActivities();
            if (CoreRune.FW_APPLOCK && (rootTask = this.mRootWindowContainer.getDefaultDisplay().getRootTask(1, 1)) != null && (topActivity = rootTask.getTopActivity(false, true)) != null && !this.mTaskSupervisor.isTopResumedActivity(topActivity) && (topActivity.isState(ActivityRecord.State.RESUMED) || topActivity.nowVisible)) {
                Log.d("ActivityTaskManager", "Applock Activity record " + topActivity);
                this.mService.mExt.startAppLockActivity(topActivity);
            }
            this.mWindowManager.executeAppTransition();
        } finally {
            this.mService.continueWindowLayout();
            Trace.traceEnd(32L);
        }
    }

    public void dismissKeyguard(IBinder iBinder, IKeyguardDismissCallback iKeyguardDismissCallback, CharSequence charSequence) {
        ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(iBinder);
        if (forTokenLocked == null || !forTokenLocked.visibleIgnoringKeyguard) {
            failCallback(iKeyguardDismissCallback);
            return;
        }
        Slog.i("ActivityTaskManager", "Activity requesting to dismiss Keyguard: " + forTokenLocked);
        if (forTokenLocked.getTurnScreenOnFlag() && forTokenLocked.isTopRunningActivity()) {
            this.mTaskSupervisor.wakeUp("dismissKeyguard::" + forTokenLocked.packageName);
        }
        this.mWindowManager.dismissKeyguard(iKeyguardDismissCallback, charSequence);
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX && forTokenLocked.getDisplayId() == 0 && isDisplayOccluded(forTokenLocked.getDisplayId())) {
            getDisplayState(forTokenLocked.getDisplayId()).mDismissalRequestedInOccluded = true;
        }
    }

    public final void failCallback(IKeyguardDismissCallback iKeyguardDismissCallback) {
        try {
            iKeyguardDismissCallback.onDismissError();
        } catch (RemoteException e) {
            Slog.w("ActivityTaskManager", "Failed to call callback", e);
        }
    }

    public boolean canShowActivityWhileKeyguardShowing(ActivityRecord activityRecord) {
        KeyguardDisplayState displayState = getDisplayState(activityRecord.getDisplayId());
        return activityRecord.containsDismissKeyguardWindow() && canDismissKeyguard() && !displayState.mAodShowing && (displayState.mDismissalRequested || (activityRecord.canShowWhenLocked() && displayState.mDismissingKeyguardActivity != activityRecord));
    }

    public boolean canShowWhileOccluded(boolean z, boolean z2) {
        return z2 || (z && !this.mWindowManager.isKeyguardSecure(this.mService.getCurrentUserId()));
    }

    public boolean checkKeyguardVisibility(ActivityRecord activityRecord) {
        if (activityRecord.mDisplayContent.canShowWithInsecureKeyguard() && canDismissKeyguard()) {
            return true;
        }
        if (isKeyguardOrAodShowing(activityRecord.mDisplayContent.getDisplayId())) {
            return canShowActivityWhileKeyguardShowing(activityRecord);
        }
        if (isKeyguardLocked(activityRecord.getDisplayId())) {
            return canShowWhileOccluded(activityRecord.containsDismissKeyguardWindow(), activityRecord.canShowWhenLocked());
        }
        return true;
    }

    public void updateVisibility() {
        for (int childCount = this.mRootWindowContainer.getChildCount() - 1; childCount >= 0; childCount--) {
            DisplayContent displayContent = (DisplayContent) this.mRootWindowContainer.getChildAt(childCount);
            if (!displayContent.isRemoving() && !displayContent.isRemoved()) {
                KeyguardDisplayState displayState = getDisplayState(displayContent.mDisplayId);
                displayState.updateVisibility(this, displayContent);
                if (displayState.mRequestDismissKeyguard) {
                    handleDismissKeyguard(displayContent.getDisplayId());
                }
            }
        }
    }

    public final void handleOccludedChanged(int i, ActivityRecord activityRecord) {
        EventLogTags.writeWmOccludedChanged(i, isDisplayOccluded(i) ? 1 : 0);
        if (i != 2 && i != 0) {
            updateKeyguardSleepToken(i);
            return;
        }
        TransitionController transitionController = this.mRootWindowContainer.mTransitionController;
        boolean isDisplayOccluded = isDisplayOccluded(i);
        boolean isKeyguardLocked = isKeyguardLocked(i);
        if (isKeyguardLocked) {
            transitionController.isCollecting();
        }
        this.mWindowManager.mExt.mPolicyExt.onKeyguardOccludedChangedLw(isDisplayOccluded, i, isKeyguardLocked);
        this.mService.deferWindowLayout();
        try {
            if (CoreRune.FW_CHN_PREMIUM_WATCH && ((!transitionController.isCollecting() || !transitionController.getCollectingTransition().isInKeyguardTransition()) && activityRecord != null && activityRecord.mNoTransitionOcclusion)) {
                this.mWindowManager.mPolicy.applyKeyguardOcclusionChange();
            } else if (isKeyguardLocked(i)) {
                if (isDisplayOccluded) {
                    this.mRootWindowContainer.getDisplayContent(i).requestTransitionAndLegacyPrepare(8, IInstalld.FLAG_USE_QUOTA, activityRecord != null ? activityRecord.getRootTask() : null);
                    if (CoreRune.MW_SHELL_TRANSITION_BUG_FIX) {
                        handleFreeformTaskOccluded(activityRecord);
                    }
                } else {
                    this.mRootWindowContainer.getDisplayContent(i).requestTransitionAndLegacyPrepare(9, IInstalld.FLAG_FORCE);
                    if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX && transitionController.isCollecting() && isDismissalRequestedInOccluded(i)) {
                        transitionController.getCollectingTransition().addFlag(65536);
                    }
                }
            } else if (transitionController.inTransition()) {
                ArrayList arrayList = transitionController.mStateValidators;
                WindowManagerPolicy windowManagerPolicy = this.mWindowManager.mPolicy;
                Objects.requireNonNull(windowManagerPolicy);
                arrayList.add(new KeyguardController$$ExternalSyntheticLambda1(windowManagerPolicy));
            } else {
                this.mWindowManager.mPolicy.applyKeyguardOcclusionChange();
            }
            updateKeyguardSleepToken(i);
            if (isKeyguardLocked) {
                this.mWindowManager.executeAppTransition();
            }
        } finally {
            this.mService.continueWindowLayout();
        }
    }

    public final void handleKeyguardGoingAwayChanged(DisplayContent displayContent) {
        this.mService.deferWindowLayout();
        try {
            displayContent.prepareAppTransition(7, 0);
            displayContent.mAtmService.getTransitionController().requestTransitionIfNeeded(1, 256, null, displayContent);
            updateKeyguardSleepToken();
            this.mWindowManager.executeAppTransition();
        } finally {
            this.mService.continueWindowLayout();
        }
    }

    public final void handleDismissKeyguard(int i) {
        if (this.mWindowManager.isKeyguardSecure(this.mService.getCurrentUserId())) {
            this.mWindowManager.dismissKeyguard(null, null);
            KeyguardDisplayState displayState = getDisplayState(i);
            displayState.mDismissalRequested = true;
            DisplayContent defaultDisplay = this.mRootWindowContainer.getDefaultDisplay();
            if (displayState.mKeyguardShowing && canDismissKeyguard() && defaultDisplay.mAppTransition.containsTransitRequest(9)) {
                this.mWindowManager.executeAppTransition();
            }
        }
    }

    public boolean isDisplayOccluded(int i) {
        return getDisplayState(i).mOccluded;
    }

    public ActivityRecord getTopOccludingActivity(int i) {
        return getDisplayState(i).mTopOccludesActivity;
    }

    public ActivityRecord getDismissKeyguardActivity(int i) {
        return getDisplayState(i).mDismissingKeyguardActivity;
    }

    public boolean canDismissKeyguard() {
        return this.mWindowManager.mPolicy.isKeyguardTrustedLw() || !this.mWindowManager.isKeyguardSecure(this.mService.getCurrentUserId());
    }

    public boolean isShowingDream() {
        return getDisplayState(0).mShowingDream;
    }

    public final void updateKeyguardSleepToken() {
        for (int childCount = this.mRootWindowContainer.getChildCount() - 1; childCount >= 0; childCount--) {
            updateKeyguardSleepToken(((DisplayContent) this.mRootWindowContainer.getChildAt(childCount)).mDisplayId);
        }
    }

    public final void updateKeyguardSleepToken(int i) {
        KeyguardDisplayState displayState = getDisplayState(i);
        if (isKeyguardUnoccludedOrAodShowing(i)) {
            displayState.mSleepTokenAcquirer.acquire(i);
        } else {
            displayState.mSleepTokenAcquirer.release(i);
        }
    }

    public final KeyguardDisplayState getDisplayState(int i) {
        KeyguardDisplayState keyguardDisplayState = (KeyguardDisplayState) this.mDisplayStates.get(i);
        if (keyguardDisplayState != null) {
            return keyguardDisplayState;
        }
        KeyguardDisplayState keyguardDisplayState2 = new KeyguardDisplayState(this.mService, i, this.mSleepTokenAcquirer);
        this.mDisplayStates.append(i, keyguardDisplayState2);
        return keyguardDisplayState2;
    }

    public void onDisplayRemoved(int i) {
        KeyguardDisplayState keyguardDisplayState = (KeyguardDisplayState) this.mDisplayStates.get(i);
        if (keyguardDisplayState != null) {
            keyguardDisplayState.onRemoved();
            this.mDisplayStates.remove(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mWindowManager.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                updateDeferTransitionForAod(false);
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void updateDeferTransitionForAod(boolean z) {
        if (z != this.mWaitingForWakeTransition && this.mService.getTransitionController().isCollecting()) {
            if (z && isAodShowing(0)) {
                this.mWaitingForWakeTransition = true;
                this.mWindowManager.mAtmService.getTransitionController().deferTransitionReady();
                this.mWindowManager.f1749mH.postDelayed(this.mResetWaitTransition, 5000L);
            } else {
                if (z) {
                    return;
                }
                this.mWaitingForWakeTransition = false;
                this.mWindowManager.mAtmService.getTransitionController().continueTransitionReady();
                this.mWindowManager.f1749mH.removeCallbacks(this.mResetWaitTransition);
            }
        }
    }

    public void handleDexDisplayDisabled() {
        if (CoreRune.SAFE_DEBUG) {
            Slog.d("ActivityTaskManager", "handleDexDisplayDisabled: release a SleepToken of Dex Display");
        }
        getDisplayState(2).mSleepTokenAcquirer.release(2);
    }

    public final void handleFreeformTaskOccluded(ActivityRecord activityRecord) {
        Task rootTask = activityRecord != null ? activityRecord.getRootTask() : null;
        Transition collectingTransition = this.mService.getTransitionController().getCollectingTransition();
        if (rootTask == null || !rootTask.inFreeformWindowingMode() || collectingTransition == null || collectingTransition.mType == 8 || rootTask.isDesktopModeEnabled()) {
            return;
        }
        Slog.w("ActivityTaskManager", "handleFreeformTaskOccluded: failed to request TRANSIT_KEYGUARD_OCCLUDE, transition=" + collectingTransition + ", make fullscreen, " + rootTask);
        rootTask.setWindowingMode(1);
    }

    public boolean isWakeAndUnlock(int i) {
        return getDisplayState(i).mWakeAndUnlock;
    }

    public class KeyguardDisplayState {
        public boolean mAodShowing;
        public boolean mDismissalRequested;
        public boolean mDismissalRequestedInOccluded;
        public ActivityRecord mDismissingKeyguardActivity;
        public final int mDisplayId;
        public boolean mKeyguardGoingAway;
        public boolean mKeyguardShowing;
        public boolean mOccluded;
        public boolean mRequestDismissKeyguard;
        public final ActivityTaskManagerService mService;
        public boolean mShowingDream;
        public final ActivityTaskManagerInternal.SleepTokenAcquirer mSleepTokenAcquirer;
        public ActivityRecord mTopOccludesActivity;
        public ActivityRecord mTopTurnScreenOnActivity;
        public boolean mWakeAndUnlock;

        public KeyguardDisplayState(ActivityTaskManagerService activityTaskManagerService, int i, ActivityTaskManagerInternal.SleepTokenAcquirer sleepTokenAcquirer) {
            this.mService = activityTaskManagerService;
            this.mDisplayId = i;
            this.mSleepTokenAcquirer = sleepTokenAcquirer;
        }

        public void onRemoved() {
            this.mTopOccludesActivity = null;
            this.mDismissingKeyguardActivity = null;
            this.mTopTurnScreenOnActivity = null;
            this.mSleepTokenAcquirer.release(this.mDisplayId);
        }

        public void updateVisibility(KeyguardController keyguardController, DisplayContent displayContent) {
            boolean z;
            boolean z2 = this.mOccluded;
            boolean z3 = this.mKeyguardGoingAway;
            ActivityRecord activityRecord = this.mDismissingKeyguardActivity;
            boolean z4 = false;
            this.mRequestDismissKeyguard = false;
            this.mOccluded = false;
            this.mShowingDream = false;
            this.mTopOccludesActivity = null;
            this.mDismissingKeyguardActivity = null;
            this.mTopTurnScreenOnActivity = null;
            Task rootTaskForControllingOccluding = getRootTaskForControllingOccluding(displayContent);
            ActivityRecord topNonFinishingActivity = rootTaskForControllingOccluding != null ? rootTaskForControllingOccluding.getTopNonFinishingActivity() : null;
            if (topNonFinishingActivity != null) {
                if (topNonFinishingActivity.containsDismissKeyguardWindow()) {
                    this.mDismissingKeyguardActivity = topNonFinishingActivity;
                }
                if (topNonFinishingActivity.getTurnScreenOnFlag() && topNonFinishingActivity.currentLaunchCanTurnScreenOn()) {
                    this.mTopTurnScreenOnActivity = topNonFinishingActivity;
                }
                if (topNonFinishingActivity.mDismissKeyguard && this.mKeyguardShowing) {
                    this.mKeyguardGoingAway = true;
                } else if (topNonFinishingActivity.canShowWhenLocked()) {
                    this.mTopOccludesActivity = topNonFinishingActivity;
                }
                topNonFinishingActivity.mDismissKeyguard = false;
                boolean z5 = this.mTopOccludesActivity != null || (this.mDismissingKeyguardActivity != null && rootTaskForControllingOccluding.topRunningActivity() == this.mDismissingKeyguardActivity && keyguardController.canShowWhileOccluded(true, false));
                z = z5;
                if (this.mDisplayId != 0) {
                    z = z5 | (displayContent.canShowWithInsecureKeyguard() && keyguardController.canDismissKeyguard());
                }
            } else {
                z = false;
            }
            boolean z6 = displayContent.getDisplayPolicy().isShowingDreamLw() && topNonFinishingActivity != null && topNonFinishingActivity.getActivityType() == 5;
            this.mShowingDream = z6;
            boolean z7 = z6 || z;
            this.mOccluded = z7;
            ActivityRecord activityRecord2 = this.mDismissingKeyguardActivity;
            this.mRequestDismissKeyguard = (activityRecord == activityRecord2 || z7 || this.mKeyguardGoingAway || activityRecord2 == null) ? false : true;
            if (z7 && this.mKeyguardShowing && !displayContent.isSleeping() && ((!topNonFinishingActivity.fillsParent() || (topNonFinishingActivity.isDesktopModeEnabled() && topNonFinishingActivity.inFreeformWindowingMode())) && displayContent.mWallpaperController.getWallpaperTarget() == null)) {
                displayContent.pendingLayoutChanges |= 4;
            }
            if (this.mTopTurnScreenOnActivity != null && !this.mService.mWindowManager.mPowerManager.isInteractive() && (this.mRequestDismissKeyguard || z)) {
                keyguardController.mTaskSupervisor.wakeUp("handleTurnScreenOn::" + this.mTopTurnScreenOnActivity.packageName);
                this.mTopTurnScreenOnActivity.setCurrentLaunchCanTurnScreenOn(false);
            }
            ActivityRecord activityRecord3 = this.mDismissingKeyguardActivity;
            if (activityRecord != activityRecord3) {
                if (activityRecord3 != null) {
                    this.mService.mAmInternal.setKeyguardPkgInfo(activityRecord3.packageName, activityRecord3.getUid());
                } else {
                    this.mService.mAmInternal.setKeyguardPkgInfo((String) null, -1);
                }
            }
            boolean z8 = this.mOccluded;
            if (z2 != z8) {
                int i = this.mDisplayId;
                if (i == 0) {
                    EventLogTags.writeWmSetKeyguardShown(i, this.mKeyguardShowing ? 1 : 0, this.mAodShowing ? 1 : 0, this.mKeyguardGoingAway ? 1 : 0, z8 ? 1 : 0, "updateVisibility");
                }
                keyguardController.handleOccludedChanged(this.mDisplayId, this.mTopOccludesActivity);
                if (this.mOccluded && !this.mService.isPrepareOccluding(this.mDisplayId) && this.mService.isOccluding(this.mDisplayId)) {
                    if (CoreRune.SAFE_DEBUG) {
                        Slog.d("ActivityTaskManager", "visibilitiesUpdated, mOccluding is set to false, displayId=" + this.mDisplayId + ", caller=" + Debug.getCallers(3));
                    }
                    this.mService.setOccluding(false, this.mDisplayId);
                }
            } else {
                if (!z3 && this.mKeyguardGoingAway) {
                    keyguardController.handleKeyguardGoingAwayChanged(displayContent);
                }
                if (z4 || topNonFinishingActivity == null) {
                }
                if (this.mOccluded || this.mKeyguardGoingAway) {
                    displayContent.mTransitionController.collect(topNonFinishingActivity);
                    return;
                }
                return;
            }
            z4 = true;
            if (z4) {
            }
        }

        public final Task getRootTaskForControllingOccluding(final DisplayContent displayContent) {
            return displayContent.getRootTask(new Predicate() { // from class: com.android.server.wm.KeyguardController$KeyguardDisplayState$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$getRootTaskForControllingOccluding$0;
                    lambda$getRootTaskForControllingOccluding$0 = KeyguardController.KeyguardDisplayState.lambda$getRootTaskForControllingOccluding$0(DisplayContent.this, (Task) obj);
                    return lambda$getRootTaskForControllingOccluding$0;
                }
            });
        }

        public static /* synthetic */ boolean lambda$getRootTaskForControllingOccluding$0(DisplayContent displayContent, Task task) {
            return task != null && task.isFocusableAndVisible() && !task.inPinnedWindowingMode() && (!task.isAlwaysOnTopFreeform() || task == displayContent.getFocusedRootTask());
        }

        public void dumpStatus(PrintWriter printWriter, String str) {
            printWriter.println(str + " KeyguardShowing=" + this.mKeyguardShowing + " AodShowing=" + this.mAodShowing + " KeyguardGoingAway=" + this.mKeyguardGoingAway + " DismissalRequested=" + this.mDismissalRequested + "  Occluded=" + this.mOccluded + " DismissingKeyguardActivity=" + this.mDismissingKeyguardActivity + " TurnScreenOnActivity=" + this.mTopTurnScreenOnActivity + " at display=" + this.mDisplayId);
        }

        public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1120986464257L, this.mDisplayId);
            protoOutputStream.write(1133871366146L, this.mKeyguardShowing);
            protoOutputStream.write(1133871366147L, this.mAodShowing);
            protoOutputStream.write(1133871366148L, this.mOccluded);
            protoOutputStream.write(1133871366149L, this.mKeyguardGoingAway);
            protoOutputStream.end(start);
        }
    }

    public void dump(PrintWriter printWriter, String str) {
        KeyguardDisplayState displayState = getDisplayState(0);
        printWriter.println(str + "KeyguardController:");
        printWriter.println(str + "  mKeyguardShowing=" + displayState.mKeyguardShowing);
        printWriter.println(str + "  mAodShowing=" + displayState.mAodShowing);
        printWriter.println(str + "  mKeyguardGoingAway=" + displayState.mKeyguardGoingAway);
        dumpDisplayStates(printWriter, str);
        printWriter.println(str + "  mDismissalRequested=" + displayState.mDismissalRequested);
        printWriter.println();
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        KeyguardDisplayState displayState = getDisplayState(0);
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1133871366147L, displayState.mAodShowing);
        protoOutputStream.write(1133871366145L, displayState.mKeyguardShowing);
        protoOutputStream.write(1133871366149L, displayState.mKeyguardGoingAway);
        writeDisplayStatesToProto(protoOutputStream, 2246267895812L);
        protoOutputStream.end(start);
    }

    public final void dumpDisplayStates(PrintWriter printWriter, String str) {
        for (int i = 0; i < this.mDisplayStates.size(); i++) {
            ((KeyguardDisplayState) this.mDisplayStates.valueAt(i)).dumpStatus(printWriter, str);
        }
    }

    public final void writeDisplayStatesToProto(ProtoOutputStream protoOutputStream, long j) {
        for (int i = 0; i < this.mDisplayStates.size(); i++) {
            ((KeyguardDisplayState) this.mDisplayStates.valueAt(i)).dumpDebug(protoOutputStream, j);
        }
    }
}
