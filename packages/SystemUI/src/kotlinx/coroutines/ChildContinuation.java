package kotlinx.coroutines;

import kotlin.jvm.internal.Intrinsics;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.coroutines.internal.DispatchedContinuation;
import kotlinx.coroutines.internal.DispatchedContinuationKt;
import kotlinx.coroutines.internal.Symbol;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class ChildContinuation extends JobNode {
    public final CancellableContinuationImpl child;

    public ChildContinuation(CancellableContinuationImpl cancellableContinuationImpl) {
        this.child = cancellableContinuationImpl;
    }

    @Override // kotlinx.coroutines.JobNode
    public final boolean getOnCancelling() {
        return true;
    }

    @Override // kotlinx.coroutines.JobNode
    public final void invoke(Throwable th) {
        CancellableContinuationImpl cancellableContinuationImpl = this.child;
        JobSupport jobSupport = this.job;
        if (jobSupport == null) {
            jobSupport = null;
        }
        Throwable continuationCancellationCause = cancellableContinuationImpl.getContinuationCancellationCause(jobSupport);
        if (cancellableContinuationImpl.isReusable()) {
            DispatchedContinuation dispatchedContinuation = (DispatchedContinuation) cancellableContinuationImpl.delegate;
            AtomicRef atomicRef = dispatchedContinuation._reusableCancellableContinuation;
            while (true) {
                Object obj = atomicRef.value;
                Symbol symbol = DispatchedContinuationKt.REUSABLE_CLAIMED;
                if (Intrinsics.areEqual(obj, symbol)) {
                    if (dispatchedContinuation._reusableCancellableContinuation.compareAndSet(symbol, continuationCancellationCause)) {
                        return;
                    }
                } else {
                    if (obj instanceof Throwable) {
                        return;
                    }
                    if (dispatchedContinuation._reusableCancellableContinuation.compareAndSet(obj, null)) {
                        break;
                    }
                }
            }
        }
        cancellableContinuationImpl.cancel(continuationCancellationCause);
        cancellableContinuationImpl.detachChildIfNonReusable();
    }
}
