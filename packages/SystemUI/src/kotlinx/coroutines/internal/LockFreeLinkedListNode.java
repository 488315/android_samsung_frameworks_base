package kotlinx.coroutines.internal;

import kotlin.jvm.internal.PropertyReference0Impl;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.coroutines.DebugStringsKt;

/* loaded from: classes4.dex */
public class LockFreeLinkedListNode {
    public final AtomicRef _next = AtomicFU.atomic(this);
    public final AtomicRef _prev = AtomicFU.atomic(this);
    public final AtomicRef _removedRef = AtomicFU.atomic((Object) null);

    public final boolean addLast(LockFreeLinkedListNode lockFreeLinkedListNode, int i) {
        LockFreeLinkedListNode lockFreeLinkedListNodeCorrectPrev;
        do {
            lockFreeLinkedListNodeCorrectPrev = correctPrev();
            if (lockFreeLinkedListNodeCorrectPrev == null) {
                Object obj = this._prev.value;
                while (true) {
                    lockFreeLinkedListNodeCorrectPrev = (LockFreeLinkedListNode) obj;
                    if (!lockFreeLinkedListNodeCorrectPrev.isRemoved()) {
                        break;
                    }
                    obj = lockFreeLinkedListNodeCorrectPrev._prev.value;
                }
            }
            if (lockFreeLinkedListNodeCorrectPrev instanceof ListClosed) {
                return (((ListClosed) lockFreeLinkedListNodeCorrectPrev).forbiddenElementsBitmask & i) == 0 && lockFreeLinkedListNodeCorrectPrev.addLast(lockFreeLinkedListNode, i);
            }
            lockFreeLinkedListNode._prev.lazySet(lockFreeLinkedListNodeCorrectPrev);
            lockFreeLinkedListNode._next.lazySet(this);
        } while (!lockFreeLinkedListNodeCorrectPrev._next.compareAndSet(this, lockFreeLinkedListNode));
        lockFreeLinkedListNode.finishAdd(this);
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x001b, code lost:
    
        return r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0033, code lost:
    
        if (r3._next.compareAndSet(r2, ((kotlinx.coroutines.internal.Removed) r4).ref) != false) goto L21;
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
                        if (lockFreeLinkedListNode == lockFreeLinkedListNode2 || this._prev.compareAndSet(lockFreeLinkedListNode, lockFreeLinkedListNode2)) {
                            break;
                        }
                    } else {
                        if (isRemoved()) {
                            return null;
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

    public final void finishAdd(LockFreeLinkedListNode lockFreeLinkedListNode) {
        LockFreeLinkedListNode lockFreeLinkedListNode2;
        AtomicRef atomicRef = lockFreeLinkedListNode._prev;
        do {
            lockFreeLinkedListNode2 = (LockFreeLinkedListNode) atomicRef.value;
            if (this._next.value != lockFreeLinkedListNode) {
                return;
            }
        } while (!lockFreeLinkedListNode._prev.compareAndSet(lockFreeLinkedListNode2, this));
        if (isRemoved()) {
            lockFreeLinkedListNode.correctPrev();
        }
    }

    public final LockFreeLinkedListNode getNextNode() {
        LockFreeLinkedListNode lockFreeLinkedListNode;
        Object obj = this._next.value;
        Removed removed = obj instanceof Removed ? (Removed) obj : null;
        return (removed == null || (lockFreeLinkedListNode = removed.ref) == null) ? (LockFreeLinkedListNode) obj : lockFreeLinkedListNode;
    }

    public boolean isRemoved() {
        return this._next.value instanceof Removed;
    }

    public String toString() {
        return new PropertyReference0Impl(this) { // from class: kotlinx.coroutines.internal.LockFreeLinkedListNode.toString.1
            @Override // kotlin.jvm.internal.PropertyReference0Impl, kotlin.reflect.KProperty0
            public final Object get() {
                return this.receiver.getClass().getSimpleName();
            }
        } + "@" + DebugStringsKt.getHexAddress(this);
    }
}
