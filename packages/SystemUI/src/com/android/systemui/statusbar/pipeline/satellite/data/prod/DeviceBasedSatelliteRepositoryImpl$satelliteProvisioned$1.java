package com.android.systemui.statusbar.pipeline.satellite.data.prod;

import android.telephony.satellite.SatelliteManager;
import android.telephony.satellite.SatelliteProvisionStateCallback;
import com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.ExecutorsKt;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes3.dex */
final class DeviceBasedSatelliteRepositoryImpl$satelliteProvisioned$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ SatelliteManager $sm;
    private /* synthetic */ Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ DeviceBasedSatelliteRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceBasedSatelliteRepositoryImpl$satelliteProvisioned$1(DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl, SatelliteManager satelliteManager, Continuation continuation) {
        super(2, continuation);
        this.this$0 = deviceBasedSatelliteRepositoryImpl;
        this.$sm = satelliteManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DeviceBasedSatelliteRepositoryImpl$satelliteProvisioned$1 deviceBasedSatelliteRepositoryImpl$satelliteProvisioned$1 = new DeviceBasedSatelliteRepositoryImpl$satelliteProvisioned$1(this.this$0, this.$sm, continuation);
        deviceBasedSatelliteRepositoryImpl$satelliteProvisioned$1.L$0 = obj;
        return deviceBasedSatelliteRepositoryImpl$satelliteProvisioned$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceBasedSatelliteRepositoryImpl$satelliteProvisioned$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(7:0|2|(1:(1:(3:6|22|23)(2:7|8))(1:9))(3:10|(1:13)|21)|14|24|15|19) */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x007c, code lost:
    
        r4 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x007d, code lost:
    
        com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl.Companion.access$e(com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl.Companion, r10.this$0.logBuffer, "error registering for provisioning state callback", r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0099, code lost:
    
        if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r5, r6, r10) == r1) goto L21;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        final ProducerScope producerScope;
        Object obj2;
        int i = 2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = this.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope2 = (ProducerScope) this.L$0;
            DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl = this.this$0;
            SatelliteManager satelliteManager = this.$sm;
            this.L$0 = producerScope2;
            this.L$1 = producerScope2;
            this.label = 1;
            DeviceBasedSatelliteRepositoryImpl.Companion companion = DeviceBasedSatelliteRepositoryImpl.Companion;
            deviceBasedSatelliteRepositoryImpl.getClass();
            obj = BuildersKt.withContext(deviceBasedSatelliteRepositoryImpl.bgDispatcher, new DeviceBasedSatelliteRepositoryImpl$queryIsSatelliteProvisioned$2(deviceBasedSatelliteRepositoryImpl, satelliteManager, null), this);
            if (obj != coroutineSingletons) {
                producerScope = producerScope2;
                obj2 = producerScope2;
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
        ProducerScope producerScope3 = (ProducerScope) this.L$1;
        producerScope = (ProducerScope) this.L$0;
        ResultKt.throwOnFailure(obj);
        obj2 = producerScope3;
        ((ChannelCoroutine) obj2).mo3476trySendJP2dKIU(obj);
        final DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl2 = this.this$0;
        SatelliteProvisionStateCallback satelliteProvisionStateCallback = new SatelliteProvisionStateCallback() { // from class: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$satelliteProvisioned$1$callback$1
            public final void onSatelliteProvisionStateChanged(final boolean z) {
                DeviceBasedSatelliteRepositoryImpl.Companion.i$default(DeviceBasedSatelliteRepositoryImpl.Companion, deviceBasedSatelliteRepositoryImpl2.logBuffer, new Function1() { // from class: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$satelliteProvisioned$1$callback$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj3) {
                        return "onSatelliteProvisionStateChanged: ".concat(z ? "provisioned" : "not provisioned");
                    }
                });
                ((ChannelCoroutine) producerScope).mo3476trySendJP2dKIU(Boolean.valueOf(z));
            }
        };
        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
        DeviceBasedSatelliteRepositoryImpl.Companion.i$default(DeviceBasedSatelliteRepositoryImpl.Companion, this.this$0.logBuffer, new DeviceBasedSatelliteRepositoryImpl$$ExternalSyntheticLambda0(7));
        this.$sm.registerForProvisionStateChanged(ExecutorsKt.asExecutor(this.this$0.bgDispatcher), satelliteProvisionStateCallback);
        ref$BooleanRef.element = true;
        DeviceBasedSatelliteRepositoryImpl$connectionStateFlow$1$$ExternalSyntheticLambda0 deviceBasedSatelliteRepositoryImpl$connectionStateFlow$1$$ExternalSyntheticLambda0 = new DeviceBasedSatelliteRepositoryImpl$connectionStateFlow$1$$ExternalSyntheticLambda0(ref$BooleanRef, this.$sm, satelliteProvisionStateCallback, i);
        this.L$0 = null;
        this.L$1 = null;
        this.label = 2;
    }
}
