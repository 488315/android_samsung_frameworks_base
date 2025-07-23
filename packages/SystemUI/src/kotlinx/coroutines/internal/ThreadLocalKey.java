package kotlinx.coroutines.internal;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class ThreadLocalKey implements CoroutineContext.Key {
    public final ThreadLocal threadLocal;

    public ThreadLocalKey(ThreadLocal<?> threadLocal) {
        this.threadLocal = threadLocal;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ThreadLocalKey) && Intrinsics.areEqual(this.threadLocal, ((ThreadLocalKey) obj).threadLocal);
    }

    public final int hashCode() {
        return this.threadLocal.hashCode();
    }

    public final String toString() {
        return "ThreadLocalKey(threadLocal=" + this.threadLocal + ")";
    }
}
