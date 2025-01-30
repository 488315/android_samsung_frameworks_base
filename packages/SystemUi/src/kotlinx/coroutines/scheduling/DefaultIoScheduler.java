package kotlinx.coroutines.scheduling;

import android.support.v4.media.AbstractC0000x2c234b15;
import java.util.concurrent.Executor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlinx.coroutines.ExecutorCoroutineDispatcher;
import kotlinx.coroutines.internal.LimitedDispatcher;
import kotlinx.coroutines.internal.SystemPropsKt;
import kotlinx.coroutines.internal.SystemPropsKt__SystemPropsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class DefaultIoScheduler extends ExecutorCoroutineDispatcher implements Executor {
    public static final DefaultIoScheduler INSTANCE = new DefaultIoScheduler();

    /* renamed from: default, reason: not valid java name */
    public static final LimitedDispatcher f838default;

    static {
        UnlimitedIoScheduler unlimitedIoScheduler = UnlimitedIoScheduler.INSTANCE;
        int i = SystemPropsKt__SystemPropsKt.AVAILABLE_PROCESSORS;
        if (64 >= i) {
            i = 64;
        }
        int systemProp$default = SystemPropsKt.systemProp$default("kotlinx.coroutines.io.parallelism", i, 0, 0, 12);
        unlimitedIoScheduler.getClass();
        if (!(systemProp$default >= 1)) {
            throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("Expected positive parallelism level, but got ", systemProp$default).toString());
        }
        f838default = new LimitedDispatcher(unlimitedIoScheduler, systemProp$default);
    }

    private DefaultIoScheduler() {
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        throw new IllegalStateException("Cannot be invoked on Dispatchers.IO".toString());
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    public final void dispatch(CoroutineContext coroutineContext, Runnable runnable) {
        f838default.dispatch(coroutineContext, runnable);
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    public final void dispatchYield(CoroutineContext coroutineContext, Runnable runnable) {
        f838default.dispatchYield(coroutineContext, runnable);
    }

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        dispatch(EmptyCoroutineContext.INSTANCE, runnable);
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
