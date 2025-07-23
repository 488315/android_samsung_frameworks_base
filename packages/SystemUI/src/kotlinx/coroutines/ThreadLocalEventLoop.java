package kotlinx.coroutines;

import kotlinx.coroutines.internal.Symbol;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class ThreadLocalEventLoop {
    public static final ThreadLocalEventLoop INSTANCE = new ThreadLocalEventLoop();
    public static final ThreadLocal ref;

    static {
        new Symbol("ThreadLocalEventLoop");
        ref = new ThreadLocal();
    }

    private ThreadLocalEventLoop() {
    }

    public static EventLoop getEventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() {
        ThreadLocal threadLocal = ref;
        EventLoop eventLoop = (EventLoop) threadLocal.get();
        if (eventLoop != null) {
            return eventLoop;
        }
        BlockingEventLoop blockingEventLoop = new BlockingEventLoop(Thread.currentThread());
        threadLocal.set(blockingEventLoop);
        return blockingEventLoop;
    }
}
