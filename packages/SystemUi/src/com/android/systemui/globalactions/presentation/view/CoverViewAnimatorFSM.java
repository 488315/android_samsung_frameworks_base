package com.android.systemui.globalactions.presentation.view;

import com.samsung.android.globalactions.presentation.view.GlobalActionsAnimator;
import com.samsung.android.globalactions.presentation.view.ViewAnimationState;
import com.samsung.android.globalactions.presentation.view.ViewStateController;
import com.samsung.android.globalactions.util.LogWrapper;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CoverViewAnimatorFSM {
    public final GlobalActionsAnimator mAnimator;
    public final LogWrapper mLogWrapper;
    public State mState;
    public final ViewStateController mViewStateController;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.globalactions.presentation.view.CoverViewAnimatorFSM$1 */
    public abstract /* synthetic */ class AbstractC14201 {

        /* renamed from: $SwitchMap$com$android$systemui$globalactions$presentation$view$CoverViewAnimatorFSM$State */
        public static final /* synthetic */ int[] f296x86372cb2;

        static {
            int[] iArr = new int[State.values().length];
            f296x86372cb2 = iArr;
            try {
                iArr[State.IDLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f296x86372cb2[State.MAIN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f296x86372cb2[State.CONFIRM.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        int i = AbstractC14201.f296x86372cb2[this.mState.ordinal()];
        GlobalActionsAnimator globalActionsAnimator = this.mAnimator;
        if (i == 1) {
            if (event == Event.SHOW) {
                globalActionsAnimator.startShowAnimation();
                setState(State.MAIN);
                return;
            }
            return;
        }
        if (i != 2) {
            if (i != 3) {
                return;
            }
            if (event == Event.HIDE_CONFIRM) {
                globalActionsAnimator.startDismissConfirmAnimation();
                setState(State.MAIN);
                return;
            }
            if (event == Event.SECURE_CONFIRM) {
                globalActionsAnimator.startDismissAnimation(true);
                setState(State.MAIN);
                return;
            } else if (event == Event.COVER_TOAST) {
                globalActionsAnimator.startToastAnimation();
                setState(State.MAIN);
                return;
            } else {
                if (event == Event.HIDE) {
                    globalActionsAnimator.startDismissAnimation(false);
                    return;
                }
                return;
            }
        }
        if (event == Event.SHOW_CONFIRM) {
            globalActionsAnimator.startShowConfirmAnimation();
            setState(State.CONFIRM);
            return;
        }
        if (event == Event.SECURE_CONFIRM) {
            globalActionsAnimator.startDismissAnimation(true);
            setState(State.IDLE);
            return;
        }
        if (event == Event.HIDE) {
            globalActionsAnimator.startDismissAnimation(false);
            setState(State.IDLE);
        } else if (event == Event.COVER_TOAST) {
            globalActionsAnimator.startToastAnimation();
        } else if (event == Event.HIDE_CONFIRM) {
            globalActionsAnimator.startDismissAnimation(false);
            setState(State.IDLE);
        }
    }

    public final void setState(State state) {
        this.mLogWrapper.v("CoverViewAnimatorFSM", "ViewState = " + state.name());
        this.mState = state;
    }
}
