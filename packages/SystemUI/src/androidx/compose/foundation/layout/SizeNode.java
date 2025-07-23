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
final class SizeNode extends Modifier.Node implements LayoutModifierNode {
    public boolean enforceIncoming;
    public float maxHeight;
    public float maxWidth;
    public float minHeight;
    public float minWidth;

    public /* synthetic */ SizeNode(float f, float f2, float f3, float f4, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, f2, f3, f4, z);
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0045, code lost:
    
        if (r5 != Integer.MAX_VALUE) goto L24;
     */
    /* renamed from: getTargetConstraints-OenEA2s, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final long m146getTargetConstraintsOenEA2s(androidx.compose.ui.layout.IntrinsicMeasureScope r8) {
        /*
            r7 = this;
            float r0 = r7.maxWidth
            androidx.compose.ui.unit.Dp$Companion r1 = androidx.compose.ui.unit.Dp.Companion
            r1.getClass()
            float r1 = androidx.compose.ui.unit.Dp.Unspecified
            boolean r0 = androidx.compose.ui.unit.Dp.m836equalsimpl0(r0, r1)
            r2 = 2147483647(0x7fffffff, float:NaN)
            r3 = 0
            if (r0 != 0) goto L1d
            float r0 = r7.maxWidth
            int r0 = r8.mo51roundToPx0680j_4(r0)
            if (r0 >= 0) goto L1e
            r0 = r3
            goto L1e
        L1d:
            r0 = r2
        L1e:
            float r4 = r7.maxHeight
            boolean r4 = androidx.compose.ui.unit.Dp.m836equalsimpl0(r4, r1)
            if (r4 != 0) goto L30
            float r4 = r7.maxHeight
            int r4 = r8.mo51roundToPx0680j_4(r4)
            if (r4 >= 0) goto L31
            r4 = r3
            goto L31
        L30:
            r4 = r2
        L31:
            float r5 = r7.minWidth
            boolean r5 = androidx.compose.ui.unit.Dp.m836equalsimpl0(r5, r1)
            if (r5 != 0) goto L48
            float r5 = r7.minWidth
            int r5 = r8.mo51roundToPx0680j_4(r5)
            if (r5 <= r0) goto L42
            r5 = r0
        L42:
            if (r5 >= 0) goto L45
            r5 = r3
        L45:
            if (r5 == r2) goto L48
            goto L49
        L48:
            r5 = r3
        L49:
            float r6 = r7.minHeight
            boolean r1 = androidx.compose.ui.unit.Dp.m836equalsimpl0(r6, r1)
            if (r1 != 0) goto L60
            float r7 = r7.minHeight
            int r7 = r8.mo51roundToPx0680j_4(r7)
            if (r7 <= r4) goto L5a
            r7 = r4
        L5a:
            if (r7 >= 0) goto L5d
            r7 = r3
        L5d:
            if (r7 == r2) goto L60
            r3 = r7
        L60:
            long r7 = androidx.compose.ui.unit.ConstraintsKt.Constraints(r5, r0, r3, r4)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.layout.SizeNode.m146getTargetConstraintsOenEA2s(androidx.compose.ui.layout.IntrinsicMeasureScope):long");
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int maxIntrinsicHeight(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        long m146getTargetConstraintsOenEA2s = m146getTargetConstraintsOenEA2s(lookaheadCapablePlaceable);
        if (Constraints.m818getHasFixedHeightimpl(m146getTargetConstraintsOenEA2s)) {
            return Constraints.m820getMaxHeightimpl(m146getTargetConstraintsOenEA2s);
        }
        if (!this.enforceIncoming) {
            i = ConstraintsKt.m832constrainWidthK40F9xA(i, m146getTargetConstraintsOenEA2s);
        }
        return ConstraintsKt.m831constrainHeightK40F9xA(intrinsicMeasurable.maxIntrinsicHeight(i), m146getTargetConstraintsOenEA2s);
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int maxIntrinsicWidth(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        long m146getTargetConstraintsOenEA2s = m146getTargetConstraintsOenEA2s(lookaheadCapablePlaceable);
        if (Constraints.m819getHasFixedWidthimpl(m146getTargetConstraintsOenEA2s)) {
            return Constraints.m821getMaxWidthimpl(m146getTargetConstraintsOenEA2s);
        }
        if (!this.enforceIncoming) {
            i = ConstraintsKt.m831constrainHeightK40F9xA(i, m146getTargetConstraintsOenEA2s);
        }
        return ConstraintsKt.m832constrainWidthK40F9xA(intrinsicMeasurable.maxIntrinsicWidth(i), m146getTargetConstraintsOenEA2s);
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo4measure3p2s80s(MeasureScope measureScope, Measurable measurable, long j) {
        int m823getMinWidthimpl;
        int m821getMaxWidthimpl;
        int m822getMinHeightimpl;
        int m820getMaxHeightimpl;
        long Constraints;
        MeasureResult layout$1;
        long m146getTargetConstraintsOenEA2s = m146getTargetConstraintsOenEA2s(measureScope);
        if (this.enforceIncoming) {
            Constraints = ConstraintsKt.m830constrainN9IONVI(j, m146getTargetConstraintsOenEA2s);
        } else {
            float f = this.minWidth;
            Dp.Companion.getClass();
            float f2 = Dp.Unspecified;
            if (Dp.m836equalsimpl0(f, f2)) {
                m823getMinWidthimpl = Constraints.m823getMinWidthimpl(j);
                int m821getMaxWidthimpl2 = Constraints.m821getMaxWidthimpl(m146getTargetConstraintsOenEA2s);
                if (m823getMinWidthimpl > m821getMaxWidthimpl2) {
                    m823getMinWidthimpl = m821getMaxWidthimpl2;
                }
            } else {
                m823getMinWidthimpl = Constraints.m823getMinWidthimpl(m146getTargetConstraintsOenEA2s);
            }
            if (Dp.m836equalsimpl0(this.maxWidth, f2)) {
                m821getMaxWidthimpl = Constraints.m821getMaxWidthimpl(j);
                int m823getMinWidthimpl2 = Constraints.m823getMinWidthimpl(m146getTargetConstraintsOenEA2s);
                if (m821getMaxWidthimpl < m823getMinWidthimpl2) {
                    m821getMaxWidthimpl = m823getMinWidthimpl2;
                }
            } else {
                m821getMaxWidthimpl = Constraints.m821getMaxWidthimpl(m146getTargetConstraintsOenEA2s);
            }
            if (Dp.m836equalsimpl0(this.minHeight, f2)) {
                m822getMinHeightimpl = Constraints.m822getMinHeightimpl(j);
                int m820getMaxHeightimpl2 = Constraints.m820getMaxHeightimpl(m146getTargetConstraintsOenEA2s);
                if (m822getMinHeightimpl > m820getMaxHeightimpl2) {
                    m822getMinHeightimpl = m820getMaxHeightimpl2;
                }
            } else {
                m822getMinHeightimpl = Constraints.m822getMinHeightimpl(m146getTargetConstraintsOenEA2s);
            }
            if (Dp.m836equalsimpl0(this.maxHeight, f2)) {
                m820getMaxHeightimpl = Constraints.m820getMaxHeightimpl(j);
                int m822getMinHeightimpl2 = Constraints.m822getMinHeightimpl(m146getTargetConstraintsOenEA2s);
                if (m820getMaxHeightimpl < m822getMinHeightimpl2) {
                    m820getMaxHeightimpl = m822getMinHeightimpl2;
                }
            } else {
                m820getMaxHeightimpl = Constraints.m820getMaxHeightimpl(m146getTargetConstraintsOenEA2s);
            }
            Constraints = ConstraintsKt.Constraints(m823getMinWidthimpl, m821getMaxWidthimpl, m822getMinHeightimpl, m820getMaxHeightimpl);
        }
        final Placeable mo608measureBRTryo0 = measurable.mo608measureBRTryo0(Constraints);
        layout$1 = measureScope.layout$1(mo608measureBRTryo0.width, mo608measureBRTryo0.height, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.foundation.layout.SizeNode$measure$1
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
        long m146getTargetConstraintsOenEA2s = m146getTargetConstraintsOenEA2s(lookaheadCapablePlaceable);
        if (Constraints.m818getHasFixedHeightimpl(m146getTargetConstraintsOenEA2s)) {
            return Constraints.m820getMaxHeightimpl(m146getTargetConstraintsOenEA2s);
        }
        if (!this.enforceIncoming) {
            i = ConstraintsKt.m832constrainWidthK40F9xA(i, m146getTargetConstraintsOenEA2s);
        }
        return ConstraintsKt.m831constrainHeightK40F9xA(intrinsicMeasurable.minIntrinsicHeight(i), m146getTargetConstraintsOenEA2s);
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int minIntrinsicWidth(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        long m146getTargetConstraintsOenEA2s = m146getTargetConstraintsOenEA2s(lookaheadCapablePlaceable);
        if (Constraints.m819getHasFixedWidthimpl(m146getTargetConstraintsOenEA2s)) {
            return Constraints.m821getMaxWidthimpl(m146getTargetConstraintsOenEA2s);
        }
        if (!this.enforceIncoming) {
            i = ConstraintsKt.m831constrainHeightK40F9xA(i, m146getTargetConstraintsOenEA2s);
        }
        return ConstraintsKt.m832constrainWidthK40F9xA(intrinsicMeasurable.minIntrinsicWidth(i), m146getTargetConstraintsOenEA2s);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public SizeNode(float r8, float r9, float r10, float r11, boolean r12, int r13, kotlin.jvm.internal.DefaultConstructorMarker r14) {
        /*
            r7 = this;
            r14 = r13 & 1
            if (r14 == 0) goto Lb
            androidx.compose.ui.unit.Dp$Companion r8 = androidx.compose.ui.unit.Dp.Companion
            r8.getClass()
            float r8 = androidx.compose.ui.unit.Dp.Unspecified
        Lb:
            r1 = r8
            r8 = r13 & 2
            if (r8 == 0) goto L17
            androidx.compose.ui.unit.Dp$Companion r8 = androidx.compose.ui.unit.Dp.Companion
            r8.getClass()
            float r9 = androidx.compose.ui.unit.Dp.Unspecified
        L17:
            r2 = r9
            r8 = r13 & 4
            if (r8 == 0) goto L23
            androidx.compose.ui.unit.Dp$Companion r8 = androidx.compose.ui.unit.Dp.Companion
            r8.getClass()
            float r10 = androidx.compose.ui.unit.Dp.Unspecified
        L23:
            r3 = r10
            r8 = r13 & 8
            if (r8 == 0) goto L2f
            androidx.compose.ui.unit.Dp$Companion r8 = androidx.compose.ui.unit.Dp.Companion
            r8.getClass()
            float r11 = androidx.compose.ui.unit.Dp.Unspecified
        L2f:
            r4 = r11
            r6 = 0
            r0 = r7
            r5 = r12
            r0.<init>(r1, r2, r3, r4, r5, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.layout.SizeNode.<init>(float, float, float, float, boolean, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    private SizeNode(float f, float f2, float f3, float f4, boolean z) {
        this.minWidth = f;
        this.minHeight = f2;
        this.maxWidth = f3;
        this.maxHeight = f4;
        this.enforceIncoming = z;
    }
}
