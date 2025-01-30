package kotlinx.coroutines;

import kotlin.coroutines.Continuation;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.coroutines.internal.DispatchedContinuation;
import kotlinx.coroutines.internal.DispatchedContinuationKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class CancellableContinuationKt {
    public static final CancellableContinuationImpl getOrCreateCancellableContinuation(Continuation continuation) {
        CancellableContinuationImpl cancellableContinuationImpl;
        boolean z = true;
        if (!(continuation instanceof DispatchedContinuation)) {
            return new CancellableContinuationImpl(continuation, 1);
        }
        DispatchedContinuation dispatchedContinuation = (DispatchedContinuation) continuation;
        AtomicRef atomicRef = dispatchedContinuation._reusableCancellableContinuation;
        while (true) {
            Object obj = atomicRef.value;
            if (obj == null) {
                dispatchedContinuation._reusableCancellableContinuation.setValue(DispatchedContinuationKt.REUSABLE_CLAIMED);
                cancellableContinuationImpl = null;
                break;
            }
            if (obj instanceof CancellableContinuationImpl) {
                if (dispatchedContinuation._reusableCancellableContinuation.compareAndSet(obj, DispatchedContinuationKt.REUSABLE_CLAIMED)) {
                    cancellableContinuationImpl = (CancellableContinuationImpl) obj;
                    break;
                }
            } else if (obj != DispatchedContinuationKt.REUSABLE_CLAIMED && !(obj instanceof Throwable)) {
                throw new IllegalStateException(("Inconsistent state " + obj).toString());
            }
        }
        if (cancellableContinuationImpl != null) {
            Object obj2 = cancellableContinuationImpl._state.value;
            if (!(obj2 instanceof CompletedContinuation) || ((CompletedContinuation) obj2).idempotentResume == null) {
                cancellableContinuationImpl._decision.setValue(0);
                cancellableContinuationImpl._state.setValue(Active.INSTANCE);
            } else {
                cancellableContinuationImpl.m284x2343eba7();
                z = false;
            }
            CancellableContinuationImpl cancellableContinuationImpl2 = z ? cancellableContinuationImpl : null;
            if (cancellableContinuationImpl2 != null) {
                return cancellableContinuationImpl2;
            }
        }
        return new CancellableContinuationImpl(continuation, 2);
    }
}
