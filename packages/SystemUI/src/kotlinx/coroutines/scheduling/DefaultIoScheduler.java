package kotlinx.coroutines.scheduling;

import java.util.concurrent.Executor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.ExecutorCoroutineDispatcher;
import kotlinx.coroutines.internal.SystemPropsKt;
import kotlinx.coroutines.internal.SystemPropsKt__SystemPropsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class DefaultIoScheduler extends ExecutorCoroutineDispatcher implements Executor {
    public static final DefaultIoScheduler INSTANCE = new DefaultIoScheduler();

    /* renamed from: default, reason: not valid java name */
    public static final CoroutineDispatcher f132default;

    static {
        UnlimitedIoScheduler unlimitedIoScheduler = UnlimitedIoScheduler.INSTANCE;
        int i = SystemPropsKt__SystemPropsKt.AVAILABLE_PROCESSORS;
        if (64 >= i) {
            i = 64;
        }
        f132default = unlimitedIoScheduler.limitedParallelism(SystemPropsKt.systemProp$default(i, 12, "kotlinx.coroutines.io.parallelism"));
    }

    private DefaultIoScheduler() {
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        throw new IllegalStateException("Cannot be invoked on Dispatchers.IO");
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    public final void dispatch(CoroutineContext coroutineContext, Runnable runnable) {
        f132default.dispatch(coroutineContext, runnable);
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    public final void dispatchYield(CoroutineContext coroutineContext, Runnable runnable) {
        f132default.dispatchYield(coroutineContext, runnable);
    }

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        dispatch(EmptyCoroutineContext.INSTANCE, runnable);
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    public final CoroutineDispatcher limitedParallelism(int i) {
        return UnlimitedIoScheduler.INSTANCE.limitedParallelism(i);
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    public final String toString() {
        return "Dispatchers.IO";
    }

    @Override // kotlinx.coroutines.ExecutorCoroutineDispatcher
    public final Executor getExecutor() {
        return this;
    }
}
