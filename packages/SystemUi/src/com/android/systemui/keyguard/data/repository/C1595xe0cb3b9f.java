package com.android.systemui.keyguard.data.repository;

import android.app.admin.DevicePolicyManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$isFingerprintEnabledByDevicePolicy$1$1", m277f = "BiometricSettingsRepository.kt", m278l = {310}, m279m = "invokeSuspend")
/* renamed from: com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$isFingerprintEnabledByDevicePolicy$1$1 */
/* loaded from: classes.dex */
public final class C1595xe0cb3b9f extends SuspendLambda implements Function3 {
    final /* synthetic */ DevicePolicyManager $devicePolicyManager;
    final /* synthetic */ int $userId;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1595xe0cb3b9f(DevicePolicyManager devicePolicyManager, int i, Continuation<? super C1595xe0cb3b9f> continuation) {
        super(3, continuation);
        this.$devicePolicyManager = devicePolicyManager;
        this.$userId = i;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        C1595xe0cb3b9f c1595xe0cb3b9f = new C1595xe0cb3b9f(this.$devicePolicyManager, this.$userId, (Continuation) obj3);
        c1595xe0cb3b9f.L$0 = (FlowCollector) obj;
        return c1595xe0cb3b9f.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Boolean valueOf = Boolean.valueOf((this.$devicePolicyManager.getKeyguardDisabledFeatures(null, this.$userId) & 32) == 0);
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
