package androidx.compose.foundation.layout;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.LayoutModifierNode;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class OffsetNode extends Modifier.Node implements LayoutModifierNode {
    public boolean rtlAware;
    public float x;
    public float y;

    public /* synthetic */ OffsetNode(float f, float f2, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, f2, z);
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
        layout$1 = measureScope.layout$1(mo608measureBRTryo0.width, mo608measureBRTryo0.height, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.foundation.layout.OffsetNode$measure$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj;
                OffsetNode offsetNode = OffsetNode.this;
                if (offsetNode.rtlAware) {
                    placementScope.placeRelative(mo608measureBRTryo0, measureScope.mo51roundToPx0680j_4(offsetNode.x), measureScope.mo51roundToPx0680j_4(OffsetNode.this.y), 0.0f);
                } else {
                    placementScope.place(mo608measureBRTryo0, measureScope.mo51roundToPx0680j_4(offsetNode.x), measureScope.mo51roundToPx0680j_4(OffsetNode.this.y), 0.0f);
                }
                return Unit.INSTANCE;
            }
        });
        return layout$1;
    }

    private OffsetNode(float f, float f2, boolean z) {
        this.x = f;
        this.y = f2;
        this.rtlAware = z;
    }
}
