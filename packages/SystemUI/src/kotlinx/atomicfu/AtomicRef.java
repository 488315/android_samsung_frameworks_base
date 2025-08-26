package kotlinx.atomicfu;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.atomicfu.TraceBase;

/* loaded from: classes4.dex */
public final class AtomicRef {
    public static final AtomicReferenceFieldUpdater FU;
    public final TraceBase trace;
    public volatile Object value;

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
        boolean zCompareAndSet = FU.compareAndSet(this, obj, obj2);
        if (zCompareAndSet) {
            TraceBase.None none = TraceBase.None.INSTANCE;
            TraceBase traceBase = this.trace;
            if (traceBase != none) {
                Objects.toString(obj);
                Objects.toString(obj2);
                traceBase.getClass();
            }
        }
        return zCompareAndSet;
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
