package com.android.systemui.recents;

import android.os.Handler;
import com.android.systemui.plugins.ActivityStarter;
import dagger.Lazy;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class OverviewProxyRecentsImpl implements RecentsImplementation {
    public final ActivityStarter mActivityStarter;
    public final Lazy mCentralSurfacesOptionalLazy;
    public Handler mHandler;
    public final OverviewProxyService mOverviewProxyService;
    public boolean mThreeFingerKeyReleased = true;

    public OverviewProxyRecentsImpl(Lazy lazy, OverviewProxyService overviewProxyService, ActivityStarter activityStarter) {
        this.mCentralSurfacesOptionalLazy = lazy;
        this.mOverviewProxyService = overviewProxyService;
        this.mActivityStarter = activityStarter;
    }
}
