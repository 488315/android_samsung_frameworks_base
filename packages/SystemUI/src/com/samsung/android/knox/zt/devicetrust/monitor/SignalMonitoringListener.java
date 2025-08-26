package com.samsung.android.knox.zt.devicetrust.monitor;

import android.os.Bundle;

/* loaded from: classes4.dex */
public abstract class SignalMonitoringListener implements IMonitoringListener {
    @Override // com.samsung.android.knox.zt.devicetrust.monitor.IMonitoringListener
    public final int checkUrlReputation(String str) {
        return 0;
    }

    @Override // com.samsung.android.knox.zt.devicetrust.monitor.IMonitoringListener
    public abstract int onSignal(String str);

    @Override // com.samsung.android.knox.zt.devicetrust.monitor.IMonitoringListener
    public final void onEvent(int i, Bundle bundle) {
    }

    @Override // com.samsung.android.knox.zt.devicetrust.monitor.IMonitoringListener
    public final void onEventGeneralized(int i, String str) {
    }

    @Override // com.samsung.android.knox.zt.devicetrust.monitor.IMonitoringListener
    public final void onEventSimplified(int i, String str) {
    }

    @Override // com.samsung.android.knox.zt.devicetrust.monitor.IMonitoringListener
    public final void onUnauthorizedAccessDetected(int i, int i2, int i3, long j, int i4, int i5, String str, String str2) {
    }
}
