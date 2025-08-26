package kotlinx.coroutines;

import kotlin.coroutines.CoroutineContext;

/* loaded from: classes4.dex */
public interface ThreadContextElement extends CoroutineContext.Element {
    void restoreThreadContext(Object obj);

    Object updateThreadContext(CoroutineContext coroutineContext);
}
