package com.android.systemui.keyguard;

import android.net.Uri;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

public final class CustomizationProvider$delete$$inlined$runBlocking$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ String[] $selectionArgs$inlined;
    final /* synthetic */ String $spanName;
    final /* synthetic */ Uri $uri$inlined;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    boolean Z$0;
    int label;
    final /* synthetic */ CustomizationProvider this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CustomizationProvider$delete$$inlined$runBlocking$1(String str, Continuation continuation, CustomizationProvider customizationProvider, Uri uri, String[] strArr) {
        super(2, continuation);
        this.$spanName = str;
        this.this$0 = customizationProvider;
        this.$uri$inlined = uri;
        this.$selectionArgs$inlined = strArr;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CustomizationProvider$delete$$inlined$runBlocking$1 customizationProvider$delete$$inlined$runBlocking$1 = new CustomizationProvider$delete$$inlined$runBlocking$1(this.$spanName, continuation, this.this$0, this.$uri$inlined, this.$selectionArgs$inlined);
        customizationProvider$delete$$inlined$runBlocking$1.L$0 = obj;
        return customizationProvider$delete$$inlined$runBlocking$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CustomizationProvider$delete$$inlined$runBlocking$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0087  */
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
            if (r1 == 0) goto L25
            if (r1 != r2) goto L1d
            int r0 = r9.I$0
            boolean r1 = r9.Z$0
            java.lang.Object r2 = r9.L$1
            java.lang.String r2 = (java.lang.String) r2
            java.lang.Object r9 = r9.L$0
            com.android.app.tracing.coroutines.TraceData r9 = (com.android.app.tracing.coroutines.TraceData) r9
            kotlin.ResultKt.throwOnFailure(r10)     // Catch: java.lang.Throwable -> L1b
            goto L71
        L1b:
            r10 = move-exception
            goto L80
        L1d:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L25:
            kotlin.ResultKt.throwOnFailure(r10)
            java.lang.Object r10 = r9.L$0
            kotlinx.coroutines.CoroutineScope r10 = (kotlinx.coroutines.CoroutineScope) r10
            java.lang.String r10 = r9.$spanName
            com.android.app.tracing.coroutines.TraceDataThreadLocal r1 = com.android.app.tracing.coroutines.TraceContextElementKt.traceThreadLocal
            java.lang.Object r1 = r1.get()
            com.android.app.tracing.coroutines.TraceData r1 = (com.android.app.tracing.coroutines.TraceData) r1
            boolean r4 = android.os.Trace.isEnabled()
            if (r1 != 0) goto L41
            if (r4 == 0) goto L3f
            goto L41
        L3f:
            java.lang.String r10 = "<none>"
        L41:
            if (r1 == 0) goto L46
            r1.beginSpan(r10)
        L46:
            if (r4 == 0) goto L51
            java.util.concurrent.ThreadLocalRandom r5 = java.util.concurrent.ThreadLocalRandom.current()
            int r5 = r5.nextInt()
            goto L52
        L51:
            r5 = 0
        L52:
            if (r4 == 0) goto L57
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackBegin(r5, r3, r10)
        L57:
            com.android.systemui.keyguard.CustomizationProvider r6 = r9.this$0     // Catch: java.lang.Throwable -> L7c
            android.net.Uri r7 = r9.$uri$inlined     // Catch: java.lang.Throwable -> L7c
            java.lang.String[] r8 = r9.$selectionArgs$inlined     // Catch: java.lang.Throwable -> L7c
            r9.L$0 = r1     // Catch: java.lang.Throwable -> L7c
            r9.L$1 = r10     // Catch: java.lang.Throwable -> L7c
            r9.Z$0 = r4     // Catch: java.lang.Throwable -> L7c
            r9.I$0 = r5     // Catch: java.lang.Throwable -> L7c
            r9.label = r2     // Catch: java.lang.Throwable -> L7c
            java.lang.Object r10 = com.android.systemui.keyguard.CustomizationProvider.access$deleteSelection(r6, r7, r8, r9)     // Catch: java.lang.Throwable -> L7c
            if (r10 != r0) goto L6e
            return r0
        L6e:
            r9 = r1
            r1 = r4
            r0 = r5
        L71:
            if (r1 == 0) goto L76
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r3)
        L76:
            if (r9 == 0) goto L7b
            r9.endSpan()
        L7b:
            return r10
        L7c:
            r10 = move-exception
            r9 = r1
            r1 = r4
            r0 = r5
        L80:
            if (r1 == 0) goto L85
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r3)
        L85:
            if (r9 == 0) goto L8a
            r9.endSpan()
        L8a:
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.CustomizationProvider$delete$$inlined$runBlocking$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
