package com.android.server.wm;

import com.android.server.wm.utils.OptPropFactory;

public final class AppCompatResizeOverrides {
    public final ActivityRecord mActivityRecord;
    public final OptPropFactory.OptProp mAllowForceResizeOverrideOptProp;

    public AppCompatResizeOverrides(ActivityRecord activityRecord, OptPropFactory optPropFactory) {
        this.mActivityRecord = activityRecord;
        this.mAllowForceResizeOverrideOptProp =
                optPropFactory.create(
                        "android.window.PROPERTY_COMPAT_ALLOW_RESIZEABLE_ACTIVITY_OVERRIDES");
    }
}
