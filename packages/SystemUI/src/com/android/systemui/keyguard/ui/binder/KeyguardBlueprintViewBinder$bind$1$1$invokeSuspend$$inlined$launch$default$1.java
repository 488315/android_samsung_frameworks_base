package com.android.systemui.keyguard.ui.binder;

import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardBlueprintViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardSmartspaceViewModel;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

public final class KeyguardBlueprintViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ KeyguardClockViewModel $clockViewModel$inlined;
    final /* synthetic */ ConstraintLayout $constraintLayout$inlined;
    final /* synthetic */ KeyguardSmartspaceViewModel $smartspaceViewModel$inlined;
    final /* synthetic */ String $spanName;
    final /* synthetic */ KeyguardBlueprintViewModel $viewModel$inlined;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    boolean Z$0;
    int label;

    public KeyguardBlueprintViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$1(String str, Continuation continuation, KeyguardBlueprintViewModel keyguardBlueprintViewModel, KeyguardClockViewModel keyguardClockViewModel, KeyguardSmartspaceViewModel keyguardSmartspaceViewModel, ConstraintLayout constraintLayout) {
        super(2, continuation);
        this.$spanName = str;
        this.$viewModel$inlined = keyguardBlueprintViewModel;
        this.$clockViewModel$inlined = keyguardClockViewModel;
        this.$smartspaceViewModel$inlined = keyguardSmartspaceViewModel;
        this.$constraintLayout$inlined = constraintLayout;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardBlueprintViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$1 keyguardBlueprintViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$1 = new KeyguardBlueprintViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$1(this.$spanName, continuation, this.$viewModel$inlined, this.$clockViewModel$inlined, this.$smartspaceViewModel$inlined, this.$constraintLayout$inlined);
        keyguardBlueprintViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$1.L$0 = obj;
        return keyguardBlueprintViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardBlueprintViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
            goto L82
        L1b:
            r13 = move-exception
            goto L95
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
            com.android.systemui.keyguard.ui.viewmodel.KeyguardBlueprintViewModel r6 = r12.$viewModel$inlined     // Catch: java.lang.Throwable -> L91
            kotlinx.coroutines.flow.StateFlowImpl r6 = r6.blueprint     // Catch: java.lang.Throwable -> L91
            r7 = 0
            kotlinx.coroutines.flow.Flow r6 = com.android.systemui.util.kotlin.FlowKt.pairwise(r6, r7)     // Catch: java.lang.Throwable -> L91
            com.android.systemui.keyguard.ui.binder.KeyguardBlueprintViewBinder$bind$1$1$1$1 r7 = new com.android.systemui.keyguard.ui.binder.KeyguardBlueprintViewBinder$bind$1$1$1$1     // Catch: java.lang.Throwable -> L91
            com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel r8 = r12.$clockViewModel$inlined     // Catch: java.lang.Throwable -> L91
            com.android.systemui.keyguard.ui.viewmodel.KeyguardSmartspaceViewModel r9 = r12.$smartspaceViewModel$inlined     // Catch: java.lang.Throwable -> L91
            com.android.systemui.keyguard.ui.viewmodel.KeyguardBlueprintViewModel r10 = r12.$viewModel$inlined     // Catch: java.lang.Throwable -> L91
            androidx.constraintlayout.widget.ConstraintLayout r11 = r12.$constraintLayout$inlined     // Catch: java.lang.Throwable -> L91
            r7.<init>()     // Catch: java.lang.Throwable -> L91
            r12.L$0 = r1     // Catch: java.lang.Throwable -> L91
            r12.L$1 = r13     // Catch: java.lang.Throwable -> L91
            r12.Z$0 = r4     // Catch: java.lang.Throwable -> L91
            r12.I$0 = r5     // Catch: java.lang.Throwable -> L91
            r12.label = r2     // Catch: java.lang.Throwable -> L91
            java.lang.Object r12 = r6.collect(r7, r12)     // Catch: java.lang.Throwable -> L91
            if (r12 != r0) goto L7f
            return r0
        L7f:
            r12 = r1
            r1 = r4
            r0 = r5
        L82:
            kotlin.Unit r13 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L1b
            if (r1 == 0) goto L89
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r3)
        L89:
            if (r12 == 0) goto L8e
            r12.endSpan()
        L8e:
            kotlin.Unit r12 = kotlin.Unit.INSTANCE
            return r12
        L91:
            r13 = move-exception
            r12 = r1
            r1 = r4
            r0 = r5
        L95:
            if (r1 == 0) goto L9a
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r3)
        L9a:
            if (r12 == 0) goto L9f
            r12.endSpan()
        L9f:
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.binder.KeyguardBlueprintViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
