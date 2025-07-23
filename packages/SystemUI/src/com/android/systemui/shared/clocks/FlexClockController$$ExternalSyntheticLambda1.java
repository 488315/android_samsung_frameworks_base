package com.android.systemui.shared.clocks;

import com.android.systemui.animation.AxisDefinition;
import com.android.systemui.animation.GSFAxes;
import com.android.systemui.plugins.clocks.ClockAxisStyle;
import com.android.systemui.shared.clocks.FlexClockController;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class FlexClockController$$ExternalSyntheticLambda1 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        ClockAxisStyle clockAxisStyle = (ClockAxisStyle) obj;
        FlexClockController.Companion companion = FlexClockController.Companion;
        FontUtils fontUtils = FontUtils.INSTANCE;
        AxisDefinition axisDefinition = GSFAxes.WEIGHT;
        fontUtils.getClass();
        clockAxisStyle.put(axisDefinition.tag, 600.0f);
        GSFAxes.INSTANCE.getClass();
        clockAxisStyle.put(GSFAxes.WIDTH.tag, 100.0f);
        clockAxisStyle.put(GSFAxes.ROUND.tag, 100.0f);
        clockAxisStyle.put(GSFAxes.SLANT.tag, 0.0f);
        return Unit.INSTANCE;
    }
}
