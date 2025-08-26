package com.android.systemui.common.ui.view;

import android.os.Handler;
import android.os.Trace;
import android.view.Choreographer;
import android.view.View;
import com.android.app.tracing.coroutines.TrackTracer;
import java.util.concurrent.ThreadLocalRandom;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;

/* loaded from: classes.dex */
public final class ChoreographerUtilsImpl implements ChoreographerUtils {
    public static final ChoreographerUtilsImpl INSTANCE = new ChoreographerUtilsImpl();
    public static final TrackTracer t = new TrackTracer("ChoreographerUtils", 0, null, 6, null);

    /* renamed from: com.android.systemui.common.ui.view.ChoreographerUtilsImpl$waitUntilNextDoFrameDone$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        int I$0;
        long J$0;
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ChoreographerUtilsImpl.this.waitUntilNextDoFrameDone(null, this);
        }
    }

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
                        CancellableContinuation cancellableContinuation2 = cancellableContinuation;
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
                cancellableContinuationImpl.invokeOnCancellation(new Function1() { // from class: com.android.systemui.common.ui.view.ChoreographerUtilsImpl$waitUntilNextDoFrameDoneTraced$2$1.2
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
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
                    function0.invoke();
                }
            });
        }
        Object result = cancellableContinuationImpl.getResult();
        return result == CoroutineSingletons.COROUTINE_SUSPENDED ? result : Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object waitUntilNextDoFrameDone(View view, ContinuationImpl continuationImpl) throws Throwable {
        AnonymousClass1 anonymousClass1;
        String str;
        Throwable th;
        int i;
        long j;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i2 = anonymousClass1.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i2 - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = anonymousClass1.label;
        if (i3 == 0) {
            ResultKt.throwOnFailure(obj);
            TrackTracer trackTracer = t;
            long j2 = trackTracer.traceTag;
            int iNextInt = ThreadLocalRandom.current().nextInt();
            String str2 = trackTracer.trackName;
            Trace.asyncTraceForTrackBegin(j2, str2, "waitUntilNextDoFrameDone", iNextInt);
            try {
                ChoreographerUtilsImpl choreographerUtilsImpl = INSTANCE;
                anonymousClass1.L$0 = str2;
                anonymousClass1.J$0 = j2;
                anonymousClass1.I$0 = iNextInt;
                anonymousClass1.label = 1;
                choreographerUtilsImpl.getClass();
                if (waitUntilNextDoFrameDoneTraced(view, anonymousClass1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                str = str2;
                i = iNextInt;
                j = j2;
            } catch (Throwable th2) {
                str = str2;
                th = th2;
                i = iNextInt;
                j = j2;
                Trace.asyncTraceForTrackEnd(j, str, i);
                throw th;
            }
        } else {
            if (i3 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i = anonymousClass1.I$0;
            j = anonymousClass1.J$0;
            str = (String) anonymousClass1.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (Throwable th3) {
                th = th3;
                Trace.asyncTraceForTrackEnd(j, str, i);
                throw th;
            }
        }
        Unit unit = Unit.INSTANCE;
        Trace.asyncTraceForTrackEnd(j, str, i);
        return Unit.INSTANCE;
    }
}
