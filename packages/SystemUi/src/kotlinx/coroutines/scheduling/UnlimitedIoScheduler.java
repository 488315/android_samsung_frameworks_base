package kotlinx.coroutines.scheduling;

import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class UnlimitedIoScheduler extends CoroutineDispatcher {
    public static final UnlimitedIoScheduler INSTANCE = new UnlimitedIoScheduler();

    private UnlimitedIoScheduler() {
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    public final void dispatch(CoroutineContext coroutineContext, Runnable runnable) {
        DefaultScheduler defaultScheduler = DefaultScheduler.INSTANCE;
        defaultScheduler.coroutineScheduler.dispatch(runnable, TasksKt.BlockingContext, false);
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    public final void dispatchYield(CoroutineContext coroutineContext, Runnable runnable) {
        DefaultScheduler defaultScheduler = DefaultScheduler.INSTANCE;
        defaultScheduler.coroutineScheduler.dispatch(runnable, TasksKt.BlockingContext, true);
    }
}
