package com.android.systemui.keyguard.ui.binder;

import android.content.Context;
import android.view.View;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardPreviewSmartspaceViewModel;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

public final class KeyguardPreviewSmartspaceViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Context $previewContext$inlined;
    final /* synthetic */ View $smartspace$inlined;
    final /* synthetic */ String $spanName;
    final /* synthetic */ boolean $splitShadePreview$inlined;
    final /* synthetic */ KeyguardPreviewSmartspaceViewModel $viewModel$inlined;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    boolean Z$0;
    int label;

    public KeyguardPreviewSmartspaceViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$1(String str, Continuation continuation, KeyguardPreviewSmartspaceViewModel keyguardPreviewSmartspaceViewModel, boolean z, Context context, View view) {
        super(2, continuation);
        this.$spanName = str;
        this.$viewModel$inlined = keyguardPreviewSmartspaceViewModel;
        this.$splitShadePreview$inlined = z;
        this.$previewContext$inlined = context;
        this.$smartspace$inlined = view;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardPreviewSmartspaceViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$1 keyguardPreviewSmartspaceViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$1 = new KeyguardPreviewSmartspaceViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$1(this.$spanName, continuation, this.$viewModel$inlined, this.$splitShadePreview$inlined, this.$previewContext$inlined, this.$smartspace$inlined);
        keyguardPreviewSmartspaceViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$1.L$0 = obj;
        return keyguardPreviewSmartspaceViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardPreviewSmartspaceViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
            java.lang.String r2 = "Coroutines"
            r3 = 1
            if (r1 == 0) goto L26
            if (r1 == r3) goto L13
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r13 = "call to 'resume' before 'invoke' with coroutine"
            r12.<init>(r13)
            throw r12
        L13:
            int r0 = r12.I$0
            boolean r1 = r12.Z$0
            java.lang.Object r3 = r12.L$1
            java.lang.String r3 = (java.lang.String) r3
            java.lang.Object r12 = r12.L$0
            com.android.app.tracing.coroutines.TraceData r12 = (com.android.app.tracing.coroutines.TraceData) r12
            kotlin.ResultKt.throwOnFailure(r13)     // Catch: java.lang.Throwable -> L23
            goto L7d
        L23:
            r13 = move-exception
            goto L8d
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
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackBegin(r5, r2, r13)
        L58:
            com.android.systemui.keyguard.ui.viewmodel.KeyguardPreviewSmartspaceViewModel r6 = r12.$viewModel$inlined     // Catch: java.lang.Throwable -> L8b
            kotlinx.coroutines.flow.ReadonlyStateFlow r7 = r6.selectedClockSize     // Catch: java.lang.Throwable -> L8b
            com.android.systemui.keyguard.ui.binder.KeyguardPreviewSmartspaceViewBinder$bind$1$1$1$1 r8 = new com.android.systemui.keyguard.ui.binder.KeyguardPreviewSmartspaceViewBinder$bind$1$1$1$1     // Catch: java.lang.Throwable -> L8b
            boolean r9 = r12.$splitShadePreview$inlined     // Catch: java.lang.Throwable -> L8b
            android.content.Context r10 = r12.$previewContext$inlined     // Catch: java.lang.Throwable -> L8b
            android.view.View r11 = r12.$smartspace$inlined     // Catch: java.lang.Throwable -> L8b
            r8.<init>()     // Catch: java.lang.Throwable -> L8b
            r12.L$0 = r1     // Catch: java.lang.Throwable -> L8b
            r12.L$1 = r13     // Catch: java.lang.Throwable -> L8b
            r12.Z$0 = r4     // Catch: java.lang.Throwable -> L8b
            r12.I$0 = r5     // Catch: java.lang.Throwable -> L8b
            r12.label = r3     // Catch: java.lang.Throwable -> L8b
            kotlinx.coroutines.flow.StateFlow r13 = r7.$$delegate_0     // Catch: java.lang.Throwable -> L85
            java.lang.Object r12 = r13.collect(r8, r12)     // Catch: java.lang.Throwable -> L85
            if (r12 != r0) goto L7a
            return r0
        L7a:
            r12 = r1
            r1 = r4
            r0 = r5
        L7d:
            kotlin.KotlinNothingValueException r13 = new kotlin.KotlinNothingValueException     // Catch: java.lang.Throwable -> L23
            r13.<init>()     // Catch: java.lang.Throwable -> L23
            throw r13     // Catch: java.lang.Throwable -> L23
        L83:
            r13 = r12
            goto L87
        L85:
            r12 = move-exception
            goto L83
        L87:
            r12 = r1
            r1 = r4
            r0 = r5
            goto L8d
        L8b:
            r13 = move-exception
            goto L87
        L8d:
            if (r1 == 0) goto L92
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r2)
        L92:
            if (r12 == 0) goto L97
            r12.endSpan()
        L97:
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.binder.KeyguardPreviewSmartspaceViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
