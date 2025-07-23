package kotlinx.coroutines;

import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface CancelHandler extends NotCompleted {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class UserSupplied implements CancelHandler {
        public final Function1 handler;

        public UserSupplied(Function1 function1) {
            this.handler = function1;
        }

        @Override // kotlinx.coroutines.CancelHandler
        public final void invoke(Throwable th) {
            this.handler.mo779invoke(th);
        }

        public final String toString() {
            return MotionLayout$$ExternalSyntheticOutline0.m("CancelHandler.UserSupplied[", this.handler.getClass().getSimpleName(), "@", DebugStringsKt.getHexAddress(this), "]");
        }
    }

    void invoke(Throwable th);
}
