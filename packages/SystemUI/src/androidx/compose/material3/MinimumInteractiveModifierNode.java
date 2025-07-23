package androidx.compose.material3;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode;
import androidx.compose.ui.node.CompositionLocalConsumerModifierNodeKt;
import androidx.compose.ui.node.LayoutModifierNode;
import androidx.compose.ui.unit.Dp;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class MinimumInteractiveModifierNode extends Modifier.Node implements CompositionLocalConsumerModifierNode, LayoutModifierNode {
    @Override // androidx.compose.ui.node.LayoutModifierNode
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo4measure3p2s80s(MeasureScope measureScope, Measurable measurable, long j) {
        MeasureResult layout$1;
        float f = ((Dp) CompositionLocalConsumerModifierNodeKt.currentValueOf(this, InteractiveComponentSizeKt.LocalMinimumInteractiveComponentSize)).value;
        float f2 = 0;
        if (f < f2) {
            f = f2;
        }
        final Placeable mo608measureBRTryo0 = measurable.mo608measureBRTryo0(j);
        boolean z = this.isAttached && !Float.isNaN(f) && Float.compare(f, f2) > 0;
        int mo51roundToPx0680j_4 = Float.isNaN(f) ? 0 : measureScope.mo51roundToPx0680j_4(f);
        final int max = z ? Math.max(mo608measureBRTryo0.width, mo51roundToPx0680j_4) : mo608measureBRTryo0.width;
        final int max2 = z ? Math.max(mo608measureBRTryo0.height, mo51roundToPx0680j_4) : mo608measureBRTryo0.height;
        layout$1 = measureScope.layout$1(max, max2, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.material3.MinimumInteractiveModifierNode$measure$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                ((Placeable.PlacementScope) obj).place(mo608measureBRTryo0, MathKt__MathJVMKt.roundToInt((max - mo608measureBRTryo0.width) / 2.0f), MathKt__MathJVMKt.roundToInt((max2 - mo608measureBRTryo0.height) / 2.0f), 0.0f);
                return Unit.INSTANCE;
            }
        });
        return layout$1;
    }
}
