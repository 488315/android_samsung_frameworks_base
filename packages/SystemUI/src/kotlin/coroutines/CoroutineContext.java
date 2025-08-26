package kotlin.coroutines;

import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public interface CoroutineContext {

    public abstract class DefaultImpls {
        public static Element get(Element element, Key key) {
            if (Intrinsics.areEqual(element.getKey(), key)) {
                return element;
            }
            return null;
        }

        public static CoroutineContext minusKey(Element element, Key key) {
            return Intrinsics.areEqual(element.getKey(), key) ? EmptyCoroutineContext.INSTANCE : element;
        }

        public static CoroutineContext plus(CoroutineContext coroutineContext, CoroutineContext coroutineContext2) {
            return coroutineContext2 == EmptyCoroutineContext.INSTANCE ? coroutineContext : (CoroutineContext) coroutineContext2.fold(coroutineContext, new CoroutineContext$DefaultImpls$$ExternalSyntheticLambda0());
        }
    }

    public interface Element extends CoroutineContext {
        Key getKey();
    }

    public interface Key {
    }

    Object fold(Object obj, Function2 function2);

    Element get(Key key);

    CoroutineContext minusKey(Key key);

    CoroutineContext plus(CoroutineContext coroutineContext);
}
