package com.android.systemui.globalactions.presentation.view;

import com.samsung.android.globalactions.presentation.view.GlobalActionsAnimator;
import com.samsung.android.globalactions.presentation.view.ViewAnimationState;
import com.samsung.android.globalactions.presentation.view.ViewStateController;
import com.samsung.android.globalactions.util.LogWrapper;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class CoverViewAnimatorFSM {
    public final GlobalActionsAnimator mAnimator;
    public final LogWrapper mLogWrapper;
    public State mState;
    public final ViewStateController mViewStateController;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public enum Event {
        SHOW,
        HIDE,
        SHOW_CONFIRM,
        HIDE_CONFIRM,
        /* JADX INFO: Fake field, exist only in values array */
        CONFIGURATION_CHANGED,
        SECURE_CONFIRM,
        COVER_TOAST
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    enum State {
        IDLE,
        MAIN,
        CONFIRM
    }

    public CoverViewAnimatorFSM(GlobalActionsAnimator globalActionsAnimator, LogWrapper logWrapper, ViewStateController viewStateController) {
        this.mAnimator = globalActionsAnimator;
        this.mLogWrapper = logWrapper;
        this.mViewStateController = viewStateController;
        setState(State.IDLE);
    }

    public final void handleAnimationEvent(Event event) {
        this.mLogWrapper.v("CoverViewAnimatorFSM", "handleAnimationEvent() Event : " + event + ", state : " + this.mState);
        if (this.mViewStateController.getState() != ViewAnimationState.IDLE) {
            return;
        }
        int ordinal = this.mState.ordinal();
        if (ordinal == 0) {
            if (event == Event.SHOW) {
                this.mAnimator.startShowAnimation();
                setState(State.MAIN);
                return;
            }
            return;
        }
        if (ordinal != 1) {
            if (ordinal != 2) {
                return;
            }
            if (event == Event.HIDE_CONFIRM) {
                this.mAnimator.startDismissConfirmAnimation();
                setState(State.MAIN);
                return;
            }
            if (event == Event.SECURE_CONFIRM) {
                this.mAnimator.startDismissAnimation(true);
                setState(State.MAIN);
                return;
            } else if (event == Event.COVER_TOAST) {
                this.mAnimator.startToastAnimation();
                setState(State.MAIN);
                return;
            } else {
                if (event == Event.HIDE) {
                    this.mAnimator.startDismissAnimation(false);
                    return;
                }
                return;
            }
        }
        if (event == Event.SHOW_CONFIRM) {
            this.mAnimator.startShowConfirmAnimation();
            setState(State.CONFIRM);
            return;
        }
        if (event == Event.SECURE_CONFIRM) {
            this.mAnimator.startDismissAnimation(true);
            setState(State.IDLE);
            return;
        }
        if (event == Event.HIDE) {
            this.mAnimator.startDismissAnimation(false);
            setState(State.IDLE);
        } else if (event == Event.COVER_TOAST) {
            this.mAnimator.startToastAnimation();
        } else if (event == Event.HIDE_CONFIRM) {
            this.mAnimator.startDismissAnimation(false);
            setState(State.IDLE);
        }
    }

    public final void setState(State state) {
        this.mLogWrapper.v("CoverViewAnimatorFSM", "ViewState = " + state.name());
        this.mState = state;
    }
}
