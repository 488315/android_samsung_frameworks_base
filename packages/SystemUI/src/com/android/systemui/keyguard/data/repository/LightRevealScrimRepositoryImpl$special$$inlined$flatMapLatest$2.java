package com.android.systemui.keyguard.data.repository;

import com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl;
import com.android.systemui.keyguard.shared.model.BiometricUnlockModel;
import com.android.systemui.keyguard.shared.model.BiometricUnlockSource;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

public final class LightRevealScrimRepositoryImpl$special$$inlined$flatMapLatest$2 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ LightRevealScrimRepositoryImpl this$0;

    public LightRevealScrimRepositoryImpl$special$$inlined$flatMapLatest$2(Continuation continuation, LightRevealScrimRepositoryImpl lightRevealScrimRepositoryImpl) {
        super(3, continuation);
        this.this$0 = lightRevealScrimRepositoryImpl;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        LightRevealScrimRepositoryImpl$special$$inlined$flatMapLatest$2 lightRevealScrimRepositoryImpl$special$$inlined$flatMapLatest$2 = new LightRevealScrimRepositoryImpl$special$$inlined$flatMapLatest$2((Continuation) obj3, this.this$0);
        lightRevealScrimRepositoryImpl$special$$inlined$flatMapLatest$2.L$0 = (FlowCollector) obj;
        lightRevealScrimRepositoryImpl$special$$inlined$flatMapLatest$2.L$1 = obj2;
        return lightRevealScrimRepositoryImpl$special$$inlined$flatMapLatest$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flow;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            BiometricUnlockModel biometricUnlockModel = (BiometricUnlockModel) this.L$1;
            int i2 = LightRevealScrimRepositoryImpl.WhenMappings.$EnumSwitchMapping$0[biometricUnlockModel.mode.ordinal()];
            if (i2 == 1 || i2 == 2 || i2 == 3) {
                flow = biometricUnlockModel.source == BiometricUnlockSource.FACE_SENSOR ? this.this$0.faceRevealEffect : this.this$0.fingerprintRevealEffect;
            } else {
                flow = this.this$0.nonBiometricRevealEffect;
            }
            this.label = 1;
            if (FlowKt.emitAll(this, flow, flowCollector) == coroutineSingletons) {
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
