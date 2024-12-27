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

final class DeviceEntryFingerprintAuthRepositoryImpl$isLockedOut$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DeviceEntryFingerprintAuthRepositoryImpl this$0;

    public DeviceEntryFingerprintAuthRepositoryImpl$isLockedOut$1(DeviceEntryFingerprintAuthRepositoryImpl deviceEntryFingerprintAuthRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = deviceEntryFingerprintAuthRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DeviceEntryFingerprintAuthRepositoryImpl$isLockedOut$1 deviceEntryFingerprintAuthRepositoryImpl$isLockedOut$1 = new DeviceEntryFingerprintAuthRepositoryImpl$isLockedOut$1(this.this$0, continuation);
        deviceEntryFingerprintAuthRepositoryImpl$isLockedOut$1.L$0 = obj;
        return deviceEntryFingerprintAuthRepositoryImpl$isLockedOut$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceEntryFingerprintAuthRepositoryImpl$isLockedOut$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final DeviceEntryFingerprintAuthRepositoryImpl deviceEntryFingerprintAuthRepositoryImpl = this.this$0;
            final Function0 function0 = new Function0() { // from class: com.android.systemui.keyguard.data.repository.DeviceEntryFingerprintAuthRepositoryImpl$isLockedOut$1$sendLockoutUpdate$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    ChannelExt channelExt = ChannelExt.INSTANCE;
                    ProducerScope producerScope2 = ProducerScope.this;
                    Boolean valueOf = Boolean.valueOf(deviceEntryFingerprintAuthRepositoryImpl.keyguardUpdateMonitor.isFingerprintLockedOut());
                    channelExt.getClass();
                    ChannelExt.trySendWithFailureLogging(producerScope2, valueOf, "DeviceEntryFingerprintAuthRepositoryImpl", "onLockedOutStateChanged");
                    return Unit.INSTANCE;
                }
            };
            final ?? r3 = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.keyguard.data.repository.DeviceEntryFingerprintAuthRepositoryImpl$isLockedOut$1$callback$1
                @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
                public final void onLockedOutStateChanged(BiometricSourceType biometricSourceType) {
                    if (biometricSourceType == BiometricSourceType.FINGERPRINT) {
                        Function0.this.invoke();
                    }
                }
            };
            this.this$0.keyguardUpdateMonitor.registerCallback(r3);
            function0.invoke();
            final DeviceEntryFingerprintAuthRepositoryImpl deviceEntryFingerprintAuthRepositoryImpl2 = this.this$0;
            Function0 function02 = new Function0() { // from class: com.android.systemui.keyguard.data.repository.DeviceEntryFingerprintAuthRepositoryImpl$isLockedOut$1.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    DeviceEntryFingerprintAuthRepositoryImpl.this.keyguardUpdateMonitor.removeCallback(r3);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function02, this) == coroutineSingletons) {
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
