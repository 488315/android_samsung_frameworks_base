package kotlinx.atomicfu;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.atomicfu.TraceBase;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class AtomicInt {
    public static final AtomicIntegerFieldUpdater FU;
    public final TraceBase trace;
    public volatile int value;

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
        FU = AtomicIntegerFieldUpdater.newUpdater(AtomicInt.class, "value");
    }

    public AtomicInt(int i, TraceBase traceBase) {
        this.trace = traceBase;
        this.value = i;
    }

    public final boolean compareAndSet(int i, int i2) {
        boolean compareAndSet = FU.compareAndSet(this, i, i2);
        if (compareAndSet) {
            TraceBase.None none = TraceBase.None.INSTANCE;
            TraceBase traceBase = this.trace;
            if (traceBase != none) {
                traceBase.getClass();
            }
        }
        return compareAndSet;
    }

    public final int decrementAndGet() {
        int decrementAndGet = FU.decrementAndGet(this);
        TraceBase.None none = TraceBase.None.INSTANCE;
        TraceBase traceBase = this.trace;
        if (traceBase != none) {
            traceBase.getClass();
        }
        return decrementAndGet;
    }

    public final int incrementAndGet() {
        int incrementAndGet = FU.incrementAndGet(this);
        TraceBase.None none = TraceBase.None.INSTANCE;
        TraceBase traceBase = this.trace;
        if (traceBase != none) {
            traceBase.getClass();
        }
        return incrementAndGet;
    }

    public final String toString() {
        return String.valueOf(this.value);
    }
}
