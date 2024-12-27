package com.android.systemui.keyguard.ui.binder;

import android.widget.ImageView;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.VibratorHelper;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ FalsingManager $falsingManager$inlined;
    final /* synthetic */ Function1 $messageDisplayer$inlined;
    final /* synthetic */ String $spanName;
    final /* synthetic */ ImageView $startButton$inlined;
    final /* synthetic */ VibratorHelper $vibratorHelper$inlined;
    final /* synthetic */ KeyguardBottomAreaViewModel $viewModel$inlined;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    boolean Z$0;
    int label;
    final /* synthetic */ KeyguardBottomAreaViewBinder this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$1(String str, Continuation continuation, KeyguardBottomAreaViewModel keyguardBottomAreaViewModel, KeyguardBottomAreaViewBinder keyguardBottomAreaViewBinder, ImageView imageView, FalsingManager falsingManager, Function1 function1, VibratorHelper vibratorHelper) {
        super(2, continuation);
        this.$spanName = str;
        this.$viewModel$inlined = keyguardBottomAreaViewModel;
        this.this$0 = keyguardBottomAreaViewBinder;
        this.$startButton$inlined = imageView;
        this.$falsingManager$inlined = falsingManager;
        this.$messageDisplayer$inlined = function1;
        this.$vibratorHelper$inlined = vibratorHelper;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$1 keyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$1 = new KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$1(this.$spanName, continuation, this.$viewModel$inlined, this.this$0, this.$startButton$inlined, this.$falsingManager$inlined, this.$messageDisplayer$inlined, this.$vibratorHelper$inlined);
        keyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$1.L$0 = obj;
        return keyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x009c  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r15) {
        /*
            r14 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r14.label
            r2 = 1
            java.lang.String r3 = "Coroutines"
            if (r1 == 0) goto L26
            if (r1 != r2) goto L1e
            int r0 = r14.I$0
            boolean r1 = r14.Z$0
            java.lang.Object r2 = r14.L$1
            java.lang.String r2 = (java.lang.String) r2
            java.lang.Object r14 = r14.L$0
            com.android.app.tracing.coroutines.TraceData r14 = (com.android.app.tracing.coroutines.TraceData) r14
            kotlin.ResultKt.throwOnFailure(r15)     // Catch: java.lang.Throwable -> L1b
            goto L80
        L1b:
            r15 = move-exception
            goto L95
        L1e:
            java.lang.IllegalStateException r14 = new java.lang.IllegalStateException
            java.lang.String r15 = "call to 'resume' before 'invoke' with coroutine"
            r14.<init>(r15)
            throw r14
        L26:
            kotlin.ResultKt.throwOnFailure(r15)
            java.lang.Object r15 = r14.L$0
            kotlinx.coroutines.CoroutineScope r15 = (kotlinx.coroutines.CoroutineScope) r15
            java.lang.String r15 = r14.$spanName
            com.android.app.tracing.coroutines.TraceDataThreadLocal r1 = com.android.app.tracing.coroutines.TraceContextElementKt.traceThreadLocal
            java.lang.Object r1 = r1.get()
            com.android.app.tracing.coroutines.TraceData r1 = (com.android.app.tracing.coroutines.TraceData) r1
            boolean r4 = android.os.Trace.isEnabled()
            if (r1 != 0) goto L42
            if (r4 == 0) goto L40
            goto L42
        L40:
            java.lang.String r15 = "<none>"
        L42:
            if (r1 == 0) goto L47
            r1.beginSpan(r15)
        L47:
            if (r4 == 0) goto L52
            java.util.concurrent.ThreadLocalRandom r5 = java.util.concurrent.ThreadLocalRandom.current()
            int r5 = r5.nextInt()
            goto L53
        L52:
            r5 = 0
        L53:
            if (r4 == 0) goto L58
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackBegin(r5, r3, r15)
        L58:
            com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel r6 = r14.$viewModel$inlined     // Catch: java.lang.Throwable -> L93
            kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest r6 = r6.startButton     // Catch: java.lang.Throwable -> L93
            com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$1$1 r13 = new com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$1$1     // Catch: java.lang.Throwable -> L93
            com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder r8 = r14.this$0     // Catch: java.lang.Throwable -> L93
            android.widget.ImageView r9 = r14.$startButton$inlined     // Catch: java.lang.Throwable -> L93
            com.android.systemui.plugins.FalsingManager r10 = r14.$falsingManager$inlined     // Catch: java.lang.Throwable -> L93
            kotlin.jvm.functions.Function1 r11 = r14.$messageDisplayer$inlined     // Catch: java.lang.Throwable -> L93
            com.android.systemui.statusbar.VibratorHelper r12 = r14.$vibratorHelper$inlined     // Catch: java.lang.Throwable -> L93
            r7 = r13
            r7.<init>()     // Catch: java.lang.Throwable -> L93
            r14.L$0 = r1     // Catch: java.lang.Throwable -> L93
            r14.L$1 = r15     // Catch: java.lang.Throwable -> L93
            r14.Z$0 = r4     // Catch: java.lang.Throwable -> L93
            r14.I$0 = r5     // Catch: java.lang.Throwable -> L93
            r14.label = r2     // Catch: java.lang.Throwable -> L93
            java.lang.Object r14 = r6.collect(r13, r14)     // Catch: java.lang.Throwable -> L93
            if (r14 != r0) goto L7d
            return r0
        L7d:
            r14 = r1
            r1 = r4
            r0 = r5
        L80:
            kotlin.Unit r15 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L1b
            if (r1 == 0) goto L87
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r3)
        L87:
            if (r14 == 0) goto L8c
            r14.endSpan()
        L8c:
            kotlin.Unit r14 = kotlin.Unit.INSTANCE
            return r14
        L8f:
            r14 = r1
            r1 = r4
            r0 = r5
            goto L95
        L93:
            r15 = move-exception
            goto L8f
        L95:
            if (r1 == 0) goto L9a
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r3)
        L9a:
            if (r14 == 0) goto L9f
            r14.endSpan()
        L9f:
            throw r15
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
