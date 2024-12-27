package com.samsung.android.server.packagefeature;

import com.samsung.android.rune.CoreRune;

public enum PackageFeatureGroup {
    FOLDABLE_PACKAGE_FEATURE("FOLDABLE_PACKAGE_FEATURE", "FoldablePackagePolicy", true, false),
    REFRESH_RATE_PACKAGE_FEATURE(
            "REFRESH_RATE_PACKAGE_FEATURE", "RefreshRatePolicy", CoreRune.FW_VRR_POLICY, false),
    BROADCAST_RECEIVER_FEATURE(
            "BROADCAST_RECEIVER_FEATURE", "BROADCAST_RECEIVER_ALLOW_LIST", true, true),
    APP_CATEGORY_FEATURE("APP_CATEGORY_FEATURE", "appcategory", true, true),
    TEST_PACKAGE_FEATURE_GROUP(
            "TEST_PACKAGE_FEATURE_GROUP", "TestPackageFeatureGroup", false, false);

    public final boolean mEnabled;
    public final String mName;
    public final int mRawResId;
    public final boolean mUnformatted;

    PackageFeatureGroup(String str, String str2, boolean z, boolean z2) {
        this.mEnabled = z;
        this.mName = str2;
        this.mRawResId = r2;
        this.mUnformatted = z2;
    }
}
