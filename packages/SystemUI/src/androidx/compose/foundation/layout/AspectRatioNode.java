package androidx.compose.foundation.layout;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.IntrinsicMeasurable;
import androidx.compose.ui.node.LayoutModifierNode;
import androidx.compose.ui.node.LookaheadCapablePlaceable;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.IntSize;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AspectRatioNode extends Modifier.Node implements LayoutModifierNode {
    public float aspectRatio;
    public boolean matchHeightConstraintsFirst;

    public AspectRatioNode(float f, boolean z) {
        this.aspectRatio = f;
        this.matchHeightConstraintsFirst = z;
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int maxIntrinsicHeight(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        return i != Integer.MAX_VALUE ? Math.round(i / this.aspectRatio) : intrinsicMeasurable.maxIntrinsicHeight(i);
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int maxIntrinsicWidth(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        return i != Integer.MAX_VALUE ? Math.round(i * this.aspectRatio) : intrinsicMeasurable.maxIntrinsicWidth(i);
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0069, code lost:
    
        if (androidx.compose.ui.unit.IntSize.m861equalsimpl0(r5, 0) == false) goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x00ca, code lost:
    
        androidx.compose.ui.unit.IntSize.Companion.getClass();
        r5 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00c7, code lost:
    
        if (androidx.compose.ui.unit.IntSize.m861equalsimpl0(r5, 0) == false) goto L53;
     */
    @Override // androidx.compose.ui.node.LayoutModifierNode
    /* renamed from: measure-3p2s80s */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final androidx.compose.ui.layout.MeasureResult mo4measure3p2s80s(androidx.compose.ui.layout.MeasureScope r8, androidx.compose.ui.layout.Measurable r9, long r10) {
        /*
            r7 = this;
            boolean r0 = r7.matchHeightConstraintsFirst
            r1 = 0
            r3 = 0
            r4 = 1
            if (r0 != 0) goto L6d
            long r5 = r7.m97tryMaxWidthJN0ABg(r10, r4)
            androidx.compose.ui.unit.IntSize$Companion r0 = androidx.compose.ui.unit.IntSize.Companion
            r0.getClass()
            boolean r0 = androidx.compose.ui.unit.IntSize.m861equalsimpl0(r5, r1)
            if (r0 != 0) goto L19
            goto Ld0
        L19:
            long r5 = r7.m96tryMaxHeightJN0ABg(r10, r4)
            boolean r0 = androidx.compose.ui.unit.IntSize.m861equalsimpl0(r5, r1)
            if (r0 != 0) goto L25
            goto Ld0
        L25:
            long r5 = r7.m99tryMinWidthJN0ABg(r10, r4)
            boolean r0 = androidx.compose.ui.unit.IntSize.m861equalsimpl0(r5, r1)
            if (r0 != 0) goto L31
            goto Ld0
        L31:
            long r5 = r7.m98tryMinHeightJN0ABg(r10, r4)
            boolean r0 = androidx.compose.ui.unit.IntSize.m861equalsimpl0(r5, r1)
            if (r0 != 0) goto L3d
            goto Ld0
        L3d:
            long r5 = r7.m97tryMaxWidthJN0ABg(r10, r3)
            boolean r0 = androidx.compose.ui.unit.IntSize.m861equalsimpl0(r5, r1)
            if (r0 != 0) goto L49
            goto Ld0
        L49:
            long r5 = r7.m96tryMaxHeightJN0ABg(r10, r3)
            boolean r0 = androidx.compose.ui.unit.IntSize.m861equalsimpl0(r5, r1)
            if (r0 != 0) goto L55
            goto Ld0
        L55:
            long r5 = r7.m99tryMinWidthJN0ABg(r10, r3)
            boolean r0 = androidx.compose.ui.unit.IntSize.m861equalsimpl0(r5, r1)
            if (r0 != 0) goto L61
            goto Ld0
        L61:
            long r5 = r7.m98tryMinHeightJN0ABg(r10, r3)
            boolean r7 = androidx.compose.ui.unit.IntSize.m861equalsimpl0(r5, r1)
            if (r7 != 0) goto Lca
            goto Ld0
        L6d:
            long r5 = r7.m96tryMaxHeightJN0ABg(r10, r4)
            androidx.compose.ui.unit.IntSize$Companion r0 = androidx.compose.ui.unit.IntSize.Companion
            r0.getClass()
            boolean r0 = androidx.compose.ui.unit.IntSize.m861equalsimpl0(r5, r1)
            if (r0 != 0) goto L7d
            goto Ld0
        L7d:
            long r5 = r7.m97tryMaxWidthJN0ABg(r10, r4)
            boolean r0 = androidx.compose.ui.unit.IntSize.m861equalsimpl0(r5, r1)
            if (r0 != 0) goto L88
            goto Ld0
        L88:
            long r5 = r7.m98tryMinHeightJN0ABg(r10, r4)
            boolean r0 = androidx.compose.ui.unit.IntSize.m861equalsimpl0(r5, r1)
            if (r0 != 0) goto L93
            goto Ld0
        L93:
            long r5 = r7.m99tryMinWidthJN0ABg(r10, r4)
            boolean r0 = androidx.compose.ui.unit.IntSize.m861equalsimpl0(r5, r1)
            if (r0 != 0) goto L9e
            goto Ld0
        L9e:
            long r5 = r7.m96tryMaxHeightJN0ABg(r10, r3)
            boolean r0 = androidx.compose.ui.unit.IntSize.m861equalsimpl0(r5, r1)
            if (r0 != 0) goto La9
            goto Ld0
        La9:
            long r5 = r7.m97tryMaxWidthJN0ABg(r10, r3)
            boolean r0 = androidx.compose.ui.unit.IntSize.m861equalsimpl0(r5, r1)
            if (r0 != 0) goto Lb4
            goto Ld0
        Lb4:
            long r5 = r7.m98tryMinHeightJN0ABg(r10, r3)
            boolean r0 = androidx.compose.ui.unit.IntSize.m861equalsimpl0(r5, r1)
            if (r0 != 0) goto Lbf
            goto Ld0
        Lbf:
            long r5 = r7.m99tryMinWidthJN0ABg(r10, r3)
            boolean r7 = androidx.compose.ui.unit.IntSize.m861equalsimpl0(r5, r1)
            if (r7 != 0) goto Lca
            goto Ld0
        Lca:
            androidx.compose.ui.unit.IntSize$Companion r7 = androidx.compose.ui.unit.IntSize.Companion
            r7.getClass()
            r5 = r1
        Ld0:
            androidx.compose.ui.unit.IntSize$Companion r7 = androidx.compose.ui.unit.IntSize.Companion
            r7.getClass()
            boolean r7 = androidx.compose.ui.unit.IntSize.m861equalsimpl0(r5, r1)
            if (r7 != 0) goto Lf0
            androidx.compose.ui.unit.Constraints$Companion r7 = androidx.compose.ui.unit.Constraints.Companion
            r10 = 32
            long r10 = r5 >> r10
            int r10 = (int) r10
            r0 = 4294967295(0xffffffff, double:2.1219957905E-314)
            long r0 = r0 & r5
            int r11 = (int) r0
            r7.getClass()
            long r10 = androidx.compose.ui.unit.Constraints.Companion.m827fixedJhjzzOo(r10, r11)
        Lf0:
            androidx.compose.ui.layout.Placeable r7 = r9.mo608measureBRTryo0(r10)
            int r9 = r7.width
            int r10 = r7.height
            androidx.compose.foundation.layout.AspectRatioNode$measure$1 r11 = new androidx.compose.foundation.layout.AspectRatioNode$measure$1
            r11.<init>()
            androidx.compose.ui.layout.MeasureResult r7 = androidx.compose.ui.layout.MeasureScope.layout$default(r8, r9, r10, r11)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.layout.AspectRatioNode.mo4measure3p2s80s(androidx.compose.ui.layout.MeasureScope, androidx.compose.ui.layout.Measurable, long):androidx.compose.ui.layout.MeasureResult");
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int minIntrinsicHeight(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        return i != Integer.MAX_VALUE ? Math.round(i / this.aspectRatio) : intrinsicMeasurable.minIntrinsicHeight(i);
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int minIntrinsicWidth(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        return i != Integer.MAX_VALUE ? Math.round(i * this.aspectRatio) : intrinsicMeasurable.minIntrinsicWidth(i);
    }

    /* renamed from: tryMaxHeight-JN-0ABg, reason: not valid java name */
    public final long m96tryMaxHeightJN0ABg(long j, boolean z) {
        int round;
        int m820getMaxHeightimpl = Constraints.m820getMaxHeightimpl(j);
        if (m820getMaxHeightimpl == Integer.MAX_VALUE || (round = Math.round(m820getMaxHeightimpl * this.aspectRatio)) <= 0 || (z && !AspectRatioKt.m95isSatisfiedByNN6EwU(round, m820getMaxHeightimpl, j))) {
            IntSize.Companion.getClass();
            return 0L;
        }
        long j2 = (round << 32) | (m820getMaxHeightimpl & 4294967295L);
        IntSize.Companion companion = IntSize.Companion;
        return j2;
    }

    /* renamed from: tryMaxWidth-JN-0ABg, reason: not valid java name */
    public final long m97tryMaxWidthJN0ABg(long j, boolean z) {
        int round;
        int m821getMaxWidthimpl = Constraints.m821getMaxWidthimpl(j);
        if (m821getMaxWidthimpl == Integer.MAX_VALUE || (round = Math.round(m821getMaxWidthimpl / this.aspectRatio)) <= 0 || (z && !AspectRatioKt.m95isSatisfiedByNN6EwU(m821getMaxWidthimpl, round, j))) {
            IntSize.Companion.getClass();
            return 0L;
        }
        long j2 = (m821getMaxWidthimpl << 32) | (round & 4294967295L);
        IntSize.Companion companion = IntSize.Companion;
        return j2;
    }

    /* renamed from: tryMinHeight-JN-0ABg, reason: not valid java name */
    public final long m98tryMinHeightJN0ABg(long j, boolean z) {
        int m822getMinHeightimpl = Constraints.m822getMinHeightimpl(j);
        int round = Math.round(m822getMinHeightimpl * this.aspectRatio);
        if (round <= 0 || (z && !AspectRatioKt.m95isSatisfiedByNN6EwU(round, m822getMinHeightimpl, j))) {
            IntSize.Companion.getClass();
            return 0L;
        }
        long j2 = (round << 32) | (m822getMinHeightimpl & 4294967295L);
        IntSize.Companion companion = IntSize.Companion;
        return j2;
    }

    /* renamed from: tryMinWidth-JN-0ABg, reason: not valid java name */
    public final long m99tryMinWidthJN0ABg(long j, boolean z) {
        int m823getMinWidthimpl = Constraints.m823getMinWidthimpl(j);
        int round = Math.round(m823getMinWidthimpl / this.aspectRatio);
        if (round <= 0 || (z && !AspectRatioKt.m95isSatisfiedByNN6EwU(m823getMinWidthimpl, round, j))) {
            IntSize.Companion.getClass();
            return 0L;
        }
        long j2 = (m823getMinWidthimpl << 32) | (round & 4294967295L);
        IntSize.Companion companion = IntSize.Companion;
        return j2;
    }
}
