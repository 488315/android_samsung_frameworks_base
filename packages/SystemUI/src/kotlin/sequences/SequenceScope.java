package kotlin.sequences;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class SequenceScope {
    public abstract CoroutineSingletons yield(Object obj, Continuation continuation);

    public final Object yieldAll(Iterable iterable, RestrictedSuspendLambda restrictedSuspendLambda) {
        if ((iterable instanceof Collection) && ((Collection) iterable).isEmpty()) {
            return Unit.INSTANCE;
        }
        Object yieldAll = yieldAll(iterable.iterator(), restrictedSuspendLambda);
        return yieldAll == CoroutineSingletons.COROUTINE_SUSPENDED ? yieldAll : Unit.INSTANCE;
    }

    public abstract Object yieldAll(Iterator it, RestrictedSuspendLambda restrictedSuspendLambda);
}
