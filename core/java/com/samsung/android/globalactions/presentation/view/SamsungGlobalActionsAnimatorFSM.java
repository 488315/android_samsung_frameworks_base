package com.samsung.android.globalactions.presentation.view;

import com.samsung.android.globalactions.util.LogWrapper;

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

    public SamsungGlobalActionsAnimatorFSM(
            GlobalActionsAnimator animator,
            LogWrapper logWrapper,
            ViewStateController stateController) {
        this.mAnimator = animator;
        this.mViewStateController = stateController;
        this.mLogWrapper = logWrapper;
        setState(State.IDLE);
    }

    public void setState(State state) {
        this.mLogWrapper.i(TAG, "ViewState = " + state.name());
        this.mState = state;
    }

    public void handleAnimationEvent(Event event) {
        if (this.mViewStateController.getState() != ViewAnimationState.IDLE
                && (event != Event.CONFIGURATION_CHANGED || this.mState != State.MAIN)) {
            return;
        }
        switch (this.mState) {
            case IDLE:
                if (event == Event.SHOW) {
                    if (this.mIsPortrait) {
                        this.mAnimator.showMainViewPort();
                    } else {
                        this.mAnimator.showMainViewLand();
                    }
                    this.mAnimator.startShowAnimation();
                    setState(State.MAIN);
                    break;
                }
                break;
            case MAIN:
                if (event == Event.CONFIGURATION_CHANGED) {
                    if (this.mIsPortrait) {
                        this.mAnimator.showMainViewPort();
                        break;
                    } else {
                        this.mAnimator.showMainViewLand();
                        break;
                    }
                } else if (event == Event.SHOW_CONFIRM) {
                    if (this.mAnimator.isHideConfirmAnimationRunning()) {
                        this.mAnimator.cancelHideConfirmAnimation();
                    }
                    if (this.mAnimator.isSafeModeConfirm()) {
                        this.mAnimator.startShowSafeModeAnimation();
                        setState(State.SAFE_MODE);
                        break;
                    } else {
                        this.mAnimator.startShowConfirmAnimation();
                        setState(State.CONFIRM);
                        break;
                    }
                } else if (event == Event.HIDE) {
                    this.mAnimator.startDismissAnimation(false);
                    setState(State.IDLE);
                    break;
                } else if (event == Event.SECURE_CONFIRM) {
                    this.mAnimator.startDismissAnimation(true);
                    setState(State.IDLE);
                    break;
                }
                break;
            case CONFIRM:
                if (event == Event.CONFIGURATION_CHANGED) {
                    if (this.mIsPortrait) {
                        this.mAnimator.setListViewPort();
                    } else {
                        this.mAnimator.setListViewLand();
                    }
                    this.mAnimator.initializeSelectedActionView();
                    break;
                } else if (event == Event.HIDE_CONFIRM) {
                    if (this.mAnimator.isShowConfirmAnimationRunning()) {
                        this.mAnimator.cancelShowConfirmAnimation();
                    }
                    this.mAnimator.startDismissConfirmAnimation();
                    setState(State.MAIN);
                    break;
                } else if (event == Event.SHOW_CONFIRM) {
                    this.mAnimator.startSetSafeModeAnimation();
                    setState(State.SAFE_MODE);
                    break;
                } else if (event == Event.HIDE) {
                    this.mAnimator.startDismissAnimation(false);
                    setState(State.IDLE);
                    break;
                } else if (event == Event.SECURE_CONFIRM) {
                    this.mAnimator.startDismissAnimation(true);
                    setState(State.IDLE);
                    break;
                }
                break;
            case SAFE_MODE:
                if (event == Event.CONFIGURATION_CHANGED) {
                    if (this.mIsPortrait) {
                        this.mAnimator.setListViewPort();
                    } else {
                        this.mAnimator.setListViewLand();
                    }
                    this.mAnimator.initializeSelectedActionView();
                    break;
                } else if (event == Event.HIDE_CONFIRM) {
                    if (this.mAnimator.isShowConfirmAnimationRunning()) {
                        this.mAnimator.cancelShowConfirmAnimation();
                    }
                    this.mAnimator.startDismissSafeModeAnimation();
                    setState(State.MAIN);
                    break;
                } else if (event == Event.HIDE) {
                    this.mAnimator.startDismissAnimation(false);
                    setState(State.IDLE);
                    break;
                } else if (event == Event.SECURE_CONFIRM) {
                    this.mAnimator.startDismissAnimation(true);
                    setState(State.IDLE);
                    break;
                }
                break;
        }
    }

    public void setOrientation(boolean isPortrait) {
        this.mIsPortrait = isPortrait;
    }
}
