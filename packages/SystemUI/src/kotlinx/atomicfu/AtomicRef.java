package kotlinx.atomicfu;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.atomicfu.TraceBase;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class AtomicRef {
    public static final AtomicReferenceFieldUpdater FU;
    public final TraceBase trace;
    public volatile Object value;

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
        FU = AtomicReferenceFieldUpdater.newUpdater(AtomicRef.class, Object.class, "value");
    }

    public AtomicRef(Object obj, TraceBase traceBase) {
        this.trace = traceBase;
        this.value = obj;
    }

    public final boolean compareAndSet(Object obj, Object obj2) {
        boolean compareAndSet = FU.compareAndSet(this, obj, obj2);
        if (compareAndSet) {
            TraceBase.None none = TraceBase.None.INSTANCE;
            TraceBase traceBase = this.trace;
            if (traceBase != none) {
                Objects.toString(obj);
                Objects.toString(obj2);
                traceBase.getClass();
            }
        }
        return compareAndSet;
    }

    public final Object getAndSet(Object obj) {
        Object andSet = FU.getAndSet(this, obj);
        TraceBase.None none = TraceBase.None.INSTANCE;
        TraceBase traceBase = this.trace;
        if (traceBase != none) {
            Objects.toString(obj);
            Objects.toString(andSet);
            traceBase.getClass();
        }
        return andSet;
    }

    public final void lazySet(Object obj) {
        FU.lazySet(this, obj);
        TraceBase.None none = TraceBase.None.INSTANCE;
        TraceBase traceBase = this.trace;
        if (traceBase != none) {
            Objects.toString(obj);
            traceBase.getClass();
        }
    }

    public final void setValue(Object obj) {
        this.value = obj;
        TraceBase traceBase = this.trace;
        if (traceBase != TraceBase.None.INSTANCE) {
            Objects.toString(obj);
            traceBase.getClass();
        }
    }

    public final String toString() {
        return String.valueOf(this.value);
    }
}
