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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SatelliteSupport$Companion$whenSupported$lambda$1$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ SatelliteSupport $satelliteSupport$inlined;
    final /* synthetic */ Function1 $supported$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SatelliteSupport$Companion$whenSupported$lambda$1$$inlined$flatMapLatest$1(Continuation continuation, Function1 function1, SatelliteSupport satelliteSupport) {
        super(3, continuation);
        this.$supported$inlined = function1;
        this.$satelliteSupport$inlined = satelliteSupport;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        SatelliteSupport$Companion$whenSupported$lambda$1$$inlined$flatMapLatest$1 satelliteSupport$Companion$whenSupported$lambda$1$$inlined$flatMapLatest$1 = new SatelliteSupport$Companion$whenSupported$lambda$1$$inlined$flatMapLatest$1((Continuation) obj3, this.$supported$inlined, this.$satelliteSupport$inlined);
        satelliteSupport$Companion$whenSupported$lambda$1$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        satelliteSupport$Companion$whenSupported$lambda$1$$inlined$flatMapLatest$1.L$1 = obj2;
        return satelliteSupport$Companion$whenSupported$lambda$1$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Flow flow = (Flow) this.$supported$inlined.invoke(((SatelliteSupport.Supported) this.$satelliteSupport$inlined).satelliteManager);
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
