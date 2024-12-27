package com.android.systemui.statusbar.pipeline.satellite.data.prod;

import android.telephony.satellite.SatelliteManager;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* JADX WARN: Can't wrap try/catch for region: R(10:0|1|(1:(1:(3:5|6|7)(2:9|10))(1:11))(2:21|(1:23)(1:24))|12|13|14|15|(1:17)|6|7) */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0078, code lost:
    
        r4 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0079, code lost:
    
        com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl.Companion.access$e(com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl.Companion, r9.this$0.logBuffer, "error registering for provisioning state callback", r4);
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r10) {
        /*
            r9 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r9.label
            r2 = 2
            r3 = 0
            r4 = 1
            if (r1 == 0) goto L26
            if (r1 == r4) goto L1a
            if (r1 != r2) goto L12
            kotlin.ResultKt.throwOnFailure(r10)
            goto L98
        L12:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L1a:
            java.lang.Object r1 = r9.L$1
            kotlinx.coroutines.channels.ProducerScope r1 = (kotlinx.coroutines.channels.ProducerScope) r1
            java.lang.Object r5 = r9.L$0
            kotlinx.coroutines.channels.ProducerScope r5 = (kotlinx.coroutines.channels.ProducerScope) r5
            kotlin.ResultKt.throwOnFailure(r10)
            goto L4c
        L26:
            kotlin.ResultKt.throwOnFailure(r10)
            java.lang.Object r10 = r9.L$0
            r1 = r10
            kotlinx.coroutines.channels.ProducerScope r1 = (kotlinx.coroutines.channels.ProducerScope) r1
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl r10 = r9.this$0
            android.telephony.satellite.SatelliteManager r5 = r9.$sm
            r9.L$0 = r1
            r9.L$1 = r1
            r9.label = r4
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$Companion r6 = com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl.Companion
            r10.getClass()
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$queryIsSatelliteProvisioned$2 r6 = new com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$queryIsSatelliteProvisioned$2
            r6.<init>(r10, r5, r3)
            kotlinx.coroutines.CoroutineDispatcher r10 = r10.bgDispatcher
            java.lang.Object r10 = kotlinx.coroutines.BuildersKt.withContext(r10, r6, r9)
            if (r10 != r0) goto L4b
            return r0
        L4b:
            r5 = r1
        L4c:
            kotlinx.coroutines.channels.ChannelCoroutine r1 = (kotlinx.coroutines.channels.ChannelCoroutine) r1
            r1.mo2552trySendJP2dKIU(r10)
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$satelliteProvisioned$1$callback$1 r10 = new com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$satelliteProvisioned$1$callback$1
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl r1 = r9.this$0
            r10.<init>()
            kotlin.jvm.internal.Ref$BooleanRef r1 = new kotlin.jvm.internal.Ref$BooleanRef
            r1.<init>()
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$Companion r6 = com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl.Companion     // Catch: java.lang.Exception -> L78
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl r7 = r9.this$0     // Catch: java.lang.Exception -> L78
            com.android.systemui.log.LogBuffer r7 = r7.logBuffer     // Catch: java.lang.Exception -> L78
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$satelliteProvisioned$1$1 r8 = new kotlin.jvm.functions.Function1() { // from class: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$satelliteProvisioned$1.1
                static {
                    /*
                        com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$satelliteProvisioned$1$1 r0 = new com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$satelliteProvisioned$1$1
                        r0.<init>()
                        
                        // error: 0x0005: SPUT 
  (r0 I:com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$satelliteProvisioned$1$1)
 com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$satelliteProvisioned$1.1.INSTANCE com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$satelliteProvisioned$1$1
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$satelliteProvisioned$1.AnonymousClass1.<clinit>():void");
                }

                {
                    /*
                        r1 = this;
                        r0 = 1
                        r1.<init>(r0)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$satelliteProvisioned$1.AnonymousClass1.<init>():void");
                }

                @Override // kotlin.jvm.functions.Function1
                public final /* bridge */ /* synthetic */ java.lang.Object invoke(java.lang.Object r1) {
                    /*
                        r0 = this;
                        com.android.systemui.log.core.LogMessage r1 = (com.android.systemui.log.core.LogMessage) r1
                        java.lang.String r0 = "registerForProvisionStateChanged"
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$satelliteProvisioned$1.AnonymousClass1.invoke(java.lang.Object):java.lang.Object");
                }
            }     // Catch: java.lang.Exception -> L78
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl.Companion.i$default(r6, r7, r8)     // Catch: java.lang.Exception -> L78
            android.telephony.satellite.SatelliteManager r6 = r9.$sm     // Catch: java.lang.Exception -> L78
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl r7 = r9.this$0     // Catch: java.lang.Exception -> L78
            kotlinx.coroutines.CoroutineDispatcher r7 = r7.bgDispatcher     // Catch: java.lang.Exception -> L78
            java.util.concurrent.Executor r7 = kotlinx.coroutines.ExecutorsKt.asExecutor(r7)     // Catch: java.lang.Exception -> L78
            r6.registerForProvisionStateChanged(r7, r10)     // Catch: java.lang.Exception -> L78
            r1.element = r4     // Catch: java.lang.Exception -> L78
            goto L84
        L78:
            r4 = move-exception
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$Companion r6 = com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl.Companion
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl r7 = r9.this$0
            com.android.systemui.log.LogBuffer r7 = r7.logBuffer
            java.lang.String r8 = "error registering for provisioning state callback"
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl.Companion.access$e(r6, r7, r8, r4)
        L84:
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$satelliteProvisioned$1$2 r4 = new com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$satelliteProvisioned$1$2
            android.telephony.satellite.SatelliteManager r6 = r9.$sm
            r4.<init>()
            r9.L$0 = r3
            r9.L$1 = r3
            r9.label = r2
            java.lang.Object r9 = kotlinx.coroutines.channels.ProduceKt.awaitClose(r5, r4, r9)
            if (r9 != r0) goto L98
            return r0
        L98:
            kotlin.Unit r9 = kotlin.Unit.INSTANCE
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$satelliteProvisioned$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
