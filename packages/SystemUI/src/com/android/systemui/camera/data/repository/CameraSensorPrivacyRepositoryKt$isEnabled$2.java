package com.android.systemui.camera.data.repository;

import android.hardware.SensorPrivacyManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

final class CameraSensorPrivacyRepositoryKt$isEnabled$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ SensorPrivacyManager $this_isEnabled;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CameraSensorPrivacyRepositoryKt$isEnabled$2(SensorPrivacyManager sensorPrivacyManager, Continuation continuation) {
        super(2, continuation);
        this.$this_isEnabled = sensorPrivacyManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CameraSensorPrivacyRepositoryKt$isEnabled$2 cameraSensorPrivacyRepositoryKt$isEnabled$2 = new CameraSensorPrivacyRepositoryKt$isEnabled$2(this.$this_isEnabled, continuation);
        cameraSensorPrivacyRepositoryKt$isEnabled$2.L$0 = obj;
        return cameraSensorPrivacyRepositoryKt$isEnabled$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CameraSensorPrivacyRepositoryKt$isEnabled$2) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Boolean valueOf = Boolean.valueOf(this.$this_isEnabled.isSensorPrivacyEnabled(1, 2));
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
