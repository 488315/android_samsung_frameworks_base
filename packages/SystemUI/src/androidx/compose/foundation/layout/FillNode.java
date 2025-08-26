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
        int iM825getMinWidthimpl;
        int iM823getMaxWidthimpl;
        int iM824getMinHeightimpl;
        int iM822getMaxHeightimpl;
        if (!Constraints.m819getHasBoundedWidthimpl(j) || this.direction == Direction.Vertical) {
            iM825getMinWidthimpl = Constraints.m825getMinWidthimpl(j);
            iM823getMaxWidthimpl = Constraints.m823getMaxWidthimpl(j);
        } else {
            iM825getMinWidthimpl = RangesKt___RangesKt.coerceIn(Math.round(Constraints.m823getMaxWidthimpl(j) * this.fraction), Constraints.m825getMinWidthimpl(j), Constraints.m823getMaxWidthimpl(j));
            iM823getMaxWidthimpl = iM825getMinWidthimpl;
        }
        if (!Constraints.m818getHasBoundedHeightimpl(j) || this.direction == Direction.Horizontal) {
            iM824getMinHeightimpl = Constraints.m824getMinHeightimpl(j);
            iM822getMaxHeightimpl = Constraints.m822getMaxHeightimpl(j);
        } else {
            iM824getMinHeightimpl = RangesKt___RangesKt.coerceIn(Math.round(Constraints.m822getMaxHeightimpl(j) * this.fraction), Constraints.m824getMinHeightimpl(j), Constraints.m822getMaxHeightimpl(j));
            iM822getMaxHeightimpl = iM824getMinHeightimpl;
        }
        final Placeable placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(ConstraintsKt.Constraints(iM825getMinWidthimpl, iM823getMaxWidthimpl, iM824getMinHeightimpl, iM822getMaxHeightimpl));
        return measureScope.layout$1(placeableMo610measureBRTryo0.width, placeableMo610measureBRTryo0.height, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.foundation.layout.FillNode$measure$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                ((Placeable.PlacementScope) obj).placeRelative(placeableMo610measureBRTryo0, 0, 0, 0.0f);
                return Unit.INSTANCE;
            }
        });
    }
}
