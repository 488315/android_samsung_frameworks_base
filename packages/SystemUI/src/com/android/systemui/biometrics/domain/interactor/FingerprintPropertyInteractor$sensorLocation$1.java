package com.android.systemui.biometrics.domain.interactor;

import android.hardware.biometrics.SensorLocationInternal;
import com.android.systemui.biometrics.shared.model.SensorLocation;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

final class FingerprintPropertyInteractor$sensorLocation$1 extends SuspendLambda implements Function3 {
    /* synthetic */ float F$0;
    /* synthetic */ Object L$0;
    int label;

    public FingerprintPropertyInteractor$sensorLocation$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        float floatValue = ((Number) obj2).floatValue();
        FingerprintPropertyInteractor$sensorLocation$1 fingerprintPropertyInteractor$sensorLocation$1 = new FingerprintPropertyInteractor$sensorLocation$1((Continuation) obj3);
        fingerprintPropertyInteractor$sensorLocation$1.L$0 = (SensorLocationInternal) obj;
        fingerprintPropertyInteractor$sensorLocation$1.F$0 = floatValue;
        return fingerprintPropertyInteractor$sensorLocation$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        SensorLocationInternal sensorLocationInternal = (SensorLocationInternal) this.L$0;
        return new SensorLocation(sensorLocationInternal.sensorLocationX, sensorLocationInternal.sensorLocationY, sensorLocationInternal.sensorRadius, this.F$0);
    }
}
