package kotlinx.coroutines;

import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class InvokeOnCancel extends CancelHandler {
    public final Function1 handler;

    public InvokeOnCancel(Function1 function1) {
        this.handler = function1;
    }

    @Override // kotlin.jvm.functions.Function1
    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
        invoke((Throwable) obj);
        return Unit.INSTANCE;
    }

    public final String toString() {
        return MotionLayout$$ExternalSyntheticOutline0.m22m("InvokeOnCancel[", DebugStringsKt.getClassSimpleName(this.handler), "@", DebugStringsKt.getHexAddress(this), "]");
    }

    @Override // kotlinx.coroutines.CancelHandlerBase
    public final void invoke(Throwable th) {
        this.handler.invoke(th);
    }
}
