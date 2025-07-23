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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        return this.isHorizontal == flowMeasurePolicy.isHorizontal && Intrinsics.areEqual(this.horizontalArrangement, flowMeasurePolicy.horizontalArrangement) && Intrinsics.areEqual(this.verticalArrangement, flowMeasurePolicy.verticalArrangement) && Dp.m836equalsimpl0(this.mainAxisSpacing, flowMeasurePolicy.mainAxisSpacing) && Intrinsics.areEqual(this.crossAxisAlignment, flowMeasurePolicy.crossAxisAlignment) && Dp.m836equalsimpl0(this.crossAxisArrangementSpacing, flowMeasurePolicy.crossAxisArrangementSpacing) && this.maxItemsInMainAxis == flowMeasurePolicy.maxItemsInMainAxis && this.maxLines == flowMeasurePolicy.maxLines && Intrinsics.areEqual(this.overflow, flowMeasurePolicy.overflow);
    }

    public final int hashCode() {
        int hashCode = (this.verticalArrangement.hashCode() + ((this.horizontalArrangement.hashCode() + (Boolean.hashCode(this.isHorizontal) * 31)) * 31)) * 31;
        Dp.Companion companion = Dp.Companion;
        return this.overflow.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.maxLines, ReorderTile$$ExternalSyntheticOutline0.m(this.maxItemsInMainAxis, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.crossAxisArrangementSpacing, (this.crossAxisAlignment.hashCode() + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.mainAxisSpacing, hashCode, 31)) * 31, 31), 31), 31);
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
        this.overflow.m106setOverflowMeasurableshBUhpc$foundation_layout(intrinsicMeasurable, intrinsicMeasurable2, this.isHorizontal, ConstraintsKt.Constraints$default(0, i, 0, 0, 13));
        boolean z = this.isHorizontal;
        float f = this.mainAxisSpacing;
        if (!z) {
            List list4 = (List) CollectionsKt___CollectionsKt.firstOrNull(list);
            if (list4 == null) {
                list4 = EmptyList.INSTANCE;
            }
            return maxIntrinsicMainAxisSize(list4, i, intrinsicMeasureScope.mo51roundToPx0680j_4(f));
        }
        List list5 = (List) CollectionsKt___CollectionsKt.firstOrNull(list);
        if (list5 == null) {
            list5 = EmptyList.INSTANCE;
        }
        return intrinsicCrossAxisSize(list5, i, intrinsicMeasureScope.mo51roundToPx0680j_4(f), intrinsicMeasureScope.mo51roundToPx0680j_4(this.crossAxisArrangementSpacing), this.maxItemsInMainAxis, this.maxLines, this.overflow);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [kotlin.jvm.functions.Function3, kotlin.jvm.internal.Lambda] */
    public final int maxIntrinsicMainAxisSize(List list, int i, int i2) {
        ?? r0 = this.maxMainAxisIntrinsicItemSize;
        int i3 = FlowLayoutKt.$r8$clinit;
        int size = list.size();
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        while (i4 < size) {
            int intValue = ((Number) r0.invoke((IntrinsicMeasurable) list.get(i4), Integer.valueOf(i4), Integer.valueOf(i))).intValue() + i2;
            int i8 = i4 + 1;
            if (i8 - i6 == this.maxItemsInMainAxis || i8 == list.size()) {
                i5 = Math.max(i5, (i7 + intValue) - i2);
                i7 = 0;
                i6 = i4;
            } else {
                i7 += intValue;
            }
            i4 = i8;
        }
        return i5;
    }

    @Override // androidx.compose.ui.layout.MultiContentMeasurePolicy
    public final int maxIntrinsicWidth(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
        List list2 = (List) CollectionsKt___CollectionsKt.getOrNull(1, list);
        IntrinsicMeasurable intrinsicMeasurable = list2 != null ? (IntrinsicMeasurable) CollectionsKt___CollectionsKt.firstOrNull(list2) : null;
        List list3 = (List) CollectionsKt___CollectionsKt.getOrNull(2, list);
        IntrinsicMeasurable intrinsicMeasurable2 = list3 != null ? (IntrinsicMeasurable) CollectionsKt___CollectionsKt.firstOrNull(list3) : null;
        this.overflow.m106setOverflowMeasurableshBUhpc$foundation_layout(intrinsicMeasurable, intrinsicMeasurable2, this.isHorizontal, ConstraintsKt.Constraints$default(0, 0, 0, i, 7));
        boolean z = this.isHorizontal;
        float f = this.mainAxisSpacing;
        if (z) {
            List list4 = (List) CollectionsKt___CollectionsKt.firstOrNull(list);
            if (list4 == null) {
                list4 = EmptyList.INSTANCE;
            }
            return maxIntrinsicMainAxisSize(list4, i, intrinsicMeasureScope.mo51roundToPx0680j_4(f));
        }
        List list5 = (List) CollectionsKt___CollectionsKt.firstOrNull(list);
        if (list5 == null) {
            list5 = EmptyList.INSTANCE;
        }
        return intrinsicCrossAxisSize(list5, i, intrinsicMeasureScope.mo51roundToPx0680j_4(f), intrinsicMeasureScope.mo51roundToPx0680j_4(this.crossAxisArrangementSpacing), this.maxItemsInMainAxis, this.maxLines, this.overflow);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.compose.ui.layout.MultiContentMeasurePolicy
    /* renamed from: measure-3p2s80s, reason: not valid java name */
    public final MeasureResult mo107measure3p2s80s(MeasureScope measureScope, List list, long j) {
        MeasureResult layout$1;
        long Constraints;
        long Constraints2;
        int i;
        Measurable safeNext;
        FlowLayoutBuildingBlocks.WrapInfo wrapInfo;
        FlowLayoutBuildingBlocks.WrapEllipsisInfo wrapEllipsisInfo;
        int i2;
        int i3;
        int i4;
        MeasureResult layout$12;
        int height;
        int width;
        int i5;
        Integer num;
        IntIntPair m0boximpl;
        FlowLayoutBuildingBlocks.WrapInfo wrapInfo2;
        MutableIntList mutableIntList;
        MutableIntList mutableIntList2;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        MeasureResult layout$13;
        final FlowMeasurePolicy flowMeasurePolicy = this;
        MeasureScope measureScope2 = measureScope;
        if (flowMeasurePolicy.maxLines != 0 && flowMeasurePolicy.maxItemsInMainAxis != 0 && !((ArrayList) list).isEmpty()) {
            int m820getMaxHeightimpl = Constraints.m820getMaxHeightimpl(j);
            final FlowLayoutOverflowState flowLayoutOverflowState = flowMeasurePolicy.overflow;
            if (m820getMaxHeightimpl != 0 || flowLayoutOverflowState.type == FlowLayoutOverflow.OverflowType.Visible) {
                List list2 = (List) CollectionsKt___CollectionsKt.first(list);
                if (list2.isEmpty()) {
                    layout$13 = measureScope2.layout$1(0, 0, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.foundation.layout.FlowMeasurePolicy$measure$2
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final /* bridge */ /* synthetic */ Object mo779invoke(Object obj) {
                            return Unit.INSTANCE;
                        }
                    });
                    return layout$13;
                }
                List list3 = (List) CollectionsKt___CollectionsKt.getOrNull(1, list);
                Measurable measurable = list3 != null ? (Measurable) CollectionsKt___CollectionsKt.firstOrNull(list3) : null;
                List list4 = (List) CollectionsKt___CollectionsKt.getOrNull(2, list);
                Measurable measurable2 = list4 != null ? (Measurable) CollectionsKt___CollectionsKt.firstOrNull(list4) : null;
                list2.size();
                flowLayoutOverflowState.getClass();
                boolean z = flowMeasurePolicy.isHorizontal;
                LayoutOrientation layoutOrientation = z ? LayoutOrientation.Horizontal : LayoutOrientation.Vertical;
                Constraints = ConstraintsKt.Constraints(0, Constraints.m821getMaxWidthimpl(r10), (r2 & 4) != 0 ? Constraints.m822getMinHeightimpl(r10) : 0, Constraints.m820getMaxHeightimpl(OrientationIndependentConstraints.m116constructorimpl(j, layoutOrientation)));
                long m118toBoxConstraintsOenEA2s = OrientationIndependentConstraints.m118toBoxConstraintsOenEA2s(Constraints, layoutOrientation);
                if (measurable != null) {
                    FlowLayoutKt.m104measureAndCacherqJ1uqs(measurable, flowMeasurePolicy, m118toBoxConstraintsOenEA2s, new Function1() { // from class: androidx.compose.foundation.layout.FlowLayoutOverflowState$setOverflowMeasurables$3$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo779invoke(Object obj) {
                            int i11;
                            int i12;
                            Placeable placeable = (Placeable) obj;
                            if (placeable != null) {
                                FlowLineMeasurePolicy flowLineMeasurePolicy = flowMeasurePolicy;
                                i11 = flowLineMeasurePolicy.mainAxisSize(placeable);
                                i12 = flowLineMeasurePolicy.crossAxisSize(placeable);
                            } else {
                                i11 = 0;
                                i12 = 0;
                            }
                            FlowLayoutOverflowState.this.seeMoreSize = IntIntPair.m0boximpl(IntIntPair.m1constructorimpl(i11, i12));
                            FlowLayoutOverflowState.this.seeMorePlaceable = placeable;
                            return Unit.INSTANCE;
                        }
                    });
                    flowLayoutOverflowState.seeMoreMeasurable = measurable;
                }
                if (measurable2 != null) {
                    FlowLayoutKt.m104measureAndCacherqJ1uqs(measurable2, flowMeasurePolicy, m118toBoxConstraintsOenEA2s, new Function1() { // from class: androidx.compose.foundation.layout.FlowLayoutOverflowState$setOverflowMeasurables$4$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo779invoke(Object obj) {
                            int i11;
                            int i12;
                            Placeable placeable = (Placeable) obj;
                            if (placeable != null) {
                                FlowLineMeasurePolicy flowLineMeasurePolicy = flowMeasurePolicy;
                                i11 = flowLineMeasurePolicy.mainAxisSize(placeable);
                                i12 = flowLineMeasurePolicy.crossAxisSize(placeable);
                            } else {
                                i11 = 0;
                                i12 = 0;
                            }
                            FlowLayoutOverflowState.this.collapseSize = IntIntPair.m0boximpl(IntIntPair.m1constructorimpl(i11, i12));
                            FlowLayoutOverflowState.this.collapsePlaceable = placeable;
                            return Unit.INSTANCE;
                        }
                    });
                    flowLayoutOverflowState.collapseMeasurable = measurable2;
                }
                Iterator it = list2.iterator();
                long m116constructorimpl = OrientationIndependentConstraints.m116constructorimpl(j, z ? LayoutOrientation.Horizontal : LayoutOrientation.Vertical);
                int i11 = FlowLayoutKt.$r8$clinit;
                MutableVector mutableVector = new MutableVector(new MeasureResult[16], 0);
                int m821getMaxWidthimpl = Constraints.m821getMaxWidthimpl(m116constructorimpl);
                int m823getMinWidthimpl = Constraints.m823getMinWidthimpl(m116constructorimpl);
                int m820getMaxHeightimpl2 = Constraints.m820getMaxHeightimpl(m116constructorimpl);
                MutableIntObjectMap mutableIntObjectMapOf = IntObjectMapKt.mutableIntObjectMapOf();
                int ceil = (int) Math.ceil(measureScope2.mo57toPx0680j_4(flowMeasurePolicy.mainAxisSpacing));
                int ceil2 = (int) Math.ceil(measureScope2.mo57toPx0680j_4(flowMeasurePolicy.crossAxisArrangementSpacing));
                ArrayList arrayList = new ArrayList();
                long Constraints3 = ConstraintsKt.Constraints(0, m821getMaxWidthimpl, 0, m820getMaxHeightimpl2);
                Constraints2 = ConstraintsKt.Constraints(0, Constraints.m821getMaxWidthimpl(Constraints3), (r2 & 4) != 0 ? Constraints.m822getMinHeightimpl(Constraints3) : 0, Constraints.m820getMaxHeightimpl(Constraints3));
                long m118toBoxConstraintsOenEA2s2 = OrientationIndependentConstraints.m118toBoxConstraintsOenEA2s(Constraints2, z ? LayoutOrientation.Horizontal : LayoutOrientation.Vertical);
                final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                MutableVector mutableVector2 = mutableVector;
                FlowLineInfo flowLineInfo = it instanceof ContextualFlowItemIterator ? new FlowLineInfo(0, 0, measureScope2.mo54toDpu2uoSUM(m821getMaxWidthimpl), measureScope2.mo54toDpu2uoSUM(m820getMaxHeightimpl2), null) : null;
                if (it.hasNext()) {
                    i = m823getMinWidthimpl;
                    safeNext = FlowLayoutKt.safeNext(it, flowLineInfo);
                } else {
                    i = m823getMinWidthimpl;
                    safeNext = null;
                }
                IntIntPair m0boximpl2 = safeNext != null ? IntIntPair.m0boximpl(FlowLayoutKt.m104measureAndCacherqJ1uqs(safeNext, flowMeasurePolicy, m118toBoxConstraintsOenEA2s2, new Function1() { // from class: androidx.compose.foundation.layout.FlowLayoutKt$breakDownItems$nextSize$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    /* JADX WARN: Type inference failed for: r1v1, types: [T, androidx.compose.ui.layout.Placeable] */
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj) {
                        ref$ObjectRef.element = (Placeable) obj;
                        return Unit.INSTANCE;
                    }
                })) : null;
                Integer valueOf = m0boximpl2 != null ? Integer.valueOf((int) (m0boximpl2.packedValue >> 32)) : null;
                Integer valueOf2 = m0boximpl2 != null ? Integer.valueOf((int) (m0boximpl2.packedValue & 4294967295L)) : null;
                Measurable measurable3 = safeNext;
                IntIntPair intIntPair = m0boximpl2;
                MutableIntList mutableIntList3 = new MutableIntList(0, 1, null);
                MutableIntList mutableIntList4 = new MutableIntList(0, 1, null);
                int i12 = flowMeasurePolicy.maxLines;
                int i13 = flowMeasurePolicy.maxItemsInMainAxis;
                FlowLayoutOverflowState flowLayoutOverflowState2 = flowMeasurePolicy.overflow;
                FlowLayoutBuildingBlocks flowLayoutBuildingBlocks = new FlowLayoutBuildingBlocks(i13, flowLayoutOverflowState2, m116constructorimpl, i12, ceil, ceil2, null);
                FlowLayoutBuildingBlocks.WrapInfo m103getWrapInfoOpUlnko = flowLayoutBuildingBlocks.m103getWrapInfoOpUlnko(it.hasNext(), 0, IntIntPair.m1constructorimpl(m821getMaxWidthimpl, m820getMaxHeightimpl2), intIntPair, 0, 0, 0, false, false);
                if (m103getWrapInfoOpUlnko.isLastItemInContainer) {
                    wrapInfo = m103getWrapInfoOpUlnko;
                    wrapEllipsisInfo = flowLayoutBuildingBlocks.getWrapEllipsisInfo(wrapInfo, intIntPair != null, -1, 0, m821getMaxWidthimpl, 0);
                } else {
                    wrapInfo = m103getWrapInfoOpUlnko;
                    wrapEllipsisInfo = null;
                }
                int i14 = m821getMaxWidthimpl;
                FlowLayoutBuildingBlocks.WrapEllipsisInfo wrapEllipsisInfo2 = wrapEllipsisInfo;
                int i15 = i14;
                Integer num2 = valueOf2;
                MutableIntList mutableIntList5 = mutableIntList3;
                MutableIntList mutableIntList6 = mutableIntList4;
                int i16 = m820getMaxHeightimpl2;
                FlowLayoutBuildingBlocks.WrapInfo wrapInfo3 = wrapInfo;
                int i17 = i;
                int i18 = 0;
                int i19 = 0;
                int i20 = 0;
                int i21 = 0;
                int i22 = 0;
                int i23 = 0;
                Measurable measurable4 = measurable3;
                while (!wrapInfo3.isLastItemInContainer && measurable4 != null) {
                    valueOf.getClass();
                    int intValue = valueOf.intValue();
                    num2.getClass();
                    int intValue2 = num2.intValue();
                    int i24 = i14;
                    int i25 = i20 + intValue;
                    int max = Math.max(i19, intValue2);
                    int i26 = i15 - intValue;
                    flowLayoutOverflowState2.getClass();
                    int i27 = i18 + 1;
                    ArrayList arrayList2 = arrayList;
                    arrayList2.add(measurable4);
                    mutableIntObjectMapOf.set(i18, ref$ObjectRef.element);
                    int i28 = i27 - i21;
                    boolean z2 = i28 < i13;
                    if (flowLineInfo != null) {
                        if (z2) {
                            int i29 = i26 - ceil;
                            i5 = i28;
                            i9 = i29 < 0 ? 0 : i29;
                        } else {
                            i5 = i28;
                            i9 = i24;
                        }
                        measureScope2.mo54toDpu2uoSUM(i9);
                        if (z2) {
                            i10 = i16;
                        } else {
                            i10 = (i16 - max) - ceil2;
                            if (i10 < 0) {
                                i10 = 0;
                            }
                        }
                        measureScope2.mo54toDpu2uoSUM(i10);
                    } else {
                        i5 = i28;
                    }
                    Measurable safeNext2 = !it.hasNext() ? null : FlowLayoutKt.safeNext(it, flowLineInfo);
                    ref$ObjectRef.element = null;
                    IntIntPair m0boximpl3 = safeNext2 != null ? IntIntPair.m0boximpl(FlowLayoutKt.m104measureAndCacherqJ1uqs(safeNext2, flowMeasurePolicy, m118toBoxConstraintsOenEA2s2, new Function1() { // from class: androidx.compose.foundation.layout.FlowLayoutKt$breakDownItems$1$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        /* JADX WARN: Type inference failed for: r1v1, types: [T, androidx.compose.ui.layout.Placeable] */
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo779invoke(Object obj) {
                            ref$ObjectRef.element = (Placeable) obj;
                            return Unit.INSTANCE;
                        }
                    })) : null;
                    Integer valueOf3 = m0boximpl3 != null ? Integer.valueOf(((int) (m0boximpl3.packedValue >> 32)) + ceil) : null;
                    Integer valueOf4 = m0boximpl3 != null ? Integer.valueOf((int) (m0boximpl3.packedValue & 4294967295L)) : null;
                    boolean hasNext = it.hasNext();
                    int i30 = i22;
                    long m1constructorimpl = IntIntPair.m1constructorimpl(i26, i16);
                    if (m0boximpl3 == null) {
                        num = valueOf4;
                        m0boximpl = null;
                    } else {
                        valueOf3.getClass();
                        int intValue3 = valueOf3.intValue();
                        valueOf4.getClass();
                        num = valueOf4;
                        m0boximpl = IntIntPair.m0boximpl(IntIntPair.m1constructorimpl(intValue3, num.intValue()));
                    }
                    FlowLayoutBuildingBlocks.WrapInfo m103getWrapInfoOpUlnko2 = flowLayoutBuildingBlocks.m103getWrapInfoOpUlnko(hasNext, i5, m1constructorimpl, m0boximpl, i30, i23, max, false, false);
                    int i31 = max;
                    Iterator it2 = it;
                    if (m103getWrapInfoOpUlnko2.isLastItemInLine) {
                        int max2 = Math.max(i17, i25);
                        i8 = i24;
                        int min = Math.min(max2, i8);
                        int i32 = i23 + i31;
                        wrapInfo2 = m103getWrapInfoOpUlnko2;
                        FlowLayoutBuildingBlocks.WrapEllipsisInfo wrapEllipsisInfo3 = flowLayoutBuildingBlocks.getWrapEllipsisInfo(wrapInfo2, m0boximpl3 != null, i30, i32, i26, i5);
                        mutableIntList2 = mutableIntList6;
                        mutableIntList2.add(i31);
                        i16 = (m820getMaxHeightimpl2 - i32) - ceil2;
                        mutableIntList = mutableIntList5;
                        mutableIntList.add(i27);
                        i22 = i30 + 1;
                        wrapEllipsisInfo2 = wrapEllipsisInfo3;
                        i27 = i27;
                        i21 = i27;
                        i6 = i8;
                        valueOf = valueOf3 != null ? Integer.valueOf(valueOf3.intValue() - ceil) : null;
                        i23 = i32 + ceil2;
                        i7 = 0;
                        i31 = 0;
                        i17 = min;
                    } else {
                        wrapInfo2 = m103getWrapInfoOpUlnko2;
                        mutableIntList = mutableIntList5;
                        mutableIntList2 = mutableIntList6;
                        i6 = i24;
                        valueOf = valueOf3;
                        i7 = i25;
                        i8 = i26;
                        i22 = i30;
                    }
                    int i33 = i27;
                    i15 = i8;
                    i14 = i6;
                    mutableIntList5 = mutableIntList;
                    measurable4 = safeNext2;
                    i18 = i33;
                    mutableIntList6 = mutableIntList2;
                    arrayList = arrayList2;
                    num2 = num;
                    it = it2;
                    wrapInfo3 = wrapInfo2;
                    i20 = i7;
                    i19 = i31;
                    flowMeasurePolicy = this;
                    measureScope2 = measureScope;
                }
                ArrayList arrayList3 = arrayList;
                MutableIntList mutableIntList7 = mutableIntList5;
                MutableIntList mutableIntList8 = mutableIntList6;
                if (wrapEllipsisInfo2 != null) {
                    FlowLayoutBuildingBlocks.WrapEllipsisInfo wrapEllipsisInfo4 = wrapEllipsisInfo2;
                    arrayList3.add(wrapEllipsisInfo4.ellipsis);
                    mutableIntObjectMapOf.set(arrayList3.size() - 1, wrapEllipsisInfo4.placeable);
                    int i34 = mutableIntList7._size - 1;
                    boolean z3 = wrapEllipsisInfo4.placeEllipsisOnLastContentLine;
                    long j2 = wrapEllipsisInfo4.ellipsisSize;
                    if (z3) {
                        mutableIntList8.set(i34, Math.max(mutableIntList8.get(i34), (int) (j2 & 4294967295L)));
                        mutableIntList7.set(i34, mutableIntList7.last() + 1);
                    } else {
                        mutableIntList8.add((int) (j2 & 4294967295L));
                        mutableIntList7.add(mutableIntList7.last() + 1);
                    }
                }
                int size = arrayList3.size();
                Placeable[] placeableArr = new Placeable[size];
                for (int i35 = 0; i35 < size; i35++) {
                    placeableArr[i35] = mutableIntObjectMapOf.get(i35);
                }
                int i36 = mutableIntList7._size;
                int[] iArr = new int[i36];
                int[] iArr2 = new int[i36];
                int[] iArr3 = mutableIntList7.content;
                int i37 = 0;
                int i38 = 0;
                int i39 = 0;
                Placeable[] placeableArr2 = placeableArr;
                while (i38 < i36) {
                    Placeable[] placeableArr3 = placeableArr2;
                    int i40 = i17;
                    int i41 = iArr3[i38];
                    MutableIntList mutableIntList9 = mutableIntList8;
                    ArrayList arrayList4 = arrayList3;
                    int i42 = i36;
                    MutableVector mutableVector3 = mutableVector2;
                    int[] iArr4 = iArr;
                    MeasureResult measure = RowColumnMeasurePolicyKt.measure(this, i40, Constraints.m822getMinHeightimpl(Constraints3), Constraints.m821getMaxWidthimpl(Constraints3), mutableIntList9.get(i38), ceil, measureScope, arrayList4, placeableArr3, i37, i41, iArr4, i38);
                    iArr = iArr4;
                    arrayList3 = arrayList4;
                    if (z) {
                        height = measure.getWidth();
                        width = measure.getHeight();
                    } else {
                        height = measure.getHeight();
                        width = measure.getWidth();
                    }
                    iArr2[i38] = width;
                    i39 += width;
                    int max3 = Math.max(i40, height);
                    mutableVector3.add(measure);
                    i38++;
                    placeableArr2 = placeableArr3;
                    i37 = i41;
                    mutableVector2 = mutableVector3;
                    i36 = i42;
                    mutableIntList8 = mutableIntList9;
                    i17 = max3;
                }
                final MutableVector mutableVector4 = mutableVector2;
                int i43 = i17;
                if (mutableVector4.size == 0) {
                    i2 = 0;
                    i3 = 0;
                } else {
                    i2 = i43;
                    i3 = i39;
                }
                if (z) {
                    Arrangement.Vertical vertical = this.verticalArrangement;
                    int mo51roundToPx0680j_4 = ((mutableVector4.size - 1) * measureScope.mo51roundToPx0680j_4(vertical.mo94getSpacingD9Ej5fM())) + i3;
                    int m822getMinHeightimpl = Constraints.m822getMinHeightimpl(m116constructorimpl);
                    i4 = Constraints.m820getMaxHeightimpl(m116constructorimpl);
                    if (mo51roundToPx0680j_4 < m822getMinHeightimpl) {
                        mo51roundToPx0680j_4 = m822getMinHeightimpl;
                    }
                    if (mo51roundToPx0680j_4 <= i4) {
                        i4 = mo51roundToPx0680j_4;
                    }
                    vertical.arrange(measureScope, i4, iArr2, iArr);
                } else {
                    Arrangement.Horizontal horizontal = this.horizontalArrangement;
                    int mo51roundToPx0680j_42 = ((mutableVector4.size - 1) * measureScope.mo51roundToPx0680j_4(horizontal.mo94getSpacingD9Ej5fM())) + i3;
                    int m822getMinHeightimpl2 = Constraints.m822getMinHeightimpl(m116constructorimpl);
                    int m820getMaxHeightimpl3 = Constraints.m820getMaxHeightimpl(m116constructorimpl);
                    if (mo51roundToPx0680j_42 < m822getMinHeightimpl2) {
                        mo51roundToPx0680j_42 = m822getMinHeightimpl2;
                    }
                    int i44 = mo51roundToPx0680j_42 > m820getMaxHeightimpl3 ? m820getMaxHeightimpl3 : mo51roundToPx0680j_42;
                    horizontal.arrange(measureScope, i44, iArr2, measureScope.getLayoutDirection(), iArr);
                    i4 = i44;
                }
                int m823getMinWidthimpl2 = Constraints.m823getMinWidthimpl(m116constructorimpl);
                int m821getMaxWidthimpl2 = Constraints.m821getMaxWidthimpl(m116constructorimpl);
                if (i2 < m823getMinWidthimpl2) {
                    i2 = m823getMinWidthimpl2;
                }
                if (i2 <= m821getMaxWidthimpl2) {
                    m821getMaxWidthimpl2 = i2;
                }
                if (z) {
                    int i45 = i4;
                    i4 = m821getMaxWidthimpl2;
                    m821getMaxWidthimpl2 = i45;
                }
                layout$12 = measureScope.layout$1(i4, m821getMaxWidthimpl2, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.foundation.layout.FlowLayoutKt$placeHelper$3
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj) {
                        MutableVector<MeasureResult> mutableVector5 = mutableVector4;
                        Object[] objArr = mutableVector5.content;
                        int i46 = mutableVector5.size;
                        for (int i47 = 0; i47 < i46; i47++) {
                            ((MeasureResult) objArr[i47]).placeChildren();
                        }
                        return Unit.INSTANCE;
                    }
                });
                return layout$12;
            }
        }
        layout$1 = measureScope2.layout$1(0, 0, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.foundation.layout.FlowMeasurePolicy$measure$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final /* bridge */ /* synthetic */ Object mo779invoke(Object obj) {
                return Unit.INSTANCE;
            }
        });
        return layout$1;
    }

    @Override // androidx.compose.ui.layout.MultiContentMeasurePolicy
    public final int minIntrinsicHeight(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
        List list2 = (List) CollectionsKt___CollectionsKt.getOrNull(1, list);
        IntrinsicMeasurable intrinsicMeasurable = list2 != null ? (IntrinsicMeasurable) CollectionsKt___CollectionsKt.firstOrNull(list2) : null;
        List list3 = (List) CollectionsKt___CollectionsKt.getOrNull(2, list);
        IntrinsicMeasurable intrinsicMeasurable2 = list3 != null ? (IntrinsicMeasurable) CollectionsKt___CollectionsKt.firstOrNull(list3) : null;
        this.overflow.m106setOverflowMeasurableshBUhpc$foundation_layout(intrinsicMeasurable, intrinsicMeasurable2, this.isHorizontal, ConstraintsKt.Constraints$default(0, i, 0, 0, 13));
        boolean z = this.isHorizontal;
        float f = this.crossAxisArrangementSpacing;
        float f2 = this.mainAxisSpacing;
        if (z) {
            List list4 = (List) CollectionsKt___CollectionsKt.firstOrNull(list);
            if (list4 == null) {
                list4 = EmptyList.INSTANCE;
            }
            return intrinsicCrossAxisSize(list4, i, intrinsicMeasureScope.mo51roundToPx0680j_4(f2), intrinsicMeasureScope.mo51roundToPx0680j_4(f), this.maxItemsInMainAxis, this.maxLines, this.overflow);
        }
        List list5 = (List) CollectionsKt___CollectionsKt.firstOrNull(list);
        if (list5 == null) {
            list5 = EmptyList.INSTANCE;
        }
        return minIntrinsicMainAxisSize(list5, i, intrinsicMeasureScope.mo51roundToPx0680j_4(f2), intrinsicMeasureScope.mo51roundToPx0680j_4(f), this.maxItemsInMainAxis, this.maxLines, this.overflow);
    }

    /* JADX WARN: Code restructure failed: missing block: B:79:0x0095, code lost:
    
        if (r8.type == androidx.compose.foundation.layout.FlowLayoutOverflow.OverflowType.ExpandOrCollapseIndicator) goto L18;
     */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00a8 A[LOOP:1: B:21:0x00a6->B:22:0x00a8, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0143  */
    /* JADX WARN: Type inference failed for: r0v1, types: [kotlin.jvm.functions.Function3, kotlin.jvm.internal.Lambda] */
    /* JADX WARN: Type inference failed for: r2v0, types: [kotlin.jvm.functions.Function3, kotlin.jvm.internal.Lambda] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int minIntrinsicMainAxisSize(java.util.List r18, int r19, int r20, int r21, int r22, int r23, androidx.compose.foundation.layout.FlowLayoutOverflowState r24) {
        /*
            Method dump skipped, instructions count: 329
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.layout.FlowMeasurePolicy.minIntrinsicMainAxisSize(java.util.List, int, int, int, int, int, androidx.compose.foundation.layout.FlowLayoutOverflowState):int");
    }

    @Override // androidx.compose.ui.layout.MultiContentMeasurePolicy
    public final int minIntrinsicWidth(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
        List list2 = (List) CollectionsKt___CollectionsKt.getOrNull(1, list);
        IntrinsicMeasurable intrinsicMeasurable = list2 != null ? (IntrinsicMeasurable) CollectionsKt___CollectionsKt.firstOrNull(list2) : null;
        List list3 = (List) CollectionsKt___CollectionsKt.getOrNull(2, list);
        IntrinsicMeasurable intrinsicMeasurable2 = list3 != null ? (IntrinsicMeasurable) CollectionsKt___CollectionsKt.firstOrNull(list3) : null;
        this.overflow.m106setOverflowMeasurableshBUhpc$foundation_layout(intrinsicMeasurable, intrinsicMeasurable2, this.isHorizontal, ConstraintsKt.Constraints$default(0, 0, 0, i, 7));
        boolean z = this.isHorizontal;
        float f = this.crossAxisArrangementSpacing;
        float f2 = this.mainAxisSpacing;
        if (z) {
            List list4 = (List) CollectionsKt___CollectionsKt.firstOrNull(list);
            if (list4 == null) {
                list4 = EmptyList.INSTANCE;
            }
            return minIntrinsicMainAxisSize(list4, i, intrinsicMeasureScope.mo51roundToPx0680j_4(f2), intrinsicMeasureScope.mo51roundToPx0680j_4(f), this.maxItemsInMainAxis, this.maxLines, this.overflow);
        }
        List list5 = (List) CollectionsKt___CollectionsKt.firstOrNull(list);
        if (list5 == null) {
            list5 = EmptyList.INSTANCE;
        }
        return intrinsicCrossAxisSize(list5, i, intrinsicMeasureScope.mo51roundToPx0680j_4(f2), intrinsicMeasureScope.mo51roundToPx0680j_4(f), this.maxItemsInMainAxis, this.maxLines, this.overflow);
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
