package com.android.server.wm;

import com.android.server.wm.utils.OptPropFactory;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class AppCompatController {
    public final ActivityRecord mActivityRecord;
    public final AppCompatAspectRatioPolicy mAppCompatAspectRatioPolicy;
    public final AppCompatDeviceStateQuery mAppCompatDeviceStateQuery;
    public final AppCompatLetterboxPolicy mAppCompatLetterboxPolicy;
    public final AppCompatOverrides mAppCompatOverrides;
    public final AppCompatReachabilityPolicy mAppCompatReachabilityPolicy;
    public final AppCompatSizeCompatModePolicy mAppCompatSizeCompatModePolicy;
    public final DesktopAppCompatAspectRatioPolicy mDesktopAppCompatAspectRatioPolicy;
    public final AppCompatOrientationPolicy mOrientationPolicy;
    public final TransparentPolicy mTransparentPolicy;

    public AppCompatController(
            WindowManagerService windowManagerService, ActivityRecord activityRecord) {
        this.mActivityRecord = activityRecord;
        OptPropFactory optPropFactory =
                new OptPropFactory(
                        windowManagerService.mContext.getPackageManager(),
                        activityRecord.packageName);
        AppCompatDeviceStateQuery appCompatDeviceStateQuery =
                new AppCompatDeviceStateQuery(activityRecord);
        this.mAppCompatDeviceStateQuery = appCompatDeviceStateQuery;
        TransparentPolicy transparentPolicy =
                new TransparentPolicy(activityRecord, windowManagerService.mAppCompatConfiguration);
        this.mTransparentPolicy = transparentPolicy;
        AppCompatOverrides appCompatOverrides =
                new AppCompatOverrides(
                        activityRecord,
                        windowManagerService.mAppCompatConfiguration,
                        optPropFactory,
                        appCompatDeviceStateQuery);
        this.mAppCompatOverrides = appCompatOverrides;
        this.mOrientationPolicy =
                new AppCompatOrientationPolicy(activityRecord, appCompatOverrides);
        this.mAppCompatAspectRatioPolicy =
                new AppCompatAspectRatioPolicy(
                        activityRecord, transparentPolicy, appCompatOverrides);
        this.mAppCompatReachabilityPolicy =
                new AppCompatReachabilityPolicy(
                        activityRecord, windowManagerService.mAppCompatConfiguration);
        this.mAppCompatLetterboxPolicy =
                new AppCompatLetterboxPolicy(
                        activityRecord, windowManagerService.mAppCompatConfiguration);
        this.mDesktopAppCompatAspectRatioPolicy =
                new DesktopAppCompatAspectRatioPolicy(
                        activityRecord,
                        appCompatOverrides,
                        transparentPolicy,
                        windowManagerService.mAppCompatConfiguration);
        this.mAppCompatSizeCompatModePolicy = new AppCompatSizeCompatModePolicy(activityRecord);
    }
}
