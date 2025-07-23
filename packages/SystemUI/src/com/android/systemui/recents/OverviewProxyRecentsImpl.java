package com.android.systemui.recents;

import android.os.Handler;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.policy.KeyguardStateController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class OverviewProxyRecentsImpl implements RecentsImplementation {
    public final ActivityStarter mActivityStarter;
    public Handler mHandler;
    public final KeyguardStateController mKeyguardStateController;
    public final LauncherProxyService mLauncherProxyService;
    public boolean mThreeFingerKeyReleased = true;
    public boolean mLeftOrRightEventSent = false;

    public OverviewProxyRecentsImpl(LauncherProxyService launcherProxyService, ActivityStarter activityStarter, KeyguardStateController keyguardStateController) {
        this.mLauncherProxyService = launcherProxyService;
        this.mActivityStarter = activityStarter;
        this.mKeyguardStateController = keyguardStateController;
    }
}
