package com.android.server.wm;

import com.android.server.wm.utils.OptPropFactory;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class AppCompatOverrides {
    public final AppCompatAspectRatioOverrides mAppCompatAspectRatioOverrides;
    public final AppCompatCameraOverrides mAppCompatCameraOverrides;
    public final AppCompatFocusOverrides mAppCompatFocusOverrides;
    public final AppCompatLetterboxOverrides mAppCompatLetterboxOverrides;
    public final AppCompatOrientationOverrides mAppCompatOrientationOverrides;
    public final AppCompatReachabilityOverrides mAppCompatReachabilityOverrides;
    public final AppCompatResizeOverrides mAppCompatResizeOverrides;

    public AppCompatOverrides(ActivityRecord activityRecord, AppCompatConfiguration appCompatConfiguration, OptPropFactory optPropFactory, AppCompatDeviceStateQuery appCompatDeviceStateQuery) {
        this.mAppCompatCameraOverrides = new AppCompatCameraOverrides(activityRecord, appCompatConfiguration, optPropFactory);
        this.mAppCompatOrientationOverrides = new AppCompatOrientationOverrides(activityRecord, appCompatConfiguration, optPropFactory);
        AppCompatReachabilityOverrides appCompatReachabilityOverrides = new AppCompatReachabilityOverrides(activityRecord, appCompatConfiguration, appCompatDeviceStateQuery);
        this.mAppCompatReachabilityOverrides = appCompatReachabilityOverrides;
        this.mAppCompatAspectRatioOverrides = new AppCompatAspectRatioOverrides(activityRecord, appCompatConfiguration, optPropFactory, appCompatDeviceStateQuery, appCompatReachabilityOverrides);
        this.mAppCompatFocusOverrides = new AppCompatFocusOverrides(activityRecord, appCompatConfiguration, optPropFactory);
        this.mAppCompatResizeOverrides = new AppCompatResizeOverrides(activityRecord, optPropFactory);
        this.mAppCompatLetterboxOverrides = new AppCompatLetterboxOverrides(activityRecord, appCompatConfiguration);
    }
}
