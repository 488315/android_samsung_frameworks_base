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

/* loaded from: classes.dex */
final class PaddingValuesModifier extends Modifier.Node implements LayoutModifierNode {
    public PaddingValues paddingValues;

    public PaddingValuesModifier(PaddingValues paddingValues) {
        this.paddingValues = paddingValues;
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo4measure3p2s80s(final MeasureScope measureScope, Measurable measurable, long j) {
        float fMo111calculateLeftPaddingu2uoSUM = this.paddingValues.mo111calculateLeftPaddingu2uoSUM(measureScope.getLayoutDirection());
        float f = 0;
        Dp.Companion companion = Dp.Companion;
        if (Float.compare(fMo111calculateLeftPaddingu2uoSUM, f) < 0 || Float.compare(this.paddingValues.mo113calculateTopPaddingD9Ej5fM(), f) < 0 || Float.compare(this.paddingValues.mo112calculateRightPaddingu2uoSUM(measureScope.getLayoutDirection()), f) < 0 || Float.compare(this.paddingValues.mo110calculateBottomPaddingD9Ej5fM(), f) < 0) {
            InlineClassHelperKt.throwIllegalArgumentException("Padding must be non-negative");
        }
        int iMo52roundToPx0680j_4 = measureScope.mo52roundToPx0680j_4(this.paddingValues.mo112calculateRightPaddingu2uoSUM(measureScope.getLayoutDirection())) + measureScope.mo52roundToPx0680j_4(this.paddingValues.mo111calculateLeftPaddingu2uoSUM(measureScope.getLayoutDirection()));
        int iMo52roundToPx0680j_42 = measureScope.mo52roundToPx0680j_4(this.paddingValues.mo110calculateBottomPaddingD9Ej5fM()) + measureScope.mo52roundToPx0680j_4(this.paddingValues.mo113calculateTopPaddingD9Ej5fM());
        final Placeable placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(ConstraintsKt.m835offsetNN6EwU(-iMo52roundToPx0680j_4, -iMo52roundToPx0680j_42, j));
        return measureScope.layout$1(ConstraintsKt.m834constrainWidthK40F9xA(placeableMo610measureBRTryo0.width + iMo52roundToPx0680j_4, j), ConstraintsKt.m833constrainHeightK40F9xA(placeableMo610measureBRTryo0.height + iMo52roundToPx0680j_42, j), MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.foundation.layout.PaddingValuesModifier$measure$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Placeable placeable = placeableMo610measureBRTryo0;
                MeasureScope measureScope2 = measureScope;
                ((Placeable.PlacementScope) obj).place(placeable, measureScope2.mo52roundToPx0680j_4(this.paddingValues.mo111calculateLeftPaddingu2uoSUM(measureScope2.getLayoutDirection())), measureScope.mo52roundToPx0680j_4(this.paddingValues.mo113calculateTopPaddingD9Ej5fM()), 0.0f);
                return Unit.INSTANCE;
            }
        });
    }
}
