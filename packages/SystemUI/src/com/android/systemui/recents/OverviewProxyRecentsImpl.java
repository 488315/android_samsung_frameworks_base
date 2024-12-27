package com.android.systemui.recents;

import android.os.Handler;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.policy.KeyguardStateController;

public final class OverviewProxyRecentsImpl implements RecentsImplementation {
    public final ActivityStarter mActivityStarter;
    public Handler mHandler;
    public final KeyguardStateController mKeyguardStateController;
    public final OverviewProxyService mOverviewProxyService;
    public boolean mThreeFingerKeyReleased = true;

    public OverviewProxyRecentsImpl(OverviewProxyService overviewProxyService, ActivityStarter activityStarter, KeyguardStateController keyguardStateController) {
        this.mOverviewProxyService = overviewProxyService;
        this.mActivityStarter = activityStarter;
        this.mKeyguardStateController = keyguardStateController;
    }
}
