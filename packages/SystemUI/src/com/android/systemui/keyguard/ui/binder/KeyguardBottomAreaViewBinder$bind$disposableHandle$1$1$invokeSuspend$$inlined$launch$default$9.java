package com.android.systemui.keyguard.ui.binder;

import android.widget.ImageView;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.MutableStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$9 extends SuspendLambda implements Function2 {
    final /* synthetic */ MutableStateFlow $configurationBasedDimensions$inlined;
    final /* synthetic */ ImageView $endButton$inlined;
    final /* synthetic */ String $spanName;
    final /* synthetic */ ImageView $startButton$inlined;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    boolean Z$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$9(String str, Continuation continuation, MutableStateFlow mutableStateFlow, ImageView imageView, ImageView imageView2) {
        super(2, continuation);
        this.$spanName = str;
        this.$configurationBasedDimensions$inlined = mutableStateFlow;
        this.$startButton$inlined = imageView;
        this.$endButton$inlined = imageView2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$9 keyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$9 = new KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$9(this.$spanName, continuation, this.$configurationBasedDimensions$inlined, this.$startButton$inlined, this.$endButton$inlined);
        keyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$9.L$0 = obj;
        return keyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$9;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$9) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x008e  */
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
            if (r1 == 0) goto L26
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
            goto L79
        L23:
            r11 = move-exception
            goto L87
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
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackBegin(r5, r2, r11)
        L58:
            kotlinx.coroutines.flow.MutableStateFlow r6 = r10.$configurationBasedDimensions$inlined     // Catch: java.lang.Throwable -> L85
            com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$9$1 r7 = new com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$9$1     // Catch: java.lang.Throwable -> L85
            android.widget.ImageView r8 = r10.$startButton$inlined     // Catch: java.lang.Throwable -> L85
            android.widget.ImageView r9 = r10.$endButton$inlined     // Catch: java.lang.Throwable -> L85
            r7.<init>()     // Catch: java.lang.Throwable -> L85
            r10.L$0 = r1     // Catch: java.lang.Throwable -> L85
            r10.L$1 = r11     // Catch: java.lang.Throwable -> L85
            r10.Z$0 = r4     // Catch: java.lang.Throwable -> L85
            r10.I$0 = r5     // Catch: java.lang.Throwable -> L85
            r10.label = r3     // Catch: java.lang.Throwable -> L85
            kotlinx.coroutines.flow.StateFlowImpl r6 = (kotlinx.coroutines.flow.StateFlowImpl) r6     // Catch: java.lang.Throwable -> L7f
            java.lang.Object r10 = r6.collect(r7, r10)     // Catch: java.lang.Throwable -> L7f
            if (r10 != r0) goto L76
            return r0
        L76:
            r10 = r1
            r1 = r4
            r0 = r5
        L79:
            kotlin.KotlinNothingValueException r11 = new kotlin.KotlinNothingValueException     // Catch: java.lang.Throwable -> L23
            r11.<init>()     // Catch: java.lang.Throwable -> L23
            throw r11     // Catch: java.lang.Throwable -> L23
        L7f:
            r10 = move-exception
            r11 = r10
        L81:
            r10 = r1
            r1 = r4
            r0 = r5
            goto L87
        L85:
            r11 = move-exception
            goto L81
        L87:
            if (r1 == 0) goto L8c
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r2)
        L8c:
            if (r10 == 0) goto L91
            r10.endSpan()
        L91:
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$9.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
