package com.android.systemui.shade.domain.interactor;

public interface PanelExpansionInteractor {
    int getBarState();

    boolean isCollapsing();

    boolean isFullyCollapsed();

    boolean isFullyExpanded();

    boolean isPanelExpanded();

    boolean isTracking();

    boolean shouldHideStatusBarIconsWhenExpanded();
}
