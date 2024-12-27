package com.android.systemui.keyguard.ui.binder;

import android.widget.ImageView;
import com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

public final class DeviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$5 extends SuspendLambda implements Function2 {
    final /* synthetic */ ImageView $bgView$inlined;
    final /* synthetic */ String $spanName;
    final /* synthetic */ DeviceEntryIconViewModel $viewModel$inlined;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    boolean Z$0;
    int label;

    public DeviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$5(String str, Continuation continuation, DeviceEntryIconViewModel deviceEntryIconViewModel, ImageView imageView) {
        super(2, continuation);
        this.$spanName = str;
        this.$viewModel$inlined = deviceEntryIconViewModel;
        this.$bgView$inlined = imageView;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DeviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$5 deviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$5 = new DeviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$5(this.$spanName, continuation, this.$viewModel$inlined, this.$bgView$inlined);
        deviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$5.L$0 = obj;
        return deviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$5;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$5) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

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
            java.lang.String r2 = "Coroutines"
            r3 = 1
            if (r1 == 0) goto L25
            if (r1 == r3) goto L13
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L13:
            int r0 = r9.I$0
            boolean r1 = r9.Z$0
            java.lang.Object r3 = r9.L$1
            java.lang.String r3 = (java.lang.String) r3
            java.lang.Object r9 = r9.L$0
            com.android.app.tracing.coroutines.TraceData r9 = (com.android.app.tracing.coroutines.TraceData) r9
            kotlin.ResultKt.throwOnFailure(r10)     // Catch: java.lang.Throwable -> L23
            goto L76
        L23:
            r10 = move-exception
            goto L80
        L25:
            kotlin.ResultKt.throwOnFailure(r10)
            java.lang.Object r10 = r9.L$0
            kotlinx.coroutines.CoroutineScope r10 = (kotlinx.coroutines.CoroutineScope) r10
            java.lang.String r10 = r9.$spanName
            com.android.app.tracing.coroutines.TraceDataThreadLocal r1 = com.android.app.tracing.coroutines.TraceContextElementKt.traceThreadLocal
            java.lang.Object r1 = r1.get()
            com.android.app.tracing.coroutines.TraceData r1 = (com.android.app.tracing.coroutines.TraceData) r1
            boolean r4 = android.os.Trace.isEnabled()
            if (r1 != 0) goto L41
            if (r4 == 0) goto L3f
            goto L41
        L3f:
            java.lang.String r10 = "<none>"
        L41:
            if (r1 == 0) goto L46
            r1.beginSpan(r10)
        L46:
            if (r4 == 0) goto L51
            java.util.concurrent.ThreadLocalRandom r5 = java.util.concurrent.ThreadLocalRandom.current()
            int r5 = r5.nextInt()
            goto L52
        L51:
            r5 = 0
        L52:
            if (r4 == 0) goto L57
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackBegin(r5, r2, r10)
        L57:
            com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel r6 = r9.$viewModel$inlined     // Catch: java.lang.Throwable -> L7c
            kotlinx.coroutines.flow.StateFlow r6 = r6.useBackgroundProtection     // Catch: java.lang.Throwable -> L7c
            com.android.systemui.keyguard.ui.binder.DeviceEntryIconViewBinder$bind$2$1$5$1 r7 = new com.android.systemui.keyguard.ui.binder.DeviceEntryIconViewBinder$bind$2$1$5$1     // Catch: java.lang.Throwable -> L7c
            android.widget.ImageView r8 = r9.$bgView$inlined     // Catch: java.lang.Throwable -> L7c
            r7.<init>()     // Catch: java.lang.Throwable -> L7c
            r9.L$0 = r1     // Catch: java.lang.Throwable -> L7c
            r9.L$1 = r10     // Catch: java.lang.Throwable -> L7c
            r9.Z$0 = r4     // Catch: java.lang.Throwable -> L7c
            r9.I$0 = r5     // Catch: java.lang.Throwable -> L7c
            r9.label = r3     // Catch: java.lang.Throwable -> L7c
            java.lang.Object r9 = r6.collect(r7, r9)     // Catch: java.lang.Throwable -> L7c
            if (r9 != r0) goto L73
            return r0
        L73:
            r9 = r1
            r1 = r4
            r0 = r5
        L76:
            kotlin.KotlinNothingValueException r10 = new kotlin.KotlinNothingValueException     // Catch: java.lang.Throwable -> L23
            r10.<init>()     // Catch: java.lang.Throwable -> L23
            throw r10     // Catch: java.lang.Throwable -> L23
        L7c:
            r10 = move-exception
            r9 = r1
            r1 = r4
            r0 = r5
        L80:
            if (r1 == 0) goto L85
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r2)
        L85:
            if (r9 == 0) goto L8a
            r9.endSpan()
        L8a:
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.binder.DeviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$5.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
