package kotlinx.coroutines;

import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function1;

/* loaded from: classes4.dex */
public interface CancelHandler extends NotCompleted {

    public final class UserSupplied implements CancelHandler {
        public final Function1 handler;

        public UserSupplied(Function1 function1) {
            this.handler = function1;
        }

        @Override // kotlinx.coroutines.CancelHandler
        public final void invoke(Throwable th) {
            this.handler.mo781invoke(th);
        }

        public final String toString() {
            return MotionLayout$$ExternalSyntheticOutline0.m("CancelHandler.UserSupplied[", this.handler.getClass().getSimpleName(), "@", DebugStringsKt.getHexAddress(this), "]");
        }
    }

    void invoke(Throwable th);
}
