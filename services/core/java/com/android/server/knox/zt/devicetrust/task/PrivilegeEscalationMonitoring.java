package com.android.server.knox.zt.devicetrust.task;

import android.os.RemoteException;

import com.android.server.knox.zt.devicetrust.EndpointMonitorImpl;
import com.android.server.knox.zt.devicetrust.data.ProcEventData;

import com.samsung.android.knox.zt.devicetrust.IEndpointMonitorListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Predicate;

public final class PrivilegeEscalationMonitoring extends ReschedulableMonitoringTask {
    public PrivilegeEscalationMonitoring(
            int i,
            int i2,
            int i3,
            int i4,
            IEndpointMonitorListener iEndpointMonitorListener,
            Predicate predicate,
            EndpointMonitorImpl.Injector injector) {
        super(12, i, i2, i3, i4, iEndpointMonitorListener, predicate, injector);
    }

    @Override // com.android.server.knox.zt.devicetrust.task.MonitoringTask
    public final void onMonitored() {
        ArrayList readPrivEscalData = this.mNative.readPrivEscalData();
        if (readPrivEscalData != null) {
            try {
                Iterator it = readPrivEscalData.iterator();
                while (it.hasNext()) {
                    onEvent((ProcEventData) it.next());
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
