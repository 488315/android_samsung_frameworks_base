package kotlinx.coroutines.sync;

import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CancellableContinuationKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.Waiter;
import kotlinx.coroutines.internal.Segment;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.sync.MutexImpl;

/* loaded from: classes4.dex */
public class MutexImpl extends SemaphoreAndMutexImpl implements Mutex {
    public final AtomicRef owner;

    public final class CancellableContinuationWithOwner implements CancellableContinuation, Waiter {
        public final CancellableContinuationImpl cont;
        public final Object owner;

        public CancellableContinuationWithOwner(CancellableContinuationImpl cancellableContinuationImpl, Object obj) {
            this.cont = cancellableContinuationImpl;
            this.owner = obj;
        }

        @Override // kotlinx.coroutines.CancellableContinuation
        public final boolean cancel(Throwable th) {
            return this.cont.cancel(th);
        }

        @Override // kotlinx.coroutines.CancellableContinuation
        public final void completeResume(Object obj) {
            this.cont.completeResume(obj);
        }

        @Override // kotlin.coroutines.Continuation
        public final CoroutineContext getContext() {
            return this.cont.context;
        }

        @Override // kotlinx.coroutines.CancellableContinuation
        public final void invokeOnCancellation(Function1 function1) {
            this.cont.invokeOnCancellation(function1);
        }

        @Override // kotlinx.coroutines.CancellableContinuation
        public final boolean isActive() {
            return this.cont.isActive();
        }

        @Override // kotlinx.coroutines.CancellableContinuation
        public final boolean isCancelled$1() {
            return this.cont.isCancelled$1();
        }

        @Override // kotlinx.coroutines.CancellableContinuation
        public final boolean isCompleted() {
            return this.cont.isCompleted();
        }

        @Override // kotlinx.coroutines.CancellableContinuation
        public final void resume(Unit unit, Function1 function1) {
            this.cont.resume(unit, function1);
        }

        @Override // kotlinx.coroutines.CancellableContinuation
        public final void resumeUndispatched(CoroutineDispatcher coroutineDispatcher, Unit unit) {
            this.cont.resumeUndispatched(coroutineDispatcher, unit);
        }

        @Override // kotlin.coroutines.Continuation
        public final void resumeWith(Object obj) {
            this.cont.resumeWith(obj);
        }

        @Override // kotlinx.coroutines.CancellableContinuation
        public final Symbol tryResume(Object obj, Function3 function3) {
            final MutexImpl mutexImpl = MutexImpl.this;
            Function3 function32 = new Function3() { // from class: kotlinx.coroutines.sync.MutexImpl$CancellableContinuationWithOwner$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj2, Object obj3, Object obj4) {
                    MutexImpl mutexImpl2 = mutexImpl;
                    AtomicRef atomicRef = mutexImpl2.owner;
                    MutexImpl.CancellableContinuationWithOwner cancellableContinuationWithOwner = this;
                    atomicRef.setValue(cancellableContinuationWithOwner.owner);
                    mutexImpl2.unlock(cancellableContinuationWithOwner.owner);
                    return Unit.INSTANCE;
                }
            };
            Symbol symbolTryResumeImpl = this.cont.tryResumeImpl((Unit) obj, function32);
            if (symbolTryResumeImpl != null) {
                mutexImpl.owner.setValue(this.owner);
            }
            return symbolTryResumeImpl;
        }

        @Override // kotlinx.coroutines.CancellableContinuation
        public final Symbol tryResumeWithException(Throwable th) {
            return this.cont.tryResumeWithException(th);
        }

        @Override // kotlinx.coroutines.Waiter
        public final void invokeOnCancellation(Segment segment, int i) {
            this.cont.invokeOnCancellation(segment, i);
        }

        @Override // kotlinx.coroutines.CancellableContinuation
        public final void resume(Object obj, Function3 function3) {
            final MutexImpl mutexImpl = MutexImpl.this;
            mutexImpl.owner.setValue(this.owner);
            Function1 function1 = new Function1() { // from class: kotlinx.coroutines.sync.MutexImpl$CancellableContinuationWithOwner$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    mutexImpl.unlock(this.owner);
                    return Unit.INSTANCE;
                }
            };
            this.cont.resume((Unit) obj, function1);
        }
    }

    public MutexImpl(boolean z) {
        super(1, z ? 1 : 0);
        this.owner = AtomicFU.atomic(z ? null : MutexKt.NO_OWNER);
    }

    public final boolean isLocked() {
        return Math.max(this._availablePermits.value, 0) == 0;
    }

    @Override // kotlinx.coroutines.sync.Mutex
    public final Object lock(Continuation continuation) {
        if (tryLock()) {
            return Unit.INSTANCE;
        }
        CancellableContinuationImpl orCreateCancellableContinuation = CancellableContinuationKt.getOrCreateCancellableContinuation(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation));
        try {
            acquire(new CancellableContinuationWithOwner(orCreateCancellableContinuation, null));
            Object result = orCreateCancellableContinuation.getResult();
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (result != coroutineSingletons) {
                result = Unit.INSTANCE;
            }
            return result == coroutineSingletons ? result : Unit.INSTANCE;
        } catch (Throwable th) {
            orCreateCancellableContinuation.releaseClaimedReusableContinuation$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
            throw th;
        }
    }

    public final String toString() {
        String hexAddress = DebugStringsKt.getHexAddress(this);
        boolean zIsLocked = isLocked();
        Object obj = this.owner.value;
        StringBuilder sbM = CarrierTextManagerLogger$$ExternalSyntheticOutline0.m("Mutex@", hexAddress, "[isLocked=", ",owner=", zIsLocked);
        sbM.append(obj);
        sbM.append("]");
        return sbM.toString();
    }

    public final boolean tryLock() {
        int i;
        int i2;
        char c;
        while (true) {
            int i3 = this._availablePermits.value;
            if (i3 > this.permits) {
                do {
                    i = this._availablePermits.value;
                    i2 = this.permits;
                    if (i > i2) {
                    }
                } while (!this._availablePermits.compareAndSet(i, i2));
            } else {
                if (i3 <= 0) {
                    c = 1;
                    break;
                }
                if (this._availablePermits.compareAndSet(i3, i3 - 1)) {
                    this.owner.setValue(null);
                    c = 0;
                    break;
                }
            }
        }
        if (c == 0) {
            return true;
        }
        if (c == 1) {
            return false;
        }
        if (c != 2) {
            throw new IllegalStateException("unexpected");
        }
        throw new IllegalStateException("This mutex is already locked by the specified owner: null".toString());
    }

    @Override // kotlinx.coroutines.sync.Mutex
    public final void unlock(Object obj) {
        while (isLocked()) {
            Object obj2 = this.owner.value;
            Symbol symbol = MutexKt.NO_OWNER;
            if (obj2 != symbol) {
                if (obj2 != obj && obj != null) {
                    throw new IllegalStateException(("This mutex is locked by " + obj2 + ", but " + obj + " is expected").toString());
                }
                if (this.owner.compareAndSet(obj2, symbol)) {
                    release();
                    return;
                }
            }
        }
        throw new IllegalStateException("This mutex is not locked");
    }
}
