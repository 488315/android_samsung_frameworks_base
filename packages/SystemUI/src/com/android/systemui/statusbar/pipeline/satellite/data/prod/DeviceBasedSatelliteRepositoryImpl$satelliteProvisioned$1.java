package com.android.systemui.statusbar.pipeline.satellite.data.prod;

import android.telephony.satellite.SatelliteManager;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Can't wrap try/catch for region: R(7:0|1|(1:(1:(3:5|6|7)(2:9|10))(1:11))(3:21|(1:23)|17)|12|13|14|15) */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0099, code lost:
    
        if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r5, r6, r10) == r1) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x007c, code lost:
    
        r4 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x007d, code lost:
    
        com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl.Companion.access$e(com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl.Companion, r10.this$0.logBuffer, "error registering for provisioning state callback", r4);
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r11) {
        /*
            r10 = this;
            r0 = 2
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r10.label
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L26
            if (r2 == r4) goto L1a
            if (r2 != r0) goto L12
            kotlin.ResultKt.throwOnFailure(r11)
            goto L9c
        L12:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L1a:
            java.lang.Object r2 = r10.L$1
            kotlinx.coroutines.channels.ProducerScope r2 = (kotlinx.coroutines.channels.ProducerScope) r2
            java.lang.Object r5 = r10.L$0
            kotlinx.coroutines.channels.ProducerScope r5 = (kotlinx.coroutines.channels.ProducerScope) r5
            kotlin.ResultKt.throwOnFailure(r11)
            goto L4c
        L26:
            kotlin.ResultKt.throwOnFailure(r11)
            java.lang.Object r11 = r10.L$0
            r2 = r11
            kotlinx.coroutines.channels.ProducerScope r2 = (kotlinx.coroutines.channels.ProducerScope) r2
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl r11 = r10.this$0
            android.telephony.satellite.SatelliteManager r5 = r10.$sm
            r10.L$0 = r2
            r10.L$1 = r2
            r10.label = r4
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$Companion r6 = com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl.Companion
            r11.getClass()
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$queryIsSatelliteProvisioned$2 r6 = new com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$queryIsSatelliteProvisioned$2
            r6.<init>(r11, r5, r3)
            kotlinx.coroutines.CoroutineDispatcher r11 = r11.bgDispatcher
            java.lang.Object r11 = kotlinx.coroutines.BuildersKt.withContext(r11, r6, r10)
            if (r11 != r1) goto L4b
            goto L9b
        L4b:
            r5 = r2
        L4c:
            kotlinx.coroutines.channels.ChannelCoroutine r2 = (kotlinx.coroutines.channels.ChannelCoroutine) r2
            r2.mo3456trySendJP2dKIU(r11)
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$satelliteProvisioned$1$callback$1 r11 = new com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$satelliteProvisioned$1$callback$1
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl r2 = r10.this$0
            r11.<init>()
            kotlin.jvm.internal.Ref$BooleanRef r2 = new kotlin.jvm.internal.Ref$BooleanRef
            r2.<init>()
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$Companion r6 = com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl.Companion     // Catch: java.lang.Exception -> L7c
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl r7 = r10.this$0     // Catch: java.lang.Exception -> L7c
            com.android.systemui.log.LogBuffer r7 = r7.logBuffer     // Catch: java.lang.Exception -> L7c
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$$ExternalSyntheticLambda0 r8 = new com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$$ExternalSyntheticLambda0     // Catch: java.lang.Exception -> L7c
            r9 = 7
            r8.<init>(r9)     // Catch: java.lang.Exception -> L7c
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl.Companion.i$default(r6, r7, r8)     // Catch: java.lang.Exception -> L7c
            android.telephony.satellite.SatelliteManager r6 = r10.$sm     // Catch: java.lang.Exception -> L7c
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl r7 = r10.this$0     // Catch: java.lang.Exception -> L7c
            kotlinx.coroutines.CoroutineDispatcher r7 = r7.bgDispatcher     // Catch: java.lang.Exception -> L7c
            java.util.concurrent.Executor r7 = kotlinx.coroutines.ExecutorsKt.asExecutor(r7)     // Catch: java.lang.Exception -> L7c
            r6.registerForProvisionStateChanged(r7, r11)     // Catch: java.lang.Exception -> L7c
            r2.element = r4     // Catch: java.lang.Exception -> L7c
            goto L88
        L7c:
            r4 = move-exception
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$Companion r6 = com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl.Companion
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl r7 = r10.this$0
            com.android.systemui.log.LogBuffer r7 = r7.logBuffer
            java.lang.String r8 = "error registering for provisioning state callback"
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl.Companion.access$e(r6, r7, r8, r4)
        L88:
            android.telephony.satellite.SatelliteManager r4 = r10.$sm
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$connectionStateFlow$1$$ExternalSyntheticLambda0 r6 = new com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$connectionStateFlow$1$$ExternalSyntheticLambda0
            r6.<init>(r2, r4, r11, r0)
            r10.L$0 = r3
            r10.L$1 = r3
            r10.label = r0
            java.lang.Object r10 = kotlinx.coroutines.channels.ProduceKt.awaitClose(r5, r6, r10)
            if (r10 != r1) goto L9c
        L9b:
            return r1
        L9c:
            kotlin.Unit r10 = kotlin.Unit.INSTANCE
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$satelliteProvisioned$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
