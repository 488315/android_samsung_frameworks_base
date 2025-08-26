package com.android.systemui.qs.footer.ui.compose;

import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.Dp;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;

/* loaded from: classes2.dex */
public final /* synthetic */ class FooterActionsKt$$ExternalSyntheticLambda4 implements Function3 {
    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        MeasureScope measureScope = (MeasureScope) obj;
        Measurable measurable = (Measurable) obj2;
        Constraints constraints = (Constraints) obj3;
        Dp.Companion companion = Dp.Companion;
        final int iMo52roundToPx0680j_4 = measureScope.mo52roundToPx0680j_4(4);
        int i = iMo52roundToPx0680j_4 * 2;
        boolean zM819getHasBoundedWidthimpl = Constraints.m819getHasBoundedWidthimpl(constraints.value);
        long j = constraints.value;
        final Placeable placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(zM819getHasBoundedWidthimpl ? Constraints.m816copyZbe2FdA$default(j, 0, Constraints.m823getMaxWidthimpl(j) + i, 0, 0, 13) : j);
        return measureScope.layout$1(ConstraintsKt.m834constrainWidthK40F9xA(placeableMo610measureBRTryo0.width - i, j), ConstraintsKt.m833constrainHeightK40F9xA(placeableMo610measureBRTryo0.height, j), MapsKt__MapsKt.emptyMap(), new Function1() { // from class: com.android.systemui.qs.footer.ui.compose.FooterActionsKt$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj4) {
                ((Placeable.PlacementScope) obj4).place(placeableMo610measureBRTryo0, -iMo52roundToPx0680j_4, 0, 0.0f);
                return Unit.INSTANCE;
            }
        });
    }
}
