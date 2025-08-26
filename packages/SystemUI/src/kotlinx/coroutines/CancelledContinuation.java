package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import kotlin.coroutines.Continuation;
import kotlinx.atomicfu.AtomicBoolean;
import kotlinx.atomicfu.AtomicFU;

/* loaded from: classes4.dex */
public final class CancelledContinuation extends CompletedExceptionally {
    public final AtomicBoolean _resumed;

    public CancelledContinuation(Continuation continuation, Throwable th, boolean z) {
        if (th == null) {
            th = new CancellationException("Continuation " + continuation + " was cancelled normally");
        }
        super(th, z);
        this._resumed = AtomicFU.atomic(false);
    }
}
