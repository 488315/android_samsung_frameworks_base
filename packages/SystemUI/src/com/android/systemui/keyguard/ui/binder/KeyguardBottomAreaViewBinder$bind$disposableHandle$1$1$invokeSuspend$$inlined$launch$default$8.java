package com.android.systemui.keyguard.ui.binder;

import android.view.View;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.MutableStateFlow;

public final class KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$8 extends SuspendLambda implements Function2 {
    final /* synthetic */ View $ambientIndicationArea$inlined;
    final /* synthetic */ MutableStateFlow $configurationBasedDimensions$inlined;
    final /* synthetic */ String $spanName;
    final /* synthetic */ KeyguardBottomAreaViewModel $viewModel$inlined;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    boolean Z$0;
    int label;

    public KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$8(String str, Continuation continuation, MutableStateFlow mutableStateFlow, KeyguardBottomAreaViewModel keyguardBottomAreaViewModel, View view) {
        super(2, continuation);
        this.$spanName = str;
        this.$configurationBasedDimensions$inlined = mutableStateFlow;
        this.$viewModel$inlined = keyguardBottomAreaViewModel;
        this.$ambientIndicationArea$inlined = view;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$8 keyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$8 = new KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$8(this.$spanName, continuation, this.$configurationBasedDimensions$inlined, this.$viewModel$inlined, this.$ambientIndicationArea$inlined);
        keyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$8.L$0 = obj;
        return keyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$8;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$8) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

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
            goto L86
        L1b:
            r11 = move-exception
            goto L9b
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
            kotlinx.coroutines.flow.MutableStateFlow r6 = r10.$configurationBasedDimensions$inlined     // Catch: java.lang.Throwable -> L99
            com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$lambda$9$$inlined$map$1 r7 = new com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$lambda$9$$inlined$map$1     // Catch: java.lang.Throwable -> L99
            r7.<init>()     // Catch: java.lang.Throwable -> L99
            com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$lambda$9$$inlined$flatMapLatest$1 r6 = new com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$lambda$9$$inlined$flatMapLatest$1     // Catch: java.lang.Throwable -> L99
            com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel r8 = r10.$viewModel$inlined     // Catch: java.lang.Throwable -> L99
            r9 = 0
            r6.<init>(r9, r8)     // Catch: java.lang.Throwable -> L99
            kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest r6 = kotlinx.coroutines.flow.FlowKt.transformLatest(r7, r6)     // Catch: java.lang.Throwable -> L99
            com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$8$3 r7 = new com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$8$3     // Catch: java.lang.Throwable -> L99
            android.view.View r8 = r10.$ambientIndicationArea$inlined     // Catch: java.lang.Throwable -> L99
            r7.<init>()     // Catch: java.lang.Throwable -> L99
            r10.L$0 = r1     // Catch: java.lang.Throwable -> L99
            r10.L$1 = r11     // Catch: java.lang.Throwable -> L99
            r10.Z$0 = r4     // Catch: java.lang.Throwable -> L99
            r10.I$0 = r5     // Catch: java.lang.Throwable -> L99
            r10.label = r2     // Catch: java.lang.Throwable -> L99
            java.lang.Object r10 = r6.collect(r7, r10)     // Catch: java.lang.Throwable -> L99
            if (r10 != r0) goto L83
            return r0
        L83:
            r10 = r1
            r1 = r4
            r0 = r5
        L86:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L1b
            if (r1 == 0) goto L8d
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r3)
        L8d:
            if (r10 == 0) goto L92
            r10.endSpan()
        L92:
            kotlin.Unit r10 = kotlin.Unit.INSTANCE
            return r10
        L95:
            r10 = r1
            r1 = r4
            r0 = r5
            goto L9b
        L99:
            r11 = move-exception
            goto L95
        L9b:
            if (r1 == 0) goto La0
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r3)
        La0:
            if (r10 == 0) goto La5
            r10.endSpan()
        La5:
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$8.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
