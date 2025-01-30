package kotlinx.coroutines;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import kotlin.coroutines.AbstractCoroutineContextElement;
import kotlin.coroutines.AbstractCoroutineContextKey;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class CoroutineDispatcher extends AbstractCoroutineContextElement implements ContinuationInterceptor {
    public static final Key Key = new Key(null);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Key extends AbstractCoroutineContextKey {
        public /* synthetic */ Key(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Key() {
            super(ContinuationInterceptor.Key, new Function1() { // from class: kotlinx.coroutines.CoroutineDispatcher.Key.1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    CoroutineContext.Element element = (CoroutineContext.Element) obj;
                    if (element instanceof CoroutineDispatcher) {
                        return (CoroutineDispatcher) element;
                    }
                    return null;
                }
            });
        }
    }

    public CoroutineDispatcher() {
        super(ContinuationInterceptor.Key);
    }

    public abstract void dispatch(CoroutineContext coroutineContext, Runnable runnable);

    public void dispatchYield(CoroutineContext coroutineContext, Runnable runnable) {
        dispatch(coroutineContext, runnable);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0018  */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2 */
    /* JADX WARN: Type inference failed for: r3v5 */
    /* JADX WARN: Type inference failed for: r3v6 */
    @Override // kotlin.coroutines.AbstractCoroutineContextElement, kotlin.coroutines.CoroutineContext
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final CoroutineContext.Element get(CoroutineContext.Key key) {
        ?? r3;
        boolean z;
        if (key instanceof AbstractCoroutineContextKey) {
            AbstractCoroutineContextKey abstractCoroutineContextKey = (AbstractCoroutineContextKey) key;
            CoroutineContext.Key key2 = this.key;
            if (key2 == abstractCoroutineContextKey) {
                abstractCoroutineContextKey.getClass();
            } else if (abstractCoroutineContextKey.topmostKey != key2) {
                z = false;
                if (z) {
                    return null;
                }
                CoroutineContext.Element element = (CoroutineContext.Element) abstractCoroutineContextKey.safeCast.invoke(this);
                boolean z2 = element instanceof CoroutineContext.Element;
                r3 = element;
                if (!z2) {
                    return null;
                }
            }
            z = true;
            if (z) {
            }
        } else {
            this = this;
            if (ContinuationInterceptor.Key != key) {
                r3 = 0;
            }
        }
        return r3;
    }

    public boolean isDispatchNeeded() {
        return true;
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
            return (!z || ((CoroutineContext.Element) abstractCoroutineContextKey.safeCast.invoke(this)) == null) ? this : EmptyCoroutineContext.INSTANCE;
        }
        z = true;
        if (z) {
            return this;
        }
    }

    public String toString() {
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(DebugStringsKt.getClassSimpleName(this), "@", DebugStringsKt.getHexAddress(this));
    }
}
