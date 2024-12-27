package com.android.systemui.keyguard.ui.binder;

import com.android.systemui.keyguard.ui.view.DeviceEntryIconView;
import com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerUdfpsIconViewModel;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

public final class AlternateBouncerUdfpsViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $spanName;
    final /* synthetic */ DeviceEntryIconView $view$inlined;
    final /* synthetic */ AlternateBouncerUdfpsIconViewModel $viewModel$inlined;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    boolean Z$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AlternateBouncerUdfpsViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$2(String str, Continuation continuation, AlternateBouncerUdfpsIconViewModel alternateBouncerUdfpsIconViewModel, DeviceEntryIconView deviceEntryIconView) {
        super(2, continuation);
        this.$spanName = str;
        this.$viewModel$inlined = alternateBouncerUdfpsIconViewModel;
        this.$view$inlined = deviceEntryIconView;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AlternateBouncerUdfpsViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$2 alternateBouncerUdfpsViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$2 = new AlternateBouncerUdfpsViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$2(this.$spanName, continuation, this.$viewModel$inlined, this.$view$inlined);
        alternateBouncerUdfpsViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$2.L$0 = obj;
        return alternateBouncerUdfpsViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AlternateBouncerUdfpsViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0093  */
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
            java.lang.String r3 = "Coroutines"
            if (r1 == 0) goto L26
            if (r1 != r2) goto L1e
            int r0 = r9.I$0
            boolean r1 = r9.Z$0
            java.lang.Object r2 = r9.L$1
            java.lang.String r2 = (java.lang.String) r2
            java.lang.Object r9 = r9.L$0
            com.android.app.tracing.coroutines.TraceData r9 = (com.android.app.tracing.coroutines.TraceData) r9
            kotlin.ResultKt.throwOnFailure(r10)     // Catch: java.lang.Throwable -> L1b
            goto L77
        L1b:
            r10 = move-exception
            goto L8c
        L1e:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L26:
            kotlin.ResultKt.throwOnFailure(r10)
            java.lang.Object r10 = r9.L$0
            kotlinx.coroutines.CoroutineScope r10 = (kotlinx.coroutines.CoroutineScope) r10
            java.lang.String r10 = r9.$spanName
            com.android.app.tracing.coroutines.TraceDataThreadLocal r1 = com.android.app.tracing.coroutines.TraceContextElementKt.traceThreadLocal
            java.lang.Object r1 = r1.get()
            com.android.app.tracing.coroutines.TraceData r1 = (com.android.app.tracing.coroutines.TraceData) r1
            boolean r4 = android.os.Trace.isEnabled()
            if (r1 != 0) goto L42
            if (r4 == 0) goto L40
            goto L42
        L40:
            java.lang.String r10 = "<none>"
        L42:
            if (r1 == 0) goto L47
            r1.beginSpan(r10)
        L47:
            if (r4 == 0) goto L52
            java.util.concurrent.ThreadLocalRandom r5 = java.util.concurrent.ThreadLocalRandom.current()
            int r5 = r5.nextInt()
            goto L53
        L52:
            r5 = 0
        L53:
            if (r4 == 0) goto L58
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackBegin(r5, r3, r10)
        L58:
            com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerUdfpsIconViewModel r6 = r9.$viewModel$inlined     // Catch: java.lang.Throwable -> L8a
            com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerUdfpsIconViewModel$special$$inlined$map$1 r6 = r6.alpha     // Catch: java.lang.Throwable -> L8a
            com.android.systemui.keyguard.ui.binder.AlternateBouncerUdfpsViewBinder$bind$1$1$2$1 r7 = new com.android.systemui.keyguard.ui.binder.AlternateBouncerUdfpsViewBinder$bind$1$1$2$1     // Catch: java.lang.Throwable -> L8a
            com.android.systemui.keyguard.ui.view.DeviceEntryIconView r8 = r9.$view$inlined     // Catch: java.lang.Throwable -> L8a
            r7.<init>()     // Catch: java.lang.Throwable -> L8a
            r9.L$0 = r1     // Catch: java.lang.Throwable -> L8a
            r9.L$1 = r10     // Catch: java.lang.Throwable -> L8a
            r9.Z$0 = r4     // Catch: java.lang.Throwable -> L8a
            r9.I$0 = r5     // Catch: java.lang.Throwable -> L8a
            r9.label = r2     // Catch: java.lang.Throwable -> L8a
            java.lang.Object r9 = r6.collect(r7, r9)     // Catch: java.lang.Throwable -> L8a
            if (r9 != r0) goto L74
            return r0
        L74:
            r9 = r1
            r1 = r4
            r0 = r5
        L77:
            kotlin.Unit r10 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L1b
            if (r1 == 0) goto L7e
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r3)
        L7e:
            if (r9 == 0) goto L83
            r9.endSpan()
        L83:
            kotlin.Unit r9 = kotlin.Unit.INSTANCE
            return r9
        L86:
            r9 = r1
            r1 = r4
            r0 = r5
            goto L8c
        L8a:
            r10 = move-exception
            goto L86
        L8c:
            if (r1 == 0) goto L91
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r3)
        L91:
            if (r9 == 0) goto L96
            r9.endSpan()
        L96:
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.binder.AlternateBouncerUdfpsViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
