package androidx.lifecycle;

import androidx.lifecycle.Lifecycle;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.Job;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class LifecycleCoroutineScopeImpl extends LifecycleCoroutineScope implements LifecycleEventObserver {
    public final CoroutineContext coroutineContext;
    public final Lifecycle lifecycle;

    public LifecycleCoroutineScopeImpl(Lifecycle lifecycle, CoroutineContext coroutineContext) {
        Job job;
        this.lifecycle = lifecycle;
        this.coroutineContext = coroutineContext;
        if (lifecycle.getCurrentState() != Lifecycle.State.DESTROYED || (job = (Job) coroutineContext.get(Job.Key)) == null) {
            return;
        }
        job.cancel(null);
    }

    @Override // kotlinx.coroutines.CoroutineScope
    public final CoroutineContext getCoroutineContext() {
        return this.coroutineContext;
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        Lifecycle lifecycle = this.lifecycle;
        if (lifecycle.getCurrentState().compareTo(Lifecycle.State.DESTROYED) <= 0) {
            lifecycle.removeObserver(this);
            Job job = (Job) this.coroutineContext.get(Job.Key);
            if (job != null) {
                job.cancel(null);
            }
        }
    }
}
