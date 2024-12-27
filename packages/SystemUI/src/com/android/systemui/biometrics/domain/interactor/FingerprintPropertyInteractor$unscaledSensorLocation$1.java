package com.android.systemui.biometrics.domain.interactor;

import android.hardware.biometrics.SensorLocationInternal;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

final class FingerprintPropertyInteractor$unscaledSensorLocation$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public FingerprintPropertyInteractor$unscaledSensorLocation$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        FingerprintPropertyInteractor$unscaledSensorLocation$1 fingerprintPropertyInteractor$unscaledSensorLocation$1 = new FingerprintPropertyInteractor$unscaledSensorLocation$1((Continuation) obj3);
        fingerprintPropertyInteractor$unscaledSensorLocation$1.L$0 = (Map) obj;
        fingerprintPropertyInteractor$unscaledSensorLocation$1.L$1 = (String) obj2;
        return fingerprintPropertyInteractor$unscaledSensorLocation$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Map map = (Map) this.L$0;
        return map.getOrDefault((String) this.L$1, map.getOrDefault("", SensorLocationInternal.DEFAULT));
    }
}
