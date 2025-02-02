package com.android.systemui.keyguard.data.repository;

import android.app.admin.DevicePolicyManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$4", m277f = "BiometricSettingsRepository.kt", m278l = {190}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
public final class BiometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$4 extends SuspendLambda implements Function3 {
    final /* synthetic */ CoroutineDispatcher $backgroundDispatcher$inlined;
    final /* synthetic */ DevicePolicyManager $devicePolicyManager$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ BiometricSettingsRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BiometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$4(Continuation continuation, BiometricSettingsRepositoryImpl biometricSettingsRepositoryImpl, CoroutineDispatcher coroutineDispatcher, DevicePolicyManager devicePolicyManager) {
        super(3, continuation);
        this.this$0 = biometricSettingsRepositoryImpl;
        this.$backgroundDispatcher$inlined = coroutineDispatcher;
        this.$devicePolicyManager$inlined = devicePolicyManager;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        BiometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$4 biometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$4 = new BiometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$4((Continuation) obj3, this.this$0, this.$backgroundDispatcher$inlined, this.$devicePolicyManager$inlined);
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
            Flow distinctUntilChanged = FlowKt.distinctUntilChanged(FlowKt.flowOn(FlowKt.transformLatest(this.this$0.devicePolicyChangedForAllUsers, new C1595xe0cb3b9f(this.$devicePolicyManager$inlined, ((Number) this.L$1).intValue(), null)), this.$backgroundDispatcher$inlined));
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
