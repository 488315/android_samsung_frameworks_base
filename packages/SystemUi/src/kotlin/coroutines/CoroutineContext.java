package kotlin.coroutines;

import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface CoroutineContext {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class DefaultImpls {
        public static CoroutineContext plus(CoroutineContext coroutineContext, CoroutineContext coroutineContext2) {
            return coroutineContext2 == EmptyCoroutineContext.INSTANCE ? coroutineContext : (CoroutineContext) coroutineContext2.fold(coroutineContext, new Function2() { // from class: kotlin.coroutines.CoroutineContext$plus$1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    CombinedContext combinedContext;
                    CoroutineContext.Element element = (CoroutineContext.Element) obj2;
                    CoroutineContext minusKey = ((CoroutineContext) obj).minusKey(element.getKey());
                    EmptyCoroutineContext emptyCoroutineContext = EmptyCoroutineContext.INSTANCE;
                    if (minusKey == emptyCoroutineContext) {
                        return element;
                    }
                    ContinuationInterceptor.Key key = ContinuationInterceptor.Key;
                    ContinuationInterceptor continuationInterceptor = (ContinuationInterceptor) minusKey.get(key);
                    if (continuationInterceptor == null) {
                        combinedContext = new CombinedContext(minusKey, element);
                    } else {
                        CoroutineContext minusKey2 = minusKey.minusKey(key);
                        if (minusKey2 == emptyCoroutineContext) {
                            return new CombinedContext(element, continuationInterceptor);
                        }
                        combinedContext = new CombinedContext(new CombinedContext(minusKey2, element), continuationInterceptor);
                    }
                    return combinedContext;
                }
            });
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Element extends CoroutineContext {
        Key getKey();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Key {
    }

    Object fold(Object obj, Function2 function2);

    Element get(Key key);

    CoroutineContext minusKey(Key key);

    CoroutineContext plus(CoroutineContext coroutineContext);
}
