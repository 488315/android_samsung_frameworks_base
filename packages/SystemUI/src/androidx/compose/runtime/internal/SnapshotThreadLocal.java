package androidx.compose.runtime.internal;

import java.util.concurrent.atomic.AtomicReference;
import kotlin.Unit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SnapshotThreadLocal<T> {
    public Object mainThreadValue;
    public final AtomicReference map = new AtomicReference(SnapshotThreadLocalKt.emptyThreadMap);
    public final Object writeMutex = new Object();

    public final Object get() {
        long currentThreadId = Thread_jvmKt.currentThreadId();
        if (currentThreadId == Thread_androidKt.MainThreadId) {
            return this.mainThreadValue;
        }
        ThreadMap threadMap = (ThreadMap) this.map.get();
        int find = threadMap.find(currentThreadId);
        if (find >= 0) {
            return threadMap.values[find];
        }
        return null;
    }

    public final void set(Object obj) {
        long currentThreadId = Thread_jvmKt.currentThreadId();
        if (currentThreadId == Thread_androidKt.MainThreadId) {
            this.mainThreadValue = obj;
            return;
        }
        synchronized (this.writeMutex) {
            ThreadMap threadMap = (ThreadMap) this.map.get();
            int find = threadMap.find(currentThreadId);
            if (find >= 0) {
                threadMap.values[find] = obj;
            } else {
                this.map.set(threadMap.newWith(currentThreadId, obj));
                Unit unit = Unit.INSTANCE;
            }
        }
    }
}
