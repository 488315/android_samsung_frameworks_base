package com.android.systemui.navigationbar.plugin;

import com.android.systemui.BasicRune;
import com.android.systemui.util.DeviceType;
import com.samsung.systemui.splugins.navigationbar.FeatureChecker;

public final class BasicRuneFeatureChecker implements FeatureChecker {
    @Override // com.samsung.systemui.splugins.navigationbar.FeatureChecker
    public final boolean isDeviceSupportLargeCoverScreen() {
        return BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.FeatureChecker
    public final boolean isDeviceSupportTaskbar() {
        return BasicRune.NAVBAR_TASKBAR;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.FeatureChecker
    public final boolean isFoldableTypeFlip() {
        return BasicRune.BASIC_FOLDABLE_TYPE_FLIP;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.FeatureChecker
    public final boolean isFoldableTypeFold() {
        return BasicRune.BASIC_FOLDABLE_TYPE_FOLD;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.FeatureChecker
    public final boolean isNavigationBarMigrated() {
        return BasicRune.NAVBAR_SIMPLIFIED_GESTURE;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.FeatureChecker
    public final boolean isOpenThemeSupported() {
        return BasicRune.NAVBAR_OPEN_THEME;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.FeatureChecker
    public final boolean isSupportSearcle() {
        return BasicRune.NAVBAR_SUPPORT_SEARCLE;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.FeatureChecker
    public final boolean isTablet() {
        return DeviceType.isTablet();
    }
}
