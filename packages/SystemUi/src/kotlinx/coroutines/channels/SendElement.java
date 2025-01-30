package kotlinx.coroutines.channels;

import kotlin.Result;
import kotlin.Unit;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CancellableContinuationImplKt;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.Symbol;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class SendElement extends Send {
    public final CancellableContinuation cont;
    public final Object pollResult;

    public SendElement(Object obj, CancellableContinuation cancellableContinuation) {
        this.pollResult = obj;
        this.cont = cancellableContinuation;
    }

    @Override // kotlinx.coroutines.channels.Send
    public final void completeResumeSend() {
        Symbol symbol = CancellableContinuationImplKt.RESUME_TOKEN;
        CancellableContinuationImpl cancellableContinuationImpl = (CancellableContinuationImpl) this.cont;
        cancellableContinuationImpl.dispatchResume(cancellableContinuationImpl.resumeMode);
    }

    @Override // kotlinx.coroutines.channels.Send
    public final Object getPollResult() {
        return this.pollResult;
    }

    @Override // kotlinx.coroutines.channels.Send
    public final void resumeSendClosed(Closed closed) {
        int i = Result.$r8$clinit;
        Throwable th = closed.closeCause;
        if (th == null) {
            th = new ClosedSendChannelException("Channel was closed");
        }
        ((CancellableContinuationImpl) this.cont).resumeWith(new Result.Failure(th));
    }

    @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode
    public final String toString() {
        return DebugStringsKt.getClassSimpleName(this) + "@" + DebugStringsKt.getHexAddress(this) + "(" + this.pollResult + ")";
    }

    @Override // kotlinx.coroutines.channels.Send
    public final Symbol tryResumeSend(LockFreeLinkedListNode.PrepareOp prepareOp) {
        if (((CancellableContinuationImpl) this.cont).tryResumeImpl(Unit.INSTANCE, prepareOp != null ? prepareOp.desc : null, null) == null) {
            return null;
        }
        if (prepareOp != null) {
            prepareOp.finishPrepare();
        }
        return CancellableContinuationImplKt.RESUME_TOKEN;
    }
}
