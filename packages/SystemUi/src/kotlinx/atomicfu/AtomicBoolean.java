package kotlinx.atomicfu;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.atomicfu.TraceBase;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class AtomicBoolean {

    /* renamed from: FU */
    public static final AtomicIntegerFieldUpdater f662FU;
    public volatile int _value;
    public final TraceBase trace;

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
        f662FU = AtomicIntegerFieldUpdater.newUpdater(AtomicBoolean.class, "_value");
    }

    public AtomicBoolean(boolean z, TraceBase traceBase) {
        this.trace = traceBase;
        this._value = z ? 1 : 0;
    }

    public final boolean compareAndSet() {
        boolean compareAndSet = f662FU.compareAndSet(this, 0, 1);
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
        return String.valueOf(this._value != 0);
    }
}
