package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.StateFlow;

public final class MobileIconViewModel$special$$inlined$flatMapLatest$6 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public MobileIconViewModel$special$$inlined$flatMapLatest$6(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        MobileIconViewModel$special$$inlined$flatMapLatest$6 mobileIconViewModel$special$$inlined$flatMapLatest$6 = new MobileIconViewModel$special$$inlined$flatMapLatest$6((Continuation) obj3);
        mobileIconViewModel$special$$inlined$flatMapLatest$6.L$0 = (FlowCollector) obj;
        mobileIconViewModel$special$$inlined$flatMapLatest$6.L$1 = obj2;
        return mobileIconViewModel$special$$inlined$flatMapLatest$6.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            StateFlow networkTypeBackground = ((MobileIconViewModelCommon) this.L$1).getNetworkTypeBackground();
            this.label = 1;
            if (FlowKt.emitAll(this, networkTypeBackground, flowCollector) == coroutineSingletons) {
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
