package kotlinx.coroutines.internal;

import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.coroutines.CompletedExceptionally;
import kotlinx.coroutines.CompletedWithCancellation;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.DispatchedTask;
import kotlinx.coroutines.EventLoop;
import kotlinx.coroutines.ThreadLocalEventLoop;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class DispatchedContinuation extends DispatchedTask implements CoroutineStackFrame, Continuation {
    public final AtomicRef _reusableCancellableContinuation;
    public Object _state;
    public final Continuation continuation;
    public final Object countOrElement;
    public final CoroutineDispatcher dispatcher;

    public DispatchedContinuation(CoroutineDispatcher coroutineDispatcher, Continuation<Object> continuation) {
        super(-1);
        this.dispatcher = coroutineDispatcher;
        this.continuation = continuation;
        this._state = DispatchedContinuationKt.UNDEFINED;
        this.countOrElement = ThreadContextKt.threadContextElements(getContext());
        this._reusableCancellableContinuation = AtomicFU.atomic((Object) null);
    }

    @Override // kotlinx.coroutines.DispatchedTask
    /* renamed from: cancelCompletedResult$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    public final void mo283xd43a9d22(Object obj, Throwable th) {
        if (obj instanceof CompletedWithCancellation) {
            ((CompletedWithCancellation) obj).onCancellation.invoke(th);
        }
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
        CoroutineContext context;
        Object updateThreadContext;
        CoroutineContext context2 = this.continuation.getContext();
        Throwable m2859exceptionOrNullimpl = Result.m2859exceptionOrNullimpl(obj);
        Object completedExceptionally = m2859exceptionOrNullimpl == null ? obj : new CompletedExceptionally(m2859exceptionOrNullimpl, false, 2, null);
        if (this.dispatcher.isDispatchNeeded()) {
            this._state = completedExceptionally;
            this.resumeMode = 0;
            this.dispatcher.dispatch(context2, this);
            return;
        }
        ThreadLocalEventLoop.INSTANCE.getClass();
        EventLoop m295x4695df28 = ThreadLocalEventLoop.m295x4695df28();
        if (m295x4695df28.isUnconfinedLoopActive()) {
            this._state = completedExceptionally;
            this.resumeMode = 0;
            m295x4695df28.dispatchUnconfined(this);
            return;
        }
        m295x4695df28.incrementUseCount(true);
        try {
            context = getContext();
            updateThreadContext = ThreadContextKt.updateThreadContext(context, this.countOrElement);
        } finally {
            try {
            } finally {
            }
        }
        try {
            this.continuation.resumeWith(obj);
            Unit unit = Unit.INSTANCE;
            while (m295x4695df28.processUnconfinedEvent()) {
            }
        } finally {
            ThreadContextKt.restoreThreadContext(context, updateThreadContext);
        }
    }

    @Override // kotlinx.coroutines.DispatchedTask
    /* renamed from: takeState$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    public final Object mo288x99f628c6() {
        Object obj = this._state;
        this._state = DispatchedContinuationKt.UNDEFINED;
        return obj;
    }

    public final String toString() {
        return "DispatchedContinuation[" + this.dispatcher + ", " + DebugStringsKt.toDebugString(this.continuation) + "]";
    }

    @Override // kotlinx.coroutines.DispatchedTask
    /* renamed from: getDelegate$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    public final Continuation mo285xefbb4835() {
        return this;
    }
}
