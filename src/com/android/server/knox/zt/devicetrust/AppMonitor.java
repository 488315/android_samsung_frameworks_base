package com.android.server.knox.zt.devicetrust;

import com.android.server.LocalServices;

/* loaded from: classes6.dex */
public final class AppMonitor {
    private volatile boolean isMonitoringOn;
    private volatile EndpointMonitorInternal mInternal;

    private AppMonitor() {
    }

    public static AppMonitor get() {
        return InstanceHolder.INSTANCE;
    }

    public void reportApplicationBinding(long j, int i, int i2, String str, String str2) {
        if (getEndpointMonitor() != null) {
            this.mInternal.reportApplicationBinding(j, i, i2, str, str2);
        }
    }

    public void reportApplicationDying(long j, int i, int i2, String str, long j2) {
        if (getEndpointMonitor() != null) {
            this.mInternal.reportApplicationDying(j, i, i2, str, j2);
        }
    }

    private EndpointMonitorInternal getEndpointMonitor() {
        if (this.mInternal == null) {
            synchronized (this) {
                if (this.mInternal == null) {
                    this.mInternal = (EndpointMonitorInternal) LocalServices.getService(EndpointMonitorInternal.class);
                }
            }
        }
        return this.mInternal;
    }

    public boolean isOn() {
        return this.isMonitoringOn;
    }

    public void setOn(boolean z) {
        this.isMonitoringOn = z;
    }

    private static class InstanceHolder {
        private static final AppMonitor INSTANCE = new AppMonitor();

        private InstanceHolder() {
        }
    }
}
