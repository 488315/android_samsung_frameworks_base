package kotlinx.coroutines;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class JobNode extends CompletionHandlerBase implements DisposableHandle, Incomplete {
    public JobSupport job;

    @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode, kotlinx.coroutines.DisposableHandle
    public final void dispose() {
        Object m293x8adbf455;
        JobSupport job = getJob();
        do {
            m293x8adbf455 = job.m293x8adbf455();
            if (!(m293x8adbf455 instanceof JobNode)) {
                if (!(m293x8adbf455 instanceof Incomplete) || ((Incomplete) m293x8adbf455).getList() == null) {
                    return;
                }
                remove();
                return;
            }
            if (m293x8adbf455 != this) {
                return;
            }
        } while (!job._state.compareAndSet(m293x8adbf455, JobSupportKt.EMPTY_ACTIVE));
    }

    public final JobSupport getJob() {
        JobSupport jobSupport = this.job;
        if (jobSupport != null) {
            return jobSupport;
        }
        return null;
    }

    @Override // kotlinx.coroutines.Incomplete
    public final NodeList getList() {
        return null;
    }

    public Job getParent() {
        return getJob();
    }

    @Override // kotlinx.coroutines.Incomplete
    public final boolean isActive() {
        return true;
    }

    @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode
    public final String toString() {
        return DebugStringsKt.getClassSimpleName(this) + "@" + DebugStringsKt.getHexAddress(this) + "[job@" + DebugStringsKt.getHexAddress(getJob()) + "]";
    }
}
