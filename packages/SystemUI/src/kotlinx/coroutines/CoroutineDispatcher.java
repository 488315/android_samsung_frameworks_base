package kotlinx.coroutines;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import kotlin.coroutines.AbstractCoroutineContextElement;
import kotlin.coroutines.AbstractCoroutineContextKey;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.internal.DispatchedContinuationKt;
import kotlinx.coroutines.internal.LimitedDispatcher;
import kotlinx.coroutines.internal.LimitedDispatcherKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class CoroutineDispatcher extends AbstractCoroutineContextElement implements ContinuationInterceptor {
    public static final Key Key = new Key(null);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Key extends AbstractCoroutineContextKey {
        public /* synthetic */ Key(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Key() {
            super(ContinuationInterceptor.Key, new CoroutineDispatcher$Key$$ExternalSyntheticLambda0());
        }
    }

    public CoroutineDispatcher() {
        super(ContinuationInterceptor.Key);
    }

    public abstract void dispatch(CoroutineContext coroutineContext, Runnable runnable);

    public void dispatchYield(CoroutineContext coroutineContext, Runnable runnable) {
        DispatchedContinuationKt.safeDispatch(this, coroutineContext, runnable);
    }

    @Override // kotlin.coroutines.AbstractCoroutineContextElement, kotlin.coroutines.CoroutineContext
    public final CoroutineContext.Element get(CoroutineContext.Key key) {
        boolean z;
        CoroutineContext.Element element;
        if (!(key instanceof AbstractCoroutineContextKey)) {
            if (ContinuationInterceptor.Key == key) {
                return this;
            }
            return null;
        }
        AbstractCoroutineContextKey abstractCoroutineContextKey = (AbstractCoroutineContextKey) key;
        CoroutineContext.Key key2 = this.key;
        if (key2 == abstractCoroutineContextKey) {
            abstractCoroutineContextKey.getClass();
        } else if (abstractCoroutineContextKey.topmostKey != key2) {
            z = false;
            if (!z && (element = (CoroutineContext.Element) abstractCoroutineContextKey.safeCast.mo779invoke(this)) != null) {
                return element;
            }
        }
        z = true;
        return !z ? null : null;
    }

    public boolean isDispatchNeeded(CoroutineContext coroutineContext) {
        return !(this instanceof Unconfined);
    }

    public CoroutineDispatcher limitedParallelism(int i) {
        LimitedDispatcherKt.checkParallelism(i);
        return new LimitedDispatcher(this, i, null);
    }

    @Override // kotlin.coroutines.AbstractCoroutineContextElement, kotlin.coroutines.CoroutineContext
    public final CoroutineContext minusKey(CoroutineContext.Key key) {
        boolean z;
        if (!(key instanceof AbstractCoroutineContextKey)) {
            return ContinuationInterceptor.Key == key ? EmptyCoroutineContext.INSTANCE : this;
        }
        AbstractCoroutineContextKey abstractCoroutineContextKey = (AbstractCoroutineContextKey) key;
        CoroutineContext.Key key2 = this.key;
        if (key2 == abstractCoroutineContextKey) {
            abstractCoroutineContextKey.getClass();
        } else if (abstractCoroutineContextKey.topmostKey != key2) {
            z = false;
            return (!z || ((CoroutineContext.Element) abstractCoroutineContextKey.safeCast.mo779invoke(this)) == null) ? this : EmptyCoroutineContext.INSTANCE;
        }
        z = true;
        if (z) {
            return this;
        }
    }

    public String toString() {
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(getClass().getSimpleName(), "@", DebugStringsKt.getHexAddress(this));
    }
}
