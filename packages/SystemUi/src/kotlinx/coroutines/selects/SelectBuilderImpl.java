package kotlinx.coroutines.selects;

import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.BaseContinuationImpl;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.coroutines.jvm.internal.RestrictedContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicLong;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.atomicfu.TraceBase;
import kotlinx.coroutines.CancellableContinuationImplKt;
import kotlinx.coroutines.CompletedExceptionally;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobCancellingNode;
import kotlinx.coroutines.internal.AtomicDesc;
import kotlinx.coroutines.internal.AtomicOp;
import kotlinx.coroutines.internal.DispatchedContinuationKt;
import kotlinx.coroutines.internal.LockFreeLinkedListHead;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.OpDescriptor;
import kotlinx.coroutines.internal.Symbol;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class SelectBuilderImpl extends LockFreeLinkedListHead implements SelectInstance, Continuation, CoroutineStackFrame {
    public final Continuation uCont;
    public final AtomicRef _state = AtomicFU.atomic(SelectKt.NOT_SELECTED);
    public final AtomicRef _result = AtomicFU.atomic(SelectKt.UNDECIDED);
    public final AtomicRef _parentHandle = AtomicFU.atomic((Object) null);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AtomicSelectOp extends AtomicOp {
        public final AtomicDesc desc;
        public final SelectBuilderImpl impl;
        public final long opSequence;

        public AtomicSelectOp(SelectBuilderImpl selectBuilderImpl, AtomicDesc atomicDesc) {
            this.impl = selectBuilderImpl;
            this.desc = atomicDesc;
            AtomicLong atomicLong = SelectKt.selectOpSequenceNumber.number;
            atomicLong.getClass();
            long incrementAndGet = AtomicLong.f664FU.incrementAndGet(atomicLong);
            TraceBase.None none = TraceBase.None.INSTANCE;
            TraceBase traceBase = atomicLong.trace;
            if (traceBase != none) {
                traceBase.getClass();
            }
            this.opSequence = incrementAndGet;
            atomicDesc.atomicOp = this;
        }

        @Override // kotlinx.coroutines.internal.AtomicOp
        public final void complete(Object obj, Object obj2) {
            boolean z = obj2 == null;
            Symbol symbol = z ? null : SelectKt.NOT_SELECTED;
            SelectBuilderImpl selectBuilderImpl = this.impl;
            if (selectBuilderImpl._state.compareAndSet(this, symbol) && z) {
                selectBuilderImpl.doAfterSelect();
            }
            this.desc.complete(this, obj2);
        }

        @Override // kotlinx.coroutines.internal.AtomicOp
        public final long getOpSequence() {
            return this.opSequence;
        }

        /* JADX WARN: Code restructure failed: missing block: B:13:0x0025, code lost:
        
            r0 = null;
         */
        @Override // kotlinx.coroutines.internal.AtomicOp
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object prepare(Object obj) {
            Symbol symbol;
            if (obj == null) {
                AtomicRef atomicRef = this.impl._state;
                while (true) {
                    Object obj2 = atomicRef.value;
                    if (obj2 == this) {
                        break;
                    }
                    if (!(obj2 instanceof OpDescriptor)) {
                        Symbol symbol2 = SelectKt.NOT_SELECTED;
                        if (obj2 != symbol2) {
                            symbol = SelectKt.ALREADY_SELECTED;
                            break;
                        }
                        if (this.impl._state.compareAndSet(symbol2, this)) {
                            break;
                        }
                    } else {
                        ((OpDescriptor) obj2).perform(this.impl);
                    }
                }
                if (symbol != null) {
                    return symbol;
                }
            }
            try {
                return this.desc.prepare(this);
            } catch (Throwable th) {
                if (obj == null) {
                    this.impl._state.compareAndSet(this, SelectKt.NOT_SELECTED);
                }
                throw th;
            }
        }

        @Override // kotlinx.coroutines.internal.OpDescriptor
        public final String toString() {
            return "AtomicSelectOp(sequence=" + this.opSequence + ")";
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DisposeNode extends LockFreeLinkedListNode {
        public final DisposableHandle handle;

        public DisposeNode(DisposableHandle disposableHandle) {
            this.handle = disposableHandle;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SelectOnCancelling extends JobCancellingNode {
        public SelectOnCancelling() {
        }

        @Override // kotlin.jvm.functions.Function1
        public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((Throwable) obj);
            return Unit.INSTANCE;
        }

        @Override // kotlinx.coroutines.CompletionHandlerBase
        public final void invoke(Throwable th) {
            SelectBuilderImpl selectBuilderImpl = SelectBuilderImpl.this;
            if (selectBuilderImpl.trySelect()) {
                selectBuilderImpl.resumeSelectWithException(getJob().getCancellationException());
            }
        }
    }

    public SelectBuilderImpl(Continuation<Object> continuation) {
        this.uCont = continuation;
    }

    public final void disposeOnSelect(DisposableHandle disposableHandle) {
        DisposeNode disposeNode = new DisposeNode(disposableHandle);
        if (!isSelected()) {
            while (!getPrevNode().addNext(disposeNode, this)) {
            }
            if (!isSelected()) {
                return;
            }
        }
        disposableHandle.dispose();
    }

    public final void doAfterSelect() {
        DisposableHandle disposableHandle = (DisposableHandle) this._parentHandle.value;
        if (disposableHandle != null) {
            disposableHandle.dispose();
        }
        for (LockFreeLinkedListNode lockFreeLinkedListNode = (LockFreeLinkedListNode) getNext(); !Intrinsics.areEqual(lockFreeLinkedListNode, this); lockFreeLinkedListNode = lockFreeLinkedListNode.getNextNode()) {
            if (lockFreeLinkedListNode instanceof DisposeNode) {
                ((DisposeNode) lockFreeLinkedListNode).handle.dispose();
            }
        }
    }

    @Override // kotlin.coroutines.jvm.internal.CoroutineStackFrame
    public final CoroutineStackFrame getCallerFrame() {
        Continuation continuation = this.uCont;
        if (continuation instanceof CoroutineStackFrame) {
            return (CoroutineStackFrame) continuation;
        }
        return null;
    }

    @Override // kotlin.coroutines.Continuation
    public final CoroutineContext getContext() {
        return this.uCont.getContext();
    }

    public final Object getResult() {
        Job job;
        if (!isSelected() && (job = (Job) getContext().get(Job.Key)) != null) {
            DisposableHandle invokeOnCompletion$default = Job.DefaultImpls.invokeOnCompletion$default(job, true, new SelectOnCancelling(), 2);
            this._parentHandle.setValue(invokeOnCompletion$default);
            if (isSelected()) {
                invokeOnCompletion$default.dispose();
            }
        }
        Object obj = this._result.value;
        Symbol symbol = SelectKt.UNDECIDED;
        if (obj == symbol) {
            AtomicRef atomicRef = this._result;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (atomicRef.compareAndSet(symbol, coroutineSingletons)) {
                return coroutineSingletons;
            }
            obj = this._result.value;
        }
        if (obj == SelectKt.RESUMED) {
            throw new IllegalStateException("Already resumed");
        }
        if (obj instanceof CompletedExceptionally) {
            throw ((CompletedExceptionally) obj).cause;
        }
        return obj;
    }

    public final boolean isSelected() {
        AtomicRef atomicRef = this._state;
        while (true) {
            Object obj = atomicRef.value;
            if (obj == SelectKt.NOT_SELECTED) {
                return false;
            }
            if (!(obj instanceof OpDescriptor)) {
                return true;
            }
            ((OpDescriptor) obj).perform(this);
        }
    }

    public final void onTimeout(long j, final Function1 function1) {
        if (j > 0) {
            disposeOnSelect(DelayKt.getDelay(getContext()).invokeOnTimeout(j, new Runnable() { // from class: kotlinx.coroutines.selects.SelectBuilderImpl$onTimeout$$inlined$Runnable$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.lang.Runnable
                public final void run() {
                    Continuation continuation;
                    if (SelectBuilderImpl.this.trySelect()) {
                        final Function1 function12 = function1;
                        SelectBuilderImpl selectBuilderImpl = SelectBuilderImpl.this;
                        selectBuilderImpl.getClass();
                        try {
                            if (function12 instanceof BaseContinuationImpl) {
                                continuation = ((BaseContinuationImpl) function12).create(selectBuilderImpl);
                            } else {
                                CoroutineContext context = selectBuilderImpl.getContext();
                                continuation = context == EmptyCoroutineContext.INSTANCE ? new RestrictedContinuationImpl(selectBuilderImpl) { // from class: kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$1
                                    private int label;

                                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                    public final Object invokeSuspend(Object obj) {
                                        int i = this.label;
                                        if (i != 0) {
                                            if (i != 1) {
                                                throw new IllegalStateException("This coroutine had already completed".toString());
                                            }
                                            this.label = 2;
                                            ResultKt.throwOnFailure(obj);
                                            return obj;
                                        }
                                        this.label = 1;
                                        ResultKt.throwOnFailure(obj);
                                        Function1 function13 = function12;
                                        TypeIntrinsics.beforeCheckcastToFunctionOfArity(1, function13);
                                        return function13.invoke(this);
                                    }
                                } : new ContinuationImpl(selectBuilderImpl, context) { // from class: kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$2
                                    private int label;

                                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                    public final Object invokeSuspend(Object obj) {
                                        int i = this.label;
                                        if (i != 0) {
                                            if (i != 1) {
                                                throw new IllegalStateException("This coroutine had already completed".toString());
                                            }
                                            this.label = 2;
                                            ResultKt.throwOnFailure(obj);
                                            return obj;
                                        }
                                        this.label = 1;
                                        ResultKt.throwOnFailure(obj);
                                        Function1 function13 = function12;
                                        TypeIntrinsics.beforeCheckcastToFunctionOfArity(1, function13);
                                        return function13.invoke(this);
                                    }
                                };
                            }
                            Continuation intercepted = IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation);
                            int i = Result.$r8$clinit;
                            DispatchedContinuationKt.resumeCancellableWith(intercepted, Unit.INSTANCE, null);
                        } catch (Throwable th) {
                            int i2 = Result.$r8$clinit;
                            selectBuilderImpl.resumeWith(new Result.Failure(th));
                            throw th;
                        }
                    }
                }
            }, getContext()));
            return;
        }
        if (trySelect()) {
            try {
                TypeIntrinsics.beforeCheckcastToFunctionOfArity(1, function1);
                Object invoke = function1.invoke(this);
                if (invoke != CoroutineSingletons.COROUTINE_SUSPENDED) {
                    int i = Result.$r8$clinit;
                    resumeWith(invoke);
                }
            } catch (Throwable th) {
                int i2 = Result.$r8$clinit;
                resumeWith(new Result.Failure(th));
            }
        }
    }

    public final void resumeSelectWithException(Throwable th) {
        AtomicRef atomicRef = this._result;
        while (true) {
            Object obj = atomicRef.value;
            Symbol symbol = SelectKt.UNDECIDED;
            if (obj == symbol) {
                if (this._result.compareAndSet(symbol, new CompletedExceptionally(th, false, 2, null))) {
                    return;
                }
            } else {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (obj != coroutineSingletons) {
                    throw new IllegalStateException("Already resumed");
                }
                if (this._result.compareAndSet(coroutineSingletons, SelectKt.RESUMED)) {
                    Continuation intercepted = IntrinsicsKt__IntrinsicsJvmKt.intercepted(this.uCont);
                    int i = Result.$r8$clinit;
                    intercepted.resumeWith(new Result.Failure(th));
                    return;
                }
            }
        }
    }

    @Override // kotlin.coroutines.Continuation
    public final void resumeWith(Object obj) {
        AtomicRef atomicRef = this._result;
        while (true) {
            Object obj2 = atomicRef.value;
            Symbol symbol = SelectKt.UNDECIDED;
            if (obj2 == symbol) {
                Throwable m2859exceptionOrNullimpl = Result.m2859exceptionOrNullimpl(obj);
                if (this._result.compareAndSet(symbol, m2859exceptionOrNullimpl == null ? obj : new CompletedExceptionally(m2859exceptionOrNullimpl, false, 2, null))) {
                    return;
                }
            } else {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (obj2 != coroutineSingletons) {
                    throw new IllegalStateException("Already resumed");
                }
                if (this._result.compareAndSet(coroutineSingletons, SelectKt.RESUMED)) {
                    int i = Result.$r8$clinit;
                    if (!(obj instanceof Result.Failure)) {
                        this.uCont.resumeWith(obj);
                        return;
                    }
                    Continuation continuation = this.uCont;
                    Throwable m2859exceptionOrNullimpl2 = Result.m2859exceptionOrNullimpl(obj);
                    Intrinsics.checkNotNull(m2859exceptionOrNullimpl2);
                    continuation.resumeWith(new Result.Failure(m2859exceptionOrNullimpl2));
                    return;
                }
            }
        }
    }

    @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode
    public final String toString() {
        return "SelectInstance(state=" + this._state.value + ", result=" + this._result.value + ")";
    }

    public final boolean trySelect() {
        Object trySelectOther = trySelectOther();
        if (trySelectOther == CancellableContinuationImplKt.RESUME_TOKEN) {
            return true;
        }
        if (trySelectOther == null) {
            return false;
        }
        throw new IllegalStateException(("Unexpected trySelectIdempotent result " + trySelectOther).toString());
    }

    public final Object trySelectOther() {
        AtomicRef atomicRef = this._state;
        while (true) {
            Object obj = atomicRef.value;
            Symbol symbol = SelectKt.NOT_SELECTED;
            if (obj == symbol) {
                if (this._state.compareAndSet(symbol, null)) {
                    doAfterSelect();
                    return CancellableContinuationImplKt.RESUME_TOKEN;
                }
            } else {
                if (!(obj instanceof OpDescriptor)) {
                    return null;
                }
                ((OpDescriptor) obj).perform(this);
            }
        }
    }
}
