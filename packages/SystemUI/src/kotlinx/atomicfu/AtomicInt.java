package kotlinx.atomicfu;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.atomicfu.TraceBase;

/* loaded from: classes4.dex */
public final class AtomicInt {
    public static final AtomicIntegerFieldUpdater FU;
    public final TraceBase trace;
    public volatile int value;

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
        boolean zCompareAndSet = FU.compareAndSet(this, i, i2);
        if (zCompareAndSet) {
            TraceBase.None none = TraceBase.None.INSTANCE;
            TraceBase traceBase = this.trace;
            if (traceBase != none) {
                traceBase.getClass();
            }
        }
        return zCompareAndSet;
    }

    public final int decrementAndGet() {
        int iDecrementAndGet = FU.decrementAndGet(this);
        TraceBase.None none = TraceBase.None.INSTANCE;
        TraceBase traceBase = this.trace;
        if (traceBase != none) {
            traceBase.getClass();
        }
        return iDecrementAndGet;
    }

    public final int incrementAndGet() {
        int iIncrementAndGet = FU.incrementAndGet(this);
        TraceBase.None none = TraceBase.None.INSTANCE;
        TraceBase traceBase = this.trace;
        if (traceBase != none) {
            traceBase.getClass();
        }
        return iIncrementAndGet;
    }

    public final String toString() {
        return String.valueOf(this.value);
    }
}
