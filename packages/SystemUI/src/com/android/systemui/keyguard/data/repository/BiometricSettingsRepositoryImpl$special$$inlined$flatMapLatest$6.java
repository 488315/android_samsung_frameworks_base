package com.android.systemui.keyguard.data.repository;

import android.app.admin.DevicePolicyManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

public final class BiometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$6 extends SuspendLambda implements Function3 {
    final /* synthetic */ CoroutineDispatcher $backgroundDispatcher$inlined;
    final /* synthetic */ DevicePolicyManager $devicePolicyManager$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ BiometricSettingsRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BiometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$6(Continuation continuation, BiometricSettingsRepositoryImpl biometricSettingsRepositoryImpl, CoroutineDispatcher coroutineDispatcher, DevicePolicyManager devicePolicyManager) {
        super(3, continuation);
        this.this$0 = biometricSettingsRepositoryImpl;
        this.$backgroundDispatcher$inlined = coroutineDispatcher;
        this.$devicePolicyManager$inlined = devicePolicyManager;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        BiometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$6 biometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$6 = new BiometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$6((Continuation) obj3, this.this$0, this.$backgroundDispatcher$inlined, this.$devicePolicyManager$inlined);
        biometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$6.L$0 = (FlowCollector) obj;
        biometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$6.L$1 = obj2;
        return biometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$6.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            int intValue = ((Number) this.L$1).intValue();
            Flow distinctUntilChanged = FlowKt.distinctUntilChanged(FlowKt.flowOn(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new BiometricSettingsRepositoryImpl$isFingerprintEnabledByDevicePolicy$1$2(this.$devicePolicyManager$inlined, intValue, null), FlowKt.transformLatest(this.this$0.devicePolicyChangedForAllUsers, new BiometricSettingsRepositoryImpl$isFingerprintEnabledByDevicePolicy$1$1(this.$devicePolicyManager$inlined, intValue, null))), this.$backgroundDispatcher$inlined));
            this.label = 1;
            if (FlowKt.emitAll(this, distinctUntilChanged, flowCollector) == coroutineSingletons) {
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
