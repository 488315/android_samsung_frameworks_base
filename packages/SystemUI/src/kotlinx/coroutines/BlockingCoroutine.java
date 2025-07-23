package kotlinx.coroutines;

import java.util.concurrent.locks.LockSupport;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
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
        if (Intrinsics.areEqual(Thread.currentThread(), this.blockedThread)) {
            return;
        }
        LockSupport.unpark(this.blockedThread);
    }
}
