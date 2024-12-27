package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.ExecutorsKt;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

final class MobileConnectionsRepositoryImpl$activeMobileDataSubscriptionId$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MobileConnectionsRepositoryImpl this$0;

    public MobileConnectionsRepositoryImpl$activeMobileDataSubscriptionId$1(MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mobileConnectionsRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MobileConnectionsRepositoryImpl$activeMobileDataSubscriptionId$1 mobileConnectionsRepositoryImpl$activeMobileDataSubscriptionId$1 = new MobileConnectionsRepositoryImpl$activeMobileDataSubscriptionId$1(this.this$0, continuation);
        mobileConnectionsRepositoryImpl$activeMobileDataSubscriptionId$1.L$0 = obj;
        return mobileConnectionsRepositoryImpl$activeMobileDataSubscriptionId$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MobileConnectionsRepositoryImpl$activeMobileDataSubscriptionId$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope = (ProducerScope) this.L$0;
            final MobileConnectionsRepositoryImpl$activeMobileDataSubscriptionId$1$callback$1 mobileConnectionsRepositoryImpl$activeMobileDataSubscriptionId$1$callback$1 = new MobileConnectionsRepositoryImpl$activeMobileDataSubscriptionId$1$callback$1(producerScope);
            MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl = this.this$0;
            mobileConnectionsRepositoryImpl.telephonyManager.registerTelephonyCallback(ExecutorsKt.asExecutor(mobileConnectionsRepositoryImpl.bgDispatcher), mobileConnectionsRepositoryImpl$activeMobileDataSubscriptionId$1$callback$1);
            final MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$activeMobileDataSubscriptionId$1.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    MobileConnectionsRepositoryImpl.this.telephonyManager.unregisterTelephonyCallback(mobileConnectionsRepositoryImpl$activeMobileDataSubscriptionId$1$callback$1);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
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
