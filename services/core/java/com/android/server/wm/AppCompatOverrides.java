package com.android.server.wm;

import com.android.server.wm.utils.OptPropFactory;

public final class AppCompatOverrides {
    public final AppCompatAspectRatioOverrides mAppCompatAspectRatioOverrides;
    public final AppCompatCameraOverrides mAppCompatCameraOverrides;
    public final AppCompatFocusOverrides mAppCompatFocusOverrides;
    public final AppCompatLetterboxOverrides mAppCompatLetterboxOverrides;
    public final AppCompatOrientationOverrides mAppCompatOrientationOverrides;
    public final AppCompatReachabilityOverrides mAppCompatReachabilityOverrides;
    public final AppCompatResizeOverrides mAppCompatResizeOverrides;

    public AppCompatOverrides(
            ActivityRecord activityRecord,
            AppCompatConfiguration appCompatConfiguration,
            OptPropFactory optPropFactory,
            AppCompatDeviceStateQuery appCompatDeviceStateQuery) {
        this.mAppCompatCameraOverrides =
                new AppCompatCameraOverrides(
                        activityRecord, appCompatConfiguration, optPropFactory);
        this.mAppCompatOrientationOverrides =
                new AppCompatOrientationOverrides(
                        activityRecord, appCompatConfiguration, optPropFactory);
        AppCompatReachabilityOverrides appCompatReachabilityOverrides =
                new AppCompatReachabilityOverrides(
                        activityRecord, appCompatConfiguration, appCompatDeviceStateQuery);
        this.mAppCompatReachabilityOverrides = appCompatReachabilityOverrides;
        this.mAppCompatAspectRatioOverrides =
                new AppCompatAspectRatioOverrides(
                        activityRecord,
                        appCompatConfiguration,
                        optPropFactory,
                        appCompatDeviceStateQuery,
                        appCompatReachabilityOverrides);
        this.mAppCompatFocusOverrides =
                new AppCompatFocusOverrides(activityRecord, appCompatConfiguration, optPropFactory);
        this.mAppCompatResizeOverrides =
                new AppCompatResizeOverrides(activityRecord, optPropFactory);
        this.mAppCompatLetterboxOverrides =
                new AppCompatLetterboxOverrides(activityRecord, appCompatConfiguration);
    }
}
