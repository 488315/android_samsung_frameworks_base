package kotlinx.atomicfu;

import kotlinx.atomicfu.TraceBase;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class AtomicFU {
    public static final AtomicRef atomic(Object obj) {
        return new AtomicRef(obj, TraceBase.None.INSTANCE);
    }

    public static final AtomicInt atomic() {
        return new AtomicInt(0, TraceBase.None.INSTANCE);
    }

    public static final AtomicBoolean atomic(boolean z) {
        return new AtomicBoolean(z, TraceBase.None.INSTANCE);
    }
}
