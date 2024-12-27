package com.android.server.knox.zt.devicetrust.task;

import android.os.Handler;

import com.android.server.knox.zt.devicetrust.EndpointMonitorImpl;
import com.android.server.knox.zt.devicetrust.data.EndpointData;

import com.samsung.android.knox.zt.devicetrust.IEndpointMonitorListener;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Predicate;

public abstract class HandleableMonitoringTask extends MonitoringTask {
    public final Handler mHandler;
    public final Queue mQueue;

    public HandleableMonitoringTask(
            int i,
            int i2,
            int i3,
            int i4,
            int i5,
            IEndpointMonitorListener iEndpointMonitorListener,
            Predicate predicate,
            EndpointMonitorImpl.Injector injector) {
        super(i, i2, i3, i4, i5, iEndpointMonitorListener, predicate, injector);
        this.mQueue = new ConcurrentLinkedQueue();
        this.mHandler = injector.getHandler();
    }

    public void establish() {}

    public final Queue getDataQueue() {
        return this.mQueue;
    }

    public final void handle(EndpointData endpointData) {
        this.mQueue.offer(endpointData);
        this.mHandler.post(this);
    }

    public void release() {}
}
