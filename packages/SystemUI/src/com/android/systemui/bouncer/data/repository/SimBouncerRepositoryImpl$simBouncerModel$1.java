package com.android.systemui.bouncer.data.repository;

import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

final class SimBouncerRepositoryImpl$simBouncerModel$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ KeyguardUpdateMonitor $keyguardUpdateMonitor;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SimBouncerRepositoryImpl$simBouncerModel$1(KeyguardUpdateMonitor keyguardUpdateMonitor, Continuation continuation) {
        super(2, continuation);
        this.$keyguardUpdateMonitor = keyguardUpdateMonitor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SimBouncerRepositoryImpl$simBouncerModel$1 simBouncerRepositoryImpl$simBouncerModel$1 = new SimBouncerRepositoryImpl$simBouncerModel$1(this.$keyguardUpdateMonitor, continuation);
        simBouncerRepositoryImpl$simBouncerModel$1.L$0 = obj;
        return simBouncerRepositoryImpl$simBouncerModel$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SimBouncerRepositoryImpl$simBouncerModel$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.keyguard.KeyguardUpdateMonitorCallback, com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$simBouncerModel$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$simBouncerModel$1$callback$1
                @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
                public final void onSimStateChanged(int i2, int i3, int i4) {
                    ((ChannelCoroutine) ProducerScope.this).mo2552trySendJP2dKIU(Unit.INSTANCE);
                }
            };
            this.$keyguardUpdateMonitor.registerCallback(r1);
            final KeyguardUpdateMonitor keyguardUpdateMonitor = this.$keyguardUpdateMonitor;
            Function0 function0 = new Function0() { // from class: com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$simBouncerModel$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    KeyguardUpdateMonitor.this.removeCallback(r1);
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
