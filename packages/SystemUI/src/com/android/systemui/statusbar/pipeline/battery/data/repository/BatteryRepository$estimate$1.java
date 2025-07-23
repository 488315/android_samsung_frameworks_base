package com.android.systemui.statusbar.pipeline.battery.data.repository;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class BatteryRepository$estimate$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ BatteryRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BatteryRepository$estimate$1(BatteryRepository batteryRepository, Continuation continuation) {
        super(2, continuation);
        this.this$0 = batteryRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        BatteryRepository$estimate$1 batteryRepository$estimate$1 = new BatteryRepository$estimate$1(this.this$0, continuation);
        batteryRepository$estimate$1.L$0 = obj;
        return batteryRepository$estimate$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BatteryRepository$estimate$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0076, code lost:
    
        if (r10 == r0) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0083, code lost:
    
        if (r1.emit((java.lang.String) r10, r9) == r0) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0096, code lost:
    
        if (kotlinx.coroutines.DelayKt.m3449delayVtjQ1oo(r5, r9) == r0) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0098, code lost:
    
        return r0;
     */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:20:0x0096 -> B:7:0x0037). Please report as a decompilation issue!!! */
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
            r2 = 1
            r3 = 3
            r4 = 2
            if (r1 == 0) goto L2f
            if (r1 == r2) goto L27
            if (r1 == r4) goto L1f
            if (r1 != r3) goto L17
            java.lang.Object r1 = r9.L$0
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            kotlin.ResultKt.throwOnFailure(r10)
            goto L37
        L17:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L1f:
            java.lang.Object r1 = r9.L$0
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            kotlin.ResultKt.throwOnFailure(r10)
            goto L86
        L27:
            java.lang.Object r1 = r9.L$0
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            kotlin.ResultKt.throwOnFailure(r10)
            goto L79
        L2f:
            kotlin.ResultKt.throwOnFailure(r10)
            java.lang.Object r10 = r9.L$0
            kotlinx.coroutines.flow.FlowCollector r10 = (kotlinx.coroutines.flow.FlowCollector) r10
            r1 = r10
        L37:
            com.android.systemui.statusbar.pipeline.battery.data.repository.BatteryRepository r10 = r9.this$0
            r9.L$0 = r1
            r9.label = r2
            r10.getClass()
            kotlinx.coroutines.CancellableContinuationImpl r5 = new kotlinx.coroutines.CancellableContinuationImpl
            kotlin.coroutines.Continuation r6 = kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt.intercepted(r9)
            r5.<init>(r6, r2)
            r5.initCancellability()
            com.android.systemui.statusbar.pipeline.battery.data.repository.BatteryRepository$fetchEstimate$2$callback$1 r6 = new com.android.systemui.statusbar.pipeline.battery.data.repository.BatteryRepository$fetchEstimate$2$callback$1
            r6.<init>(r5)
            com.android.systemui.statusbar.policy.BatteryController r10 = r10.controller
            com.android.systemui.statusbar.policy.BatteryControllerImpl r10 = (com.android.systemui.statusbar.policy.BatteryControllerImpl) r10
            java.util.ArrayList r7 = r10.mFetchCallbacks
            monitor-enter(r7)
            java.util.ArrayList r8 = r10.mFetchCallbacks     // Catch: java.lang.Throwable -> L99
            r8.add(r6)     // Catch: java.lang.Throwable -> L99
            monitor-exit(r7)     // Catch: java.lang.Throwable -> L99
            boolean r6 = r10.mFetchingEstimate
            if (r6 == 0) goto L63
            goto L70
        L63:
            r10.mFetchingEstimate = r2
            android.os.Handler r6 = r10.mBgHandler
            com.android.systemui.statusbar.policy.BatteryControllerImpl$$ExternalSyntheticLambda0 r7 = new com.android.systemui.statusbar.policy.BatteryControllerImpl$$ExternalSyntheticLambda0
            r8 = 0
            r7.<init>(r10, r8)
            r6.post(r7)
        L70:
            java.lang.Object r10 = r5.getResult()
            kotlin.coroutines.intrinsics.CoroutineSingletons r5 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            if (r10 != r0) goto L79
            goto L98
        L79:
            java.lang.String r10 = (java.lang.String) r10
            r9.L$0 = r1
            r9.label = r4
            java.lang.Object r10 = r1.emit(r10, r9)
            if (r10 != r0) goto L86
            goto L98
        L86:
            kotlin.time.Duration$Companion r10 = kotlin.time.Duration.Companion
            kotlin.time.DurationUnit r10 = kotlin.time.DurationUnit.MINUTES
            long r5 = kotlin.time.DurationKt.toDuration(r4, r10)
            r9.L$0 = r1
            r9.label = r3
            java.lang.Object r10 = kotlinx.coroutines.DelayKt.m3449delayVtjQ1oo(r5, r9)
            if (r10 != r0) goto L37
        L98:
            return r0
        L99:
            r9 = move-exception
            monitor-exit(r7)     // Catch: java.lang.Throwable -> L99
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.battery.data.repository.BatteryRepository$estimate$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
