package com.android.systemui.plugins.clocks;

import android.graphics.Rect;

/* loaded from: classes2.dex */
public interface ClockFaceEvents {
    void onFontSettingChanged(float f);

    void onSecondaryDisplayChanged(boolean z);

    void onTargetRegionChanged(Rect rect);

    void onThemeChanged(ThemeConfig themeConfig);

    void onTimeTick();
}
