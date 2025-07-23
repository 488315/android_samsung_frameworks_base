package kotlinx.atomicfu;

import kotlinx.atomicfu.TraceBase;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class AtomicFU {
    public static final AtomicRef atomic(Object obj) {
        return new AtomicRef(obj, TraceBase.None.INSTANCE);
    }

    public static final AtomicInt atomic(int i) {
        return new AtomicInt(i, TraceBase.None.INSTANCE);
    }

    public static final AtomicLong atomic(long j) {
        return new AtomicLong(j, TraceBase.None.INSTANCE);
    }

    public static final AtomicBoolean atomic(boolean z) {
        return new AtomicBoolean(z, TraceBase.None.INSTANCE);
    }
}
