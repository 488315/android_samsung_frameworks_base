package com.android.systemui.plugins.keyguardstatusview;

public interface PluginKeyguardUpdateMonitor {
    void dispatchFaceWidgetFullScreenMode(boolean z);

    int getCurrentUser();

    boolean isDeviceInteractive();

    boolean isDeviceProvisioned();

    boolean isEnabledBioUnlock();

    boolean isFMMLock();

    boolean isFingerprintOptionEnabled();

    boolean isKeyguardShowing();

    boolean isKeyguardUnlocking();

    boolean isKeyguardVisible();

    boolean isRemoteLock();

    boolean isSystemUser();

    boolean isUnlockWithFingerprintPossible(int i);

    boolean isUserUnlocked();

    void registerCallback(PluginKeyguardUpdateMonitorCallback pluginKeyguardUpdateMonitorCallback);

    void removeCallback(PluginKeyguardUpdateMonitorCallback pluginKeyguardUpdateMonitorCallback);
}
