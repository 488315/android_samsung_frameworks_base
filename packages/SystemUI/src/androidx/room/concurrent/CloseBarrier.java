package androidx.room.concurrent;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class CloseBarrier {
    public final AtomicInteger blockers = new AtomicInteger(0);
    public final AtomicBoolean closeInitiated = new AtomicBoolean(false);

    public CloseBarrier(Function0 function0) {
    }

    public final boolean block$room_runtime_release() {
        synchronized (this) {
            if (this.closeInitiated.get()) {
                return false;
            }
            this.blockers.incrementAndGet();
            return true;
        }
    }

    public final void unblock$room_runtime_release() {
        synchronized (this) {
            this.blockers.decrementAndGet();
            if (this.blockers.get() < 0) {
                throw new IllegalStateException("Unbalanced call to unblock() detected.");
            }
            Unit unit = Unit.INSTANCE;
        }
    }
}
