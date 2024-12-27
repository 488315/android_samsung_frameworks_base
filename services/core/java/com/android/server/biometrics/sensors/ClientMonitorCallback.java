package com.android.server.biometrics.sensors;

public interface ClientMonitorCallback {
    default void onBiometricAction() {}

    void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z);

    default void onClientStarted(BaseClientMonitor baseClientMonitor) {}
}
