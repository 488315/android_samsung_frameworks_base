package kotlinx.coroutines.internal;

import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.ThreadContextElement;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class ThreadLocalElement implements ThreadContextElement {
    public final ThreadLocalKey key;
    public final ThreadLocal threadLocal;
    public final Object value;

    public ThreadLocalElement(Object obj, ThreadLocal<Object> threadLocal) {
        this.value = obj;
        this.threadLocal = threadLocal;
        this.key = new ThreadLocalKey(threadLocal);
    }

    @Override // kotlin.coroutines.CoroutineContext
    public final Object fold(Object obj, Function2 function2) {
        return function2.invoke(obj, this);
    }

    @Override // kotlin.coroutines.CoroutineContext
    public final CoroutineContext.Element get(CoroutineContext.Key key) {
        if (Intrinsics.areEqual(this.key, key)) {
            return this;
        }
        return null;
    }

    @Override // kotlin.coroutines.CoroutineContext.Element
    public final CoroutineContext.Key getKey() {
        return this.key;
    }

    @Override // kotlin.coroutines.CoroutineContext
    public final CoroutineContext minusKey(CoroutineContext.Key key) {
        return Intrinsics.areEqual(this.key, key) ? EmptyCoroutineContext.INSTANCE : this;
    }

    @Override // kotlin.coroutines.CoroutineContext
    public final CoroutineContext plus(CoroutineContext coroutineContext) {
        return CoroutineContext.DefaultImpls.plus(this, coroutineContext);
    }

    @Override // kotlinx.coroutines.ThreadContextElement
    public final void restoreThreadContext(Object obj) {
        this.threadLocal.set(obj);
    }

    public final String toString() {
        return "ThreadLocal(value=" + this.value + ", threadLocal = " + this.threadLocal + ")";
    }

    @Override // kotlinx.coroutines.ThreadContextElement
    public final Object updateThreadContext(CoroutineContext coroutineContext) {
        Object obj = this.threadLocal.get();
        this.threadLocal.set(this.value);
        return obj;
    }
}
