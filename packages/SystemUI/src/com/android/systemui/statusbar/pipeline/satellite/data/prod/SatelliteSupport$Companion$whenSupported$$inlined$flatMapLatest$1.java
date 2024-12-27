package com.android.systemui.statusbar.pipeline.satellite.data.prod;

import com.android.systemui.statusbar.pipeline.satellite.data.prod.SatelliteSupport;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

public final class SatelliteSupport$Companion$whenSupported$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ Flow $orElse$inlined;
    final /* synthetic */ Flow $retrySignal$inlined;
    final /* synthetic */ Function1 $supported$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SatelliteSupport$Companion$whenSupported$$inlined$flatMapLatest$1(Continuation continuation, Flow flow, Flow flow2, Function1 function1) {
        super(3, continuation);
        this.$retrySignal$inlined = flow;
        this.$orElse$inlined = flow2;
        this.$supported$inlined = function1;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        SatelliteSupport$Companion$whenSupported$$inlined$flatMapLatest$1 satelliteSupport$Companion$whenSupported$$inlined$flatMapLatest$1 = new SatelliteSupport$Companion$whenSupported$$inlined$flatMapLatest$1((Continuation) obj3, this.$retrySignal$inlined, this.$orElse$inlined, this.$supported$inlined);
        satelliteSupport$Companion$whenSupported$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        satelliteSupport$Companion$whenSupported$$inlined$flatMapLatest$1.L$1 = obj2;
        return satelliteSupport$Companion$whenSupported$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            SatelliteSupport satelliteSupport = (SatelliteSupport) this.L$1;
            Flow transformLatest = satelliteSupport instanceof SatelliteSupport.Supported ? FlowKt.transformLatest(this.$retrySignal$inlined, new SatelliteSupport$Companion$whenSupported$lambda$1$$inlined$flatMapLatest$1(null, this.$supported$inlined, satelliteSupport)) : this.$orElse$inlined;
            this.label = 1;
            if (FlowKt.emitAll(this, transformLatest, flowCollector) == coroutineSingletons) {
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
