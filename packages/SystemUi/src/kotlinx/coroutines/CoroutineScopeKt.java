package kotlinx.coroutines;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.internal.ContextScope;
import kotlinx.coroutines.internal.ScopeCoroutine;
import kotlinx.coroutines.intrinsics.UndispatchedKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class CoroutineScopeKt {
    public static final ContextScope CoroutineScope(CoroutineContext coroutineContext) {
        if (coroutineContext.get(Job.Key) == null) {
            coroutineContext = coroutineContext.plus(new JobImpl(null));
        }
        return new ContextScope(coroutineContext);
    }

    public static void cancel$default(CoroutineScope coroutineScope) {
        Job job = (Job) coroutineScope.getCoroutineContext().get(Job.Key);
        if (job != null) {
            job.cancel(null);
        } else {
            throw new IllegalStateException(("Scope cannot be cancelled because it does not have a job: " + coroutineScope).toString());
        }
    }

    public static final Object coroutineScope(Function2 function2, Continuation continuation) {
        ScopeCoroutine scopeCoroutine = new ScopeCoroutine(continuation.getContext(), continuation);
        Object startUndispatchedOrReturn = UndispatchedKt.startUndispatchedOrReturn(scopeCoroutine, scopeCoroutine, function2);
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        return startUndispatchedOrReturn;
    }
}
