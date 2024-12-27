package com.android.systemui.communal.data.repository;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

public final class CommunalSceneRepositoryImpl$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ CommunalSceneRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalSceneRepositoryImpl$special$$inlined$flatMapLatest$1(Continuation continuation, CommunalSceneRepositoryImpl communalSceneRepositoryImpl) {
        super(3, continuation);
        this.this$0 = communalSceneRepositoryImpl;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        CommunalSceneRepositoryImpl$special$$inlined$flatMapLatest$1 communalSceneRepositoryImpl$special$$inlined$flatMapLatest$1 = new CommunalSceneRepositoryImpl$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        communalSceneRepositoryImpl$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        communalSceneRepositoryImpl$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return communalSceneRepositoryImpl$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Flow flow = (Flow) this.L$1;
            if (flow == null) {
                flow = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(this.this$0.defaultTransitionState);
            }
            this.label = 1;
            if (FlowKt.emitAll(this, flow, flowCollector) == coroutineSingletons) {
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
