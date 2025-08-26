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
import com.android.systemui.lifecycle.ExclusiveActivatable;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.internal.ProgressionUtilKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.ranges.RangesKt___RangesKt;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

/* loaded from: classes2.dex */
public final class ResizeableItemFrameViewModel extends ExclusiveActivatable {
    public final Flow resizeInfo;
    public final StateFlowImpl gridLayoutInfo = StateFlowKt.MutableStateFlow(null);
    public final AnchoredDraggableState topDragState = new AnchoredDraggableState(0, (DraggableAnchors<int>) AnchoredDraggableKt.DraggableAnchors(new ResizeableItemFrameViewModel$$ExternalSyntheticLambda0(1)));
    public final AnchoredDraggableState bottomDragState = new AnchoredDraggableState(0, (DraggableAnchors<int>) AnchoredDraggableKt.DraggableAnchors(new ResizeableItemFrameViewModel$$ExternalSyntheticLambda0(2)));

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

    /* renamed from: com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel$onActivated$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ResizeableItemFrameViewModel.this.onActivated(this);
        }
    }

    public ResizeableItemFrameViewModel() {
        final int i = 0;
        final SafeFlow safeFlowSnapshotFlow = SnapshotStateKt.snapshotFlow(new Function0(this) { // from class: com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel$$ExternalSyntheticLambda4
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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        ResizeInfo resizeInfo = new ResizeInfo(-((Number) obj).intValue(), DragHandle.TOP);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(resizeInfo, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = safeFlowSnapshotFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        final int i2 = 1;
        final SafeFlow safeFlowSnapshotFlow2 = SnapshotStateKt.snapshotFlow(new Function0(this) { // from class: com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel$$ExternalSyntheticLambda4
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
        final ChannelLimitedFlowMerge channelLimitedFlowMergeMerge = FlowKt.merge(flow, new Flow() { // from class: com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel$special$$inlined$map$2

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        ResizeInfo resizeInfo = new ResizeInfo(((Number) obj).intValue(), DragHandle.BOTTOM);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(resizeInfo, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = safeFlowSnapshotFlow2.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        this.resizeInfo = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel$special$$inlined$filter$1

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        if (((ResizeInfo) obj).spans != 0) {
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = channelLimitedFlowMergeMerge.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
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
            int iCeil = (int) Math.ceil((i2 + f) / (f2 + f));
            int i3 = gridLayoutInfo.resizeMultiple;
            int i4 = gridLayoutInfo.totalSpans;
            int iFloor = ((int) Math.floor(RangesKt___RangesKt.coerceIn(iCeil, i3, i4) / i3)) * i3;
            int i5 = gridLayoutInfo.currentSpan;
            if (iFloor > i5) {
                iFloor = i5;
            }
            int i6 = gridLayoutInfo.maxHeightPx;
            int iFloor2 = ((int) Math.floor(RangesKt___RangesKt.coerceIn((int) Math.ceil((i6 + f) / (f2 + f)), i3, i4) / i3)) * i3;
            if (iFloor2 < i5) {
                iFloor2 = i5;
            }
            final int i7 = 0;
            boolean z = i5 == iFloor;
            DragHandle dragHandle2 = DragHandle.TOP;
            final int i8 = gridLayoutInfo.currentRow;
            if ((dragHandle != dragHandle2 || i8 != 0 || !z) && ((dragHandle != DragHandle.BOTTOM || i8 + i5 != iFloor2 || !z) && (dragHandle != dragHandle2 || i5 != iFloor2))) {
                int iFloor3 = ((int) Math.floor(RangesKt___RangesKt.coerceIn((int) Math.ceil((i2 + f) / (f2 + f)), i3, i4) / i3)) * i3;
                if (iFloor3 > i5) {
                    iFloor3 = i5;
                }
                int iFloor4 = ((int) Math.floor(RangesKt___RangesKt.coerceIn((int) Math.ceil((i6 + f) / (f2 + f)), i3, i4) / i3)) * i3;
                if (iFloor4 < i5) {
                    iFloor4 = i5;
                }
                if (dragHandle == dragHandle2) {
                    i = (i8 + i5) - iFloor3;
                    if (i < 0) {
                        i = 0;
                    }
                } else {
                    i = i8 + iFloor4;
                    if (i > i4) {
                        i = i4;
                    }
                }
                if (dragHandle == dragHandle2) {
                    int i9 = (i8 + i5) - iFloor4;
                    if (i9 >= 0) {
                        i7 = i9;
                    }
                } else {
                    int i10 = iFloor3 + i8;
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
                    public final Object mo781invoke(Object obj) {
                        DraggableAnchorsConfig draggableAnchorsConfig = (DraggableAnchorsConfig) obj;
                        int i11 = gridLayoutInfo.resizeMultiple;
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
        int iIntValue;
        int iIntValue2 = ((Number) ((SnapshotMutableStateImpl) anchoredDraggableState.currentValue$delegate).getValue()).intValue();
        int size = anchoredDraggableState.getAnchors().getSize();
        Integer num = null;
        int i = Integer.MAX_VALUE;
        for (int i2 = 0; i2 < size; i2++) {
            Integer num2 = (Integer) anchoredDraggableState.getAnchors().anchorAt(i2);
            if (num2 != null && (iIntValue = num2.intValue()) != iIntValue2) {
                int i3 = z ? iIntValue2 - iIntValue : iIntValue - iIntValue2;
                if (1 <= i3 && i3 < i) {
                    num = num2;
                    i = i3;
                }
            }
        }
        return num;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object onActivated(Continuation continuation) {
        AnonymousClass1 anonymousClass1;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            ResizeableItemFrameViewModel$onActivated$$inlined$coroutineScopeTraced$1 resizeableItemFrameViewModel$onActivated$$inlined$coroutineScopeTraced$1 = new ResizeableItemFrameViewModel$onActivated$$inlined$coroutineScopeTraced$1(null, "ResizeableItemFrameViewModel.onActivated", this);
            anonymousClass1.label = 1;
            if (CoroutineScopeKt.coroutineScope(resizeableItemFrameViewModel$onActivated$$inlined$coroutineScopeTraced$1, anonymousClass1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
