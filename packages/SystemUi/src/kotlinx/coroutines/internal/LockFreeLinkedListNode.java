package kotlinx.coroutines.internal;

import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference0Impl;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.coroutines.DebugStringsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class LockFreeLinkedListNode {
    public final AtomicRef _next = AtomicFU.atomic(this);
    public final AtomicRef _prev = AtomicFU.atomic(this);
    public final AtomicRef _removedRef = AtomicFU.atomic((Object) null);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class CondAddOp extends AtomicOp {
        public final LockFreeLinkedListNode newNode;
        public LockFreeLinkedListNode oldNext;

        public CondAddOp(LockFreeLinkedListNode lockFreeLinkedListNode) {
            this.newNode = lockFreeLinkedListNode;
        }

        @Override // kotlinx.coroutines.internal.AtomicOp
        public final void complete(Object obj, Object obj2) {
            LockFreeLinkedListNode lockFreeLinkedListNode = (LockFreeLinkedListNode) obj;
            boolean z = obj2 == null;
            LockFreeLinkedListNode lockFreeLinkedListNode2 = this.newNode;
            LockFreeLinkedListNode lockFreeLinkedListNode3 = z ? lockFreeLinkedListNode2 : this.oldNext;
            if (lockFreeLinkedListNode3 != null && lockFreeLinkedListNode._next.compareAndSet(this, lockFreeLinkedListNode3) && z) {
                LockFreeLinkedListNode lockFreeLinkedListNode4 = this.oldNext;
                Intrinsics.checkNotNull(lockFreeLinkedListNode4);
                lockFreeLinkedListNode2.finishAdd(lockFreeLinkedListNode4);
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PrepareOp extends OpDescriptor {
        public final LockFreeLinkedListNode affected;
        public final AbstractAtomicDesc desc;
        public final LockFreeLinkedListNode next;

        public PrepareOp(LockFreeLinkedListNode lockFreeLinkedListNode, LockFreeLinkedListNode lockFreeLinkedListNode2, AbstractAtomicDesc abstractAtomicDesc) {
            this.affected = lockFreeLinkedListNode;
            this.next = lockFreeLinkedListNode2;
            this.desc = abstractAtomicDesc;
        }

        public final void finishPrepare() {
            this.desc.finishPrepare(this);
        }

        @Override // kotlinx.coroutines.internal.OpDescriptor
        public final AtomicOp getAtomicOp() {
            AtomicOp atomicOp = this.desc.atomicOp;
            if (atomicOp != null) {
                return atomicOp;
            }
            return null;
        }

        @Override // kotlinx.coroutines.internal.OpDescriptor
        public final Object perform(Object obj) {
            LockFreeLinkedListNode lockFreeLinkedListNode = (LockFreeLinkedListNode) obj;
            Object onPrepare = this.desc.onPrepare(this);
            Symbol symbol = LockFreeLinkedList_commonKt.REMOVE_PREPARED;
            if (onPrepare != symbol) {
                Object decide = onPrepare != null ? getAtomicOp().decide(onPrepare) : getAtomicOp()._consensus.value;
                lockFreeLinkedListNode._next.compareAndSet(this, decide == AtomicKt.NO_DECISION ? getAtomicOp() : decide == null ? this.desc.updatedNext(this.next) : this.next);
                return null;
            }
            LockFreeLinkedListNode lockFreeLinkedListNode2 = this.next;
            if (lockFreeLinkedListNode._next.compareAndSet(this, lockFreeLinkedListNode2.removed())) {
                this.desc.onRemoved(lockFreeLinkedListNode);
                lockFreeLinkedListNode2.correctPrev();
            }
            return symbol;
        }

        @Override // kotlinx.coroutines.internal.OpDescriptor
        public final String toString() {
            return "PrepareOp(op=" + getAtomicOp() + ")";
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class RemoveFirstDesc extends AbstractAtomicDesc {
        public final AtomicRef _affectedNode = AtomicFU.atomic((Object) null);
        public final AtomicRef _originalNext = AtomicFU.atomic((Object) null);
        public final LockFreeLinkedListNode queue;

        public RemoveFirstDesc(LockFreeLinkedListNode lockFreeLinkedListNode) {
            this.queue = lockFreeLinkedListNode;
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode.AbstractAtomicDesc
        public Object failure(LockFreeLinkedListNode lockFreeLinkedListNode) {
            if (lockFreeLinkedListNode == this.queue) {
                return LockFreeLinkedListKt.LIST_EMPTY;
            }
            return null;
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode.AbstractAtomicDesc
        public final void finishOnSuccess(LockFreeLinkedListNode lockFreeLinkedListNode) {
            lockFreeLinkedListNode.correctPrev();
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode.AbstractAtomicDesc
        public final void finishPrepare(PrepareOp prepareOp) {
            this._affectedNode.compareAndSet(null, prepareOp.affected);
            this._originalNext.compareAndSet(null, prepareOp.next);
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode.AbstractAtomicDesc
        public final LockFreeLinkedListNode getAffectedNode() {
            return (LockFreeLinkedListNode) this._affectedNode.value;
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode.AbstractAtomicDesc
        public final LockFreeLinkedListNode getOriginalNext() {
            return (LockFreeLinkedListNode) this._originalNext.value;
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode.AbstractAtomicDesc
        public final boolean retry(Object obj) {
            if (!(obj instanceof Removed)) {
                return false;
            }
            ((Removed) obj).ref.helpRemovePrev();
            return true;
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode.AbstractAtomicDesc
        public final LockFreeLinkedListNode takeAffectedNode(OpDescriptor opDescriptor) {
            AtomicRef atomicRef = this.queue._next;
            while (true) {
                Object obj = atomicRef.value;
                if (!(obj instanceof OpDescriptor)) {
                    return (LockFreeLinkedListNode) obj;
                }
                OpDescriptor opDescriptor2 = (OpDescriptor) obj;
                if (opDescriptor.isEarlierThan(opDescriptor2)) {
                    return null;
                }
                opDescriptor2.perform(this.queue);
            }
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode.AbstractAtomicDesc
        public final Removed updatedNext(LockFreeLinkedListNode lockFreeLinkedListNode) {
            return lockFreeLinkedListNode.removed();
        }
    }

    public final boolean addNext(LockFreeLinkedListNode lockFreeLinkedListNode, LockFreeLinkedListHead lockFreeLinkedListHead) {
        lockFreeLinkedListNode._prev.lazySet(this);
        lockFreeLinkedListNode._next.lazySet(lockFreeLinkedListHead);
        if (!this._next.compareAndSet(lockFreeLinkedListHead, lockFreeLinkedListNode)) {
            return false;
        }
        lockFreeLinkedListNode.finishAdd(lockFreeLinkedListHead);
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0040, code lost:
    
        if (r3._next.compareAndSet(r1, ((kotlinx.coroutines.internal.Removed) r4).ref) != false) goto L26;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final LockFreeLinkedListNode correctPrev() {
        while (true) {
            LockFreeLinkedListNode lockFreeLinkedListNode = (LockFreeLinkedListNode) this._prev.value;
            LockFreeLinkedListNode lockFreeLinkedListNode2 = lockFreeLinkedListNode;
            while (true) {
                LockFreeLinkedListNode lockFreeLinkedListNode3 = null;
                while (true) {
                    Object obj = lockFreeLinkedListNode2._next.value;
                    if (obj == this) {
                        if (lockFreeLinkedListNode == lockFreeLinkedListNode2) {
                            return lockFreeLinkedListNode2;
                        }
                        if (this._prev.compareAndSet(lockFreeLinkedListNode, lockFreeLinkedListNode2)) {
                            return lockFreeLinkedListNode2;
                        }
                    } else {
                        if (isRemoved()) {
                            return null;
                        }
                        if (obj == null) {
                            return lockFreeLinkedListNode2;
                        }
                        if (obj instanceof OpDescriptor) {
                            ((OpDescriptor) obj).perform(lockFreeLinkedListNode2);
                            break;
                        }
                        if (!(obj instanceof Removed)) {
                            lockFreeLinkedListNode3 = lockFreeLinkedListNode2;
                            lockFreeLinkedListNode2 = (LockFreeLinkedListNode) obj;
                        } else {
                            if (lockFreeLinkedListNode3 != null) {
                                break;
                            }
                            lockFreeLinkedListNode2 = (LockFreeLinkedListNode) lockFreeLinkedListNode2._prev.value;
                        }
                    }
                }
                lockFreeLinkedListNode2 = lockFreeLinkedListNode3;
            }
        }
    }

    public void dispose() {
        remove();
    }

    public final void finishAdd(LockFreeLinkedListNode lockFreeLinkedListNode) {
        LockFreeLinkedListNode lockFreeLinkedListNode2;
        AtomicRef atomicRef = lockFreeLinkedListNode._prev;
        do {
            lockFreeLinkedListNode2 = (LockFreeLinkedListNode) atomicRef.value;
            if (getNext() != lockFreeLinkedListNode) {
                return;
            }
        } while (!lockFreeLinkedListNode._prev.compareAndSet(lockFreeLinkedListNode2, this));
        if (isRemoved()) {
            lockFreeLinkedListNode.correctPrev();
        }
    }

    public final Object getNext() {
        AtomicRef atomicRef = this._next;
        while (true) {
            Object obj = atomicRef.value;
            if (!(obj instanceof OpDescriptor)) {
                return obj;
            }
            ((OpDescriptor) obj).perform(this);
        }
    }

    public final LockFreeLinkedListNode getNextNode() {
        LockFreeLinkedListNode lockFreeLinkedListNode;
        Object next = getNext();
        Symbol symbol = LockFreeLinkedListKt.CONDITION_FALSE;
        Removed removed = next instanceof Removed ? (Removed) next : null;
        return (removed == null || (lockFreeLinkedListNode = removed.ref) == null) ? (LockFreeLinkedListNode) next : lockFreeLinkedListNode;
    }

    public final LockFreeLinkedListNode getPrevNode() {
        LockFreeLinkedListNode correctPrev = correctPrev();
        if (correctPrev == null) {
            correctPrev = (LockFreeLinkedListNode) this._prev.value;
            while (correctPrev.isRemoved()) {
                correctPrev = (LockFreeLinkedListNode) correctPrev._prev.value;
            }
        }
        return correctPrev;
    }

    public final void helpRemovePrev() {
        while (true) {
            Object next = this.getNext();
            if (!(next instanceof Removed)) {
                this.correctPrev();
                return;
            }
            this = ((Removed) next).ref;
        }
    }

    public boolean isRemoved() {
        return getNext() instanceof Removed;
    }

    public boolean remove() {
        return removeOrNext() == null;
    }

    public final LockFreeLinkedListNode removeOrNext() {
        Object next;
        LockFreeLinkedListNode lockFreeLinkedListNode;
        do {
            next = getNext();
            if (next instanceof Removed) {
                return ((Removed) next).ref;
            }
            if (next == this) {
                return (LockFreeLinkedListNode) next;
            }
            lockFreeLinkedListNode = (LockFreeLinkedListNode) next;
        } while (!this._next.compareAndSet(next, lockFreeLinkedListNode.removed()));
        lockFreeLinkedListNode.correctPrev();
        return null;
    }

    public final Removed removed() {
        Removed removed = (Removed) this._removedRef.value;
        if (removed != null) {
            return removed;
        }
        Removed removed2 = new Removed(this);
        this._removedRef.lazySet(removed2);
        return removed2;
    }

    public String toString() {
        return new PropertyReference0Impl(this) { // from class: kotlinx.coroutines.internal.LockFreeLinkedListNode$toString$1
            @Override // kotlin.jvm.internal.PropertyReference0Impl, kotlin.reflect.KProperty0
            public final Object get() {
                return DebugStringsKt.getClassSimpleName(this.receiver);
            }
        } + "@" + DebugStringsKt.getHexAddress(this);
    }

    public final int tryCondAddNext(LockFreeLinkedListNode lockFreeLinkedListNode, LockFreeLinkedListNode lockFreeLinkedListNode2, CondAddOp condAddOp) {
        lockFreeLinkedListNode._prev.lazySet(this);
        lockFreeLinkedListNode._next.lazySet(lockFreeLinkedListNode2);
        condAddOp.oldNext = lockFreeLinkedListNode2;
        if (this._next.compareAndSet(lockFreeLinkedListNode2, condAddOp)) {
            return condAddOp.perform(this) == null ? 1 : 2;
        }
        return 0;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class AbstractAtomicDesc extends AtomicDesc {
        @Override // kotlinx.coroutines.internal.AtomicDesc
        public final void complete(AtomicOp atomicOp, Object obj) {
            LockFreeLinkedListNode originalNext;
            boolean z = obj == null;
            LockFreeLinkedListNode affectedNode = getAffectedNode();
            if (affectedNode == null || (originalNext = getOriginalNext()) == null) {
                return;
            }
            if (affectedNode._next.compareAndSet(atomicOp, z ? updatedNext(originalNext) : originalNext) && z) {
                finishOnSuccess(originalNext);
            }
        }

        public Object failure(LockFreeLinkedListNode lockFreeLinkedListNode) {
            return null;
        }

        public abstract void finishOnSuccess(LockFreeLinkedListNode lockFreeLinkedListNode);

        public abstract void finishPrepare(PrepareOp prepareOp);

        public abstract LockFreeLinkedListNode getAffectedNode();

        public abstract LockFreeLinkedListNode getOriginalNext();

        public Object onPrepare(PrepareOp prepareOp) {
            finishPrepare(prepareOp);
            return null;
        }

        @Override // kotlinx.coroutines.internal.AtomicDesc
        public final Object prepare(AtomicOp atomicOp) {
            while (true) {
                LockFreeLinkedListNode takeAffectedNode = takeAffectedNode(atomicOp);
                if (takeAffectedNode == null) {
                    return AtomicKt.RETRY_ATOMIC;
                }
                Object obj = takeAffectedNode._next.value;
                if (obj == atomicOp) {
                    return null;
                }
                if (atomicOp._consensus.value != AtomicKt.NO_DECISION) {
                    return null;
                }
                if (obj instanceof OpDescriptor) {
                    OpDescriptor opDescriptor = (OpDescriptor) obj;
                    if (atomicOp.isEarlierThan(opDescriptor)) {
                        return AtomicKt.RETRY_ATOMIC;
                    }
                    opDescriptor.perform(takeAffectedNode);
                } else {
                    Object failure = failure(takeAffectedNode);
                    if (failure != null) {
                        return failure;
                    }
                    if (retry(obj)) {
                        continue;
                    } else {
                        PrepareOp prepareOp = new PrepareOp(takeAffectedNode, (LockFreeLinkedListNode) obj, this);
                        if (takeAffectedNode._next.compareAndSet(obj, prepareOp)) {
                            try {
                                if (prepareOp.perform(takeAffectedNode) != LockFreeLinkedList_commonKt.REMOVE_PREPARED) {
                                    return null;
                                }
                            } catch (Throwable th) {
                                takeAffectedNode._next.compareAndSet(prepareOp, obj);
                                throw th;
                            }
                        } else {
                            continue;
                        }
                    }
                }
            }
        }

        public boolean retry(Object obj) {
            return false;
        }

        public LockFreeLinkedListNode takeAffectedNode(OpDescriptor opDescriptor) {
            LockFreeLinkedListNode affectedNode = getAffectedNode();
            Intrinsics.checkNotNull(affectedNode);
            return affectedNode;
        }

        public abstract Removed updatedNext(LockFreeLinkedListNode lockFreeLinkedListNode);

        public void onRemoved(LockFreeLinkedListNode lockFreeLinkedListNode) {
        }
    }
}
