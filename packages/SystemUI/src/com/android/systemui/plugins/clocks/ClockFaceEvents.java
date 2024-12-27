package com.android.systemui.plugins.clocks;

import android.graphics.Rect;

public interface ClockFaceEvents {
    void onFontSettingChanged(float f);

    void onRegionDarknessChanged(boolean z);

    void onSecondaryDisplayChanged(boolean z);

    void onTargetRegionChanged(Rect rect);

    void onTimeTick();
}
