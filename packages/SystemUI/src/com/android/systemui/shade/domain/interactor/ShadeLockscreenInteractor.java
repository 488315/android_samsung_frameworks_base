package com.android.systemui.shade.domain.interactor;

public interface ShadeLockscreenInteractor {
    void blockExpansionForCurrentTouch();

    void dozeTimeTick();

    void expandToNotifications();

    boolean isExpanded();

    boolean isFullyCollapsed();

    void resetViewGroupFade();

    void resetViews(boolean z);

    void resetViews(boolean z, boolean z2);

    void setKeyguardStatusBarAlpha();

    void setKeyguardTransitionProgress(float f);

    void setOverStretchAmount(float f);

    void setPulsing(boolean z);

    void showAodUi();

    void startBouncerPreHideAnimation();

    void transitionToExpandedShade(long j, boolean z);

    default void onDismissCancelled() {
    }

    default void onDragDownAmountChanged(float f) {
    }
}
