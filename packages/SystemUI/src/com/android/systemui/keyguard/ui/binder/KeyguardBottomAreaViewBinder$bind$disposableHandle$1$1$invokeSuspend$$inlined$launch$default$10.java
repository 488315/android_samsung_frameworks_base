package com.android.systemui.keyguard.ui.binder;

import com.android.systemui.animation.view.LaunchableLinearLayout;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel;
import com.android.systemui.statusbar.VibratorHelper;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$10 extends SuspendLambda implements Function2 {
    final /* synthetic */ LaunchableLinearLayout $settingsMenu$inlined;
    final /* synthetic */ String $spanName;
    final /* synthetic */ VibratorHelper $vibratorHelper$inlined;
    final /* synthetic */ KeyguardBottomAreaViewModel $viewModel$inlined;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    boolean Z$0;
    int label;
    final /* synthetic */ KeyguardBottomAreaViewBinder this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$10(String str, Continuation continuation, KeyguardBottomAreaViewModel keyguardBottomAreaViewModel, KeyguardBottomAreaViewBinder keyguardBottomAreaViewBinder, LaunchableLinearLayout launchableLinearLayout, VibratorHelper vibratorHelper) {
        super(2, continuation);
        this.$spanName = str;
        this.$viewModel$inlined = keyguardBottomAreaViewModel;
        this.this$0 = keyguardBottomAreaViewBinder;
        this.$settingsMenu$inlined = launchableLinearLayout;
        this.$vibratorHelper$inlined = vibratorHelper;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$10 keyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$10 = new KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$10(this.$spanName, continuation, this.$viewModel$inlined, this.this$0, this.$settingsMenu$inlined, this.$vibratorHelper$inlined);
        keyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$10.L$0 = obj;
        return keyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$10;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$10) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x009d  */
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
            goto L83
        L1b:
            r13 = move-exception
            goto L96
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
            com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel r6 = r12.$viewModel$inlined     // Catch: java.lang.Throwable -> L92
            com.android.systemui.keyguard.ui.viewmodel.KeyguardSettingsMenuViewModel r6 = r6.settingsMenuViewModel     // Catch: java.lang.Throwable -> L92
            kotlinx.coroutines.flow.ReadonlyStateFlow r6 = r6.isVisible     // Catch: java.lang.Throwable -> L92
            kotlinx.coroutines.flow.Flow r6 = kotlinx.coroutines.flow.FlowKt.distinctUntilChanged(r6)     // Catch: java.lang.Throwable -> L92
            com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$10$1 r7 = new com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$10$1     // Catch: java.lang.Throwable -> L92
            com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder r8 = r12.this$0     // Catch: java.lang.Throwable -> L92
            com.android.systemui.animation.view.LaunchableLinearLayout r9 = r12.$settingsMenu$inlined     // Catch: java.lang.Throwable -> L92
            com.android.systemui.statusbar.VibratorHelper r10 = r12.$vibratorHelper$inlined     // Catch: java.lang.Throwable -> L92
            com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel r11 = r12.$viewModel$inlined     // Catch: java.lang.Throwable -> L92
            r7.<init>()     // Catch: java.lang.Throwable -> L92
            r12.L$0 = r1     // Catch: java.lang.Throwable -> L92
            r12.L$1 = r13     // Catch: java.lang.Throwable -> L92
            r12.Z$0 = r4     // Catch: java.lang.Throwable -> L92
            r12.I$0 = r5     // Catch: java.lang.Throwable -> L92
            r12.label = r2     // Catch: java.lang.Throwable -> L92
            java.lang.Object r12 = r6.collect(r7, r12)     // Catch: java.lang.Throwable -> L92
            if (r12 != r0) goto L80
            return r0
        L80:
            r12 = r1
            r1 = r4
            r0 = r5
        L83:
            kotlin.Unit r13 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L1b
            if (r1 == 0) goto L8a
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r3)
        L8a:
            if (r12 == 0) goto L8f
            r12.endSpan()
        L8f:
            kotlin.Unit r12 = kotlin.Unit.INSTANCE
            return r12
        L92:
            r13 = move-exception
            r12 = r1
            r1 = r4
            r0 = r5
        L96:
            if (r1 == 0) goto L9b
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r3)
        L9b:
            if (r12 == 0) goto La0
            r12.endSpan()
        La0:
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$10.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
