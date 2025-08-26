package com.android.systemui.communal.ui.compose;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.lazy.grid.LazyGridItemInfo;
import androidx.compose.foundation.lazy.grid.LazyGridMeasureResult;
import androidx.compose.foundation.lazy.grid.LazyGridMeasuredItem;
import androidx.compose.foundation.lazy.grid.LazyGridState;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.collection.MutableVectorKt$$ExternalSyntheticOutline0;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.ListImplementation$$ExternalSyntheticOutline0;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel;
import java.util.Iterator;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

/* loaded from: classes2.dex */
final class ResizeableItemFrameKt$UpdateGridLayoutInfo$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ long $currentSpan;
    final /* synthetic */ Density $density;
    final /* synthetic */ PaddingValues $gridContentPadding;
    final /* synthetic */ LazyGridState $gridState;
    final /* synthetic */ String $key;
    final /* synthetic */ int $maxHeightPx;
    final /* synthetic */ int $minHeightPx;
    final /* synthetic */ int $resizeMultiple;
    final /* synthetic */ Arrangement.Vertical $verticalArrangement;
    final /* synthetic */ ResizeableItemFrameViewModel $viewModel;
    int label;

    /* renamed from: com.android.systemui.communal.ui.compose.ResizeableItemFrameKt$UpdateGridLayoutInfo$1$1$6, reason: invalid class name */
    final /* synthetic */ class AnonymousClass6 extends AdaptedFunctionReference implements Function4 {
        public static final AnonymousClass6 INSTANCE = new AnonymousClass6();

        public AnonymousClass6() {
            super(4, Triple.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V", 4);
        }

        @Override // kotlin.jvm.functions.Function4
        public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
            int iIntValue = ((Number) obj).intValue();
            int iIntValue2 = ((Number) obj2).intValue();
            return new Triple(new Integer(iIntValue), new Integer(iIntValue2), (LazyGridItemInfo) obj3);
        }
    }

    /* renamed from: com.android.systemui.communal.ui.compose.ResizeableItemFrameKt$UpdateGridLayoutInfo$1$1$7, reason: invalid class name */
    final class AnonymousClass7 extends SuspendLambda implements Function2 {
        final /* synthetic */ long $currentSpan;
        final /* synthetic */ int $maxHeightPx;
        final /* synthetic */ int $minHeightPx;
        final /* synthetic */ int $resizeMultiple;
        final /* synthetic */ float $verticalContentPaddingPx;
        final /* synthetic */ float $verticalItemSpacingPx;
        final /* synthetic */ ResizeableItemFrameViewModel $viewModel;
        /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass7(ResizeableItemFrameViewModel resizeableItemFrameViewModel, float f, int i, int i2, long j, int i3, float f2, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = resizeableItemFrameViewModel;
            this.$verticalItemSpacingPx = f;
            this.$maxHeightPx = i;
            this.$minHeightPx = i2;
            this.$currentSpan = j;
            this.$resizeMultiple = i3;
            this.$verticalContentPaddingPx = f2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass7 anonymousClass7 = new AnonymousClass7(this.$viewModel, this.$verticalItemSpacingPx, this.$maxHeightPx, this.$minHeightPx, this.$currentSpan, this.$resizeMultiple, this.$verticalContentPaddingPx, continuation);
            anonymousClass7.L$0 = obj;
            return anonymousClass7;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass7) create((Triple) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Triple triple = (Triple) this.L$0;
            int iIntValue = ((Number) triple.component1()).intValue();
            int iIntValue2 = ((Number) triple.component2()).intValue();
            LazyGridItemInfo lazyGridItemInfo = (LazyGridItemInfo) triple.component3();
            ResizeableItemFrameViewModel resizeableItemFrameViewModel = this.$viewModel;
            float f = this.$verticalItemSpacingPx;
            Integer num = lazyGridItemInfo != null ? new Integer(((LazyGridMeasuredItem) lazyGridItemInfo).row) : null;
            int i = this.$maxHeightPx;
            int i2 = this.$minHeightPx;
            int i3 = (int) this.$currentSpan;
            int i4 = this.$resizeMultiple;
            float f2 = this.$verticalContentPaddingPx;
            StateFlowImpl stateFlowImpl = resizeableItemFrameViewModel.gridLayoutInfo;
            if (num == null) {
                stateFlowImpl.setValue(null);
            } else {
                if (i < i2) {
                    throw new IllegalArgumentException(ListImplementation$$ExternalSyntheticOutline0.m(i, i2, "Maximum item span of ", " cannot be less than the minimum span of ").toString());
                }
                if (i3 > iIntValue) {
                    throw new IllegalArgumentException(MutableVectorKt$$ExternalSyntheticOutline0.m(i3, iIntValue, "Current span (", ") cannot exceed the total number of spans (", ")").toString());
                }
                if (i4 <= 0) {
                    throw new IllegalArgumentException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i4, "Resize multiple (", ") must be a positive integer").toString());
                }
                float f3 = iIntValue2 - f2;
                int i5 = (int) f3;
                stateFlowImpl.updateState(null, new ResizeableItemFrameViewModel.GridLayoutInfo(num.intValue(), i3, i > i5 ? i5 : i, i2, i4, iIntValue, (f3 - ((iIntValue - 1) * f)) / iIntValue, f));
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ResizeableItemFrameKt$UpdateGridLayoutInfo$1$1(Density density, Arrangement.Vertical vertical, PaddingValues paddingValues, LazyGridState lazyGridState, String str, ResizeableItemFrameViewModel resizeableItemFrameViewModel, int i, int i2, long j, int i3, Continuation continuation) {
        super(2, continuation);
        this.$density = density;
        this.$verticalArrangement = vertical;
        this.$gridContentPadding = paddingValues;
        this.$gridState = lazyGridState;
        this.$key = str;
        this.$viewModel = resizeableItemFrameViewModel;
        this.$maxHeightPx = i;
        this.$minHeightPx = i2;
        this.$currentSpan = j;
        this.$resizeMultiple = i3;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ResizeableItemFrameKt$UpdateGridLayoutInfo$1$1(this.$density, this.$verticalArrangement, this.$gridContentPadding, this.$gridState, this.$key, this.$viewModel, this.$maxHeightPx, this.$minHeightPx, this.$currentSpan, this.$resizeMultiple, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ResizeableItemFrameKt$UpdateGridLayoutInfo$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            float fMo58toPx0680j_4 = this.$density.mo58toPx0680j_4(this.$verticalArrangement.mo95getSpacingD9Ej5fM());
            Density density = this.$density;
            PaddingValues paddingValues = this.$gridContentPadding;
            float fMo110calculateBottomPaddingD9Ej5fM = paddingValues.mo110calculateBottomPaddingD9Ej5fM() + paddingValues.mo113calculateTopPaddingD9Ej5fM();
            Dp.Companion companion = Dp.Companion;
            float fMo58toPx0680j_42 = density.mo58toPx0680j_4(fMo110calculateBottomPaddingD9Ej5fM);
            SafeFlow safeFlowSnapshotFlow = SnapshotStateKt.snapshotFlow(new ResizeableItemFrameKt$$ExternalSyntheticLambda0(this.$gridState, 1));
            SafeFlow safeFlowSnapshotFlow2 = SnapshotStateKt.snapshotFlow(new ResizeableItemFrameKt$$ExternalSyntheticLambda0(this.$gridState, 2));
            final LazyGridState lazyGridState = this.$gridState;
            final String str = this.$key;
            FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1Combine = FlowKt.combine(safeFlowSnapshotFlow, safeFlowSnapshotFlow2, SnapshotStateKt.snapshotFlow(new Function0() { // from class: com.android.systemui.communal.ui.compose.ResizeableItemFrameKt$UpdateGridLayoutInfo$1$1$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Object next;
                    Iterator it = ((LazyGridMeasureResult) lazyGridState.getLayoutInfo()).visibleItemsInfo.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            next = null;
                            break;
                        }
                        next = it.next();
                        if (Intrinsics.areEqual(((LazyGridMeasuredItem) ((LazyGridItemInfo) next)).key, str)) {
                            break;
                        }
                    }
                    return (LazyGridItemInfo) next;
                }
            }), AnonymousClass6.INSTANCE);
            AnonymousClass7 anonymousClass7 = new AnonymousClass7(this.$viewModel, fMo58toPx0680j_4, this.$maxHeightPx, this.$minHeightPx, this.$currentSpan, this.$resizeMultiple, fMo58toPx0680j_42, null);
            this.label = 1;
            if (FlowKt.collectLatest(flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1Combine, anonymousClass7, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
