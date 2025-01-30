package kotlinx.coroutines;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ThreadLocalEventLoop {
    public static final ThreadLocalEventLoop INSTANCE = new ThreadLocalEventLoop();
    public static final ThreadLocal ref = new ThreadLocal();

    private ThreadLocalEventLoop() {
    }

    /* renamed from: getEventLoop$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    public static EventLoop m295x4695df28() {
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
