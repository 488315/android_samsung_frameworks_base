package kotlinx.coroutines.internal;

import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicRef;

/* loaded from: classes4.dex */
public abstract class ConcurrentLinkedListNode {
    public final AtomicRef _next = AtomicFU.atomic((Object) null);
    public final AtomicRef _prev;

    public ConcurrentLinkedListNode(ConcurrentLinkedListNode concurrentLinkedListNode) {
        this._prev = AtomicFU.atomic(concurrentLinkedListNode);
    }

    public final void cleanPrev() {
        this._prev.lazySet(null);
    }

    public final ConcurrentLinkedListNode getNext() {
        Object obj = this._next.value;
        if (obj == ConcurrentLinkedListKt.CLOSED) {
            return null;
        }
        return (ConcurrentLinkedListNode) obj;
    }

    public abstract boolean isRemoved();

    public final void remove() {
        Object obj;
        ConcurrentLinkedListNode next;
        if (getNext() == null) {
            return;
        }
        while (true) {
            ConcurrentLinkedListNode concurrentLinkedListNode = (ConcurrentLinkedListNode) this._prev.value;
            while (concurrentLinkedListNode != null && concurrentLinkedListNode.isRemoved()) {
                concurrentLinkedListNode = (ConcurrentLinkedListNode) concurrentLinkedListNode._prev.value;
            }
            ConcurrentLinkedListNode next2 = getNext();
            next2.getClass();
            while (next2.isRemoved() && (next = next2.getNext()) != null) {
                next2 = next;
            }
            AtomicRef atomicRef = next2._prev;
            do {
                obj = atomicRef.value;
            } while (!atomicRef.compareAndSet(obj, ((ConcurrentLinkedListNode) obj) == null ? null : concurrentLinkedListNode));
            if (concurrentLinkedListNode != null) {
                concurrentLinkedListNode._next.setValue(next2);
            }
            if (!next2.isRemoved() || next2.getNext() == null) {
                if (concurrentLinkedListNode == null || !concurrentLinkedListNode.isRemoved()) {
                    return;
                }
            }
        }
    }
}
