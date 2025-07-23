package kotlinx.atomicfu;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.atomicfu.TraceBase;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class AtomicLong {
    public static final AtomicLongFieldUpdater FU;
    public final TraceBase trace;
    public volatile long value;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        long addAndGet = FU.addAndGet(this, j);
        TraceBase.None none = TraceBase.None.INSTANCE;
        TraceBase traceBase = this.trace;
        if (traceBase != none) {
            traceBase.getClass();
        }
        return addAndGet;
    }

    public final boolean compareAndSet(long j, long j2) {
        boolean compareAndSet = FU.compareAndSet(this, j, j2);
        if (compareAndSet) {
            TraceBase.None none = TraceBase.None.INSTANCE;
            TraceBase traceBase = this.trace;
            if (traceBase != none) {
                traceBase.getClass();
            }
        }
        return compareAndSet;
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
