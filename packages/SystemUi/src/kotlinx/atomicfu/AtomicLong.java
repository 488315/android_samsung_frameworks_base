package kotlinx.atomicfu;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.atomicfu.TraceBase;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class AtomicLong {

    /* renamed from: FU */
    public static final AtomicLongFieldUpdater f664FU;
    public final TraceBase trace;
    public volatile long value;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        f664FU = AtomicLongFieldUpdater.newUpdater(AtomicLong.class, "value");
    }

    public AtomicLong(long j, TraceBase traceBase) {
        this.trace = traceBase;
        this.value = j;
    }

    public final boolean compareAndSet(long j, long j2) {
        boolean compareAndSet = f664FU.compareAndSet(this, j, j2);
        if (compareAndSet) {
            TraceBase.None none = TraceBase.None.INSTANCE;
            TraceBase traceBase = this.trace;
            if (traceBase != none) {
                traceBase.getClass();
            }
        }
        return compareAndSet;
    }

    public final String toString() {
        return String.valueOf(this.value);
    }
}
