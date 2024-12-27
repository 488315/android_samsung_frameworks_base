package com.android.systemui.statusbar.pipeline.satellite.data.prod;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.telephony.satellite.SatelliteManager;
import android.telephony.satellite.SatelliteModemStateCallback;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl;
import com.android.systemui.statusbar.pipeline.satellite.shared.model.SatelliteConnectionState;
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
import kotlinx.coroutines.channels.SendChannel;

final class DeviceBasedSatelliteRepositoryImpl$connectionStateFlow$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ SatelliteManager $sm;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DeviceBasedSatelliteRepositoryImpl this$0;

    public DeviceBasedSatelliteRepositoryImpl$connectionStateFlow$1(SatelliteManager satelliteManager, DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.$sm = satelliteManager;
        this.this$0 = deviceBasedSatelliteRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DeviceBasedSatelliteRepositoryImpl$connectionStateFlow$1 deviceBasedSatelliteRepositoryImpl$connectionStateFlow$1 = new DeviceBasedSatelliteRepositoryImpl$connectionStateFlow$1(this.$sm, this.this$0, continuation);
        deviceBasedSatelliteRepositoryImpl$connectionStateFlow$1.L$0 = obj;
        return deviceBasedSatelliteRepositoryImpl$connectionStateFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceBasedSatelliteRepositoryImpl$connectionStateFlow$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl = this.this$0;
            final SatelliteModemStateCallback satelliteModemStateCallback = new SatelliteModemStateCallback() { // from class: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$connectionStateFlow$1$cb$1
                public final void onSatelliteModemStateChanged(final int i2) {
                    SatelliteConnectionState satelliteConnectionState;
                    DeviceBasedSatelliteRepositoryImpl.Companion companion = DeviceBasedSatelliteRepositoryImpl.Companion;
                    LogBuffer logBuffer = DeviceBasedSatelliteRepositoryImpl.this.logBuffer;
                    Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$connectionStateFlow$1$cb$1.1
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj2) {
                            ((LogMessage) obj2).setInt1(i2);
                            return Unit.INSTANCE;
                        }
                    };
                    AnonymousClass2 anonymousClass2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$connectionStateFlow$1$cb$1.2
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj2) {
                            return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(((LogMessage) obj2).getInt1(), "onSatelliteModemStateChanged: state=");
                        }
                    };
                    companion.getClass();
                    DeviceBasedSatelliteRepositoryImpl.Companion.i(logBuffer, function1, anonymousClass2);
                    SendChannel sendChannel = producerScope;
                    SatelliteConnectionState.Companion.getClass();
                    switch (i2) {
                        case -1:
                            satelliteConnectionState = SatelliteConnectionState.Unknown;
                            break;
                        case 0:
                        case 1:
                        case 6:
                            satelliteConnectionState = SatelliteConnectionState.On;
                            break;
                        case 2:
                        case 3:
                        case 7:
                            satelliteConnectionState = SatelliteConnectionState.Connected;
                            break;
                        case 4:
                        case 5:
                            satelliteConnectionState = SatelliteConnectionState.Off;
                            break;
                        default:
                            satelliteConnectionState = SatelliteConnectionState.Unknown;
                            break;
                    }
                    ((ChannelCoroutine) sendChannel).mo2552trySendJP2dKIU(satelliteConnectionState);
                }
            };
            final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
            try {
                ref$BooleanRef.element = this.$sm.registerForModemStateChanged(ExecutorsKt.asExecutor(this.this$0.bgDispatcher), satelliteModemStateCallback) == 0;
            } catch (Exception e) {
                DeviceBasedSatelliteRepositoryImpl.Companion.access$e(DeviceBasedSatelliteRepositoryImpl.Companion, this.this$0.logBuffer, "error registering for modem state", e);
            }
            final SatelliteManager satelliteManager = this.$sm;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$connectionStateFlow$1.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    if (Ref$BooleanRef.this.element) {
                        satelliteManager.unregisterForModemStateChanged(satelliteModemStateCallback);
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
