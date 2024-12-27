package com.android.server.knox.zt.devicetrust;

import com.android.server.LocalServices;

/* loaded from: classes5.dex */
public final class AppMonitor {
    private volatile boolean isMonitoringOn;
    private volatile EndpointMonitorInternal mInternal;

    private AppMonitor() {
    }

    public static AppMonitor get() {
        return InstanceHolder.INSTANCE;
    }

    public void reportApplicationBinding(long bindingTime, int pid, int uid, String procName, String label) {
        if (getEndpointMonitor() != null) {
            this.mInternal.reportApplicationBinding(bindingTime, pid, uid, procName, label);
        }
    }

    public void reportApplicationDying(long dyingTime, int pid, int uid, String procName, long cpuTime) {
        if (getEndpointMonitor() != null) {
            this.mInternal.reportApplicationDying(dyingTime, pid, uid, procName, cpuTime);
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

    public void setOn(boolean onOff) {
        this.isMonitoringOn = onOff;
    }

    private static class InstanceHolder {
        private static final AppMonitor INSTANCE = new AppMonitor();

        private InstanceHolder() {
        }
    }
}
