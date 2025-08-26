package com.samsung.systemui.splugins.navigationbar;

/* loaded from: classes4.dex */
public interface ColorSetting {
    void addColorCallback(Runnable runnable);

    int getNavigationBarColor();

    void setNavigationBarColor(int i);

    default void onSettingChanged(int i) {
    }
}
