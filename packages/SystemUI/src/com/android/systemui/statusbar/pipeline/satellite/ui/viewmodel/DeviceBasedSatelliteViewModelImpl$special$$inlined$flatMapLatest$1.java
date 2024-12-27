package com.android.systemui.statusbar.pipeline.satellite.ui.viewmodel;

import com.android.systemui.log.LogBuffer;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.FlowCollector;

public final class DeviceBasedSatelliteViewModelImpl$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ LogBuffer $logBuffer$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceBasedSatelliteViewModelImpl$special$$inlined$flatMapLatest$1(Continuation continuation, LogBuffer logBuffer) {
        super(3, continuation);
        this.$logBuffer$inlined = logBuffer;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        DeviceBasedSatelliteViewModelImpl$special$$inlined$flatMapLatest$1 deviceBasedSatelliteViewModelImpl$special$$inlined$flatMapLatest$1 = new DeviceBasedSatelliteViewModelImpl$special$$inlined$flatMapLatest$1((Continuation) obj3, this.$logBuffer$inlined);
        deviceBasedSatelliteViewModelImpl$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        deviceBasedSatelliteViewModelImpl$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return deviceBasedSatelliteViewModelImpl$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0077 A[RETURN] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r11) {
        /*
            r10 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r10.label
            r2 = 0
            r3 = 2
            r4 = 1
            if (r1 == 0) goto L21
            if (r1 == r4) goto L19
            if (r1 != r3) goto L11
            kotlin.ResultKt.throwOnFailure(r11)
            goto L78
        L11:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L19:
            java.lang.Object r1 = r10.L$0
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            kotlin.ResultKt.throwOnFailure(r11)
            goto L5e
        L21:
            kotlin.ResultKt.throwOnFailure(r11)
            java.lang.Object r11 = r10.L$0
            r1 = r11
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            java.lang.Object r11 = r10.L$1
            java.lang.Boolean r11 = (java.lang.Boolean) r11
            boolean r11 = r11.booleanValue()
            if (r11 == 0) goto L66
            com.android.systemui.log.core.LogLevel r11 = com.android.systemui.log.core.LogLevel.INFO
            com.android.systemui.statusbar.pipeline.satellite.ui.viewmodel.DeviceBasedSatelliteViewModelImpl$shouldShowIconForOosAfterHysteresis$1$2 r5 = new kotlin.jvm.functions.Function1() { // from class: com.android.systemui.statusbar.pipeline.satellite.ui.viewmodel.DeviceBasedSatelliteViewModelImpl$shouldShowIconForOosAfterHysteresis$1$2
                static {
                    /*
                        com.android.systemui.statusbar.pipeline.satellite.ui.viewmodel.DeviceBasedSatelliteViewModelImpl$shouldShowIconForOosAfterHysteresis$1$2 r0 = new com.android.systemui.statusbar.pipeline.satellite.ui.viewmodel.DeviceBasedSatelliteViewModelImpl$shouldShowIconForOosAfterHysteresis$1$2
                        r0.<init>()
                        
                        // error: 0x0005: SPUT 
  (r0 I:com.android.systemui.statusbar.pipeline.satellite.ui.viewmodel.DeviceBasedSatelliteViewModelImpl$shouldShowIconForOosAfterHysteresis$1$2)
 com.android.systemui.statusbar.pipeline.satellite.ui.viewmodel.DeviceBasedSatelliteViewModelImpl$shouldShowIconForOosAfterHysteresis$1$2.INSTANCE com.android.systemui.statusbar.pipeline.satellite.ui.viewmodel.DeviceBasedSatelliteViewModelImpl$shouldShowIconForOosAfterHysteresis$1$2
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.satellite.ui.viewmodel.DeviceBasedSatelliteViewModelImpl$shouldShowIconForOosAfterHysteresis$1$2.<clinit>():void");
                }

                {
                    /*
                        r1 = this;
                        r0 = 1
                        r1.<init>(r0)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.satellite.ui.viewmodel.DeviceBasedSatelliteViewModelImpl$shouldShowIconForOosAfterHysteresis$1$2.<init>():void");
                }

                @Override // kotlin.jvm.functions.Function1
                public final java.lang.Object invoke(java.lang.Object r3) {
                    /*
                        r2 = this;
                        com.android.systemui.log.core.LogMessage r3 = (com.android.systemui.log.core.LogMessage) r3
                        long r2 = r3.getLong1()
                        java.lang.StringBuilder r0 = new java.lang.StringBuilder
                        java.lang.String r1 = "Waiting "
                        r0.<init>(r1)
                        r0.append(r2)
                        java.lang.String r2 = " seconds before showing the satellite icon"
                        r0.append(r2)
                        java.lang.String r2 = r0.toString()
                        return r2
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.satellite.ui.viewmodel.DeviceBasedSatelliteViewModelImpl$shouldShowIconForOosAfterHysteresis$1$2.invoke(java.lang.Object):java.lang.Object");
                }
            }
            com.android.systemui.log.LogBuffer r6 = r10.$logBuffer$inlined
            java.lang.String r7 = "DeviceBasedSatelliteViewModel"
            com.android.systemui.log.core.LogMessage r11 = r6.obtain(r7, r11, r5, r2)
            long r5 = com.android.systemui.statusbar.pipeline.satellite.ui.viewmodel.DeviceBasedSatelliteViewModelImpl.DELAY_DURATION
            kotlin.time.Duration$Companion r7 = kotlin.time.Duration.Companion
            kotlin.time.DurationUnit r7 = kotlin.time.DurationUnit.SECONDS
            long r7 = kotlin.time.Duration.m2544toLongimpl(r5, r7)
            r9 = r11
            com.android.systemui.log.LogMessageImpl r9 = (com.android.systemui.log.LogMessageImpl) r9
            r9.long1 = r7
            com.android.systemui.log.LogBuffer r7 = r10.$logBuffer$inlined
            r7.commit(r11)
            r10.L$0 = r1
            r10.label = r4
            java.lang.Object r11 = kotlinx.coroutines.DelayKt.m2546delayVtjQ1oo(r5, r10)
            if (r11 != r0) goto L5e
            return r0
        L5e:
            java.lang.Boolean r11 = java.lang.Boolean.TRUE
            kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 r4 = new kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
            r4.<init>(r11)
            goto L6d
        L66:
            java.lang.Boolean r11 = java.lang.Boolean.FALSE
            kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 r4 = new kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
            r4.<init>(r11)
        L6d:
            r10.L$0 = r2
            r10.label = r3
            java.lang.Object r10 = kotlinx.coroutines.flow.FlowKt.emitAll(r10, r4, r1)
            if (r10 != r0) goto L78
            return r0
        L78:
            kotlin.Unit r10 = kotlin.Unit.INSTANCE
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.satellite.ui.viewmodel.DeviceBasedSatelliteViewModelImpl$special$$inlined$flatMapLatest$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
