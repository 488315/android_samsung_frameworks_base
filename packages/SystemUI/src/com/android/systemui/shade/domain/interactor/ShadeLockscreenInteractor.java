package com.android.systemui.shade.domain.interactor;

/* loaded from: classes3.dex */
public interface ShadeLockscreenInteractor {
    void blockExpansionForCurrentTouch();

    void expandToNotifications();

    boolean isExpanded();

    boolean isFullyCollapsed();

    void resetViewGroupFade();

    void resetViews(boolean z);

    void resetViews(boolean z, boolean z2);

    void setKeyguardStatusBarAlpha();

    void setOverStretchAmount(float f);

    void setPulsing(boolean z);

    void showAodUi();

    void transitionToExpandedShade(long j, boolean z);

    default void onDragDownAmountChanged(float f) {
    }

    default void onDismissCancelled() {
    }
}
