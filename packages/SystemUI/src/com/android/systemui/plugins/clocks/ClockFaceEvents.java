package com.android.systemui.plugins.clocks;

import android.graphics.Rect;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface ClockFaceEvents {
    void onFontSettingChanged(float f);

    void onRegionDarknessChanged(boolean z);

    void onSecondaryDisplayChanged(boolean z);

    void onTargetRegionChanged(Rect rect);

    void onTimeTick();
}
