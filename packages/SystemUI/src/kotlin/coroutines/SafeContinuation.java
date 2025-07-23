package kotlin.coroutines;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Result;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SafeContinuation implements Continuation, CoroutineStackFrame {
    public static final AtomicReferenceFieldUpdater RESULT;
    public final Continuation delegate;
    private volatile Object result;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        RESULT = AtomicReferenceFieldUpdater.newUpdater(SafeContinuation.class, Object.class, "result");
    }

    public SafeContinuation(Continuation continuation, Object obj) {
        this.delegate = continuation;
        this.result = obj;
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
        return this.delegate.getContext();
    }

    public final Object getOrThrow() {
        Object obj = this.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.UNDECIDED;
        if (obj == coroutineSingletons) {
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = RESULT;
            CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (atomicReferenceFieldUpdater.compareAndSet(this, coroutineSingletons, coroutineSingletons2)) {
                return coroutineSingletons2;
            }
            obj = this.result;
        }
        if (obj == CoroutineSingletons.RESUMED) {
            return CoroutineSingletons.COROUTINE_SUSPENDED;
        }
        if (obj instanceof Result.Failure) {
            throw ((Result.Failure) obj).exception;
        }
        return obj;
    }

    @Override // kotlin.coroutines.Continuation
    public final void resumeWith(Object obj) {
        while (true) {
            Object obj2 = this.result;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.UNDECIDED;
            if (obj2 != coroutineSingletons) {
                CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (obj2 != coroutineSingletons2) {
                    throw new IllegalStateException("Already resumed");
                }
                if (RESULT.compareAndSet(this, coroutineSingletons2, CoroutineSingletons.RESUMED)) {
                    this.delegate.resumeWith(obj);
                    return;
                }
            } else if (RESULT.compareAndSet(this, coroutineSingletons, obj)) {
                return;
            }
        }
    }

    public final String toString() {
        return "SafeContinuation for " + this.delegate;
    }

    public SafeContinuation(Continuation continuation) {
        this(continuation, CoroutineSingletons.UNDECIDED);
    }
}
