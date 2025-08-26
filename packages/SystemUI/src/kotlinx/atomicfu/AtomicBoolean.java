package kotlinx.atomicfu;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.atomicfu.TraceBase;

/* loaded from: classes4.dex */
public final class AtomicBoolean {
    public static final AtomicIntegerFieldUpdater FU;
    public volatile int _value;
    public final TraceBase trace;

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
        boolean zCompareAndSet = FU.compareAndSet(this, 0, 1);
        if (zCompareAndSet) {
            TraceBase.None none = TraceBase.None.INSTANCE;
            TraceBase traceBase = this.trace;
            if (traceBase != none) {
                traceBase.getClass();
            }
        }
        return zCompareAndSet;
    }

    public final boolean getValue() {
        return this._value != 0;
    }

    public final String toString() {
        return String.valueOf(getValue());
    }
}
