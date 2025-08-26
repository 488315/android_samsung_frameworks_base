package androidx.compose.runtime.internal;

import java.util.concurrent.atomic.AtomicReference;
import kotlin.Unit;

/* loaded from: classes.dex */
public final class SnapshotThreadLocal<T> {
    public Object mainThreadValue;
    public final AtomicReference map = new AtomicReference(SnapshotThreadLocalKt.emptyThreadMap);
    public final Object writeMutex = new Object();

    public final Object get() {
        long jCurrentThreadId = Thread_jvmKt.currentThreadId();
        if (jCurrentThreadId == Thread_androidKt.MainThreadId) {
            return this.mainThreadValue;
        }
        ThreadMap threadMap = (ThreadMap) this.map.get();
        int iFind = threadMap.find(jCurrentThreadId);
        if (iFind >= 0) {
            return threadMap.values[iFind];
        }
        return null;
    }

    public final void set(Object obj) {
        long jCurrentThreadId = Thread_jvmKt.currentThreadId();
        if (jCurrentThreadId == Thread_androidKt.MainThreadId) {
            this.mainThreadValue = obj;
            return;
        }
        synchronized (this.writeMutex) {
            ThreadMap threadMap = (ThreadMap) this.map.get();
            int iFind = threadMap.find(jCurrentThreadId);
            if (iFind >= 0) {
                threadMap.values[iFind] = obj;
            } else {
                this.map.set(threadMap.newWith(jCurrentThreadId, obj));
                Unit unit = Unit.INSTANCE;
            }
        }
    }
}
