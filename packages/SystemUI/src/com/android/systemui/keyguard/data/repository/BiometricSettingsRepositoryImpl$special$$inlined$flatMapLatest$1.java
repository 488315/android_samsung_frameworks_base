package com.android.systemui.keyguard.data.repository;

import com.android.systemui.biometrics.AuthController;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

public final class BiometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ AuthController $authController$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BiometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$1(Continuation continuation, AuthController authController) {
        super(3, continuation);
        this.$authController$inlined = authController;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        BiometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$1 biometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$1 = new BiometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$1((Continuation) obj3, this.$authController$inlined);
        biometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        biometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return biometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            int intValue = ((Number) this.L$1).intValue();
            ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
            BiometricSettingsRepositoryImpl$isFingerprintEnrolled$1$1 biometricSettingsRepositoryImpl$isFingerprintEnrolled$1$1 = new BiometricSettingsRepositoryImpl$isFingerprintEnrolled$1$1(this.$authController$inlined, intValue, null);
            conflatedCallbackFlow.getClass();
            Flow conflatedCallbackFlow2 = FlowConflatedKt.conflatedCallbackFlow(biometricSettingsRepositoryImpl$isFingerprintEnrolled$1$1);
            this.label = 1;
            if (FlowKt.emitAll(this, conflatedCallbackFlow2, flowCollector) == coroutineSingletons) {
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
