package androidx.compose.foundation.layout;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.AlignmentLine;
import androidx.compose.ui.layout.HorizontalAlignmentLine;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.LayoutModifierNode;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.Dp;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.RangesKt___RangesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AlignmentLineOffsetDpNode extends Modifier.Node implements LayoutModifierNode {
    public float after;
    public AlignmentLine alignmentLine;
    public float before;

    public /* synthetic */ AlignmentLineOffsetDpNode(AlignmentLine alignmentLine, float f, float f2, DefaultConstructorMarker defaultConstructorMarker) {
        this(alignmentLine, f, f2);
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo4measure3p2s80s(MeasureScope measureScope, Measurable measurable, long j) {
        long j2;
        long m814copyZbe2FdA$default;
        MeasureResult layout$1;
        final AlignmentLine alignmentLine = this.alignmentLine;
        final float f = this.before;
        float f2 = this.after;
        boolean z = alignmentLine instanceof HorizontalAlignmentLine;
        if (z) {
            j2 = j;
            m814copyZbe2FdA$default = Constraints.m814copyZbe2FdA$default(j2, 0, 0, 0, 0, 11);
        } else {
            j2 = j;
            m814copyZbe2FdA$default = Constraints.m814copyZbe2FdA$default(j2, 0, 0, 0, 0, 14);
        }
        final Placeable mo608measureBRTryo0 = measurable.mo608measureBRTryo0(m814copyZbe2FdA$default);
        int i = mo608measureBRTryo0.get(alignmentLine);
        if (i == Integer.MIN_VALUE) {
            i = 0;
        }
        int i2 = z ? mo608measureBRTryo0.height : mo608measureBRTryo0.width;
        int m820getMaxHeightimpl = z ? Constraints.m820getMaxHeightimpl(j2) : Constraints.m821getMaxWidthimpl(j2);
        Dp.Companion.getClass();
        float f3 = Dp.Unspecified;
        int i3 = m820getMaxHeightimpl - i2;
        final int coerceIn = RangesKt___RangesKt.coerceIn((!Dp.m836equalsimpl0(f, f3) ? measureScope.mo51roundToPx0680j_4(f) : 0) - i, 0, i3);
        final int coerceIn2 = RangesKt___RangesKt.coerceIn(((!Dp.m836equalsimpl0(f2, f3) ? measureScope.mo51roundToPx0680j_4(f2) : 0) - i2) + i, 0, i3 - coerceIn);
        int max = z ? mo608measureBRTryo0.width : Math.max(mo608measureBRTryo0.width + coerceIn + coerceIn2, Constraints.m823getMinWidthimpl(j2));
        final int max2 = z ? Math.max(mo608measureBRTryo0.height + coerceIn + coerceIn2, Constraints.m822getMinHeightimpl(j2)) : mo608measureBRTryo0.height;
        final int i4 = max;
        layout$1 = measureScope.layout$1(i4, max2, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.foundation.layout.AlignmentLineKt$alignmentLineOffsetMeasure$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                int i5;
                Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj;
                int i6 = 0;
                if (AlignmentLine.this instanceof HorizontalAlignmentLine) {
                    i5 = 0;
                } else {
                    float f4 = f;
                    Dp.Companion.getClass();
                    i5 = !Dp.m836equalsimpl0(f4, Dp.Unspecified) ? coerceIn : (i4 - coerceIn2) - mo608measureBRTryo0.width;
                }
                if (AlignmentLine.this instanceof HorizontalAlignmentLine) {
                    float f5 = f;
                    Dp.Companion.getClass();
                    i6 = !Dp.m836equalsimpl0(f5, Dp.Unspecified) ? coerceIn : (max2 - coerceIn2) - mo608measureBRTryo0.height;
                }
                placementScope.placeRelative(mo608measureBRTryo0, i5, i6, 0.0f);
                return Unit.INSTANCE;
            }
        });
        return layout$1;
    }

    private AlignmentLineOffsetDpNode(AlignmentLine alignmentLine, float f, float f2) {
        this.alignmentLine = alignmentLine;
        this.before = f;
        this.after = f2;
    }
}
