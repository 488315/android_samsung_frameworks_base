package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import androidx.compose.ui.layout.ApproachMeasureScope;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.Placeable;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.math.MathKt__MathJVMKt;

/* loaded from: classes2.dex */
public final /* synthetic */ class SquishTileKt$$ExternalSyntheticLambda1 implements Function3 {
    public final /* synthetic */ Function0 f$0;

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        ApproachMeasureScope approachMeasureScope = (ApproachMeasureScope) obj;
        float fFloatValue = ((Number) this.f$0.invoke()).floatValue();
        int iMo604getLookaheadSizeYbymL2g = (int) (approachMeasureScope.mo604getLookaheadSizeYbymL2g() & 4294967295L);
        final Placeable placeableMo610measureBRTryo0 = ((Measurable) obj2).mo610measureBRTryo0(approachMeasureScope.mo603getLookaheadConstraintsmsEJaDk());
        int iRoundToInt = MathKt__MathJVMKt.roundToInt(iMo604getLookaheadSizeYbymL2g * fFloatValue);
        final int i = (iRoundToInt - iMo604getLookaheadSizeYbymL2g) / 2;
        return approachMeasureScope.layout$1(placeableMo610measureBRTryo0.width, iRoundToInt, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.SquishTileKt$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj4) {
                ((Placeable.PlacementScope) obj4).place(placeableMo610measureBRTryo0, 0, i, 0.0f);
                return Unit.INSTANCE;
            }
        });
    }
}
