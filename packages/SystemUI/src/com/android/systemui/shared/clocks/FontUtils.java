package com.android.systemui.shared.clocks;

import com.android.systemui.animation.AxisDefinition;
import com.android.systemui.plugins.clocks.AxisType;
import com.android.systemui.plugins.clocks.ClockFontAxis;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class FontUtils {
    public static final FontUtils INSTANCE = new FontUtils();

    private FontUtils() {
    }

    public static ClockFontAxis toClockAxis(AxisDefinition axisDefinition, AxisType axisType, Float f, String str, String str2) {
        return new ClockFontAxis(axisDefinition.tag, axisType, axisDefinition.maxValue, axisDefinition.minValue, f.floatValue(), str, str2);
    }
}
