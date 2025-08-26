package com.android.systemui.statusbar.pipeline.satellite.data.prod;

import android.telephony.satellite.SatelliteCommunicationAccessStateCallback;
import android.telephony.satellite.SatelliteManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlinx.coroutines.ExecutorsKt;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes3.dex */
final class DeviceBasedSatelliteRepositoryImpl$isSatelliteAvailableFlow$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ SatelliteManager $sm;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DeviceBasedSatelliteRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceBasedSatelliteRepositoryImpl$isSatelliteAvailableFlow$1(DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl, SatelliteManager satelliteManager, Continuation continuation) {
        super(2, continuation);
        this.this$0 = deviceBasedSatelliteRepositoryImpl;
        this.$sm = satelliteManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DeviceBasedSatelliteRepositoryImpl$isSatelliteAvailableFlow$1 deviceBasedSatelliteRepositoryImpl$isSatelliteAvailableFlow$1 = new DeviceBasedSatelliteRepositoryImpl$isSatelliteAvailableFlow$1(this.this$0, this.$sm, continuation);
        deviceBasedSatelliteRepositoryImpl$isSatelliteAvailableFlow$1.L$0 = obj;
        return deviceBasedSatelliteRepositoryImpl$isSatelliteAvailableFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceBasedSatelliteRepositoryImpl$isSatelliteAvailableFlow$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [android.telephony.satellite.SatelliteCommunicationAccessStateCallback, com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$isSatelliteAvailableFlow$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl = this.this$0;
            ?? r1 = new SatelliteCommunicationAccessStateCallback() { // from class: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$isSatelliteAvailableFlow$1$callback$1
                public final void onAccessAllowedStateChanged(boolean z) {
                    DeviceBasedSatelliteRepositoryImpl.Companion companion = DeviceBasedSatelliteRepositoryImpl.Companion;
                    LogBuffer logBuffer = deviceBasedSatelliteRepositoryImpl.logBuffer;
                    DeviceBasedSatelliteRepositoryImpl$$ExternalSyntheticLambda0 deviceBasedSatelliteRepositoryImpl$$ExternalSyntheticLambda0 = new DeviceBasedSatelliteRepositoryImpl$$ExternalSyntheticLambda0(6);
                    companion.getClass();
                    LogMessage logMessageObtain = logBuffer.obtain("DeviceBasedSatelliteRepo", LogLevel.INFO, deviceBasedSatelliteRepositoryImpl$$ExternalSyntheticLambda0, null);
                    logMessageObtain.setBool1(z);
                    Unit unit = Unit.INSTANCE;
                    logBuffer.commit(logMessageObtain);
                    ((ChannelCoroutine) producerScope).mo3476trySendJP2dKIU(Boolean.valueOf(z));
                }
            };
            Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
            try {
                DeviceBasedSatelliteRepositoryImpl.Companion.i$default(DeviceBasedSatelliteRepositoryImpl.Companion, this.this$0.logBuffer, new DeviceBasedSatelliteRepositoryImpl$$ExternalSyntheticLambda0(4));
                this.$sm.registerForCommunicationAccessStateChanged(ExecutorsKt.asExecutor(this.this$0.bgDispatcher), (SatelliteCommunicationAccessStateCallback) r1);
                ref$BooleanRef.element = true;
            } catch (Exception e) {
                DeviceBasedSatelliteRepositoryImpl.Companion.access$e(DeviceBasedSatelliteRepositoryImpl.Companion, this.this$0.logBuffer, "Error calling registerForCommunicationAccessStateChanged", e);
            }
            DeviceBasedSatelliteRepositoryImpl$signalStrengthFlow$1$$ExternalSyntheticLambda1 deviceBasedSatelliteRepositoryImpl$signalStrengthFlow$1$$ExternalSyntheticLambda1 = new DeviceBasedSatelliteRepositoryImpl$signalStrengthFlow$1$$ExternalSyntheticLambda1(ref$BooleanRef, this.this$0, this.$sm, (DeviceBasedSatelliteRepositoryImpl$isSatelliteAvailableFlow$1$callback$1) r1);
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, deviceBasedSatelliteRepositoryImpl$signalStrengthFlow$1$$ExternalSyntheticLambda1, this) == coroutineSingletons) {
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
