package com.android.systemui.keyguard.data.repository;

import android.app.admin.DevicePolicyManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$isFaceEnabledByDevicePolicy$1", m277f = "BiometricSettingsRepository.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
public final class BiometricSettingsRepositoryImpl$isFaceEnabledByDevicePolicy$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ DevicePolicyManager $devicePolicyManager;
    /* synthetic */ int I$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BiometricSettingsRepositoryImpl$isFaceEnabledByDevicePolicy$1(DevicePolicyManager devicePolicyManager, Continuation<? super BiometricSettingsRepositoryImpl$isFaceEnabledByDevicePolicy$1> continuation) {
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
        return Boolean.valueOf((this.$devicePolicyManager.getKeyguardDisabledFeatures(null, this.I$0) & 128) == 0);
    }
}
