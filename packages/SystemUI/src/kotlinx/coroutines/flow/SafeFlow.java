package kotlinx.coroutines.flow;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.internal.SafeCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SafeFlow extends AbstractFlow {
    public final Function2 block;

    public SafeFlow(Function2 function2) {
        this.block = function2;
    }

    @Override // kotlinx.coroutines.flow.AbstractFlow
    public final Object collectSafely(SafeCollector safeCollector, Continuation continuation) {
        Object invoke = this.block.invoke(safeCollector, continuation);
        return invoke == CoroutineSingletons.COROUTINE_SUSPENDED ? invoke : Unit.INSTANCE;
    }
}
