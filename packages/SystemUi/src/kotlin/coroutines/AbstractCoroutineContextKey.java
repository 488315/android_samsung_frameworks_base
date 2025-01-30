package kotlin.coroutines;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class AbstractCoroutineContextKey implements CoroutineContext.Key {
    public final Function1 safeCast;
    public final CoroutineContext.Key topmostKey;

    public AbstractCoroutineContextKey(CoroutineContext.Key key, Function1 function1) {
        this.safeCast = function1;
        this.topmostKey = key instanceof AbstractCoroutineContextKey ? ((AbstractCoroutineContextKey) key).topmostKey : key;
    }
}
