package kotlinx.coroutines;

import kotlin.coroutines.CoroutineContext;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class JobKt {
    public static final void ensureActive(CoroutineContext coroutineContext) {
        Job job = (Job) coroutineContext.get(Job.Key);
        if (job != null && !job.isActive()) {
            throw ((JobSupport) job).getCancellationException();
        }
    }
}
