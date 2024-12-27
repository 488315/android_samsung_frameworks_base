package com.android.systemui.keyguard.data.repository;

import com.android.systemui.biometrics.shared.model.SensorStrength;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

public final class BiometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$4 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ BiometricSettingsRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BiometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$4(Continuation continuation, BiometricSettingsRepositoryImpl biometricSettingsRepositoryImpl) {
        super(3, continuation);
        this.this$0 = biometricSettingsRepositoryImpl;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        BiometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$4 biometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$4 = new BiometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$4((Continuation) obj3, this.this$0);
        biometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$4.L$0 = (FlowCollector) obj;
        biometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$4.L$1 = obj2;
        return biometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$4.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            ReadonlyStateFlow readonlyStateFlow = ((SensorStrength) this.L$1) == SensorStrength.STRONG ? this.this$0.isStrongBiometricAllowed : this.this$0.isNonStrongBiometricAllowed;
            this.label = 1;
            if (FlowKt.emitAll(this, readonlyStateFlow, flowCollector) == coroutineSingletons) {
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
