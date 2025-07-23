package androidx.compose.foundation.layout;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.IntrinsicMeasurable;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.LayoutModifierNode;
import androidx.compose.ui.node.LookaheadCapablePlaceable;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
abstract class IntrinsicSizeModifier extends Modifier.Node implements LayoutModifierNode {
    /* renamed from: calculateContentConstraints-l58MMJ0 */
    public abstract long mo113calculateContentConstraintsl58MMJ0(Measurable measurable, long j);

    public abstract boolean getEnforceIncoming();

    public int maxIntrinsicHeight(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        return intrinsicMeasurable.maxIntrinsicHeight(i);
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public int maxIntrinsicWidth(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        return intrinsicMeasurable.maxIntrinsicWidth(i);
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo4measure3p2s80s(MeasureScope measureScope, Measurable measurable, long j) {
        MeasureResult layout$1;
        long mo113calculateContentConstraintsl58MMJ0 = mo113calculateContentConstraintsl58MMJ0(measurable, j);
        if (getEnforceIncoming()) {
            mo113calculateContentConstraintsl58MMJ0 = ConstraintsKt.m830constrainN9IONVI(j, mo113calculateContentConstraintsl58MMJ0);
        }
        final Placeable mo608measureBRTryo0 = measurable.mo608measureBRTryo0(mo113calculateContentConstraintsl58MMJ0);
        layout$1 = measureScope.layout$1(mo608measureBRTryo0.width, mo608measureBRTryo0.height, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.foundation.layout.IntrinsicSizeModifier$measure$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj;
                Placeable placeable = Placeable.this;
                IntOffset.Companion.getClass();
                if (placementScope.getParentLayoutDirection() == LayoutDirection.Ltr || placementScope.getParentWidth() == 0) {
                    Placeable.PlacementScope.access$handleMotionFrameOfReferencePlacement(placementScope, placeable);
                    placeable.mo609placeAtf8xVGno(IntOffset.m851plusqkQi6aY(0L, placeable.apparentToRealOffset), 0.0f, (Function1) null);
                } else {
                    int parentWidth = placementScope.getParentWidth() - placeable.width;
                    IntOffset.Companion companion = IntOffset.Companion;
                    Placeable.PlacementScope.access$handleMotionFrameOfReferencePlacement(placementScope, placeable);
                    placeable.mo609placeAtf8xVGno(IntOffset.m851plusqkQi6aY(((parentWidth - ((int) (0 >> 32))) << 32) | (4294967295L & ((int) (0 & 4294967295L))), placeable.apparentToRealOffset), 0.0f, (Function1) null);
                }
                return Unit.INSTANCE;
            }
        });
        return layout$1;
    }

    public int minIntrinsicHeight(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        return intrinsicMeasurable.minIntrinsicHeight(i);
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public int minIntrinsicWidth(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        return intrinsicMeasurable.minIntrinsicWidth(i);
    }
}
