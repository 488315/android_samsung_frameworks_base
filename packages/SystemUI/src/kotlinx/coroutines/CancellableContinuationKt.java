package kotlinx.coroutines;

import kotlin.coroutines.Continuation;
import kotlinx.atomicfu.AtomicInt;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.atomicfu.TraceBase;
import kotlinx.coroutines.internal.DispatchedContinuation;
import kotlinx.coroutines.internal.DispatchedContinuationKt;

/* loaded from: classes4.dex */
public abstract class CancellableContinuationKt {
    public static final CancellableContinuationImpl getOrCreateCancellableContinuation(Continuation continuation) {
        CancellableContinuationImpl cancellableContinuationImpl;
        CancellableContinuationImpl cancellableContinuationImpl2;
        if (!(continuation instanceof DispatchedContinuation)) {
            return new CancellableContinuationImpl(continuation, 1);
        }
        DispatchedContinuation dispatchedContinuation = (DispatchedContinuation) continuation;
        AtomicRef atomicRef = dispatchedContinuation._reusableCancellableContinuation;
        while (true) {
            Object obj = atomicRef.value;
            cancellableContinuationImpl = null;
            if (obj == null) {
                dispatchedContinuation._reusableCancellableContinuation.setValue(DispatchedContinuationKt.REUSABLE_CLAIMED);
                cancellableContinuationImpl2 = null;
                break;
            }
            if (obj instanceof CancellableContinuationImpl) {
                if (dispatchedContinuation._reusableCancellableContinuation.compareAndSet(obj, DispatchedContinuationKt.REUSABLE_CLAIMED)) {
                    cancellableContinuationImpl2 = (CancellableContinuationImpl) obj;
                    break;
                }
            } else if (obj != DispatchedContinuationKt.REUSABLE_CLAIMED && !(obj instanceof Throwable)) {
                throw new IllegalStateException(("Inconsistent state " + obj).toString());
            }
        }
        if (cancellableContinuationImpl2 != null) {
            Object obj2 = cancellableContinuationImpl2._state.value;
            if (!(obj2 instanceof CompletedContinuation) || ((CompletedContinuation) obj2).idempotentResume == null) {
                AtomicInt atomicInt = cancellableContinuationImpl2._decisionAndIndex;
                atomicInt.value = 536870911;
                TraceBase traceBase = atomicInt.trace;
                if (traceBase != TraceBase.None.INSTANCE) {
                    traceBase.getClass();
                }
                cancellableContinuationImpl2._state.setValue(Active.INSTANCE);
                cancellableContinuationImpl = cancellableContinuationImpl2;
            } else {
                cancellableContinuationImpl2.detachChild$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
            }
            if (cancellableContinuationImpl != null) {
                return cancellableContinuationImpl;
            }
        }
        return new CancellableContinuationImpl(continuation, 2);
    }
}
