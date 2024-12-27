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
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        DeviceBasedSatelliteInteractor$areAllConnectionsOutOfService$1 deviceBasedSatelliteInteractor$areAllConnectionsOutOfService$1 = new DeviceBasedSatelliteInteractor$areAllConnectionsOutOfService$1(this.this$0, (Continuation) obj3);
        deviceBasedSatelliteInteractor$areAllConnectionsOutOfService$1.Z$0 = booleanValue;
        deviceBasedSatelliteInteractor$areAllConnectionsOutOfService$1.Z$1 = booleanValue2;
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
        LogMessage obtain = logBuffer.obtain("DeviceBasedSatelliteInteractor", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.pipeline.satellite.domain.interactor.DeviceBasedSatelliteInteractor$areAllConnectionsOutOfService$1.2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                LogMessage logMessage = (LogMessage) obj2;
                return "Updating OOS status. allConnectionsOOs=" + logMessage.getBool1() + " deviceEmergencyOnly=" + logMessage.getBool2();
            }
        }, null);
        ((LogMessageImpl) obtain).bool1 = z;
        ((LogMessageImpl) obtain).bool2 = z2;
        logBuffer.commit(obtain);
        return Boolean.valueOf(z && !z2);
    }
}
