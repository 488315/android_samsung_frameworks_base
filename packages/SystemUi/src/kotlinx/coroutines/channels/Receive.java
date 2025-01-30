package kotlinx.coroutines.channels;

import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class Receive extends LockFreeLinkedListNode implements ReceiveOrClosed {
    @Override // kotlinx.coroutines.channels.ReceiveOrClosed
    public final Object getOfferResult() {
        return AbstractChannelKt.OFFER_SUCCESS;
    }

    public Function1 resumeOnCancellationFun(Object obj) {
        return null;
    }

    public abstract void resumeReceiveClosed(Closed closed);
}
