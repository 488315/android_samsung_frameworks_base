package com.android.systemui.screenshot;

import android.app.ActivityOptions;
import android.app.ExitTransitionCoordinator;
import android.content.Intent;
import android.os.UserHandle;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

public final class ActionIntentExecutor$launchIntentAsync$$inlined$launch$default$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Intent $intent$inlined;
    final /* synthetic */ ActivityOptions $options$inlined;
    final /* synthetic */ boolean $overrideTransition$inlined;
    final /* synthetic */ String $spanName;
    final /* synthetic */ ExitTransitionCoordinator $transitionCoordinator$inlined;
    final /* synthetic */ UserHandle $user$inlined;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    boolean Z$0;
    int label;
    final /* synthetic */ ActionIntentExecutor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ActionIntentExecutor$launchIntentAsync$$inlined$launch$default$1(String str, Continuation continuation, ActionIntentExecutor actionIntentExecutor, Intent intent, UserHandle userHandle, boolean z, ActivityOptions activityOptions, ExitTransitionCoordinator exitTransitionCoordinator) {
        super(2, continuation);
        this.$spanName = str;
        this.this$0 = actionIntentExecutor;
        this.$intent$inlined = intent;
        this.$user$inlined = userHandle;
        this.$overrideTransition$inlined = z;
        this.$options$inlined = activityOptions;
        this.$transitionCoordinator$inlined = exitTransitionCoordinator;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ActionIntentExecutor$launchIntentAsync$$inlined$launch$default$1 actionIntentExecutor$launchIntentAsync$$inlined$launch$default$1 = new ActionIntentExecutor$launchIntentAsync$$inlined$launch$default$1(this.$spanName, continuation, this.this$0, this.$intent$inlined, this.$user$inlined, this.$overrideTransition$inlined, this.$options$inlined, this.$transitionCoordinator$inlined);
        actionIntentExecutor$launchIntentAsync$$inlined$launch$default$1.L$0 = obj;
        return actionIntentExecutor$launchIntentAsync$$inlined$launch$default$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ActionIntentExecutor$launchIntentAsync$$inlined$launch$default$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0093  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r14) {
        /*
            r13 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r13.label
            r2 = 1
            java.lang.String r3 = "Coroutines"
            if (r1 == 0) goto L26
            if (r1 != r2) goto L1e
            int r0 = r13.I$0
            boolean r1 = r13.Z$0
            java.lang.Object r2 = r13.L$1
            java.lang.String r2 = (java.lang.String) r2
            java.lang.Object r13 = r13.L$0
            com.android.app.tracing.coroutines.TraceData r13 = (com.android.app.tracing.coroutines.TraceData) r13
            kotlin.ResultKt.throwOnFailure(r14)     // Catch: java.lang.Throwable -> L1b
            goto L79
        L1b:
            r14 = move-exception
            goto L8c
        L1e:
            java.lang.IllegalStateException r13 = new java.lang.IllegalStateException
            java.lang.String r14 = "call to 'resume' before 'invoke' with coroutine"
            r13.<init>(r14)
            throw r13
        L26:
            kotlin.ResultKt.throwOnFailure(r14)
            java.lang.Object r14 = r13.L$0
            kotlinx.coroutines.CoroutineScope r14 = (kotlinx.coroutines.CoroutineScope) r14
            java.lang.String r14 = r13.$spanName
            com.android.app.tracing.coroutines.TraceDataThreadLocal r1 = com.android.app.tracing.coroutines.TraceContextElementKt.traceThreadLocal
            java.lang.Object r1 = r1.get()
            com.android.app.tracing.coroutines.TraceData r1 = (com.android.app.tracing.coroutines.TraceData) r1
            boolean r4 = android.os.Trace.isEnabled()
            if (r1 != 0) goto L42
            if (r4 == 0) goto L40
            goto L42
        L40:
            java.lang.String r14 = "<none>"
        L42:
            if (r1 == 0) goto L47
            r1.beginSpan(r14)
        L47:
            if (r4 == 0) goto L52
            java.util.concurrent.ThreadLocalRandom r5 = java.util.concurrent.ThreadLocalRandom.current()
            int r5 = r5.nextInt()
            goto L53
        L52:
            r5 = 0
        L53:
            if (r4 == 0) goto L58
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackBegin(r5, r3, r14)
        L58:
            com.android.systemui.screenshot.ActionIntentExecutor r6 = r13.this$0     // Catch: java.lang.Throwable -> L88
            android.content.Intent r7 = r13.$intent$inlined     // Catch: java.lang.Throwable -> L88
            android.os.UserHandle r8 = r13.$user$inlined     // Catch: java.lang.Throwable -> L88
            boolean r9 = r13.$overrideTransition$inlined     // Catch: java.lang.Throwable -> L88
            android.app.ActivityOptions r10 = r13.$options$inlined     // Catch: java.lang.Throwable -> L88
            android.app.ExitTransitionCoordinator r11 = r13.$transitionCoordinator$inlined     // Catch: java.lang.Throwable -> L88
            r13.L$0 = r1     // Catch: java.lang.Throwable -> L88
            r13.L$1 = r14     // Catch: java.lang.Throwable -> L88
            r13.Z$0 = r4     // Catch: java.lang.Throwable -> L88
            r13.I$0 = r5     // Catch: java.lang.Throwable -> L88
            r13.label = r2     // Catch: java.lang.Throwable -> L88
            r12 = r13
            java.lang.Object r13 = r6.launchIntent(r7, r8, r9, r10, r11, r12)     // Catch: java.lang.Throwable -> L88
            if (r13 != r0) goto L76
            return r0
        L76:
            r13 = r1
            r1 = r4
            r0 = r5
        L79:
            kotlin.Unit r14 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L1b
            if (r1 == 0) goto L80
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r3)
        L80:
            if (r13 == 0) goto L85
            r13.endSpan()
        L85:
            kotlin.Unit r13 = kotlin.Unit.INSTANCE
            return r13
        L88:
            r14 = move-exception
            r13 = r1
            r1 = r4
            r0 = r5
        L8c:
            if (r1 == 0) goto L91
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r3)
        L91:
            if (r13 == 0) goto L96
            r13.endSpan()
        L96:
            throw r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenshot.ActionIntentExecutor$launchIntentAsync$$inlined$launch$default$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
