package com.android.systemui.plugins.keyguardstatusview;

import com.android.systemui.plugins.annotations.SupportVersionChecker;
import com.android.systemui.plugins.annotations.VersionCheck;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
