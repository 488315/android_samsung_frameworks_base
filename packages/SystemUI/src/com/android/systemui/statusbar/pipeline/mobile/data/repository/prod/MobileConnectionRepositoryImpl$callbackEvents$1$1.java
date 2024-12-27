package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger;
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

final class MobileConnectionRepositoryImpl$callbackEvents$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ MobileInputLogger $logger;
    final /* synthetic */ MobileConnectionRepositoryImpl $this_run;
    private /* synthetic */ Object L$0;
    int label;

    public MobileConnectionRepositoryImpl$callbackEvents$1$1(MobileConnectionRepositoryImpl mobileConnectionRepositoryImpl, MobileInputLogger mobileInputLogger, Continuation continuation) {
        super(2, continuation);
        this.$this_run = mobileConnectionRepositoryImpl;
        this.$logger = mobileInputLogger;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MobileConnectionRepositoryImpl$callbackEvents$1$1 mobileConnectionRepositoryImpl$callbackEvents$1$1 = new MobileConnectionRepositoryImpl$callbackEvents$1$1(this.$this_run, this.$logger, continuation);
        mobileConnectionRepositoryImpl$callbackEvents$1$1.L$0 = obj;
        return mobileConnectionRepositoryImpl$callbackEvents$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MobileConnectionRepositoryImpl$callbackEvents$1$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope = (ProducerScope) this.L$0;
            final MobileConnectionRepositoryImpl$callbackEvents$1$1$callback$1 mobileConnectionRepositoryImpl$callbackEvents$1$1$callback$1 = new MobileConnectionRepositoryImpl$callbackEvents$1$1$callback$1(this.$logger, this.$this_run, producerScope);
            MobileConnectionRepositoryImpl mobileConnectionRepositoryImpl = this.$this_run;
            mobileConnectionRepositoryImpl.telephonyManager.registerTelephonyCallback(ExecutorsKt.asExecutor(mobileConnectionRepositoryImpl.bgDispatcher), mobileConnectionRepositoryImpl$callbackEvents$1$1$callback$1);
            final MobileConnectionRepositoryImpl mobileConnectionRepositoryImpl2 = this.$this_run;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryImpl$callbackEvents$1$1.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    MobileConnectionRepositoryImpl.this.telephonyManager.unregisterTelephonyCallback(mobileConnectionRepositoryImpl$callbackEvents$1$1$callback$1);
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
