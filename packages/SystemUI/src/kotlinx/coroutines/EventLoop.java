package kotlinx.coroutines;

import kotlin.collections.ArrayDeque;
import kotlinx.coroutines.internal.LimitedDispatcherKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class EventLoop extends CoroutineDispatcher {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean shared;
    public ArrayDeque unconfinedQueue;
    public long useCount;

    public final void decrementUseCount(boolean z) {
        long j = this.useCount - (z ? 4294967296L : 1L);
        this.useCount = j;
        if (j <= 0 && this.shared) {
            shutdown();
        }
    }

    public final void dispatchUnconfined(DispatchedTask dispatchedTask) {
        ArrayDeque arrayDeque = this.unconfinedQueue;
        if (arrayDeque == null) {
            arrayDeque = new ArrayDeque();
            this.unconfinedQueue = arrayDeque;
        }
        arrayDeque.addLast(dispatchedTask);
    }

    public final void incrementUseCount(boolean z) {
        this.useCount = (z ? 4294967296L : 1L) + this.useCount;
        if (z) {
            return;
        }
        this.shared = true;
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    public final CoroutineDispatcher limitedParallelism(int i) {
        LimitedDispatcherKt.checkParallelism(i);
        return this;
    }

    public long processNextEvent() {
        return !processUnconfinedEvent() ? Long.MAX_VALUE : 0L;
    }

    public final boolean processUnconfinedEvent() {
        ArrayDeque arrayDeque = this.unconfinedQueue;
        if (arrayDeque == null) {
            return false;
        }
        DispatchedTask dispatchedTask = (DispatchedTask) (arrayDeque.isEmpty() ? null : arrayDeque.removeFirst());
        if (dispatchedTask == null) {
            return false;
        }
        dispatchedTask.run();
        return true;
    }

    public void shutdown() {
    }
}
