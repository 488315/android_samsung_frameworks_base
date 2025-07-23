package com.android.systemui.qs.panels.ui.compose;

import androidx.compose.foundation.pager.PagerState;
import androidx.compose.runtime.SnapshotStateKt;
import com.android.systemui.qs.panels.ui.viewmodel.PaginatedGridViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.SafeFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class PaginatedGridLayout$TileGrid$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ PagerState $pagerState;
    final /* synthetic */ PaginatedGridViewModel $viewModel;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PaginatedGridLayout$TileGrid$2$1(PagerState pagerState, PaginatedGridViewModel paginatedGridViewModel, Continuation continuation) {
        super(2, continuation);
        this.$pagerState = pagerState;
        this.$viewModel = paginatedGridViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new PaginatedGridLayout$TileGrid$2$1(this.$pagerState, this.$viewModel, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((PaginatedGridLayout$TileGrid$2$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            SafeFlow snapshotFlow = SnapshotStateKt.snapshotFlow(new PaginatedGridLayout$$ExternalSyntheticLambda0(this.$pagerState, 3));
            final PaginatedGridViewModel paginatedGridViewModel = this.$viewModel;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.qs.panels.ui.compose.PaginatedGridLayout$TileGrid$2$1.2
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    PaginatedGridViewModel.this.inFirstPage$receiver.inFirstPage = ((Boolean) obj2).booleanValue();
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (snapshotFlow.collect(flowCollector, this) == coroutineSingletons) {
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
