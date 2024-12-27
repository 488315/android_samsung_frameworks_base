package com.android.server.knox.zt.devicetrust.task;

import android.os.RemoteException;
import com.android.server.knox.zt.devicetrust.EndpointMonitorImpl;
import com.android.server.knox.zt.devicetrust.data.SockEventData;
import com.samsung.android.knox.zt.devicetrust.IEndpointMonitorListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SocketStateMonitoring extends ReschedulableMonitoringTask {
    public SocketStateMonitoring(int i, int i2, int i3, int i4, IEndpointMonitorListener iEndpointMonitorListener, Predicate predicate, EndpointMonitorImpl.Injector injector) {
        super(3, i, i2, i3, i4, iEndpointMonitorListener, predicate, injector);
    }

    @Override // com.android.server.knox.zt.devicetrust.task.MonitoringTask
    public final void onMonitored() {
        ArrayList readSockData = this.mNative.readSockData();
        if (readSockData != null) {
            try {
                Iterator it = readSockData.iterator();
                while (it.hasNext()) {
                    onEvent((SockEventData) it.next());
                }
            } catch (RemoteException e) {
                onTransactionFailure("Failed in transaction: ", e);
            }
        }
        destroyChecked();
    }

    @Override // com.android.server.knox.zt.devicetrust.task.MonitoringTask
    public final void onTransactionFailure(String str, Throwable th) {
        super.onTransactionFailure(str, th);
        this.mListener = null;
    }
}
