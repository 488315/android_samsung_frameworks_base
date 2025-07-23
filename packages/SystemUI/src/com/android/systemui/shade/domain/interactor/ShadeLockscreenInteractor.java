package com.android.systemui.shade.domain.interactor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
