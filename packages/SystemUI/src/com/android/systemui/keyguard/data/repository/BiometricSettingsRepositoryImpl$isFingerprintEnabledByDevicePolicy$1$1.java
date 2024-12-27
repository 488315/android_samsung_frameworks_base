package com.android.systemui.keyguard.data.repository;

import android.app.admin.DevicePolicyManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class BiometricSettingsRepositoryImpl$isFingerprintEnabledByDevicePolicy$1$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ DevicePolicyManager $devicePolicyManager;
    final /* synthetic */ int $userId;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BiometricSettingsRepositoryImpl$isFingerprintEnabledByDevicePolicy$1$1(DevicePolicyManager devicePolicyManager, int i, Continuation continuation) {
        super(3, continuation);
        this.$devicePolicyManager = devicePolicyManager;
        this.$userId = i;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        BiometricSettingsRepositoryImpl$isFingerprintEnabledByDevicePolicy$1$1 biometricSettingsRepositoryImpl$isFingerprintEnabledByDevicePolicy$1$1 = new BiometricSettingsRepositoryImpl$isFingerprintEnabledByDevicePolicy$1$1(this.$devicePolicyManager, this.$userId, (Continuation) obj3);
        biometricSettingsRepositoryImpl$isFingerprintEnabledByDevicePolicy$1$1.L$0 = (FlowCollector) obj;
        return biometricSettingsRepositoryImpl$isFingerprintEnabledByDevicePolicy$1$1.invokeSuspend(Unit.INSTANCE);
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
