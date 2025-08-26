package kotlinx.coroutines.flow;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.internal.SafeCollector;

/* loaded from: classes4.dex */
public final class SafeFlow extends AbstractFlow {
    public final Function2 block;

    public SafeFlow(Function2 function2) {
        this.block = function2;
    }

    @Override // kotlinx.coroutines.flow.AbstractFlow
    public final Object collectSafely(SafeCollector safeCollector, Continuation continuation) {
        Object objInvoke = this.block.invoke(safeCollector, continuation);
        return objInvoke == CoroutineSingletons.COROUTINE_SUSPENDED ? objInvoke : Unit.INSTANCE;
    }
}
