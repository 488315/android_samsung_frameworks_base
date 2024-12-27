package com.android.server.wm;

public final class AppCompatOrientationPolicy {
    public final ActivityRecord mActivityRecord;
    public final AppCompatOverrides mAppCompatOverrides;

    public AppCompatOrientationPolicy(
            ActivityRecord activityRecord, AppCompatOverrides appCompatOverrides) {
        this.mActivityRecord = activityRecord;
        this.mAppCompatOverrides = appCompatOverrides;
    }
}
