package kotlinx.coroutines;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicInt;
import kotlinx.coroutines.internal.DispatchedContinuationKt;
import kotlinx.coroutines.internal.ScopeCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class DispatchedCoroutine extends ScopeCoroutine {
    public final AtomicInt _decision;

    public DispatchedCoroutine(CoroutineContext coroutineContext, Continuation continuation) {
        super(coroutineContext, continuation);
        this._decision = AtomicFU.atomic(0);
    }

    @Override // kotlinx.coroutines.internal.ScopeCoroutine, kotlinx.coroutines.JobSupport
    public final void afterCompletion(Object obj) {
        afterResume(obj);
    }

    @Override // kotlinx.coroutines.internal.ScopeCoroutine, kotlinx.coroutines.JobSupport
    public final void afterResume(Object obj) {
        AtomicInt atomicInt = this._decision;
        do {
            int i = atomicInt.value;
            if (i != 0) {
                if (i != 1) {
                    throw new IllegalStateException("Already resumed");
                }
                DispatchedContinuationKt.resumeCancellableWith(CompletionStateKt.recoverResult(obj), IntrinsicsKt__IntrinsicsJvmKt.intercepted(this.uCont));
                return;
            }
        } while (!this._decision.compareAndSet(0, 2));
    }
}
