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
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
final class PainterNode extends Modifier.Node implements LayoutModifierNode, DrawModifierNode {
    public Alignment alignment;
    public float alpha;
    public ColorFilter colorFilter;
    public ContentScale contentScale;
    public Painter painter;
    public boolean sizeToIntrinsics;

    /* JADX WARN: Illegal instructions before constructor call */
    public PainterNode(Painter painter, boolean z, Alignment alignment, ContentScale contentScale, float f, ColorFilter colorFilter, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 4) != 0) {
            Alignment.Companion.getClass();
            alignment = Alignment.Companion.Center;
        }
        Alignment alignment2 = alignment;
        if ((i & 8) != 0) {
            ContentScale.Companion.getClass();
            contentScale = ContentScale.Companion.Inside;
        }
        this(painter, z, alignment2, contentScale, (i & 16) != 0 ? 1.0f : f, (i & 32) != 0 ? null : colorFilter);
    }

    /* renamed from: hasSpecifiedAndFiniteHeight-uvyYCjk, reason: not valid java name */
    public static boolean m363hasSpecifiedAndFiniteHeightuvyYCjk(long j) {
        Size.Companion.getClass();
        return !Size.m416equalsimpl0(j, Size.Unspecified) && (Float.floatToRawIntBits(Float.intBitsToFloat((int) (j & 4294967295L))) & Integer.MAX_VALUE) < 2139095040;
    }

    /* renamed from: hasSpecifiedAndFiniteWidth-uvyYCjk, reason: not valid java name */
    public static boolean m364hasSpecifiedAndFiniteWidthuvyYCjk(long j) {
        Size.Companion.getClass();
        return !Size.m416equalsimpl0(j, Size.Unspecified) && (Float.floatToRawIntBits(Float.intBitsToFloat((int) (j >> 32))) & Integer.MAX_VALUE) < 2139095040;
    }

    @Override // androidx.compose.ui.node.DrawModifierNode
    public final void draw(LayoutNodeDrawScope layoutNodeDrawScope) {
        long jM632timesUQTWf7w;
        long jMo563getIntrinsicSizeNHjbRc = this.painter.mo563getIntrinsicSizeNHjbRc();
        boolean zM364hasSpecifiedAndFiniteWidthuvyYCjk = m364hasSpecifiedAndFiniteWidthuvyYCjk(jMo563getIntrinsicSizeNHjbRc);
        CanvasDrawScope canvasDrawScope = layoutNodeDrawScope.canvasDrawScope;
        long jFloatToRawIntBits = (Float.floatToRawIntBits(zM364hasSpecifiedAndFiniteWidthuvyYCjk ? Float.intBitsToFloat((int) (jMo563getIntrinsicSizeNHjbRc >> 32)) : Float.intBitsToFloat((int) (canvasDrawScope.mo547getSizeNHjbRc() >> 32))) << 32) | (Float.floatToRawIntBits(m363hasSpecifiedAndFiniteHeightuvyYCjk(jMo563getIntrinsicSizeNHjbRc) ? Float.intBitsToFloat((int) (jMo563getIntrinsicSizeNHjbRc & 4294967295L)) : Float.intBitsToFloat((int) (canvasDrawScope.mo547getSizeNHjbRc() & 4294967295L))) & 4294967295L);
        if (Float.intBitsToFloat((int) (canvasDrawScope.mo547getSizeNHjbRc() >> 32)) == 0.0f || Float.intBitsToFloat((int) (canvasDrawScope.mo547getSizeNHjbRc() & 4294967295L)) == 0.0f) {
            Size.Companion.getClass();
            jM632timesUQTWf7w = 0;
        } else {
            jM632timesUQTWf7w = ScaleFactorKt.m632timesUQTWf7w(jFloatToRawIntBits, this.contentScale.mo608computeScaleFactorH7hwNQA(jFloatToRawIntBits, canvasDrawScope.mo547getSizeNHjbRc()));
        }
        IntSize.Companion companion = IntSize.Companion;
        long jMo353alignKFBX0sM = this.alignment.mo353alignKFBX0sM((Math.round(Float.intBitsToFloat((int) (jM632timesUQTWf7w >> 32))) << 32) | (Math.round(Float.intBitsToFloat((int) (jM632timesUQTWf7w & 4294967295L))) & 4294967295L), (Math.round(Float.intBitsToFloat((int) (canvasDrawScope.mo547getSizeNHjbRc() >> 32))) << 32) | (Math.round(Float.intBitsToFloat((int) (canvasDrawScope.mo547getSizeNHjbRc() & 4294967295L))) & 4294967295L), layoutNodeDrawScope.getLayoutDirection());
        IntOffset.Companion companion2 = IntOffset.Companion;
        float f = (int) (jMo353alignKFBX0sM >> 32);
        float f2 = (int) (jMo353alignKFBX0sM & 4294967295L);
        canvasDrawScope.drawContext.transform.translate(f, f2);
        try {
            this.painter.m564drawx_KDEd0(layoutNodeDrawScope, jM632timesUQTWf7w, this.alpha, this.colorFilter);
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
        return this.sizeToIntrinsics && this.painter.mo563getIntrinsicSizeNHjbRc() != 9205357640488583168L;
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int maxIntrinsicHeight(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        if (!getUseIntrinsicSize()) {
            return intrinsicMeasurable.maxIntrinsicHeight(i);
        }
        long jM365modifyConstraintsZezNO4M = m365modifyConstraintsZezNO4M(ConstraintsKt.Constraints$default(0, i, 0, 0, 13));
        return Math.max(Constraints.m824getMinHeightimpl(jM365modifyConstraintsZezNO4M), intrinsicMeasurable.maxIntrinsicHeight(i));
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int maxIntrinsicWidth(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        if (!getUseIntrinsicSize()) {
            return intrinsicMeasurable.maxIntrinsicWidth(i);
        }
        long jM365modifyConstraintsZezNO4M = m365modifyConstraintsZezNO4M(ConstraintsKt.Constraints$default(0, 0, 0, i, 7));
        return Math.max(Constraints.m825getMinWidthimpl(jM365modifyConstraintsZezNO4M), intrinsicMeasurable.maxIntrinsicWidth(i));
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo4measure3p2s80s(MeasureScope measureScope, Measurable measurable, long j) {
        final Placeable placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(m365modifyConstraintsZezNO4M(j));
        return measureScope.layout$1(placeableMo610measureBRTryo0.width, placeableMo610measureBRTryo0.height, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.ui.draw.PainterNode$measure$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                ((Placeable.PlacementScope) obj).placeRelative(placeableMo610measureBRTryo0, 0, 0, 0.0f);
                return Unit.INSTANCE;
            }
        });
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int minIntrinsicHeight(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        if (!getUseIntrinsicSize()) {
            return intrinsicMeasurable.minIntrinsicHeight(i);
        }
        long jM365modifyConstraintsZezNO4M = m365modifyConstraintsZezNO4M(ConstraintsKt.Constraints$default(0, i, 0, 0, 13));
        return Math.max(Constraints.m824getMinHeightimpl(jM365modifyConstraintsZezNO4M), intrinsicMeasurable.minIntrinsicHeight(i));
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int minIntrinsicWidth(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        if (!getUseIntrinsicSize()) {
            return intrinsicMeasurable.minIntrinsicWidth(i);
        }
        long jM365modifyConstraintsZezNO4M = m365modifyConstraintsZezNO4M(ConstraintsKt.Constraints$default(0, 0, 0, i, 7));
        return Math.max(Constraints.m825getMinWidthimpl(jM365modifyConstraintsZezNO4M), intrinsicMeasurable.minIntrinsicWidth(i));
    }

    /* renamed from: modifyConstraints-ZezNO4M, reason: not valid java name */
    public final long m365modifyConstraintsZezNO4M(long j) {
        boolean z = false;
        boolean z2 = Constraints.m819getHasBoundedWidthimpl(j) && Constraints.m818getHasBoundedHeightimpl(j);
        if (Constraints.m821getHasFixedWidthimpl(j) && Constraints.m820getHasFixedHeightimpl(j)) {
            z = true;
        }
        if ((!getUseIntrinsicSize() && z2) || z) {
            return Constraints.m816copyZbe2FdA$default(j, Constraints.m823getMaxWidthimpl(j), 0, Constraints.m822getMaxHeightimpl(j), 0, 10);
        }
        long jMo563getIntrinsicSizeNHjbRc = this.painter.mo563getIntrinsicSizeNHjbRc();
        int iRound = m364hasSpecifiedAndFiniteWidthuvyYCjk(jMo563getIntrinsicSizeNHjbRc) ? Math.round(Float.intBitsToFloat((int) (jMo563getIntrinsicSizeNHjbRc >> 32))) : Constraints.m825getMinWidthimpl(j);
        int iRound2 = m363hasSpecifiedAndFiniteHeightuvyYCjk(jMo563getIntrinsicSizeNHjbRc) ? Math.round(Float.intBitsToFloat((int) (jMo563getIntrinsicSizeNHjbRc & 4294967295L))) : Constraints.m824getMinHeightimpl(j);
        int iM834constrainWidthK40F9xA = ConstraintsKt.m834constrainWidthK40F9xA(iRound, j);
        long jFloatToRawIntBits = (Float.floatToRawIntBits(ConstraintsKt.m833constrainHeightK40F9xA(iRound2, j)) & 4294967295L) | (Float.floatToRawIntBits(iM834constrainWidthK40F9xA) << 32);
        if (getUseIntrinsicSize()) {
            long jFloatToRawIntBits2 = (Float.floatToRawIntBits(!m364hasSpecifiedAndFiniteWidthuvyYCjk(this.painter.mo563getIntrinsicSizeNHjbRc()) ? Float.intBitsToFloat((int) (jFloatToRawIntBits >> 32)) : Float.intBitsToFloat((int) (this.painter.mo563getIntrinsicSizeNHjbRc() >> 32))) << 32) | (Float.floatToRawIntBits(!m363hasSpecifiedAndFiniteHeightuvyYCjk(this.painter.mo563getIntrinsicSizeNHjbRc()) ? Float.intBitsToFloat((int) (jFloatToRawIntBits & 4294967295L)) : Float.intBitsToFloat((int) (this.painter.mo563getIntrinsicSizeNHjbRc() & 4294967295L))) & 4294967295L);
            if (Float.intBitsToFloat((int) (jFloatToRawIntBits >> 32)) == 0.0f || Float.intBitsToFloat((int) (jFloatToRawIntBits & 4294967295L)) == 0.0f) {
                Size.Companion.getClass();
                jFloatToRawIntBits = 0;
            } else {
                jFloatToRawIntBits = ScaleFactorKt.m632timesUQTWf7w(jFloatToRawIntBits2, this.contentScale.mo608computeScaleFactorH7hwNQA(jFloatToRawIntBits2, jFloatToRawIntBits));
            }
        }
        return Constraints.m816copyZbe2FdA$default(j, ConstraintsKt.m834constrainWidthK40F9xA(Math.round(Float.intBitsToFloat((int) (jFloatToRawIntBits >> 32))), j), 0, ConstraintsKt.m833constrainHeightK40F9xA(Math.round(Float.intBitsToFloat((int) (jFloatToRawIntBits & 4294967295L))), j), 0, 10);
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
