package kotlinx.coroutines.channels;

import kotlinx.coroutines.Waiter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class WaiterEB {
    public final Waiter waiter;

    public WaiterEB(Waiter waiter) {
        this.waiter = waiter;
    }

    public final String toString() {
        return "WaiterEB(" + this.waiter + ")";
    }
}
