package kotlinx.coroutines;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.concurrent.CancellationException;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicInt;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.coroutines.CancelHandler;
import kotlinx.coroutines.internal.DispatchedContinuation;
import kotlinx.coroutines.internal.DispatchedContinuationKt;
import kotlinx.coroutines.internal.Segment;
import kotlinx.coroutines.internal.Symbol;

/* loaded from: classes4.dex */
public class CancellableContinuationImpl extends DispatchedTask implements CancellableContinuation, CoroutineStackFrame, Waiter {
    public final AtomicInt _decisionAndIndex;
    public final AtomicRef _parentHandle;
    public final AtomicRef _state;
    public final CoroutineContext context;
    public final Continuation delegate;

    public CancellableContinuationImpl(Continuation continuation, int i) {
        super(i);
        this.delegate = continuation;
        this.context = continuation.getContext();
        this._decisionAndIndex = AtomicFU.atomic(536870911);
        this._state = AtomicFU.atomic(Active.INSTANCE);
        this._parentHandle = AtomicFU.atomic((Object) null);
    }

    public static void multipleHandlersError(NotCompleted notCompleted, Object obj) {
        throw new IllegalStateException(("It's prohibited to register multiple handlers, tried to register " + notCompleted + ", already has " + obj).toString());
    }

    public static Object resumedState(NotCompleted notCompleted, Object obj, int i, Function3 function3) {
        if (obj instanceof CompletedExceptionally) {
            return obj;
        }
        if (i != 1 && i != 2) {
            return obj;
        }
        if (function3 != null || (notCompleted instanceof CancelHandler)) {
            return new CompletedContinuation(obj, notCompleted instanceof CancelHandler ? (CancelHandler) notCompleted : null, function3, null, null, 16, null);
        }
        return obj;
    }

    public final void callCancelHandler(CancelHandler cancelHandler, Throwable th) {
        try {
            cancelHandler.invoke(th);
        } catch (Throwable th2) {
            CoroutineExceptionHandlerKt.handleCoroutineException(new CompletionHandlerException("Exception in invokeOnCancellation handler for " + this, th2), this.context);
        }
    }

    public final void callOnCancellation(Function3 function3, Throwable th, Object obj) {
        try {
            function3.invoke(th, obj, this.context);
        } catch (Throwable th2) {
            CoroutineExceptionHandlerKt.handleCoroutineException(new CompletionHandlerException("Exception in resume onCancellation handler for " + this, th2), this.context);
        }
    }

    public final void callSegmentOnCancellation(Segment segment, Throwable th) {
        int i = this._decisionAndIndex.value & 536870911;
        if (i == 536870911) {
            throw new IllegalStateException("The index for Segment.onCancellation(..) is broken");
        }
        try {
            segment.onCancellation(this.context, i);
        } catch (Throwable th2) {
            CoroutineExceptionHandlerKt.handleCoroutineException(new CompletionHandlerException("Exception in invokeOnCancellation handler for " + this, th2), this.context);
        }
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public final boolean cancel(Throwable th) {
        Object obj;
        AtomicRef atomicRef = this._state;
        do {
            obj = atomicRef.value;
            if (!(obj instanceof NotCompleted)) {
                return false;
            }
        } while (!this._state.compareAndSet(obj, new CancelledContinuation(this, th, (obj instanceof CancelHandler) || (obj instanceof Segment))));
        NotCompleted notCompleted = (NotCompleted) obj;
        if (notCompleted instanceof CancelHandler) {
            callCancelHandler((CancelHandler) obj, th);
        } else if (notCompleted instanceof Segment) {
            callSegmentOnCancellation((Segment) obj, th);
        }
        detachChildIfNonReusable();
        dispatchResume(this.resumeMode);
        return true;
    }

    @Override // kotlinx.coroutines.DispatchedTask
    public final void cancelCompletedResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Throwable th) {
        Throwable th2;
        AtomicRef atomicRef = this._state;
        while (true) {
            Object obj = atomicRef.value;
            if (obj instanceof NotCompleted) {
                throw new IllegalStateException("Not completed");
            }
            if (obj instanceof CompletedExceptionally) {
                return;
            }
            if (obj instanceof CompletedContinuation) {
                CompletedContinuation completedContinuation = (CompletedContinuation) obj;
                if (completedContinuation.cancelCause != null) {
                    throw new IllegalStateException("Must be called at most once");
                }
                if (this._state.compareAndSet(obj, CompletedContinuation.copy$default(completedContinuation, null, th, 15))) {
                    CancelHandler cancelHandler = completedContinuation.cancelHandler;
                    if (cancelHandler != null) {
                        callCancelHandler(cancelHandler, th);
                    }
                    Function3 function3 = completedContinuation.onCancellation;
                    if (function3 != null) {
                        callOnCancellation(function3, th, completedContinuation.result);
                        return;
                    }
                    return;
                }
                th2 = th;
            } else {
                th2 = th;
                if (this._state.compareAndSet(obj, new CompletedContinuation(obj, null, null, null, th2, 14, null))) {
                    return;
                }
            }
            th = th2;
        }
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public final void completeResume(Object obj) {
        dispatchResume(this.resumeMode);
    }

    public final void detachChild$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() {
        DisposableHandle disposableHandle = (DisposableHandle) this._parentHandle.value;
        if (disposableHandle == null) {
            return;
        }
        disposableHandle.dispose();
        this._parentHandle.setValue(NonDisposableHandle.INSTANCE);
    }

    public final void detachChildIfNonReusable() {
        if (isReusable()) {
            return;
        }
        detachChild$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
    }

    public final void dispatchResume(int i) {
        int i2;
        AtomicInt atomicInt = this._decisionAndIndex;
        do {
            i2 = atomicInt.value;
            int i3 = i2 >> 29;
            if (i3 != 0) {
                if (i3 != 1) {
                    throw new IllegalStateException("Already resumed");
                }
                Continuation continuation = this.delegate;
                boolean z = i == 4;
                if (!z && (continuation instanceof DispatchedContinuation)) {
                    boolean z2 = i == 1 || i == 2;
                    int i4 = this.resumeMode;
                    if (z2 == (i4 == 1 || i4 == 2)) {
                        DispatchedContinuation dispatchedContinuation = (DispatchedContinuation) continuation;
                        CoroutineDispatcher coroutineDispatcher = dispatchedContinuation.dispatcher;
                        CoroutineContext context = dispatchedContinuation.continuation.getContext();
                        if (DispatchedContinuationKt.safeIsDispatchNeeded(coroutineDispatcher, context)) {
                            DispatchedContinuationKt.safeDispatch(coroutineDispatcher, context, this);
                            return;
                        }
                        ThreadLocalEventLoop.INSTANCE.getClass();
                        EventLoop eventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = ThreadLocalEventLoop.getEventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
                        if (eventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.useCount >= 4294967296L) {
                            eventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.dispatchUnconfined(this);
                            return;
                        }
                        eventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.incrementUseCount(true);
                        try {
                            DispatchedTaskKt.resume(this, this.delegate, true);
                            do {
                            } while (eventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.processUnconfinedEvent());
                        } finally {
                            try {
                                return;
                            } finally {
                            }
                        }
                        return;
                    }
                }
                DispatchedTaskKt.resume(this, continuation, z);
                return;
            }
        } while (!this._decisionAndIndex.compareAndSet(i2, 1073741824 + (536870911 & i2)));
    }

    @Override // kotlin.coroutines.jvm.internal.CoroutineStackFrame
    public final CoroutineStackFrame getCallerFrame() {
        Continuation continuation = this.delegate;
        if (continuation instanceof CoroutineStackFrame) {
            return (CoroutineStackFrame) continuation;
        }
        return null;
    }

    @Override // kotlin.coroutines.Continuation
    public final CoroutineContext getContext() {
        return this.context;
    }

    public Throwable getContinuationCancellationCause(JobSupport jobSupport) {
        return jobSupport.getCancellationException();
    }

    @Override // kotlinx.coroutines.DispatchedTask
    public final Continuation getDelegate$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() {
        return this.delegate;
    }

    @Override // kotlinx.coroutines.DispatchedTask
    public final Throwable getExceptionalResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Object obj) {
        Throwable exceptionalResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = super.getExceptionalResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(obj);
        if (exceptionalResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host != null) {
            return exceptionalResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host;
        }
        return null;
    }

    public final Object getResult() {
        int i;
        Job job;
        Job job2;
        boolean zIsReusable = isReusable();
        AtomicInt atomicInt = this._decisionAndIndex;
        do {
            i = atomicInt.value;
            int i2 = i >> 29;
            if (i2 != 0) {
                if (i2 != 2) {
                    throw new IllegalStateException("Already suspended");
                }
                if (zIsReusable) {
                    releaseClaimedReusableContinuation$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
                }
                Object obj = this._state.value;
                if (obj instanceof CompletedExceptionally) {
                    throw ((CompletedExceptionally) obj).cause;
                }
                int i3 = this.resumeMode;
                if ((i3 != 1 && i3 != 2) || (job = (Job) this.context.get(Job.Key)) == null || job.isActive()) {
                    return getSuccessfulResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(obj);
                }
                CancellationException cancellationException = job.getCancellationException();
                cancelCompletedResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(cancellationException);
                throw cancellationException;
            }
        } while (!this._decisionAndIndex.compareAndSet(i, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS + (536870911 & i)));
        if (((DisposableHandle) this._parentHandle.value) == null && (job2 = (Job) this.context.get(Job.Key)) != null) {
            this._parentHandle.compareAndSet(null, JobKt.invokeOnCompletion$default(job2, new ChildContinuation(this)));
        }
        if (zIsReusable) {
            releaseClaimedReusableContinuation$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
        }
        return CoroutineSingletons.COROUTINE_SUSPENDED;
    }

    @Override // kotlinx.coroutines.DispatchedTask
    public final Object getSuccessfulResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Object obj) {
        return obj instanceof CompletedContinuation ? ((CompletedContinuation) obj).result : obj;
    }

    public final void initCancellability() {
        Job job = (Job) this.context.get(Job.Key);
        DisposableHandle disposableHandle = null;
        if (job != null) {
            DisposableHandle disposableHandleInvokeOnCompletion$default = JobKt.invokeOnCompletion$default(job, new ChildContinuation(this));
            this._parentHandle.compareAndSet(null, disposableHandleInvokeOnCompletion$default);
            disposableHandle = disposableHandleInvokeOnCompletion$default;
        }
        if (disposableHandle != null && isCompleted()) {
            disposableHandle.dispose();
            this._parentHandle.setValue(NonDisposableHandle.INSTANCE);
        }
    }

    @Override // kotlinx.coroutines.Waiter
    public final void invokeOnCancellation(Segment segment, int i) {
        int i2;
        AtomicInt atomicInt = this._decisionAndIndex;
        do {
            i2 = atomicInt.value;
            if ((i2 & 536870911) != 536870911) {
                throw new IllegalStateException("invokeOnCancellation should be called at most once");
            }
        } while (!atomicInt.compareAndSet(i2, ((i2 >> 29) << 29) + i));
        invokeOnCancellationImpl(segment);
    }

    /* JADX WARN: Code restructure failed: missing block: B:54:0x0096, code lost:
    
        multipleHandlersError(r10, r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0099, code lost:
    
        throw null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void invokeOnCancellationImpl(NotCompleted notCompleted) {
        AtomicRef atomicRef = this._state;
        while (true) {
            Object obj = atomicRef.value;
            if (!(obj instanceof Active)) {
                if ((obj instanceof CancelHandler) || (obj instanceof Segment)) {
                    break;
                }
                if (obj instanceof CompletedExceptionally) {
                    CompletedExceptionally completedExceptionally = (CompletedExceptionally) obj;
                    if (!completedExceptionally._handled.compareAndSet()) {
                        multipleHandlersError(notCompleted, obj);
                        throw null;
                    }
                    if (obj instanceof CancelledContinuation) {
                        if (!(obj instanceof CompletedExceptionally)) {
                            completedExceptionally = null;
                        }
                        Throwable th = completedExceptionally != null ? completedExceptionally.cause : null;
                        if (notCompleted instanceof CancelHandler) {
                            callCancelHandler((CancelHandler) notCompleted, th);
                            return;
                        } else {
                            callSegmentOnCancellation((Segment) notCompleted, th);
                            return;
                        }
                    }
                    return;
                }
                if (obj instanceof CompletedContinuation) {
                    CompletedContinuation completedContinuation = (CompletedContinuation) obj;
                    if (completedContinuation.cancelHandler != null) {
                        multipleHandlersError(notCompleted, obj);
                        throw null;
                    }
                    if (notCompleted instanceof Segment) {
                        return;
                    }
                    CancelHandler cancelHandler = (CancelHandler) notCompleted;
                    Throwable th2 = completedContinuation.cancelCause;
                    if (th2 != null) {
                        callCancelHandler(cancelHandler, th2);
                        return;
                    } else {
                        if (this._state.compareAndSet(obj, CompletedContinuation.copy$default(completedContinuation, cancelHandler, null, 29))) {
                            return;
                        }
                    }
                } else {
                    if (notCompleted instanceof Segment) {
                        return;
                    }
                    if (this._state.compareAndSet(obj, new CompletedContinuation(obj, (CancelHandler) notCompleted, null, null, null, 28, null))) {
                        return;
                    }
                }
            } else if (this._state.compareAndSet(obj, notCompleted)) {
                return;
            }
        }
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public final boolean isActive() {
        return this._state.value instanceof NotCompleted;
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public final boolean isCancelled$1() {
        return this._state.value instanceof CancelledContinuation;
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public final boolean isCompleted() {
        return !(this._state.value instanceof NotCompleted);
    }

    public final boolean isReusable() {
        return this.resumeMode == 2 && ((DispatchedContinuation) this.delegate)._reusableCancellableContinuation.value != null;
    }

    public String nameString() {
        return "CancellableContinuation";
    }

    public final void releaseClaimedReusableContinuation$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() {
        Continuation continuation = this.delegate;
        Throwable th = null;
        DispatchedContinuation dispatchedContinuation = continuation instanceof DispatchedContinuation ? (DispatchedContinuation) continuation : null;
        if (dispatchedContinuation != null) {
            AtomicRef atomicRef = dispatchedContinuation._reusableCancellableContinuation;
            while (true) {
                Object obj = atomicRef.value;
                Symbol symbol = DispatchedContinuationKt.REUSABLE_CLAIMED;
                if (obj == symbol) {
                    if (dispatchedContinuation._reusableCancellableContinuation.compareAndSet(symbol, this)) {
                        break;
                    }
                } else {
                    if (!(obj instanceof Throwable)) {
                        throw new IllegalStateException(("Inconsistent state " + obj).toString());
                    }
                    if (!dispatchedContinuation._reusableCancellableContinuation.compareAndSet(obj, null)) {
                        throw new IllegalArgumentException("Failed requirement.");
                    }
                    th = (Throwable) obj;
                }
            }
            if (th == null) {
                return;
            }
            detachChild$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
            cancel(th);
        }
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public final void resume(Unit unit, final Function1 function1) {
        resumeImpl$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(unit, this.resumeMode, function1 != null ? new Function3() { // from class: kotlinx.coroutines.CancellableContinuationImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                function1.mo781invoke((Throwable) obj);
                return Unit.INSTANCE;
            }
        } : null);
    }

    public final void resumeImpl$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Object obj, int i, Function3 function3) {
        Object obj2;
        AtomicRef atomicRef = this._state;
        do {
            obj2 = atomicRef.value;
            if (!(obj2 instanceof NotCompleted)) {
                if (obj2 instanceof CancelledContinuation) {
                    CancelledContinuation cancelledContinuation = (CancelledContinuation) obj2;
                    if (cancelledContinuation._resumed.compareAndSet()) {
                        if (function3 != null) {
                            callOnCancellation(function3, cancelledContinuation.cause, obj);
                            return;
                        }
                        return;
                    }
                }
                throw new IllegalStateException(("Already resumed, but proposed with update " + obj).toString());
            }
        } while (!this._state.compareAndSet(obj2, resumedState((NotCompleted) obj2, obj, i, function3)));
        detachChildIfNonReusable();
        dispatchResume(i);
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public final void resumeUndispatched(CoroutineDispatcher coroutineDispatcher, Unit unit) {
        Continuation continuation = this.delegate;
        DispatchedContinuation dispatchedContinuation = continuation instanceof DispatchedContinuation ? (DispatchedContinuation) continuation : null;
        resumeImpl$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(unit, (dispatchedContinuation != null ? dispatchedContinuation.dispatcher : null) == coroutineDispatcher ? 4 : this.resumeMode, null);
    }

    @Override // kotlin.coroutines.Continuation
    public final void resumeWith(Object obj) {
        Throwable thM3442exceptionOrNullimpl = Result.m3442exceptionOrNullimpl(obj);
        if (thM3442exceptionOrNullimpl != null) {
            obj = new CompletedExceptionally(thM3442exceptionOrNullimpl, false, 2, null);
        }
        resumeImpl$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(obj, this.resumeMode, null);
    }

    @Override // kotlinx.coroutines.DispatchedTask
    public final Object takeState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() {
        return this._state.value;
    }

    public final String toString() {
        String strNameString = nameString();
        String debugString = DebugStringsKt.toDebugString(this.delegate);
        Object obj = this._state.value;
        String str = obj instanceof NotCompleted ? "Active" : obj instanceof CancelledContinuation ? "Cancelled" : "Completed";
        String hexAddress = DebugStringsKt.getHexAddress(this);
        StringBuilder sb = new StringBuilder();
        sb.append(strNameString);
        sb.append("(");
        sb.append(debugString);
        sb.append("){");
        sb.append(str);
        return TransitionKt$$ExternalSyntheticOutline0.m(sb, "}@", hexAddress);
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public final Symbol tryResume(Object obj, Function3 function3) {
        return tryResumeImpl(obj, function3);
    }

    public final Symbol tryResumeImpl(Object obj, Function3 function3) {
        Object obj2;
        AtomicRef atomicRef = this._state;
        do {
            obj2 = atomicRef.value;
            if (!(obj2 instanceof NotCompleted)) {
                boolean z = obj2 instanceof CompletedContinuation;
                return null;
            }
        } while (!this._state.compareAndSet(obj2, resumedState((NotCompleted) obj2, obj, this.resumeMode, function3)));
        detachChildIfNonReusable();
        return CancellableContinuationImplKt.RESUME_TOKEN;
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public final Symbol tryResumeWithException(Throwable th) {
        return tryResumeImpl(new CompletedExceptionally(th, false, 2, null), null);
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public final void resume(Object obj, Function3 function3) {
        resumeImpl$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(obj, this.resumeMode, function3);
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public final void invokeOnCancellation(Function1 function1) {
        invokeOnCancellationImpl(new CancelHandler.UserSupplied(function1));
    }
}
