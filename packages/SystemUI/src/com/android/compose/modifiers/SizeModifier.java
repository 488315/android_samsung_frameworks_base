package com.android.compose.modifiers;

import androidx.compose.ui.layout.IntrinsicMeasurable;
import androidx.compose.ui.layout.LayoutModifier;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.LookaheadCapablePlaceable;
import androidx.compose.ui.platform.InspectorValueInfo;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SizeModifier extends InspectorValueInfo implements LayoutModifier {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final boolean enforceIncoming;
    public final Function1 maxHeight;
    public final Function1 maxWidth;
    public final Function1 minHeight;
    public final Function1 minWidth;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ SizeModifier(kotlin.jvm.functions.Function1 r2, kotlin.jvm.functions.Function1 r3, kotlin.jvm.functions.Function1 r4, kotlin.jvm.functions.Function1 r5, boolean r6, kotlin.jvm.functions.Function1 r7, int r8, kotlin.jvm.internal.DefaultConstructorMarker r9) {
        /*
            r1 = this;
            r9 = r8 & 1
            com.android.compose.modifiers.PaddingKt$$ExternalSyntheticLambda0 r0 = com.android.compose.modifiers.SizeKt.SizeUnspecified
            if (r9 == 0) goto L7
            r2 = r0
        L7:
            r9 = r8 & 2
            if (r9 == 0) goto Lc
            r3 = r0
        Lc:
            r9 = r8 & 4
            if (r9 == 0) goto L11
            r4 = r0
        L11:
            r8 = r8 & 8
            if (r8 == 0) goto L16
            r5 = r0
        L16:
            r1.<init>(r2, r3, r4, r5, r6, r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.compose.modifiers.SizeModifier.<init>(kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1, boolean, kotlin.jvm.functions.Function1, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof SizeModifier)) {
            return false;
        }
        SizeModifier sizeModifier = (SizeModifier) obj;
        return Intrinsics.areEqual(this.minWidth, sizeModifier.minWidth) && Intrinsics.areEqual(this.minHeight, sizeModifier.minHeight) && Intrinsics.areEqual(this.maxWidth, sizeModifier.maxWidth) && Intrinsics.areEqual(this.maxHeight, sizeModifier.maxHeight) && this.enforceIncoming == sizeModifier.enforceIncoming;
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x004c, code lost:
    
        if (r5 != Integer.MAX_VALUE) goto L24;
     */
    /* renamed from: getTargetConstraints-OenEA2s, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final long m938getTargetConstraintsOenEA2s(androidx.compose.ui.layout.IntrinsicMeasureScope r8) {
        /*
            r7 = this;
            com.android.compose.modifiers.PaddingKt$$ExternalSyntheticLambda0 r0 = com.android.compose.modifiers.SizeKt.SizeUnspecified
            kotlin.jvm.functions.Function1 r1 = r7.maxWidth
            boolean r2 = kotlin.jvm.internal.Intrinsics.areEqual(r1, r0)
            r3 = 2147483647(0x7fffffff, float:NaN)
            r4 = 0
            if (r2 != 0) goto L1c
            java.lang.Object r1 = r1.mo779invoke(r8)
            java.lang.Number r1 = (java.lang.Number) r1
            int r1 = r1.intValue()
            if (r1 >= 0) goto L1d
            r1 = r4
            goto L1d
        L1c:
            r1 = r3
        L1d:
            kotlin.jvm.functions.Function1 r2 = r7.maxHeight
            boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual(r2, r0)
            if (r5 != 0) goto L33
            java.lang.Object r2 = r2.mo779invoke(r8)
            java.lang.Number r2 = (java.lang.Number) r2
            int r2 = r2.intValue()
            if (r2 >= 0) goto L34
            r2 = r4
            goto L34
        L33:
            r2 = r3
        L34:
            kotlin.jvm.functions.Function1 r5 = r7.minWidth
            boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r0)
            if (r6 != 0) goto L4f
            java.lang.Object r5 = r5.mo779invoke(r8)
            java.lang.Number r5 = (java.lang.Number) r5
            int r5 = r5.intValue()
            if (r5 <= r1) goto L49
            r5 = r1
        L49:
            if (r5 >= 0) goto L4c
            r5 = r4
        L4c:
            if (r5 == r3) goto L4f
            goto L50
        L4f:
            r5 = r4
        L50:
            kotlin.jvm.functions.Function1 r7 = r7.minHeight
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r7, r0)
            if (r0 != 0) goto L6b
            java.lang.Object r7 = r7.mo779invoke(r8)
            java.lang.Number r7 = (java.lang.Number) r7
            int r7 = r7.intValue()
            if (r7 <= r2) goto L65
            r7 = r2
        L65:
            if (r7 >= 0) goto L68
            r7 = r4
        L68:
            if (r7 == r3) goto L6b
            r4 = r7
        L6b:
            long r7 = androidx.compose.ui.unit.ConstraintsKt.Constraints(r5, r1, r4, r2)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.compose.modifiers.SizeModifier.m938getTargetConstraintsOenEA2s(androidx.compose.ui.layout.IntrinsicMeasureScope):long");
    }

    public final int hashCode() {
        return (this.maxHeight.hashCode() + ((this.maxWidth.hashCode() + ((this.minHeight.hashCode() + (this.minWidth.hashCode() * 31)) * 31)) * 31)) * 31;
    }

    @Override // androidx.compose.ui.layout.LayoutModifier
    public final int maxIntrinsicHeight(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        long m938getTargetConstraintsOenEA2s = m938getTargetConstraintsOenEA2s(lookaheadCapablePlaceable);
        return Constraints.m818getHasFixedHeightimpl(m938getTargetConstraintsOenEA2s) ? Constraints.m820getMaxHeightimpl(m938getTargetConstraintsOenEA2s) : ConstraintsKt.m831constrainHeightK40F9xA(intrinsicMeasurable.maxIntrinsicHeight(i), m938getTargetConstraintsOenEA2s);
    }

    @Override // androidx.compose.ui.layout.LayoutModifier
    public final int maxIntrinsicWidth(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        long m938getTargetConstraintsOenEA2s = m938getTargetConstraintsOenEA2s(lookaheadCapablePlaceable);
        return Constraints.m819getHasFixedWidthimpl(m938getTargetConstraintsOenEA2s) ? Constraints.m821getMaxWidthimpl(m938getTargetConstraintsOenEA2s) : ConstraintsKt.m832constrainWidthK40F9xA(intrinsicMeasurable.maxIntrinsicWidth(i), m938getTargetConstraintsOenEA2s);
    }

    @Override // androidx.compose.ui.layout.LayoutModifier
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo108measure3p2s80s(MeasureScope measureScope, Measurable measurable, long j) {
        int m823getMinWidthimpl;
        int m821getMaxWidthimpl;
        int m822getMinHeightimpl;
        int m820getMaxHeightimpl;
        long Constraints;
        MeasureResult layout$1;
        long m938getTargetConstraintsOenEA2s = m938getTargetConstraintsOenEA2s(measureScope);
        if (this.enforceIncoming) {
            Constraints = ConstraintsKt.m830constrainN9IONVI(j, m938getTargetConstraintsOenEA2s);
        } else {
            PaddingKt$$ExternalSyntheticLambda0 paddingKt$$ExternalSyntheticLambda0 = SizeKt.SizeUnspecified;
            if (Intrinsics.areEqual(this.minWidth, paddingKt$$ExternalSyntheticLambda0)) {
                m823getMinWidthimpl = Constraints.m823getMinWidthimpl(j);
                int m821getMaxWidthimpl2 = Constraints.m821getMaxWidthimpl(m938getTargetConstraintsOenEA2s);
                if (m823getMinWidthimpl > m821getMaxWidthimpl2) {
                    m823getMinWidthimpl = m821getMaxWidthimpl2;
                }
            } else {
                m823getMinWidthimpl = Constraints.m823getMinWidthimpl(m938getTargetConstraintsOenEA2s);
            }
            if (Intrinsics.areEqual(this.maxWidth, paddingKt$$ExternalSyntheticLambda0)) {
                m821getMaxWidthimpl = Constraints.m821getMaxWidthimpl(j);
                int m823getMinWidthimpl2 = Constraints.m823getMinWidthimpl(m938getTargetConstraintsOenEA2s);
                if (m821getMaxWidthimpl < m823getMinWidthimpl2) {
                    m821getMaxWidthimpl = m823getMinWidthimpl2;
                }
            } else {
                m821getMaxWidthimpl = Constraints.m821getMaxWidthimpl(m938getTargetConstraintsOenEA2s);
            }
            if (Intrinsics.areEqual(this.minHeight, paddingKt$$ExternalSyntheticLambda0)) {
                m822getMinHeightimpl = Constraints.m822getMinHeightimpl(j);
                int m820getMaxHeightimpl2 = Constraints.m820getMaxHeightimpl(m938getTargetConstraintsOenEA2s);
                if (m822getMinHeightimpl > m820getMaxHeightimpl2) {
                    m822getMinHeightimpl = m820getMaxHeightimpl2;
                }
            } else {
                m822getMinHeightimpl = Constraints.m822getMinHeightimpl(m938getTargetConstraintsOenEA2s);
            }
            if (Intrinsics.areEqual(this.maxHeight, paddingKt$$ExternalSyntheticLambda0)) {
                m820getMaxHeightimpl = Constraints.m820getMaxHeightimpl(j);
                int m822getMinHeightimpl2 = Constraints.m822getMinHeightimpl(m938getTargetConstraintsOenEA2s);
                if (m820getMaxHeightimpl < m822getMinHeightimpl2) {
                    m820getMaxHeightimpl = m822getMinHeightimpl2;
                }
            } else {
                m820getMaxHeightimpl = Constraints.m820getMaxHeightimpl(m938getTargetConstraintsOenEA2s);
            }
            Constraints = ConstraintsKt.Constraints(m823getMinWidthimpl, m821getMaxWidthimpl, m822getMinHeightimpl, m820getMaxHeightimpl);
        }
        final Placeable mo608measureBRTryo0 = measurable.mo608measureBRTryo0(Constraints);
        layout$1 = measureScope.layout$1(mo608measureBRTryo0.width, mo608measureBRTryo0.height, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: com.android.compose.modifiers.SizeModifier$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                int i = SizeModifier.$r8$clinit;
                ((Placeable.PlacementScope) obj).placeRelative(Placeable.this, 0, 0, 0.0f);
                return Unit.INSTANCE;
            }
        });
        return layout$1;
    }

    @Override // androidx.compose.ui.layout.LayoutModifier
    public final int minIntrinsicHeight(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        long m938getTargetConstraintsOenEA2s = m938getTargetConstraintsOenEA2s(lookaheadCapablePlaceable);
        return Constraints.m818getHasFixedHeightimpl(m938getTargetConstraintsOenEA2s) ? Constraints.m820getMaxHeightimpl(m938getTargetConstraintsOenEA2s) : ConstraintsKt.m831constrainHeightK40F9xA(intrinsicMeasurable.minIntrinsicHeight(i), m938getTargetConstraintsOenEA2s);
    }

    @Override // androidx.compose.ui.layout.LayoutModifier
    public final int minIntrinsicWidth(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        long m938getTargetConstraintsOenEA2s = m938getTargetConstraintsOenEA2s(lookaheadCapablePlaceable);
        return Constraints.m819getHasFixedWidthimpl(m938getTargetConstraintsOenEA2s) ? Constraints.m821getMaxWidthimpl(m938getTargetConstraintsOenEA2s) : ConstraintsKt.m832constrainWidthK40F9xA(intrinsicMeasurable.minIntrinsicWidth(i), m938getTargetConstraintsOenEA2s);
    }

    public SizeModifier(Function1 function1, Function1 function12, Function1 function13, Function1 function14, boolean z, Function1 function15) {
        super(function15);
        this.minWidth = function1;
        this.minHeight = function12;
        this.maxWidth = function13;
        this.maxHeight = function14;
        this.enforceIncoming = z;
    }
}
