package kotlinx.coroutines.internal;

import kotlin.jvm.internal.PropertyReference0Impl;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.coroutines.DebugStringsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class LockFreeLinkedListNode {
    public final AtomicRef _next = AtomicFU.atomic(this);
    public final AtomicRef _prev = AtomicFU.atomic(this);
    public final AtomicRef _removedRef = AtomicFU.atomic((Object) null);

    public final boolean addLast(LockFreeLinkedListNode lockFreeLinkedListNode, int i) {
        LockFreeLinkedListNode correctPrev;
        do {
            correctPrev = correctPrev();
            if (correctPrev == null) {
                Object obj = this._prev.value;
                while (true) {
                    correctPrev = (LockFreeLinkedListNode) obj;
                    if (!correctPrev.isRemoved()) {
                        break;
                    }
                    obj = correctPrev._prev.value;
                }
            }
            if (correctPrev instanceof ListClosed) {
                return (((ListClosed) correctPrev).forbiddenElementsBitmask & i) == 0 && correctPrev.addLast(lockFreeLinkedListNode, i);
            }
            lockFreeLinkedListNode._prev.lazySet(correctPrev);
            lockFreeLinkedListNode._next.lazySet(this);
        } while (!correctPrev._next.compareAndSet(this, lockFreeLinkedListNode));
        lockFreeLinkedListNode.finishAdd(this);
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0033, code lost:
    
        if (r3._next.compareAndSet(r2, ((kotlinx.coroutines.internal.Removed) r4).ref) != false) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x001b, code lost:
    
        return r2;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final kotlinx.coroutines.internal.LockFreeLinkedListNode correctPrev() {
        /*
            r7 = this;
        L0:
            kotlinx.atomicfu.AtomicRef r0 = r7._prev
            java.lang.Object r0 = r0.value
            kotlinx.coroutines.internal.LockFreeLinkedListNode r0 = (kotlinx.coroutines.internal.LockFreeLinkedListNode) r0
            r1 = 0
            r2 = r0
        L8:
            r3 = r1
        L9:
            kotlinx.atomicfu.AtomicRef r4 = r2._next
            java.lang.Object r4 = r4.value
            if (r4 != r7) goto L1c
            if (r0 != r2) goto L12
            goto L1b
        L12:
            kotlinx.atomicfu.AtomicRef r1 = r7._prev
            boolean r0 = r1.compareAndSet(r0, r2)
            if (r0 != 0) goto L1b
            goto L0
        L1b:
            return r2
        L1c:
            boolean r5 = r7.isRemoved()
            if (r5 == 0) goto L23
            return r1
        L23:
            boolean r5 = r4 instanceof kotlinx.coroutines.internal.Removed
            if (r5 == 0) goto L3f
            if (r3 == 0) goto L38
            kotlinx.atomicfu.AtomicRef r5 = r3._next
            kotlinx.coroutines.internal.Removed r4 = (kotlinx.coroutines.internal.Removed) r4
            kotlinx.coroutines.internal.LockFreeLinkedListNode r4 = r4.ref
            boolean r2 = r5.compareAndSet(r2, r4)
            if (r2 != 0) goto L36
            goto L0
        L36:
            r2 = r3
            goto L8
        L38:
            kotlinx.atomicfu.AtomicRef r2 = r2._prev
            java.lang.Object r2 = r2.value
            kotlinx.coroutines.internal.LockFreeLinkedListNode r2 = (kotlinx.coroutines.internal.LockFreeLinkedListNode) r2
            goto L9
        L3f:
            r3 = r4
            kotlinx.coroutines.internal.LockFreeLinkedListNode r3 = (kotlinx.coroutines.internal.LockFreeLinkedListNode) r3
            r6 = r3
            r3 = r2
            r2 = r6
            goto L9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.internal.LockFreeLinkedListNode.correctPrev():kotlinx.coroutines.internal.LockFreeLinkedListNode");
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
        return new PropertyReference0Impl(this) { // from class: kotlinx.coroutines.internal.LockFreeLinkedListNode$toString$1
            @Override // kotlin.jvm.internal.PropertyReference0Impl, kotlin.reflect.KProperty0
            public final Object get() {
                return this.receiver.getClass().getSimpleName();
            }
        } + "@" + DebugStringsKt.getHexAddress(this);
    }
}
