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
        Direction direction = this.direction;
        Direction direction2 = Direction.Vertical;
        int iM825getMinWidthimpl = direction != direction2 ? 0 : Constraints.m825getMinWidthimpl(j);
        Direction direction3 = this.direction;
        Direction direction4 = Direction.Horizontal;
        final Placeable placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(ConstraintsKt.Constraints(iM825getMinWidthimpl, (this.direction == direction2 || !this.unbounded) ? Constraints.m823getMaxWidthimpl(j) : Integer.MAX_VALUE, direction3 == direction4 ? Constraints.m824getMinHeightimpl(j) : 0, (this.direction == direction4 || !this.unbounded) ? Constraints.m822getMaxHeightimpl(j) : Integer.MAX_VALUE));
        final int iCoerceIn = RangesKt___RangesKt.coerceIn(placeableMo610measureBRTryo0.width, Constraints.m825getMinWidthimpl(j), Constraints.m823getMaxWidthimpl(j));
        final int iCoerceIn2 = RangesKt___RangesKt.coerceIn(placeableMo610measureBRTryo0.height, Constraints.m824getMinHeightimpl(j), Constraints.m822getMaxHeightimpl(j));
        return measureScope.layout$1(iCoerceIn, iCoerceIn2, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.foundation.layout.WrapContentNode$measure$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Function2 function2 = this.this$0.alignmentCallback;
                int i = iCoerceIn;
                Placeable placeable = placeableMo610measureBRTryo0;
                Placeable.PlacementScope.m628place70tqf50$default((Placeable.PlacementScope) obj, placeableMo610measureBRTryo0, ((IntOffset) function2.invoke(IntSize.m861boximpl(((i - placeable.width) << 32) | ((iCoerceIn2 - placeable.height) & 4294967295L)), measureScope.getLayoutDirection())).packedValue);
                return Unit.INSTANCE;
            }
        });
    }
}
