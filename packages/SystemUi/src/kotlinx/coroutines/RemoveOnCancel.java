package kotlinx.coroutines;

import kotlin.Unit;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class RemoveOnCancel extends BeforeResumeCancelHandler {
    public final LockFreeLinkedListNode node;

    public RemoveOnCancel(LockFreeLinkedListNode lockFreeLinkedListNode) {
        this.node = lockFreeLinkedListNode;
    }

    @Override // kotlin.jvm.functions.Function1
    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
        invoke((Throwable) obj);
        return Unit.INSTANCE;
    }

    public final String toString() {
        return "RemoveOnCancel[" + this.node + "]";
    }

    @Override // kotlinx.coroutines.CancelHandlerBase
    public final void invoke(Throwable th) {
        this.node.remove();
    }
}
