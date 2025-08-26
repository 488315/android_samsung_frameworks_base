package kotlinx.atomicfu;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.atomicfu.TraceBase;

/* loaded from: classes4.dex */
public final class AtomicLong {
    public static final AtomicLongFieldUpdater FU;
    public final TraceBase trace;
    public volatile long value;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        FU = AtomicLongFieldUpdater.newUpdater(AtomicLong.class, "value");
    }

    public AtomicLong(long j, TraceBase traceBase) {
        this.trace = traceBase;
        this.value = j;
    }

    public final long addAndGet(long j) {
        long jAddAndGet = FU.addAndGet(this, j);
        TraceBase.None none = TraceBase.None.INSTANCE;
        TraceBase traceBase = this.trace;
        if (traceBase != none) {
            traceBase.getClass();
        }
        return jAddAndGet;
    }

    public final boolean compareAndSet(long j, long j2) {
        boolean zCompareAndSet = FU.compareAndSet(this, j, j2);
        if (zCompareAndSet) {
            TraceBase.None none = TraceBase.None.INSTANCE;
            TraceBase traceBase = this.trace;
            if (traceBase != none) {
                traceBase.getClass();
            }
        }
        return zCompareAndSet;
    }

    public final long getAndIncrement() {
        long andIncrement = FU.getAndIncrement(this);
        TraceBase.None none = TraceBase.None.INSTANCE;
        TraceBase traceBase = this.trace;
        if (traceBase != none) {
            traceBase.getClass();
        }
        return andIncrement;
    }

    public final String toString() {
        return String.valueOf(this.value);
    }
}
