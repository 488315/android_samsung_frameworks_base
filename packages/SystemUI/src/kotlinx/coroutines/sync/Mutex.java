package kotlinx.coroutines.sync;

import kotlin.coroutines.Continuation;

/* loaded from: classes4.dex */
public interface Mutex {
    Object lock(Continuation continuation);

    void unlock(Object obj);
}
