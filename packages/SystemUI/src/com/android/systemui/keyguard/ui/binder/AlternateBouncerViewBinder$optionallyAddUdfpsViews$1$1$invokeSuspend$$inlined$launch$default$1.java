package com.android.systemui.keyguard.ui.binder;

import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerUdfpsIconViewModel;
import dagger.Lazy;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

public final class AlternateBouncerViewBinder$optionallyAddUdfpsViews$1$1$invokeSuspend$$inlined$launch$default$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $spanName;
    final /* synthetic */ Lazy $udfpsA11yOverlayViewModel$inlined;
    final /* synthetic */ AlternateBouncerUdfpsIconViewModel $udfpsIconViewModel$inlined;
    final /* synthetic */ ConstraintLayout $view$inlined;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    boolean Z$0;
    int label;

    public AlternateBouncerViewBinder$optionallyAddUdfpsViews$1$1$invokeSuspend$$inlined$launch$default$1(String str, Continuation continuation, AlternateBouncerUdfpsIconViewModel alternateBouncerUdfpsIconViewModel, ConstraintLayout constraintLayout, Lazy lazy) {
        super(2, continuation);
        this.$spanName = str;
        this.$udfpsIconViewModel$inlined = alternateBouncerUdfpsIconViewModel;
        this.$view$inlined = constraintLayout;
        this.$udfpsA11yOverlayViewModel$inlined = lazy;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AlternateBouncerViewBinder$optionallyAddUdfpsViews$1$1$invokeSuspend$$inlined$launch$default$1 alternateBouncerViewBinder$optionallyAddUdfpsViews$1$1$invokeSuspend$$inlined$launch$default$1 = new AlternateBouncerViewBinder$optionallyAddUdfpsViews$1$1$invokeSuspend$$inlined$launch$default$1(this.$spanName, continuation, this.$udfpsIconViewModel$inlined, this.$view$inlined, this.$udfpsA11yOverlayViewModel$inlined);
        alternateBouncerViewBinder$optionallyAddUdfpsViews$1$1$invokeSuspend$$inlined$launch$default$1.L$0 = obj;
        return alternateBouncerViewBinder$optionallyAddUdfpsViews$1$1$invokeSuspend$$inlined$launch$default$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AlternateBouncerViewBinder$optionallyAddUdfpsViews$1$1$invokeSuspend$$inlined$launch$default$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r12) {
        /*
            r11 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r11.label
            r2 = 1
            java.lang.String r3 = "Coroutines"
            if (r1 == 0) goto L26
            if (r1 != r2) goto L1e
            int r0 = r11.I$0
            boolean r1 = r11.Z$0
            java.lang.Object r2 = r11.L$1
            java.lang.String r2 = (java.lang.String) r2
            java.lang.Object r11 = r11.L$0
            com.android.app.tracing.coroutines.TraceData r11 = (com.android.app.tracing.coroutines.TraceData) r11
            kotlin.ResultKt.throwOnFailure(r12)     // Catch: java.lang.Throwable -> L1b
            goto L79
        L1b:
            r12 = move-exception
            goto L8e
        L1e:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L26:
            kotlin.ResultKt.throwOnFailure(r12)
            java.lang.Object r12 = r11.L$0
            kotlinx.coroutines.CoroutineScope r12 = (kotlinx.coroutines.CoroutineScope) r12
            java.lang.String r12 = r11.$spanName
            com.android.app.tracing.coroutines.TraceDataThreadLocal r1 = com.android.app.tracing.coroutines.TraceContextElementKt.traceThreadLocal
            java.lang.Object r1 = r1.get()
            com.android.app.tracing.coroutines.TraceData r1 = (com.android.app.tracing.coroutines.TraceData) r1
            boolean r4 = android.os.Trace.isEnabled()
            if (r1 != 0) goto L42
            if (r4 == 0) goto L40
            goto L42
        L40:
            java.lang.String r12 = "<none>"
        L42:
            if (r1 == 0) goto L47
            r1.beginSpan(r12)
        L47:
            if (r4 == 0) goto L52
            java.util.concurrent.ThreadLocalRandom r5 = java.util.concurrent.ThreadLocalRandom.current()
            int r5 = r5.nextInt()
            goto L53
        L52:
            r5 = 0
        L53:
            if (r4 == 0) goto L58
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackBegin(r5, r3, r12)
        L58:
            com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerUdfpsIconViewModel r6 = r11.$udfpsIconViewModel$inlined     // Catch: java.lang.Throwable -> L8c
            kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest r7 = r6.iconLocation     // Catch: java.lang.Throwable -> L8c
            com.android.systemui.keyguard.ui.binder.AlternateBouncerViewBinder$optionallyAddUdfpsViews$1$1$1$1 r8 = new com.android.systemui.keyguard.ui.binder.AlternateBouncerViewBinder$optionallyAddUdfpsViews$1$1$1$1     // Catch: java.lang.Throwable -> L8c
            androidx.constraintlayout.widget.ConstraintLayout r9 = r11.$view$inlined     // Catch: java.lang.Throwable -> L8c
            dagger.Lazy r10 = r11.$udfpsA11yOverlayViewModel$inlined     // Catch: java.lang.Throwable -> L8c
            r8.<init>()     // Catch: java.lang.Throwable -> L8c
            r11.L$0 = r1     // Catch: java.lang.Throwable -> L8c
            r11.L$1 = r12     // Catch: java.lang.Throwable -> L8c
            r11.Z$0 = r4     // Catch: java.lang.Throwable -> L8c
            r11.I$0 = r5     // Catch: java.lang.Throwable -> L8c
            r11.label = r2     // Catch: java.lang.Throwable -> L8c
            java.lang.Object r11 = r7.collect(r8, r11)     // Catch: java.lang.Throwable -> L8c
            if (r11 != r0) goto L76
            return r0
        L76:
            r11 = r1
            r1 = r4
            r0 = r5
        L79:
            kotlin.Unit r12 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L1b
            if (r1 == 0) goto L80
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r3)
        L80:
            if (r11 == 0) goto L85
            r11.endSpan()
        L85:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        L88:
            r11 = r1
            r1 = r4
            r0 = r5
            goto L8e
        L8c:
            r12 = move-exception
            goto L88
        L8e:
            if (r1 == 0) goto L93
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r3)
        L93:
            if (r11 == 0) goto L98
            r11.endSpan()
        L98:
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.binder.AlternateBouncerViewBinder$optionallyAddUdfpsViews$1$1$invokeSuspend$$inlined$launch$default$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
