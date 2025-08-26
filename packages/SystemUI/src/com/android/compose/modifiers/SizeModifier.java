package com.android.compose.modifiers;

import androidx.compose.ui.layout.IntrinsicMeasurable;
import androidx.compose.ui.layout.IntrinsicMeasureScope;
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
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class SizeModifier extends InspectorValueInfo implements LayoutModifier {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final boolean enforceIncoming;
    public final Function1 maxHeight;
    public final Function1 maxWidth;
    public final Function1 minHeight;
    public final Function1 minWidth;

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ SizeModifier(Function1 function1, Function1 function12, Function1 function13, Function1 function14, boolean z, Function1 function15, int i, DefaultConstructorMarker defaultConstructorMarker) {
        int i2 = i & 1;
        PaddingKt$$ExternalSyntheticLambda0 paddingKt$$ExternalSyntheticLambda0 = SizeKt.SizeUnspecified;
        this(i2 != 0 ? paddingKt$$ExternalSyntheticLambda0 : function1, (i & 2) != 0 ? paddingKt$$ExternalSyntheticLambda0 : function12, (i & 4) != 0 ? paddingKt$$ExternalSyntheticLambda0 : function13, (i & 8) != 0 ? paddingKt$$ExternalSyntheticLambda0 : function14, z, function15);
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof SizeModifier)) {
            return false;
        }
        SizeModifier sizeModifier = (SizeModifier) obj;
        return Intrinsics.areEqual(this.minWidth, sizeModifier.minWidth) && Intrinsics.areEqual(this.minHeight, sizeModifier.minHeight) && Intrinsics.areEqual(this.maxWidth, sizeModifier.maxWidth) && Intrinsics.areEqual(this.maxHeight, sizeModifier.maxHeight) && this.enforceIncoming == sizeModifier.enforceIncoming;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x004f  */
    /* renamed from: getTargetConstraints-OenEA2s, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final long m940getTargetConstraintsOenEA2s(IntrinsicMeasureScope intrinsicMeasureScope) {
        int iIntValue;
        int iIntValue2;
        int iIntValue3;
        PaddingKt$$ExternalSyntheticLambda0 paddingKt$$ExternalSyntheticLambda0 = SizeKt.SizeUnspecified;
        Function1 function1 = this.maxWidth;
        int i = 0;
        if (Intrinsics.areEqual(function1, paddingKt$$ExternalSyntheticLambda0)) {
            iIntValue = Integer.MAX_VALUE;
        } else {
            iIntValue = ((Number) function1.mo781invoke(intrinsicMeasureScope)).intValue();
            if (iIntValue < 0) {
                iIntValue = 0;
            }
        }
        Function1 function12 = this.maxHeight;
        if (Intrinsics.areEqual(function12, paddingKt$$ExternalSyntheticLambda0)) {
            iIntValue2 = Integer.MAX_VALUE;
        } else {
            iIntValue2 = ((Number) function12.mo781invoke(intrinsicMeasureScope)).intValue();
            if (iIntValue2 < 0) {
                iIntValue2 = 0;
            }
        }
        Function1 function13 = this.minWidth;
        if (Intrinsics.areEqual(function13, paddingKt$$ExternalSyntheticLambda0)) {
            iIntValue3 = 0;
        } else {
            iIntValue3 = ((Number) function13.mo781invoke(intrinsicMeasureScope)).intValue();
            if (iIntValue3 > iIntValue) {
                iIntValue3 = iIntValue;
            }
            if (iIntValue3 < 0) {
                iIntValue3 = 0;
            }
            if (iIntValue3 == Integer.MAX_VALUE) {
            }
        }
        Function1 function14 = this.minHeight;
        if (!Intrinsics.areEqual(function14, paddingKt$$ExternalSyntheticLambda0)) {
            int iIntValue4 = ((Number) function14.mo781invoke(intrinsicMeasureScope)).intValue();
            if (iIntValue4 > iIntValue2) {
                iIntValue4 = iIntValue2;
            }
            if (iIntValue4 < 0) {
                iIntValue4 = 0;
            }
            if (iIntValue4 != Integer.MAX_VALUE) {
                i = iIntValue4;
            }
        }
        return ConstraintsKt.Constraints(iIntValue3, iIntValue, i, iIntValue2);
    }

    public final int hashCode() {
        return (this.maxHeight.hashCode() + ((this.maxWidth.hashCode() + ((this.minHeight.hashCode() + (this.minWidth.hashCode() * 31)) * 31)) * 31)) * 31;
    }

    @Override // androidx.compose.ui.layout.LayoutModifier
    public final int maxIntrinsicHeight(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        long jM940getTargetConstraintsOenEA2s = m940getTargetConstraintsOenEA2s(lookaheadCapablePlaceable);
        return Constraints.m820getHasFixedHeightimpl(jM940getTargetConstraintsOenEA2s) ? Constraints.m822getMaxHeightimpl(jM940getTargetConstraintsOenEA2s) : ConstraintsKt.m833constrainHeightK40F9xA(intrinsicMeasurable.maxIntrinsicHeight(i), jM940getTargetConstraintsOenEA2s);
    }

    @Override // androidx.compose.ui.layout.LayoutModifier
    public final int maxIntrinsicWidth(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        long jM940getTargetConstraintsOenEA2s = m940getTargetConstraintsOenEA2s(lookaheadCapablePlaceable);
        return Constraints.m821getHasFixedWidthimpl(jM940getTargetConstraintsOenEA2s) ? Constraints.m823getMaxWidthimpl(jM940getTargetConstraintsOenEA2s) : ConstraintsKt.m834constrainWidthK40F9xA(intrinsicMeasurable.maxIntrinsicWidth(i), jM940getTargetConstraintsOenEA2s);
    }

    @Override // androidx.compose.ui.layout.LayoutModifier
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo109measure3p2s80s(MeasureScope measureScope, Measurable measurable, long j) {
        int iM825getMinWidthimpl;
        int iM823getMaxWidthimpl;
        int iM824getMinHeightimpl;
        int iM822getMaxHeightimpl;
        long jConstraints;
        long jM940getTargetConstraintsOenEA2s = m940getTargetConstraintsOenEA2s(measureScope);
        if (this.enforceIncoming) {
            jConstraints = ConstraintsKt.m832constrainN9IONVI(j, jM940getTargetConstraintsOenEA2s);
        } else {
            PaddingKt$$ExternalSyntheticLambda0 paddingKt$$ExternalSyntheticLambda0 = SizeKt.SizeUnspecified;
            if (Intrinsics.areEqual(this.minWidth, paddingKt$$ExternalSyntheticLambda0)) {
                iM825getMinWidthimpl = Constraints.m825getMinWidthimpl(j);
                int iM823getMaxWidthimpl2 = Constraints.m823getMaxWidthimpl(jM940getTargetConstraintsOenEA2s);
                if (iM825getMinWidthimpl > iM823getMaxWidthimpl2) {
                    iM825getMinWidthimpl = iM823getMaxWidthimpl2;
                }
            } else {
                iM825getMinWidthimpl = Constraints.m825getMinWidthimpl(jM940getTargetConstraintsOenEA2s);
            }
            if (Intrinsics.areEqual(this.maxWidth, paddingKt$$ExternalSyntheticLambda0)) {
                iM823getMaxWidthimpl = Constraints.m823getMaxWidthimpl(j);
                int iM825getMinWidthimpl2 = Constraints.m825getMinWidthimpl(jM940getTargetConstraintsOenEA2s);
                if (iM823getMaxWidthimpl < iM825getMinWidthimpl2) {
                    iM823getMaxWidthimpl = iM825getMinWidthimpl2;
                }
            } else {
                iM823getMaxWidthimpl = Constraints.m823getMaxWidthimpl(jM940getTargetConstraintsOenEA2s);
            }
            if (Intrinsics.areEqual(this.minHeight, paddingKt$$ExternalSyntheticLambda0)) {
                iM824getMinHeightimpl = Constraints.m824getMinHeightimpl(j);
                int iM822getMaxHeightimpl2 = Constraints.m822getMaxHeightimpl(jM940getTargetConstraintsOenEA2s);
                if (iM824getMinHeightimpl > iM822getMaxHeightimpl2) {
                    iM824getMinHeightimpl = iM822getMaxHeightimpl2;
                }
            } else {
                iM824getMinHeightimpl = Constraints.m824getMinHeightimpl(jM940getTargetConstraintsOenEA2s);
            }
            if (Intrinsics.areEqual(this.maxHeight, paddingKt$$ExternalSyntheticLambda0)) {
                iM822getMaxHeightimpl = Constraints.m822getMaxHeightimpl(j);
                int iM824getMinHeightimpl2 = Constraints.m824getMinHeightimpl(jM940getTargetConstraintsOenEA2s);
                if (iM822getMaxHeightimpl < iM824getMinHeightimpl2) {
                    iM822getMaxHeightimpl = iM824getMinHeightimpl2;
                }
            } else {
                iM822getMaxHeightimpl = Constraints.m822getMaxHeightimpl(jM940getTargetConstraintsOenEA2s);
            }
            jConstraints = ConstraintsKt.Constraints(iM825getMinWidthimpl, iM823getMaxWidthimpl, iM824getMinHeightimpl, iM822getMaxHeightimpl);
        }
        final Placeable placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(jConstraints);
        return measureScope.layout$1(placeableMo610measureBRTryo0.width, placeableMo610measureBRTryo0.height, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: com.android.compose.modifiers.SizeModifier$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                int i = SizeModifier.$r8$clinit;
                ((Placeable.PlacementScope) obj).placeRelative(placeableMo610measureBRTryo0, 0, 0, 0.0f);
                return Unit.INSTANCE;
            }
        });
    }

    @Override // androidx.compose.ui.layout.LayoutModifier
    public final int minIntrinsicHeight(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        long jM940getTargetConstraintsOenEA2s = m940getTargetConstraintsOenEA2s(lookaheadCapablePlaceable);
        return Constraints.m820getHasFixedHeightimpl(jM940getTargetConstraintsOenEA2s) ? Constraints.m822getMaxHeightimpl(jM940getTargetConstraintsOenEA2s) : ConstraintsKt.m833constrainHeightK40F9xA(intrinsicMeasurable.minIntrinsicHeight(i), jM940getTargetConstraintsOenEA2s);
    }

    @Override // androidx.compose.ui.layout.LayoutModifier
    public final int minIntrinsicWidth(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        long jM940getTargetConstraintsOenEA2s = m940getTargetConstraintsOenEA2s(lookaheadCapablePlaceable);
        return Constraints.m821getHasFixedWidthimpl(jM940getTargetConstraintsOenEA2s) ? Constraints.m823getMaxWidthimpl(jM940getTargetConstraintsOenEA2s) : ConstraintsKt.m834constrainWidthK40F9xA(intrinsicMeasurable.minIntrinsicWidth(i), jM940getTargetConstraintsOenEA2s);
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
