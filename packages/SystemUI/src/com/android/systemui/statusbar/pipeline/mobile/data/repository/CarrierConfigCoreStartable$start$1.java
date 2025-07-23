package com.android.systemui.statusbar.pipeline.mobile.data.repository;

import android.os.PersistableBundle;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class CarrierConfigCoreStartable$start$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ CarrierConfigCoreStartable this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CarrierConfigCoreStartable$start$1(CarrierConfigCoreStartable carrierConfigCoreStartable, Continuation continuation) {
        super(2, continuation);
        this.this$0 = carrierConfigCoreStartable;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CarrierConfigCoreStartable$start$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CarrierConfigCoreStartable$start$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CarrierConfigRepository carrierConfigRepository = this.this$0.carrierConfigRepository;
            this.label = 1;
            final CarrierConfigRepositoryImpl carrierConfigRepositoryImpl = (CarrierConfigRepositoryImpl) carrierConfigRepository;
            carrierConfigRepositoryImpl.isListening = true;
            Object collect = carrierConfigRepositoryImpl.carrierConfigStream.collect(new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepositoryImpl$startObservingCarrierConfigUpdates$2
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    Pair pair = (Pair) obj2;
                    int intValue = ((Number) pair.getFirst()).intValue();
                    CarrierConfigRepositoryImpl.this.getOrCreateConfigForSubId(Integer.valueOf(intValue)).processNewCarrierConfig((PersistableBundle) pair.getSecond());
                    return Unit.INSTANCE;
                }
            }, this);
            if (collect != coroutineSingletons) {
                collect = Unit.INSTANCE;
            }
            if (collect == coroutineSingletons) {
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
