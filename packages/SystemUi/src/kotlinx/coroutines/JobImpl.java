package kotlinx.coroutines;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class JobImpl extends JobSupport {
    public final boolean handlesException;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JobImpl(Job job) {
        super(true);
        JobSupport job2;
        boolean z = true;
        initParentJob(job);
        ChildHandle childHandle = (ChildHandle) this._parentHandle.value;
        ChildHandleNode childHandleNode = childHandle instanceof ChildHandleNode ? (ChildHandleNode) childHandle : null;
        if (childHandleNode != null && (job2 = childHandleNode.getJob()) != null) {
            while (!job2.mo290x3da20e56()) {
                ChildHandle childHandle2 = (ChildHandle) job2._parentHandle.value;
                ChildHandleNode childHandleNode2 = childHandle2 instanceof ChildHandleNode ? (ChildHandleNode) childHandle2 : null;
                if (childHandleNode2 != null && (job2 = childHandleNode2.getJob()) != null) {
                }
            }
            this.handlesException = z;
        }
        z = false;
        this.handlesException = z;
    }

    @Override // kotlinx.coroutines.JobSupport
    /* renamed from: getHandlesException$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    public final boolean mo290x3da20e56() {
        return this.handlesException;
    }

    @Override // kotlinx.coroutines.JobSupport
    /* renamed from: getOnCancelComplete$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    public final boolean mo291x6aca53c8() {
        return true;
    }
}
