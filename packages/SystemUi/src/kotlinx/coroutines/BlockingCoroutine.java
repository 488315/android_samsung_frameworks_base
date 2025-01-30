package kotlinx.coroutines;

import java.util.concurrent.locks.LockSupport;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class BlockingCoroutine extends AbstractCoroutine {
    public final Thread blockedThread;
    public final EventLoop eventLoop;

    public BlockingCoroutine(CoroutineContext coroutineContext, Thread thread, EventLoop eventLoop) {
        super(coroutineContext, true, true);
        this.blockedThread = thread;
        this.eventLoop = eventLoop;
    }

    @Override // kotlinx.coroutines.JobSupport
    public final void afterCompletion(Object obj) {
        Thread currentThread = Thread.currentThread();
        Thread thread = this.blockedThread;
        if (Intrinsics.areEqual(currentThread, thread)) {
            return;
        }
        LockSupport.unpark(thread);
    }
}
