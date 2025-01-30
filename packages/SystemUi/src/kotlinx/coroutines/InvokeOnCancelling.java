package kotlinx.coroutines;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicInt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class InvokeOnCancelling extends JobCancellingNode {
    public final AtomicInt _invoked = AtomicFU.atomic();
    public final Function1 handler;

    public InvokeOnCancelling(Function1 function1) {
        this.handler = function1;
    }

    @Override // kotlin.jvm.functions.Function1
    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
        invoke((Throwable) obj);
        return Unit.INSTANCE;
    }

    @Override // kotlinx.coroutines.CompletionHandlerBase
    public final void invoke(Throwable th) {
        if (this._invoked.compareAndSet(0, 1)) {
            this.handler.invoke(th);
        }
    }
}
