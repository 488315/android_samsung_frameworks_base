package androidx.compose.ui.graphics;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.LayoutModifierNode;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BlockGraphicsLayerModifier extends Modifier.Node implements LayoutModifierNode {
    public Function1 layerBlock;

    public BlockGraphicsLayerModifier(Function1 function1) {
        this.layerBlock = function1;
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final boolean getShouldAutoInvalidate() {
        return false;
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo4measure3p2s80s(MeasureScope measureScope, Measurable measurable, long j) {
        MeasureResult layout$1;
        final Placeable mo608measureBRTryo0 = measurable.mo608measureBRTryo0(j);
        layout$1 = measureScope.layout$1(mo608measureBRTryo0.width, mo608measureBRTryo0.height, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.ui.graphics.BlockGraphicsLayerModifier$measure$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Placeable.PlacementScope.placeWithLayer$default((Placeable.PlacementScope) obj, Placeable.this, 0, 0, this.layerBlock, 4);
                return Unit.INSTANCE;
            }
        });
        return layout$1;
    }

    public final String toString() {
        return "BlockGraphicsLayerModifier(block=" + this.layerBlock + ')';
    }
}
