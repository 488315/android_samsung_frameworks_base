package com.android.systemui.statusbar.pipeline.satellite.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class DeviceBasedSatelliteInteractor$allConnectionsOos$1$1 extends SuspendLambda implements Function4 {
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    int label;

    public DeviceBasedSatelliteInteractor$allConnectionsOos$1$1(Continuation continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        boolean booleanValue3 = ((Boolean) obj3).booleanValue();
        DeviceBasedSatelliteInteractor$allConnectionsOos$1$1 deviceBasedSatelliteInteractor$allConnectionsOos$1$1 = new DeviceBasedSatelliteInteractor$allConnectionsOos$1$1((Continuation) obj4);
        deviceBasedSatelliteInteractor$allConnectionsOos$1$1.Z$0 = booleanValue;
        deviceBasedSatelliteInteractor$allConnectionsOos$1$1.Z$1 = booleanValue2;
        deviceBasedSatelliteInteractor$allConnectionsOos$1$1.Z$2 = booleanValue3;
        return deviceBasedSatelliteInteractor$allConnectionsOos$1$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf((this.Z$0 || this.Z$1 || this.Z$2) ? false : true);
    }
}
