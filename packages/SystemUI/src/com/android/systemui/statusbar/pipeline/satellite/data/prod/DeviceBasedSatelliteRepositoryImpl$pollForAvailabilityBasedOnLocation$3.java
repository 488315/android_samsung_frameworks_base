package com.android.systemui.statusbar.pipeline.satellite.data.prod;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

final class DeviceBasedSatelliteRepositoryImpl$pollForAvailabilityBasedOnLocation$3 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ DeviceBasedSatelliteRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceBasedSatelliteRepositoryImpl$pollForAvailabilityBasedOnLocation$3(DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = deviceBasedSatelliteRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DeviceBasedSatelliteRepositoryImpl$pollForAvailabilityBasedOnLocation$3 deviceBasedSatelliteRepositoryImpl$pollForAvailabilityBasedOnLocation$3 = new DeviceBasedSatelliteRepositoryImpl$pollForAvailabilityBasedOnLocation$3(this.this$0, continuation);
        deviceBasedSatelliteRepositoryImpl$pollForAvailabilityBasedOnLocation$3.Z$0 = ((Boolean) obj).booleanValue();
        return deviceBasedSatelliteRepositoryImpl$pollForAvailabilityBasedOnLocation$3;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((DeviceBasedSatelliteRepositoryImpl$pollForAvailabilityBasedOnLocation$3) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    /* JADX WARN: Removed duplicated region for block: B:12:0x004f A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0043 A[RETURN] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:11:0x004d -> B:6:0x0023). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r7) {
        /*
            r6 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r6.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L1c
            if (r1 == r3) goto L18
            if (r1 != r2) goto L10
            kotlin.ResultKt.throwOnFailure(r7)
            goto L23
        L10:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L18:
            kotlin.ResultKt.throwOnFailure(r7)
            goto L44
        L1c:
            kotlin.ResultKt.throwOnFailure(r7)
            boolean r7 = r6.Z$0
            if (r7 == 0) goto L50
        L23:
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$Companion r7 = com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl.Companion
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl r1 = r6.this$0
            com.android.systemui.log.LogBuffer r1 = r1.logBuffer
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$pollForAvailabilityBasedOnLocation$3$1 r4 = new kotlin.jvm.functions.Function1() { // from class: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$pollForAvailabilityBasedOnLocation$3.1
                static {
                    /*
                        com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$pollForAvailabilityBasedOnLocation$3$1 r0 = new com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$pollForAvailabilityBasedOnLocation$3$1
                        r0.<init>()
                        
                        // error: 0x0005: SPUT 
  (r0 I:com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$pollForAvailabilityBasedOnLocation$3$1)
 com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$pollForAvailabilityBasedOnLocation$3.1.INSTANCE com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$pollForAvailabilityBasedOnLocation$3$1
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$pollForAvailabilityBasedOnLocation$3.AnonymousClass1.<clinit>():void");
                }

                {
                    /*
                        r1 = this;
                        r0 = 1
                        r1.<init>(r0)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$pollForAvailabilityBasedOnLocation$3.AnonymousClass1.<init>():void");
                }

                @Override // kotlin.jvm.functions.Function1
                public final /* bridge */ /* synthetic */ java.lang.Object invoke(java.lang.Object r1) {
                    /*
                        r0 = this;
                        com.android.systemui.log.core.LogMessage r1 = (com.android.systemui.log.core.LogMessage) r1
                        java.lang.String r0 = "requestIsCommunicationAllowedForCurrentLocation"
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$pollForAvailabilityBasedOnLocation$3.AnonymousClass1.invoke(java.lang.Object):java.lang.Object");
                }
            }
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl.Companion.i$default(r7, r1, r4)
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl r7 = r6.this$0
            r6.label = r3
            r7.getClass()
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$checkIsSatelliteAllowed$2 r1 = new com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$checkIsSatelliteAllowed$2
            r4 = 0
            r1.<init>(r7, r4)
            kotlinx.coroutines.CoroutineDispatcher r7 = r7.bgDispatcher
            java.lang.Object r7 = kotlinx.coroutines.BuildersKt.withContext(r7, r1, r6)
            if (r7 != r0) goto L44
            return r0
        L44:
            r6.label = r2
            r4 = 1200000(0x124f80, double:5.92879E-318)
            java.lang.Object r7 = kotlinx.coroutines.DelayKt.delay(r4, r6)
            if (r7 != r0) goto L23
            return r0
        L50:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$pollForAvailabilityBasedOnLocation$3.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
