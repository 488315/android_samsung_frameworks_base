package com.android.systemui.keyguard.ui.binder;

import com.android.systemui.common.ui.view.LongPressHandlingView;
import com.android.systemui.keyguard.ui.view.DeviceEntryIconView;
import com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

public final class DeviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$3 extends SuspendLambda implements Function2 {
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
    public DeviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$3(String str, Continuation continuation, DeviceEntryIconViewModel deviceEntryIconViewModel, LongPressHandlingView longPressHandlingView, DeviceEntryIconView deviceEntryIconView) {
        super(2, continuation);
        this.$spanName = str;
        this.$viewModel$inlined = deviceEntryIconViewModel;
        this.$longPressHandlingView$inlined = longPressHandlingView;
        this.$view$inlined = deviceEntryIconView;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DeviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$3 deviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$3 = new DeviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$3(this.$spanName, continuation, this.$viewModel$inlined, this.$longPressHandlingView$inlined, this.$view$inlined);
        deviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$3.L$0 = obj;
        return deviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$3;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0089  */
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
            java.lang.String r2 = "Coroutines"
            r3 = 1
            if (r1 == 0) goto L25
            if (r1 == r3) goto L13
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L13:
            int r0 = r10.I$0
            boolean r1 = r10.Z$0
            java.lang.Object r3 = r10.L$1
            java.lang.String r3 = (java.lang.String) r3
            java.lang.Object r10 = r10.L$0
            com.android.app.tracing.coroutines.TraceData r10 = (com.android.app.tracing.coroutines.TraceData) r10
            kotlin.ResultKt.throwOnFailure(r11)     // Catch: java.lang.Throwable -> L23
            goto L78
        L23:
            r11 = move-exception
            goto L82
        L25:
            kotlin.ResultKt.throwOnFailure(r11)
            java.lang.Object r11 = r10.L$0
            kotlinx.coroutines.CoroutineScope r11 = (kotlinx.coroutines.CoroutineScope) r11
            java.lang.String r11 = r10.$spanName
            com.android.app.tracing.coroutines.TraceDataThreadLocal r1 = com.android.app.tracing.coroutines.TraceContextElementKt.traceThreadLocal
            java.lang.Object r1 = r1.get()
            com.android.app.tracing.coroutines.TraceData r1 = (com.android.app.tracing.coroutines.TraceData) r1
            boolean r4 = android.os.Trace.isEnabled()
            if (r1 != 0) goto L41
            if (r4 == 0) goto L3f
            goto L41
        L3f:
            java.lang.String r11 = "<none>"
        L41:
            if (r1 == 0) goto L46
            r1.beginSpan(r11)
        L46:
            if (r4 == 0) goto L51
            java.util.concurrent.ThreadLocalRandom r5 = java.util.concurrent.ThreadLocalRandom.current()
            int r5 = r5.nextInt()
            goto L52
        L51:
            r5 = 0
        L52:
            if (r4 == 0) goto L57
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackBegin(r5, r2, r11)
        L57:
            com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel r6 = r10.$viewModel$inlined     // Catch: java.lang.Throwable -> L7e
            kotlinx.coroutines.flow.StateFlow r6 = r6.isUdfpsSupported     // Catch: java.lang.Throwable -> L7e
            com.android.systemui.keyguard.ui.binder.DeviceEntryIconViewBinder$bind$2$1$3$1 r7 = new com.android.systemui.keyguard.ui.binder.DeviceEntryIconViewBinder$bind$2$1$3$1     // Catch: java.lang.Throwable -> L7e
            com.android.systemui.common.ui.view.LongPressHandlingView r8 = r10.$longPressHandlingView$inlined     // Catch: java.lang.Throwable -> L7e
            com.android.systemui.keyguard.ui.view.DeviceEntryIconView r9 = r10.$view$inlined     // Catch: java.lang.Throwable -> L7e
            r7.<init>()     // Catch: java.lang.Throwable -> L7e
            r10.L$0 = r1     // Catch: java.lang.Throwable -> L7e
            r10.L$1 = r11     // Catch: java.lang.Throwable -> L7e
            r10.Z$0 = r4     // Catch: java.lang.Throwable -> L7e
            r10.I$0 = r5     // Catch: java.lang.Throwable -> L7e
            r10.label = r3     // Catch: java.lang.Throwable -> L7e
            java.lang.Object r10 = r6.collect(r7, r10)     // Catch: java.lang.Throwable -> L7e
            if (r10 != r0) goto L75
            return r0
        L75:
            r10 = r1
            r1 = r4
            r0 = r5
        L78:
            kotlin.KotlinNothingValueException r11 = new kotlin.KotlinNothingValueException     // Catch: java.lang.Throwable -> L23
            r11.<init>()     // Catch: java.lang.Throwable -> L23
            throw r11     // Catch: java.lang.Throwable -> L23
        L7e:
            r11 = move-exception
            r10 = r1
            r1 = r4
            r0 = r5
        L82:
            if (r1 == 0) goto L87
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r2)
        L87:
            if (r10 == 0) goto L8c
            r10.endSpan()
        L8c:
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.binder.DeviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$3.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
