package com.android.systemui.statusbar.pipeline.satellite.data.prod;

import android.telephony.TelephonyManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.ExecutorsKt;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class DeviceBasedSatelliteRepositoryImpl$radioPowerState$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ TelephonyManager $telephonyManager;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DeviceBasedSatelliteRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceBasedSatelliteRepositoryImpl$radioPowerState$1(TelephonyManager telephonyManager, DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.$telephonyManager = telephonyManager;
        this.this$0 = deviceBasedSatelliteRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DeviceBasedSatelliteRepositoryImpl$radioPowerState$1 deviceBasedSatelliteRepositoryImpl$radioPowerState$1 = new DeviceBasedSatelliteRepositoryImpl$radioPowerState$1(this.$telephonyManager, this.this$0, continuation);
        deviceBasedSatelliteRepositoryImpl$radioPowerState$1.L$0 = obj;
        return deviceBasedSatelliteRepositoryImpl$radioPowerState$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceBasedSatelliteRepositoryImpl$radioPowerState$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope = (ProducerScope) this.L$0;
            final DeviceBasedSatelliteRepositoryImpl$radioPowerState$1$cb$1 deviceBasedSatelliteRepositoryImpl$radioPowerState$1$cb$1 = new DeviceBasedSatelliteRepositoryImpl$radioPowerState$1$cb$1(producerScope);
            this.$telephonyManager.registerTelephonyCallback(ExecutorsKt.asExecutor(this.this$0.bgDispatcher), deviceBasedSatelliteRepositoryImpl$radioPowerState$1$cb$1);
            final TelephonyManager telephonyManager = this.$telephonyManager;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$radioPowerState$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    telephonyManager.unregisterTelephonyCallback(deviceBasedSatelliteRepositoryImpl$radioPowerState$1$cb$1);
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
