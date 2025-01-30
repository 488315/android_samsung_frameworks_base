package com.android.systemui.plugins.keyguardstatusview;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
