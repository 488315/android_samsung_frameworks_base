package androidx.compose.foundation.layout;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.LayoutModifierNode;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.ranges.RangesKt___RangesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class FillNode extends Modifier.Node implements LayoutModifierNode {
    public Direction direction;
    public float fraction;

    public FillNode(Direction direction, float f) {
        this.direction = direction;
        this.fraction = f;
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo4measure3p2s80s(MeasureScope measureScope, Measurable measurable, long j) {
        int m823getMinWidthimpl;
        int m821getMaxWidthimpl;
        int m822getMinHeightimpl;
        int m820getMaxHeightimpl;
        MeasureResult layout$1;
        if (!Constraints.m817getHasBoundedWidthimpl(j) || this.direction == Direction.Vertical) {
            m823getMinWidthimpl = Constraints.m823getMinWidthimpl(j);
            m821getMaxWidthimpl = Constraints.m821getMaxWidthimpl(j);
        } else {
            m823getMinWidthimpl = RangesKt___RangesKt.coerceIn(Math.round(Constraints.m821getMaxWidthimpl(j) * this.fraction), Constraints.m823getMinWidthimpl(j), Constraints.m821getMaxWidthimpl(j));
            m821getMaxWidthimpl = m823getMinWidthimpl;
        }
        if (!Constraints.m816getHasBoundedHeightimpl(j) || this.direction == Direction.Horizontal) {
            m822getMinHeightimpl = Constraints.m822getMinHeightimpl(j);
            m820getMaxHeightimpl = Constraints.m820getMaxHeightimpl(j);
        } else {
            m822getMinHeightimpl = RangesKt___RangesKt.coerceIn(Math.round(Constraints.m820getMaxHeightimpl(j) * this.fraction), Constraints.m822getMinHeightimpl(j), Constraints.m820getMaxHeightimpl(j));
            m820getMaxHeightimpl = m822getMinHeightimpl;
        }
        final Placeable mo608measureBRTryo0 = measurable.mo608measureBRTryo0(ConstraintsKt.Constraints(m823getMinWidthimpl, m821getMaxWidthimpl, m822getMinHeightimpl, m820getMaxHeightimpl));
        layout$1 = measureScope.layout$1(mo608measureBRTryo0.width, mo608measureBRTryo0.height, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.foundation.layout.FillNode$measure$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                ((Placeable.PlacementScope) obj).placeRelative(Placeable.this, 0, 0, 0.0f);
                return Unit.INSTANCE;
            }
        });
        return layout$1;
    }
}
