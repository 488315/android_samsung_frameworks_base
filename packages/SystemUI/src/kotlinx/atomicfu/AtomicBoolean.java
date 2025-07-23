package kotlinx.atomicfu;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.atomicfu.TraceBase;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class AtomicBoolean {
    public static final AtomicIntegerFieldUpdater FU;
    public volatile int _value;
    public final TraceBase trace;

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
        FU = AtomicIntegerFieldUpdater.newUpdater(AtomicBoolean.class, "_value");
    }

    public AtomicBoolean(boolean z, TraceBase traceBase) {
        this.trace = traceBase;
        this._value = z ? 1 : 0;
    }

    public final boolean compareAndSet() {
        boolean compareAndSet = FU.compareAndSet(this, 0, 1);
        if (compareAndSet) {
            TraceBase.None none = TraceBase.None.INSTANCE;
            TraceBase traceBase = this.trace;
            if (traceBase != none) {
                traceBase.getClass();
            }
        }
        return compareAndSet;
    }

    public final boolean getValue() {
        return this._value != 0;
    }

    public final String toString() {
        return String.valueOf(getValue());
    }
}
