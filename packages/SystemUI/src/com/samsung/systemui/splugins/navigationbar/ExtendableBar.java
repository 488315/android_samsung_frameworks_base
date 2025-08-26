package com.samsung.systemui.splugins.navigationbar;

import android.content.Context;
import android.graphics.drawable.Drawable;

/* loaded from: classes4.dex */
public interface ExtendableBar {
    static /* synthetic */ void updateBackPanelColor$default(ExtendableBar extendableBar, int i, int i2, int i3, int i4, int i5, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: updateBackPanelColor");
        }
        if ((i5 & 1) != 0) {
            i = 0;
        }
        if ((i5 & 2) != 0) {
            i2 = 0;
        }
        if ((i5 & 4) != 0) {
            i3 = 0;
        }
        if ((i5 & 8) != 0) {
            i4 = 0;
        }
        extendableBar.updateBackPanelColor(i, i2, i3, i4);
    }

    void forceSetBackGesture(boolean z);

    int getBarDisplayId();

    BarLayoutParams getBarLayoutParamsProvider();

    ButtonDispatcherProxyBase getButtonDispatcherProxy();

    ColorSetting getDefaultColorProvider();

    IconThemeBase getDefaultIconTheme();

    LayoutProviderContainer getDefaultLayoutProviderContainer();

    int getDisabledFlags();

    FeatureChecker getFeatureChecker();

    NavBarStoreAdapter getNavBarStoreAdapter();

    Context getNavigationBarContext();

    TaskStackAdapterBase getTaskStackAdapter();

    default boolean isCoverDisplayNavBarEnabled() {
        return false;
    }

    default boolean isCoverLargeScreenTaskEnabled() {
        return false;
    }

    boolean isKeyguardShowing();

    default boolean isTaskbarEnabled() {
        return false;
    }

    void registerKeyguardStateCallback();

    void resetScheduleAutoHide();

    void setBarLayoutParamsProvider(BarLayoutParams barLayoutParams);

    void setColorProvider(ColorSetting colorSetting, boolean z);

    void setDefaultBarLayoutParamsProvider();

    void setDefaultIconTheme(IconThemeBase iconThemeBase);

    void setForceShowNavigationBarFlag(Context context, boolean z);

    void setIconThemeAlpha(float f);

    void setLayoutProviderContainer(LayoutProviderContainer layoutProviderContainer);

    void setRotationLockAtAngle(boolean z, int i);

    void setRotationLocked(boolean z);

    void unregisterKeyguardStateCallback();

    void updateOpaqueColor(int i);

    default void notifyForceImmersiveStateChanged() {
    }

    default void updateIconsAndHints(boolean z) {
    }

    default void updateActiveIndicatorSpringParams(float f, float f2) {
    }

    default void updateBackGestureIcon(Drawable drawable, Drawable drawable2) {
    }

    default void updateBackPanelColor(int i, int i2, int i3, int i4) {
    }
}
