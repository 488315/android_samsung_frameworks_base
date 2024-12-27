package com.android.server.knox.zt.devicetrust.task;

import com.android.server.knox.zt.devicetrust.EndpointMonitorImpl;

import com.samsung.android.knox.zt.devicetrust.IEndpointMonitorListener;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

public abstract class ReschedulableMonitoringTask extends SchedulableMonitoringTask {
    public final String mFingerprint;
    public final AtomicBoolean mIsScheduled;
    public volatile boolean mIsStandby;
    public final TaskRescheduler mTaskRescheduler;

    public ReschedulableMonitoringTask(
            int i,
            int i2,
            int i3,
            int i4,
            int i5,
            IEndpointMonitorListener iEndpointMonitorListener,
            Predicate predicate,
            EndpointMonitorImpl.Injector injector) {
        super(i, i2, i3, i4, i5, iEndpointMonitorListener, predicate, injector);
        this.mTaskRescheduler = injector.mTaskRescheduler;
        this.mFingerprint = TaskRescheduler.generateFingerprint(i, i2, i3, i4, i5);
        this.mIsScheduled = new AtomicBoolean(false);
    }

    public final void destroy() {
        this.mTaskRescheduler.destroy(this);
    }

    public final void destroyChecked() {
        if (this.mIsStandby) {
            destroy();
        }
    }

    public final String getFingerprint() {
        return this.mFingerprint;
    }

    public final boolean isStandby() {
        return this.mIsStandby;
    }

    public final void keep() {
        this.mTaskRescheduler.keep(this);
    }

    @Override // com.android.server.knox.zt.devicetrust.task.SchedulableMonitoringTask
    public final void schedule() {
        if (this.mIsScheduled.getAndSet(true)) {
            return;
        }
        super.schedule();
    }

    public final void setFilter(Predicate predicate) {
        this.mFilter = ensureFilter(predicate);
    }

    public final void setListener(IEndpointMonitorListener iEndpointMonitorListener) {
        this.mListener = iEndpointMonitorListener;
    }

    public final void setStandby(boolean z) {
        this.mIsStandby = z;
    }
}
