package com.android.systemui.plugins.keyguardstatusview;

import com.android.systemui.plugins.annotations.SupportVersionChecker;
import com.android.systemui.plugins.annotations.VersionCheck;

@SupportVersionChecker
public interface PluginKnoxStateMonitor {
    boolean isLockscreenAllDisabled();

    @VersionCheck(version = 3012)
    boolean isLockscreenBatteryInfoEnabled();

    boolean isLockscreenClockEnabled();

    boolean isLockscreenDateEnabled();

    boolean isLockscreenOwnerInfoEnabled();
}
