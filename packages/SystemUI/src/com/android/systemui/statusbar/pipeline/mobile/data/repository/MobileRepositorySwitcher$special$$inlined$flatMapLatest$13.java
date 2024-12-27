package com.android.systemui.statusbar.pipeline.mobile.data.repository;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MobileRepositorySwitcher$special$$inlined$flatMapLatest$13 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public MobileRepositorySwitcher$special$$inlined$flatMapLatest$13(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        MobileRepositorySwitcher$special$$inlined$flatMapLatest$13 mobileRepositorySwitcher$special$$inlined$flatMapLatest$13 = new MobileRepositorySwitcher$special$$inlined$flatMapLatest$13((Continuation) obj3);
        mobileRepositorySwitcher$special$$inlined$flatMapLatest$13.L$0 = (FlowCollector) obj;
        mobileRepositorySwitcher$special$$inlined$flatMapLatest$13.L$1 = obj2;
        return mobileRepositorySwitcher$special$$inlined$flatMapLatest$13.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Flow hasCarrierMergedConnection = ((MobileConnectionsRepository) this.L$1).getHasCarrierMergedConnection();
            this.label = 1;
            if (FlowKt.emitAll(this, hasCarrierMergedConnection, flowCollector) == coroutineSingletons) {
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
