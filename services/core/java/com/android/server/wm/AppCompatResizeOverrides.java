package com.android.server.wm;

import com.android.server.wm.utils.OptPropFactory;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class AppCompatResizeOverrides {
    public final ActivityRecord mActivityRecord;
    public final OptPropFactory.OptProp mAllowForceResizeOverrideOptProp;

    public AppCompatResizeOverrides(ActivityRecord activityRecord, OptPropFactory optPropFactory) {
        this.mActivityRecord = activityRecord;
        this.mAllowForceResizeOverrideOptProp = optPropFactory.create("android.window.PROPERTY_COMPAT_ALLOW_RESIZEABLE_ACTIVITY_OVERRIDES");
    }
}
