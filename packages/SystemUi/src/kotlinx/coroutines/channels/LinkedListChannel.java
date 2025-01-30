package kotlinx.coroutines.channels;

import java.util.ArrayList;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.channels.AbstractSendChannel;
import kotlinx.coroutines.internal.LockFreeLinkedListHead;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.OnUndeliveredElementKt;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.internal.UndeliveredElementException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class LinkedListChannel extends AbstractChannel {
    public LinkedListChannel(Function1 function1) {
        super(function1);
    }

    @Override // kotlinx.coroutines.channels.AbstractChannel
    public final boolean isBufferAlwaysEmpty() {
        return true;
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    public final boolean isBufferAlwaysFull() {
        return false;
    }

    @Override // kotlinx.coroutines.channels.AbstractChannel
    public final boolean isBufferEmpty() {
        return true;
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    public final boolean isBufferFull() {
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    public final Object offerInternal(Object obj) {
        ReceiveOrClosed receiveOrClosed;
        do {
            Object offerInternal = super.offerInternal(obj);
            Symbol symbol = AbstractChannelKt.OFFER_SUCCESS;
            if (offerInternal == symbol) {
                return symbol;
            }
            if (offerInternal != AbstractChannelKt.OFFER_FAILED) {
                if (offerInternal instanceof Closed) {
                    return offerInternal;
                }
                throw new IllegalStateException(("Invalid offerInternal result " + offerInternal).toString());
            }
            LockFreeLinkedListHead lockFreeLinkedListHead = this.queue;
            AbstractSendChannel.SendBuffered sendBuffered = new AbstractSendChannel.SendBuffered(obj);
            while (true) {
                LockFreeLinkedListNode prevNode = lockFreeLinkedListHead.getPrevNode();
                if (prevNode instanceof ReceiveOrClosed) {
                    receiveOrClosed = (ReceiveOrClosed) prevNode;
                    break;
                }
                if (prevNode.addNext(sendBuffered, lockFreeLinkedListHead)) {
                    receiveOrClosed = null;
                    break;
                }
            }
            if (receiveOrClosed == null) {
                return AbstractChannelKt.OFFER_SUCCESS;
            }
        } while (!(receiveOrClosed instanceof Closed));
        return receiveOrClosed;
    }

    @Override // kotlinx.coroutines.channels.AbstractChannel
    /* renamed from: onCancelIdempotentList-w-w6eGU */
    public final void mo2869onCancelIdempotentListww6eGU(Object obj, Closed closed) {
        UndeliveredElementException undeliveredElementException = null;
        if (obj != null) {
            boolean z = obj instanceof ArrayList;
            Function1 function1 = this.onUndeliveredElement;
            if (z) {
                ArrayList arrayList = (ArrayList) obj;
                UndeliveredElementException undeliveredElementException2 = null;
                for (int size = arrayList.size() - 1; -1 < size; size--) {
                    Send send = (Send) arrayList.get(size);
                    if (send instanceof AbstractSendChannel.SendBuffered) {
                        undeliveredElementException2 = function1 != null ? OnUndeliveredElementKt.callUndeliveredElementCatchingException(function1, ((AbstractSendChannel.SendBuffered) send).element, undeliveredElementException2) : null;
                    } else {
                        send.resumeSendClosed(closed);
                    }
                }
                undeliveredElementException = undeliveredElementException2;
            } else {
                Send send2 = (Send) obj;
                if (!(send2 instanceof AbstractSendChannel.SendBuffered)) {
                    send2.resumeSendClosed(closed);
                } else if (function1 != null) {
                    undeliveredElementException = OnUndeliveredElementKt.callUndeliveredElementCatchingException(function1, ((AbstractSendChannel.SendBuffered) send2).element, null);
                }
            }
        }
        if (undeliveredElementException != null) {
            throw undeliveredElementException;
        }
    }
}
