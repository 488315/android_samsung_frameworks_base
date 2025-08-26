package com.android.systemui.keyguard.data.repository;

import android.hardware.biometrics.BiometricSourceType;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes2.dex */
final class DeviceEntryFingerprintAuthRepositoryImpl$isLockedOut$2$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DeviceEntryFingerprintAuthRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceEntryFingerprintAuthRepositoryImpl$isLockedOut$2$1(DeviceEntryFingerprintAuthRepositoryImpl deviceEntryFingerprintAuthRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = deviceEntryFingerprintAuthRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DeviceEntryFingerprintAuthRepositoryImpl$isLockedOut$2$1 deviceEntryFingerprintAuthRepositoryImpl$isLockedOut$2$1 = new DeviceEntryFingerprintAuthRepositoryImpl$isLockedOut$2$1(this.this$0, continuation);
        deviceEntryFingerprintAuthRepositoryImpl$isLockedOut$2$1.L$0 = obj;
        return deviceEntryFingerprintAuthRepositoryImpl$isLockedOut$2$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceEntryFingerprintAuthRepositoryImpl$isLockedOut$2$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope = (ProducerScope) this.L$0;
            final DeviceEntryFingerprintAuthRepositoryImpl$$ExternalSyntheticLambda0 deviceEntryFingerprintAuthRepositoryImpl$$ExternalSyntheticLambda0 = new DeviceEntryFingerprintAuthRepositoryImpl$$ExternalSyntheticLambda0(producerScope, this.this$0, 3);
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.keyguard.data.repository.DeviceEntryFingerprintAuthRepositoryImpl$isLockedOut$2$1$callback$1
                @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
                public final void onLockedOutStateChanged(BiometricSourceType biometricSourceType) {
                    if (biometricSourceType == BiometricSourceType.FINGERPRINT) {
                        deviceEntryFingerprintAuthRepositoryImpl$$ExternalSyntheticLambda0.invoke();
                    }
                }
            };
            this.this$0.keyguardUpdateMonitor.registerCallback(keyguardUpdateMonitorCallback);
            deviceEntryFingerprintAuthRepositoryImpl$$ExternalSyntheticLambda0.invoke();
            DeviceEntryFingerprintAuthRepositoryImpl$$ExternalSyntheticLambda0 deviceEntryFingerprintAuthRepositoryImpl$$ExternalSyntheticLambda02 = new DeviceEntryFingerprintAuthRepositoryImpl$$ExternalSyntheticLambda0(this.this$0, keyguardUpdateMonitorCallback, 4);
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, deviceEntryFingerprintAuthRepositoryImpl$$ExternalSyntheticLambda02, this) == coroutineSingletons) {
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
