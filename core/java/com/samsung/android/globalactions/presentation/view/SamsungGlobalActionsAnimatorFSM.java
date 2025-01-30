package com.samsung.android.globalactions.presentation.view;

import com.samsung.android.globalactions.util.LogWrapper;

/* loaded from: classes5.dex */
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
      GlobalActionsAnimator animator, LogWrapper logWrapper, ViewStateController stateController) {
    this.mAnimator = animator;
    this.mViewStateController = stateController;
    this.mLogWrapper = logWrapper;
    setState(State.IDLE);
  }

  public void setState(State state) {
    this.mLogWrapper.m265i(TAG, "ViewState = " + state.name());
    this.mState = state;
  }

  public void handleAnimationEvent(Event event) {
    if (this.mViewStateController.getState() != ViewAnimationState.IDLE
        && (event != Event.CONFIGURATION_CHANGED || this.mState != State.MAIN)) {
      return;
    }
    switch (C50271.f2966x3d77ada5[this.mState.ordinal()]) {
      case 1:
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
      case 2:
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
      case 3:
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
      case 4:
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

  /* renamed from: com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimatorFSM$1 */
  static /* synthetic */ class C50271 {

    /* renamed from: $SwitchMap$com$samsung$android$globalactions$presentation$view$SamsungGlobalActionsAnimatorFSM$State */
    static final /* synthetic */ int[] f2966x3d77ada5;

    static {
      int[] iArr = new int[State.values().length];
      f2966x3d77ada5 = iArr;
      try {
        iArr[State.IDLE.ordinal()] = 1;
      } catch (NoSuchFieldError e) {
      }
      try {
        f2966x3d77ada5[State.MAIN.ordinal()] = 2;
      } catch (NoSuchFieldError e2) {
      }
      try {
        f2966x3d77ada5[State.CONFIRM.ordinal()] = 3;
      } catch (NoSuchFieldError e3) {
      }
      try {
        f2966x3d77ada5[State.SAFE_MODE.ordinal()] = 4;
      } catch (NoSuchFieldError e4) {
      }
    }
  }

  public void setOrientation(boolean isPortrait) {
    this.mIsPortrait = isPortrait;
  }
}
