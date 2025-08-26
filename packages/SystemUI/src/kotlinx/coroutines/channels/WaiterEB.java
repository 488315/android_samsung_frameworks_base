package kotlinx.coroutines.channels;

import kotlinx.coroutines.Waiter;

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
