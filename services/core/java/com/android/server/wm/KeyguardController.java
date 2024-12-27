package com.android.server.wm;

import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;
import android.util.SparseArray;

import com.android.internal.policy.IKeyguardDismissCallback;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.policy.PhoneWindowManager;
import com.android.server.wm.ActivityTaskManagerService.SleepTokenAcquirerImpl;

import java.io.PrintWriter;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class KeyguardController {
    public RootWindowContainer mRootWindowContainer;
    public final ActivityTaskManagerService mService;
    public final ActivityTaskManagerService.SleepTokenAcquirerImpl mSleepTokenAcquirer;
    public final ActivityTaskSupervisor mTaskSupervisor;
    public boolean mWaitingForWakeTransition;
    public WindowManagerService mWindowManager;
    public final SparseArray mDisplayStates = new SparseArray();
    public Transition.ReadyCondition mWaitAodHide = null;
    public final KeyguardController$$ExternalSyntheticLambda0 mResetWaitTransition =
            new KeyguardController$$ExternalSyntheticLambda0(0, this);

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class KeyguardDisplayState {
        public boolean mAodShowing;
        public boolean mDismissalRequested;
        public ActivityRecord mDismissingKeyguardActivity;
        public final int mDisplayId;
        public boolean mKeyguardGoingAway;
        public boolean mKeyguardShowing;
        public boolean mOccluded;
        public boolean mRequestDismissKeyguard;
        public final ActivityTaskManagerService mService;
        public final ActivityTaskManagerService.SleepTokenAcquirerImpl mSleepTokenAcquirer;
        public ActivityRecord mTopOccludesActivity;
        public ActivityRecord mTopTurnScreenOnActivity;
        public boolean mWakeAndUnlock;

        public KeyguardDisplayState(
                ActivityTaskManagerService activityTaskManagerService,
                int i,
                ActivityTaskManagerService.SleepTokenAcquirerImpl sleepTokenAcquirerImpl) {
            this.mService = activityTaskManagerService;
            this.mDisplayId = i;
            this.mSleepTokenAcquirer = sleepTokenAcquirerImpl;
        }
    }

    public KeyguardController(
            ActivityTaskManagerService activityTaskManagerService,
            ActivityTaskSupervisor activityTaskSupervisor) {
        this.mService = activityTaskManagerService;
        this.mTaskSupervisor = activityTaskSupervisor;
        Objects.requireNonNull(activityTaskManagerService);
        this.mSleepTokenAcquirer =
                activityTaskManagerService.new SleepTokenAcquirerImpl("keyguard");
    }

    public final boolean canDismissKeyguard() {
        return ((PhoneWindowManager) this.mWindowManager.mPolicy).isKeyguardTrustedLw()
                || !this.mWindowManager.isKeyguardSecure(
                        this.mService.mAmInternal.getCurrentUserId());
    }

    public final boolean canShowWhileOccluded(boolean z, boolean z2) {
        return z2
                || (z
                        && !this.mWindowManager.isKeyguardSecure(
                                this.mService.mAmInternal.getCurrentUserId()));
    }

    public final boolean checkKeyguardVisibility(ActivityRecord activityRecord) {
        if (((activityRecord.mDisplayContent.mDisplay.getFlags() & 32) != 0)
                && canDismissKeyguard()) {
            return true;
        }
        if (!isKeyguardOrAodShowing(activityRecord.mDisplayContent.mDisplayId)) {
            if (isKeyguardLocked(activityRecord.getDisplayId())) {
                return canShowWhileOccluded(
                        activityRecord.containsDismissKeyguardWindow(),
                        activityRecord.canShowWhenLocked());
            }
            return true;
        }
        KeyguardDisplayState displayState = getDisplayState(activityRecord.getDisplayId());
        if (activityRecord.containsDismissKeyguardWindow()
                && canDismissKeyguard()
                && !displayState.mAodShowing) {
            return displayState.mDismissalRequested
                    || (activityRecord.canShowWhenLocked()
                            && displayState.mDismissingKeyguardActivity != activityRecord);
        }
        return false;
    }

    public final void dismissKeyguard(
            IBinder iBinder,
            IKeyguardDismissCallback iKeyguardDismissCallback,
            CharSequence charSequence) {
        ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(iBinder);
        if (forTokenLocked == null || !forTokenLocked.visibleIgnoringKeyguard) {
            try {
                iKeyguardDismissCallback.onDismissError();
                return;
            } catch (RemoteException e) {
                Slog.w("ActivityTaskManager", "Failed to call callback", e);
                return;
            }
        }
        Slog.i("ActivityTaskManager", "Activity requesting to dismiss Keyguard: " + forTokenLocked);
        if ((forTokenLocked.mTurnScreenOn || forTokenLocked.containsTurnScreenOnWindow())
                && forTokenLocked.isTopRunningActivity()) {
            this.mTaskSupervisor.wakeUp("dismissKeyguard::" + forTokenLocked.packageName);
        }
        this.mWindowManager.dismissKeyguard(iKeyguardDismissCallback, charSequence);
    }

    public final void dump(PrintWriter printWriter) {
        KeyguardDisplayState displayState = getDisplayState(0);
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                                        printWriter,
                                        "  KeyguardController:",
                                        "    mKeyguardShowing="),
                                displayState.mKeyguardShowing,
                                printWriter,
                                "    mAodShowing="),
                        displayState.mAodShowing,
                        printWriter,
                        "    mKeyguardGoingAway="),
                displayState.mKeyguardGoingAway,
                printWriter);
        for (int i = 0; i < this.mDisplayStates.size(); i++) {
            KeyguardDisplayState keyguardDisplayState =
                    (KeyguardDisplayState) this.mDisplayStates.valueAt(i);
            keyguardDisplayState.getClass();
            printWriter.println(
                    "   KeyguardShowing="
                            + keyguardDisplayState.mKeyguardShowing
                            + " AodShowing="
                            + keyguardDisplayState.mAodShowing
                            + " KeyguardGoingAway="
                            + keyguardDisplayState.mKeyguardGoingAway
                            + " DismissalRequested="
                            + keyguardDisplayState.mDismissalRequested
                            + "  Occluded="
                            + keyguardDisplayState.mOccluded
                            + " DismissingKeyguardActivity="
                            + keyguardDisplayState.mDismissingKeyguardActivity
                            + " TurnScreenOnActivity="
                            + keyguardDisplayState.mTopTurnScreenOnActivity
                            + " at display="
                            + keyguardDisplayState.mDisplayId);
        }
        printWriter.println("    mDismissalRequested=" + displayState.mDismissalRequested);
        printWriter.println();
    }

    public final KeyguardDisplayState getDisplayState(int i) {
        KeyguardDisplayState keyguardDisplayState =
                (KeyguardDisplayState) this.mDisplayStates.get(i);
        if (keyguardDisplayState != null) {
            return keyguardDisplayState;
        }
        KeyguardDisplayState keyguardDisplayState2 =
                new KeyguardDisplayState(this.mService, i, this.mSleepTokenAcquirer);
        this.mDisplayStates.append(i, keyguardDisplayState2);
        return keyguardDisplayState2;
    }

    public final void handleFreeformTaskOccluded(ActivityRecord activityRecord) {
        Task rootTask = activityRecord != null ? activityRecord.getRootTask() : null;
        Transition transition =
                this.mService
                        .mWindowOrganizerController
                        .mTransitionController
                        .mCollectingTransition;
        if (rootTask == null
                || !rootTask.inFreeformWindowingMode()
                || transition == null
                || transition.mType == 8
                || rootTask.isDesktopModeEnabled()) {
            return;
        }
        Slog.w(
                "ActivityTaskManager",
                "handleFreeformTaskOccluded: failed to request TRANSIT_KEYGUARD_OCCLUDE,"
                    + " transition="
                        + transition
                        + ", make fullscreen, "
                        + rootTask);
        rootTask.setWindowingMode(1);
    }

    public final boolean isKeyguardGoingAway(int i) {
        KeyguardDisplayState displayState = getDisplayState(i);
        return displayState.mKeyguardGoingAway && displayState.mKeyguardShowing;
    }

    public final boolean isKeyguardLocked(int i) {
        KeyguardDisplayState displayState = getDisplayState(i);
        return displayState.mKeyguardShowing && !displayState.mKeyguardGoingAway;
    }

    public final boolean isKeyguardOccluded(int i) {
        KeyguardDisplayState displayState = getDisplayState(i);
        return displayState.mKeyguardShowing
                && !displayState.mKeyguardGoingAway
                && displayState.mOccluded;
    }

    public final boolean isKeyguardOrAodShowing(int i) {
        KeyguardDisplayState displayState = getDisplayState(i);
        return ((!displayState.mKeyguardShowing && !displayState.mAodShowing)
                        || displayState.mKeyguardGoingAway
                        || displayState.mOccluded)
                ? false
                : true;
    }

    public final boolean isKeyguardShowing(int i) {
        KeyguardDisplayState displayState = getDisplayState(i);
        return (!displayState.mKeyguardShowing
                        || displayState.mKeyguardGoingAway
                        || displayState.mOccluded)
                ? false
                : true;
    }

    public final void setWakeTransitionReady() {
        if (this.mWindowManager.mAtmService.mWindowOrganizerController.mTransitionController
                        .getCollectingTransitionType()
                == 11) {
            this.mWindowManager.mAtmService.mWindowOrganizerController.mTransitionController
                    .setReady(this.mRootWindowContainer.mDefaultDisplay, true);
        }
    }

    public final void updateDeferTransitionForAod(boolean z) {
        TransitionController transitionController =
                this.mService.mWindowOrganizerController.mTransitionController;
        if (transitionController.mFullReadyTracking) {
            if (z == (this.mWaitAodHide != null)) {
                return;
            }
        } else if (z == this.mWaitingForWakeTransition) {
            return;
        }
        if (transitionController.isCollecting()) {
            KeyguardController$$ExternalSyntheticLambda0
                    keyguardController$$ExternalSyntheticLambda0 = this.mResetWaitTransition;
            if (z && getDisplayState(0).mAodShowing) {
                this.mWaitingForWakeTransition = true;
                this.mWindowManager.mAtmService.mWindowOrganizerController.mTransitionController
                        .deferTransitionReady();
                Transition.ReadyCondition readyCondition =
                        new Transition.ReadyCondition("AOD hidden");
                this.mWaitAodHide = readyCondition;
                this.mWindowManager.mAtmService.mWindowOrganizerController.mTransitionController
                        .waitFor(readyCondition);
                this.mWindowManager.mH.postDelayed(
                        keyguardController$$ExternalSyntheticLambda0, 5000L);
                return;
            }
            if (z) {
                return;
            }
            this.mWaitingForWakeTransition = false;
            this.mWindowManager.mAtmService.mWindowOrganizerController.mTransitionController
                    .continueTransitionReady();
            this.mWindowManager.mH.removeCallbacks(keyguardController$$ExternalSyntheticLambda0);
            Transition.ReadyCondition readyCondition2 = this.mWaitAodHide;
            this.mWaitAodHide = null;
            readyCondition2.meet();
        }
    }

    public final void updateKeyguardSleepToken() {
        for (int childCount = this.mRootWindowContainer.getChildCount() - 1;
                childCount >= 0;
                childCount--) {
            updateKeyguardSleepToken(
                    ((DisplayContent) this.mRootWindowContainer.getChildAt(childCount)).mDisplayId);
        }
    }

    public final void updateKeyguardSleepToken(int i) {
        KeyguardDisplayState displayState = getDisplayState(i);
        boolean isKeyguardOrAodShowing =
                (i == 0 && getDisplayState(i).mAodShowing)
                        ? !r1.mKeyguardGoingAway
                        : isKeyguardOrAodShowing(i);
        ActivityTaskManagerService.SleepTokenAcquirerImpl sleepTokenAcquirerImpl =
                displayState.mSleepTokenAcquirer;
        if (isKeyguardOrAodShowing) {
            sleepTokenAcquirerImpl.acquire(i, false);
        } else {
            sleepTokenAcquirerImpl.release(i);
        }
    }
}
