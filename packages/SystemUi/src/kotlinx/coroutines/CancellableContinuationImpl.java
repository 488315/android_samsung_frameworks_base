package kotlinx.coroutines;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.functions.Function1;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicInt;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.internal.DispatchedContinuation;
import kotlinx.coroutines.internal.DispatchedContinuationKt;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.Symbol;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class CancellableContinuationImpl extends DispatchedTask implements CancellableContinuation, CoroutineStackFrame {
    public final AtomicInt _decision;
    public final AtomicRef _state;
    public final CoroutineContext context;
    public final Continuation delegate;
    public DisposableHandle parentHandle;

    public CancellableContinuationImpl(Continuation<Object> continuation, int i) {
        super(i);
        this.delegate = continuation;
        this.context = continuation.getContext();
        this._decision = AtomicFU.atomic();
        this._state = AtomicFU.atomic(Active.INSTANCE);
    }

    public static void multipleHandlersError(Object obj, Function1 function1) {
        throw new IllegalStateException(("It's prohibited to register multiple handlers, tried to register " + function1 + ", already has " + obj).toString());
    }

    public static Object resumedState(NotCompleted notCompleted, Object obj, int i, Function1 function1, LockFreeLinkedListNode.AbstractAtomicDesc abstractAtomicDesc) {
        if (obj instanceof CompletedExceptionally) {
            return obj;
        }
        boolean z = true;
        if (i != 1 && i != 2) {
            z = false;
        }
        if (!z && abstractAtomicDesc == null) {
            return obj;
        }
        if (function1 != null || (((notCompleted instanceof CancelHandler) && !(notCompleted instanceof BeforeResumeCancelHandler)) || abstractAtomicDesc != null)) {
            return new CompletedContinuation(obj, notCompleted instanceof CancelHandler ? (CancelHandler) notCompleted : null, function1, abstractAtomicDesc, null, 16, null);
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

    public final void callOnCancellation(Function1 function1, Throwable th) {
        try {
            function1.invoke(th);
        } catch (Throwable th2) {
            CoroutineExceptionHandlerKt.handleCoroutineException(new CompletionHandlerException("Exception in resume onCancellation handler for " + this, th2), this.context);
        }
    }

    public final void cancel(Throwable th) {
        Object obj;
        boolean z;
        AtomicRef atomicRef = this._state;
        do {
            obj = atomicRef.value;
            if (!(obj instanceof NotCompleted)) {
                return;
            } else {
                z = obj instanceof CancelHandler;
            }
        } while (!this._state.compareAndSet(obj, new CancelledContinuation(this, th, z)));
        CancelHandler cancelHandler = z ? (CancelHandler) obj : null;
        if (cancelHandler != null) {
            callCancelHandler(cancelHandler, th);
        }
        if (!isReusable()) {
            m284x2343eba7();
        }
        dispatchResume(this.resumeMode);
    }

    @Override // kotlinx.coroutines.DispatchedTask
    /* renamed from: cancelCompletedResult$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    public final void mo283xd43a9d22(Object obj, Throwable th) {
        AtomicRef atomicRef = this._state;
        while (true) {
            Object obj2 = atomicRef.value;
            if (obj2 instanceof NotCompleted) {
                throw new IllegalStateException("Not completed".toString());
            }
            if (obj2 instanceof CompletedExceptionally) {
                return;
            }
            if (obj2 instanceof CompletedContinuation) {
                CompletedContinuation completedContinuation = (CompletedContinuation) obj2;
                if (!(!(completedContinuation.cancelCause != null))) {
                    throw new IllegalStateException("Must be called at most once".toString());
                }
                if (this._state.compareAndSet(obj2, CompletedContinuation.copy$default(completedContinuation, null, th, 15))) {
                    CancelHandler cancelHandler = completedContinuation.cancelHandler;
                    if (cancelHandler != null) {
                        callCancelHandler(cancelHandler, th);
                    }
                    Function1 function1 = completedContinuation.onCancellation;
                    if (function1 != null) {
                        callOnCancellation(function1, th);
                        return;
                    }
                    return;
                }
            } else if (this._state.compareAndSet(obj2, new CompletedContinuation(obj2, null, null, null, th, 14, null))) {
                return;
            }
        }
    }

    /* renamed from: detachChild$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    public final void m284x2343eba7() {
        DisposableHandle disposableHandle = this.parentHandle;
        if (disposableHandle == null) {
            return;
        }
        disposableHandle.dispose();
        this.parentHandle = NonDisposableHandle.INSTANCE;
    }

    /* JADX WARN: Finally extract failed */
    public final void dispatchResume(int i) {
        boolean z;
        AtomicInt atomicInt = this._decision;
        while (true) {
            int i2 = atomicInt.value;
            if (i2 != 0) {
                if (i2 != 1) {
                    throw new IllegalStateException("Already resumed".toString());
                }
                z = false;
            } else if (this._decision.compareAndSet(0, 2)) {
                z = true;
                break;
            }
        }
        if (z) {
            return;
        }
        Continuation continuation = this.delegate;
        boolean z2 = i == 4;
        if (!z2 && (continuation instanceof DispatchedContinuation)) {
            boolean z3 = i == 1 || i == 2;
            int i3 = this.resumeMode;
            if (z3 == (i3 == 1 || i3 == 2)) {
                CoroutineDispatcher coroutineDispatcher = ((DispatchedContinuation) continuation).dispatcher;
                CoroutineContext context = continuation.getContext();
                if (coroutineDispatcher.isDispatchNeeded()) {
                    coroutineDispatcher.dispatch(context, this);
                    return;
                }
                ThreadLocalEventLoop.INSTANCE.getClass();
                EventLoop m295x4695df28 = ThreadLocalEventLoop.m295x4695df28();
                if (m295x4695df28.isUnconfinedLoopActive()) {
                    m295x4695df28.dispatchUnconfined(this);
                    return;
                }
                m295x4695df28.incrementUseCount(true);
                try {
                    DispatchedTaskKt.resume(this, this.delegate, true);
                    do {
                    } while (m295x4695df28.processUnconfinedEvent());
                } catch (Throwable th) {
                    try {
                        handleFatalException(th, null);
                    } finally {
                        m295x4695df28.decrementUseCount(true);
                    }
                }
                return;
            }
        }
        DispatchedTaskKt.resume(this, continuation, z2);
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
    /* renamed from: getDelegate$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    public final Continuation mo285xefbb4835() {
        return this.delegate;
    }

    @Override // kotlinx.coroutines.DispatchedTask
    /* renamed from: getExceptionalResult$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    public final Throwable mo286xd8447f2f(Object obj) {
        Throwable mo286xd8447f2f = super.mo286xd8447f2f(obj);
        if (mo286xd8447f2f != null) {
            return mo286xd8447f2f;
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x002a, code lost:
    
        if (r6.parentHandle != null) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x002c, code lost:
    
        installParentHandle();
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x002f, code lost:
    
        if (r0 == false) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0031, code lost:
    
        releaseClaimedReusableContinuation();
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0036, code lost:
    
        return kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0037, code lost:
    
        if (r0 == false) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0039, code lost:
    
        releaseClaimedReusableContinuation();
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x003c, code lost:
    
        r0 = r6._state.value;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0042, code lost:
    
        if ((r0 instanceof kotlinx.coroutines.CompletedExceptionally) != false) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0044, code lost:
    
        r1 = r6.resumeMode;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0046, code lost:
    
        if (r1 == 1) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0048, code lost:
    
        if (r1 != 2) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x004b, code lost:
    
        r4 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x004c, code lost:
    
        if (r4 == false) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x004e, code lost:
    
        r1 = (kotlinx.coroutines.Job) r6.context.get(kotlinx.coroutines.Job.Key);
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0058, code lost:
    
        if (r1 == null) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x005e, code lost:
    
        if (r1.isActive() == false) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0061, code lost:
    
        r1 = ((kotlinx.coroutines.JobSupport) r1).getCancellationException();
        mo283xd43a9d22(r0, r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x006a, code lost:
    
        throw r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x006f, code lost:
    
        return mo287x119202a3(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0074, code lost:
    
        throw ((kotlinx.coroutines.CompletedExceptionally) r0).cause;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0026, code lost:
    
        if (r1 == false) goto L20;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object getResult() {
        boolean z;
        boolean isReusable = isReusable();
        AtomicInt atomicInt = this._decision;
        while (true) {
            int i = atomicInt.value;
            boolean z2 = true;
            if (i != 0) {
                if (i != 2) {
                    throw new IllegalStateException("Already suspended".toString());
                }
                z = false;
            } else if (this._decision.compareAndSet(0, 1)) {
                z = true;
                break;
            }
        }
    }

    @Override // kotlinx.coroutines.DispatchedTask
    /* renamed from: getSuccessfulResult$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    public final Object mo287x119202a3(Object obj) {
        return obj instanceof CompletedContinuation ? ((CompletedContinuation) obj).result : obj;
    }

    public final void initCancellability() {
        DisposableHandle installParentHandle = installParentHandle();
        if (installParentHandle != null && (!(this._state.value instanceof NotCompleted))) {
            installParentHandle.dispose();
            this.parentHandle = NonDisposableHandle.INSTANCE;
        }
    }

    public final DisposableHandle installParentHandle() {
        Job job = (Job) this.context.get(Job.Key);
        if (job == null) {
            return null;
        }
        DisposableHandle invokeOnCompletion$default = Job.DefaultImpls.invokeOnCompletion$default(job, true, new ChildContinuation(this), 2);
        this.parentHandle = invokeOnCompletion$default;
        return invokeOnCompletion$default;
    }

    public final void invokeOnCancellation(Function1 function1) {
        CancelHandler invokeOnCancel = function1 instanceof CancelHandler ? (CancelHandler) function1 : new InvokeOnCancel(function1);
        AtomicRef atomicRef = this._state;
        while (true) {
            Object obj = atomicRef.value;
            if (!(obj instanceof Active)) {
                if (obj instanceof CancelHandler) {
                    multipleHandlersError(obj, function1);
                    throw null;
                }
                boolean z = obj instanceof CompletedExceptionally;
                if (z) {
                    CompletedExceptionally completedExceptionally = (CompletedExceptionally) obj;
                    if (!completedExceptionally._handled.compareAndSet()) {
                        multipleHandlersError(obj, function1);
                        throw null;
                    }
                    if (obj instanceof CancelledContinuation) {
                        if (!z) {
                            completedExceptionally = null;
                        }
                        callCancelHandler(function1, completedExceptionally != null ? completedExceptionally.cause : null);
                        return;
                    }
                    return;
                }
                if (obj instanceof CompletedContinuation) {
                    CompletedContinuation completedContinuation = (CompletedContinuation) obj;
                    if (completedContinuation.cancelHandler != null) {
                        multipleHandlersError(obj, function1);
                        throw null;
                    }
                    if (invokeOnCancel instanceof BeforeResumeCancelHandler) {
                        return;
                    }
                    Throwable th = completedContinuation.cancelCause;
                    if (th != null) {
                        callCancelHandler(function1, th);
                        return;
                    } else {
                        if (this._state.compareAndSet(obj, CompletedContinuation.copy$default(completedContinuation, invokeOnCancel, null, 29))) {
                            return;
                        }
                    }
                } else {
                    if (invokeOnCancel instanceof BeforeResumeCancelHandler) {
                        return;
                    }
                    if (this._state.compareAndSet(obj, new CompletedContinuation(obj, invokeOnCancel, null, null, null, 28, null))) {
                        return;
                    }
                }
            } else if (this._state.compareAndSet(obj, invokeOnCancel)) {
                return;
            }
        }
    }

    public final boolean isReusable() {
        if (this.resumeMode == 2) {
            if (((DispatchedContinuation) this.delegate)._reusableCancellableContinuation.value != null) {
                return true;
            }
        }
        return false;
    }

    public String nameString() {
        return "CancellableContinuation";
    }

    public final void releaseClaimedReusableContinuation() {
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
                        throw new IllegalArgumentException("Failed requirement.".toString());
                    }
                    th = (Throwable) obj;
                }
            }
            if (th == null) {
                return;
            }
            m284x2343eba7();
            cancel(th);
        }
    }

    public final void resumeImpl(Object obj, int i, Function1 function1) {
        Object obj2;
        AtomicRef atomicRef = this._state;
        do {
            obj2 = atomicRef.value;
            if (!(obj2 instanceof NotCompleted)) {
                if (obj2 instanceof CancelledContinuation) {
                    CancelledContinuation cancelledContinuation = (CancelledContinuation) obj2;
                    if (cancelledContinuation._resumed.compareAndSet()) {
                        if (function1 != null) {
                            callOnCancellation(function1, cancelledContinuation.cause);
                            return;
                        }
                        return;
                    }
                }
                throw new IllegalStateException(("Already resumed, but proposed with update " + obj).toString());
            }
        } while (!this._state.compareAndSet(obj2, resumedState((NotCompleted) obj2, obj, i, function1, null)));
        if (!isReusable()) {
            m284x2343eba7();
        }
        dispatchResume(i);
    }

    public final void resumeUndispatched(CoroutineDispatcher coroutineDispatcher, Unit unit) {
        Continuation continuation = this.delegate;
        DispatchedContinuation dispatchedContinuation = continuation instanceof DispatchedContinuation ? (DispatchedContinuation) continuation : null;
        resumeImpl(unit, (dispatchedContinuation != null ? dispatchedContinuation.dispatcher : null) == coroutineDispatcher ? 4 : this.resumeMode, null);
    }

    @Override // kotlin.coroutines.Continuation
    public final void resumeWith(Object obj) {
        Throwable m2859exceptionOrNullimpl = Result.m2859exceptionOrNullimpl(obj);
        if (m2859exceptionOrNullimpl != null) {
            obj = new CompletedExceptionally(m2859exceptionOrNullimpl, false, 2, null);
        }
        resumeImpl(obj, this.resumeMode, null);
    }

    @Override // kotlinx.coroutines.DispatchedTask
    /* renamed from: takeState$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    public final Object mo288x99f628c6() {
        return this._state.value;
    }

    public final String toString() {
        String nameString = nameString();
        String debugString = DebugStringsKt.toDebugString(this.delegate);
        Object obj = this._state.value;
        String str = obj instanceof NotCompleted ? "Active" : obj instanceof CancelledContinuation ? "Cancelled" : "Completed";
        String hexAddress = DebugStringsKt.getHexAddress(this);
        StringBuilder sb = new StringBuilder();
        sb.append(nameString);
        sb.append("(");
        sb.append(debugString);
        sb.append("){");
        sb.append(str);
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(sb, "}@", hexAddress);
    }

    public final Symbol tryResumeImpl(Object obj, LockFreeLinkedListNode.AbstractAtomicDesc abstractAtomicDesc, Function1 function1) {
        Object obj2;
        AtomicRef atomicRef = this._state;
        do {
            obj2 = atomicRef.value;
            if (!(obj2 instanceof NotCompleted)) {
                if ((obj2 instanceof CompletedContinuation) && abstractAtomicDesc != null && ((CompletedContinuation) obj2).idempotentResume == abstractAtomicDesc) {
                    return CancellableContinuationImplKt.RESUME_TOKEN;
                }
                return null;
            }
        } while (!this._state.compareAndSet(obj2, resumedState((NotCompleted) obj2, obj, this.resumeMode, function1, abstractAtomicDesc)));
        if (!isReusable()) {
            m284x2343eba7();
        }
        return CancellableContinuationImplKt.RESUME_TOKEN;
    }

    public final void callCancelHandler(Function1 function1, Throwable th) {
        try {
            function1.invoke(th);
        } catch (Throwable th2) {
            CoroutineExceptionHandlerKt.handleCoroutineException(new CompletionHandlerException("Exception in invokeOnCancellation handler for " + this, th2), this.context);
        }
    }
}
