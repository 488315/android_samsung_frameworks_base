package com.android.systemui.communal.ui.viewmodel;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.AnchoredDraggableKt;
import androidx.compose.foundation.gestures.AnchoredDraggableState;
import androidx.compose.foundation.gestures.DraggableAnchors;
import androidx.compose.foundation.gestures.DraggableAnchorsConfig;
import androidx.compose.foundation.shape.DpCornerSize$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.internal.ProgressionUtilKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.ranges.RangesKt___RangesKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ResizeableItemFrameViewModel extends ExclusiveActivatable {
    public final Flow resizeInfo;
    public final StateFlowImpl gridLayoutInfo = StateFlowKt.MutableStateFlow(null);
    public final AnchoredDraggableState topDragState = new AnchoredDraggableState(0, (DraggableAnchors<int>) AnchoredDraggableKt.DraggableAnchors(new ResizeableItemFrameViewModel$$ExternalSyntheticLambda0(1)));
    public final AnchoredDraggableState bottomDragState = new AnchoredDraggableState(0, (DraggableAnchors<int>) AnchoredDraggableKt.DraggableAnchors(new ResizeableItemFrameViewModel$$ExternalSyntheticLambda0(2)));

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class GridLayoutInfo {
        public final int currentRow;
        public final int currentSpan;
        public final float heightPerSpanPx;
        public final int maxHeightPx;
        public final int minHeightPx;
        public final int resizeMultiple;
        public final int totalSpans;
        public final float verticalItemSpacingPx;

        public GridLayoutInfo(int i, int i2, int i3, int i4, int i5, int i6, float f, float f2) {
            this.currentRow = i;
            this.currentSpan = i2;
            this.maxHeightPx = i3;
            this.minHeightPx = i4;
            this.resizeMultiple = i5;
            this.totalSpans = i6;
            this.heightPerSpanPx = f;
            this.verticalItemSpacingPx = f2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof GridLayoutInfo)) {
                return false;
            }
            GridLayoutInfo gridLayoutInfo = (GridLayoutInfo) obj;
            return this.currentRow == gridLayoutInfo.currentRow && this.currentSpan == gridLayoutInfo.currentSpan && this.maxHeightPx == gridLayoutInfo.maxHeightPx && this.minHeightPx == gridLayoutInfo.minHeightPx && this.resizeMultiple == gridLayoutInfo.resizeMultiple && this.totalSpans == gridLayoutInfo.totalSpans && Float.compare(this.heightPerSpanPx, gridLayoutInfo.heightPerSpanPx) == 0 && Float.compare(this.verticalItemSpacingPx, gridLayoutInfo.verticalItemSpacingPx) == 0;
        }

        public final int hashCode() {
            return Float.hashCode(this.verticalItemSpacingPx) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.heightPerSpanPx, ReorderTile$$ExternalSyntheticOutline0.m(this.totalSpans, ReorderTile$$ExternalSyntheticOutline0.m(this.resizeMultiple, ReorderTile$$ExternalSyntheticOutline0.m(this.minHeightPx, ReorderTile$$ExternalSyntheticOutline0.m(this.maxHeightPx, ReorderTile$$ExternalSyntheticOutline0.m(this.currentSpan, Integer.hashCode(this.currentRow) * 31, 31), 31), 31), 31), 31), 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("GridLayoutInfo(currentRow=");
            sb.append(this.currentRow);
            sb.append(", currentSpan=");
            sb.append(this.currentSpan);
            sb.append(", maxHeightPx=");
            sb.append(this.maxHeightPx);
            sb.append(", minHeightPx=");
            sb.append(this.minHeightPx);
            sb.append(", resizeMultiple=");
            sb.append(this.resizeMultiple);
            sb.append(", totalSpans=");
            sb.append(this.totalSpans);
            sb.append(", heightPerSpanPx=");
            sb.append(this.heightPerSpanPx);
            sb.append(", verticalItemSpacingPx=");
            return DpCornerSize$$ExternalSyntheticOutline0.m(this.verticalItemSpacingPx, ")", sb);
        }
    }

    public ResizeableItemFrameViewModel() {
        final int i = 0;
        final SafeFlow snapshotFlow = SnapshotStateKt.snapshotFlow(new Function0(this) { // from class: com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel$$ExternalSyntheticLambda4
            public final /* synthetic */ ResizeableItemFrameViewModel f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        return Integer.valueOf(((Number) ((SnapshotMutableStateImpl) this.f$0.topDragState.settledValue$delegate).getValue()).intValue());
                    default:
                        return Integer.valueOf(((Number) ((SnapshotMutableStateImpl) this.f$0.bottomDragState.settledValue$delegate).getValue()).intValue());
                }
            }
        });
        Flow flow = new Flow() { // from class: com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4b
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Number r5 = (java.lang.Number) r5
                        int r5 = r5.intValue()
                        com.android.systemui.communal.ui.viewmodel.ResizeInfo r6 = new com.android.systemui.communal.ui.viewmodel.ResizeInfo
                        int r5 = -r5
                        com.android.systemui.communal.ui.viewmodel.DragHandle r2 = com.android.systemui.communal.ui.viewmodel.DragHandle.TOP
                        r6.<init>(r5, r2)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r6, r0)
                        if (r4 != r1) goto L4b
                        return r1
                    L4b:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final int i2 = 1;
        final SafeFlow snapshotFlow2 = SnapshotStateKt.snapshotFlow(new Function0(this) { // from class: com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel$$ExternalSyntheticLambda4
            public final /* synthetic */ ResizeableItemFrameViewModel f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        return Integer.valueOf(((Number) ((SnapshotMutableStateImpl) this.f$0.topDragState.settledValue$delegate).getValue()).intValue());
                    default:
                        return Integer.valueOf(((Number) ((SnapshotMutableStateImpl) this.f$0.bottomDragState.settledValue$delegate).getValue()).intValue());
                }
            }
        });
        final ChannelLimitedFlowMerge merge = FlowKt.merge(flow, new Flow() { // from class: com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel$special$$inlined$map$2$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel$special$$inlined$map$2$2$1 r0 = (com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel$special$$inlined$map$2$2$1 r0 = new com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4a
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Number r5 = (java.lang.Number) r5
                        int r5 = r5.intValue()
                        com.android.systemui.communal.ui.viewmodel.ResizeInfo r6 = new com.android.systemui.communal.ui.viewmodel.ResizeInfo
                        com.android.systemui.communal.ui.viewmodel.DragHandle r2 = com.android.systemui.communal.ui.viewmodel.DragHandle.BOTTOM
                        r6.<init>(r5, r2)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r6, r0)
                        if (r4 != r1) goto L4a
                        return r1
                    L4a:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        this.resizeInfo = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel$special$$inlined$filter$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel$special$$inlined$filter$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    Object L$1;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel$special$$inlined$filter$1$2$1 r0 = (com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel$special$$inlined$filter$1$2$1 r0 = new com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel$special$$inlined$filter$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L44
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        com.android.systemui.communal.ui.viewmodel.ResizeInfo r6 = (com.android.systemui.communal.ui.viewmodel.ResizeInfo) r6
                        int r6 = r6.spans
                        if (r6 == 0) goto L44
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L44
                        return r1
                    L44:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel$special$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
    }

    public static final DraggableAnchors access$calculateAnchorsForHandle(ResizeableItemFrameViewModel resizeableItemFrameViewModel, DragHandle dragHandle, final GridLayoutInfo gridLayoutInfo) {
        final int i;
        resizeableItemFrameViewModel.getClass();
        if (gridLayoutInfo != null) {
            int i2 = gridLayoutInfo.minHeightPx;
            float f = gridLayoutInfo.verticalItemSpacingPx;
            float f2 = gridLayoutInfo.heightPerSpanPx;
            int ceil = (int) Math.ceil((i2 + f) / (f2 + f));
            int i3 = gridLayoutInfo.resizeMultiple;
            int i4 = gridLayoutInfo.totalSpans;
            int floor = ((int) Math.floor(RangesKt___RangesKt.coerceIn(ceil, i3, i4) / i3)) * i3;
            int i5 = gridLayoutInfo.currentSpan;
            if (floor > i5) {
                floor = i5;
            }
            int i6 = gridLayoutInfo.maxHeightPx;
            int floor2 = ((int) Math.floor(RangesKt___RangesKt.coerceIn((int) Math.ceil((i6 + f) / (f2 + f)), i3, i4) / i3)) * i3;
            if (floor2 < i5) {
                floor2 = i5;
            }
            final int i7 = 0;
            boolean z = i5 == floor;
            DragHandle dragHandle2 = DragHandle.TOP;
            final int i8 = gridLayoutInfo.currentRow;
            if ((dragHandle != dragHandle2 || i8 != 0 || !z) && ((dragHandle != DragHandle.BOTTOM || i8 + i5 != floor2 || !z) && (dragHandle != dragHandle2 || i5 != floor2))) {
                int floor3 = ((int) Math.floor(RangesKt___RangesKt.coerceIn((int) Math.ceil((i2 + f) / (f2 + f)), i3, i4) / i3)) * i3;
                if (floor3 > i5) {
                    floor3 = i5;
                }
                int floor4 = ((int) Math.floor(RangesKt___RangesKt.coerceIn((int) Math.ceil((i6 + f) / (f2 + f)), i3, i4) / i3)) * i3;
                if (floor4 < i5) {
                    floor4 = i5;
                }
                if (dragHandle == dragHandle2) {
                    i = (i8 + i5) - floor3;
                    if (i < 0) {
                        i = 0;
                    }
                } else {
                    i = i8 + floor4;
                    if (i > i4) {
                        i = i4;
                    }
                }
                if (dragHandle == dragHandle2) {
                    int i9 = (i8 + i5) - floor4;
                    if (i9 >= 0) {
                        i7 = i9;
                    }
                } else {
                    int i10 = floor3 + i8;
                    if (i10 <= i4) {
                        i4 = i10;
                    }
                    i7 = i4;
                }
                if (dragHandle != dragHandle2) {
                    i8 += i5;
                }
                return AnchoredDraggableKt.DraggableAnchors(new Function1() { // from class: com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj) {
                        DraggableAnchorsConfig draggableAnchorsConfig = (DraggableAnchorsConfig) obj;
                        int i11 = ResizeableItemFrameViewModel.GridLayoutInfo.this.resizeMultiple;
                        if (i11 <= 0) {
                            throw new IllegalArgumentException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i11, "Step must be positive, was: ", "."));
                        }
                        int i12 = i7;
                        int progressionLastElement = ProgressionUtilKt.getProgressionLastElement(i12, i, i11);
                        if (i12 <= progressionLastElement) {
                            while (true) {
                                draggableAnchorsConfig.at(Integer.valueOf(i12 - i8), Integer.signum(r4) * ((int) ((r0.heightPerSpanPx + r0.verticalItemSpacingPx) * Math.abs(r4))));
                                if (i12 == progressionLastElement) {
                                    break;
                                }
                                i12 += i11;
                            }
                        }
                        return Unit.INSTANCE;
                    }
                });
            }
        }
        return AnchoredDraggableKt.DraggableAnchors(new ResizeableItemFrameViewModel$$ExternalSyntheticLambda0(0));
    }

    public static Integer getNextAnchor(AnchoredDraggableState anchoredDraggableState, boolean z) {
        int intValue;
        int intValue2 = ((Number) ((SnapshotMutableStateImpl) anchoredDraggableState.currentValue$delegate).getValue()).intValue();
        int size = anchoredDraggableState.getAnchors().getSize();
        Integer num = null;
        int i = Integer.MAX_VALUE;
        for (int i2 = 0; i2 < size; i2++) {
            Integer num2 = (Integer) anchoredDraggableState.getAnchors().anchorAt(i2);
            if (num2 != null && (intValue = num2.intValue()) != intValue2) {
                int i3 = z ? intValue2 - intValue : intValue - intValue2;
                if (1 <= i3 && i3 < i) {
                    num = num2;
                    i = i3;
                }
            }
        }
        return num;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object onActivated(kotlin.coroutines.Continuation r6) {
        /*
            r5 = this;
            boolean r0 = r6 instanceof com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel$onActivated$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel$onActivated$1 r0 = (com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel$onActivated$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel$onActivated$1 r0 = new com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel$onActivated$1
            r0.<init>(r5, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 == r3) goto L2b
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L2b:
            kotlin.ResultKt.throwOnFailure(r6)
            goto L43
        L2f:
            kotlin.ResultKt.throwOnFailure(r6)
            com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel$onActivated$$inlined$coroutineScopeTraced$1 r6 = new com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel$onActivated$$inlined$coroutineScopeTraced$1
            r2 = 0
            java.lang.String r4 = "ResizeableItemFrameViewModel.onActivated"
            r6.<init>(r2, r4, r5)
            r0.label = r3
            java.lang.Object r5 = kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r6, r0)
            if (r5 != r1) goto L43
            return r1
        L43:
            kotlin.KotlinNothingValueException r5 = new kotlin.KotlinNothingValueException
            r5.<init>()
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel.onActivated(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
