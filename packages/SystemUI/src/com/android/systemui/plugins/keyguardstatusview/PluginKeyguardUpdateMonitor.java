package com.android.systemui.plugins.keyguardstatusview;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface PluginKeyguardUpdateMonitor {
    void dispatchFaceWidgetFullScreenMode(boolean z);

    int getCurrentUser();

    boolean getUserCanSkipBouncer(int i);

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
