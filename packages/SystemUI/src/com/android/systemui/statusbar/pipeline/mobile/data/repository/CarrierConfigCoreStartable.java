package com.android.systemui.statusbar.pipeline.mobile.data.repository;

import android.os.PersistableBundle;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.CoreStartable;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes3.dex */
public final class CarrierConfigCoreStartable implements CoreStartable {
    public final CarrierConfigRepository carrierConfigRepository;
    public final CoroutineScope scope;

    /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigCoreStartable$start$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CarrierConfigCoreStartable.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CarrierConfigRepository carrierConfigRepository = CarrierConfigCoreStartable.this.carrierConfigRepository;
                this.label = 1;
                final CarrierConfigRepositoryImpl carrierConfigRepositoryImpl = (CarrierConfigRepositoryImpl) carrierConfigRepository;
                carrierConfigRepositoryImpl.isListening = true;
                Object objCollect = carrierConfigRepositoryImpl.carrierConfigStream.collect(new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepositoryImpl$startObservingCarrierConfigUpdates$2
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Pair pair = (Pair) obj2;
                        int iIntValue = ((Number) pair.getFirst()).intValue();
                        carrierConfigRepositoryImpl.getOrCreateConfigForSubId(Integer.valueOf(iIntValue)).processNewCarrierConfig((PersistableBundle) pair.getSecond());
                        return Unit.INSTANCE;
                    }
                }, this);
                if (objCollect != coroutineSingletons) {
                    objCollect = Unit.INSTANCE;
                }
                if (objCollect == coroutineSingletons) {
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

    public CarrierConfigCoreStartable(CarrierConfigRepository carrierConfigRepository, CoroutineScope coroutineScope) {
        this.carrierConfigRepository = carrierConfigRepository;
        this.scope = coroutineScope;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        CoroutineTracingKt.launchTraced$default(this.scope, null, null, new AnonymousClass1(null), 7);
    }
}
