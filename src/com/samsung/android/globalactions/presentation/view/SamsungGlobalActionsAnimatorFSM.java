package com.samsung.android.globalactions.presentation.view;

import com.samsung.android.globalactions.util.LogWrapper;

/* loaded from: classes6.dex */
public class SamsungGlobalActionsAnimatorFSM {
    private static final String TAG = "SamsungGlobalActionsAnimatorFSM";
    private final GlobalActionsAnimator mAnimator;
    private boolean mIsPortrait;
    private final LogWrapper mLogWrapper;
    public State mState;
    private ViewStateController mViewStateController;

    public enum Event {
        SHOW,
        HIDE,
        SHOW_CONFIRM,
        HIDE_CONFIRM,
        CONFIGURATION_CHANGED,
        SECURE_CONFIRM
    }

    public enum State {
        IDLE,
        MAIN,
        CONFIRM,
        SAFE_MODE
    }

    public SamsungGlobalActionsAnimatorFSM(GlobalActionsAnimator globalActionsAnimator, LogWrapper logWrapper, ViewStateController viewStateController) {
        this.mAnimator = globalActionsAnimator;
        this.mViewStateController = viewStateController;
        this.mLogWrapper = logWrapper;
        setState(State.IDLE);
    }

    public void setState(State state) {
        this.mLogWrapper.i(TAG, "ViewState = " + state.name());
        this.mState = state;
    }

    public void handleAnimationEvent(Event event) {
        if (this.mViewStateController.getState() == ViewAnimationState.IDLE || (event == Event.CONFIGURATION_CHANGED && this.mState == State.MAIN)) {
            int iOrdinal = this.mState.ordinal();
            if (iOrdinal == 0) {
                if (event == Event.SHOW) {
                    if (this.mIsPortrait) {
                        this.mAnimator.showMainViewPort();
                    } else {
                        this.mAnimator.showMainViewLand();
                    }
                    this.mAnimator.startShowAnimation();
                    setState(State.MAIN);
                    return;
                }
                return;
            }
            if (iOrdinal == 1) {
                if (event == Event.CONFIGURATION_CHANGED) {
                    if (this.mIsPortrait) {
                        this.mAnimator.showMainViewPort();
                        return;
                    } else {
                        this.mAnimator.showMainViewLand();
                        return;
                    }
                }
                if (event == Event.SHOW_CONFIRM) {
                    if (this.mAnimator.isHideConfirmAnimationRunning()) {
                        this.mAnimator.cancelHideConfirmAnimation();
                    }
                    if (this.mAnimator.isSafeModeConfirm()) {
                        this.mAnimator.startShowSafeModeAnimation();
                        setState(State.SAFE_MODE);
                        return;
                    } else {
                        this.mAnimator.startShowConfirmAnimation();
                        setState(State.CONFIRM);
                        return;
                    }
                }
                if (event == Event.HIDE) {
                    this.mAnimator.startDismissAnimation(false);
                    setState(State.IDLE);
                    return;
                } else {
                    if (event == Event.SECURE_CONFIRM) {
                        this.mAnimator.startDismissAnimation(true);
                        setState(State.IDLE);
                        return;
                    }
                    return;
                }
            }
            if (iOrdinal != 2) {
                if (iOrdinal != 3) {
                    return;
                }
                if (event == Event.CONFIGURATION_CHANGED) {
                    if (this.mIsPortrait) {
                        this.mAnimator.setListViewPort();
                    } else {
                        this.mAnimator.setListViewLand();
                    }
                    this.mAnimator.initializeSelectedActionView();
                    return;
                }
                if (event == Event.HIDE_CONFIRM) {
                    if (this.mAnimator.isShowConfirmAnimationRunning()) {
                        this.mAnimator.cancelShowConfirmAnimation();
                    }
                    this.mAnimator.startDismissSafeModeAnimation();
                    setState(State.MAIN);
                    return;
                }
                if (event == Event.HIDE) {
                    this.mAnimator.startDismissAnimation(false);
                    setState(State.IDLE);
                    return;
                } else {
                    if (event == Event.SECURE_CONFIRM) {
                        this.mAnimator.startDismissAnimation(true);
                        setState(State.IDLE);
                        return;
                    }
                    return;
                }
            }
            if (event == Event.CONFIGURATION_CHANGED) {
                if (this.mIsPortrait) {
                    this.mAnimator.setListViewPort();
                } else {
                    this.mAnimator.setListViewLand();
                }
                this.mAnimator.initializeSelectedActionView();
                return;
            }
            if (event == Event.HIDE_CONFIRM) {
                if (this.mAnimator.isShowConfirmAnimationRunning()) {
                    this.mAnimator.cancelShowConfirmAnimation();
                }
                this.mAnimator.startDismissConfirmAnimation();
                setState(State.MAIN);
                return;
            }
            if (event == Event.SHOW_CONFIRM) {
                this.mAnimator.startSetSafeModeAnimation();
                setState(State.SAFE_MODE);
            } else if (event == Event.HIDE) {
                this.mAnimator.startDismissAnimation(false);
                setState(State.IDLE);
            } else if (event == Event.SECURE_CONFIRM) {
                this.mAnimator.startDismissAnimation(true);
                setState(State.IDLE);
            }
        }
    }

    public void setOrientation(boolean z) {
        this.mIsPortrait = z;
    }
}
