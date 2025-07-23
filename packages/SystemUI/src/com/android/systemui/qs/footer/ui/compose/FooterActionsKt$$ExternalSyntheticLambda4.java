package com.android.systemui.qs.footer.ui.compose;

import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.Dp;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class FooterActionsKt$$ExternalSyntheticLambda4 implements Function3 {
    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        MeasureResult layout$1;
        MeasureScope measureScope = (MeasureScope) obj;
        Measurable measurable = (Measurable) obj2;
        Constraints constraints = (Constraints) obj3;
        Dp.Companion companion = Dp.Companion;
        final int mo51roundToPx0680j_4 = measureScope.mo51roundToPx0680j_4(4);
        int i = mo51roundToPx0680j_4 * 2;
        boolean m817getHasBoundedWidthimpl = Constraints.m817getHasBoundedWidthimpl(constraints.value);
        long j = constraints.value;
        final Placeable mo608measureBRTryo0 = measurable.mo608measureBRTryo0(m817getHasBoundedWidthimpl ? Constraints.m814copyZbe2FdA$default(j, 0, Constraints.m821getMaxWidthimpl(j) + i, 0, 0, 13) : j);
        layout$1 = measureScope.layout$1(ConstraintsKt.m832constrainWidthK40F9xA(mo608measureBRTryo0.width - i, j), ConstraintsKt.m831constrainHeightK40F9xA(mo608measureBRTryo0.height, j), MapsKt__MapsKt.emptyMap(), new Function1() { // from class: com.android.systemui.qs.footer.ui.compose.FooterActionsKt$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj4) {
                ((Placeable.PlacementScope) obj4).place(Placeable.this, -mo51roundToPx0680j_4, 0, 0.0f);
                return Unit.INSTANCE;
            }
        });
        return layout$1;
    }
}
