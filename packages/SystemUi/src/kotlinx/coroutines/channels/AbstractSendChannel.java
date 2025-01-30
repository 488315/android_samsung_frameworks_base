package kotlinx.coroutines.channels;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import kotlin.ExceptionsKt__ExceptionsKt;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CancellableContinuationImplKt;
import kotlinx.coroutines.CancellableContinuationKt;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.RemoveOnCancel;
import kotlinx.coroutines.channels.ChannelResult;
import kotlinx.coroutines.internal.InlineList;
import kotlinx.coroutines.internal.LockFreeLinkedListHead;
import kotlinx.coroutines.internal.LockFreeLinkedListKt;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.OnUndeliveredElementKt;
import kotlinx.coroutines.internal.Removed;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.internal.UndeliveredElementException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class AbstractSendChannel implements SendChannel {
    public final Function1 onUndeliveredElement;
    public final LockFreeLinkedListHead queue = new LockFreeLinkedListHead();
    public final AtomicRef onCloseHandler = AtomicFU.atomic((Object) null);

    public AbstractSendChannel(Function1 function1) {
        this.onUndeliveredElement = function1;
    }

    public static final void access$helpCloseAndResumeWithSendException(AbstractSendChannel abstractSendChannel, CancellableContinuationImpl cancellableContinuationImpl, Object obj, Closed closed) {
        UndeliveredElementException callUndeliveredElementCatchingException;
        abstractSendChannel.getClass();
        helpClose(closed);
        Throwable th = closed.closeCause;
        if (th == null) {
            th = new ClosedSendChannelException("Channel was closed");
        }
        Function1 function1 = abstractSendChannel.onUndeliveredElement;
        if (function1 == null || (callUndeliveredElementCatchingException = OnUndeliveredElementKt.callUndeliveredElementCatchingException(function1, obj, null)) == null) {
            int i = Result.$r8$clinit;
            cancellableContinuationImpl.resumeWith(new Result.Failure(th));
        } else {
            ExceptionsKt__ExceptionsKt.addSuppressed(callUndeliveredElementCatchingException, th);
            int i2 = Result.$r8$clinit;
            cancellableContinuationImpl.resumeWith(new Result.Failure(callUndeliveredElementCatchingException));
        }
    }

    public static void helpClose(Closed closed) {
        Object obj = null;
        while (true) {
            LockFreeLinkedListNode prevNode = closed.getPrevNode();
            Receive receive = prevNode instanceof Receive ? (Receive) prevNode : null;
            if (receive == null) {
                break;
            } else if (receive.remove()) {
                obj = InlineList.m2876plusFjFbRPM(obj, receive);
            } else {
                ((Removed) receive.getNext()).ref.helpRemovePrev();
            }
        }
        if (obj != null) {
            if (!(obj instanceof ArrayList)) {
                ((Receive) obj).resumeReceiveClosed(closed);
                return;
            }
            ArrayList arrayList = (ArrayList) obj;
            for (int size = arrayList.size() - 1; -1 < size; size--) {
                ((Receive) arrayList.get(size)).resumeReceiveClosed(closed);
            }
        }
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    public final boolean close(Throwable th) {
        boolean z;
        Object obj;
        Symbol symbol;
        Closed closed = new Closed(th);
        LockFreeLinkedListHead lockFreeLinkedListHead = this.queue;
        while (true) {
            LockFreeLinkedListNode prevNode = lockFreeLinkedListHead.getPrevNode();
            if (!(!(prevNode instanceof Closed))) {
                z = false;
                break;
            }
            if (prevNode.addNext(closed, lockFreeLinkedListHead)) {
                z = true;
                break;
            }
        }
        if (!z) {
            closed = (Closed) this.queue.getPrevNode();
        }
        helpClose(closed);
        if (z && (obj = this.onCloseHandler.value) != null && obj != (symbol = AbstractChannelKt.HANDLER_INVOKED) && this.onCloseHandler.compareAndSet(obj, symbol)) {
            TypeIntrinsics.beforeCheckcastToFunctionOfArity(1, obj);
            ((Function1) obj).invoke(th);
        }
        return z;
    }

    public Object enqueueSend(final SendElement sendElement) {
        boolean z;
        LockFreeLinkedListNode prevNode;
        boolean isBufferAlwaysFull = isBufferAlwaysFull();
        LockFreeLinkedListHead lockFreeLinkedListHead = this.queue;
        if (isBufferAlwaysFull) {
            do {
                prevNode = lockFreeLinkedListHead.getPrevNode();
                if (prevNode instanceof ReceiveOrClosed) {
                    return prevNode;
                }
            } while (!prevNode.addNext(sendElement, lockFreeLinkedListHead));
            return null;
        }
        LockFreeLinkedListNode.CondAddOp condAddOp = new LockFreeLinkedListNode.CondAddOp(sendElement) { // from class: kotlinx.coroutines.channels.AbstractSendChannel$enqueueSend$$inlined$addLastIfPrevAndIf$1
            @Override // kotlinx.coroutines.internal.AtomicOp
            public final Object prepare(Object obj) {
                if (this.isBufferFull()) {
                    return null;
                }
                return LockFreeLinkedListKt.CONDITION_FALSE;
            }
        };
        while (true) {
            LockFreeLinkedListNode prevNode2 = lockFreeLinkedListHead.getPrevNode();
            if (!(prevNode2 instanceof ReceiveOrClosed)) {
                int tryCondAddNext = prevNode2.tryCondAddNext(sendElement, lockFreeLinkedListHead, condAddOp);
                z = true;
                if (tryCondAddNext != 1) {
                    if (tryCondAddNext == 2) {
                        z = false;
                        break;
                    }
                } else {
                    break;
                }
            } else {
                return prevNode2;
            }
        }
        if (z) {
            return null;
        }
        return AbstractChannelKt.ENQUEUE_FAILED;
    }

    public String getBufferDebugString() {
        return "";
    }

    public final Closed getClosedForSend() {
        LockFreeLinkedListNode prevNode = this.queue.getPrevNode();
        Closed closed = prevNode instanceof Closed ? (Closed) prevNode : null;
        if (closed == null) {
            return null;
        }
        helpClose(closed);
        return closed;
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    public final void invokeOnClose(Function1 function1) {
        if (!this.onCloseHandler.compareAndSet(null, function1)) {
            Object obj = this.onCloseHandler.value;
            if (obj == AbstractChannelKt.HANDLER_INVOKED) {
                throw new IllegalStateException("Another handler was already registered and successfully invoked");
            }
            throw new IllegalStateException("Another handler was already registered: " + obj);
        }
        Closed closedForSend = getClosedForSend();
        if (closedForSend == null || !this.onCloseHandler.compareAndSet(function1, AbstractChannelKt.HANDLER_INVOKED)) {
            return;
        }
        ((ProduceKt$awaitClose$4$1) function1).invoke(closedForSend.closeCause);
    }

    public abstract boolean isBufferAlwaysFull();

    public abstract boolean isBufferFull();

    @Override // kotlinx.coroutines.channels.SendChannel
    public final boolean isClosedForSend() {
        return getClosedForSend() != null;
    }

    public Object offerInternal(Object obj) {
        ReceiveOrClosed takeFirstReceiveOrPeekClosed;
        do {
            takeFirstReceiveOrPeekClosed = takeFirstReceiveOrPeekClosed();
            if (takeFirstReceiveOrPeekClosed == null) {
                return AbstractChannelKt.OFFER_FAILED;
            }
        } while (takeFirstReceiveOrPeekClosed.tryResumeReceive(obj) == null);
        takeFirstReceiveOrPeekClosed.completeResumeReceive(obj);
        return takeFirstReceiveOrPeekClosed.getOfferResult();
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    public final Object send(Object obj, Continuation continuation) {
        if (offerInternal(obj) == AbstractChannelKt.OFFER_SUCCESS) {
            return Unit.INSTANCE;
        }
        CancellableContinuationImpl orCreateCancellableContinuation = CancellableContinuationKt.getOrCreateCancellableContinuation(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation));
        while (true) {
            if (!(this.queue.getNextNode() instanceof ReceiveOrClosed) && isBufferFull()) {
                Function1 function1 = this.onUndeliveredElement;
                SendElement sendElement = function1 == null ? new SendElement(obj, orCreateCancellableContinuation) : new SendElementWithUndeliveredHandler(obj, orCreateCancellableContinuation, function1);
                Object enqueueSend = enqueueSend(sendElement);
                if (enqueueSend == null) {
                    orCreateCancellableContinuation.invokeOnCancellation(new RemoveOnCancel(sendElement));
                    break;
                }
                if (enqueueSend instanceof Closed) {
                    access$helpCloseAndResumeWithSendException(this, orCreateCancellableContinuation, obj, (Closed) enqueueSend);
                    break;
                }
                if (enqueueSend != AbstractChannelKt.ENQUEUE_FAILED && !(enqueueSend instanceof Receive)) {
                    throw new IllegalStateException(("enqueueSend returned " + enqueueSend).toString());
                }
            }
            Object offerInternal = offerInternal(obj);
            if (offerInternal == AbstractChannelKt.OFFER_SUCCESS) {
                int i = Result.$r8$clinit;
                orCreateCancellableContinuation.resumeWith(Unit.INSTANCE);
                break;
            }
            if (offerInternal != AbstractChannelKt.OFFER_FAILED) {
                if (!(offerInternal instanceof Closed)) {
                    throw new IllegalStateException(("offerInternal returned " + offerInternal).toString());
                }
                access$helpCloseAndResumeWithSendException(this, orCreateCancellableContinuation, obj, (Closed) offerInternal);
            }
        }
        Object result = orCreateCancellableContinuation.getResult();
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (result != coroutineSingletons) {
            result = Unit.INSTANCE;
        }
        return result == coroutineSingletons ? result : Unit.INSTANCE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [kotlinx.coroutines.internal.LockFreeLinkedListNode] */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v3 */
    public ReceiveOrClosed takeFirstReceiveOrPeekClosed() {
        ?? r0;
        LockFreeLinkedListNode removeOrNext;
        LockFreeLinkedListHead lockFreeLinkedListHead = this.queue;
        while (true) {
            r0 = (LockFreeLinkedListNode) lockFreeLinkedListHead.getNext();
            if (r0 != lockFreeLinkedListHead && (r0 instanceof ReceiveOrClosed)) {
                if (((((ReceiveOrClosed) r0) instanceof Closed) && !r0.isRemoved()) || (removeOrNext = r0.removeOrNext()) == null) {
                    break;
                }
                removeOrNext.helpRemovePrev();
            }
        }
        r0 = 0;
        return (ReceiveOrClosed) r0;
    }

    public final Send takeFirstSendOrPeekClosed() {
        LockFreeLinkedListNode lockFreeLinkedListNode;
        LockFreeLinkedListNode removeOrNext;
        LockFreeLinkedListHead lockFreeLinkedListHead = this.queue;
        while (true) {
            lockFreeLinkedListNode = (LockFreeLinkedListNode) lockFreeLinkedListHead.getNext();
            if (lockFreeLinkedListNode != lockFreeLinkedListHead && (lockFreeLinkedListNode instanceof Send)) {
                if (((((Send) lockFreeLinkedListNode) instanceof Closed) && !lockFreeLinkedListNode.isRemoved()) || (removeOrNext = lockFreeLinkedListNode.removeOrNext()) == null) {
                    break;
                }
                removeOrNext.helpRemovePrev();
            }
        }
        lockFreeLinkedListNode = null;
        return (Send) lockFreeLinkedListNode;
    }

    public final String toString() {
        String str;
        String str2;
        String classSimpleName = DebugStringsKt.getClassSimpleName(this);
        String hexAddress = DebugStringsKt.getHexAddress(this);
        LockFreeLinkedListNode lockFreeLinkedListNode = this.queue;
        LockFreeLinkedListNode nextNode = lockFreeLinkedListNode.getNextNode();
        if (nextNode == lockFreeLinkedListNode) {
            str2 = "EmptyQueue";
        } else {
            if (nextNode instanceof Closed) {
                str = nextNode.toString();
            } else if (nextNode instanceof Receive) {
                str = "ReceiveQueued";
            } else if (nextNode instanceof Send) {
                str = "SendQueued";
            } else {
                str = "UNEXPECTED:" + nextNode;
            }
            LockFreeLinkedListNode prevNode = lockFreeLinkedListNode.getPrevNode();
            if (prevNode != nextNode) {
                int i = 0;
                for (LockFreeLinkedListNode lockFreeLinkedListNode2 = (LockFreeLinkedListNode) lockFreeLinkedListNode.getNext(); !Intrinsics.areEqual(lockFreeLinkedListNode2, lockFreeLinkedListNode); lockFreeLinkedListNode2 = lockFreeLinkedListNode2.getNextNode()) {
                    i++;
                }
                str2 = str + ",queueSize=" + i;
                if (prevNode instanceof Closed) {
                    str2 = str2 + ",closedForSend=" + prevNode;
                }
            } else {
                str2 = str;
            }
        }
        String bufferDebugString = getBufferDebugString();
        StringBuilder sb = new StringBuilder();
        sb.append(classSimpleName);
        sb.append("@");
        sb.append(hexAddress);
        sb.append("{");
        sb.append(str2);
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(sb, "}", bufferDebugString);
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    /* renamed from: trySend-JP2dKIU, reason: not valid java name */
    public final Object mo2872trySendJP2dKIU(Object obj) {
        Object offerInternal = offerInternal(obj);
        if (offerInternal == AbstractChannelKt.OFFER_SUCCESS) {
            ChannelResult.Companion companion = ChannelResult.Companion;
            Unit unit = Unit.INSTANCE;
            companion.getClass();
            return unit;
        }
        if (offerInternal == AbstractChannelKt.OFFER_FAILED) {
            Closed closedForSend = getClosedForSend();
            if (closedForSend == null) {
                ChannelResult.Companion.getClass();
                return ChannelResult.failed;
            }
            ChannelResult.Companion companion2 = ChannelResult.Companion;
            helpClose(closedForSend);
            Throwable th = closedForSend.closeCause;
            if (th == null) {
                th = new ClosedSendChannelException("Channel was closed");
            }
            companion2.getClass();
            return ChannelResult.Companion.m2875closedJP2dKIU(th);
        }
        if (!(offerInternal instanceof Closed)) {
            throw new IllegalStateException(("trySend returned " + offerInternal).toString());
        }
        ChannelResult.Companion companion3 = ChannelResult.Companion;
        Closed closed = (Closed) offerInternal;
        helpClose(closed);
        Throwable th2 = closed.closeCause;
        if (th2 == null) {
            th2 = new ClosedSendChannelException("Channel was closed");
        }
        companion3.getClass();
        return ChannelResult.Companion.m2875closedJP2dKIU(th2);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SendBuffered extends Send {
        public final Object element;

        public SendBuffered(Object obj) {
            this.element = obj;
        }

        @Override // kotlinx.coroutines.channels.Send
        public final Object getPollResult() {
            return this.element;
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode
        public final String toString() {
            return "SendBuffered@" + DebugStringsKt.getHexAddress(this) + "(" + this.element + ")";
        }

        @Override // kotlinx.coroutines.channels.Send
        public final Symbol tryResumeSend(LockFreeLinkedListNode.PrepareOp prepareOp) {
            Symbol symbol = CancellableContinuationImplKt.RESUME_TOKEN;
            if (prepareOp != null) {
                prepareOp.finishPrepare();
            }
            return symbol;
        }

        @Override // kotlinx.coroutines.channels.Send
        public final void completeResumeSend() {
        }

        @Override // kotlinx.coroutines.channels.Send
        public final void resumeSendClosed(Closed closed) {
        }
    }
}
