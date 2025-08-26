package com.android.systemui.shade.domain.interactor;

/* loaded from: classes3.dex */
public interface PanelExpansionInteractor {
    int getBarState();

    boolean isCollapsing();

    boolean isFullyCollapsed();

    boolean isFullyExpanded();

    boolean isPanelExpanded();

    boolean isTracking();

    boolean shouldHideStatusBarIconsWhenExpanded();
}
