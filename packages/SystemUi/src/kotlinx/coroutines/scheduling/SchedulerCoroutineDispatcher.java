package kotlinx.coroutines.scheduling;

import java.util.concurrent.Executor;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.ExecutorCoroutineDispatcher;
import kotlinx.coroutines.internal.Symbol;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class SchedulerCoroutineDispatcher extends ExecutorCoroutineDispatcher {
    public final CoroutineScheduler coroutineScheduler;

    public SchedulerCoroutineDispatcher() {
        this(0, 0, 0L, null, 15, null);
    }

    public void close() {
        this.coroutineScheduler.close();
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    public final void dispatch(CoroutineContext coroutineContext, Runnable runnable) {
        CoroutineScheduler coroutineScheduler = this.coroutineScheduler;
        Symbol symbol = CoroutineScheduler.NOT_IN_STACK;
        coroutineScheduler.dispatch(runnable, TasksKt.NonBlockingContext, false);
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    public final void dispatchYield(CoroutineContext coroutineContext, Runnable runnable) {
        CoroutineScheduler coroutineScheduler = this.coroutineScheduler;
        Symbol symbol = CoroutineScheduler.NOT_IN_STACK;
        coroutineScheduler.dispatch(runnable, TasksKt.NonBlockingContext, true);
    }

    @Override // kotlinx.coroutines.ExecutorCoroutineDispatcher
    public final Executor getExecutor() {
        return this.coroutineScheduler;
    }

    public /* synthetic */ SchedulerCoroutineDispatcher(int i, int i2, long j, String str, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? TasksKt.CORE_POOL_SIZE : i, (i3 & 2) != 0 ? TasksKt.MAX_POOL_SIZE : i2, (i3 & 4) != 0 ? TasksKt.IDLE_WORKER_KEEP_ALIVE_NS : j, (i3 & 8) != 0 ? "CoroutineScheduler" : str);
    }

    public SchedulerCoroutineDispatcher(int i, int i2, long j, String str) {
        this.coroutineScheduler = new CoroutineScheduler(i, i2, j, str);
    }
}
