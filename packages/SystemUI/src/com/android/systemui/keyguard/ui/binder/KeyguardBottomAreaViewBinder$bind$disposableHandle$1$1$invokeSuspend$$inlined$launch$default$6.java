package com.android.systemui.keyguard.ui.binder;

import android.widget.ImageView;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

public final class KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$6 extends SuspendLambda implements Function2 {
    final /* synthetic */ ImageView $endButton$inlined;
    final /* synthetic */ String $spanName;
    final /* synthetic */ KeyguardBottomAreaViewModel $viewModel$inlined;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    boolean Z$0;
    int label;
    final /* synthetic */ KeyguardBottomAreaViewBinder this$0;

    public KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$6(String str, Continuation continuation, KeyguardBottomAreaViewBinder keyguardBottomAreaViewBinder, ImageView imageView, KeyguardBottomAreaViewModel keyguardBottomAreaViewModel) {
        super(2, continuation);
        this.$spanName = str;
        this.this$0 = keyguardBottomAreaViewBinder;
        this.$endButton$inlined = imageView;
        this.$viewModel$inlined = keyguardBottomAreaViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$6 keyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$6 = new KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$6(this.$spanName, continuation, this.this$0, this.$endButton$inlined, this.$viewModel$inlined);
        keyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$6.L$0 = obj;
        return keyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$6;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$6) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
            goto L76
        L1b:
            r11 = move-exception
            goto L8b
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
            com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder r6 = r10.this$0     // Catch: java.lang.Throwable -> L89
            android.widget.ImageView r7 = r10.$endButton$inlined     // Catch: java.lang.Throwable -> L89
            com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel r8 = r10.$viewModel$inlined     // Catch: java.lang.Throwable -> L89
            kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest r9 = r8.endButton     // Catch: java.lang.Throwable -> L89
            kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest r8 = r8.alpha     // Catch: java.lang.Throwable -> L89
            r10.L$0 = r1     // Catch: java.lang.Throwable -> L89
            r10.L$1 = r11     // Catch: java.lang.Throwable -> L89
            r10.Z$0 = r4     // Catch: java.lang.Throwable -> L89
            r10.I$0 = r5     // Catch: java.lang.Throwable -> L89
            r10.label = r2     // Catch: java.lang.Throwable -> L89
            java.lang.Object r10 = com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder.access$updateButtonAlpha(r6, r7, r9, r8, r10)     // Catch: java.lang.Throwable -> L89
            if (r10 != r0) goto L73
            return r0
        L73:
            r10 = r1
            r1 = r4
            r0 = r5
        L76:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L1b
            if (r1 == 0) goto L7d
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r3)
        L7d:
            if (r10 == 0) goto L82
            r10.endSpan()
        L82:
            kotlin.Unit r10 = kotlin.Unit.INSTANCE
            return r10
        L85:
            r10 = r1
            r1 = r4
            r0 = r5
            goto L8b
        L89:
            r11 = move-exception
            goto L85
        L8b:
            if (r1 == 0) goto L90
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r3)
        L90:
            if (r10 == 0) goto L95
            r10.endSpan()
        L95:
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$6.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
