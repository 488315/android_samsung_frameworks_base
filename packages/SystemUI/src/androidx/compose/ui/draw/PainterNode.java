package androidx.compose.ui.draw;

import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.layout.ContentScale;
import androidx.compose.ui.layout.IntrinsicMeasurable;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.layout.ScaleFactorKt;
import androidx.compose.ui.node.DrawModifierNode;
import androidx.compose.ui.node.LayoutModifierNode;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import androidx.compose.ui.node.LookaheadCapablePlaceable;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntSize;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class PainterNode extends Modifier.Node implements LayoutModifierNode, DrawModifierNode {
    public Alignment alignment;
    public float alpha;
    public ColorFilter colorFilter;
    public ContentScale contentScale;
    public Painter painter;
    public boolean sizeToIntrinsics;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public PainterNode(androidx.compose.ui.graphics.painter.Painter r8, boolean r9, androidx.compose.ui.Alignment r10, androidx.compose.ui.layout.ContentScale r11, float r12, androidx.compose.ui.graphics.ColorFilter r13, int r14, kotlin.jvm.internal.DefaultConstructorMarker r15) {
        /*
            r7 = this;
            r15 = r14 & 4
            if (r15 == 0) goto Lb
            androidx.compose.ui.Alignment$Companion r10 = androidx.compose.ui.Alignment.Companion
            r10.getClass()
            androidx.compose.ui.BiasAlignment r10 = androidx.compose.ui.Alignment.Companion.Center
        Lb:
            r3 = r10
            r10 = r14 & 8
            if (r10 == 0) goto L17
            androidx.compose.ui.layout.ContentScale$Companion r10 = androidx.compose.ui.layout.ContentScale.Companion
            r10.getClass()
            androidx.compose.ui.layout.ContentScale$Companion$Inside$1 r11 = androidx.compose.ui.layout.ContentScale.Companion.Inside
        L17:
            r4 = r11
            r10 = r14 & 16
            if (r10 == 0) goto L1e
            r12 = 1065353216(0x3f800000, float:1.0)
        L1e:
            r5 = r12
            r10 = r14 & 32
            if (r10 == 0) goto L24
            r13 = 0
        L24:
            r0 = r7
            r1 = r8
            r2 = r9
            r6 = r13
            r0.<init>(r1, r2, r3, r4, r5, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.draw.PainterNode.<init>(androidx.compose.ui.graphics.painter.Painter, boolean, androidx.compose.ui.Alignment, androidx.compose.ui.layout.ContentScale, float, androidx.compose.ui.graphics.ColorFilter, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    /* renamed from: hasSpecifiedAndFiniteHeight-uvyYCjk, reason: not valid java name */
    public static boolean m362hasSpecifiedAndFiniteHeightuvyYCjk(long j) {
        Size.Companion.getClass();
        return !Size.m414equalsimpl0(j, Size.Unspecified) && (Float.floatToRawIntBits(Float.intBitsToFloat((int) (j & 4294967295L))) & Integer.MAX_VALUE) < 2139095040;
    }

    /* renamed from: hasSpecifiedAndFiniteWidth-uvyYCjk, reason: not valid java name */
    public static boolean m363hasSpecifiedAndFiniteWidthuvyYCjk(long j) {
        Size.Companion.getClass();
        return !Size.m414equalsimpl0(j, Size.Unspecified) && (Float.floatToRawIntBits(Float.intBitsToFloat((int) (j >> 32))) & Integer.MAX_VALUE) < 2139095040;
    }

    @Override // androidx.compose.ui.node.DrawModifierNode
    public final void draw(LayoutNodeDrawScope layoutNodeDrawScope) {
        long j;
        long mo561getIntrinsicSizeNHjbRc = this.painter.mo561getIntrinsicSizeNHjbRc();
        boolean m363hasSpecifiedAndFiniteWidthuvyYCjk = m363hasSpecifiedAndFiniteWidthuvyYCjk(mo561getIntrinsicSizeNHjbRc);
        CanvasDrawScope canvasDrawScope = layoutNodeDrawScope.canvasDrawScope;
        long floatToRawIntBits = (Float.floatToRawIntBits(m363hasSpecifiedAndFiniteWidthuvyYCjk ? Float.intBitsToFloat((int) (mo561getIntrinsicSizeNHjbRc >> 32)) : Float.intBitsToFloat((int) (canvasDrawScope.mo545getSizeNHjbRc() >> 32))) << 32) | (Float.floatToRawIntBits(m362hasSpecifiedAndFiniteHeightuvyYCjk(mo561getIntrinsicSizeNHjbRc) ? Float.intBitsToFloat((int) (mo561getIntrinsicSizeNHjbRc & 4294967295L)) : Float.intBitsToFloat((int) (canvasDrawScope.mo545getSizeNHjbRc() & 4294967295L))) & 4294967295L);
        if (Float.intBitsToFloat((int) (canvasDrawScope.mo545getSizeNHjbRc() >> 32)) == 0.0f || Float.intBitsToFloat((int) (canvasDrawScope.mo545getSizeNHjbRc() & 4294967295L)) == 0.0f) {
            Size.Companion.getClass();
            j = 0;
        } else {
            j = ScaleFactorKt.m630timesUQTWf7w(floatToRawIntBits, this.contentScale.mo606computeScaleFactorH7hwNQA(floatToRawIntBits, canvasDrawScope.mo545getSizeNHjbRc()));
        }
        IntSize.Companion companion = IntSize.Companion;
        long mo352alignKFBX0sM = this.alignment.mo352alignKFBX0sM((Math.round(Float.intBitsToFloat((int) (j >> 32))) << 32) | (Math.round(Float.intBitsToFloat((int) (j & 4294967295L))) & 4294967295L), (Math.round(Float.intBitsToFloat((int) (canvasDrawScope.mo545getSizeNHjbRc() >> 32))) << 32) | (Math.round(Float.intBitsToFloat((int) (canvasDrawScope.mo545getSizeNHjbRc() & 4294967295L))) & 4294967295L), layoutNodeDrawScope.getLayoutDirection());
        IntOffset.Companion companion2 = IntOffset.Companion;
        float f = (int) (mo352alignKFBX0sM >> 32);
        float f2 = (int) (mo352alignKFBX0sM & 4294967295L);
        canvasDrawScope.drawContext.transform.translate(f, f2);
        try {
            this.painter.m562drawx_KDEd0(layoutNodeDrawScope, j, this.alpha, this.colorFilter);
            canvasDrawScope.drawContext.transform.translate(-f, -f2);
            layoutNodeDrawScope.drawContent();
        } catch (Throwable th) {
            canvasDrawScope.drawContext.transform.translate(-f, -f2);
            throw th;
        }
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final boolean getShouldAutoInvalidate() {
        return false;
    }

    public final boolean getUseIntrinsicSize() {
        return this.sizeToIntrinsics && this.painter.mo561getIntrinsicSizeNHjbRc() != 9205357640488583168L;
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int maxIntrinsicHeight(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        if (!getUseIntrinsicSize()) {
            return intrinsicMeasurable.maxIntrinsicHeight(i);
        }
        long m364modifyConstraintsZezNO4M = m364modifyConstraintsZezNO4M(ConstraintsKt.Constraints$default(0, i, 0, 0, 13));
        return Math.max(Constraints.m822getMinHeightimpl(m364modifyConstraintsZezNO4M), intrinsicMeasurable.maxIntrinsicHeight(i));
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int maxIntrinsicWidth(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        if (!getUseIntrinsicSize()) {
            return intrinsicMeasurable.maxIntrinsicWidth(i);
        }
        long m364modifyConstraintsZezNO4M = m364modifyConstraintsZezNO4M(ConstraintsKt.Constraints$default(0, 0, 0, i, 7));
        return Math.max(Constraints.m823getMinWidthimpl(m364modifyConstraintsZezNO4M), intrinsicMeasurable.maxIntrinsicWidth(i));
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo4measure3p2s80s(MeasureScope measureScope, Measurable measurable, long j) {
        MeasureResult layout$1;
        final Placeable mo608measureBRTryo0 = measurable.mo608measureBRTryo0(m364modifyConstraintsZezNO4M(j));
        layout$1 = measureScope.layout$1(mo608measureBRTryo0.width, mo608measureBRTryo0.height, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.ui.draw.PainterNode$measure$1
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
        if (!getUseIntrinsicSize()) {
            return intrinsicMeasurable.minIntrinsicHeight(i);
        }
        long m364modifyConstraintsZezNO4M = m364modifyConstraintsZezNO4M(ConstraintsKt.Constraints$default(0, i, 0, 0, 13));
        return Math.max(Constraints.m822getMinHeightimpl(m364modifyConstraintsZezNO4M), intrinsicMeasurable.minIntrinsicHeight(i));
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int minIntrinsicWidth(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        if (!getUseIntrinsicSize()) {
            return intrinsicMeasurable.minIntrinsicWidth(i);
        }
        long m364modifyConstraintsZezNO4M = m364modifyConstraintsZezNO4M(ConstraintsKt.Constraints$default(0, 0, 0, i, 7));
        return Math.max(Constraints.m823getMinWidthimpl(m364modifyConstraintsZezNO4M), intrinsicMeasurable.minIntrinsicWidth(i));
    }

    /* renamed from: modifyConstraints-ZezNO4M, reason: not valid java name */
    public final long m364modifyConstraintsZezNO4M(long j) {
        boolean z = false;
        boolean z2 = Constraints.m817getHasBoundedWidthimpl(j) && Constraints.m816getHasBoundedHeightimpl(j);
        if (Constraints.m819getHasFixedWidthimpl(j) && Constraints.m818getHasFixedHeightimpl(j)) {
            z = true;
        }
        if ((!getUseIntrinsicSize() && z2) || z) {
            return Constraints.m814copyZbe2FdA$default(j, Constraints.m821getMaxWidthimpl(j), 0, Constraints.m820getMaxHeightimpl(j), 0, 10);
        }
        long mo561getIntrinsicSizeNHjbRc = this.painter.mo561getIntrinsicSizeNHjbRc();
        int round = m363hasSpecifiedAndFiniteWidthuvyYCjk(mo561getIntrinsicSizeNHjbRc) ? Math.round(Float.intBitsToFloat((int) (mo561getIntrinsicSizeNHjbRc >> 32))) : Constraints.m823getMinWidthimpl(j);
        int round2 = m362hasSpecifiedAndFiniteHeightuvyYCjk(mo561getIntrinsicSizeNHjbRc) ? Math.round(Float.intBitsToFloat((int) (mo561getIntrinsicSizeNHjbRc & 4294967295L))) : Constraints.m822getMinHeightimpl(j);
        int m832constrainWidthK40F9xA = ConstraintsKt.m832constrainWidthK40F9xA(round, j);
        long floatToRawIntBits = (Float.floatToRawIntBits(ConstraintsKt.m831constrainHeightK40F9xA(round2, j)) & 4294967295L) | (Float.floatToRawIntBits(m832constrainWidthK40F9xA) << 32);
        if (getUseIntrinsicSize()) {
            long floatToRawIntBits2 = (Float.floatToRawIntBits(!m363hasSpecifiedAndFiniteWidthuvyYCjk(this.painter.mo561getIntrinsicSizeNHjbRc()) ? Float.intBitsToFloat((int) (floatToRawIntBits >> 32)) : Float.intBitsToFloat((int) (this.painter.mo561getIntrinsicSizeNHjbRc() >> 32))) << 32) | (Float.floatToRawIntBits(!m362hasSpecifiedAndFiniteHeightuvyYCjk(this.painter.mo561getIntrinsicSizeNHjbRc()) ? Float.intBitsToFloat((int) (floatToRawIntBits & 4294967295L)) : Float.intBitsToFloat((int) (this.painter.mo561getIntrinsicSizeNHjbRc() & 4294967295L))) & 4294967295L);
            if (Float.intBitsToFloat((int) (floatToRawIntBits >> 32)) == 0.0f || Float.intBitsToFloat((int) (floatToRawIntBits & 4294967295L)) == 0.0f) {
                Size.Companion.getClass();
                floatToRawIntBits = 0;
            } else {
                floatToRawIntBits = ScaleFactorKt.m630timesUQTWf7w(floatToRawIntBits2, this.contentScale.mo606computeScaleFactorH7hwNQA(floatToRawIntBits2, floatToRawIntBits));
            }
        }
        return Constraints.m814copyZbe2FdA$default(j, ConstraintsKt.m832constrainWidthK40F9xA(Math.round(Float.intBitsToFloat((int) (floatToRawIntBits >> 32))), j), 0, ConstraintsKt.m831constrainHeightK40F9xA(Math.round(Float.intBitsToFloat((int) (floatToRawIntBits & 4294967295L))), j), 0, 10);
    }

    public final String toString() {
        return "PainterModifier(painter=" + this.painter + ", sizeToIntrinsics=" + this.sizeToIntrinsics + ", alignment=" + this.alignment + ", alpha=" + this.alpha + ", colorFilter=" + this.colorFilter + ')';
    }

    public PainterNode(Painter painter, boolean z, Alignment alignment, ContentScale contentScale, float f, ColorFilter colorFilter) {
        this.painter = painter;
        this.sizeToIntrinsics = z;
        this.alignment = alignment;
        this.contentScale = contentScale;
        this.alpha = f;
        this.colorFilter = colorFilter;
    }
}
