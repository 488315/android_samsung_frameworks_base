package kotlinx.coroutines;

import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.coroutines.internal.DispatchedContinuation;
import kotlinx.coroutines.internal.DispatchedContinuationKt;
import kotlinx.coroutines.internal.Symbol;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ChildContinuation extends JobCancellingNode {
    public final CancellableContinuationImpl child;

    public ChildContinuation(CancellableContinuationImpl cancellableContinuationImpl) {
        this.child = cancellableContinuationImpl;
    }

    @Override // kotlin.jvm.functions.Function1
    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
        invoke((Throwable) obj);
        return Unit.INSTANCE;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x002e, code lost:
    
        r0 = true;
     */
    /* JADX WARN: Removed duplicated region for block: B:17:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:23:? A[RETURN, SYNTHETIC] */
    @Override // kotlinx.coroutines.CompletionHandlerBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void invoke(Throwable th) {
        boolean z;
        CancellableContinuationImpl cancellableContinuationImpl = this.child;
        Throwable continuationCancellationCause = cancellableContinuationImpl.getContinuationCancellationCause(getJob());
        if (cancellableContinuationImpl.isReusable()) {
            DispatchedContinuation dispatchedContinuation = (DispatchedContinuation) cancellableContinuationImpl.delegate;
            AtomicRef atomicRef = dispatchedContinuation._reusableCancellableContinuation;
            while (true) {
                Object obj = atomicRef.value;
                Symbol symbol = DispatchedContinuationKt.REUSABLE_CLAIMED;
                if (Intrinsics.areEqual(obj, symbol)) {
                    if (dispatchedContinuation._reusableCancellableContinuation.compareAndSet(symbol, continuationCancellationCause)) {
                        break;
                    }
                } else if (obj instanceof Throwable) {
                    break;
                } else if (dispatchedContinuation._reusableCancellableContinuation.compareAndSet(obj, null)) {
                    break;
                }
                if (z) {
                    cancellableContinuationImpl.cancel(continuationCancellationCause);
                    if (cancellableContinuationImpl.isReusable()) {
                        return;
                    }
                    cancellableContinuationImpl.m284x2343eba7();
                    return;
                }
                return;
            }
        }
        z = false;
        if (z) {
        }
    }
}
