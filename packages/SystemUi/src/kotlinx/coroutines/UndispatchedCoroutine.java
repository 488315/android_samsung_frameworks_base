package kotlinx.coroutines;

import kotlin.Pair;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.internal.ScopeCoroutine;
import kotlinx.coroutines.internal.ThreadContextKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class UndispatchedCoroutine extends ScopeCoroutine {
    public final ThreadLocal threadStateToRecover;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public UndispatchedCoroutine(CoroutineContext coroutineContext, Continuation<Object> continuation) {
        super(coroutineContext.get(r0) == null ? coroutineContext.plus(r0) : coroutineContext, continuation);
        UndispatchedMarker undispatchedMarker = UndispatchedMarker.INSTANCE;
        ThreadLocal threadLocal = new ThreadLocal();
        this.threadStateToRecover = threadLocal;
        if (continuation.getContext().get(ContinuationInterceptor.Key) instanceof CoroutineDispatcher) {
            return;
        }
        Object updateThreadContext = ThreadContextKt.updateThreadContext(coroutineContext, null);
        ThreadContextKt.restoreThreadContext(coroutineContext, updateThreadContext);
        threadLocal.set(new Pair(coroutineContext, updateThreadContext));
    }

    @Override // kotlinx.coroutines.internal.ScopeCoroutine, kotlinx.coroutines.JobSupport
    public final void afterResume(Object obj) {
        ThreadLocal threadLocal = this.threadStateToRecover;
        Pair pair = (Pair) threadLocal.get();
        if (pair != null) {
            ThreadContextKt.restoreThreadContext((CoroutineContext) pair.component1(), pair.component2());
            threadLocal.set(null);
        }
        Object recoverResult = CompletionStateKt.recoverResult(obj);
        Continuation continuation = this.uCont;
        CoroutineContext context = continuation.getContext();
        Object updateThreadContext = ThreadContextKt.updateThreadContext(context, null);
        UndispatchedCoroutine updateUndispatchedCompletion = updateThreadContext != ThreadContextKt.NO_THREAD_ELEMENTS ? CoroutineContextKt.updateUndispatchedCompletion(continuation, context, updateThreadContext) : null;
        try {
            continuation.resumeWith(recoverResult);
            Unit unit = Unit.INSTANCE;
        } finally {
            if (updateUndispatchedCompletion == null || updateUndispatchedCompletion.clearThreadContext()) {
                ThreadContextKt.restoreThreadContext(context, updateThreadContext);
            }
        }
    }

    public final boolean clearThreadContext() {
        ThreadLocal threadLocal = this.threadStateToRecover;
        if (threadLocal.get() == null) {
            return false;
        }
        threadLocal.set(null);
        return true;
    }
}
