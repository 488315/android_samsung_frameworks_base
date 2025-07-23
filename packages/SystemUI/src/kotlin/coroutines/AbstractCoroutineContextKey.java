package kotlin.coroutines;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class AbstractCoroutineContextKey implements CoroutineContext.Key {
    public final Function1 safeCast;
    public final CoroutineContext.Key topmostKey;

    public AbstractCoroutineContextKey(CoroutineContext.Key key, Function1 function1) {
        this.safeCast = function1;
        this.topmostKey = key instanceof AbstractCoroutineContextKey ? ((AbstractCoroutineContextKey) key).topmostKey : key;
    }
}
