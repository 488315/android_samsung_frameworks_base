package com.android.systemui.shade.domain.interactor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
