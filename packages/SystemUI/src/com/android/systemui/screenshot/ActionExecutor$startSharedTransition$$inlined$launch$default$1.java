package com.android.systemui.screenshot;

import android.content.Intent;
import android.os.UserHandle;
import android.util.Pair;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ActionExecutor$startSharedTransition$$inlined$launch$default$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Intent $intent$inlined;
    final /* synthetic */ boolean $overrideTransition$inlined;
    final /* synthetic */ String $spanName;
    final /* synthetic */ UserHandle $user$inlined;
    final /* synthetic */ Pair $windowTransition$inlined;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    boolean Z$0;
    int label;
    final /* synthetic */ ActionExecutor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ActionExecutor$startSharedTransition$$inlined$launch$default$1(String str, Continuation continuation, ActionExecutor actionExecutor, Intent intent, UserHandle userHandle, boolean z, Pair pair) {
        super(2, continuation);
        this.$spanName = str;
        this.this$0 = actionExecutor;
        this.$intent$inlined = intent;
        this.$user$inlined = userHandle;
        this.$overrideTransition$inlined = z;
        this.$windowTransition$inlined = pair;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ActionExecutor$startSharedTransition$$inlined$launch$default$1 actionExecutor$startSharedTransition$$inlined$launch$default$1 = new ActionExecutor$startSharedTransition$$inlined$launch$default$1(this.$spanName, continuation, this.this$0, this.$intent$inlined, this.$user$inlined, this.$overrideTransition$inlined, this.$windowTransition$inlined);
        actionExecutor$startSharedTransition$$inlined$launch$default$1.L$0 = obj;
        return actionExecutor$startSharedTransition$$inlined$launch$default$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ActionExecutor$startSharedTransition$$inlined$launch$default$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
            goto L82
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
            com.android.systemui.screenshot.ActionExecutor r6 = r14.this$0     // Catch: java.lang.Throwable -> L91
            com.android.systemui.screenshot.ActionIntentExecutor r7 = r6.intentExecutor     // Catch: java.lang.Throwable -> L91
            android.content.Intent r8 = r14.$intent$inlined     // Catch: java.lang.Throwable -> L91
            android.os.UserHandle r9 = r14.$user$inlined     // Catch: java.lang.Throwable -> L91
            boolean r10 = r14.$overrideTransition$inlined     // Catch: java.lang.Throwable -> L91
            android.util.Pair r6 = r14.$windowTransition$inlined     // Catch: java.lang.Throwable -> L91
            java.lang.Object r11 = r6.first     // Catch: java.lang.Throwable -> L91
            android.app.ActivityOptions r11 = (android.app.ActivityOptions) r11     // Catch: java.lang.Throwable -> L91
            java.lang.Object r6 = r6.second     // Catch: java.lang.Throwable -> L91
            r12 = r6
            android.app.ExitTransitionCoordinator r12 = (android.app.ExitTransitionCoordinator) r12     // Catch: java.lang.Throwable -> L91
            r14.L$0 = r1     // Catch: java.lang.Throwable -> L91
            r14.L$1 = r15     // Catch: java.lang.Throwable -> L91
            r14.Z$0 = r4     // Catch: java.lang.Throwable -> L91
            r14.I$0 = r5     // Catch: java.lang.Throwable -> L91
            r14.label = r2     // Catch: java.lang.Throwable -> L91
            r13 = r14
            java.lang.Object r14 = r7.launchIntent(r8, r9, r10, r11, r12, r13)     // Catch: java.lang.Throwable -> L91
            if (r14 != r0) goto L7f
            return r0
        L7f:
            r14 = r1
            r1 = r4
            r0 = r5
        L82:
            kotlin.Unit r15 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L1b
            if (r1 == 0) goto L89
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r3)
        L89:
            if (r14 == 0) goto L8e
            r14.endSpan()
        L8e:
            kotlin.Unit r14 = kotlin.Unit.INSTANCE
            return r14
        L91:
            r15 = move-exception
            r14 = r1
            r1 = r4
            r0 = r5
        L95:
            if (r1 == 0) goto L9a
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r3)
        L9a:
            if (r14 == 0) goto L9f
            r14.endSpan()
        L9f:
            throw r15
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenshot.ActionExecutor$startSharedTransition$$inlined$launch$default$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
