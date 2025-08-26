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

/* loaded from: classes.dex */
final class UnspecifiedConstraintsNode extends Modifier.Node implements LayoutModifierNode {
    public float minHeight;
    public float minWidth;

    public /* synthetic */ UnspecifiedConstraintsNode(float f, float f2, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, f2);
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int maxIntrinsicHeight(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        int iMaxIntrinsicHeight = intrinsicMeasurable.maxIntrinsicHeight(i);
        float f = this.minHeight;
        Dp.Companion.getClass();
        int iMo52roundToPx0680j_4 = !Dp.m838equalsimpl0(f, Dp.Unspecified) ? lookaheadCapablePlaceable.mo52roundToPx0680j_4(this.minHeight) : 0;
        return iMaxIntrinsicHeight < iMo52roundToPx0680j_4 ? iMo52roundToPx0680j_4 : iMaxIntrinsicHeight;
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int maxIntrinsicWidth(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        int iMaxIntrinsicWidth = intrinsicMeasurable.maxIntrinsicWidth(i);
        float f = this.minWidth;
        Dp.Companion.getClass();
        int iMo52roundToPx0680j_4 = !Dp.m838equalsimpl0(f, Dp.Unspecified) ? lookaheadCapablePlaceable.mo52roundToPx0680j_4(this.minWidth) : 0;
        return iMaxIntrinsicWidth < iMo52roundToPx0680j_4 ? iMo52roundToPx0680j_4 : iMaxIntrinsicWidth;
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo4measure3p2s80s(MeasureScope measureScope, Measurable measurable, long j) {
        int iM825getMinWidthimpl;
        float f = this.minWidth;
        Dp.Companion.getClass();
        float f2 = Dp.Unspecified;
        int iM824getMinHeightimpl = 0;
        if (Dp.m838equalsimpl0(f, f2) || Constraints.m825getMinWidthimpl(j) != 0) {
            iM825getMinWidthimpl = Constraints.m825getMinWidthimpl(j);
        } else {
            iM825getMinWidthimpl = measureScope.mo52roundToPx0680j_4(this.minWidth);
            int iM823getMaxWidthimpl = Constraints.m823getMaxWidthimpl(j);
            if (iM825getMinWidthimpl > iM823getMaxWidthimpl) {
                iM825getMinWidthimpl = iM823getMaxWidthimpl;
            }
            if (iM825getMinWidthimpl < 0) {
                iM825getMinWidthimpl = 0;
            }
        }
        int iM823getMaxWidthimpl2 = Constraints.m823getMaxWidthimpl(j);
        if (Dp.m838equalsimpl0(this.minHeight, f2) || Constraints.m824getMinHeightimpl(j) != 0) {
            iM824getMinHeightimpl = Constraints.m824getMinHeightimpl(j);
        } else {
            int iMo52roundToPx0680j_4 = measureScope.mo52roundToPx0680j_4(this.minHeight);
            int iM822getMaxHeightimpl = Constraints.m822getMaxHeightimpl(j);
            if (iMo52roundToPx0680j_4 > iM822getMaxHeightimpl) {
                iMo52roundToPx0680j_4 = iM822getMaxHeightimpl;
            }
            if (iMo52roundToPx0680j_4 >= 0) {
                iM824getMinHeightimpl = iMo52roundToPx0680j_4;
            }
        }
        final Placeable placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(ConstraintsKt.Constraints(iM825getMinWidthimpl, iM823getMaxWidthimpl2, iM824getMinHeightimpl, Constraints.m822getMaxHeightimpl(j)));
        return measureScope.layout$1(placeableMo610measureBRTryo0.width, placeableMo610measureBRTryo0.height, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.foundation.layout.UnspecifiedConstraintsNode$measure$1
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
        int iMinIntrinsicHeight = intrinsicMeasurable.minIntrinsicHeight(i);
        float f = this.minHeight;
        Dp.Companion.getClass();
        int iMo52roundToPx0680j_4 = !Dp.m838equalsimpl0(f, Dp.Unspecified) ? lookaheadCapablePlaceable.mo52roundToPx0680j_4(this.minHeight) : 0;
        return iMinIntrinsicHeight < iMo52roundToPx0680j_4 ? iMo52roundToPx0680j_4 : iMinIntrinsicHeight;
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int minIntrinsicWidth(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        int iMinIntrinsicWidth = intrinsicMeasurable.minIntrinsicWidth(i);
        float f = this.minWidth;
        Dp.Companion.getClass();
        int iMo52roundToPx0680j_4 = !Dp.m838equalsimpl0(f, Dp.Unspecified) ? lookaheadCapablePlaceable.mo52roundToPx0680j_4(this.minWidth) : 0;
        return iMinIntrinsicWidth < iMo52roundToPx0680j_4 ? iMo52roundToPx0680j_4 : iMinIntrinsicWidth;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public UnspecifiedConstraintsNode(float f, float f2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 1) != 0) {
            Dp.Companion.getClass();
            f = Dp.Unspecified;
        }
        if ((i & 2) != 0) {
            Dp.Companion.getClass();
            f2 = Dp.Unspecified;
        }
        this(f, f2, null);
    }

    private UnspecifiedConstraintsNode(float f, float f2) {
        this.minWidth = f;
        this.minHeight = f2;
    }
}
