package com.android.systemui.plugins.keyguardstatusview;

public interface PluginLockPatternUtils {
    String getDeviceOwnerInfo();

    String getOwnerInfo(int i);

    boolean isDeviceOwnerInfoEnabled();

    boolean isOwnerInfoEnabled(int i);
}
