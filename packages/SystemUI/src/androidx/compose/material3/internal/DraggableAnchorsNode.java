package androidx.compose.material3.internal;

import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.LayoutModifierNode;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.IntSizeKt;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class DraggableAnchorsNode<T> extends Modifier.Node implements LayoutModifierNode {
    public Function2 anchors;
    public boolean didLookahead;
    public Orientation orientation;
    public AnchoredDraggableState state;

    public DraggableAnchorsNode(AnchoredDraggableState<T> anchoredDraggableState, Function2 function2, Orientation orientation) {
        this.state = anchoredDraggableState;
        this.anchors = function2;
        this.orientation = orientation;
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo4measure3p2s80s(final MeasureScope measureScope, Measurable measurable, long j) {
        MeasureResult layout$1;
        final Placeable mo608measureBRTryo0 = measurable.mo608measureBRTryo0(j);
        if (!measureScope.isLookingAhead() || !this.didLookahead) {
            Pair pair = (Pair) this.anchors.invoke(IntSize.m859boximpl(IntSizeKt.IntSize(mo608measureBRTryo0.width, mo608measureBRTryo0.height)), Constraints.m813boximpl(j));
            AnchoredDraggableState anchoredDraggableState = this.state;
            DraggableAnchors draggableAnchors = (DraggableAnchors) pair.getFirst();
            Object second = pair.getSecond();
            if (!Intrinsics.areEqual(anchoredDraggableState.getAnchors(), draggableAnchors)) {
                ((SnapshotMutableStateImpl) anchoredDraggableState.anchors$delegate).setValue(draggableAnchors);
                if (!anchoredDraggableState.trySnapTo(second)) {
                    anchoredDraggableState.setDragTarget(second);
                }
            }
        }
        this.didLookahead = measureScope.isLookingAhead() || this.didLookahead;
        layout$1 = measureScope.layout$1(mo608measureBRTryo0.width, mo608measureBRTryo0.height, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.material3.internal.DraggableAnchorsNode$measure$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj;
                final float positionOf = MeasureScope.this.isLookingAhead() ? ((MapDraggableAnchors) this.state.getAnchors()).positionOf(this.state.targetValue$delegate.getValue()) : this.state.requireOffset();
                Orientation orientation = this.orientation;
                final float f = orientation == Orientation.Horizontal ? positionOf : 0.0f;
                if (orientation != Orientation.Vertical) {
                    positionOf = 0.0f;
                }
                final Placeable placeable = mo608measureBRTryo0;
                Function1 function1 = new Function1() { // from class: androidx.compose.material3.internal.DraggableAnchorsNode$measure$1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj2) {
                        ((Placeable.PlacementScope) obj2).place(Placeable.this, MathKt__MathJVMKt.roundToInt(f), MathKt__MathJVMKt.roundToInt(positionOf), 0.0f);
                        return Unit.INSTANCE;
                    }
                };
                placementScope.motionFrameOfReferencePlacement = true;
                function1.mo779invoke(placementScope);
                placementScope.motionFrameOfReferencePlacement = false;
                return Unit.INSTANCE;
            }
        });
        return layout$1;
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onDetach() {
        this.didLookahead = false;
    }
}
