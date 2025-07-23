package androidx.compose.foundation.layout;

import androidx.compose.foundation.layout.internal.InlineClassHelperKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.LayoutModifierNode;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.Dp;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class PaddingValuesModifier extends Modifier.Node implements LayoutModifierNode {
    public PaddingValues paddingValues;

    public PaddingValuesModifier(PaddingValues paddingValues) {
        this.paddingValues = paddingValues;
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo4measure3p2s80s(final MeasureScope measureScope, Measurable measurable, long j) {
        MeasureResult layout$1;
        float mo110calculateLeftPaddingu2uoSUM = this.paddingValues.mo110calculateLeftPaddingu2uoSUM(measureScope.getLayoutDirection());
        float f = 0;
        Dp.Companion companion = Dp.Companion;
        if (Float.compare(mo110calculateLeftPaddingu2uoSUM, f) < 0 || Float.compare(this.paddingValues.mo112calculateTopPaddingD9Ej5fM(), f) < 0 || Float.compare(this.paddingValues.mo111calculateRightPaddingu2uoSUM(measureScope.getLayoutDirection()), f) < 0 || Float.compare(this.paddingValues.mo109calculateBottomPaddingD9Ej5fM(), f) < 0) {
            InlineClassHelperKt.throwIllegalArgumentException("Padding must be non-negative");
        }
        int mo51roundToPx0680j_4 = measureScope.mo51roundToPx0680j_4(this.paddingValues.mo111calculateRightPaddingu2uoSUM(measureScope.getLayoutDirection())) + measureScope.mo51roundToPx0680j_4(this.paddingValues.mo110calculateLeftPaddingu2uoSUM(measureScope.getLayoutDirection()));
        int mo51roundToPx0680j_42 = measureScope.mo51roundToPx0680j_4(this.paddingValues.mo109calculateBottomPaddingD9Ej5fM()) + measureScope.mo51roundToPx0680j_4(this.paddingValues.mo112calculateTopPaddingD9Ej5fM());
        final Placeable mo608measureBRTryo0 = measurable.mo608measureBRTryo0(ConstraintsKt.m833offsetNN6EwU(-mo51roundToPx0680j_4, -mo51roundToPx0680j_42, j));
        layout$1 = measureScope.layout$1(ConstraintsKt.m832constrainWidthK40F9xA(mo608measureBRTryo0.width + mo51roundToPx0680j_4, j), ConstraintsKt.m831constrainHeightK40F9xA(mo608measureBRTryo0.height + mo51roundToPx0680j_42, j), MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.foundation.layout.PaddingValuesModifier$measure$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Placeable placeable = Placeable.this;
                MeasureScope measureScope2 = measureScope;
                ((Placeable.PlacementScope) obj).place(placeable, measureScope2.mo51roundToPx0680j_4(this.paddingValues.mo110calculateLeftPaddingu2uoSUM(measureScope2.getLayoutDirection())), measureScope.mo51roundToPx0680j_4(this.paddingValues.mo112calculateTopPaddingD9Ej5fM()), 0.0f);
                return Unit.INSTANCE;
            }
        });
        return layout$1;
    }
}
