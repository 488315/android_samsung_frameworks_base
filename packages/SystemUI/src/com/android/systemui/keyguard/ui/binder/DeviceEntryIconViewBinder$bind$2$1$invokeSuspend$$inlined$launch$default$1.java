package com.android.systemui.keyguard.ui.binder;

import com.android.systemui.common.ui.view.LongPressHandlingView;
import com.android.systemui.keyguard.ui.view.DeviceEntryIconView;
import com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

public final class DeviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ LongPressHandlingView $longPressHandlingView$inlined;
    final /* synthetic */ String $spanName;
    final /* synthetic */ DeviceEntryIconView $view$inlined;
    final /* synthetic */ DeviceEntryIconViewModel $viewModel$inlined;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    boolean Z$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$1(String str, Continuation continuation, DeviceEntryIconViewModel deviceEntryIconViewModel, LongPressHandlingView longPressHandlingView, DeviceEntryIconView deviceEntryIconView) {
        super(2, continuation);
        this.$spanName = str;
        this.$viewModel$inlined = deviceEntryIconViewModel;
        this.$longPressHandlingView$inlined = longPressHandlingView;
        this.$view$inlined = deviceEntryIconView;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DeviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$1 deviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$1 = new DeviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$1(this.$spanName, continuation, this.$viewModel$inlined, this.$longPressHandlingView$inlined, this.$view$inlined);
        deviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$1.L$0 = obj;
        return deviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0093  */
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
            r2 = 1
            java.lang.String r3 = "Coroutines"
            if (r1 == 0) goto L26
            if (r1 != r2) goto L1e
            int r0 = r10.I$0
            boolean r1 = r10.Z$0
            java.lang.Object r2 = r10.L$1
            java.lang.String r2 = (java.lang.String) r2
            java.lang.Object r10 = r10.L$0
            com.android.app.tracing.coroutines.TraceData r10 = (com.android.app.tracing.coroutines.TraceData) r10
            kotlin.ResultKt.throwOnFailure(r11)     // Catch: java.lang.Throwable -> L1b
            goto L79
        L1b:
            r11 = move-exception
            goto L8c
        L1e:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L26:
            kotlin.ResultKt.throwOnFailure(r11)
            java.lang.Object r11 = r10.L$0
            kotlinx.coroutines.CoroutineScope r11 = (kotlinx.coroutines.CoroutineScope) r11
            java.lang.String r11 = r10.$spanName
            com.android.app.tracing.coroutines.TraceDataThreadLocal r1 = com.android.app.tracing.coroutines.TraceContextElementKt.traceThreadLocal
            java.lang.Object r1 = r1.get()
            com.android.app.tracing.coroutines.TraceData r1 = (com.android.app.tracing.coroutines.TraceData) r1
            boolean r4 = android.os.Trace.isEnabled()
            if (r1 != 0) goto L42
            if (r4 == 0) goto L40
            goto L42
        L40:
            java.lang.String r11 = "<none>"
        L42:
            if (r1 == 0) goto L47
            r1.beginSpan(r11)
        L47:
            if (r4 == 0) goto L52
            java.util.concurrent.ThreadLocalRandom r5 = java.util.concurrent.ThreadLocalRandom.current()
            int r5 = r5.nextInt()
            goto L53
        L52:
            r5 = 0
        L53:
            if (r4 == 0) goto L58
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackBegin(r5, r3, r11)
        L58:
            com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel r6 = r10.$viewModel$inlined     // Catch: java.lang.Throwable -> L88
            kotlinx.coroutines.flow.Flow r6 = r6.isVisible     // Catch: java.lang.Throwable -> L88
            com.android.systemui.keyguard.ui.binder.DeviceEntryIconViewBinder$bind$2$1$1$1 r7 = new com.android.systemui.keyguard.ui.binder.DeviceEntryIconViewBinder$bind$2$1$1$1     // Catch: java.lang.Throwable -> L88
            com.android.systemui.common.ui.view.LongPressHandlingView r8 = r10.$longPressHandlingView$inlined     // Catch: java.lang.Throwable -> L88
            com.android.systemui.keyguard.ui.view.DeviceEntryIconView r9 = r10.$view$inlined     // Catch: java.lang.Throwable -> L88
            r7.<init>()     // Catch: java.lang.Throwable -> L88
            r10.L$0 = r1     // Catch: java.lang.Throwable -> L88
            r10.L$1 = r11     // Catch: java.lang.Throwable -> L88
            r10.Z$0 = r4     // Catch: java.lang.Throwable -> L88
            r10.I$0 = r5     // Catch: java.lang.Throwable -> L88
            r10.label = r2     // Catch: java.lang.Throwable -> L88
            java.lang.Object r10 = r6.collect(r7, r10)     // Catch: java.lang.Throwable -> L88
            if (r10 != r0) goto L76
            return r0
        L76:
            r10 = r1
            r1 = r4
            r0 = r5
        L79:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L1b
            if (r1 == 0) goto L80
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r3)
        L80:
            if (r10 == 0) goto L85
            r10.endSpan()
        L85:
            kotlin.Unit r10 = kotlin.Unit.INSTANCE
            return r10
        L88:
            r11 = move-exception
            r10 = r1
            r1 = r4
            r0 = r5
        L8c:
            if (r1 == 0) goto L91
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r3)
        L91:
            if (r10 == 0) goto L96
            r10.endSpan()
        L96:
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.binder.DeviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
