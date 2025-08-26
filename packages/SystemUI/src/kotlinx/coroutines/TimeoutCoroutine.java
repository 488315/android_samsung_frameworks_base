package kotlinx.coroutines;

import kotlin.coroutines.Continuation;
import kotlinx.coroutines.internal.ScopeCoroutine;

/* loaded from: classes4.dex */
public final class TimeoutCoroutine extends ScopeCoroutine implements Runnable {
    public final long time;

    public TimeoutCoroutine(long j, Continuation continuation) {
        super(continuation.getContext(), continuation);
        this.time = j;
    }

    @Override // kotlinx.coroutines.JobSupport
    public final String nameString$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() {
        return super.nameString$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() + "(timeMillis=" + this.time + ")";
    }

    @Override // java.lang.Runnable
    public final void run() {
        long j = this.time;
        DelayKt.getDelay(this.context);
        cancelImpl$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(new TimeoutCancellationException("Timed out waiting for " + j + " ms", this));
    }
}
