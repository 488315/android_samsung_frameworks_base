package kotlin.coroutines.jvm.internal;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.internal.DispatchedContinuation;
import kotlinx.coroutines.internal.DispatchedContinuationKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class ContinuationImpl extends BaseContinuationImpl {
    private final CoroutineContext _context;
    private transient Continuation intercepted;

    public ContinuationImpl(Continuation continuation, CoroutineContext coroutineContext) {
        super(continuation);
        this._context = coroutineContext;
    }

    @Override // kotlin.coroutines.Continuation
    public CoroutineContext getContext() {
        CoroutineContext coroutineContext = this._context;
        coroutineContext.getClass();
        return coroutineContext;
    }

    public final Continuation intercepted() {
        Continuation continuation = this.intercepted;
        if (continuation != null) {
            return continuation;
        }
        ContinuationInterceptor continuationInterceptor = (ContinuationInterceptor) getContext().get(ContinuationInterceptor.Key);
        Continuation dispatchedContinuation = continuationInterceptor != null ? new DispatchedContinuation((CoroutineDispatcher) continuationInterceptor, this) : this;
        this.intercepted = dispatchedContinuation;
        return dispatchedContinuation;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public void releaseIntercepted() {
        Continuation continuation = this.intercepted;
        if (continuation != null && continuation != this) {
            CoroutineContext.Element element = getContext().get(ContinuationInterceptor.Key);
            element.getClass();
            DispatchedContinuation dispatchedContinuation = (DispatchedContinuation) continuation;
            while (dispatchedContinuation._reusableCancellableContinuation.value == DispatchedContinuationKt.REUSABLE_CLAIMED) {
            }
            Object obj = dispatchedContinuation._reusableCancellableContinuation.value;
            CancellableContinuationImpl cancellableContinuationImpl = obj instanceof CancellableContinuationImpl ? (CancellableContinuationImpl) obj : null;
            if (cancellableContinuationImpl != null) {
                cancellableContinuationImpl.detachChild$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
            }
        }
        this.intercepted = CompletedContinuation.INSTANCE;
    }

    public ContinuationImpl(Continuation continuation) {
        this(continuation, continuation != null ? continuation.getContext() : null);
    }
}
