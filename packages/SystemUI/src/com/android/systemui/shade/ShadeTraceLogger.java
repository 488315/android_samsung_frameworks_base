package com.android.systemui.shade;

import com.android.app.tracing.coroutines.TrackTracer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ShadeTraceLogger {
    public static final ShadeTraceLogger INSTANCE = new ShadeTraceLogger();
    public static final TrackTracer t = new TrackTracer("ShadeTraceLogger", 0, "shade", 2, null);

    private ShadeTraceLogger() {
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x0086, code lost:
    
        if (r7.mo779invoke(r0) == r8) goto L35;
     */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object traceReparenting(kotlin.jvm.functions.Function1 r7, kotlin.coroutines.jvm.internal.ContinuationImpl r8) {
        /*
            r6 = this;
            boolean r0 = r8 instanceof com.android.systemui.shade.ShadeTraceLogger$traceReparenting$1
            if (r0 == 0) goto L13
            r0 = r8
            com.android.systemui.shade.ShadeTraceLogger$traceReparenting$1 r0 = (com.android.systemui.shade.ShadeTraceLogger$traceReparenting$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.shade.ShadeTraceLogger$traceReparenting$1 r0 = new com.android.systemui.shade.ShadeTraceLogger$traceReparenting$1
            r0.<init>(r6, r8)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r8 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r0.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L40
            if (r1 == r3) goto L32
            if (r1 != r2) goto L2a
            kotlin.ResultKt.throwOnFailure(r6)
            goto L89
        L2a:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L32:
            int r7 = r0.I$0
            long r1 = r0.J$0
            java.lang.Object r8 = r0.L$0
            java.lang.String r8 = (java.lang.String) r8
            kotlin.ResultKt.throwOnFailure(r6)     // Catch: java.lang.Throwable -> L3e
            goto L71
        L3e:
            r6 = move-exception
            goto L7c
        L40:
            kotlin.ResultKt.throwOnFailure(r6)
            com.android.app.tracing.coroutines.TrackTracer r6 = com.android.systemui.shade.ShadeTraceLogger.t
            long r4 = r6.traceTag
            boolean r1 = android.os.Trace.isEnabled()
            if (r1 == 0) goto L80
            int r1 = com.android.app.tracing.TraceUtils.$r8$clinit
            java.util.concurrent.ThreadLocalRandom r1 = java.util.concurrent.ThreadLocalRandom.current()
            int r1 = r1.nextInt()
            java.lang.String r2 = "reparenting"
            java.lang.String r6 = r6.trackName
            android.os.Trace.asyncTraceForTrackBegin(r4, r6, r2, r1)
            r0.L$0 = r6     // Catch: java.lang.Throwable -> L77
            r0.J$0 = r4     // Catch: java.lang.Throwable -> L77
            r0.I$0 = r1     // Catch: java.lang.Throwable -> L77
            r0.label = r3     // Catch: java.lang.Throwable -> L77
            java.lang.Object r7 = r7.mo779invoke(r0)     // Catch: java.lang.Throwable -> L77
            if (r7 != r8) goto L6e
            goto L88
        L6e:
            r8 = r6
            r7 = r1
            r1 = r4
        L71:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L3e
            android.os.Trace.asyncTraceForTrackEnd(r1, r8, r7)
            goto L89
        L77:
            r7 = move-exception
            r8 = r6
            r6 = r7
            r7 = r1
            r1 = r4
        L7c:
            android.os.Trace.asyncTraceForTrackEnd(r1, r8, r7)
            throw r6
        L80:
            r0.label = r2
            java.lang.Object r6 = r7.mo779invoke(r0)
            if (r6 != r8) goto L89
        L88:
            return r8
        L89:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.ShadeTraceLogger.traceReparenting(kotlin.jvm.functions.Function1, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
