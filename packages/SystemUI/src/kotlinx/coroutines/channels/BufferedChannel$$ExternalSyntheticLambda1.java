package kotlinx.coroutines.channels;

import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.internal.OnUndeliveredElementKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final /* synthetic */ class BufferedChannel$$ExternalSyntheticLambda1 implements Function3 {
    public final /* synthetic */ Function1 f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ BufferedChannel$$ExternalSyntheticLambda1(Object obj, Function1 function1) {
        this.f$0 = function1;
        this.f$1 = obj;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        OnUndeliveredElementKt.callUndeliveredElement(this.f$0, this.f$1, (CoroutineContext) obj3);
        return Unit.INSTANCE;
    }
}
