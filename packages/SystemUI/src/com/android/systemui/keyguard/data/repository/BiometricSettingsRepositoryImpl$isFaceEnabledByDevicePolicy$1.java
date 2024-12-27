package com.android.systemui.keyguard.data.repository;

import android.app.admin.DevicePolicyManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

final class BiometricSettingsRepositoryImpl$isFaceEnabledByDevicePolicy$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ DevicePolicyManager $devicePolicyManager;
    /* synthetic */ int I$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BiometricSettingsRepositoryImpl$isFaceEnabledByDevicePolicy$1(DevicePolicyManager devicePolicyManager, Continuation continuation) {
        super(3, continuation);
        this.$devicePolicyManager = devicePolicyManager;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        int intValue = ((Number) obj).intValue();
        BiometricSettingsRepositoryImpl$isFaceEnabledByDevicePolicy$1 biometricSettingsRepositoryImpl$isFaceEnabledByDevicePolicy$1 = new BiometricSettingsRepositoryImpl$isFaceEnabledByDevicePolicy$1(this.$devicePolicyManager, (Continuation) obj3);
        biometricSettingsRepositoryImpl$isFaceEnabledByDevicePolicy$1.I$0 = intValue;
        return biometricSettingsRepositoryImpl$isFaceEnabledByDevicePolicy$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf(BiometricSettingsRepositoryKt.isNotActive(this.$devicePolicyManager, this.I$0, 128));
    }
}
