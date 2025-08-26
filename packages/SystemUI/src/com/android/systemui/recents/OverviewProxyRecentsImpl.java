package com.android.systemui.recents;

import android.os.Handler;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.policy.KeyguardStateController;

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
