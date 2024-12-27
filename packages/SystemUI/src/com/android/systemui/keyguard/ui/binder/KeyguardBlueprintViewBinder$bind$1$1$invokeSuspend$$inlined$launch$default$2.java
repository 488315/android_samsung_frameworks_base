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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class KeyguardBlueprintViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$2 extends SuspendLambda implements Function2 {
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardBlueprintViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$2(String str, Continuation continuation, KeyguardBlueprintViewModel keyguardBlueprintViewModel, ConstraintLayout constraintLayout, KeyguardClockViewModel keyguardClockViewModel, KeyguardSmartspaceViewModel keyguardSmartspaceViewModel) {
        super(2, continuation);
        this.$spanName = str;
        this.$viewModel$inlined = keyguardBlueprintViewModel;
        this.$constraintLayout$inlined = constraintLayout;
        this.$clockViewModel$inlined = keyguardClockViewModel;
        this.$smartspaceViewModel$inlined = keyguardSmartspaceViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardBlueprintViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$2 keyguardBlueprintViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$2 = new KeyguardBlueprintViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$2(this.$spanName, continuation, this.$viewModel$inlined, this.$constraintLayout$inlined, this.$clockViewModel$inlined, this.$smartspaceViewModel$inlined);
        keyguardBlueprintViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$2.L$0 = obj;
        return keyguardBlueprintViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardBlueprintViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0095  */
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
            goto L7e
        L23:
            r13 = move-exception
            goto L8e
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
            com.android.systemui.keyguard.ui.viewmodel.KeyguardBlueprintViewModel r6 = r12.$viewModel$inlined     // Catch: java.lang.Throwable -> L8c
            kotlinx.coroutines.flow.SharedFlowImpl r7 = r6.refreshTransition     // Catch: java.lang.Throwable -> L8c
            com.android.systemui.keyguard.ui.binder.KeyguardBlueprintViewBinder$bind$1$1$2$1 r8 = new com.android.systemui.keyguard.ui.binder.KeyguardBlueprintViewBinder$bind$1$1$2$1     // Catch: java.lang.Throwable -> L8c
            androidx.constraintlayout.widget.ConstraintLayout r9 = r12.$constraintLayout$inlined     // Catch: java.lang.Throwable -> L8c
            com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel r10 = r12.$clockViewModel$inlined     // Catch: java.lang.Throwable -> L8c
            com.android.systemui.keyguard.ui.viewmodel.KeyguardSmartspaceViewModel r11 = r12.$smartspaceViewModel$inlined     // Catch: java.lang.Throwable -> L8c
            r8.<init>()     // Catch: java.lang.Throwable -> L8c
            r12.L$0 = r1     // Catch: java.lang.Throwable -> L8c
            r12.L$1 = r13     // Catch: java.lang.Throwable -> L8c
            r12.Z$0 = r4     // Catch: java.lang.Throwable -> L8c
            r12.I$0 = r5     // Catch: java.lang.Throwable -> L8c
            r12.label = r3     // Catch: java.lang.Throwable -> L8c
            r7.getClass()     // Catch: java.lang.Throwable -> L86
            kotlin.coroutines.intrinsics.CoroutineSingletons r12 = kotlinx.coroutines.flow.SharedFlowImpl.collect$suspendImpl(r7, r8, r12)     // Catch: java.lang.Throwable -> L86
            if (r12 != r0) goto L7b
            return r0
        L7b:
            r12 = r1
            r1 = r4
            r0 = r5
        L7e:
            kotlin.KotlinNothingValueException r13 = new kotlin.KotlinNothingValueException     // Catch: java.lang.Throwable -> L23
            r13.<init>()     // Catch: java.lang.Throwable -> L23
            throw r13     // Catch: java.lang.Throwable -> L23
        L84:
            r13 = r12
            goto L88
        L86:
            r12 = move-exception
            goto L84
        L88:
            r12 = r1
            r1 = r4
            r0 = r5
            goto L8e
        L8c:
            r13 = move-exception
            goto L88
        L8e:
            if (r1 == 0) goto L93
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r2)
        L93:
            if (r12 == 0) goto L98
            r12.endSpan()
        L98:
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.binder.KeyguardBlueprintViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
