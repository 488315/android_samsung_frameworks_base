package androidx.compose.foundation.layout;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.IntrinsicMeasurable;
import androidx.compose.ui.layout.IntrinsicMeasureScope;
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

    /* JADX WARN: Removed duplicated region for block: B:23:0x0048  */
    /* renamed from: getTargetConstraints-OenEA2s, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final long m147getTargetConstraintsOenEA2s(IntrinsicMeasureScope intrinsicMeasureScope) {
        int iMo52roundToPx0680j_4;
        int iMo52roundToPx0680j_42;
        int iMo52roundToPx0680j_43;
        float f = this.maxWidth;
        Dp.Companion.getClass();
        float f2 = Dp.Unspecified;
        int i = 0;
        if (Dp.m838equalsimpl0(f, f2)) {
            iMo52roundToPx0680j_4 = Integer.MAX_VALUE;
        } else {
            iMo52roundToPx0680j_4 = intrinsicMeasureScope.mo52roundToPx0680j_4(this.maxWidth);
            if (iMo52roundToPx0680j_4 < 0) {
                iMo52roundToPx0680j_4 = 0;
            }
        }
        if (Dp.m838equalsimpl0(this.maxHeight, f2)) {
            iMo52roundToPx0680j_42 = Integer.MAX_VALUE;
        } else {
            iMo52roundToPx0680j_42 = intrinsicMeasureScope.mo52roundToPx0680j_4(this.maxHeight);
            if (iMo52roundToPx0680j_42 < 0) {
                iMo52roundToPx0680j_42 = 0;
            }
        }
        if (Dp.m838equalsimpl0(this.minWidth, f2)) {
            iMo52roundToPx0680j_43 = 0;
        } else {
            iMo52roundToPx0680j_43 = intrinsicMeasureScope.mo52roundToPx0680j_4(this.minWidth);
            if (iMo52roundToPx0680j_43 > iMo52roundToPx0680j_4) {
                iMo52roundToPx0680j_43 = iMo52roundToPx0680j_4;
            }
            if (iMo52roundToPx0680j_43 < 0) {
                iMo52roundToPx0680j_43 = 0;
            }
            if (iMo52roundToPx0680j_43 == Integer.MAX_VALUE) {
            }
        }
        if (!Dp.m838equalsimpl0(this.minHeight, f2)) {
            int iMo52roundToPx0680j_44 = intrinsicMeasureScope.mo52roundToPx0680j_4(this.minHeight);
            if (iMo52roundToPx0680j_44 > iMo52roundToPx0680j_42) {
                iMo52roundToPx0680j_44 = iMo52roundToPx0680j_42;
            }
            if (iMo52roundToPx0680j_44 < 0) {
                iMo52roundToPx0680j_44 = 0;
            }
            if (iMo52roundToPx0680j_44 != Integer.MAX_VALUE) {
                i = iMo52roundToPx0680j_44;
            }
        }
        return ConstraintsKt.Constraints(iMo52roundToPx0680j_43, iMo52roundToPx0680j_4, i, iMo52roundToPx0680j_42);
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int maxIntrinsicHeight(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        long jM147getTargetConstraintsOenEA2s = m147getTargetConstraintsOenEA2s(lookaheadCapablePlaceable);
        if (Constraints.m820getHasFixedHeightimpl(jM147getTargetConstraintsOenEA2s)) {
            return Constraints.m822getMaxHeightimpl(jM147getTargetConstraintsOenEA2s);
        }
        if (!this.enforceIncoming) {
            i = ConstraintsKt.m834constrainWidthK40F9xA(i, jM147getTargetConstraintsOenEA2s);
        }
        return ConstraintsKt.m833constrainHeightK40F9xA(intrinsicMeasurable.maxIntrinsicHeight(i), jM147getTargetConstraintsOenEA2s);
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int maxIntrinsicWidth(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        long jM147getTargetConstraintsOenEA2s = m147getTargetConstraintsOenEA2s(lookaheadCapablePlaceable);
        if (Constraints.m821getHasFixedWidthimpl(jM147getTargetConstraintsOenEA2s)) {
            return Constraints.m823getMaxWidthimpl(jM147getTargetConstraintsOenEA2s);
        }
        if (!this.enforceIncoming) {
            i = ConstraintsKt.m833constrainHeightK40F9xA(i, jM147getTargetConstraintsOenEA2s);
        }
        return ConstraintsKt.m834constrainWidthK40F9xA(intrinsicMeasurable.maxIntrinsicWidth(i), jM147getTargetConstraintsOenEA2s);
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo4measure3p2s80s(MeasureScope measureScope, Measurable measurable, long j) {
        int iM825getMinWidthimpl;
        int iM823getMaxWidthimpl;
        int iM824getMinHeightimpl;
        int iM822getMaxHeightimpl;
        long jConstraints;
        long jM147getTargetConstraintsOenEA2s = m147getTargetConstraintsOenEA2s(measureScope);
        if (this.enforceIncoming) {
            jConstraints = ConstraintsKt.m832constrainN9IONVI(j, jM147getTargetConstraintsOenEA2s);
        } else {
            float f = this.minWidth;
            Dp.Companion.getClass();
            float f2 = Dp.Unspecified;
            if (Dp.m838equalsimpl0(f, f2)) {
                iM825getMinWidthimpl = Constraints.m825getMinWidthimpl(j);
                int iM823getMaxWidthimpl2 = Constraints.m823getMaxWidthimpl(jM147getTargetConstraintsOenEA2s);
                if (iM825getMinWidthimpl > iM823getMaxWidthimpl2) {
                    iM825getMinWidthimpl = iM823getMaxWidthimpl2;
                }
            } else {
                iM825getMinWidthimpl = Constraints.m825getMinWidthimpl(jM147getTargetConstraintsOenEA2s);
            }
            if (Dp.m838equalsimpl0(this.maxWidth, f2)) {
                iM823getMaxWidthimpl = Constraints.m823getMaxWidthimpl(j);
                int iM825getMinWidthimpl2 = Constraints.m825getMinWidthimpl(jM147getTargetConstraintsOenEA2s);
                if (iM823getMaxWidthimpl < iM825getMinWidthimpl2) {
                    iM823getMaxWidthimpl = iM825getMinWidthimpl2;
                }
            } else {
                iM823getMaxWidthimpl = Constraints.m823getMaxWidthimpl(jM147getTargetConstraintsOenEA2s);
            }
            if (Dp.m838equalsimpl0(this.minHeight, f2)) {
                iM824getMinHeightimpl = Constraints.m824getMinHeightimpl(j);
                int iM822getMaxHeightimpl2 = Constraints.m822getMaxHeightimpl(jM147getTargetConstraintsOenEA2s);
                if (iM824getMinHeightimpl > iM822getMaxHeightimpl2) {
                    iM824getMinHeightimpl = iM822getMaxHeightimpl2;
                }
            } else {
                iM824getMinHeightimpl = Constraints.m824getMinHeightimpl(jM147getTargetConstraintsOenEA2s);
            }
            if (Dp.m838equalsimpl0(this.maxHeight, f2)) {
                iM822getMaxHeightimpl = Constraints.m822getMaxHeightimpl(j);
                int iM824getMinHeightimpl2 = Constraints.m824getMinHeightimpl(jM147getTargetConstraintsOenEA2s);
                if (iM822getMaxHeightimpl < iM824getMinHeightimpl2) {
                    iM822getMaxHeightimpl = iM824getMinHeightimpl2;
                }
            } else {
                iM822getMaxHeightimpl = Constraints.m822getMaxHeightimpl(jM147getTargetConstraintsOenEA2s);
            }
            jConstraints = ConstraintsKt.Constraints(iM825getMinWidthimpl, iM823getMaxWidthimpl, iM824getMinHeightimpl, iM822getMaxHeightimpl);
        }
        final Placeable placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(jConstraints);
        return measureScope.layout$1(placeableMo610measureBRTryo0.width, placeableMo610measureBRTryo0.height, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.foundation.layout.SizeNode$measure$1
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
        long jM147getTargetConstraintsOenEA2s = m147getTargetConstraintsOenEA2s(lookaheadCapablePlaceable);
        if (Constraints.m820getHasFixedHeightimpl(jM147getTargetConstraintsOenEA2s)) {
            return Constraints.m822getMaxHeightimpl(jM147getTargetConstraintsOenEA2s);
        }
        if (!this.enforceIncoming) {
            i = ConstraintsKt.m834constrainWidthK40F9xA(i, jM147getTargetConstraintsOenEA2s);
        }
        return ConstraintsKt.m833constrainHeightK40F9xA(intrinsicMeasurable.minIntrinsicHeight(i), jM147getTargetConstraintsOenEA2s);
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int minIntrinsicWidth(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        long jM147getTargetConstraintsOenEA2s = m147getTargetConstraintsOenEA2s(lookaheadCapablePlaceable);
        if (Constraints.m821getHasFixedWidthimpl(jM147getTargetConstraintsOenEA2s)) {
            return Constraints.m823getMaxWidthimpl(jM147getTargetConstraintsOenEA2s);
        }
        if (!this.enforceIncoming) {
            i = ConstraintsKt.m833constrainHeightK40F9xA(i, jM147getTargetConstraintsOenEA2s);
        }
        return ConstraintsKt.m834constrainWidthK40F9xA(intrinsicMeasurable.minIntrinsicWidth(i), jM147getTargetConstraintsOenEA2s);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public SizeNode(float f, float f2, float f3, float f4, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 1) != 0) {
            Dp.Companion.getClass();
            f = Dp.Unspecified;
        }
        float f5 = f;
        if ((i & 2) != 0) {
            Dp.Companion.getClass();
            f2 = Dp.Unspecified;
        }
        float f6 = f2;
        if ((i & 4) != 0) {
            Dp.Companion.getClass();
            f3 = Dp.Unspecified;
        }
        float f7 = f3;
        if ((i & 8) != 0) {
            Dp.Companion.getClass();
            f4 = Dp.Unspecified;
        }
        this(f5, f6, f7, f4, z, null);
    }

    private SizeNode(float f, float f2, float f3, float f4, boolean z) {
        this.minWidth = f;
        this.minHeight = f2;
        this.maxWidth = f3;
        this.maxHeight = f4;
        this.enforceIncoming = z;
    }
}
