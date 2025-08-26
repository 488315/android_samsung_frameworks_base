package kotlinx.coroutines;

import kotlinx.coroutines.internal.Symbol;

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
