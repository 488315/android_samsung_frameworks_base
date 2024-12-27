package com.android.server.wm;

import android.util.Slog;

import com.samsung.android.rune.CoreRune;
import com.samsung.android.server.packagefeature.PackageFeature;
import com.samsung.android.server.packagefeature.PackageFeatureUserChange;
import com.samsung.android.server.packagefeature.PackageFeatureUserChangePersister;
import com.samsung.android.server.packagefeature.util.PackageSpecialManagementList;

public final class MultiTaskingAppCompatOrientationOverrides {
    public static final boolean SUPPORTS_DEFAULT_ENABLED = CoreRune.IS_TABLET_DEVICE;
    public PackageSpecialManagementList mDefaultDisabledList;
    public boolean mDefaultEnabled;
    public PackageSpecialManagementList mDefaultEnabledList;
    public final MultiTaskingAppCompatOrientationOverrides$$ExternalSyntheticLambda0 mDumpInterface;
    public PackageSpecialManagementList mTabletRotationCompatList;
    public final PackageFeatureUserChange mUserOverride;

    public MultiTaskingAppCompatOrientationOverrides() {
        MultiTaskingAppCompatOrientationOverrides$$ExternalSyntheticLambda0
                multiTaskingAppCompatOrientationOverrides$$ExternalSyntheticLambda0 =
                        new MultiTaskingAppCompatOrientationOverrides$$ExternalSyntheticLambda0();
        String str = PackageFeatureUserChangePersister.PACKAGE_SETTINGS_DIRECTORY;
        boolean z = CoreRune.MT_APP_COMPAT_LANDSCAPE_VIEW_FOR_PORTRAIT_APPS;
        this.mUserOverride =
                new PackageFeatureUserChange(
                        64,
                        str,
                        "OrientationControlPackageMap",
                        multiTaskingAppCompatOrientationOverrides$$ExternalSyntheticLambda0,
                        z,
                        null);
        if (z) {
            setDefaultEnabled(SUPPORTS_DEFAULT_ENABLED);
        }
    }

    public final int getRespectOrientationRequest(int i, String str) {
        int intValue;
        int adjustedUserId = MultiTaskingAppCompatUtils.getAdjustedUserId(i, 3, null);
        if (str == null
                || CompatChangeableAppsCache.LazyHolder.sCache.query(
                        new CompatChangeableAppsCache$$ExternalSyntheticLambda0(str, 0),
                        adjustedUserId)) {
            return -1;
        }
        synchronized (this) {
            try {
                Integer num = (Integer) this.mUserOverride.getValue(adjustedUserId, str);
                intValue = num != null ? num.intValue() : -1;
            } finally {
            }
        }
        if (intValue == -1) {
            synchronized (this) {
                try {
                    if (this.mDefaultEnabled) {
                        PackageSpecialManagementList packageSpecialManagementList =
                                this.mDefaultDisabledList;
                        if (packageSpecialManagementList == null
                                || !packageSpecialManagementList.contains(str)) {
                            CompatChangeableAppsCache compatChangeableAppsCache =
                                    CompatChangeableAppsCache.LazyHolder.sCache;
                            if (compatChangeableAppsCache.query(
                                            new CompatChangeableAppsCache$$ExternalSyntheticLambda0(
                                                    str, 1),
                                            adjustedUserId)
                                    && !compatChangeableAppsCache.query(
                                            new CompatChangeableAppsCache$$ExternalSyntheticLambda0(
                                                    str, 3),
                                            adjustedUserId)) {}
                        } else {
                            intValue = 0;
                        }
                    }
                    PackageSpecialManagementList packageSpecialManagementList2 =
                            this.mDefaultEnabledList;
                    intValue =
                            (packageSpecialManagementList2 == null
                                            || !packageSpecialManagementList2.contains(str))
                                    ? -1
                                    : 31;
                } finally {
                }
            }
            if (intValue == -1) {
                intValue = -1;
            }
        }
        if (intValue == -1) {
            return -1;
        }
        int i2 = intValue & (-193);
        if (i2 == 0 || i2 == 7 || i2 == 31 || i2 == 32) {
            return i2;
        }
        if ((intValue & 7) != 0) {
            return (intValue & 24) != 0 ? 31 : 7;
        }
        return 0;
    }

    public final void setDefaultEnabled(boolean z) {
        synchronized (this) {
            try {
                Slog.d(
                        "MultiTaskingAppCompat",
                        "setDefaultEnabled: " + this.mDefaultEnabled + " to " + z);
                this.mDefaultEnabled = z;
                if (CoreRune.MT_APP_COMPAT_LANDSCAPE_VIEW_FOR_PORTRAIT_APPS) {
                    if (z) {
                        this.mDefaultDisabledList =
                                new PackageSpecialManagementList(
                                        PackageFeature.IGNORE_APP_ROTATION_DISABLED);
                    } else {
                        this.mDefaultEnabledList =
                                new PackageSpecialManagementList(
                                        PackageFeature.IGNORE_APP_ROTATION);
                    }
                    if (CoreRune.IS_TABLET_DEVICE) {
                        this.mTabletRotationCompatList =
                                new PackageSpecialManagementList(
                                        PackageFeature.TABLET_APP_ROTATION_COMPAT);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
