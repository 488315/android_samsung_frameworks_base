package com.samsung.android.knox.zt.devicetrust.monitor;

import android.os.Bundle;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class EndpointMonitoringListener implements IMonitoringListener {
    @Override // com.samsung.android.knox.zt.devicetrust.monitor.IMonitoringListener
    public int checkUrlReputation(String str) {
        return 0;
    }

    @Override // com.samsung.android.knox.zt.devicetrust.monitor.IMonitoringListener
    public int onSignal(String str) {
        return 0;
    }

    @Override // com.samsung.android.knox.zt.devicetrust.monitor.IMonitoringListener
    public void onEvent(int i, Bundle bundle) {
    }

    @Override // com.samsung.android.knox.zt.devicetrust.monitor.IMonitoringListener
    public void onEventGeneralized(int i, String str) {
    }

    @Override // com.samsung.android.knox.zt.devicetrust.monitor.IMonitoringListener
    public void onEventSimplified(int i, String str) {
    }

    @Override // com.samsung.android.knox.zt.devicetrust.monitor.IMonitoringListener
    public void onUnauthorizedAccessDetected(int i, int i2, int i3, long j, int i4, int i5, String str, String str2) {
    }
}
