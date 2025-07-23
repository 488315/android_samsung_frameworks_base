package kotlinx.coroutines.scheduling;

import kotlinx.coroutines.internal.LockFreeTaskQueue;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class GlobalQueue extends LockFreeTaskQueue {
    public GlobalQueue() {
        super(false);
    }
}
