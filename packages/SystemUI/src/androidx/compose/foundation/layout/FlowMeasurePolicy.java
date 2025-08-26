package androidx.compose.foundation.layout;

import androidx.collection.IntIntPair;
import androidx.collection.IntObjectMapKt;
import androidx.collection.MutableIntList;
import androidx.collection.MutableIntObjectMap;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.foundation.BorderModifierNodeElement$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.FlowLayoutBuildingBlocks;
import androidx.compose.foundation.layout.FlowLayoutOverflow;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.layout.IntrinsicMeasurable;
import androidx.compose.ui.layout.IntrinsicMeasureScope;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.MultiContentMeasurePolicy;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.Dp;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.ranges.IntProgressionIterator;
import kotlin.ranges.IntRange;

/* loaded from: classes.dex */
final class FlowMeasurePolicy implements MultiContentMeasurePolicy, FlowLineMeasurePolicy {
    public final CrossAxisAlignment crossAxisAlignment;
    public final float crossAxisArrangementSpacing;
    public final Arrangement.Horizontal horizontalArrangement;
    public final boolean isHorizontal;
    public final float mainAxisSpacing;
    public final int maxItemsInMainAxis;
    public final int maxLines;
    public final Lambda maxMainAxisIntrinsicItemSize;
    public final Lambda minCrossAxisIntrinsicItemSize;
    public final Lambda minMainAxisIntrinsicItemSize;
    public final FlowLayoutOverflowState overflow;
    public final Arrangement.Vertical verticalArrangement;

    public /* synthetic */ FlowMeasurePolicy(boolean z, Arrangement.Horizontal horizontal, Arrangement.Vertical vertical, float f, CrossAxisAlignment crossAxisAlignment, float f2, int i, int i2, FlowLayoutOverflowState flowLayoutOverflowState, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, horizontal, vertical, f, crossAxisAlignment, f2, i, i2, flowLayoutOverflowState);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FlowMeasurePolicy)) {
            return false;
        }
        FlowMeasurePolicy flowMeasurePolicy = (FlowMeasurePolicy) obj;
        return this.isHorizontal == flowMeasurePolicy.isHorizontal && Intrinsics.areEqual(this.horizontalArrangement, flowMeasurePolicy.horizontalArrangement) && Intrinsics.areEqual(this.verticalArrangement, flowMeasurePolicy.verticalArrangement) && Dp.m838equalsimpl0(this.mainAxisSpacing, flowMeasurePolicy.mainAxisSpacing) && Intrinsics.areEqual(this.crossAxisAlignment, flowMeasurePolicy.crossAxisAlignment) && Dp.m838equalsimpl0(this.crossAxisArrangementSpacing, flowMeasurePolicy.crossAxisArrangementSpacing) && this.maxItemsInMainAxis == flowMeasurePolicy.maxItemsInMainAxis && this.maxLines == flowMeasurePolicy.maxLines && Intrinsics.areEqual(this.overflow, flowMeasurePolicy.overflow);
    }

    public final int hashCode() {
        int iHashCode = (this.verticalArrangement.hashCode() + ((this.horizontalArrangement.hashCode() + (Boolean.hashCode(this.isHorizontal) * 31)) * 31)) * 31;
        Dp.Companion companion = Dp.Companion;
        return this.overflow.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.maxLines, ReorderTile$$ExternalSyntheticOutline0.m(this.maxItemsInMainAxis, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.crossAxisArrangementSpacing, (this.crossAxisAlignment.hashCode() + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.mainAxisSpacing, iHashCode, 31)) * 31, 31), 31), 31);
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [kotlin.jvm.functions.Function3, kotlin.jvm.internal.Lambda] */
    /* JADX WARN: Type inference failed for: r2v0, types: [kotlin.jvm.functions.Function3, kotlin.jvm.internal.Lambda] */
    public final int intrinsicCrossAxisSize(List list, int i, int i2, int i3, int i4, int i5, FlowLayoutOverflowState flowLayoutOverflowState) {
        return (int) (FlowLayoutKt.intrinsicCrossAxisSize(list, this.minMainAxisIntrinsicItemSize, this.minCrossAxisIntrinsicItemSize, i, i2, i3, i4, i5, flowLayoutOverflowState) >> 32);
    }

    @Override // androidx.compose.ui.layout.MultiContentMeasurePolicy
    public final int maxIntrinsicHeight(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
        List list2 = (List) CollectionsKt___CollectionsKt.getOrNull(1, list);
        IntrinsicMeasurable intrinsicMeasurable = list2 != null ? (IntrinsicMeasurable) CollectionsKt___CollectionsKt.firstOrNull(list2) : null;
        List list3 = (List) CollectionsKt___CollectionsKt.getOrNull(2, list);
        IntrinsicMeasurable intrinsicMeasurable2 = list3 != null ? (IntrinsicMeasurable) CollectionsKt___CollectionsKt.firstOrNull(list3) : null;
        this.overflow.m107setOverflowMeasurableshBUhpc$foundation_layout(intrinsicMeasurable, intrinsicMeasurable2, this.isHorizontal, ConstraintsKt.Constraints$default(0, i, 0, 0, 13));
        boolean z = this.isHorizontal;
        float f = this.mainAxisSpacing;
        if (!z) {
            List list4 = (List) CollectionsKt___CollectionsKt.firstOrNull(list);
            if (list4 == null) {
                list4 = EmptyList.INSTANCE;
            }
            return maxIntrinsicMainAxisSize(list4, i, intrinsicMeasureScope.mo52roundToPx0680j_4(f));
        }
        List list5 = (List) CollectionsKt___CollectionsKt.firstOrNull(list);
        if (list5 == null) {
            list5 = EmptyList.INSTANCE;
        }
        return intrinsicCrossAxisSize(list5, i, intrinsicMeasureScope.mo52roundToPx0680j_4(f), intrinsicMeasureScope.mo52roundToPx0680j_4(this.crossAxisArrangementSpacing), this.maxItemsInMainAxis, this.maxLines, this.overflow);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [kotlin.jvm.functions.Function3, kotlin.jvm.internal.Lambda] */
    public final int maxIntrinsicMainAxisSize(List list, int i, int i2) {
        ?? r0 = this.maxMainAxisIntrinsicItemSize;
        int i3 = FlowLayoutKt.$r8$clinit;
        int size = list.size();
        int i4 = 0;
        int iMax = 0;
        int i5 = 0;
        int i6 = 0;
        while (i4 < size) {
            int iIntValue = ((Number) r0.invoke((IntrinsicMeasurable) list.get(i4), Integer.valueOf(i4), Integer.valueOf(i))).intValue() + i2;
            int i7 = i4 + 1;
            if (i7 - i5 == this.maxItemsInMainAxis || i7 == list.size()) {
                iMax = Math.max(iMax, (i6 + iIntValue) - i2);
                i6 = 0;
                i5 = i4;
            } else {
                i6 += iIntValue;
            }
            i4 = i7;
        }
        return iMax;
    }

    @Override // androidx.compose.ui.layout.MultiContentMeasurePolicy
    public final int maxIntrinsicWidth(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
        List list2 = (List) CollectionsKt___CollectionsKt.getOrNull(1, list);
        IntrinsicMeasurable intrinsicMeasurable = list2 != null ? (IntrinsicMeasurable) CollectionsKt___CollectionsKt.firstOrNull(list2) : null;
        List list3 = (List) CollectionsKt___CollectionsKt.getOrNull(2, list);
        IntrinsicMeasurable intrinsicMeasurable2 = list3 != null ? (IntrinsicMeasurable) CollectionsKt___CollectionsKt.firstOrNull(list3) : null;
        this.overflow.m107setOverflowMeasurableshBUhpc$foundation_layout(intrinsicMeasurable, intrinsicMeasurable2, this.isHorizontal, ConstraintsKt.Constraints$default(0, 0, 0, i, 7));
        boolean z = this.isHorizontal;
        float f = this.mainAxisSpacing;
        if (z) {
            List list4 = (List) CollectionsKt___CollectionsKt.firstOrNull(list);
            if (list4 == null) {
                list4 = EmptyList.INSTANCE;
            }
            return maxIntrinsicMainAxisSize(list4, i, intrinsicMeasureScope.mo52roundToPx0680j_4(f));
        }
        List list5 = (List) CollectionsKt___CollectionsKt.firstOrNull(list);
        if (list5 == null) {
            list5 = EmptyList.INSTANCE;
        }
        return intrinsicCrossAxisSize(list5, i, intrinsicMeasureScope.mo52roundToPx0680j_4(f), intrinsicMeasureScope.mo52roundToPx0680j_4(this.crossAxisArrangementSpacing), this.maxItemsInMainAxis, this.maxLines, this.overflow);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.compose.ui.layout.MultiContentMeasurePolicy
    /* renamed from: measure-3p2s80s, reason: not valid java name */
    public final MeasureResult mo108measure3p2s80s(MeasureScope measureScope, List list, long j) {
        int i;
        Measurable measurableSafeNext;
        FlowLayoutBuildingBlocks.WrapInfo wrapInfo;
        FlowLayoutBuildingBlocks.WrapEllipsisInfo wrapEllipsisInfo;
        int i2;
        int i3;
        int iM822getMaxHeightimpl;
        int height;
        int width;
        int i4;
        Integer num;
        IntIntPair intIntPairM0boximpl;
        FlowLayoutBuildingBlocks.WrapInfo wrapInfo2;
        MutableIntList mutableIntList;
        MutableIntList mutableIntList2;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        final FlowMeasurePolicy flowMeasurePolicy = this;
        MeasureScope measureScope2 = measureScope;
        if (flowMeasurePolicy.maxLines != 0 && flowMeasurePolicy.maxItemsInMainAxis != 0 && !((ArrayList) list).isEmpty()) {
            int iM822getMaxHeightimpl2 = Constraints.m822getMaxHeightimpl(j);
            final FlowLayoutOverflowState flowLayoutOverflowState = flowMeasurePolicy.overflow;
            if (iM822getMaxHeightimpl2 != 0 || flowLayoutOverflowState.type == FlowLayoutOverflow.OverflowType.Visible) {
                List list2 = (List) CollectionsKt___CollectionsKt.first(list);
                if (list2.isEmpty()) {
                    return measureScope2.layout$1(0, 0, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.foundation.layout.FlowMeasurePolicy$measure$2
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj) {
                            return Unit.INSTANCE;
                        }
                    });
                }
                List list3 = (List) CollectionsKt___CollectionsKt.getOrNull(1, list);
                Measurable measurable = list3 != null ? (Measurable) CollectionsKt___CollectionsKt.firstOrNull(list3) : null;
                List list4 = (List) CollectionsKt___CollectionsKt.getOrNull(2, list);
                Measurable measurable2 = list4 != null ? (Measurable) CollectionsKt___CollectionsKt.firstOrNull(list4) : null;
                list2.size();
                flowLayoutOverflowState.getClass();
                boolean z = flowMeasurePolicy.isHorizontal;
                LayoutOrientation layoutOrientation = z ? LayoutOrientation.Horizontal : LayoutOrientation.Vertical;
                long jM117constructorimpl = OrientationIndependentConstraints.m117constructorimpl(j, layoutOrientation);
                long jM119toBoxConstraintsOenEA2s = OrientationIndependentConstraints.m119toBoxConstraintsOenEA2s(ConstraintsKt.Constraints(0, Constraints.m823getMaxWidthimpl(jM117constructorimpl), (10 & 4) != 0 ? Constraints.m824getMinHeightimpl(jM117constructorimpl) : 0, Constraints.m822getMaxHeightimpl(jM117constructorimpl)), layoutOrientation);
                if (measurable != null) {
                    FlowLayoutKt.m105measureAndCacherqJ1uqs(measurable, flowMeasurePolicy, jM119toBoxConstraintsOenEA2s, new Function1() { // from class: androidx.compose.foundation.layout.FlowLayoutOverflowState$setOverflowMeasurables$3$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            int iMainAxisSize;
                            int iCrossAxisSize;
                            Placeable placeable = (Placeable) obj;
                            if (placeable != null) {
                                FlowLineMeasurePolicy flowLineMeasurePolicy = flowMeasurePolicy;
                                iMainAxisSize = flowLineMeasurePolicy.mainAxisSize(placeable);
                                iCrossAxisSize = flowLineMeasurePolicy.crossAxisSize(placeable);
                            } else {
                                iMainAxisSize = 0;
                                iCrossAxisSize = 0;
                            }
                            flowLayoutOverflowState.seeMoreSize = IntIntPair.m0boximpl(IntIntPair.m1constructorimpl(iMainAxisSize, iCrossAxisSize));
                            flowLayoutOverflowState.seeMorePlaceable = placeable;
                            return Unit.INSTANCE;
                        }
                    });
                    flowLayoutOverflowState.seeMoreMeasurable = measurable;
                }
                if (measurable2 != null) {
                    FlowLayoutKt.m105measureAndCacherqJ1uqs(measurable2, flowMeasurePolicy, jM119toBoxConstraintsOenEA2s, new Function1() { // from class: androidx.compose.foundation.layout.FlowLayoutOverflowState$setOverflowMeasurables$4$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            int iMainAxisSize;
                            int iCrossAxisSize;
                            Placeable placeable = (Placeable) obj;
                            if (placeable != null) {
                                FlowLineMeasurePolicy flowLineMeasurePolicy = flowMeasurePolicy;
                                iMainAxisSize = flowLineMeasurePolicy.mainAxisSize(placeable);
                                iCrossAxisSize = flowLineMeasurePolicy.crossAxisSize(placeable);
                            } else {
                                iMainAxisSize = 0;
                                iCrossAxisSize = 0;
                            }
                            flowLayoutOverflowState.collapseSize = IntIntPair.m0boximpl(IntIntPair.m1constructorimpl(iMainAxisSize, iCrossAxisSize));
                            flowLayoutOverflowState.collapsePlaceable = placeable;
                            return Unit.INSTANCE;
                        }
                    });
                    flowLayoutOverflowState.collapseMeasurable = measurable2;
                }
                Iterator it = list2.iterator();
                long jM117constructorimpl2 = OrientationIndependentConstraints.m117constructorimpl(j, z ? LayoutOrientation.Horizontal : LayoutOrientation.Vertical);
                int i10 = FlowLayoutKt.$r8$clinit;
                MutableVector mutableVector = new MutableVector(new MeasureResult[16], 0);
                int iM823getMaxWidthimpl = Constraints.m823getMaxWidthimpl(jM117constructorimpl2);
                int iM825getMinWidthimpl = Constraints.m825getMinWidthimpl(jM117constructorimpl2);
                int iM822getMaxHeightimpl3 = Constraints.m822getMaxHeightimpl(jM117constructorimpl2);
                MutableIntObjectMap mutableIntObjectMapMutableIntObjectMapOf = IntObjectMapKt.mutableIntObjectMapOf();
                int iCeil = (int) Math.ceil(measureScope2.mo58toPx0680j_4(flowMeasurePolicy.mainAxisSpacing));
                int iCeil2 = (int) Math.ceil(measureScope2.mo58toPx0680j_4(flowMeasurePolicy.crossAxisArrangementSpacing));
                ArrayList arrayList = new ArrayList();
                long jConstraints = ConstraintsKt.Constraints(0, iM823getMaxWidthimpl, 0, iM822getMaxHeightimpl3);
                long jM119toBoxConstraintsOenEA2s2 = OrientationIndependentConstraints.m119toBoxConstraintsOenEA2s(ConstraintsKt.Constraints(0, Constraints.m823getMaxWidthimpl(jConstraints), (10 & 4) != 0 ? Constraints.m824getMinHeightimpl(jConstraints) : 0, Constraints.m822getMaxHeightimpl(jConstraints)), z ? LayoutOrientation.Horizontal : LayoutOrientation.Vertical);
                final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                MutableVector mutableVector2 = mutableVector;
                FlowLineInfo flowLineInfo = it instanceof ContextualFlowItemIterator ? new FlowLineInfo(0, 0, measureScope2.mo55toDpu2uoSUM(iM823getMaxWidthimpl), measureScope2.mo55toDpu2uoSUM(iM822getMaxHeightimpl3), null) : null;
                if (it.hasNext()) {
                    i = iM825getMinWidthimpl;
                    measurableSafeNext = FlowLayoutKt.safeNext(it, flowLineInfo);
                } else {
                    i = iM825getMinWidthimpl;
                    measurableSafeNext = null;
                }
                IntIntPair intIntPairM0boximpl2 = measurableSafeNext != null ? IntIntPair.m0boximpl(FlowLayoutKt.m105measureAndCacherqJ1uqs(measurableSafeNext, flowMeasurePolicy, jM119toBoxConstraintsOenEA2s2, new Function1() { // from class: androidx.compose.foundation.layout.FlowLayoutKt$breakDownItems$nextSize$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    /* JADX WARN: Type inference failed for: r1v1, types: [T, androidx.compose.ui.layout.Placeable] */
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        ref$ObjectRef.element = (Placeable) obj;
                        return Unit.INSTANCE;
                    }
                })) : null;
                Integer numValueOf = intIntPairM0boximpl2 != null ? Integer.valueOf((int) (intIntPairM0boximpl2.packedValue >> 32)) : null;
                Integer numValueOf2 = intIntPairM0boximpl2 != null ? Integer.valueOf((int) (intIntPairM0boximpl2.packedValue & 4294967295L)) : null;
                Measurable measurable3 = measurableSafeNext;
                IntIntPair intIntPair = intIntPairM0boximpl2;
                MutableIntList mutableIntList3 = new MutableIntList(0, 1, null);
                MutableIntList mutableIntList4 = new MutableIntList(0, 1, null);
                int i11 = flowMeasurePolicy.maxLines;
                int i12 = flowMeasurePolicy.maxItemsInMainAxis;
                FlowLayoutOverflowState flowLayoutOverflowState2 = flowMeasurePolicy.overflow;
                FlowLayoutBuildingBlocks flowLayoutBuildingBlocks = new FlowLayoutBuildingBlocks(i12, flowLayoutOverflowState2, jM117constructorimpl2, i11, iCeil, iCeil2, null);
                FlowLayoutBuildingBlocks.WrapInfo wrapInfoM104getWrapInfoOpUlnko = flowLayoutBuildingBlocks.m104getWrapInfoOpUlnko(it.hasNext(), 0, IntIntPair.m1constructorimpl(iM823getMaxWidthimpl, iM822getMaxHeightimpl3), intIntPair, 0, 0, 0, false, false);
                if (wrapInfoM104getWrapInfoOpUlnko.isLastItemInContainer) {
                    wrapInfo = wrapInfoM104getWrapInfoOpUlnko;
                    wrapEllipsisInfo = flowLayoutBuildingBlocks.getWrapEllipsisInfo(wrapInfo, intIntPair != null, -1, 0, iM823getMaxWidthimpl, 0);
                } else {
                    wrapInfo = wrapInfoM104getWrapInfoOpUlnko;
                    wrapEllipsisInfo = null;
                }
                int i13 = iM823getMaxWidthimpl;
                FlowLayoutBuildingBlocks.WrapEllipsisInfo wrapEllipsisInfo2 = wrapEllipsisInfo;
                int i14 = i13;
                Integer num2 = numValueOf2;
                MutableIntList mutableIntList5 = mutableIntList3;
                MutableIntList mutableIntList6 = mutableIntList4;
                int i15 = iM822getMaxHeightimpl3;
                FlowLayoutBuildingBlocks.WrapInfo wrapInfo3 = wrapInfo;
                int i16 = i;
                int i17 = 0;
                int i18 = 0;
                int i19 = 0;
                int i20 = 0;
                int i21 = 0;
                int i22 = 0;
                Measurable measurable4 = measurable3;
                while (!wrapInfo3.isLastItemInContainer && measurable4 != null) {
                    numValueOf.getClass();
                    int iIntValue = numValueOf.intValue();
                    num2.getClass();
                    int iIntValue2 = num2.intValue();
                    int i23 = i13;
                    int i24 = i19 + iIntValue;
                    int iMax = Math.max(i18, iIntValue2);
                    int i25 = i14 - iIntValue;
                    flowLayoutOverflowState2.getClass();
                    int i26 = i17 + 1;
                    ArrayList arrayList2 = arrayList;
                    arrayList2.add(measurable4);
                    mutableIntObjectMapMutableIntObjectMapOf.set(i17, ref$ObjectRef.element);
                    int i27 = i26 - i20;
                    boolean z2 = i27 < i12;
                    if (flowLineInfo != null) {
                        if (z2) {
                            int i28 = i25 - iCeil;
                            i4 = i27;
                            i8 = i28 < 0 ? 0 : i28;
                        } else {
                            i4 = i27;
                            i8 = i23;
                        }
                        measureScope2.mo55toDpu2uoSUM(i8);
                        if (z2) {
                            i9 = i15;
                        } else {
                            i9 = (i15 - iMax) - iCeil2;
                            if (i9 < 0) {
                                i9 = 0;
                            }
                        }
                        measureScope2.mo55toDpu2uoSUM(i9);
                    } else {
                        i4 = i27;
                    }
                    Measurable measurableSafeNext2 = !it.hasNext() ? null : FlowLayoutKt.safeNext(it, flowLineInfo);
                    ref$ObjectRef.element = null;
                    IntIntPair intIntPairM0boximpl3 = measurableSafeNext2 != null ? IntIntPair.m0boximpl(FlowLayoutKt.m105measureAndCacherqJ1uqs(measurableSafeNext2, flowMeasurePolicy, jM119toBoxConstraintsOenEA2s2, new Function1() { // from class: androidx.compose.foundation.layout.FlowLayoutKt$breakDownItems$1$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        /* JADX WARN: Type inference failed for: r1v1, types: [T, androidx.compose.ui.layout.Placeable] */
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            ref$ObjectRef.element = (Placeable) obj;
                            return Unit.INSTANCE;
                        }
                    })) : null;
                    Integer numValueOf3 = intIntPairM0boximpl3 != null ? Integer.valueOf(((int) (intIntPairM0boximpl3.packedValue >> 32)) + iCeil) : null;
                    Integer numValueOf4 = intIntPairM0boximpl3 != null ? Integer.valueOf((int) (intIntPairM0boximpl3.packedValue & 4294967295L)) : null;
                    boolean zHasNext = it.hasNext();
                    int i29 = i21;
                    long jM1constructorimpl = IntIntPair.m1constructorimpl(i25, i15);
                    if (intIntPairM0boximpl3 == null) {
                        num = numValueOf4;
                        intIntPairM0boximpl = null;
                    } else {
                        numValueOf3.getClass();
                        int iIntValue3 = numValueOf3.intValue();
                        numValueOf4.getClass();
                        num = numValueOf4;
                        intIntPairM0boximpl = IntIntPair.m0boximpl(IntIntPair.m1constructorimpl(iIntValue3, num.intValue()));
                    }
                    FlowLayoutBuildingBlocks.WrapInfo wrapInfoM104getWrapInfoOpUlnko2 = flowLayoutBuildingBlocks.m104getWrapInfoOpUlnko(zHasNext, i4, jM1constructorimpl, intIntPairM0boximpl, i29, i22, iMax, false, false);
                    int i30 = iMax;
                    Iterator it2 = it;
                    if (wrapInfoM104getWrapInfoOpUlnko2.isLastItemInLine) {
                        int iMax2 = Math.max(i16, i24);
                        i7 = i23;
                        int iMin = Math.min(iMax2, i7);
                        int i31 = i22 + i30;
                        wrapInfo2 = wrapInfoM104getWrapInfoOpUlnko2;
                        FlowLayoutBuildingBlocks.WrapEllipsisInfo wrapEllipsisInfo3 = flowLayoutBuildingBlocks.getWrapEllipsisInfo(wrapInfo2, intIntPairM0boximpl3 != null, i29, i31, i25, i4);
                        mutableIntList2 = mutableIntList6;
                        mutableIntList2.add(i30);
                        i15 = (iM822getMaxHeightimpl3 - i31) - iCeil2;
                        mutableIntList = mutableIntList5;
                        mutableIntList.add(i26);
                        i21 = i29 + 1;
                        wrapEllipsisInfo2 = wrapEllipsisInfo3;
                        i26 = i26;
                        i20 = i26;
                        i5 = i7;
                        numValueOf = numValueOf3 != null ? Integer.valueOf(numValueOf3.intValue() - iCeil) : null;
                        i22 = i31 + iCeil2;
                        i6 = 0;
                        i30 = 0;
                        i16 = iMin;
                    } else {
                        wrapInfo2 = wrapInfoM104getWrapInfoOpUlnko2;
                        mutableIntList = mutableIntList5;
                        mutableIntList2 = mutableIntList6;
                        i5 = i23;
                        numValueOf = numValueOf3;
                        i6 = i24;
                        i7 = i25;
                        i21 = i29;
                    }
                    int i32 = i26;
                    i14 = i7;
                    i13 = i5;
                    mutableIntList5 = mutableIntList;
                    measurable4 = measurableSafeNext2;
                    i17 = i32;
                    mutableIntList6 = mutableIntList2;
                    arrayList = arrayList2;
                    num2 = num;
                    it = it2;
                    wrapInfo3 = wrapInfo2;
                    i19 = i6;
                    i18 = i30;
                    flowMeasurePolicy = this;
                    measureScope2 = measureScope;
                }
                ArrayList arrayList3 = arrayList;
                MutableIntList mutableIntList7 = mutableIntList5;
                MutableIntList mutableIntList8 = mutableIntList6;
                if (wrapEllipsisInfo2 != null) {
                    FlowLayoutBuildingBlocks.WrapEllipsisInfo wrapEllipsisInfo4 = wrapEllipsisInfo2;
                    arrayList3.add(wrapEllipsisInfo4.ellipsis);
                    mutableIntObjectMapMutableIntObjectMapOf.set(arrayList3.size() - 1, wrapEllipsisInfo4.placeable);
                    int i33 = mutableIntList7._size - 1;
                    boolean z3 = wrapEllipsisInfo4.placeEllipsisOnLastContentLine;
                    long j2 = wrapEllipsisInfo4.ellipsisSize;
                    if (z3) {
                        mutableIntList8.set(i33, Math.max(mutableIntList8.get(i33), (int) (j2 & 4294967295L)));
                        mutableIntList7.set(i33, mutableIntList7.last() + 1);
                    } else {
                        mutableIntList8.add((int) (j2 & 4294967295L));
                        mutableIntList7.add(mutableIntList7.last() + 1);
                    }
                }
                int size = arrayList3.size();
                Placeable[] placeableArr = new Placeable[size];
                for (int i34 = 0; i34 < size; i34++) {
                    placeableArr[i34] = mutableIntObjectMapMutableIntObjectMapOf.get(i34);
                }
                int i35 = mutableIntList7._size;
                int[] iArr = new int[i35];
                int[] iArr2 = new int[i35];
                int[] iArr3 = mutableIntList7.content;
                int i36 = 0;
                int i37 = 0;
                int i38 = 0;
                Placeable[] placeableArr2 = placeableArr;
                while (i37 < i35) {
                    Placeable[] placeableArr3 = placeableArr2;
                    int i39 = i16;
                    int i40 = iArr3[i37];
                    MutableIntList mutableIntList9 = mutableIntList8;
                    ArrayList arrayList4 = arrayList3;
                    int i41 = i35;
                    MutableVector mutableVector3 = mutableVector2;
                    int[] iArr4 = iArr;
                    MeasureResult measureResultMeasure = RowColumnMeasurePolicyKt.measure(this, i39, Constraints.m824getMinHeightimpl(jConstraints), Constraints.m823getMaxWidthimpl(jConstraints), mutableIntList9.get(i37), iCeil, measureScope, arrayList4, placeableArr3, i36, i40, iArr4, i37);
                    iArr = iArr4;
                    arrayList3 = arrayList4;
                    if (z) {
                        height = measureResultMeasure.getWidth();
                        width = measureResultMeasure.getHeight();
                    } else {
                        height = measureResultMeasure.getHeight();
                        width = measureResultMeasure.getWidth();
                    }
                    iArr2[i37] = width;
                    i38 += width;
                    int iMax3 = Math.max(i39, height);
                    mutableVector3.add(measureResultMeasure);
                    i37++;
                    placeableArr2 = placeableArr3;
                    i36 = i40;
                    mutableVector2 = mutableVector3;
                    i35 = i41;
                    mutableIntList8 = mutableIntList9;
                    i16 = iMax3;
                }
                final MutableVector mutableVector4 = mutableVector2;
                int i42 = i16;
                if (mutableVector4.size == 0) {
                    i2 = 0;
                    i3 = 0;
                } else {
                    i2 = i42;
                    i3 = i38;
                }
                if (z) {
                    Arrangement.Vertical vertical = this.verticalArrangement;
                    int iMo52roundToPx0680j_4 = ((mutableVector4.size - 1) * measureScope.mo52roundToPx0680j_4(vertical.mo95getSpacingD9Ej5fM())) + i3;
                    int iM824getMinHeightimpl = Constraints.m824getMinHeightimpl(jM117constructorimpl2);
                    iM822getMaxHeightimpl = Constraints.m822getMaxHeightimpl(jM117constructorimpl2);
                    if (iMo52roundToPx0680j_4 < iM824getMinHeightimpl) {
                        iMo52roundToPx0680j_4 = iM824getMinHeightimpl;
                    }
                    if (iMo52roundToPx0680j_4 <= iM822getMaxHeightimpl) {
                        iM822getMaxHeightimpl = iMo52roundToPx0680j_4;
                    }
                    vertical.arrange(measureScope, iM822getMaxHeightimpl, iArr2, iArr);
                } else {
                    Arrangement.Horizontal horizontal = this.horizontalArrangement;
                    int iMo52roundToPx0680j_42 = ((mutableVector4.size - 1) * measureScope.mo52roundToPx0680j_4(horizontal.mo95getSpacingD9Ej5fM())) + i3;
                    int iM824getMinHeightimpl2 = Constraints.m824getMinHeightimpl(jM117constructorimpl2);
                    int iM822getMaxHeightimpl4 = Constraints.m822getMaxHeightimpl(jM117constructorimpl2);
                    if (iMo52roundToPx0680j_42 < iM824getMinHeightimpl2) {
                        iMo52roundToPx0680j_42 = iM824getMinHeightimpl2;
                    }
                    int i43 = iMo52roundToPx0680j_42 > iM822getMaxHeightimpl4 ? iM822getMaxHeightimpl4 : iMo52roundToPx0680j_42;
                    horizontal.arrange(measureScope, i43, iArr2, measureScope.getLayoutDirection(), iArr);
                    iM822getMaxHeightimpl = i43;
                }
                int iM825getMinWidthimpl2 = Constraints.m825getMinWidthimpl(jM117constructorimpl2);
                int iM823getMaxWidthimpl2 = Constraints.m823getMaxWidthimpl(jM117constructorimpl2);
                if (i2 < iM825getMinWidthimpl2) {
                    i2 = iM825getMinWidthimpl2;
                }
                if (i2 <= iM823getMaxWidthimpl2) {
                    iM823getMaxWidthimpl2 = i2;
                }
                if (z) {
                    int i44 = iM822getMaxHeightimpl;
                    iM822getMaxHeightimpl = iM823getMaxWidthimpl2;
                    iM823getMaxWidthimpl2 = i44;
                }
                return measureScope.layout$1(iM822getMaxHeightimpl, iM823getMaxWidthimpl2, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.foundation.layout.FlowLayoutKt$placeHelper$3
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        MutableVector<MeasureResult> mutableVector5 = mutableVector4;
                        Object[] objArr = mutableVector5.content;
                        int i45 = mutableVector5.size;
                        for (int i46 = 0; i46 < i45; i46++) {
                            ((MeasureResult) objArr[i46]).placeChildren();
                        }
                        return Unit.INSTANCE;
                    }
                });
            }
        }
        return measureScope2.layout$1(0, 0, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.foundation.layout.FlowMeasurePolicy$measure$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj) {
                return Unit.INSTANCE;
            }
        });
    }

    @Override // androidx.compose.ui.layout.MultiContentMeasurePolicy
    public final int minIntrinsicHeight(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
        List list2 = (List) CollectionsKt___CollectionsKt.getOrNull(1, list);
        IntrinsicMeasurable intrinsicMeasurable = list2 != null ? (IntrinsicMeasurable) CollectionsKt___CollectionsKt.firstOrNull(list2) : null;
        List list3 = (List) CollectionsKt___CollectionsKt.getOrNull(2, list);
        IntrinsicMeasurable intrinsicMeasurable2 = list3 != null ? (IntrinsicMeasurable) CollectionsKt___CollectionsKt.firstOrNull(list3) : null;
        this.overflow.m107setOverflowMeasurableshBUhpc$foundation_layout(intrinsicMeasurable, intrinsicMeasurable2, this.isHorizontal, ConstraintsKt.Constraints$default(0, i, 0, 0, 13));
        boolean z = this.isHorizontal;
        float f = this.crossAxisArrangementSpacing;
        float f2 = this.mainAxisSpacing;
        if (z) {
            List list4 = (List) CollectionsKt___CollectionsKt.firstOrNull(list);
            if (list4 == null) {
                list4 = EmptyList.INSTANCE;
            }
            return intrinsicCrossAxisSize(list4, i, intrinsicMeasureScope.mo52roundToPx0680j_4(f2), intrinsicMeasureScope.mo52roundToPx0680j_4(f), this.maxItemsInMainAxis, this.maxLines, this.overflow);
        }
        List list5 = (List) CollectionsKt___CollectionsKt.firstOrNull(list);
        if (list5 == null) {
            list5 = EmptyList.INSTANCE;
        }
        return minIntrinsicMainAxisSize(list5, i, intrinsicMeasureScope.mo52roundToPx0680j_4(f2), intrinsicMeasureScope.mo52roundToPx0680j_4(f), this.maxItemsInMainAxis, this.maxLines, this.overflow);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0084  */
    /* JADX WARN: Type inference failed for: r0v1, types: [kotlin.jvm.functions.Function3, kotlin.jvm.internal.Lambda] */
    /* JADX WARN: Type inference failed for: r2v0, types: [kotlin.jvm.functions.Function3, kotlin.jvm.internal.Lambda] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int minIntrinsicMainAxisSize(List list, int i, int i2, int i3, int i4, int i5, FlowLayoutOverflowState flowLayoutOverflowState) {
        int i6;
        FlowLayoutOverflow.OverflowType overflowType;
        int i7 = i4;
        int i8 = i5;
        FlowLayoutOverflowState flowLayoutOverflowState2 = flowLayoutOverflowState;
        ?? r2 = this.minMainAxisIntrinsicItemSize;
        ?? r0 = this.minCrossAxisIntrinsicItemSize;
        int i9 = FlowLayoutKt.$r8$clinit;
        int i10 = 0;
        if (list.isEmpty()) {
            return 0;
        }
        int size = list.size();
        final int[] iArr = new int[size];
        int size2 = list.size();
        final int[] iArr2 = new int[size2];
        int size3 = list.size();
        int i11 = 0;
        while (i11 < size3) {
            IntrinsicMeasurable intrinsicMeasurable = (IntrinsicMeasurable) list.get(i11);
            int i12 = i10;
            int iIntValue = ((Number) r2.invoke(intrinsicMeasurable, Integer.valueOf(i11), Integer.valueOf(i))).intValue();
            iArr[i11] = iIntValue;
            iArr2[i11] = ((Number) r0.invoke(intrinsicMeasurable, Integer.valueOf(i11), Integer.valueOf(iIntValue))).intValue();
            i11++;
            i10 = i12;
        }
        int i13 = i10;
        int i14 = Integer.MAX_VALUE;
        if (i8 != Integer.MAX_VALUE && i7 != Integer.MAX_VALUE) {
            i14 = i7 * i8;
        }
        if (i14 >= list.size() || ((overflowType = flowLayoutOverflowState2.type) != FlowLayoutOverflow.OverflowType.ExpandIndicator && overflowType != FlowLayoutOverflow.OverflowType.ExpandOrCollapseIndicator)) {
            if (i14 >= list.size() && i8 >= flowLayoutOverflowState2.minLinesToShowCollapse) {
                if (flowLayoutOverflowState2.type == FlowLayoutOverflow.OverflowType.ExpandOrCollapseIndicator) {
                    i6 = 1;
                }
            }
            i6 = i13;
        }
        int iMin = Math.min(i14 - i6, list.size());
        int i15 = i13;
        int i16 = i15;
        while (i15 < size) {
            i16 += iArr[i15];
            i15++;
        }
        int size4 = ((list.size() - 1) * i2) + i16;
        if (size2 == 0) {
            throw new NoSuchElementException();
        }
        int i17 = iArr2[i13];
        IntProgressionIterator it = new IntRange(1, size2 - 1).iterator();
        while (it.hasNext) {
            int i18 = iArr2[it.nextInt()];
            if (i17 < i18) {
                i17 = i18;
            }
        }
        if (size == 0) {
            throw new NoSuchElementException();
        }
        int i19 = iArr[i13];
        IntProgressionIterator it2 = new IntRange(1, size - 1).iterator();
        while (it2.hasNext) {
            int i20 = iArr[it2.nextInt()];
            if (i19 < i20) {
                i19 = i20;
            }
        }
        int i21 = size4;
        int i22 = i19;
        while (i22 <= i21 && i17 != i) {
            int i23 = (i22 + i21) / 2;
            long jIntrinsicCrossAxisSize = FlowLayoutKt.intrinsicCrossAxisSize(list, new Function3() { // from class: androidx.compose.foundation.layout.FlowLayoutKt.intrinsicCrossAxisSize.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    int iIntValue2 = ((Number) obj2).intValue();
                    ((Number) obj3).intValue();
                    return Integer.valueOf(iArr[iIntValue2]);
                }
            }, new Function3() { // from class: androidx.compose.foundation.layout.FlowLayoutKt.intrinsicCrossAxisSize.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    int iIntValue2 = ((Number) obj2).intValue();
                    ((Number) obj3).intValue();
                    return Integer.valueOf(iArr2[iIntValue2]);
                }
            }, i23, i2, i3, i7, i8, flowLayoutOverflowState2);
            int i24 = (int) (jIntrinsicCrossAxisSize >> 32);
            int i25 = (int) (jIntrinsicCrossAxisSize & 4294967295L);
            if (i24 > i || i25 < iMin) {
                i22 = i23 + 1;
                if (i22 > i21) {
                    return i22;
                }
            } else {
                if (i24 >= i) {
                    return i23;
                }
                i21 = i23 - 1;
            }
            i7 = i4;
            i8 = i5;
            flowLayoutOverflowState2 = flowLayoutOverflowState;
            i17 = i24;
            size4 = i23;
        }
        return size4;
    }

    @Override // androidx.compose.ui.layout.MultiContentMeasurePolicy
    public final int minIntrinsicWidth(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
        List list2 = (List) CollectionsKt___CollectionsKt.getOrNull(1, list);
        IntrinsicMeasurable intrinsicMeasurable = list2 != null ? (IntrinsicMeasurable) CollectionsKt___CollectionsKt.firstOrNull(list2) : null;
        List list3 = (List) CollectionsKt___CollectionsKt.getOrNull(2, list);
        IntrinsicMeasurable intrinsicMeasurable2 = list3 != null ? (IntrinsicMeasurable) CollectionsKt___CollectionsKt.firstOrNull(list3) : null;
        this.overflow.m107setOverflowMeasurableshBUhpc$foundation_layout(intrinsicMeasurable, intrinsicMeasurable2, this.isHorizontal, ConstraintsKt.Constraints$default(0, 0, 0, i, 7));
        boolean z = this.isHorizontal;
        float f = this.crossAxisArrangementSpacing;
        float f2 = this.mainAxisSpacing;
        if (z) {
            List list4 = (List) CollectionsKt___CollectionsKt.firstOrNull(list);
            if (list4 == null) {
                list4 = EmptyList.INSTANCE;
            }
            return minIntrinsicMainAxisSize(list4, i, intrinsicMeasureScope.mo52roundToPx0680j_4(f2), intrinsicMeasureScope.mo52roundToPx0680j_4(f), this.maxItemsInMainAxis, this.maxLines, this.overflow);
        }
        List list5 = (List) CollectionsKt___CollectionsKt.firstOrNull(list);
        if (list5 == null) {
            list5 = EmptyList.INSTANCE;
        }
        return intrinsicCrossAxisSize(list5, i, intrinsicMeasureScope.mo52roundToPx0680j_4(f2), intrinsicMeasureScope.mo52roundToPx0680j_4(f), this.maxItemsInMainAxis, this.maxLines, this.overflow);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("FlowMeasurePolicy(isHorizontal=");
        sb.append(this.isHorizontal);
        sb.append(", horizontalArrangement=");
        sb.append(this.horizontalArrangement);
        sb.append(", verticalArrangement=");
        sb.append(this.verticalArrangement);
        sb.append(", mainAxisSpacing=");
        BorderModifierNodeElement$$ExternalSyntheticOutline0.m(this.mainAxisSpacing, ", crossAxisAlignment=", sb);
        sb.append(this.crossAxisAlignment);
        sb.append(", crossAxisArrangementSpacing=");
        BorderModifierNodeElement$$ExternalSyntheticOutline0.m(this.crossAxisArrangementSpacing, ", maxItemsInMainAxis=", sb);
        sb.append(this.maxItemsInMainAxis);
        sb.append(", maxLines=");
        sb.append(this.maxLines);
        sb.append(", overflow=");
        sb.append(this.overflow);
        sb.append(')');
        return sb.toString();
    }

    private FlowMeasurePolicy(boolean z, Arrangement.Horizontal horizontal, Arrangement.Vertical vertical, float f, CrossAxisAlignment crossAxisAlignment, float f2, int i, int i2, FlowLayoutOverflowState flowLayoutOverflowState) {
        this.isHorizontal = z;
        this.horizontalArrangement = horizontal;
        this.verticalArrangement = vertical;
        this.mainAxisSpacing = f;
        this.crossAxisAlignment = crossAxisAlignment;
        this.crossAxisArrangementSpacing = f2;
        this.maxItemsInMainAxis = i;
        this.maxLines = i2;
        this.overflow = flowLayoutOverflowState;
        this.maxMainAxisIntrinsicItemSize = z ? new Function3() { // from class: androidx.compose.foundation.layout.FlowMeasurePolicy$maxMainAxisIntrinsicItemSize$1
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                ((Number) obj2).intValue();
                return Integer.valueOf(((IntrinsicMeasurable) obj).maxIntrinsicWidth(((Number) obj3).intValue()));
            }
        } : new Function3() { // from class: androidx.compose.foundation.layout.FlowMeasurePolicy$maxMainAxisIntrinsicItemSize$2
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                ((Number) obj2).intValue();
                return Integer.valueOf(((IntrinsicMeasurable) obj).maxIntrinsicHeight(((Number) obj3).intValue()));
            }
        };
        this.minCrossAxisIntrinsicItemSize = z ? new Function3() { // from class: androidx.compose.foundation.layout.FlowMeasurePolicy$minCrossAxisIntrinsicItemSize$1
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                ((Number) obj2).intValue();
                return Integer.valueOf(((IntrinsicMeasurable) obj).minIntrinsicHeight(((Number) obj3).intValue()));
            }
        } : new Function3() { // from class: androidx.compose.foundation.layout.FlowMeasurePolicy$minCrossAxisIntrinsicItemSize$2
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                ((Number) obj2).intValue();
                return Integer.valueOf(((IntrinsicMeasurable) obj).minIntrinsicWidth(((Number) obj3).intValue()));
            }
        };
        this.minMainAxisIntrinsicItemSize = z ? new Function3() { // from class: androidx.compose.foundation.layout.FlowMeasurePolicy$minMainAxisIntrinsicItemSize$1
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                ((Number) obj2).intValue();
                return Integer.valueOf(((IntrinsicMeasurable) obj).minIntrinsicWidth(((Number) obj3).intValue()));
            }
        } : new Function3() { // from class: androidx.compose.foundation.layout.FlowMeasurePolicy$minMainAxisIntrinsicItemSize$2
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                ((Number) obj2).intValue();
                return Integer.valueOf(((IntrinsicMeasurable) obj).minIntrinsicHeight(((Number) obj3).intValue()));
            }
        };
    }
}
