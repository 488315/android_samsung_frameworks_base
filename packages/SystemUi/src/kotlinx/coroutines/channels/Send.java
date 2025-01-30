package kotlinx.coroutines.channels;

import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.Symbol;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class Send extends LockFreeLinkedListNode {
    public abstract void completeResumeSend();

    public abstract Object getPollResult();

    public abstract void resumeSendClosed(Closed closed);

    public abstract Symbol tryResumeSend(LockFreeLinkedListNode.PrepareOp prepareOp);

    public void undeliveredElement() {
    }
}
