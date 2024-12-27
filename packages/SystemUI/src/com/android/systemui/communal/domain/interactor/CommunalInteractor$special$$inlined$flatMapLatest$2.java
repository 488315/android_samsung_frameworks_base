package com.android.systemui.communal.domain.interactor;

import com.android.systemui.communal.data.repository.CommunalWidgetRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.EmptyFlow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

public final class CommunalInteractor$special$$inlined$flatMapLatest$2 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ CommunalInteractor this$0;

    public CommunalInteractor$special$$inlined$flatMapLatest$2(Continuation continuation, CommunalInteractor communalInteractor) {
        super(3, continuation);
        this.this$0 = communalInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        CommunalInteractor$special$$inlined$flatMapLatest$2 communalInteractor$special$$inlined$flatMapLatest$2 = new CommunalInteractor$special$$inlined$flatMapLatest$2((Continuation) obj3, this.this$0);
        communalInteractor$special$$inlined$flatMapLatest$2.L$0 = (FlowCollector) obj;
        communalInteractor$special$$inlined$flatMapLatest$2.L$1 = obj2;
        return communalInteractor$special$$inlined$flatMapLatest$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Flow flow = !((Boolean) this.L$1).booleanValue() ? EmptyFlow.INSTANCE : ((CommunalWidgetRepositoryImpl) this.this$0.widgetRepository).communalWidgets;
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
