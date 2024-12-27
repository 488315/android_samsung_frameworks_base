package com.android.systemui.statusbar.pipeline.mobile.data.repository;

import android.telephony.ServiceState;
import android.util.Log;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

final class DummyMobileConnectionRepository$serviceStateBroadcast$3 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DummyMobileConnectionRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DummyMobileConnectionRepository$serviceStateBroadcast$3(DummyMobileConnectionRepository dummyMobileConnectionRepository, Continuation continuation) {
        super(2, continuation);
        this.this$0 = dummyMobileConnectionRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DummyMobileConnectionRepository$serviceStateBroadcast$3 dummyMobileConnectionRepository$serviceStateBroadcast$3 = new DummyMobileConnectionRepository$serviceStateBroadcast$3(this.this$0, continuation);
        dummyMobileConnectionRepository$serviceStateBroadcast$3.L$0 = obj;
        return dummyMobileConnectionRepository$serviceStateBroadcast$3;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DummyMobileConnectionRepository$serviceStateBroadcast$3) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            ServiceState semGetServiceState = this.this$0.phone.semGetServiceState(0);
            Log.d("DummyMobileConnectionRepository", "serviceStateBroadcast flow is started (" + semGetServiceState + ")");
            this.label = 1;
            if (flowCollector.emit(semGetServiceState, this) == coroutineSingletons) {
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
