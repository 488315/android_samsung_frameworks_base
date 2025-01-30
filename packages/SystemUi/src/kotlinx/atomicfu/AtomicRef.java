package kotlinx.atomicfu;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.atomicfu.TraceBase;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class AtomicRef {

    /* renamed from: FU */
    public static final AtomicReferenceFieldUpdater f665FU;
    public final TraceBase trace;
    public volatile Object value;

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
        f665FU = AtomicReferenceFieldUpdater.newUpdater(AtomicRef.class, Object.class, "value");
    }

    public AtomicRef(Object obj, TraceBase traceBase) {
        this.trace = traceBase;
        this.value = obj;
    }

    public final boolean compareAndSet(Object obj, Object obj2) {
        boolean compareAndSet = f665FU.compareAndSet(this, obj, obj2);
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
        Object andSet = f665FU.getAndSet(this, obj);
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
        f665FU.lazySet(this, obj);
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
