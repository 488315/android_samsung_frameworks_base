package com.android.systemui.keyguard.ui.binder;

import com.android.systemui.keyguard.ui.view.DeviceEntryIconView;
import com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class DeviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$7 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $spanName;
    final /* synthetic */ DeviceEntryIconView $view$inlined;
    final /* synthetic */ DeviceEntryIconViewModel $viewModel$inlined;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    boolean Z$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$7(String str, Continuation continuation, DeviceEntryIconViewModel deviceEntryIconViewModel, DeviceEntryIconView deviceEntryIconView) {
        super(2, continuation);
        this.$spanName = str;
        this.$viewModel$inlined = deviceEntryIconViewModel;
        this.$view$inlined = deviceEntryIconView;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DeviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$7 deviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$7 = new DeviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$7(this.$spanName, continuation, this.$viewModel$inlined, this.$view$inlined);
        deviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$7.L$0 = obj;
        return deviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$7;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$7) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0099  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r10) {
        /*
            r9 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r9.label
            r2 = 1
            java.lang.String r3 = "Coroutines"
            if (r1 == 0) goto L26
            if (r1 != r2) goto L1e
            int r0 = r9.I$0
            boolean r1 = r9.Z$0
            java.lang.Object r2 = r9.L$1
            java.lang.String r2 = (java.lang.String) r2
            java.lang.Object r9 = r9.L$0
            com.android.app.tracing.coroutines.TraceData r9 = (com.android.app.tracing.coroutines.TraceData) r9
            kotlin.ResultKt.throwOnFailure(r10)     // Catch: java.lang.Throwable -> L1b
            goto L79
        L1b:
            r10 = move-exception
            goto L92
        L1e:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L26:
            kotlin.ResultKt.throwOnFailure(r10)
            java.lang.Object r10 = r9.L$0
            kotlinx.coroutines.CoroutineScope r10 = (kotlinx.coroutines.CoroutineScope) r10
            java.lang.String r10 = r9.$spanName
            com.android.app.tracing.coroutines.TraceDataThreadLocal r1 = com.android.app.tracing.coroutines.TraceContextElementKt.traceThreadLocal
            java.lang.Object r1 = r1.get()
            com.android.app.tracing.coroutines.TraceData r1 = (com.android.app.tracing.coroutines.TraceData) r1
            boolean r4 = android.os.Trace.isEnabled()
            if (r1 != 0) goto L42
            if (r4 == 0) goto L40
            goto L42
        L40:
            java.lang.String r10 = "<none>"
        L42:
            if (r1 == 0) goto L47
            r1.beginSpan(r10)
        L47:
            if (r4 == 0) goto L52
            java.util.concurrent.ThreadLocalRandom r5 = java.util.concurrent.ThreadLocalRandom.current()
            int r5 = r5.nextInt()
            goto L53
        L52:
            r5 = 0
        L53:
            if (r4 == 0) goto L58
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackBegin(r5, r3, r10)
        L58:
            com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel r6 = r9.$viewModel$inlined     // Catch: java.lang.Throwable -> L90
            kotlinx.coroutines.flow.ReadonlyStateFlow r6 = r6.deviceEntryViewAlpha     // Catch: java.lang.Throwable -> L90
            com.android.systemui.keyguard.ui.binder.DeviceEntryIconViewBinder$bind$2$1$7$1 r7 = new com.android.systemui.keyguard.ui.binder.DeviceEntryIconViewBinder$bind$2$1$7$1     // Catch: java.lang.Throwable -> L90
            com.android.systemui.keyguard.ui.view.DeviceEntryIconView r8 = r9.$view$inlined     // Catch: java.lang.Throwable -> L90
            r7.<init>()     // Catch: java.lang.Throwable -> L90
            r9.L$0 = r1     // Catch: java.lang.Throwable -> L90
            r9.L$1 = r10     // Catch: java.lang.Throwable -> L90
            r9.Z$0 = r4     // Catch: java.lang.Throwable -> L90
            r9.I$0 = r5     // Catch: java.lang.Throwable -> L90
            r9.label = r2     // Catch: java.lang.Throwable -> L90
            kotlinx.coroutines.flow.StateFlow r10 = r6.$$delegate_0     // Catch: java.lang.Throwable -> L8a
            java.lang.Object r9 = r10.collect(r7, r9)     // Catch: java.lang.Throwable -> L8a
            if (r9 != r0) goto L76
            return r0
        L76:
            r9 = r1
            r1 = r4
            r0 = r5
        L79:
            kotlin.Unit r10 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L1b
            if (r1 == 0) goto L80
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r3)
        L80:
            if (r9 == 0) goto L85
            r9.endSpan()
        L85:
            kotlin.Unit r9 = kotlin.Unit.INSTANCE
            return r9
        L88:
            r10 = r9
            goto L8c
        L8a:
            r9 = move-exception
            goto L88
        L8c:
            r9 = r1
            r1 = r4
            r0 = r5
            goto L92
        L90:
            r10 = move-exception
            goto L8c
        L92:
            if (r1 == 0) goto L97
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r3)
        L97:
            if (r9 == 0) goto L9c
            r9.endSpan()
        L9c:
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.binder.DeviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$7.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
