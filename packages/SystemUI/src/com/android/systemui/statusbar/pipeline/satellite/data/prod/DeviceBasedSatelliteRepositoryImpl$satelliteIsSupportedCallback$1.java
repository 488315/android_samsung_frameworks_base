package com.android.systemui.statusbar.pipeline.satellite.data.prod;

import android.telephony.satellite.SatelliteSupportedStateCallback;
import com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlinx.coroutines.ExecutorsKt;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

final class DeviceBasedSatelliteRepositoryImpl$satelliteIsSupportedCallback$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DeviceBasedSatelliteRepositoryImpl this$0;

    public DeviceBasedSatelliteRepositoryImpl$satelliteIsSupportedCallback$1(DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = deviceBasedSatelliteRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DeviceBasedSatelliteRepositoryImpl$satelliteIsSupportedCallback$1 deviceBasedSatelliteRepositoryImpl$satelliteIsSupportedCallback$1 = new DeviceBasedSatelliteRepositoryImpl$satelliteIsSupportedCallback$1(this.this$0, continuation);
        deviceBasedSatelliteRepositoryImpl$satelliteIsSupportedCallback$1.L$0 = obj;
        return deviceBasedSatelliteRepositoryImpl$satelliteIsSupportedCallback$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceBasedSatelliteRepositoryImpl$satelliteIsSupportedCallback$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl = this.this$0;
            final SatelliteSupportedStateCallback satelliteSupportedStateCallback = new SatelliteSupportedStateCallback() { // from class: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$satelliteIsSupportedCallback$1$callback$1
                public final void onSatelliteSupportedStateChanged(final boolean z) {
                    DeviceBasedSatelliteRepositoryImpl.Companion.i$default(DeviceBasedSatelliteRepositoryImpl.Companion, DeviceBasedSatelliteRepositoryImpl.this.logBuffer, new Function1() { // from class: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$satelliteIsSupportedCallback$1$callback$1.1
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj2) {
                            return "onSatelliteSupportedStateChanged: ".concat(z ? "supported" : "not supported");
                        }
                    });
                    ((ChannelCoroutine) producerScope).mo2552trySendJP2dKIU(Boolean.valueOf(z));
                }
            };
            final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
            try {
                DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl2 = this.this$0;
                deviceBasedSatelliteRepositoryImpl2.satelliteManager.registerForSupportedStateChanged(ExecutorsKt.asExecutor(deviceBasedSatelliteRepositoryImpl2.bgDispatcher), satelliteSupportedStateCallback);
                ref$BooleanRef.element = true;
            } catch (Exception e) {
                DeviceBasedSatelliteRepositoryImpl.Companion.access$e(DeviceBasedSatelliteRepositoryImpl.Companion, this.this$0.logBuffer, "error registering for supported state change", e);
            }
            final DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl3 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$satelliteIsSupportedCallback$1.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    if (Ref$BooleanRef.this.element) {
                        deviceBasedSatelliteRepositoryImpl3.satelliteManager.unregisterForSupportedStateChanged(satelliteSupportedStateCallback);
                    }
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
