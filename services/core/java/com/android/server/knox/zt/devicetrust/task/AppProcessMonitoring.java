package com.android.server.knox.zt.devicetrust.task;

import android.os.RemoteException;
import com.android.server.knox.zt.devicetrust.AppMonitor;
import com.android.server.knox.zt.devicetrust.EndpointMonitorImpl;
import com.android.server.knox.zt.devicetrust.data.EndpointData;
import com.samsung.android.knox.zt.devicetrust.IEndpointMonitorListener;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AppProcessMonitoring extends HandleableMonitoringTask {
    public final AppMonitor mAppMonitor;

    public AppProcessMonitoring(int i, int i2, int i3, int i4, IEndpointMonitorListener iEndpointMonitorListener, Predicate predicate, EndpointMonitorImpl.Injector injector) {
        super(7, i, i2, i4, i3, iEndpointMonitorListener, predicate, injector);
        injector.getClass();
        this.mAppMonitor = AppMonitor.get();
    }

    @Override // com.android.server.knox.zt.devicetrust.task.HandleableMonitoringTask
    public final void establish() {
        this.mAppMonitor.setOn(true);
    }

    @Override // com.android.server.knox.zt.devicetrust.task.MonitoringTask
    public final void onMonitored() {
        EndpointData endpointData = (EndpointData) this.mQueue.poll();
        if (endpointData == null || !this.mFilter.test(endpointData)) {
            return;
        }
        try {
            onEvent(endpointData);
        } catch (RemoteException e) {
            onTransactionFailure("Failed in transaction: ", e);
        }
    }

    @Override // com.android.server.knox.zt.devicetrust.task.HandleableMonitoringTask
    public final void release() {
        this.mAppMonitor.setOn(false);
    }
}
