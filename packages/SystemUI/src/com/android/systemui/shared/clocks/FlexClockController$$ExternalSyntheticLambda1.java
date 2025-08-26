package com.android.systemui.shared.clocks;

import com.android.systemui.animation.AxisDefinition;
import com.android.systemui.animation.GSFAxes;
import com.android.systemui.plugins.clocks.ClockAxisStyle;
import com.android.systemui.shared.clocks.FlexClockController;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class FlexClockController$$ExternalSyntheticLambda1 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
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
