package com.android.systemui.keyguard.ui.binder;

import com.android.systemui.keyguard.ui.view.DeviceEntryIconView;
import com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel;
import com.android.systemui.statusbar.VibratorHelper;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

public final class DeviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$4 extends SuspendLambda implements Function2 {
    final /* synthetic */ CoroutineScope $applicationScope$inlined;
    final /* synthetic */ String $spanName;
    final /* synthetic */ VibratorHelper $vibratorHelper$inlined;
    final /* synthetic */ DeviceEntryIconView $view$inlined;
    final /* synthetic */ DeviceEntryIconViewModel $viewModel$inlined;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    boolean Z$0;
    int label;

    public DeviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$4(String str, Continuation continuation, DeviceEntryIconViewModel deviceEntryIconViewModel, DeviceEntryIconView deviceEntryIconView, VibratorHelper vibratorHelper, CoroutineScope coroutineScope) {
        super(2, continuation);
        this.$spanName = str;
        this.$viewModel$inlined = deviceEntryIconViewModel;
        this.$view$inlined = deviceEntryIconView;
        this.$vibratorHelper$inlined = vibratorHelper;
        this.$applicationScope$inlined = coroutineScope;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DeviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$4 deviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$4 = new DeviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$4(this.$spanName, continuation, this.$viewModel$inlined, this.$view$inlined, this.$vibratorHelper$inlined, this.$applicationScope$inlined);
        deviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$4.L$0 = obj;
        return deviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$4;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$4) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r13) {
        /*
            r12 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r12.label
            r2 = 1
            java.lang.String r3 = "Coroutines"
            if (r1 == 0) goto L26
            if (r1 != r2) goto L1e
            int r0 = r12.I$0
            boolean r1 = r12.Z$0
            java.lang.Object r2 = r12.L$1
            java.lang.String r2 = (java.lang.String) r2
            java.lang.Object r12 = r12.L$0
            com.android.app.tracing.coroutines.TraceData r12 = (com.android.app.tracing.coroutines.TraceData) r12
            kotlin.ResultKt.throwOnFailure(r13)     // Catch: java.lang.Throwable -> L1b
            goto L7b
        L1b:
            r13 = move-exception
            goto L90
        L1e:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r13 = "call to 'resume' before 'invoke' with coroutine"
            r12.<init>(r13)
            throw r12
        L26:
            kotlin.ResultKt.throwOnFailure(r13)
            java.lang.Object r13 = r12.L$0
            kotlinx.coroutines.CoroutineScope r13 = (kotlinx.coroutines.CoroutineScope) r13
            java.lang.String r13 = r12.$spanName
            com.android.app.tracing.coroutines.TraceDataThreadLocal r1 = com.android.app.tracing.coroutines.TraceContextElementKt.traceThreadLocal
            java.lang.Object r1 = r1.get()
            com.android.app.tracing.coroutines.TraceData r1 = (com.android.app.tracing.coroutines.TraceData) r1
            boolean r4 = android.os.Trace.isEnabled()
            if (r1 != 0) goto L42
            if (r4 == 0) goto L40
            goto L42
        L40:
            java.lang.String r13 = "<none>"
        L42:
            if (r1 == 0) goto L47
            r1.beginSpan(r13)
        L47:
            if (r4 == 0) goto L52
            java.util.concurrent.ThreadLocalRandom r5 = java.util.concurrent.ThreadLocalRandom.current()
            int r5 = r5.nextInt()
            goto L53
        L52:
            r5 = 0
        L53:
            if (r4 == 0) goto L58
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackBegin(r5, r3, r13)
        L58:
            com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel r6 = r12.$viewModel$inlined     // Catch: java.lang.Throwable -> L8e
            kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest r7 = r6.accessibilityDelegateHint     // Catch: java.lang.Throwable -> L8e
            com.android.systemui.keyguard.ui.binder.DeviceEntryIconViewBinder$bind$2$1$4$1 r8 = new com.android.systemui.keyguard.ui.binder.DeviceEntryIconViewBinder$bind$2$1$4$1     // Catch: java.lang.Throwable -> L8e
            com.android.systemui.keyguard.ui.view.DeviceEntryIconView r9 = r12.$view$inlined     // Catch: java.lang.Throwable -> L8e
            com.android.systemui.statusbar.VibratorHelper r10 = r12.$vibratorHelper$inlined     // Catch: java.lang.Throwable -> L8e
            kotlinx.coroutines.CoroutineScope r11 = r12.$applicationScope$inlined     // Catch: java.lang.Throwable -> L8e
            r8.<init>()     // Catch: java.lang.Throwable -> L8e
            r12.L$0 = r1     // Catch: java.lang.Throwable -> L8e
            r12.L$1 = r13     // Catch: java.lang.Throwable -> L8e
            r12.Z$0 = r4     // Catch: java.lang.Throwable -> L8e
            r12.I$0 = r5     // Catch: java.lang.Throwable -> L8e
            r12.label = r2     // Catch: java.lang.Throwable -> L8e
            java.lang.Object r12 = r7.collect(r8, r12)     // Catch: java.lang.Throwable -> L8e
            if (r12 != r0) goto L78
            return r0
        L78:
            r12 = r1
            r1 = r4
            r0 = r5
        L7b:
            kotlin.Unit r13 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L1b
            if (r1 == 0) goto L82
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r3)
        L82:
            if (r12 == 0) goto L87
            r12.endSpan()
        L87:
            kotlin.Unit r12 = kotlin.Unit.INSTANCE
            return r12
        L8a:
            r12 = r1
            r1 = r4
            r0 = r5
            goto L90
        L8e:
            r13 = move-exception
            goto L8a
        L90:
            if (r1 == 0) goto L95
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r3)
        L95:
            if (r12 == 0) goto L9a
            r12.endSpan()
        L9a:
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.binder.DeviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$4.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
