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
        long jM816copyZbe2FdA$default;
        final AlignmentLine alignmentLine = this.alignmentLine;
        final float f = this.before;
        float f2 = this.after;
        boolean z = alignmentLine instanceof HorizontalAlignmentLine;
        if (z) {
            j2 = j;
            jM816copyZbe2FdA$default = Constraints.m816copyZbe2FdA$default(j2, 0, 0, 0, 0, 11);
        } else {
            j2 = j;
            jM816copyZbe2FdA$default = Constraints.m816copyZbe2FdA$default(j2, 0, 0, 0, 0, 14);
        }
        final Placeable placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(jM816copyZbe2FdA$default);
        int i = placeableMo610measureBRTryo0.get(alignmentLine);
        if (i == Integer.MIN_VALUE) {
            i = 0;
        }
        int i2 = z ? placeableMo610measureBRTryo0.height : placeableMo610measureBRTryo0.width;
        int iM822getMaxHeightimpl = z ? Constraints.m822getMaxHeightimpl(j2) : Constraints.m823getMaxWidthimpl(j2);
        Dp.Companion.getClass();
        float f3 = Dp.Unspecified;
        int i3 = iM822getMaxHeightimpl - i2;
        final int iCoerceIn = RangesKt___RangesKt.coerceIn((!Dp.m838equalsimpl0(f, f3) ? measureScope.mo52roundToPx0680j_4(f) : 0) - i, 0, i3);
        final int iCoerceIn2 = RangesKt___RangesKt.coerceIn(((!Dp.m838equalsimpl0(f2, f3) ? measureScope.mo52roundToPx0680j_4(f2) : 0) - i2) + i, 0, i3 - iCoerceIn);
        int iMax = z ? placeableMo610measureBRTryo0.width : Math.max(placeableMo610measureBRTryo0.width + iCoerceIn + iCoerceIn2, Constraints.m825getMinWidthimpl(j2));
        final int iMax2 = z ? Math.max(placeableMo610measureBRTryo0.height + iCoerceIn + iCoerceIn2, Constraints.m824getMinHeightimpl(j2)) : placeableMo610measureBRTryo0.height;
        final int i4 = iMax;
        return measureScope.layout$1(i4, iMax2, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.foundation.layout.AlignmentLineKt$alignmentLineOffsetMeasure$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                int i5;
                Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj;
                int i6 = 0;
                if (alignmentLine instanceof HorizontalAlignmentLine) {
                    i5 = 0;
                } else {
                    float f4 = f;
                    Dp.Companion.getClass();
                    i5 = !Dp.m838equalsimpl0(f4, Dp.Unspecified) ? iCoerceIn : (i4 - iCoerceIn2) - placeableMo610measureBRTryo0.width;
                }
                if (alignmentLine instanceof HorizontalAlignmentLine) {
                    float f5 = f;
                    Dp.Companion.getClass();
                    i6 = !Dp.m838equalsimpl0(f5, Dp.Unspecified) ? iCoerceIn : (iMax2 - iCoerceIn2) - placeableMo610measureBRTryo0.height;
                }
                placementScope.placeRelative(placeableMo610measureBRTryo0, i5, i6, 0.0f);
                return Unit.INSTANCE;
            }
        });
    }

    private AlignmentLineOffsetDpNode(AlignmentLine alignmentLine, float f, float f2) {
        this.alignmentLine = alignmentLine;
        this.before = f;
        this.after = f2;
    }
}
