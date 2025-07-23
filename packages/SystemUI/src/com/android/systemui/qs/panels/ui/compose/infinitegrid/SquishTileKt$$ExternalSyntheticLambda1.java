package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import androidx.compose.ui.layout.ApproachMeasureScope;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.Placeable;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class SquishTileKt$$ExternalSyntheticLambda1 implements Function3 {
    public final /* synthetic */ Function0 f$0;

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        MeasureResult layout$1;
        ApproachMeasureScope approachMeasureScope = (ApproachMeasureScope) obj;
        float floatValue = ((Number) this.f$0.invoke()).floatValue();
        int mo602getLookaheadSizeYbymL2g = (int) (approachMeasureScope.mo602getLookaheadSizeYbymL2g() & 4294967295L);
        final Placeable mo608measureBRTryo0 = ((Measurable) obj2).mo608measureBRTryo0(approachMeasureScope.mo601getLookaheadConstraintsmsEJaDk());
        int roundToInt = MathKt__MathJVMKt.roundToInt(mo602getLookaheadSizeYbymL2g * floatValue);
        final int i = (roundToInt - mo602getLookaheadSizeYbymL2g) / 2;
        layout$1 = approachMeasureScope.layout$1(mo608measureBRTryo0.width, roundToInt, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.SquishTileKt$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj4) {
                ((Placeable.PlacementScope) obj4).place(Placeable.this, 0, i, 0.0f);
                return Unit.INSTANCE;
            }
        });
        return layout$1;
    }
}
