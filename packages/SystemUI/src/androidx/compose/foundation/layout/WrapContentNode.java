package androidx.compose.foundation.layout;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.LayoutModifierNode;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntSize;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.ranges.RangesKt___RangesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class WrapContentNode extends Modifier.Node implements LayoutModifierNode {
    public Function2 alignmentCallback;
    public Direction direction;
    public boolean unbounded;

    public WrapContentNode(Direction direction, boolean z, Function2 function2) {
        this.direction = direction;
        this.unbounded = z;
        this.alignmentCallback = function2;
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo4measure3p2s80s(final MeasureScope measureScope, Measurable measurable, long j) {
        MeasureResult layout$1;
        Direction direction = this.direction;
        Direction direction2 = Direction.Vertical;
        int m823getMinWidthimpl = direction != direction2 ? 0 : Constraints.m823getMinWidthimpl(j);
        Direction direction3 = this.direction;
        Direction direction4 = Direction.Horizontal;
        final Placeable mo608measureBRTryo0 = measurable.mo608measureBRTryo0(ConstraintsKt.Constraints(m823getMinWidthimpl, (this.direction == direction2 || !this.unbounded) ? Constraints.m821getMaxWidthimpl(j) : Integer.MAX_VALUE, direction3 == direction4 ? Constraints.m822getMinHeightimpl(j) : 0, (this.direction == direction4 || !this.unbounded) ? Constraints.m820getMaxHeightimpl(j) : Integer.MAX_VALUE));
        final int coerceIn = RangesKt___RangesKt.coerceIn(mo608measureBRTryo0.width, Constraints.m823getMinWidthimpl(j), Constraints.m821getMaxWidthimpl(j));
        final int coerceIn2 = RangesKt___RangesKt.coerceIn(mo608measureBRTryo0.height, Constraints.m822getMinHeightimpl(j), Constraints.m820getMaxHeightimpl(j));
        layout$1 = measureScope.layout$1(coerceIn, coerceIn2, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.foundation.layout.WrapContentNode$measure$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Function2 function2 = WrapContentNode.this.alignmentCallback;
                int i = coerceIn;
                Placeable placeable = mo608measureBRTryo0;
                Placeable.PlacementScope.m626place70tqf50$default((Placeable.PlacementScope) obj, mo608measureBRTryo0, ((IntOffset) function2.invoke(IntSize.m859boximpl(((i - placeable.width) << 32) | ((coerceIn2 - placeable.height) & 4294967295L)), measureScope.getLayoutDirection())).packedValue);
                return Unit.INSTANCE;
            }
        });
        return layout$1;
    }
}
