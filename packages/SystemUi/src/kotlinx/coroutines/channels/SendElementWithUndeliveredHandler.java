package kotlinx.coroutines.channels;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineExceptionHandlerKt;
import kotlinx.coroutines.internal.OnUndeliveredElementKt;
import kotlinx.coroutines.internal.UndeliveredElementException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class SendElementWithUndeliveredHandler extends SendElement {
    public final Function1 onUndeliveredElement;

    public SendElementWithUndeliveredHandler(Object obj, CancellableContinuation cancellableContinuation, Function1 function1) {
        super(obj, cancellableContinuation);
        this.onUndeliveredElement = function1;
    }

    @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode
    public final boolean remove() {
        if (!super.remove()) {
            return false;
        }
        undeliveredElement();
        return true;
    }

    @Override // kotlinx.coroutines.channels.Send
    public final void undeliveredElement() {
        CoroutineContext coroutineContext = ((CancellableContinuationImpl) this.cont).context;
        UndeliveredElementException callUndeliveredElementCatchingException = OnUndeliveredElementKt.callUndeliveredElementCatchingException(this.onUndeliveredElement, this.pollResult, null);
        if (callUndeliveredElementCatchingException != null) {
            CoroutineExceptionHandlerKt.handleCoroutineException(callUndeliveredElementCatchingException, coroutineContext);
        }
    }
}
