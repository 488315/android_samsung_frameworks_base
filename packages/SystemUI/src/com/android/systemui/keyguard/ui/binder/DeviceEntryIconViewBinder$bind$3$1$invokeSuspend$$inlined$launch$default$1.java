package com.android.systemui.keyguard.ui.binder;

import android.widget.ImageView;
import androidx.compose.ui.graphics.Color;
import com.android.systemui.keyguard.ui.view.DeviceEntryIconView;
import com.android.systemui.keyguard.ui.viewmodel.DeviceEntryForegroundViewModel;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class DeviceEntryIconViewBinder$bind$3$1$invokeSuspend$$inlined$launch$default$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ImageView $fgIconView$inlined;
    final /* synthetic */ DeviceEntryForegroundViewModel $fgViewModel$inlined;
    final /* synthetic */ Color $overrideColor$inlined;
    final /* synthetic */ String $spanName;
    final /* synthetic */ DeviceEntryIconView $view$inlined;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    boolean Z$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceEntryIconViewBinder$bind$3$1$invokeSuspend$$inlined$launch$default$1(String str, Continuation continuation, DeviceEntryForegroundViewModel deviceEntryForegroundViewModel, ImageView imageView, DeviceEntryIconView deviceEntryIconView, Color color) {
        super(2, continuation);
        this.$spanName = str;
        this.$fgViewModel$inlined = deviceEntryForegroundViewModel;
        this.$fgIconView$inlined = imageView;
        this.$view$inlined = deviceEntryIconView;
        this.$overrideColor$inlined = color;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DeviceEntryIconViewBinder$bind$3$1$invokeSuspend$$inlined$launch$default$1 deviceEntryIconViewBinder$bind$3$1$invokeSuspend$$inlined$launch$default$1 = new DeviceEntryIconViewBinder$bind$3$1$invokeSuspend$$inlined$launch$default$1(this.$spanName, continuation, this.$fgViewModel$inlined, this.$fgIconView$inlined, this.$view$inlined, this.$overrideColor$inlined);
        deviceEntryIconViewBinder$bind$3$1$invokeSuspend$$inlined$launch$default$1.L$0 = obj;
        return deviceEntryIconViewBinder$bind$3$1$invokeSuspend$$inlined$launch$default$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceEntryIconViewBinder$bind$3$1$invokeSuspend$$inlined$launch$default$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0092  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0097  */
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
            goto L7b
        L1b:
            r12 = move-exception
            goto L90
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
            com.android.systemui.keyguard.ui.viewmodel.DeviceEntryForegroundViewModel r6 = r11.$fgViewModel$inlined     // Catch: java.lang.Throwable -> L8e
            kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 r6 = r6.viewModel     // Catch: java.lang.Throwable -> L8e
            com.android.systemui.keyguard.ui.binder.DeviceEntryIconViewBinder$bind$3$1$1$1 r7 = new com.android.systemui.keyguard.ui.binder.DeviceEntryIconViewBinder$bind$3$1$1$1     // Catch: java.lang.Throwable -> L8e
            android.widget.ImageView r8 = r11.$fgIconView$inlined     // Catch: java.lang.Throwable -> L8e
            com.android.systemui.keyguard.ui.view.DeviceEntryIconView r9 = r11.$view$inlined     // Catch: java.lang.Throwable -> L8e
            androidx.compose.ui.graphics.Color r10 = r11.$overrideColor$inlined     // Catch: java.lang.Throwable -> L8e
            r7.<init>()     // Catch: java.lang.Throwable -> L8e
            r11.L$0 = r1     // Catch: java.lang.Throwable -> L8e
            r11.L$1 = r12     // Catch: java.lang.Throwable -> L8e
            r11.Z$0 = r4     // Catch: java.lang.Throwable -> L8e
            r11.I$0 = r5     // Catch: java.lang.Throwable -> L8e
            r11.label = r2     // Catch: java.lang.Throwable -> L8e
            java.lang.Object r11 = r6.collect(r7, r11)     // Catch: java.lang.Throwable -> L8e
            if (r11 != r0) goto L78
            return r0
        L78:
            r11 = r1
            r1 = r4
            r0 = r5
        L7b:
            kotlin.Unit r12 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L1b
            if (r1 == 0) goto L82
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r3)
        L82:
            if (r11 == 0) goto L87
            r11.endSpan()
        L87:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        L8a:
            r11 = r1
            r1 = r4
            r0 = r5
            goto L90
        L8e:
            r12 = move-exception
            goto L8a
        L90:
            if (r1 == 0) goto L95
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r3)
        L95:
            if (r11 == 0) goto L9a
            r11.endSpan()
        L9a:
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.binder.DeviceEntryIconViewBinder$bind$3$1$invokeSuspend$$inlined$launch$default$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
