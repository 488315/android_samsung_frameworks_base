package com.android.systemui.shared.clocks;

import com.android.systemui.animation.AxisDefinition;
import com.android.systemui.plugins.clocks.AxisType;
import com.android.systemui.plugins.clocks.ClockFontAxis;

/* loaded from: classes3.dex */
public final class FontUtils {
    public static final FontUtils INSTANCE = new FontUtils();

    private FontUtils() {
    }

    public static ClockFontAxis toClockAxis(AxisDefinition axisDefinition, AxisType axisType, Float f, String str, String str2) {
        return new ClockFontAxis(axisDefinition.tag, axisType, axisDefinition.maxValue, axisDefinition.minValue, f.floatValue(), str, str2);
    }
}
