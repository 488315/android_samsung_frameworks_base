package kotlinx.coroutines.sync;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlinx.atomicfu.AtomicBoolean;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CancellableContinuationImplKt;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.internal.AtomicOp;
import kotlinx.coroutines.internal.LockFreeLinkedListHead;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.OpDescriptor;
import kotlinx.coroutines.internal.Removed;
import kotlinx.coroutines.internal.Symbol;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class MutexImpl implements Mutex {
    public final AtomicRef _state;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class LockCont extends LockWaiter {
        public final CancellableContinuation cont;

        public LockCont(Object obj, CancellableContinuation cancellableContinuation) {
            super(MutexImpl.this, obj);
            this.cont = cancellableContinuation;
        }

        @Override // kotlinx.coroutines.sync.MutexImpl.LockWaiter
        public final void completeResumeLockWaiter() {
            Symbol symbol = CancellableContinuationImplKt.RESUME_TOKEN;
            CancellableContinuationImpl cancellableContinuationImpl = (CancellableContinuationImpl) this.cont;
            cancellableContinuationImpl.dispatchResume(cancellableContinuationImpl.resumeMode);
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode
        public final String toString() {
            return "LockCont[" + this.owner + ", " + this.cont + "] for " + MutexImpl.this;
        }

        @Override // kotlinx.coroutines.sync.MutexImpl.LockWaiter
        public final boolean tryResumeLockWaiter() {
            if (!this.isTaken.compareAndSet()) {
                return false;
            }
            Unit unit = Unit.INSTANCE;
            final MutexImpl mutexImpl = MutexImpl.this;
            return ((CancellableContinuationImpl) this.cont).tryResumeImpl(unit, null, new Function1() { // from class: kotlinx.coroutines.sync.MutexImpl$LockCont$tryResumeLockWaiter$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    MutexImpl.this.unlock(this.owner);
                    return Unit.INSTANCE;
                }
            }) != null;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class LockWaiter extends LockFreeLinkedListNode implements DisposableHandle {
        public final AtomicBoolean isTaken = AtomicFU.atomic(false);
        public final Object owner;

        public LockWaiter(MutexImpl mutexImpl, Object obj) {
            this.owner = obj;
        }

        public abstract void completeResumeLockWaiter();

        public abstract boolean tryResumeLockWaiter();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class LockedQueue extends LockFreeLinkedListHead {
        public volatile Object owner;

        public LockedQueue(Object obj) {
            this.owner = obj;
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode
        public final String toString() {
            return "LockedQueue[" + this.owner + "]";
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class UnlockOp extends AtomicOp {
        public final LockedQueue queue;

        public UnlockOp(LockedQueue lockedQueue) {
            this.queue = lockedQueue;
        }

        @Override // kotlinx.coroutines.internal.AtomicOp
        public final void complete(Object obj, Object obj2) {
            ((MutexImpl) obj)._state.compareAndSet(this, obj2 == null ? MutexKt.EMPTY_UNLOCKED : this.queue);
        }

        @Override // kotlinx.coroutines.internal.AtomicOp
        public final Object prepare(Object obj) {
            LockedQueue lockedQueue = this.queue;
            if (lockedQueue.getNext() == lockedQueue) {
                return null;
            }
            return MutexKt.UNLOCK_FAIL;
        }
    }

    public MutexImpl(boolean z) {
        this._state = AtomicFU.atomic(z ? MutexKt.EMPTY_LOCKED : MutexKt.EMPTY_UNLOCKED);
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0033, code lost:
    
        r0 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0034, code lost:
    
        if (r0 == false) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0038, code lost:
    
        return kotlin.Unit.INSTANCE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0039, code lost:
    
        r12 = kotlinx.coroutines.CancellableContinuationKt.getOrCreateCancellableContinuation(kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt.intercepted(r12));
        r1 = null;
        r0 = new kotlinx.coroutines.sync.MutexImpl.LockCont(r11, null, r12);
        r2 = r11._state;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0049, code lost:
    
        r7 = r2.value;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x004d, code lost:
    
        if ((r7 instanceof kotlinx.coroutines.sync.Empty) == false) goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x007c, code lost:
    
        if ((r7 instanceof kotlinx.coroutines.sync.MutexImpl.LockedQueue) == false) goto L86;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00ce, code lost:
    
        if ((r7 instanceof kotlinx.coroutines.internal.OpDescriptor) == false) goto L88;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00d0, code lost:
    
        ((kotlinx.coroutines.internal.OpDescriptor) r7).perform(r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00ec, code lost:
    
        throw new java.lang.IllegalStateException(("Illegal state " + r7).toString());
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x007e, code lost:
    
        r8 = (kotlinx.coroutines.sync.MutexImpl.LockedQueue) r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0083, code lost:
    
        if (r8.owner == null) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0085, code lost:
    
        r9 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0088, code lost:
    
        if (r9 == false) goto L83;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0092, code lost:
    
        if (r8.getPrevNode().addNext(r0, r8) == false) goto L95;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0098, code lost:
    
        if (r11._state.value == r7) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00a0, code lost:
    
        if (r0.isTaken.compareAndSet() != false) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00a3, code lost:
    
        r0 = new kotlinx.coroutines.sync.MutexImpl.LockCont(r11, null, r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00a9, code lost:
    
        r12.invokeOnCancellation(new kotlinx.coroutines.RemoveOnCancel(r0));
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00b1, code lost:
    
        r11 = r12.getResult();
        r12 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00b7, code lost:
    
        if (r11 != r12) goto L50;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00ba, code lost:
    
        r11 = kotlin.Unit.INSTANCE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00bc, code lost:
    
        if (r11 != r12) goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00be, code lost:
    
        return r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00c1, code lost:
    
        return kotlin.Unit.INSTANCE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00cb, code lost:
    
        throw new java.lang.IllegalStateException("Already locked by null".toString());
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0087, code lost:
    
        r9 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x004f, code lost:
    
        r8 = ((kotlinx.coroutines.sync.Empty) r7).locked;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0056, code lost:
    
        if (r8 == kotlinx.coroutines.sync.MutexKt.UNLOCKED) goto L78;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x006b, code lost:
    
        if (r11._state.compareAndSet(r7, kotlinx.coroutines.sync.MutexKt.EMPTY_LOCKED) == false) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x006d, code lost:
    
        r12.resumeImpl(kotlin.Unit.INSTANCE, r12.resumeMode, new kotlinx.coroutines.sync.MutexImpl$lockSuspend$2$1$1(r11, r1));
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0058, code lost:
    
        r11._state.compareAndSet(r7, new kotlinx.coroutines.sync.MutexImpl.LockedQueue(r8));
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object lock(Continuation continuation) {
        AtomicRef atomicRef = this._state;
        while (true) {
            Object obj = atomicRef.value;
            if (obj instanceof Empty) {
                if (((Empty) obj).locked != MutexKt.UNLOCKED) {
                    break;
                }
                if (this._state.compareAndSet(obj, MutexKt.EMPTY_LOCKED)) {
                    boolean z = true;
                    break;
                }
            } else if (obj instanceof LockedQueue) {
                if (!(((LockedQueue) obj).owner != null)) {
                    throw new IllegalStateException("Already locked by null".toString());
                }
            } else {
                if (!(obj instanceof OpDescriptor)) {
                    throw new IllegalStateException(("Illegal state " + obj).toString());
                }
                ((OpDescriptor) obj).perform(this);
            }
        }
    }

    public final String toString() {
        AtomicRef atomicRef = this._state;
        while (true) {
            Object obj = atomicRef.value;
            if (obj instanceof Empty) {
                return "Mutex[" + ((Empty) obj).locked + "]";
            }
            if (!(obj instanceof OpDescriptor)) {
                if (!(obj instanceof LockedQueue)) {
                    throw new IllegalStateException(("Illegal state " + obj).toString());
                }
                return "Mutex[" + ((LockedQueue) obj).owner + "]";
            }
            ((OpDescriptor) obj).perform(this);
        }
    }

    public final void unlock(Object obj) {
        LockFreeLinkedListNode lockFreeLinkedListNode;
        AtomicRef atomicRef = this._state;
        while (true) {
            Object obj2 = atomicRef.value;
            if (obj2 instanceof Empty) {
                if (obj == null) {
                    if (!(((Empty) obj2).locked != MutexKt.UNLOCKED)) {
                        throw new IllegalStateException("Mutex is not locked".toString());
                    }
                } else {
                    Object obj3 = ((Empty) obj2).locked;
                    if (!(obj3 == obj)) {
                        throw new IllegalStateException(("Mutex is locked by " + obj3 + " but expected " + obj).toString());
                    }
                }
                if (this._state.compareAndSet(obj2, MutexKt.EMPTY_UNLOCKED)) {
                    return;
                }
            } else if (obj2 instanceof OpDescriptor) {
                ((OpDescriptor) obj2).perform(this);
            } else {
                if (!(obj2 instanceof LockedQueue)) {
                    throw new IllegalStateException(("Illegal state " + obj2).toString());
                }
                if (obj != null) {
                    LockedQueue lockedQueue = (LockedQueue) obj2;
                    if (!(lockedQueue.owner == obj)) {
                        throw new IllegalStateException(("Mutex is locked by " + lockedQueue.owner + " but expected " + obj).toString());
                    }
                }
                LockedQueue lockedQueue2 = (LockedQueue) obj2;
                while (true) {
                    lockFreeLinkedListNode = (LockFreeLinkedListNode) lockedQueue2.getNext();
                    if (lockFreeLinkedListNode == lockedQueue2) {
                        lockFreeLinkedListNode = null;
                        break;
                    } else if (lockFreeLinkedListNode.remove()) {
                        break;
                    } else {
                        ((Removed) lockFreeLinkedListNode.getNext()).ref.helpRemovePrev();
                    }
                }
                if (lockFreeLinkedListNode == null) {
                    UnlockOp unlockOp = new UnlockOp(lockedQueue2);
                    if (this._state.compareAndSet(obj2, unlockOp) && unlockOp.perform(this) == null) {
                        return;
                    }
                } else {
                    LockWaiter lockWaiter = (LockWaiter) lockFreeLinkedListNode;
                    if (lockWaiter.tryResumeLockWaiter()) {
                        Object obj4 = lockWaiter.owner;
                        if (obj4 == null) {
                            obj4 = MutexKt.LOCKED;
                        }
                        lockedQueue2.owner = obj4;
                        lockWaiter.completeResumeLockWaiter();
                        return;
                    }
                }
            }
        }
    }
}
