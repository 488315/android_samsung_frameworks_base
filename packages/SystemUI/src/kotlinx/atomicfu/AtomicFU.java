package kotlinx.atomicfu;

import kotlinx.atomicfu.TraceBase;

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
