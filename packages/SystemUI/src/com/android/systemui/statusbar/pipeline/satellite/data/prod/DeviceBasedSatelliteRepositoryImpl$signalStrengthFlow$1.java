package com.android.systemui.statusbar.pipeline.satellite.data.prod;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.telephony.satellite.NtnSignalStrength;
import android.telephony.satellite.NtnSignalStrengthCallback;
import android.telephony.satellite.SatelliteManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogMessage;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl = this.this$0;
            final NtnSignalStrengthCallback ntnSignalStrengthCallback = new NtnSignalStrengthCallback() { // from class: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$signalStrengthFlow$1$cb$1
                public final void onNtnSignalStrengthChanged(final NtnSignalStrength ntnSignalStrength) {
                    DeviceBasedSatelliteRepositoryImpl.Companion companion = DeviceBasedSatelliteRepositoryImpl.Companion;
                    LogBuffer logBuffer = DeviceBasedSatelliteRepositoryImpl.this.verboseLogBuffer;
                    Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$signalStrengthFlow$1$cb$1.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj2) {
                            ((LogMessage) obj2).setInt1(ntnSignalStrength.getLevel());
                            return Unit.INSTANCE;
                        }
                    };
                    AnonymousClass2 anonymousClass2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$signalStrengthFlow$1$cb$1.2
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj2) {
                            return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(((LogMessage) obj2).getInt1(), "onNtnSignalStrengthChanged: level=");
                        }
                    };
                    companion.getClass();
                    DeviceBasedSatelliteRepositoryImpl.Companion.i(logBuffer, function1, anonymousClass2);
                    ((ChannelCoroutine) producerScope).mo2552trySendJP2dKIU(Integer.valueOf(ntnSignalStrength.getLevel()));
                }
            };
            final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
            try {
                this.$sm.registerForNtnSignalStrengthChanged(ExecutorsKt.asExecutor(this.this$0.bgDispatcher), ntnSignalStrengthCallback);
                ref$BooleanRef.element = true;
                DeviceBasedSatelliteRepositoryImpl.Companion.i$default(DeviceBasedSatelliteRepositoryImpl.Companion, this.this$0.logBuffer, new Function1() { // from class: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$signalStrengthFlow$1.1
                    @Override // kotlin.jvm.functions.Function1
                    public final /* bridge */ /* synthetic */ Object invoke(Object obj2) {
                        return "Registered for signal strength successfully";
                    }
                });
            } catch (Exception e) {
                DeviceBasedSatelliteRepositoryImpl.Companion.access$e(DeviceBasedSatelliteRepositoryImpl.Companion, this.this$0.logBuffer, "error registering for signal strength", e);
            }
            final SatelliteManager satelliteManager = this.$sm;
            final DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$signalStrengthFlow$1.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    if (Ref$BooleanRef.this.element) {
                        satelliteManager.unregisterForNtnSignalStrengthChanged(ntnSignalStrengthCallback);
                        DeviceBasedSatelliteRepositoryImpl.Companion.i$default(DeviceBasedSatelliteRepositoryImpl.Companion, deviceBasedSatelliteRepositoryImpl2.logBuffer, new Function1() { // from class: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl.signalStrengthFlow.1.2.1
                            @Override // kotlin.jvm.functions.Function1
                            public final /* bridge */ /* synthetic */ Object invoke(Object obj2) {
                                return "Unregistered for signal strength successfully";
                            }
                        });
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
