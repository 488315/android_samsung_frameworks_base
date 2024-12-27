package com.android.systemui.camera.data.repository;

import android.hardware.SensorPrivacyManager;
import android.os.UserHandle;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

final class CameraSensorPrivacyRepositoryKt$isEnabled$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ SensorPrivacyManager $this_isEnabled;
    final /* synthetic */ UserHandle $userHandle;
    private /* synthetic */ Object L$0;
    int label;

    public CameraSensorPrivacyRepositoryKt$isEnabled$1(SensorPrivacyManager sensorPrivacyManager, UserHandle userHandle, Continuation continuation) {
        super(2, continuation);
        this.$this_isEnabled = sensorPrivacyManager;
        this.$userHandle = userHandle;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CameraSensorPrivacyRepositoryKt$isEnabled$1 cameraSensorPrivacyRepositoryKt$isEnabled$1 = new CameraSensorPrivacyRepositoryKt$isEnabled$1(this.$this_isEnabled, this.$userHandle, continuation);
        cameraSensorPrivacyRepositoryKt$isEnabled$1.L$0 = obj;
        return cameraSensorPrivacyRepositoryKt$isEnabled$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CameraSensorPrivacyRepositoryKt$isEnabled$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final SensorPrivacyManager.OnSensorPrivacyChangedListener onSensorPrivacyChangedListener = new SensorPrivacyManager.OnSensorPrivacyChangedListener() { // from class: com.android.systemui.camera.data.repository.CameraSensorPrivacyRepositoryKt$isEnabled$1$privacyCallback$1
                public final void onSensorPrivacyChanged(int i2, boolean z) {
                    if (i2 == 2) {
                        ((ChannelCoroutine) ProducerScope.this).mo2552trySendJP2dKIU(Boolean.valueOf(z));
                    }
                }
            };
            this.$this_isEnabled.addSensorPrivacyListener(2, this.$userHandle.getIdentifier(), onSensorPrivacyChangedListener);
            final SensorPrivacyManager sensorPrivacyManager = this.$this_isEnabled;
            Function0 function0 = new Function0() { // from class: com.android.systemui.camera.data.repository.CameraSensorPrivacyRepositoryKt$isEnabled$1.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    sensorPrivacyManager.removeSensorPrivacyListener(onSensorPrivacyChangedListener);
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
