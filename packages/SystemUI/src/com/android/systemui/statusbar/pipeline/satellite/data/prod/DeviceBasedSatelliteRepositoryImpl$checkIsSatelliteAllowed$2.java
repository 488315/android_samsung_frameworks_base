package com.android.systemui.statusbar.pipeline.satellite.data.prod;

import android.os.OutcomeReceiver;
import android.telephony.satellite.SatelliteManager;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl;
import java.util.concurrent.Executor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.ExecutorsKt;

final class DeviceBasedSatelliteRepositoryImpl$checkIsSatelliteAllowed$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ DeviceBasedSatelliteRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceBasedSatelliteRepositoryImpl$checkIsSatelliteAllowed$2(DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = deviceBasedSatelliteRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DeviceBasedSatelliteRepositoryImpl$checkIsSatelliteAllowed$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceBasedSatelliteRepositoryImpl$checkIsSatelliteAllowed$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl = this.this$0;
        SatelliteManager satelliteManager = deviceBasedSatelliteRepositoryImpl.satelliteManager;
        if (satelliteManager == null) {
            return null;
        }
        Executor asExecutor = ExecutorsKt.asExecutor(deviceBasedSatelliteRepositoryImpl.bgDispatcher);
        final DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl2 = this.this$0;
        satelliteManager.requestIsCommunicationAllowedForCurrentLocation(asExecutor, new OutcomeReceiver() { // from class: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$checkIsSatelliteAllowed$2.1
            @Override // android.os.OutcomeReceiver
            public final void onError(Throwable th) {
                DeviceBasedSatelliteRepositoryImpl.Companion.access$e(DeviceBasedSatelliteRepositoryImpl.Companion, DeviceBasedSatelliteRepositoryImpl.this.logBuffer, "Found exception when checking availability", (SatelliteManager.SatelliteException) th);
                DeviceBasedSatelliteRepositoryImpl.this.isSatelliteAllowedForCurrentLocation.updateState(null, Boolean.FALSE);
            }

            @Override // android.os.OutcomeReceiver
            public final void onResult(Object obj2) {
                Boolean bool = (Boolean) obj2;
                final boolean booleanValue = bool.booleanValue();
                DeviceBasedSatelliteRepositoryImpl.Companion.i$default(DeviceBasedSatelliteRepositoryImpl.Companion, DeviceBasedSatelliteRepositoryImpl.this.logBuffer, new Function1() { // from class: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$checkIsSatelliteAllowed$2$1$onResult$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj3) {
                        return KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("isSatelliteAllowedForCurrentLocation: ", booleanValue);
                    }
                });
                DeviceBasedSatelliteRepositoryImpl.this.isSatelliteAllowedForCurrentLocation.updateState(null, bool);
            }
        });
        return Unit.INSTANCE;
    }
}
