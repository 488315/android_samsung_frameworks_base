package kotlin.sequences;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;

/* loaded from: classes4.dex */
public abstract class SequenceScope {
    public abstract CoroutineSingletons yield(Object obj, Continuation continuation);

    public final Object yieldAll(Iterable iterable, RestrictedSuspendLambda restrictedSuspendLambda) {
        if ((iterable instanceof Collection) && ((Collection) iterable).isEmpty()) {
            return Unit.INSTANCE;
        }
        Object objYieldAll = yieldAll(iterable.iterator(), restrictedSuspendLambda);
        return objYieldAll == CoroutineSingletons.COROUTINE_SUSPENDED ? objYieldAll : Unit.INSTANCE;
    }

    public abstract Object yieldAll(Iterator it, RestrictedSuspendLambda restrictedSuspendLambda);
}
