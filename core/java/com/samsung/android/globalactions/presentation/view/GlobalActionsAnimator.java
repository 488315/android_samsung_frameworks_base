package com.samsung.android.globalactions.presentation.view;

/* loaded from: classes5.dex */
public interface GlobalActionsAnimator {
  void cancelHideConfirmAnimation();

  void cancelShowConfirmAnimation();

  void initializeSelectedActionView();

  boolean isHideConfirmAnimationRunning();

  boolean isSafeModeConfirm();

  boolean isShowConfirmAnimationRunning();

  void setListViewLand();

  void setListViewPort();

  void showMainViewLand();

  void showMainViewPort();

  void startDismissAnimation(boolean z);

  void startDismissConfirmAnimation();

  void startDismissSafeModeAnimation();

  void startSetSafeModeAnimation();

  void startShowAnimation();

  void startShowConfirmAnimation();

  void startShowSafeModeAnimation();

  default void startToastAnimation() {}
}
