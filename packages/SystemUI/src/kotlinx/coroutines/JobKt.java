package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes4.dex */
public abstract class JobKt {
    public static JobImpl Job$default() {
        return new JobImpl(null);
    }

    public static final void cancel(CoroutineContext coroutineContext, CancellationException cancellationException) {
        Job job = (Job) coroutineContext.get(Job.Key);
        if (job != null) {
            job.cancel(cancellationException);
        }
    }

    public static final Object cancelAndJoin(Job job, ContinuationImpl continuationImpl) {
        job.cancel(null);
        Object join = job.join(continuationImpl);
        return join == CoroutineSingletons.COROUTINE_SUSPENDED ? join : Unit.INSTANCE;
    }

    public static final void ensureActive(CoroutineContext coroutineContext) {
        Job job = (Job) coroutineContext.get(Job.Key);
        if (job != null && !job.isActive()) {
            throw job.getCancellationException();
        }
    }

    public static final Job getJob(CoroutineContext coroutineContext) {
        Job job = (Job) coroutineContext.get(Job.Key);
        if (job != null) {
            return job;
        }
        throw new IllegalStateException(("Current context doesn't contain Job in it: " + coroutineContext).toString());
    }

    public static DisposableHandle invokeOnCompletion$default(Job job, JobNode jobNode) {
        return job instanceof JobSupport ? ((JobSupport) job).invokeOnCompletionInternal$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(true, jobNode) : job.invokeOnCompletion(jobNode.getOnCancelling(), true, new JobKt__JobKt$invokeOnCompletion$1(jobNode));
    }

    public static final boolean isActive(CoroutineContext coroutineContext) {
        Job job = (Job) coroutineContext.get(Job.Key);
        if (job != null) {
            return job.isActive();
        }
        return true;
    }
}
