package com.android.systemui.keyguard.ui.binder;

import com.android.systemui.keyguard.ui.SwipeUpAnywhereGestureHandler;
import com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerDependencies;
import com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerViewModel;
import com.android.systemui.statusbar.gesture.TapGestureDetector;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class AlternateBouncerViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ AlternateBouncerDependencies $alternateBouncerDependencies$inlined;
    final /* synthetic */ String $spanName;
    final /* synthetic */ SwipeUpAnywhereGestureHandler $swipeUpAnywhereGestureHandler$inlined;
    final /* synthetic */ TapGestureDetector $tapGestureDetector$inlined;
    final /* synthetic */ AlternateBouncerViewModel $viewModel$inlined;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    boolean Z$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AlternateBouncerViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$1(String str, Continuation continuation, AlternateBouncerViewModel alternateBouncerViewModel, SwipeUpAnywhereGestureHandler swipeUpAnywhereGestureHandler, TapGestureDetector tapGestureDetector, AlternateBouncerDependencies alternateBouncerDependencies) {
        super(2, continuation);
        this.$spanName = str;
        this.$viewModel$inlined = alternateBouncerViewModel;
        this.$swipeUpAnywhereGestureHandler$inlined = swipeUpAnywhereGestureHandler;
        this.$tapGestureDetector$inlined = tapGestureDetector;
        this.$alternateBouncerDependencies$inlined = alternateBouncerDependencies;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AlternateBouncerViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$1 alternateBouncerViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$1 = new AlternateBouncerViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$1(this.$spanName, continuation, this.$viewModel$inlined, this.$swipeUpAnywhereGestureHandler$inlined, this.$tapGestureDetector$inlined, this.$alternateBouncerDependencies$inlined);
        alternateBouncerViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$1.L$0 = obj;
        return alternateBouncerViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AlternateBouncerViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0095  */
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
            goto L8e
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
            com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerViewModel r6 = r12.$viewModel$inlined     // Catch: java.lang.Throwable -> L8a
            kotlinx.coroutines.flow.Flow r7 = r6.registerForDismissGestures     // Catch: java.lang.Throwable -> L8a
            com.android.systemui.keyguard.ui.binder.AlternateBouncerViewBinder$bind$1$1$1$1 r8 = new com.android.systemui.keyguard.ui.binder.AlternateBouncerViewBinder$bind$1$1$1$1     // Catch: java.lang.Throwable -> L8a
            com.android.systemui.keyguard.ui.SwipeUpAnywhereGestureHandler r9 = r12.$swipeUpAnywhereGestureHandler$inlined     // Catch: java.lang.Throwable -> L8a
            com.android.systemui.statusbar.gesture.TapGestureDetector r10 = r12.$tapGestureDetector$inlined     // Catch: java.lang.Throwable -> L8a
            com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerDependencies r11 = r12.$alternateBouncerDependencies$inlined     // Catch: java.lang.Throwable -> L8a
            r8.<init>()     // Catch: java.lang.Throwable -> L8a
            r12.L$0 = r1     // Catch: java.lang.Throwable -> L8a
            r12.L$1 = r13     // Catch: java.lang.Throwable -> L8a
            r12.Z$0 = r4     // Catch: java.lang.Throwable -> L8a
            r12.I$0 = r5     // Catch: java.lang.Throwable -> L8a
            r12.label = r2     // Catch: java.lang.Throwable -> L8a
            java.lang.Object r12 = r7.collect(r8, r12)     // Catch: java.lang.Throwable -> L8a
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
            r13 = move-exception
            r12 = r1
            r1 = r4
            r0 = r5
        L8e:
            if (r1 == 0) goto L93
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r3)
        L93:
            if (r12 == 0) goto L98
            r12.endSpan()
        L98:
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.binder.AlternateBouncerViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
