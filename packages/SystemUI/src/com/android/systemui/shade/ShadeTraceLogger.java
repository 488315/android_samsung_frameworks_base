package com.android.systemui.shade;

import android.os.Trace;
import com.android.app.tracing.TraceUtils;
import com.android.app.tracing.coroutines.TrackTracer;
import java.util.concurrent.ThreadLocalRandom;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final class ShadeTraceLogger {
    public static final ShadeTraceLogger INSTANCE = new ShadeTraceLogger();
    public static final TrackTracer t = new TrackTracer("ShadeTraceLogger", 0, "shade", 2, null);

    /* renamed from: com.android.systemui.shade.ShadeTraceLogger$traceReparenting$1, reason: invalid class name */
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
            return ShadeTraceLogger.this.traceReparenting(null, this);
        }
    }

    private ShadeTraceLogger() {
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x0086, code lost:
    
        if (r7.mo781invoke(r0) == r8) goto L35;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object traceReparenting(Function1 function1, ContinuationImpl continuationImpl) throws Throwable {
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
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = anonymousClass1.label;
        if (i3 == 0) {
            ResultKt.throwOnFailure(obj);
            TrackTracer trackTracer = t;
            long j2 = trackTracer.traceTag;
            if (Trace.isEnabled()) {
                int i4 = TraceUtils.$r8$clinit;
                int iNextInt = ThreadLocalRandom.current().nextInt();
                String str2 = trackTracer.trackName;
                Trace.asyncTraceForTrackBegin(j2, str2, "reparenting", iNextInt);
                try {
                    anonymousClass1.L$0 = str2;
                    anonymousClass1.J$0 = j2;
                    anonymousClass1.I$0 = iNextInt;
                    anonymousClass1.label = 1;
                    if (function1.mo781invoke(anonymousClass1) != obj2) {
                        str = str2;
                        i = iNextInt;
                        j = j2;
                        Unit unit = Unit.INSTANCE;
                        Trace.asyncTraceForTrackEnd(j, str, i);
                    }
                } catch (Throwable th2) {
                    str = str2;
                    th = th2;
                    i = iNextInt;
                    j = j2;
                    Trace.asyncTraceForTrackEnd(j, str, i);
                    throw th;
                }
            } else {
                anonymousClass1.label = 2;
            }
            return obj2;
        }
        if (i3 == 1) {
            i = anonymousClass1.I$0;
            j = anonymousClass1.J$0;
            str = (String) anonymousClass1.L$0;
            try {
                ResultKt.throwOnFailure(obj);
                Unit unit2 = Unit.INSTANCE;
                Trace.asyncTraceForTrackEnd(j, str, i);
            } catch (Throwable th3) {
                th = th3;
                Trace.asyncTraceForTrackEnd(j, str, i);
                throw th;
            }
        } else {
            if (i3 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
