package kotlin.coroutines;

import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final /* synthetic */ class CoroutineContext$DefaultImpls$$ExternalSyntheticLambda0 implements Function2 {
    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        CoroutineContext.Element element = (CoroutineContext.Element) obj2;
        CoroutineContext minusKey = ((CoroutineContext) obj).minusKey(element.getKey());
        EmptyCoroutineContext emptyCoroutineContext = EmptyCoroutineContext.INSTANCE;
        if (minusKey == emptyCoroutineContext) {
            return element;
        }
        ContinuationInterceptor.Key key = ContinuationInterceptor.Key;
        ContinuationInterceptor continuationInterceptor = (ContinuationInterceptor) minusKey.get(key);
        if (continuationInterceptor == null) {
            return new CombinedContext(minusKey, element);
        }
        CoroutineContext minusKey2 = minusKey.minusKey(key);
        return minusKey2 == emptyCoroutineContext ? new CombinedContext(element, continuationInterceptor) : new CombinedContext(new CombinedContext(minusKey2, element), continuationInterceptor);
    }
}
