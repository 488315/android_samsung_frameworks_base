package com.android.systemui.statusbar.pipeline.satellite.data.prod;

import android.telephony.satellite.NtnSignalStrength;
import android.telephony.satellite.NtnSignalStrengthCallback;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class DeviceBasedSatelliteRepositoryImpl$signalStrengthFlow$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ SatelliteManager $sm;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DeviceBasedSatelliteRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceBasedSatelliteRepositoryImpl$signalStrengthFlow$1(SatelliteManager satelliteManager, DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.$sm = satelliteManager;
        this.this$0 = deviceBasedSatelliteRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DeviceBasedSatelliteRepositoryImpl$signalStrengthFlow$1 deviceBasedSatelliteRepositoryImpl$signalStrengthFlow$1 = new DeviceBasedSatelliteRepositoryImpl$signalStrengthFlow$1(this.$sm, this.this$0, continuation);
        deviceBasedSatelliteRepositoryImpl$signalStrengthFlow$1.L$0 = obj;
        return deviceBasedSatelliteRepositoryImpl$signalStrengthFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceBasedSatelliteRepositoryImpl$signalStrengthFlow$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [android.telephony.satellite.NtnSignalStrengthCallback, com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$signalStrengthFlow$1$cb$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl = this.this$0;
            ?? r1 = new NtnSignalStrengthCallback() { // from class: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$signalStrengthFlow$1$cb$1
                public final void onNtnSignalStrengthChanged(NtnSignalStrength ntnSignalStrength) {
                    DeviceBasedSatelliteRepositoryImpl.Companion companion = DeviceBasedSatelliteRepositoryImpl.Companion;
                    LogBuffer logBuffer = DeviceBasedSatelliteRepositoryImpl.this.verboseLogBuffer;
                    DeviceBasedSatelliteRepositoryImpl$$ExternalSyntheticLambda0 deviceBasedSatelliteRepositoryImpl$$ExternalSyntheticLambda0 = new DeviceBasedSatelliteRepositoryImpl$$ExternalSyntheticLambda0(10);
                    companion.getClass();
                    LogMessage obtain = logBuffer.obtain("DeviceBasedSatelliteRepo", LogLevel.INFO, deviceBasedSatelliteRepositoryImpl$$ExternalSyntheticLambda0, null);
                    obtain.setInt1(ntnSignalStrength.getLevel());
                    Unit unit = Unit.INSTANCE;
                    logBuffer.commit(obtain);
                    ((ChannelCoroutine) producerScope).mo3456trySendJP2dKIU(Integer.valueOf(ntnSignalStrength.getLevel()));
                }
            };
            Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
            try {
                this.$sm.registerForNtnSignalStrengthChanged(ExecutorsKt.asExecutor(this.this$0.bgDispatcher), (NtnSignalStrengthCallback) r1);
                ref$BooleanRef.element = true;
                DeviceBasedSatelliteRepositoryImpl.Companion.i$default(DeviceBasedSatelliteRepositoryImpl.Companion, this.this$0.logBuffer, new DeviceBasedSatelliteRepositoryImpl$$ExternalSyntheticLambda0(8));
            } catch (Exception e) {
                DeviceBasedSatelliteRepositoryImpl.Companion.access$e(DeviceBasedSatelliteRepositoryImpl.Companion, this.this$0.logBuffer, "error registering for signal strength", e);
            }
            DeviceBasedSatelliteRepositoryImpl$signalStrengthFlow$1$$ExternalSyntheticLambda1 deviceBasedSatelliteRepositoryImpl$signalStrengthFlow$1$$ExternalSyntheticLambda1 = new DeviceBasedSatelliteRepositoryImpl$signalStrengthFlow$1$$ExternalSyntheticLambda1(ref$BooleanRef, this.$sm, (DeviceBasedSatelliteRepositoryImpl$signalStrengthFlow$1$cb$1) r1, this.this$0);
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
