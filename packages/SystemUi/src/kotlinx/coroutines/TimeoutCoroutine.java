package kotlinx.coroutines;

import kotlin.coroutines.Continuation;
import kotlinx.coroutines.internal.ScopeCoroutine;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class TimeoutCoroutine extends ScopeCoroutine implements Runnable {
    public final long time;

    public TimeoutCoroutine(long j, Continuation<Object> continuation) {
        super(continuation.getContext(), continuation);
        this.time = j;
    }

    @Override // kotlinx.coroutines.AbstractCoroutine, kotlinx.coroutines.JobSupport
    /* renamed from: nameString$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    public final String mo282x29b568d4() {
        return DebugStringsKt.getClassSimpleName(this) + "(timeMillis=" + this.time + ")";
    }

    @Override // java.lang.Runnable
    public final void run() {
        m292x7bd83b56(new TimeoutCancellationException("Timed out waiting for " + this.time + " ms", this));
    }
}
