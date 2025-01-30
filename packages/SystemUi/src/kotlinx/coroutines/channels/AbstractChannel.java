package kotlinx.coroutines.channels;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import com.samsung.android.nexus.video.VideoPlayer;
import java.util.ArrayList;
import java.util.concurrent.CancellationException;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BeforeResumeCancelHandler;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CancellableContinuationImplKt;
import kotlinx.coroutines.CancellableContinuationKt;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.channels.AbstractChannel;
import kotlinx.coroutines.channels.ChannelResult;
import kotlinx.coroutines.internal.AtomicKt;
import kotlinx.coroutines.internal.DispatchedContinuationKt;
import kotlinx.coroutines.internal.InlineList;
import kotlinx.coroutines.internal.LockFreeLinkedListHead;
import kotlinx.coroutines.internal.LockFreeLinkedListKt;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.LockFreeLinkedList_commonKt;
import kotlinx.coroutines.internal.OnUndeliveredElementKt;
import kotlinx.coroutines.internal.Removed;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.intrinsics.UndispatchedKt;
import kotlinx.coroutines.selects.SelectBuilderImpl;
import kotlinx.coroutines.selects.SelectClause1;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.selects.SelectKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class AbstractChannel extends AbstractSendChannel implements Channel {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class ReceiveElement extends Receive {
        public final CancellableContinuation cont;
        public final int receiveMode;

        public ReceiveElement(CancellableContinuation cancellableContinuation, int i) {
            this.cont = cancellableContinuation;
            this.receiveMode = i;
        }

        @Override // kotlinx.coroutines.channels.ReceiveOrClosed
        public final void completeResumeReceive(Object obj) {
            Symbol symbol = CancellableContinuationImplKt.RESUME_TOKEN;
            CancellableContinuationImpl cancellableContinuationImpl = (CancellableContinuationImpl) this.cont;
            cancellableContinuationImpl.dispatchResume(cancellableContinuationImpl.resumeMode);
        }

        @Override // kotlinx.coroutines.channels.Receive
        public final void resumeReceiveClosed(Closed closed) {
            int i = this.receiveMode;
            CancellableContinuation cancellableContinuation = this.cont;
            if (i == 1) {
                ChannelResult.Companion companion = ChannelResult.Companion;
                Throwable th = closed.closeCause;
                companion.getClass();
                ChannelResult m2873boximpl = ChannelResult.m2873boximpl(ChannelResult.Companion.m2875closedJP2dKIU(th));
                int i2 = Result.$r8$clinit;
                ((CancellableContinuationImpl) cancellableContinuation).resumeWith(m2873boximpl);
                return;
            }
            int i3 = Result.$r8$clinit;
            Throwable th2 = closed.closeCause;
            if (th2 == null) {
                th2 = new ClosedReceiveChannelException("Channel was closed");
            }
            ((CancellableContinuationImpl) cancellableContinuation).resumeWith(new Result.Failure(th2));
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode
        public final String toString() {
            return "ReceiveElement@" + DebugStringsKt.getHexAddress(this) + "[receiveMode=" + this.receiveMode + "]";
        }

        @Override // kotlinx.coroutines.channels.ReceiveOrClosed
        public final Symbol tryResumeReceive(Object obj) {
            Object obj2;
            if (this.receiveMode == 1) {
                ChannelResult.Companion.getClass();
                obj2 = ChannelResult.m2873boximpl(obj);
            } else {
                obj2 = obj;
            }
            if (((CancellableContinuationImpl) this.cont).tryResumeImpl(obj2, null, resumeOnCancellationFun(obj)) == null) {
                return null;
            }
            return CancellableContinuationImplKt.RESUME_TOKEN;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ReceiveElementWithUndeliveredHandler extends ReceiveElement {
        public final Function1 onUndeliveredElement;

        public ReceiveElementWithUndeliveredHandler(CancellableContinuation cancellableContinuation, int i, Function1 function1) {
            super(cancellableContinuation, i);
            this.onUndeliveredElement = function1;
        }

        @Override // kotlinx.coroutines.channels.Receive
        public final Function1 resumeOnCancellationFun(Object obj) {
            return OnUndeliveredElementKt.bindCancellationFun(this.onUndeliveredElement, obj, ((CancellableContinuationImpl) this.cont).context);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ReceiveSelect extends Receive implements DisposableHandle {
        public final Function2 block;
        public final AbstractChannel channel;
        public final int receiveMode;
        public final SelectInstance select;

        public ReceiveSelect(AbstractChannel abstractChannel, SelectInstance selectInstance, Function2 function2, int i) {
            this.channel = abstractChannel;
            this.select = selectInstance;
            this.block = function2;
            this.receiveMode = i;
        }

        @Override // kotlinx.coroutines.channels.ReceiveOrClosed
        public final void completeResumeReceive(Object obj) {
            Object obj2;
            if (this.receiveMode == 1) {
                ChannelResult.Companion.getClass();
                obj2 = ChannelResult.m2873boximpl(obj);
            } else {
                obj2 = obj;
            }
            SelectBuilderImpl selectBuilderImpl = (SelectBuilderImpl) this.select;
            selectBuilderImpl.getClass();
            Function1 resumeOnCancellationFun = resumeOnCancellationFun(obj);
            try {
                Continuation intercepted = IntrinsicsKt__IntrinsicsJvmKt.intercepted(IntrinsicsKt__IntrinsicsJvmKt.createCoroutineUnintercepted(selectBuilderImpl, this.block, obj2));
                int i = Result.$r8$clinit;
                DispatchedContinuationKt.resumeCancellableWith(intercepted, Unit.INSTANCE, resumeOnCancellationFun);
            } catch (Throwable th) {
                int i2 = Result.$r8$clinit;
                selectBuilderImpl.resumeWith(new Result.Failure(th));
                throw th;
            }
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode, kotlinx.coroutines.DisposableHandle
        public final void dispose() {
            if (remove()) {
                this.channel.getClass();
            }
        }

        @Override // kotlinx.coroutines.channels.Receive
        public final Function1 resumeOnCancellationFun(Object obj) {
            Function1 function1 = this.channel.onUndeliveredElement;
            if (function1 == null) {
                return null;
            }
            SelectBuilderImpl selectBuilderImpl = (SelectBuilderImpl) this.select;
            selectBuilderImpl.getClass();
            return OnUndeliveredElementKt.bindCancellationFun(function1, obj, selectBuilderImpl.getContext());
        }

        @Override // kotlinx.coroutines.channels.Receive
        public final void resumeReceiveClosed(Closed closed) {
            SelectBuilderImpl selectBuilderImpl = (SelectBuilderImpl) this.select;
            if (selectBuilderImpl.trySelect()) {
                int i = this.receiveMode;
                if (i == 0) {
                    Throwable th = closed.closeCause;
                    if (th == null) {
                        th = new ClosedReceiveChannelException("Channel was closed");
                    }
                    selectBuilderImpl.resumeSelectWithException(th);
                    return;
                }
                if (i != 1) {
                    return;
                }
                Function2 function2 = this.block;
                ChannelResult.Companion companion = ChannelResult.Companion;
                Throwable th2 = closed.closeCause;
                companion.getClass();
                ChannelResult m2873boximpl = ChannelResult.m2873boximpl(ChannelResult.Companion.m2875closedJP2dKIU(th2));
                selectBuilderImpl.getClass();
                try {
                    Continuation intercepted = IntrinsicsKt__IntrinsicsJvmKt.intercepted(IntrinsicsKt__IntrinsicsJvmKt.createCoroutineUnintercepted(selectBuilderImpl, function2, m2873boximpl));
                    int i2 = Result.$r8$clinit;
                    DispatchedContinuationKt.resumeCancellableWith(intercepted, Unit.INSTANCE, null);
                } catch (Throwable th3) {
                    int i3 = Result.$r8$clinit;
                    selectBuilderImpl.resumeWith(new Result.Failure(th3));
                    throw th3;
                }
            }
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode
        public final String toString() {
            StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("ReceiveSelect@", DebugStringsKt.getHexAddress(this), "[");
            m4m.append(this.select);
            m4m.append(",receiveMode=");
            return ConstraintWidget$$ExternalSyntheticOutline0.m19m(m4m, this.receiveMode, "]");
        }

        @Override // kotlinx.coroutines.channels.ReceiveOrClosed
        public final Symbol tryResumeReceive(Object obj) {
            return (Symbol) ((SelectBuilderImpl) this.select).trySelectOther();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RemoveReceiveOnCancel extends BeforeResumeCancelHandler {
        public final Receive receive;

        public RemoveReceiveOnCancel(Receive receive) {
            this.receive = receive;
        }

        @Override // kotlin.jvm.functions.Function1
        public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((Throwable) obj);
            return Unit.INSTANCE;
        }

        public final String toString() {
            return "RemoveReceiveOnCancel[" + this.receive + "]";
        }

        @Override // kotlinx.coroutines.CancelHandlerBase
        public final void invoke(Throwable th) {
            if (this.receive.remove()) {
                AbstractChannel.this.getClass();
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TryPollDesc extends LockFreeLinkedListNode.RemoveFirstDesc {
        public TryPollDesc(LockFreeLinkedListHead lockFreeLinkedListHead) {
            super(lockFreeLinkedListHead);
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode.RemoveFirstDesc, kotlinx.coroutines.internal.LockFreeLinkedListNode.AbstractAtomicDesc
        public final Object failure(LockFreeLinkedListNode lockFreeLinkedListNode) {
            if (lockFreeLinkedListNode instanceof Closed) {
                return lockFreeLinkedListNode;
            }
            if (lockFreeLinkedListNode instanceof Send) {
                return null;
            }
            return AbstractChannelKt.POLL_FAILED;
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode.AbstractAtomicDesc
        public final Object onPrepare(LockFreeLinkedListNode.PrepareOp prepareOp) {
            Symbol tryResumeSend = ((Send) prepareOp.affected).tryResumeSend(prepareOp);
            if (tryResumeSend == null) {
                return LockFreeLinkedList_commonKt.REMOVE_PREPARED;
            }
            Symbol symbol = AtomicKt.RETRY_ATOMIC;
            if (tryResumeSend == symbol) {
                return symbol;
            }
            return null;
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode.AbstractAtomicDesc
        public final void onRemoved(LockFreeLinkedListNode lockFreeLinkedListNode) {
            ((Send) lockFreeLinkedListNode).undeliveredElement();
        }
    }

    public AbstractChannel(Function1 function1) {
        super(function1);
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    public final void cancel(CancellationException cancellationException) {
        if (isClosedForReceive()) {
            return;
        }
        if (cancellationException == null) {
            cancellationException = new CancellationException(DebugStringsKt.getClassSimpleName(this).concat(" was cancelled"));
        }
        onCancelIdempotent(close(cancellationException));
    }

    public boolean enqueueReceiveInternal(final Receive receive) {
        int tryCondAddNext;
        LockFreeLinkedListNode prevNode;
        boolean isBufferAlwaysEmpty = isBufferAlwaysEmpty();
        LockFreeLinkedListHead lockFreeLinkedListHead = this.queue;
        if (!isBufferAlwaysEmpty) {
            LockFreeLinkedListNode.CondAddOp condAddOp = new LockFreeLinkedListNode.CondAddOp(receive) { // from class: kotlinx.coroutines.channels.AbstractChannel$enqueueReceiveInternal$$inlined$addLastIfPrevAndIf$1
                @Override // kotlinx.coroutines.internal.AtomicOp
                public final Object prepare(Object obj) {
                    if (this.isBufferEmpty()) {
                        return null;
                    }
                    return LockFreeLinkedListKt.CONDITION_FALSE;
                }
            };
            do {
                LockFreeLinkedListNode prevNode2 = lockFreeLinkedListHead.getPrevNode();
                if (!(!(prevNode2 instanceof Send))) {
                    break;
                }
                tryCondAddNext = prevNode2.tryCondAddNext(receive, lockFreeLinkedListHead, condAddOp);
                if (tryCondAddNext == 1) {
                    return true;
                }
            } while (tryCondAddNext != 2);
        } else {
            do {
                prevNode = lockFreeLinkedListHead.getPrevNode();
                if (!(!(prevNode instanceof Send))) {
                }
            } while (!prevNode.addNext(receive, lockFreeLinkedListHead));
            return true;
        }
        return false;
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    public final SelectClause1 getOnReceiveCatching() {
        return new SelectClause1() { // from class: kotlinx.coroutines.channels.AbstractChannel$onReceiveCatching$1
            @Override // kotlinx.coroutines.selects.SelectClause1
            public final void registerSelectClause1(SelectBuilderImpl selectBuilderImpl, Function2 function2) {
                AbstractChannel abstractChannel = AbstractChannel.this;
                abstractChannel.getClass();
                while (!selectBuilderImpl.isSelected()) {
                    if (!(abstractChannel.queue.getNextNode() instanceof Send) && abstractChannel.isBufferEmpty()) {
                        AbstractChannel.ReceiveSelect receiveSelect = new AbstractChannel.ReceiveSelect(abstractChannel, selectBuilderImpl, function2, 1);
                        boolean enqueueReceiveInternal = abstractChannel.enqueueReceiveInternal(receiveSelect);
                        if (enqueueReceiveInternal) {
                            selectBuilderImpl.disposeOnSelect(receiveSelect);
                        }
                        if (enqueueReceiveInternal) {
                            return;
                        }
                    } else {
                        Object pollSelectInternal = abstractChannel.pollSelectInternal(selectBuilderImpl);
                        if (pollSelectInternal == SelectKt.ALREADY_SELECTED) {
                            return;
                        }
                        if (pollSelectInternal != AbstractChannelKt.POLL_FAILED && pollSelectInternal != AtomicKt.RETRY_ATOMIC) {
                            boolean z = pollSelectInternal instanceof Closed;
                            if (!z) {
                                if (z) {
                                    ChannelResult.Companion companion = ChannelResult.Companion;
                                    Throwable th = ((Closed) pollSelectInternal).closeCause;
                                    companion.getClass();
                                    pollSelectInternal = ChannelResult.Companion.m2875closedJP2dKIU(th);
                                } else {
                                    ChannelResult.Companion.getClass();
                                }
                                UndispatchedKt.startCoroutineUnintercepted(selectBuilderImpl, function2, ChannelResult.m2873boximpl(pollSelectInternal));
                            } else if (selectBuilderImpl.trySelect()) {
                                ChannelResult.Companion companion2 = ChannelResult.Companion;
                                Throwable th2 = ((Closed) pollSelectInternal).closeCause;
                                companion2.getClass();
                                UndispatchedKt.startCoroutineUnintercepted(selectBuilderImpl, function2, ChannelResult.m2873boximpl(ChannelResult.Companion.m2875closedJP2dKIU(th2)));
                            }
                        }
                    }
                }
            }
        };
    }

    public abstract boolean isBufferAlwaysEmpty();

    public abstract boolean isBufferEmpty();

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    public boolean isClosedForReceive() {
        LockFreeLinkedListNode nextNode = this.queue.getNextNode();
        Closed closed = null;
        Closed closed2 = nextNode instanceof Closed ? (Closed) nextNode : null;
        if (closed2 != null) {
            AbstractSendChannel.helpClose(closed2);
            closed = closed2;
        }
        return closed != null && isBufferEmpty();
    }

    public void onCancelIdempotent(boolean z) {
        Closed closedForSend = getClosedForSend();
        if (closedForSend == null) {
            throw new IllegalStateException("Cannot happen".toString());
        }
        Object obj = null;
        while (true) {
            LockFreeLinkedListNode prevNode = closedForSend.getPrevNode();
            if (prevNode instanceof LockFreeLinkedListHead) {
                mo2869onCancelIdempotentListww6eGU(obj, closedForSend);
                return;
            } else if (prevNode.remove()) {
                obj = InlineList.m2876plusFjFbRPM(obj, (Send) prevNode);
            } else {
                ((Removed) prevNode.getNext()).ref.helpRemovePrev();
            }
        }
    }

    /* renamed from: onCancelIdempotentList-w-w6eGU, reason: not valid java name */
    public void mo2869onCancelIdempotentListww6eGU(Object obj, Closed closed) {
        if (obj != null) {
            if (!(obj instanceof ArrayList)) {
                ((Send) obj).resumeSendClosed(closed);
                return;
            }
            ArrayList arrayList = (ArrayList) obj;
            for (int size = arrayList.size() - 1; -1 < size; size--) {
                ((Send) arrayList.get(size)).resumeSendClosed(closed);
            }
        }
    }

    public Object pollInternal() {
        while (true) {
            Send takeFirstSendOrPeekClosed = takeFirstSendOrPeekClosed();
            if (takeFirstSendOrPeekClosed == null) {
                return AbstractChannelKt.POLL_FAILED;
            }
            if (takeFirstSendOrPeekClosed.tryResumeSend(null) != null) {
                takeFirstSendOrPeekClosed.completeResumeSend();
                return takeFirstSendOrPeekClosed.getPollResult();
            }
            takeFirstSendOrPeekClosed.undeliveredElement();
        }
    }

    public Object pollSelectInternal(SelectBuilderImpl selectBuilderImpl) {
        TryPollDesc tryPollDesc = new TryPollDesc(this.queue);
        Object perform = new SelectBuilderImpl.AtomicSelectOp(selectBuilderImpl, tryPollDesc).perform(null);
        if (perform != null) {
            return perform;
        }
        LockFreeLinkedListNode affectedNode = tryPollDesc.getAffectedNode();
        Intrinsics.checkNotNull(affectedNode);
        ((Send) affectedNode).completeResumeSend();
        LockFreeLinkedListNode affectedNode2 = tryPollDesc.getAffectedNode();
        Intrinsics.checkNotNull(affectedNode2);
        return ((Send) affectedNode2).getPollResult();
    }

    public final Object receive(Continuation continuation) {
        Object pollInternal = pollInternal();
        return (pollInternal == AbstractChannelKt.POLL_FAILED || (pollInternal instanceof Closed)) ? receiveSuspend(0, (ContinuationImpl) continuation) : pollInternal;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // kotlinx.coroutines.channels.ReceiveChannel
    /* renamed from: receiveCatching-JP2dKIU, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object mo2870receiveCatchingJP2dKIU(Continuation continuation) {
        AbstractChannel$receiveCatching$1 abstractChannel$receiveCatching$1;
        int i;
        if (continuation instanceof AbstractChannel$receiveCatching$1) {
            abstractChannel$receiveCatching$1 = (AbstractChannel$receiveCatching$1) continuation;
            int i2 = abstractChannel$receiveCatching$1.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                abstractChannel$receiveCatching$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj = abstractChannel$receiveCatching$1.result;
                Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = abstractChannel$receiveCatching$1.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    Object pollInternal = pollInternal();
                    if (pollInternal != AbstractChannelKt.POLL_FAILED) {
                        if (!(pollInternal instanceof Closed)) {
                            ChannelResult.Companion.getClass();
                            return pollInternal;
                        }
                        ChannelResult.Companion companion = ChannelResult.Companion;
                        Throwable th = ((Closed) pollInternal).closeCause;
                        companion.getClass();
                        return ChannelResult.Companion.m2875closedJP2dKIU(th);
                    }
                    abstractChannel$receiveCatching$1.label = 1;
                    obj = receiveSuspend(1, abstractChannel$receiveCatching$1);
                    if (obj == obj2) {
                        return obj2;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                return ((ChannelResult) obj).holder;
            }
        }
        abstractChannel$receiveCatching$1 = new AbstractChannel$receiveCatching$1(this, continuation);
        Object obj3 = abstractChannel$receiveCatching$1.result;
        Object obj22 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = abstractChannel$receiveCatching$1.label;
        if (i != 0) {
        }
        return ((ChannelResult) obj3).holder;
    }

    public final Object receiveSuspend(int i, ContinuationImpl continuationImpl) {
        Object obj;
        CancellableContinuationImpl orCreateCancellableContinuation = CancellableContinuationKt.getOrCreateCancellableContinuation(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuationImpl));
        Function1 function1 = this.onUndeliveredElement;
        ReceiveElement receiveElement = function1 == null ? new ReceiveElement(orCreateCancellableContinuation, i) : new ReceiveElementWithUndeliveredHandler(orCreateCancellableContinuation, i, function1);
        while (true) {
            if (enqueueReceiveInternal(receiveElement)) {
                orCreateCancellableContinuation.invokeOnCancellation(new RemoveReceiveOnCancel(receiveElement));
                break;
            }
            Object pollInternal = pollInternal();
            if (pollInternal instanceof Closed) {
                receiveElement.resumeReceiveClosed((Closed) pollInternal);
                break;
            }
            if (pollInternal != AbstractChannelKt.POLL_FAILED) {
                if (receiveElement.receiveMode == 1) {
                    ChannelResult.Companion.getClass();
                    obj = ChannelResult.m2873boximpl(pollInternal);
                } else {
                    obj = pollInternal;
                }
                orCreateCancellableContinuation.resumeImpl(obj, orCreateCancellableContinuation.resumeMode, receiveElement.resumeOnCancellationFun(pollInternal));
            }
        }
        Object result = orCreateCancellableContinuation.getResult();
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        return result;
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    public final ReceiveOrClosed takeFirstReceiveOrPeekClosed() {
        ReceiveOrClosed takeFirstReceiveOrPeekClosed = super.takeFirstReceiveOrPeekClosed();
        if (takeFirstReceiveOrPeekClosed != null) {
            boolean z = takeFirstReceiveOrPeekClosed instanceof Closed;
        }
        return takeFirstReceiveOrPeekClosed;
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    /* renamed from: tryReceive-PtdJZtk, reason: not valid java name */
    public final Object mo2871tryReceivePtdJZtk() {
        Object pollInternal = pollInternal();
        if (pollInternal == AbstractChannelKt.POLL_FAILED) {
            ChannelResult.Companion.getClass();
            return ChannelResult.failed;
        }
        if (!(pollInternal instanceof Closed)) {
            ChannelResult.Companion.getClass();
            return pollInternal;
        }
        ChannelResult.Companion companion = ChannelResult.Companion;
        Throwable th = ((Closed) pollInternal).closeCause;
        companion.getClass();
        return ChannelResult.Companion.m2875closedJP2dKIU(th);
    }
}
