package com.android.systemui.keyguard.data.repository;

import android.hardware.biometrics.BiometricSourceType;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.biometrics.shared.model.AuthenticationReason;
import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.keyguard.shared.model.AcquiredFingerprintAuthenticationStatus;
import com.android.systemui.keyguard.shared.model.ErrorFingerprintAuthenticationStatus;
import com.android.systemui.keyguard.shared.model.FailFingerprintAuthenticationStatus;
import com.android.systemui.keyguard.shared.model.FingerprintAuthenticationStatus;
import com.android.systemui.keyguard.shared.model.HelpFingerprintAuthenticationStatus;
import com.android.systemui.keyguard.shared.model.SuccessFingerprintAuthenticationStatus;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

final class DeviceEntryFingerprintAuthRepositoryImpl$authenticationStatus$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DeviceEntryFingerprintAuthRepositoryImpl this$0;

    public DeviceEntryFingerprintAuthRepositoryImpl$authenticationStatus$1(DeviceEntryFingerprintAuthRepositoryImpl deviceEntryFingerprintAuthRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = deviceEntryFingerprintAuthRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DeviceEntryFingerprintAuthRepositoryImpl$authenticationStatus$1 deviceEntryFingerprintAuthRepositoryImpl$authenticationStatus$1 = new DeviceEntryFingerprintAuthRepositoryImpl$authenticationStatus$1(this.this$0, continuation);
        deviceEntryFingerprintAuthRepositoryImpl$authenticationStatus$1.L$0 = obj;
        return deviceEntryFingerprintAuthRepositoryImpl$authenticationStatus$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceEntryFingerprintAuthRepositoryImpl$authenticationStatus$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.keyguard.data.repository.DeviceEntryFingerprintAuthRepositoryImpl$authenticationStatus$1$callback$1
                @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
                public final void onBiometricAcquired(BiometricSourceType biometricSourceType, int i2) {
                    sendUpdateIfFingerprint(biometricSourceType, new AcquiredFingerprintAuthenticationStatus(AuthenticationReason.DeviceEntryAuthentication.INSTANCE, i2));
                }

                @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
                public final void onBiometricAuthFailed(BiometricSourceType biometricSourceType) {
                    sendUpdateIfFingerprint(biometricSourceType, FailFingerprintAuthenticationStatus.INSTANCE);
                }

                @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
                public final void onBiometricAuthenticated(int i2, BiometricSourceType biometricSourceType, boolean z) {
                    sendUpdateIfFingerprint(biometricSourceType, new SuccessFingerprintAuthenticationStatus(i2, z));
                }

                @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
                public final void onBiometricError(int i2, String str, BiometricSourceType biometricSourceType) {
                    sendUpdateIfFingerprint(biometricSourceType, new ErrorFingerprintAuthenticationStatus(i2, str, 0L, 4, null));
                }

                @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
                public final void onBiometricHelp(int i2, String str, BiometricSourceType biometricSourceType) {
                    sendUpdateIfFingerprint(biometricSourceType, new HelpFingerprintAuthenticationStatus(i2, str));
                }

                public final void sendUpdateIfFingerprint(BiometricSourceType biometricSourceType, FingerprintAuthenticationStatus fingerprintAuthenticationStatus) {
                    if (biometricSourceType != BiometricSourceType.FINGERPRINT) {
                        return;
                    }
                    ChannelExt.INSTANCE.getClass();
                    ChannelExt.trySendWithFailureLogging(ProducerScope.this, fingerprintAuthenticationStatus, "DeviceEntryFingerprintAuthRepositoryImpl", "new fingerprint authentication status");
                }
            };
            this.this$0.keyguardUpdateMonitor.registerCallback(r1);
            final DeviceEntryFingerprintAuthRepositoryImpl deviceEntryFingerprintAuthRepositoryImpl = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.keyguard.data.repository.DeviceEntryFingerprintAuthRepositoryImpl$authenticationStatus$1.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    DeviceEntryFingerprintAuthRepositoryImpl.this.keyguardUpdateMonitor.removeCallback(r1);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
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
