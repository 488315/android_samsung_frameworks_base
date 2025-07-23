package com.android.systemui.common.ui.view;

import android.os.Handler;
import android.os.Trace;
import android.view.Choreographer;
import android.view.View;
import com.android.app.tracing.coroutines.TrackTracer;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ChoreographerUtilsImpl implements ChoreographerUtils {
    public static final ChoreographerUtilsImpl INSTANCE = new ChoreographerUtilsImpl();
    public static final TrackTracer t = new TrackTracer("ChoreographerUtils", 0, null, 6, null);

    private ChoreographerUtilsImpl() {
    }

    public static Object waitUntilNextDoFrameDoneTraced(final View view, Continuation continuation) {
        final CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        final Choreographer.FrameCallback frameCallback = new Choreographer.FrameCallback() { // from class: com.android.systemui.common.ui.view.ChoreographerUtilsImpl$waitUntilNextDoFrameDoneTraced$2$frameCallback$1
            @Override // android.view.Choreographer.FrameCallback
            public final void doFrame(long j) {
                TrackTracer trackTracer = ChoreographerUtilsImpl.t;
                if (Trace.isEnabled()) {
                    Trace.instantForTrack(trackTracer.traceTag, trackTracer.trackName, "We're in doFrame, waiting for it to end.");
                }
                Handler handler = view.getHandler();
                final CancellableContinuation cancellableContinuation = cancellableContinuationImpl;
                handler.postAtFrontOfQueue(new Runnable() { // from class: com.android.systemui.common.ui.view.ChoreographerUtilsImpl$waitUntilNextDoFrameDoneTraced$2$frameCallback$1.2
                    @Override // java.lang.Runnable
                    public final void run() {
                        TrackTracer trackTracer2 = ChoreographerUtilsImpl.t;
                        if (Trace.isEnabled()) {
                            Trace.instantForTrack(trackTracer2.traceTag, trackTracer2.trackName, "DoFrame ended.");
                        }
                        CancellableContinuation cancellableContinuation2 = CancellableContinuation.this;
                        int i = Result.$r8$clinit;
                        cancellableContinuation2.resumeWith(Unit.INSTANCE);
                    }
                });
            }
        };
        final Function0 function0 = new Function0() { // from class: com.android.systemui.common.ui.view.ChoreographerUtilsImpl$waitUntilNextDoFrameDoneTraced$2$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                TrackTracer trackTracer = ChoreographerUtilsImpl.t;
                if (Trace.isEnabled()) {
                    Trace.instantForTrack(trackTracer.traceTag, trackTracer.trackName, "Waiting for next doFrame");
                }
                final Choreographer choreographer = Choreographer.getInstance();
                final Choreographer.FrameCallback frameCallback2 = frameCallback;
                CancellableContinuation.this.invokeOnCancellation(new Function1() { // from class: com.android.systemui.common.ui.view.ChoreographerUtilsImpl$waitUntilNextDoFrameDoneTraced$2$1.2
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj) {
                        choreographer.removeFrameCallback(frameCallback2);
                        return Unit.INSTANCE;
                    }
                });
                choreographer.postFrameCallback(frameCallback);
                return Unit.INSTANCE;
            }
        };
        INSTANCE.getClass();
        if (view.getHandler().getLooper().isCurrentThread()) {
            function0.invoke();
        } else {
            view.getHandler().postAtFrontOfQueue(new Runnable() { // from class: com.android.systemui.common.ui.view.ChoreographerUtilsImpl$sam$java_lang_Runnable$0
                @Override // java.lang.Runnable
                public final /* synthetic */ void run() {
                    Function0.this.invoke();
                }
            });
        }
        Object result = cancellableContinuationImpl.getResult();
        return result == CoroutineSingletons.COROUTINE_SUSPENDED ? result : Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object waitUntilNextDoFrameDone(android.view.View r7, kotlin.coroutines.jvm.internal.ContinuationImpl r8) {
        /*
            r6 = this;
            boolean r0 = r8 instanceof com.android.systemui.common.ui.view.ChoreographerUtilsImpl$waitUntilNextDoFrameDone$1
            if (r0 == 0) goto L13
            r0 = r8
            com.android.systemui.common.ui.view.ChoreographerUtilsImpl$waitUntilNextDoFrameDone$1 r0 = (com.android.systemui.common.ui.view.ChoreographerUtilsImpl$waitUntilNextDoFrameDone$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.common.ui.view.ChoreographerUtilsImpl$waitUntilNextDoFrameDone$1 r0 = new com.android.systemui.common.ui.view.ChoreographerUtilsImpl$waitUntilNextDoFrameDone$1
            r0.<init>(r6, r8)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r8 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r0.label
            r2 = 1
            if (r1 == 0) goto L39
            if (r1 != r2) goto L31
            int r7 = r0.I$0
            long r1 = r0.J$0
            java.lang.Object r8 = r0.L$0
            java.lang.String r8 = (java.lang.String) r8
            kotlin.ResultKt.throwOnFailure(r6)     // Catch: java.lang.Throwable -> L2f
            goto L67
        L2f:
            r6 = move-exception
            goto L76
        L31:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L39:
            kotlin.ResultKt.throwOnFailure(r6)
            com.android.app.tracing.coroutines.TrackTracer r6 = com.android.systemui.common.ui.view.ChoreographerUtilsImpl.t
            long r3 = r6.traceTag
            java.util.concurrent.ThreadLocalRandom r1 = java.util.concurrent.ThreadLocalRandom.current()
            int r1 = r1.nextInt()
            java.lang.String r5 = "waitUntilNextDoFrameDone"
            java.lang.String r6 = r6.trackName
            android.os.Trace.asyncTraceForTrackBegin(r3, r6, r5, r1)
            com.android.systemui.common.ui.view.ChoreographerUtilsImpl r5 = com.android.systemui.common.ui.view.ChoreographerUtilsImpl.INSTANCE     // Catch: java.lang.Throwable -> L74
            r0.L$0 = r6     // Catch: java.lang.Throwable -> L74
            r0.J$0 = r3     // Catch: java.lang.Throwable -> L74
            r0.I$0 = r1     // Catch: java.lang.Throwable -> L74
            r0.label = r2     // Catch: java.lang.Throwable -> L74
            r5.getClass()     // Catch: java.lang.Throwable -> L74
            java.lang.Object r7 = waitUntilNextDoFrameDoneTraced(r7, r0)     // Catch: java.lang.Throwable -> L74
            if (r7 != r8) goto L64
            return r8
        L64:
            r8 = r6
            r7 = r1
            r1 = r3
        L67:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L2f
            android.os.Trace.asyncTraceForTrackEnd(r1, r8, r7)
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        L6f:
            r8 = r6
            r6 = r7
            r7 = r1
            r1 = r3
            goto L76
        L74:
            r7 = move-exception
            goto L6f
        L76:
            android.os.Trace.asyncTraceForTrackEnd(r1, r8, r7)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.common.ui.view.ChoreographerUtilsImpl.waitUntilNextDoFrameDone(android.view.View, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
