package kotlin.coroutines;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final /* synthetic */ class CombinedContext$$ExternalSyntheticLambda1 implements Function2 {
    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        String str = (String) obj;
        CoroutineContext.Element element = (CoroutineContext.Element) obj2;
        if (str.length() == 0) {
            return element.toString();
        }
        return str + ", " + element;
    }
}
