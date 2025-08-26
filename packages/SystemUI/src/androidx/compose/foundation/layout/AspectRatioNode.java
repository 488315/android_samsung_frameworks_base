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
import androidx.compose.ui.unit.IntSize;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;

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

    /* JADX WARN: Removed duplicated region for block: B:52:0x00ca  */
    @Override // androidx.compose.ui.node.LayoutModifierNode
    /* renamed from: measure-3p2s80s */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final MeasureResult mo4measure3p2s80s(MeasureScope measureScope, Measurable measurable, long j) {
        long jM97tryMaxHeightJN0ABg;
        if (this.matchHeightConstraintsFirst) {
            jM97tryMaxHeightJN0ABg = m97tryMaxHeightJN0ABg(j, true);
            IntSize.Companion.getClass();
            if (IntSize.m863equalsimpl0(jM97tryMaxHeightJN0ABg, 0L)) {
                jM97tryMaxHeightJN0ABg = m98tryMaxWidthJN0ABg(j, true);
                if (IntSize.m863equalsimpl0(jM97tryMaxHeightJN0ABg, 0L)) {
                    jM97tryMaxHeightJN0ABg = m99tryMinHeightJN0ABg(j, true);
                    if (IntSize.m863equalsimpl0(jM97tryMaxHeightJN0ABg, 0L)) {
                        jM97tryMaxHeightJN0ABg = m100tryMinWidthJN0ABg(j, true);
                        if (IntSize.m863equalsimpl0(jM97tryMaxHeightJN0ABg, 0L)) {
                            jM97tryMaxHeightJN0ABg = m97tryMaxHeightJN0ABg(j, false);
                            if (IntSize.m863equalsimpl0(jM97tryMaxHeightJN0ABg, 0L)) {
                                jM97tryMaxHeightJN0ABg = m98tryMaxWidthJN0ABg(j, false);
                                if (IntSize.m863equalsimpl0(jM97tryMaxHeightJN0ABg, 0L)) {
                                    jM97tryMaxHeightJN0ABg = m99tryMinHeightJN0ABg(j, false);
                                    if (IntSize.m863equalsimpl0(jM97tryMaxHeightJN0ABg, 0L)) {
                                        jM97tryMaxHeightJN0ABg = m100tryMinWidthJN0ABg(j, false);
                                        if (IntSize.m863equalsimpl0(jM97tryMaxHeightJN0ABg, 0L)) {
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            jM97tryMaxHeightJN0ABg = m98tryMaxWidthJN0ABg(j, true);
            IntSize.Companion.getClass();
            if (IntSize.m863equalsimpl0(jM97tryMaxHeightJN0ABg, 0L)) {
                jM97tryMaxHeightJN0ABg = m97tryMaxHeightJN0ABg(j, true);
                if (IntSize.m863equalsimpl0(jM97tryMaxHeightJN0ABg, 0L)) {
                    jM97tryMaxHeightJN0ABg = m100tryMinWidthJN0ABg(j, true);
                    if (IntSize.m863equalsimpl0(jM97tryMaxHeightJN0ABg, 0L)) {
                        jM97tryMaxHeightJN0ABg = m99tryMinHeightJN0ABg(j, true);
                        if (IntSize.m863equalsimpl0(jM97tryMaxHeightJN0ABg, 0L)) {
                            jM97tryMaxHeightJN0ABg = m98tryMaxWidthJN0ABg(j, false);
                            if (IntSize.m863equalsimpl0(jM97tryMaxHeightJN0ABg, 0L)) {
                                jM97tryMaxHeightJN0ABg = m97tryMaxHeightJN0ABg(j, false);
                                if (IntSize.m863equalsimpl0(jM97tryMaxHeightJN0ABg, 0L)) {
                                    jM97tryMaxHeightJN0ABg = m100tryMinWidthJN0ABg(j, false);
                                    if (IntSize.m863equalsimpl0(jM97tryMaxHeightJN0ABg, 0L)) {
                                        jM97tryMaxHeightJN0ABg = m99tryMinHeightJN0ABg(j, false);
                                        if (IntSize.m863equalsimpl0(jM97tryMaxHeightJN0ABg, 0L)) {
                                            IntSize.Companion.getClass();
                                            jM97tryMaxHeightJN0ABg = 0;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        IntSize.Companion.getClass();
        if (!IntSize.m863equalsimpl0(jM97tryMaxHeightJN0ABg, 0L)) {
            Constraints.Companion.getClass();
            j = Constraints.Companion.m829fixedJhjzzOo((int) (jM97tryMaxHeightJN0ABg >> 32), (int) (4294967295L & jM97tryMaxHeightJN0ABg));
        }
        final Placeable placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(j);
        return measureScope.layout$1(placeableMo610measureBRTryo0.width, placeableMo610measureBRTryo0.height, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.foundation.layout.AspectRatioNode$measure$1
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
        return i != Integer.MAX_VALUE ? Math.round(i / this.aspectRatio) : intrinsicMeasurable.minIntrinsicHeight(i);
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int minIntrinsicWidth(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        return i != Integer.MAX_VALUE ? Math.round(i * this.aspectRatio) : intrinsicMeasurable.minIntrinsicWidth(i);
    }

    /* renamed from: tryMaxHeight-JN-0ABg, reason: not valid java name */
    public final long m97tryMaxHeightJN0ABg(long j, boolean z) {
        int iRound;
        int iM822getMaxHeightimpl = Constraints.m822getMaxHeightimpl(j);
        if (iM822getMaxHeightimpl == Integer.MAX_VALUE || (iRound = Math.round(iM822getMaxHeightimpl * this.aspectRatio)) <= 0 || (z && !AspectRatioKt.m96isSatisfiedByNN6EwU(iRound, iM822getMaxHeightimpl, j))) {
            IntSize.Companion.getClass();
            return 0L;
        }
        long j2 = (iRound << 32) | (iM822getMaxHeightimpl & 4294967295L);
        IntSize.Companion companion = IntSize.Companion;
        return j2;
    }

    /* renamed from: tryMaxWidth-JN-0ABg, reason: not valid java name */
    public final long m98tryMaxWidthJN0ABg(long j, boolean z) {
        int iRound;
        int iM823getMaxWidthimpl = Constraints.m823getMaxWidthimpl(j);
        if (iM823getMaxWidthimpl == Integer.MAX_VALUE || (iRound = Math.round(iM823getMaxWidthimpl / this.aspectRatio)) <= 0 || (z && !AspectRatioKt.m96isSatisfiedByNN6EwU(iM823getMaxWidthimpl, iRound, j))) {
            IntSize.Companion.getClass();
            return 0L;
        }
        long j2 = (iM823getMaxWidthimpl << 32) | (iRound & 4294967295L);
        IntSize.Companion companion = IntSize.Companion;
        return j2;
    }

    /* renamed from: tryMinHeight-JN-0ABg, reason: not valid java name */
    public final long m99tryMinHeightJN0ABg(long j, boolean z) {
        int iM824getMinHeightimpl = Constraints.m824getMinHeightimpl(j);
        int iRound = Math.round(iM824getMinHeightimpl * this.aspectRatio);
        if (iRound <= 0 || (z && !AspectRatioKt.m96isSatisfiedByNN6EwU(iRound, iM824getMinHeightimpl, j))) {
            IntSize.Companion.getClass();
            return 0L;
        }
        long j2 = (iRound << 32) | (iM824getMinHeightimpl & 4294967295L);
        IntSize.Companion companion = IntSize.Companion;
        return j2;
    }

    /* renamed from: tryMinWidth-JN-0ABg, reason: not valid java name */
    public final long m100tryMinWidthJN0ABg(long j, boolean z) {
        int iM825getMinWidthimpl = Constraints.m825getMinWidthimpl(j);
        int iRound = Math.round(iM825getMinWidthimpl / this.aspectRatio);
        if (iRound <= 0 || (z && !AspectRatioKt.m96isSatisfiedByNN6EwU(iM825getMinWidthimpl, iRound, j))) {
            IntSize.Companion.getClass();
            return 0L;
        }
        long j2 = (iM825getMinWidthimpl << 32) | (iRound & 4294967295L);
        IntSize.Companion companion = IntSize.Companion;
        return j2;
    }
}
