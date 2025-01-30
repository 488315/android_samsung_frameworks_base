package com.samsung.android.server.packagefeature;

import com.samsung.android.rune.CoreRune;

/* loaded from: classes2.dex */
public enum PackageFeatureGroup {
    FOLDABLE_PACKAGE_FEATURE(true, "FoldablePackagePolicy", CoreRune.IS_TABLET_DEVICE ? 17825801 : 17825800),
    REFRESH_RATE_PACKAGE_FEATURE(CoreRune.FW_VRR_POLICY, "RefreshRatePolicy", 17825802),
    BROADCAST_RECEIVER_FEATURE(true, "BROADCAST_RECEIVER_ALLOW_LIST", true),
    TEST_PACKAGE_FEATURE_GROUP(CoreRune.SAFE_DEBUG, "TestPackageFeatureGroup", 0);

    public final boolean mEnabled;
    public final String mName;
    public final int mRawResId;
    public final boolean mUnformatted;

    PackageFeatureGroup(boolean z, String str, int i) {
        this(z, str, i, false);
    }

    PackageFeatureGroup(boolean z, String str, boolean z2) {
        this(z, str, 0, z2);
    }

    PackageFeatureGroup(boolean z, String str, int i, boolean z2) {
        this.mEnabled = z;
        this.mName = str;
        this.mRawResId = i;
        this.mUnformatted = z2;
    }
}
