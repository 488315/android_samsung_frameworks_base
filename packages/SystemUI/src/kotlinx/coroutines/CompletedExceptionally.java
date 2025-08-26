package kotlinx.coroutines;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.atomicfu.AtomicBoolean;
import kotlinx.atomicfu.AtomicFU;

/* loaded from: classes4.dex */
public class CompletedExceptionally {
    public final AtomicBoolean _handled;
    public final Throwable cause;

    public CompletedExceptionally(Throwable th, boolean z) {
        this.cause = th;
        this._handled = AtomicFU.atomic(z);
    }

    public final String toString() {
        return getClass().getSimpleName() + "[" + this.cause + "]";
    }

    public /* synthetic */ CompletedExceptionally(Throwable th, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(th, (i & 2) != 0 ? false : z);
    }
}
