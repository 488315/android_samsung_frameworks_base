package kotlinx.coroutines;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicInt;
import kotlinx.coroutines.internal.DispatchedContinuationKt;
import kotlinx.coroutines.internal.ScopeCoroutine;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class DispatchedCoroutine extends ScopeCoroutine {
    public final AtomicInt _decision;

    public DispatchedCoroutine(CoroutineContext coroutineContext, Continuation<Object> continuation) {
        super(coroutineContext, continuation);
        this._decision = AtomicFU.atomic();
    }

    @Override // kotlinx.coroutines.internal.ScopeCoroutine, kotlinx.coroutines.JobSupport
    public final void afterCompletion(Object obj) {
        afterResume(obj);
    }

    @Override // kotlinx.coroutines.internal.ScopeCoroutine, kotlinx.coroutines.JobSupport
    public final void afterResume(Object obj) {
        boolean z;
        AtomicInt atomicInt = this._decision;
        while (true) {
            int i = atomicInt.value;
            z = false;
            if (i != 0) {
                if (i != 1) {
                    throw new IllegalStateException("Already resumed".toString());
                }
            } else if (this._decision.compareAndSet(0, 2)) {
                z = true;
                break;
            }
        }
        if (z) {
            return;
        }
        DispatchedContinuationKt.resumeCancellableWith(IntrinsicsKt__IntrinsicsJvmKt.intercepted(this.uCont), CompletionStateKt.recoverResult(obj), null);
    }
}
