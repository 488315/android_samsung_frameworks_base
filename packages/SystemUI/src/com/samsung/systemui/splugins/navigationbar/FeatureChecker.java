package com.samsung.systemui.splugins.navigationbar;

/* loaded from: classes4.dex */
public interface FeatureChecker {
    boolean isDeviceSupportLargeCoverScreen();

    boolean isDeviceSupportTaskbar();

    boolean isFoldableTypeFlip();

    boolean isFoldableTypeFold();

    default boolean isNavigationBarMigrated() {
        return false;
    }

    boolean isOpenThemeSupported();

    default boolean isSupportAssistantBySideButton() {
        return false;
    }

    default boolean isSupportSearcle() {
        return false;
    }

    boolean isTablet();
}
