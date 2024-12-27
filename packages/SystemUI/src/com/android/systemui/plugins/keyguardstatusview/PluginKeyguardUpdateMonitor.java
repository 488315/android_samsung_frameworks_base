package com.android.systemui.plugins.keyguardstatusview;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
