package com.android.systemui.keyguard.data.repository;

import android.hardware.biometrics.BiometricSourceType;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.common.coroutine.ChannelExt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.keyguard.KeyguardUpdateMonitorCallback, com.android.systemui.keyguard.data.repository.DeviceEntryFingerprintAuthRepositoryImpl$isRunning$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.keyguard.data.repository.DeviceEntryFingerprintAuthRepositoryImpl$isRunning$1$callback$1
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
            this.this$0.keyguardUpdateMonitor.registerCallback(r1);
            ChannelExt channelExt = ChannelExt.INSTANCE;
            Boolean valueOf = Boolean.valueOf(this.this$0.keyguardUpdateMonitor.isFingerprintDetectionRunning());
            channelExt.getClass();
            ChannelExt.trySendWithFailureLogging(producerScope, valueOf, "DeviceEntryFingerprintAuthRepositoryImpl", "Initial fingerprint running state");
            final DeviceEntryFingerprintAuthRepositoryImpl deviceEntryFingerprintAuthRepositoryImpl = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.keyguard.data.repository.DeviceEntryFingerprintAuthRepositoryImpl$isRunning$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
