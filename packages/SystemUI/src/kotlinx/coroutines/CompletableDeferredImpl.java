package kotlinx.coroutines;

import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class CompletableDeferredImpl extends JobSupport implements CompletableDeferred {
    public CompletableDeferredImpl(Job job) {
        super(true);
        initParentJob(job);
    }

    @Override // kotlinx.coroutines.Deferred
    public final Object await(ContinuationImpl continuationImpl) {
        Object awaitInternal = awaitInternal(continuationImpl);
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        return awaitInternal;
    }

    public final boolean completeExceptionally(Throwable th) {
        return makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(new CompletedExceptionally(th, false, 2, null));
    }
}
