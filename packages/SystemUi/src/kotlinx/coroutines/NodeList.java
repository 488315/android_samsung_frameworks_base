package kotlinx.coroutines;

import kotlinx.coroutines.internal.LockFreeLinkedListHead;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class NodeList extends LockFreeLinkedListHead implements Incomplete {
    @Override // kotlinx.coroutines.Incomplete
    public final boolean isActive() {
        return true;
    }

    @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode
    public final String toString() {
        return super.toString();
    }

    @Override // kotlinx.coroutines.Incomplete
    public final NodeList getList() {
        return this;
    }
}
