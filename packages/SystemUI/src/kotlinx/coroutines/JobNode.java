package kotlinx.coroutines;

import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.Removed;

/* loaded from: classes4.dex */
public abstract class JobNode extends LockFreeLinkedListNode implements DisposableHandle, Incomplete {
    public JobSupport job;

    @Override // kotlinx.coroutines.DisposableHandle
    public final void dispose() {
        Object obj;
        Object obj2;
        LockFreeLinkedListNode lockFreeLinkedListNode;
        Removed removed;
        JobSupport jobSupport = this.job;
        if (jobSupport == null) {
            jobSupport = null;
        }
        do {
            obj = jobSupport._state.value;
            if (!(obj instanceof JobNode)) {
                if (!(obj instanceof Incomplete) || ((Incomplete) obj).getList() == null) {
                    return;
                }
                do {
                    obj2 = this._next.value;
                    if (obj2 instanceof Removed) {
                        LockFreeLinkedListNode lockFreeLinkedListNode2 = ((Removed) obj2).ref;
                        return;
                    }
                    if (obj2 == this) {
                        return;
                    }
                    lockFreeLinkedListNode = (LockFreeLinkedListNode) obj2;
                    removed = (Removed) lockFreeLinkedListNode._removedRef.value;
                    if (removed == null) {
                        removed = new Removed(lockFreeLinkedListNode);
                        lockFreeLinkedListNode._removedRef.lazySet(removed);
                    }
                } while (!this._next.compareAndSet(obj2, removed));
                lockFreeLinkedListNode.correctPrev();
                return;
            }
            if (obj != this) {
                return;
            }
        } while (!jobSupport._state.compareAndSet(obj, JobSupportKt.EMPTY_ACTIVE));
    }

    @Override // kotlinx.coroutines.Incomplete
    public final NodeList getList() {
        return null;
    }

    public abstract boolean getOnCancelling();

    public abstract void invoke(Throwable th);

    @Override // kotlinx.coroutines.Incomplete
    public final boolean isActive() {
        return true;
    }

    @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode
    public final String toString() {
        String simpleName = getClass().getSimpleName();
        String hexAddress = DebugStringsKt.getHexAddress(this);
        JobSupport jobSupport = this.job;
        if (jobSupport == null) {
            jobSupport = null;
        }
        return simpleName + "@" + hexAddress + "[job@" + DebugStringsKt.getHexAddress(jobSupport) + "]";
    }
}
