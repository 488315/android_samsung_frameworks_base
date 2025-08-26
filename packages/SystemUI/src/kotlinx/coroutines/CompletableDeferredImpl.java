package kotlinx.coroutines;

import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes4.dex */
public final class CompletableDeferredImpl extends JobSupport implements CompletableDeferred {
    public CompletableDeferredImpl(Job job) {
        super(true);
        initParentJob(job);
    }

    @Override // kotlinx.coroutines.Deferred
    public final Object await(ContinuationImpl continuationImpl) {
        Object objAwaitInternal = awaitInternal(continuationImpl);
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        return objAwaitInternal;
    }

    public final boolean completeExceptionally(Throwable th) {
        return makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(new CompletedExceptionally(th, false, 2, null));
    }
}
