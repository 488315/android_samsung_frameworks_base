package kotlinx.coroutines;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class JobImpl extends JobSupport implements Job {
    public final boolean handlesException;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JobImpl(Job job) {
        super(true);
        boolean z = true;
        initParentJob(job);
        ChildHandle childHandle = (ChildHandle) this._parentHandle.value;
        ChildHandleNode childHandleNode = childHandle instanceof ChildHandleNode ? (ChildHandleNode) childHandle : null;
        if (childHandleNode != null) {
            JobSupport jobSupport = childHandleNode.job;
            jobSupport = jobSupport == null ? null : jobSupport;
            if (jobSupport != null) {
                while (!jobSupport.getHandlesException$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                    ChildHandle childHandle2 = (ChildHandle) jobSupport._parentHandle.value;
                    ChildHandleNode childHandleNode2 = childHandle2 instanceof ChildHandleNode ? (ChildHandleNode) childHandle2 : null;
                    if (childHandleNode2 != null) {
                        jobSupport = childHandleNode2.job;
                        if (jobSupport == null) {
                            jobSupport = null;
                        }
                        if (jobSupport == null) {
                        }
                    }
                }
                this.handlesException = z;
            }
        }
        z = false;
        this.handlesException = z;
    }

    @Override // kotlinx.coroutines.JobSupport
    public final boolean getHandlesException$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() {
        return this.handlesException;
    }

    @Override // kotlinx.coroutines.JobSupport
    public final boolean getOnCancelComplete$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() {
        return true;
    }
}
