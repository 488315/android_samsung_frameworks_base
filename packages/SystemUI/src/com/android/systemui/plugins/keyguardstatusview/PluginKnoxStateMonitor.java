package com.android.systemui.plugins.keyguardstatusview;

import com.android.systemui.plugins.annotations.SupportVersionChecker;
import com.android.systemui.plugins.annotations.VersionCheck;

@SupportVersionChecker
/* loaded from: classes2.dex */
public interface PluginKnoxStateMonitor {
    boolean isLockscreenAllDisabled();

    @VersionCheck(version = 3012)
    boolean isLockscreenBatteryInfoEnabled();

    boolean isLockscreenClockEnabled();

    boolean isLockscreenDateEnabled();

    boolean isLockscreenOwnerInfoEnabled();

    @VersionCheck(version = 3030)
    boolean isStatusBarHidden();
}
