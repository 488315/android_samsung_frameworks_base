package kotlinx.coroutines.sync;

import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.internal.SystemPropsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class SemaphoreKt {
    public static final int MAX_SPIN_CYCLES = SystemPropsKt.systemProp$default(100, 12, "kotlinx.coroutines.semaphore.maxSpinCycles");
    public static final Symbol PERMIT = new Symbol("PERMIT");
    public static final Symbol TAKEN = new Symbol("TAKEN");
    public static final Symbol BROKEN = new Symbol("BROKEN");
    public static final Symbol CANCELLED = new Symbol("CANCELLED");
    public static final int SEGMENT_SIZE = SystemPropsKt.systemProp$default(16, 12, "kotlinx.coroutines.semaphore.segmentSize");
}
