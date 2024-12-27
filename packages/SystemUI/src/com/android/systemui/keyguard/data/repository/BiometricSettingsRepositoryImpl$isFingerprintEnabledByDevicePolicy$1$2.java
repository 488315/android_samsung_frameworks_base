package com.android.systemui.keyguard.data.repository;

import android.app.admin.DevicePolicyManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

final class BiometricSettingsRepositoryImpl$isFingerprintEnabledByDevicePolicy$1$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ DevicePolicyManager $devicePolicyManager;
    final /* synthetic */ int $userId;
    private /* synthetic */ Object L$0;
    int label;

    public BiometricSettingsRepositoryImpl$isFingerprintEnabledByDevicePolicy$1$2(DevicePolicyManager devicePolicyManager, int i, Continuation continuation) {
        super(2, continuation);
        this.$devicePolicyManager = devicePolicyManager;
        this.$userId = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        BiometricSettingsRepositoryImpl$isFingerprintEnabledByDevicePolicy$1$2 biometricSettingsRepositoryImpl$isFingerprintEnabledByDevicePolicy$1$2 = new BiometricSettingsRepositoryImpl$isFingerprintEnabledByDevicePolicy$1$2(this.$devicePolicyManager, this.$userId, continuation);
        biometricSettingsRepositoryImpl$isFingerprintEnabledByDevicePolicy$1$2.L$0 = obj;
        return biometricSettingsRepositoryImpl$isFingerprintEnabledByDevicePolicy$1$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BiometricSettingsRepositoryImpl$isFingerprintEnabledByDevicePolicy$1$2) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Boolean valueOf = Boolean.valueOf(BiometricSettingsRepositoryKt.isNotActive(this.$devicePolicyManager, this.$userId, 32));
            this.label = 1;
            if (flowCollector.emit(valueOf, this) == coroutineSingletons) {
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
