package com.samsung.android.knox.p046zt.devicetrust.monitor;

import android.os.Bundle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class EndpointMonitoringListener implements IMonitoringListener {
    @Override // com.samsung.android.knox.p046zt.devicetrust.monitor.IMonitoringListener
    public final int checkUrlReputation(String str) {
        return 0;
    }

    @Override // com.samsung.android.knox.p046zt.devicetrust.monitor.IMonitoringListener
    public final void onEvent(int i, Bundle bundle) {
    }

    @Override // com.samsung.android.knox.p046zt.devicetrust.monitor.IMonitoringListener
    public final void onEventGeneralized(int i, String str) {
    }

    @Override // com.samsung.android.knox.p046zt.devicetrust.monitor.IMonitoringListener
    public final void onEventSimplified(int i, String str) {
    }

    @Override // com.samsung.android.knox.p046zt.devicetrust.monitor.IMonitoringListener
    public final void onUnauthorizedAccessDetected(int i, int i2, int i3, long j, int i4, int i5, String str, String str2) {
    }
}
