package kotlinx.coroutines.internal;

import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.coroutines.CompletedExceptionally;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.DispatchedTask;
import kotlinx.coroutines.EventLoop;
import kotlinx.coroutines.ThreadLocalEventLoop;

/* loaded from: classes4.dex */
public final class DispatchedContinuation extends DispatchedTask implements CoroutineStackFrame, Continuation {
    public final AtomicRef _reusableCancellableContinuation;
    public Object _state;
    public final Continuation continuation;
    public final Object countOrElement;
    public final CoroutineDispatcher dispatcher;

    public DispatchedContinuation(CoroutineDispatcher coroutineDispatcher, Continuation continuation) {
        super(-1);
        this.dispatcher = coroutineDispatcher;
        this.continuation = continuation;
        this._state = DispatchedContinuationKt.UNDEFINED;
        this.countOrElement = ThreadContextKt.threadContextElements(continuation.getContext());
        this._reusableCancellableContinuation = AtomicFU.atomic((Object) null);
    }

    @Override // kotlin.coroutines.jvm.internal.CoroutineStackFrame
    public final CoroutineStackFrame getCallerFrame() {
        Continuation continuation = this.continuation;
        if (continuation instanceof CoroutineStackFrame) {
            return (CoroutineStackFrame) continuation;
        }
        return null;
    }

    @Override // kotlin.coroutines.Continuation
    public final CoroutineContext getContext() {
        return this.continuation.getContext();
    }

    @Override // kotlin.coroutines.Continuation
    public final void resumeWith(Object obj) {
        Throwable thM3441exceptionOrNullimpl = Result.m3441exceptionOrNullimpl(obj);
        Object completedExceptionally = thM3441exceptionOrNullimpl == null ? obj : new CompletedExceptionally(thM3441exceptionOrNullimpl, false, 2, null);
        if (DispatchedContinuationKt.safeIsDispatchNeeded(this.dispatcher, this.continuation.getContext())) {
            this._state = completedExceptionally;
            this.resumeMode = 0;
            DispatchedContinuationKt.safeDispatch(this.dispatcher, this.continuation.getContext(), this);
            return;
        }
        ThreadLocalEventLoop.INSTANCE.getClass();
        EventLoop eventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = ThreadLocalEventLoop.getEventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
        if (eventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.useCount >= 4294967296L) {
            this._state = completedExceptionally;
            this.resumeMode = 0;
            eventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.dispatchUnconfined(this);
            return;
        }
        eventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.incrementUseCount(true);
        try {
            CoroutineContext context = this.continuation.getContext();
            Object objUpdateThreadContext = ThreadContextKt.updateThreadContext(context, this.countOrElement);
            try {
                this.continuation.resumeWith(obj);
                Unit unit = Unit.INSTANCE;
                while (eventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.processUnconfinedEvent()) {
                }
            } finally {
                ThreadContextKt.restoreThreadContext(context, objUpdateThreadContext);
            }
        } finally {
            try {
            } finally {
            }
        }
    }

    @Override // kotlinx.coroutines.DispatchedTask
    public final Object takeState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() {
        Object obj = this._state;
        this._state = DispatchedContinuationKt.UNDEFINED;
        return obj;
    }

    public final String toString() {
        return "DispatchedContinuation[" + this.dispatcher + ", " + DebugStringsKt.toDebugString(this.continuation) + "]";
    }

    @Override // kotlinx.coroutines.DispatchedTask
    public final Continuation getDelegate$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() {
        return this;
    }
}
