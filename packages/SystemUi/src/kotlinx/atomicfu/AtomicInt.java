package kotlinx.atomicfu;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.atomicfu.TraceBase;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class AtomicInt {

    /* renamed from: FU */
    public static final AtomicIntegerFieldUpdater f663FU;
    public final TraceBase trace;
    public volatile int value;

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
        f663FU = AtomicIntegerFieldUpdater.newUpdater(AtomicInt.class, "value");
    }

    public AtomicInt(int i, TraceBase traceBase) {
        this.trace = traceBase;
        this.value = i;
    }

    public final boolean compareAndSet(int i, int i2) {
        boolean compareAndSet = f663FU.compareAndSet(this, i, i2);
        if (compareAndSet) {
            TraceBase.None none = TraceBase.None.INSTANCE;
            TraceBase traceBase = this.trace;
            if (traceBase != none) {
                traceBase.getClass();
            }
        }
        return compareAndSet;
    }

    public final void setValue(int i) {
        this.value = i;
        TraceBase traceBase = this.trace;
        if (traceBase != TraceBase.None.INSTANCE) {
            traceBase.getClass();
        }
    }

    public final String toString() {
        return String.valueOf(this.value);
    }
}
