package com.android.systemui.screenshot;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

public final class ScreenshotProxyService$mBinder$1$dismissKeyguard$$inlined$launch$default$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ IOnDoneCallback $callback$inlined;
    final /* synthetic */ String $spanName;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    boolean Z$0;
    int label;
    final /* synthetic */ ScreenshotProxyService this$0;

    public ScreenshotProxyService$mBinder$1$dismissKeyguard$$inlined$launch$default$1(String str, Continuation continuation, ScreenshotProxyService screenshotProxyService, IOnDoneCallback iOnDoneCallback) {
        super(2, continuation);
        this.$spanName = str;
        this.this$0 = screenshotProxyService;
        this.$callback$inlined = iOnDoneCallback;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ScreenshotProxyService$mBinder$1$dismissKeyguard$$inlined$launch$default$1 screenshotProxyService$mBinder$1$dismissKeyguard$$inlined$launch$default$1 = new ScreenshotProxyService$mBinder$1$dismissKeyguard$$inlined$launch$default$1(this.$spanName, continuation, this.this$0, this.$callback$inlined);
        screenshotProxyService$mBinder$1$dismissKeyguard$$inlined$launch$default$1.L$0 = obj;
        return screenshotProxyService$mBinder$1$dismissKeyguard$$inlined$launch$default$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ScreenshotProxyService$mBinder$1$dismissKeyguard$$inlined$launch$default$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

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
            goto L7d
        L1b:
            r10 = move-exception
            goto L96
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
            com.android.systemui.screenshot.ScreenshotProxyService r6 = r9.this$0     // Catch: java.lang.Throwable -> L94
            com.android.systemui.screenshot.IOnDoneCallback r7 = r9.$callback$inlined     // Catch: java.lang.Throwable -> L94
            r9.L$0 = r1     // Catch: java.lang.Throwable -> L94
            r9.L$1 = r10     // Catch: java.lang.Throwable -> L94
            r9.Z$0 = r4     // Catch: java.lang.Throwable -> L94
            r9.I$0 = r5     // Catch: java.lang.Throwable -> L94
            r9.label = r2     // Catch: java.lang.Throwable -> L94
            kotlinx.coroutines.CoroutineDispatcher r10 = r6.mMainDispatcher     // Catch: java.lang.Throwable -> L8e
            com.android.systemui.screenshot.ScreenshotProxyService$executeAfterDismissing$2 r2 = new com.android.systemui.screenshot.ScreenshotProxyService$executeAfterDismissing$2     // Catch: java.lang.Throwable -> L8e
            r8 = 0
            r2.<init>(r6, r7, r8)     // Catch: java.lang.Throwable -> L8e
            java.lang.Object r9 = kotlinx.coroutines.BuildersKt.withContext(r10, r2, r9)     // Catch: java.lang.Throwable -> L8e
            if (r9 != r0) goto L75
            goto L77
        L75:
            kotlin.Unit r9 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L8e
        L77:
            if (r9 != r0) goto L7a
            return r0
        L7a:
            r9 = r1
            r1 = r4
            r0 = r5
        L7d:
            kotlin.Unit r10 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L1b
            if (r1 == 0) goto L84
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r3)
        L84:
            if (r9 == 0) goto L89
            r9.endSpan()
        L89:
            kotlin.Unit r9 = kotlin.Unit.INSTANCE
            return r9
        L8c:
            r10 = r9
            goto L90
        L8e:
            r9 = move-exception
            goto L8c
        L90:
            r9 = r1
            r1 = r4
            r0 = r5
            goto L96
        L94:
            r10 = move-exception
            goto L90
        L96:
            if (r1 == 0) goto L9b
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r3)
        L9b:
            if (r9 == 0) goto La0
            r9.endSpan()
        La0:
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenshot.ScreenshotProxyService$mBinder$1$dismissKeyguard$$inlined$launch$default$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
