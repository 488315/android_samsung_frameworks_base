package androidx.compose.foundation.layout;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.IntrinsicMeasurable;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.LayoutModifierNode;
import androidx.compose.ui.node.LookaheadCapablePlaceable;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.Dp;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class UnspecifiedConstraintsNode extends Modifier.Node implements LayoutModifierNode {
    public float minHeight;
    public float minWidth;

    public /* synthetic */ UnspecifiedConstraintsNode(float f, float f2, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, f2);
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int maxIntrinsicHeight(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        int maxIntrinsicHeight = intrinsicMeasurable.maxIntrinsicHeight(i);
        float f = this.minHeight;
        Dp.Companion.getClass();
        int mo51roundToPx0680j_4 = !Dp.m836equalsimpl0(f, Dp.Unspecified) ? lookaheadCapablePlaceable.mo51roundToPx0680j_4(this.minHeight) : 0;
        return maxIntrinsicHeight < mo51roundToPx0680j_4 ? mo51roundToPx0680j_4 : maxIntrinsicHeight;
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int maxIntrinsicWidth(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        int maxIntrinsicWidth = intrinsicMeasurable.maxIntrinsicWidth(i);
        float f = this.minWidth;
        Dp.Companion.getClass();
        int mo51roundToPx0680j_4 = !Dp.m836equalsimpl0(f, Dp.Unspecified) ? lookaheadCapablePlaceable.mo51roundToPx0680j_4(this.minWidth) : 0;
        return maxIntrinsicWidth < mo51roundToPx0680j_4 ? mo51roundToPx0680j_4 : maxIntrinsicWidth;
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo4measure3p2s80s(MeasureScope measureScope, Measurable measurable, long j) {
        int m823getMinWidthimpl;
        MeasureResult layout$1;
        float f = this.minWidth;
        Dp.Companion.getClass();
        float f2 = Dp.Unspecified;
        int i = 0;
        if (Dp.m836equalsimpl0(f, f2) || Constraints.m823getMinWidthimpl(j) != 0) {
            m823getMinWidthimpl = Constraints.m823getMinWidthimpl(j);
        } else {
            m823getMinWidthimpl = measureScope.mo51roundToPx0680j_4(this.minWidth);
            int m821getMaxWidthimpl = Constraints.m821getMaxWidthimpl(j);
            if (m823getMinWidthimpl > m821getMaxWidthimpl) {
                m823getMinWidthimpl = m821getMaxWidthimpl;
            }
            if (m823getMinWidthimpl < 0) {
                m823getMinWidthimpl = 0;
            }
        }
        int m821getMaxWidthimpl2 = Constraints.m821getMaxWidthimpl(j);
        if (Dp.m836equalsimpl0(this.minHeight, f2) || Constraints.m822getMinHeightimpl(j) != 0) {
            i = Constraints.m822getMinHeightimpl(j);
        } else {
            int mo51roundToPx0680j_4 = measureScope.mo51roundToPx0680j_4(this.minHeight);
            int m820getMaxHeightimpl = Constraints.m820getMaxHeightimpl(j);
            if (mo51roundToPx0680j_4 > m820getMaxHeightimpl) {
                mo51roundToPx0680j_4 = m820getMaxHeightimpl;
            }
            if (mo51roundToPx0680j_4 >= 0) {
                i = mo51roundToPx0680j_4;
            }
        }
        final Placeable mo608measureBRTryo0 = measurable.mo608measureBRTryo0(ConstraintsKt.Constraints(m823getMinWidthimpl, m821getMaxWidthimpl2, i, Constraints.m820getMaxHeightimpl(j)));
        layout$1 = measureScope.layout$1(mo608measureBRTryo0.width, mo608measureBRTryo0.height, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.foundation.layout.UnspecifiedConstraintsNode$measure$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                ((Placeable.PlacementScope) obj).placeRelative(Placeable.this, 0, 0, 0.0f);
                return Unit.INSTANCE;
            }
        });
        return layout$1;
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int minIntrinsicHeight(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        int minIntrinsicHeight = intrinsicMeasurable.minIntrinsicHeight(i);
        float f = this.minHeight;
        Dp.Companion.getClass();
        int mo51roundToPx0680j_4 = !Dp.m836equalsimpl0(f, Dp.Unspecified) ? lookaheadCapablePlaceable.mo51roundToPx0680j_4(this.minHeight) : 0;
        return minIntrinsicHeight < mo51roundToPx0680j_4 ? mo51roundToPx0680j_4 : minIntrinsicHeight;
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int minIntrinsicWidth(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        int minIntrinsicWidth = intrinsicMeasurable.minIntrinsicWidth(i);
        float f = this.minWidth;
        Dp.Companion.getClass();
        int mo51roundToPx0680j_4 = !Dp.m836equalsimpl0(f, Dp.Unspecified) ? lookaheadCapablePlaceable.mo51roundToPx0680j_4(this.minWidth) : 0;
        return minIntrinsicWidth < mo51roundToPx0680j_4 ? mo51roundToPx0680j_4 : minIntrinsicWidth;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public UnspecifiedConstraintsNode(float r1, float r2, int r3, kotlin.jvm.internal.DefaultConstructorMarker r4) {
        /*
            r0 = this;
            r4 = r3 & 1
            if (r4 == 0) goto Lb
            androidx.compose.ui.unit.Dp$Companion r1 = androidx.compose.ui.unit.Dp.Companion
            r1.getClass()
            float r1 = androidx.compose.ui.unit.Dp.Unspecified
        Lb:
            r3 = r3 & 2
            if (r3 == 0) goto L16
            androidx.compose.ui.unit.Dp$Companion r2 = androidx.compose.ui.unit.Dp.Companion
            r2.getClass()
            float r2 = androidx.compose.ui.unit.Dp.Unspecified
        L16:
            r3 = 0
            r0.<init>(r1, r2, r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.layout.UnspecifiedConstraintsNode.<init>(float, float, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    private UnspecifiedConstraintsNode(float f, float f2) {
        this.minWidth = f;
        this.minHeight = f2;
    }
}
