package com.android.systemui.statusbar.pipeline.mobile.data.repository.demo;

import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

final class DemoMobileConnectionsRepository$mobileMappingsReverseLookup$1 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DemoMobileConnectionsRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DemoMobileConnectionsRepository$mobileMappingsReverseLookup$1(DemoMobileConnectionsRepository demoMobileConnectionsRepository, Continuation continuation) {
        super(2, continuation);
        this.this$0 = demoMobileConnectionsRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DemoMobileConnectionsRepository$mobileMappingsReverseLookup$1 demoMobileConnectionsRepository$mobileMappingsReverseLookup$1 = new DemoMobileConnectionsRepository$mobileMappingsReverseLookup$1(this.this$0, continuation);
        demoMobileConnectionsRepository$mobileMappingsReverseLookup$1.L$0 = obj;
        return demoMobileConnectionsRepository$mobileMappingsReverseLookup$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DemoMobileConnectionsRepository$mobileMappingsReverseLookup$1) create((Map) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Map map = (Map) this.L$0;
        DemoMobileConnectionsRepository demoMobileConnectionsRepository = this.this$0;
        Intrinsics.checkNotNull(map);
        int i = DemoMobileConnectionsRepository.$r8$clinit;
        demoMobileConnectionsRepository.getClass();
        return DemoMobileConnectionsRepository.reverse(map);
    }
}
