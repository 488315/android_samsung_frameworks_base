package com.samsung.systemui.splugins.navigationbar;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
