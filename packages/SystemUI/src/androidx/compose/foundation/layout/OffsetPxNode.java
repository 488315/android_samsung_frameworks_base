package androidx.compose.foundation.layout;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.LayoutModifierNode;
import androidx.compose.ui.unit.IntOffset;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class OffsetPxNode extends Modifier.Node implements LayoutModifierNode {
    public Function1 offset;
    public boolean rtlAware;

    public OffsetPxNode(Function1 function1, boolean z) {
        this.offset = function1;
        this.rtlAware = z;
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final boolean getShouldAutoInvalidate() {
        return false;
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo4measure3p2s80s(final MeasureScope measureScope, Measurable measurable, long j) {
        MeasureResult layout$1;
        final Placeable mo608measureBRTryo0 = measurable.mo608measureBRTryo0(j);
        layout$1 = measureScope.layout$1(mo608measureBRTryo0.width, mo608measureBRTryo0.height, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.foundation.layout.OffsetPxNode$measure$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj;
                long j2 = ((IntOffset) OffsetPxNode.this.offset.mo779invoke(measureScope)).packedValue;
                if (OffsetPxNode.this.rtlAware) {
                    Placeable.PlacementScope.placeRelativeWithLayer$default(placementScope, mo608measureBRTryo0, (int) (j2 >> 32), (int) (j2 & 4294967295L));
                } else {
                    Placeable.PlacementScope.placeWithLayer$default(placementScope, mo608measureBRTryo0, (int) (j2 >> 32), (int) (j2 & 4294967295L), null, 12);
                }
                return Unit.INSTANCE;
            }
        });
        return layout$1;
    }
}
