package com.android.systemui.statusbar.pipeline.satellite.domain.interactor;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* loaded from: classes3.dex */
final class DeviceBasedSatelliteInteractor$areAllConnectionsOutOfService$1 extends SuspendLambda implements Function3 {
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;
    final /* synthetic */ DeviceBasedSatelliteInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceBasedSatelliteInteractor$areAllConnectionsOutOfService$1(DeviceBasedSatelliteInteractor deviceBasedSatelliteInteractor, Continuation continuation) {
        super(3, continuation);
        this.this$0 = deviceBasedSatelliteInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        boolean zBooleanValue2 = ((Boolean) obj2).booleanValue();
        DeviceBasedSatelliteInteractor$areAllConnectionsOutOfService$1 deviceBasedSatelliteInteractor$areAllConnectionsOutOfService$1 = new DeviceBasedSatelliteInteractor$areAllConnectionsOutOfService$1(this.this$0, (Continuation) obj3);
        deviceBasedSatelliteInteractor$areAllConnectionsOutOfService$1.Z$0 = zBooleanValue;
        deviceBasedSatelliteInteractor$areAllConnectionsOutOfService$1.Z$1 = zBooleanValue2;
        return deviceBasedSatelliteInteractor$areAllConnectionsOutOfService$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        boolean z2 = this.Z$1;
        LogBuffer logBuffer = this.this$0.logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("DeviceBasedSatelliteInteractor", LogLevel.INFO, new DeviceBasedSatelliteInteractor$areAllConnectionsOutOfService$1$$ExternalSyntheticLambda0(), null);
        ((LogMessageImpl) logMessageObtain).bool1 = z;
        ((LogMessageImpl) logMessageObtain).bool2 = z2;
        logBuffer.commit(logMessageObtain);
        return Boolean.valueOf(z && !z2);
    }
}
