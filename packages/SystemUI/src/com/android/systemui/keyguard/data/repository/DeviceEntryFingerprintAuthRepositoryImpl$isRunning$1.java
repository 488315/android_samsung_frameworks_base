package com.android.systemui.keyguard.data.repository;

import android.hardware.biometrics.BiometricSourceType;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.common.coroutine.ChannelExt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class DeviceEntryFingerprintAuthRepositoryImpl$isRunning$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DeviceEntryFingerprintAuthRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceEntryFingerprintAuthRepositoryImpl$isRunning$1(DeviceEntryFingerprintAuthRepositoryImpl deviceEntryFingerprintAuthRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = deviceEntryFingerprintAuthRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DeviceEntryFingerprintAuthRepositoryImpl$isRunning$1 deviceEntryFingerprintAuthRepositoryImpl$isRunning$1 = new DeviceEntryFingerprintAuthRepositoryImpl$isRunning$1(this.this$0, continuation);
        deviceEntryFingerprintAuthRepositoryImpl$isRunning$1.L$0 = obj;
        return deviceEntryFingerprintAuthRepositoryImpl$isRunning$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceEntryFingerprintAuthRepositoryImpl$isRunning$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.keyguard.data.repository.DeviceEntryFingerprintAuthRepositoryImpl$isRunning$1$callback$1
                @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
                public final void onBiometricRunningStateChanged(boolean z, BiometricSourceType biometricSourceType) {
                    if (biometricSourceType == BiometricSourceType.FINGERPRINT) {
                        ChannelExt channelExt = ChannelExt.INSTANCE;
                        Boolean valueOf = Boolean.valueOf(z);
                        channelExt.getClass();
                        ChannelExt.trySendWithFailureLogging(ProducerScope.this, valueOf, "DeviceEntryFingerprintAuthRepositoryImpl", "Fingerprint running state changed");
                    }
                }
            };
            this.this$0.keyguardUpdateMonitor.registerCallback(keyguardUpdateMonitorCallback);
            ChannelExt channelExt = ChannelExt.INSTANCE;
            Boolean valueOf = Boolean.valueOf(this.this$0.keyguardUpdateMonitor.isFingerprintDetectionRunning());
            channelExt.getClass();
            ChannelExt.trySendWithFailureLogging(producerScope, valueOf, "DeviceEntryFingerprintAuthRepositoryImpl", "Initial fingerprint running state");
            DeviceEntryFingerprintAuthRepositoryImpl$$ExternalSyntheticLambda0 deviceEntryFingerprintAuthRepositoryImpl$$ExternalSyntheticLambda0 = new DeviceEntryFingerprintAuthRepositoryImpl$$ExternalSyntheticLambda0(this.this$0, keyguardUpdateMonitorCallback, 5);
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, deviceEntryFingerprintAuthRepositoryImpl$$ExternalSyntheticLambda0, this) == coroutineSingletons) {
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
