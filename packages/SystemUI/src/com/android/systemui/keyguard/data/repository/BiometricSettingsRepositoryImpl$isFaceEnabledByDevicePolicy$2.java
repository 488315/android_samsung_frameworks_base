package com.android.systemui.keyguard.data.repository;

import android.app.admin.DevicePolicyManager;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

final class BiometricSettingsRepositoryImpl$isFaceEnabledByDevicePolicy$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ DevicePolicyManager $devicePolicyManager;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ BiometricSettingsRepositoryImpl this$0;

    public BiometricSettingsRepositoryImpl$isFaceEnabledByDevicePolicy$2(DevicePolicyManager devicePolicyManager, BiometricSettingsRepositoryImpl biometricSettingsRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.$devicePolicyManager = devicePolicyManager;
        this.this$0 = biometricSettingsRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        BiometricSettingsRepositoryImpl$isFaceEnabledByDevicePolicy$2 biometricSettingsRepositoryImpl$isFaceEnabledByDevicePolicy$2 = new BiometricSettingsRepositoryImpl$isFaceEnabledByDevicePolicy$2(this.$devicePolicyManager, this.this$0, continuation);
        biometricSettingsRepositoryImpl$isFaceEnabledByDevicePolicy$2.L$0 = obj;
        return biometricSettingsRepositoryImpl$isFaceEnabledByDevicePolicy$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BiometricSettingsRepositoryImpl$isFaceEnabledByDevicePolicy$2) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Boolean valueOf = Boolean.valueOf(BiometricSettingsRepositoryKt.isNotActive(this.$devicePolicyManager, ((UserRepositoryImpl) this.this$0.userRepository).getSelectedUserInfo().id, 128));
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
