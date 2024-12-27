package com.android.systemui.plugins.keyguardstatusview;

import com.android.systemui.plugins.annotations.SupportVersionChecker;
import com.android.systemui.plugins.annotations.VersionCheck;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@SupportVersionChecker
public interface PluginKnoxStateMonitor {
    boolean isLockscreenAllDisabled();

    @VersionCheck(version = 3012)
    boolean isLockscreenBatteryInfoEnabled();

    boolean isLockscreenClockEnabled();

    boolean isLockscreenDateEnabled();

    boolean isLockscreenOwnerInfoEnabled();
}
