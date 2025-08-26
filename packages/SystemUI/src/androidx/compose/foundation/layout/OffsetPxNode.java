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
        final Placeable placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(j);
        return measureScope.layout$1(placeableMo610measureBRTryo0.width, placeableMo610measureBRTryo0.height, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.foundation.layout.OffsetPxNode$measure$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj;
                long j2 = ((IntOffset) this.this$0.offset.mo781invoke(measureScope)).packedValue;
                if (this.this$0.rtlAware) {
                    Placeable.PlacementScope.placeRelativeWithLayer$default(placementScope, placeableMo610measureBRTryo0, (int) (j2 >> 32), (int) (j2 & 4294967295L));
                } else {
                    Placeable.PlacementScope.placeWithLayer$default(placementScope, placeableMo610measureBRTryo0, (int) (j2 >> 32), (int) (j2 & 4294967295L), null, 12);
                }
                return Unit.INSTANCE;
            }
        });
    }
}
