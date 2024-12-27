package com.android.systemui.keyguard;

import android.content.ContentValues;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

public final class CustomizationProvider$insert$$inlined$runBlocking$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $spanName;
    final /* synthetic */ ContentValues $values$inlined;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    boolean Z$0;
    int label;
    final /* synthetic */ CustomizationProvider this$0;

    public CustomizationProvider$insert$$inlined$runBlocking$1(String str, Continuation continuation, CustomizationProvider customizationProvider, ContentValues contentValues) {
        super(2, continuation);
        this.$spanName = str;
        this.this$0 = customizationProvider;
        this.$values$inlined = contentValues;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CustomizationProvider$insert$$inlined$runBlocking$1 customizationProvider$insert$$inlined$runBlocking$1 = new CustomizationProvider$insert$$inlined$runBlocking$1(this.$spanName, continuation, this.this$0, this.$values$inlined);
        customizationProvider$insert$$inlined$runBlocking$1.L$0 = obj;
        return customizationProvider$insert$$inlined$runBlocking$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CustomizationProvider$insert$$inlined$runBlocking$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r9) {
        /*
            r8 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r8.label
            r2 = 1
            java.lang.String r3 = "Coroutines"
            if (r1 == 0) goto L25
            if (r1 != r2) goto L1d
            int r0 = r8.I$0
            boolean r1 = r8.Z$0
            java.lang.Object r2 = r8.L$1
            java.lang.String r2 = (java.lang.String) r2
            java.lang.Object r8 = r8.L$0
            com.android.app.tracing.coroutines.TraceData r8 = (com.android.app.tracing.coroutines.TraceData) r8
            kotlin.ResultKt.throwOnFailure(r9)     // Catch: java.lang.Throwable -> L1b
            goto L6f
        L1b:
            r9 = move-exception
            goto L7e
        L1d:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L25:
            kotlin.ResultKt.throwOnFailure(r9)
            java.lang.Object r9 = r8.L$0
            kotlinx.coroutines.CoroutineScope r9 = (kotlinx.coroutines.CoroutineScope) r9
            java.lang.String r9 = r8.$spanName
            com.android.app.tracing.coroutines.TraceDataThreadLocal r1 = com.android.app.tracing.coroutines.TraceContextElementKt.traceThreadLocal
            java.lang.Object r1 = r1.get()
            com.android.app.tracing.coroutines.TraceData r1 = (com.android.app.tracing.coroutines.TraceData) r1
            boolean r4 = android.os.Trace.isEnabled()
            if (r1 != 0) goto L41
            if (r4 == 0) goto L3f
            goto L41
        L3f:
            java.lang.String r9 = "<none>"
        L41:
            if (r1 == 0) goto L46
            r1.beginSpan(r9)
        L46:
            if (r4 == 0) goto L51
            java.util.concurrent.ThreadLocalRandom r5 = java.util.concurrent.ThreadLocalRandom.current()
            int r5 = r5.nextInt()
            goto L52
        L51:
            r5 = 0
        L52:
            if (r4 == 0) goto L57
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackBegin(r5, r3, r9)
        L57:
            com.android.systemui.keyguard.CustomizationProvider r6 = r8.this$0     // Catch: java.lang.Throwable -> L7a
            android.content.ContentValues r7 = r8.$values$inlined     // Catch: java.lang.Throwable -> L7a
            r8.L$0 = r1     // Catch: java.lang.Throwable -> L7a
            r8.L$1 = r9     // Catch: java.lang.Throwable -> L7a
            r8.Z$0 = r4     // Catch: java.lang.Throwable -> L7a
            r8.I$0 = r5     // Catch: java.lang.Throwable -> L7a
            r8.label = r2     // Catch: java.lang.Throwable -> L7a
            java.lang.Object r9 = com.android.systemui.keyguard.CustomizationProvider.access$insertSelection(r6, r7, r8)     // Catch: java.lang.Throwable -> L7a
            if (r9 != r0) goto L6c
            return r0
        L6c:
            r8 = r1
            r1 = r4
            r0 = r5
        L6f:
            if (r1 == 0) goto L74
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r3)
        L74:
            if (r8 == 0) goto L79
            r8.endSpan()
        L79:
            return r9
        L7a:
            r9 = move-exception
            r8 = r1
            r1 = r4
            r0 = r5
        L7e:
            if (r1 == 0) goto L83
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r3)
        L83:
            if (r8 == 0) goto L88
            r8.endSpan()
        L88:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.CustomizationProvider$insert$$inlined$runBlocking$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
