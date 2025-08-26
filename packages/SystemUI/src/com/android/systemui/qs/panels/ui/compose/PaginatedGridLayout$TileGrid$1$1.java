package com.android.systemui.qs.panels.ui.compose;

import androidx.compose.foundation.pager.PagerState;
import androidx.compose.runtime.SnapshotStateKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.SafeFlow;

/* loaded from: classes2.dex */
final class PaginatedGridLayout$TileGrid$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function0 $listening;
    final /* synthetic */ PagerState $pagerState;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PaginatedGridLayout$TileGrid$1$1(Function0 function0, PagerState pagerState, Continuation continuation) {
        super(2, continuation);
        this.$listening = function0;
        this.$pagerState = pagerState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new PaginatedGridLayout$TileGrid$1$1(this.$listening, this.$pagerState, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((PaginatedGridLayout$TileGrid$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            SafeFlow safeFlowSnapshotFlow = SnapshotStateKt.snapshotFlow(new PaginatedGridLayout$$ExternalSyntheticLambda0(this.$listening, 2));
            final Function0 function0 = this.$listening;
            final PagerState pagerState = this.$pagerState;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.qs.panels.ui.compose.PaginatedGridLayout$TileGrid$1$1.2
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    ((Boolean) obj2).getClass();
                    if (!((Boolean) function0.invoke()).booleanValue()) {
                        return Unit.INSTANCE;
                    }
                    Object objScrollToPage$default = PagerState.scrollToPage$default(pagerState, 0, continuation);
                    return objScrollToPage$default == CoroutineSingletons.COROUTINE_SUSPENDED ? objScrollToPage$default : Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (safeFlowSnapshotFlow.collect(flowCollector, this) == coroutineSingletons) {
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
