package com.android.systemui.plugins.clocks;

import android.graphics.Rect;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface ClockFaceEvents {
    void onFontSettingChanged(float f);

    void onSecondaryDisplayChanged(boolean z);

    void onTargetRegionChanged(Rect rect);

    void onThemeChanged(ThemeConfig themeConfig);

    void onTimeTick();
}
