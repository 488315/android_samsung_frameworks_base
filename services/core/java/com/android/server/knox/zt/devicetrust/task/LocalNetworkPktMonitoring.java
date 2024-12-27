package com.android.server.knox.zt.devicetrust.task;

import com.android.server.knox.zt.devicetrust.EndpointMonitorImpl;

import com.samsung.android.knox.zt.devicetrust.IEndpointMonitorListener;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class LocalNetworkPktMonitoring extends NetworkEventMonitoring {
    public LocalNetworkPktMonitoring(
            int i,
            int i2,
            int i3,
            int i4,
            int i5,
            IEndpointMonitorListener iEndpointMonitorListener,
            EndpointMonitorImpl.Injector injector) {
        super(16, i, i3, i4, i5, iEndpointMonitorListener, injector);
    }
}
